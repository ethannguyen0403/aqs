package testcases.sb11test.trading;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.trading.*;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.popup.BetListPopup;
import pages.sb11.trading.popup.SoccerSPBBetSlipPopup;
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
        String dateAPI =  String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        // define event info
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName()).isNegativeHdp(false).hdpPoint(0.25).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.showLeague(companyUnit,"",eventInfo.getLeagueName());
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
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
    public void Confirm_Bets_TC881(String accountCode,String accountCurrency){
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
    public void Confirm_Bets_TC869(String accountCode,String accountCurrency){
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
    public void Confirm_Bets_TC181(String accountCode,String accountCurrency){
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
        order =  BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder).get(0);
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Login to SB11 >> Confirm Bets > Filter Status = Confirmed");
        log("@Step 3: Input the account code at the precondition > Show");
        confirmBetsPage.filter(companyUnit, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);

        log("@Step 4: Find the bet at the precondition > edit any information of the bet (Selection/HDP/Odds/Stake)");
        log("@Step 5: Click on 'Update Bet'");
        order.setRequireStake(17.50);
        confirmBetsPage.updateOrder(order,false);

        log("@Verify 1: Bet info is correctly updated in confirmed bets ");
        confirmBetsPage.verifyOrder(order);
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",accountCode);

        log("@Veirfy 2 : Bets display correctly information in Bet Settlement page");
        betSettlementPage.verifyOrderInfo(order);

        log("@Pos-condition: Deleted the order");
        betSettlementPage.deleteOrder(order);

        log("INFO: Executed completely");
    }

    @TestRails(id="1000")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC1000(String accountCode,String accountCurrency){
        log("@title: Validate deleted bets does not show in Bet Settlement page");
        log("Precondition:There is a Confirmed bet and be deleted in Confirm bet page");
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
        confirmBetsPage.filter(companyUnit,"","Confirmed",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.deleteOrder(order,false);

        log("@Step 1: Login to SB11 >> Bet Settlement page");
        log("@Step 2: Filter Confirm status with the according account code");
        log("@Step 3: Click 'Search' and observe");
        BetSettlementPage betSettlementPage = confirmBetsPage.navigatePage(TRADING,BET_SETTLEMENT,BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",order.getAccountCode());

        log("@Veirfy 1 : Confirmed bet has been deleted does not show in Bet Settlement page");
        Assert.assertFalse(betSettlementPage.isOrderDisplayInTheTable(order),"FAILED! The order is displayed when it is deleted in Confirm Bets page");

        log("INFO: Executed completely");
    }

    @TestRails(id="1001")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC1001(String accountCode,String accountCurrency){
        log("@title: Validate can update Pending Bets");
        log("Precondition:There is a Pending");
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
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.05).requireStake(9.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getAway())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 1: Login to SB11 >> Confirm Bets");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);

        log("@Step 2: Filter status Pending and the according account code then click Show");
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);

        log("@Step 3: Find the bet at the precondition > edit any information of the bet (Selection/HDP/Odds/Stake)");
        log("@Step 4: Click on 'Update Bet')");
        order.setSelection(eventInfo.getHome());
        order.setHdpPoint(0.50);
        order.setBetType("Lay");
        confirmBetsPage.updateOrder(lstOrder.get(0),true);

        log("@Step 5: Re-filter the bet has been updated and observe");
        log("@Verify 1 : Users can update Pending Bets and the updated information is saved correctly");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("INFO: Executed completely");
    }

    @TestRails(id="942")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_TC942(String accountCode,String accountCurrency){
        log("@title:Validate updated bets reflect correctly in the bet list of Bet Settlement page");
        log("Precondition: Using the updated bets in the test case C182");
        log("Note: Tobe able settled order after place bet > commfirmed and settled => we should place in the event in the past");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String dateAPI =  String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT +7"));
        String date =  String.format(DateUtils.getDate(-2,"dd/MM/yyyy","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        // define event info
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName()).isNegativeHdp(false).hdpPoint(0.25).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 1: Login to SB11 >> go to Bet Settlement >> Confirmed");
        log("@Step 2: Select the bet >>  click 'Settle and Send Settlement Email'");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",accountCode);
        betSettlementPage.settleAndSendSettlementEmail(order);

        log("@Step 3: Select status as Settled");
        betSettlementPage.filter("Settled","","","",accountCode);

        log("@Veirfy 1 : Validate the bet displays with the updated values");
        betSettlementPage.verifyOrderInfo(order);

        log("INFO: Executed completely");
    }

    @TestRails(id="185")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC185(String accountCode,String accountCurrency){
        log("@title: Validate can confirm bets and the confirmed bets will show in Bet Settlement page");
        log("Precondition: User has permission to access Confirm Bets page" +
                "Having at least an account that is having bet which is not confirmed yet\n");
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

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Confirm Bets");
        log("@Step 3: Filter with account at pre-condition and Status is Pending > Show");
        log("@Step 4: Select any bet > click Confirm Bet > Observe");
        log("@Step 5: Filter with account at pre-condition and Status is Confirmed > Show");
        log("@step 6: Observe confirmed bet at step 4");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.confirmBet(lstOrder.get(0));
        confirmBetsPage.filter(companyUnit,"","Confirmed",sport,"All","Specific Date","","",accountCode);

        log("@Veirfy 1 : User can confirm bet successfully");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("@step 7: Navigate to Trading > Bet Settlement");
        log("Step 8: Filter with account at pre-condition and Status is Confirmed");
        log("Step 9: Observe confirmed bet at step 4");
        BetSettlementPage betSettlementPage = confirmBetsPage.navigatePage(TRADING,BET_SETTLEMENT,BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",lstOrder.get(0).getAccountCode());

        log("@Veirfy 2 : Confirmed bets is shown in Bet Settlement page correctly");
        betSettlementPage.verifyOrderInfo(lstOrder.get(0));

        log("@Pos-condition: Deleted the order");

        betSettlementPage.deleteOrder(lstOrder.get(0));

        log("INFO: Executed completely");
    }

    @TestRails(id="182")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_182(String accountCode,String accountCurrency){
        log("@title: Validate updated bets reflect correctly in the bet list of Bet Entry page");
        log("Precondition:There is a settled event with the result is the home team win");
        log("There is a confirmed bet that is placed on the event with the selection is the home team, odds is 2 (HK), and stake = 100 on the settled event");
        log("Precondition:Having a valid account that can place bets (e.g. "+accountCode);
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String dateAPI =  String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        // define event info
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName()).isNegativeHdp(false).hdpPoint(0.25).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.showLeague(companyUnit,"",eventInfo.getLeagueName());
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 1: Login to SB11 >> go to Confirm Bets >> Confirmed Bets >> search the bet");
        log("@Step 2: Select the bet >> change selection as away team, hdp point, and bet type");
        log("@Step 3: Click Update Bet button");
        order.setSelection(eventInfo.getAway());
        order.setHdpPoint(0.50);
        order.setBetType("Lay");
        confirmBetsPage.filter(companyUnit, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.updateOrder(order,false);

        log("@Verify 1: Validate the bet is updated with new values accordingly");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("@Step 4 Go to Bet Entry >> search the event >> open the bet list of the event");
        log("@Verify 1: Validate the bet is updated with new values accordingly");
        betEntryPage = confirmBetsPage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        lstOrder = betListPopup.verifyListOrderInfoDisplay(lstOrder,"Handicap","");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Confirmed",eventInfo.getSportName(),"All","Specific Date","","",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),false);

        log("INFO: Executed completely");
    }

    @TestRails(id="186")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_186(String accountCode,String accountCurrency){
        log("@title: Validate can unconfirm the confirmed bets");
        log("Precondition:User has permission to access Confirm Bets page");
        log("Having at least an account that is having bet which is already confirmed\n");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String dateAPI =  String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        // define event info
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName()).isNegativeHdp(false).hdpPoint(0.25).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.showLeague(companyUnit,"",eventInfo.getLeagueName());
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        order = lstOrder.get(0);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");
        log("@Step 3:Filter with account at pre-condition and Status is Confirmed > click Show");
        log("@Step 3:Select any bet > click Unconfirm Selected > observe");
        confirmBetsPage.filter(companyUnit, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.unConfirmBet(order);

        log("@Step 4: Filter with account at pre-condition and Status is Pending > click Show > observe");
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);

        log("@Verify 1:Unconfirmed bets should move from Confirm list to Pending list successfully");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);

        log("INFO: Executed completely");
    }

    @TestRails(id="1003")
    @Test(groups = {"smoke_qc"})
    @Parameters({"accountCode","accountCurrency"})
    public void Confirm_Bets_1003(String accountCode,String accountCurrency){
        log("@title: Validate can duplicate bets for SPBPS7");
        log("Precondition:User has permission to access Confirm Bets page");
        log("Having at least an account that is having bet which is not confirmed yet\n");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String dateAPI =  String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        String league = soccerBetEntryPage.getFirstLeague();
        // define event info
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        List<Order> lstOrder = new ArrayList<>();
        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName()).isNegativeHdp(false).hdpPoint(0.25).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.showLeague(companyUnit,"",eventInfo.getLeagueName());
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        order = lstOrder.get(0);
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);

        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");
        log("@Step 3:Filter with account at pre-condition and Status is Pending > click Show");
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);

        log("@Step 4:Select any bet > click Duplicate Bet For SPBPS7");
        confirmBetsPage.duplicateBetForSPBS7(order);

        log("@Step 5: Filter with account 'SPBPS7' and Status is Pending > click Show > observe");
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", "SPBPS7");
        confirmBetsPage.verifyOrder(lstOrder.get(1));

        log("@Verify 1: Duplicated bets should display on SPBPS7 bet list with correct bet info");

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet of SPBPS7 and "+accountCode+" account");
        confirmBetsPage.deleteOrder(lstOrder.get(1),true);
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);

        log("INFO: Executed completely");
    }
    @TestRails(id="1042")
    @Test(groups = {"smoke"})
    @Parameters({"username","accountCode","accountCurrency"})
    public void Confirm_Bets_1042(String username,String accountCode,String accountCurrency){
        log("@title: Validate manual bet not display when filtering in Pending section after creating");
        log("Precondition:Have a new Manual bet created #C862");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String description = "Manua bet on " +DateUtils.getDate(0,"yyyymmddhhmmss","GMT+7");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT+7");
        String dateAPI =  String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName(sport)
                .leagueName(username)
                .eventDate(dateAPI)
                .home("Manual Bet")
                .away("")
                .openTime("13:00")
                .eventStatus("Scheduled")
                .isLive(false)
                .isN(false)
                .build();


        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName()).isNegativeHdp(false).hdpPoint(0.00).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("MB")
                .selection("home")
                .event(eventInfo)
                .betId("Manual Bet")
                .orderId("Manual Bet")
                .build();

        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        betEntryPage.goToMixedSports();

        ManualBetBetEntryPage manualBetBetEntryPage = new ManualBetBetEntryPage();
        manualBetBetEntryPage.placeManualBet(companyUnit,date, accountCode, sport,
                description, order.getSelection(),order.getBetType(),String.format("%.2f",order.getPrice()),String.format("%.2f",order.getRequireStake())
                ,String.format("%.2f",order.getRequireStake()),true);

        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");

        ConfirmBetsPage confirmBetsPage = manualBetBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3:Filter 'Sports' = All and input the account at the precondition to the 'Account Code' field and the status is Pending");
        log("@Step 4:Click 'Show' and observe the bet at precondition");
        confirmBetsPage.filter(companyUnit, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);

        log("@Verify 1: Verify manual bet does not display in the Pending section");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(order),"Failed! Manual bet display in the table");

        log("@Post-Condition: Cancel Manual bet");
        confirmBetsPage.filter(companyUnit, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.deleteOrder(order,false);

        log("INFO: Executed completely");
    }

    @TestRails(id="1043")
    @Test(groups = {"smoke"})
    @Parameters({"username","accountCode","accountCurrency"})
    public void Confirm_Bets_1043(String username, String accountCode,String accountCurrency){
        log("@title:Validate manual bet display when filtering in Confirm status after creating ");
        log("Precondition:Have a manual bet created");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String description = "Manua bet on " +DateUtils.getDate(0,"yyyymmddhhmmss","GMT+7");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT+7");
        String dateAPI =  String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName(sport)
                .leagueName(username)
                .eventDate(dateAPI)
                .home("Manual Bet")
                .away("")
                .openTime("13:00")
                .eventStatus("Scheduled")
                .isLive(false)
                .isN(false)
                .build();

        // define order info
        Order order = new Order.Builder()
                .sport(eventInfo.getSportName()).isNegativeHdp(false).hdpPoint(0.00).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("MB")
                .selection("home")
                .event(eventInfo)
                .betId("Manual Bet")
                .orderId("Manual Bet")
                .build();

        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        betEntryPage.goToMixedSports();

        ManualBetBetEntryPage manualBetBetEntryPage = new ManualBetBetEntryPage();
        manualBetBetEntryPage.placeManualBet(companyUnit,date, accountCode, sport,
                description, order.getSelection(),order.getBetType(),String.format("%.2f",order.getPrice()),String.format("%.2f",order.getRequireStake())
                ,String.format("%.2f",order.getRequireStake()),true);

        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = manualBetBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);

        log("@Step 3:Filter 'Sports' = All and input the account at the precondition to the 'Account Code' field and the status is Confirm");
        log("@Step 4:Click 'Show' and observe the bet at precondition");
        confirmBetsPage.filter(companyUnit, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);

        log("@Verify 1: Verify manual bet display when fingering Confirm status with correctly info");
        confirmBetsPage.verifyOrder(order);

        log("@Post-Condition: Cancel Manual bet");
        confirmBetsPage.filter(companyUnit, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", "", "", accountCode);
        confirmBetsPage.deleteOrder(order,false);




        log("INFO: Executed completely");
    }

}
