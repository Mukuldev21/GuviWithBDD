package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.IOException;
import java.util.Properties;

public class DriverManager {

    public static WebDriver getDriver(Properties config) throws IOException {
        String browser = System.getProperty("browser");
        if (browser == null) {
            browser = config.getProperty("browser");
        }
        if (browser == null) {
            throw new IllegalArgumentException("Browser property not set in config.properties or system property");
        }
        browser = browser.toLowerCase();
        switch (browser) {
            case "firefox":
                return new FirefoxDriver();
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--no-sandbox");
                //chromeOptions.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(chromeOptions);
            case "headless":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(options);
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                return new EdgeDriver(edgeOptions);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}
