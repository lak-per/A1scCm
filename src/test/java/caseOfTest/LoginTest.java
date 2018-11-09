package caseOfTest;

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
import org.testng.annotations.Test;

import testBase.GroundFloor;

public class LoginTest extends GroundFloor {

	@Test(priority = 0)
	public void loginCheck() {

		driver.findElement(By.xpath(objectRepoFile.getProperty("UserNameText")))
				.sendKeys(objectRepoFile.getProperty("A1SCHandle"));
		log.info("Username provided");

		driver.findElement(By.xpath(objectRepoFile.getProperty("PasswordText")))
				.sendKeys(objectRepoFile.getProperty("A1SCPassword"));
		log.info("Password provided");

		driver.findElement(By.xpath(objectRepoFile.getProperty("SubmitButton")))
				.click();
		log.info("Submit Clicked");

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("logoutLink"))));

	}

	@Test(dependsOnMethods = { "loginCheck" })
	public void openCreateQuote() {

		driver.findElement(By.xpath(objectRepoFile.getProperty("designQuote")))
				.click();

		driver.findElement(
				By.xpath(objectRepoFile.getProperty("a1sConfigurator")))
				.click();

		driver.findElement(
				By.xpath(objectRepoFile.getProperty("a1scCreateLink"))).click();
		log.info("Navigating to Create Quote Page");

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> iterator = windowHandles.iterator();
		System.out.println(windowHandles.size());

		String tempWindowHandler, titleTemp, requiredWindowID = null;
		String popUpWindowHandler = null;

		while (iterator.hasNext()) {

			tempWindowHandler = iterator.next();
			titleTemp = driver.switchTo().window(tempWindowHandler).getTitle();

			if (titleTemp.contains("A1S Quote Center")) {
				requiredWindowID = tempWindowHandler;
			}

			if (titleTemp.contains("Popup Messenger Window")) {
				popUpWindowHandler = tempWindowHandler;
			}
		}
		driver.switchTo().window(popUpWindowHandler).close();
		driver.switchTo().window(requiredWindowID);

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("createAQuoteHeader"))));
	}

	@Test(dependsOnMethods = { "openCreateQuote", "loginCheck" })
	public void createQuoteWithData() {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.switchTo()
				.frame(driver.findElement(By
						.xpath("//iframe[@class='IframeContent ng-isolate-scope']")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("dropdownCompany"))));

		Select reuseableDropdown = new Select(driver.findElement(By
				.xpath(objectRepoFile.getProperty("dropdownCompany"))));
		reuseableDropdown
				.selectByVisibleText("Catalyst - 647740/United States");


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

		driver.findElement(By.xpath(objectRepoFile.getProperty("buttonSubmit")))
				.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(objectRepoFile.getProperty("linkCmNew"))));

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("linkCmUpgrade"))));

	}
}
