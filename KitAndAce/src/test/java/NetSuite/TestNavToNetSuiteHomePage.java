package NetSuite;

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
import PageObjects.NetSuiteTestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation Test for NetSuite 
* @Package NetSuite 
* @Description: Navigation to Main page 
* @author: Howard
* @compay: PQA     
* @date 10/13/2016 
* @version V1.0   
*/



public class TestNavToNetSuiteHomePage {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	NetSuiteTestOperations netSuiteTestOperations;
	static Logger log = Logger.getLogger(TestNavToNetSuiteHomePage.class.getName());
	public InitWebDriver initWebDriver;
	
	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver=initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		netSuiteTestOperations = PageFactory.initElements(driver, NetSuiteTestOperations.class);

	}

	@Test
	public void testSignInWithCorrectInfo() throws Exception {

		netSuiteTestOperations.doLogOnSite("howard.zhang@kitandace.com", "Welcome!10011001");
	    wait.threadWait(3000);
	    Assert.assertTrue(netSuiteTestOperations.checkSignInStatus());
	}


	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
