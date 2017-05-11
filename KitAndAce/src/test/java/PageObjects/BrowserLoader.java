package PageObjects;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserLoader {

	public WebDriver driver = null;
	private FirefoxProfile firefoxprofile = null;
//	private static DesiredCapabilities caps = null;
	private String driverpath = "src\\test\\resources\\";

	public static String nodeurl = "";

	public BrowserLoader(String browsertype) {

		if (browsertype.equalsIgnoreCase("firefox"))
			initBrowser(BrowserTypes.firefox);
		if (browsertype.equalsIgnoreCase("ie"))
			initBrowser(BrowserTypes.ie);
		if (browsertype.equalsIgnoreCase("chrome"))
			initBrowser(BrowserTypes.chrome);
	}

	public BrowserLoader(BrowserTypes browserstype) {

		initBrowser(browserstype);
	}

	private void initBrowser(BrowserTypes browserstype) {
		switch (browserstype) {
		case firefox:
			System.setProperty("webdriver.gecko.driver", driverpath + "geckodriver.exe");
			DesiredCapabilities capsFF = DesiredCapabilities.firefox();
			// File firebug = new
			// File(respircespath+"/tool/firebug-1.12.1-fx.xpi");
			// File firepath = new
			// File(respircespath+"/tool/firepath-0.9.7-fx.xpi");
			firefoxprofile = new FirefoxProfile();
			try {
				// firefoxprofile.addExtension(firebug);
				// firefoxprofile.addExtension(firepath);
				firefoxprofile.setPreference("webdriver.accept.untrusted.certs", "true");
				firefoxprofile.setPreference("dom.disable_open_during_load", "false");
				firefoxprofile.setPreference("browser.link.open_newwindow.restriction",1);
				firefoxprofile.setPreference("privacy.popups.disable_from_plugins",0);
				// firefoxprofile.setPreference("extensions.firebug.currentVersion",
				// "1.12.1");
			} catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
			}
			capsFF.setCapability(FirefoxDriver.PROFILE, firefoxprofile);
			if (nodeurl.equals(""))
				driver = new FirefoxDriver(firefoxprofile);
			else
				try {
					driver = new RemoteWebDriver(new URL(nodeurl), capsFF);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// driver.manage().window().maximize();
			break;
		case ie:
			System.setProperty("webdriver.ie.driver", driverpath + "IEDriverServer.exe");
			DesiredCapabilities capsIE = DesiredCapabilities.internetExplorer();
			capsIE.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, false);
			capsIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capsIE.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
			capsIE.setCapability("ignoreZoomSetting", true);
			capsIE.setCapability("ignoreProtectedModeSettings", true);
			capsIE.setCapability("ie.unexpectedAlertBehaviour", "accept");

			if (nodeurl.equals(""))
				driver = new InternetExplorerDriver(capsIE);
			else
				try {
					driver = new RemoteWebDriver(new URL(nodeurl), capsIE);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// driver.manage().window().maximize();
			break;
		case chrome:
			System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
			DesiredCapabilities capsChrome = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("-incognito");
			options.addArguments("--disable-popup-blocking");
//			options.addArguments("no-sandbox");
//			options.addArguments("chrome.switches", "--disable-extensions");
			

			// capabilities.setCapability("chrome.switches",
			// Arrays.asList("--proxy-server=http://your-proxy-domain:4443"));
			capsChrome.setCapability(ChromeOptions.CAPABILITY, options);
			capsChrome.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
			
			if (nodeurl.equals(""))
				driver = new ChromeDriver(capsChrome);
			else
				try {
					driver = new RemoteWebDriver(new URL(nodeurl), capsChrome);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// driver.manage().window().maximize();
			break;
		}
	}
}
