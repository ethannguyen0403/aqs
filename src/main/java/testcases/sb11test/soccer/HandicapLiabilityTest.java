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
        Assert.assertEquals(handicapLiabilityPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(handicapLiabilityPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(handicapLiabilityPage.ddpSmartType.getOptions(),MatchOddsLiability.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(handicapLiabilityPage.lblPTBets.getText(),"PT-Bets","Failed! PT Bets checkbox is not displayed");
        Assert.assertEquals(handicapLiabilityPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(handicapLiabilityPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        Assert.assertEquals(handicapLiabilityPage.ddpStake.getOptions(),STAKE_LIST,"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertEquals(handicapLiabilityPage.lblShowBetType.getText(),"Show Bet Types","Failed! Show Bet Types button is not displayed");
        Assert.assertEquals(handicapLiabilityPage.lblShowLeagues.getText(), "Show Leagues","Failed! Show Leagues button is not displayed");
        Assert.assertEquals(handicapLiabilityPage.lblShowGroups.getText(),"Show Groups","Failed! Show Groups button is not displayed");
        Assert.assertEquals(handicapLiabilityPage.lblShowEvents.getText(),"Show Events","Failed! Show Events button is not displayed");
        Assert.assertEquals(handicapLiabilityPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(handicapLiabilityPage.tblOrder.getHeaderNameOfRows(), HandicapLiability.TABLE_HEADER,"FAILED! Handicap Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
