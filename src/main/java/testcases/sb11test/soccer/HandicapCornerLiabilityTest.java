package testcases.sb11test.soccer;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.HandicapCornerLiabilityPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class HandicapCornerLiabilityTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    public void HandicapCornerLiabilityTC_001(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Corner Liability");
        HandicapCornerLiabilityPage handicapCornerLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_CORNER_LIABILITY, HandicapCornerLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(handicapCornerLiabilityPage.getTitlePage().contains(HANDICAP_CORNER_LIABILITY), "Failed! Handicap Corner Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    public void HandicapCornerLiabilityTC_002(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Liability");
        HandicapCornerLiabilityPage handicapCornerLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_CORNER_LIABILITY, HandicapCornerLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertTrue(handicapCornerLiabilityPage.ddpCompanyUnit.isDisplayed(),"Failed! Company Unit dropdown is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.ddpSmartType.isDisplayed(),"Failed! Smart Type dropdown is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.cbPTBets.isDisplayed(),"Failed! PT Bets checkbox is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.txtFromDate.isDisplayed(),"Failed! From Date datetime picker is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.txtToDate.isDisplayed(),"Failed! To Date datetime picker is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.ddpStake.isDisplayed(),"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertTrue(handicapCornerLiabilityPage.lblShowBetType.isDisplayed(),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.lblShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.lblShowGroups.isDisplayed(),"Failed! Show Groups button is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.lblShowEvents.isDisplayed(),"Failed! Show Events button is not displayed");
        Assert.assertTrue(handicapCornerLiabilityPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(handicapCornerLiabilityPage.tblOrder.getHeaderNameOfRows(), HandicapLiability.TABLE_HEADER,"FAILED! Handicap Corner Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
