package com.eva.zohoCRM.utilities;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.internal.MethodSelectorDescriptor;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Utils {

	private WebDriver driver;

	private ExtentReports extreport;

	private ExtentTest extTest;
private static Utils ut;
	/*
	 * This method will Launch Browser
	 * 
	 * @Param - String browserName
	 * 
	 * @Return- return WebDriver Object
	 */

	public WebDriver getDriver(String browser) {
		try {
			switch (browser.toLowerCase()) {

			case "chrome":
				ChromeOptions option=new ChromeOptions();
				option.addArguments("disable-notifications");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(option);
				extTest.log(Status.INFO, "Chrome Browser : launched Successfully ");
				break;
			case "firefox":
				FirefoxOptions firop=new FirefoxOptions();
				firop.addArguments("disable-notifications");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(firop);
				extTest.log(Status.INFO, " fireFox Browser : launched Successfully ");
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				extTest.log(Status.INFO, "Edge Browser : launched Successfully ");
				break;

			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, " Browser is not launch ");
			throw e;
		}

		return driver;
	}

	/*
	 * This method will return object of this class
	 * 
	 */
	public static Utils getInstance() {
		if(ut==null) {
		 ut = new Utils();
		}
		return ut;
	}

	/*
	 * this method will generate Reports
	 * 
	 */
	public void createExtentReport(String appliactionName) {
		String date = new SimpleDateFormat("-dd-MM-yyyy__HH_mm_ss---").format(new Date());
		extreport = new ExtentReports();
		File folder = new File("Reports");
		if (folder.exists() == false) {
			folder.mkdir();
		}
		extreport.setSystemInfo("OS", System.getProperty("os.name"));
		extreport.setSystemInfo("Host Name", "CI");
		extreport.setSystemInfo("Environment", "QA");
		extreport.setSystemInfo("User Name", "Ajeet Yadav");
		ExtentSparkReporter extSpark = new ExtentSparkReporter(
				System.getProperty("user.dir") + "/Reports/" + date + appliactionName + ".html");
		extSpark.config().setDocumentTitle("Zoho CRM Automation Reports");
		extSpark.config().setReportName("Test Reports");
		extreport.attachReporter(extSpark);
	}

	/*
	 * this method will create Test
	 * 
	 */
	public void createTestReport(String testcaseName) {
		extTest = extreport.createTest(testcaseName);

	}

	public void addtags(String tags) {
		extTest.assignCategory(tags);
	}

	/*
	 * this method will flush reports
	 * 
	 */
	public void flushReport() {

		extreport.flush();
	}

	/*
	 * this method will sleep for specific time which is provided
	 * 
	 */

	public void threadWait(int sleepMileSec) {
		try {
			Thread.sleep(sleepMileSec);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	// ***********************Browser Launch Generic method*******************
	/*
	 * This method will take screenshot of page where will it find exception
	 * 
	 * @Param -String imagetName
	 * 
	 * @Return- not return
	 */
	public String screenShot(String imageName) {
		DateFormat datef = new SimpleDateFormat("MM_dd_yyyy HH_MM_ss a");
		String dateTime = datef.format(new Date());
		TakesScreenshot tss = (TakesScreenshot) driver;
		File source = tss.getScreenshotAs(OutputType.FILE);

		File folder = new File("SnapShots");
		//

		if (folder.exists() == false) {
			folder.mkdir();
		}

		File finaldestination = new File(
				System.getProperty("user.dir") + "/SnapShots/" + imageName + dateTime + ".png");
		String destination = finaldestination.getAbsolutePath();
		try {
			Files.copy(source, finaldestination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destination;
	}

	// ****************driver generic method******************

	/*
	 * This method will wait for all element it is implicit wait by default run with
	 * driver for all element
	 * 
	 * @Param= int durationForWaitInSecon
	 * 
	 * @return -no return
	 */
	public void implicityWaitSecond(int durationInSecond) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(durationInSecond));
	}

	/*
	 * This method will wait for specific element until element not be enabled
	 * 
	 * @Param= int durationForWaitInSecond
	 * 
	 * @Param=WebElement object
	 * 
	 * 
	 * 
	 * @return -no return
	 */
	public void exWaitElementEnabled(int durationOfSecond, WebElement we) {
		String elementName = we.getAccessibleName();
		try {

			WebDriverWait exwaitObj = new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
			exwaitObj.until(ExpectedConditions.elementToBeClickable(we));
			extTest.log(Status.INFO, elementName + "   Element is enabled and clickabled ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, elementName + "   Element is not enabled and clickabled ");
			e.printStackTrace();
		}

	}
	/*
	 * This method will wait for specific element until page not be loaded
	 * 
	 * @Param= int durationForWaitInSecond
	 * 
	 * @Param=By objectForLocating
	 * 
	 * @Param=String elementName
	 * 
	 * @return -no return
	 */

	public void exWaitElementPresence(int durationOfSecond, By locator, String elementName) {
		try {
			WebDriverWait exwaitObj = new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
			exwaitObj.until(ExpectedConditions.presenceOfElementLocated(locator));
			extTest.log(Status.INFO, elementName + "  Element is present on html ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, elementName + "  Element is not present on html ");
			e.printStackTrace();
		}
	}

	/*
	 * This method will wait for specific element until Element not be visible
	 * 
	 * @Param= int durationForWaitInSecond
	 * 
	 * @Param=By objectForLocating
	 * 
	 * @Param=String elementName
	 * 
	 * @return -no return
	 */

	public void exWaitElementVisibility(int durationOfSecond, By locator, String elementName) {
		try {
			WebDriverWait exwaitObj = new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
			exwaitObj.until(ExpectedConditions.visibilityOfElementLocated(locator));
			extTest.log(Status.INFO, elementName + "  Element is visible on page ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, elementName + "  Element is not  visible on page ");
		}
	}
	/*
	 * This method will wait for specific element until Element text not be changed
	 * 
	 * @Param= int durationForWaitInSecond
	 * 
	 * @Param=By objectForLocating
	 * 
	 * @Param=String elementName
	 * 
	 * @Param=String textForMatch
	 * 
	 * @return -no return
	 */

	public void exWaitElementTextChange(int durationOfSecond, By locator, String elementName, String textForMactch) {
		try {
			WebDriverWait exwaitObj = new WebDriverWait(driver, Duration.ofSeconds(durationOfSecond));
			exwaitObj.until(ExpectedConditions.textToBePresentInElementLocated(locator, textForMactch));
			extTest.log(Status.INFO, elementName + "  Element is present on html ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, elementName + "  Element is present on html ");
		}
	}

	/* This method will Accept Alert Pop */
	public void alertAcceptPop() {
		try {
			driver.switchTo().alert().accept();
			extTest.log(Status.INFO, "Successfull : Accepted Alert pop up is handled");
		} catch (Exception e) {
			extTest.log(Status.FAIL, " Due to Exception pop up is not handled");
			e.printStackTrace();
		}
	}

	/*
	 * This method will Not accepted Alert Pop
	 * 
	 * 
	 */
	public void alertDeninePop() {
		try {
			driver.switchTo().alert().dismiss();
			extTest.log(Status.INFO, "Successfull : dissmised Alert pop");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception  pop up not dismissed");
			e.printStackTrace();
		}
	}
	/*
	 * 
	 * 
	 * This method will refresh page
	 * 
	 * 
	 * 
	 */

	public void refresh() {
		driver.navigate().refresh();
		extTest.log(Status.INFO, " Page refreshed ");
	}

	/*
	 * This method will Back to previous page
	 * 
	 * @return no return
	 */
	public void back() {
		driver.navigate().back();
		extTest.log(Status.INFO, " Come on previous window Successfully");
	}

	/*
	 * This method will take you to the next page
	 * 
	 * @return -no return
	 */
	public void forword() {
		driver.navigate().forward();
		extTest.log(Status.INFO, " Come on next window Successfully");
	}

	/*
	 * This method will Maximize the window
	 * 
	 * @Return-Not return
	 */
	public void maximize() {
		driver.manage().window().maximize();
		extTest.log(Status.INFO, " Full screen window Successfully");
	}

	/*
	 * This method set the size of window in desktop
	 * 
	 * @Param - Dimension object
	 * 
	 * @Return - no return
	 * 
	 */
	public void setwindowSize(Dimension object) {
		driver.manage().window().setSize(object);
		extTest.log(Status.INFO, "  Successfully set Size of Window ");
	}

	/*
	 * This method set the Position of window in desktop
	 * 
	 * @Param - Point object
	 * 
	 * @Return - no return
	 * 
	 */
	public void setWindowPosition(Point object) {

		driver.manage().window().setPosition(object);
		extTest.log(Status.INFO, " Successfully set Location of Window ");
	}

	/*
	 * This method retrieve the size of Element
	 * 
	 * @Param - WebElement object
	 * 
	 * 
	 * 
	 * @Return - Array int
	 * 
	 */
	public int[] getElementSize(WebElement we) {
		String elementName = we.getAccessibleName();
		Dimension dimension = we.getSize();
		int hieghtOfElment = dimension.getHeight();
		int widthOfElement = dimension.getWidth();
		int elementSize[] = { hieghtOfElment, widthOfElement };
		extTest.log(Status.INFO,
				"Retrieve Size of element - [" + elementName + " ] and return the 1 : height and 2: width ");
		return elementSize;

	}
	/*
	 * This method retrieve the Location of Element
	 * 
	 * @Param - WebElement object
	 * 
	 * 
	 * 
	 * @Return - Array int
	 * 
	 */

	public int[] getElementLocation(WebElement we) {
		String elementName = we.getAccessibleName();
		Point point = we.getLocation();
		int positionOfXcoOrdinate = point.getX();
		int positionOfYcoOrdinate = point.getY();
		int elementLocation[] = { positionOfXcoOrdinate, positionOfYcoOrdinate };
		extTest.log(Status.INFO, "Retrieve location  of element - [ " + elementName
				+ " ] and return the 1 : X cordinate and 2: Y coordinate ");
		return elementLocation;

	}

	/*
	 * This method will Open Url at browser
	 * 
	 * @Param-String url
	 * 
	 * @Return- not return
	 */
	public void openUrl(String url) {
		try {

			driver.get(url);
			extTest.log(Status.INFO, url + " - Opened Successfully  ");
		} catch (Exception e) {

			extTest.log(Status.FAIL, "URL " + url + " - did not  Open ");
			throw e;
		}
	}

	/*
	 * This method will take focus on Frame
	 * 
	 * @Param- WebElement we
	 * 
	 * 
	 * 
	 * @Return- not return
	 */
	public void switchToFrameByWebElement(WebElement we) {
		String elementName = we.getAccessibleName();
		try {
			driver.switchTo().frame(we);
			extTest.log(Status.INFO, elementName + " -  Focus on the current iframe  ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, elementName + " - Not change focus ");
		}
	}

	/*
	 * This method will take focus on main window from frame
	 * 
	 * @Return- not return
	 */
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		extTest.log(Status.INFO, "  Focus on the main page from iFrame ");
	}
	/*
	 * This method will switch focus on window behalf title
	 * 
	 * @Param- String title
	 * 
	 * @Return- not return
	 */

	public void switchTowindowByTitle(String switchWindowTitle) {
		try {
			Set<String> windows = driver.getWindowHandles();

			for (String window : windows) {
				driver.switchTo().window(window);
				String title = driver.getTitle();
				if (title.equals(switchWindowTitle)) {
					extTest.log(Status.INFO, "  Focus on the New Window by title  ");
					break;
				}
			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "  Window did not handal and Focus Not change  - " + switchWindowTitle);
		}
	}

	/*
	 * This method will switch focus on window behalf contain title
	 * 
	 * @Param- String containfromTitle
	 * 
	 * @Return- not return
	 */
	public void switchTowindowByTitleContains(String switchWindowTitle) {
		try {
			Set<String> windows = driver.getWindowHandles();

			for (String window : windows) {
				driver.switchTo().window(window);
				String title = driver.getTitle();
				if (title.contains(switchWindowTitle)) {
					extTest.log(Status.INFO, "  Focus on the New Window by some contains of matching  title  ");
					break;
				}
			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "  Window did not handal and Focus Not change  - " + switchWindowTitle);
		}

	}
	/*
	 * This method will switch focus on window behalf URL
	 * 
	 * @Param- String URL
	 * 
	 * @Return- not return
	 */

	public void switchTowindowByURL(String switchWindowURL) {
		try {
			Set<String> windows = driver.getWindowHandles();

			for (String window : windows) {
				driver.switchTo().window(window);
				String urkValue = driver.getCurrentUrl();
				if (urkValue.equals(switchWindowURL)) {
					extTest.log(Status.INFO, "  Focus on the New Window by URL  ");
					break;
				}
			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "  Window did not handal and Focus Not change  and url is - " + switchWindowURL);
		}
	}

	/*
	 * This method will switch focus on window behalf URL contain
	 * 
	 * @Param- String containFromURL
	 * 
	 * @Return- not return
	 */

	public void switchTowindowByURLConatains(String switchWindowURL) {
		try {
			Set<String> windows = driver.getWindowHandles();

			for (String window : windows) {
				driver.switchTo().window(window);
				String urkValue = driver.getCurrentUrl();
				if (urkValue.contains(switchWindowURL)) {
					extTest.log(Status.INFO,
							"  Focus on the New Window by some contains of matching  URL - " + switchWindowURL);
					break;
				}
			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "  Window did not handal and Focus Not change  and url is -  " + switchWindowURL);

		}

	}
	/*
	 * This method will retrieve url of page
	 * 
	 * @Param- String pageName
	 * 
	 * @Return- not return
	 */

	public String getCurrentURL(String pageName) {
		String url = driver.getCurrentUrl();
		extTest.log(Status.INFO, pageName + "URL is -[  " + url + " ] Successfilly retrieve the url of the page ");

		return url;
	}
	/*
	 * This method will retrieve title of page
	 * 
	 * @Param- String pagetitle
	 * 
	 * @Return- not return
	 */

	public String getTitle(String pageName) {
		String title = driver.getTitle();
		extTest.log(Status.INFO, "Successfully return the title of page : - " + pageName);
		return title;

	}

	/*
	 * This method will checked check boxs
	 * 
	 * @Param -WebElemet we
	 * 
	 * 
	 * 
	 * @Return- not return
	 */
	public void checkedCheckBox(WebElement we) {
		try {
			if (we.isSelected() == false) {
				we.click();
				extTest.log(Status.INFO, "Successfully checked  the check  check Box");
			}
		} catch (StaleElementReferenceException e) {
			we.click();
			extTest.log(Status.INFO, "   check Box is checked after stale Exception");

		} catch (Exception e) {
			extTest.log(Status.FAIL, " Due to Exception Check Box is not checked");
			extTest.addScreenCaptureFromPath(screenShot("checkBox"));
			e.printStackTrace();
		}

	}

	/*
	 * This method will checked all check boxses
	 * 
	 * @Param -List<WebElement> object
	 * 
	 * 
	 * 
	 * @Return- not return
	 */
	public void ckeckedAllCheckBoxes(List<WebElement> listCheckedBoxes) {
		String ElementName = "";
		WebElement weCheckeBox = null;
		try {
			for (int i = 0; i < listCheckedBoxes.size(); i++) {
				weCheckeBox = listCheckedBoxes.get(i);
				ElementName = weCheckeBox.getAccessibleName();
				if (weCheckeBox.isSelected() == false) {
					weCheckeBox.click();
				}
			}
			extTest.log(Status.INFO, "Successfully checked all check Boxes  Which is : - [  " + ElementName + " ] ");

		} catch (StaleElementReferenceException e) {
			weCheckeBox.click();
			extTest.log(Status.INFO, "Successfully checked  After Stale exception all check Boxes  Which is : - [  "
					+ ElementName + " ] ");
		} catch (Exception e) {

			extTest.log(Status.FAIL, " not checked all check Boxes  Which is : - [  " + ElementName + " ]");
			extTest.addScreenCaptureFromPath(screenShot(ElementName));
			throw e;
		}

	}

	/*
	 * This method will unchecked all check boxses
	 * 
	 * @Param - List<WebElement> object
	 * 
	 *
	 * 
	 * @Return- not return
	 */
	public void unckeckedAllCheckBoxes(List<WebElement> listCheckedBoxes) {
		String ElementName = "";
		WebElement weCheckeBox = null;
		try {
			for (int i = 0; i < listCheckedBoxes.size(); i++) {
				weCheckeBox = listCheckedBoxes.get(i);
				ElementName = weCheckeBox.getAccessibleName();
				if (weCheckeBox.isSelected() == true) {
					weCheckeBox.click();
				}
			}
			extTest.log(Status.INFO, "Successfully unchecked all check Boxes  Which is : - [  " + ElementName + " ] ");

		} catch (StaleElementReferenceException e) {
			weCheckeBox.click();
			extTest.log(Status.INFO, "Successfully  unchecked  After Stale exception all check Boxes  Which is : - [  "
					+ ElementName + " ] ");
		} catch (Exception e) {

			extTest.log(Status.FAIL, " not unchecked all check Boxes  Which is : - [  " + ElementName + " ]");
			extTest.addScreenCaptureFromPath(screenShot(ElementName));
			throw e;
		}

	}

	/*
	 * This method will search element on HTML
	 * 
	 * @Param - By object
	 * 
	 * @Return- WebElement object
	 * 
	 */

	/*
	 * This method will close All browser
	 * 
	 */
	public void tearDown_Quit() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}
	/*
	 * This method will close current working browser
	 * 
	 */

	public void closeWindow() {
		driver.close();
	}

	// ****** Actions class generic method *****/

	/*
	 * This method will mouseOver on the element After that type text in element
	 * 
	 * @Param - WebElement we
	 * 
	 * @Param- String value
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */

	public void actionSendkeys(WebElement we, String value) {
		String elementName = we.getAccessibleName();
		Actions act = new Actions(driver);
		try {
			clear(we);
			act.sendKeys(value);
			extTest.log(Status.INFO,
					"Successfully Value - [" + value + "] enterd in the - [ " + elementName + " ] text box  ");
		} catch (ElementNotInteractableException e) {
			jsMouseOver(we, elementName);
			jsInputValueMethod(we, value, elementName);
		} catch (StaleElementReferenceException e) {
			clear(we);
			act.sendKeys(value);
			extTest.log(Status.INFO, "Successfully Value - [ " + value + " ] entered in the - [ " + elementName
					+ "] text box : After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL,
					"Value could not be Entered in - [ " + elementName + " ] text box  Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));

			throw e;
		}

	}

	/*
	 * This method will mouseOver on the element After that click on element
	 * 
	 * @Param - WebElement we
	 * 
	 * 
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionClick(WebElement we) {
		String elementName = we.getAccessibleName();
		Actions act = new Actions(driver);

		try {
			act.click(we).build().perform();
			extTest.log(Status.INFO, "Successfully : Maouse  over and After that click on Element : " + elementName);
		} catch (ElementNotInteractableException e) {
			jsMouseOver(we, elementName);
			jsClickMethod(we, elementName);

		} catch (StaleElementReferenceException e) {

			act.click(we).build().perform();

			extTest.log(Status.INFO, "mouse clicked on element after handling stale exception : - " + elementName);
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Action not PerForm on Element :  - " + elementName);
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will mouseOver on the element After that send value in the Text
	 * box
	 * 
	 * 
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - String valueFor Send In textBox
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionInputValue(WebElement we, String value) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		Actions act = new Actions(driver);
		String elementName = we.getAccessibleName();
		try {
			act.sendKeys(we, value).build().perform();
			extTest.log(Status.INFO,
					"Successfully mouceOver and After that enter value in the [" + elementName + " ] text Box ");

		} catch (ElementNotInteractableException e) {
			jsMouseOver(we, elementName);
			jsInputValueMethod(we, value, elementName);

		} catch (StaleElementReferenceException e) {

			act.sendKeys(we, value).build().perform();
			extTest.log(Status.INFO, "Successfully mouceOver and After that enter value in the [" + elementName
					+ " ] text Box After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Action not PerForm on Element :  - " + elementName);
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will mouseOver on the element
	 * 
	 * @Param - WebElement object
	 * 
	 * 
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionMouseOver(WebElement we) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		Actions act = new Actions(driver);
		String elementName = "";
		try {
			elementName = we.getAccessibleName();
			act.moveToElement(we).build().perform();
			extTest.log(Status.INFO, "Successfully mouceOver on the element  [" + elementName + " ] ");

		} catch (ElementNotInteractableException e) {
			jsMouseOver(we, elementName);

		} catch (StaleElementReferenceException e) {

			act.moveToElement(we).build().perform();
			extTest.log(Status.INFO,
					"Successfully mouceOver on the element  [" + elementName + " ] After Handaling Stale Exception");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Action not perform ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will pick the element from one place and drop another place
	 * 
	 * @Param- WebElement dragObject
	 * 
	 * @Param - WebElement dropObject
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionDragandDrop(WebElement we, WebElement we1) throws InterruptedException {

		// WebElement we = searchElementOnHTML(darglocator, elementName);
		// WebElement we1 = searchElementOnHTML(droplocator, elementName);
		Actions act = new Actions(driver);
		String elementName = "";
		try {
			elementName = we.getAccessibleName();
			act.dragAndDrop(we, we1).build().perform();
			extTest.log(Status.INFO, "Successfully Drag one place and Drop another Place  ");

		} catch (ElementNotInteractableException e) {
			jsdragAndDrop(we, we1, elementName);

		} catch (StaleElementReferenceException e) {

			act.dragAndDrop(we, we1).build().perform();
			extTest.log(Status.INFO,
					"Successfully Drag from one place and Drop another place  After handaled Stale Exceptiopn ");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Darg and Drop Action not perform ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * This method will perform vertical scroll or up-down /down-up
	 * 
	 * @Param - WebElement object
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionScroll(WebElement we)  {
		String elementName = "";
		Actions act = new Actions(driver);
		try {
			elementName = we.getAccessibleName();
			act.scrollToElement(we).build().perform();
			extTest.log(Status.INFO, "Successfully Scroll to Element  ");

		} catch (ElementNotInteractableException e) {
			jsMouseOver(we, elementName);
			jsScroll(we);

		} catch (StaleElementReferenceException e) {

			act.scrollToElement(we).build().perform();
			extTest.log(Status.INFO, "Successfully Scroll to Element After Handling Stale Exception ");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Scroll Action Not Perform  ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}

	}
	/*
	 * This method will perform vertical scroll or up-down /down-up
	 * 
	 * @Param - int xCoordinate,int y Coordinate
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionScroll(int xcordinate, int ycordinate)  {
		
		Actions act = new Actions(driver);
		try {
			
			act.scrollByAmount(xcordinate,ycordinate).build().perform();
			extTest.log(Status.INFO, "Successfully Scroll to Element  ");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to Exception Scroll Action Not Perform  ");
			extTest.addScreenCaptureFromPath(screenShot("Sroll"));
			throw e;
		}

	}
	/*
	 * This method will mouseOver on the element After that Double click on element
	 * 
	 * @Param - WebElement object
	 * 
	 * @Return- Not return AnyThings
	 * 
	 */
	public void actionDoubleClick(WebElement we) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		Actions act = new Actions(driver);
		String elementName = "";
		try {
			elementName = we.getAccessibleName();
			act.doubleClick(we).build().perform();
			extTest.log(Status.INFO, "Successfully perform double Click on  Element [  " + elementName + " ]");

		} catch (ElementNotInteractableException e) {
			jsMouseOver(we, elementName);
			jsDoubleClick(we, elementName);

		} catch (StaleElementReferenceException e) {

			act.doubleClick(we).build().perform();
			extTest.log(Status.INFO, "Successfully perform double Click on  Element [  " + elementName
					+ " ] After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL,
					"Due To Exception Double Click Action not perform on element : [ " + elementName + "]");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}

	}
	
	
	// *******************Select class generic method**************

	/*
	 * this method will select option from dropdown by option visible text
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - String Visible Text
	 * 
	 * @return - no return
	 */
	public void selectByText(WebElement we, String textToSelect) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		String elementName = "";
		Select selectDD = new Select(we);
		try {
			elementName = we.getAccessibleName();
			selectDD.selectByVisibleText(textToSelect);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Visisble Text  ");
		} catch (ElementNotInteractableException e) {
			jsDropdown(we, textToSelect);

		} catch (StaleElementReferenceException e) {
			selectDD = new Select(we);
			selectDD.selectByVisibleText(textToSelect);
			extTest.log(Status.INFO,
					"Successfully Selected text in Drop Down Through Visisble Text After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception DropDown From visible text was not selected   ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will select option from dropdown by optioon index number and
	 * index number starts from 0
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - int indexNumber
	 * 
	 * @return - no return
	 */
	public void selectByIndex(WebElement we, int optionIndex) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		String elementName = "";
		Select selectDD = new Select(we);
		try {
			elementName = we.getAccessibleName();
			selectDD.selectByIndex(optionIndex);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Index   ");
		} catch (StaleElementReferenceException e) {
			selectDD = new Select(we);
			selectDD.selectByIndex(optionIndex);
			extTest.log(Status.INFO,
					"Successfully Selected text in Drop Down Through Index After Handaling Stale Exception  ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception DropDown From Index was not selected    ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will select option from dropdown by option Attribute value
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - String value Attribute value
	 * 
	 * @return - no return
	 */
	public void selectByValue(WebElement we, String optionValueAttribute) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		Select selectDD = new Select(we);
		String elementName = "";
		try {
			elementName = we.getAccessibleName();
			selectDD.selectByValue(optionValueAttribute);
			extTest.log(Status.INFO, "Successfully Selected text in Drop Down Through Value Attribute Value    ");
		} catch (ElementNotInteractableException e) {
			jsDropdown(we, optionValueAttribute);

		} catch (StaleElementReferenceException e) {
			selectDD = new Select(we);
			selectDD.selectByValue(optionValueAttribute);
			extTest.log(Status.INFO,
					"Successfully Selected text in Drop Down Through Value Attribute Value After Handaling Stale Exception   ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception DropDown From Value  was not selected    ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will select option from dropdown by Partial text
	 *
	 * @Param - WebElement object
	 * 
	 * @Param - String partialvisibletext
	 * 
	 * @Param - Element Name
	 * 
	 * @return - no return
	 */
	public void selectByTextContains(WebElement we, String selectText) {
		int indexNumber = -1;
		// WebElement we = searchElementOnHTML(locator, elementName);
		String elementName = "";
		Select slt = new Select(we);
		List<WebElement> weListOption = null;
		try {
			elementName = we.getAccessibleName();
			weListOption = slt.getOptions();
			for (int i = 0; i <= weListOption.size() - 1; i++) {
				WebElement weOption = weListOption.get(i);
				String optionText = weOption.getText();
				if (optionText.contains(selectText) == true) {
					indexNumber = i;
				}
			}
		} catch (StaleElementReferenceException e) {
			weListOption = slt.getOptions();
			for (int i = 0; i <= weListOption.size() - 1; i++) {
				WebElement weOption = weListOption.get(i);
				String optionText = weOption.getText();
				if (optionText.contains(selectText) == true) {
					indexNumber = i;
				}
			}

		} catch (Exception e) {
			extTest.log(Status.FAIL, "" + elementName + " Drop Down is not select through Partial Visible Text");

			throw e;
		}
		slt.selectByIndex(indexNumber);
		extTest.log(Status.INFO,
				"Successfully " + elementName + "Selected text in Drop Down Through Partial Visisble text");
	}
	/*
	 * this method will retrieve All Selected Text from Drop Down
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param -
	 * 
	 * @return - List of All selected text
	 */

	public List<String> getAllSelectedOptions(WebElement we) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		Select slt = new Select(we);
		String elementName = "";
		List<String> list = new ArrayList<>();
		List<WebElement> listofSelectedOptions = null;
		try {
			elementName = we.getAccessibleName();
			listofSelectedOptions = slt.getAllSelectedOptions();
			for (WebElement weOptions : listofSelectedOptions) {
				String strSelectedoptions = weOptions.getText();
				list.add(strSelectedoptions);
				extTest.log(Status.INFO, "Successfully Retrieve the Inner Text Of" + elementName
						+ " All Slected Option from Drop Down    ");
			}

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception not retrieve inner Text from " + elementName + "drop down ");
			e.printStackTrace();
		}
		return list;

	}

	/*
	 * this method will retrieve Select text from drop down
	 * 
	 * @Param - WebElement
	 * 
	 * 
	 * @return - Select Text in Drop Down
	 */
	public String getDropdownSelectedText(WebElement we) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		String elementName = "";
		Select slt = new Select(we);
		String selectedOp = null;
		try {
			elementName = we.getAccessibleName();
			WebElement selectOption = slt.getFirstSelectedOption();
			selectedOp = selectOption.getText();
			extTest.log(Status.INFO,
					"Successfully : get Selected option in Drop Down " + elementName + " and return it  ");
		} catch (Exception e) {

			extTest.log(Status.FAIL, "Due to Excetion not get selected otption from " + elementName + "drop down");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			e.printStackTrace();
		}
		return selectedOp;
	}

	/*
	 * this method will retrieve All options from Drop Down
	 * 
	 * @Param - WebElement
	 * 
	 * 
	 * 
	 * @return - List of All options
	 */
	public List<String> getTextAllOptionsDropdown(WebElement we) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		Select slt = new Select(we);
		String elementName = we.getAccessibleName();
		List<String> alloptions = new ArrayList<String>();
		List<WebElement> allop = slt.getOptions();
		for (int i = 0; i < allop.size() - 1; i++) {
			WebElement weAllop = allop.get(i);
			String ailoption = weAllop.getText();
			alloptions.add(ailoption);
		}
		extTest.log(Status.INFO,
				"Successfully get the all Option from " + elementName + "the drop down and return text of all   ");

		return alloptions;

	}

	/*
	 * this method will retrieve count of All options from Drop Down
	 * 
	 * @Param - WebElement
	 * 
	 * 
	 * @return - int number
	 */
	public int getAllOptionCount(WebElement we) {
		// we = searchElementOnHTML(locator, elementName);
		Select slt = new Select(we);
		String elementName = we.getAccessibleName();
		List<WebElement> ListAlloptions = slt.getOptions();
		int allOptions = ListAlloptions.size();
		extTest.log(Status.INFO, "Successfully get the all Option from [ " + elementName
				+ " ] drop down and return All count of text  ");
		return allOptions;

	}

	// **************java script generic method********************

	/*
	 * this method will select drop Down
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - String selectText
	 * 
	 * @return - not return anything
	 */

	public void jsDropdown(WebElement select, String value) {

		try {

			((JavascriptExecutor) driver).executeScript("var select = arguments[0]; for(var i = 0;"
					+ " i < select.options.length; i++){ if(select.options[i].text == arguments[1])"
					+ "{ select.options[i].selected = true; } }", select, value);
			extTest.log(Status.INFO,
					"Successfully Selected text in Drop Down Through Visisble Text  ,Through java Script ");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Drop Down is not select by java script");
			throw e;
		}
	}

	/*
	 * this method will type value in text Box
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - String value
	 * 
	 * @Param- String elemeName
	 * 
	 * @return - not return anything
	 */
	public void jsInputValueMethod(WebElement we, String value, String elementName) {
		try {
			// WebElement we = driver.findElement(locator);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='" + value + "';", we);

			extTest.log(Status.INFO,
					"Successfully enter value in the [" + elementName + " ] text Box  through java script ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Value not entered  in the [" + elementName + " ] text Box  through java script ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
		}
	}

	/*
	 * this method will click on button
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - String elementName
	 * 
	 * @return - not return anything
	 */
	public void jsClickMethod(WebElement we, String elementName) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", we);
			extTest.log(Status.INFO,
					"Successfully : Maouse  over and After that click on Element Through Java script : - "
							+ elementName);
		} catch (Exception e) {
			extTest.log(Status.FAIL, "click  action is not perform by java script");
			throw e;
		}
	}

	/*
	 * this method will mouse over on element
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param -String elementName
	 * 
	 * @return - not return anything
	 */
	public void jsMouseOver(WebElement we, String elementName) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript(
					"if(document.createEvent){var evObj = " + "document.createEvent('MouseEvents');evObj.initEvent"
							+ "('mouseover',true, false); arguments[0].dispatchEvent(evObj);} "
							+ "else if(document.createEventObject) " + "{ arguments[0].fireEvent('onmouseover');}",
					we);
			extTest.log(Status.INFO,
					"Successfully mouceOver on the element  [" + elementName + " ] Through javascript");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Mouse over action is not perform by java script");
		}
	}

	/*
	 * this method will drag and drop from one place to another place to element
	 * 
	 * @Param - WebElement drag
	 * 
	 * @Param - WebElement drop
	 * 
	 * @Param -String elementName
	 * 
	 * @return - not return anything
	 */
	public void jsdragAndDrop(WebElement source, WebElement destination, String elementName) {
		try {
			Actions act = new Actions(driver);
			act.moveToElement(source).pause(Duration.ofSeconds(1)).clickAndHold(source).pause(Duration.ofSeconds(1))
					.moveByOffset(1, 0).moveToElement(destination).moveByOffset(1, 0).pause(Duration.ofSeconds(1))
					.release().perform();

			extTest.log(Status.INFO, "Successfully Drag And Drop action perfrom by js   ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, " Drag And Drop action is not perfrom by js   ");
			throw e;
		}
	}

	/*
	 * this method will scroll Vertical
	 * 
	 * @Param - WebElement object
	 * 
	 * @return - not return anything
	 */
	public void jsScroll(WebElement we)  {
		// WebElement we = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extTest.log(Status.INFO, "Successfully Scroll through java script   ");
	}

	public void jsScrollVertically( int y)  {
		// WebElement we = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, "+y+");");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extTest.log(Status.INFO, "Successfully Scroll through java script   ");
	}
	
	
	
	public void jsScrollUpToBottom(WebElement we)  {
		// WebElement we = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)", we);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extTest.log(Status.INFO, "Successfully Scroll through java script   ");
	}
	/*
	 * this method will perform double click on element
	 * 
	 * @Param - WebElement object
	 * 
	 * String elementName
	 * 
	 * @return - not return anything
	 */
	public void jsDoubleClick(WebElement we, String elementName) {
		// WebElement we = driver.findElement(locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', { bubbles: true }));", we);
		extTest.log(Status.INFO,
				"Successfully perform double Click on  Element [  " + elementName + " ]  Through Javascript ");
	}

	// ****************WebElement generic method****************\\\\

	public List<String> getInnerTextOfElements(List<WebElement> elements) {
		List<String> innertextValues = new ArrayList<>();
		String elementName = "";
		try {
			for (WebElement we : elements) {
				String value = we.getText();
				innertextValues.add(value);
			}

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Element could not be clicked Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			e.printStackTrace();
			;

		}
		extTest.log(Status.FAIL, "Successfully fetch the innertext value of element and return");
		return innertextValues;
	}

	/*
	 * this method will clear text Box
	 * 
	 * @Param - WebElement object
	 * 
	 * @return - not return anything
	 */
	public void clear(WebElement we) {
		String elementName = "";
		try {
			elementName = we.getAccessibleName();
			// WebElement we = searchElementOnHTML(locator, elementName);
			we.clear();
			extTest.log(Status.INFO, "Successfully Text Box will  clear[" + elementName + "] ");
		} catch (StaleElementReferenceException e) {
			we.clear();
			extTest.log(Status.INFO, "Successfully Text Box will  clear[" + elementName + "] After Stale Exception");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to exception text box  will not  clear[" + elementName + "]  ");
			throw e;
		}
	}

	/*
	 * this method will fetch attribute value
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param-String attrinuName
	 * 
	 * @return - Attribute value
	 */
	public String getAttribute(WebElement we, String attributeName) {
		String elementName = "";
		String attributename = "";
		try {
			elementName = we.getAccessibleName();
			attributename = we.getAttribute(attributeName);
			extTest.log(Status.INFO, "Successfully Fetch" + elementName + " attribute value ");

		} catch (StaleElementReferenceException e) {
			attributename = we.getAttribute(attributeName);
			extTest.log(Status.INFO,
					"Successfully Fetch" + elementName + " attribute value  After Handaling stale Exception");

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to exception not Fetch " + elementName + "attribute value ");
			e.printStackTrace();
		}
		return attributename;
	}

	/*
	 * this method will click on element
	 * 
	 * @Param - WebElement object
	 * 
	 * 
	 * @return - not return anything
	 */
	public void click(WebElement we) {
		String elementName = "";
		try {
			elementName = we.getAccessibleName();
			we.click();
			extTest.log(Status.INFO, "Successfully clicked on the [ " + elementName + " ] element  ");
		} catch (ElementNotInteractableException e) {

			jsClickMethod(we, elementName);
		} catch (StaleElementReferenceException e) {
			we.click();
			extTest.log(Status.INFO,
					"Successfully clicked on the [ " + elementName + " ] element After Handaling stale Exception  ");
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Element could not be clicked Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}
	}

	/*
	 * this method will Type vale in Text Box
	 * 
	 * @Param - WebElement object
	 * 
	 * 
	 * @Param- String value for send
	 * 
	 * @return - not return anything
	 */
	public void inputValue(WebElement we, String value) {
		String elementName = "";
		try {
			elementName = we.getAccessibleName();
			clear(we);
			we.sendKeys(value);
			extTest.log(Status.INFO,
					"Successfully Value - [" + value + "] enterd in the - [ " + elementName + " ] text box  ");
		} catch (ElementNotInteractableException e) {
			jsInputValueMethod(we, value, elementName);
		} catch (StaleElementReferenceException e) {
			clear(we);
			we.sendKeys(value);
			extTest.log(Status.INFO, "Successfully Value - [ " + value + " ] entered in the - [ " + elementName
					+ "] text box : After Handaling Stale Exception ");
		} catch (Exception e) {
			extTest.log(Status.FAIL,
					"Value could not be Entered in - [ " + elementName + " ] text box  Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}

	}

	/*
	 * this method will retrieve inner text of element
	 * 
	 * @Param - WebElement object
	 * 
	 * 
	 * @return - String innerTextOfElement
	 */
	public String getText(WebElement we) {
		String elementName = "";
		String innertext = "";
		try {
			
			innertext = we.getText();
			extTest.log(Status.INFO, "Successfully Retrieve inner Text - " + elementName);

		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due To Exception Inner text not found");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			e.printStackTrace();

		}
		return innertext;

	}
	/* Verification method */

	/*
	 * this method will verify two string
	 * 
	 * @Param - String actual
	 * 
	 * @Param - String expected
	 * 
	 * @return - not return anything
	 */

	public void verifyText(String actual, String expected) {
		if (actual.equals(expected)) {

			extTest.log(Status.PASS, "Actual Text [ " + actual + "] And Expected Text  matched  [" + expected + "  ]");
		} else {
			extTest.log(Status.FAIL, "Actual Text [ " + actual + "] And Expected Text not  match [" + expected + "  ]");
		}
	}
	/*
	 * this method will verify two string
	 * 
	 * @Param - String actual
	 * 
	 * @Param - String expected
	 * 
	 * @return - not return anything
	 */

	public void verifyTextContains(String actual, String expectedContains) {
		if (actual.contains(expectedContains)) {

			extTest.log(Status.PASS,
					"Actual Text [ " + actual + "] And Expected contains  matched  [" + expectedContains + "  ]");
		} else {
			extTest.log(Status.FAIL,
					"Actual Text [ " + actual + "] And Expected contains not  match [" + expectedContains + "  ]");
		}
	}
	/*
	 * this method will verify element is displayed on ui or not
	 * 
	 * @Param - WebElement object
	 * 
	 * @return - not return anything
	 */

	public void verifyElementVisiblility(WebElement we) {
		try {
			boolean status = we.isDisplayed();
			if (status == true) {
				extTest.log(Status.PASS, "Element is Dipalyed on UI");
			} else {
				extTest.log(Status.FAIL, "Element is not Dipalyed on UI");

			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to exception Action is not completed");
			e.printStackTrace();
		}
	}
	/*
	 * this method will verify element is Enable
	 * 
	 * @Param - WebElement object
	 * 
	 * @return - not return anything
	 */

	public void verifyElementEnable(WebElement we) {
		try {
			boolean status = we.isEnabled();
			if (status == true) {
				extTest.log(Status.PASS, "Element is Enable ");
			} else {
				extTest.log(Status.FAIL, "Element is Disable");

			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to exception Action is not completed");
			e.printStackTrace();
		}

	}
	/*
	 * this method will verify check box is checked or not
	 * 
	 * @Param - WebElement object
	 * 
	 * @return - not return anything
	 */

	public void verifyElementCheckBoxChecked(WebElement we) {
		try {
			boolean status = we.isSelected();
			if (status == true) {
				extTest.log(Status.PASS, "Checked box is checked ");
			} else {
				extTest.log(Status.FAIL, "Checked box is not check ");

			}
		} catch (Exception e) {
			extTest.log(Status.FAIL, "Due to exception Action is not completed");
			e.printStackTrace();
		}

	}

	/*
	 * this method will verify two string
	 * 
	 * 
	 * 
	 * @Param - String title
	 * 
	 * @return - not return anything
	 */
	public void verifyTitle(String expected) {
		String actual = driver.getTitle();
		if (actual.equals(expected)) {

			extTest.log(Status.PASS,
					"Actual Title [ " + actual + "] And Expected Title  matched  [" + expected + "  ]");
		} else {
			extTest.log(Status.FAIL,
					"Actual Title [ " + actual + "] And Expected Title not  match [" + expected + "  ]");
		}
	}

	/* * ------ Table scenario generic method --------------- */

	/*
	 * this method will retrieve column Number
	 * 
	 * 
	 * @Param - WebElement object
	 * 
	 * @Param - string column Name
	 * 
	 * @return - int column Number
	 */

	public int getColumnNumberByColumnName(String xpath, String columnName) {
		int columnNumber = -1;
		List<WebElement> listcolumnName = driver.findElements(By.xpath(xpath));
		int columnCount = listcolumnName.size();
		for (int i = 0; i <= columnCount - 1; i++) {
			WebElement weTableCol = listcolumnName.get(i);
			String colname = weTableCol.getText();
			if (colname.equalsIgnoreCase(columnName)) {
				columnNumber = i;
				break;
			}
		}
		return columnNumber;
	}

	public String getTableDataByUniqueData(String tableXpath, String uniqueData, String uniqueDataColumnName,
			String requiredDataColumnName) {
		/// get row number of account number
		/// because account name is in same row
		int rowNumber = 0;
		List<WebElement> listRows = driver.findElements(By.xpath(tableXpath + "//tr"));
		int cn1 = getColumnNumberByColumnName(tableXpath, uniqueDataColumnName);
		int cn2 = getColumnNumberByColumnName(tableXpath, requiredDataColumnName);

		for (int i = 0; i <= listRows.size() - 1; i++) {
			String text = driver.findElement(By.xpath(tableXpath + "//tr[" + (i + 1) + "]//td[" + cn1 + "]")).getText();
			if (text.equalsIgnoreCase(uniqueData)) {
				rowNumber = i;
			}
		}
		String requiredText = driver.findElement(By.xpath(tableXpath + "//tr[" + rowNumber + "]//td[" + cn2 + "]"))
				.getText();
		return requiredText;

	}

	public String verifyTableDataByHeaderName(String tablexPath, String uniqueHeaderNeme, String dataForVrfy) {

		List<WebElement> list = null;

		String found = "";
		int colCount = 0;
		try {

			list = driver.findElements(By.xpath(tablexPath + "//tr[1]//td"));
			for (int i = 0; i <= list.size() - 1; i++) {
				WebElement we = list.get(i);
				String headers = we.getText();
				if (uniqueHeaderNeme.equals(headers)) {
					i = i + 1;
					colCount = i;
					break;
				}

			}
			List<WebElement> weuniqueColDataList = driver.findElements(By.xpath(tablexPath + "//td[" + colCount + "]"));

			for (int j = 1; j <= weuniqueColDataList.size() - 1; j++) {
				WebElement weUniueData = weuniqueColDataList.get(j);
				String allUniqueData = weUniueData.getText();
				if (dataForVrfy.equalsIgnoreCase(allUniqueData)) {
					found = allUniqueData;
					break;
				}
			}

			extTest.log(Status.INFO, "Successfully found unique Name " + dataForVrfy);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			extTest.log(Status.FAIL, "not find unique Name " + dataForVrfy);

		}
		return found;
	}

//***********************************************Action perform by KeYboard Physically******************

	/*
	 * this method will send value from text box and click on'ENTER'key
	 * 
	 * 
	 * @Param - WebElement
	 * 
	 * @Param - String value For send
	 * 
	 * 
	 * 
	 * @return - not return
	 */

	public void InputValue_PressEnterKeys(WebElement we, String value) {
		String elementName = we.getAccessibleName();
		try {
			clear(we);
			we.sendKeys(value);
			we.sendKeys(Keys.ENTER);
			extTest.log(Status.INFO,
					"Successfully Value enterd in the -[ " + elementName + " ]-text box  And click on Enter Button ");

		} catch (ElementNotInteractableException e) {
			jsInputValueMethod(we, value, elementName);
		} catch (StaleElementReferenceException e) {
			clear(we);
			we.sendKeys(value);
			we.sendKeys(Keys.ENTER);

			extTest.log(Status.INFO, "Successfully Value enterd in the -[  " + elementName
					+ " ]text box  And click on Enter Button After Handaling Stale Exception");
		} catch (Exception e) {
			extTest.log(Status.FAIL,
					"Value could not be Entered in text box  and not clicked Enter button Due to Exception ");
			extTest.addScreenCaptureFromPath(screenShot(elementName));
			throw e;
		}

	}

	/*
	 * this method will click on Tab button after that click on element
	 * 
	 * 
	 * @Param - WebElement
	 * 
	 * @return - not return
	 */

	public void pressTab_click(WebElement we) {
		// WebElement we = searchElementOnHTML(locator, elementName);
		String elementName = we.getAccessibleName();
		try {

			we.sendKeys(Keys.TAB);
			we.click();

		} catch (ElementNotInteractableException e) {
			jsClickMethod(we, elementName);
		} catch (StaleElementReferenceException e) {
			we.sendKeys(Keys.TAB);
			we.click();

		} catch (Exception e) {
			throw e;
		}

	}

	/*
	 * this method will retrieve total Number of row in table
	 * 
	 * @Param - By
	 * 
	 * @Param - String element name
	 * 
	 * @return - int total row number
	 */
	public int getTableRowCount(By locator, String elementCollectionName) {
		List<WebElement> listCheckedBoxes = driver.findElements(locator);
		int rowCount = listCheckedBoxes.size();
		return rowCount;
	}

	/*
	 * this method will retrieve total number of column in table
	 * 
	 * @Param - By
	 * 
	 * @Param - String element name
	 * 
	 * @return - int total column number
	 */
	public int getTableColumnCount(By Tablelocator, String elementCollectionName) {

		List<WebElement> listCheckedBoxes = driver.findElements(Tablelocator);
		int ColoumnCount = listCheckedBoxes.size();
		return ColoumnCount;
	}

	/*
	 * this method will retrieve all column text
	 * 
	 * @Param -string Table Name
	 * 
	 * @Param - By
	 * 
	 * @Param - int column number
	 * 
	 * @return - List of String text of column
	 */

	public List<String> getColumnDataByColumnNumber(String tablename, String xpath, int columnNumber) {
		List<WebElement> columnList = driver.findElements(By.xpath(xpath));
		driver.findElements(By.xpath(xpath));
		List<String> columnNamelist = new ArrayList<>();
		for (int i = 0; i < columnList.size(); i++) {
			String columnName = columnList.get(i).getText();
			columnNamelist.add(columnName);

		}
		return columnNamelist;
	}

	/*
	 * this method will retrieve
	 * 
	 * @Param -String Table Name
	 * 
	 * @Param - By
	 * 
	 * @Param - String column name
	 * 
	 * @return - List of String text of column
	 */
	public List<String> getColumnDataByName(String tableName, String xpath, String columnName) {
//		int columnNumber=-1;
//		List<WebElement> listcolumnName=	driver.findElements(By. locator(table locator+"//tr[1]//td"));
//			int columnCount=listcolumnName.size();
//			for(int i=0;i<=columnCount-1;i++) {
//				WebElement weTableCol=listcolumnName.get(i);
//			String colname=	weTableCol.getText();
//			if(colname.equalsIgnoreCase(columnName)) {
//				columnNumber=i;
//			break;
//			}
//			}
//			List<WebElement> columnList=	driver.findElements(By. locator(table locator+"//tr//td["+columnNumber+"]"));
//			List<String> columnNamelist=new ArrayList<>();
//			for(int i=0;i<columnList.size();i++) {
//				String coluName=columnList.get(i).getText();
//				columnNamelist.add(coluName);
//				
//			}
//			return columnNamelist;

		int columncount = getColumnNumberByColumnName(xpath, columnName);
		List<String> columnNamelist = getColumnDataByColumnNumber(tableName, xpath, columncount);

		return columnNamelist;
	}

	/*
	 * this method will retrieve row number
	 * 
	 * @Param -string Table Name
	 * 
	 * @Param - By
	 * 
	 * @Param - String unique Data
	 * 
	 * @Param - String unique Data in column
	 * 
	 * @return - int row number
	 */
	public int getRowNumberbyRowID(String tablelocator, String tablename, String uniqueData, String uniqueColumnName) {
		int rowNumber = -1;
		List<String> columndat = getColumnDataByName(tablename, tablelocator, uniqueColumnName);
		for (int i = 0; i <= columndat.size() - 1; i++) {
			String uniqueColumnData = columndat.get(i);
			if (uniqueColumnData.equalsIgnoreCase(uniqueData)) {

				rowNumber = i;
				break;
			}
		}
		return rowNumber;

	}

}
