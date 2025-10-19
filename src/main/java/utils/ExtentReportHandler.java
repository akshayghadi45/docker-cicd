package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportHandler {

    public static ExtentReports reports;
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ExtentReports getExtentReportObject(){
        String reportPath = "./reports/"+dtf.format(LocalDateTime.now());
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, YYYY, hh:mm a '('zzz')'");
        sparkReporter.config().setTheme (Theme.DARK);
        sparkReporter.config().setReportName("Automation Results");
        sparkReporter.config().setDocumentTitle("Test Results");
        sparkReporter.config().setJs("document.getElementsByClassName('col-sm-12 col-md-4') [0] style setProperty('min-inline-size', '-webkit-fill-available');");
        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Tester is: ", "Akshay Ghadi");
        return  reports;
    }
}
