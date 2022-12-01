package com.Automation.Base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.Automation.Utils.ConfigsReader;
import com.Automation.Utils.Constants;
import com.Automation.Utils.MyScreenRecorder;
import com.Automation.Utils.TextUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import tech.grasshopper.reporter.ExtentPDFReporter;

public class TestEngine {

	public static WebDriver driver;
	ExtentPDFReporter pdfReporter;
	ExtentPDFReporter pdfReporter1;
	public static ExtentReports extent;
	public static ExtentTest test;
	String URL = "";

	// @BeforeTest(alwaysRun = false, enabled = false)
	// public void setUp() {
	// ConfigsReader.readProperties(System.getProperty("user.dir") +
	// "\\configs\\configuration.properties");
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
	// HH.mm.ss");
	// String path = System.getProperty("user.dir") + "\\TestReports\\" +
	// this.getClass().getSimpleName() + "-"
	// + dateFormat.format(new Date()) + ".html";
	// htmlReporter = new ExtentHtmlReporter(path);
	// extent = new ExtentReports();
	// extent.attachReporter(htmlReporter);
	// htmlReporter.config().setChartVisibilityOnOpen(true);
	// htmlReporter.config().setDocumentTitle("Automation Extent Report");
	// htmlReporter.config().setReportName("Automation Test Report");
	// htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	// htmlReporter.config().setTheme(Theme.STANDARD);
	// htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a
	// '('zzz')'");
	// if
	// (ConfigsReader.getPropValue("ScreenRecording").equalsIgnoreCase("YES")) {
	// MyScreenRecorder.startRecording(this.getClass().getSimpleName());
	// }
	//
	// test = extent.createTest(getClass().getSimpleName(), "Executed Test
	// Steps");
	// switch (ConfigsReader.getPropValue("browser").toLowerCase()) {
	// case "chrome":
	// extent.setSystemInfo("Browser", "Chrome");
	// WebDriverManager.chromedriver().setup();
	// driver = new ChromeDriver();
	// test.log(Status.PASS, "Chrome Browser Launched Successfully");
	// break;
	// case "firefox":
	// extent.setSystemInfo("Browser", "FireFox");
	// WebDriverManager.firefoxdriver().setup();
	// driver = new FirefoxDriver();
	// break;
	// case "edge":
	// extent.setSystemInfo("Browser", "Edge");
	// WebDriverManager.edgedriver().setup();
	// driver = new EdgeDriver();
	// break;
	// case "internetExplorer":
	// extent.setSystemInfo("Browser", "InternetExplorer");
	// WebDriverManager.iedriver().setup();
	// driver = new InternetExplorerDriver();
	// break;
	// default:
	// throw new RuntimeException("Browser is not supported");
	// }
	// driver.manage().window().maximize();
	// // driver.manage().deleteAllCookies();
	//
	// driver.get(ConfigsReader.getPropValue("SSOUrl"));
	//
	// System.out.println("TestEngine" + driver);
	// driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME,
	// TimeUnit.SECONDS);
	// test.log(Status.PASS, "Navigated to URL : " +
	// ConfigsReader.getPropValue("applicationUrl"));
	// PageInitializer.initializePageObjects();
	// }

	public TestEngine(String url) {
		URL = url;
		PageFactory.initElements(driver, this);
	}

	public TestEngine() {

		PageFactory.initElements(driver, this);
	}

	// @BeforeTest(alwaysRun = true, enabled = true)
	// public void setup() {
	// ConfigsReader.readProperties(System.getProperty("user.dir") +
	// "\\configs\\configuration.properties");
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
	// HH.mm.ss");
	// String path = System.getProperty("user.dir") + "\\TestReports\\" +
	// this.getClass().getSimpleName() + "-"
	// + dateFormat.format(new Date()) + ".html";
	// htmlReporter = new ExtentHtmlReporter(path);
	// extent = new ExtentReports();
	// extent.attachReporter(htmlReporter);
	// htmlReporter.config().setChartVisibilityOnOpen(true);
	// htmlReporter.config().setDocumentTitle("Automation Extent Report");
	// htmlReporter.config().setReportName("Automation Test Report");
	// htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	// htmlReporter.config().setTheme(Theme.STANDARD);
	// htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a
	// '('zzz')'");
	// if
	// (ConfigsReader.getPropValue("ScreenRecording").equalsIgnoreCase("YES")) {
	// MyScreenRecorder.startRecording(this.getClass().getSimpleName());
	// }
	//
	// test = extent.createTest(getClass().getSimpleName(), "Executed Test
	// Steps");
	// switch (ConfigsReader.getPropValue("browser").toLowerCase()) {
	// case "chrome":
	// extent.setSystemInfo("Browser", "Chrome");
	// WebDriverManager.chromedriver().setup();
	// driver = new ChromeDriver();
	// test.log(Status.PASS, "Chrome Browser Launched Successfully");
	// break;
	// case "firefox":
	// extent.setSystemInfo("Browser", "FireFox");
	// WebDriverManager.firefoxdriver().setup();
	// driver = new FirefoxDriver();
	// break;
	// case "edge":
	// extent.setSystemInfo("Browser", "Edge");
	// WebDriverManager.edgedriver().setup();
	// driver = new EdgeDriver();
	//
	// break;
	// case "internetExplorer":
	// extent.setSystemInfo("Browser", "InternetExplorer");
	// WebDriverManager.iedriver().setup();
	// driver = new InternetExplorerDriver();
	// break;
	// default:
	// throw new RuntimeException("Browser is not supported");
	// }
	// driver.manage().window().maximize();
	// driver.manage().deleteAllCookies();
	//
	// driver.get(URL);
	//
	// System.out.println("TestEngine" + driver);
	// driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME,
	// TimeUnit.SECONDS);
	// test.log(Status.PASS, "Navigated to URL : " +
	// ConfigsReader.getPropValue("applicationUrl"));
	// PageInitializer.initializePageObjects();
	// }

	// @BeforeMethod
	// public void set(Method method)
	// {
	// String testname=method.getName();
	// test = extent.createTest(testname);
	// }

	@BeforeTest(alwaysRun = true, enabled = true)
	public void setup1() throws IOException {
		ConfigsReader.readProperties(System.getProperty("user.dir") + "\\configs\\configuration.properties");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		String path = System.getProperty("user.dir") + "\\TestReports\\" + this.getClass().getSimpleName() + "-"
				+ dateFormat.format(new Date()) + ".pdf";
		String path1 = System.getProperty("user.dir") + "\\TestReports\\" + this.getClass().getSimpleName() + "-"
				+ dateFormat.format(new Date()) + ".html";
		String xmlPath = System.getProperty("user.dir") + "\\configs\\extent-config.xml";
		String imagepath = System.getProperty("user.dir") + "\\ScreenShots";
		extent = new ExtentReports();
		pdfReporter = new ExtentPDFReporter(path);
		extent.attachReporter(pdfReporter);
		pdfReporter.config().setTitle(".//Documents/Header_Logo_Blue.svg");
		pdfReporter.loadXMLConfig(new File(xmlPath));
		pdfReporter.config().setTitle("LearnIQ Automation Test Report");
		pdfReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extent.setSystemInfo("productname", "EPIQ");
		extent.setSystemInfo("version", "epIQ100BL");
		extent.setSystemInfo("productcode", "epIQ100");
		extent.setSystemInfo("URL", "http://10.10.3.1/epiqByCaliber_GM_TST");
		extent.setSystemInfo("Name", "Lakshmi Harika");
		extent.setSystemInfo("SystemName", "Cal4013");
		extent.setSystemInfo("Browser", "Chrome98.0.4758.102");
		extent.setSystemInfo("OS", "Windows 10");
		extent.setSystemInfo("Scenarios","");
		extent.setSystemInfo("Class Description",
				"In these Scenarios we are testing UserAssign_Qualifieduserswithexam");

		test = extent.createTest(getClass().getSimpleName(), "Executed Log Details");

		extent.setMediaResolverPath(new String[] { imagepath });
		pdfReporter.config().setMediaFolders(new String[] { imagepath });

		if (ConfigsReader.getPropValue("ScreenRecording").equalsIgnoreCase("YES")) {
			MyScreenRecorder.startRecording(this.getClass().getSimpleName());
		}

		switch (ConfigsReader.getPropValue("browser").toLowerCase()) {
		case "chrome":
			extent.setSystemInfo("Browser", "Chrome 98.0.4758.102");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			test.log(Status.PASS, "Chrome Browser Launched Successfully");
			break;
		case "firefox":
			extent.setSystemInfo("Browser", "FireFox");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			extent.setSystemInfo("Browser", "Edge");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		case "internetExplorer":
			extent.setSystemInfo("Browser", "InternetExplorer");
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		default:
			throw new RuntimeException("Browser is not supported");
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		driver.get(URL);

		System.out.println("TestEngine" + driver);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
		test.log(Status.PASS, "Navigated to URL : " + ConfigsReader.getPropValue("applicationUrl"));
		PageInitializer.initializePageObjects();

	}

	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
		if (driver != null) {
			// driver.quit();
		}
		// extent.flush();
		// MyScreenRecorder.stopRecording();
	}

}
