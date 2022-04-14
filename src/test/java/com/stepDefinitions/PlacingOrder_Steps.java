package com.stepDefinitions;

import com.utils.BrowserUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class PlacingOrder_Steps {

    private WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() throws InterruptedException {
        BrowserUtils.wait(5);
        driver.quit();
    }

    @Given("user adds items in the cart")
    public void userAddsItemsInTheCart() {
        driver.get("https://www.ae.com/us/en/p/women/sunglasses/sunglasses/aeo-cat-eye-sunglasses/0506_1680_001?menu=cat4840004");
        BrowserUtils.wait(5);

        WebElement acceptCookies = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-primary qa-btn-accept-cookie   btn-block btn-sm qa-btn-accept-cookie _btn-accept-cookie_1eoaez']")));
        acceptCookies.click();

        WebElement addToBag = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-primary qa-btn-add-to-bag   qa-add-to-bag btn-block']")));
        addToBag.click();
    }

    @And("user gets to card page and clicks Proceed to Checkout")
    public void userGetsToCardPageAndClicksProceedToCheckout() {
        driver.get("https://www.ae.com/us/en/cart");

        WebElement proceedToCheckout = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-colored-blue    btn-block btn-go2checkout qa-btn-go2checkout']")));
        proceedToCheckout.click();
    }

    @And("Shipping Info is filled out")
    public void shippingInfoIsFilledOut() {
        WebElement email = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='email']")));
        email.sendKeys("evcrosbie@gmail.com");

        WebElement firstName = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='firstname']")));
        firstName.sendKeys("Evgeniya");

        WebElement lastName = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='lastname']")));
        lastName.sendKeys("Crosbie");

        // CANCEL NOTIFICATIONS
        WebElement cancelNotifications = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='qa-btn-cancel close-btn _close-btn_1kc7gs']")));
        cancelNotifications.click();

        WebElement streetAddress = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='streetAddress1']")));
        streetAddress.sendKeys("35 Truman Cir");

        WebElement city = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='city']")));
        city.sendKeys("Mc Kees Rocks");

        WebElement zip = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='postalCode']")));
        zip.sendKeys("15136");

        WebElement states = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='states']")));
        Select statesDropDown = new Select(states);
        statesDropDown.selectByVisibleText("Pennsylvania");
    }

    @And("user chooses to Redeem Gift Card")
    public void userChoosesToRedeemGiftCard() {
        WebElement redeemGiftCard = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='gift-card-show-form']")));
        redeemGiftCard.click();
    }

    @When("user enters {string} and {string} and clicks Apply")
    public void userEntersAndAndClicksApply(String giftCardNum, String pin) throws InterruptedException {
        WebElement giftCardNumField = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='giftCardNumber']")));
        giftCardNumField.sendKeys(giftCardNum);

        WebElement pinField = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='giftCardPin']")));
        pinField.sendKeys(pin);

        BrowserUtils.wait(5);

        WebElement apply = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='apply-gift-card']")));
        apply.click();
    }

    @Then("error {string} for gift card should be displayed")
    public void errorForGiftCardShouldBeDisplayed(String expectedError) {
        try {
            WebElement giftCardWarningMessage = new WebDriverWait(driver, Duration.ofSeconds(10)).
                    until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ember-view help-block qa-error-help-block has-error   _help-block_851xj7']")));
            String actualError = giftCardWarningMessage.getText().trim();

            System.out.println("Expected error: " + expectedError);
            System.out.println("Actual error: " + actualError);
            Assert.assertEquals(actualError, expectedError);
        } catch (TimeoutException e) {
            Assert.fail("Error message was not displayed");
        }
    }

    @When("user enters {string} and {string} and {string} and {string}")
    public void userEntersAndAndAnd(String creditCard, String expiration, String cvv, String phoneNum) {
        WebElement creditCardField = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='cardNumber']")));
        creditCardField.sendKeys(creditCard);

        WebElement expirationField = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='expirationDate']")));
        expirationField.sendKeys(expiration);

        WebElement cvvField = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='securityCode']")));
        cvvField.sendKeys(cvv);

        WebElement phoneNumField = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='phoneNumber']")));
        phoneNumField.sendKeys(phoneNum);
        phoneNumField.sendKeys(Keys.TAB);
    }

    @Then("error {string} for credit card should be displayed")
    public void errorForCreditCardShouldBeDisplayedIn(String expectedError) {
        WebElement warningMessage = new WebDriverWait(driver, Duration.ofSeconds(10)).
                    until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ember-view help-block qa-error-help-block has-error  ']")));
        String actualError = warningMessage.getText().trim();

        System.out.println("Expected error: " + expectedError);
        System.out.println("Actual error: " + actualError);
        Assert.assertEquals(actualError, expectedError);
    }

}
