package testBase;

import java.io.FileInputStream;
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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utilities.ExcelReader;

public class GroundFloor {

	public static WebDriver driver;
	public static Properties configFile = new Properties();
	public static Properties objectRepoFile = new Properties();
	public static FileInputStream fis;
	// public static Logger log = Logger.getLogger("devpinoyLogger");
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excelReader;
	//public static WebDriverWait wait;
	
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

			switch (configFile.getProperty("browser").toLowerCase()) {

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
		
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

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
			return true;
		} catch (Throwable t) {
			log.info("Element not available");
			return false;
		}
	}

}
