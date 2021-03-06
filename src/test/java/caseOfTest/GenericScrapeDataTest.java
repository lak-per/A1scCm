package caseOfTest;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class GenericScrapeDataTest extends GroundFloor {

	static String retriveWindowHandle = null;

	@Test(priority = 0)
	public void loginCheck() throws Throwable {

		typeText(objectRepoFile.getProperty("UserNameText"),
				objectRepoFile.getProperty("A1SCHandle"));
		log.info("Username provided");
		test.log(LogStatus.INFO, "Username provided");

		typeText(objectRepoFile.getProperty("PasswordText"),
				objectRepoFile.getProperty("A1SCPassword"));
		log.info("Password provided");
		test.log(LogStatus.INFO, "Password provided");

		clickElement(objectRepoFile.getProperty("SubmitButton"));
		log.info("Submit Clicked");
		test.log(LogStatus.INFO, "Submit Clicked");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("logoutLink"))));

		Reporter.log("Succesfully logged in.");
		Reporter.log("<br>");
		test.log(LogStatus.INFO, "Succesfully logged in");

		clickElement(objectRepoFile.getProperty("designQuote"));
		clickElement(objectRepoFile.getProperty("a1sConfigurator"));
		clickElement(objectRepoFile.getProperty("retrieveLink"));
		test.log(LogStatus.INFO, "Navigating to Retrieve Quote Page");

		retriveWindowHandle = driver.getWindowHandle();

	}

	@Test(dependsOnMethods = { "loginCheck" }, dataProvider = "readTestData")
	public void retrieveQuote(String[][] localArray) {

		if (!localArray[1][1].contains("TUS")
				|| localArray[1][1].contains("No")
				|| localArray[1][1].isEmpty() || localArray[1][1].length() < 3) {
			test.log(
					LogStatus.INFO,
					"Data scrape isn't requested "
							+ excelReader.getCellData(sheetName, 0,
									Integer.parseInt(localArray[0][1])));
			throw new SkipException(
					"Skipping the test case as data retrieval isn't requested for test - "
							+ excelReader.getCellData(sheetName, 0,
									Integer.parseInt(localArray[0][1])));
		}

		/*
		 * if (driver.getTitle().equals("Avaya One Source Configurator")) {
		 * ((JavascriptExecutor) driver).executeScript("window.close();");
		 * test.log(LogStatus.INFO, "Closing current tab"); }
		 */

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("searchByIDText"))));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		log.info("Navigating to Retrieve Quote Page");
		test.log(LogStatus.INFO, "Navigating to Retrieve Quote Page");

		driver.switchTo().window(retriveWindowHandle);

		driver.findElement(By.xpath(objectRepoFile.getProperty("textBoxQuote")))
				.clear();
		typeText(objectRepoFile.getProperty("textBoxQuote"), localArray[1][1]);

		System.out.println("Now Processing - QRN : " + localArray[0][1]
				+ " and Quote : " + localArray[1][1]);

		clickElement(objectRepoFile.getProperty("clickSearch"));
		log.info("Navigating to Quote Summary Page");
		test.log(LogStatus.INFO, "Navigating to Quote Summary Page");

		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
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
		test.log(LogStatus.INFO, "Opening the quote in view mode");

		Reporter.log("Navigated to the Create Quote Page");
		Reporter.log("<br>");
		test.log(LogStatus.INFO, "Navigated to the Create Quote Page");

		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("linkAfterLocationManager"))));

		clickElement(objectRepoFile.getProperty("linkAfterLocationManager"));

		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int allColCount = excelReader.getColumnCount(sheetName);
		String tempName;
		for (int colCheck = 1; colCheck < allColCount; colCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, colCheck, 1);

			if (tempName.contains("Maintenance Services")) {
				allColCount = colCheck - 1;
				break;
			}
		}
		// Check to halt process at Loc 2 if it exists
				//allColCount = excelReader.getColumnCount(sheetName);
				if (!isElementDisplayed("//div[contains(text(),'Physical Location 2')]")) {

					for (int colCheck = 1; colCheck < allColCount; colCheck++) {
						tempName = (String) excelReader.getCellData(sheetName,
								colCheck, 1);

						if (tempName.contains("Physical Loc 2")) {
							allColCount = colCheck - 1;
							break;
						}
					}
				} 
		int columnStart = 15;
		for (int colCheck = 0; colCheck < 30; colCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, colCheck, 4);

			if (tempName.contains("xpath:")) {
				columnStart = colCheck;
				break;
			}
		}

		// allColCount = 21;// 561
		for (int iteration = columnStart; iteration < allColCount; iteration++) {

			String sheetName = configFile.getProperty("sheetName"), scrapedData;
			String colName = excelReader.getCellData(sheetName, iteration, 1);
			String xpathCopyLocal = excelReader.getCellData(sheetName,
					iteration, 4);
			if (xpathCopyLocal.isEmpty()) {
				xpathCopyLocal = "xpath:=//b[contains(text(),'QRN')]";
			}
			xpathCopyLocal = xpathCopyLocal.toString().substring(7,
					xpathCopyLocal.toString().length());

			if (!xpathCopyLocal.contains("input")
					&& !xpathCopyLocal.contains("select")) {
				clickElement(xpathCopyLocal);
				System.out.println("Now Processing - Page : " + colName);

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			} else if (isElementEnabled(xpathCopyLocal)) {

				if (xpathCopyLocal.contains("select")) {
					Select dropdown = new Select(driver.findElement(By
							.xpath(xpathCopyLocal)));
					scrapedData = dropdown.getFirstSelectedOption().getText();
					excelReader.setCellData(sheetName, colName,
							Integer.parseInt(localArray[2][1]), scrapedData);
					// System.out.println(xpathCopyLocal + " --- " +
					// scrapedData);
					System.out.println("\t" + "Object : " + xpathCopyLocal);

				} else if (xpathCopyLocal.contains("input")) {
					scrapedData = driver.findElement(By.xpath(xpathCopyLocal))
							.getAttribute("ctrlvalue");
					excelReader.setCellData(sheetName, colName,
							Integer.parseInt(localArray[2][1]), scrapedData);
					System.out.println("\t" + "Object : " + xpathCopyLocal);
					// System.out.println(xpathCopyLocal + " --- " +
					// scrapedData);
				}
			}
		}

		windowHandles = null;
		iterator = null;
		requiredWindowID = null;
		tempWindowHandler = null;
		titleTemp = null;

		windowHandles = driver.getWindowHandles();
		iterator = windowHandles.iterator();
		String oldWindow = null;

		while (iterator.hasNext()) {

			tempWindowHandler = iterator.next();
			titleTemp = driver.switchTo().window(tempWindowHandler).getTitle();

			if (titleTemp.contains("Retrieve Quote")) {
				requiredWindowID = tempWindowHandler;
			}

			if (titleTemp.contains("Avaya One Source Configurator")
					|| titleTemp.contains("EC Link")) {
				oldWindow = tempWindowHandler;
			}
		}
		if (requiredWindowID != null) {
			driver.switchTo().window(oldWindow).close();
		}

		driver.switchTo().window(requiredWindowID);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@DataProvider
	public Object[][] readTestData() {

		String sheetName = configFile.getProperty("sheetName"), tempName, quoteNumber = null;
		int allRowCount = excelReader.getRowCount(sheetName);
		int allColCount = excelReader.getColumnCount(sheetName);
		int columnStart = 15, rowStart = 8, columnCount = 561, colDeduction = 0, dataIndex = 0;

		for (int rowCheck = 1; rowCheck < 30; rowCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, 0, rowCheck);

			if (tempName.equals("OnFail")) {
				rowStart = rowCheck + 1;
				break;
			}
		}

		// System.out.println(rowStart);

		for (int colCheck = 0; colCheck < 30; colCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, colCheck, 4);

			if (tempName.contains("xpath:")) {
				columnStart = colCheck;
				break;
			}
		}

		String[][] dataArray = null;

		for (int rowNum = rowStart; rowNum < allRowCount; rowNum++) {

			tempName = (String) excelReader.getCellData(sheetName, 2, rowNum);
			if (!tempName.contains("TUS") || tempName.contains("No")
					|| tempName.length() < 3) {
			} else {
				dataIndex++;
			}

		}

		Object[][] data = new Object[dataIndex][1];
		dataIndex = 0;
		for (int rowNum = rowStart; rowNum < allRowCount; rowNum++) {

			tempName = (String) excelReader.getCellData(sheetName, 2, rowNum);
			if (!tempName.contains("TUS") || tempName.contains("No")
					|| tempName.length() < 3) {

				quoteNumber = (String) excelReader.getCellData(sheetName, 2,
						rowNum);
			} else {
				quoteNumber = (String) excelReader.getCellData(sheetName, 2,
						rowNum).substring(
						excelReader.getCellData(sheetName, 2, rowNum).indexOf(
								"TUS"),
						excelReader.getCellData(sheetName, 2, rowNum).indexOf(
								"TUS") + 10);

				dataArray = new String[allRowCount - rowStart][2];
				// dataArray = new String[2][2];
				dataArray[0][0] = excelReader.getCellData(sheetName, 0, 1);
				dataArray[0][1] = excelReader.getCellData(sheetName, 0, rowNum);
				dataArray[1][0] = "QRN";
				dataArray[1][1] = quoteNumber;
				dataArray[2][0] = "Row";
				dataArray[2][1] = Integer.toString(rowNum);
				data[dataIndex][0] = dataArray;
				dataIndex++;

			}

		}
		return data;
	}
}
