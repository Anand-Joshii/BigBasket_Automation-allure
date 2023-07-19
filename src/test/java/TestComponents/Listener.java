package TestComponents;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.TakesScreenshot;

import io.qameta.allure.Attachment;

public class Listener implements ITestListener {
	
	
	@Attachment
	public byte[] saveFailureScreenshot(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}
	
	@Attachment(value = "{0}", type = "test/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("On Test Start "+result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("On Test Start "+result.getMethod().getMethodName()+" Success");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("On Test Start "+result.getMethod().getMethodName()+" Failed");

		WebDriver driver = BaseTests.getDriver();
		if(driver instanceof WebDriver) {
			System.out.println("Screenshot captured for testcase: "+result.getMethod().getMethodName());
			saveFailureScreenshot(driver);
		}
		saveTextLog(result.getMethod().getMethodName()+" Failed and SS taken");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		System.out.println("On Test Skip "+result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		
		context.setAttribute("WebDriver", BaseTests.getDriver());
		System.out.println("On Start "+context.getName());
		
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("On Finish "+context.getName());
		
	}

}
