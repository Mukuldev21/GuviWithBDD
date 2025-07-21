# Selenium Cucumber Test Automation

This project is a test automation framework using **Java**, **Selenium WebDriver**, **Cucumber**, and **Maven**. It automates UI tests for a web application, supporting data-driven testing with JSON files.

## Project Structure

- `src/test/java/stepDefinitions/`  
  Contains Cucumber step definitions (e.g., `LoginSteps.java`).

- `src/test/java/pages/`  
  Page Object Model classes for each web page.

- `src/test/java/hooks/`  
  Cucumber hooks for setup/teardown and utilities.

- `src/test/resources/features/`  
  Gherkin feature files describing test scenarios.

- `src/test/resources/testdata/`  
  JSON files for test data (e.g., login credentials, course names).

- `pom.xml`  
  Maven configuration with dependencies.

## Key Technologies

- **Java 11+**
- **Selenium WebDriver**
- **Cucumber (BDD)**
- **TestNG**
- **Maven**
- **Gson** (for JSON parsing)

## How to Run

1. **Clone the repository**

2. **Install dependencies**