package stepDefinitions;

import com.google.gson.JsonObject;
import hooks.cucumberHooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.HomePage;
import pages.LandingPage;
import pages.LoginPage;
import hooks.StepErrorTracker;
import hooks.StepTracker;
import utils.ExtentStepLogger;
import com.aventstack.extentreports.ExtentTest;

public class LoginSteps {

    private WebDriver driver;
    private LandingPage landingPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private ExtentTest extentTest;

    @Given("I am on the Landing page")
    public void i_am_on_the_landing_page() {
        StepTracker.setLastStepText("Given I am on the Landing page");
        try {
            driver = cucumberHooks.getDriver();
            extentTest = cucumberHooks.getExtentTest();
            String url = cucumberHooks.config.getProperty("url");
            driver.get(url);
            landingPage = new LandingPage(driver);
            ExtentStepLogger.logStep(extentTest, driver, "Navigated to Landing page");
            Assert.assertTrue(landingPage.isOnLandingPage(), "Not on the landing page or GUVI logo not visible");
        } catch (Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }

    @And("I navigate to the Login page")
    public void i_navigate_to_the_login_page() {
        StepTracker.setLastStepText("And I navigate to the Login page");
        try {
            landingPage.clickLogin();
            loginPage = new LoginPage(driver, extentTest);
            ExtentStepLogger.logStep(extentTest, driver, "Navigated to Login page");
        } catch (Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }

    @When("I login with valid credentials from json file")
    public void i_login_with_valid_credentials_from_json_file() {
        StepTracker.setLastStepText("When I login with valid credentials from json file");
        try {
            JsonObject loginDetails = hooks.cucumberHooks.validLoginDetailsJson;
            String email = loginDetails.get("email").getAsString();
            String password = loginDetails.get("password").getAsString();
            loginPage.login(email, password);
        } catch (Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }

    @When("I login with email {string} and password {string}")
    public void i_login_with_email_and_password(String email, String password) {
        StepTracker.setLastStepText("When I login with email \"" + email + "\" and password \"" + password + "\"");
        try {
            loginPage.login(email, password);
        } catch (Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }

    @When("I login with blank email and password")
    public void i_login_with_blank_email_and_password() {
        StepTracker.setLastStepText("When I login with blank email and password");
        try {
            loginPage.login("", "");
        } catch (Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        StepTracker.setLastStepText("Then I should be logged in successfully");
        try {
            homePage = new HomePage(driver);
            boolean isLoggedIn = homePage.isUserLoggedIn();
            ExtentStepLogger.logStep(extentTest, driver, "Assertion: User is logged in successfully");
            Assert.assertTrue(isLoggedIn, "User is not logged in or not on homepage");
        } catch (AssertionError | Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }

    /*
    @Then("I should see an error message for invalid credentials")
    public void i_should_see_an_error_message_for_invalid_credentials() {
        StepTracker.setLastStepText("Then I should see an error message for invalid credentials");
        try {
            boolean emailError = loginPage.waitForEmailErrorDisplayed();
            boolean passwordError = loginPage.waitForPasswordErrorDisplayed();
            boolean noProfileError = loginPage.waitForNoProfileExistsErrorDisplayed();
            ExtentStepLogger.logStep(extentTest, driver, "Assertion: Error message displayed for invalid credentials");
            Assert.assertTrue(emailError || passwordError || noProfileError, "No error message displayed for invalid credentials");
        } catch (AssertionError | Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }
    */
    @Then("I should see an error message for invalid credentials")
    public void i_should_see_an_error_message_for_invalid_credentials() {
        StepTracker.setLastStepText("Then I should see an error message for invalid credentials");
        try {
            boolean errorDisplayed = loginPage.isAnyLoginErrorDisplayed();
            ExtentStepLogger.logStep(extentTest, driver, "Assertion: Error message displayed for invalid credentials");
            Assert.assertTrue(errorDisplayed, "No error message displayed for invalid credentials");
        } catch (AssertionError | Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }

    @Then("I should see validation errors for required fields")
    public void i_should_see_validation_errors_for_required_fields() {
        StepTracker.setLastStepText("Then I should see validation errors for required fields");
        try {
            boolean passwordError = loginPage.waitForPasswordErrorDisplayed();
            ExtentStepLogger.logStep(extentTest, driver, "Assertion: Validation errors for required fields");
            Assert.assertTrue(passwordError, "Password validation error not displayed");
        } catch (AssertionError | Exception e) {
            StepErrorTracker.setLastError(e.getMessage());
            throw e;
        }
    }
}