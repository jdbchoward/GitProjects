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
 * @Description: User logs on after filling data. Positive
 * @author: Howard
 * @compay: Kit and Ace
 * @date 1/23/2016
 * @version V1.0
 */

public class FW1235 {
	private WebDriver driver, verifyDriver;
	private Wait wait, verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	public VerifyTearDownOperations verifyTearDownOperations;
	static Logger log = Logger.getLogger(FW1235.class.getName());
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
	public void testConfirmationPageDisplay() throws Exception {
		init();
		// register one new user
		uitestOperation.registerUser(userHybris);
		// add 2 credit card
		uitestOperation.addUserAddressDetail(userHybris, billing);
		uitestOperation.addUserPaymentDetail(userHybris, billing);
//		uitestOperation.addUserAddressDetail(userHMC, uitestOperation.billings.get(1));
//		uitestOperation.addUserPaymentDetail(userHMC, uitestOperation.billings.get(1));
		
		uitestOperation.doSignOut();
		uitestOperation.buyManTshirtsWithAnonymousUser();
		
		// click checkout button
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.waitElementToBeDisplayed(By.xpath("//button[contains(text(),'Continue')]"));		
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);
		
		
//		uitestOperation.expandCheckout();
		  //check SA and uncheck SA
	    common.javascriptClick(driver,driver.findElement(By.id("checkout-address-same-as-shipping")));
	    userHMC.setLastName("Anonymous");
	    uitestOperation.fillInSAbillingInfo(userHMC);
	    
	    
	    //sign in	    
	    driver.findElement(By.id("checkout-email")).sendKeys(userHybris.getEmail());
		wait.threadWait(1000);
		wait.waitElementToBeDisplayed(By.xpath("//a[@class='js-checkout-sign-in']"));
		driver.findElement(By.xpath("//a[@class='js-checkout-sign-in']")).click();
		wait.waitElementToBeDisplayed(By.id("signin-email"));
		// login on the checkout page
		driver.findElement(By.id("signin-email")).clear();
		driver.findElement(By.id("signin-email")).sendKeys(userHybris.getEmail());
		driver.findElement(By.id("signin-password")).sendKeys(userHybris.getPassword());
		driver.findElement(By.xpath("//button[@class='btn btn--default btn--md js-checkout-signin-btn']")).click();
		
//		uitestOperation.expandCheckout();
		uitestOperation.fillInBillingInfo(billing);

        //place order
		wait.waitElementToBeEnabled(By.xpath("//button[contains(text(),'Place my order')]"));
		driver.findElement(By.xpath("//button[contains(text(),'Place my order')]")).click();
		orderNumber = uitestOperation.getOrderNumber();

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {


	    initVerifyTearDown();
		
		// login to HMC system. prepare to delete test date
		hmcTestOperation.doLogOnSite(userHMC,verifyDriver);
		verifyWait.threadWait(3000);
		// expand tree
		verifyDriver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[order]_treeicon")).click();
		verifyWait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Order]_label"));
		verifyDriver.findElement(By.id("Tree/GenericLeafNode[Order]_label")).click();
		verifyDriver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Order.code]]_input")).sendKeys(orderNumber);
		verifyDriver.findElement(By.id("Content/DateEditor[in Content/GenericCondition[Order.date]]_date")).sendKeys(common.getTodayDate());

		verifyDriver.findElement(By.id("Content/OrganizerSearch[Order]_searchbutton")).click();

		// delete order so that we can delete User later
		verifyDriver.findElement(By
				.xpath("//table[@id='Content/ClassificationOrganizerList[Order]_innertable']/tbody/tr[2]/td[4]/div/div"))
				.click();
		verifyDriver.findElement(By.id("Content/ClassificationOrganizerList[Order][delete]_img")).click();
		verifyDriver.switchTo().alert().accept();
		verifyWait.threadWait(1000);
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
