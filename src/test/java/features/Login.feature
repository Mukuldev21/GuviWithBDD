Feature: Login Functionality

    Background:
      Given I am on the Landing page
      And I navigate to the Login page

    @smoke
    Scenario: Successful login with valid credentials
      When I login with valid credentials from json file
      Then I should be logged in successfully

    @smoke
    Scenario Outline: Unsuccessful login with incorrect credentials
      When I login with email "<email>" and password "<password>"
      Then I should see an error message for invalid credentials

      Examples:
        | email               | password      |
        | invalid@example.com | wrongPassword |
        | test@guvi.in        | 123456        |

    @smoke
    Scenario: Login with blank fields
      When I login with blank email and password
      Then I should see validation errors for required fields