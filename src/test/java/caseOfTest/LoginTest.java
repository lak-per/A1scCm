package caseOfTest;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

import testBase.GroundFloor;

public class LoginTest extends GroundFloor {

	@Test(priority = 0)
	public void loginCheck() throws Throwable {

		/*
		 * driver.findElement(By.xpath(objectRepoFile.getProperty("UserNameText")
		 * )) .sendKeys(objectRepoFile.getProperty("A1SCHandle"));
		 */

		typeText(objectRepoFile.getProperty("UserNameText"),
				objectRepoFile.getProperty("A1SCHandle"));
		log.info("Username provided");

		/*
		 * driver.findElement(By.xpath(objectRepoFile.getProperty("PasswordText")
		 * )) .sendKeys(objectRepoFile.getProperty("A1SCPassword"));
		 */

		typeText(objectRepoFile.getProperty("PasswordText"),
				objectRepoFile.getProperty("A1SCPassword"));

		log.info("Password provided");

		/*
		 * driver.findElement(By.xpath(objectRepoFile.getProperty("SubmitButton")
		 * )) .click();
		 */
		clickElement(objectRepoFile.getProperty("SubmitButton"));
		log.info("Submit Clicked");

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("logoutLink"))));

		softAssert(objectRepoFile.getProperty("logoutLink"), "Lead Management");

		Reporter.log("Succesfully logged in.");
		Reporter.log("<br>");

	}

	@Test(dependsOnMethods = { "loginCheck" })
	public void openCreateQuote() {

		/*
		 * driver.findElement(By.xpath(objectRepoFile.getProperty("designQuote"))
		 * ) .click();
		 */
		clickElement(objectRepoFile.getProperty("designQuote"));

		/*
		 * driver.findElement(
		 * By.xpath(objectRepoFile.getProperty("a1sConfigurator"))) .click();
		 */
		clickElement(objectRepoFile.getProperty("a1sConfigurator"));

		/*
		 * driver.findElement(
		 * By.xpath(objectRepoFile.getProperty("a1scCreateLink"))).click();
		 */
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

	// public void createQuoteWithData(String linkID, String companyName,
	// String runMode, String testCaseName) {
	@Test(dependsOnMethods = { "openCreateQuote", "loginCheck" }, dataProvider = "readTestData")
	public void createQuoteWithData(Hashtable<String, String> data) {
		
/*		System.out.println(data.get("RunMode") + data.get("TestCaseName")
				+ data.get("CompanyName"));
*/
		if (data.get("RunMode").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping the test case - "
					+ data.get("TestCaseName")
					+ " as the RunMode was set to No.");
		}

		WebDriverWait wait = new WebDriverWait(driver, 30);

		/*
		 * driver.switchTo() .frame(driver.findElement(By
		 * .xpath("//iframe[@class='IframeContent ng-isolate-scope']")));
		 */
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("dropdownCompany"))));

		/*
		 * Select reuseableDropdown = new Select(driver.findElement(By
		 * .xpath(objectRepoFile.getProperty("dropdownCompany"))));
		 * reuseableDropdown //
		 * .selectByVisibleText("Catalyst - 647740/United States");
		 * .selectByVisibleText(companyName);
		 */
		selectFromDropdown(objectRepoFile.getProperty("dropdownCompany"),
				data.get("CompanyName"));

		Reporter.log("Successfully selected the company"
				+ data.get("CompanyName"));
		Reporter.log("<br>");

		Actions act = new Actions(driver);
		act.moveToElement(
				driver.findElement(By
						.xpath("//select[@name='ddPID_ProductMenu']")))
				.perform();

		act.moveToElement(
				driver.findElement(By.xpath("//a[@id='hrefMainMenu_21']")))
				.click().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("dropdownBusinessPartner"))));

		driver.findElement(
				By.xpath(objectRepoFile.getProperty("dropdownBusinessPartner")))
				.sendKeys("Carousel Industries Of North America - 933");

		typeText(objectRepoFile.getProperty("dropdownBusinessPartner"),
				"Carousel Industries Of North America - 933");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .xpath(objectRepoFile.getProperty("buttonSubmit"))));
		 * 
		 * driver.findElement(By.xpath(objectRepoFile.getProperty("buttonSubmit")
		 * )) .click();
		 * 
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .xpath(objectRepoFile.getProperty("linkCmNew"))));
		 * 
		 * Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
		 * .getProperty("linkCmUpgrade"))));
		 */

	}

	@DataProvider
	public Object[][] readTestData() {

		String sheetName = "Upgrade System";
		int rowCount = excelReader.getRowCount(sheetName);
		// int columnCount = excelReader.getColumnCount(sheetName);
		rowCount = 4;
		int columnCount = 15;

		Object[][] data = new Object[rowCount][columnCount - 1];

		for (int rowNum = 8; rowNum < rowCount + 8; rowNum++) {
			for (int colNum = 0; colNum < 1; colNum++) {

				data[rowNum - 8][0] = excelReader.getCellData(sheetName, 6,
						rowNum);
				data[rowNum - 8][1] = excelReader.getCellData(sheetName, 9,
						rowNum);
				data[rowNum - 8][2] = excelReader.getCellData(sheetName, 8,
						rowNum);
				data[rowNum - 8][3] = excelReader.getCellData(sheetName, 0,
						rowNum);
				/*
				 * System.out.println(data[rowNum - 8][0] + "\t" + data[rowNum -
				 * 8][1] + "\t" + data[rowNum - 8][2] + "\t" + data[rowNum -
				 * 8][3]);
				 */}
		}

		Object[][] dataOne = new Object[rowCount][1];
		columnCount = 13;
		Hashtable<String, String> tableData = null;
		for (int rowNum = 8; rowNum < rowCount + 8; rowNum++) {
			tableData = new Hashtable<String, String>();
			for (int colNum = 0; colNum < 13; colNum++) {

				tableData.put(excelReader.getCellData(sheetName, colNum, 1),
						excelReader.getCellData(sheetName, colNum, rowNum));

				dataOne[rowNum - 8][0] = tableData;
			}
		}
		return dataOne;
		// return tableData;

	}

}
