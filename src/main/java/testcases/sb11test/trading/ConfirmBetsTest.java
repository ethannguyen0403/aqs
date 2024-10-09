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
import utils.sb11.sport.EventScheduleUtils;
import utils.sb11.trading.BetEntrytUtils;
import utils.sb11.trading.BetSettlementUtils;
import utils.sb11.trading.ConfirmBetsUtils;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class ConfirmBetsTest extends BaseCaseAQS {

    @TestRails(id = "176")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC176(String accountCode) {
        log("@title: Validate Pending Soccer Bets display correct information as Bet Entry page");
        log("Precondition: Init data and Place a new bet on Bet Entry for Soccer to have a pending bet");
        String date = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,true,accountCode,"Goals","HDP","Home","FullTime",2.15,1.75,"HK",15.50,"BACK",false,"");
        lstOrder.get(0).setBetType("Back");
        log("@Step 2: Navigate to Trading > Confirm Bet");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        try {
            log("@Step 3. Filter 'Sports' = Soccer and input account at the precondition to 'Account Code' field and status = Pending");
            log("@Step 4. Click 'Show' and observe the bet at precondition");
            confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);

            log("@Verify: Bet at the precondition being displayed with correct information as same as Bet Entry page");
            confirmBetsPage.verifyOrder(lstOrder.get(0));
        } finally {
            log("@Pos-condition: Deleted the order");
            confirmBetsPage.deleteOrder(lstOrder.get(0), true);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id = "179")
    @Test(groups = {"smoke","ethan4.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC179(String accountCode) {
        log("@title:Validate deleted bets does not show in Bet Entry page");
        log("Precondition: Place a new bet on Bet Entry for Soccer and delete pending bet in confirm bet page");
        String date = String.format(DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.15,1.75,"HK",15.50,"BACK",false,"");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0), true);
        log("@Step 2: Navigate to Trading > Bet Entry");
        log("@Step 3. Filter Date and Choose League as the bet at the precondition");
        log("@Step 3. Input the account at the precondition to Account Code field");
        log("@Step 4. Click on the 'Bets' link at 'SPB' column of the event at the precondition and observe");
        BetEntryPage betEntryPage = confirmBetsPage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED, date, lstOrder.get(0).getEvent().getLeagueName());
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(lstOrder.get(0).getEvent().getHome());

        log("@Verify: Bet has been deleted does not show in Bet List - Bet Entry page");
        Assert.assertFalse(betListPopup.isOrderDisplay(lstOrder.get(0).getBetId()), "Failed! The order is exist in the list after deleting");

        log("INFO: Executed completely");
    }

    @TestRails(id = "178")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC178(String accountCode) {
        log("@title: Validate can delete Pending Bet");
        log("Precondition: Init data and Place a new bet on Bet Entry for Soccer to have a pending bet");
        String date = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));

        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,"HK",9.00,"BACK",false,"");
        log("@Step 2: Navigate to Trading > Bet Entry");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);

        log("@Step 3. Input account at the precondition to 'Account Code' field and search for the bet at precondition");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);

        log("@Step 4.  Click on 'X' button of the bet at the precondition and observe");
        confirmBetsPage.deleteOrder(lstOrder.get(0), true);

        log("@Verify: Users can delete pending bet by clicking on 'X' button and it will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(lstOrder.get(0)), "Failed! The order still displayed after deleteing");

        log("INFO: Executed completely");
    }

    @TestRails(id = "190")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC190(String accountCode) {
        log("@title:Validate can delete multiple Confirmed Bets");
        log("@Precondition: Init data and Place 2 new bet on Bet Entry for Soccer then confirm these bets in Confirm Bets Page");
        String date = String.format(DateUtils.getDate(-1, "d/MM/yyyy", "GMT +7"));

        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.15,1.75,"HK",15.50,"BACK",false,"");
        lstOrder.add(welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.15,1.75,"HK",10.00,"LAY",false,"").get(0));
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 2:Navigate to Trading > Confirm Bets & Filter status = Confirmed");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3. Input account at the precondition to 'Account Code' field");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", SOCCER, "All", "Specific Date", date, "", accountCode);
        log("@Step 4 Tick on check-boxes of some bets'");
        log("@Step 5. Click on 'Delete Selected' and observe");
        confirmBetsPage.deleteSelectedOrders(lstOrder, false);
        log("@Verify 1: Users can delete multiple confirmed bets and they will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrdersDisplayInTheTable(lstOrder), "Failed! The order still displayed after delete");
        log("INFO: Executed completely");
    }

    @TestRails(id = "308")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC308(String accountCode) {
        log("@title: Validate can delete Confirmed Bet");
        log("Precondition: Init data and Place a new bet on Bet Entry for Soccer then Confirm the Pending bet " + accountCode);
        String date = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,"HK",9.00,"BACK",false,"");
        ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 2:Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3: Input account code that used to placed bet and selected Status Confirmed");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", SOCCER, "All", "Specific Date", date, "", accountCode);

        log("@Step 4.Click on 'X' button of the bet at the precondition and observe");
        confirmBetsPage.deleteOrder(lstOrder.get(0), false);

        log("@Verify Users can delete confirmed bet by clicking on 'X' button and it will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(lstOrder.get(0)), "Failed! The order still displayed after deleting");

        log("INFO: Executed completely");
    }

    @TestRails(id = "881")
    @Test(groups = {"smoke","ethan7.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC881(String accountCode) {
        log("@title: Validate can delete multiple Pending Bets");
        log("Precondition: Account has place above 2 bets on an event in Bet Entry");
        String date = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.15,1.75,"HK",15.50,"BACK",false,"");
        lstOrder.add(welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.15,1.75,"HK",15.50,"BACK",false,"").get(0));

        log("@Step 2:Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3: Filter Pending status and valid account code");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);

        log("@Step 4 Tick on check-boxes of some bets ");
        log("@Step 5 Click on 'Delete Selected' and observe");
        confirmBetsPage.deleteSelectedOrders(lstOrder, true);

        log("@Verify : Users can delete multiple pending bets and they will be no longer displayed");
        Assert.assertFalse(confirmBetsPage.isOrdersDisplayInTheTable(lstOrder), "Failed! The orders still displayed after delete Pending bet");

        log("INFO: Executed completely");
    }

    @TestRails(id = "869")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC869(String accountCode) {
        log("@title: Validate Pending Cricket Bets display with correct information as Bet Entry page");
        log("Precondition: Test case C863 to place a Cricket bet");
        String date = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName("Cricket")
                .leagueName("QA League")
                .eventDate(dateAPI)
                .home("QA Team 01")
                .away("QA Team 02")
                .openTime("13:00")
                .eventStatus("Scheduled")
                .isLive(false)
                .isN(false)
                .build();
        EventScheduleUtils.addEventByAPI(eventInfo, dateAPI, SPORT_ID_MAP.get("Cricket"));
        String leagueID = EventScheduleUtils.getLeagueID(eventInfo.getLeagueName(), SPORT_ID_MAP.get("Cricket"));
        String eventID = EventScheduleUtils.getEventID(dateAPI, leagueID);
        eventInfo.setEventId(eventID);
        List<Order> lstOrder = welcomePage.placeBetAPI(CRICKET,date,eventInfo,accountCode,"MatchBetting","MatchBetting",eventInfo.getHome(),"FullTime",0.5,0,"HK",15.50,
                "BACK",false,"");
        lstOrder.get(0).setBetType("Back");
        lstOrder.get(0).setMarketType("1x2");
        lstOrder.get(0).setStage(null);
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        try {
            log("@Step 3: Filter 'Sports' = Cricket and input the account at the precondition to 'Account Code' field and the status is Pending");
            confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", date, "", accountCode);

            log("@Verify: Bet at the precondition being displayed with correct information as same as Bet Entry page");
            confirmBetsPage.verifyOrder(lstOrder.get(0));
        } finally {
            log("@Pos-condition: Deleted the order");
            confirmBetsPage.deleteOrder(lstOrder.get(0), true);
            log("@Pos-condition: Deleted the event Cricket");
            EventScheduleUtils.deleteEventByAPI(eventInfo,dateAPI);
            log("INFO: Executed Pos-condition completely");
        }
        log("INFO: Executed completely");
    }

    @TestRails(id = "181")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC181(String accountCode) {
        log("@title:Validate bet info is correctly in Confirm Bet and Bet Setlement after update a bets in Confirm status");
        log("Precondition: Test case C863 to place a Cricket bet");
        String date = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));
        String dateAPI = DateUtils.formatDate(date, "dd/MM/yyyy", "yyyy-MM-dd");
        // define event info
        Event eventCricket = new Event.Builder()
                .sportName("Cricket")
                .leagueName("QA League")
                .eventDate(dateAPI)
                .home("QA Team 01")
                .away("QA Team 02")
                .openTime("13:00")
                .eventStatus("Scheduled")
                .isLive(false)
                .isN(false)
                .build();
        EventScheduleUtils.addEventByAPI(eventCricket, dateAPI, SPORT_ID_MAP.get("Cricket"));
        String leagueID = EventScheduleUtils.getLeagueID(eventCricket.getLeagueName(), SPORT_ID_MAP.get("Cricket"));
        String eventID = EventScheduleUtils.getEventID(dateAPI, leagueID);
        eventCricket.setEventId(eventID);

        List<Order> lstOrder = welcomePage.placeBetAPI(CRICKET,date,eventCricket,accountCode,"MatchBetting","MatchBetting",eventCricket.getHome(),"FullTime",0.5,0,"HK",15.50,
                "BACK",false,"");
        lstOrder.get(0).setBetType("Back");
        lstOrder.get(0).setMarketType("1x2");
        lstOrder.get(0).setStage(null);
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", eventCricket.getSportName(), "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.confirmBet(lstOrder.get(0));

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Login to SB11 >> Confirm Bets > Filter Status = Confirmed");
        log("@Step 3: Input the account code at the precondition > Show");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", eventCricket.getSportName(), "All", "Specific Date", date, "", accountCode);

        log("@Step 4: Find the bet at the precondition > edit any information of the bet (Selection/HDP/Odds/Stake)");
        log("@Step 5: Click on 'Update Bet'");
        lstOrder.get(0).setSelection(eventCricket.getAway());
        confirmBetsPage.updateOrder(lstOrder.get(0), false);

        log("@Verify 1: Bet info is correctly updated in confirmed bets ");
        confirmBetsPage.verifyOrder(lstOrder.get(0));
        BetSettlementPage betSettlementPage = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed", date, "", "", accountCode);
        betSettlementPage.reClickShowButton(lstOrder.get(0));
        log("@Verify 2 : Bets display correctly information in Bet Settlement page");
        betSettlementPage.verifyOrderInfo(lstOrder.get(0));
        log("INFO: Executed completely");
        log("@Pos-condition: Deleted the order");
        betSettlementPage.deleteOrder(lstOrder.get(0));
        log("@Pos-condition: Deleted the event Cricket");
        EventScheduleUtils.deleteEventByAPI(eventCricket,dateAPI);
        log("INFO: Executed Pos-condition completely");
    }

    @TestRails(id = "1000")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC1000(String accountCode) {
        log("@title: Validate deleted bets does not show in Bet Settlement page");
        log("Precondition:There is a Confirmed bet and be deleted in Confirm bet page");
        String date = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7");
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,true,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,"HK",9.00,
                "BACK",false,"");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.confirmBet(lstOrder.get(0));
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", SOCCER, "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0), false);

        log("@Step 1: Login to SB11 >> Bet Settlement page");
        log("@Step 2: Filter Confirm status with the according account code");
        log("@Step 3: Click 'Search' and observe");
        BetSettlementPage betSettlementPage = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed", date, "", "", lstOrder.get(0).getAccountCode());

        log("@Veirfy 1 : Confirmed bet has been deleted does not show in Bet Settlement page");
        Assert.assertFalse(betSettlementPage.isOrderDisplayInTheTable(lstOrder.get(0)), "FAILED! The order is displayed when it is deleted in Confirm Bets page");

        log("INFO: Executed completely");
    }

    @TestRails(id = "1001")
    @Test(groups = {"smoke","ethan4.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC1001(String accountCode) {
        log("@title: Validate can update Pending Bets");
        log("Precondition:There is a Pending");
        String date = String.format(DateUtils.getDate(-1, "d/MM/yyyy", "GMT +7"));
        List<Order> lstOrder =  welcomePage.placeBetAPI(SOCCER,date,true,accountCode,"Goals","HDP","Away","FullTime",2.05,1.75,"HK",9.50,"BACK",false,"");
        log("@Step 1: Login to SB11 >> Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);

        log("@Step 2: Filter status Pending and the according account code then click Show");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);

        log("@Step 3: Find the bet at the precondition > edit any information of the bet (Selection/HDP/Odds/Stake)");
        log("@Step 4: Click on 'Update Bet')");
        lstOrder.get(0).setSelection(lstOrder.get(0).getEvent().getHome());
        lstOrder.get(0).setHdpPoint(0.50);
        lstOrder.get(0).setBetType("Lay");
        confirmBetsPage.updateOrder(lstOrder.get(0), true);

        log("@Step 5: Re-filter the bet has been updated and observe");
        log("@Verify 1 : Users can update Pending Bets and the updated information is saved correctly");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("INFO: Executed completely");
    }

    @TestRails(id = "942")
    @Test(groups = {"smoke","ethan7.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC942(String accountCode) {
        log("@title:Validate updated bets reflect correctly in the bet list of Bet Settlement page");
        log("Precondition: Using the updated bets in the test case C182");
        log("Note: Tobe able settled order after place bet > commfirmed and settled => we should place in the event in the past");
        String todate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7"));
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,true,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,
                "HK",15.50,"BACK",false,"");
        //Confirm Bet
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        log("@Step 1: Login to SB11 >> go to Bet Settlement >> Confirmed");
        BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed", date, "", "", accountCode);
        betSettlementPage.reClickShowButton(lstOrder.get(0));
        log("@Step 2: Select the bet >>  click 'Settle and Send Settlement Email'");
        betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
        log("@Step 3: Select status as Settled");
        betSettlementPage.filter("Settled", date, "" ,"", accountCode);
        betSettlementPage.reClickShowButton(lstOrder.get(0));
        log("@Verify 1 : Validate the bet displays with the updated values");
        betSettlementPage.verifyOrderInfo(lstOrder.get(0));
        log("INFO: Executed completely");
    }

    @TestRails(id = "185")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_TC185(String accountCode) {
        log("@title: Validate can confirm bets and the confirmed bets will show in Bet Settlement page");
        log("Precondition: User has permission to access Confirm Bets page" +
                "Having at least an account that is having bet which is not confirmed yet\n");
        String date = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7");
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,"HK",9.00,
                "BACK",false,"");
        lstOrder.get(0).setBetType("Back");
        lstOrder.get(0).setMarketType("Handicap");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Confirm Bets");
        log("@Step 3: Filter with account at pre-condition and Status is Pending > Show");
        log("@Step 4: Select any bet > click Confirm Bet > Observe");
        log("@Step 5: Filter with account at pre-condition and Status is Confirmed > Show");
        log("@step 6: Observe confirmed bet at step 4");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.confirmBet(lstOrder.get(0));
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", SOCCER, "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.reClickShowButton(lstOrder.get(0));
        log("@Veirfy 1 : User can confirm bet successfully");
        confirmBetsPage.verifyOrder(lstOrder.get(0));
        log("@step 7: Navigate to Trading > Bet Settlement");
        log("Step 8: Filter with account at pre-condition and Status is Confirmed");
        log("Step 9: Observe confirmed bet at step 4");
        BetSettlementPage betSettlementPage = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed", date, "", "", lstOrder.get(0).getAccountCode());
        betSettlementPage.reClickShowButton(lstOrder.get(0));
        log("@Veirfy 2 : Confirmed bets is shown in Bet Settlement page correctly");
        betSettlementPage.verifyOrderInfo(lstOrder.get(0));
        log("@Pos-condition: Deleted the order");
        betSettlementPage.deleteOrder(lstOrder.get(0));
        log("INFO: Executed completely");
    }

    @TestRails(id = "182")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"accountCode", "accountCurrency"})
    public void Confirm_Bets_182(String accountCode, String accountCurrency) {
        log("@title: Validate updated bets reflect correctly in the bet list of Bet Entry page");
        log("Precondition:There is a settled event with the result is the home team win");
        log("There is a confirmed bet that is placed on the event with the selection is the home team, odds is 2 (HK), and stake = 100 on the settled event");
        log("Precondition:Having a valid account that can place bets (e.g. " + accountCode);
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,true,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,
                "HK",15.50,"BACK",false,"");
        lstOrder.get(0).setSelection(lstOrder.get(0).getEvent().getAway());
        lstOrder.get(0).setHdpPoint(0.50);
        lstOrder.get(0).setBetType("Lay");
        lstOrder.get(0).setAccountCurrency(accountCurrency);
        //Confirm Bet
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        log("@Step 1: Login to SB11 >> go to Confirm Bets >> Confirmed Bets >> search the bet");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING,CONFIRM_BETS,ConfirmBetsPage.class);
        log("@Step 2: Select the bet >> change selection as away team, hdp point, and bet type");
        log("@Step 3: Click Update Bet button");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", lstOrder.get(0).getEvent().getSportName(), "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.updateOrder(lstOrder.get(0), false);

        log("@Verify 1: Validate the bet is updated with new values accordingly");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("@Step 4 Go to Bet Entry >> search the event >> open the bet list of the event");
        log("@Verify 1: Validate the bet is updated with new values accordingly");
        BetEntryPage betEntryPage = confirmBetsPage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED, date, lstOrder.get(0).getEvent().getLeagueName());
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(lstOrder.get(0).getEvent().getHome());

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        lstOrder = betListPopup.verifyListOrderInfoDisplay(lstOrder, "Handicap", "");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet " + lstOrder.get(0).getBetId() + " in Confirm Bet page");
        confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", lstOrder.get(0).getEvent().getSportName(), "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0), false);

        log("INFO: Executed completely");
    }

    @TestRails(id = "186")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"accountCode"})
    public void Confirm_Bets_186(String accountCode) {
        log("@title: Validate can unconfirm the confirmed bets");
        log("Precondition:User has permission to access Confirm Bets page");
        log("Having at least an account that is having bet which is already confirmed\n");
        String date = String.format(DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7"));

        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,"HK",9.00,"BACK",false,"");
        ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
        lstOrder.get(0).setBetType("Back");
        lstOrder.get(0).setMarketType("Handicap");
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3:Filter with account at pre-condition and Status is Confirmed > click Show");
        log("@Step 3:Select any bet > click Unconfirm Selected > observe");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", lstOrder.get(0).getEvent().getSportName(), "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.unConfirmBet(lstOrder.get(0), false);

        log("@Step 4: Filter with account at pre-condition and Status is Pending > click Show > observe");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", lstOrder.get(0).getEvent().getSportName(), "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.reClickShowButton(lstOrder.get(0));
        log("@Verify 1:Unconfirmed bets should move from Confirm list to Pending list successfully");
        confirmBetsPage.verifyOrder(lstOrder.get(0));

        log("@Post-Condition: Cancel Pending bet " + lstOrder.get(0).getBetId() + " in Confirm Bet page");
        confirmBetsPage.deleteOrder(lstOrder.get(0), true);

        log("INFO: Executed completely");
    }

    @TestRails(id = "1003")
    @Test(groups = {"smoke_qc"})
    @Parameters({"accountCode", "accountCurrency"})
    public void Confirm_Bets_1003(String accountCode, String accountCurrency) {
        log("@title: Validate can duplicate bets for SPBPS7");
        log("Precondition:User has permission to access Confirm Bets page");
        log("Having at least an account that is having bet which is not confirmed yet\n");
        String date = String.format(DateUtils.getDate(-1, "d/MM/yyyy", "GMT+7"));

        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,date,false,accountCode,"Goals","HDP","Home","FullTime",2.05,0.25,"HK",9.00,"BACK",false,"");
        lstOrder.get(0).setBetType("Back");
        lstOrder.get(0).setMarketType("Handicap");
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3:Filter with account at pre-condition and Status is Pending > click Show");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", lstOrder.get(0).getEvent().getSportName(), "All", "Specific Date", date, "", accountCode);

        log("@Step 4:Select any bet > click Duplicate Bet For SPBPS7");
        confirmBetsPage.duplicateBetForSPBS7(lstOrder.get(0));
        Order orderDup = new Order.Builder()
                .sport(lstOrder.get(0).getEvent().getSportName()).isNegativeHdp(false).hdpPoint(0.25).price(2.05).requireStake(9.00)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode("SPBPS7").accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(lstOrder.get(0).getEvent().getHome())
                .event(lstOrder.get(0).getEvent())
                .build();
        lstOrder.add(orderDup);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 5: Filter with account 'SPBPS7' and Status is Pending > click Show > observe");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", lstOrder.get(0).getEvent().getSportName(), "All", "Specific Date", date, "", "SPBPS7");
        confirmBetsPage.waitSpinnerDisappeared();
        confirmBetsPage.verifyOrder(lstOrder.get(1));

        log("@Verify 1: Duplicated bets should display on SPBPS7 bet list with correct bet info");

        log("@Post-Condition: Cancel Pending bet " + lstOrder.get(0).getBetId() + " in Confirm Bet of SPBPS7 and " + accountCode + " account");
        confirmBetsPage.deleteOrder(lstOrder.get(1), true);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", lstOrder.get(0).getEvent().getSportName(), "All", "Specific Date", date, "", accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0), true);

        log("INFO: Executed completely");
    }

    @TestRails(id = "1042")
    @Test(groups = {"smoke"})
    @Parameters({"username", "accountCode", "accountCurrency"})
    public void Confirm_Bets_1042(String username, String accountCode, String accountCurrency) {
        log("@title: Validate manual bet not display when filtering in Pending section after creating");
        log("Precondition:Have a new Manual bet created #C862");
        String description = "Manual bet on " + DateUtils.getDate(0, "yyyymmddhhmmss", "GMT+7");
        String date = DateUtils.getDate(0, "dd/MM/yyyy", "GMT+7");
        String previousDate = DateUtils.getPreviousDate(date, "dd/MM/yyyy");
        String dateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName(SOCCER)
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

        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
        betEntryPage.goToMixedSports();

        ManualBetBetEntryPage manualBetBetEntryPage = new ManualBetBetEntryPage();
        manualBetBetEntryPage.placeManualBet(KASTRAKI_LIMITED, date, accountCode, SOCCER,
                description, order.getSelection(), order.getBetType(), String.format("%.2f", order.getPrice()), String.format("%.2f", order.getRequireStake())
                , String.format("%.2f", order.getRequireStake()), true);

        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");

        ConfirmBetsPage confirmBetsPage = manualBetBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3:Filter 'Sports' = All and input the account at the precondition to the 'Account Code' field and the status is Pending");
        log("@Step 4:Click 'Show' and observe the bet at precondition");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", eventInfo.getSportName(), "All", "Specific Date", date, "", accountCode);

        log("@Verify 1: Verify manual bet does not display in the Pending section");
        Assert.assertFalse(confirmBetsPage.isOrderDisplayInTheTable(order), "Failed! Manual bet display in the table");

        log("@Post-Condition: Cancel Manual bet");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", eventInfo.getSportName(), "Manual Bet", "Specific Date",
                previousDate, "", accountCode);
        confirmBetsPage.deleteOrder(order, false);

        log("INFO: Executed completely");
    }

    @TestRails(id = "1043")
    @Test(groups = {"smoke"})
    @Parameters({"username", "accountCode", "accountCurrency"})
    public void Confirm_Bets_1043(String username, String accountCode, String accountCurrency) {
        log("@title:Validate manual bet display when filtering in Confirm status after creating ");
        log("Precondition:Have a manual bet created");
        String description = "Manual bet on " + DateUtils.getDate(0, "yyyymmddhhmmss", "GMT+7");
        String date = DateUtils.getDate(0, "dd/MM/yyyy", "GMT+7");
        String yesterdayDate = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT+7");
        String dateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName(SOCCER)
                .leagueName(description)
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

        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
        betEntryPage.goToMixedSports();

        ManualBetBetEntryPage manualBetBetEntryPage = new ManualBetBetEntryPage();
        manualBetBetEntryPage.placeManualBet(KASTRAKI_LIMITED, date, accountCode, SOCCER,
                description, order.getSelection(), order.getBetType(), String.format("%.2f", order.getPrice()), String.format("%.2f", order.getRequireStake())
                , String.format("%.2f", order.getRequireStake()), true);

        log("@Step 1: Login to SB11");
        log("@Step 2:Navigate to Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = manualBetBetEntryPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);

        log("@Step 3:Filter 'Sports' = All and input the account at the precondition to the 'Account Code' field and the status is Confirm");
        log("@Step 4:Click 'Show' and observe the bet at precondition");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", yesterdayDate, "", accountCode);

        log("@Verify 1: Verify manual bet display when fingering Confirm status with correctly info");
        confirmBetsPage.verifyOrder(order);

        log("@Post-Condition: Cancel Manual bet");
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", eventInfo.getSportName(), "All", "Specific Date", yesterdayDate, "", accountCode);
        confirmBetsPage.deleteOrder(order, false);

        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2185")
    public void Confirm_Bets_2185() {
        log("@title: Validate Confirm Bets page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("Validate Confirm Bets page is displayed with correctly title");
        Assert.assertTrue(confirmBetsPage.getTitlePage().contains(CONFIRM_BETS), "Failed! Confirm Bets page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2186")
    public void Confirm_Bets_2186() {
        log("@title: Validate UI on Confirm Bets is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("Validate UI Info display correctly");
        confirmBetsPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2187")
    @Parameters({"accountCode"})
    public void Confirm_Bets_2187(String accountCode) {
        log("@title: Validate Total Stake is matched correctly with all bet stake on Pending list");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3: Select Status is Pending");
        log("@Step 4: Enter account at pre-condition");
        log("@Step 5: Click Show");
        String date = DateUtils.getDate(-2,"dd/MM/yyyy",GMT_7);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", date, "", accountCode);
        log("Validate Total Stake is matched correctly with all bet stake on Pending list");
        confirmBetsPage.isTotalStakeMatched();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2188")
    @Parameters({"accountCode"})
    public void Confirm_Bets_2188(String accountCode) {
        log("@title: Validate Total Confirmed Stake is matched correctly with all bet stake on Pending list");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Confirm Bets");
        ConfirmBetsPage confirmBetsPage = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 3: Select Status is Confirmed");
        log("@Step 4: Enter account at pre-condition");
        log("@Step 5: Click Show");
        String date = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Confirmed", SOCCER, "All", "Specific Date", date, "", accountCode);
        log("Validate Total Stake is matched correctly with all bet stake on Pending list");
        confirmBetsPage.isTotalStakeMatched();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23713")
    @Parameters({"accountCode"})
    public void Confirm_Bets_23713(String accountCode) {
        log("@title: Validate Total Record filter in Pending status is correctly");
        log("@Step 1: Login SB11 > Trading > Confirm Bets");
        ConfirmBetsPage page = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 2: Filter Status: Pending and Account Code: Auto-Account01 then click Show button");
        String fromdate = DateUtils.getDate(-10,"dd/MM/yyyy",GMT_7);
        page.filter(KASTRAKI_LIMITED, "", "Pending", SOCCER, "All", "Specific Date", fromdate, "", accountCode);
        log("@Step 3: Get last row number (e.g. 780) in the result table and the number according to the account in Pending Account table (HKD - Auto-Account01 780)");
        log("Verify 1: Verify the number is matched and the label record iscorrectly displayed , e.g \"Total 780 record(s) (Server responded in ... seconds)\"");
        page.verifyNumberIsMatched();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23714")
    @Parameters({"accountCode"})
    public void Confirm_Bets_23714(String accountCode) {
        log("@title: Validate Total Record filter in Confirm status is correctly");
        log("@Step 1: Login SB11 > Trading > Confirm Bets");
        ConfirmBetsPage page = welcomePage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        log("@Step 2: Filter Status: Confirm and Account Code: Auto-Account01 then click Show button");
        String fromdate = DateUtils.getDate(-10,"dd/MM/yyyy",GMT_7);
        page.filter(KASTRAKI_LIMITED, "", "Confirmed", SOCCER, "All", "Specific Date", fromdate, "", accountCode);
        log("@Step 3: Get last row number (e.g. 447) in the result table and the number according to the account in Confrimed Account table (HKD - Auto-Account01 - 447)");
        log("Verify 1: Verify the number get from the last row and the number get in Confrimed Account is matched and the label record iscorrectly displayed , e.g \"Total 780 record(s) (Server responded in ... seconds)\"");
        page.verifyNumberIsMatched();
        log("INFO: Executed completely");
    }

}

