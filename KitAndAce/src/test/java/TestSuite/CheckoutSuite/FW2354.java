package TestSuite.CheckoutSuite;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
 * @Description: Tax Calculation - trigger tax calculation for Anonymous user.
 * @author: Howard
 * @compay: Kit and Ace
 * @date 1/24/2016
 * @version V1.0
 */

public class FW2354 {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW2354.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris, userHMC;
	public BillingInfo billing;
	public VerifyTearDownOperations verifyTearDownOperations;

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
	}

	private void init() {
		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver = initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);
		hmcTestOperation = PageFactory.initElements(driver, HMCTestOperations.class);

		userHybris = uitestOperation.users.get(3);
		userHMC = uitestOperation.users.get(5);
		billing = uitestOperation.billings.get(0);

	}

	@Test
	public void testTaxEstimationAnonymousUser() throws Exception {

		init();
		String orderNumber;

		uitestOperation.buyUSManTshirtsWithAnonymousUser();
		uitestOperation.AnonymousCheckOutAndVerifyInfo(userHybris, billing);
		// make selector option visiable so that we can select
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
		Select provence = new Select(driver.findElement(By.id("checkout-region-select")));
		provence.selectByIndex(8);

		// change country
		uitestOperation.changeCountryToJapanWhenCheckOut();

		common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
		Select selectProvince = new Select(driver.findElement(By.id("checkout-region-select")));
		selectProvince.selectByIndex(41);

		uitestOperation.verifyCheckOutPageWhenNOTLogined(userHMC, billing);
		
		//chang back to US
		uitestOperation.changeCountryToUSAWhenCheckOut();
		wait.threadWait(1000);
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
		Select selectState = new Select(driver.findElement(By.id("checkout-region-select")));
		selectState.selectByIndex(8);

		uitestOperation.verifyCheckOutPageWhenNOTLogined(userHybris, billing);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {

		 driver.close();
		 driver.quit();

	}

}
