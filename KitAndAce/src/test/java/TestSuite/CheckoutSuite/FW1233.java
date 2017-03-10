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
 * @Description: Select CC. Positive
 * @author: Howard
 * @compay: Kit and Ace
 * @date 12/5/2016
 * @version V1.0
 */

public class FW1233 {
	private WebDriver driver, verifyDriver;
	private Wait wait, verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	HACTestOperations hacTestOperations;
	public VerifyTearDownOperations verifyTearDownOperations;
	static Logger log = Logger.getLogger(FW1233.class.getName());
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
		hacTestOperations=PageFactory.initElements(driver, HACTestOperations.class);
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
	public void testSelectCCWhenCheckOut() throws Exception {
		init();
		// register one new user
		uitestOperation.registerUser(userHybris);
		wait.threadWait(2000);
		// add 2 credit card
		uitestOperation.addUserPaymentDetail(userHybris, billing);
		uitestOperation.addUserPaymentDetail(userHybris, uitestOperation.billings.get(1));
		uitestOperation.buyManTshirtsWithAnonymousUser();
		
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);
		uitestOperation.selectCreditCardWhenCheckOut(billing);

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
//				.sendKeys("howard");
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
