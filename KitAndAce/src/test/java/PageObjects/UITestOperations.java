package PageObjects;

import java.util.ArrayList;
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

public class UITestOperations {

	private WebDriver driver;
	private String baseUrl;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	static Logger log = Logger.getLogger(UITestOperations.class.getName());

	public UITestOperations(WebDriver driver) {
		this.driver = driver;
		baseUrl = "https://demox.mmxreservations.com/";
		wait = new Wait(driver);
		common = PageFactory.initElements(driver, CommonActions.class);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);
	}

	public void doSignIn(String username, String psw) {
		driver.manage().window().maximize();
		driver.get("http://www.shoeme.ca/");
		// driver.get("http://www.shoes.com/");
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//a[@id='signin-link']/span")).click();
		wait.threadWait(2000);

		elementsRepositoryAction.getElement("TED_ShoesCOM_signInBtn").click();
		wait.threadWait(2000);
		driver.findElement(By.id("customer_email")).clear();
		driver.findElement(By.id("customer_email")).sendKeys(username);
		driver.findElement(By.id("customer_password")).clear();
		driver.findElement(By.id("customer_password")).sendKeys(psw);
		driver.findElement(By.xpath("//input[@value='Log In']")).click();

	}

	// public boolean doSignOut() {
	// wait.threadWait(5000);
	// common.javascriptClick(driver,
	// driver.findElement(By.xpath("//a[@data-i18n='localize_log-out']")));
	//
	// return checkSignOutStatus();
	// }

	public boolean checkSignOutStatus() {
		return !driver.getCurrentUrl().contains("sid");
	}

	public void navigationToManShoes() {
		WebElement w = driver.findElement(By.xpath("//a[@data-i18n='localize_waterproof']"));
		common.javascriptClick(driver, w);

	}

	public void filterShoes() {
		driver.findElement(By.xpath("//span[@data-i18n='localize_nxt-8.5']")).click();
	}

	public void selectItem() {
		driver.findElement(By
				.xpath("//img[contains(@src,'https://cdn.shopify.com/s/files/1/0121/3322/products/176643_1000_45_3b288327-be1e-4090-a98a-933f96a98232_medium.jpeg?v=1458761530')]"))
				.click();
		wait.threadWait(10000);
		driver.findElement(By.xpath("//div[@id='size-options']/div[4]")).click();
		driver.findElement(By.xpath("//div[@id='width-options']/div")).click();
	}

	public void addItem() {

		driver.findElement(By.id("add")).click();
	}

	public void addToWishList() {
		driver.findElement(By.cssSelector("#add-to-wishlist > a > span")).click();
	}

	public boolean checkShoppingCart() {
		String bagCount = driver.findElement(By.xpath("//span[@id='bag-count']")).getText();
		if (bagCount != null && !bagCount.equalsIgnoreCase(""))
			return (Integer.parseInt(bagCount) > 0 ? true : false);
		else
			return false;
	}

	public boolean checkWishList() {
		String wishCount = driver.findElement(By.xpath("//span[@id='wishlist-count']")).getText();
		if (wishCount != null && !wishCount.equalsIgnoreCase(""))
			return (Integer.parseInt(wishCount) > 0 ? true : false);
		else
			return false;
	}

	public void checkShoppingCartContent() {
		driver.findElement(By.id("bag-count")).click();
		driver.findElement(By.xpath("//form[@id='cart-form']/div[2]/div/div[2]/div/div[2]/a/span")).click();

	}

	public void checkWishListContent() {
		driver.findElement(By.cssSelector("#wishlist-link > span.localize.util-text")).click();

	}

	public boolean searchShoe() {
		driver.get("http://www.shoeme.ca/");
		wait.threadWait(5000);
		driver.findElement(By.id("search-field")).clear();
		driver.findElement(By.id("search-field")).sendKeys("Men's Moab Ventilator");
		driver.findElement(By.xpath("//div[@id='search-submit']/i")).click();
		wait.threadWait(5000);
		String result = driver.findElement(By.cssSelector("b.nxt-result-total")).getText();
		if (result != null && !result.equalsIgnoreCase(""))
			return (Integer.parseInt(result) > 0 ? true : false);
		else
			return false;
	}

	/// -----------------------------------

	public void OpenShoeCOMUSA(WebDriver webdriver) {

		driver.manage().window().maximize();
		driver.get("http://www.shoeme.ca/");
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// close first time advertisement
		if (ElementExist(By.xpath("//button[@data-i18n='localize_continue-shopping']"))) {
			List<WebElement> continueBtn = driver
					.findElements(By.xpath("//button[@data-i18n='localize_continue-shopping']"));
			if (continueBtn != null && continueBtn.size() > 0) {
				common.javascriptClick(webdriver, continueBtn.get(0));
				common.javascriptClick(webdriver, continueBtn.get(1));
			}

		}

		wait.threadWait(2000);
		// Go to careers
		driver.findElement(By.xpath("//a[@id='signin-link']/span")).click();
		WebElement Careerbutton = webdriver.findElement(By.xpath("//a[@data-i18n='localize_careers']"));
		Careerbutton.click();

		String CareerURL = webdriver.getCurrentUrl();
		if (CareerURL.contains("careers")) {

			WebElement ShippingToCanadaBox = webdriver.findElement(By.xpath("//a[@id='closeButton']"));// a
																										// id
																										// =closeButton

			ShippingToCanadaBox.click();
			wait.threadWait(2000);
			// open shoe.com
			WebElement ShoeComUSAButton = webdriver.findElement(By.xpath("//i[@class='icon-shoes-large']"));
			ShoeComUSAButton.click();

		}

	}

	public void changeToShipToUSA() {
		if (!ElementExist(By.xpath("//span[@class='nav-labels' and contains(text(),'My Account')]"))) {
			// change to ship to USA
			driver.findElement(By.cssSelector("img.nav-locale-flag")).click();
			wait.threadWait(2000);
			new Select(driver.findElement(By.id("Input_CountryCode"))).selectByVisibleText("United States");
			driver.findElement(By.cssSelector("button.submit.modalSubmit")).click();
			wait.threadWait(2000);

		}

		if (ElementExist(By.xpath("//a[contains(text(),'CLOSE')]"))) {
			driver.findElement(By.xpath("//a[contains(text(),'CLOSE')]")).click();
			wait.threadWait(2000);
		}
	}

	public void doSignInUSsite(String username, String psw) {
		driver.manage().window().maximize();
		// driver.get("http://www.shoes.com/");
		OpenShoeCOMUSA(driver);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		changeToShipToUSA();

		// start to login
		driver.findElement(By.xpath("//span[@class='nav-labels' and contains(text(),'My Account')]")).click();
		wait.threadWait(2000);

		WebElement element = driver
				.findElement(By.xpath("//div[@class='dropdown__menuInner']/descendant::a[contains(text(),'Sign In')]"));
		common.javascriptClick(driver, element);
		wait.threadWait(2000);
		driver.findElement(By.id("Input_Email")).clear();
		driver.findElement(By.id("Input_Email")).sendKeys(username);
		driver.findElement(By.id("Input_Password")).clear();
		driver.findElement(By.id("Input_Password")).sendKeys(psw);
		driver.findElement(By.xpath("//button[@value='Sign In']")).click();

	}

	public void doSignInUSsiteAgain(String username, String psw) {
		// start to login
		driver.findElement(By.xpath("//span[@class='nav-labels' and contains(text(),'My Account')]")).click();
		wait.threadWait(2000);

		WebElement element = driver
				.findElement(By.xpath("//div[@class='dropdown__menuInner']/descendant::a[contains(text(),'Sign In')]"));
		common.javascriptClick(driver, element);
		wait.threadWait(2000);
		driver.findElement(By.id("Input_Email")).clear();
		driver.findElement(By.id("Input_Email")).sendKeys(username);
		driver.findElement(By.id("Input_Password")).clear();
		driver.findElement(By.id("Input_Password")).sendKeys(psw);
		driver.findElement(By.xpath("//button[@value='Sign In']")).click();

	}

	public boolean checkUSSignInStatus() {
		List<WebElement> signOuts = driver.findElements(By.xpath("//a[contains(text(),'Sign Out')]"));
		if (signOuts != null && signOuts.size() > 0)
			return true;
		else
			return false;
	}

	public boolean doSignOutUS() {
		common.javascriptClick(driver, driver.findElement(
				By.xpath("//div[@class='dropdown__menuInner']/descendant::a[contains(text(),'Sign Out')]")));
		// wait.threadWait(3000);
		return !checkUSSignInStatus();
	}

	public boolean searchShoeUS() {
		driver.get("http://www.shoes.com/");
		wait.threadWait(5000);
		// List<WebElement>
		// searchInput=driver.findElements(By.xpath("//div[@class='search-container']/input[@id='searchstring']"));
		// List<WebElement>
		// searchInput=driver.findElements(By.xpath("//input[@id='searchstring']"));
		driver.findElement(By.cssSelector("#frmTextSearchlg > #searchstring")).clear();
		driver.findElement(By.cssSelector("#frmTextSearchlg > #searchstring")).sendKeys("Men's Moab Ventilator");

		driver.findElement(By.xpath("//*[@id='nav-search-expanded-trigger']/i")).click();
		wait.threadWait(5000);
		String result = driver.findElement(By.cssSelector("div.row > #productListCount > b")).getText();
		log.debug("search result is : " + result);
		if (result != null && !result.equalsIgnoreCase(""))
			return (Integer.parseInt(result) > 0 ? true : false);
		else
			return false;
	}

	public void navigationToManShoesUS() {
		List<WebElement> w = driver.findElements(By.xpath("//a[contains(text(),'Boots')]"));
		if (w != null && w.size() > 0)
			common.javascriptClick(driver, w.get(0));

	}

	public void filterShoesUS() {
		driver.findElement(By.xpath("//a[@data-facetval='adultfootwear:8.5']")).click();
	}

	/// ----------------------------------------------------------
	public void OpenHomepage(WebDriver webdriver) {

		driver.manage().window().maximize();
		String testEnvironment = common.getSettings().getValue("testEnvironment");
		if (testEnvironment.equalsIgnoreCase("dev"))
			driver.get("https://dev.hybris.kitandace.com/ca/en/");
		if (testEnvironment.equalsIgnoreCase("stage"))
			driver.get("https://staging.hybris.kitandace.com/ca/en/");

		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// close first time advertisement
		if (ElementExist(By.xpath(
				"//button[@class='modal__content__close modal__content__close--sm modal__content__close--dark js-modal-close js-newsletter-close']"))) {
			WebElement closeBtn = driver.findElement(By.xpath(
					"//button[@class='modal__content__close modal__content__close--sm modal__content__close--dark js-modal-close js-newsletter-close']"));

			if (closeBtn != null)
				common.javascriptClick(webdriver, closeBtn);
		}

		wait.threadWait(2000);

	}

	public void doSignSite(String username, String psw) {
		driver.manage().window().maximize();
		OpenHomepage(driver);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// start to login
		driver.findElement(By.xpath("//button[@class='btn-link' and contains(text(),'Sign in')]")).click();
		wait.threadWait(2000);

		driver.findElement(By.id("sign-in-form_email")).clear();
		driver.findElement(By.id("sign-in-form_email")).sendKeys(username);
		driver.findElement(By.id("sign-in-form_pwd")).clear();
		driver.findElement(By.id("sign-in-form_pwd")).sendKeys(psw);
		driver.findElement(By.xpath("//button[@class='btn btn--md btn--default btn--pw signin']")).click();

	}

	public boolean checkSignInStatus() {

		if (ElementExist(By.xpath("//a[contains(text(),' Account')]")))
			return true;
		else
			return false;
	}

	public boolean doSignOut() {
		WebElement account = driver.findElement(By.xpath("//a[contains(text(),' Account')]"));
		account.click();
		driver.findElement(By.xpath("//a[@class='account-sign-out-button']")).click();

		// wait.threadWait(3000);
		return !checkSignInStatus();
	}

	public void naviToManTShirts() {
		common.javascriptClick(driver, driver.findElement(By.xpath("//a[@href='/ca/en/c/Men/men' and @title='Men']")));
		List<WebElement> tshirtBtns = driver.findElements(By.xpath("//a[@title='T-Shirts']"));
		if (tshirtBtns != null && tshirtBtns.size() > 1) {
			tshirtBtns.get(1).click();
		}
	}

	public void buy2ManTshirts() {
		String url = driver.getCurrentUrl();
		common.javascriptClick(driver, driver.findElement(By.xpath("//a[@title='West Coast Brushed Long Sleeve']")));

		// choose size
		chooseSize("91215", "91216", "91214", "91217", "91213");
		// add to bag
		driver.findElement(By
				.xpath("//button[@class='pdp-actions__buttons__button pdp-actions__buttons__button_btn-bag js-pdp-add-to-cart']"))
				.click();

		// buy second one
		log.debug("url: " + url);
		driver.get(url);
		common.javascriptScrollPage(driver, 5000);
		wait.threadWait(2000);
		common.javascriptClick(driver, driver.findElement(By.xpath("//a[@title='Double Dose Tee']")));
		// choose size
		chooseSize("69056", "69057", "69055", "69058", "69054");
		// add to bag
		driver.findElement(By
				.xpath("//button[@class='pdp-actions__buttons__button pdp-actions__buttons__button_btn-bag js-pdp-add-to-cart']"))
				.click();
		driver.get(url);

	}

	public void chooseSize(String... dataCode) {
		List<WebElement> size = new ArrayList<WebElement>();
		size.add(driver.findElement(By.xpath("//li[@data-code='" + dataCode[0] + "' and @data-size='M']")));
		size.add(driver.findElement(By.xpath("//li[@data-code='" + dataCode[1] + "' and @data-size='L']")));
		size.add(driver.findElement(By.xpath("//li[@data-code='" + dataCode[2] + "' and @data-size='S']")));
		size.add(driver.findElement(By.xpath("//li[@data-code='" + dataCode[3] + "' and @data-size='XL']")));
		size.add(driver.findElement(By.xpath("//li[@data-code='" + dataCode[4] + "' and @data-size='XS']")));

		for (WebElement w : size) {
			if (!w.getAttribute("data-online").equalsIgnoreCase("outOfStock")) {
				w.click();
				break;
			}
		}
	}

	public void checkOut() {
		// click bag button
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		// click checkout button
		driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();

		// if address already been remembered , then skip this step
		if (!ElementExist(By.xpath("//a[@class='form__add-new-btn pull-right js-add-new-address']"))) {
			// fill in checkout information
			driver.findElement(By.id("checkout-fisrt-name")).clear();
			driver.findElement(By.id("checkout-fisrt-name")).sendKeys("Howard");
			driver.findElement(By.id("checkout-last-name")).clear();
			driver.findElement(By.id("checkout-last-name")).sendKeys("Zhang");
			driver.findElement(By.id("checkout-address-1")).clear();
			driver.findElement(By.id("checkout-address-1")).sendKeys("1234 Any street");
			driver.findElement(By.id("checkout-city")).clear();
			driver.findElement(By.id("checkout-city")).sendKeys("ANY");
			driver.findElement(By.id("checkout-zip-code")).clear();
			driver.findElement(By.id("checkout-zip-code")).sendKeys("V6B0Z0");

			// make selector option visiable so that we can select
			common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
			Select provence = new Select(driver.findElement(By.id("checkout-region-select")));
			provence.selectByIndex(2);

			driver.findElement(By.id("checkout-phone-number")).clear();
			driver.findElement(By.id("checkout-phone-number")).sendKeys("7780000000");

			common.javascriptMakeSelectOptionVisiable(driver, "phoneType-select");
			Select phoneType = new Select(driver.findElement(By.id("phoneType-select")));
			phoneType.selectByIndex(2);

		}

		driver.findElement(By.cssSelector("div.checkbox__circle.js-gift-option-checkbox")).click();
		driver.findElement(By.xpath("//input[@placeholder='Enter your credit card number']")).clear();
		driver.findElement(By.xpath("//input[@placeholder='Enter your credit card number']"))
				.sendKeys("5500 0000 0000 0004");
		driver.findElement(By.id("checkout-billing-cardholder")).clear();
		driver.findElement(By.id("checkout-billing-cardholder")).sendKeys("Howard");
		//new release changed the exp to selection rather than input 
//		driver.findElement(By.xpath("//input[@placeholder='Month/Year']")).clear();
//		driver.findElement(By.xpath("//input[@placeholder='Month/Year']")).sendKeys("08/18");
		new Select(driver.findElement(By.id("ccExpMonth"))).selectByVisibleText("8 - AUG");
		new Select(driver.findElement(By.id("ccExpYear"))).selectByVisibleText("2018");

		driver.findElement(By.cssSelector("input.field.js-credit-card__cvv")).clear();
		driver.findElement(By.cssSelector("input.field.js-credit-card__cvv")).sendKeys("737");
		driver.findElement(By.xpath("//button[contains(text(),' Place my order')]")).click();

	}

	public String getOrderNumber() {
		String orderNumber = "";
		wait.waitElementToBeDisplayed(By.xpath("//span[contains(text(),'Your order number is')]"));
		String orderString = driver.findElement(By.xpath("//span[contains(text(),'Your order number is')]")).getText();
		if (orderString != null) {
			String[] spilteString = orderString.split(" ");
			orderNumber = spilteString[spilteString.length - 1];
		}
		log.debug("order place string is: " + orderString);
		log.debug("spilted order number is: " + orderNumber);
		return orderNumber;
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