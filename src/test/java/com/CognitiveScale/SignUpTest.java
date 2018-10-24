package com.CognitiveScale;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Base.BaseClass;
import com.Base.Log;

import junit.framework.Assert;
import projUtility.SignUpPage;

public class SignUpTest extends BaseClass {

	 //WebDriver driver;
	// Testdata file path
	String filePath = System.getProperty("user.dir") + "/ExcelData/";
	String fileName = "Data1.xlsx";
	String fullPathOfFile = filePath + fileName;
	String sheetName = "Sheet1";
	
	
	
	//@Test
	public void test1() throws InterruptedException{
		
		signupPage.enterUsername("ngfhgfhg");
		Thread.sleep(2000);
		Log.info("------------------------------------ test1");
	}
	
	
	
	@SuppressWarnings("unused")
	@Test(dataProvider="Authentication",priority=0)
	public void createSignUp(String testcaseName,String Username,String Email,String Password,String expectedMsg) throws InterruptedException{
		//System.out.println("--> "+Username+" - "+Email+" - "+Password+" - "+expectedMsg);
		Log.info(testcaseName);
		try {
			
			Thread.sleep(2000);
			signupPage.enterUsername(Username);
			signupPage.enterUserEmail(Email);
			signupPage.enterPassword(Password);
			signupPage.submit();
			
			
			if(expectedMsg!=null || expectedMsg!= ""){
				boolean condition = signupPage.verifyErrorMsg(expectedMsg);
				Assert.assertTrue(condition);
			}else{
				Log.info(" No error message to verify from excel sheet");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	@Test(priority=1)
	public void validSignup(){
		signupPage.continuePage();
		signupPage.skipStep();
	}

	@DataProvider

	public Object[][] Authentication() throws Exception {

		Object[][] testObjArray	= BaseClass.getTableArray(fullPathOfFile, sheetName);
		
		


		return (testObjArray);

	}

}
