package page_objects;

import generic_Keywords.WebElementsInteractions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPageObject extends WebElementsInteractions {

    private final By getTitleOfProductPage = By.xpath("// span[contains(text() , 'Products')]");
    private final By getTextOfFirstItem = By.xpath("//a[@id='item_111_title_link']/div");

    public ProductPageObject(WebDriver driver) {
        super(driver);
    }

    public String getTitleOfPage() {
        return retrieveTextData(getTitleOfProductPage);
    }

    public String geItemName(){
        return retrieveTextData(getTextOfFirstItem);
    }
}
