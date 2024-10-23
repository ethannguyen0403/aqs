package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.OverUnderLiabilityPage;
import pages.sb11.trading.ConfirmBetsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

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

    @Test(groups = {"regression","ethan7.0"})
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

    @Test(groups = {"regression","ethan4.0"})
    @TestRails(id = "2117")
    @Parameters({"accountCode","smartGroup"})
    public void OverUnderLiabilityTC_2117(String accountCode, String smartGroup){
        log("@title: Validate Over/Under bet from Bet Entry is displayed correctly on Over/Under Liability report");
        log("Precondition: Having an Over/Under bet which have been placed on Bet Entry");
        String smartType = "Group";
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,true,accountCode,"Goals","OU","Over","FullTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Over/Under Liability");
        OverUnderLiabilityPage overUnderLiabilityPage = welcomePage.navigatePage(SOCCER,OVER_UNDER_LIABILITY, OverUnderLiabilityPage.class);
        overUnderLiabilityPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        overUnderLiabilityPage.filterResult(KASTRAKI_LIMITED, smartType,false,"All",date,"","All",true);
        overUnderLiabilityPage.waitSpinnerDisappeared();
        overUnderLiabilityPage.filterLeague("",true);
        overUnderLiabilityPage.filterGroups(smartGroup);
        log("Validate Over/Under bet from Bet Entry is displayed correctly on Over/Under Liability report");
        overUnderLiabilityPage.isOrderExist(lstOrder,smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = overUnderLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("INFO: Executed completely");
    }
}
