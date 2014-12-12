package com.lyve.qa.tests.browser;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverTest {
    public static Logger log = Logger.getLogger(FirefoxDriverTest.class);
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
    	
        WebDriver driver = new FirefoxDriver();
       // And now use this to visit Google
        driver.get("https://wwwfront.test.blackpearlsystems.net/");

        // Check the title of the page
        log.info("Page title is: " + driver.getTitle());

        //Close the browser
        driver.close();
    }

}