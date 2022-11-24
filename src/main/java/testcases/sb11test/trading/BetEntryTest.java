package testcases.sb11test.trading;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.ManualBetBetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;
import pages.sb11.trading.popup.BetSlipPopup;
import pages.sb11.trading.popup.SoccerBetListPopup;
import testcases.BaseCaseAQS;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class BetEntryTest extends BaseCaseAQS {

    @Test(groups = {"smoke1"})
    public void Bet_Entry_TC862(){
        log("@title: Validate users can place Mixed Sports bets successfully");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Mixed Sports' > Input account at precondition on 'Account Code' field > click glass icon");
        log("@Step 4: Inputting Odds, Stake and Win/Loss or Comm Amount > click Place Bet");
        log("@Step 5: Click Yes on Confirm dialog > observe");
        betEntryPage.goToMixedSports();

        ManualBetBetEntryPage manualBetBetEntryPage = new ManualBetBetEntryPage();
        String messageSuccess = manualBetBetEntryPage.placeManualBet("Kastraki Limited ","22/11/2022", "JO-TEL-AC-01", "Soccer",
                "Manual Bet Testing", null,null,"1.25","10","1",true);

        log("Validate user can place Mixed Sports bets successfully with message 'Placed successfully!");
        Assert.assertTrue(messageSuccess.contains(SBPConstants.BetEntryPage.MESSAGE_SUCCESS_MANUAL_BET), "FAILED! Incorrect place bet successful");
        log("INFO: Executed completely");
    }

    @TestRails(id="189")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC189(String accountCode,String accountCurrency){
        log("@title: Validate users can place single Soccer bets successfully without copy bet to SPBPS7 ");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition:Having a valid account that can place bets (e.g. QATE01000100)");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Get the first Event of Frist League of Today Soccer");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);

        log("@Step Precondition: Define order to place bet");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .isNegativeHdp(false)
                .hdpPoint(1.75)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .liveHomeScore(0)
                .liveAwayScore(0)
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .build();
        lstOrder.add(order);

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet without select \"option copy bet to SPBPS7same odds\" and \"copy bet to SPBPS7minus odds\"");
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
       // String placeTime = String.format(DateUtils.getDate(0,"dd/MM hh:mm","UTC+07:00"));
        //String successMessage = soccerBetEntryPage.getSuccessMessage();
        log("@Verify 1: User can place Soccer bets successfully with message 'The bet was placed successfully'");
        Assert.assertTrue(soccerBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+soccerBetEntryPage.getSuccessMessage());

        log("@Step 7: Click 'Bets' at SPB column of event at step 5 > observe");
        SoccerBetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());
        log("@Verify 2: Bets information is displayed correctly in Bet List");
        lstOrder = betListPopup.verifyListOrderInfoDisplay(lstOrder,"Handicap","");
        lstOrder = GetSoccerEventUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder,eventInfo.getEventId());
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getOrderId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending","Soccer","All","Specific Date","","",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0).getOrderId());

        log("INFO: Executed completely");
    }
}
