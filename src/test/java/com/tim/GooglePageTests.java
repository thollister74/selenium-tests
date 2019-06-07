package com.tim;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.openqa.selenium.By.*;


/**
 * Created by hollisti on 2019-05-24.
 */

public class GooglePageTests {
    @BeforeClass
    public static void beforeClassSetup(){
        System.out.println("set up for the 'beforeClass' ");

    }

    @AfterClass
    public static void afterClassCleanup(){
        System.out.println("Clean up step for After Class");
    }
    @Before
    public void beforeTestSetip()
    {

        System.out.println("Printing from before annotation");
       }

    @After
    public void afterCleanup(){
        System.out.println("Run this after the test");
;
    }

    @Test
    public void verifyGooglePage() throws InterruptedException {
        System.out.println("Tim's first test");
 //Simple script to run a Google search
        //1. Open browser
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        2. Maximize browser window and go to google.com
        driver.manage().window().maximize();
        driver.get("https://google.com");

//        3. Enter search criteria
        WebElement search = driver.findElement(name("q"));
        search.sendKeys("SeleniumHq");

//        4. click Search button
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement searchButton = driver.findElement(name("btnK"));
        searchButton.click();

//        5. Click a link from search results, confirm landing page
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> searchResults = driver.findElements(partialLinkText("Selenium - Web Browser"));
        searchResults.get(0).click();
        Assert.assertEquals("SeleniumHQ",1,1);

        System.out.println("verifyGooglePage has completed");
        driver.close();

    }


    @Test
    public void loginToStatePortalUI(){
        System.out.println("Tim's second test");
//Testing against State Portal UI
//        1. Open browser
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        2. Navigate to state portal ui in awstest (must be a way to modularize the url...
        driver.manage().window().maximize();
        driver.get("https://awstestplatform.sircon.com/govexternal/#/login?state=VA&taxType=PREMTAX&taxForm=ANNASSMT");

//        3. Enter Email, NAICID, Password in search field
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        WebElement userName = driver.findElement(id("username"));
        userName.sendKeys("vaportaltester@gmail.com");
        WebElement naicID = driver.findElement(id("userId"));
        naicID.sendKeys("M0037");
        WebElement password = driver.findElement(id("password"));
        password.sendKeys("Sircon101!");

//        4. click to login
        WebElement signInButton = driver.findElement(className("btn-submit"));
        signInButton.click();

//        5. Verify search results match
//        //annotation and verification
        assertEquals("M0037",1,1); //Confirm NAIC matches ID entered at login
        System.out.println("loginToStatePortalUI has completed");
        driver.close();
    }


    @Test
    public void automationPracticeTests() throws InterruptedException
    {
 //Testing using a sample site
        //1. Open browser
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        2. Maximize browser window and go to google.com
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php");

//        3. Enter search criteria
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement search = driver.findElement(id("search_query_top"));
        search.sendKeys("summer");

//        4. click Search button
        WebElement searchButton = driver.findElement(name("submit_search"));
        searchButton.click();

//        5. Click a link from search results
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement printedDressLink = driver.findElement(partialLinkText("Printed Dress"));
        printedDressLink.click();

//        6. Add a large number in Quantity field
        WebElement printedDressQuantity = driver.findElement(id("quantity_wanted"));
        printedDressQuantity.clear();
        printedDressQuantity.sendKeys("999");
        WebElement addToCartButton = driver.findElement(name("Submit"));
        addToCartButton.click();

//       7. Confirm cost of order
        assertEquals(25974.00, (999*26),0); //confirm amount matches expected
        WebElement modalCloseButton = driver.findElement(xpath("//*[@id='layer_cart']/div[1]/div[1]/span"));
        modalCloseButton.click();

//        8. Clear quantity and try alpha characters
        printedDressQuantity = driver.findElement(id("quantity_wanted"));
        printedDressQuantity.clear();
        printedDressQuantity.sendKeys("abc");
        assertEquals("Null quantity",1,1);

        System.out.println("automationPracticeTests has completed");
        driver.close();

    }
}