package CommonUtilities;

import BaseTest.setUp;
import POM.appleStorePage;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ScreenShot extends setUp
{
	public static Logger log = LogManager.getLogger(ScreenShot.class.getName());
	static String FileDateTime = null;
	static File FilePath = null;
	static String folderDate = new SimpleDateFormat("dd-MM-yyyy HH").format(new Date());

	//Variables for Screenshots location 
		public static String currentDir;
		public static String outPutFolder;
		public static String PassScreenShotFolder ;
		public static String FailedScreenShotFolder;
		public static String ExtentScreenShotFolder;
		public static String ExtentFailedScreenshot;
		public static String ExtentPassScreenshot;
		public static File flOutput;
		public static File flPassScreenShotFolder, src, dest;
		public static File flFailedScreenShotFolder;
		public static String PassScreenShot;
		public static String FailedScreenShot;
		
		public static String ScreenShotPath;
		public static String ScreenShotName;
		public static Properties config = setUp.loadConfig();


	
	//Create a folder to save Pass screenshots	
	public static File screenShotFolder(String status)
	{
			currentDir = ".\\src\\test\\resources\\Results";

				outPutFolder = currentDir +"\\Output_"+folderDate;
				PassScreenShot ="PassScreenShot_"+folderDate;
				PassScreenShotFolder = outPutFolder+"\\"+PassScreenShot;
				FailedScreenShot = "FailedScreenShot_"+folderDate;
				FailedScreenShotFolder = outPutFolder+"\\"+FailedScreenShot;

			flOutput = new File(outPutFolder);
			if(!flOutput.exists()) {
				if(flOutput.mkdir()) {
					//System.out.println("Directory is created!");
					log.debug("Output Directory Created..");
					}
				else {
					//System.out.println("Failed to create directory!");
	                log.error("Failed to Create Output Directory ");
					}
				}
			
			if(status.equalsIgnoreCase("Pass"))
			{
				flPassScreenShotFolder = new File(PassScreenShotFolder);
				if(!flPassScreenShotFolder.exists()) {
					if(flPassScreenShotFolder.mkdir()) {
						//System.out.println("Pass Screen shot Directory is created!");
						log.debug("PassScreenshot Directory Created..");
						}
					else {
		                //System.out.println("Failed to create directory!");
		                log.error("Failed to Create PassScreenshot Directory ");
						}
					}
					FilePath = flPassScreenShotFolder;
					ExtentPassScreenshot = ExtentScreenShotFolder +  "\\" + PassScreenShot;
					//return FilePath;
			}
			else if(status.equalsIgnoreCase("Fail"))
			{
				flFailedScreenShotFolder=new File(FailedScreenShotFolder);
				if(!flFailedScreenShotFolder.exists())
				{
					if(flFailedScreenShotFolder.mkdir())
					{
						//System.out.println("Failed Screen shot Directory is created!");
						log.debug("FailScreenshot Directory Created..");
					}
					else
					{
						//System.out.println("Failed to create directory!");
		                log.error("Failed to Create FailScreenshot Directory ");
					}
				}				
				FilePath = flFailedScreenShotFolder;
				ExtentFailedScreenshot = ExtentScreenShotFolder + "\\" + FailedScreenShot;
				//return FilePath;
			}
				//FilePath = flScreenShotFolder+"\\"+leadCreationTest1.TestCaseDesc+"_"+fileName+".jpeg";
		return FilePath;
			
	}
	
	//To capture screen shots of Pass cases and web page display on screen 
	public static String takeSnapShot(String name, String status) throws Exception
	{
		ScreenShotPath =null;
		try {
			//Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			//Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			//Move image file to new destination
			File DestFile=new File(screenShotFolder(status)+"\\"+name+".jpeg");
			ScreenShotName  =screenShotFolder(status)+"\\"+name+".jpeg";
			//Copy file at destination
			FileUtils.copyFile(SrcFile, DestFile);
			log.info("ScreenShot captured "+name+ " || Status :"+status);
		}catch(Exception e)
		{
			log.error("Unbale to capture screenshot due to "+e.getMessage());
		}
		return ScreenShotPath;
    }

	public static String getScreenShotPath(String status, String name) {
		String path = null;
		//if(config.getProperty("RunExecutedFromUtility").equalsIgnoreCase("YES"))
		//{
			if(status.equals("Fail")) {
				System.out.println("Extent failed screenshot path:" + ExtentFailedScreenshot+"\\"+name+".jpeg");
				path =  ExtentFailedScreenshot+"\\"+name+".jpeg";
			}
			else if(status.equals("Pass"))
			{
				System.out.println("Extent failed screenshot path:" + ExtentFailedScreenshot+"\\"+name+".jpeg");
				path =  ExtentPassScreenshot+"\\"+name+".jpeg";
			}
		//} else
			//path =  screenShotFolder(status)+"\\"+name+".jpeg";
		return path;
	}

}

