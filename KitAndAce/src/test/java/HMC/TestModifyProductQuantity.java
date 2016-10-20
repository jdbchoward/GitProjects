package HMC;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.HMCTestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation Test for HMC system 
* @Package HMC 
* @Description:  Modify Product Quantity 
* @author: Howard
* @compay: PQA     
* @date 10/20/2016 
* @version V1.0   
*/



public class TestModifyProductQuantity {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(TestCheckOrder.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		hmcTestOperation = PageFactory.initElements(driver, HMCTestOperations.class);

	}

	@Test
	public void testModifyProductQuantity() throws Exception {

		hmcTestOperation.doLogOnSite("howard.zhang@kitandace.com", "Integrity101");
	    wait.threadWait(3000);
	   
	    //this test case is to modify item id:91215
	    hmcTestOperation.naviToProduct("91215","1001");
	    Assert.assertTrue(hmcTestOperation.verifyProductQuanlityFromTable());

	}



	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
