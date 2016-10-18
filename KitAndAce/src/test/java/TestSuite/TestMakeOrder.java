package TestSuite;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.HMCTestOperations;
import PageObjects.TestOperations;
import PageObjects.UITestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation TestSuite 
* @Package TestSuite 
* @Description: Make an order and then check on the HMC system. 
* @author: Howard
* @compay: Kit and Ace     
* @date 10/18/2016 
* @version V1.0   
*/



public class TestMakeOrder {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(TestMakeOrder.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);
		hmcTestOperation = PageFactory.initElements(driver, HMCTestOperations.class);

	}

	@Test
	public void testSignInWithCorrectInfo() throws Exception {

//		String orderNumber="00230007";
		String orderNumber;
		//place order from Hybris system
		uitestOperation.doSignSite("howard.zhang.kitandace@outlook.com", "10011001");
	    wait.threadWait(3000);
	    uitestOperation.naviToManTShirts();
	    uitestOperation.buy2ManTshirts();
	    uitestOperation.checkOut();	    
	    orderNumber=uitestOperation.getOrderNumber();
	    //verify order in HMC system.
		hmcTestOperation.doLogOnSite("howard.zhang@kitandace.com", "Integrity101");
	    wait.threadWait(3000);
	    //expand tree
	    driver.findElement(By.id("Tree/GenericExplorerMenuTreeNode[order]_treeicon")).click();
	    wait.waitElementToBeDisplayed(By.id("Tree/GenericLeafNode[Order]_label"));
	    driver.findElement(By.id("Tree/GenericLeafNode[Order]_label")).click();	  
	    driver.findElement(By.id("Content/StringEditor[in Content/GenericCondition[Order.code]]_input")).sendKeys(orderNumber);	   
	    driver.findElement(By.id("Content/DateEditor[in Content/GenericCondition[Order.date]]_date")).sendKeys(common.getTodayDate());
	    
	    //open new window to search for user
	    String mainPageTilte=driver.getTitle();
	    driver.findElement(By.id("Content/AutocompleteReferenceEditor[in Content/GenericCondition[Order.user]]_img")).click();
        common.switchWindowHandles(driver, "User search - hybris Management Console (hMC)"); 
	    driver.findElement(By.id("StringEditor[in GenericCondition[User.uid]]_input")).sendKeys("howard");
	    driver.findElement(By.id("ModalGenericFlexibleSearchOrganizerSearch[User]_searchbutton")).click();
	    Actions action=new Actions(driver);
	    action.doubleClick(driver.findElement(By.id("StringDisplay[howard.zhang.kitandace@outlook.com]_span"))).perform();
	    common.switchWindowHandles(driver, mainPageTilte);
	    //----------
	    driver.findElement(By.id("Content/OrganizerSearch[Order]_searchbutton")).click();

	}




	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
//		driver.close();
//		driver.quit();
	}

}
