package TestSuite.CheckoutSuite;



import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import POJO.BillingInfo;
import POJO.UserInfo;
import PageObjects.BrowserLoader;
import PageObjects.BrowserStackLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.HMCTestOperations;
import PageObjects.InitWebDriver;
import PageObjects.UITestOperations;
import PageObjects.VerifyTearDownOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation TestSuite 
* @Package CheckoutSuite 
* @Description:  CA zip code validation - negative.
* @author: Howard
* @compay: Kit and Ace     
* @date 3/1/2017 
* @version V1.0   
*/



public class FW1882 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1882.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris,userHMC;
	public BillingInfo billing;
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
		
		userHybris=uitestOperation.users.get(3);
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
	public void CAzipCodeValidation() throws Exception {

		init();
	    uitestOperation.buyManTshirtsWithAnonymousUser(); 
	    
	    common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);		
//		//change country to US	
//		 uitestOperation.changeCountryToWhenCheckOut(5);	 
		 
		 
		 //Canada is chosen. Examples of valid format: A9A 9A9;	H8Y 1L3; t8n5k6; V6k4V5t;	
		
//		Enter non alfa-numeric data in zip code and trigger validation	Validation failed. 
		verifyPostCode("HSY ALR");
//		Enter 7 alpha-numeric characters (without space) and trigger validation	Validation failed. 
		verifyPostCode("1231233");
//		Enter alpha-numeric characters in the following alternate number, number, letter, number, letter, number and trigger validation	Validation failed. 
		verifyPostCode("98Y1L3");
//		Enter alpha-numeric characters in the following alternate letter, letter, letter, number, letter, number and trigger validation	Validation failed.
		verifyPostCode("HUY1L3");
//		Enter alpha-numeric characters in the following alternate letter, number, number, number, letter, number and trigger validation	Validation failed.
		verifyPostCode("H891L3");
//		Enter alpha-numeric characters in the following alternate letter, number, letter, letter, letter, number and trigger validation	Validation failed.
		verifyPostCode("H8YML3");
//		Enter alpha-numeric characters in the following alternate letter, number, letter, number, number, number and trigger validation	Validation failed.
		verifyPostCode("H8Y193");
//		Enter alpha-numeric characters in the following alternate letter, number, letter, number, letter, letter and trigger validation	Validation failed.
		verifyPostCode("H8Y1LK");
//		Enter alpha-numeric characters in the following alternate number, number, letter, space, number, letter, number and trigger validation	Validation failed.
		verifyPostCode("98Y 1L3");
//		Enter alpha-numeric characters in the following alternate letter, letter, letter, space, number, letter, number and trigger validation	Validation failed. 
		verifyPostCode("HTY 1L3");
//		Enter alpha-numeric characters in the following alternate letter, number, number, space, number, letter, number and trigger validation	Validation failed.
		verifyPostCode("H89 1L3");
//		Enter alpha-numeric characters in the following alternate letter, number, letter, space, letter, letter, number and trigger validation	Validation failed.
		verifyPostCode("H8Y ML3");
//		Enter alpha-numeric characters in the following alternate letter, number, letter, space, number, number, number and trigger validation	Validation failed.
		verifyPostCode("H8Y 193");
//		Enter alpha-numeric characters in the following alternate letter, number, letter, space, number, letter, letter and trigger validation	Validation failed.
		verifyPostCode("H8Y 1LM");
//		Enter 6 letters and numbers (using valid alternate) with separator differ from space (/,.; etc.) and trigger validation	Validation failed.
		verifyPostCode("H8Y/1L3");
//		Enter 6 letters and numbers (using valid alternate) with space in incorrect position and trigger validation	Validation failed.
		verifyPostCode("H 8Y1L3");
//		Enter mutibyte characters in zip code and trigger validation	Validation failed.
		verifyPostCode("H8Y 邮编");
		
		 
	}
	

   
   private void verifyPostCode(String invailPostCode)
   {   
	   driver.findElement(By.id("checkout-zip-code")).clear();
	   driver.findElement(By.id("checkout-zip-code")).sendKeys(invailPostCode);
	   driver.findElement(By.id("checkout-phone-number")).clear();
	   wait.threadWait(1500);
   }
   
   
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {	
		driver.close();
		driver.quit();
	}

}
