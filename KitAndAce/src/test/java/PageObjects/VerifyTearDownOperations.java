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

import POJO.UserInfo;
import junit.framework.Assert;

public class VerifyTearDownOperations {

	public WebDriver driver;
	public Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	static Logger log = Logger.getLogger(VerifyTearDownOperations.class.getName());

	public VerifyTearDownOperations() {
		BrowserLoader brower = new BrowserLoader("chrome");
		this.driver = brower.driver;
		wait = new Wait(driver);
		common = PageFactory.initElements(driver, CommonActions.class);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
	}

	
}