package caseOfTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
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
import utilities.ExcelReader;

public class MissingFieldsTest extends GroundFloor {

	public static ExcelReader fieldsFile;
	public static String xpathSheetName = "Xpath Issues";
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

	@Test(dependsOnMethods = { "loginCheck" }, enabled = false)
	public void openCreateQuote() {

		clickElement(objectRepoFile.getProperty("designQuote"));

		clickElement(objectRepoFile.getProperty("a1sConfigurator"));

		clickElement(objectRepoFile.getProperty("a1scCreateLink"));
		log.info("Navigating to Create Quote Page");

		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> iterator = windowHandles.iterator();

		String tempWindowHandler, titleTemp, requiredWindowID = null;
		String popUpWindowHandler = null;

		while (iterator.hasNext()) {

			tempWindowHandler = iterator.next();
			titleTemp = driver.switchTo().window(tempWindowHandler).getTitle();

			if (titleTemp.contains("A1S Quote Center")
					|| titleTemp.contains("EC Link")) {
				requiredWindowID = tempWindowHandler;
			}

			if (titleTemp.contains("Popup Messenger Window")) {
				popUpWindowHandler = tempWindowHandler;
			}
		}
		if (requiredWindowID != null) {
			driver.switchTo().window(popUpWindowHandler).close();
		}
		driver.switchTo().window(requiredWindowID);

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("createAQuoteHeader"))));

		Reporter.log("Navigated to the Create Quote Page");
		Reporter.log("<br>");

		driver.switchTo()
				.frame(driver.findElement(By
						.xpath("//iframe[@class='IframeContent ng-isolate-scope']")));

	}

	@Test(dependsOnMethods = { "openCreateQuote", "loginCheck" }, enabled = false)
	public void createQuoteWithData() {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("dropdownCompany"))));

		selectFromDropdown(objectRepoFile.getProperty("dropdownCompany"),
				"Jenne, Inc - 647741/United States");

		Reporter.log("Successfully selected the company");
		Reporter.log("<br>");

		Actions act = new Actions(driver);
		act.moveToElement(
				driver.findElement(By
						.xpath("//select[@name='ddPID_ProductMenu']")))
				.perform();

		act.moveToElement(
				driver.findElement(By.xpath("//a[@id='hrefMainMenu_21']")))
				.click().perform();

		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("dropdownBusinessPartner"))));

		driver.findElement(
				By.xpath(objectRepoFile.getProperty("dropdownBusinessPartner")))
				.sendKeys("Carousel Industries Of North America - 933");

		/*
		 * driver.findElement(
		 * By.xpath(objectRepoFile.getProperty("dropdownBusinessPartner")))
		 * .sendKeys(Keys.ENTER);
		 */

		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("buttonSubmit"))));

		driver.findElement(By.xpath(objectRepoFile.getProperty("buttonSubmit")))
				.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("linkCmNew"))));

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("linkCmUpgrade"))));

		clickElement(objectRepoFile.getProperty("linkCmUpgrade"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("linkAfterLocationManager"))));

		clickElement(objectRepoFile.getProperty("linkAfterLocationManager"));

		Date d = new Date();
		test.log(LogStatus.INFO, "Date d = new Date();");
		String xpathFileName = d.toString().replace(":", "_").replace(" ", "_");
		test.log(LogStatus.INFO, "String xpathFileName");
		String xpathFilePath = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\excel\\xpathFileName_"
				+ xpathFileName.trim().toString() + ".xlsx";
		test.log(LogStatus.INFO, "String xpathFilePath");

		File xpathFile = new File(xpathFilePath.toString().trim());
		// fieldsFile.createExcel(xpathFile,xpathSheetName);

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream(xpathFile);
			XSSFSheet sheet = workbook.createSheet(xpathSheetName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fieldsFile = new ExcelReader(xpathFilePath.toString().trim());
		// fieldsFile.addSheet(xpathSheetName);
		fieldsFile.setCellDatawithoutFileOut(xpathSheetName, 1, 0, "Page Name");
		fieldsFile.setCellDatawithoutFileOut(xpathSheetName, 1, 1,
				"Issues or Comments");

		test.log(LogStatus.INFO, "Xpaths's File has been created");
	}

	@Test(dependsOnMethods = { "loginCheck" })
	public void retrieveQuote() {

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

		String sheetName = configFile.getProperty("sheetName"), tempName = null;
		int allRowCount = excelReader.getRowCount(sheetName);

		for (int rowNum = 9; rowNum < allRowCount; rowNum++) {

			tempName = (String) excelReader.getCellData(sheetName, 2, rowNum);
			if (tempName.contains("TUS")) {
				tempName = (String) excelReader.getCellData(sheetName, 2,
						rowNum).substring(
						excelReader.getCellData(sheetName, 2, rowNum).indexOf(
								"TUS"),
						excelReader.getCellData(sheetName, 2, rowNum).indexOf(
								"TUS") + 10);
				break;
			}

		}

		driver.findElement(By.xpath(objectRepoFile.getProperty("textBoxQuote")))
				.clear();
		/*
		 * typeText(objectRepoFile.getProperty("textBoxQuote"),
		 * objectRepoFile.getProperty("quoteNumber"));
		 */

		typeText(objectRepoFile.getProperty("textBoxQuote"), tempName);

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
		test.log(LogStatus.INFO, "Opening the quote in modify mode");

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
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Date d = new Date();
		test.log(LogStatus.INFO, "Date d = new Date();");
		String xpathFileName = d.toString().replace(":", "_").replace(" ", "_");
		test.log(LogStatus.INFO, "String xpathFileName");
		String xpathFilePath = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\excel\\xpathFileName_"
				+ xpathFileName.trim().toString() + ".xlsx";
		test.log(LogStatus.INFO, "String xpathFilePath");

		File xpathFile = new File(xpathFilePath.toString().trim());
		// fieldsFile.createExcel(xpathFile,xpathSheetName);

		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream(xpathFile);
			XSSFSheet sheet = workbook.createSheet(xpathSheetName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fieldsFile = new ExcelReader(xpathFilePath.toString().trim());
		// fieldsFile.addSheet(xpathSheetName);
		fieldsFile.setCellDatawithoutFileOut(xpathSheetName, 1, 0, "Page Name");
		fieldsFile.setCellDatawithoutFileOut(xpathSheetName, 1, 1,
				"Issues or Comments");
	}

	@Test(dependsOnMethods = { "loginCheck", "retrieveQuote" }, dataProvider = "readTestData")
	public void findingMissingFields(String[][] localArray) {

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		test.log(LogStatus.INFO, localArray[0][0].toString() + " with "
				+ (localArray.length - 1) + " elements");
		int numOfInputs = 0, numOfSelects = 0;

		for (int iteration = 0; iteration < localArray.length; iteration++) {

			// String sheetName = configFile.getProperty("sheetName");
			String xpathCopyLocal = localArray[iteration][3];
			xpathCopyLocal = xpathCopyLocal.toString().trim()
					.substring(7, xpathCopyLocal.toString().length());

			if (!xpathCopyLocal.contains("input")
					&& !xpathCopyLocal.contains("select")) {
				clickElement(xpathCopyLocal);
				System.out.println("Now Processing Page : "
						+ localArray[0][0].toString());

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				insertxpathData(xpathSheetName, "Page Name",
						localArray[0][0].toString());
				numOfInputs = driver.findElements(
						By.xpath("//div[@id='rightPane']//input")).size();
				numOfSelects = driver.findElements(
						By.xpath("//div[@id='rightPane']//select")).size();
			} else {

				if (isElementDisplayed(xpathCopyLocal)) {

					if (xpathCopyLocal.contains("select")) {
						numOfSelects--;

					} else if (xpathCopyLocal.contains("input")) {
						numOfInputs--;
					}
				} else {
					test.log(
							LogStatus.INFO,
							localArray[iteration][0]
									+ " is not available in the application. Please remove from the reference plan");
					insertxpathData(
							xpathSheetName,
							"Issues or Comments",
							localArray[iteration][0]
									+ " is not available in the application. Please remove from the reference plan");
				}

			}

		}

		if (numOfSelects > 0) {

			test.log(LogStatus.WARNING, numOfSelects
					+ " dropdown xpaths are missing from the reference plan");
			insertxpathData(xpathSheetName, "Issues or Comments", numOfSelects
					+ " dropdown xpaths are missing from the reference plan");
		}
		if (numOfInputs > 0) {
			test.log(LogStatus.WARNING, numOfInputs
					+ " textbox xpaths are missing from the reference plan");
			insertxpathData(xpathSheetName, "Issues or Comments", numOfInputs
					+ " textbox xpaths are missing from the reference plan");
		}

		if (numOfSelects == 0 && numOfInputs == 0) {
			test.log(LogStatus.PASS, " All is good with this "
					+ localArray[0][0] + " Page and the reference plan");
			insertxpathData(xpathSheetName, "Issues or Comments",
					" All is good with this " + localArray[0][0]
							+ " Page and the reference plan");
		}

	}

	@DataProvider
	public Object[][] readTestData() {

		String sheetName = configFile.getProperty("sheetName");
		int allRowCount = excelReader.getRowCount(sheetName);
		int allColCount = excelReader.getColumnCount(sheetName);
		allRowCount = 15;
		// allColCount = 561;// 97
		int columnStart = 15, rowStart = 8, dataRowCount = 0, arrayStart = 0, arrayEnd = 0;
		String tempName;

		for (int colCheck = 0; colCheck < 30; colCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, colCheck, 4);

			if (tempName.contains("xpath:")) {
				columnStart = colCheck;
				break;
			}
		}

		for (int colCheck = 1; colCheck < allColCount; colCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, colCheck, 1);

			if (tempName.contains("Maintenance Services")) {
				allColCount = colCheck - 1;
				break;
			}
		}

		String[][] dataArray = null;
		boolean checkArrayCount = false, createArray = false;

		for (int mainColNum = columnStart; mainColNum < allColCount; mainColNum++) {
			if (excelReader.getCellData(sheetName, mainColNum, 5).isEmpty()) {
				dataRowCount++;

			}
		}

		Object[][] data = new Object[dataRowCount][1];
		dataRowCount = 0;

		for (int mainColNum = columnStart; mainColNum < allColCount; mainColNum++) {

			if (checkArrayCount == false
					&& excelReader.getCellData(sheetName, mainColNum, 5)
							.isEmpty()) {
				arrayStart = mainColNum;
				checkArrayCount = true;
			} else if (checkArrayCount == true
					&& excelReader.getCellData(sheetName, mainColNum, 5)
							.isEmpty()) {
				arrayEnd = mainColNum;
				checkArrayCount = false;
				createArray = true;
				mainColNum--;
			}

			if (createArray == true) {

				dataArray = new String[arrayEnd - arrayStart][8];

				for (int rowNum = arrayStart; rowNum < arrayEnd; rowNum++) {

					for (int colNum = 0; colNum < 7; colNum++) {

						dataArray[rowNum - arrayStart][colNum] = excelReader
								.getCellData(sheetName, rowNum, colNum + 1);
					}

				}
				data[dataRowCount][0] = dataArray;
				dataRowCount++;
				createArray = false;
			}

		}

		return data;

	}

	public void insertxpathData(String sheetName, String colName, String data) {
		int rowNum = fieldsFile.getRowCount(sheetName);
		fieldsFile.setCellData(sheetName, colName, rowNum + 1, data);
	}

}
