package POM;

import CommonUtilities.CommonMethods;
import listeners.TestListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class productPage extends TestListeners {
    public static Logger log = LogManager.getLogger(productPage.class.getName());
    public  CommonMethods commonMethods;

    public productPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.commonMethods = new CommonMethods(driver);

    }
    @FindBy(linkText = "Visit the Apple Store")
    WebElement appleStoreLink;

    public void clickVisitAppleStore() {
        //appleStoreLink.click();
        commonMethods.Click(appleStoreLink);
    }

}
