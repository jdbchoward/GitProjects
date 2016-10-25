package PageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class InitWebDriver {

	public WebDriver driver;
	CommonActions common;
	static Logger log = Logger.getLogger(InitWebDriver.class.getName());

	public InitWebDriver() {

		common = PageFactory.initElements(driver, CommonActions.class);
		String browserType = common.getSettings().getValue("browserType");

		String runingLocation = common.getSettings().getValue("testLocation");
		if (runingLocation.equalsIgnoreCase("local")) {
			BrowserLoader brower = new BrowserLoader(browserType);
			driver = brower.driver;
		}
		if (runingLocation.equalsIgnoreCase("browserStack")) {
			BrowserStackLoader brower = new BrowserStackLoader(browserType);
			driver = brower.driver;
		}

	}

}
