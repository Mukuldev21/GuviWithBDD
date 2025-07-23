package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.JsonObject;
import io.cucumber.java.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;

import static com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String;

public class cucumberHooks {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<Integer> stepCounter = ThreadLocal.withInitial(() -> 0);

    public static WebDriver getDriver() { return driver.get(); }

    public static Properties config;
    public static ExtentReports extent = ExtentManager.getInstance();
    private static Map<String, ExtentTest> featureTestMap = new ConcurrentHashMap<>();
    private static ThreadLocal<ExtentTest> featureThread = new ThreadLocal<>();
    public static JsonObject validLoginDetailsJson;
    public static JsonObject courseEnrollmentDetailsJson;

    /*
    @Before
    public void setUp(Scenario scenario)  {
        config = ConfigReader.loadProperties("src/test/resources/config/config.properties");
        stepCounter.set(0);

        try {
            validLoginDetailsJson = ConfigReader.loadJsonConfig("src/test/resources/testData/ValidLoginDetails.json");
            courseEnrollmentDetailsJson = ConfigReader.loadJsonConfig("src/test/resources/testData/CourseEnrollmentDetails.json");
            driver.set(DriverManager.getDriver(config));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize WebDriver or load config",e);
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        ExtentTest test = extent.createTest(scenario.getName());
        scenarioThread.set(test);
        ScreenshotCleaner.deleteOldScreenshots("test-output/SparkReport/screenshots", 5);
    }
    */

    @Before
    public void setUp(Scenario scenario)  {
        config = ConfigReader.loadProperties("src/test/resources/config/config.properties");
        stepCounter.set(0);

        try {
            validLoginDetailsJson = ConfigReader.loadJsonConfig("src/test/resources/testData/ValidLoginDetails.json");
            courseEnrollmentDetailsJson = ConfigReader.loadJsonConfig("src/test/resources/testData/CourseEnrollmentDetails.json");
            driver.set(DriverManager.getDriver(config));
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize WebDriver or load config",e);
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Extract feature name from scenario
        String featureName = scenario.getUri().toString()
            .substring(scenario.getUri().toString().lastIndexOf('/') + 1)
            .replace(".feature", "");

        // Create or get the feature-level test
        ExtentTest featureTest = featureTestMap.computeIfAbsent(featureName, k -> extent.createTest(featureName));
        featureThread.set(featureTest);

        ScreenshotCleaner.deleteOldScreenshots("test-output/SparkReport/screenshots", 5);

        // Log scenario start as a step/node
        featureTest.info("Scenario: " + scenario.getName());
    }

    @AfterStep
    public void addScreenshotToReport(Scenario scenario) {
        try {
            if (getDriver() == null) {
                DriverManager.getDriver(config);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new WebDriverWait(getDriver(), Duration.ofSeconds(8)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );

        stepCounter.set(stepCounter.get() + 1);
        String currentStepNumber = "Step " + stepCounter.get();

        String stepInfo = StepTracker.getLastStepText();
        if (stepInfo == null || stepInfo.isEmpty()) {
            stepInfo = "Step execution in progress .... ";
        }

        String base64Screenshot = captureScreenshotAsBase64();
        ExtentTest featureTest = featureThread.get();

        if (scenario.isFailed()) {
            String error = StepErrorTracker.getLastError();
            featureTest.fail(currentStepNumber + ": " + stepInfo,
                    createScreenCaptureFromBase64String(base64Screenshot).build());
            if (error != null) {
                featureTest.fail("<br><pre style='color:#ff5252; background:#212121;'>" + error + "</pre>");
            }
        } else {
            featureTest.pass(currentStepNumber + ": " + stepInfo,
                    createScreenCaptureFromBase64String(base64Screenshot).build());
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        ExtentTest featureTest = featureThread.get();
        if (scenario.isFailed()) {
            featureTest.fail("Scenario failed: " + scenario.getName());
        } else {
            featureTest.pass("Scenario passed: " + scenario.getName());
        }
        WebDriver webDriver = getDriver();
        if (webDriver != null) {
            DriverManager.quitDriver(webDriver);
            driver.remove();
        }
        extent.flush();
    }

    private String captureScreenshotAsBase64() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            return null;
        }
    }

    public static ExtentTest getExtentTest() {
        return featureThread.get();
    }
}