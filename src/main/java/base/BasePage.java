package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class BasePage {
    public static String getScreenshot(String imageName, WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot) driver;
        File f = ts.getScreenshotAs(OutputType.FILE);
        String filepath = "./Screenshot/"+ imageName;
        try {
            FileUtils.copyFile(f, new File(filepath) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filepath;
    }

    public  static String convertImg_Base64(String screenshotPath){
        byte[] file = null;
        try {
            file = FileUtils.readFileToByteArray(new File(screenshotPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String base64Img = Base64.getEncoder().encodeToString(file);
        return base64Img;
    }
}
