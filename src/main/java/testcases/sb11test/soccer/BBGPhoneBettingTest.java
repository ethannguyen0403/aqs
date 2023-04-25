package testcases.sb11test.soccer;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.BBGPhoneBettingPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class BBGPhoneBettingTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    public void BBGPhoneBettingTC_001(){
        log("@title: Validate BBG-Phone Betting page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG-Phone Betting");
        BBGPhoneBettingPage bbgPhoneBettingPage = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("Validate BBG-Phone Betting page is displayed with correctly title");
        Assert.assertTrue(bbgPhoneBettingPage.getTitlePage().contains(BBG_PHONE_BETTING), "Failed! BBG-Phone Betting page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void BBGPhoneBettingTC_002(){
        log("@title: Validate BBG-Phone Betting page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG-Phone Betting");
        BBGPhoneBettingPage bbgPhoneBettingPage = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, From Date, To Date");
        Assert.assertTrue(bbgPhoneBettingPage.ddpCompanyUnit.isDisplayed(),"Failed! Company Unit dropdown is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.ddpReportBy.isDisplayed(),"Failed! Report By dropdown is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.txtFromDate.isDisplayed(),"Failed! From Date datetime picker is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.txtToDate.isDisplayed(),"Failed! To Date datetime picker is not displayed");

        log("Show Bet Types, Show Leagues, Show Win/Lose and Show button");
        Assert.assertTrue(bbgPhoneBettingPage.btnShowBetTypes.isDisplayed(),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.btnShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.btnShowWinLose.isDisplayed(),"Failed! Show Win/Lose button is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(bbgPhoneBettingPage.tblOrder.getHeaderNameOfRows(), BBGPhoneBetting.TABLE_HEADER,"FAILED! BBG-Phone Betting Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
