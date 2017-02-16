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
* @Description:  US zip code validation - negative.
* @author: Howard
* @compay: Kit and Ace     
* @date 2/23/2017 
* @version V1.0   
*/



public class FW1879 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1879.class.getName());
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
	public void zipMaxLenthValidation() throws Exception {

		init();
	    uitestOperation.buyManTshirtsWithAnonymousUser(); 
	    
	    common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);		
		//change country to US	
		 uitestOperation.changeCountryToWhenCheckOut(4);	 
		 
		 
		 //41233;41233-4456;41233 4456
//		 Enter non numeric data and trigger validation	Validation failed.
		 verifyPostCode("abcde");
//		 Enter special symbols and trigger validation	Validation failed. 
		 verifyPostCode("#$%^!");
//		 Enter mix with special symbols / digits and characters and trigger validation	Validation failed. 
		 verifyPostCode("#$123");
//		 Enter 4 digits and trigger validation	Validation failed. 
		 verifyPostCode("4123");
//		 Enter 6 digits with space before the 6-th digit and trigger validation	Validation failed.
		 verifyPostCode("41233 5");
//		 Enter 6 digits without space before the 6-th digit and trigger validation	Validation failed.
		 verifyPostCode("412335");
//		 Enter 6 digits with hyphen before the 6-th digit and trigger validation	Validation failed. 
		 verifyPostCode("41233-5");
//		 Enter only hyphens and spaces and trigger validation	Validation failed. 
		 verifyPostCode("--  ");
//		 Enter mix of hyphens/spaces and digits and trigger validation	Validation failed. 
		 verifyPostCode("--  23");
//		 Enter 9 digits without space/hyphen before the 6-th digit and trigger validation	Validation failed.
		 verifyPostCode("412334123");
//		 Enter 9 digits with space/hyphen that is placed in incorrect position and trigger validation	Validation failed. 
		 verifyPostCode("412-334123");
//		 Enter 10 digits and trigger validation	Validation failed. 
		 verifyPostCode("4123341233");
//		 Enter multi-byte characters and trigger validation	Validation failed. 
		 verifyPostCode("邮政");
		 
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
