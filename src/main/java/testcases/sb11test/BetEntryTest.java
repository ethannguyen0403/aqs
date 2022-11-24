package testcases.sb11test;

import com.paltech.utils.DateUtils;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;
import pages.sb11.trading.popup.BetSlipPopup;
import pages.sb11.trading.popup.SoccerBetListPopup;
import testcases.BaseCaseAQS;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class BetEntryTest extends BaseCaseAQS {

    @Test(groups = {"smoke1"})
    public void BetEntry_TC_001(){
        log("@title: Validate users can place Soccer bets successfully with correct bets information in Bet List");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        betEntryPage.openBetSlipSoccer("JO-TEL-AC-01","HOME-FT");
        betEntryPage.placeSoccerBet("1.25","1","20",false,false,true);

        log("Verify that Page title is correctly display");

        log("INFO: Executed completely");
    }
    @Test(groups = {"smoke1"})
    public void BetEntry_TC189(){
        log("@title: Validate users can place single Soccer bets successfully without copy bet to SPBPS7 ");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition:Having a valid account that can place bets (e.g. QATE01000100)");
        List<Order> lstOrder = new ArrayList<>();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(-4,"d/MM/yyyy","GMT -4"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague("Kastraki Limited",date,"All");

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet without select \"option copy bet to SPBPS7same odds\" and \"copy bet to SPBPS7minus odds\"");
        soccerBetEntryPage.placeBet("accountCode","eventName",true,"Home",lstOrder,false,false,true);

        log("@Verify 1: User can place Soccer bets successfully with message 'The bet was placed successfully'");
        String placeTime = String.format(DateUtils.getDate(0,"dd/MM hh:mm","GMT +7"));
        String successMessage = soccerBetEntryPage.getSuccessMessage();

        log("@Step 7: Click 'Bets' at SPB column of event at step 5 > observe");
        SoccerBetListPopup betListPopup = soccerBetEntryPage.openBetList("eventName");

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        betListPopup.verifyOrderInfoDisplay(lstOrder,"Handicap");



        log("INFO: Executed completely");
    }
}
