package caseOfTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.GroundFloor;

public class LoginTest extends GroundFloor {

	@Test
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
		
		Assert.assertTrue(isElementPresent(By.xpath(objectRepoFile.getProperty("logoutLink"))));
		
		
		
	}

}
