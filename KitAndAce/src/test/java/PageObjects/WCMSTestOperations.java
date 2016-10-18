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

		List<WebElement> welcomes = driver.findElements(By.xpath("//span[contains(text(),'Welcome')]"));
		if (welcomes != null && welcomes.size() > 0)
			return true;
		else
			return false;

	}

	public void naviToKSCanadaStage() {
		
		driver.findElement(
				By.xpath("//div[@class='advancedGroupboxPreLabel']/span[contains(text(),'Kit and Ace Canada')]"))
				.click();
		wait.waitElementToBeDisplayed(By.xpath("//div[@class='advancedGroupboxPreLabel']/span[contains(text(),'Kit and Ace content catalog / Staged')]"));
		driver.findElement(
				By.xpath("//div[@class='advancedGroupboxPreLabel']/span[contains(text(),'Kit and Ace content catalog / Staged')]"))
				.click();
		
	}
	
	//searchPage is the page u want to search
	public void searchForPageToChange(String searchPage)
	{
		wait.waitElementToBeDisplayed(By.xpath("//td/input[@class='z-textbox' and @z.type='zul.vd.Txbox']"));
		driver.findElement(By.xpath("//td/input[@class='z-textbox' and @z.type='zul.vd.Txbox']")).sendKeys(searchPage);
		List<WebElement> searchBtn=driver.findElements(By.xpath("//img[@src='/cmscockpit/cockpit/images/BUTTON_search.png']"));
		if(searchBtn!=null&&searchBtn.size()>0)
		{
			searchBtn.get(0).click();
		}
	}
	
	//when there is more than one edit button on the table, using index to location the one we need.
	public void clickEdit(int editBtnIndex)
	{
		
		List<WebElement> editBtns=driver.findElements(By.xpath("//img[@title='Edit' and @src='/cmscockpit/cockpit/images/icon_func_edit.png']"));
		if(editBtns!=null&&editBtns.size()>0)
		{
			editBtns.get(editBtnIndex).click();
		}
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