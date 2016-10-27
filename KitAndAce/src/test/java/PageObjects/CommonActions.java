package PageObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import HMC.TestCheckOrder;

public class CommonActions {

	private ParseProperties settings;
	private String parsePath = "src\\test\\resources\\Setting.properties";
	static Logger log = Logger.getLogger(CommonActions.class.getName());

	public ParseProperties getSettings() {
		return new ParseProperties(parsePath);
	}

	/**
	 * When webdrive can't controlling click etc, (through element not display
	 * etc) then use this. )
	 */
	public void javascriptClick(WebDriver driver, WebElement webElement) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
	}

	public void javascriptOpenLink(WebDriver driver, WebElement webElement) {
		String href = webElement.getAttribute("href");
		((JavascriptExecutor) driver).executeScript("window.open('" + href + "')");
	}
	
	
	public void javascriptInputToTextField(WebDriver driver,WebElement ele,String content)
	{//this element could be input's iframe
		String js = "arguments[0].contentWindow.document.body.innerHTML='"
				+ content + "'";
		((JavascriptExecutor) driver).executeScript(js, ele);
	}
	
	public void javascriptScrollPage(WebDriver driver,int offset)
	{
		String js = "window.scrollBy(0,"+offset+")";		
		((JavascriptExecutor) driver).executeScript(js, "");
	}
	
	
	public void javascriptMakeSelectOptionVisiable(WebDriver driver,String jQueryId)
	{
		String id="#"+jQueryId;
		String js="jQuery("+"'"+id+"').css('display','block')";
		 //Use JavascriptExecutor to make the element visible 
//		((JavascriptExecutor)wd).executeScript("jQuery('#assignee').css('display','block')");
		((JavascriptExecutor)driver).executeScript(js);
		
	}
	
	
	public void javascripDoubleClick(WebDriver driver, WebElement webElement) {
		((JavascriptExecutor) driver).executeScript("arguments[0].dblclick();", webElement);
	}
	
	public String getTodayDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		date.setDate(date.getDate()-1);
		log.debug(dateFormat.format(date));
		return dateFormat.format(date);
		
	}

	/**
	 * Switch WindowHandles,pageName is the title of page that u want to
	 * switched to
	 */

	public void switchWindowHandles(WebDriver driver, String pageName) {
		for (String winHandle : driver.getWindowHandles()) {

			driver.switchTo().window(winHandle);
			if (driver.getTitle().equalsIgnoreCase(pageName)) {
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				break;
			}

		}
	}
	
	
	public Map<String,String> getBrowserName(WebDriver driver)
	{
		Map<String,String> systemInfo=new HashMap<String,String>();
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
	    String browserName = cap.getBrowserName().toLowerCase();
	    systemInfo.put("browserName", browserName);
	    log.debug(browserName);
	    String os = cap.getPlatform().toString();
	    systemInfo.put("os", os);
	    log.debug(os);
	    String v = cap.getVersion().toString();
	    systemInfo.put("version", v);
	    log.debug(v);
	    return systemInfo;
	}
}
