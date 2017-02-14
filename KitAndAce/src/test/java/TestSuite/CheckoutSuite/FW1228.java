package TestSuite.CheckoutSuite;



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
import POJO.UserInfo;
import PageObjects.BrowserLoader;
import PageObjects.BrowserStackLoader;
import PageObjects.CommonActions;
import PageObjects.Countries;
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
* @Description:  Change taxes due to change country in SA during checkout
* @author: Howard
* @compay: Kit and Ace     
* @date 1/18/2016 
* @version V1.0   
*/



public class FW1228 {
	private WebDriver driver,verifyDriver;
	private Wait wait,verifyWait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	UITestOperations uitestOperation;
	HMCTestOperations hmcTestOperation;
	HACTestOperations hacTestOperations;
	static Logger log = Logger.getLogger(FW1228.class.getName());
	public InitWebDriver initWebDriver;
	public UserInfo userHybris,userHMC;
	public BillingInfo billing;
	public VerifyTearDownOperations verifyTearDownOperations;
	private Object String;

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
		hacTestOperations=PageFactory.initElements(driver, HACTestOperations.class);
		
		userHybris=uitestOperation.users.get(1);
		userHMC=uitestOperation.users.get(2);
		billing=uitestOperation.billings.get(0);
	}
	
	
	private void initVerifyTearDown()
	{
		verifyTearDownOperations= PageFactory.initElements(driver, VerifyTearDownOperations.class);
		verifyDriver=verifyTearDownOperations.driver;
		verifyWait=verifyTearDownOperations.wait;
	}

	@Test
	public void checkTaxRateWhenChangeCountry() throws Exception {

		init();
		// register one new user
		uitestOperation.registerUser(userHybris);
		wait.threadWait(2000);
		uitestOperation.buyManTshirtsWithAnonymousUser();

        // checkout
		uitestOperation.checkOut(userHybris, billing);
		
		
		//changeToCanada
		verifyTaxRate(Countries.Canada.toString());
		
		
		//changeToUSA
		changeCountry(4,8);
		driver.findElement(By.id("checkout-zip-code")).sendKeys("94401");
		common.javascriptScrollPage(driver, -1000);
		wait.threadWait(5000);
		verifyTaxRate(Countries.USA.toString());
		

	}
	


    private void changeCountry(int countryIndex,int stateIndex)
    {
    	//change country
    	common.javascriptMakeSelectOptionVisiable(driver, "checkout-country-select");
		Select selectCountry = new Select(driver.findElement(By.id("checkout-country-select")));
		selectCountry.selectByIndex(countryIndex);
		wait.waitElementToBeDisplayed(By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"));
		driver.findElement(By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']")).click();
		
		//chanage state
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
		Select selectState = new Select(driver.findElement(By.id("checkout-region-select")));
		selectState.selectByIndex(stateIndex);
    }
    
    private void verifyTaxRate(String country)
    {
          if(country.equalsIgnoreCase(Countries.Canada.toString()))
        	 Assert.assertEquals(getTaxRate(), 12); 
          if(country.equalsIgnoreCase(Countries.USA.toString()))
         	 Assert.assertEquals(getTaxRate(), 9); 
    }

    
    private int getTaxRate()
    {
    	List<WebElement> priceTag=driver.findElements(By.xpath("//div[@class='order-summary__price__val pull-right']"));
    	if(priceTag==null || priceTag.size()==0)
    		return -1;
    	    	
    	String tax=priceTag.get(1).getText(); 
    	String total=priceTag.get(2).getText();
       
    	double taxRate=praseStringToNumber(tax)/(praseStringToNumber(total)-praseStringToNumber(tax));
    	System.out.println("taxRate is: "+ (int)(taxRate*100));
        return (int)(taxRate*100);    	
    	
    }
    
    
    private double praseStringToNumber(String price)
    {
    	
    	return Double.parseDouble(price.substring(1, price.length()));
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
//
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
