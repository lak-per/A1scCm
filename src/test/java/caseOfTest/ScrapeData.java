package caseOfTest;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import testBase.GroundFloor;

public class ScrapeData extends GroundFloor {

	@Test(priority = 0)
	public void loginCheck() throws Throwable {

		typeText(objectRepoFile.getProperty("UserNameText"),
				objectRepoFile.getProperty("A1SCHandle"));
		log.info("Username provided");

		typeText(objectRepoFile.getProperty("PasswordText"),
				objectRepoFile.getProperty("A1SCPassword"));
		log.info("Password provided");

		clickElement(objectRepoFile.getProperty("SubmitButton"));
		log.info("Submit Clicked");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("logoutLink"))));

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("logoutLink"))));

		softAssert(objectRepoFile.getProperty("logoutLink"), "Lead Management");

		Reporter.log("Succesfully logged in.");
		Reporter.log("<br>");

	}

	@Test(dependsOnMethods = { "loginCheck" })
	public void retrieveQuote() {

		clickElement(objectRepoFile.getProperty("designQuote"));
		clickElement(objectRepoFile.getProperty("a1sConfigurator"));
		clickElement(objectRepoFile.getProperty("retrieveLink"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("searchByIDText"))));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("searchByIDText"))));
		log.info("Navigating to Retrieve Quote Page");

		typeText(objectRepoFile.getProperty("textBoxQuote"), "TUS1242896");
		clickElement(objectRepoFile.getProperty("clickSearch"));
		log.info("Navigating to Quote Summary Page");

		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> iterator = windowHandles.iterator();

		String tempWindowHandler, titleTemp, requiredWindowID = null;

		while (iterator.hasNext()) {

			tempWindowHandler = iterator.next();
			titleTemp = driver.switchTo().window(tempWindowHandler).getTitle();

			if (titleTemp.contains("A1S Quote Center")
					|| titleTemp.contains("EC Link")) {
				requiredWindowID = tempWindowHandler;
			}
		}

		driver.switchTo().window(requiredWindowID);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("linkQuoteActions"))));

		clickElement(objectRepoFile.getProperty("linkQuoteActions"));
		clickElement(objectRepoFile.getProperty("clickModify"));
		log.info("Opening the quote in view mode");

		Reporter.log("Navigated to the Create Quote Page");
		Reporter.log("<br>");

		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("linkCMModel"))));

		clickElement(objectRepoFile.getProperty("linkCMModel"));

		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods = { "retrieveQuote", "loginCheck" }, dataProvider = "readTestData")
	public void scrapeData(String columnName, String xpathLocal) {

		String sheetName = "Upgrade System", scrapedData;
		String xpathCopyLocal = xpathLocal.toLowerCase();
		xpathLocal = xpathLocal.toString().substring(7,
				xpathCopyLocal.toString().length());

		if (!xpathCopyLocal.contains("input")
				&& !xpathCopyLocal.contains("select")) {
			clickElement(xpathLocal);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		} else if (isElementEnabled(xpathLocal)) {

			if (xpathLocal.contains("select")) {
				Select dropdown = new Select(driver.findElement(By
						.xpath(xpathLocal)));
				scrapedData = dropdown.getFirstSelectedOption().getText();
				excelReader.setCellData(sheetName, columnName, 82, scrapedData);

			} else if (xpathLocal.contains("input")) {
				scrapedData = driver.findElement(By.xpath(xpathLocal))
						.getAttribute("ctrlvalue");
				excelReader.setCellData(sheetName, columnName, 82, scrapedData);
			}
		} else {
			throw new SkipException("Skipping the test case as - " + columnName
					+ " is disabled in the application.");
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@DataProvider
	public Object[][] readTestData() {

		String sheetName = "Upgrade System";
		int rowCount = excelReader.getRowCount(sheetName);
		rowCount = 3;
		int columnStart = 15, columnCount = 232;

		Object[][] data = new Object[columnCount][2];
		for (int rowNum = columnStart - 15; rowNum < columnCount; rowNum++) {
			for (int colNum = 0; colNum < 1; colNum++) {

				data[rowNum][0] = excelReader.getCellData(sheetName,
						rowNum + 15, 1);
				data[rowNum][1] = excelReader.getCellData(sheetName,
						rowNum + 15, 4);
			}
		}
		return data;
	}
}
