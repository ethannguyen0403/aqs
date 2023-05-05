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
        Assert.assertTrue(overUnderLiabilityPage.ddpCompanyUnit.isDisplayed(),"Failed! Company Unit dropdown is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.ddpSmartType.isDisplayed(),"Failed! Smart Type dropdown is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.cbPTBets.isDisplayed(),"Failed! PT Bets checkbox is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.txtFromDate.isDisplayed(),"Failed! From Date datetime picker is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.txtToDate.isDisplayed(),"Failed! To Date datetime picker is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.ddpStake.isDisplayed(),"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertTrue(overUnderLiabilityPage.lblShowBetType.isDisplayed(),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.lblShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.lblShowGroups.isDisplayed(),"Failed! Show Groups button is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.lblShowEvents.isDisplayed(),"Failed! Show Events button is not displayed");
        Assert.assertTrue(overUnderLiabilityPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(overUnderLiabilityPage.tblOrder.getHeaderNameOfRows(), OverUnderLiability.TABLE_HEADER,"FAILED! Over/Under Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
}
