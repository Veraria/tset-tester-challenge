package com.tset.testerchallenge;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;

public class PriceComponentPage extends BasePage {


    @FindBy(xpath = "//span[@class='font-bold']")
    WebElement total;
    @FindBy(xpath = "//span[contains(text(),'Baseprice')]")
    public WebElement baseprice;
    @FindBy(id = "base-edit-icon")
    public WebElement pencilIcon;
    @FindBy(id = "base-value-input")
    WebElement baseValueTextField;
    @FindBy(id = "base-check-icon")
    WebElement checkIcon;
    @FindBy(id = "ghost-label-input")
    WebElement labelTextField;
    @FindBy(id = "ghost-value-input")
    WebElement valueTextField;
    @FindBy(id = "ghost-check-icon")
    WebElement rowCheckIcon;

    @FindBy(xpath = "//span[contains(text(),'Internal surcharge')]")
    public WebElement internalSurcharge;
    @FindBy(xpath = "//span[contains(text(),'Storage surcharge')]")
    public WebElement storageSurcharge;
    @FindBy(xpath = "//span[contains(text(),'Scrap surcharge')]")
    public WebElement scrapSurcharge;
    @FindBy(xpath = "//span[contains(text(),'Alloy surcharge')]")
    public WebElement alloySurcharge;
    @FindBy (xpath = "(//span[contains(@id,'edit-icon')])[2]")
    public WebElement storageEditIcon;
    @FindBy (xpath = "(//span[contains(@id,'edit-icon')])[4]")
    public WebElement scrapEditIcon;
    @FindBy (xpath = "(//span[contains(@id,'edit-icon')])[5]")
    public WebElement alloyEditIcon;

    @FindBy(xpath = "(//span[contains(@id,'thrash-icon')])[5]")
    public WebElement trashIcon;
    @FindBy(xpath = "(//span[contains(@id,'edit-icon')])[2]")
    WebElement editIcon;
    @FindBy(xpath = "//p[text()=' This label is too short! ']")
    public WebElement labelErrorMessage;
    @FindBy(xpath = "//p[text()=' Cannot be negative! ']")
    public WebElement valueErrorMessage;
    @FindBy (xpath = "(//div[contains(text(),'1.0')])[2]")
    public WebElement externalSurchargeValue;
    @FindBy (xpath = "//div[contains(text(),'0.77')]")
    public WebElement internalSurchargeValue;


    public PriceComponentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public PriceComponentPage hoverOverElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        return new PriceComponentPage(driver);
    }

    public void waitForElementAndClick(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }


    public PriceComponentPage enterNewBaseValue(String value) {

        Actions actions = new Actions(driver);
        actions.moveToElement(baseValueTextField).perform();
        actions.moveToElement(baseValueTextField).contextClick();
        baseValueTextField.clear();
        baseValueTextField.sendKeys(value);
        return new PriceComponentPage(driver);
    }

    public PriceComponentPage enterNewValue(String value) {

        valueTextField.click();
        valueTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        valueTextField.sendKeys(value);
        return new PriceComponentPage(driver);
    }

    public PriceComponentPage enterNewLabel(String labelName) {
        labelTextField.click();
        labelTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        labelTextField.sendKeys(labelName);
        return new PriceComponentPage(driver);
    }

    public void clickCheckIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(checkIcon));
        checkIcon.click();
    }

    public void clickRowCheckIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(rowCheckIcon));
        rowCheckIcon.click();
    }

    public void clickTrashIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(trashIcon));
        trashIcon.click();
    }

    public void clickEditIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(editIcon));
        editIcon.click();
    }

    public void waitForElementToBePresent(String elementId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
    }

    public PriceComponentPage addPriceComponents() {

        labelTextField.click();
        labelTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        inputComponentLabelAndComponentValue();
        return new PriceComponentPage(driver);
    }

    public void inputComponentLabelAndComponentValue() {
        HashMap<String, String> priceComponents = new HashMap<>();
        priceComponents.put("Alloy surcharge", "2.15");
        priceComponents.put("Scrap surcharge", "3.14");
        priceComponents.put("Internal surcharge", "0.7658");
        priceComponents.put("External surcharge", "1");
        priceComponents.put("Storage surcharge", "0.30");

        for (HashMap.Entry<String, String> set :
                priceComponents.entrySet()) {
            labelTextField.click();
            labelTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            labelTextField.sendKeys(set.getKey());

            valueTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            valueTextField.sendKeys(set.getValue());
            waitForElementToBePresent("ghost-check-icon");
            clickingElementJavaScriptExecutor(rowCheckIcon);
        }
    }

    public void clickingElementJavaScriptExecutor(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);

    }

    public String getTextFromTotal() {
        return total.getText();
    }

    public String getTextFromElement(WebElement element) {
        return element.getText();
    }

}
