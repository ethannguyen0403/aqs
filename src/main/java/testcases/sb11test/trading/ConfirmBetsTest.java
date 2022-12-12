package testcases.sb11test.trading;

import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.trading.*;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.popup.BetListPopup;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
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
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 2: Navigate to Trading > Confirm Bet");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        try {
            log("@Step 3. Filter 'Sports' = Soccer and input account at the precondition to 'Account Code' field and status = Pending");
            log("@Step 4. Click 'Show' and observe the bet at precondition");
            confirmBetsPage.filter(companyUnit, "", "Pending", sport, "All", "Specific Date", "", "", accountCode);

            log("@Verify: Bet at the precondition being displayed with correct information as same as Bet Entry page");
            confirmBetsPage.verifyOrder(lstOrder.get(0));
        }finally {
            log("@Pos-condition: Deleted the order");
            confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="179")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC179(String accountCode,String accountCurrency){
        log("@title:Validate deleted bets does not show in Bet Entry page");
        log("Precondition: Place a new bet on Bet Entry for Soccer and delete pending bet in confirm bet page");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
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
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);

        log("@Step 2: Navigate to Trading > Bet Entry");
        log("@Step 3. Filter Date and Choose League as the bet at the precondition");
        log("@Step 3. Input the account at the precondition to Account Code field");
        log("@Step 4. Click on the 'Bets' link at 'SPB' column of the event at the precondition and observe");
        betEntryPage= confirmBetsPage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());

        log("@Verify: Bet has been deleted does not show in Bet List - Bet Entry page");
        Assert.assertFalse(betListPopup.isOrderDisplay(order.getBetId()),"Failed! The order is exist in the list after deleting");

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
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 2: Navigate to Trading > Bet Entry");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);

        log("@Step 3. Input account at the precondition to 'Account Code' field and search for the bet at precondition");
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);

        log("@Step 4.  Click on 'X' button of the bet at the precondition and observe");
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);

        log("@Verify: Users can delete pending bet by clicking on 'X' button and it will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(order),"Failed! The order still displayed after deleteing");

        log("INFO: Executed completely");
    }

    @TestRails(id="190")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC190(String accountCode,String accountCurrency){
        log("@title:Validate can delete multiple Confirmed Bets");
        log("@Precondition: Init data and Place 2 new bet on Bet Entry for Soccer then confirm these bets in Confirm Bets Page");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
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
                .event(eventInfo)
                .build();
        Order order1 = new Order.Builder()
                .sport(sport).isNegativeHdp(true).hdpPoint(1.75).price(2.15).requireStake(10.00)
                .oddType("HK").betType("Lay").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        lstOrder.add(order1);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.confirmMultipleBets(lstOrder);

        log("@Step 2:Navigate to Trading > Confirm Bets & Filter status = Confirmed");
        log("@Step 3. Input account at the precondition to 'Account Code' field");
        confirmBetsPage.filter(companyUnit,"","Confirmed",sport,"All","Specific Date","","",accountCode);

        log("@Step 4 Tick on check-boxes of some bets'");
        log("@Step 5. Click on 'Delete Selected' and observe");
        confirmBetsPage.deleteSelectedOrders(lstOrder,false);

        log("@Verify: Users can delete multiple confirmed bets and they will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrdersDisplayInTheTable(lstOrder),"Failed! The order still displayed after delete");

        log("INFO: Executed completely");
    }

    @TestRails(id="308")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC308(String accountCode,String accountCurrency){
        log("@title: Validate can delete Confirmed Bet");
        log("Precondition: Init data and Place a new bet on Bet Entry for Soccer then Confirm the Pending bet "+accountCode);
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(0.25).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 2:Navigate to Trading > Confirm Bets");
        log("@Step 3: Input account code that used to placed bet and selected Status Confirmed");
        confirmBetsPage.filter(companyUnit,"","Confirmed",sport,"All","Specific Date","","",accountCode);

        log("@Step 4.Click on 'X' button of the bet at the precondition and observe");
        confirmBetsPage.deleteOrder(order,false);

        log("@Verify Users can delete confirmed bet by clicking on 'X' button and it will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(order),"Failed! The order still displayed after deleteing");

        log("INFO: Executed completely");
    }

    @TestRails(id="881")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC881(String accountCode,String accountCurrency){
        log("@title: Validate can delete multiple Pending Bets");
        log("Precondition: Account has place above 2 bets on an event in Bet Entry");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
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
                .event(eventInfo)
                .build();
        Order order1 = new Order.Builder()
                .sport(sport).isNegativeHdp(true).hdpPoint(1.75).price(2.15).requireStake(10.00)
                .oddType("HK").betType("Lay").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        lstOrder.add(order1);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);

        log("@Step 2:Navigate to Trading > Confirm Bets");
        log("@Step 3: Filter Pending status and valid account code");
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);

        log("@Step 4 Tick on check-boxes of some bets ");
        log("@Step 5 Click on 'Delete Selected' and observe");
        confirmBetsPage.deleteSelectedOrders(lstOrder,true);

        log("@Verify : Users can delete multiple pending bets and they will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrdersDisplayInTheTable(lstOrder),"Failed! The orders still displayed after delete Pending bet");

        log("INFO: Executed completely");
    }

    @TestRails(id="869")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC869(String accountCode,String accountCurrency){
        log("@title: Validate Pending Cricket Bets display with correct information as Bet Entry page");
        log("Precondition: Test case C863 to place a Cricket bet");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        String companyUnit = "Kastraki Limited";
        String dateAPI = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName("Cricket")
                .leagueName("QA League")
                .eventDate(dateAPI)
                .home("QA 01")
                .away("QA 02")
                .openTime("13:00")
                .eventStatus("Scheduled")
                .isLive(false)
                .isN(false)
                .build();
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName())
                .isNegativeHdp(false)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("Match-HDP")
                .selection(eventInfo.getHome())
                .handicapRuns(9.5)
                .handicapWtks(10)
                .isLive(false)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        eventInfo = welcomePage.createEvent(eventInfo);
        CricketBetEntryPage cricketBetEntryPage =betEntryPage.goToCricket();
        cricketBetEntryPage.showLeague(companyUnit,"",eventInfo.getLeagueName());
        cricketBetEntryPage.placeBet(order,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        try {
            log("@Step 3: Filter 'Sports' = Cricket and input the account at the precondition to 'Account Code' field and the status is Pending");
            confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);

            log("@Verify: Bet at the precondition being displayed with correct information as same as Bet Entry page");
            confirmBetsPage.verifyOrder(lstOrder.get(0));
        }finally {
            log("@Pos-condition: Deleted the order");
            confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="181")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC181(String accountCode,String accountCurrency){
        log("@title:Validate bet info is correctly in Confirm Bet and Bet Setlement after update a bets in Confirm status");
        log("Precondition: Test case C863 to place a Cricket bet");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        String companyUnit = "Kastraki Limited";
        String dateAPI = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        // define event info
        Event eventInfo = new Event.Builder()
                .sportName("Soccer")
                .leagueName("QA League")
                .eventDate(dateAPI)
                .home("QA 01")
                .away("QA 02")
                .openTime("13:00")
                .eventStatus("Scheduled")
                .isLive(false)
                .isN(false)
                .build();
        List<Order> lstOrder = new ArrayList<>();
        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName())
                .isNegativeHdp(false)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("Match-HDP")
                .selection(eventInfo.getHome())
                .handicapRuns(9.50)
                .handicapWtks(10)
                .isLive(false)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        eventInfo = welcomePage.createEvent(eventInfo);
        CricketBetEntryPage cricketBetEntryPage =betEntryPage.goToCricket();
        cricketBetEntryPage.showLeague(companyUnit,"",eventInfo.getLeagueName());
        cricketBetEntryPage.placeBet(order,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.confirmBet(order);
        try {
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Login to SB11 >> Confirm Bets > Filter Status = Confirmed");
        log("@Step 3: Input the account code at the precondition > Show");
        confirmBetsPage.filter(companyUnit, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        log("@Step 4: Find the bet at the precondition > edit any information of the bet (Selection/HDP/Odds/Stake)");
        log("@Step 5: Click on 'Update Bet'");
        confirmBetsPage.updateOrder(order,false);

        log("@Verify 1: Bet info is correctly updated in confirmed bets ");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("@Veirfy 2 : Bets display correctly information in Bet Settlement page");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",accountCode);
        betSettlementPage.verifyOrderInfo(order);
        log("@Pos-condition: Deleted the order");
            betSettlementPage.deleteOrder(order);
        }finally {

        }
        log("INFO: Executed completely");
    }

}
