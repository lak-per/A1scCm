package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import testBase.GroundFloor;

public class UtitliyClass extends GroundFloor {

	public static String screenshotName;

	public static void screenshotCapture() throws IOException {

		Date d = new Date();

		/*
		 * screenshotName = System.getProperty("user.dir") +
		 * "test-output\\html\\" + d.toString().replace(":", "_").replace(" ",
		 * "_") + ".jpg";
		 */

		screenshotName = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\screenshots\\"
				+ d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination
		File DestFile = new File(screenshotName);

		// Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);

	}

	public static boolean iTestRunnable() {

		String sheetName = "Upgrade System";
		int rowCount = excelReader.getRowCount(sheetName);
		// int columnCount = excelReader.getColumnCount(sheetName);
		int columnCount = 4;

		for (int rowNum = 8; rowNum < 10; rowNum++) {
			for (int colNum = 0; colNum < 2; colNum++) {
				if (excelReader.getCellData(sheetName, 0, rowNum).toUpperCase()
						.equals("Y")) {
					return true;
				}

			}
		}
		return false;
	}
}
