package CommonUtilities;

import BaseTest.setUp;
import POM.productPage;
import listeners.TestListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.*;


public class CommonMethods extends TestListeners {
	public static JavascriptExecutor js;
	public static WebDriverWait wait;
	public static Actions a;
	public static Logger log = LogManager.getLogger(CommonMethods.class.getName());

	//public static Logger log = LoggerFactory.getLogger(CommonMethods.class.getName());
	public static Properties prop = setUp.loadConfig();
	public static JSONObject json;

    static {
        try {
            json = setUp.loadJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
	public CommonMethods(WebDriver driver)
	{
		this.driver = driver;
	}

    public void ExWait(String locator) throws Exception {
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));

	}
	public void ExWait(WebElement locator) throws Exception {
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOf(locator));

	}

	public void waitForURL(String urlContains) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.urlContains(urlContains));
	}

	public boolean isElementsDisplay(WebElement locator)
	{
		try {
			ExWait(locator);
			locator.isDisplayed();
			log.info("Element display on page... ");
		} catch (Exception e) {
			log.error("Element Not displayed on page.:" + e.getMessage());
		}
		return true;
	}
	public void displayInfoLogs(String message,String name, boolean onLog4j, boolean extentReport) throws Exception {
		if(onLog4j)
		{
			log.info(message+" "+name);
		}
		if (extentReport) {
			TestListeners.extentInfo(message,name);
		}
	}
	public void Click(WebElement locator) {
		try {
			ExWait(locator);
			locator.click();
			log.info("Sucessfully clicked on " + locator);
			TestListeners.extentInfo("Sucessfully clicked on ", String.valueOf(locator));

		} catch (Exception e) {
			log.error("Not Sucessfully clicked on " + locator + " due to :" + e.getMessage());
		}
	}

	public void input(WebElement locator, String value) {
		try {
			Click(locator);
			locator.clear();
			locator.sendKeys(value);
			Thread.sleep(200);
			log.info("Data successfully entered on " + locator + " = " + value);
			TestListeners.extentInfo("Data successfully entered on " + locator, " = " + value);

		} catch (Exception e) {
			log.error("Data Not Sucessfully entered on " + locator + " due to :" + e.getMessage());
		}
	}

	public String getElementText(WebElement locator) throws Exception {
		ExWait(locator);
		String txtMsg = locator.getText();
		return txtMsg;
	}

	public String getElementValue(WebElement locator) throws Exception {
		ExWait(locator);
		String elementValue = locator.getAttribute("value");
		//log.info("Value of WebElement :" +elementValue);
		return elementValue;
	}
	public void ExWaitsForWebelements(List<WebElement> ele) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfAllElements(ele));
	}

	//To highlight selected webelement
	public void highLight(WebElement locator) throws Exception {
		ExWait(locator);
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='4px solid yellow'", locator);
	}

	//to scroll down the page by pixel values as Y co-ordiante
	public void scrollDown(int y) {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + y + ")");
	}

	//To scroll down the page by visibility of the element
	public void scrollByVisibilityofElement(WebElement locator) throws Exception {
		js = (JavascriptExecutor) driver;
		//WebElement element = driver.findElement(By.xpath(locator));
		js.executeScript("arguments[0].scrollIntoView()", locator);
	}

	//To scroll down the page at the bottom of page.
	public void scrollAtBottom() {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		//Return the complete height of body (page)
	}

	//To select values from dropdown by visible text
	public void selectByText(WebElement locator, String value) throws Exception {
		Select sel = new Select(locator);
		try {
			ExWait(locator);
			sel.selectByVisibleText(value);
			log.info("Data = " + value + " Sucessfully Selected from dropdown " + locator);
		} catch (Exception e) {
			log.error("Not able to select from dropdown " + locator + "due to " + e.getMessage());
		}
	}

	//To select values from dropdown by its value
	public void selectByValue(WebElement locator, String value) throws InterruptedException, IOException {
		try {
			Select sel = new Select(locator);
			ExWait(locator);
			sel.selectByValue(value);
			log.info("Data = " + value + " Sucessfully Selected from dropdown " + locator);
		} catch (Exception e) {
			log.error("Not able to select from dropdown " + locator + "due to " + e.getMessage());
		}
	}

	//To select values from dropdown by its index value
	public void selectByIndex(WebElement locator, int index) throws Exception {
		try {
			Select sel = new Select(locator);
			ExWait(locator);
			sel.selectByIndex(index);
			log.info("Data = " + index + " Sucessfully Selected from dropdown " + locator);
		} catch (Exception e) {
			log.error("Not able to select from dropdown " + locator + "due to " + e.getMessage());
		}
	}

	//To handle mouse hover actions
	public void mouseHover(WebElement locator) throws Exception {
		try {
			a = new Actions(driver);
			ExWait(locator);
			highLight(locator);
			a.moveToElement(locator).perform();
			log.info("Mouse hover on " + locator);
		} catch (Exception e) {
			log.error("Unable to mouse hover due to " + e.getMessage());
		}

	}

	//To handle mouse hover actions
	public  void mouseClick(WebElement locator) {
		try {
			a = new Actions(driver);
			ExWait(locator);
			highLight(locator);
			a.moveToElement(locator).click().build().perform();
			log.info("Mouse Click on " + locator);
		} catch (Exception e) {
			log.error("Not able to Mouseclick due to " + e.getMessage());
		}

	}

	public void AlertHandle(String action) {
		try {
			if (action.equalsIgnoreCase("accept")) {
				driver.switchTo().alert().accept();
				log.info("Alert accepted succesfully");
			} else if (action.equalsIgnoreCase("dismiss")) {
				driver.switchTo().alert().dismiss();
				log.error("Alert dismissed succesfully");
			}
		} catch (Exception e) {
			log.info("Not able to clicked on alert due to " + e.getMessage());
		}
	}

	public boolean switchToWindow() {
		try {
			String parentWin = driver.getWindowHandle();
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> it = windows.iterator();
			while (it.hasNext()) {
				String childWin = it.next();
				if (!parentWin.equals(childWin)) {
					driver.switchTo().window(childWin);
					//Verify title of the child window
					//System.out.println("Window Title = "+driver.getTitle() +"and URL = "+driver.getCurrentUrl() );
					log.info("Swiched to Child window name : " + driver.getTitle() + " || URL :" + driver.getCurrentUrl());
					return  true;
				}
			}
		} catch (Exception e) {
			log.error("Not able switch to window " + e.getMessage());
		}
		return false;

	}

	public boolean switchToWindowByTitle(String title) {
		String currentWindow = driver.getWindowHandle();
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				String switchedWindowTitle = driver.switchTo().window(windowId).getTitle();
				if ((switchedWindowTitle.equals(title)) || (switchedWindowTitle.contains(title))) {
					log.info("Switched to window by title: "+title);
					return true;
				} else {
					driver.switchTo().window(currentWindow);
					log.info("Switched to window by title: "+title);
				}
			}
		}
		return false;
	}

	public void switchToParentWin() {

		try {
			String parentwindow = driver.getWindowHandle();
			driver.switchTo().window(parentwindow);
			log.info("Swiched to parent window " + driver.getTitle() + " with ID " + parentwindow);

		} catch (Exception e) {
			log.error("Not able to switch to parent window " + e.getMessage());
		}

	}

	public static String readPropertyFile(String propertyName) throws IOException {
		String propertyValue = prop.getProperty(propertyName);
		return propertyValue;
	}

	public static String getValueByKey(String key)  {
		JSONObject jobj = json;
		if (jobj.containsKey(key)) {
			return jobj.get(key).toString();
		} else {
			throw new RuntimeException("Key not found: " + key);
		}
	}
}
