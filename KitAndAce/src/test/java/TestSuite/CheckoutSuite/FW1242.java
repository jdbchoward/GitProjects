package TestSuite.CheckoutSuite;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.HMCTestOperations;
import PageObjects.InitWebDriver;
import PageObjects.UITestOperations;
import PageObjects.Wait;
import junit.framework.Assert;

/**
 * @Title: Automation TestSuite
 * @Package CheckoutSuite
 * @Description: Anonymous user. Registration on confirmation page.
 * @author: Howard
 * @compay: Kit and Ace
 * @date 11/30/2016
 * @version V1.0
 */

public class FW1242 {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW1242.class.getName());
	public InitWebDriver initWebDriver;
	String orderNumber;
	// String orderNumber="00253014";

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);

		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver = initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);
		hmcTestOperation = PageFactory.initElements(driver, HMCTestOperations.class);

	}

	@Test
	public void testRegistrationONConfirmPage() throws Exception {

		// place order from Hybris system
		uitestOperation.buyManTshirtsWithAnonymousUser();
		uitestOperation.AnonymousCheckOut("howard.zhangkitandace@yahoo.com");
		orderNumber = uitestOperation.getOrderNumber();
//		Assert.assertTrue(uitestOperation.verifyConfirmationPage());
		
		//create new account
		driver.findElement(By.id("create-account-checkout_pwd")).sendKeys("10011001");
		driver.findElement(By.id("create-account-checkout_checkPwd")).sendKeys("10011001");
		driver.findElement(By.xpath("//button[@class='button js-checkout-confirm-create']")).click();
		
        Assert.assertTrue(uitestOperation.verifyAccountPage());
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {

		// login to HMC system. prepare to delete test date
		hmcTestOperation.doLogOnSite("howard.zhang@kitandace.com", "Integrity101");
		wait.threadWait(3000);
		// expand tree
		driver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[order]_treeicon")).click();
		wait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Order]_label"));
		driver.findElement(By.id("Tree/GenericLeafNode[Order]_label")).click();
		driver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Order.code]]_input")).sendKeys(orderNumber);
		driver.findElement(By.id("Content/DateEditor[in Content/GenericCondition[Order.date]]_date")).sendKeys(common.getTodayDate());

		driver.findElement(By.id("Content/OrganizerSearch[Order]_searchbutton")).click();

		// delete order so that we can delete User later
		driver.findElement(By
				.xpath("//table[@id='Content/ClassificationOrganizerList[Order]_innertable']/tbody/tr[2]/td[4]/div/div"))
				.click();
		driver.findElement(By.id("Content/ClassificationOrganizerList[Order][delete]_img")).click();
		driver.switchTo().alert().accept();
		wait.threadWait(1000);
		// Navigate to User and Delete that TEST USER
		driver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[user]_label")).click();
		wait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Customer]_label"));
		driver.findElement(By.id("Tree/GenericLeafNode[Customer]_label")).click();
		driver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Customer.name]]_input"))
				.sendKeys("howard");
		driver.findElement(By.id("Content/OrganizerSearch[Customer]_searchbutton")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Howard Anonymous')]")).click();
		driver.findElement(By.id("Content/ClassificationOrganizerList[Customer][delete]_img")).click();
		driver.switchTo().alert().accept();
		driver.close();
		driver.quit();
	}

}
