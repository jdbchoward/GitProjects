package TestSuite.CheckoutSuite;



import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import POJO.BillingInfo;
import POJO.BuyingItem;
import POJO.UserInfo;
import PageObjects.BrowserLoader;
import PageObjects.BrowserStackLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.HACTestOperations;
import PageObjects.HMCTestOperations;
import PageObjects.InitWebDriver;
import PageObjects.UITestOperations;
import PageObjects.VerifyTearDownOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation TestSuite 
* @Package CheckoutSuite 
* @Description:  Anonymous user. Not registered on confirmation page. Test Buying Item From XML
* @author: Howard
* @compay: Kit and Ace     
* @date 05/12/2017
* @version V1.0   
*/



public class TestBuyingItemFromXML {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	HACTestOperations hacTestOperations;
	static Logger log = Logger.getLogger(TestBuyingItemFromXML.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris,userHMC;
	public BillingInfo billing;
	public VerifyTearDownOperations verifyTearDownOperations;
	String orderNumber;

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);		
	}
	
	
	private void init()
	{
		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver=initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);
		hmcTestOperation = PageFactory.initElements(driver, HMCTestOperations.class);
		hacTestOperations=PageFactory.initElements(driver, HACTestOperations.class);
		userHybris=uitestOperation.users.get(1);
		userHMC=uitestOperation.users.get(2);
		billing=uitestOperation.billings.get(0);
	}
	
	
	private void initVerifyTearDown()
	{
		verifyTearDownOperations= PageFactory.initElements(driver, VerifyTearDownOperations.class);
		verifyDriver=verifyTearDownOperations.driver;
		verifyWait=verifyTearDownOperations.wait;
	}

	@Test
	public void anonymousCheckOut() throws Exception {

		init();		
		//place order from Hybris system
//		buy all the items from xml file
		for(BuyingItem item:uitestOperation.buyingItem)
		{
			//buying 
			for(int i=0;i<item.getBuyingQty();i++)
			{
			uitestOperation.buyItemsFromXML(item);
			}
		}
	    
	    uitestOperation.AnonymousCheckOut(userHybris,billing);	    
	    orderNumber=uitestOperation.getOrderNumber();
	}
	




	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		
//		 initVerifyTearDown();
//		 // login to HAC system. prepare to delete test date
//	    hacTestOperations.doLogOnSite(userHMC,verifyDriver);
//	    //clean order
//	    hacTestOperations.cleanOrder(orderNumber,verifyDriver);
//	    //clean user
//	    hacTestOperations.cleanUser(userHybris,verifyDriver);
//	    
//		driver.close();
//		driver.quit();
//		verifyDriver.close();
//		verifyDriver.quit();
	}

}
