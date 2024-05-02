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
    public void OverUnderLiabilityTC_2115(){
        log("@title: Validate Over/Under Liability page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        log("Validate Handicap Liability page is displayed with correctly title");
        Assert.assertTrue(overUnderLiabilityPage.getTitlePage().contains(OVER_UNDER_LIABILITY), "Failed! Over/Under Liability page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2116")
    public void OverUnderLiabilityTC_2116(){
        log("@title: Validate UI on Over/Under Liability is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        log(" Validate UI Info display correctly");
        overUnderLiabilityPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2117")
    @Parameters({"accountCode","accountCurrency","smartGroup"})
    public void OverUnderLiabilityTC_2117(String accountCode, String smartGroup){
        log("@title: Validate Over/Under bet from Bet Entry is displayed correctly on Over/Under Liability report");
        log("Precondition: Having an Over/Under bet which have been placed on Bet Entry");
        String sport="Soccer";
        String smartType = "Group";

        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        Event event = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,"");
        event.setEventDate(dateAPI);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("OU").selection("Over").stage("FullTime")
                .odds(1).handicap(0.5).oddType("HK").requireStake(5.5).betType("BACK").build();
        BetEntrytUtils.placeBetAPI(order);
        lstOrder.add(order);
        log("Get Bet ID of placed bet");
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        overUnderLiabilityPage.filterResult(KASTRAKI_LIMITED, smartType,false,"All",date,date,"All",true);
        overUnderLiabilityPage.filterGroups(smartGroup);
        log("Validate Over/Under bet from Bet Entry is displayed correctly on Over/Under Liability report");
        overUnderLiabilityPage.isOrderExist(lstOrder,smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = overUnderLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("INFO: Executed completely");
    }
}
