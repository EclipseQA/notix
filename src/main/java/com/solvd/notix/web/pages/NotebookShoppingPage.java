package com.solvd.notix.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.notix.web.components.NavbarMenu;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NotebookShoppingPage extends AbstractPage {

    @FindBy(xpath = "(//span[@class='add2cart'])[%d]")
    private ExtendedWebElement buyButton;

    @FindBy(xpath = "//span[@id='productsList']//div[contains(@class,'product')]//div[@class='image']")
    private List<ExtendedWebElement> listImageProducts;

    @FindBy(xpath = "//input[@name='%s' and @value='%s']")
    private ExtendedWebElement filterOption;

    @FindBy(xpath = "//a[contains(text(), '%s')]")
    private ExtendedWebElement panelFilterOption;

    @FindBy(xpath = "((//a[contains(text(), 'Процессор')]/ancestor::div[contains(@class,'panel')])[1]//div[contains(@class,'scroll')])[1]")
    private ExtendedWebElement test;

    private Point startPoint;

    public void openPanelFilterOption(FilterPanel panel) {
        panelFilterOption.format(panel.getXpathId()).click();
    }

    public void checkFilterOption(FilterOption option, String value) {
        try {
            filterOption.format(option.getXpathId(), value).click();
        } catch (ElementNotInteractableException e) {
            startPoint = filterOption.format(option.getXpathId(), value).getLocation();
            scrollWheelDown();
        }
    }

    public void scrollWheelDown() {
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport(startPoint.getX(), startPoint.getY());
        new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 10);
    }

    public void clickOnBuyButton(int timesToClick) {
        if (timesToClick > listImageProducts.size() || timesToClick < 0) {
            throw new RuntimeException();
        }
        for (int i = 1; i <= timesToClick; i++) {
            buyButton.format(i).click();
        }
    }

    public NotebookDescriptionPage clickOnProductById(int id) {
        if (id > listImageProducts.size() || id <= 0) {
            throw new RuntimeException();
        }
        listImageProducts.get(id).click();
        return new NotebookDescriptionPage(driver);
    }

    public NavbarMenu switchToNavbarMenu() {
        return new NavbarMenu(driver);
    }

    public enum FilterPanel {
        RESOLUTION("Разрешение экрана"),
        CPU("Процессор");

        private String xpathId;

        FilterPanel(String xpathId) {
            this.xpathId = xpathId;
        }

        public String getXpathId() {
            return xpathId;
        }
    }

    public enum FilterOption {
        BRAND("brand"),
        CPU("Protsessor"),
        RESOLUTION("RazresheniyeEkrana");

        private String xpathId;

        FilterOption(String xpathId) {
            this.xpathId = xpathId;
        }

        public String getXpathId() {
            return xpathId;
        }
    }

    public NotebookShoppingPage(WebDriver driver) {
        super(driver);
        setPageURL("/notebook");
    }
}
