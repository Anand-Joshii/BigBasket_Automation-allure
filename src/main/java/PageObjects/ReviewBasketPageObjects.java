package PageObjects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

import Reusable.Waits;

public class ReviewBasketPageObjects extends Waits {

	private WebDriver driver;

	public ReviewBasketPageObjects(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(@class,'BasketDescription')]")
	private List<WebElement> prodList;

	@FindBy(xpath = "//span[@class='Label-sc-15v1nk5-0 BasketTotalPrice___StyledLabel-sc-lf15n8-1 gJxZPQ gvOA-DF']")
	private List<WebElement> costList;

	@FindBy(xpath = "//ul[@class='BasketGroup___StyledUl-sc-obttrd-0 kXTIrq']")
	private WebElement prodContainer;

	@FindBy(xpath = "//span[@class='Label-sc-15v1nk5-0 BasketValue___StyledLabel2-sc-1etqe0r-3 gJxZPQ lcDdWp']")
	private WebElement subTotal;

	@FindBy(xpath = "//button[.='Checkout']")
	private WebElement checkoutButton;

	@FindBy(xpath = "//button[@class='px-10 py-2 text-base rounded-2xs bg-darkOnyx-600 font-semibold']")
	private WebElement button;

	@FindBy(xpath = "//button[@class='Button-sc-1dr2sn8-0 BasketControls___StyledButton2-sc-k63v4f-3 gJHNnE fJvtwC']")
	private List<WebElement> deleteButton;

	@FindBy(xpath = "//a[@href='/basket/?nc=nb']")
	private WebElement basketIconButton;

	@FindBy(xpath = "//li[@class='BasketItem___StyledLi-sc-pyj73d-0 bbfXYq']")
	private List<WebElement> singleProductContainer;

	@FindBy(xpath = "//span[.=\"Let's fill the empty \"]")
	private WebElement emptyBasketMessage;

	@FindBy(xpath = "//span[.='Basket']")
	private WebElement basketMessage;

	/*Return the product present in the cart page*/
	public HashMap<String, Float> cartProds() {

		HashMap<String, Float> map = new HashMap<String, Float>();
		waitForVisibility(prodContainer);
		Iterator<WebElement> it1 = prodList.iterator();
		Iterator<WebElement> it2 = costList.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			map.put(it1.next().getText(), Float.parseFloat(it2.next().getText().split("₹")[1]));
		}
		return map;

	}
	
	//Verifying if the subtoal is equal to the sum of all product's price
	public boolean verifyTotal() {
		float total = Float.parseFloat(subTotal.getText().split(" ")[1]);
		boolean check = false;
		Iterator<WebElement> it2 = costList.iterator();
		float amount = 0;
		while (it2.hasNext()) {
			amount = amount + Float.parseFloat(it2.next().getText().split("₹")[1]);
		}
		if (total == amount)
			check = true;
		return check;
	}

	public void clickCheckout() {
		button.click();
		checkoutButton.click();
	}
	
	//Deleting all the products from cart page. Returns message if the cart is empty
	public String deleteAllProducts() throws InterruptedException {
		if (emptyBasketMessage.isDisplayed()) {
			return "Cart is Empty";
		} else {
			waitForVisibility(prodContainer);
			Actions action = new Actions(driver);
			waitForAllVisibility(singleProductContainer);
			for (int i = deleteButton.size() - 1; i >= 0; i--) {
				action.moveToElement(deleteButton.get(i));
				deleteButton.get(i).click();
			}
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scrollBy(0,-1000)");
			return emptyBasketMessage.getText() + " " + basketMessage.getText();
		}
	}

	public void basketIconClick() {
		waitForVisibility(basketIconButton);
		basketIconButton.click();
	}

}
