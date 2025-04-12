package listeners;

import BaseTest.setUp;
import CommonUtilities.ExtentReporterNG;
import CommonUtilities.ScreenShot;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class TestListeners extends setUp implements ITestListener, ISuiteListener
{
   	public static ExtentTest test;
   	public static ExtentReports extent= ExtentReporterNG.getReportObject();
	public static ThreadLocal<ExtentTest> extentTest =new ThreadLocal<>();
	
	public static List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();
	public static LocalDateTime startTime;
	public static LocalDateTime endTime;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
    public static int runnableCount = 0;
    
    public static Properties config = setUp.loadConfig();
    public static int runnableTCCount = 0;
    public static int iterationCount = 0;
    static File statusFile = new File(System.getProperty("user.dir") + config.getProperty("StatusFile"));
    static FileWriter myWriter;
    static int testCount = 0;

    
	public void onTestStart(ITestResult result) 
	{
		String methodName = result.getMethod().getMethodName();
		//test = extent.createTest(result.getTestClass().getName() + "  @TestCase : " + result.getMethod().getMethodName());
		test = extent.createTest(result.getTestClass().getName());
		extentTest.set(test);
		log.info("Test Case_" + methodName+ "_Successfully Started");
	}

	public void onTestSuccess(ITestResult result)
	{
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Case:- " + methodName+ " PASSED" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().pass(m);
        passedtests.add(result.getMethod());
		log.info("Test Case_" + methodName + "_Successfully Passed");
	}

	public static void extentInfo(String message,String name) throws Exception
	{
		Markup m = MarkupHelper.createLabel(message +" "+name, ExtentColor.BLUE);
    	extentTest.get().log(Status.INFO, m);
    	//ScreenShot.takeSnapShot(name, "Pass");
    	//extentTest.get().log(Status.INFO, message,MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.ScreenShotName).build() );
	}
	public static void extentError(String message,String name)
	{
		Markup m = MarkupHelper.createLabel(message +" "+name, ExtentColor.RED);
    	extentTest.get().log(Status.FAIL, m);
	}

	public void onTestFailure(ITestResult result)
	{
		String methodName = result.getMethod().getMethodName();
		log.error(methodName+ " Get Failed due to " + "\n" + result.getThrowable().getMessage());

		String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		// String excepionMessage= result.getThrowable().getMessage();
		extentTest.get()
				.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
						+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
						+ " \n");
        failedtests.add(result.getMethod());

		try {
				if(driver != null) {
					ScreenShot.takeSnapShot(methodName, "Fail");
					extentTest.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
						MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.getScreenShotPath("Fail", methodName)).build());
				}
			} catch (Exception e) 
			{
				System.out.println("Exception occured while adding SS to extent report :"+e.getMessage());
			}
		
		String failureLogg = "TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);

	}
	public void onTestSkipped(ITestResult result) {

		//extentTest.get().log(Status.SKIP,result.getMethod().getMethodName()+" Skipped");
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		extent.removeTest(test);

		skippedtests.add(result.getMethod());

		log.info("Test Case_" + methodName + "_get Skipped as its Runmode is 'NO' ");

	}
	public void onFinish(ITestContext context) {
		if (extent != null) {
			extent.flush();
		}
	}

}

	

