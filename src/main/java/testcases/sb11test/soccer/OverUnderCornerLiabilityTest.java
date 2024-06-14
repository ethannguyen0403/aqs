package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.HandicapCornerLiabilityPage;
import pages.sb11.soccer.OverUnderCornerLiabilityPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.SoccerBetEntryPage;
import pages.sb11.trading.popup.BetListPopup;
import pages.sb11.trading.popup.SoccerSPBBetSlipPopup;
import testcases.BaseCaseAQS;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class OverUnderCornerLiabilityTest extends BaseCaseAQS {
    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2118")
    public void OverUnderCornerLiabilityTC_2118(){
        log("@title: Validate Over/Under Corner Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Corner Liability");
        OverUnderCornerLiabilityPage overUnderCornerLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_CORNER_LIABILITY, OverUnderCornerLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(overUnderCornerLiabilityPage.getTitlePage().contains(OVER_UNDER_CORNER_LIABILITY), "Failed! Over/Under Corner Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2119")
    public void OverUnderCornerLiabilityTC_2119(){
        log("@title: Validate UI on Over/Under Corner Liability is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Corner Liability");
        OverUnderCornerLiabilityPage overUnderCornerLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_CORNER_LIABILITY, OverUnderCornerLiabilityPage.class);
        log(" Validate UI Info display correctly");
        overUnderCornerLiabilityPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2120")
    @Parameters({"accountCode","accountCurrency","smartGroup"})
    public void OverUnderCornerLiabilityTC_2120(String accountCode, String accountCurrency, String smartGroup){
        log("@title: Validate Over/Under Corner bet from Bet Entry is displayed correctly on Over/Under Corner Liability report");
        log("Precondition: Having an 1x2 bet which have been placed on Bet Entry");
        String marketType = "Over Under - Corners";
        String smartType = "Group";

        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED,date,"All");
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,SOCCER,"");
        Order order = new Order.Builder()
                .sport(SOCCER)
                .hdpPoint(0.00)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType(marketType)
                .handicapRuns(1.75)
                .stage("Full Time")
                .selection("Over")
                .liveHomeScore(0)
                .liveAwayScore(0)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .event(eventInfo)
                .build();
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED,date,eventInfo.getLeagueName());
        SoccerSPBBetSlipPopup soccerSPBBetSlipPopup = soccerBetEntryPage.openSPBBetSlip(accountCode,eventInfo.getHome());
        soccerSPBBetSlipPopup.placeOverUnderCorners(order,false,false,true);

        log("Get Bet ID of placed bet");
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());
        order.setBetId(betListPopup.getBetID(order,marketType));
        betListPopup.close();

        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        OverUnderCornerLiabilityPage overUnderCornerLiabilityPage = soccerBetEntryPage.navigatePage(SOCCER,OVER_UNDER_CORNER_LIABILITY, OverUnderCornerLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        overUnderCornerLiabilityPage.filterResult(KASTRAKI_LIMITED, smartType,false,"All",date,date,"All",true);
        overUnderCornerLiabilityPage.filterGroups(smartGroup);
        log("Validate 1x2 bet from Bet Entry is displayed correctly on 1x2 Liability report");
        overUnderCornerLiabilityPage.isOrderExist(order,smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ order.getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = overUnderCornerLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(order,true);
        log("INFO: Executed completely");
    }
}
