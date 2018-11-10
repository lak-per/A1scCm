package a1sc.oe;

import utilities.ExcelReader;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {

		ExcelReader excelReader;
		excelReader = new ExcelReader(
				System.getProperty("user.dir")
						+ "\\src\\test\\resources\\excel\\CM_UPGRADE_TestPlanReference.xlsx");

		String sheetName = "Upgrade System";
		int rowCount = excelReader.getRowCount(sheetName);
		// int columnCount = excelReader.getColumnCount(sheetName);

		Object[][] data = new Object[rowCount][2];

		for (int rowNum = 8; rowNum < excelReader.getRowCount(sheetName); rowNum++) {
			for (int colNum = 0; colNum < 1; colNum++) {

				data[rowNum - 8][0] = excelReader.getCellData(sheetName, 6,
						rowNum);

				data[rowNum - 8][1] = excelReader.getCellData(sheetName, 9,
						rowNum);
				System.out.println(data[rowNum - 8][0] + "\t"
						+ data[rowNum - 8][1]);
			}

		}

	}
}
