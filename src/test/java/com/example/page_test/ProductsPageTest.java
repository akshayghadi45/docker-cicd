package com.example.page_test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_objects.LoginPageObject;
import page_objects.ProductPageObject;

public class ProductsPageTest extends BaseTest {
    ProductPageObject productPageObject;
    LoginPageObject loginPageObject;
    private static final Logger logger = LogManager.getLogger(ProductsPageTest.class);
    @Test
    public void getItemName(){
        String username = "performance glitch user";
        String password = "secret_sauce";
        loginPageObject = new LoginPageObject(driver);
        productPageObject = loginPageObject.userLogin("standard_user", "secret_sauce");
        logger.info("username is: "+username +" and password is: " + password);
        System.out.println(productPageObject.geItemName());
    }
}
