package com.lyve.qa.selenium.pages;

import com.lyve.qa.Util.QaConstants;
import com.lyve.qa.Util.QaScreenshot;
import com.lyve.qa.selenium.pages.HomePage.Label;
import com.lyve.qa.selenium.pages.HomePage.Tab;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**This is the <baseURL>/login page that is loaded when we click Sign In from the base URL
    PROD baseURL = www.mylyve.com
    TEST baseURL = https://wwwfront.test.blackpearlsystems.net
    DOGFOOD baseURL = https://wwwfront.dogfood.blackpearlsystems.net
 */
public class LoginPage extends AbstractPage {

    private static LoginPage instance;
    public final Logger log = Logger.getLogger(LoginPage.class);
    private final String pageTittle = "Lyve: Sign In";

    /**
     * Private constructor prevents construction outside this class.
     */
    private LoginPage() {
    }

    public static synchronized LoginPage getInstance() {

        if (instance == null) {
            instance = new LoginPage();
        }

        return instance;
    }

    /**
     * Returns the Page Tittle as a String
     *
     */

    public String getPageTitle(){
        return pageTittle;
    }

    /**
     * Label Element as list of all labels on page
     *
     * @version 2.00
     * @paramlocator
     *        as jQuery mapping for Label element
     * @paramdescription
     *        as description for Label element
     * @author mmadhusoodan
     */
    public static enum Label {

        Company(".company", "Company"),
        BuildNumber(".close", "Build Number"),
        Error(".alert.alert-danger", "Error");

        private String locator;
        private String description;

        private Label(String locator, String description) {
            this.locator = locator;this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        public String getDescription() {
            return this.description;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }

    /**
     * Text Input Element as list of all text inputs on page
     *
     * @version 2.00
     * @paramlocator
     *        as jQuery mapping for text input element
     * @paramdescription
     *        as description for Input Element element
     * @author mmadhusoodan
     */
    public static enum TextInput {

        Email("[name=\"email\"]", "Enter email address"),
        Password("[name=\"password\"]", "Password");

        private String locator;
        private String description;

        private TextInput(String locator, String description) {
            this.locator = locator;
            this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }

    /**
     * Button Element as list of all buttons on page
     *
     * @version 2.00
     *
     * @author mmadhusoodan
     */
    public static enum Button {

        Signin(".btn.btn--large", null, true, "Sign in");

        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Button(String locator, String spinner, boolean pageLoad, String description) {
            this.locator = locator;
            this.spinner = spinner;
            this.pageLoad = pageLoad;
            this.description = description;

        }

        public String getLocator() {
            return this.locator;
        }

        public String getSpinner() {
            return this.spinner;
        }

        public boolean getPageLoad() {
            return this.pageLoad;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }

    /**
     * This Method set to type value in text input
     *
     * @author mmadhusoodan
     * @param driver
     * @param input
     * @param text
     * @return LoginPage
     *
     */

    public LoginPage setText(WebDriver driver, TextInput input, String text) {

        try {
            String query = "$('" + input.getLocator() + "').val('" + text + "').trigger('keyup');";
            if (isElementPresent(driver, input.getLocator()) == true) {
                changeElementBackground(driver, input.getLocator());
                String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
                removeElementBackground(driver, input.getLocator());
                log.info("Type \"" + text + "\" in \"" + input.toString() + "\" Text Input in \"" + this.getClass().getSimpleName() + "\"");
            }
        }
        catch (WebDriverException e){
            log.error(QaConstants.JQUERY_NOT_DEFINED);
            QaScreenshot.capture(driver);
            e.printStackTrace();
        }

        return instance;

    }

    /**
     * This Method set to click on button on page
     *
     * @author mmadhusoodan
     * @param driver
     * @param button
     * @return LoginPage
     * 
     */
    public boolean clickButton(WebDriver driver, Button button) {

        String query = "return $('" + button.getLocator() + "').click();";
        if (isElementPresent(driver, button.getLocator())) {
            changeElementBackground(driver, button.getLocator());
            try{
               ((JavascriptExecutor) driver).executeScript(query);
                removeElementBackground(driver, button.getLocator());
                log.info("Press \"" + button.toString() + "\" Button in \"" + this.getClass().getSimpleName() + "\"");

                return true;
            }
            catch (WebDriverException e){
                log.error(QaConstants.JQUERY_NOT_DEFINED);
                QaScreenshot.capture(driver);
                e.printStackTrace();
            } catch (Exception e){
                QaScreenshot.capture(driver);
                e.printStackTrace();
            }

        }
        else {
            log.info(button.getLocator() + " is not present");
            log.info("Taking Screenshots now ");
            QaScreenshot.capture(driver);
            return false;
        }
        return false;
    }

    /**
     * This method set to return Label text
     * 
     * @author mmadhusoodan
     * @param driver
     * @param label
     * @return String
     * 
     */
    public String getInfo(WebDriver driver, Label label) {

        // Get Label text
        try {
            String query = "return $('" + label.getLocator() + "').text().trim()";
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            return retval;
        }
        catch (WebDriverException e){
            log.error(QaConstants.JQUERY_NOT_DEFINED);
            QaScreenshot.capture(driver);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method set to Login to application
     * 
     * @author mmadhusoodan
     * @param driver
     * @param email
     * @param password
     * @throws Exception
     * @return void
     * 
     */
    public boolean login(WebDriver driver, String email, String password) {

        log.info("===========================================");
        log.info("Type email, password and click on \"Sign in\" button");
        log.info("===========================================");
        waitForElementToBeAppear(driver, TextInput.Email.getLocator());
        waitForElementToBeAppear(driver, Button.Signin.getLocator());
        setText(driver, TextInput.Email, email);
        setText(driver, TextInput.Password, password);
        assertTrue("Click on Sign in Failed", clickButton(driver, Button.Signin));

        if (QaConstants.SUCCESS == isElementPresent(driver, HomePage.Label.LoginName.getLocator())){
            log.info("===========================================");
            log.info(QaConstants.LOGIN_SUCCEEDED);
            log.info("===========================================");
            HomePage homePage = HomePage.getInstance();
            String actualTitle = driver.getTitle();
            assertEquals("Page Title Don't Match",HomePage.Tab.Status.toString(), actualTitle);
            return true;
        }
        else {
            String screenError = getInfo(driver, LoginPage.Label.Error);

            if (QaConstants.USERNAME_PASSWORD_INCORRECT.equalsIgnoreCase(screenError)) {
                log.info("===========================================");
                log.info(QaConstants.USERNAME_PASSWORD_INCORRECT);
                log.info("===========================================");

            } else if (QaConstants.MISSING_CREDENTIALS.equalsIgnoreCase(screenError)) {
                log.info("===========================================");
                log.info(QaConstants.MISSING_CREDENTIALS);
                log.info("===========================================");

            }
            return false;
        }
    }

}