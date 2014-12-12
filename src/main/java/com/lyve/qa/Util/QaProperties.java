package com.lyve.qa.Util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;

public class QaProperties { // singleton
    private static Logger log = Logger.getLogger(QaProperties.class);
    private static QaProperties instance;

    private QaProperties() {
    };

    private static String HOME_DIR = "/Users/mmadhusoodan/workspace/com.qa.selenium";

    public static QaProperties getInstance() {
        if (instance == null) {
            instance = new QaProperties();
        }
        return instance;
    }

    public static Properties getProperty() {
        Properties props = new Properties();
        try {
            FileInputStream in;
            String address = "/src/main/resources/local.properties";
            address = HOME_DIR + address;
            log.info("Looking for property file: " + address);

            File fileProps = new File(address);

            if (fileProps.exists()) {
                in = new FileInputStream(fileProps);
            }
            else {
                in = new FileInputStream(new File("local.properties"));
            }

            props.load(in);
            in.close();
            return props;
        }
        catch (Exception e) {
            log.info(e);
            return null;
        }
    }

    public static Properties props = getProperty();

    public static String getIPAddress() {
        String address = "";
        try {
            InetAddress in = InetAddress.getLocalHost();
            address = in.getHostAddress();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    public static String getBrowser() {
        return props.getProperty("portal.browser");
    }
    
    public static String getPortalURL() {
        return props.getProperty("portal.url");
    }

    public static String getScreenShotsDir() {
        return props.getProperty("screenshots.root.folder");
    }

    public static String getReportsDir() {
        return props.getProperty("reports.root.folder");
    }
    
    public static boolean isFirefox(){
        return "firefox".equals(getBrowser());
           
    }
    
    public static boolean isChrome() {
        return "chrome".equals(getBrowser());
    }
    
    public static boolean isIE() {
        return "ie".equals(getBrowser());
    }

    public static boolean isSafari() {
        return "safari".equals(getBrowser());
    }

}
