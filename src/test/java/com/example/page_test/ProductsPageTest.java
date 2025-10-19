package com.example.page_test;

import org.testng.annotations.Test;
import page_objects.LoginPageObject;
import page_objects.ProductPageObject;

public class ProductsPageTest extends BaseTest {
    ProductPageObject productPageObject;
    LoginPageObject loginPageObject;
    @Test
    public void getItemName(){

        loginPageObject = new LoginPageObject(driver);
        productPageObject = loginPageObject.userLogin("standard_user", "secret_sauce");
        System.out.println(productPageObject.geItemName());
    }
}
