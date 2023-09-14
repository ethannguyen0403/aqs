package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.MatchOddsLiabilityPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.SoccerBetEntryPage;
import pages.sb11.trading.popup.BetListPopup;
import pages.sb11.trading.popup.SoccerSPBBetSlipPopup;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class MatchOddsLiabilityTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2106")
    public void MatchOddsLiabilityTC_2106(){
        log("@title: Validate 1x2 Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log("Validate 1x2 Liability page is displayed with correctly title");
        Assert.assertTrue(matchOddsLiabilityPage.getTitlePage().contains(MATCH_ODDS_LIABILITY), "Failed! 1x2 Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2107")
    public void MatchOddsLiabilityTC_2107(){
        log("@title: Validate 1x2 Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertEquals(matchOddsLiabilityPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST_ALL,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.ddpSmartType.getOptions(),MatchOddsLiability.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.lblPTBets.getText(),"PT-Bets","Failed! PT Bets checkbox is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.ddpStake.getOptions(),STAKE_LIST,"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertEquals(matchOddsLiabilityPage.lblShowBetType.getText(),"Show Bet Types\nAll","Failed! Show Bet Types button is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.lblShowLeagues.getText(), "Show Leagues\nAll","Failed! Show Leagues button is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.lblShowGroups.getText(),"Show Groups\nAll","Failed! Show Groups button is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.lblShowEvents.getText(),"Show Events\nAll","Failed! Show Events button is not displayed");
        Assert.assertEquals(matchOddsLiabilityPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(matchOddsLiabilityPage.tblOrder.getHeaderNameOfRows(), MatchOddsLiability.TABLE_HEADER,"FAILED! 1x2 Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2108")
    @Parameters({"accountCode","accountCurrency","smartGroup"})
    public void MatchOddsLiabilityTC_2108(String accountCode, String accountCurrency, String smartGroup){
        log("@title: Validate 1x2 bet from Bet Entry is displayed correctly on 1x2 Liability report");
        log("Precondition: Having an 1x2 bet which have been placed on Bet Entry");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String marketType = "1x2";
        String smartType = "Group";

        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
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
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());
        SoccerSPBBetSlipPopup soccerSPBBetSlipPopup = soccerBetEntryPage.openSPBBetSlip(accountCode,eventInfo.getHome());
        soccerSPBBetSlipPopup.placeMoreBet(order,false,false,true);

        log("Get Bet ID of placed bet");
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());
        order.setBetId(betListPopup.getBetID(order,marketType));
        betListPopup.close();

        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        matchOddsLiabilityPage.filterResult(companyUnit, sport, smartType,false,"All",date,date,"All",true);
        matchOddsLiabilityPage.filterGroups(smartGroup);
        log("Validate 1x2 bet from Bet Entry is displayed correctly on 1x2 Liability report");
        matchOddsLiabilityPage.isOrderExist(order,smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ order.getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = matchOddsLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(order,true);
        log("INFO: Executed completely");
    }
}

