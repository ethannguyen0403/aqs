package testcases.sb11test.soccer;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.HandicapCornerLiabilityPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class HandicapCornerLiabilityTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2112")
    public void HandicapCornerLiabilityTC_001(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Corner Liability");
        HandicapCornerLiabilityPage handicapCornerLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_CORNER_LIABILITY, HandicapCornerLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(handicapCornerLiabilityPage.getTitlePage().contains(HANDICAP_CORNER_LIABILITY), "Failed! Handicap Corner Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2113")
    public void HandicapCornerLiabilityTC_002(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Liability");
        HandicapCornerLiabilityPage handicapCornerLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_CORNER_LIABILITY, HandicapCornerLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertEquals(handicapCornerLiabilityPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.ddpSmartType.getOptions(),MatchOddsLiability.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.lblPTBets.getText(),"PT-Bets","Failed! PT Bets checkbox is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.ddpStake.getOptions(),STAKE_LIST,"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertEquals(handicapCornerLiabilityPage.lblShowBetType.getText(),"Show Bet Types","Failed! Show Bet Types button is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.lblShowLeagues.getText(), "Show Leagues","Failed! Show Leagues button is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.lblShowGroups.getText(),"Show Groups","Failed! Show Groups button is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.lblShowEvents.getText(),"Show Events","Failed! Show Events button is not displayed");
        Assert.assertEquals(handicapCornerLiabilityPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(handicapCornerLiabilityPage.tblOrder.getHeaderNameOfRows(), HandicapLiability.TABLE_HEADER,"FAILED! Handicap Corner Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
