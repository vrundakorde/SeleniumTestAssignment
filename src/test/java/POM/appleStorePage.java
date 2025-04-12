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

public class appleStorePage extends TestListeners {
    public static Logger log = LogManager.getLogger(appleStorePage.class.getName());
    public  CommonMethods commonMethods;
    public appleStorePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.commonMethods = new CommonMethods(driver);
    }

    @FindBy(xpath = "//nav[@aria-label='Home page Navigation Bar']//a[@role='button']//span[text()='Apple Watch']")
    WebElement watchDropdownOption;

    @FindBy(xpath ="//div[@aria-label='Navigation List']//*[contains(text(),'Apple Watch')]")
    List<WebElement> appleWatchList;

    @FindBy(xpath ="//div[@id='4k2av5ugzs']")
    WebElement watchImage;

    @FindBy(xpath ="//div[@id='4k2av5ugzs']//*[contains(text(),'Quick look')]")
    WebElement quickLookButton;

    @FindBy(xpath="//div[@class='VariationHandler__wrapper__EwrUC']")
    WebElement quickLookModal;
    @FindBy(xpath ="//h2[@id='asin-title']")
    WebElement quickLookModalTitle;

    public void selectWatchModel(String watchModel) throws Exception {
        commonMethods.mouseClick(watchDropdownOption);
        log.info("Apple watch dropdown clicked..");
        for(WebElement ele : appleWatchList)
        {
            if(ele.getText().equalsIgnoreCase(watchModel))
            {
                commonMethods.mouseClick(ele);
                commonMethods.displayInfoLogs("Clicked on apple watch: " , watchModel,true,true);
                return;
            }
        }
    }

    public boolean hoverAndCheckQuickLook() throws Exception {

        commonMethods.mouseHover(watchImage);
        if(commonMethods.isElementsDisplay(quickLookButton))
        {
            commonMethods.displayInfoLogs("QuickLook button display on mouse hover " , "on product",true,true);
            return true;
        }
        else return false;
    }

    public boolean clickQuickLook() throws Exception {
        Thread.sleep(500);
        commonMethods.scrollByVisibilityofElement(quickLookButton);
        commonMethods.mouseClick(quickLookButton);
       // if(quickLookModal.isDisplayed())
        if(commonMethods.isElementsDisplay(quickLookModal))
        {
            commonMethods.displayInfoLogs("QuickLook Modal view display" , "after clicking on QuickLook button..",true,true);
            return true;
        }
        else return false;
    }

    public boolean verifyModalContent(String expectedText) throws Exception {
        Thread.sleep(500);
        String ModelTitle = commonMethods.getElementText(quickLookModalTitle);
        commonMethods.displayInfoLogs("Model content: "+ModelTitle ,"  Expected Text : "+expectedText,true,true);
        if(checkWordsFromString(expectedText,ModelTitle))
        {
           commonMethods.displayInfoLogs("Modal content is related to Same product for which Quick look is performed.","",true,true);
            return true;
        }
        else return false;
    }

    //To verify that each word in String1 exists somewhere in String2
    public static boolean checkWordsFromString(String str1, String str2) {
        // Normalize: remove special characters & lowercase
        String normalizedStr1 = str1.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
        String normalizedStr2 = str2.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
        String[] words = normalizedStr1.split("\\s+");
        for (String word : words) {
            if (!normalizedStr2.contains(word)) {
                //System.out.println("Missing word: " + word);
                return false;
            }
        }
        return true;
    }


}
