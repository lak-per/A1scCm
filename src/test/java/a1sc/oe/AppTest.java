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

		System.out.println("Start");
		/*
		 * ExcelReader excelReader; excelReader = new ExcelReader(
		 * System.getProperty("user.dir") +
		 * "\\src\\test\\resources\\excel\\CM_UPGRADE_TestPlanReference.xlsx");
		 * 
		 * String sheetName = "Upgrade System", temp; int rowCount =
		 * excelReader.getRowCount(sheetName); int colCount =
		 * excelReader.getColumnCount(sheetName); // System.out.println(rowCount
		 * + " - " + colCount); rowCount = 3; int columnStart = 15, columnCount
		 * = 5;
		 * 
		 * int allRowCount = excelReader.getRowCount(sheetName); int allColCount
		 * = excelReader.getColumnCount(sheetName);
		 * System.out.println(allRowCount + " - " + allColCount); // rowCount =
		 * 3; int rowStart = 8, colDeduction = 4; String[][] dataArray = null;
		 * 
		 * Object[][] data = new Object[allRowCount - rowStart][2]; for (int
		 * rowNum = rowStart; rowNum < allRowCount; rowNum++) {
		 * 
		 * dataArray = new String[allColCount - colDeduction][2];
		 * dataArray[0][0] = excelReader.getCellData(sheetName, 0, 1);
		 * dataArray[0][1] = excelReader.getCellData(sheetName, 0, rowNum);
		 * dataArray[1][0] = excelReader.getCellData(sheetName, 2, 1);
		 * dataArray[1][1] = excelReader.getCellData(sheetName, 2, rowNum);
		 * dataArray[2][0] = "Row"; dataArray[2][1] = Integer.toString(rowNum);
		 * System.out.println(dataArray[0][0] + " - " + dataArray[0][1]);
		 * System.out.println(dataArray[1][0] + " - " + dataArray[1][1]);
		 * 
		 * for (int colNum = 0; colNum < 20; colNum++) {
		 * 
		 * dataArray[colNum + 3][0] = excelReader.getCellData(sheetName, colNum
		 * + columnStart, 1); dataArray[colNum + 3][1] =
		 * excelReader.getCellData(sheetName, colNum + columnStart, 4);
		 * System.out.println(dataArray[colNum + 2][0] + " - " +
		 * dataArray[colNum + 2][1]); } // data[rowNum - rowStart][0] =
		 * dataArray; }
		 */
		String abcd = "QRN# : TUS1243402", quoteNumber;
		quoteNumber = (String) abcd.substring(abcd.indexOf("TUS"),
				abcd.indexOf("TUS") + 10);
		System.out.println(quoteNumber);

	}
}
