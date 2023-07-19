package TestComponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import PageObjects.ReviewBasketPageObjects;
import PageObjects.DeliveryOptionsPageObjects;
import PageObjects.HomePageObjects;
import PageObjects.ProductPagePObjects;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTests {

	public WebDriver driver;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	private Properties prop;
	public HomePageObjects hp;
	public ReviewBasketPageObjects bp;
	public DeliveryOptionsPageObjects dp;
	public ProductPagePObjects pp;
	private String path = "src/main/java/Resources/Browser.properties";
	private FileInputStream fis;

	public WebDriver initBrowser() throws IOException {
		prop = new Properties();
		fis = new FileInputStream(path);
		prop.load(fis);
		String browser = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
		tdriver.set(driver);
		return getDriver();
	}

	@BeforeTest(alwaysRun=true)
	public void launchApp() throws IOException {
		driver = initBrowser();
		fis = new FileInputStream(path);
		prop.load(fis);
		driver.get(prop.getProperty("website"));
		hp = new HomePageObjects(driver);
		bp = new ReviewBasketPageObjects(driver);
		dp = new DeliveryOptionsPageObjects(driver);
		pp = new ProductPagePObjects(driver);
	}

	@AfterTest(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}


	public static synchronized WebDriver getDriver() {
		return tdriver.get();
	}
}