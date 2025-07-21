package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }
    // In BasePage.java, add a generic loader wait utility
    protected void waitForLoaderToDisappear() {
        try {
            // Adjust selector to match your loader/overlay
            By loaderBy = By.cssSelector(".loader, .overlay, .spinner");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
        } catch (Exception ignored) {
            // Ignore if loader is not present
        }
    }
}
