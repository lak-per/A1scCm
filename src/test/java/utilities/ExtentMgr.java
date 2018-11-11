package utilities;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentMgr {

	private static ExtentReports extent = null;

	public static ExtentReports getInstance() {

		if (extent == null) {
			extent = new ExtentReports(System.getProperty("user.dir")
					+ "\\test-output\\html\\extent.html", true);

			extent.loadConfig(new File(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\extent-config.xml"));
		}

		return extent;
	}
}
