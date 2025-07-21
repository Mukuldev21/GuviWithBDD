Feature: Course Enrollment End-to-End Flow

  Background:
    Given I am on the Landing page
    And I navigate to the Login page
    And I login with valid credentials from json file
    And I should be logged in successfully

  @Course
  Scenario: I enroll in a paid course and submit feedback after canceling the payment
    When I navigate to the Paid Courses tab
    And I search for the course name from the JSON file
    And I select the course from the search results
    And I click the Enroll Now button
    And I should see the payment screen
    And I close the checkout
    And I confirm exit by clicking "Exit payment"
    And I select the feedback checkbox for question 1 using data from the JSON file
    And I select the feedback checkbox for question 2 using data from the JSON file
    And I submit the feedback
    Then I should see the feedback completion message and close it