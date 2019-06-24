package com.tim;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


/**
 * Created by hollisti on 2019-05-24.
 */

public class TimsFirstAutomatedTests {

    private static WebDriver driver;

    //For now, keeping this element in test class, later we will move it to page object
    private By postalCodeField = By.cssSelector("#postcode");
    private By phoneNumberField = By.cssSelector("#phone_mobile");
    private By aliasField = By.cssSelector("#alias");
    private By submitButton = By.cssSelector("#submitAccount");

    @BeforeClass
    public static void beforeClassSetup() {
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void afterClassCleanup() {
        System.out.println("Clean up steps held in 'afterClass'");
        driver.close();
    }

    @Before
    public void beforeTestSetup() {

        driver.get("http://automationpractice.com/index.php");
    }


    @Test
    public void productQuantityFieldValidations() throws InterruptedException {
        //Testing using a sample site
        //1. Open browser-moved to Before class
//      #---Moved to Before Class---#

//        2. Go to google.com
//      #---Moved to Before Class---#

//        3. Enter search criteria
        driver.findElement(By.id("search_query_top")).sendKeys("summer");

//        4. click Search button
        driver.findElement(By.name("submit_search")).click();

//        5. Click a link from search results
        driver.findElement(By.partialLinkText("Printed Chiffon Dress")).click();
        String costPerItem = driver.findElement(By.id("our_price_display")).getText().replace("$", "");
        System.out.println("Variable 'costPerItem' value is: " + costPerItem);

//        6. Add a large number in Quantity field
        driver.findElement(By.id("quantity_wanted")).clear();
        driver.findElement(By.id("quantity_wanted")).sendKeys("999");
        driver.findElement(By.name("Submit")).click();

//       7. Confirm cost of order
        driver.findElement(By.partialLinkText("Proceed to checkout")).click();

 //     ****** Should I use BigDecimal instead of double? ******
        String numberOfItemsInCart = driver.findElement(By.cssSelector("#summary_products_quantity")).getText().replace(" Products","");
        assertEquals("Expected x but found y items in cart",(0+Integer.valueOf(numberOfItemsInCart)),999);
        System.out.println("There are "+ numberOfItemsInCart +" items in the cart.");
        System.out.println("Expected is: " +(999 * Float.valueOf(costPerItem)));


        String totalCostBeforeShipping = driver.findElement(By.id("total_product")).getText().replace("$", "").replace(",","");
        System.out.println("Variable 'totalCostBeforeShipping' value is: " + totalCostBeforeShipping);
        String expectedTotal = formatDecimalsForCurrency(999 * Float.valueOf(costPerItem));
        assertEquals("Actual and Expected Total costs don't match", expectedTotal, totalCostBeforeShipping); //confirm amount matches expected
    }



    @Test
    public void automationPracticeCheckout() throws InterruptedException {
        //Testing using a sample site
        //1. Open browser-moved to Before class

//        2. Go to google.com
        driver.get("http://automationpractice.com/index.php");

//        3. Enter search criteria
        driver.findElement(By.id("search_query_top")).sendKeys("faded");

//        4. click Search button
        driver.findElement(By.name("submit_search")).click();

//        5. Click a link from search results
        driver.findElement(By.partialLinkText("Faded Short Sleeve T-shirts")).click();

//        6. Add a large number in Quantity field
        driver.findElement(By.id("quantity_wanted")).clear();
        driver.findElement(By.id("quantity_wanted")).sendKeys("1");
        driver.findElement(By.name("Submit")).click();

//       7. Confirm cost of order
        assertEquals(16.51, (16.51 * 1), 0); //confirm amount matches expected
        driver.findElement(By.xpath("//*[@id=\'layer_cart_product_price\']")).click();

//        8. Proceed through checkout steps
        String testEmail = UUID.randomUUID().toString();
        testEmail = testEmail.substring(0, Math.min(testEmail.length(), 8)); // ---truncating the email address but not sure how!--- //
        driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > a > span")).click();
        driver.findElement(By.cssSelector("#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium > span")).click();
        driver.findElement(By.id("email_create")).sendKeys(testEmail + "@testing.net");
        Thread.sleep(15000); // slowing test to show off randomized email address
        driver.findElement(By.id("SubmitCreate")).click();


//        9. Name & Password
        driver.findElement(By.xpath("//*[@id=\'id_gender1\']")).click();
        driver.findElement(By.name("customer_firstname")).clear();
        driver.findElement(By.name("customer_firstname")).sendKeys("Malcolm");
        driver.findElement(By.name("customer_lastname")).clear();
        driver.findElement(By.name("customer_lastname")).sendKeys("Reynolds");
        driver.findElement(By.name("passwd")).clear();
        driver.findElement(By.name("passwd")).sendKeys("12345");

//         10. Address info
        driver.findElement(By.cssSelector("#address1")).clear();
        driver.findElement(By.cssSelector("#address1")).sendKeys("9121 Serenity Dr");
        driver.findElement(By.cssSelector("#address2")).clear();
        driver.findElement(By.cssSelector("#address2")).sendKeys("Suite 14");
        driver.findElement(By.cssSelector("#city")).clear();
        driver.findElement(By.cssSelector("#city")).sendKeys("Browncoat");

//      Select State from a dropdown
        selectWebElement(By.xpath("//*[@id=\'id_state\']"),"Washington");

//      Complete form and submit
        driver.findElement(postalCodeField).clear();
        driver.findElement(postalCodeField).sendKeys("99258");
        clearAndSendKeys(phoneNumberField, "509.555.8574");
        clearAndSendKeys(aliasField, "Business Address");
        driver.findElement(submitButton).click();
    }


    private String formatDecimalsForCurrency(float amtToBeFormatted){
        DecimalFormat df = new DecimalFormat("#.00");
        df.setMaximumFractionDigits(2);
        return df.format(amtToBeFormatted);
    }

    private void selectWebElement(By by, String elementToSelect){
        Select dropdown = new Select(driver.findElement(by));
        dropdown.selectByVisibleText(elementToSelect);
    }

    private void clearAndSendKeys(By by,String value){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(value);

    }
}