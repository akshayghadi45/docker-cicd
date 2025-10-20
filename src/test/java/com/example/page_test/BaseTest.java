package com.example.page_test;

import base.AppConstants;
import base.BasePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import static utils.ExtentReportHandler.getExtentReportObject;
import static utils.ExtentReportHandler.reports;


public class BaseTest {
    protected WebDriver driver;
    protected String browser;
    private final ChromeOptions co = new ChromeOptions();
    private final FirefoxOptions fo =new FirefoxOptions();

    private static final Logger logger = LogManager.getLogger(BaseTest.class);


    //reports
    protected static ThreadLocal<ExtentTest> testLogger = new ThreadLocal<>();
    private static final ExtentReports extentReports = getExtentReportObject();

    @BeforeMethod
    @Parameters("browserName")
    public void setup(@Optional String browserName, ITestResult iTestResult){
        //browser = AppConstants.browserName;
        if(browserName != null){
            browser = browserName.toLowerCase();
        }else{
            browser =AppConstants.browserName;
        }
        logger.info("browserName:{}",browser);

        if(browser.equalsIgnoreCase("chrome")){
            if(System.getProperty("platform", "local").equalsIgnoreCase("local")){
                //co.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver= new ChromeDriver();

            }
            else if(System.getProperty("platform", "local").equalsIgnoreCase("remote")){
                co.setPlatformName("linux");
                co.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
                    //remote webdriver url for selenium standalone
                    //driver = new RemoteWebDriver(new URL("http://localhost:4441"), co);
                    //remote webdriver url for selenium grd
                    driver = new RemoteWebDriver(new URL("http://localhost:4444"), co);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(AppConstants.platform.equalsIgnoreCase("remote_git")){
//                co.addArguments("--headless");                          //for githib actions
//                co.addArguments("--disable-gpu");
//                co.addArguments("--no-sandbox");
//                co.addArguments("--remote-allow-origins=*");
//                // Use a unique temp folder for each session to avoid conflicts
//                co.addArguments("--user-data-dir=/tmp/chrome-" + UUID.randomUUID());
//                WebDriverManager.chromedriver().setup();
//                driver = new ChromeDriver(co);

                co.addArguments("--headless=new");          // headless Chrome for CI
                co.addArguments("--disable-gpu");
                co.addArguments("--no-sandbox");
                co.addArguments("--disable-dev-shm-usage"); // important in Docker
                co.addArguments("--remote-allow-origins=*");
                // unique profile to avoid conflicts
                co.addArguments("--user-data-dir=/tmp/chrome-" + UUID.randomUUID());

                // Connect to Selenium Grid running in GH Actions service
                try {
                    driver = new RemoteWebDriver(
                            new URL(System.getProperty("grid.url", "http://localhost:4444/wd/hub")),
                            co
                    );
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

            }
            else {
                logger.info("Platform not supported");
            }
        }
        else if(browser.equalsIgnoreCase("firefox")){
            if(System.getProperty("platform", "local").equalsIgnoreCase("local")){
                //fo.addArguments("--remote-allow-origins=*");
                WebDriverManager.firefoxdriver().setup();
                driver= new FirefoxDriver();
            }
            else if(System.getProperty("platform", "local").equalsIgnoreCase("remote")){
                fo.setPlatformName("linux");
                fo.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
                    //remote webdriver url for selenium standalone
                    //driver = new RemoteWebDriver(new URL("http://localhost:4442"), fo);
                    //remote webdriver url for selenium grid
                    driver = new RemoteWebDriver(new URL("http://localhost:4444"), fo);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                logger.info("Platform not supported");
            }
        }
        else{
            logger.info("Browser name not found" );
        }
        ExtentTest test = reports.createTest(iTestResult.getMethod().getMethodName());
        testLogger.set(test);
        testLogger.get().log(Status.INFO, "Driver start time: "+ LocalDateTime.now().toString());
    }
    @AfterMethod
    public void tearDown(ITestResult iTestResult){
        if(iTestResult.isSuccess()){
            testLogger.get().log(Status.PASS, MarkupHelper.createLabel(iTestResult.getMethod().getMethodName(), ExtentColor.GREEN));
        }
        else{
            testLogger.get().log(Status.FAIL, "Test Failed  due to: " + iTestResult.getThrowable().getMessage());
            String screenshot = BasePage.getScreenshot(iTestResult.getMethod().getMethodName()+"jpg", driver);
            testLogger.get().fail(MediaEntityBuilder.createScreenCaptureFromBase64String(BasePage.convertImg_Base64(screenshot)).build());
        }
        testLogger.get().log(Status.INFO, "Driver end time: " + LocalDateTime.now());
        driver.quit();
    }

    @AfterClass
    public void flushTestReports(){
        reports.flush();
    }
}
