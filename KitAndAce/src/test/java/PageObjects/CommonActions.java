package PageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import HMC.TestCheckOrder;
import POJO.BillingInfo;
import POJO.UserInfo;

public class CommonActions {

	private ParseProperties settings;
	private String parsePath = "src\\test\\resources\\Setting.properties";
	static Logger log = Logger.getLogger(CommonActions.class.getName());
	private static String filepath = "src\\test\\resources\\information\\";

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
	
	public void javascriptInputInToTextArea(WebDriver driver,WebElement ele,String content)
	{
		String js = "arguments[0].value=\""+ content + "\"";
		((JavascriptExecutor) driver).executeScript("arguments[0].enabled = true", ele);	 
 		((JavascriptExecutor)driver).executeScript(js, ele);
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
	
	//typePojo  1.UserInfo;  2.BillingInfo
	private Map<String,List> getFromXlsFile(String fileName,int typePojo)
	{
		Map<String,List> xlsMap=new HashMap<String,List>();
		List<UserInfo> users=new ArrayList<UserInfo>();
		List<BillingInfo> billings=new ArrayList<BillingInfo>();
		try {

			FileInputStream file = new FileInputStream(new File(filepath + fileName));

			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				switch (typePojo) {
				case 1: users.add(assignUser(row));
				        break;
				case 2: billings.add(assignBilling(row));
		                break;
					
			}
				// For each row, iterate through each columns
//				Iterator<Cell> cellIterator = row.cellIterator();
//				while (cellIterator.hasNext()) {
//
//					Cell cell = cellIterator.next();
//
//					switch (cell.getCellType()) {
//					case Cell.CELL_TYPE_BOOLEAN:
//						System.out.print(cell.getBooleanCellValue() + "\t\t");
//						break;
//					case Cell.CELL_TYPE_NUMERIC:
//						System.out.print(cell.getNumericCellValue() + "\t\t");
//						break;
//					case Cell.CELL_TYPE_STRING:
//						System.out.print(cell.getStringCellValue() + "\t\t");
//						break;
//					}
//				}
//				System.out.println("");
			}
			xlsMap.put("users", users);
			xlsMap.put("billings", billings);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return xlsMap;
	}
	
	
	private UserInfo assignUser(Row row){
		UserInfo user=new UserInfo();
		user.setFirstName(row.getCell(0).toString());
		user.setLastName(row.getCell(1).toString());
		user.setAddress(row.getCell(2).toString());
		user.setCity(row.getCell(3).toString());
		user.setZip(row.getCell(4).toString());
		user.setPhone(row.getCell(5).toString());
		user.setEmail(row.getCell(6).toString());
		user.setPassword(row.getCell(7).toString());
		user.setSystem(row.getCell(8).toString());
		return user;
	}
	
	private BillingInfo assignBilling(Row row){
		BillingInfo billing=new BillingInfo();
		billing.setCardNum((row.getCell(0).toString()));
		billing.setExpMonth(row.getCell(1).toString());
		billing.setExpYear(row.getCell(2).toString().substring(0,4));
		billing.setCvc(row.getCell(3).toString().substring(0,3));
		return billing;
	}
	
	//typePojo  1.UserInfo;  2.BillingInfo
	public Map<String,List> getInformation(int typePojo)
	{
		Map<String,List> xlsMap=new HashMap<String,List>();
		switch (typePojo) {
		case 1:  xlsMap=getFromXlsFile("UserInfo.xls",1);
		        break;
		case 2: xlsMap=getFromXlsFile("BillingInfo.xls",2);
                break;
			
	     }
		
		return xlsMap;
	}
}
