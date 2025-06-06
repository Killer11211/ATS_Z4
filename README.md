name: Test and Build Java App

on:
  workflow_dispatch:
  push:
    branches: [ main ]

jobs:
  test_build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v3

    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        distribution: 'temurin'
        java-version: '17'

    # Krok 1: Dry run aplikacji
    - name: Dry Run Application
      run: |
        echo "=== Dry Run ==="
        mvn clean compile -DskipTests
        mvn exec:java -Dexec.mainClass="com.example.Main"
      env:
        DRY_RUN: "true"

    # Krok 2: Testy jednostkowe
    - name: Run Unit Tests
      run: mvn test

    # Krok 3: Budowanie pliku .jar
    - name: Package Application (JAR)
      run: mvn package -DskipTests

    # Krok 4: Automatyczne testowanie zbudowanego .jar
    - name: Run JAR (automated test)
      run: |
        echo "=== Testing built JAR ==="
        java -jar target/*.jar --dry-run
      env:
        DRY_RUN: "true"

    # Krok 5: Udostępnienie artefaktu
    - name: Upload .jar artifact
      uses: actions/upload-artifact@v4
      with:
        name: my-application
        path: target/*.jar
