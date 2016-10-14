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

public class WCMSTestOperations {

	private WebDriver driver;
	private String baseUrl;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	static Logger log = Logger.getLogger(WCMSTestOperations.class.getName());

	public WCMSTestOperations(WebDriver driver) {
		this.driver = driver;
		baseUrl = "https://demox.mmxreservations.com/";
		wait = new Wait(driver);
		common = PageFactory.initElements(driver, CommonActions.class);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
	}

	
	/// ----------------------------------------------------------

	public void doLogOnSite(String username, String psw) {
		driver.manage().window().maximize();
		driver.get("http://admindev.hybris.kitandace.com/cmscockpit/index.zul");
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

		if (ElementExist(By.xpath("//td[@class='page-header-left']"))) {
			String loginInfo = driver.findElement(By.xpath("//td[@class='page-header-left']")).getText();

			return loginInfo.contains("Explorer:");
		} else
			return false;

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