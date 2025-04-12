package POM;
import BaseTest.setUp;
import CommonUtilities.CommonMethods;
import listeners.TestListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class amazonElectronics extends TestListeners  {

    public static Logger log = LogManager.getLogger(amazonElectronics.class.getName());
    private CommonMethods commonMethods;
    public amazonElectronics(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.commonMethods = new CommonMethods(driver);
    }
    @FindBy(id = "searchDropdownBox")
    WebElement categoryDropdown;

    @FindBy(id = "twotabsearchtextbox")
    WebElement searchBox;

    @FindBy(id = "nav-flyout-searchAjax")
    WebElement suggestionsBox;

    @FindBy(xpath ="//div[@class=\"s-suggestion-container\"]")
    List<WebElement> suggestionsList;


    //select category from Amazon home page
        public void selectCategory(String category) throws Exception {
            commonMethods.displayInfoLogs("Selecting category: ",category,true,true);
            new Select(categoryDropdown).selectByVisibleText(category);
    }
    //to search any product from search box
        public void searchProduct(String searchTerm) throws Exception {
            commonMethods.displayInfoLogs("Typing search term: ",searchTerm,true,true);
            commonMethods.input(searchBox,searchTerm);
    }
    //to get the suggestions from suggestion box after entering search product
        public List<String> getSuggestionsText() throws Exception {
        Thread.sleep(500);
            commonMethods.ExWait(suggestionsBox);
        List<String> suggestionTexts = new ArrayList<>();
        for (WebElement suggestion : suggestionsList) {
            suggestionTexts.add(suggestion.getText());
        }
            log.info("Found " + suggestionTexts.size() + " suggestions");
            return suggestionTexts;
    }
    //validateSuggestions method used to validate the suggestion list with serach product
    public void validateSuggestions(List<String> suggestions, String searchProduct) throws Exception {
        Thread.sleep(1000);
        System.out.println("Fetched suggestions:");
        for (String suggestion : suggestions) {
            System.out.println("- " + suggestion);
        }
        Assert.assertTrue(suggestions.size() > 0, "No suggestions found!");
        commonMethods.displayInfoLogs("Validating each suggestion contains iphone 13 ...","",true,true);
        for (String suggestion : suggestions) {
            boolean valid = suggestion.toLowerCase().contains(searchProduct.toLowerCase());
            Assert.assertTrue(valid, "Invalid suggestion: " + suggestion);
        }
    }
    public void clickSuggestion(String fullTextMatch) throws Exception {
        commonMethods.displayInfoLogs("Looking for suggestion: " ,fullTextMatch,true,true);
        Thread.sleep(500);
        commonMethods.ExWait(suggestionsBox);
        for (WebElement suggestion : suggestionsList) {
            if (suggestion.getText().equalsIgnoreCase(fullTextMatch)) {
                suggestion.click();
                commonMethods.displayInfoLogs("Clicked on suggestion: " , fullTextMatch,true,true);
                return;
            }
        }
        log.error("Suggestion not found: " + fullTextMatch);
    }

    }

