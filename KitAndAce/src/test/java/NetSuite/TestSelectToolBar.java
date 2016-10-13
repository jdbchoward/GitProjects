package NetSuite;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PageObjects.BrowserLoader;
import PageObjects.CommonActions;
import PageObjects.ElementsRepositoryAction;
import PageObjects.NetSuiteTestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: Automation Test for NetSuite 
* @Package NetSuite 
* @Description: Select Toolbar 
* @author: Howard
* @compay: PQA     
* @date 10/13/2016 
* @version V1.0   
*/



public class TestSelectToolBar {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	NetSuiteTestOperations netSuiteTestOperations;
	static Logger log = Logger.getLogger(TestSelectToolBar.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		netSuiteTestOperations = PageFactory.initElements(driver, NetSuiteTestOperations.class);

	}

	@Test
	public void testSelectFromToolBar() throws Exception {

		netSuiteTestOperations.doLogOnSite("howard.zhang@kitandace.com", "Welcome!10011001");
	    wait.threadWait(3000);
	    selectFromToolBar();
	    
	}

	public void selectFromToolBar() {

		WebElement transactions = driver.findElement(By.xpath("//li[@data-title='Transactions']"));
		transactions.click();

		WebElement salesOrderList = driver.findElement(
				By.xpath("//a[@href='/app/accounting/transactions/transactionlist.nl?Transaction_TYPE=SalesOrd']"));
		common.javascriptClick(driver, salesOrderList);

	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
