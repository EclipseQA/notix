package com.solvd.notix.web;

import com.ibm.icu.text.MessageFormat;
import com.ibm.icu.util.ULocale;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.solvd.notix.web.components.FooterMenu;
import com.solvd.notix.web.components.NavbarMenu;
import com.solvd.notix.web.dto.NotebookModel;
import com.solvd.notix.web.dto.getdto.GetNotebookModel;
import com.solvd.notix.web.pages.MainPage;
import com.solvd.notix.web.pages.NotebookDescriptionPage;
import com.solvd.notix.web.pages.NotebookShoppingPage;
import com.solvd.notix.web.pages.SalesPage;
import com.zebrunner.carina.utils.R;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Random;

public class NotixTest implements IAbstractTest {

    @Test
    public void testFilterPanelOptions() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();
        NotebookShoppingPage gamingNotebookShoppingPage = mainPage.goToGamingNotebookShoppingPage();
        gamingNotebookShoppingPage.checkFilterOption(NotebookShoppingPage.FilterOption.BRAND, R.TESTDATA.get("brand"));
        gamingNotebookShoppingPage.openPanelFilterOption(NotebookShoppingPage.FilterPanel.CPU);
        gamingNotebookShoppingPage.checkFilterOption(NotebookShoppingPage.FilterOption.CPU, R.TESTDATA.get("cpu"));
        gamingNotebookShoppingPage.openPanelFilterOption(NotebookShoppingPage.FilterPanel.RESOLUTION);
        gamingNotebookShoppingPage.checkFilterOption(NotebookShoppingPage.FilterOption.RESOLUTION, R.TESTDATA.get("resolution"));


        NotebookDescriptionPage descriptionPage = gamingNotebookShoppingPage.clickOnProductById(1);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(descriptionPage.getDescriptionItem(NotebookDescriptionPage.DescriptionCommonField.CPU_MODEL)
                .contains(R.TESTDATA.get("cpu")));
        softAssert.assertTrue(descriptionPage.getDescriptionItem(NotebookDescriptionPage.DescriptionCommonField.RESOLUTION)
                .contains(R.TESTDATA.get("resolution")));
        softAssert.assertTrue(descriptionPage.getDescriptionItem(NotebookDescriptionPage.DescriptionCommonField.PRODUCT_LINE)
                .contains(R.TESTDATA.get("brand").toUpperCase()));
        softAssert.assertAll();
    }

    @Test
    public void testAddedProductDescription() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();

        FooterMenu footerMenu = mainPage.switchToFooterMenu();
        NotebookShoppingPage notebookShoppingPage = footerMenu.goToNotebookPage();

        int i = new Random().nextInt(20) + 1;
        NotebookDescriptionPage notebookDescriptionPage = notebookShoppingPage.clickOnProductById(i);
        NotebookModel notebookModelOnShoppingPage = GetNotebookModel.getNotebookModel(notebookDescriptionPage);
        notebookDescriptionPage.clickBuyButton();

        NavbarMenu navbarMenu = notebookDescriptionPage.switchToNavbarMenu();

        NotebookDescriptionPage notebookDescriptionPage2 = navbarMenu.goToLastAddedProductDescriptionPageInCart();
        NotebookModel notebookModelInCart = GetNotebookModel.getNotebookModel(notebookDescriptionPage2);

        Assert.assertEquals(notebookModelOnShoppingPage, notebookModelInCart);
    }

    @Test
    public void testSearchSuggestion() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();

        NavbarMenu navbarMenu = mainPage.switchToNavBarMenu();
        navbarMenu.typeTextInSearchInput(R.TESTDATA.get("device.1"));

        Assert.assertTrue(navbarMenu.isTypedProductSuggestionPresent());
    }

    @Test
    public void testSalesPrice() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();

        NavbarMenu navbarMenu = mainPage.switchToNavBarMenu();

        SalesPage salesPage = navbarMenu.goToSalesPage();

        List<Float> oldPricesList = salesPage.getOldPrices();
        List<Float> discountPricesList = salesPage.getDiscountPrices();
        for (int i = 0; i < oldPricesList.size(); i++) {
            Assert.assertTrue(discountPricesList.get(i) < oldPricesList.get(i));
        }
    }

    @Test()
    public void testTheSpellingNumberOfProductsInCart() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();

        NavbarMenu navbarMenu = mainPage.switchToNavBarMenu();

        NotebookShoppingPage notebookShoppingPage = navbarMenu.goToNotebookPage();
        int numberToAddProducts = new Random().nextInt(20) + 1;
        notebookShoppingPage.clickOnBuyButton(numberToAddProducts);

        NavbarMenu navbarMenu1 = notebookShoppingPage.switchToNavbarMenu();

        ULocale locale = new ULocale("ru_RU");
        MessageFormat formatter =
                new MessageFormat("{0, plural, one {# ТОВАР} few {# ТОВАРА} many {# ТОВАРОВ} other {# ТОВАР}}", locale);
        String expectedResult = formatter.format(new Object[]{numberToAddProducts});
        String actualResult = navbarMenu1.getNumberOfProductsInCart();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test()
    public void testOpenNotebookPageFromNavbar() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();
        NavbarMenu navbarMenu = mainPage.switchToNavBarMenu();
        NotebookShoppingPage notebookShoppingPage = navbarMenu.goToNotebookPage();
        Assert.assertTrue(notebookShoppingPage.isPageOpened());
    }
}
