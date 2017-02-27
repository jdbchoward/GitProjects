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
* @Description:  JP zip code validation - negative.
* @author: Howard
* @compay: Kit and Ace     
* @date 3/3/2017 
* @version V1.0   
*/



public class FW1884 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1884.class.getName());
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
	public void AUandNZzipCodeValidation() throws Exception {

		init();
	    uitestOperation.buyManTshirtsWithAnonymousUser(); 
	    
	    common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);		
		//change country to AU	
		 uitestOperation.changeCountryToWhenCheckOut(1);	 
		 
		 
		 //UK is chosen. Examples of valid format: 999-9999 OR 999 9999	
		 
//		 Enter non numeric data in all 2 correct formats and trigger validation	Validation failed.
		 verifyPostCode("AAA-BBBB");
		 verifyPostCode("AAA BBBB");
//		 Enter special symbols in all 2 correct formats and trigger validation	Validation failed.
		 verifyPostCode("9$9-9999");
		 verifyPostCode("999 99#9");
//		 Enter mix with special symbols / digits and characters in all 2 correct formats and trigger validation	Validation failed.
		 verifyPostCode("9/9-9999");
		 verifyPostCode("9/9 9999");
//		 Enter 4 digits with space before the 4-th number and trigger validation	Validation failed.
		 verifyPostCode("9997 999");
//		 Enter 4 digits without space before the 4-th number and trigger validation	Validation failed.
		 verifyPostCode("9999999");
//		 Enter 4 digits with hyphen before the 4-th number and trigger validation	Validation failed.
		 verifyPostCode("9999-999");
//		 Enter 4 digits with hyphens (4) before the 4-th number and trigger validation	Validation failed.
		 verifyPostCode("9999-9999");
//		 Enter 4 digits with spaces (4) before the 4-th number and trigger validation	Validation failed.
		 verifyPostCode("9999 999");
//		 Enter only hyphens and spaces and trigger validation	Validation failed.
		 verifyPostCode("----   ");
//		 Enter mix of hyphens/spaces and digits and trigger validation	Validation failed.
		 verifyPostCode("99 9/9999");
//		 Enter 7 digits without space/hyphen before the 4-th digit and trigger validation	Validation failed.
		 verifyPostCode("9999999");
//		 Enter 7 digits with space/hyphen that is placed in incorrect position and trigger validation	Validation failed.
		 verifyPostCode("9 99-9999");
//		 Enter 8 digits and trigger validation	Validation failed.
		 verifyPostCode("999-99999");
//		 Enter multi-byte characters and trigger validation	Validation failed.
		 verifyPostCode("999-99邮编");
		 
		 

		 
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
