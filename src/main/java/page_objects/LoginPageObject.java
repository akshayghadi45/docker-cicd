package page_objects;

import generic_Keywords.WebElementsInteractions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends WebElementsInteractions {

    private final By usernameTextField = By.id("user-name");
    private final By passwordTextField = By.id("password");
    private final By loginButton = By.id("login-button");

    public LoginPageObject(WebDriver driver) {
        super(driver);
    }

    public ProductPageObject userLogin(String username, String password) {
        goToApplication("https://www.saucedemo.com/");
        sendText(usernameTextField, "standard_user");
        sendText(passwordTextField, "secret_sauce");
        clickElement(loginButton);
        return new ProductPageObject(driver);
    }
}
