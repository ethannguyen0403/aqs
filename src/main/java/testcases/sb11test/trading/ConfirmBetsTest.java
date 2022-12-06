package testcases.sb11test.trading;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.*;
import pages.sb11.trading.popup.BetListPopup;
import testcases.BaseCaseAQS;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class ConfirmBetsTest extends BaseCaseAQS {

    @TestRails(id="176")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC176(String accountCode,String accountCurrency){
        log("@title: Validate Pending Soccer Bets display correct information as Bet Entry page");
        log("Precondition: Init data and Place a new bet on Bet Entry for Soccer to have a pending bet");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);

        log("@Step 2: Navigate to Trading > Bet Entry");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);

        log("@Step 3. Filter 'Sports' = Soccer and input account at the precondition to 'Account Code' field and status = Pending");
        log("@Step 4. Click 'Show' and observe the bet at precondition");
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);

        log("@Verify: Bet at the precondition being displayed with correct information as same as Bet Entry page");
        confirmBetsPage.verifyOrder(order);
        log("INFO: Executed completely");
    }

    @TestRails(id="177")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC177(String accountCode,String accountCurrency){
        log("@title: Validate Pending Soccer Bets display correct information as Bet Entry page");
        log("Precondition: Init data and Place a new bet on Bet Entry for Soccer to have a pending bet");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);

        log("@Step 2: Navigate to Trading > Bet Entry");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);

        log("@Step 3. Filter 'Sports' = Soccer and input account at the precondition to 'Account Code' field and status = Pending");
        log("@Step 4. Click 'Show' and observe the bet at precondition");
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);

        log("@Verify: Bet at the precondition being displayed with correct information as same as Bet Entry page");
        confirmBetsPage.verifyOrder(order);
        log("INFO: Executed completely");
    }

    @TestRails(id="178")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC178(String accountCode,String accountCurrency){
        log("@title: Validate can delete Pending Bet");
        log("Precondition: Init data and Place a new bet on Bet Entry for Soccer to have a pending bet");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);

        log("@Step 2: Navigate to Trading > Bet Entry");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);

        log("@Step 3. Input account at the precondition to 'Account Code' field and search for the bet at precondition");
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);

        log("@Step 4.  Click on 'X' button of the bet at the precondition and observe");
        confirmBetsPage.deleteOrder(lstOrder.get(0).getBetId());

        log("@Verify: Users can delete pending bet by clicking on 'X' button and it will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(order.getOrderId()),"Failed! The order still displayed after deleteing");

        log("INFO: Executed completely");
    }


    @TestRails(id="308")
    @Test(groups = {"smoke1"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC308(String accountCode,String accountCurrency){
        log("@title: Validate can delete Confirmed Bet");
        log("Precondition: Init data andPlace a new bet on Bet Entry for Soccer with "+accountCode);
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);

        log("@Step 2: Navigate to Trading > Bet Entry");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);

        log("@Step 3. Input account at the precondition to 'Account Code' field");
        log("@Step 4. Select the bet at precondition and click on 'Pending Bet'");
        log("@Step 5. Filter Status = Pending > Click show");
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.confirmBet(order.getOrderId());
        confirmBetsPage.filter(companyUnit,"","Confirm",sport,"All","Specific Date","","",accountCode);

        log("@Step 6. Click on 'X' button of the bet at the precondition and observe");
        confirmBetsPage.deleteOrder(order.getOrderId());

        log("@Verify Users can delete confirmed bet by clicking on 'X' button and it will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(order.getOrderId()),"Failed! The order still displayed after deleteing");
        log("INFO: Executed completely");
    }

    @TestRails(id="863")
    @Test(groups = {"smoke1"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC863(String accountCode,String accountCurrency){
        log("@title: Validate user can place Cricket bets successfully with correct bets information in Bet List");
        log("Precondition: User has permission to access Bet Entry page");
        log("Having a valid account that can place bets (e.g. )" +accountCode);
        log("Having a Cricket event which has been created on Event Schedule > Cricket");
        String sport="Cricket";
        String companyUnit = "Kastraki Limited";
        String marketType = "Match-HDP";
        String leagueName = "QA League";

        log("@Step 1: Login to SB11 site");

        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));

        log("@Step prepare data: Add event for QA League in today local time and can filter in today in Trading>Bet Entry");
        Event eventInfo =  welcomePage.createCricketEvent(dateAPI,dateAPI,sport,leagueName);

        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Cricket' > select any League > click Show");
        CricketBetEntryPage cricketBetEntryPage =betEntryPage.goToCricket();

        log("@Step Precondition: Get the first Event of Frist League of Today Cricket");
        cricketBetEntryPage.showLeague(companyUnit,"",leagueName);

        log("@Step Precondition: Define order to place bet");
        Order order = new Order.Builder()
                .sport(sport)
                .isNegativeHdp(false)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType(marketType)
                .selection(eventInfo.getHome())
                .handicapRuns(9.5)
                .handicapWtks(10)
                .isLive(false)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .build();

       log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet without select \"option copy bet to SPBPS7same odds\" and \"copy bet to SPBPS7minus odds\"");
        cricketBetEntryPage.placeBet(order,true);

        log("@Verify 1: User can place Cricet bets successfully with message 'The bet was placed successfully'");
        Assert.assertTrue(cricketBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+cricketBetEntryPage.getSuccessMessage());

        log("@Step 7: Click 'Bets' at CPB column of event at step 5 > observe");
        BetListPopup betListPopup = cricketBetEntryPage.openBetList(eventInfo.getHome());

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        order = betListPopup.verifyOrderInfoDisplay(order,CRICKET_MARKET_TYPE_BET_LIST.get(marketType),"");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ order.getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.deleteOrder(order.getBetId());

        log("INFO: Executed completely");
    }
}
