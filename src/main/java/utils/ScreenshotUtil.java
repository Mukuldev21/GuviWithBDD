package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver,String scenarioName){
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String folderRelative = "test-output/SparkReport/screenshots/";
        String fileName = scenarioName.replaceAll("[^a-zA-Z0-9]","_") + "_" + System.currentTimeMillis() + ".png";

        Path screenshotDir = Paths.get(System.getProperty("user.dir"),folderRelative);
        try{
            Files.createDirectories(screenshotDir);
        } catch (IOException e){
            e.printStackTrace();
        }
        Path destPath = screenshotDir.resolve(fileName);
        try{
            Files.copy(src.toPath(),destPath);
        } catch (IOException e){
            e.printStackTrace();
        }
        return "screenshots/" + fileName;
    }
}
