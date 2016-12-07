package TestSuite.CheckoutSuite;

import java.util.List;

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
 * @Description: Edit order. Positive
 * @author: Howard
 * @compay: Kit and Ace
 * @date 12/16/2016
 * @version V1.0
 */

public class FW1240 {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	public VerifyTearDownOperations verifyTearDownOperations;
	static Logger log = Logger.getLogger(FW1240.class.getName());
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

//	private void initVerifyTearDown() {
//		verifyTearDownOperations = PageFactory.initElements(driver, VerifyTearDownOperations.class);
//		verifyDriver = verifyTearDownOperations.driver;
//		verifyWait = verifyTearDownOperations.wait;
//	}

	@Test
	public void testEditOrder() throws Exception {
		init();
		// register one new user
		uitestOperation.buy2ManTshirts();
		
		// click checkout button
		wait.waitForElementIsClickable(By.xpath("//button[contains(text(),'Checkout')]"));
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Checkout')]"));
		common.javascriptClick(driver, btnCheckOut);
		
		//update one order item
		uitestOperation.editOrder();
		
		// click checkout button
		wait.waitForElementIsClickable(By.xpath("//button[contains(text(),'Checkout')]"));
		btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Checkout')]"));
		common.javascriptClick(driver, btnCheckOut);
		
		Assert.assertTrue(uitestOperation.verifyUpdatedQuanlity(2));

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();

	}

}
