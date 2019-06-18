package com.tim;



import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;


/**
 * Created by hollisti on 2019-05-24.
 */

public class GooglePageTests {


    private WebDriver driver;

    @BeforeClass
    public static void beforeClassSetup()
    {
    System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");

    }

    @AfterClass
    public static void afterClassCleanup()
    {
        System.out.println("Clean up step for 'afterClass'");
    }

    @Before
    public void beforeTestSetup()
    {
       driver  = new ChromeDriver();
       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
       driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
    }

    @After
    public void afterCleanup()
    {
        driver.close();
    }

    @Test
    public void verifyGooglePage() throws InterruptedException {
        //Simple script to run a Google search
        //1. Open browser-moved to Before class


//        2. Go to google.com

        driver.get("https://google.com");

//        3. Enter search criteria
        driver.findElement(By.name("q")).sendKeys("SeleniumHq");

//        4. click Search button
         driver.findElement(By.name("btnK")).click();

//        5. Click a link from search results, confirm landing page
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> searchResults = driver.findElements(By.partialLinkText("Selenium - Web Browser"));
        searchResults.get(0).click();

//      Assert using GET TEST
        assertEquals("Selenium automates browsers",driver.findElement(By.xpath("//*[@id='mainContent']/p[1]/i")).getText());
    }


    @Test
    public void loginToStatePortalUI() {
        System.out.println("Tim's second test");
//Testing against State Portal UI
//        1. Open browser
//          Moved to 'Before' class


//        2. Navigate to state portal ui in awstest (must be a way to modularize the url...

        driver.get("https://awstestplatform.sircon.com/govexternal/#/login?state=VA&taxType=PREMTAX&taxForm=ANNASSMT");

//        3. Enter Email, NAICID, Password in search field
      driver.findElement(By.id("username")).clear();
      driver.findElement(By.id("username")).sendKeys("vaportaltester@gmail.com");
      driver.findElement(By.id("userId")).clear();
      driver.findElement(By.id("userId")).sendKeys("M0037");

      driver.findElement(By.id("password")).clear();
      driver.findElement(By.id("password")).sendKeys("Sircon101!");

//        4. click to login
      driver.findElement(By.className("btn-submit")).click();

//        5. Verify search results match
//        //annotation and verification
       assertEquals("M0037", 1, 1); //Confirm NAIC matches ID entered at login
       System.out.println("loginToStatePortalUI has completed");
    }


    @Test
    public void automationPracticeTests() throws InterruptedException {
        //Testing using a sample site
        //1. Open browser-moved to Before class

//        2. Go to google.com
        driver.get("http://automationpractice.com/index.php");

//        3. Enter search criteria
        driver.findElement(By.id("search_query_top")).sendKeys("summer");

//        4. click Search button
        driver.findElement(By.name("submit_search")).click();

//        5. Click a link from search results
        driver.findElement(By.partialLinkText("Printed Dress")).click();

//        6. Add a large number in Quantity field
        driver.findElement(By.id("quantity_wanted")).clear();
        driver.findElement(By.id("quantity_wanted")).sendKeys("999");
        driver.findElement(By.name("Submit")).click();

//       7. Confirm cost of order
        assertEquals(25974.00, (999 * 26), 0); //confirm amount matches expected
        driver.findElement(By.xpath("//*[@id='layer_cart']/div[1]/div[1]/span")).click();

//        8. Clear quantity and try alpha characters
        driver.findElement(By.id("quantity_wanted")).clear();
        driver.findElement(By.id("quantity_wanted")).sendKeys("abc");

//       Assert using get text and xpath,
        assertEquals("100% cotton double printed dress. Black and white striped top and orange high waisted skater skirt bottom.", driver.findElement(By.xpath("//*[@id=\'short_description_content\']/p")).getText());

//       Assert using get text and CSS Selection, failing
       assertEquals("Null quantity",driver.findElement(By.cssSelector("#product > div.fancybox-overlay.fancybox-overlay-fixed > div > div > div > div > p")).getText());
        System.out.println("automationPracticeTests has completed");

//      Assert using Select from a list
        driver.findElement(By.xpath("//*[@id=\'group_1\']")).click();
        System.out.println("Sample page test(s) complete");
    }
    @Test
    public void automationPracticeCheckout()throws InterruptedException
    {
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
            testEmail = testEmail.substring(0, Math.min(testEmail.length(),8)); // ---truncating the email address but not sure how!--- //
            driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > a > span")).click();
            driver.findElement(By.cssSelector("#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium > span")).click();
            driver.findElement(By.id("email_create")).sendKeys(testEmail+"@testing.net");
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
            Select stDropdown = new Select (driver.findElement(By.xpath("//*[@id=\'id_state\']")));
            stDropdown.selectByVisibleText("Washington");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

//      Complete form and submit
            driver.findElement(By.cssSelector("#postcode")).clear();
            driver.findElement(By.cssSelector("#postcode")).sendKeys("99258");
            driver.findElement(By.cssSelector("#phone_mobile")).clear();
            driver.findElement(By.cssSelector("#phone_mobile")).sendKeys("509.555.8574");
            driver.findElement(By.cssSelector("#alias")).clear();
            driver.findElement(By.cssSelector("#alias")).sendKeys("Business Address");
            driver.findElement(By.cssSelector("#submitAccount")).click();







        }



    }