package Reusable;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {
	
	WebDriver driver;
	public Waits(WebDriver driver) {
		this.driver = driver;
	}
	
	
	
	public void waitForVisibility(WebElement element) {
		WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(10));
		w.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForNumberOfElements(By by,int length) {
		WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(10));
		w.until(ExpectedConditions.numberOfElementsToBe(by, length));
	}
	
	public void waitForStaleElement(By by) {
		WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(5));
		w.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(by)));
	}
	
	
	
	public void waitForAllVisibility(List<WebElement> element) {
		WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(10));
		w.until(ExpectedConditions.visibilityOfAllElements(element));
	}
	
	public Boolean waitForInvisElement(WebElement element) {
		WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(5));
		return w.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public void waitForStaleElements(By by) {
		WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(5));
		w.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(by)));
	}

}
