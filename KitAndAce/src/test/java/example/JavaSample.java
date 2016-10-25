package example;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class JavaSample {

	  public static final String USERNAME = "howardzhang1";
	  public static final String AUTOMATE_KEY = "J7kQzrcFhcQp48Z8Ss9W";
	  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

  public static void main(String[] args) throws Exception {

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("browser", "Chrome");
    caps.setCapability("browser_version", "54.0");
    caps.setCapability("os", "Windows");
    caps.setCapability("os_version", "7");
    caps.setCapability("resolution", "1280x1024");
    caps.setCapability("acceptSslCerts", "true");
    caps.setCapability("browserstack.local", "true");
    
//    caps.setCapability("browser", "Safari");
//    caps.setCapability("browser_version", "10.0");
//    caps.setCapability("os", "OS X");
//    caps.setCapability("os_version", "Sierra");
//    caps.setCapability("resolution", "1024x768");
    
    
//    caps.setCapability("browserName", "iPhone");
//    caps.setCapability("platform", "MAC");
//    caps.setCapability("device", "iPhone 6S Plus");
    

    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);   
    driver.get("https://admindev.hybris.kitandace.com/hmc/hybris");
    WebElement element = driver.findElement(By.name("q"));

    element.sendKeys("BrowserStack");
    element.submit();

    System.out.println(driver.getTitle());
    driver.quit();

  }
}
