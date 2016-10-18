package TestSuite;

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
import PageObjects.TestOperations;
import PageObjects.UITestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation TestSuite 
* @Package TestSuite 
* @Description: Make an order and then check on the HMC system. 
* @author: Howard
* @compay: Kit and Ace     
* @date 10/18/2016 
* @version V1.0   
*/



public class TestMakeOrder {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	static Logger log = Logger.getLogger(TestMakeOrder.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);

	}

	@Test
	public void testSignInWithCorrectInfo() throws Exception {

		uitestOperation.doSignSite("howard.zhang.kitandace@outlook.com", "10011001");
	    wait.threadWait(3000);
	    uitestOperation.naviToManTShirts();
	    uitestOperation.buy2ManTshirts();
	    uitestOperation.checkOut();
	    

	}



	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
//		driver.close();
//		driver.quit();
	}

}
