package testcases.sb11test.soccer;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.OverUnderLiabilityPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class OverUnderLiabilityTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2115")
    public void OverUnderLiabilityTC_001(){
        log("@title: Validate Over/Under Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(overUnderLiabilityPage.getTitlePage().contains(OVER_UNDER_LIABILITY), "Failed! Over/Under Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    @TestRails(id = "2116")
    public void OverUnderLiabilityTC_002(){
        log("@title: Validate Over/Under Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertEquals(overUnderLiabilityPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.ddpSmartType.getOptions(),MatchOddsLiability.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblPTBets.getText(),"PT-Bets","Failed! PT Bets checkbox is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.ddpStake.getOptions(),STAKE_LIST,"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertEquals(overUnderLiabilityPage.lblShowBetType.getText(),"Show Bet Types","Failed! Show Bet Types button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblShowLeagues.getText(), "Show Leagues","Failed! Show Leagues button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblShowGroups.getText(),"Show Groups","Failed! Show Groups button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblShowEvents.getText(),"Show Events","Failed! Show Events button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(overUnderLiabilityPage.tblOrder.getHeaderNameOfRows(), OverUnderLiability.TABLE_HEADER,"FAILED! Over/Under Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
