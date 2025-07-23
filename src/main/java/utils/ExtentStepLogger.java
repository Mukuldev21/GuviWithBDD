package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ExtentStepLogger {
    public static void logStep(ExtentTest test, WebDriver driver, String message) {
        try {
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.info(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e) {
            test.info(message + " (screenshot unavailable)");
        }
    }
}