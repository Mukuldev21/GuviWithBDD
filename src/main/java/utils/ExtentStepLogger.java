package utils;

  import com.aventstack.extentreports.ExtentTest;
  import com.aventstack.extentreports.MediaEntityBuilder;
  import org.openqa.selenium.OutputType;
  import org.openqa.selenium.TakesScreenshot;
  import org.openqa.selenium.WebDriver;
  import org.openqa.selenium.WebElement;

  public class ExtentStepLogger {

      // Generic step log with screenshot
      public static void logStep(ExtentTest test, WebDriver driver, String message) {
          try {
              String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
              test.info(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
          } catch (Exception e) {
              test.info(message + " (screenshot unavailable)");
          }
      }

      // Log click action
      public static void logClick(ExtentTest test, String elementName) {
          test.info("Clicked on: <b>" + elementName + "</b>");
      }

      // Log typing action
      public static void logType(ExtentTest test, String elementName, String value) {
          test.info("Typed <b>" + value + "</b> into: <b>" + elementName + "</b>");
      }

      // Log select action
      public static void logSelect(ExtentTest test, String elementName, String selectedOption) {
          test.info("Selected <b>" + selectedOption + "</b> from: <b>" + elementName + "</b>");
      }

      // Log assertion with expected and actual values
      public static void logAssertion(ExtentTest test, String message, Object expected, Object actual) {
          test.info("Assertion: " + message + "<br>Expected: <b>" + expected + "</b><br>Actual: <b>" + actual + "</b>");
      }

      // Log navigation
      public static void logNavigation(ExtentTest test, String url) {
          test.info("Navigated to URL: <b>" + url + "</b>");
      }

      // Log wait action
      public static void logWait(ExtentTest test, String waitDescription) {
          test.info("Waited for: <b>" + waitDescription + "</b>");
      }

      // Log element visibility
      public static void logElementVisibility(ExtentTest test, String elementName, boolean isVisible) {
          test.info("Element <b>" + elementName + "</b> visibility: <b>" + isVisible + "</b>");
      }

      // Log element enabled/disabled state
      public static void logElementState(ExtentTest test, String elementName, boolean isEnabled) {
          test.info("Element <b>" + elementName + "</b> enabled: <b>" + isEnabled + "</b>");
      }
  }