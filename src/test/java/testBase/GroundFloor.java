package testBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.ExcelReader;
import utilities.ExtentMgr;
import utilities.UtitliyClass;

public class GroundFloor {

	public static WebDriver driver;
	public static Properties configFile = new Properties();
	public static Properties objectRepoFile = new Properties();
	public static FileInputStream fis;
	// public static Logger log = Logger.getLogger("devpinoyLogger");
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excelReader;
	public static ExtentReports extentReports = ExtentMgr.getInstance();
	public static ExtentTest test;
	public static WebDriverWait wait;
	public static String browser, QRN;

	// public static WebDriverWait wait;

	@BeforeSuite
	public void setUp() throws Exception {

		if (driver == null) {
			fis = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\properties\\Config.properties");
			configFile.load(fis);
			log.info("Loading Configuration Repository");

			fis = new FileInputStream(
					System.getProperty("user.dir")
							+ "\\src\\test\\resources\\properties\\ObjectRepo.properties");
			objectRepoFile.load(fis);
			log.info("Loading Object Repository");

			if (System.getProperty("browser") != null
					&& !System.getProperty("browser").isEmpty()) {
				browser = System.getProperty("browser");
				System.out
						.println(configFile.getProperty("browser").toString());
				System.out.println(browser);
			} else {
				browser = configFile.getProperty("browser").toLowerCase();
			}
			configFile.setProperty("browser", browser);
			System.out.println(configFile.getProperty("browser").toString());

			if (System.getenv("QRN") != null && !System.getenv("QRN").isEmpty()) {
				QRN = System.getenv("QRN");
			} else {
				QRN = objectRepoFile.getProperty("quoteNumber");
			}
			objectRepoFile.setProperty("quoteNumber", QRN);

			switch (configFile.getProperty("browser").toString()) {

			case "chrome":
				System.setProperty(
						"webdriver.chrome.driver",
						System.getProperty("user.dir")
								+ "\\src\\test\\resources\\executables\\chromedriver.exe");
				ChromeOptions cOptions = new ChromeOptions();
				cOptions.addArguments("--start-maximized");
				cOptions.addArguments("--disable-notifications");
				driver = new ChromeDriver(cOptions);
				log.info("Chrome Launched");
				break;

			case "firefox":
				System.setProperty(
						"webdriver.gecko.driver",
						System.getProperty("user.dir")
								+ "\\src\\test\\resources\\executables\\geckodriver.exe");
				FirefoxOptions ffOptions = new FirefoxOptions();
				ffOptions.addArguments("--start-maximized");
				driver = new FirefoxDriver(ffOptions);
				log.info("Firefox Launched");
				break;

			case "explorer":
				System.setProperty(
						"webdriver.ie.driver",
						System.getProperty("user.dir")
								+ "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				InternetExplorerOptions ieDesires = new InternetExplorerOptions();
				ieDesires.setCapability("ignoreProtectedModeSettings", true);
				ieDesires.setCapability("ignoreZoomSetting", true);
				driver = new InternetExplorerDriver(ieDesires);
				log.info("Internet Explorer Launched");
				break;
			}
		}

		excelReader = new ExcelReader(System.getProperty("user.dir")
				+ configFile.getProperty("excelTestData"));

		driver.get(configFile.getProperty("A1SCLandingPage"));
		log.info("Navigated to A1SC Landing Page");
		driver.manage()
				.timeouts()
				.implicitlyWait(
						Integer.parseInt(configFile.getProperty("implicitWait")),
						TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		System.setProperty("org.uncommons.reportng.escape-output", "false");

	}

	@AfterSuite
	public void tearDown() {

		if (driver != null)
			driver.quit();
		log.info("Browser Closed");

	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			log.info("Element is available");
			Reporter.log("Element is available");
			Reporter.log("<br>");
			return true;
		} catch (Throwable t) {
			log.info("Element not available");
			Reporter.log("Element not available");
			Reporter.log("<br>");
			return false;
		}
	}

	public void clickElement(String element) {
		driver.findElement(By.xpath(element)).click();
		Reporter.log(element + " is clicked");
		Reporter.log("<br>");

		// test.log(LogStatus.INFO, element + " is clicked");
	}

	public void typeText(String element, String value) {

		driver.findElement(By.xpath(element)).sendKeys(value);
		Reporter.log(value + " is typed into " + element);
		Reporter.log("<br>");

		// test.log(LogStatus.PASS, value + " is typed into " + element);
	}

	public void selectFromDropdown(String element, String value) {
		Select dropdown = new Select(driver.findElement(By.xpath(element)));
		dropdown.selectByVisibleText(value);
		Reporter.log(value + " is selected in " + element);
		Reporter.log("<br>");

		// test.log(LogStatus.INFO, value + " is selected in" + element);
	}

	public void softAssert(String actual, String expected) throws IOException {
		try {
			String actualText = driver.findElement(By.xpath(actual)).getText();
			Assert.assertEquals(actualText, expected);
			// test.log(LogStatus.PASS, "Expected text - " + expected +
			// " was met");
		} catch (Throwable e) {

			UtitliyClass.screenshotCapture();
			Reporter.log("<br>" + " Failure Reason " + e.getMessage());
			Reporter.log("<br>");
			Reporter.log("<a target=\"blank\" href="
					+ UtitliyClass.screenshotName + ">Screenshot </a>");
			Reporter.log("<br>");

			/*
			 * test.log(LogStatus.FAIL,
			 * test.addScreenCapture(UtitliyClass.screenshotName));
			 */
		}

	}

	public boolean isElementEnabled(String xpathLoc) {
		try {
			if (driver.findElement(By.xpath(xpathLoc)).isEnabled()) {
				log.info("Element is enabled");
				Reporter.log("Element is enabled");
				Reporter.log("<br>");
				return true;
			}

			else {
				log.info("Element not enabled");
				Reporter.log("Element not enabled");
				Reporter.log("<br>");
				return false;
			}

		} catch (Throwable t) {
			log.info("Element not enabled");
			Reporter.log("Element not enabled");
			Reporter.log("<br>");
			return false;
		}
	}

}
