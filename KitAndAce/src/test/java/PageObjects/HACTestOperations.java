package PageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import POJO.UserInfo;
import junit.framework.Assert;

public class HACTestOperations {

	private WebDriver driver;
	private Wait wait;
	private String baseUrl;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	static Logger log = Logger.getLogger(HMCTestOperations.class.getName());

	public HACTestOperations(WebDriver driver) {
		this.driver = driver;
		wait = new Wait(driver);
		common = PageFactory.initElements(driver, CommonActions.class);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
	}

	/// ----------------------------------------------------------

	public void doLogOnSite(UserInfo user, WebDriver driver) {
		String testEnvironment = common.getSettings().getValue("testEnvironment");
		if (testEnvironment.equalsIgnoreCase("dev"))
			baseUrl = "https://admindev.hybris.kitandace.com/hac";
		if (testEnvironment.equalsIgnoreCase("stage"))
			baseUrl = "https://adminstaging.hybris.kitandace.com/hac";

		driver.get(baseUrl);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// start to login
		driver.findElement(By.xpath("//input[@name='j_username']")).clear();
		driver.findElement(By.xpath("//input[@name='j_username']")).sendKeys(user.getEmail());
		driver.findElement(By.xpath("//input[@name='j_password']")).clear();
		driver.findElement(By.xpath("//input[@name='j_password']")).sendKeys(user.getPassword());
		driver.findElement(By.xpath("//button[@type='submit']")).click();

	}

	public void cleanOrder(String orderNum, WebDriver driver) {
		String sql = "DELETE FROM orders item_t0 WHERE ( item_t0.p_code  = '" + orderNum + "')";
		String url = baseUrl + "/console/flexsearch/";
		driver.manage().window().maximize();
		driver.get(url);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// navigation to sql tab
		driver.findElement(By.id("nav-tab-2")).click();

		WebElement textbox = driver.findElement(By.xpath("//div[@id='sqlQueryWrapper']/div[2]/div/textarea"));
		common.javascriptInputInToTextArea(driver, textbox, sql);
		wait.threadWait(2000);
		WebElement commitCheckbox = driver.findElement(By.id("commitCheckbox1"));
		common.javascriptClick(driver, commitCheckbox);
		driver.findElement(By.id("buttonSubmit2")).click();
		cleanCache(driver);
	}

	public void cleanUser(UserInfo userHybris, WebDriver driver) {
		//  此处\\n 因为要传到js里面，所以不能直接\n。
		String sql = "REMOVE KtacCustomer;firstName[unique=true];lastName[unique=true];\\n;Howard;Anonymous;";
		String url = baseUrl + "/console/impex/import";
		driver.manage().window().maximize();
		driver.get(url);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement textbox = driver.findElement(By.xpath("//div[@id='textarea-container']/div/div/textarea"));	
		common.javascriptInputInToTextArea(driver, textbox, sql);
		wait.threadWait(2000);
		driver.findElement(By.xpath("//input[@value='Import content']")).click();
	}

	public void cleanCache(WebDriver driver) {
		String url = baseUrl + "/monitoring/cache";
		driver.manage().window().maximize();
		driver.get(url);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.id("resetCache")).click();
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