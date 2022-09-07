package tests;

import com.tset.testerchallenge.PriceComponentPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PriceComponentTests extends BaseTest {

    @Test(description = "Verifying the base price after changing the base value", priority = 1)

    public void changeBasePriceValueTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage.hoverOverElement(priceComponentPage.baseprice)
                .waitForElementAndClick(priceComponentPage.pencilIcon);
        priceComponentPage.enterNewBaseValue("5")
                .clickCheckIcon();

        String expectedResult = priceComponentPage.getTextFromTotal();

        Assert.assertEquals("5.00", expectedResult, "The result should be 5.00 but it's " + expectedResult);

    }




    @Test(description = "Verifying if all price components are added correctly", priority = 2)

    public void addingAllPriceComponentsTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        priceComponentPage.addPriceComponents();

        String roundedValue = priceComponentPage.getTextFromElement(priceComponentPage.internalSurchargeValue);
        //	If value has more than 2 decimal digits, round to 2 decimal digits; test input is 0.7658
        Assert.assertEquals(0.77, Double.parseDouble(roundedValue));


    }


    @Test(description = "Verifying if the price component is removed when clicking trash icon", priority = 3)

    public void removePriceComponentTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage.hoverOverElement(priceComponentPage.internalSurcharge);
        priceComponentPage.clickTrashIcon();

        String expectedResult = priceComponentPage.getTextFromTotal();

        Assert.assertEquals("11.59", expectedResult, "The result should be 11.59 but it's " + expectedResult);

    }


    @Test(description = "Verifying that when editing label the label error message is displayed after invalid label input",
          priority = 4)

    public void editPriceComponentOfStorageSurchargeTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage.hoverOverElement(priceComponentPage.storageSurcharge);
        priceComponentPage.storageEditIcon.click();
        priceComponentPage.enterNewLabel("T");

        String errorMessage = priceComponentPage.getTextFromElement(priceComponentPage.labelErrorMessage);
        Assert.assertEquals("This label is too short!", errorMessage, "The message should be 'This label is too short!' but it's " + errorMessage);

        //restoring last valid state because the input is invalid
        priceComponentPage.clickRowCheckIcon();
        String labelText = priceComponentPage.getTextFromElement(priceComponentPage.storageSurcharge);
        Assert.assertEquals("Storage surcharge", labelText);
    }

    @Test(description = "Verifying that when editing value the value error message is displayed after adding negative values",
          priority = 5)

    public void editPriceComponentOfScrapSurchargeTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage.hoverOverElement(priceComponentPage.scrapSurcharge);
        priceComponentPage.scrapEditIcon.click();
        priceComponentPage.enterNewValue("-2.15");

        String errorMessage = priceComponentPage.getTextFromElement(priceComponentPage.valueErrorMessage);
        Assert.assertEquals("Cannot be negative!", errorMessage, "The message should be 'Cannot be negative!' but it's " + errorMessage);

    }

    @Test(description = "Verifying tha after editing the value of price component total amount is changed",
         priority = 6)

    public void editPriceComponentOfAlloySurcharge() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage.hoverOverElement(priceComponentPage.alloySurcharge);
        priceComponentPage.alloyEditIcon.click();
        priceComponentPage.enterNewValue("1.79");
        priceComponentPage.clickRowCheckIcon();

        String expectedResult = priceComponentPage.getTextFromTotal();

        Assert.assertEquals("13.38", expectedResult, "The result should be 13.38 but it's " + expectedResult);
    }
}