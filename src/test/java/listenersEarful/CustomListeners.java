package listenersEarful;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

import testBase.GroundFloor;
import utilities.UtitliyClass;

public class CustomListeners extends GroundFloor implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {

		test = extentReports.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName().toString()
				+ " has started execution");

		Reporter.log(" test has started");
		Reporter.log("<br>");
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		Reporter.log(result.getName() + " has passed");
		Reporter.log("<br>");
		try {
			UtitliyClass.screenshotCapture();
			Reporter.log("<a target=\"blank\" href="
					+ UtitliyClass.screenshotName + ">Screenshot </a>");
			Reporter.log("<br>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.log(LogStatus.PASS, result.getName().toString() + " has passed");
		test.log(LogStatus.PASS,
				test.addScreenCapture(UtitliyClass.screenshotName));
		extentReports.endTest(test);
		extentReports.flush();

	}

	@Override
	public void onTestFailure(ITestResult result) {

		Reporter.log(result.getName() + " has failed");
		Reporter.log("<br>");
		try {

			UtitliyClass.screenshotCapture();
			Reporter.log("<a target=\"blank\" href="
					+ UtitliyClass.screenshotName + ">Screenshot </a>");
			Reporter.log("<br>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.log(LogStatus.FAIL, result.getName().toString()
				+ " has failed due to " + result.getThrowable());
		test.log(LogStatus.FAIL,
				test.addScreenCapture(UtitliyClass.screenshotName));
		extentReports.endTest(test);
		extentReports.flush();

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		Reporter.log(result.getName() + "has skipped");
		try {
			UtitliyClass.screenshotCapture();
			Reporter.log("<a target=\"blank\" href="
					+ UtitliyClass.screenshotName + ">Screenshot </a>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.log(LogStatus.SKIP, result.getName().toString()
				+ " has skipped due to " + result.getThrowable());
		extentReports.endTest(test);
		extentReports.flush();

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
