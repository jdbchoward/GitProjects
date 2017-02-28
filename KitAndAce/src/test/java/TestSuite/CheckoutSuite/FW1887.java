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
* @Description:  UK zip code validation - positive.
* @author: Howard
* @compay: Kit and Ace     
* @date 3/6/2017 
* @version V1.0   
*/



public class FW1887 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1887.class.getName());
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
		
		userHybris=uitestOperation.users.get(7);
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
	public void UKzipCodeValidation() throws Exception {

		init();
	    uitestOperation.buyManTshirtsWithAnonymousUser(); 
	    
	    common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);		
		//change country to UK	
		 uitestOperation.changeCountryToWhenCheckOut(5);	 
		 
		 
		 driver.findElement(By.id("checkout-address-1")).clear();
			driver.findElement(By.id("checkout-address-1")).sendKeys(userHybris.getAddress());
			driver.findElement(By.id("checkout-city")).clear();
			driver.findElement(By.id("checkout-city")).sendKeys(userHybris.getCity());
		 //UK is chosen. Examples of valid format: ST16 3QH	
		 
		 
//		 Fill in correct zip code in 5 characters format (first letter and last letter)	Correct data is entered
		 verifyPostCode(userHybris.getZip());
//		 Fill in correct zip code in 5 characters format with space (first letter and last letter)	Correct data is entered
		 verifyPostCode("ST16 H");
//		 Fill in correct zip code in 6 characters format (first letter and last letter)	Correct data is entered
		 verifyPostCode("ST163Q");
//		 Fill in correct zip code in 6 characters format with space (first letter and last letter)	Correct data is entered
		 verifyPostCode("ST16 3Q");
//		 Fill in correct zip code in 7 characters format (first letter and last letter)	Correct data is entered
		 verifyPostCode("ST163QH");
//		 Fill in correct zip code in 7 characters/digits format with space (first letter and last letter)	Correct data is entered
		 verifyPostCode("ST16 3QH");
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
