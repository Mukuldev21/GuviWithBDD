package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.google.gson.JsonObject;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.DriverManager;
import utils.ScreenshotUtil;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class cucumberHooks {

    //public static WebDriver driver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static Properties config;
    public static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> scenarioThread = new ThreadLocal<>();
    public static JsonObject validLoginDetailsJson;
    public static JsonObject courseEnrollmentDetailsJson;

    @Before
    public void setUp(Scenario scenario)  {
        // Load Configuration Properties
        config = ConfigReader.loadProperties("src/test/resources/config/config.properties");

        // Initialize WebDriver
        try {
            validLoginDetailsJson = ConfigReader.loadJsonConfig("src/test/resources/testData/ValidLoginDetails.json");
            courseEnrollmentDetailsJson = ConfigReader.loadJsonConfig("src/test/resources/testData/CourseEnrollmentDetails.json");
            driver.set(DriverManager.getDriver(config));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize WebDriver or load config",e);
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // Create ExtentTest instance for reporting
        ExtentTest test = extent.createTest(scenario.getName());
        scenarioThread.set(test);
        ScreenshotCleaner.deleteOldScreenshots("test-output/SparkReport/screenshots", 5);
    }


    @AfterStep
    public void addScreenshotToReport(Scenario scenario) {
        try {
            if (getDriver() == null) {
                WebDriver driver= DriverManager.getDriver(config);
                //driver = new ChromeDriver();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), Duration.ofSeconds(15)).until(
                webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );

        String stepInfo = StepTracker.getLastStepText();
        if (stepInfo == null) {
            stepInfo = "Step info not found";
        }

        String screenshotPath = ScreenshotUtil.captureScreenshot(getDriver(),
                scenario.getName().replaceAll(" ", "_") + "_" + System.currentTimeMillis());

        try {
            String imgTag = "<br><img src='" + screenshotPath + "' height='200' width='200'/>";
            String logMessage = "<span style='font-weight:bold; color:#90caf9; background:#263238; padding:2px 6px; border-radius:4px;'>"
                    + stepInfo + "</span>" + imgTag;

            if (scenario.isFailed()) {
                String error = StepErrorTracker.getLastError();
                String errorDetails = error != null
                        ? "<br><pre style='color:#ff5252; background:#212121;'>" + error + "</pre>"
                        : "<br><pre style='color:#ff5252; background:#212121;'>Step failed. See stack trace in console or logs.</pre>";
                logMessage += errorDetails;
                scenarioThread.get().fail(logMessage);
                ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, logMessage);
            } else {
                scenarioThread.get().pass(logMessage);
                ExtentCucumberAdapter.getCurrentStep().log(Status.PASS, logMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StepErrorTracker.clear();
        }
    }

    @After
    public void tearDown(Scenario scenario) {

        DriverManager.quitDriver(getDriver());
        extent.flush();
    }

}
