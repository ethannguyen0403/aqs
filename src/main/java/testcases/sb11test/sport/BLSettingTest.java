package testcases.sb11test.sport;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.BLSettingPage;
import pages.sb11.sport.OpenPricePage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BLSettingTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    public void BLSettingsTC_001(){
        log("@title: Validate BL Settings page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > BL Settings");
        BLSettingPage blSettingPage = welcomePage.navigatePage(SPORT,BL_SETTINGS, BLSettingPage.class);
        log("Validate BL Settings page is displayed with correctly title");
        Assert.assertTrue(blSettingPage.getTitlePage().contains(BL_SETTINGS), "Failed! BL Settings page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2097")
    public void BLSettingsTC_002(){
        log("@title: Validate UI on Open Price is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > BL Settings");
        BLSettingPage blSettingPage = welcomePage.navigatePage(SPORT,BL_SETTINGS, BLSettingPage.class);
        log("Validate UI on BL Settings is correctly displayed");
        log("Date, Show League button, Leagues and Show button");
        Assert.assertEquals(blSettingPage.lblDate.getText(), "Date","Failed! Date datetime picker is not displayed!");
        Assert.assertEquals(blSettingPage.btnShowLeagues.getText(),"Show Leagues","Failed! Show League button is not displayed!");
        Assert.assertTrue(blSettingPage.ddpLeague.getOptions().contains("All"),"Failed! League dropdown is not displayed!");
        Assert.assertEquals(blSettingPage.ddpOrderBy.getOptions(),ORDER_BY_LIST,"Failed! Order By dropdown is not displayed!");
        Assert.assertEquals(blSettingPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("Validate BL Settings table header columns is correctly display");
        Assert.assertEquals(blSettingPage.tbBLSettings.getHeaderNameOfRows(), BLSettings.TABLE_HEADER,"FAILED! BL Settings table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2098")
    public void BLSettingsTC_003(){
        log("@title: Validate League list is displayed correctly when clicking Show Leagues");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > BL Settings");
        BLSettingPage blSettingPage = welcomePage.navigatePage(SPORT,BL_SETTINGS, BLSettingPage.class);
        log("@Step 3:  Select Date and click Show League ");
        log("Validate League list is displayed correctly");
        int leagueSize = blSettingPage.ddpLeague.getNumberOfItems();
        Assert.assertTrue(leagueSize > 0, "Failed! League list is having no item" );
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2099")
    public void BLSettingsTC_004(){
        log("@title: Validate selected League is displayed correctly when clicking Show");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > BL Settings");
        BLSettingPage blSettingPage = welcomePage.navigatePage(SPORT,BL_SETTINGS, BLSettingPage.class);
        log("@Step 3:  Select Date and click Show League ");
        String league = blSettingPage.getFirstLeague();
        log("@Step 4: Select a league and click Show");
        blSettingPage.filterResult("",league,"KOT",true);
        log("Validate Event of selected league is displayed correctly on Event table");
        Assert.assertTrue(blSettingPage.isLeagueExist(league),"FAILED! League "+ league+" does not display in the list");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    @TestRails(id = "2100")
    public void BLSettingsTC_005(){
        log("@title: Validate selected League is displayed correctly when clicking Show");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > BL Settings");
        BLSettingPage blSettingPage = welcomePage.navigatePage(SPORT,BL_SETTINGS, BLSettingPage.class);
        log("@Step 3:  Select Date and click Show League ");
        String league = blSettingPage.getFirstLeague();
        log("@Step 4: Select a league and click Show");
        blSettingPage.filterResult("",league,"KOT",true);
        log("@Step 5: Click Edit on an event");
        blSettingPage.openBLSettingPopup("JS Kabylie");
        log("@Step 6: Fill full info and click Submit");
        log("Navigate to Soccer > BBT");
        log("Filter with event at step 4");
        log("Validate Event of selected league is displayed correctly on Event table");

        log("INFO: Executed completely");
    }

}
