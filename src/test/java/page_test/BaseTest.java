package page_test;

import base.AppConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    protected String browser;
    //private ChromeOptions co;
    //private FirefoxOptions fo;

    @BeforeTest
    @Parameters("browserName")
    public void setup(@Optional String browserName){
        //browser = AppConstants.browserName;
        if(browserName != null){
            browser = browserName.toLowerCase();
        }else{
            browser =AppConstants.browserName;
        }

        if(browser.equalsIgnoreCase("chrome")){
            if(AppConstants.playform.equalsIgnoreCase("local")){
                //co.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver= new ChromeDriver();

            }
        }
        else if(browser.equalsIgnoreCase("firefox")){
            if(AppConstants.playform.equalsIgnoreCase("local")){
                //fo.addArguments("--remote-allow-origins=*");
                WebDriverManager.firefoxdriver().setup();
                driver= new FirefoxDriver();
            }
        }
        else{
            System.out.println("Browser name entered is not supported!!");
        }
    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
