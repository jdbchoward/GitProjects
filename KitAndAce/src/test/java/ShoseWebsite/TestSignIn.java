package ShoseWebsite;

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
import PageObjects.TestOperations;
import PageObjects.Wait;
import junit.framework.Assert;


/**   
* @Title: EPT Automation Test on Shoes.ca 
* @Package ShoseWebsite 
* @Description: EPT Automation Test on Shoes.ca 
* @author: Howard
* @compay: PQA     
* @date 09/19/2016 
* @version V1.0   
*/



public class TestSignIn {
	private WebDriver driver;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	TestOperations testOperation;
	static Logger log = Logger.getLogger(TestSignIn.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");
		BrowserLoader brower = new BrowserLoader(browserType);
		driver = brower.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		testOperation = PageFactory.initElements(driver, TestOperations.class);

	}

	@Test
	public void testSignInWithCorrectInfo() throws Exception {

	    testOperation.doSignIn("hiend@yeah.net", "10011001");
//		wait.waitElementToBeDisplayed(By.xpath("//div[@id='admin_header']/h1"));
//		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='admin_header']/h1")).getText().equalsIgnoreCase("Account Details and Order History"));
	    wait.threadWait(3000);
	    Assert.assertTrue(!testOperation.checkSignOutStatus());
//	    wait.threadWait(5000);
		testOperation.doSignOut();
		wait.threadWait(1000);
	}

	@Test(dependsOnMethods = { "testSignInWithCorrectInfo" })
	public void testSignInWithBadInfo() throws Exception {

		testOperation.doSignIn("aa", "111");
		driver.get("http://www.shoeme.ca/");
		wait.threadWait(1000);
		Assert.assertTrue(testOperation.checkSignOutStatus());
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

}
