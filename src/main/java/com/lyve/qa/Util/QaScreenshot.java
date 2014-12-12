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

public class QaScreenshot extends TestWatcher {

    private static Logger log = Logger.getLogger(QaScreenshot.class);
    private static QaScreenshot instance;
    private WebDriver driver;

    private static QaCalendar calendar = QaCalendar.getInstance();
    private static String captureDate = calendar.getCaptureTime();
    private static String screenshotsDir = QaProperties.getScreenShotsDir();

    private static String path = screenshotsDir + QaConstants.SLASH + captureDate + QaConstants.SLASH;

    private QaScreenshot() {
    };

    public QaScreenshot(WebDriver driver) {
        this.driver =  driver;
    }

    public static synchronized QaScreenshot getInstance() {
        if (instance == null) {
            instance = new QaScreenshot();
        }
        return instance;
    }

    @Override
    protected void failed(Throwable e, Description description) {

        String path = capture(driver);
        log.info("Image Path: "+path);
        log.info(description.getAnnotations());
    }
    /*
    * Since this object requires a driver object, I don't want to create it without one. So create a private no
    * argument constructor to block direct creation.
    */
    public static String capture(WebDriver driver) {

        try{
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

            path = path + source.getName();
            FileUtils.copyFile(source, new File(path));
        }

        catch( IOException e ){
            path = "Failed to capture screenshot: " + e.getMessage();
        }
        catch( Exception e ){
            path = "Failed to capture screenshot: " + e.getMessage();
        }
        return path;
    }

}