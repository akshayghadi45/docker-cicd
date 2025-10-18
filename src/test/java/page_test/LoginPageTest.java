package page_test;

import org.testng.annotations.Test;
import page_objects.LoginPageObject;
import page_objects.ProductPageObject;

public class LoginPageTest extends BaseTest {

    LoginPageObject loginPageObject;
    ProductPageObject productPageObject;

    @Test
    public void userLoginTest(){
        loginPageObject = new LoginPageObject(driver);
        productPageObject = loginPageObject.userLogin("standard_user", "secret_sauce");
        System.out.println(productPageObject.getTitleOfPage());
    }
}
