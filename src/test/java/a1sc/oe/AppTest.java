package a1sc.oe;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	public static ExcelReader fieldsFile;
	public static String xpathSheetName = "Xpath Issues";

	public static void main(String[] args) {

		ExcelReader excelReader;
		excelReader = new ExcelReader(System.getProperty("user.dir")
				+ "\\src\\test\\resources\\excel\\IPO_TestPlanReference.xlsx");

		String sheetName = "IPO - MM", tempName, quoteNumber;
		int allRowCount = excelReader.getRowCount(sheetName);
		int allColCount = excelReader.getColumnCount(sheetName);
		int columnStart = 15, rowStart = 8, columnCount = 561, colDeduction = 0, dataIndex = 0;

		for (int rowCheck = 1; rowCheck < 30; rowCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, 0, rowCheck);

			if (tempName.equals("OnFail")) {
				rowStart = rowCheck + 1;
				System.out.println(rowStart);
				break;
			}
		}

		for (int colCheck = 0; colCheck < 30; colCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, colCheck, 4);

			if (tempName.contains("xpath:")) {
				columnStart = colCheck;
				System.out.println(columnStart);
				break;
			}
		}

		for (int rowNum = rowStart; rowNum < allRowCount; rowNum++) {

			tempName = (String) excelReader.getCellData(sheetName, 2, rowNum);
			// System.out.println(tempName);
			if (!tempName.contains("TUS") || tempName.contains("No")
					|| tempName.length() < 3) {
			} else {
				dataIndex++;
			}

		}
		System.out.println(dataIndex);
		
		
		allColCount = excelReader.getColumnCount(sheetName);
		
		for (int colCheck = 0; colCheck < allColCount; colCheck++) {
			tempName = (String) excelReader.getCellData(sheetName, colCheck, 1);

			if (tempName.contains("Maintenance Services")) {
				allColCount = colCheck-1;
				break;
			}
		}
		
		System.out.println(allColCount);
	}

}
