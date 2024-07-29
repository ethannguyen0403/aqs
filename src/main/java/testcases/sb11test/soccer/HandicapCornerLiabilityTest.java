package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.HandicapCornerLiabilityPage;
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

public class HandicapCornerLiabilityTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2112")
    public void HandicapCornerLiabilityTC_2112(){
        log("@title: Validate Handicap Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Corner Liability");
        HandicapCornerLiabilityPage handicapCornerLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_CORNER_LIABILITY, HandicapCornerLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(handicapCornerLiabilityPage.getTitlePage().contains(HANDICAP_CORNER_LIABILITY), "Failed! Handicap Corner Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan4.0"})
    @TestRails(id = "2113")
    public void HandicapCornerLiabilityTC_2113(){
        log("@title: Validate UI on Handicap Corner Liability is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Liability");
        HandicapCornerLiabilityPage handicapCornerLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_CORNER_LIABILITY, HandicapCornerLiabilityPage.class);
        log(" Validate UI Info display correctly");
        handicapCornerLiabilityPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan4.0"})
    @TestRails(id = "2114")
    @Parameters({"accountCode","smartGroup"})
    public void HandicapCornerLiabilityTC_2114(String accountCode, String smartGroup){
        log("@title: Validate Handicap Corner bet from Bet Entry is displayed correctly on Handicap Corner Liability report");
        log("Precondition: Having an Handicap Corner bet which have been placed on Bet Entry");
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,true,accountCode,"Corners","HDP","Home","FullTime",2.15,0.00,"HK",
                15.50,"BACK",false,"");
        lstOrder.get(0).setHome(lstOrder.get(0).getEvent().getHome());
        lstOrder.get(0).setAway(lstOrder.get(0).getEvent().getAway());
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Handicap Corner Liability");
        HandicapCornerLiabilityPage handicapCornerLiabilityPage = welcomePage.navigatePage(SOCCER,HANDICAP_CORNER_LIABILITY, HandicapCornerLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        handicapCornerLiabilityPage.filterResult(KASTRAKI_LIMITED, "Group",false,"All",date,"","All",true);
        handicapCornerLiabilityPage.filterGroups(smartGroup);
        log("Validate Handicap Corner bet from Bet Entry is displayed correctly on Handicap Corner Liability report");
        handicapCornerLiabilityPage.isOrderExist(lstOrder.get(0),smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = handicapCornerLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("INFO: Executed completely");
    }
}
