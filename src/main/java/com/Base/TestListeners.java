package com.Base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import projUtility.SignUpPage;

public class TestListeners implements ITestListener {
	
	protected static WebDriver driver;
	protected static ExtentReports reports;
	protected static ExtentTest test;
	static String ReportPath;
    String snapID=BaseClass.addDate();
	@Override
	public void onTestStart(ITestResult result) {
		Log.info("\n \n");
		Log.info("***********************************************************");
		Log.info("***********************************************************");
		Log.info("************** On Test starts *************** " + result.getName());
		Log.info("***********************************************************");
		Log.info("***********************************************************");
		ReportPath = System.getProperty("user.dir")+"/Reports/Report_"+result.getName()+snapID+"_"+System.currentTimeMillis()+".html";
		reports = new ExtentReports(ReportPath);
		
		 reports
		 .addSystemInfo("Created By", "Chaitanya Kumar")
         .addSystemInfo("Project", "CognitiveTask")
         .addSystemInfo("User Name", System.getProperty("user.name"))         
         .addSystemInfo("Browser",PropertyManager.getInstance().browser)
         .addSystemInfo("Environment", "Windows")
         .addSystemInfo("Time Zone", System.getProperty("user.timezone"));
		 
		 
		reports.loadConfig(new File(System.getProperty("user.dir")+"/Configurations/extent-config.xml"));
		test = reports.startTest(result.getMethod().getMethodName());
		test.log(LogStatus.INFO, result.getMethod().getMethodName() + "Test is started");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.info(" ########## On Test success ########## " + result.getName());
		Log.info("\n \n");
		test.log(LogStatus.PASS, result.getMethod().getMethodName() + "Test is passed");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		Log.info("\n \n");
		Log.info("----------------------------------------- ");
		Log.info("---------- On Test fails Start ------------ " + result.getName());
		test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "Test is failed");
		String fileName = result.getMethod().getMethodName() + ".png";

		String filePath = SignUpPage.takeScreenshot(fileName);
		String file = test.addScreenCapture(filePath);
		test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "------- test is failed -------", file);
		test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "------- test is failed -------",
				result.getThrowable().getMessage());
		Log.info("---------- On Test fails Ends ----------");
		Log.info("----------------------------------------- ");
		Log.info("\n \n");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.info("=========== On Test skipped ============= " + result.getName());
		test.log(LogStatus.SKIP, result.getMethod().getMethodName() + "test is skipped");

	}

	@Override
	public void onStart(ITestContext context) {
		Log.info("\n \n");
		Log.info("********* Test class starts ********** " + context.getClass());
		Log.info("\n \n");
		reports = new ExtentReports(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss-ms").format(new Date()) + "reports.html");

	}

	@Override
	public void onFinish(ITestContext context) {
		Log.info("===============================================================");
		Log.info("===============================================================");
		Log.info("================= Test finished =============== " + context.getName());
		Log.info("===============================================================");
		Log.info("===============================================================");
		Log.info("\n \n");

		reports.endTest(test);
		reports.flush();

		Log.info("######################## Web Report URL: ########################");
		Log.info(ReportPath);
		Log.info("######################## Web Report URL: ########################");
		Log.info("\n \n");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

}
