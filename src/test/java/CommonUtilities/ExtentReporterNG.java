package CommonUtilities;

import BaseTest.setUp;
import POM.appleStorePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ExtentReporterNG extends setUp
{
	public static File flOutput;
	static ExtentReports extent ;
	public static Logger log = LogManager.getLogger(ExtentReporterNG.class.getName());
	static String folderDate = new SimpleDateFormat("dd-MM-yyyy HH").format(new Date());
	public static String currentDir = ".\\src\\test\\resources\\Results";
	public static String outPutFolder = currentDir +"\\Output_"+folderDate;
	public static String reportPath = outPutFolder+"\\TestReport_"+folderDate+".html";
	public static Properties config = setUp.loadConfig();


	public static ExtentReports getReportObject()
	{
		extent = new ExtentReports();
		
		flOutput = new File(currentDir);
		if(!flOutput.exists()) {
			if(flOutput.mkdir()) {
				log.info("Reports Directory is created!");
			}
			else {
				log.error("Failed to create reports directory!");
			}		
		}

			outPutFolder = currentDir +"\\Output_"+folderDate;
			reportPath = outPutFolder+"\\TestReport_"+folderDate+".html";

		flOutput = new File(outPutFolder);
		if(!flOutput.exists()) {
			if(flOutput.mkdir()) {
				log.info("Extent report Directory is created!");
			}
			else {
				log.error("Failed to create extent report directory!");
			}
		}
    
		ExtentSparkReporter reporter =new ExtentSparkReporter(reportPath).viewConfigurer()
			    .viewOrder()
			    .as(new ViewName[] { 
				   ViewName.DASHBOARD, 
				   ViewName.TEST, 
				   ViewName.CATEGORY,
				   ViewName.AUTHOR, 
				   ViewName.DEVICE, 
				   ViewName.EXCEPTION, 
				   ViewName.LOG
				})
			  .apply();
		//ExtentSparkReporter reporter =new ExtentSparkReporter(reportPath).filter().statusFilter().as(new Status [] {Status.FAIL,Status.PASS}).apply().viewConfigurer().viewOrder().as(new ViewName [] {ViewName.DASHBOARD,ViewName.TEST}).apply();
		
		 reporter.config().setTimelineEnabled(false);
		 reporter.config().setCss(".sysenv-container{right:50%} .category-container{left:50%}");
		 reporter.config().setJs("document.querySelector('.category-container .card .card-header p').innerHTML='Cases/Scenarios <br> Note: Skipped Tests - Either not selected during run / error during run';");
		 //reporter.config().setReportName("<img src='"+ config.getProperty("LogoName")+"'/>");
		try {
			reporter.loadXMLConfig(new File(System.getProperty("user.dir") + config.getProperty("ExtentConfigXml")));
		} catch (IOException e) {
			log.error("Unable to load config.xml file due to "+e.getMessage());
		}

		extent.attachReporter(reporter);
		extent.setSystemInfo("Project Name","Amazon Test");
		extent.setSystemInfo("OS", System.getProperty("os.name"));

		return extent;
	}
	
}
