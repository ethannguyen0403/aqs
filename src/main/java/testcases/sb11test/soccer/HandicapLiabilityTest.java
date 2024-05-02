package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.HandicapLiabilityPage;
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

public class HandicapLiabilityTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2109")
    public void HandicapLiabilityTC_2109(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Liability");
        HandicapLiabilityPage handicapLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_LIABILITY, HandicapLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(handicapLiabilityPage.getTitlePage().contains(HANDICAP_LIABILITY), "Failed! Handicap Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2110")
    public void HandicapLiabilityTC_2110(){
        log("@title: Validate UI on Handicap Liability is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Liability");
        HandicapLiabilityPage handicapLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_LIABILITY, HandicapLiabilityPage.class);
        log(" Validate UI Info display correctly");
        handicapLiabilityPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2111")
    @Parameters({"accountCode","accountCurrency","smartGroup"})
    public void HandicapLiabilityTC_2111(String accountCode, String accountCurrency, String smartGroup){
        log("@title: Validate Handicap bet from Bet Entry is displayed correctly on Handicap Liability report");
        log("Precondition: Having an Handicap bet which have been placed on Bet Entry");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String smartType = "Group";

        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .home(eventInfo.getHome())
                .away(eventInfo.getAway())
                .competitionName(eventInfo.getLeagueName())
                .build();
        lstOrder.add(order);

        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);

        log("Get Bet ID of placed bet");
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        HandicapLiabilityPage handicapLiabilityPage = soccerBetEntryPage.navigatePage(SOCCER,HANDICAP_LIABILITY, HandicapLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        handicapLiabilityPage.filterResult(companyUnit, smartType,false,"All",date,date,"All",true);
        handicapLiabilityPage.filterGroups(smartGroup);
        log("Verify 1: Validate Handicap bet from Bet Entry is displayed correctly on Handicap Liability report");
        handicapLiabilityPage.isOrderExist(lstOrder,smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = handicapLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("INFO: Executed completely");
    }
}
