package com.tim;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by hollisti on 2019-05-24.
 */
public class GooglePageTests {

    @Test
    public void verifyGooglePage(){
        System.out.println("Tim's first test");
        /*Testing Google
        1. Open browser*/
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        2. Navigate to google


//        3. Enter 'selenium' in search field
//        4. click Search button
//        5. Verify search results match
//        */
//        //annotation and verification


    }
    @Test
    public void test2(){
        System.out.println("Tim's second test");
        /*Testing Google
        1. Open browser
        2. Navigate to google
        3. Enter 'selenium' in search field
        4. click Search button
        5. Verify search results match
        */
        //annotation and verification


    }
}
