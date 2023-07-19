package PageObjects;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Reusable.Waits;
import java.util.HashMap;

public class HomePageObjects extends Waits {

	public WebDriver driver;
	public String message;

	public HomePageObjects(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "search-found")
	private WebElement searchResultContainer;

	@FindBy(xpath = "//a[@class='bb-logo change-logo hidden-xs hidden-sm']")
	private WebElement logo;

	@FindBy(xpath = "//input[@id='input']")
	private WebElement searchBar;

	@FindBy(xpath = "//li[@qa='prodListAS']//p[@qa='prodNameAS']")
	private List<WebElement> prodList;

	@FindBy(xpath = "//div[@class='toast-title']")
	private WebElement toastMessage;

	@FindBy(css = ".cart-items")
	private WebElement basketContainer;

	@FindBy(xpath = "//li[@qa='itemsListMB']//a[@qa='prodNameMB']")
	private List<WebElement> basketProdList;

	@FindBy(xpath = "//div[@class='toast-title']")
	private WebElement prodQuantity;

	@FindBy(xpath = "//a[@qa='myBasket']//span[@title='Your Basket']")
	private WebElement prodBasketIcon;

	@FindBy(xpath = "//div[@class='row mrp']/span")
	private List<WebElement> costList;

	@FindBy(xpath = "//div[@id='navbar-main']")
	private WebElement dummyClick;

	@FindBy(xpath = "//button[@qa='viewBasketMB']")
	private WebElement viewBasketButton;

	@FindBy(xpath = "//input[@id='otpEmail']")
	private WebElement mobileInput;

	@FindBy(xpath = "//button[@class='btn btn-default login-btn']")
	private WebElement loginButton;

	@FindBy(xpath = "//button[.='Verify & Continue']")
	private WebElement afterOTPLoginButton;

	@FindBy(xpath = "//button[@id='headlessui-menu-button-:r6:']")
	private WebElement profileButton;

	@FindBy(xpath = "//a[@ng-show='vm.newLoginFlow']")
	private WebElement homePageLoginButton;
	
	@FindBy(xpath = "//div[@class='form-inline']")
	private List<WebElement> searchProdContainersList;

	By addCartSearchButton = By.xpath("//button[@qa='addBtnAS']");
	By basketProds = By.xpath("//li[@qa='itemsListMB']//a[@qa='prodNameMB']");
	By basketButton = By.xpath("//button[@qa='viewBasketMB']");
	By otpLoginButton = By.xpath("//button[.='Verify & Continue']");
	By searchProdList = By.xpath("//li[@qa='prodListAS']//p[@qa='prodNameAS']");
	By costListby = By.xpath("//div[@class='row mrp']/span");
	By searchProdContainers = By.xpath("//div[@class='form-inline']");

	// Clear the search bar and enter the product
	public void searchProd(String keyword) {
		searchBar.clear();
		searchBar.sendKeys(keyword);
	}

	/*
	 * Get the WebElements - Products from search bar, get the text from the
	 * elements Return the list of products
	 */
	public ArrayList<String> getProdsList(String product) {
		searchProd(product);
		ArrayList<String> li = new ArrayList<String>();
		waitForAllVisibility(searchProdContainersList);
		Iterator<WebElement> it = prodList.iterator();
		while (it.hasNext())
			li.add(it.next().getText());
		return li;
	}

	/*
	 * Get the product list from the above method. Check if the list contains the
	 * product presence. Returns True if present else returns false
	 */
	public boolean validateProductInSearch(String product) {

		ArrayList<String> prods = getProdsList(product);
		Iterator<String> it = prods.iterator();
		boolean presence = false;
		while (it.hasNext()) {
			String iter = it.next().toLowerCase();
			if (iter.contains(product.toLowerCase())) {
				presence = true;
				break;
			}
		}
		return presence;
	}

	/*
	 * Looks for the product present in the results from search bar. Clicks on add
	 * button for first occurance of the product in search results Returns the toast
	 * message for successful addition of the product
	 */
	public boolean addCartSearchButton(String product) {
		searchBar.clear();
		searchBar.sendKeys(product);
		waitForVisibility(searchResultContainer);
		Iterator<WebElement> it = prodList.iterator();
		while (it.hasNext()) {
			String next = it.next().getText().toLowerCase();
			if (next.contains(product.toLowerCase())) {
				it.next().findElement(addCartSearchButton).click();
				break;
			}
		}
		return toastMessage.isDisplayed();
	}

	/* Returns products with their price present in the basket */
	public HashMap<String, Float> checkBasket() throws InterruptedException {

		HashMap<String, Float> map = new HashMap<String, Float>();
		Actions action = new Actions(driver);
		int i = 0;
		while (i != 5) {
			action.moveToElement(dummyClick).click().build().perform();
			i++;
		}
		action.moveToElement(prodBasketIcon).perform();
		waitForVisibility(basketContainer);
		waitForStaleElements(costListby);
		Iterator<WebElement> it1 = basketProdList.iterator();
		Iterator<WebElement> it2 = costList.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			map.put(it1.next().getText(), Float.parseFloat(it2.next().getText()));
		}
		return map;
	}

	/* Clicks on the basket button to map onto Login page/functionality */
	public void basketCheckoutIconClick() {

		waitForStaleElement(basketButton);
		viewBasketButton.click();

	}

	/*
	 * Send mobile number on the login page. After clicking on login button waits
	 * for the OTP to be entered. Clicks on login button after entering OTP
	 */
	public void loginFunctionalityFromBasket(String mobileNumber) {
		waitForVisibility(mobileInput);
		mobileInput.sendKeys(mobileNumber);
		loginButton.click();
		boolean check = true;
		while (check) {
			if (afterOTPLoginButton.isEnabled()) {
				afterOTPLoginButton.click();
				check = false;
			}
		}

	}

	public void login(String number) {
		homePageLoginButton.click();
		loginFunctionalityFromBasket(number);
	}
}
