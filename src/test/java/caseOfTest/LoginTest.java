package caseOfTest;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> iterator = windowHandles.iterator();

		while (iterator.hasNext()) {
			String tempWindowHandler = iterator.next();

			if (driver.switchTo().window(tempWindowHandler).getTitle()
					.equals("EC Link")) {
				driver.switchTo().window(tempWindowHandler);
				break;
			}

		}

		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("createAQuoteHeader"))));
	}

	@Test(dependsOnMethods = { "openCreateQuote" , "loginCheck"})
	public void createQuoteWithData() {

		Select reuseableDropdown = new Select(driver.findElement(By
				.xpath(objectRepoFile.getProperty("dropdownCompany"))));
		reuseableDropdown.selectByValue("Catalyst - 647740/United States");

		reuseableDropdown = new Select(driver.findElement(By.xpath(objectRepoFile
				.getProperty("dropdownModel"))));
		reuseableDropdown.selectByIndex(4);

		/*
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .xpath(configFile.getProperty("dropdownBusinessPartner"))));
		 */
		driver.findElement(
				By.xpath(objectRepoFile.getProperty("dropdownBusinessPartner")))
				.sendKeys("Carousel Industries Of North America - 933");

		driver.findElement(By.xpath(objectRepoFile.getProperty("buttonSubmit")))
				.click();

		/*
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .xpath(configFile.getProperty("linkCmNew"))));
		 */
		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile
				.getProperty("linkCmUpgrade"))));

	}

}
