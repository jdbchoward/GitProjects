package TestSuite.CheckoutSuite;



import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import POJO.BillingInfo;
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
* @Description:  Enter shipping address for guest user with no account - Positive
* @author: Howard
* @compay: Kit and Ace     
* @date 1/2/2016 
* @version V1.0   
*/



public class FW1216 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	HACTestOperations hacTestOperations;
	static Logger log = Logger.getLogger(FW1216.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris,userHMC;
	public BillingInfo billing;
	String orderNumber;
	public VerifyTearDownOperations verifyTearDownOperations;

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
	public void checkEnterAddressVerification() throws Exception {

		init();
		
		
	    uitestOperation.buyManTshirtsWithAnonymousUser();
	    
	    //check out with no address entered
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);
		driver.findElement(By.xpath("//button[contains(text(),' Place my order')]")).click();
		
		wait.threadWait(5000);	    
	    
	    uitestOperation.AnonymousCheckOutAfterVerifyFields(userHybris,billing);	 
	    driver.findElement(By.xpath("//button[contains(text(),' Place my order')]")).click();
	    
	    orderNumber=uitestOperation.getOrderNumber();	    	    
	    
	}
	




	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		
		//verify order in HMC system.
	    initVerifyTearDown();
		hmcTestOperation.doLogOnSite(userHMC,verifyDriver);
		verifyWait.threadWait(3000);
	    //expand tree
	    verifyDriver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[order]_treeicon")).click();
	    verifyWait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Order]_label"));
	    verifyDriver.findElement(By.id("Tree/GenericLeafNode[Order]_label")).click();	  
	    verifyDriver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Order.code]]_input")).sendKeys(orderNumber);	   
	    verifyDriver.findElement(By.id("Content/DateEditor[in Content/GenericCondition[Order.date]]_date")).sendKeys(common.getTodayDate());
	    
	    //verify order 
	    verifyDriver.findElement(By.id("Content/OrganizerSearch[Order]_searchbutton")).click();
	    Assert.assertTrue(hmcTestOperation.searchFromTable("Content/ClassificationOrganizerList[Order]_innertable",4, orderNumber,null,verifyDriver));

		
	 // login to HAC system. prepare to delete test date
	    hacTestOperations.doLogOnSite(userHMC,verifyDriver);
	    //clean order
	    hacTestOperations.cleanOrder(orderNumber,verifyDriver);
	    //clean user
	    hacTestOperations.cleanUser(userHybris,verifyDriver);
//		
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
