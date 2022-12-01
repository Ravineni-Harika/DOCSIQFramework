package com.Automation.Base;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.usermodel.DateAndTime;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.Automation.Utils.ConfigsReader;
import com.Automation.Utils.Constants;

import com.Automation.Utils.TimeUtil;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


public class ActionEngine extends PageInitializer {
	static SoftAssert soft = new SoftAssert();
	public static String tempPwd = "";
	public static String uniqueCode = "";
	public static FileInputStream fis;
	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;
	public static String row;
	public static XSSFCell cell;

	static Properties prop = ConfigsReader.readProperties("./configs/configuration.properties");
	public ActionEngine(String url) {

		super(url);
	}
	public ActionEngine() {

	
	}
	/**
	 * Method that click on Web element
	 * 
	 * @param element
	 */
	public static void click(WebElement element, String elementName) {
		try {
			waitForElementVisibile(element);
			highLightEle(driver, element,elementName, test);
			element.click();
			test.log(Status.PASS,  elementName +" successfully performed click action");
			
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to click " + elementName);
			test.log(Status.FAIL, "Exception :" + e.getMessage());
			ScreenCapture(driver, false,elementName);
		}

	}

	/**
	 * Method that getSize on Web element
	 * 
	 * @param element
	 */
	public static int getSize(List<WebElement> element) {
		return element.size();
	}

	/**
	 * Method that sends text to any given element
	 * 
	 * @param element
	 * @param text
	 */
	public static void sendText(WebElement element, String text, String elementName) {
		try {

			waitForPageToLoad(1000);
			waitForElementVisibile(element);
			highLightEle(driver, element,elementName, test);
			element.clear();
			element.sendKeys(text);
			test.log(Status.PASS, "Successfully Entered " + text + " in " + elementName + " TextBox");
			
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to enter data in " + elementName);
			test.log(Status.FAIL, "Exception :" + e.getMessage());
			ScreenCapture(driver, false,elementName);
		}

	}

	/*
	 * This method capture the screen based upon the screen capture
	 * configuration value and testCase Result
	 */
	public static void ScreenCapture(WebDriver driver, boolean testCaseResult,String elementName) {
		List<String> ListClassname = Arrays
				.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
		String Classname = ListClassname.get(ListClassname.size() - 1);
		
		String destination = null;
	
//		String scrBase64 = null;
		
		try {
			String dateName = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			
			File source  = ts.getScreenshotAs(OutputType.FILE);
//			File source = OutputType.FILE.convertFromBase64Png(scrBase64);
			// after execution, you could see a folder "FailedTestsScreenshots"
			// under src folder
			// destination = System.getProperty("user.dir") +
			// "//FailedTestsScreenshots//" + dateName + ".png";
			destination = System.getProperty("user.dir") + "//ScreenShots//" + Classname + "-" + dateName
					+ ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination, true);
			// Returns the captured file path

			// if test case passed
			if (testCaseResult) {
				// if screen capture configuration value is yes then capture the
				// screen
				if (prop.getProperty("ScreenCapture").equalsIgnoreCase("YES")) {
//					test.log(Status.PASS, "Successfully "+ elementName +" performed click action");
					destination = System.getProperty("user.dir") + "//ScreenShots//" + Classname + "-" + dateName
							+ ".png";
					
					test.pass("The specified locator "+ elementName +" found in the given time interval.", MediaEntityBuilder
							.createScreenCaptureFromPath(getScreenshot(driver, Classname),elementName).build());
					
				}

			}
			// if test case failed then always capture the screen
			else {
				test.fail(
						"The specified locator Webelement not found in the given time interval: Please check the provided locator value.",
						MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(driver, Classname),elementName)
								.build());
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Exception at Screen Capturing :" + e.getMessage());
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Method that sends Keys to any given element
	 * 
	 * @param element
	 * @param text
	 */
	public static void sendKeys(WebElement element, Keys text, String elementName) {

		waitForElementVisibile(element);
		highLightEle(driver, element, test);
		element.clear();
		element.sendKeys(text);
		testresult(driver, test, elementName);
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Method that waitforElementVisibile
	 * 
	 * @param element
	 * 
	 */
	public static void waitForElementVisibile(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Method return Object of JavaScript Executor type
	 * 
	 * @return js object
	 */
	public static JavascriptExecutor getJSExecutor() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Method performs click using JavaScript executor
	 * 
	 * @param element
	 */
	public static void jsClick(WebElement element, String elementName) {
		try {

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			highLightEle(driver, element,elementName, test);
			element.click();
			
			// getJSExecutor().executeScript("arguments[0].click();", element);
			test.log(Status.PASS, "Successfully clicked on " + elementName);
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed clicked on " + elementName);
			test.log(Status.FAIL, "Exception :" + e.getMessage());
		}
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Method performs SendText using JavaScript executor
	 * 
	 * @param element
	 */

	public static void sendTextUsingJS(WebElement element, String text, String elementName) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='" + text + "';", element);
			test.log(Status.PASS, "Successfully Entered " + text + " in " + elementName + " TextBox");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to enter data in " + elementName);
			test.log(Status.FAIL, "Exception :" + e.getMessage());
		}
	}
	// ---------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Method performs SendText in InnerText using JavaScript executor
	 * 
	 * @param element
	 */
	public static void SendTextUsingJSInnerText(WebElement element, String text, String elementName) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].innerHTML='" + text + "';", element);
			test.log(Status.PASS, "Successfully Entered " + text + " in " + elementName + " TextBox");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to enter data in " + elementName);
			test.log(Status.FAIL, "Exception :" + e.getMessage());
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Methods scrolls up using JavaScript executor
	 * 
	 * @param pixel
	 */
	public static void scrollUp(int pixel) {
		getJSExecutor().executeScript("window.scrollBy(0, -" + pixel + ")");
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Methods scrolls down using JavaScript executor
	 * 
	 * @param pixel
	 */
	public static void scrollDown(int pixel) {
		getJSExecutor().executeScript("window.scrollBy(0," + pixel + ")");
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public static WebDriverWait getWaitObject() {
		return new WebDriverWait(driver, Constants.EXPLICIT_WAIT_TIME);
	}

	// ------------------------------------------------------------------------------------------------------------------------------------
	public static void waitForClickability(WebElement element) {
		getWaitObject().until(ExpectedConditions.elementToBeClickable(element));
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Method that will take a screenshot and store with name in specified
	 * location with .png extension
	 * 
	 * @param fileName
	 */
	public static void takeScreenshot(String fileName) {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(Constants.SCREENSHOT_FILEPATH + fileName + getTimeStamp() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Methods wait for page Load
	 * 
	 * @param pixel
	 */

	public static void waitForPageToLoad(int timeInMilliSeconds) {
		try {
			Thread.sleep(timeInMilliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method will generate timeStamp
	 * 
	 * @return
	 */
	public static String getTimeStamp() {

		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");

		return sdf.format(date);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method wait for Invisibility of element
	 * 
	 * @return
	 */

	public static void waitForInvisibilityOfElement(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOf(ele));

	}
	// ---------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method wait for some seconds
	 * 
	 * @return
	 */

	public static void WaitFor(int timeInMilliSeconds) {
		try {
			Thread.sleep(timeInMilliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// --------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method for paste Data Using Robot
	 * 
	 * @return
	 */
	public static void pasteDataUsingRobot(String textToCopy) {

		// String text = "Hello World";
		WaitFor(5000);
		StringSelection stringSelection = new StringSelection(textToCopy);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (Exception e) {
			// TODO: handle exception
		}
		WaitFor(5000);

	}
	// -------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method for getting Text
	 * 
	 * @return
	 */
	public static String getText(WebElement element, String elementName) {
		String gettxt = "";

		waitForPageToLoad(1000);
		waitForElementVisibile(element);
		highLightEle(driver, element, test);
		gettxt = element.getText();
		testresult(driver, test, elementName);
		return gettxt;
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for select Dropdown Using VisisbleText
	 * 
	 * @return
	 */

	public static void selectDropdownUsingVisisbleText(WebElement element, String text, String elementName) {

		waitForPageToLoad(1000);
		waitForElementVisibile(element);
		highLightEle(driver, element, test);
		Select select = new Select(element);
		select.selectByVisibleText(text);
		testresult(driver, test, elementName);
	}

	// --------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for select Dropdown Using Value
	 * 
	 * @return
	 */
	public static void selectDropdownUsingValue(WebElement element, String text, String elementName) {

		waitForPageToLoad(1000);
		waitForElementVisibile(element);
		highLightEle(driver, element, test);
		Select select = new Select(element);
		select.selectByValue(text);
		testresult(driver, test, elementName);

	}

	// -----------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for select Dropdown Using Index
	 * 
	 * @return
	 */
	public static void selectDropdownUsingIndex(WebElement element, int indexNo, String elementName) {

		waitForPageToLoad(1000);
		waitForElementVisibile(element);
		highLightEle(driver, element, test);
		Select select = new Select(element);
		select.selectByIndex(indexNo);
		testresult(driver, test, elementName);
	}

	// -----------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for assert Text
	 * 
	 * @return
	 */
	public static void assertText(WebElement element, String expectedText, String elementName) {
		String actualText = "";

		waitForPageToLoad(1000);
		waitForElementVisibile(element);
		highLightEle(driver, element, test);
		actualText = element.getText();
		Assert.assertEquals(actualText, expectedText);
		testresult(driver, test, elementName);
	}

	// -----------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for switchTo BodyFrame
	 * 
	 * @return
	 */
	public static void switchToBodyFrame(WebDriver driver) {

		driver.switchTo().frame("bodyFrame");
		TimeUtil.shortWait();

	}

	// -----------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for getScreenshot
	 * 
	 * @return
	 */
	public static String getScreenshot(WebDriver driver, String classname) {
		// below line is just to append the date format with the screenshot name
		// to avoid duplicate names
		String destination = null;
//		String scrBase64 = null;
		try {
			String dateName = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source  = ts.getScreenshotAs(OutputType.FILE);
//			File source = OutputType.FILE.convertFromBase64Png(scrBase64);
			// after execution, you could see a folder "FailedTestsScreenshots"
			// under src folder
			// destination = System.getProperty("user.dir") +
			// "//FailedTestsScreenshots//" + dateName + ".png";
			destination = System.getProperty("user.dir") + "//FailedTestsScreenshots//" + classname + "-" + dateName
					+ ".png";
			destination = System.getProperty("user.dir") + "//ScreenShots//" + classname + "-" + dateName
					+ ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination, true);
			// Returns the captured file path

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return destination;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for getScreenshot
	 * 
	 * @return classname
	 */
	public static String getFailScreenshot(WebDriver driver, String classname) {
		// below line is just to append the date format with the screenshot name
		// to avoid duplicate names
		String destination = null;
		String scrBase64 = null;

		try {
			String dateName = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			scrBase64 = ts.getScreenshotAs(OutputType.BASE64);
			File source = OutputType.FILE.convertFromBase64Png(scrBase64);

			// after execution, you could see a folder "FailedTestsScreenshots"
			// under src folder
			destination = System.getProperty("user.dir") + "//FailedTestsScreenshots//" + classname + "-" + dateName
					+ ".png";

			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination, true);
			// Returns the captured file path

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return scrBase64;
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for isAlertPresent
	 * 
	 * @return
	 */
	public static void isAlertPresent(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					3 /* timeout in seconds */);
			if (wait.until(ExpectedConditions.alertIsPresent()) == null) {

			} else {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				test.info("Checking for alert: If present then click 'Ok'.");
				test.log(Status.PASS, "Successfully clicked 'Ok' at alert");
			}
		} catch (org.openqa.selenium.NoAlertPresentException e) {

			// System.out.println("No Alert Present");

		}

		catch (org.openqa.selenium.WebDriverException e) {
			// System.out.println(e);

		}

	}

	// -----------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for SelectRadioBtn And Checkbox
	 * 
	 * @return
	 */
	public static void SelectRadioBtnAndCheckbox(WebDriver driver, WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		highLightEle(driver, element, test);
		boolean status = element.isSelected();

		if (status == true) {
			test.log(Status.PASS, "Element state is already in selected mode.");
		} else {
			element.click();
			test.log(Status.PASS, "Element selected successfully");
		}

	}

	// --------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for highLightElement
	 * 
	 * @return
	 */
	public static void highLightElement(WebDriver driver, WebElement element, ExtentTest test) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style',' border: 2px solid red;');", element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("");
		}
		// js.executeScript("arguments[0].setAttribute('style','border: solid
		// 2px white');", element);
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px transparent');", element);
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for saveUniqueCode
	 * 
	 * @return
	 */

	public static void saveUniqueCode(WebDriver driver, WebElement element) {

		String message = "";
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		message = element.getText();
		String[] str = message.split(":");
		String saveuniqueCode = str[1];
		saveuniqueCode = saveuniqueCode.trim();
		uniqueCode = saveuniqueCode;

	}

	// --------------------------------------------------------------------------------------------------------------------
	public static void switchToDefaultContent(WebDriver driver) {

		driver.switchTo().defaultContent();
		TimeUtil.shortWait();

	}

	// --------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method for highLightElement
	 * 
	 * @return
	 */

	public static void highLightEle(WebDriver driver, WebElement element, ExtentTest test) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style',' border: 2px solid red;');", element);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("");
		}
		// js.executeScript("arguments[0].setAttribute('style','border: solid
		// 2px white');", element);
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px transparent');", element);
	}
	public static void highLightEle(WebDriver driver, WebElement element,String elementname, ExtentTest test) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style',' border: 2px solid red;');", element);
		ScreenCapture(driver, true,elementname);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("");
		}
		// js.executeScript("arguments[0].setAttribute('style','border: solid
		// 2px white');", element);
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px transparent');", element);
	}

	// ------------------------------------------------------------------------------------------------------------------
	public static String getConfirmationmsg(WebDriver driver, WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));

		String msg = element.getText().trim();
		return msg;

	}

	// ----------------------------------------------------------------------------------------------
	public static void VerifyConfrmmsg(WebElement element, String configurationVal) {

		String msg = element.getText().trim();
		String cnfrmMsg = (msg.split("Unique Code_New")[0]).trim();
		cnfrmMsg = cnfrmMsg.replace("\n", " ").replace("\r", "");
		List<String> ListClassname = Arrays
				.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
		String Classname = ListClassname.get(ListClassname.size() - 1);

		if (cnfrmMsg.equalsIgnoreCase(configurationVal)) {
			test.log(Status.PASS, "Expected value is (" + configurationVal + ")        Actual value is (" + cnfrmMsg
					+ ") Actual and Expected value matched.");
		}

		else {

			try {
				test.fail(
						"Expected value is (" + configurationVal + ")        Actual Value is (" + cnfrmMsg
								+ ") Actual and Expected value does not matched.",
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver, Classname))
								.build());

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}
		}
	}

	// -------------------------------------------------------------------------------------------------------------------
	public static String getUniqueCode() {

		return uniqueCode;
	}

	// -----------------------------------------------------------------------------------------------------------------------------
	public static void unCheckRadioBtnCheckbox(WebDriver driver, WebElement locator) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(locator));
			
			List<String> ListClassname = Arrays
					.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
			String Classname = ListClassname.get(ListClassname.size() - 1);
			boolean flag = locator.isSelected();
			if (flag == true) {

				locator.click();
				test.log(Status.PASS, "Element unchecked successfully.");
				try {
					test.pass(
							"The specified locator Webelement not found in the given time interval: Please check the provided locator value",
							MediaEntityBuilder.createScreenCaptureFromBase64String(getFailScreenshot(driver, Classname))
									.build());
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			else {
				test.log(Status.PASS, "Element is already in Uncheck state.");

			}
	
		}
		catch (org.openqa.selenium.TimeoutException e) {
			try {
				
				test.fail(
						"The specified locator Webelement not found in the given time interval: Please check the provided locator value");

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}

		}

		catch (org.openqa.selenium.WebDriverException e) {

			try {
				test.fail("Step is get failed becuase of this exception:-------");

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}

		}
	}
	public static void unCheckRadioBtnCheckbox(WebDriver driver, WebElement locator,String elementName) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(locator));
			highLightEle(driver, locator,elementName, test);
			ScreenCapture(driver, true,elementName);
			List<String> ListClassname = Arrays
					.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
			String Classname = ListClassname.get(ListClassname.size() - 1);
			boolean flag = locator.isSelected();
			if (flag == true) {

				locator.click();
				test.log(Status.PASS, "Element unchecked successfully.");
				try {
					test.pass(
							"The specified locator Webelement not found in the given time interval: Please check the provided locator value",
							MediaEntityBuilder.createScreenCaptureFromBase64String(getFailScreenshot(driver, Classname))
									.build());
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

			else {
				test.log(Status.PASS, "Element is already in Uncheck state.");
				highLightEle(driver, locator,elementName, test);

			}
	
		}
		catch (org.openqa.selenium.TimeoutException e) {
			try {
				
				test.fail(
						"The specified locator Webelement not found in the given time interval: Please check the provided locator value");

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}

		}

		catch (org.openqa.selenium.WebDriverException e) {

			try {
				test.fail("Step is get failed becuase of this exception:-------");

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}

		}
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------
	public static void verifyCaptionContains(WebElement element, String expectedCaption) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		highLightEle(driver, element, test);
		String actualCaptionValue = element.getText().trim();

	}

	// -------------------------------------------------------------------------------------------------------------------
	public static void testresult(WebDriver driver, ExtentTest test, String elementName) {
		List<String> ListClassname = Arrays
				.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
		String Classname = ListClassname.get(ListClassname.size() - 1);
		try {
			System.out.println("ScreenCapture Value " + prop.getProperty("ScreenCapture"));
			test.log(Status.PASS, "Successfully clicked on " + elementName);
			if (prop.getProperty("ScreenCapture").equalsIgnoreCase("YES")) {

				test.pass("The specified locator Webelement found in the given time interval.",
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

			} else {

				test.pass("The specified locator Webelement found in the given time interval.");
			}
			soft.assertTrue(true);
		} catch (Exception e) {

			try {
				test.fail(
						"The specified locator Webelement not found in the given time interval: Please check the provided locator value.",
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver, Classname))
								.build());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			test.log(Status.FAIL, "Failed clicked on " + elementName);
			test.log(Status.FAIL, "Exception :" + e.getMessage());
			soft.assertTrue(false);
		}

	}
	// --------------------------------------------------------------------------------------------------------------

	public static void enterUniqueCode(WebDriver driver, WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(uniqueCode);

	}

	// --------------------------------------------------------------------------------------------------------------------
	public static void verifyExactCaption(WebElement element, String expectedCaption) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		highLightElement(driver, element, test);
		String actualCaptionValue = element.getText();

		if (actualCaptionValue.equals(expectedCaption)) {
			testresult(driver, test, expectedCaption);
		}

	}

	// --------------------------------------------------------------------------------------------------------------------
	public static void verifyUniqueCode(WebElement element) {

		String UniqueCode = uniqueCode;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		highLightElement(driver, element, test);
		String actualUniqueCodeValue = element.getText();
		if (actualUniqueCodeValue.equals(UniqueCode)) {

		}
	}

	// ---------------------------------------------------------------------------------------------------------
	public static String getCurrentDate(String desiredFormat) {

		DateFormat df = new SimpleDateFormat(desiredFormat);
		Date dateobj = new Date();
		String currentDate = df.format(dateobj);

		return currentDate;
	}

	// -------------------------------------------------------------------------------------------------------------
	public static String getCurrentDateTime(String desiredFormat) {

		DateFormat df = new SimpleDateFormat(desiredFormat);
		DateAndTime dateobj = new DateAndTime();

		String currentDateTime = df.format(dateobj);

		return currentDateTime;
	}

	// -------------------------------------------------------------------------------------
	public static void clearField(WebElement element, String UIElementName) {
		List<String> ListClassname = Arrays
				.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
		String Classname = ListClassname.get(ListClassname.size() - 1);
		try {
			WebElement ele = element;
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(element));
			highLightEle(driver, element, test);
			boolean readonly = isreadOnly(driver, element);
			if (readonly == false) {
				if (!ele.getAttribute("value").equals("")) {
					element.clear();
					test.log(Status.PASS, "Value successfully cleared from " + UIElementName + ".");
				}
			} else if ((!ele.getTagName().equals("input")) || (!ele.getTagName().equals("textarea"))) {

				try {
					test.fail(
							"To clear the value specified locator Webelement should be a Textbox or Textarea and editable in nature.",
							MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

				}

				
				catch (Exception e1) {

					e1.printStackTrace();
				}

			}

		}

		catch (org.openqa.selenium.TimeoutException e) {
			try {
				test.fail(
						"The specified locator Webelement not found in the given time interval: Please check the provided locator value.",
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

			}

			catch (Exception e1) {

				e1.printStackTrace();
			} 
		}

		catch (java.lang.NullPointerException e) {
			try {

				test.fail("Make sure passed locator having 'value' attribute.",
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

			}  catch (Exception e1) {

				e1.printStackTrace();
			}

		}

		catch (org.openqa.selenium.WebDriverException e) {

			try {
				test.fail("Step is get failed becuase of this exception:-------" + e.toString(),
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}

		}
	}

	// -----------------------------------------------------------------------------------------------------------------------
	public static boolean isreadOnly(WebDriver driver, WebElement element) {

		WebElement ele = element;
		Boolean readOnly = false;
		readOnly = ((ele.getAttribute("disabled") != null) || (ele.getAttribute("readonly") != null));
		return readOnly;
	}

	public static void uploadDocument(String documentPathWithExtension) {

		Robot rb;
		try {
			rb = new Robot();

			StringSelection str = new StringSelection(documentPathWithExtension);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(str, null);

			Thread.sleep(3000);

			rb.keyPress(KeyEvent.VK_CONTROL);

			Thread.sleep(3000);

			rb.keyPress(KeyEvent.VK_V);

			Thread.sleep(3000);

			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_V);
			Thread.sleep(3000);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			test.pass("Document uploaded successfully.");

		} catch (Exception e) {

		}
	}

	// --------------------------------------------------------------------------------------------------------------------------
	public static void SystemManager_UserGroups_InitiateMenu() {

		WebElement Menu = driver.findElement(By.xpath("//div[@class='menu-toggler sidebar-toggler']"));
		WebElement SystemManager = driver
				.findElement(By.xpath("//span[@class='title'][contains(text(),'System Manager')]"));
		WebElement UserGroups = driver.findElement(By.xpath("//a[contains(text(),'User Groups')]"));
		WebElement InitiateMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Initiate')]"));

		click(Menu, "Menu");
		click(SystemManager, "System Manager Menu");
		click(UserGroups, "UserGroups menu");
		click(InitiateMenu, "Initate Menu");
	}

	// ----------------------------------------------------------------------------------------------------------------------------
	public static void SystemManager_UserGroups_ModifyMenu() {

		WebElement Menu = driver.findElement(By.xpath("//div[@class='menu-toggler sidebar-toggler']"));
		WebElement SystemManager = driver
				.findElement(By.xpath("//span[@class='title'][contains(text(),'System Manager')]"));
		WebElement UserGroups = driver.findElement(By.xpath("//a[contains(text(),'User Groups')]"));
		WebElement ModifyMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Modify')]"));

		click(Menu, "Menu");
		click(SystemManager, "System Manager Menu");
		click(UserGroups, "UserGroups menu");
		click(ModifyMenu, "Modify Menu");
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	public static void SystemManager_UserGroups_ApproveMenu() {

		WebElement Menu = driver.findElement(By.xpath("//div[@class='menu-toggler sidebar-toggler']"));
		WebElement SystemManager = driver
				.findElement(By.xpath("//span[@class='title'][contains(text(),'System Manager')]"));
		WebElement UserGroups = driver.findElement(By.xpath("//a[contains(text(),'User Groups')]"));
		WebElement ApproveMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Approve')]"));

		click(Menu, "Menu");
		click(SystemManager, "System Manager Menu");
		click(UserGroups, "UserGroups menu");
		click(ApproveMenu, "Approve Menu");
	}

	// -------------------------------------------------------------------------------------------------------------------------
	public static void SystemManager_UserGroups_ReinitationMenu() {

		WebElement Menu = driver.findElement(By.xpath("//div[@class='menu-toggler sidebar-toggler']"));
		WebElement CourseManager = driver
				.findElement(By.xpath("//span[@class='title'][contains(text(),'Course Manager')]"));
		WebElement ReinitiationMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Re-initiation')]"));

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(ReinitiationMenu, "Reinitiation Menu");
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	public static void SystemManager_UserGroups_RITransferMenu() {

		WebElement Menu = driver.findElement(By.xpath("//div[@class='menu-toggler sidebar-toggler']"));
		WebElement CourseManager = driver
				.findElement(By.xpath("//span[@class='title'][contains(text(),'Course Manager')]"));
		WebElement RITransfer = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'RI-Transfer')]"));

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(RITransfer, "Reinitiation Menu");
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public static void click_CourseManager_InitiateMenu(WebElement Menu, WebElement CourseManager,
			WebElement InitiateMenu) {

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(InitiateMenu, "Initate Menu");

	}

	// -------------------------------------------------------------------------------------------------------------------------------
	public static void CourseManager_ProposeMenu(WebElement Menu, WebElement CourseManager, WebElement ProposeMenu) {

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(ProposeMenu, "Propose Menu");
	}

	// ----------------------------------------------------------------------------------------------------------------------------------
	public static void CourseManager_ApproveMenu(WebElement Menu, WebElement CourseManager, WebElement ApproveMenu) {

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(ApproveMenu, "Approve Menu");
	}

	// -----------------------------------------------------------------------------------------------------------------------------
	public static void CourseManager_ReinitationMenu(WebElement Menu, WebElement CourseManager,
			WebElement ReinitiationMenu) {

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(ReinitiationMenu, "Reinitiation Menu");
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	public static void CourseManager_RITransferMenu(WebElement Menu, WebElement CourseManager, WebElement RITransfer) {

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		jsClick(RITransfer, "Reinitiation Menu");
	}

	// -----------------------------------------------------------------------------------------------------------------------------------
	public static void CourseManager_ModifyMenu(WebElement Menu, WebElement CourseManager, WebElement ModifyMenu) {

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(ModifyMenu, "Modify Menu");
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public static void CourseManager_StatusChangeMenu(WebElement Menu, WebElement CourseManager,
			WebElement StatusChangeMenu) {

		click(Menu, "Menu");
		click(CourseManager, "Course Manager Menu");
		click(StatusChangeMenu, "StatusChange Menu");
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	public static void CourseManager_AuditTrailsMenu(String ElementMenu) {

		WebElement InitiateMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Initiate')]"));
		WebElement ApproveMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Approve')]"));
		WebElement ReinitiationMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Re-initiation')]"));
		WebElement RITransfer = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'RI-Transfer')]"));
		WebElement ModifyMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Modify')]"));
		WebElement StatusChangeMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Status Change')]"));
		WebElement AuditTrailsMenu = driver.findElement(By.xpath(
				"//li[contains(@class,'nav-item open')]//a[contains(@class,'nav-link nav-toggle')][contains(text(),'Audit Trails')]"));
		String Menu1 = "InitiateMenu";
		String Menu2 = "ApproveMenu";
		String Menu3 = "ReinitiationMenu";
		String Menu4 = "RITransfer";
		String Menu5 = "ModifyMenu";
		String Menu6 = "StatusChangeMenu";

		if (Menu1.equalsIgnoreCase("ElementMenu")) {
			switchToDefaultContent(driver);
			TimeUtil.shortWait();
			click(InitiateMenu, "Initiate Menu");
			TimeUtil.mediumWait();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", AuditTrailsMenu);
			AuditTrailsMenu.click();
		}
	}

	// --------------------------------------------------------------------------------------------------------------------------
	public static void EsignCnfrm(String eSign, WebElement ConfirmMsg, String configurationVal, WebElement NextButton) {

		boolean esign1 = e_SignAvailabe();

		if (esign1 == true) {
			E_sign.e_Sign(eSign);

		}
		String msg = ConfirmMsg.getText().trim();
		String cnfrmMsg = (msg.split("Unique Code_New")[0]).trim();
		cnfrmMsg = cnfrmMsg.replace("\n", " ").replace("\r", "");
		TimeUtil.shortWait();
		saveUniqueCode(driver, ConfirmMsg);
		String msg1 = getConfirmationmsg(driver, ConfirmMsg);
		String uniqueCode = getUniqueCode();
		verifyCaptionContains(ConfirmMsg, uniqueCode);
		click(NextButton, "Next Button");

		if (cnfrmMsg.equalsIgnoreCase(configurationVal)) {
			test.log(Status.PASS, "Expected value is (" + configurationVal + ")        Actual value is (" + cnfrmMsg
					+ ") Actual and Expected value matched.");
		}
	}

	// ---------------------------------------------------------------------------
	public static void alertAccept(WebDriver driver) {

		driver.switchTo().alert().accept();
		test.log(Status.PASS, "Clicked 'Ok' at alert.");

	}

	// ---------------------------------------------------------------------------------------------------------------------------
	public static boolean e_SignAvailabe() {
		List<String> ListClassname = Arrays
				.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
		String Classname = ListClassname.get(ListClassname.size() - 1);
		boolean esign = false;

		try {

			driver.findElement(By.xpath("//span[contains(text(),'Meaning of This Electronic Signature_New')]"))
					.isDisplayed();

			TimeUtil.shortWait();
			esign = true;

		}

		catch (org.openqa.selenium.NoSuchFrameException e) {

		}

		catch (org.openqa.selenium.WebDriverException e) {

			try {
				test.fail("Step is get failed becuase of this exception:-------" + e.toString(),
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}
		}

		return esign;
	}

	// -----------------------------------------------------------------------------------------------------------------------
	public static void SearchRecord(WebElement SearchFilter, WebElement SearchByNew, WebElement SearchByNewDropdown,
			WebElement DisplayedRecord, WebElement Aud_UniqueCodeField, WebElement Display) {

		click(SearchFilter, "SearchFilter");
		click(SearchByNew, "SearchByNew");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement Ele1 = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		jse.executeScript("arguments[0].value='Unique Code_NEW';", Ele1);
		TimeUtil.mediumWait();
		click(SearchByNewDropdown, "SearchByNewDropdown");
		clearField(Aud_UniqueCodeField, "uniquecodetextbox");
		enterUniqueCode(driver, Aud_UniqueCodeField);
		TimeUtil.shortWait();
		click(Display, "Display");
		TimeUtil.shortWait();
		click(DisplayedRecord, "Displayed Record");

	}

	// -------------------------------------------------------------------------------------------------------------

	public static void Configuration(String noOfAprReqForInitDrpdwn, String noOfAprReqForModDrpdwn,
			String noOfAprReqForSTCDrpdwn) {

		WebElement EsignAtInitCheckBox = driver.findElement(By.id("Config_IsEsignAtCreatInit"));
		WebElement EsignAtModInitCheckBox = driver.findElement(By.id("Config_IsEsignAtModifyInit"));
		WebElement EsignAtSTCInitCheckBox = driver.findElement(By.id("Config_IsEsignAtStatusChangeInit"));
		WebElement EsignAtInitAprCheckBox = driver.findElement(By.id("Config_IsEsignAtCreatAppr"));
		WebElement EsignAtModAprCheckBox = driver.findElement(By.id("Config_IsEsignAtModifyAppr"));
		WebElement EsignAtSTCAprCheckBox = driver.findElement(By.id("Config_IsEsignAtStatusChangeAppr"));
		WebElement NoOfAprReqForRegDrpdwn = driver.findElement(By.id("select2-Config_NarAtCreate-container"));
		WebElement NoOfAprReqForModDrpdwn = driver.findElement(By.id("select2-Config_NarAtModify-container"));
		WebElement NoOfAprReqForSTCDrpdwn = driver.findElement(By.id("select2-Config_NarAtStatusChange-container"));
		WebElement Remarks = driver.findElement(By.id("Config_Remarks"));
		WebElement SearchSel = driver.findElement(By.xpath("/html[1]/body[1]/span[1]/span[1]/span[2]/ul[1]/li[2]"));
		WebElement iframe = driver.findElement(By.id("Config_Remarks"));
		WebElement SubmitBtn = driver.findElement(By.id("btnSubmit"));
		WebElement Search = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		WebElement SignOutBtn = driver.findElement(By.xpath("//i[@class='icon-power']"));

		SelectRadioBtnAndCheckbox(driver, EsignAtInitCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtModInitCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtSTCInitCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtInitAprCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtModAprCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtSTCAprCheckBox);
		click(NoOfAprReqForRegDrpdwn, "NoOfAprReqForRegDrpdwn");
		WebElement Element = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + noOfAprReqForInitDrpdwn + "';", Element);
		TimeUtil.shortWait();
		click(SearchSel, "SearchSel");
		click(NoOfAprReqForModDrpdwn, "NoOfAprReqForModDrpdwn");
		WebElement Element1 = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		jse.executeScript("arguments[0].value='" + noOfAprReqForModDrpdwn + "';", Element1);
		TimeUtil.shortWait();
		click(SearchSel, "SearchSel");
		click(NoOfAprReqForSTCDrpdwn, "NoOfAprReqForSTCDrpdwn");
		WebElement Element2 = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		jse.executeScript("arguments[0].value='" + noOfAprReqForSTCDrpdwn + "';", Element2);
		TimeUtil.shortWait();
		click(SearchSel, "SearchSel");
		sendText(Remarks, "Configured", "RemarksVal");
		click(SubmitBtn, "Submit Button");
		switchToDefaultContent(driver);
		TimeUtil.shortWait();
	}

	public static void CM_Configuration(WebElement ModuleMenu, WebElement ConfigMenu, WebElement SubMenu,
			String noOfAprReqForInitDrpdwn, String noOfAprReqForModDrpdwn, String noOfAprReqForSTCDrpdwn) {
		WebElement Menu = driver.findElement(By.xpath("//div[@class='menu-toggler sidebar-toggler']"));
		WebElement EsignAtInitCheckBox = driver.findElement(By.id("Config_IsEsignAtCreatInit"));
		WebElement EsignAtModInitCheckBox = driver.findElement(By.id("Config_IsEsignAtModifyInit"));
		WebElement EsignAtSTCInitCheckBox = driver.findElement(By.id("Config_IsEsignAtStatusChangeInit"));
		WebElement EsignAtInitAprCheckBox = driver.findElement(By.id("Config_IsEsignAtCreatAppr"));
		WebElement EsignAtModAprCheckBox = driver.findElement(By.id("Config_IsEsignAtModifyAppr"));
		WebElement EsignAtSTCAprCheckBox = driver.findElement(By.id("Config_IsEsignAtStatusChangeAppr"));
		WebElement NoOfAprReqForRegDrpdwn = driver.findElement(By.id("select2-Config_NarAtCreate-container"));
		WebElement NoOfAprReqForModDrpdwn = driver.findElement(By.id("select2-Config_NarAtModify-container"));
		WebElement NoOfAprReqForSTCDrpdwn = driver.findElement(By.id("select2-Config_NarAtStatusChange-container"));
		WebElement Remarks = driver.findElement(By.id("Config_Remarks"));
		WebElement SearchSel = driver.findElement(By.xpath("/html[1]/body[1]/span[1]/span[1]/span[2]/ul[1]/li[2]"));
		WebElement iframe = driver.findElement(By.id("Config_Remarks"));
		WebElement SubmitBtn = driver.findElement(By.id("btnSubmit"));
		WebElement Search = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		WebElement SignOutBtn = driver.findElement(By.xpath("//i[@class='icon-power']"));
		click(Menu, " Menu");
		click(ModuleMenu, "Module Menu");
		click(ConfigMenu, "Menu");
		click(SubMenu, "SubMenu");
		switchToBodyFrame(driver);
		SelectRadioBtnAndCheckbox(driver, EsignAtInitCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtModInitCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtSTCInitCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtInitAprCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtModAprCheckBox);
		SelectRadioBtnAndCheckbox(driver, EsignAtSTCAprCheckBox);
		click(NoOfAprReqForRegDrpdwn, "NoOfAprReqForRegDrpdwn");
		WebElement Element = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + noOfAprReqForInitDrpdwn + "';", Element);
		TimeUtil.shortWait();
		click(SearchSel, "SearchSel");
		click(NoOfAprReqForModDrpdwn, "NoOfAprReqForModDrpdwn");
		WebElement Element1 = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		jse.executeScript("arguments[0].value='" + noOfAprReqForModDrpdwn + "';", Element1);
		TimeUtil.shortWait();
		click(SearchSel, "SearchSel");
		click(NoOfAprReqForSTCDrpdwn, "NoOfAprReqForSTCDrpdwn");
		WebElement Element2 = driver.findElement(By.xpath("//input[@class='select2-search__field']"));
		jse.executeScript("arguments[0].value='" + noOfAprReqForSTCDrpdwn + "';", Element2);
		TimeUtil.shortWait();
		click(SearchSel, "SearchSel");
		sendText(Remarks, "Configured", "RemarksVal");
		click(SubmitBtn, "Submit Button");
	}

	// ----------------------------------------------------------------------------------------------------------------------
	// public static void ATEventSectionInitation(
	// ) {
	//
	// WebElement
	// InitiatedValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[1]/td[1]"));
	// WebElement
	// InitiatedByValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[1]/td[2]"));
	// WebElement
	// DateValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[1]/td[3]"));
	// WebElement
	// ReturnedValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[2]/td[1]"));
	// WebElement
	// ReturnedByValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[2]/td[2]"));
	// WebElement
	// ReturnedDateValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[2]/td[3]"));
	// WebElement
	// ReturnedRemarks=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[2]/td[4]"));
	// WebElement
	// ReinitiatedValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[3]/td[1]"));
	// WebElement
	// ReinitiatedByValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[3]/td[2]"));
	// WebElement
	// ReinitiatedDateValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[3]/td[3]"));
	// WebElement
	// ReinitiatedRemarks=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[3]/td[4]"));
	// WebElement
	// ReinitiatedAprvValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[4]/td[1]"));
	// WebElement
	// ReinitiatedAprvByValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[4]/td[2]"));
	// WebElement
	// ReinitiatedAprvDateValue=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[4]/td[3]"));
	// WebElement
	// ReinitiatedAprvRemarks=driver.findElement(By.xpath("//table[@class='audit-preview-container
	// my-3 w-100']/tbody/tr[4]/td[4]"));
	// WebElement
	// ApprovalRequiredValue=driver.findElement(By.xpath("//div[@class='approve-status']//span[contains(text(),'No.
	// of Approvals Required:')]//span[@class='approve-status-span']"));
	// WebElement
	// ApprovalCompletedVal=driver.findElement(By.xpath("//div[@class='approve-status']//span//span[contains(text(),'No.
	// of Approvals Completed:_New:')]//span[@class='approve-status-span']"));
	// verifyExactCaption(InitiatedValue, InitActionVal);
	// verifyExactCaption(InitiatedByValue, InitActionByVal);
	// String Currentdate = getCurrentDate("MMM d yyyy");
	// verifyCaptionContains(DateValue, Currentdate);
	// verifyCaptionContains(ApprovalRequiredValue, ApprovalReqVal);
	// verifyCaptionContains(ApprovalCompletedVal, ApprovalCompleteVal);
	// click(CloseBtn,"Close");
	// }
	// -------------------------------------------------------------------------------------------------------------------
	public static void verifyAttribute(WebElement element, String attribute, String expectedValue) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		highLightEle(driver, element, test);
		String actualAttributeValue = element.getAttribute(attribute);

		if (actualAttributeValue.equals(expectedValue)) {

		}

	}

	// ------------------------------------------------------------------------------------------------------

	public static void selectDateonReQualification(WebElement Melement, WebElement Yelement, String month, String day,
			String year) {

		try {

			String actualMonth = Melement.getText();
			String actualYear = Yelement.getText();
			if (!actualMonth.equals(month)) {
				selectOptionFromDropdownByName(Melement, month, "month");
				if (!actualYear.equals(year)) {
					selectOptionFromDropdownByName(Yelement, year, "year");
				}
				List<WebElement> from_date_tr = driver.findElements(By
						.xpath("//body[1]/div[2]/div[2]/div[1]/table[1]//tbody//tr//td[contains(@class,'available')]"));

//				System.out.println("start date  " + from_date_tr.size());

				for (int j = 0; j < from_date_tr.size(); j++) {
//					System.out.println("start date Displayed " + from_date_tr.get(j).getText());
					String dayval = from_date_tr.get(j).getText();
					if (dayval.equals(day)) {

						from_date_tr.get(j).click();
						break;

					}
				}

			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	// -----------------------------------------------------------------------------------------------------------------
	public static void selectDateonCalender(WebElement Melement, WebElement Yelement, String month, String day,
			String year) {

		try {

			String actualMonth = Melement.getText();
			String actualYear = Yelement.getText();
			if (!actualMonth.equals(month)) {
				selectOptionFromDropdownByName(Melement, month, "month");
				if (!actualYear.equals(year)) {
					selectOptionFromDropdownByName(Yelement, year, "year");
				}
				List<WebElement> from_date_tr = driver.findElements(
						By.xpath("//body[1]/div[5]/div[2]/div[1]/table[1]//tbody//tr//td[contains(@class,'active')]"));

				System.out.println("start date  " + from_date_tr.size());

				for (int j = 0; j < from_date_tr.size(); j++) {
					System.out.println("start date Displayed " + from_date_tr.get(j).getText());
					String dayval = from_date_tr.get(j).getText();
					if (dayval.equals(day)) {

						from_date_tr.get(j).click();
						break;

					}
				}

			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	// ----------------------------------------------------------------------------------------------------------------------------
	public static void selectDateonNextReviewCalender(WebElement Melement, WebElement Yelement, String month,
			String day, String year) {

		try {

			String actualMonth = Melement.getText();
			String actualYear = Yelement.getText();
			if (!actualMonth.equals(month)) {
				selectOptionFromDropdownByName(Melement, month, "month");
				if (!actualYear.equals(year)) {
					selectOptionFromDropdownByName(Yelement, year, "year");
				}
				List<WebElement> from_date_tr = driver.findElements(
						By.xpath("//body[1]/div[3]/div[2]/div[1]/table[1]//tbody//tr//td[contains(@class,'active')]"));

				System.out.println("start date  " + from_date_tr.size());

				for (int j = 0; j < from_date_tr.size(); j++) {
					System.out.println("start date Displayed " + from_date_tr.get(j).getText());
					String dayval = from_date_tr.get(j).getText();
					if (dayval.equals(day)) {

						from_date_tr.get(j).click();
						break;

					}
				}

			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	// -----------------------------------------------------------------------------------------------------
	public static void selectDateonLastDateResponseCalender(WebElement Melement, WebElement Yelement, String month,
			String day, String year) {

		try {

			String actualMonth = Melement.getText();
			String actualYear = Yelement.getText();

			if (!actualMonth.equals(month)) {

				selectOptionFromDropdownByName(Melement, month, "month");

				if (!actualYear.equals(year)) {

					selectOptionFromDropdownByName(Yelement, year, "year");

				}

				List<WebElement> from_date = driver
						.findElements(By.xpath("//body[1]/div[4]/div[2]/div[1]/table[1]/tbody[1]//tr//td"));

				for (int i = 0; i < from_date.size(); i++) {
					String dayval = from_date.get(i).getText();

					if (dayval.equals(day)) {
						from_date.get(i).click();
						break;
					}

				}
			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	// ------------------------------------------------------------------------------------------------------
	public static void selectOptionFromDropdownByName(WebElement element, String option, String elementname) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		highLightEle(driver, element, test);
		Select select = new Select(element);

		select.selectByVisibleText(option);
		if (option.equals(select.getFirstSelectedOption().getText())) {

			test.log(Status.PASS, "Successfully selected (" + option + ") option in the Dropdown");
		}

		testresult(driver, test, elementname);
	}

	// --------------------------------------------------------------------------------------------------------------
	public static void selectDateonNextReviewCal(WebDriver driver, WebElement Melement, WebElement Yelement,
			String month, String day, String year) {

		try {

			String actualMonth = Melement.getText();
			String actualYear = Yelement.getText();
			if (!actualMonth.equals(month)) {
				selectOptionFromDropdownByName(Melement, month, "month");
				if (!actualYear.equals(year)) {
					selectOptionFromDropdownByName(Yelement, year, "Year");
				}
				List<WebElement> from_date_tr = driver.findElements(
						By.xpath("//body[1]/div[3]/div[2]/div[1]/table[1]//tbody//tr//td[contains(@class,'active')]"));

				System.out.println("start date  " + from_date_tr.size());

				for (int j = 0; j < from_date_tr.size(); j++) {
					System.out.println("start date Displayed " + from_date_tr.get(j).getText());
					String dayval = from_date_tr.get(j).getText();
					if (dayval.equals(day)) {

						from_date_tr.get(j).click();
						break;

					}
				}

			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	// -------------------------------------------------------------
	public static void selectDateonEffectivefromCalender(WebDriver driver, WebElement Melement, WebElement Yelement,
			String month, String day, String year) {

		try {

			String actualMonth = Melement.getText();
			String actualYear = Yelement.getText();
			if (!actualMonth.equals(month)) {
				selectOptionFromDropdownByName(Melement, month, "Month");
				if (!actualYear.equals(year)) {
					selectOptionFromDropdownByName(Yelement, year, "Year");
				}
				List<WebElement> from_date_tr = driver.findElements(
						By.xpath("//body[1]/div[2]/div[2]/div[1]/table[1]//tbody//tr//td[contains(@class,'active')]"));

				// System.out.println("start date " + from_date_tr.size());

				for (int j = 0; j < from_date_tr.size(); j++) {
					// System.out.println("start date Displayed " +
					// from_date_tr.get(j).getText());
					String dayval = from_date_tr.get(j).getText();
					if (dayval.equals(day)) {

						from_date_tr.get(j).click();
						break;

					}
				}

			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	// --------------------------------------------
	public static void selectCurrentdate() {

		try {

			List<WebElement> from_date_tr = driver.findElements(
					By.xpath("//body[1]/div[2]/div[2]/div[1]/table[1]//tbody//tr//td[contains(@class,'today')]"));
			from_date_tr.get(0).click();

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	public static void selectStartdateCurrentdate() {

		try {

			List<WebElement> from_date_tr = driver.findElements(
					By.xpath("//body[1]/div[2]/div[2]/div[1]/table[1]/tbody[1]/tr//td[contains(@class,'today')]"));
			from_date_tr.get(0).click();

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	public static void selectEnddateCurrentdate() {

		try {

			List<WebElement> from_date_tr = driver.findElements(
					By.xpath("//body[1]/div[3]/div[2]/div[1]/table[1]/tbody[1]/tr//td[contains(@class,'today')]"));
			from_date_tr.get(0).click();

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	public static void selectlastdateCurrentdate() {

		try {

			List<WebElement> from_date_tr = driver.findElements(
					By.xpath("//body[1]/div[4]/div[2]/div[1]/table[1]/tbody[1]/tr//td[contains(@class,'today')]"));
			from_date_tr.get(0).click();

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	public static void selectNextdate() {

		try {

			List<WebElement> from_date_tr = driver.findElements(By.xpath(
					"//body[1]/div[3]/div[2]/div[1]/table[1]//tbody//tr//td[contains(@class,'active start-date active end-date available')]"));
			from_date_tr.get(0).click();

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	// ---------------------------------------------------------------------------
	public static void removeFocus(WebElement element) {
		List<String> ListClassname = Arrays
				.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
		String Classname = ListClassname.get(ListClassname.size() - 1);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(element));
			WebElement webElement = element;
			webElement.sendKeys(Keys.TAB);

			test.log(Status.PASS, "Focus has been changed successfully.");
			if (prop.getProperty("ScreenCapture").equalsIgnoreCase("YES")) {
				test.pass(
						"The specified locator Webelement found in the given time interval: Please check the provided locator value.",
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());
				// Below assertion is for summary report count
				soft.assertTrue(true);
			}

		} catch (org.openqa.selenium.NoSuchElementException e) {

		} catch (org.openqa.selenium.TimeoutException e) {
			try {
				test.fail(
						"The specified locator Webelement not found in the given time interval: Please check the provided locator value",
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}

		}

		catch (org.openqa.selenium.WebDriverException e) {

			try {
				test.fail("Step is get failed becuase of this exception:-------" + e.toString(),
						MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshot(driver,Classname)).build());

			}
			catch (Exception e1) {

				e1.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	public static void SelectCourse(String CourseName) {

		List<WebElement> allcourseName = driver.findElements(By.xpath("//table[@id='ListTab']/tbody//tr"));
		for (int i = 1; i <= allcourseName.size(); i++) {
			String tempcourseName = driver.findElement(By.xpath("//table[@id='ListTab']/tbody//tr[" + i + "]/td[1]"))
					.getText();
			System.out.println(tempcourseName);

			if (tempcourseName.equals(CourseName)) {
				driver.findElement(By.xpath("//table[@id='ListTab']/tbody/tr[" + i + "]/td[1]")).click();

				break;

			}
		}
	}

	public static void saveTempPassword(WebDriver driver, WebElement element, ExtentTest test) {
		String tempPwd = "";

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
		highLightEle(driver, element, test);
		tempPwd = element.getAttribute("value");
		test.pass("Successfully saved the value (" + tempPwd + ").");
		ActionEngine.tempPwd = tempPwd;

	}
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public static void enterTempPwd(WebDriver driver, WebElement element) {
		List<String> ListClassname = Arrays
				.asList(Thread.currentThread().getStackTrace()[2].getClassName().split("\\."));
		String Classname = ListClassname.get(ListClassname.size() - 1);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(ActionEngine.tempPwd);
			test.pass("Successfully entered the (" + ActionEngine.tempPwd + ") in the specified locator.");
			if (prop.getProperty("ScreenCapture").equalsIgnoreCase("YES")) {
				try {
					test.pass(
							"The specified locator Webelement  found in the given time interval: Please check the provided locator value.",
							MediaEntityBuilder.createScreenCaptureFromBase64String(ActionEngine.getScreenshot(driver,Classname))
									.build());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Below assertion is for summary report count
				soft.assertTrue(true);
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			try {
				test.fail(
						"The specified locator Webelement not found in the given time interval: Please check the provided locator value",
						MediaEntityBuilder.createScreenCaptureFromBase64String(ActionEngine.getScreenshot(driver,Classname))
								.build());

			}

			catch (Exception e1) {

				e1.printStackTrace();
			}

		}

	}

//======================================
public static void printAttendance(String DocLocation, String courseName) {

	// below line execute the AutoIT script .
	try {
		Runtime.getRuntime()
				.exec("//calqa//NICHELON SELENIUM WORKS//Common Script//PrintAttendance//PrintTest.exe");

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	TimeUtil.longwait();

	

	Robot rb;

	try {
		rb = new Robot();

		StringSelection str = new StringSelection(DocLocation + "\\"+ courseName);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(str, null);

		Thread.sleep(1000);


		rb.keyPress(KeyEvent.VK_CONTROL);

		Thread.sleep(1000);

		rb.keyPress(KeyEvent.VK_V);
		Thread.sleep(1000);

		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		Thread.sleep(2000);

		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);

	} catch (Exception e) {

	}

	
	// below line execute the AutoIT script .
	try {
		Runtime.getRuntime()
				.exec("//calqa//NICHELON SELENIUM WORKS//Common Script//PrintAttendance//PrintInitiatedMsg.exe");
		

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
public static void doubleclick(WebElement element, String elementName) {
	try {
		waitForElementVisibile(element);
		highLightEle(driver, element,elementName, test);
		Actions actions = new Actions(driver);
		actions.doubleClick(element).build().perform();
		test.log(Status.PASS,  elementName +" successfully performed click action");
		
	} catch (Exception e) {
		test.log(Status.FAIL, "Failed to click " + elementName);
		test.log(Status.FAIL, "Exception :" + e.getMessage());
		ScreenCapture(driver, false,elementName);
	}

}



public static void getCellData() throws IOException
{
	
	fis=new FileInputStream("./TestData/Exceldata.xlsx");
	wb=new XSSFWorkbook(fis);
	sheet=wb.getSheet("Sheet3");

	int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

	  //Create a loop over all the rows of excel file to read it

	for (int i = 0; i < rowCount+1; i++) {

		Row row = sheet.getRow(i);

		//Create a loop to print cell values in a row

		for (int j = 0; j < row.getLastCellNum(); j++) {

		//Print Excel data in console

			System.out.print(row.getCell(j).getStringCellValue()+"|| ");

		}

		System.out.println();
	}
}

}

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
