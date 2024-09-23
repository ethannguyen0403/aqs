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

import java.util.List;

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

    @Test(groups = {"regression","ethan6.0"})
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

    @Test(groups = {"regression","ethan6.0"})
    @TestRails(id = "2120")
    @Parameters({"accountCode","smartGroup"})
    public void OverUnderCornerLiabilityTC_2120(String accountCode, String smartGroup){
        log("@title: Validate Over/Under Corner bet from Bet Entry is displayed correctly on Over/Under Corner Liability report");
        log("Precondition: Having an 1x2 bet which have been placed on Bet Entry");
        String smartType = "Group";
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date,true,accountCode,"Corners","OU","Over","FullTime",2.15,0.00,"HK",15.50,
                "BACK",false,"");
        lstOrder.get(0).setHome(lstOrder.get(0).getEvent().getHome());
        lstOrder.get(0).setAway(lstOrder.get(0).getEvent().getAway());
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        OverUnderCornerLiabilityPage overUnderCornerLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_CORNER_LIABILITY, OverUnderCornerLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        overUnderCornerLiabilityPage.filterResult(KASTRAKI_LIMITED, smartType,false,"All",date,"","All",true);
        overUnderCornerLiabilityPage.filterGroups(smartGroup,false);
        overUnderCornerLiabilityPage.showLeagues(true,true,"");
        log("Validate 1x2 bet from Bet Entry is displayed correctly on 1x2 Liability report");
        overUnderCornerLiabilityPage.isOrderExist(lstOrder.get(0),smartGroup);
        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = overUnderCornerLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("INFO: Executed completely");
    }
}
