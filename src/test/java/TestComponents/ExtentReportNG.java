package TestComponents;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;

public class ExtentReportNG {

	public static ExtentReports getReporter() {
		String path = System.getProperty("user.dir") + "\\reports\\report.html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setDocumentTitle("Test Report");
		reporter.config().setReportName("BigBasket-TestReport");
		ExtentReports ext = new ExtentReports();
		ext.attachReporter(reporter);
		ext.setSystemInfo("AJ", "Tester");
		return ext;

	}
}
