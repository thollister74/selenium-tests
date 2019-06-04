package com.tim;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
    public void beforeTestSetip(){

        System.out.println("Printing from before annotation");
    }

    @After
    public void afterCleanup(){
        System.out.println("Run this after the test");
    }

    @Test
    public void verifyGooglePage(){
        System.out.println("Tim's first test");
        //Testing Google
        //1. Open browser
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        2. Navigate to google
        driver.manage().window().maximize();
        driver.get("https://google.com");

//        3. Enter 'selenium' in search field
        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys("automated testing using Selenium");

//        4. click Search button
        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.click();



//        5. Verify search results match
//        //annotation and verification


    }
    @Test
    public void test2(){
        System.out.println("Tim's second test");
//        Testing Google
//        1. Open browser
//        2. Navigate to google
//        3. Enter 'selenium' in search field
//        4. click Search button
//        5. Verify search results match
//        */
//        //annotation and verification


    }
}
