package generic_Keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class WebElementsInteractions {
    protected WebDriver driver;

    public WebElementsInteractions(WebDriver driver) {
        this.driver = driver;
    }

    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    public void sendText(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    public void goToApplication(String URL) {
        driver.get(URL);
    }

    public  String retrieveTextData (By locator) {
        return driver.findElement(locator).getText();
    }
}
