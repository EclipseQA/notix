package com.solvd.notix.web.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.solvd.notix.web.pages.NotebookShoppingPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class FooterMenu extends AbstractUIObject {

    @FindBy(linkText = "%s")
    private ExtendedWebElement productSection;


    public NotebookShoppingPage goToNotebookPage() {
        productSection.format(ProductsPage.NOTEBOOK.getPageName()).click();
        return new NotebookShoppingPage(driver);
    }

    public enum ProductsPage {
        NOTEBOOK("Ноутбуки"),
        MONOBLOCK("Моноблоки"),
        PROJECTOR("Проекторы"),
        PRINTER("Принтеры МФУ");

        private String pageName;

        ProductsPage(String pageName) {
            this.pageName = pageName;
        }

        public String getPageName() {
            return pageName;
        }
    }

    public FooterMenu(WebDriver driver) {
        super(driver);
    }

    public FooterMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
