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
		excelReader = new ExcelReader(
				System.getProperty("user.dir")
						+ "\\src\\test\\resources\\excel\\CM_UPGRADE_TestPlanReference.xlsx");

		Date d = new Date();
		String xpathFileName = d.toString().replace(":", "_").replace(" ", "_");
		String xpathFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\xpathFileName_"
				+ xpathFileName.trim().toString() + ".xlsx";
		System.out.println(xpathFilePath);
		File xpathFile = new File(xpathFilePath.toString().trim());
		// fieldsFile.createExcel(xpathFile,xpathSheetName);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream(xpathFile);
			XSSFSheet sheet = workbook.createSheet(xpathSheetName);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fieldsFile = new ExcelReader(xpathFilePath.toString().trim());
		//fieldsFile.addSheet(xpathSheetName);
		fieldsFile.setCellDatawithoutFileOut(xpathSheetName, 1, 0, "Page Name");
		fieldsFile.setCellDatawithoutFileOut(xpathSheetName, 1, 1,
				"Issues or Comments");

	}
}
