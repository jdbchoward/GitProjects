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
 * @Description: Tax Calculation - add new address for Registered user with
 *               data.
 * @author: Howard
 * @compay: Kit and Ace
 * @date 1/25/2016
 * @version V1.0
 */

public class FW2370 {
	private WebDriver driver, verifyDriver;
	private Wait wait, verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW2370.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris, userHMC, nonTaxStateUser;
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
		nonTaxStateUser = uitestOperation.users.get(6);
		userHMC = uitestOperation.users.get(2);
		billing = uitestOperation.billings.get(0);

	}

	private void initVerifyTearDown() {
		verifyTearDownOperations = PageFactory.initElements(driver, VerifyTearDownOperations.class);
		verifyDriver = verifyTearDownOperations.driver;
		verifyWait = verifyTearDownOperations.wait;
	}

	@Test
	public void testTaxEstimationAddNewAddress4RegisteredUser() throws Exception {

		init();
		uitestOperation.registerUser(userHybris);
		// registerUser with non-taxable state and taxable state
		uitestOperation.addUserAddressDetail(userHybris, billing, 4, 8);
		uitestOperation.addUserAddressDetail(nonTaxStateUser, billing, 4, 41);

		// register invalid address
		nonTaxStateUser.setZip("00000");
		nonTaxStateUser.setAddress("888 invalid address");
		uitestOperation.addUserAddressDetail(nonTaxStateUser, billing, 4, 41);

		uitestOperation.buyUSManTshirtsWithAnonymousUser();
		// uitestOperation.AnonymousCheckOutAndVerifyInfo(userHybris, billing);

		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Checkout')]"));
		common.javascriptClick(driver, btnCheckOut);
		prepareMoreAddresses();
	}

	private void clickAddAddress() {
		driver.findElement(By.xpath("//a[@class='form__add-new-btn pull-right js-add-new-address']")).click();
		wait.threadWait(1000);
	}

	private void prepareMoreAddresses() {
		// add new address
		clickAddAddress();
		userHybris.setAddress("711 W Century Blvd");
		uitestOperation.addAddressWhenCheckOut(userHybris, billing);
		driver.findElement(By.xpath("//a[@class='btn btn--bordered btn--sm js-add-new-address-btn']")).click();

		common.javascriptScrollPage(driver, -1500);
		wait.threadWait(3000);
		clickAddAddress();
		nonTaxStateUser.setAddress("219 SW 6th Ave");
		nonTaxStateUser.setZip("97204");
		uitestOperation.addAddressWhenCheckOut(nonTaxStateUser, billing);
		driver.findElement(By.xpath("//a[@class='btn btn--bordered btn--sm js-add-new-address-btn']")).click();

		common.javascriptScrollPage(driver, -1500);
		wait.threadWait(3000);
		clickAddAddress();
		nonTaxStateUser.setZip("00000");
		nonTaxStateUser.setAddress("999 invalid address");
		uitestOperation.addAddressWhenCheckOut(nonTaxStateUser, billing);
		driver.findElement(By.xpath("//a[@class='btn btn--bordered btn--sm js-add-new-address-btn']")).click();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {

		initVerifyTearDown();

		// login to HMC system. prepare to delete test date
		hmcTestOperation.doLogOnSite(userHMC, verifyDriver);
		verifyWait.threadWait(3000);
		// Navigate to User and Delete that TEST USER
		verifyDriver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[user]_label")).click();
		verifyWait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Customer]_label"));
		verifyDriver.findElement(By.id("Tree/GenericLeafNode[Customer]_label")).click();
		verifyDriver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Customer.name]]_input"))
				.sendKeys(userHMC.getFirstName());
		verifyDriver.findElement(By.id("Content/OrganizerSearch[Customer]_searchbutton")).click();
		verifyDriver.findElement(By.xpath("//div[contains(text(),'Howard Anonymous')]")).click();
		verifyDriver.findElement(By.id("Content/ClassificationOrganizerList[Customer][delete]_img")).click();
		verifyDriver.switchTo().alert().accept();
		driver.close();
		driver.quit();
		verifyDriver.close();
		verifyDriver.quit();
	}

}
