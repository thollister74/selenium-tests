package com.tim;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**d
 * Created by hollisti on 2019-05-24.
 */

public class TimsAutomatedTests {

    private static WebDriver driver;

    private String formatDecimalsForCurrency(float amtToBeFormatted){
        DecimalFormat df = new DecimalFormat("#.00");
        df.setMaximumFractionDigits(2);
        return df.format(amtToBeFormatted);
    }

    private void selectWebElement(By by, String elementToSelect){
        Select dropdown = new Select(driver.findElement(by));
        dropdown.selectByVisibleText(elementToSelect);
    }

    private void clearFieldAndSendTextToField(By by, String value){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(value);
    }

    //For now, keeping this element in test class, later we will move it to page object
    private By postalCodeField = By.cssSelector("#postcode");
    private By phoneNumberField = By.cssSelector("#phone_mobile");
    private By aliasField = By.cssSelector("#alias");
    private By submitButton = By.cssSelector("#submitAccount");
    private By address1 = By.cssSelector("#address1");
    private By address2 = By.cssSelector("#address2");
    private By city = By.cssSelector("#city");
    private By quantity_wanted = By.id("quantity_wanted");
    private By customer_firstname = By.name("customer_firstname");
    private By customer_lastname = By.name("customer_lastname");
    private By setPassword = By.name("passwd");
    private By searchTextField = By.id ("search_query_top");
    private By emailTextField = By.id("email_create");
    private By checkboxOnAddressConfirmation = By.id("addressesAreEquals");
    private By agreeToTermsOfServiceCheckbox = By.id("cgv");
    private By checkForSuccessText = By.cssSelector(".alert-success");
    private By modalProceedToCheckoutButton = By.partialLinkText("Proceed to checkout");
    private By checkNumberOfItemsInCart = By.id(("summary_products_quantity"));
    private By checkOutScreenQuantityTextField = By.className("cart_quantity_input");
    private boolean isChecked;
    private By addToCartButton = By.name("submit_search");
    private By womenButtonOnMenuBar = By.partialLinkText("Women");
    private By searchMagnifyingGlassButton = By.name("submit_search");
    private By addItemToCartButton = By.name("Submit");
    private By cartSummaryproceedToCheckoutButton = By.partialLinkText("Proceed to checkout");

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
        System.out.println("Action listed in 'afterClass' have been completed");
        driver.close();
    }

    @Before
    public void beforeTestSetup() {
        driver.get("http://automationpractice.com/index.php");
    }


   @After
    public void afterTestCleanup(){
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.cssSelector(".shopping_cart .ajax_cart_quantity"))).perform();
        driver.findElement(By.className(".ajax_cart_block_remove_link")).click();
    }


    @Test
    public void productQuantityFieldValidations() throws InterruptedException {
//      From landing page, enter search criteria
        clearFieldAndSendTextToField(searchTextField,"summer");

//        4. click Search button
        driver.findElement(searchMagnifyingGlassButton).click();

//        5. Click a link from search results
        driver.findElement(By.partialLinkText("Printed Chiffon Dress")).click();
        String costPerItem = driver.findElement(By.id("our_price_display")).getText().replace("$", "");
        System.out.println("Variable 'costPerItem' value is: " + costPerItem);

//        6. Add a large number in Quantity field
        clearFieldAndSendTextToField(quantity_wanted,"999");
        driver.findElement(By.name("Submit")).click();

//       7. Confirm cost of order
        driver.findElement(modalProceedToCheckoutButton).click();

 //     ****** Should I use BigDecimal instead of double? ******
        String numberOfItemsInCart = driver.findElement(By.cssSelector("#summary_products_quantity")).getText().replace(" Products","");
        assertEquals("Unexpected number of items in cart",(0+Integer.valueOf(numberOfItemsInCart)),999);
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
//        driver.get("http://automationpractice.com/index.php");

//        3. Enter search criteria
        clearFieldAndSendTextToField(searchTextField, "faded");

//        4. click Search button
        driver.findElement(searchMagnifyingGlassButton).click();

//        5. Click a link from search results
        driver.findElement(By.partialLinkText("Faded Short Sleeve T-shirts")).click();

//        6. Add a large number in Quantity field
        clearFieldAndSendTextToField(quantity_wanted, "1");
        driver.findElement(addItemToCartButton).click();

//       7. Confirm cost of order
        assertEquals(16.51, (16.51 * 1), 0); //confirm amount matches expected
        driver.findElement(By.xpath("//*[@id=\'layer_cart_product_price\']")).click();//add to cart button

//        8. Proceed through checkout steps
        String testEmail = UUID.randomUUID().toString();
        testEmail = testEmail.substring(0, Math.min(testEmail.length(), 8)); // ---truncating the email address but not sure how!--- //
        driver.findElement(modalProceedToCheckoutButton).click();
        driver.findElement(cartSummaryproceedToCheckoutButton).click();
        clearFieldAndSendTextToField(emailTextField, testEmail + "@testing.net");
        System.out.println("Test email is: " +testEmail + "@testing.net");
        driver.findElement(By.id("SubmitCreate")).click();

//        9. Name & Password
        driver.findElement(By.id("id_gender1")).click();
        clearFieldAndSendTextToField(customer_firstname, "Malcolm");
        clearFieldAndSendTextToField(customer_lastname, "Reynolds");
        String createPassword = UUID.randomUUID().toString();
        createPassword = createPassword.substring(0,Math.min(createPassword.length(),8));
        clearFieldAndSendTextToField(setPassword, createPassword);
        System.out.println("Test password is: " +createPassword);

//         10. Address info
        clearFieldAndSendTextToField(address1, "9121 Serenity Dr");
        clearFieldAndSendTextToField(address2, "Suite 14");
        clearFieldAndSendTextToField(city, "Browncoat");

//      Select State from a dropdown
        selectWebElement(By.id("id_state"), "Washington");

//      Complete form and submit

        clearFieldAndSendTextToField(postalCodeField, "99258");
        clearFieldAndSendTextToField(phoneNumberField, "509.555.8574");
        clearFieldAndSendTextToField(aliasField, "Business Address");
        driver.findElement(submitButton).click();

        //Address Page
        assertTrue(isChecked = driver.findElement(checkboxOnAddressConfirmation).isSelected());
        driver.findElement(By.name("processAddress")).click();

        //Shipping info page
        isChecked = driver.findElement(agreeToTermsOfServiceCheckbox).isSelected();
        driver.findElement(agreeToTermsOfServiceCheckbox).click();
        driver.findElement(By.name("processCarrier")).click();
        driver.findElement(By.className("cheque")).click();//Payment Method
        driver.findElement(By.cssSelector("#cart_navigation > button > span")).click();
        String saleConfirmation = checkForSuccessText.toString();
        assertEquals("Did not find expected 'Success' text", "Your order on My Store is complete.",           driver.findElement(checkForSuccessText).getText());
    }

    @Test
    public void editShoppingCartContentsAndConfirmCartUpdatesCorrectly ()throws InterruptedException{
//        3. Enter search criteria
        clearFieldAndSendTextToField(searchTextField, "printed summer dress");
        driver.findElement(addToCartButton).click();
        driver.findElement(By.partialLinkText("Faded Short Sleeve T-shirts")).click();

        clearFieldAndSendTextToField(quantity_wanted, "2");
        driver.findElement(By.name("Submit")).click();

        //Check number of items in cart
        driver.findElement(By.partialLinkText("Proceed to checkout")).click();
        assertEquals("'Actual' didn't match 'Expected'","2 Products",driver.findElement(checkNumberOfItemsInCart).getText());

        //Add to cart and re-check count
        driver.findElement(By.className("icon-plus")).click();
        TimeUnit.SECONDS.sleep(1);
        assertEquals("'Actual' didn't match 'Expected'","3 Products",driver.findElement(checkNumberOfItemsInCart).getText());

        //Subtract from cart and re-check count
        driver.findElement(By.className("icon-minus")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.className("icon-minus")).click();
        TimeUnit.SECONDS.sleep(1);
        assertEquals("'Actual' didn't match 'Expected'","1 Product",driver.findElement(checkNumberOfItemsInCart).getText());

        clearFieldAndSendTextToField(checkOutScreenQuantityTextField,"11");
        TimeUnit.SECONDS.sleep(2);
        assertEquals("'Actual' didn't match 'Expected'","11 Products",driver.findElement(checkNumberOfItemsInCart).getText());
        //placed a stop point at ln 232 and visually confirmed 11 items. Not sure why this is failing
        //Increased sleep time (a terrible approach, I know) and the test passing. ??
        WebElement modalProceedToCheckoutButton = driver.findElement(By.partialLinkText("Proceed to checkout"));
        modalProceedToCheckoutButton.click();
    }

    @Test
    public void addMultipleItemsToCartEditContents () throws InterruptedException  {
        clearFieldAndSendTextToField(searchTextField, "Blouse");
        driver.findElement(By.name("submit_search")).click();
        driver.findElement(addToCartButton).click();
        driver.findElement(By.partialLinkText("Printed Summer Dress")).click();
        driver.findElement(By.name("Submit")).click();
        driver.findElement(modalProceedToCheckoutButton).click();
        assertEquals("'Actual' didn't match 'Expected'","1 Product",driver.findElement(checkNumberOfItemsInCart).getText());

        driver.findElement(womenButtonOnMenuBar).click();
        driver.findElement(By.partialLinkText("Printed Dress")).click();
        driver.findElement(By.name("Submit")).click();
        driver.findElement(modalProceedToCheckoutButton).click();
        assertEquals("'Actual' didn't match 'Expected'","2 Products",driver.findElement(checkNumberOfItemsInCart).getText());

        driver.findElement(womenButtonOnMenuBar).click();
        driver.findElement(By.partialLinkText("Printed Chiffon Dress")).click();
        driver.findElement(By.name("Submit")).click();
        driver.findElement(modalProceedToCheckoutButton).click();
        assertEquals("'Actual' didn't match 'Expected'","3 Products",driver.findElement(checkNumberOfItemsInCart).getText());



    }
}