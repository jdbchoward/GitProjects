package TestSuite.CheckoutSuite;



import java.util.ArrayList;
import java.util.List;

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
import POJO.GiftCard;
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
* @Description:  Add/Remove Gift Card validation - negative
* @author: Howard
* @compay: Kit and Ace     
* @date 4/28/2017 
* @version V1.0   
*/



public class FW3650 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	static Logger log = Logger.getLogger(FW3650.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris,userHMC;
	public BillingInfo billing;
	public GiftCard giftCard;
	public VerifyTearDownOperations verifyTearDownOperations;

	@BeforeTest(alwaysRun = true)
	public void setUp() throws Exception {

		common = PageFactory.initElements(driver, CommonActions.class);		
	}
	
	
	private void init()
	{
		initWebDriver = PageFactory.initElements(driver, InitWebDriver.class);
		driver=initWebDriver.driver;
		wait = new Wait(driver);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
		uitestOperation = PageFactory.initElements(driver, UITestOperations.class);
		hmcTestOperation = PageFactory.initElements(driver, HMCTestOperations.class);
		
		userHybris=uitestOperation.users.get(3);
		userHMC=uitestOperation.users.get(2);
		billing=uitestOperation.billings.get(0);
		giftCard=uitestOperation.giftCards.get(0);
	}
	

	@Test
	public void validationAddGiftCard() throws Exception {

		init();
	    uitestOperation.buyManTshirtsWithAnonymousUser(); 
	    
	    common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);		
		//add gift card
		WebElement txtGiftCardInput=driver.findElement(By.id(""));
		//try different card format
//		Enter non-alfa-numeric data in gift card field and trigger validation	Validation failed. An error message is shown. Field is highlighted
		uitestOperation.addGiftCard(giftCard.getCardNum());
//		Enter alpha-numeric characters (without space) and trigger validation	Validation failed. An error message is shown. Field is highlighted
		uitestOperation.addGiftCard(giftCard.getCardNum());
//		Enter letters and numbers (using valid alternate) with separator differ from space (/,.; etc.) and trigger validation	Validation failed. An error message is shown. Field is highlighted
		uitestOperation.addGiftCard(giftCard.getCardNum());
//		Enter 6 letters and numbers (using valid alternate) with space in the incorrect position and trigger validation	Validation failed. An error message is shown. Field is highlighted
		uitestOperation.addGiftCard(giftCard.getCardNum());
//		Enter multi-byte characters in gift card field and trigger validation	Validation failed. An error message is shown. Field is highlighted
		uitestOperation.addGiftCard(giftCard.getCardNum());
//		Add one valid gift card	one gift card added
		uitestOperation.addGiftCard(giftCard.getCardNum());
//		Add multi valid gift cards	multiple gift cards added
		uitestOperation.addGiftCard(giftCard.getCardNum());
//		Remove one valid gift card	one gift card removed
		uitestOperation.removeGiftCard(giftCard.getCardNum());
//		Remove multi valid gift cards	multi gift cards removed
		uitestOperation.removeGiftCard(giftCard.getCardNum());
		
	}
	



   
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {	
		driver.close();
		driver.quit();
	}

}
