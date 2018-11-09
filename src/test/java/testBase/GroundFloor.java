package testBase;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class GroundFloor {

	public static WebDriver driver;
	public static Properties configFile = new Properties();
	public static Properties objectRepoFile = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");

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
				break;

			case "firefox":
				System.setProperty(
						"webdriver.gecko.driver",
						System.getProperty("user.dir")
								+ "\\src\\test\\resources\\executables\\geckodriver.exe");
				FirefoxOptions ffOptions = new FirefoxOptions();
				ffOptions.addArguments("--start-maximized");
				driver = new FirefoxDriver(ffOptions);
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
				break;
			}
		}

		driver.get(configFile.getProperty("A1SCLandingPage"));
		driver.manage()
				.timeouts()
				.implicitlyWait(
						Integer.parseInt(configFile.getProperty("implicitWait")),
						TimeUnit.SECONDS);

	}

	@AfterSuite
	public void tearDown() {

		if (driver != null)
			driver.quit();

	}

}
