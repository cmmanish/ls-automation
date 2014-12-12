package com.lyve.qa.tests.browser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by mmadhusoodan on 12/11/14.
 */
public class ChromeDriverTest {

    WebDriver driver;

    @Before
    public void setUp(){
        try {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
            driver = new ChromeDriver();

        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Test
    public void testMethod(){

        driver.get("https://wwwfront.test.blackpearlsystems.net/");

    }

    @After
    public void tearDown(){
        driver.quit();
    }
}

