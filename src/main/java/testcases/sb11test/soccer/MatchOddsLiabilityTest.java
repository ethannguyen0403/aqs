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

    @Test(groups = {"regression","ethan3.0"})
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

    @Test(groups = {"regression","ethan5.0"})
    @TestRails(id = "2107")
    public void MatchOddsLiabilityTC_2107(){
        log("@title: Validate UI on 1x2 Liability is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log(" Validate UI Info display correctly");
        matchOddsLiabilityPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan5.0"})
    @TestRails(id = "2108")
    @Parameters({"accountCode","smartGroup"})
    public void MatchOddsLiabilityTC_2108(String accountCode, String smartGroup){
        log("@title: Validate 1x2 bet from Bet Entry is displayed correctly on 1x2 Liability report");
        log("Precondition: Having an 1x2 bet which have been placed on Bet Entry");
        String smartType = "Group";
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("Get Bet ID of placed bet");
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date,true,accountCode,"Goals","1x2","Home","FullTime",2.15,0.00,"HK",15.50,
                "BACK",false,"");
        lstOrder.get(0).setHome(lstOrder.get(0).getEvent().getHome());
        lstOrder.get(0).setAway(lstOrder.get(0).getEvent().getAway());
        //Wait for Bet update on 1x2 Liability
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > 1x2 Liability");
        MatchOddsLiabilityPage matchOddsLiabilityPage = welcomePage.navigatePage(SOCCER,MATCH_ODDS_LIABILITY, MatchOddsLiabilityPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition ");
        log("@Step 4: Click Show");
        matchOddsLiabilityPage.filterResult(KASTRAKI_LIMITED, SOCCER, smartType,false,"All",date,date,"All",true);
        matchOddsLiabilityPage.filterGroups(smartGroup);
        matchOddsLiabilityPage.filterLeague(lstOrder.get(0).getEvent().getLeagueName());
        log("Validate 1x2 bet from Bet Entry is displayed correctly on 1x2 Liability report");
        matchOddsLiabilityPage.isOrderExist(lstOrder.get(0),smartGroup);

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = matchOddsLiabilityPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("INFO: Executed completely");
    }
}

