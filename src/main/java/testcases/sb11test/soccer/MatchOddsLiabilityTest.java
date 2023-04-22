package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.MatchOddsLiabilityPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;
import pages.sb11.trading.popup.SoccerSPBBetSlipPopup;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class MatchOddsLiabilityTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    public void MatchOddsLiabilityTC_001(){
        log("@title: Validate 1x2 Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log("Validate 1x2 Liability page is displayed with correctly title");
        Assert.assertTrue(matchOddsLiabilityPage.getTitlePage().contains(MATCH_ODDS_LIABILITY), "Failed! 1x2 Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void MatchOddsLiabilityTC_002(){
        log("@title: Validate 1x2 Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertTrue(matchOddsLiabilityPage.ddpCompanyUnit.isDisplayed(),"Failed! Company Unit dropdown is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.ddpSmartType.isDisplayed(),"Failed! Smart Type dropdown is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.cbPTBets.isDisplayed(),"Failed! PT Bets checkbox is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.txtFromDate.isDisplayed(),"Failed! From Date datetime picker is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.txtToDate.isDisplayed(),"Failed! To Date datetime picker is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.ddpStake.isDisplayed(),"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertTrue(matchOddsLiabilityPage.lblShowBetType.isDisplayed(),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.lblShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.lblShowGroups.isDisplayed(),"Failed! Show Groups button is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.lblShowEvents.isDisplayed(),"Failed! Show Events button is not displayed");
        Assert.assertTrue(matchOddsLiabilityPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(matchOddsLiabilityPage.tblOrder.getHeaderNameOfRows(), MatchOddsLiability.TABLE_HEADER,"FAILED! 1x2 Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    @Parameters({"accountCode","accountCurrency"})
    public void MatchOddsLiabilityTC_003(String accountCode, String accountCurrency){
        log("@title: Validate 1x2 Liability page is displayed when navigate");
        log("Precondition: User has permission to access Bet Settlement page\n" +
                "Having an account with Confirmed bet settle Win/Lose and configuring Account Percentage");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String marketType = "1x2";

        String date = String.format(DateUtils.getDate(1,"d/MM/yyyy","UTC+7:00"));
        String dateAPI = String.format(DateUtils.getDate(1,"yyyy-MM-dd","UTC+7:00"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        Order order = new Order.Builder()
                .sport(sport)
                .hdpPoint(0.00)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType(marketType)
                .stage("Full Time")
                .selection("Home")
                .liveHomeScore(0)
                .liveAwayScore(0)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .event(eventInfo)
                .build();
        SoccerSPBBetSlipPopup soccerSPBBetSlipPopup = soccerBetEntryPage.openSPBBetSlip(accountCode,eventInfo.getHome());
        soccerSPBBetSlipPopup.placeMoreBet(order,false,false,false);

        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");

        log("@Step 4: Click Show");
        
        log("Validate 1x2 bet from Bet Entry is displayed correctly on 1x2 Liability report");

        log("INFO: Executed completely");
    }
}

