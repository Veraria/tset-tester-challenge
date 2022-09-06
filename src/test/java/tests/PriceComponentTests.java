package tests;

import com.tset.testerchallenge.PriceComponentPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PriceComponentTests extends BaseTest {

    @Test(description = "Verifying the base price after changing the base value",priority = 0)

    public void changeBasePriceValueTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage.hoverOverElement(priceComponentPage.baseprice)
                .waitForElementAndClick(priceComponentPage.pencilIcon);
        priceComponentPage.enterNewBaseValue("5")
                .clickCheckIcon();

        String expectedResult = priceComponentPage.getTextFromTotal();

        Assert.assertEquals("5.00", expectedResult, "The result should be 5.00 but it's " + expectedResult);

    }


    @Test(description = "Verifying if all price components are added correctly", priority = 1)

    public void addingAllPriceComponentsTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        priceComponentPage.addPriceComponents();

        String expectedValue = priceComponentPage.getTextFromElement(priceComponentPage.externalSurchargeValue);
        //	If value has no decimal digits, show a 0 as decimal digit; test input is 1
       Assert.assertEquals(1.0, Double.parseDouble(expectedValue), "The value should be 1.0 but it's " + Double.parseDouble(expectedValue));

        String roundedValue = priceComponentPage.getTextFromElement(priceComponentPage.internalSurchargeValue);
        //	If value has more than 2 decimal digits, round to 2 decimal digits; test input is 0.7658
        Assert.assertEquals(0.77, Double.parseDouble(roundedValue));


    }


    @Test(description = "Verifying if the price component is removed when clicking trash icon",priority = 2)

    public void removePriceComponentTest() {

        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        driver.navigate().refresh();
        priceComponentPage.enterNewLabel("Internal surcharge")
                .enterNewValue("100")
                .clickRowCheckIcon();
        priceComponentPage.hoverOverElement(priceComponentPage.internalSurcharge);
        priceComponentPage.clickTrashIcon();

        String expectedResult = priceComponentPage.getTextFromTotal();

        Assert.assertEquals("1.00", expectedResult, "The result should be 1.00 but it's " + expectedResult);

    }


    @Test(description = "Verifying that when editing label the label error message is displayed after invalid label input",
          priority = 3)

    public void editPriceComponentOfStorageSurchargeTest() {


        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        driver.navigate().refresh();
        priceComponentPage.enterNewLabel("Storage surcharge")
                .enterNewValue("4")
                .clickRowCheckIcon();
        priceComponentPage.hoverOverElement(priceComponentPage.storageSurcharge)
                .clickEditIcon();
        priceComponentPage.enterNewLabel("T");

        String errorMessage = priceComponentPage.getTextFromElement(priceComponentPage.labelErrorMessage);
        Assert.assertEquals("This label is too short!", errorMessage, "The message should be 'This label is too short!' but it's " + errorMessage);

        //restoring last valid state because the input is invalid
        priceComponentPage.clickRowCheckIcon();
        String labelText = priceComponentPage.getTextFromElement(priceComponentPage.storageSurcharge);
        Assert.assertEquals("Storage surcharge", labelText);
    }

    @Test(description = "Verifying that when editing value the value error message is displayed after adding negative values",
          priority = 4)

    public void editPriceComponentOfScrapSurchargeTest() {


        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        driver.navigate().refresh();
        priceComponentPage.enterNewLabel("Scrap surcharge")
                .enterNewValue("2.50")
                .clickRowCheckIcon();
        priceComponentPage.hoverOverElement(priceComponentPage.scrapSurcharge);
        priceComponentPage.clickEditIcon();
        priceComponentPage.enterNewValue("-2.15");

        String errorMessage = priceComponentPage.getTextFromElement(priceComponentPage.valueErrorMessage);
        Assert.assertEquals("Cannot be negative!", errorMessage, "The message should be 'Cannot be negative!' but it's " + errorMessage);

    }

    @Test(description = "Verifying tha after editing the value of price component total amount is changed",
         priority = 5)
    public void editPriceComponentOfAlloySurcharge() {


        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage.enterNewLabel("Alloy surcharge")
                .enterNewValue("2.00")
                .clickRowCheckIcon();
        priceComponentPage.hoverOverElement(priceComponentPage.alloySurcharge);
        priceComponentPage.clickEditIcon();
        priceComponentPage.enterNewValue("1.79");
        priceComponentPage.clickRowCheckIcon();

        String expectedResult = priceComponentPage.getTextFromTotal();

        Assert.assertEquals("2.79", expectedResult, "The result should be 2.79 but it's " + expectedResult);
    }



}