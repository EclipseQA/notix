package com.solvd.notix.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.notix.web.components.NavbarMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class NotebookDescriptionPage extends AbstractPage {

    @FindBy(xpath = "//div[@id='details']//td[contains(text(),'%s')]/following-sibling::td")
    private ExtendedWebElement productInformationField;

    @FindBy(xpath = "//div[@class='addto']//button")
    private ExtendedWebElement buyButton;

    @FindBy(xpath = "//span[@itemprop='brand']")
    private ExtendedWebElement brandNameField;

    public NavbarMenu switchToNavbarMenu() {
        return new NavbarMenu(driver);
    }

    public void clickBuyButton() {
        buyButton.click();
    }

    public String getBrandName() {
        return brandNameField.getText();
    }

    public String getDescriptionItem(DescriptionCommonField field) {
        return productInformationField.format(field.getXpathId()).getText();
    }

    public enum DescriptionCommonField {
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
