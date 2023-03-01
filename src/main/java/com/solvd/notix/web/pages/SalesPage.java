package com.solvd.notix.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SalesPage extends AbstractPage {

    @FindBy(className = "old-price")
    private List<ExtendedWebElement> listOldPrices;

    @FindBy(xpath = "//small/parent::span")
    List<ExtendedWebElement> listDiscountPrices;


    public List<Float> getDiscountPrices() {
        List<Float> discountPricesList = new ArrayList<>();
        for (ExtendedWebElement discountPrice :
                listDiscountPrices) {
            String stringToFormat = discountPrice.getText().trim().replace(",", ".");
            int indexOfRubWord = stringToFormat.indexOf(" р");
            float price = Float.parseFloat(stringToFormat.substring(0, indexOfRubWord).replace(" ", ""));
            discountPricesList.add(price);
        }
        return discountPricesList;
    }

    public List<Float> getOldPrices() {
        List<Float> oldPricesList = new ArrayList<>();
        for (ExtendedWebElement oldPrice :
                listOldPrices) {
            oldPricesList.add(Float.valueOf(oldPrice.getText().trim().replace(" ", "").replace(",", ".")));
        }
        return oldPricesList;
    }

    public SalesPage(WebDriver driver) {
        super(driver);
        setPageURL("/sales");
    }
}
