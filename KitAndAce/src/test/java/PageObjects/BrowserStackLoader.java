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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserStackLoader {

	public WebDriver driver = null;
	private FirefoxProfile firefoxprofile = null;
	private static DesiredCapabilities caps = null;
	private String driverpath = "src\\test\\resources\\";
	
	  public static final String USERNAME = "howardzhang1";
	  public static final String AUTOMATE_KEY = "J7kQzrcFhcQp48Z8Ss9W";
	  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	public static String nodeurl = "";

	public BrowserStackLoader(String browsertype) {

		if (browsertype.equalsIgnoreCase("firefox"))
			initBrowser(BrowserTypes.firefox);
		if (browsertype.equalsIgnoreCase("ie"))
			initBrowser(BrowserTypes.ie);
		if (browsertype.equalsIgnoreCase("chrome"))
			initBrowser(BrowserTypes.chrome);
		if (browsertype.equalsIgnoreCase("Safari"))
			initBrowser(BrowserTypes.Safari);
		if (browsertype.equalsIgnoreCase("Andriod"))
			initBrowser(BrowserTypes.Andriod);
		if (browsertype.equalsIgnoreCase("iOS"))
			initBrowser(BrowserTypes.iOS);
		
	}

	public BrowserStackLoader(BrowserTypes browserstype) {

		initBrowser(browserstype);
	}

	private void initBrowser(BrowserTypes browserstype)  {
		switch (browserstype) {
		case firefox:
			caps = new DesiredCapabilities();
			caps.setCapability("browser", "Firefox");
			caps.setCapability("browser_version", "46.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "7");
			caps.setCapability("resolution", "1280x1024");
			caps.setCapability("browserstack.local", "true");
			try {
				driver = new RemoteWebDriver(new URL(URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case ie:
			caps = new DesiredCapabilities();
			caps.setCapability("browser", "IE");
			caps.setCapability("browser_version", "9.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "7");
			caps.setCapability("resolution", "1280x1024");
			caps.setCapability("browserstack.ie.enablePopups", "true");
			caps.setCapability("browserstack.local", "true");
			try {
				driver = new RemoteWebDriver(new URL(URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case chrome:
			 caps = new DesiredCapabilities();
			caps.setCapability("browser", "Chrome");
			caps.setCapability("browser_version", "54.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "7");
			caps.setCapability("resolution", "1280x1024");
			caps.setCapability("browserstack.local", "true");
			try {
				driver = new RemoteWebDriver(new URL(URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

			
		case Safari:
			caps = new DesiredCapabilities();
			caps.setCapability("browser", "Safari");
			caps.setCapability("browser_version", "8.0");
			caps.setCapability("os", "OS X");
			caps.setCapability("os_version", "Yosemite");
			caps.setCapability("resolution", "1280x1024");
			caps.setCapability("browserstack.safari.enablePopups", "true");
		    caps.setCapability("browserstack.local", "true");
		    try {
				driver = new RemoteWebDriver(new URL(URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case Andriod:
			caps = new DesiredCapabilities();
			caps.setCapability("browserName", "android");
			caps.setCapability("platform", "ANDROID");
			caps.setCapability("device", "Google Nexus 7");
//			caps.setCapability("browserName", "android");
//			caps.setCapability("platform", "ANDROID");
//			caps.setCapability("device", "Amazon Kindle Fire HDX 7");
			caps.setCapability("browserstack.local", "true");
		    try {
				driver = new RemoteWebDriver(new URL(URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case iOS:
			caps = new DesiredCapabilities();
			caps.setCapability("browserName", "iPhone");
			caps.setCapability("platform", "MAC");
			caps.setCapability("device", "iPhone 6S Plus");
			caps.setCapability("browserstack.local", "true");
		    try {
				driver = new RemoteWebDriver(new URL(URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
}
