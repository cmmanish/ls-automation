package com.lyve.qa.tests.login;

import com.lyve.qa.Util.QaConstants;
import com.lyve.qa.Util.QaScreenshot;
import com.lyve.qa.common.LyvePortal;
import com.lyve.qa.selenium.pages.HomePage;
import com.lyve.qa.selenium.pages.LoginPage;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {

    final private static Logger log = Logger.getLogger(LoginTest.class);
    public static WebDriver driver = LyvePortal.getBrowser();

    @Rule
    public QaScreenshot screenShootRule = new QaScreenshot(driver);

    public LoginTest() {
        log.info("-----------------------------------------");
        log.info("Now Running LoginAcceptanceTest Suite");
        log.info("-----------------------------------------");
    }

    @AfterClass
    public static void cleanup() {

        log.info("<--------- Start Logout Test --------->");
        logoutPortal(driver);
        driver.close();
        log.info("<--------- End Logout Test --------->");
    }

    @Test
    public void T1_SuccessfulLoginTest() throws Exception {

        log.info("T1_SuccessfulLoginTest");
        log.info("<--------- Start Test --------->");
        String email = "portal_test_01@lyveminds.com";
        String password = "lyve123";
        String name = "Manish";

        loginPortal(driver, email, password, name);

        logoutPortal(driver);
        log.info("<--------- End Test --------->");
    }
    @After
    public void writeJSON(){


    }

    @Test
    public void T2_WrongUsernameLogintest() throws Exception {

        log.info("T2_WrongUsernameLogintest");
        log.info("<--------- Start Test --------->");
        String email = "portal_dogfood_01@lyveminds.com";
        String password = "lyve123";
        String name = "Manish";

        loginPortal(driver, email, password, name);
        log.info("<--------- End Test --------->");
    }

    @Test
    public void T3_BlankPasswordLogintest() throws Exception {

        log.info("T3_BlankPasswordLogintest");
        log.info("<--------- Start Test --------->");
        String email = "portal_test_01@lyveminds.com";
        String password = "";
        String name = "Manish";

        loginPortal(driver, email, password, name);
        log.info("<--------- End Test --------->");
    }

    @Test
    public void T4_SpaceAsPasswordLogintest() throws Exception {

        log.info("T3_BlankPasswordLogintest");
        log.info("<--------- Start Test --------->");
        String email = "portal_test_01@lyveminds.com";
        String password = " ";
        String name = "Manish";

        loginPortal(driver, email, password, name);
        log.info("<--------- End Test --------->");
    }

    /**
     * Helper Methods are defined here, later on they can be moved to a base class
     * if needed
     *
     */
    public static void loginPortal(WebDriver driver, String email, String password, String expectedScreenName) {

        log.info("===========================================");
        log.info("Now Running loginPortal()");
        log.info("===========================================");

        try {
            HomePage homePage = HomePage.getInstance();
            homePage.click(driver, HomePage.Link.SignIn);
            assertEquals("Page Tittle Doesn't match ", HomePage.Link.SignIn.toString(), driver.getTitle());

            LoginPage loginPage = LoginPage.getInstance();

            if (QaConstants.SUCCESS == loginPage.login(driver, email, password)) {
                //Verify the Login Name in the Home Page
                String actualScreenName = homePage.getInfo(driver, HomePage.Label.LoginName);
                assertEquals(QaConstants.SCREEN_NAME_DOESNT_MATCH, expectedScreenName, actualScreenName);
                log.info("===========================================");
                log.info("Welcome : " + expectedScreenName);
                log.info("===========================================");

            }
            else {
                log.info("===========================================");
                QaScreenshot.capture(driver);
                log.info("===========================================");
            }
        }
        catch (Exception e){
               e.printStackTrace();
        }
    }

    public static void logoutPortal(WebDriver driver){

        log.info("===========================================");
        log.info("Now Running logoutPortal()");
        log.info("===========================================");
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Signout);

    }
}