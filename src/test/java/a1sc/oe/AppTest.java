package a1sc.oe;

import java.util.Arrays;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import utilities.ExcelReader;

/**
 * Unit test for simple App.
 */
public class AppTest {

	public static WebDriver driver;

	public static void main(String[] args) {

		ExcelReader excelReader;
		excelReader = new ExcelReader(
				System.getProperty("user.dir")
						+ "\\src\\test\\resources\\excel\\CM_UPGRADE_TestPlanReference.xlsx");

		String sheetName = "Upgrade System", tempName, quoteNumber = null;
		int allRowCount = excelReader.getRowCount(sheetName);
		int allColCount = excelReader.getColumnCount(sheetName);
		allRowCount = 15;
		allColCount = 97;
		// rowCount = 3;
		int columnStart = 15, rowStart = 8, columnCount = 561, colDeduction = 0, arrayStart = 0, arrayEnd = 0;
		String[][] dataArray = null;
		boolean checkArrayCount = false, createArray = false;

		for (int mainColNum = columnStart; mainColNum < allColCount; mainColNum++) {

			/*
			 * System.out.println(excelReader .getCellData(sheetName,
			 * mainColNum, 2) + " ---- " + checkArrayCount);
			 */

			if (checkArrayCount == false
					&& excelReader.getCellData(sheetName, mainColNum, 2)
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
				//System.out.println(arrayStart + " ---- " + arrayEnd);
				dataArray = new String[arrayEnd - arrayStart][8];
				System.out.println("----------------------------------");
				for (int rowNum = arrayStart; rowNum < arrayEnd; rowNum++) {
					

					for (int colNum = 1; colNum < 8; colNum++) {

						dataArray[rowNum - arrayStart][colNum] = excelReader
								.getCellData(sheetName, rowNum, colNum);

						System.out.print(dataArray[rowNum - arrayStart][colNum]
								+ "\t");
					}
					System.out.println();
				}
				createArray = false;
			}
		}		

		/*
		 * for (int rowNum = rowStart; rowNum < allRowCount; rowNum++) { for
		 * (int colNum = 0; colNum < 8; colNum++) { dataArray = new
		 * String[allRowCount - rowStart][7]; } }
		 * 
		 * Object[][] data = new Object[allRowCount - rowStart][1]; for (int
		 * rowNum = rowStart; rowNum < allRowCount; rowNum++) {
		 * 
		 * tempName = (String) excelReader.getCellData(sheetName, 2, rowNum); if
		 * (!tempName.contains("TUS") || tempName.contains("No") ||
		 * tempName.isEmpty() ) {
		 * 
		 * quoteNumber = (String) excelReader.getCellData(sheetName, 2, rowNum);
		 * 
		 * throw new SkipException(
		 * "Skipping the test case as data retrieval isn't requested for test - "
		 * + excelReader.getCellData(sheetName, 0, rowNum));
		 * 
		 * } else { quoteNumber = (String) excelReader.getCellData(sheetName, 2,
		 * rowNum).substring( excelReader.getCellData(sheetName, 2,
		 * rowNum).indexOf( "TUS"), excelReader.getCellData(sheetName, 2,
		 * rowNum).indexOf( "TUS") + 10);
		 * 
		 * dataArray = new String[allRowCount - rowStart][7]; dataArray[0][0] =
		 * excelReader.getCellData(sheetName, 0, 1); dataArray[0][1] =
		 * excelReader.getCellData(sheetName, 0, rowNum); dataArray[1][0] =
		 * "QRN"; dataArray[1][1] = quoteNumber; dataArray[2][0] = "Row";
		 * dataArray[2][1] = Integer.toString(rowNum); data[rowNum -
		 * rowStart][0] = dataArray;
		 * 
		 * }
		 * 
		 * for (int jowNum = rowStart; jowNum < allRowCount; jowNum++) { //
		 * dataArray[0][0]
		 * 
		 * }
		 * 
		 * 
		 * dataArray = new String[allRowCount - rowStart][2]; dataArray[0][0] =
		 * excelReader.getCellData(sheetName, 0, 1); dataArray[0][1] =
		 * excelReader.getCellData(sheetName, 0, rowNum); dataArray[1][0] =
		 * "QRN"; dataArray[1][1] = quoteNumber; dataArray[2][0] = "Row";
		 * dataArray[2][1] = Integer.toString(rowNum); data[rowNum -
		 * rowStart][0] = dataArray; }
		 */
	}
}
