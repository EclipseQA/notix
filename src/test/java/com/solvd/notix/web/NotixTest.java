package com.solvd.notix.web;

import com.ibm.icu.text.MessageFormat;
import com.ibm.icu.util.ULocale;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.solvd.notix.web.components.FooterMenu;
import com.solvd.notix.web.components.NavbarMenu;
import com.solvd.notix.web.components.OrderFormModel;
import com.solvd.notix.web.dto.Notebook;
import com.solvd.notix.web.dto.getdto.GetCustomer;
import com.solvd.notix.web.dto.getdto.GetNotebook;
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
    public void testMakeAnOrder() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();

        FooterMenu footerMenu = mainPage.switchToFooterMenu();
        NotebookShoppingPage notebookShoppingPage = footerMenu.goToNotebookPage();
        notebookShoppingPage.clickOnBuyButton(1);

        NavbarMenu navbarMenu = notebookShoppingPage.switchToNavbarMenu();
        navbarMenu.hoverOnCart();

        OrderFormModel orderFormModel = navbarMenu.clickBuyButton();
        orderFormModel.fillCustomerInformationInputsByCustomerModel(GetCustomer.getCustomerWithAllFields());
        orderFormModel.clickOrderButton();
        Assert.assertTrue(orderFormModel.isCheckoutBodyPresent());
    }

    @Test
    public void testFilterPanelOptions() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();
        NotebookShoppingPage gamingNotebookShoppingPage = mainPage.goToGamingNotebookShoppingPage();

        String expectedBrand = R.TESTDATA.get("brand");
        String expectedCpu = R.TESTDATA.get("cpu");
        String expectedResolution = R.TESTDATA.get("resolution");
        gamingNotebookShoppingPage.checkFilterOption(NotebookShoppingPage.FilterOption.BRAND, expectedBrand);
        gamingNotebookShoppingPage.openPanelFilterOption(NotebookShoppingPage.FilterPanel.CPU);
        gamingNotebookShoppingPage.checkFilterOption(NotebookShoppingPage.FilterOption.CPU, expectedCpu);
        gamingNotebookShoppingPage.openPanelFilterOption(NotebookShoppingPage.FilterPanel.RESOLUTION);
        gamingNotebookShoppingPage.checkFilterOption(NotebookShoppingPage.FilterOption.RESOLUTION, expectedResolution);

        int timesToClick = new Random().nextInt(gamingNotebookShoppingPage.getNumberOfProductsOnPage()) + 1;
        NotebookDescriptionPage descriptionPage = gamingNotebookShoppingPage.clickOnProductById(timesToClick);
        String actualBrand = descriptionPage.getBrandName();
        String actualCpu = descriptionPage.getDescriptionItem(NotebookDescriptionPage.DescriptionCommonField.CPU_MODEL);
        String actualResolution = descriptionPage.getDescriptionItem(NotebookDescriptionPage.DescriptionCommonField.RESOLUTION);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(actualBrand.toLowerCase().contains(expectedBrand.toLowerCase()));
        softAssert.assertTrue(actualCpu.toLowerCase().contains(expectedCpu.toLowerCase()));
        softAssert.assertTrue(actualResolution.toLowerCase().contains(expectedResolution.toLowerCase()));
        softAssert.assertAll();
    }

    @Test
    public void testAddedProductDescription() {
        MainPage mainPage = new MainPage(getDriver());
        mainPage.open();

        FooterMenu footerMenu = mainPage.switchToFooterMenu();
        NotebookShoppingPage notebookShoppingPage = footerMenu.goToNotebookPage();

        int timesToClick = new Random().nextInt(notebookShoppingPage.getNumberOfProductsOnPage()) + 1;
        NotebookDescriptionPage notebookDescriptionPage = notebookShoppingPage.clickOnProductById(timesToClick);
        Notebook notebookOnShoppingPage = GetNotebook.getNotebookByPage(notebookDescriptionPage);
        notebookDescriptionPage.clickBuyButton();

        NavbarMenu navbarMenu = notebookDescriptionPage.switchToNavbarMenu();

        NotebookDescriptionPage notebookDescriptionPage2 = navbarMenu.goToLastAddedProductDescriptionPageInCart();
        Notebook notebookInCart = GetNotebook.getNotebookByPage(notebookDescriptionPage2);

        Assert.assertEquals(notebookOnShoppingPage, notebookInCart);
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
        int timesToClick = new Random().nextInt(notebookShoppingPage.getNumberOfProductsOnPage()) + 1;
        notebookShoppingPage.clickOnBuyButton(timesToClick);

        NavbarMenu navbarMenu1 = notebookShoppingPage.switchToNavbarMenu();

        ULocale locale = new ULocale("ru_RU");
        MessageFormat formatter =
                new MessageFormat("{0, plural, one {# ТОВАР} few {# ТОВАРА} many {# ТОВАРОВ} other {# ТОВАР}}", locale);
        String expectedResult = formatter.format(new Object[]{timesToClick});
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
