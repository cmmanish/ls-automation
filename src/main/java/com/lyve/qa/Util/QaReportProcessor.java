package com.lyve.qa.Util;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by mmadhusoodan on 10/26/15.
 */
public class QaReportProcessor {
    public static Logger log = Logger.getLogger(QaReportProcessor.class);

    WebDriver driver = null;
    private String testLodgeReportDir = "";
    private String reportScreenshotPath = "";
    private String userName = "manish@shopkick.com";
    private String password = "shopkick123";
    public static String platformType = "Android";
    private String reportName = "";
    private String reportTime = "";
    private String TestLodgeURL = "";
    private static String testResult = "";
    String projectId = "10019";
    String suiteId = "58074";
    public String testRunId = "";
    public String testRunName = "";
    String passed, failed, notRun = "";


    /**
     * Creates a TestRun and uploads the results
     *
     * @return String TestRunId
     */

    public String getTestLodgeReportName() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(QaConstants.TEST_LODGE_DIR + File.separator + QaConstants.TEST_LODGE_RESULT_JSON));
            JSONObject jsonObject = (JSONObject) obj;
            reportName = (String) jsonObject.get("reportName");
            log.info("reportName: " + reportName);
        } catch (Exception e) {
            log.error(e);
        }
        return reportName;
    }

    public int getTestcaseCount() {
        JSONParser parser = new JSONParser();
        int tcaseCount = 0;
        try {
            Object obj = parser.parse(new FileReader(QaConstants.TEST_LODGE_DIR + File.separator + QaConstants.TEST_LODGE_RESULT_JSON));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray resultsList = (JSONArray) jsonObject.get("resultsList");
            tcaseCount = resultsList.size();
        } catch (Exception e) {
            log.error(e);
        }
        return tcaseCount;
    }

    public boolean isTestcaseNamesEmpty() {
        JSONParser parser = new JSONParser();
        int emptyCount = 0;
        try {
            Object obj = parser.parse(new FileReader(QaConstants.TEST_LODGE_DIR + File.separator + QaConstants.TEST_LODGE_RESULT_JSON));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray resultsList = (JSONArray) jsonObject.get("resultsList");
            if (resultsList.size() == 0) {
                return false;
            }
            for (int i = 0; i < resultsList.size(); i++) {
                JSONObject testCaseObject = (JSONObject) resultsList.get(i);
                String tSection = testCaseObject.get("testSectionName").toString();
                String tCase = testCaseObject.get("testName").toString();
                if ("".equalsIgnoreCase(tSection) || "".equalsIgnoreCase(tCase)) {
                    emptyCount++;
                }
            }
            if (emptyCount == resultsList.size()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    public String getTestLodgeResults() {
        try {

            testResult = "\npassed: " + passed + "\n" + "failed: " + failed + "\n" + "notRun: " + notRun + "\n";
        } catch (Exception e) {
            log.error(e);
            return "";
        }
        return testResult;
    }

    public boolean hasFailedTests() {
        boolean flag = false;
        try {
            if (Integer.parseInt(failed) != 0)
                flag = true;
            return flag;
        } catch (Exception e) {
            log.error(e);
            return flag;
        }
    }

    /**
     * launch TestLodge and SignIn and go to the test results pace and
     * take a screen and place it in the reports directory
     */
    public HashMap<String, String> LaunchTestLodgeAndGrabValues(String url, String testRunName) throws Exception {

        HashMap<String, String> resultMap = new HashMap<>();
        reportName = getTestLodgeReportName();
        if (!reportName.isEmpty()) {
            try {
                testLodgeReportDir = reportName.split("_t")[0].trim();
                testLodgeReportDir = testLodgeReportDir.replace(":", "_");
                System.setProperty("webdriver.chrome.driver", QaConstants.CHROME_DRIVER_LOCATION);
                driver = new ChromeDriver();
                WebDriverWait wait = new WebDriverWait(driver, 5);
                driver.navigate().to(url);
                driver.manage().window().maximize();

                driver.findElement(By.name("email")).sendKeys(userName);
                driver.findElement(By.name("password")).sendKeys(password);
                driver.findElement(By.className("submit")).click();

                Thread.sleep(5000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("second")));

                assertEquals("Welcome", driver.findElement(By.className("first")).getText().trim());
                log.info(driver.findElement(By.className("first")).getText() + " " + driver.findElement(By.className("second")).getText());
                String a = driver.findElement(By.className("test_run")).getText().trim();

                String reportStatus = driver.findElement(By.className("details")).getText().trim();

                if ("Complete".equalsIgnoreCase(reportStatus)) {
                    log.info("Report is Complete");
                } else {
                    log.info("Report is In progress");
                }

                notRun = driver.findElements(By.className("count_cont")).get(0).getText().trim();
                passed = driver.findElements(By.className("count_cont")).get(1).getText().trim();
                failed = driver.findElements(By.className("count_cont")).get(2).getText().trim();

                Integer percentagePass = 100 * Integer.parseInt(passed) / (Integer.parseInt(notRun) + Integer.parseInt(passed) + Integer.parseInt(failed));
                testResult = "\npassed: " + passed + "\n" + "failed " + failed + "\n" + "notRun " + notRun + "\n";
                resultMap.put("notRun", notRun);
                resultMap.put("passed", passed);
                resultMap.put("failed", failed);
                resultMap.put("percentagePass", percentagePass.toString());
                resultMap.put("reportScreenshotPath", reportScreenshotPath);
                resultMap.put("reportStatus", reportStatus);

                log.info(testResult);
            } catch (NoSuchElementException nse) {
                log.info(reportName + " Not Found, Probably report was not uploaded ... Exiting");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (driver != null)
                    driver.quit();
            }
        } else {
            log.info("reportName is Empty check testLodge.json");
        }
        return resultMap;
    }

    public String getHumanReadableReportTime(String reportScreenshotPath) {

        try {
            reportTime = (reportScreenshotPath.split("/screenshot")[0]);
            reportTime = reportTime.split(platformType + "_")[1];
            reportTime = reportTime.replace("_", ":");
        } catch (Exception e) {
            log.error(e);
        }
        return reportTime;
    }
}