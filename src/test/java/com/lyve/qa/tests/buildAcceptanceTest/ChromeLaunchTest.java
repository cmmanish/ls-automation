package com.lyve.qa.tests.buildAcceptanceTest;

import com.lyve.qa.Util.QaConstants;
import com.lyve.qa.Util.QaReportProcessor;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChromeLaunchTest {

    public static Logger log = Logger.getLogger(ChromeLaunchTest.class);
    private static String userName = "manish@shopkick.com";
    private static String password = "shopkick123";

    @Test
    public void Test1() {
        System.setProperty("webdriver.chrome.driver", QaConstants.CHROME_DRIVER_LOCATION);
        WebDriver driver = new ChromeDriver();
        try {
            log.info("--------------------------------------------------------------------------------");
            WebDriverWait wait = new WebDriverWait(driver, 5);
            driver.navigate().to("https://shopkick.testlodge.com/");
            driver.manage().window().maximize();//

            log.info("--------------------------------------------------------------------------------");

            driver.findElement(By.name("email")).sendKeys(userName);
            driver.findElement(By.name("password")).sendKeys(password);

            driver.findElement(By.className("submit")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("second")));

            assertEquals("Welcome", driver.findElement(By.className("first")).getText().trim());

            driver.navigate().to("https://shopkick.testlodge.com/projects/10019/runs/195404");
            log.info(driver.findElement(By.className("first")).getText() + " " + driver.findElement(By.className("second")).getText());
            String a = driver.findElement(By.className("test_run")).getText().trim();

            //assertEquals("Report NOT Complete", "Complete", driver.findElement(By.className("details")).getText().trim());

            String notRun = driver.findElements(By.className("count_cont")).get(0).getText().trim();
            String pass = driver.findElements(By.className("count_cont")).get(1).getText().trim();
            String fail = driver.findElements(By.className("count_cont")).get(2).getText().trim();

            log.info("pass: " + pass);
            log.info("fail: " + fail);
            log.info("notRun: " + notRun);
            log.info("--------------------------------------------------------------------------------");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    @Test
    public void Test2() {
        QaReportProcessor qaReportProcessor = new QaReportProcessor();
        log.info(qaReportProcessor.getTestcaseCount());
        log.info(qaReportProcessor.isTestcaseNamesEmpty());
    }
}
