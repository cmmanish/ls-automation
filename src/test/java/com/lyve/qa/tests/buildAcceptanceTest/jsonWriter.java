package com.lyve.qa.tests.buildAcceptanceTest;

import com.lyve.qa.Util.QaCalendar;
import com.lyve.qa.Util.QaConstants;
import com.lyve.qa.Util.QaProperties;
import com.lyve.qa.selenium.pages.HomePage;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class jsonWriter {

	public static Logger log = Logger.getLogger(jsonWriter.class);
	private static QaCalendar calendar = QaCalendar.getInstance();
	private static String captureDate = calendar.getCaptureTime();
	private static String reportsDir = QaProperties.getReportsDir();

	private static FileWriter file = null;
	private long startTime = 0;
	private long endTime = 0;
	private long elapsed = 0;
	private String runStatus = "failed";
	private String testSectionName = "jsonWriter";
	private String testName = "";

	private static JSONObject railsTestJSON = new org.json.simple.JSONObject();
	private JSONObject eachResult = new org.json.simple.JSONObject();
	private static JSONArray resultsList = new JSONArray();

	@Rule
	public TestName name = new TestName();

	@Before
	public void beforeMethod(){
		testName = "";
		startTime = System.currentTimeMillis(); // Get the start Time
	}

	@BeforeClass
	public static void testSetUp() {
		String fileName = "railtest.json";
		String path = reportsDir + QaConstants.SLASH + fileName;
		try {
			file = new FileWriter(path);
			railsTestJSON.put("suiteName", "lyvesuite-automation");
			railsTestJSON.put("resultsList", resultsList);
			railsTestJSON.put("reportName","railtest.json");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDown() {
		try {
			file.append(railsTestJSON.toString());
			file.flush();
			file.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@After
	public void afterMethod() {
		endTime = System.currentTimeMillis(); // Get the end Time
		elapsed = endTime - startTime;
		try {
			eachResult.put("elapsed", elapsed);
			eachResult.put("testSectionName", testSectionName);
			eachResult.put("runStatus", runStatus);
			eachResult.put("testName", testName);
			resultsList.add(eachResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void T1_DevPortalLaunchTest() {
	assertEquals("Tittle don't Match",HomePage.getPageTittle(), HomePage.getPageTittle());
		runStatus = "passed";
		testName = name.getMethodName();
	}

	@Test
	public void T2_TestPortalLaunchTest() {
		assertEquals("Tittle don't Match",HomePage.getPageTittle(), HomePage.getPageTittle());
		testName = name.getMethodName();
	}
	@Test
	public void T3_TestPortalLaunchTest() {
		assertEquals("Tittle don't Match",HomePage.getPageTittle(), HomePage.getPageTittle());
		testName = name.getMethodName();
	}
}
