package a1sc.oe;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

		String sheetName = "Upgrade System";
		/*
		 * int rowCount = excelReader.getRowCount(sheetName); // int columnCount
		 * = excelReader.getColumnCount(sheetName);
		 * 
		 * Object[][] data = new Object[rowCount][2];
		 * 
		 * for (int rowNum = 8; rowNum < excelReader.getRowCount(sheetName);
		 * rowNum++) { for (int colNum = 0; colNum < 1; colNum++) {
		 * 
		 * data[rowNum - 8][0] = excelReader.getCellData(sheetName, 6, rowNum);
		 * 
		 * data[rowNum - 8][1] = excelReader.getCellData(sheetName, 9, rowNum);
		 * System.out.println(data[rowNum - 8][0] + "\t" + data[rowNum - 8][1]);
		 * }
		 * 
		 * }
		 */
		Object[][] data = new Object[20][20];
		Hashtable<String, String> tableData = null;
		int rowCount = 4;
		for (int rowNum = 8; rowNum < rowCount + 8; rowNum++) {
			tableData = new Hashtable<String, String>();
			for (int colNum = 0; colNum < 13; colNum++) {

				tableData.put(excelReader.getCellData(sheetName, colNum, 1),
						excelReader.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 8][0] = tableData;
				/*
				 * tableData.put(excelReader.getCellData(sheetName, 6, 0),
				 * excelReader.getCellData(sheetName, 6, rowNum));
				 * tableData.put(excelReader.getCellData(sheetName, 8, 0),
				 * excelReader.getCellData(sheetName, 8, rowNum));
				 * tableData.put(excelReader.getCellData(sheetName, 9, 0),
				 * excelReader.getCellData(sheetName, 9, rowNum));
				 */
				System.out.println((rowNum - 8) + " - " + colNum + " - "
						+ data[rowNum - 8][0]);
				/*System.out.println(data.get("Run Mode"));
				System.out.println(data.get("TestCase Name"));
				System.out.println(data.get("Run Mode"));*/
			}
			System.out.println("---------------------------------------");
		}

	}
}
