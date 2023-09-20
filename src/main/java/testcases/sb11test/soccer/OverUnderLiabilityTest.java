package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.HandicapLiabilityPage;
import pages.sb11.soccer.OverUnderLiabilityPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.SoccerBetEntryPage;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.List;

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

    @Test(groups = {"regression"})
    @TestRails(id = "2116")
    public void OverUnderLiabilityTC_002(){
        log("@title: Validate UI on Over/Under Liability is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Smart Type, Show Only PT-Bets,Live/NonLive, From Date, To Date, Stake");
        Assert.assertEquals(overUnderLiabilityPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST_ALL,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.ddpSmartType.getOptions(),MatchOddsLiability.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblPTBets.getText(),"PT-Bets","Failed! PT Bets checkbox is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.ddpStake.getOptions(),STAKE_LIST,"Failed! Stake dropdown is not displayed");
        log("Show Bet Types, Show Leagues, Show Groups, Show Events and Show button");
        Assert.assertEquals(overUnderLiabilityPage.lblShowBetType.getText(),"Show Bet Types\nAll","Failed! Show Bet Types button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblShowLeagues.getText(), "Show Leagues\nAll","Failed! Show Leagues button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblShowGroups.getText(),"Show Groups\nAll","Failed! Show Groups button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.lblShowEvents.getText(),"Show Events\nAll","Failed! Show Events button is not displayed");
        Assert.assertEquals(overUnderLiabilityPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(overUnderLiabilityPage.tblOrder.getHeaderNameOfRows(), OverUnderLiability.TABLE_HEADER,"FAILED! Over/Under Liability Bets table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2117")
    @Parameters({"accountCode","accountCurrency","smartGroup"})
    public void OverUnderLiabilityTC_003(String accountCode, String accountCurrency, String smartGroup){
        log("@title: Validate Over/Under bet from Bet Entry is displayed correctly on Over/Under Liability report");
        log("Precondition: Having an Over/Under bet which have been placed on Bet Entry");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String smartType = "Group";

        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("OVER")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .home(eventInfo.getHome())
                .away(eventInfo.getAway())
                .competitionName(eventInfo.getLeagueName())
                .build();
        lstOrder.add(order);

        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"OVER",lstOrder,false,false,true);

        log("Get Bet ID of placed bet");
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = soccerBetEntryPage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        overUnderLiabilityPage.filterResult(companyUnit, smartType,false,"All",date,date,"All",true);
        overUnderLiabilityPage.filterGroups(smartGroup);
        log("Validate Over/Under bet from Bet Entry is displayed correctly on Over/Under Liability report");
        overUnderLiabilityPage.isOrderExist(lstOrder,smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = overUnderLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("INFO: Executed completely");
    }
}
