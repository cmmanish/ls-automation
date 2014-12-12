package com.lyve.qa.common;

import com.lyve.qa.Util.QaConstants;
import com.lyve.qa.Util.QaProperties;
import com.lyve.qa.Util.QaScreenshot;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class LyvePortal {

	static Logger log = Logger.getLogger(LyvePortal.class);

	private static WebDriver driver = null;
	private static int WIDTH = 1920;
	private static int HEIGHT = 1080;

	private static QaScreenshot qaScreenshot = QaScreenshot.getInstance();

	public LyvePortal() {
	}

	public static WebDriver getBrowser() {

		setupBrowser();
		launchPortal();
		return driver;
	}

    //Factory Method Pattern
	public static void setupBrowser() {

		try {
			if (QaProperties.isFirefox()) {
				driver = new FirefoxDriver();
			}
			else if (QaProperties.isChrome()) {
				System.setProperty("webdriver.chrome.driver", QaConstants.CHROME_DRIVER_LOCATION);
				driver = new ChromeDriver();
			}
			else if (QaProperties.isSafari()) {
		        driver = new SafariDriver();
			}
            else if (QaProperties.isIE()) {
		        driver = new InternetExplorerDriver();
            }
			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			log.info("===========================================");
			log.info("Platform:"+cap.getPlatform());
			log.info("===========================================");
			log.info("Browser: "+cap.getBrowserName());
			log.info("===========================================");
			log.info("Version: "+cap.getVersion());

		} catch (Exception e) {
			e.printStackTrace();
		}

        //driver.manage().window().setSize(new Dimension(WIDTH, HEIGHT));
        //log.info("Set Browser Dimensions to " + WIDTH + "x" + HEIGHT + ".");
  	}

	public static void launchPortal() {

		String portalURL = QaProperties.getPortalURL();
		log.info("==============================");
		log.info("PortalURL: "+ portalURL);
		log.info("==============================");
		driver.navigate().to(portalURL);

		try {

			if ((QaConstants.PAGE_LOAD_ERROR).equalsIgnoreCase(driver.getTitle())) {
				log.info("===========================================");
				log.info("Host not reachable");
				log.info("===========================================");
				qaScreenshot.capture(driver);
				driver.close();
				System.exit(1);
			}
			else if (QaConstants.NEED_TO_ON_VPN.equalsIgnoreCase(driver.getTitle())) {
				log.info("===========================================");
				log.info("You need To Be On VPN");
				log.info("===========================================");
				qaScreenshot.capture(driver);
				driver.close();
				System.exit(1);
			}
			else {
				driver.getTitle();
				log.info("===========================================");
				log.info("Launching ...  " + portalURL);
				log.info("===========================================");

			}
		} catch (Exception e){
				e.printStackTrace();
		}
	}

}