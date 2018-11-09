package caseOfTest;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import testBase.GroundFloor;

public class LoginTest extends GroundFloor {

	@Test
	public void loginCheck() {

		driver.findElement(By.xpath(objectRepoFile.getProperty("UserNameText")))
				.sendKeys(objectRepoFile.getProperty("A1SCHandle"));

		driver.findElement(By.xpath(objectRepoFile.getProperty("PasswordText")))
				.sendKeys(objectRepoFile.getProperty("A1SCPassword"));

		driver.findElement(By.xpath(objectRepoFile.getProperty("SubmitButton")))
				.click();
	}

}
