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
* @Description:   Zip code validation JP anonimous user - positive
* @author: Howard
* @compay: Kit and Ace     
* @date 2/10/2017 
* @version V1.0   
*/



public class FW1834 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1834.class.getName());
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
		
		userHybris=uitestOperation.users.get(5);
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
	public void zipValidationJPanonymousCheckOut() throws Exception {

		init();
	    uitestOperation.buyManTshirtsWithAnonymousUser(); 
	    
	    common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Checkout')]"));
		common.javascriptClick(driver, btnCheckOut);		
		
		
		//change country
		//Tokyo  [Fill in correct zip code in 7 digit format with hyphen]
		changeCountryVerifyZip(userHybris.getZip(),41,false);
		//[Fill in correct zip code in 7 digit format with space]  
		changeCountryVerifyZip("999 9999",34,true);
	}
	


   private void changeCountryVerifyZip(String zipCode,int state,boolean saChecked)
   {
		//change country
	    if(!saChecked)	    
		uitestOperation.changeCountryToJapanWhenCheckOut();
		driver.findElement(By.id("checkout-zip-code")).clear();
		driver.findElement(By.id("checkout-zip-code")).sendKeys(zipCode);
		driver.findElement(By.id("checkout-phone-number")).clear();
	  
	    
	    
		//change state
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
		Select selectProvince = new Select(driver.findElement(By.id("checkout-region-select")));
		selectProvince.selectByIndex(state);
	    
		
	   //verify SA
		if(!saChecked)
	    common.javascriptClick(driver,driver.findElement(By.id("checkout-address-same-as-shipping")));
	    wait.threadWait(1000);
	    driver.findElement(By.id("checkout-billing-zip-code")).clear();
		driver.findElement(By.id("checkout-billing-zip-code")).sendKeys(zipCode);
		driver.findElement(By.id("checkout-billing-city")).clear();
   }

   
   
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {	
		driver.close();
		driver.quit();
	}

}
