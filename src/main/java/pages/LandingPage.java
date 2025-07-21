package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JavaScriptUtil;

import java.time.Duration;

public class LandingPage extends BasePage {

    // Locators
    @FindBy(xpath = "(//a[contains(@href,'/sign-in/')])[2]")
    private WebElement loginButton;

    @FindBy(xpath = "(//a[contains(@href,'/register/')])[2]")
    private WebElement signUpButton;

    @FindBy(xpath = "(//a[contains(@href,'/courses/')])[1]")
    private WebElement coursesLink;

    // Locator for GUVI logo on landing page
    @FindBy(css = "div.flex.mr-auto.lg\\:ml-8 .guvi_logo")
    private WebElement guviLogo;

    // Constructor
    public LandingPage(WebDriver driver) {
        super(driver);
    }

    // Methods
    // method to wait for an element to be clickable
    private void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    private void waitAndScrollToElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        JavaScriptUtil.scrollIntoView(driver, element);
    }

    public void clickLogin() {
        waitForLoaderToDisappear();
        waitAndScrollToElement(loginButton);
        loginButton.click();
    }

    public void clickSignUp() {
        waitForLoaderToDisappear();
        waitAndScrollToElement(signUpButton);
        signUpButton.click();
    }

    public void clickCourses() {
        waitForLoaderToDisappear();
        waitAndScrollToElement(coursesLink);
        coursesLink.click();
    }

    // Method to validate landing page by URL and logo visibility
    public boolean isOnLandingPage() {
        try {
            String expectedUrl = driver.getCurrentUrl();
            String landingUrl = pages.LandingPage.class
                    .getClassLoader()
                    .getResource("config/config.properties")
                    .toString(); // Or fetch from config if available
            wait.until(ExpectedConditions.visibilityOf(guviLogo));
            return expectedUrl.contains("guvi.in") && guviLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}