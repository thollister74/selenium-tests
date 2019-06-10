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
import java.util.List;
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
        System.out.println("Tim's first test");
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
        assertEquals("SeleniumHQ", 0, 0);

        System.out.println("verifyGooglePage has completed");


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
        //1. Open browser

//        2. Maximize browser window and go to google.com
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
        assertEquals("Null quantity", 1, 1);

        System.out.println("automationPracticeTests has completed");


    }
}