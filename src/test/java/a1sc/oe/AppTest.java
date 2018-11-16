package a1sc.oe;

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
		/*
		 * String sheetName = "Upgrade System";
		 */
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
		/*
		 * Object[][] data = new Object[20][20]; Hashtable<String, String>
		 * tableData = null; int rowCount = 4; for (int rowNum = 8; rowNum <
		 * rowCount + 8; rowNum++) { tableData = new Hashtable<String,
		 * String>(); for (int colNum = 0; colNum < 13; colNum++) {
		 * 
		 * tableData.put(excelReader.getCellData(sheetName, colNum, 1),
		 * excelReader.getCellData(sheetName, colNum, rowNum)); data[rowNum -
		 * 8][0] = tableData;
		 * 
		 * tableData.put(excelReader.getCellData(sheetName, 6, 0),
		 * excelReader.getCellData(sheetName, 6, rowNum));
		 * tableData.put(excelReader.getCellData(sheetName, 8, 0),
		 * excelReader.getCellData(sheetName, 8, rowNum));
		 * tableData.put(excelReader.getCellData(sheetName, 9, 0),
		 * excelReader.getCellData(sheetName, 9, rowNum));
		 * 
		 * System.out.println((rowNum - 8) + " - " + colNum + " - " +
		 * data[rowNum - 8][0]); System.out.println(data.get("Run Mode"));
		 * System.out.println(data.get("TestCase Name"));
		 * System.out.println(data.get("Run Mode")); }
		 * System.out.println("---------------------------------------"); }
		 */

		/*
		 * WebDriver driver;
		 * 
		 * System.setProperty( "webdriver.chrome.driver",
		 * System.getProperty("user.dir") +
		 * "\\src\\test\\resources\\executables\\chromedriver.exe");
		 * ChromeOptions cOptions = new ChromeOptions();
		 * cOptions.addArguments("--start-maximized");
		 * cOptions.addArguments("--disable-notifications"); driver = new
		 * ChromeDriver(cOptions);
		 * 
		 * //driver.get("https://www.google.com/"); driver.get(
		 * "https://stackoverflow.com/questions/34453773/how-to-do-ctrlf-in-webdriver-java-just-like-f5/34490497#34490497"
		 * ); try { Thread.sleep(5000L); } catch (InterruptedException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * //driver.findElement
		 * (By.xpath("//label[contains(text(),'Password')]"))
		 * .sendKeys(Keys.chord(Keys.CONTROL, "f"));
		 * 
		 * try { Thread.sleep(5000L); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * Actions action = new Actions(driver);
		 * action.keyDown(Keys.CONTROL).sendKeys
		 * (String.valueOf('\u0066')).perform();
		 * 
		 * driver.quit();
		 */

		String sheetName = "Upgrade System", temp;
		int rowCount = excelReader.getRowCount(sheetName);
		int colCount = excelReader.getColumnCount(sheetName);
		System.out.println(rowCount + " - " + colCount);
		rowCount = 3;
		int columnStart = 15, columnCount = 5;

		Object[][] data = new Object[columnCount][2];
		for (int rowNum = columnStart - 15; rowNum < columnCount; rowNum++) {
			for (int colNum = 0; colNum < 1; colNum++) {

				data[rowNum][0] = excelReader.getCellData(sheetName,
						rowNum + 15, 1);
				data[rowNum][1] = excelReader.getCellData(sheetName,
						rowNum + 15, 4);
			}
		}
		rowCount = excelReader.getRowCount(sheetName);
		boolean yeah = excelReader.setCellData(sheetName, "Model Name", rowCount, "CM");
		System.out.println(yeah);
		//excelReader.setCellData(sheetName, "CountryName", rowCount, "India");

	}
}
