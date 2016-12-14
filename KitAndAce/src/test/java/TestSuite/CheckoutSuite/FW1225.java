package TestSuite.CheckoutSuite;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
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
 * @Description: Primary shipping address
 * @author: Howard
 * @compay: Kit and Ace
 * @date 1/16/2016
 * @version V1.0
 */

public class FW1225 {
	private WebDriver driver, verifyDriver;
	private Wait wait, verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	public VerifyTearDownOperations verifyTearDownOperations;
	static Logger log = Logger.getLogger(FW1225.class.getName());
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
	public void testVerifyPrimaryShippingAddressOnCheckOutPage() throws Exception {
		init();
		// register one new user
		uitestOperation.registerUser(userHybris);
		// add 2 credit card
		uitestOperation.addUserAddressDetail(userHybris, billing);
		uitestOperation.addUserPaymentDetail(userHybris, billing);
		uitestOperation.addUserAddressDetail(userHMC, uitestOperation.billings.get(1));
		uitestOperation.addUserPaymentDetail(userHMC, uitestOperation.billings.get(1));
		uitestOperation.buyManTshirtsWithAnonymousUser();

		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Edit your options')]"));
		common.javascriptClick(driver, btnCheckOut);

		// verify the address select and text under
		// if we can select different option which means this item must be
		// displayed on the screen
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-select-delivery-address");
		Select selectCC = new Select(driver.findElement(By.id("checkout-select-delivery-address")));
		selectCC.selectByIndex(1);

		String selectedAddress = userHybris.getAddress();
		Assert.assertTrue(uitestOperation.ElementExist(By.xpath("//p[contains(text(),'" + selectedAddress + "')]")));

	}

	private void clickAddAddress() {
		driver.findElement(By.xpath("//a[@class='form__add-new-btn pull-right js-add-new-address']")).click();
		wait.threadWait(1000);
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
