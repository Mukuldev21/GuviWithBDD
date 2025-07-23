package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ExtentStepLogger;
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

    private ExtentTest extentTest;

    // Constructor
    public LoginPage(WebDriver driver, ExtentTest extentTest) {
        super(driver);
        this.extentTest = extentTest;
    }


    /*
    // Methods to interact with the page
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        ExtentStepLogger.logStep(extentTest, driver, "Entered email: " + email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        ExtentStepLogger.logStep(extentTest, driver, "Entered password");
    }

    public void clickLogin() {
        try {
            waitForLoaderToDisappear();
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            JavaScriptUtil.scrollIntoView(driver, loginButton);
            ExtentStepLogger.logStep(extentTest, driver, "Clicking Login button");
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
    */
    // ... (imports and class definition remain unchanged)

    public void enterEmail(String email) {
        emailInput.clear();
        ExtentStepLogger.logType(extentTest, "Email Input", email);
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        ExtentStepLogger.logType(extentTest, "Password Input", "********");
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        try {
            waitForLoaderToDisappear();
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            ExtentStepLogger.logElementVisibility(extentTest, "Login Button", loginButton.isDisplayed());
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            ExtentStepLogger.logElementState(extentTest, "Login Button", loginButton.isEnabled());
            JavaScriptUtil.scrollIntoView(driver, loginButton);
            ExtentStepLogger.logClick(extentTest, "Login Button");
            loginButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Error clicking login button: " + e.getMessage());
        }
    }

    public void setKeepMeLoggedIn(boolean value) {
        ExtentStepLogger.logElementState(extentTest, "Keep Me Logged In Checkbox", keepMeLoggedInCheckbox.isEnabled());
        if (keepMeLoggedInCheckbox.isSelected() != value) {
            ExtentStepLogger.logClick(extentTest, "Keep Me Logged In Checkbox");
            keepMeLoggedInCheckbox.click();
        }
    }

    public void clickForgotPassword() {
        ExtentStepLogger.logClick(extentTest, "Forgot Password Link");
        forgotPasswordLink.click();
    }

    // Wait for email error message to be visible
    public boolean waitForEmailErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailErrorMessage));
            boolean visible = emailErrorMessage.isDisplayed() && !emailErrorMessage.getText().trim().isEmpty();
            ExtentStepLogger.logElementVisibility(extentTest, "Email Error Message", visible);
            return visible;
        } catch (Exception e) {
            return false;
        }
    }

    // Wait for password error message to be visible
    public boolean waitForPasswordErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordErrorMessage));
            boolean visible = passwordErrorMessage.isDisplayed() && !passwordErrorMessage.getText().trim().isEmpty();
            ExtentStepLogger.logElementVisibility(extentTest, "Password Error Message", visible);
            return visible;
        } catch (Exception e) {
            return false;
        }
    }

    // ... (other methods remain unchanged)

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

    public boolean isAnyLoginErrorDisplayed() {
        try {
            wait.until(driver ->
                (emailErrorMessage.isDisplayed() && !emailErrorMessage.getText().trim().isEmpty()) ||
                (passwordErrorMessage.isDisplayed() && !passwordErrorMessage.getText().trim().isEmpty()) ||
                (noProfileExistsError.isDisplayed() && !noProfileExistsError.getText().trim().isEmpty())
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}