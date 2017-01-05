package TestSuite.CheckoutSuite;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import POJO.BillingInfo;
import POJO.UserInfo;
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
 * @Description:  Login user on Checkout page.
 * @author: Howard
 * @compay: Kit and Ace
 * @date 1/17/2017
 * @version V1.0
 */

public class FW1237 {
	private WebDriver driver, verifyDriver;
	private Wait wait, verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	public VerifyTearDownOperations verifyTearDownOperations;
	static Logger log = Logger.getLogger(FW1237.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris, userHMC;
	public BillingInfo billing;
	String orderNumber;
	// String orderNumber="00253014";

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

	private void initVerifyTearDown() {
		verifyTearDownOperations = PageFactory.initElements(driver, VerifyTearDownOperations.class);
		verifyDriver = verifyTearDownOperations.driver;
		verifyWait = verifyTearDownOperations.wait;
	}

	@Test
	public void testLoginOnCheckoutPage() throws Exception {
		init();
		//buy item
		uitestOperation.buyManTshirtsWithAnonymousUser();
		
		// checkout
		verifyEmailWhenAnonymousCheckOut(userHybris,billing);

	}
	
	
	
	public void verifyEmailWhenAnonymousCheckOut(UserInfo user, BillingInfo billing) {
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.waitElementToBeDisplayed(By.xpath("//button[contains(text(),'Checkout')]"));
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Checkout')]"));
		common.javascriptClick(driver, btnCheckOut);
		//Fill in E-Mail Field with data in incorrect format (no "@" character, no ending, no characters before the "@" character etc.)
		driver.findElement(By.id("checkout-email")).sendKeys("aaaaa.com");
		wait.threadWait(1000);
		
		//Fill in E-Mail Field with correct format using multibyte symbols
		driver.findElement(By.id("checkout-email")).clear();
		driver.findElement(By.id("checkout-email")).sendKeys("aa@bb@.com");
		wait.threadWait(1000);
		
		
		//Fill in E-Mail Field with correct format using multibyte symbols
		driver.findElement(By.id("checkout-email")).clear();
		driver.findElement(By.id("checkout-email")).sendKeys("aa@bb.com");
		wait.threadWait(1000);
		
		
		

	}
	

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {

		driver.close();
		driver.quit();
	}

}
