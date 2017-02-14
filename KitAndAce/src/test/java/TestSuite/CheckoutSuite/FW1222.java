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
* @Description:  Change country in SA during checkout
* @author: Howard
* @compay: Kit and Ace     
* @date 1/13/2016 
* @version V1.0   
*/



public class FW1222 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1222.class.getName());
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
	public void checkChangeCountry() throws Exception {

		init();
		
	    uitestOperation.buyManTshirtsWithAnonymousUser();
	    
	    //check out with no address entered
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);
		
		//verify is prselected canada
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-country-select");
		Select selectCC = new Select(driver.findElement(By.id("checkout-country-select")));
		WebElement option = selectCC.getFirstSelectedOption();
		log.debug("selected contry is: "+option.getText());		
		Assert.assertTrue(option.getText().contains("Canada - CAD"));
		//change country and check currecy 
		uitestOperation.changeCountryToJapanWhenCheckOut(); 
		//verify province selected
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-country-select");
		selectCC = new Select(driver.findElement(By.id("checkout-country-select")));
		option = selectCC.getFirstSelectedOption();
		log.debug("selected contry is changed to : "+option.getText());
		Assert.assertTrue(option.getText().contains("Japan - JPY"));
		
		//check province after country selected change
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
		Select selectProvince = new Select(driver.findElement(By.id("checkout-region-select")));
		selectProvince.selectByIndex(1);
		WebElement optionProvince = selectProvince.getFirstSelectedOption();
		log.debug("selected contry province: "+optionProvince.getText());	
		Assert.assertTrue(optionProvince.getText().contains("Aichi"));

	}
	




	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		
		driver.close();
		driver.quit();
	}

}
