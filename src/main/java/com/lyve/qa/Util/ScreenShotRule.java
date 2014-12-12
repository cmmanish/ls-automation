package com.lyve.qa.Util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;

public class ScreenShotRule extends TestWatcher {

    public static Logger log = Logger.getLogger(ScreenShotRule.class);

    private WebDriver browser;
    private static QaCalendar calendar = QaCalendar.getInstance();
    private static String captureDate = calendar.getCaptureTime();
    private static String screenshots = QaProperties.getScreenShotsDir();
    private static String path = screenshots + QaConstants.SLASH + captureDate + QaConstants.SLASH;

    public ScreenShotRule(WebDriver browser) {
        this.browser =  browser;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) browser;

        File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = getDestinationFile();
        try {
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private File getDestinationFile() {
        String userDirectory = FileUtils.getUserDirectoryPath();
        String fileName = "screenShot.png";
        String absoluteFileName = userDirectory + QaConstants.SLASH + fileName;

        return new File(absoluteFileName);
    }

}
