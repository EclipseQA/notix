package com.solvd.notix.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.listener.EventFiringSeleniumCommandExecutor;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.notix.web.components.NavbarMenu;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

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
    @FindBy(xpath = "//a[contains(text(), '%s')]/ancestor::div[@class='panel-heading']/following-sibling::div//div[contains(@class,'panel-body')]//div[@class='mCSB_dragger_bar']")
    private ExtendedWebElement scrollBar;
    @FindBy(xpath = "//div[contains(@class,'navbar-tshop')]")
    private NavbarMenu navbarMenu;
    private FilterPanel panel;
    public void openPanelFilterOption(FilterPanel panel) {
        panelFilterOption.format(panel.getXpathId()).scrollTo();
        panelFilterOption.format(panel.getXpathId()).click();
        this.panel = panel;
    }

    public int getNumberOfProductsOnPage() {
        return listImageProducts.size();
    }

    public void checkFilterOption(FilterOption option, String value) {
        int retryCount = Integer.parseInt(R.TESTDATA.get("retry_scroll_count"));
        while (retryCount != 0) {
            try {
                filterOption.format(option.getXpathId(), value).check();
                if (filterOption.format(option.getXpathId(), value).isChecked()) {
                    return;
                }
            } catch (ElementNotInteractableException e) {
                retryCount--;
                scrollTheBar();
            }
        }
    }

    public void scrollTheBar() {
        Actions actions = new Actions(driver);
        actions.clickAndHold(scrollBar.format(panel.getXpathId()).getElement()).moveByOffset(0, 10).perform();
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
        return navbarMenu;
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
