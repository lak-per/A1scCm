package caseOfTest;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import testBase.GroundFloor;

public class CreateQuoteTest extends GroundFloor {

	//@Test(dataProvider = "readTestData")
	//dependsOnMethods = { "openCreateQuote", "loginCheck" }, 
	public void createQuoteWithData(String linkID, String companyName) {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.switchTo()
				.frame(driver.findElement(By
						.xpath("//iframe[@class='IframeContent ng-isolate-scope']")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("dropdownCompany"))));

		Select reuseableDropdown = new Select(driver.findElement(By
				.xpath(objectRepoFile.getProperty("dropdownCompany"))));
		reuseableDropdown
		// .selectByVisibleText("Catalyst - 647740/United States");
				.selectByVisibleText(companyName);
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

		try {
			Thread.sleep(3000);
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

	}

	//@DataProvider
	public Object[][] readTestData() {

		String sheetName = "Upgrade System";
		int rowCount = excelReader.getRowCount(sheetName);
		// int columnCount = excelReader.getColumnCount(sheetName);

		Object[][] data = new Object[rowCount][2];

		for (int rowNum = 8; rowNum < rowCount; rowNum++) {
			for (int colNum = 0; colNum < 2; colNum++) {

				data[rowNum - 8][colNum] = excelReader.getCellData(sheetName,
						colNum, rowNum);
			}
		}

		return data;

	}
}
