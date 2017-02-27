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
* @Description:  AU and NZ zip code validation - negative.
* @author: Howard
* @compay: Kit and Ace     
* @date 3/2/2017 
* @version V1.0   
*/



public class FW1883 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1883.class.getName());
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
		 uitestOperation.changeCountryToWhenCheckOut(0);	 
		 
		 
		 //UK is chosen. Examples of valid format: 9999	
		 
//		 Enter non alfa-numeric data in zip code and trigger validation	Validation failed.
		 verifyPostCode("ABCD");
//		 Enter characters in zip code and trigger validation	Validation failed.
		 verifyPostCode("9B99");
//		 Enter mix of digits and characters in zip code and trigger validation	Validation failed.
		 verifyPostCode("N9A9");
//		 Enter digits with space/hyphen in zip code and trigger validation	Validation failed.
		 verifyPostCode("99-99");
//		 Enter digits with dots/commas in zip code and trigger validation	Validation failed.
		 verifyPostCode("99,99");
//		 Enter mutibyte characters in zip code and trigger validation	Validation failed.
		 verifyPostCode("99邮政");
		 
		 
		 
//		 Repeat steps above for NZ country	Results are the same
			//change country to AU	
		 uitestOperation.changeCountryToWhenCheckOut(3);
//		 Enter non alfa-numeric data in zip code and trigger validation	Validation failed.
		 verifyPostCode("ABCD");
//		 Enter characters in zip code and trigger validation	Validation failed.
		 verifyPostCode("9B99");
//		 Enter mix of digits and characters in zip code and trigger validation	Validation failed.
		 verifyPostCode("N9A9");
//		 Enter digits with space/hyphen in zip code and trigger validation	Validation failed.
		 verifyPostCode("99-99");
//		 Enter digits with dots/commas in zip code and trigger validation	Validation failed.
		 verifyPostCode("99,99");
//		 Enter mutibyte characters in zip code and trigger validation	Validation failed.
		 verifyPostCode("99邮政");

		 
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
