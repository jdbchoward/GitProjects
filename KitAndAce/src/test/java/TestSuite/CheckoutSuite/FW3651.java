package TestSuite.CheckoutSuite;



import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import POJO.BillingInfo;
import POJO.GiftCard;
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
* @Description:  Checkout with only Gift Card - negative. 
* @author: Howard
* @compay: Kit and Ace     
* @date 5/8/2017 
* @version V1.0   
*/



public class FW3651 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	HACTestOperations hacTestOperations;
	static Logger log = Logger.getLogger(FW3651.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris,userHMC;
	public BillingInfo billing;
	public GiftCard giftCard;
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
		giftCard=uitestOperation.giftCards.get(0);
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
//		String orderNumber="00253014";
		
		//place order from Hybris system
	    uitestOperation.buyManTshirtsWithAnonymousUser();
	    uitestOperation.AnonymousGiftCardCheckOut(userHybris,billing,giftCard);	    
	    orderNumber=uitestOperation.getOrderNumber();	    
	
	}
	




	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		
		initVerifyTearDown();
		 // login to HAC system. prepare to delete test date
	    hacTestOperations.doLogOnSite(userHMC,verifyDriver);
	    //clean order
	    hacTestOperations.cleanOrder(orderNumber,verifyDriver);
	    //clean user
	    hacTestOperations.cleanUser(userHybris,verifyDriver);
	    
	    
	    
//		//delete order so that we can delete User later
//		verifyDriver.findElement(By.xpath("//table[@id='Content/ClassificationOrganizerList[Order]_innertable']/tbody/tr[2]/td[4]/div/div")).click();
//		verifyDriver.findElement(By.id("Content/ClassificationOrganizerList[Order][delete]_img")).click();
//		verifyDriver.switchTo().alert().accept();
//		verifyWait.threadWait(1000);
//		//Navigate to User and Delete that TEST USER
//		verifyDriver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[user]_label")).click();
//		verifyWait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Customer]_label"));
//	    verifyDriver.findElement(By.id("Tree/GenericLeafNode[Customer]_label")).click();	  
//	    verifyDriver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Customer.name]]_input")).sendKeys("howard");	 
//	    verifyDriver.findElement(By.id("Content/OrganizerSearch[Customer]_searchbutton")).click();
//	    verifyDriver.findElement(By.xpath("//div[contains(text(),'Howard Anonymous')]")).click();
//	    verifyDriver.findElement(By.id("Content/ClassificationOrganizerList[Customer][delete]_img")).click();
//	    verifyDriver.switchTo().alert().accept();
		driver.close();
		driver.quit();
		verifyDriver.close();
		verifyDriver.quit();
	}

}
