package com.lyve.qa.selenium.pages;

import com.lyve.qa.Util.QaConstants;
import com.lyve.qa.Util.QaScreenshot;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class HomePage extends AbstractPage {

    private static HomePage instance;
    public static Logger log = Logger.getLogger(HomePage.class);
    private static final String pageTittle = "Collect, Protect, and Rediscover Your Photos and Videos | Lyve";

    /**
     * Private constructor prevents construction outside this class.
     */
    private HomePage() {
    }

    public static synchronized HomePage getInstance() {

        if (instance == null) {
            instance = new HomePage();
        }

        return instance;
    }

    /**
     * Returns the Page Tittle as a String
     *
     */

    public static String getPageTittle(){
        return pageTittle;
    }

    /**
     * Label Element as list of all labels on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Label element
     * @param description
     *        as description for Label element
     * @author mmadhusoodan
     */
    public static enum Label {

        LoginName (".nav__user.js-cookie-name", "LoginName"),
        ShowIfLoggedIn(".show-if-logged-in", "Company");
        
        private String locator;
        private String description;

        private Label(String locator, String description) {
            this.locator = locator;
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
     * This method set to return Link text
     *
     * @author mmadhusoodan
     * @param driver
     * @param label
     * @return String
     *
     */
    public String getInfo(WebDriver driver, Tab tab) {

        // Get Label text
        String query = "return $('" + tab.getLocator() + "').text()";
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;

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
        String query = "return $('" + label.getLocator() + "').text()";
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;

    }
    
    /**
     * TextPillow Element as list of all TextPillows on page
     * 
     * @version 2.00
     * @param id
     *        as jQuery mapping for element
     * @param dropDownInputId
     *        as jQuery mapping for text input element
     * @param dropDownClassName
     *        as jQuery mapping for drop down menu element
     * @author mmadhusoodan
     */
    public static enum DropDownMenu {

        Client("#client_dd_0_input", "#client_dd_0", ".dropdown_item", "client_name");

        private String id;
        private String dropDownInputId;
        private String dropDownClassName;
        private String attributeName;

        private DropDownMenu(String id, String dropDownInputId, String dropDownClassName, String attributeName) {

            this.id = id;
            this.dropDownInputId = dropDownInputId;
            this.dropDownClassName = dropDownClassName;
            this.attributeName = attributeName;
        }

        public String getId() {
            return this.id;
        }

        public String getDropDownInputId() {
            return this.dropDownInputId;
        }

        public String getDropDownClassName() {
            return this.dropDownClassName;
        }

        public String getAttributeName() {
            return this.attributeName;
        }
    }

    /**
     * Tab Element as list of all buttons on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Tabs element locator
     * @param spinner
     *        as jQuery mapping for spinner
     * @param pageLoad
     *        as jQuery mapping for pageLoad
     * @param description
     *        as description for Tab element
     * @author mmadhusoodan
     */
    public static enum Tab {

        Home("a:contains(\"Home\")", null, true, "Collect, Protect, and Rediscover Your Photos and Videos | Lyve"),
        Status("a:contains(\"Status\")", null, true, "Lyve: Your status"),
        Profile("a:contains(\"Visitors\")", null, true, "Lyve: Account"),
        Orders("a:contains(\"Orders\")", "#null", true, "Lyve: Order History"),
        History("#sub_navigation_container a:contains(\"History\")", null, true, "History");

        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Tab(String locator, String spinner, boolean pageLoad, String description) {
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
     * Link Element as list of all links on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Tabs element locator
     * @param spinner
     *        as jQuery mapping for spinner
     * @param pageLoad
     *        as jQuery mapping for pageLoad
     * @param description
     *        as description for Link element
     * @author mmadhusoodan
     */
    public static enum Link {

        SignIn("a:eq(6)", null, true, "Lyve: Sign In"),
        Signout("a:eq(5)", null, true, "Collect, Protect, and Rediscover Your Photos and Videos | Lyve");

        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Link(String locator, String spinner, boolean pageLoad, String description) {
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
     * This Method set to click on Tab on Home page
     * 
     * @author mmadhusoodan
     * @param driver
     * @param tab
     * @return void
     * 
     */
    public void select(WebDriver driver, Tab tab) {
        String query = "$('" + tab.getLocator() + "')[0].click();";
        if (isElementPresent(driver, tab.getLocator()) == true) {
            changeElementBackground(driver, tab.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, tab.getLocator());
            log.info("Select \"" + tab.toString() + "\" Tab in \"" + this.getClass().getSimpleName() + "\"");

            if (tab.getPageLoad()) {
                waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
            }

            if (tab.getSpinner() != null) {
                waitForElementToDissappear(driver, tab.getSpinner());
            }
        }

    }

    /**
     * This Method set to click on link on page
     * 
     * @author mmadhusoodan
     * @param driver
     * @param link
     * @return void
     * 
     */
    public void click(WebDriver driver, Link link) {

        try {
            String query = "$('" + link.getLocator() + "')[0].click();";
            if (isElementPresent(driver, link.getLocator())) {
                changeElementBackground(driver, link.getLocator());
                wait(100);
                removeElementBackground(driver, link.getLocator());
                String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
                log.info("Press \"" + link.toString() + "\" Link in \"" + this.getClass().getSimpleName() + "\"");

                if (link.getPageLoad()) {
                    waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
                }

                if (link.getSpinner() != null) {
                    waitForElementToDissappear(driver, link.getSpinner());
                }
              }
            }
            catch (WebDriverException e){
                log.error(QaConstants.JQUERY_NOT_DEFINED);
                QaScreenshot.capture(driver);
                e.printStackTrace();
            }

    }

}
