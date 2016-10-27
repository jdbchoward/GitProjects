package Cscockpit;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PageObjects.CommonActions;
import PageObjects.CscockpitTestOperations;
import PageObjects.ElementsRepositoryAction;
import PageObjects.InitWebDriver;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation Test for CustomerService system 
* @Package WCMS 
* @Description: Navigation to Main page 
* @author: Howard
* @compay: PQA     
* @date 10/27/2016 
* @version V1.0   
*/



public class TestNavToCustomerServiceHomePage {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	CscockpitTestOperations cscockpitTestOperations;
	static Logger log = Logger.getLogger(TestNavToCustomerServiceHomePage.class.getName());
	public InitWebDriver initWebDriver;
	
	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver=initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		cscockpitTestOperations = PageFactory.initElements(driver, CscockpitTestOperations.class);

	}

	@Test
	public void testSignInWithCorrectInfo() throws Exception {

		cscockpitTestOperations.doLogOnSite("howard.zhang@kitandace.com", "Integrity101");
	    wait.threadWait(3000);	    
	    Assert.assertTrue(cscockpitTestOperations.checkSignInStatus());	    
	}
	
	@Test(dependsOnMethods = { "testSignInWithCorrectInfo" })
	public void testSearchCustomer()
	{
		cscockpitTestOperations.searchCustomer("howard");
		Assert.assertTrue(cscockpitTestOperations.verifyCustomerSearch());
	}
	


	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
