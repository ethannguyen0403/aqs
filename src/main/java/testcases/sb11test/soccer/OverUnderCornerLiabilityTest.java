package testcases.sb11test.soccer;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.OverUnderCornerLiabilityPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class OverUnderCornerLiabilityTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2118")
    public void OverUnderCornerLiabilityTC_001(){
        log("@title: Validate Over/Under Corner Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Corner Liability");
        OverUnderCornerLiabilityPage overUnderCornerLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_CORNER_LIABILITY, OverUnderCornerLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(overUnderCornerLiabilityPage.getTitlePage().contains(OVER_UNDER_CORNER_LIABILITY), "Failed! Over/Under Corner Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    @TestRails(id = "2119")
    public void OverUnderCornerLiabilityTC_002(){
        log("@title: Validate Over/Under Corner Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Corner Liability");
        OverUnderCornerLiabilityPage overUnderCornerLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_CORNER_LIABILITY, OverUnderCornerLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertEquals(overUnderCornerLiabilityPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.ddpSmartType.getOptions(),MatchOddsLiability.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.lblPTBets.getText(),"PT-Bets","Failed! PT Bets checkbox is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.ddpStake.getOptions(),STAKE_LIST,"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertEquals(overUnderCornerLiabilityPage.lblShowBetType.getText(),"Show Bet Types","Failed! Show Bet Types button is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.lblShowLeagues.getText(), "Show Leagues","Failed! Show Leagues button is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.lblShowGroups.getText(),"Show Groups","Failed! Show Groups button is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.lblShowEvents.getText(),"Show Events","Failed! Show Events button is not displayed");
        Assert.assertEquals(overUnderCornerLiabilityPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(overUnderCornerLiabilityPage.tblOrder.getHeaderNameOfRows(), OverUnderCornerLiability.TABLE_HEADER,"FAILED! Over/Under Corner Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
