package POM;

import CommonUtilities.CommonMethods;
import listeners.TestListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Set;

public class searchResultPage extends TestListeners {
    public static Logger log = LogManager.getLogger(searchResultPage.class.getName());
    public  CommonMethods commonMethods;
    public searchResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.commonMethods = new CommonMethods(driver);
    }

    @FindBy(xpath="//div[@role=\"listitem\"]//span[contains(text(),'Apple iPhone 13')]")
    List<WebElement> productLinks;


    public void clickFirstProduct() throws Exception {
        commonMethods.ExWaitsForWebelements(productLinks);
        productLinks.get(0).click();
        log.info("Clicked on first product from list");
        TestListeners.extentInfo("Clicked on first product from list","");
        commonMethods.displayInfoLogs("Clicked on first product from list","",true,true);
        Thread.sleep(1000);
    }

    public boolean validateNewTabOpened(Set<String> oldHandles) {
        Set<String> currentHandles = driver.getWindowHandles();
        return currentHandles.size() > oldHandles.size();
    }
     public boolean switchToNewWindow()
     {
         return commonMethods.switchToWindow();
     }
}
