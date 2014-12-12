package com.lyve.qa.tests.buildAcceptanceTest;

import com.lyve.qa.Util.QaCalendar;
import com.lyve.qa.Util.QaConstants;
import com.lyve.qa.Util.QaProperties;
import com.lyve.qa.Util.QaScreenshot;
import com.lyve.qa.common.LyvePortal;
import com.lyve.qa.selenium.pages.HomePage;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BAT {

	public static Logger log = Logger.getLogger(BAT.class);
	public static WebDriver driver = LyvePortal.getBrowser();
	private static QaCalendar calendar = QaCalendar.getInstance();
	private static String captureDate = calendar.getCaptureTime();
	private static String reportsDir = QaProperties.getReportsDir();

	static FileWriter file = null;
	private long startTime = 0;
	private long endTime = 0;
	private long elapsed =0;
	private String runStatus = "failed";
	private String testSectionName = "BAT";
	private String testName = "";

	@Rule
	public QaScreenshot screenShootRule = new QaScreenshot(driver);
	@Rule
	public TestName name = new TestName();

	@BeforeClass
	public static void testSetUp() {
		log.info("<--------- Start testSetUp Test --------->");
		log.info("<--------- End testSetUp Test --------->");
		String fileName = captureDate+"/railtest.json";
		String path = reportsDir + QaConstants.SLASH + fileName;
		try {
			file = new FileWriter(path);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void cleanup() {
		log.info("<--------- Start tearDown Test --------->");
		driver.close();
		log.info("<--------- End tearDown Test --------->");
		try {
			file.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


	}

	@Before
	public void beforeMethod(){
		testName = "";
		startTime = System.currentTimeMillis(); // Get the start Time
	}

	@After
	public void afterMethod(){
		endTime = System.currentTimeMillis(); // Get the end Time
		elapsed = endTime - startTime ;
		log.info("Finished: "+ testName+ " in: "+ elapsed + " Status: "+ runStatus);
		log.info(runStatus);
		JSONObject obj = new JSONObject();
		try {
			obj.put("elapsed:", elapsed);
			obj.put("testSectionName:", "BAT");
			obj.put("runStatus:", runStatus);
			obj.put("testName:", testName);
		}
		catch (JSONException e){
			e.printStackTrace();
		}

		try {

			file.write(obj.toString());
			file.write("\n");//appends the string to the file
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void T1_DevPortalLaunchTest() {

		log.info("TestName "+name.getMethodName());
		driver.get("https://wwwfront.dev.blackpearlsystems.net/");
		assertEquals("Tittle don't Match",driver.getTitle(), HomePage.getPageTittle());
		runStatus = "passed";
		testName = name.getMethodName();

	}

	@Test
	public void T2_TestPortalLaunchTest(){
		log.info("TestName "+name.getMethodName());
		driver.get("https://wwwfront.test.blackpearlsystems.net/");
		assertEquals("Tittle don't Match",driver.getTitle(), HomePage.getPageTittle());
		runStatus = "passed";
		testName = name.getMethodName();
	}

	@Test
	public void T3_DogFoodPortalLaunchTest() {
		log.info("TestName "+name.getMethodName());
		driver.get("https://wwwfront.dogfood.blackpearlsystems.net/");
		assertEquals("Tittle don't Match", driver.getTitle(), HomePage.getPageTittle());
		runStatus = "passed";
		testName = name.getMethodName();
	}
}

