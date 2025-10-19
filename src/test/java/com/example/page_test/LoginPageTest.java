package com.example.page_test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_objects.LoginPageObject;
import page_objects.ProductPageObject;

public class LoginPageTest extends BaseTest {

    LoginPageObject loginPageObject;
    ProductPageObject productPageObject;
    private static final Logger logger = LogManager.getLogger(LoginPageTest.class);

    @Test
    public void userLoginTest(){
        String username ="standard_user";
        String password = "secret_sauce";
        loginPageObject = new LoginPageObject(driver);
        productPageObject = loginPageObject.userLogin("standard_user", "secret_sauce");
        logger.info("username is: "+username +" and password is: " + password);
        System.out.println(productPageObject.getTitleOfPage());
    }
}
