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

import POJO.BillingInfo;
import POJO.UserInfo;
import junit.framework.Assert;

public class UITestOperations {

	private WebDriver driver;
	private String baseUrl;
	private Wait wait;
	CommonActions common;
	ElementsRepositoryAction elementsRepositoryAction;
	static Logger log = Logger.getLogger(UITestOperations.class.getName());
	public List<UserInfo> users = new ArrayList<UserInfo>();
	public List<BillingInfo> billings = new ArrayList<BillingInfo>();

	public UITestOperations(WebDriver driver) {
		this.driver = driver;
//		baseUrl = "https://demox.mmxreservations.com/";
		wait = new Wait(driver);
		common = PageFactory.initElements(driver, CommonActions.class);
		elementsRepositoryAction = new ElementsRepositoryAction(driver);

		this.users = common.getInformation(1).get("users");
		this.billings = common.getInformation(2).get("billings");
	}
	/**
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
*/
	/// ----------------------------------------------------------

//    private String url_WestCoastBrushedLongSleeve = "Men/T-Shirts-and-Long-Sleeves/p/West-Coast-Brushed-Long-Sleeve/KM031092?color=KM031092-10001";
//	private String url_DoubleTakeButtonUp = "Men/p/Double-Take-Button-Up/KM021086?color=KM021086-10804";
	
	private String url_Remsen_Tee="Men/p/Remsen-Tee/KM021038?color=KM021038-10022";
	private String url_V_Tee="/Men/p/V-Tee/KM021032?color=KM021032-10298";
	
	
	
	private String getBaseURL(){
		String baseURL="";
		String testEnvironment = common.getSettings().getValue("testEnvironment");
		if (testEnvironment.equalsIgnoreCase("dev"))	
			baseURL="https://dev.hybris.kitandace.com/ca/en/";	
		if (testEnvironment.equalsIgnoreCase("stage"))	
			baseURL= "https://staging.hybris.kitandace.com/ca/en/";
//		baseURL= "https://mig.hybris.kitandace.com/ca/en/";   //automation testing only
	
		return baseURL;
		
		
	}
	public void OpenHomepage(WebDriver webdriver) {

		driver.manage().window().maximize();
		driver.get(getBaseURL());

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

	public void doSignSite(UserInfo user) {
		driver.manage().window().maximize();
		OpenHomepage(driver);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// start to login
		// driver.findElement(By.xpath("//button[@class='btn-link' and
		// contains(text(),'Sign in')]")).click();
		common.javascriptClick(driver,
				driver.findElement(By.xpath("//button[@class='btn-link' and contains(text(),'Sign in')]")));
		wait.threadWait(2000);

		driver.findElement(By.id("sign-in-form_email")).clear();
		driver.findElement(By.id("sign-in-form_email")).sendKeys(user.getEmail());
		driver.findElement(By.id("sign-in-form_pwd")).clear();
		driver.findElement(By.id("sign-in-form_pwd")).sendKeys(user.getPassword());
		driver.findElement(By.xpath("//button[@class='btn btn--md btn--default btn--pw signin']")).click();

	}

	public void killAdv() {
		// close first time advertisement
		if (ElementExist(By.xpath(
				"//button[@class='modal__content__close modal__content__close--sm modal__content__close--dark js-modal-close js-newsletter-close']"))) {
			WebElement closeBtn = driver.findElement(By.xpath(
					"//button[@class='modal__content__close modal__content__close--sm modal__content__close--dark js-modal-close js-newsletter-close']"));

			if (closeBtn != null)
				common.javascriptClick(driver, closeBtn);
		}

		wait.threadWait(2000);
	}

	public boolean checkSignInStatus() {

		if (ElementExist(By.xpath("//a[contains(text(),' Account')]")))
			return true;
		else
			return false;
	}

	public boolean doSignOut() {
		WebElement account = driver.findElement(By.xpath("//a[contains(text(),' Account')]"));
		common.javascriptClick(driver, account);
		// account.click();
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

		// common.javascriptClick(driver,
		// driver.findElement(By.xpath("//a[@title='West Coast Brushed Long
		// Sleeve']")));

//		driver.get(getBaseURL()+url_WestCoastBrushedLongSleeve);
		driver.get(getBaseURL()+url_Remsen_Tee);
		
		killAdv();
		// choose size
//		chooseSize("91215", "91216", "91214", "91217", "91213");
		chooseSize("20043", "20044", "20042", "20045", "20041");
		// add to bag
		WebElement btnAdd = driver.findElement(By.xpath(
				"//button[@class='pdp-actions__buttons__button pdp-actions__buttons__button_btn-bag js-pdp-add-to-cart']"));

		common.javascriptClick(driver, btnAdd);

	
//		driver.get(getBaseURL()+url_DoubleTakeButtonUp);
		driver.get(getBaseURL()+url_V_Tee);
		
		wait.threadWait(2000);
		// choose size
//		chooseSize("88604", "88605", "88603", "88606", "88602");
		chooseSize("20007", "20009", "20005", "20011", "20003");
		// add to bag
		btnAdd = driver.findElement(By.xpath(
				"//button[@class='pdp-actions__buttons__button pdp-actions__buttons__button_btn-bag js-pdp-add-to-cart']"));
		common.javascriptClick(driver, btnAdd);
		// driver.get(url);

	}

	public void buyManTshirtsWithAnonymousUser() {
		String url = driver.getCurrentUrl();

		// common.javascriptClick(driver,
		// driver.findElement(By.xpath("//a[@title='West Coast Brushed Long
		// Sleeve']")));
		driver.manage().window().maximize();
//		driver.get(getBaseURL()+url_WestCoastBrushedLongSleeve);
		driver.get(getBaseURL()+url_Remsen_Tee);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		killAdv();
		wait.threadWait(1000);
		// choose size
//		chooseSize("91215", "91216", "91214", "91217", "91213");
		chooseSize("20043", "20044", "20042", "20045", "20041");
		// add to bag
		WebElement btnAdd = driver.findElement(By.xpath(
				"//button[@class='pdp-actions__buttons__button pdp-actions__buttons__button_btn-bag js-pdp-add-to-cart']"));
		common.javascriptClick(driver, btnAdd);
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
	
	
	
	public void buyOnlyShipToCanadaItem() {
		String url = driver.getCurrentUrl();
		String Four_Button_Brushed_Henley_url="Men/p/Four-Button-Brushed-Henley/KM031195?color=KM031195-10022";

		// common.javascriptClick(driver,
		// driver.findElement(By.xpath("//a[@title='West Coast Brushed Long
		// Sleeve']")));
		driver.manage().window().maximize();
		driver.get(getBaseURL()+Four_Button_Brushed_Henley_url);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		killAdv();
		wait.threadWait(1000);
		// choose size
		chooseSize("110007", "110008", "110006", "110009", "110005");
		// add to bag
		WebElement btnAdd = driver.findElement(By.xpath(
				"//button[@class='pdp-actions__buttons__button pdp-actions__buttons__button_btn-bag js-pdp-add-to-cart']"));
		common.javascriptClick(driver, btnAdd);
	}
	

	public void AnonymousCheckOut(UserInfo user, BillingInfo billing) {
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);
//		expandCheckout();
		driver.findElement(By.id("checkout-email")).sendKeys(user.getEmail());
//		btnNext(1);
		addUserInfo(user);
//		btnNext(2);
		addBillInfo(billing);
		driver.findElement(By.xpath("//button[contains(text(),' Place my order')]")).click();
	}

	public void AnonymousCheckOutAfterVerifyFields(UserInfo user, BillingInfo billing) {
		
//		expandCheckout();
		driver.findElement(By.id("checkout-email")).sendKeys(user.getEmail());
		addUserInfo(user);
		addBillInfo(billing);
		// driver.findElement(By.xpath("//button[contains(text(),' Place my
		// order')]")).click();
	}

	public void AnonymousCheckOutAndThenLogin(UserInfo user, BillingInfo billing) {
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.waitElementToBeDisplayed(By.xpath("//button[contains(text(),'Continue')]"));
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);
		driver.findElement(By.id("checkout-email")).sendKeys(user.getEmail());
		wait.threadWait(1000);
		wait.waitElementToBeDisplayed(By.xpath("//a[@class='js-checkout-sign-in']"));
		driver.findElement(By.xpath("//a[@class='js-checkout-sign-in']")).click();
		wait.waitElementToBeDisplayed(By.id("signin-email"));
		// login on the checkout page
		driver.findElement(By.id("signin-email")).clear();
		driver.findElement(By.id("signin-email")).sendKeys(user.getEmail());
		driver.findElement(By.id("signin-password")).sendKeys(user.getPassword());
		driver.findElement(By.xpath("//button[@class='btn btn--default btn--md js-checkout-signin-btn']")).click();

	}

	public void checkOut(UserInfo user, BillingInfo billing) {
		// click bag button
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		// click checkout button
		driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
		
//		expandCheckout();

		// if address already been remembered , then skip this step
		if (!ElementExist(By.xpath("//a[@class='form__add-new-btn pull-right js-add-new-address']")))
			addUserInfo(user);
		addBillInfo(billing);

	}

	public void editOrder() {
		// edit order
		wait.waitElementToBeDisplayed(By.xpath("//a[@class='pull-right js-edit-order']"));
		driver.findElement(By.xpath("//a[@class='pull-right js-edit-order']")).click();

		// click edit for second item
		List<WebElement> btnEdits = driver.findElements(By.xpath("//button[contains(text(),'Edit')]"));
		if (btnEdits != null && btnEdits.size() > 0) {
			btnEdits.get(1).click();
		}

		wait.threadWait(5000);
		// change quanlity for second item
		List<WebElement> btnQuanlity = driver.findElements(By.xpath("//button[@class='counter__btn js-bag-qty-plus']"));

		System.out.println("btnQuanlity: " + btnQuanlity.size());
		if (btnQuanlity != null && btnQuanlity.size() > 0) {
			common.javascriptClick(driver, btnQuanlity.get(1));
		}

		// click update
		List<WebElement> btnUpdate = driver.findElements(By.xpath("//button[contains(text(),'Update')]"));
		if (btnUpdate != null && btnUpdate.size() > 0) {
			common.javascriptClick(driver, btnUpdate.get(1));
		}

	}

	public void editOrderColorSize() {
		// edit order
		wait.waitElementToBeDisplayed(By.xpath("//a[@class='pull-right js-edit-order']"));
		driver.findElement(By.xpath("//a[@class='pull-right js-edit-order']")).click();

		wait.threadWait(5000);
		// click edit for second item
		List<WebElement> btnEdits = driver.findElements(By.xpath("//button[contains(text(),'Edit')]"));
		if (btnEdits != null && btnEdits.size() > 0) {
			btnEdits.get(1).click();
		}

		wait.threadWait(5000);
		// // change quanlity for second item
		// List<WebElement> btnQuanlity =
		// driver.findElements(By.xpath("//button[@class='counter__btn
		// js-bag-qty-plus']"));
		//
		// System.out.println("btnQuanlity: " +btnQuanlity.size());
		// if (btnQuanlity != null && btnQuanlity.size() > 0) {
		// common.javascriptClick(driver, btnQuanlity.get(1));
		// }

		// change color and size
		List<WebElement> colors = driver.findElements(By.xpath("//a[@data-colorcode='KM021038-10038']"));
		common.javascriptClick(driver, colors.get(1));
		wait.threadWait(2000);
		driver.findElement(By.xpath("//button[@data-colorcode='KM021038-10038' and @data-sizecode='35862']")).click();

		// // click update
		// List<WebElement> btnUpdate =
		// driver.findElements(By.xpath("//button[contains(text(),'Update')]"));
		// if (btnUpdate != null && btnUpdate.size() > 0) {
		// common.javascriptClick(driver, btnUpdate.get(1));
		// }

	}

	private void addUserInfo(UserInfo user) {

		// fill in checkout information
		driver.findElement(By.id("checkout-fisrt-name")).clear();
		driver.findElement(By.id("checkout-fisrt-name")).sendKeys(user.getFirstName());
		driver.findElement(By.id("checkout-last-name")).clear();
		driver.findElement(By.id("checkout-last-name")).sendKeys(user.getLastName());
		driver.findElement(By.id("checkout-address-1")).clear();
		driver.findElement(By.id("checkout-address-1")).sendKeys(user.getAddress());
		driver.findElement(By.id("checkout-city")).clear();
		driver.findElement(By.id("checkout-city")).sendKeys(user.getCity());
		driver.findElement(By.id("checkout-zip-code")).clear();
		driver.findElement(By.id("checkout-zip-code")).sendKeys(user.getZip());

		// make selector option visiable so that we can select
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-region-select");
		Select provence = new Select(driver.findElement(By.id("checkout-region-select")));
		provence.selectByIndex(2);

		driver.findElement(By.id("checkout-phone-number")).clear();
		driver.findElement(By.id("checkout-phone-number")).sendKeys(user.getPhone());

		common.javascriptMakeSelectOptionVisiable(driver, "phoneType-select");
		Select phoneType = new Select(driver.findElement(By.id("phoneType-select")));
		phoneType.selectByIndex(2);
		driver.findElement(By.cssSelector("div.checkbox__circle.js-gift-option-checkbox")).click();
		

	}

	private void addBillInfo(BillingInfo billing) {
		driver.findElement(By.xpath("//input[@placeholder='Enter your credit card number']")).clear();
		driver.findElement(By.xpath("//input[@placeholder='Enter your credit card number']"))
				.sendKeys(billing.getCardNum());
		driver.findElement(By.id("checkout-billing-cardholder")).clear();
		driver.findElement(By.id("checkout-billing-cardholder")).sendKeys("Howard");

		new Select(driver.findElement(By.id("ccExpMonth"))).selectByVisibleText(billing.getExpMonth());
		new Select(driver.findElement(By.id("ccExpYear"))).selectByVisibleText(billing.getExpYear());

		driver.findElement(By.cssSelector("input.field.js-credit-card__cvv")).clear();
		driver.findElement(By.cssSelector("input.field.js-credit-card__cvv")).sendKeys(billing.getCvc());

	}

	public void fillInSAbillingInfo(UserInfo user) {
		// fill in checkout information
		driver.findElement(By.id("checkout-billing-fisrt-name")).clear();
		driver.findElement(By.id("checkout-billing-fisrt-name")).sendKeys(user.getFirstName());
		driver.findElement(By.id("checkout-billing-last-name")).clear();
		driver.findElement(By.id("checkout-billing-last-name")).sendKeys(user.getLastName());
		driver.findElement(By.id("checkout-billing-address-1")).clear();
		driver.findElement(By.id("checkout-billing-address-1")).sendKeys(user.getAddress());
		driver.findElement(By.id("checkout-billing-city")).clear();
		driver.findElement(By.id("checkout-billing-city")).sendKeys(user.getCity());
		driver.findElement(By.id("checkout-billing-zip-code")).clear();
		driver.findElement(By.id("checkout-billing-zip-code")).sendKeys(user.getZip());

		// make selector option visiable so that we can select
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-billing-region-select");
		Select provence = new Select(driver.findElement(By.id("checkout-billing-region-select")));
		provence.selectByIndex(2);

		driver.findElement(By.id("checkout-billingAddressForm-phone-number")).clear();
		driver.findElement(By.id("checkout-billingAddressForm-phone-number")).sendKeys(user.getPhone());

		common.javascriptMakeSelectOptionVisiable(driver, "billingAddressForm-phoneType-select");
		Select phoneType = new Select(driver.findElement(By.id("billingAddressForm-phoneType-select")));
		phoneType.selectByIndex(2);
	}

	public void fillInBillingInfo(BillingInfo billing) {
		addBillInfo(billing);
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

	public boolean verifyConfirmationPage() {
		boolean confirmed = true;
		confirmed = ElementExist(By.xpath("//h2[contains(text(),'Thank You')]"));
		confirmed = ElementExist(By.xpath("//span[contains(text(),'howard.zhangkitandace@yahoo.com')]"));
		confirmed = ElementExist(By.xpath("//span[contains(text(),'Your order number is')]"));
		confirmed = ElementExist(By.xpath("//span[contains(text(),'Order Total:')]"));
		confirmed = ElementExist(By.xpath("//span[contains(text(),'Payment Method:')]"));
		confirmed = ElementExist(By.xpath("//div[contains(text(),'we will ship to')]"));
		confirmed = ElementExist(By.xpath("//div[contains(text(),'CONTINUE SHOPPING')]"));
		// check continue shopping button "Women" and "Men"
		confirmed = driver.findElement(By.xpath("//div[@class='continue-shopping-component__buttons']/ul/li[1]/a"))
				.getText().equalsIgnoreCase("Women");
		confirmed = driver.findElement(By.xpath("//div[@class='continue-shopping-component__buttons']/ul/li[2]/a"))
				.getText().equalsIgnoreCase("Men");

		return confirmed;
	}

	public boolean verifyAccountPage() {
		boolean confirmed = true;
		confirmed = driver.getCurrentUrl().toString().contains("my-account");
		confirmed = driver.findElement(By.xpath("//h2/span[1]")).getText().equalsIgnoreCase("Howard");
		confirmed = ElementExist(By.xpath("//a[contains(text(),'Order #')]"));
		confirmed = ElementExist(By.xpath("//div[contains(text(),'Full Name')]"));
		return confirmed;
	}

	public boolean verifyUpdatedQuanlity(int quanlityNum) {
		List<WebElement> quanlity = driver
				.findElements(By.xpath("//table[@class='order-summary__list__i__details']/tbody/tr[2]/td[4]"));
		for (WebElement w : quanlity) {
			if (w.getText() != null && Integer.parseInt(w.getText()) == quanlityNum)
				return true;
		}

		return false;
	}

	public void registerUser(UserInfo user) {
		OpenHomepage(driver);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// start to register
		common.javascriptClick(driver,
				driver.findElement(By.xpath("//button[@class='btn-link' and contains(text(),'Sign in')]")));
		wait.threadWait(2000);
//		driver.findElement(By.xpath("//button[@class='btn btn--md btn--bordered btn--pw create']")).click();
		driver.findElement(By.xpath("//button[@class='btn btn--inverse btn--block create']")).click();
		// fill in the form
		driver.findElement(By.id("create-account-form_firstName")).sendKeys(user.getFirstName());
		driver.findElement(By.id("create-account-form_lastName")).sendKeys(user.getLastName());
		driver.findElement(By.id("create-account-form_email")).sendKeys(user.getEmail());
		driver.findElement(By.id("create-account-form_pwd")).sendKeys(user.getPassword());
		driver.findElement(By.xpath("//button[@class='btn btn--md btn--default btn--pw create-account']")).click();

	}

	public void addUserPaymentDetail(UserInfo user, BillingInfo billing) {
		List<WebElement> btnAccounts = driver.findElements(By.xpath("//a[@href='/ca/en/my-account']"));
		common.javascriptClick(driver, btnAccounts.get(0));
		wait.threadWait(2000);

		driver.findElement(By.xpath("//div[@class='action-btn content-block__button big js-account-new-wallet']"))
				.click();
		// add credit card
		driver.findElement(By.id("edit-wallet-form_firstName")).clear();
		driver.findElement(By.id("edit-wallet-form_firstName")).sendKeys(user.getFirstName());

		driver.findElement(By.id("edit-wallet-form_lastName")).clear();
		driver.findElement(By.id("edit-wallet-form_lastName")).sendKeys(user.getLastName());

		driver.findElement(By.id("edit-wallet-form_addressFirst")).clear();
		driver.findElement(By.id("edit-wallet-form_addressFirst")).sendKeys(user.getAddress());

		driver.findElement(By.id("edit-wallet-form_city")).clear();
		driver.findElement(By.id("edit-wallet-form_city")).sendKeys(user.getCity());

		driver.findElement(By.id("edit-wallet-form_zipCode")).clear();
		driver.findElement(By.id("edit-wallet-form_zipCode")).sendKeys(user.getZip());

		driver.findElement(By.id("edit-wallet-form_phone")).clear();
		driver.findElement(By.id("edit-wallet-form_phone")).sendKeys(user.getPhone());

		driver.findElement(By.id("checkout-billing-cardholder")).clear();
		driver.findElement(By.id("checkout-billing-cardholder")).sendKeys(user.getFirstName());

		common.javascriptMakeSelectOptionVisiable(driver, "edit-wallet-form_province");
		Select provence = new Select(driver.findElement(By.id("edit-wallet-form_province")));
		provence.selectByIndex(2);

		driver.findElement(By.xpath("//input[contains(@class,'cc-card-number__field field js-credit-card__number')]"))
				.clear();
		driver.findElement(By.xpath("//input[contains(@class,'cc-card-number__field field js-credit-card__number')]"))
				.sendKeys(billing.getCardNum());
		new Select(driver.findElement(By.id("ccExpMonth"))).selectByVisibleText(billing.getExpMonth());
		new Select(driver.findElement(By.id("ccExpYear"))).selectByVisibleText(billing.getExpYear());
		driver.findElement(By.id("frmCcCvv")).clear();
		driver.findElement(By.id("frmCcCvv")).sendKeys(billing.getCvc());
		driver.findElement(By.xpath("//button[@class='btn js-account-wallet-save']")).click();
		                                              

	}

	public void addCreditCardWhenCheckOut(UserInfo user, BillingInfo billing) {
		driver.findElement(By.xpath("//a[@class='form__add-new-btn pull-right js-add-new-billing-info']")).click();
		wait.threadWait(1000);
		common.javascriptClick(driver, driver.findElement(By.id("checkout-address-same-as-shipping")));
		wait.threadWait(1000);

		driver.findElement(By.id("checkout-billing-fisrt-name")).clear();
		driver.findElement(By.id("checkout-billing-fisrt-name")).sendKeys(user.getFirstName());

		driver.findElement(By.id("checkout-billing-last-name")).clear();
		driver.findElement(By.id("checkout-billing-last-name")).sendKeys(user.getLastName());

		driver.findElement(By.id("checkout-billing-address-1")).clear();
		driver.findElement(By.id("checkout-billing-address-1")).sendKeys(user.getAddress());

		driver.findElement(By.id("checkout-billing-city")).clear();
		driver.findElement(By.id("checkout-billing-city")).sendKeys(user.getCity());

		driver.findElement(By.id("checkout-billing-zip-code")).clear();
		driver.findElement(By.id("checkout-billing-zip-code")).sendKeys(user.getZip());

		driver.findElement(By.id("checkout-billingAddressForm-phone-number")).clear();
		driver.findElement(By.id("checkout-billingAddressForm-phone-number")).sendKeys(user.getPhone());

		common.javascriptMakeSelectOptionVisiable(driver, "checkout-billing-region-select");
		Select provence = new Select(driver.findElement(By.id("checkout-billing-region-select")));
		provence.selectByIndex(2);

		driver.findElement(By.xpath("//input[contains(@class,'cc-card-number__field field js-credit-card__number')]"))
				.clear();
		driver.findElement(By.xpath("//input[contains(@class,'cc-card-number__field field js-credit-card__number')]"))
				.sendKeys(billing.getCardNum());
		driver.findElement(By.id("checkout-billing-cardholder")).sendKeys(user.getFirstName());
		new Select(driver.findElement(By.id("ccExpMonth"))).selectByVisibleText(billing.getExpMonth());
		new Select(driver.findElement(By.id("ccExpYear"))).selectByVisibleText(billing.getExpYear());
		driver.findElement(By.id("frmCcCvv")).sendKeys(billing.getCvc());

		driver.findElement(By.xpath("//a[@class='btn btn--inverse btn--sm js-add-new-billing-info-btn']")).click();
		                                         
		wait.threadWait(5000);
		// check account
		common.javascriptClick(driver,
				driver.findElement(By.xpath("//a[@class='btn-link' and contains(text(),' Account')]")));
		wait.threadWait(2000);
	}

	public void addCreditCardWhenCheckOutSameAsSA(UserInfo user, BillingInfo billing) {
		driver.findElement(By.xpath("//a[@class='form__add-new-btn pull-right js-add-new-billing-info']")).click();
		wait.threadWait(1000);

		driver.findElement(By.xpath("//input[contains(@class,'cc-card-number__field field js-credit-card__number')]"))
				.clear();
		driver.findElement(By.xpath("//input[contains(@class,'cc-card-number__field field js-credit-card__number')]"))
				.sendKeys(billing.getCardNum());
		driver.findElement(By.id("checkout-billing-cardholder")).sendKeys(user.getFirstName());
		new Select(driver.findElement(By.id("ccExpMonth"))).selectByVisibleText(billing.getExpMonth());
		new Select(driver.findElement(By.id("ccExpYear"))).selectByVisibleText(billing.getExpYear());
		driver.findElement(By.id("frmCcCvv")).sendKeys(billing.getCvc());

		driver.findElement(By.xpath("//a[@class='btn btn--inverse btn--sm js-add-new-billing-info-btn']")).click();
		wait.threadWait(5000);
		// check account
		common.javascriptClick(driver,
				driver.findElement(By.xpath("//a[@class='btn-link' and contains(text(),' Account')]")));
		wait.threadWait(2000);
	}

	public void addAddressWhenCheckOut(UserInfo user, BillingInfo billing) {
		addUserInfo(user);
	}

	public void addUserAddressDetail(UserInfo user, BillingInfo billing) {
		List<WebElement> btnAccounts = driver.findElements(By.xpath("//a[@href='/ca/en/my-account']"));
		common.javascriptClick(driver, btnAccounts.get(0));
		wait.threadWait(2000);

		driver.findElement(By.xpath("//div[@class='action-btn content-block__button big js-account-new-address']"))
				.click();
		// add credit card
		driver.findElement(By.id("edit-address-form_firstName")).clear();
		driver.findElement(By.id("edit-address-form_firstName")).sendKeys(user.getFirstName());

		driver.findElement(By.id("edit-address-form_lastName")).clear();
		driver.findElement(By.id("edit-address-form_lastName")).sendKeys(user.getLastName());

		driver.findElement(By.id("edit-address-form_addressFirst")).clear();
		driver.findElement(By.id("edit-address-form_addressFirst")).sendKeys(user.getAddress());

		driver.findElement(By.id("edit-address-form_city")).clear();
		driver.findElement(By.id("edit-address-form_city")).sendKeys(user.getCity());

		driver.findElement(By.id("edit-address-form_zipCode")).clear();
		driver.findElement(By.id("edit-address-form_zipCode")).sendKeys(user.getZip());

		driver.findElement(By.id("edit-address-form_phone")).clear();
		driver.findElement(By.id("edit-address-form_phone")).sendKeys(user.getPhone());

		common.javascriptMakeSelectOptionVisiable(driver, "province-select");
		Select provence = new Select(driver.findElement(By.id("province-select")));
		provence.selectByIndex(2);

		driver.findElement(By.xpath("//button[@class='button js-account-address-save']")).click();

	}

	public void selectCreditCardWhenCheckOut(BillingInfo billing) {
		// // click checkout button
		// WebElement btnCheckOut =
		// driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		// common.javascriptClick(driver, btnCheckOut);
		wait.waitElementToBeEnabled(By.xpath("//button[contains(text(),'Place my order')]"));
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-card-select");
		Select selectCC = new Select(driver.findElement(By.id("checkout-card-select")));
		selectCC.selectByIndex(1);
		// get select credit card's last 4 number and compare to the original
		// one
		String last4numberOfSelectCard = selectCC.getOptions().get(1).getText().substring(4, 8);
		String last4numberOfBilling = billing.getCardNum().substring(15, 19);
		Assert.assertEquals(last4numberOfSelectCard, last4numberOfBilling);

	}

	public void selectAnotherAddressAndCC(BillingInfo billing) {
		selectCreditCardWhenCheckOut(billing);
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-select-delivery-address");
		Select selectCC = new Select(driver.findElement(By.id("checkout-select-delivery-address")));
		selectCC.selectByIndex(1);
	}

	public void changeCountryToJapanWhenCheckOut() {
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-country-select");
		Select selectCC = new Select(driver.findElement(By.id("checkout-country-select")));
		selectCC.selectByIndex(1);
		wait.waitElementToBeDisplayed(
				By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"));
		driver.findElement(By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"))
				.click();
		String totalprice = driver.findElement(By.xpath("//div[@class='order-summary__price__val pull-right']"))
				.getText();

		Assert.assertTrue(totalprice.contains("¥"));
	}

	
	public void changeCountryToUSAWhenCheckOut() {
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-country-select");
		Select selectCC = new Select(driver.findElement(By.id("checkout-country-select")));
		selectCC.selectByIndex(4);
		wait.waitElementToBeDisplayed(
				By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"));
		driver.findElement(By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"))
				.click();
		String totalprice = driver.findElement(By.xpath("//div[@class='order-summary__price__val pull-right']"))
				.getText();

		Assert.assertTrue(totalprice.contains("$"));
	}
	
	public void changeCountryToWhenCheckOut(int countryNum) {
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-country-select");
		Select selectCC = new Select(driver.findElement(By.id("checkout-country-select")));
		selectCC.selectByIndex(countryNum);
		wait.waitElementToBeDisplayed(
				By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"));
		driver.findElement(By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"))
				.click();
	}
	
	
	public void changeCountryToAUWhenCheckOut() {
		common.javascriptMakeSelectOptionVisiable(driver, "checkout-country-select");
		Select selectCC = new Select(driver.findElement(By.id("checkout-country-select")));
		selectCC.selectByIndex(0);
		wait.waitElementToBeDisplayed(
				By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"));
		driver.findElement(By.xpath("//button[@class='modal__content__ok js-checkout-change-country-close is-active']"))
				.click();
		String totalprice = driver.findElement(By.xpath("//div[@class='order-summary__price__val pull-right']"))
				.getText();

		Assert.assertTrue(totalprice.contains("$"));
	}
	
	
	public void removeOrderFromCheckOutPage(int itemIndex, boolean all, int totalItems) {
		// edit order
		wait.waitElementToBeDisplayed(By.xpath("//a[@class='pull-right js-edit-order']"));
		driver.findElement(By.xpath("//a[@class='pull-right js-edit-order']")).click();

		wait.waitElementToBeDisplayed(By.xpath("//span[@class='icon -close']"));
		// click close button
		List<WebElement> btnRemoveItems = driver.findElements(By.xpath("//span[@class='icon -close']"));
		wait.threadWait(3000);
		if (!all) {
			btnRemoveItems.get(itemIndex + 1).click();
			wait.threadWait(1500);
		} else {
			for (int i = 2; i <= totalItems + 1; i++) {
				btnRemoveItems.get(i).click();
				wait.threadWait(1500);
			}
		}
		// close cart [first one close botton is index 1]
		btnRemoveItems.get(1).click();

	}

	public void AnonymousCheckOutAndVerifyInfo(UserInfo user, BillingInfo billing) {
		common.javascriptClick(driver, driver
				.findElement(By.xpath("//li[@class='sb-tab']/button[@class='btn-link mini-cart js-mini-cart-link']")));
		wait.threadWait(1000);
		// click checkout button
		WebElement btnCheckOut = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
		common.javascriptClick(driver, btnCheckOut);
		driver.findElement(By.id("checkout-email")).sendKeys(user.getEmail());
		addUserInfo(user);
		addBillInfo(billing);
//		verifyAnonymousCheckPage(user, billing);
		checkBasicCheckOutInfo(false);

	}

	public void verifyAnonymousCheckPage(UserInfo user, BillingInfo billing) {

		checkBasicCheckOutInfo(false);
		// try to input user and billing information , if no issue input, that
		// means all the field are exist.
		addUserInfo(user);
		addBillInfo(billing);

	}

	public void checkBasicCheckOutInfo(boolean signedIn) {
		boolean verified = true;
		// checking the number of blocks :
		List<WebElement> webElements = driver.findElements(By.xpath("//legend[@class='form__title']"));
		Assert.assertTrue(webElements != null && webElements.size() >= 2);

		if (!signedIn) {
			// checking where had sign and email
			verified = ElementExist(By.xpath("//div[@class='order-summary__title clearfix']"));
			verified = ElementExist(By.xpath("//a[@class='js-checkout-sign-in']"));
			verified = ElementExist(By.id("checkout-email"));
		}
		// checking order summary
		List<String> titles = new ArrayList<String>();
		titles.add("Subtotal");
		titles.add("Tax");
		titles.add("Order total");
		List<WebElement> summary = driver
				.findElements(By.xpath("//div[@class='order-summary__price__title pull-left']"));
		for (WebElement orderSummary : summary) {

			if (orderSummary.getText().length() > 0)
				verified = titles.contains(orderSummary.getText());
		}
	}

	public void verifyCheckOutPageWhenNOTLogined(UserInfo user, BillingInfo billing) {
		// if all the fields can be input which means all these fields are
		// exsit.
		addUserInfo(user);
		verifyAnonymousCheckPage(user, billing);
	}

	public void verifyDeliveryCost(int selectedIndex, int cost) {
		List<WebElement> checkboxElement = driver
				.findElements(By.xpath("//div[@class='checkbox__circle js-shipping-option-checkbox']"));

		// Assert.assertTrue(checkboxElement.get(selectedIndex).isSelected());

		String costString = driver
				.findElement(By.xpath("//div[@class='order-summary__price__val pull-right transform-uppercase']"))
				.getText();

		Assert.assertTrue(costString.equalsIgnoreCase("$" + Integer.toString(cost)));

	}
	
	
	public void buyUSManTshirtsWithAnonymousUser() {
		String url = driver.getCurrentUrl();		
		String baseURL="https://staging.hybris.kitandace.com/us/en/";
		driver.manage().window().maximize(); 
//		driver.get(baseURL+url_WestCoastBrushedLongSleeve);
		driver.get(baseURL+url_Remsen_Tee);
		wait.WaitUntilPageLoaded();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		killAdv();
		wait.threadWait(1000);
		// choose size
//		chooseSize("91215", "91216", "91214", "91217", "91213");
		chooseSize("20043", "20044", "20042", "20045", "20041");
		// add to bag
		WebElement btnAdd = driver.findElement(By.xpath(
				"//button[@class='pdp-actions__buttons__button pdp-actions__buttons__button_btn-bag js-pdp-add-to-cart']"));
		common.javascriptClick(driver, btnAdd);
	}
	
	
	public void addUserAddressDetail(UserInfo user, BillingInfo billing,int countryCode,int stateCode) {
		List<WebElement> btnAccounts = driver.findElements(By.xpath("//a[@href='/ca/en/my-account']"));
		common.javascriptClick(driver, btnAccounts.get(0));
		wait.threadWait(2000);

		driver.findElement(By.xpath("//div[@class='action-btn content-block__button big js-account-new-address']"))
				.click();
		
		wait.threadWait(1000);
		common.javascriptMakeSelectOptionVisiable(driver, "country-select");
		Select selectCC = new Select(driver.findElement(By.id("country-select")));
		selectCC.selectByIndex(countryCode);
		
		wait.threadWait(1000);
		// add credit card
		driver.findElement(By.id("edit-address-form_firstName")).clear();
		driver.findElement(By.id("edit-address-form_firstName")).sendKeys(user.getFirstName());

		driver.findElement(By.id("edit-address-form_lastName")).clear();
		driver.findElement(By.id("edit-address-form_lastName")).sendKeys(user.getLastName());

		driver.findElement(By.id("edit-address-form_addressFirst")).clear();
		driver.findElement(By.id("edit-address-form_addressFirst")).sendKeys(user.getAddress());

		driver.findElement(By.id("edit-address-form_city")).clear();
		driver.findElement(By.id("edit-address-form_city")).sendKeys(user.getCity());

		driver.findElement(By.id("edit-address-form_zipCode")).clear();
		driver.findElement(By.id("edit-address-form_zipCode")).sendKeys(user.getZip());

		driver.findElement(By.id("edit-address-form_phone")).clear();
		driver.findElement(By.id("edit-address-form_phone")).sendKeys(user.getPhone());

		common.javascriptMakeSelectOptionVisiable(driver, "province-select");
		Select provence = new Select(driver.findElement(By.id("province-select")));
		provence.selectByIndex(stateCode);

		driver.findElement(By.xpath("//button[@class='button js-account-address-save']")).click();

	}
	
	
	
	

//	public void btnNext(int step) {
//		switch (step) {
//		case 1:
//			if (ElementExist(By.xpath("//button[@class='btn btn--default js-step-1']"))) {
//				WebElement btnNextStep = driver.findElement(By.xpath("//button[@class='btn btn--default js-step-1']"));
//				common.javascriptClick(driver, btnNextStep);
//				wait.threadWait(1500);
//			}
//			break;
//		case 2:
//			if (ElementExist(By.xpath("//button[@class='btn btn--default js-step-2']"))) {
//				WebElement btnNextStep = driver.findElement(By.xpath("//button[@class='btn btn--default js-step-2']"));
//				common.javascriptClick(driver, btnNextStep);
//				wait.threadWait(1500);
//			}
//			break;
//
//		default:
//			System.out.println("not match");
//			break;
//		}
//
//	}
	
	
//	public void expandCheckout()
//	{
////		WebElement chBoxDeliveryOption=driver.findElement(By.xpath("//div[contains(text(),'Select your shipping option')]"));
////		WebElement btnPlaceOrder=driver.findElement(By.xpath("//button[contains(text(),' Place my order')]"));
////		WebElement shopInfo=driver.findElement(By.xpath("//legend[contains(text(),'Shipping Information')]"));
////		WebElement paymentInfo=driver.findElement(By.xpath("//legend[contains(text(),'Payment Information')]"));
//		try{
//			if(!ElementClickable(By.xpath("//div[contains(text(),'Select your shipping option')]")))
//			{
//			driver.findElement(By.xpath("//legend[contains(text(),'Shipping Information')]")).click();
//			}
//			
//			if(!ElementClickable(By.xpath("//label[@for='checkout-card-select']")) && !ElementClickable(By.xpath("//div[contains(text(),'Credit Card Information')]")))
//			driver.findElement(By.xpath("//legend[contains(text(),'Payment Information')]")).click();
//
//	
//		}catch(Exception e)
//		{
//			System.out.println("expand failed");
//			e.printStackTrace();
//		}
//		
//		wait.threadWait(2000);
//		
//	}

	public boolean ElementExist(By Locator) {
		try {
			driver.findElement(Locator);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			return false;
		}
	}
	
	
	public boolean ElementDisplayed(By Locator){		
		try {
			driver.findElement(Locator).isDisplayed();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean ElementClickable(By Locator){		
		try {
			driver.findElement(Locator).click();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
	
}