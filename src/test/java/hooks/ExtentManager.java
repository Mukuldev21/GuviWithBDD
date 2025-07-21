package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;

public class ExtentManager {

    // Singleton instance of ExtentReports
    private static ExtentReports extent;

    // Returns the singleton ExtentReports instance
    public static ExtentReports getInstance(){
        if(extent == null){

            // Define the directory where screenshots are stored
            String screenshotDirectory = System.getProperty("user.dir") + "/screenshots";
            int daysThreshold = 7; // Number of days to keep screenshots

            // Delete screenshots older than the threshold
            ScreenshotCleaner.deleteOldScreenshots(screenshotDirectory, daysThreshold);

            // Define the path for the Spark HTML report
            String reportPath = System.getProperty("user.dir") + "/test-output/SparkReport/Spark.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            try{
                // Load custom Spark report configuration from XML
                spark.loadXMLConfig(new File("src/test/java/hooks/spark-config.xml"));
            } catch (IOException e){
                e.printStackTrace();
            }

            // Set the report name and document title
            spark.config().setReportName("GUVI Test Automation Report");
            spark.config().setDocumentTitle("Extent Spark Dashboard");

            // Create and attach the reporter to the ExtentReports instance
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}