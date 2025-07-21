package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SignUpPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "mobileNumber")
    private WebElement phoneInput;

    @FindBy(xpath = "//button[contains(text(),'Sign Up')]")
    private WebElement signUpButton;

    // Locator for the profile dropdown
    @FindBy(id = "profileDrpDwn")
    private WebElement profileDropdown;

    // Locator for the degree dropdown
    @FindBy(id = "degreeDrpDwn")
    private WebElement degreeDropdown;

    @FindBy(id = "year")
    private WebElement yearInput;

    @FindBy(css = "#name ~ .invalid-feedback")
    private WebElement nameInvalidFeedback;

    @FindBy(css = "#email ~ .invalid-feedback")
    private WebElement emailInvalidFeedback;

    @FindBy(css = "#password ~ .invalid-feedback")
    private WebElement passwordInvalidFeedback;

    @FindBy(css = "#mobileNumber ~ .invalid-feedback")
    private WebElement phoneInvalidFeedback;

    @FindBy(css = "#yearGroup .invalid-feedback")
    private WebElement yearInvalidFeedback;

    // Locator for the verification message after successful sign up
    @FindBy(xpath = "//p[contains(text(),'A verification link has been sent to the given email ID')]")
    private WebElement verificationMessage;

    // Constructor
    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void enterName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void enterPhone(String phone) {
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void clickSignUp() {
        signUpButton.click();
    }

    public void signUp(String name, String email, String password, String phone) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        enterPhone(phone);
        clickSignUp();
    }

    // Method to select profile by visible text
    public void selectProfile(String profile) {
        Select select = new Select(profileDropdown);
        select.selectByVisibleText(profile);
    }

    // Method to select degree by visible text
    public void selectDegree(String degree) {
        Select select = new Select(degreeDropdown);
        select.selectByVisibleText(degree);
    }
    // Method to enter the year in a year input field
    public void enterYear(String year) {
        yearInput.clear();
        yearInput.sendKeys(year);
    }

    // Method to validate name input
    public boolean isNameValid() {
        return !nameInput.getAttribute("value").trim().isEmpty();
    }

    // Method to validate email input
    public boolean isEmailValid() {
        String email = emailInput.getAttribute("value").trim();
        return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    // Method to validate password input
    public boolean isPasswordValid() {
        String password = passwordInput.getAttribute("value").trim();
        return password.length() >= 8; // Example: Minimum 8 characters
    }

    // Method to validate phone input
    public boolean isPhoneValid() {
        String phone = phoneInput.getAttribute("value").trim();
        return phone.matches("^\\d{10}$"); // Example: 10-digit phone number
    }

    // Method to validate year input
    public boolean isYearValid() {
        String year = yearInput.getAttribute("value").trim();
        return year.matches("^\\d{4}$"); // Example: 4-digit year
    }

    // Waits for the name error message to be visible
    public boolean waitForNameErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(nameInvalidFeedback));
            return nameInvalidFeedback.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Waits for the email error message to be visible
    public boolean waitForEmailErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInvalidFeedback));
            return emailInvalidFeedback.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Waits for the password error message to be visible
    public boolean waitForPasswordErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordInvalidFeedback));
            return passwordInvalidFeedback.isDisplayed() && !passwordInvalidFeedback.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Waits for the phone error message to be visible
    public boolean waitForPhoneErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(phoneInvalidFeedback));
            return phoneInvalidFeedback.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Waits for the year error message to be visible
    public boolean waitForYearErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(yearInvalidFeedback));
            return yearInvalidFeedback.isDisplayed() && !yearInvalidFeedback.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to wait for the verification message to be visible
    public boolean waitForVerificationMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(verificationMessage));
            return verificationMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}