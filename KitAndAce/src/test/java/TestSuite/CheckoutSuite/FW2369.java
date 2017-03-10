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
import PageObjects.HACTestOperations;
import PageObjects.HMCTestOperations;
import PageObjects.InitWebDriver;
import PageObjects.UITestOperations;
import PageObjects.VerifyTearDownOperations;
import PageObjects.Wait;
import junit.framework.Assert;

/**
 * @Title: Automation TestSuite
 * @Package CheckoutSuite
 * @Description: Tax Calculation - trigger tax calculation for Registered user
 *               with data.
 * @author: Howard
 * @compay: Kit and Ace
 * @date 1/25/2016
 * @version V1.0
 */

public class FW2369 {
	private WebDriver driver, verifyDriver;
	private Wait wait, verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	HACTestOperations hacTestOperations;
	static Logger log = Logger.getLogger(FW2369.class.getName());
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
		hacTestOperations=PageFactory.initElements(driver, HACTestOperations.class);

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
	public void testTaxEstimationTriggerTaxCalculationByAddressChange() throws Exception {

		init();
		uitestOperation.registerUser(userHybris);
		wait.threadWait(2000);
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
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);

		// verify select different address
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-select-delivery-address");
		Select selectCC = new Select(driver.findElement(By.id("checkout-select-delivery-address")));
		selectCC.selectByIndex(0);
		wait.threadWait(4000);
		selectCC.selectByIndex(1);
		wait.threadWait(4000);
		selectCC.selectByIndex(2);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {

		initVerifyTearDown();
		
		 // login to HAC system. prepare to delete test date
	    hacTestOperations.doLogOnSite(userHMC,verifyDriver);
	    //clean user
	    hacTestOperations.cleanUser(userHybris,verifyDriver);
		

//		// login to HMC system. prepare to delete test date
//		hmcTestOperation.doLogOnSite(userHMC, verifyDriver);
//		verifyWait.threadWait(3000);
//		// Navigate to User and Delete that TEST USER
//		verifyDriver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[user]_label")).click();
//		verifyWait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Customer]_label"));
//		verifyDriver.findElement(By.id("Tree/GenericLeafNode[Customer]_label")).click();
//		verifyDriver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Customer.name]]_input"))
//				.sendKeys(userHMC.getFirstName());
//		verifyDriver.findElement(By.id("Content/OrganizerSearch[Customer]_searchbutton")).click();
//		verifyDriver.findElement(By.xpath("//div[contains(text(),'Howard Anonymous')]")).click();
//		verifyDriver.findElement(By.id("Content/ClassificationOrganizerList[Customer][delete]_img")).click();
//		verifyDriver.switchTo().alert().accept();
		driver.close();
		driver.quit();
		verifyDriver.close();
		verifyDriver.quit();
	}

}
