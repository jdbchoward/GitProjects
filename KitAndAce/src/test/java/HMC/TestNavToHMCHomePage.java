package HMC;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import POJO.UserInfo;
import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.HMCTestOperations;
import PageObjects.InitWebDriver;
import PageObjects.UITestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation Test for HMC system 
* @Package HMC 
* @Description: Navigation to Main page 
* @author: Howard
* @compay: PQA     
* @date 10/12/2016 
* @version V1.0   
*/



public class TestNavToHMCHomePage {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(TestNavToHMCHomePage.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris,userHMC;

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver=initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);
		hmcTestOperation = PageFactory.initElements(driver, HMCTestOperations.class);
		userHybris=uitestOperation.users.get(0);
		userHMC=uitestOperation.users.get(2);

	}

	@Test
	public void testSignInWithCorrectInfo() throws Exception {

		hmcTestOperation.doLogOnSite(userHMC,driver);
	    wait.threadWait(3000);
	    Assert.assertTrue(hmcTestOperation.checkSignInStatus());
	}

//	@Test(dependsOnMethods = { "testSignInWithCorrectInfo" })
//	public void testSignInWithBadInfo() throws Exception {
//
//		hmcTestOperation.doLogOnSite("aa", "111");
//		Assert.assertTrue(!hmcTestOperation.checkSignInStatus());
//	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
