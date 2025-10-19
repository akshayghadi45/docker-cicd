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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.LocalDateTime;

import static utils.ExtentReportHandler.getExtentReportObject;
import static utils.ExtentReportHandler.reports;

public class BaseTest {
    protected WebDriver driver;
    protected String browser;
    //private ChromeOptions co;
    //private FirefoxOptions fo;

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
