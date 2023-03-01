package com.solvd.notix.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.notix.web.components.FooterMenu;
import com.solvd.notix.web.components.NavbarMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class MainPage extends AbstractPage {

    @FindBy(xpath = "//div[@class='menu-square']//div[text()='%s']")
    private ExtendedWebElement menuSquare;

    public NotebookShoppingPage goToGamingNotebookShoppingPage() {
        menuSquare.format(MenuSquareOption.GAMING.getXpathId()).click();
        return new NotebookShoppingPage(driver);
    }

    public FooterMenu switchToFooterMenu() {
        return new FooterMenu(driver);
    }

    public NavbarMenu switchToNavBarMenu() {
        return new NavbarMenu(driver);
    }

    public enum MenuSquareOption {
        NOTEBOOK("Ноутбуки"),
        MONOBLOCK("Моноблоки"),
        GAMING("Gaming"),
        TELEVISION("Телевизоры");

        private String xpathId;

        MenuSquareOption(String xpathId) {
            this.xpathId = xpathId;
        }

        public String getXpathId() {
            return xpathId;
        }
    }

    public MainPage(WebDriver driver) {
        super(driver);
    }
}
