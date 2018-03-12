package com.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class BaseEngine {
	ExtentReports extentReports;
	ExtentTest extentTest;
	private static WebDriver driver;
	@Parameters("sbrowser")
	@BeforeSuite
public WebDriver openBrowser(@Optional("chrome")String browser) {
		String dir=System.getProperty("user.dir");
	if(browser.equalsIgnoreCase("chrome")) {
		System.setProperty("webdriver.chrome.driver", dir+"\\Drivers\\chromedriver.exe");
		   driver = new ChromeDriver();
		   init();
	}
	if(browser.equalsIgnoreCase("firefox")) {
		if(driver==null) {
		System.setProperty("webdriver.gecko.driver", dir+"\\Drivers\\geckodriver.exe");
		   driver = new FirefoxDriver();
		   init();
		}
	}
	if(browser.equalsIgnoreCase("firefox")) {
		if(driver==null) {
		System.setProperty("webdriver.ie.driver", dir+"\\Drivers\\IEDriverServer.exe");
		   driver = new InternetExplorerDriver();
		   init();
		}
	}
	return driver;
}
	public static WebDriver getDriver() {
		return driver;
	}
	@AfterMethod
	public void afterTestCase(ITestResult result) throws IOException {
		if(result.getStatus()==ITestResult.FAILURE) {
			System.out.println("TC IS FAILURE SO TAKING SCREENSHOT");
			TakesScreenshot ts=(TakesScreenshot)getDriver();
			File screnshot=ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screnshot,new File("E:\\practicewell\\ClearTripPOM\\screenshots"+result.getName()+".png"));
			getExtentTest().log(LogStatus.FAIL, result.getThrowable());
			getExtentTest().log(LogStatus.FAIL, "screnShot is"+getExtentTest().addScreenCapture("E:\\practicewell\\ClearTripPOM\\extentrepo\\repots.html"));
			//getExtentTest().log(LogStatus.FAIL, "3para", "screnShot is"+getExtentTest().addScreenCapture("E:\\practicewell\\ClearTripPOM\\extentrepo\\repots.html"));
	}
	}
	@BeforeMethod
	public void beforeTestCase(Method method ) {
		System.out.println("NOW EXECUTING :" + method.getName());
		extentTest = extentReports.startTest(method.getName());
	
		}
	
	public static void init() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	@BeforeTest
	public void initReports() {
		extentReports= new ExtentReports("E:\\practicewell\\ClearTripPOM\\extentrepo\\repots.html", true);
	}
	@AfterTest
	public void generateReports() {
		if (extentReports!=null) {
			extentReports.endTest(getExtentTest());
			extentReports.flush();
		}
	}
	public ExtentTest getExtentTest() {
		return extentTest;
	}
}
