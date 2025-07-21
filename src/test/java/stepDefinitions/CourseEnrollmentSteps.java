package stepDefinitions;

import hooks.StepErrorTracker;
import hooks.StepTracker;
import hooks.cucumberHooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import pages.HomePage;
import pages.LandingPage;

import java.util.List;

public class CourseEnrollmentSteps {

    HomePage homePage = new HomePage(cucumberHooks.getDriver());
    LandingPage landingPage = new LandingPage(cucumberHooks.getDriver());

    @When("I navigate to the Paid Courses tab")
    public void navigateToPaidCoursesTab() {
        try {
            StepTracker.setLastStepText("I navigate to the Paid Courses tab");
            homePage.clickPaidCoursesTab();
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error navigating to Paid Courses tab: " + e.getMessage());
        }
    }

    /*@And("I search for the course name from the JSON file")
    public void searchCourseFromJson() {
        try {
            String courseName = cucumberHooks.courseEnrollmentDetailsJson.get("Course Name").getAsString();
            StepTracker.setLastStepText("Searching for course: " + courseName);
            homePage.enterSearchText(courseName);
            homePage.clickSearchButton();
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error searching for course: " + e.getMessage());
        }
    }*/
   @And("I search for the course name from the JSON file")
    public void searchCourseFromJson() {
        try {
            String courseName = cucumberHooks.courseEnrollmentDetailsJson.get("Course Name").getAsString();
            StepTracker.setLastStepText("I search for the course name from the JSON file: " + courseName);

            // Enter course name in the search box
            homePage.enterSearchText(courseName);
            homePage.clickSearchButton();

            // Wait for search results or "No results" message
            List<WebElement> results = homePage.getCourseResults();
            if (results.isEmpty() && homePage.isNoResultsDisplayed()) {
                StepErrorTracker.setLastError("No courses found matching the keyword: " + courseName);
                return;
            }

            // Verify if the course is displayed in the search results
            WebElement courseCard = homePage.findCourseByTitle(courseName);
            if (courseCard == null) {
                StepErrorTracker.setLastError("Course not found in search results: " + courseName);
                return;
            }

            // Click the course card
            boolean isCourseClicked = homePage.clickCourseIfPresent(courseName);
            if (!isCourseClicked) {
                StepErrorTracker.setLastError("Failed to click the course card: " + courseName);
            }
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error searching for course: " + e.getMessage());
        }
    }

    @And("I select the course from the search results")
    public void selectCourseFromSearchResults() {
        try {
            String courseName = cucumberHooks.courseEnrollmentDetailsJson.get("Course Name").getAsString();
            StepTracker.setLastStepText("Selecting course: " + courseName);
            boolean isCourseSelected = homePage.clickCourseIfPresent(courseName);
            if (!isCourseSelected) {
                StepErrorTracker.setLastError("Course not found: " + courseName);
            }
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error selecting course: " + e.getMessage());
        }
    }


    @And("I click the Enroll Now button")
    public void clickEnrollButton() {
        try {
            StepTracker.setLastStepText("Clicking button: " + "Enroll Now");
            homePage.clickEnrollNowButton();
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error clicking Enroll Now button: - " + e.getMessage());
        }
    }

    @And("I should see the payment screen")
    public void verifyPaymentScreen() {
        try {
            StepTracker.setLastStepText("Verifying payment screen");

            // Verify if the payment screen is displayed
            if (!homePage.isPaymentScreenDisplayed()) {
                StepErrorTracker.setLastError("Payment screen is not displayed");
                return;
            }

            // Click the "Pay Now" button
            homePage.clickPayNow();

            // Verify if the payment frame is visible
            if (!homePage.isPaymentFrameVisible()) {
                StepErrorTracker.setLastError("Payment frame is not visible");
            }
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error verifying payment screen: " + e.getMessage());
        }
    }

    @And("I close the checkout")
    public void closeCheckout() {
        try {
            StepTracker.setLastStepText("Closing checkout");
            homePage.clickCloseCheckoutButton();
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error closing checkout: " + e.getMessage());
        }
    }

    @And("I confirm exit by clicking {string}")
    public void confirmExit(String exitOption) {
        try {
            StepTracker.setLastStepText("Confirming exit by clicking: " + exitOption);
            boolean isExitConfirmed = homePage.confirmAndExit();
            if (!isExitConfirmed) {
                StepErrorTracker.setLastError("Error confirming exit");
            }
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error confirming exit: " + e.getMessage());
        }
    }

   @And("I select the feedback checkbox for question {int} using data from the JSON file")
    public void selectFeedbackCheckbox(int questionNumber) {
        try {
            StepTracker.setLastStepText("Selecting feedback checkbox for question: " + questionNumber);

            // Verify and exit payment
            if (!homePage.validateAndExitPayment()) {
                StepErrorTracker.setLastError("Failed to validate and exit payment");
                return;
            }

            // Verify if the "Help Us" modal is displayed
            if (!homePage.isHelpUsModalDisplayed()) {
                StepErrorTracker.setLastError("Help Us modal is not displayed");
                return;
            }

            // Select feedback checkbox based on the question number
            if (questionNumber == 1) {
                String feedbackReason = cucumberHooks.courseEnrollmentDetailsJson.get("feedbackReason").getAsString();
                homePage.verifyAndSelectFeedbackCheckbox(feedbackReason, cucumberHooks.courseEnrollmentDetailsJson);
            } else if (questionNumber == 2) {
                String feedbackImprovement = cucumberHooks.courseEnrollmentDetailsJson.get("feedbackImprovement").getAsString();
                homePage.verifyAndSelectImprovementCheckbox(feedbackImprovement, cucumberHooks.courseEnrollmentDetailsJson);
            }
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error selecting feedback checkbox for question: " + questionNumber + " - " + e.getMessage());
        }
    }

    @And("I submit the feedback")
    public void submitFeedback() {
        try {
            StepTracker.setLastStepText("Submitting feedback");
            homePage.clickFeedbackSubmitButton();
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error submitting feedback: " + e.getMessage());
        }
    }

    @Then("I should see the feedback completion message and close it")
    public void verifyFeedbackCompletionMessage() {
        try {
            StepTracker.setLastStepText("Verifying feedback completion message");
            boolean isMessageDisplayed = homePage.validateAndCloseFeedbackCompletion();
            if (!isMessageDisplayed) {
                StepErrorTracker.setLastError("Feedback completion message not displayed");
            }
        } catch (Exception e) {
            StepErrorTracker.setLastError("Error verifying feedback completion message: " + e.getMessage());
        }
    }
}