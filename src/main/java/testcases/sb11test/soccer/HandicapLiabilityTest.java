package testcases.sb11test.soccer;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.HandicapLiabilityPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class HandicapLiabilityTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2109")
    public void HandicapLiabilityTC_001(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Liability");
        HandicapLiabilityPage handicapLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_LIABILITY, HandicapLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(handicapLiabilityPage.getTitlePage().contains(HANDICAP_LIABILITY), "Failed! Handicap Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2110")
    public void HandicapLiabilityTC_002(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Liability");
        HandicapLiabilityPage handicapLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_LIABILITY, HandicapLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertTrue(handicapLiabilityPage.ddpCompanyUnit.isDisplayed(),"Failed! Company Unit dropdown is not displayed");
        Assert.assertTrue(handicapLiabilityPage.ddpSmartType.isDisplayed(),"Failed! Smart Type dropdown is not displayed");
        Assert.assertTrue(handicapLiabilityPage.cbPTBets.isDisplayed(),"Failed! PT Bets checkbox is not displayed");
        Assert.assertTrue(handicapLiabilityPage.txtFromDate.isDisplayed(),"Failed! From Date datetime picker is not displayed");
        Assert.assertTrue(handicapLiabilityPage.txtToDate.isDisplayed(),"Failed! To Date datetime picker is not displayed");
        Assert.assertTrue(handicapLiabilityPage.ddpStake.isDisplayed(),"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertTrue(handicapLiabilityPage.lblShowBetType.isDisplayed(),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(handicapLiabilityPage.lblShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(handicapLiabilityPage.lblShowGroups.isDisplayed(),"Failed! Show Groups button is not displayed");
        Assert.assertTrue(handicapLiabilityPage.lblShowEvents.isDisplayed(),"Failed! Show Events button is not displayed");
        Assert.assertTrue(handicapLiabilityPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(handicapLiabilityPage.tblOrder.getHeaderNameOfRows(), HandicapLiability.TABLE_HEADER,"FAILED! Handicap Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
