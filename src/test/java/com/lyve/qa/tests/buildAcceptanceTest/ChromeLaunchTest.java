package com.lyve.qa.tests.buildAcceptanceTest;

import com.lyve.qa.Util.QaConstants;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChromeLaunchTest {

    public static Logger log = Logger.getLogger(ChromeLaunchTest.class);

    @Test
    public void Test() {
        log.info("--------------------------------------------------------------------------------");
        System.setProperty("webdriver.chrome.driver", QaConstants.CHROME_DRIVER_LOCATION);
        WebDriver driver = new ChromeDriver();
        log.info(driver);
        driver.get("https://shopkick.testlodge.com/projects/10019/test_runs");
        log.info("--------------------------------------------------------------------------------");
        driver.close();

    }
}
