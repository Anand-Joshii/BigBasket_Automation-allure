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

public class ProductPagePObjects extends Waits {

	WebDriver driver;

	public ProductPagePObjects(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private Actions action;

	@FindBy(xpath = "//h1[@class='GrE04']")
	private WebElement productName;

	// //td[@data-qa='productPrice']
	@FindBy(xpath = "//td[@data-qa='productPrice']")
	private WebElement price;

	// //div[@data-qa='addToBasket']
	@FindBy(xpath = "//div[@data-qa='addToBasket']")
	private WebElement addToBasket;

	// //div[@data-qa='myBasket']
	@FindBy(xpath = "//div[@data-qa='myBasket']")
	private WebElement basketIcon;

	// //div[@class='_3eLxX']
	@FindBy(xpath = "//div[@class='_3eLxX']")
	private List<WebElement> basketProdNames;

	// //div[@class='_1lvmT']
	@FindBy(xpath = "//div[@class='_2VA-h']")
	private List<WebElement> basketProdContainers;

	// //div[@class='_2jeTu']
	@FindBy(xpath = "//div[@class='_2jeTu']/div[1]")
	private List<WebElement> basketProdCosts;

	// //a[@data-qa='viewBasketCheckout']
	@FindBy(xpath = "//a[@data-qa='viewBasketCheckout']")
	private WebElement basketCheckout;

	@FindBy(xpath = "//input[@id='input']")
	private WebElement searchBar;

	@FindBy(xpath = "//li[@qa='prodListAS']//p[@qa='prodNameAS']")
	private List<WebElement> prodList;

	@FindBy(id = "search-found")
	private WebElement searchResultContainer;

	// //div[@data-qa='add']
	@FindBy(xpath = "//div[@data-qa='add']")
	private WebElement addQuantity;

	// //div[@data-qa='addedQuantity']
	@FindBy(xpath = "//div[@data-qa='addedQuantity']")
	private WebElement quantity;

	// //div[@class='Mbz4m'][1]//span[2]
	@FindBy(xpath = "//div[@class='Mbz4m'][1]//span[2]")
	private WebElement subTotal;

	By productContainers = By.xpath("//div[@class='sOXt1']/div/div");
	By basketCheckOut = By.xpath("//a[@data-qa='viewBasketCheckout']");
	By toast = By.xpath("//div[@class='_3pK87']");
	By quantityStale = By.xpath("//div[@data-qa='addedQuantity']");
	By subTOTAL = By.xpath("//div[@class='Mbz4m'][1]//span[2]");
	By stale = By.xpath("//div[@class='_287jQ']");

	public void searchProdandClick(String keyword) {
		searchBar.clear();
		searchBar.sendKeys(keyword);
		waitForVisibility(searchResultContainer);
		Iterator<WebElement> it = prodList.iterator();
		while (it.hasNext()) {
			String next = it.next().getText().toLowerCase();
			if (next.contains(keyword.toLowerCase())) {
				it.next().click();
				break;
			}
		}
	}

	public String verifyProductName() {
		return productName.getText();
	}

	public void addToBasket() {
		System.out.println("Clicking on add to basket button");
		addToBasket.click();
	}

	public boolean verifyProductInBasketAndSubTotal(int count) {
		action = new Actions(driver);
		boolean check = false;
		float actualPrice = Float.parseFloat(price.getText().split(" ")[1]);
		action.moveToElement(basketIcon).build().perform();
		waitForAllVisibility(basketProdContainers);
		Iterator<WebElement> it = basketProdNames.iterator();
		ArrayList<String> prods = new ArrayList<String>();
		while (it.hasNext())
			prods.add(it.next().getText());
		System.out.println(prods.get(0));
		float total = Float.parseFloat(subTotal.getText().split(" ")[1]);
		if (total == (actualPrice * (count + 1)))
			check = true;
		return check;
	}

	public void clickBasketCheckout() {
		basketCheckout.click();
	}

	public int verifyAddProductCount(int count) throws InterruptedException {
		if (count > 0) {
			int temp = 0;
			while (temp < count) {
				Thread.sleep(3000);
				addQuantity.click();
				temp++;
			}
		}
		waitForStaleElement(quantityStale);
		int result = Integer.parseInt(quantity.getText());
		return result;
	}

//	public boolean verifySubTotal(int count) throws InterruptedException {
//		action = new Actions(driver);
//		boolean check = false;
//		float actualPrice = Float.parseFloat(price.getText().split(" ")[1]);
//		action.moveToElement(basketIcon).build().perform();
//		waitForAllVisibility(basketProdContainers);
//		float total = Float.parseFloat(subTotal.getText().split(" ")[1]);
//		if(total == (actualPrice*(count+1)))
//			check=true;
//		return check;
//	}

}
