package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.JavaScriptUtil;

public class LoginPage extends BasePage {

    // Locators
    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-btn")
    private WebElement loginButton;

    @FindBy(css = "#emailgroup .invalid-feedback")
    private WebElement emailErrorMessage;

    @FindBy(css = "#passwordGroup .invalid-feedback")
    private WebElement passwordErrorMessage;

    @FindBy(id = "logged-in")
    private WebElement keepMeLoggedInCheckbox;

    @FindBy(css = ".forgot-password-link")
    private WebElement forgotPasswordLink;

    // Locator for specific invalid email format error
    @FindBy(css = "#emailgroup .invalid-feedback.is-invalid")
    private WebElement invalidEmailFormatError;

    // Locator for "No profile exists" error message
    @FindBy(css = "#emailgroup .invalid-feedback:not(.is-invalid)")
    private WebElement noProfileExistsError;


    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Methods to interact with the page
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }
    public void clickLogin() {
        try {
            waitForLoaderToDisappear();
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            JavaScriptUtil.scrollIntoView(driver, loginButton);
            loginButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Error clicking login button: " + e.getMessage());
        }
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public void setKeepMeLoggedIn(boolean value) {
        if (keepMeLoggedInCheckbox.isSelected() != value) {
            keepMeLoggedInCheckbox.click();
        }
    }

    public void clickForgotPassword() {
        forgotPasswordLink.click();
    }

    // Wait for email error message to be visible
    public boolean waitForEmailErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailErrorMessage));
            return emailErrorMessage.isDisplayed() && !emailErrorMessage.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Wait for password error message to be visible
    public boolean waitForPasswordErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordErrorMessage));
            return passwordErrorMessage.isDisplayed() && !passwordErrorMessage.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailErrorMessage() {
        return emailErrorMessage.getText().trim();
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage.getText().trim();
    }

    // Wait for the invalid email format error to be visible
    public boolean waitForInvalidEmailFormatErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(invalidEmailFormatError));
            return invalidEmailFormatError.isDisplayed() && !invalidEmailFormatError.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Get the invalid email format error message text
    public String getInvalidEmailFormatErrorMessage() {
        return invalidEmailFormatError.getText().trim();
    }
    // Wait for the "No profile exists" error to be visible
    public boolean waitForNoProfileExistsErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(noProfileExistsError));
            return noProfileExistsError.isDisplayed() && !noProfileExistsError.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Get the "No profile exists" error message text
    public String getNoProfileExistsErrorMessage() {
        return noProfileExistsError.getText().trim();
    }
}