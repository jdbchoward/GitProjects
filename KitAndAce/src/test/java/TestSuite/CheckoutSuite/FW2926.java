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
import PageObjects.HMCTestOperations;
import PageObjects.InitWebDriver;
import PageObjects.UITestOperations;
import PageObjects.VerifyTearDownOperations;
import PageObjects.Wait;
import junit.framework.Assert;

/**
 * @Title: Automation TestSuite
 * @Package CheckoutSuite
 * @Description: Allow guests to select Gift Option at checkout.
 * @author: Howard
 * @compay: Kit and Ace
 * @date 2/8/2016
 * @version V1.0
 */

public class FW2926 {
	private WebDriver driver, verifyDriver;
	private Wait wait, verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW2926.class.getName());
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

		userHybris = uitestOperation.users.get(1);
		userHMC = uitestOperation.users.get(2);
		billing = uitestOperation.billings.get(0);
	}



	@Test
	public void testAllowGuestGiftCheckOut() throws Exception {

		init();
		uitestOperation.buyManTshirtsWithAnonymousUser();
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Checkout')]"));
		common.javascriptClick(driver, btnCheckOut);
		uitestOperation.AnonymousCheckOutAfterVerifyFields(userHybris, billing);
		
		common.javascriptScrollPage(driver, -500);
		wait.threadWait(3000);
		driver.findElement(By.cssSelector("div.checkbox__circle.js-gift-option-checkbox")).click();
		wait.threadWait(3000);
		driver.findElement(By.cssSelector("div.checkbox__circle.js-gift-option-checkbox")).click();

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {

		driver.close();
		driver.quit();
	}

}
