package com.solvd.notix.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.notix.web.dto.Customer;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class OrderFormModel extends AbstractUIObject {

    @FindBy(id = "clientName")
    private ExtendedWebElement nameInput;
    @FindBy(id = "address")
    private ExtendedWebElement addressInput;
    @FindBy(id = "subscribe")
    private ExtendedWebElement phoneInput;

    @FindBy(id = "deliveryType")
    private ExtendedWebElement deliveryTypeSelect;

    @FindBy(id = "comments")
    private ExtendedWebElement commentsTextArea;
    @FindBy(id = "finalCheckout")
    private ExtendedWebElement orderButton;
    @FindBy(id = "finalPhoneNumber")
    private ExtendedWebElement finalPhoneNumber;

    public boolean isCheckoutBodyPresent() {
        return finalPhoneNumber.isVisible();
    }

    public void clickOrderButton() {
        orderButton.click();
    }

    public void fillCustomerInformationInputsByCustomerModel(Customer customer) {
        fillNameInput(customer.getName());
        fillAddressInput(customer.getAddress());
        fillPhoneNumberInput(customer.getPhoneNumber());
    }

    public void fillPhoneNumberInput(String value) {
        phoneInput.click();
        phoneInput.getElement().sendKeys(value);
    }

    public void fillAddressInput(String value) {
        addressInput.type(value);
    }

    public void fillNameInput(String value) {
        nameInput.type(value);
    }

    public OrderFormModel(WebDriver driver) {
        super(driver);
    }

    public OrderFormModel(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
