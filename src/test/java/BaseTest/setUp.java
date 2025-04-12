package BaseTest;

import CommonUtilities.CommonMethods;
import CommonUtilities.ExtentReporterNG;
import POM.amazonElectronics;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class setUp {
    public static WebDriver driver;
    public static Properties prop=new Properties();
    public static Logger log = LogManager.getLogger(setUp.class.getName());
    public static JSONParser parser = new JSONParser();


    @BeforeMethod
    public static void setUpTest1()throws Exception
    {
        String browserName= CommonMethods.readPropertyFile("Browser");
        //using webdriver manager
        if(browserName.equalsIgnoreCase("Chrome"))
        {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito"); // Optional: open in incognito
            chromeOptions.addArguments("start-maximized");
            // Use a temporary user data dir (wipes history/cache each run)
            chromeOptions.addArguments("user-data-dir=C:/temp/selenium_profile");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(chromeOptions);
            log.info("......Chrome Browser Launched ......");
        }
        else if(browserName.equalsIgnoreCase("Firefox"))
        {

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(firefoxOptions);
            log.info("Firefox Browser Launched..");
        }
        String URL = CommonMethods.readPropertyFile("testsiteurl");
        driver.get(URL);
        log.info("URL : "+URL);
        driver.navigate().refresh();
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    @AfterMethod
    public void tearDownTest()
    {
            driver.quit();
            log.info("Browser Closed..");
    }

    public static Properties loadConfig() {
        Properties config = new Properties();
        FileInputStream fis = null;
        try {

            fis = new FileInputStream(
                    System.getProperty("user.dir") + "\\src\\test\\resources\\PropertyFiles\\Config.properties");
             } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            config.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return config;
    }

    public static JSONObject loadJSON() throws IOException, ParseException {
        try{
            Object obj = parser.parse(new FileReader( "./src/test/resources/TestData/testInput.json"));

            return (JSONObject)obj;
        } catch (Exception e) {
        throw new RuntimeException("Failed to read JSON file : " +e);
        }
    }
}
