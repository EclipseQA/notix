package com.solvd.notix.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.notix.web.components.NavbarMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class NotebookDescriptionPage extends AbstractPage {

    @FindBy(xpath = "//tr[@class='product-item']/td[contains(text(),'%s')]/following-sibling::td")
    private ExtendedWebElement detailProductField;

    @FindBy(xpath = "//div[@class='addto']//button")
    private ExtendedWebElement buyButton;


    public NavbarMenu switchToNavbarMenu() {
        return new NavbarMenu(driver);
    }

    public void clickBuyButton() {
        buyButton.click();
    }

    public String getDescriptionItem(DescriptionCommonField field) {
        return detailProductField.format(field.getXpathId()).getText();
    }

    public enum DescriptionCommonField {
        PRODUCT_LINE("Продуктовая линейка"),
        CPU_MODEL("Модель процессора"),
        YEAR("Дата выхода"),
        RESOLUTION("Разрешение экрана"),
        RAM("Объём памяти");

        private String xpathId;

        DescriptionCommonField(String xpathId) {
            this.xpathId = xpathId;
        }

        public String getXpathId() {
            return xpathId;
        }
    }

    public NotebookDescriptionPage(WebDriver driver) {
        super(driver);
    }
}
