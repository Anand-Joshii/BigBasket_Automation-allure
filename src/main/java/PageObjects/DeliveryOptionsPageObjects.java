package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;

import Reusable.Waits;

public class DeliveryOptionsPageObjects extends Waits {

	private WebDriver driver;

	public DeliveryOptionsPageObjects(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@class='Button-sc-1dr2sn8-0 DeliveryOptions___StyledButton-sc-xh4ikf-0 gJHNnE dZHUOn']")
	private WebElement continueToPaymentEle;

	public void clickCountinueToPayments() {
		Actions action = new Actions(driver);
		waitForVisibility(continueToPaymentEle);
		action.moveToElement(continueToPaymentEle);
		continueToPaymentEle.click();
	}

}
