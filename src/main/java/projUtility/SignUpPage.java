package projUtility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Base.BaseClass;
import com.Base.Log;

public class SignUpPage {

	private static WebDriver driver;

	public SignUpPage(WebDriver driver) {

		SignUpPage.driver = driver;

		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "user_login")
	WebElement username;

	@FindBy(id = "user_email")
	WebElement userEmail;

	@FindBy(id = "user_password")
	WebElement password;

	@FindBy(id = "signup_button")
	WebElement submit;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement continuePage;
	
	@FindBy(linkText="skip this step")
	WebElement skipTheStep;
	
	@FindBy(linkText="Start a project")
	WebElement successAcctMsg;

	public void enterUsername(String user) {
		Log.info("Enter username as: "+user);
		username.clear();
		username.sendKeys(user, Keys.TAB);
	}

	public void enterUserEmail(String email) {
		Log.info("Enter Email as: "+email);
		userEmail.clear();
		userEmail.sendKeys(email, Keys.TAB);
	}

	public void enterPassword(String pwd) {
		Log.info("Enter Password as: "+pwd);
		password.clear();
		password.sendKeys(pwd, Keys.TAB);
	}

	public void submit() {
		if (submit.isEnabled()) {
			submit.click();
		}else{
			Log.warn("**------** Submit button is not enabled to click **------**");
		}
	}
	
	
	public void continuePage(){
		continuePage.click();
	}
	
	public void skipStep() {
		
		skipTheStep.click();
	}

	public boolean verifyErrorMsg(String expectedMsg) {
		boolean msg = false;

		Log.info("Verify error message as: "+expectedMsg);
		try{
			msg = driver.findElement(By.xpath("//dd[contains(text(),'" + expectedMsg + "')]")).isDisplayed();
		}catch(ElementNotVisibleException e){
			Log.warn(e.getMessage());
		}
		return msg;
		
	}
	
	public boolean verifySignUp(){
		boolean status = false; 
		try{
		status= successAcctMsg.isDisplayed();
		}catch(ElementNotVisibleException e){
			Log.warn(e.getMessage());
		}
		
		return status;
	}

	public static String takeScreenshot(String screenshotName) {
		
		String fullPath = null;
		if (driver != null) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);

			try {
				fullPath = BaseClass.projFolPath + "/Snapshots/" + screenshotName;
				FileUtils.copyFile(src, new File(fullPath));
			} catch (IOException e) {
				Log.fatal("------------ Faild to copy screenshot to dest ---------- ");
				e.printStackTrace();
			}

		}

		return fullPath;

	}

}
