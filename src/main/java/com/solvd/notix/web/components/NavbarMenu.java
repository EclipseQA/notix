package com.solvd.notix.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.notix.web.pages.NotebookDescriptionPage;
import com.solvd.notix.web.pages.NotebookShoppingPage;
import com.solvd.notix.web.pages.SalesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class NavbarMenu extends AbstractUIObject {

    @FindBy(linkText = "Акции")
    private ExtendedWebElement salesSection;

    @FindBy(xpath = "//a[text()=' Каталог ']")
    private ExtendedWebElement catalogDropDown;

    @FindBy(xpath = "//li[@class='megamenu-content']//a[text()=' %s ']")
    private ExtendedWebElement catalogDropdownOption;

    @FindBy(id = "cart-link")
    private ExtendedWebElement cartSection;

    @FindBy(id = "searchQ")
    private ExtendedWebElement searchInput;

    @FindBy(xpath = "//ul[@id='ui-id-1']//li")
    private List<ExtendedWebElement> searchSuggestions;

    @FindBy(xpath = "//a[@id='cart-link']/following-sibling::div//a[@class='linkP']")
    private List<ExtendedWebElement> productsInCart;

    private String typedProduct;

    public NotebookDescriptionPage goToLastAddedProductDescriptionPageInCart() {
        cartSection.hover();
        int lastProductId = productsInCart.size() - 1;
        productsInCart.get(lastProductId).click();
        return new NotebookDescriptionPage(driver);
    }

    public boolean isTypedProductSuggestionPresent() {
        waitUntil(ExpectedConditions.attributeToBe(driver.findElement(By.id("searchQ")),
                "class", "search-input ui-autocomplete-input"), EXPLICIT_TIMEOUT);
        for (ExtendedWebElement element :
                searchSuggestions) {
            String s = element.getAttribute("data-value").toLowerCase().trim();
            if (s.contains(typedProduct.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void typeTextInSearchInput(String value) {
        typedProduct = value;
        searchInput.type(value);
    }

    public String getNumberOfProductsInCart() {
        String stringToFormat = cartSection.getText().trim();
        int indexOfTotalPrice = stringToFormat.indexOf(" (");
        stringToFormat = stringToFormat.substring(0, indexOfTotalPrice);
        return stringToFormat;
    }

    public SalesPage goToSalesPage() {
        salesSection.click();
        return new SalesPage(driver);
    }

    public void hoverOnCatalogSection() {
        catalogDropDown.hover();
    }

    public NotebookShoppingPage goToNotebookPage() {
        hoverOnCatalogSection();
        catalogDropdownOption.format("Ноутбуки").click();
        return new NotebookShoppingPage(driver);
    }

    public NavbarMenu(WebDriver driver) {
        super(driver);
    }

    public NavbarMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
