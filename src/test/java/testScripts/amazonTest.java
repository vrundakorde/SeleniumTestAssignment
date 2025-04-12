package testScripts;

import BaseTest.setUp;
import CommonUtilities.CommonMethods;
import POM.amazonElectronics;
import POM.appleStorePage;
import POM.productPage;
import POM.searchResultPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class amazonTest extends setUp {

    public amazonElectronics aE1;
    public searchResultPage resultsPage;
    public appleStorePage storePage;
    public productPage prodPage;
    public boolean result;
    @Test
    public void verifyElectronicsSearch() throws Exception
    {
        List<String> suggestions ;
        aE1 = new amazonElectronics(driver);
        //Click on Electronics from dropdown menu and type “IPhone 13”
        aE1.selectCategory(CommonMethods.getValueByKey("category"));
        aE1.searchProduct(CommonMethods.getValueByKey("searchTerm"));
        //Get All the dropdown suggestions and validate all are related to searched product
        suggestions = aE1.getSuggestionsText();
        aE1.validateSuggestions(suggestions,CommonMethods.getValueByKey("searchTerm"));
        //Then Type again “IPhone 13 128 GB” variant and Click “iPhone 13 128GB” variant from dropdown Result.
        aE1.searchProduct(CommonMethods.getValueByKey("variant"));
        aE1.clickSuggestion(CommonMethods.getValueByKey("variant"));

        //From Results, click on the searched product and validate new tab is opened
        // Store current window and validate tab switch
        resultsPage = new searchResultPage(driver);
        //String mainWindow = driver.getWindowHandle();
        resultsPage.clickFirstProduct();
        result=resultsPage.switchToNewWindow();
        Assert.assertTrue(result,"New tab is opened and switch to it..");

        //Thread.sleep(1000);
        prodPage = new productPage(driver);
        prodPage.clickVisitAppleStore();

        storePage = new appleStorePage(driver);
        storePage.selectWatchModel(CommonMethods.getValueByKey("watchModel"));
        result= storePage.hoverAndCheckQuickLook();
        Assert.assertTrue(result,"Quick look button is displayed on image");
        result = storePage.clickQuickLook();
        Assert.assertTrue(result,"Quick look model container is displayed");

        result=storePage.verifyModalContent(CommonMethods.getValueByKey("watchModel"));
        Assert.assertTrue(result,"newly opened modal is related to Same product for which Quick look is performed.");

    }
}
