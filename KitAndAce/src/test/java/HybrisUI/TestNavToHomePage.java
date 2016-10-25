package HybrisUI;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.InitWebDriver;
import PageObjects.TestOperations;
import PageObjects.UITestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation Test for Hybris system 
* @Package HybrisUI 
* @Description: Navigation to Main page 
* @author: Howard
* @compay: PQA     
* @date 10/12/2016 
* @version V1.0   
*/



public class TestNavToHomePage {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	static Logger log = Logger.getLogger(TestNavToHomePage.class.getName());
	public InitWebDriver initWebDriver;

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver=initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);

	}

	@Test
	public void testSignInWithCorrectInfo() throws Exception {

		uitestOperation.doSignSite("howard.zhang.kitandace@outlook.com", "10011001");
	    wait.threadWait(3000);
	    Assert.assertTrue(uitestOperation.checkSignInStatus());
		uitestOperation.doSignOut();
	}

	@Test(dependsOnMethods = { "testSignInWithCorrectInfo" })
	public void testSignInWithBadInfo() throws Exception {

		uitestOperation.doSignSite("aa", "111");
		Assert.assertTrue(!uitestOperation.checkSignInStatus());
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
