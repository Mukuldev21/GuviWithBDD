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
    private static ThreadLocal<ExtentTest> scenarioThread = new ThreadLocal<>();
    public static JsonObject validLoginDetailsJson;
    public static JsonObject courseEnrollmentDetailsJson;

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

        // Create or get the feature-level test (parent)
        ExtentTest featureTest = featureTestMap.computeIfAbsent(featureName, k -> extent.createTest(featureName));

        // Create a scenario node (child) under the feature
        String highlightedName = "<span style='color:#fff; font-weight:bold;'>" + scenario.getName() + "</span>";
        ExtentTest scenarioNode = featureTest.createNode(highlightedName);
        scenarioThread.set(scenarioNode);

        ScreenshotCleaner.deleteOldScreenshots("test-output/SparkReport/screenshots", 5);

        // Log scenario tags as badges
        String tags = scenario.getSourceTagNames().stream()
                .map(tag -> "<span style='background-color:#E0E0E0; color:#000; padding:2px 5px; border-radius:3px; margin-right:5px;'>" + tag + "</span>")
                .reduce("", (a, b) -> a + b);
        scenarioNode.info("<div>Tags: " + tags + "</div>");
    }

   /*
    @AfterStep
    public void addScreenshotToReport(Scenario scenario) {
        // Get the feature file name
        String featureFile = scenario.getUri().toString();
        boolean isLoginFeature = featureFile.endsWith("Login.feature");

        stepCounter.set(stepCounter.get() + 1);
        String currentStepNumber = "Step " + stepCounter.get();

        String stepInfo = StepTracker.getLastStepText();
        if (stepInfo == null || stepInfo.isEmpty()) {
            stepInfo = "Step execution in progress .... ";
        }

        ExtentTest scenarioNode = scenarioThread.get();

        if (isLoginFeature) {
            String base64Screenshot = captureScreenshotAsBase64();
            if (scenario.isFailed()) {
                String error = StepErrorTracker.getLastError();
                scenarioNode.fail(currentStepNumber + ": " + stepInfo,
                        createScreenCaptureFromBase64String(base64Screenshot).build());
                if (error != null) {
                    scenarioNode.fail("<br><pre style='color:#ff5252; background:#212121;'>" + error + "</pre>");
                }
            } else {
                scenarioNode.pass(currentStepNumber + ": " + stepInfo,
                        createScreenCaptureFromBase64String(base64Screenshot).build());
            }
        } else {
            if (scenario.isFailed()) {
                String error = StepErrorTracker.getLastError();
                scenarioNode.fail(currentStepNumber + ": " + stepInfo);
                if (error != null) {
                    scenarioNode.fail("<br><pre style='color:#ff5252; background:#212121;'>" + error + "</pre>");
                }
            } else {
                scenarioNode.pass(currentStepNumber + ": " + stepInfo);
            }
        }
    }

    */

    @AfterStep
    public void addScreenshotToReport(Scenario scenario) {
        String featureUri = scenario.getUri() != null ? scenario.getUri().toString() : "";
        // Adjust the path check as per your project structure if needed
        boolean isFeatureInFeaturesPackage = featureUri.contains("/features/") && featureUri.endsWith(".feature");

        stepCounter.set(stepCounter.get() + 1);
        String currentStepNumber = "Step " + stepCounter.get();

        String stepInfo = StepTracker.getLastStepText();
        if (stepInfo == null || stepInfo.isEmpty()) {
            stepInfo = "Step execution in progress .... ";
        }

        ExtentTest scenarioNode = scenarioThread.get();
        if (scenarioNode == null) return;

        try {
            if (isFeatureInFeaturesPackage && getDriver() != null) {
                String base64Screenshot = captureScreenshotAsBase64();
                if (scenario.isFailed()) {
                    String error = StepErrorTracker.getLastError();
                    scenarioNode.fail(currentStepNumber + ": " + stepInfo,
                            createScreenCaptureFromBase64String(base64Screenshot).build());
                    if (error != null) {
                        scenarioNode.fail("<br><pre style='color:#ff5252; background:#212121;'>" + error + "</pre>");
                    }
                } else {
                    scenarioNode.pass(currentStepNumber + ": " + stepInfo,
                            createScreenCaptureFromBase64String(base64Screenshot).build());
                }
            } else {
                if (scenario.isFailed()) {
                    String error = StepErrorTracker.getLastError();
                    scenarioNode.fail(currentStepNumber + ": " + stepInfo);
                    if (error != null) {
                        scenarioNode.fail("<br><pre style='color:#ff5252; background:#212121;'>" + error + "</pre>");
                    }
                } else {
                    scenarioNode.pass(currentStepNumber + ": " + stepInfo);
                }
            }
        } catch (Exception e) {
            scenarioNode.warning("Could not attach screenshot or log step: " + e.getMessage());
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        ExtentTest scenarioNode = scenarioThread.get();
        if (scenario.isFailed()) {
            scenarioNode.fail("<span style='color:#F44336;'>❌ Scenario failed: " + scenario.getName() + "</span>");
        } else {
            scenarioNode.pass("<span style='color:#4CAF50;'>✅ Scenario passed: " + scenario.getName() + "</span>");
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
        return scenarioThread.get();
    }
}