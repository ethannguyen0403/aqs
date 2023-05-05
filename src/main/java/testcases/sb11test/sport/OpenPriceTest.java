package testcases.sb11test.sport;

import com.paltech.element.common.Label;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.EventMappingPage;
import pages.sb11.sport.OpenPricePage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.awt.*;

import static common.SBPConstants.*;

public class OpenPriceTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2092")
    public void OpenPriceTC_001(){
        log("@title: Validate Open Price page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("Validate Event Mapping page is displayed with correctly title");
        Assert.assertTrue(openPricePage.getTitlePage().contains(OPEN_PRICE), "Failed! Open Price page for Cricket sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2093")
    public void OpenPriceTC_002(){
        log("@title: Validate UI on Open Price is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("Validate UI on Open Price is correctly displayed");
        log("Date, Show League button, Leagues and Show button");
        openPricePage.filterResult("","All",true);
        Assert.assertTrue(openPricePage.txtDate.isDisplayed(),"Failed! Date datetime picker is not displayed!");
        Assert.assertTrue(openPricePage.btnShowLeagues.isDisplayed(),"Failed! Show League button is not displayed!");
        Assert.assertTrue(openPricePage.ddpLeague.isDisplayed(),"Failed! League dropdown is not displayed!");
        Assert.assertTrue(openPricePage.btnShow.isDisplayed(),"Failed! Show button is not displayed!");
        log("Event table header columns is correctly display");
        log("Header is " + openPricePage.tbOpenPrice.getHeaderNameOfRows());
        Assert.assertEquals(openPricePage.tbOpenPrice.getHeaderNameOfRows(), OpenPrice.TABLE_HEADER,"FAILED! Open Price table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2094")
    public void OpenPriceTC_003(){
        log("@title: Validate League list is displayed correctly when clicking Show Leagues");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("@Step 3:  Select Date and click Show League ");
        openPricePage.filterResult("","All",false);
        log("Validate League list is displayed correctly");
        int leagueSize = openPricePage.ddpLeague.getNumberOfItems();
        Assert.assertTrue(leagueSize > 0, "Failed! League list is having no item" );
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2095")
    public void OpenPriceTC_004(){
        log("@title: Validate selected League is displayed correctly when clicking Show");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("@Step 3:  Select Date and click Show League ");
        String league = openPricePage.getFirstLeague();
        log("@Step 4: Select a league and click Show");
        openPricePage.filterResult("",league,true);
        log("Validate selected League is displayed correctly when clicking Show");
        Assert.assertTrue(openPricePage.isLeagueExist(league),"FAILED! League "+ league+" does not display in the list");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    @TestRails(id = "2096")
    public void OpenPriceTC_005(){
        log("@title: Validate updated Open Price is displayed correctly on BBT page");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("@Step 3:  Select Date and click Show League ");
        String league = openPricePage.getFirstLeague();
        log("@Step 4: Select a league and click Show");
        openPricePage.filterResult("",league,true);
        log("@Step 5: Fill result on any event and click Submit");
        log("@Step 6: Navigate to Soccer > BBT");
        log("@Step 7: Filter with event at step 4");
        log("Validate League list is displayed correctly");
        log("INFO: Executed completely");
    }
}
