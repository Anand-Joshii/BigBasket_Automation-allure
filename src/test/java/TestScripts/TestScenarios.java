package TestScripts;

import TestComponents.BaseTests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestScenarios extends BaseTests {

	private static String phoneNumber = "Your Phone Number";
	private static String searchKey = "Tomato";
	public HashMap<String, Float> basketProds;
	private int count = 2;

	// Entering a product name in search bar and displaying the search results
	@Description("Verify the search results")
	@Epic("BigBasket")
	@Feature("Feature-01: Search Bar")
	@Story("Story: Search Bar Product Results")
	@Step("Verify Search Bar Results")
	@Severity(SeverityLevel.CRITICAL)
	@Test(groups = { "simple" })
	public void getSearchResults() {

		ArrayList<String> prods = hp.getProdsList(searchKey);
		for (String prod : prods)
			System.out.println(prod);

	}

	// Validating if the search results displays the product searched for.
	@Description("Verify if the search results displays the product searched for.")
	@Epic("BigBasket")
	@Feature("Feature-01: Search Bar")
	@Story("Story: Product presence in the search results")
	@Step("Verify product in search results")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "DataExample", groups = { "simple" })
	public void validateProductPresence(String val) {

		boolean status = hp.validateProductInSearch(val);
		Assert.assertTrue(status);
	}

	// Adding products from search bar and verifying the toasst message
	@Description("Adding products from search bar and verifying the toasst message")
	@Epic("BigBasket")
	@Feature("Feature-01: Search Bar")
	@Story("Story: Toast message presence")
	@Step("Verify Toast message")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "DataExample")
	public void addProductsToBasket(String addProduct) throws InterruptedException {

		boolean verifyToast = hp.addCartSearchButton(addProduct);
		Assert.assertTrue(verifyToast);
	}

	/*
	 * Once products are added, displaying the added products and logging in to the
	 * application
	 */
	@Description("displaying the added products and logging in to the application")
	@Epic("BigBasket")
	@Feature("Feature-02: Cart")
	@Story("Story: Verify products and login")
	@Step("Verify Logo")
	@Severity(SeverityLevel.BLOCKER)
	@Test(dependsOnMethods = "addProductsToBasket")
	public void checkoutAndLogin() throws InterruptedException {

		// Verify items after adding to the basket.
		basketProds = hp.checkBasket();
		basketProds.forEach((key, value) -> System.out.println(key + " " + value));
		hp.basketCheckoutIconClick();
		hp.loginFunctionalityFromBasket(phoneNumber);
	}

	/*
	 * Verify if the added products are present in the cart page Cart Page product
	 * names gets updated/changed. Failing this test.
	 */
	@Description("Verify if the added products are present in the cart page")
	@Epic("BigBasket")
	@Feature("Feature-02: Cart")
	@Story("Story: products presence in cart page")
	@Step("Verify products in cart page")
	@Severity(SeverityLevel.NORMAL)
	@Test(dependsOnMethods = "checkoutAndLogin")
	public void verifyProdsInCartPage() throws InterruptedException {

		HashMap<String, Float> cartMap = bp.cartProds();
		Assert.assertTrue(basketProds.equals(cartMap));
	}

	/*
	 * Verifying the subtotal on checkout page and continue to payment page. Running
	 * the test even if the above test fails by using alwaysRun.
	 */
	@Description("Verifying the subtotal on checkout page and continue to payment page")
	@Epic("BigBasket")
	@Feature("Feature-03: CheckOut")
	@Story("Story: validate subtotal and checkout")
	@Step("Verify subtotal and click on checkout")
	@Severity(SeverityLevel.NORMAL)
	@Test(dependsOnMethods = "verifyProdsInCartPage", alwaysRun = true)
	public void verifySubTotalAndCheckOut() throws InterruptedException {

		Assert.assertTrue(bp.verifyTotal());
		bp.clickCheckout();
		dp.clickCountinueToPayments();
	}

	/*
	 * Deleting all products after logging in from Home Page Validating if cart is
	 * empty.
	 */
	@Description("Deleting all products after logging in from Home Page Validating if cart is empty.")
	@Epic("BigBasket")
	@Feature("Feature-04: Login")
	@Story("Story: Delete all products from basket if available after logging in")
	@Step("Verify deletion of products after logging in")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginAndDeleteAllProduct() throws InterruptedException {
		hp.login(phoneNumber);
		bp.basketIconClick();
		System.out.println(bp.deleteAllProducts());
	}

	/*
	 * This Test searches a product, clicks on the product found. It also verifies
	 * the product availability in the product page. Adds and increament the product
	 * count and verify the subtotal based on count and checkout
	 */
	@Description("Verify the product page functionality")
	@Epic("BigBasket")
	@Feature("Feature-05: Product Page")
	@Story("Story: Adding and updating the products on product page")
	@Step("Verify addition of products and updation of the same on product page and checkout")
	@Severity(SeverityLevel.CRITICAL)
	@Test(groups = { "simple" })
	public void verifyProductPageFunc() throws InterruptedException {
		pp.searchProdandClick("Tomato");
		pp.verifyProductName();
		pp.addToBasket();
		int actual = pp.verifyAddProductCount(count);
		Assert.assertEquals(actual, count+1);
		Assert.assertTrue(pp.verifyProductInBasketAndSubTotal(count));
		pp.clickBasketCheckout();
	}

	// This unit grabs the data from the excel file present in the project
	@DataProvider
	public Object[][] DataExample() throws IOException {

		String sheetName = "Products";
		ExcelUtils excelFile = new ExcelUtils(
				System.getProperty("user.dir") + "/src/main/java/Resources/BigBasket-TestData.xlsx", sheetName);
		int rowCount = excelFile.getRowCount();
		Object[][] obj = new Object[rowCount][1];
		for (int i = 0; i < rowCount; i++) {
			obj[i][0] = excelFile.getData(i + 1, 0);
		}

		return obj;

	}

}
