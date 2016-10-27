package PageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import junit.framework.Assert;

public class CscockpitTestOperations {

	private WebDriver driver;
	private String baseUrl;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	static Logger log = Logger.getLogger(CscockpitTestOperations.class.getName());

	public CscockpitTestOperations(WebDriver driver) {
		this.driver = driver;
		baseUrl = "https://demox.mmxreservations.com/";
		wait = new Wait(driver);
		common = PageFactory.initElements(driver, CommonActions.class);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
	}

	/// ----------------------------------------------------------

	public void doLogOnSite(String username, String psw) {
		driver.manage().window().maximize();
		
		
		String testEnvironment = common.getSettings().getValue("testEnvironment");
		if (testEnvironment.equalsIgnoreCase("dev"))
			driver.get("https://admindev.hybris.kitandace.com/cscockpit/login.zul");
		if (testEnvironment.equalsIgnoreCase("stage"))
			driver.get("http://adminstaging.hybris.kitandace.com/cscockpit/login.zul");		
		
		
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait.threadWait(2000);
		// start to login
		driver.findElement(By.xpath("//input[@name='j_username']")).clear();
		driver.findElement(By.xpath("//input[@name='j_username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='j_password']")).clear();
		driver.findElement(By.xpath("//input[@name='j_password']")).sendKeys(psw);
		driver.findElement(By.xpath("//td[@class='z-button-cm']")).click();

	}

	public boolean checkSignInStatus() {				
		wait.waitElementToBeDisplayed(By.xpath("//span[contains(text(),'Infobox')]"));
		driver.findElement(By.xpath("//span[contains(text(),'Infobox')]")).click();
		return ElementExist(By.xpath("//td[contains(text(),'Customer Service')]"));

	}

	public void searchCustomer(String customerName) {
		
		wait.threadWait(3000);
		List<WebElement> selects=driver.findElements(By.xpath("//select[@z.type='zul.sel.Lisel']"));
		
		if(selects!=null&&selects.size()>0)
		{
			new Select(selects.get(0)).selectByVisibleText("Kit and Ace Canada");
		}
		
		wait.threadWait(2000);
		driver.findElement(By.xpath("//a[contains(text(),'Find Customer')]")).click();
		wait.waitElementToBeDisplayed(By.xpath("//span[contains(text(),'Customer Name')]/following-sibling::input"));
		driver.findElement(By.xpath("//span[contains(text(),'Customer Name')]/following-sibling::input")).clear();
		driver.findElement(By.xpath("//span[contains(text(),'Customer Name')]/following-sibling::input")).sendKeys(customerName);
		  wait.threadWait(2000);
        driver.findElement(By.xpath("//td[contains(text(),'Search')]")).click();
        wait.threadWait(2000);
        driver.findElement(By.xpath("//td[contains(text(),'Select')]")).click();
		
	}
	
public boolean verifyCustomerSearch()
{
	return ElementExist(By.xpath("//span[contains(text(),'howard.zhang.kitandace@outlook.com')]"));
}
	

	public boolean ElementExist(By Locator) {
		try {
			driver.findElement(Locator);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			return false;
		}
	}
}