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
import pages.sb11.trading.popup.SoccerBetSlipPopup;
import pages.sb11.trading.popup.SoccerSPBBetSlipPopup;
import testcases.BaseCaseAQS;
import utils.sb11.trading.BetEntrytUtils;
import utils.sb11.sport.EventScheduleUtils;
import utils.sb11.trading.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class BetEntryTest extends BaseCaseAQS {

    @TestRails(id="862")
    @Parameters({"accountCode"})
    @Test(groups = {"smoke"})
    public void Bet_Entry_TC862(String accountCode){
        log("@title: Validate users can place Mixed Sports bets successfully");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Mixed Sports' > Input account at precondition on 'Account Code' field > click glass icon");
        log("@Step 4: Inputting Odds, Stake and Win/Loss or Comm Amount > click Place Bet");
        log("@Step 5: Click Yes on Confirm dialog > observe");
        betEntryPage.goToMixedSports();

        ManualBetBetEntryPage manualBetBetEntryPage = new ManualBetBetEntryPage();
        String messageSuccess = manualBetBetEntryPage.placeManualBet("Kastraki Limited ",date, accountCode, "Soccer",
                "Manual Bet Testing", null,null,"1.25","10","1",true);

        log("Validate user can place Mixed Sports bets successfully with message 'Placed successfully!");
        Assert.assertTrue(messageSuccess.contains(SBPConstants.BetEntryPage.MESSAGE_SUCCESS_MANUAL_BET), "FAILED! Incorrect place bet successful");

        log("INFO: Executed completely");
    }

    @TestRails(id="189")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC189(String accountCode,String accountCurrency){
        log("@title:Validate users can place single Soccer bets and bet list show correct info");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition:Having a valid account that can place bets (e.g. "+accountCode);
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,SOCCER,"");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Get the first Event of Frist League of Today Soccer");
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED,date,eventInfo.getLeagueName());

        log("@Step Precondition: Define order to place bet");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(SOCCER).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet without select \"option copy bet to SPBPS7same odds\" and \"copy bet to SPBPS7minus odds\"");
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        String successMessage = soccerBetEntryPage.getSuccessMessage();

        log("@Verify 1: User can place Soccer bets successfully with message 'The bet was placed successfully'");
        Assert.assertTrue(successMessage.contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+successMessage);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        log("@Step 7: Click 'Bets' at SPB column of event at step 5 > observe" +order.getBetId());
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        lstOrder = betListPopup.verifyListOrderInfoDisplay(lstOrder,"Handicap","");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);

        log("INFO: Executed completely");
    }

    @TestRails(id="863")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC863(String accountCode,String accountCurrency){
        log("@title: Validate user can place Cricket bets successfully with correct bets information in Bet List");
        log("Precondition: User has permission to access Bet Entry page");
        log("Having a valid account that can place bets (e.g. )" +accountCode);
        log("Having a Cricket event which has been created on Event Schedule > Cricket");
        String sportName = "Cricket";
        String companyUnit = "Kastraki Limited";
        String leagueName = "QA League";
        log("@Step 1: Login to SB11 site");
        String date = String.format(DateUtils.getDate(-1,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(dateAPI)
                .home("QA Team 01")
                .away("QA Team 02")
                .openTime("15:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        log("@Step prepare data: Add event for QA League in today local time and can filter in today in Trading>Bet Entry");
        EventScheduleUtils.addEventByAPI(eventInfo, dateAPI, SPORT_ID_MAP.get(sportName));
        String leagueID = EventScheduleUtils.getLeagueID(eventInfo.getLeagueName(), SPORT_ID_MAP.get(sportName));
        String eventID = EventScheduleUtils.getEventID(dateAPI, leagueID);
        eventInfo.setEventId(eventID);

        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Cricket' > select any League > click Show");
        CricketBetEntryPage cricketBetEntryPage =betEntryPage.goToCricket();

        log("@Step Precondition: Get the first Event of Frist League of Today Cricket");
        cricketBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());

        log("@Step Precondition: Define order to place bet");
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
                .build();

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet without select \"option copy bet to SPBPS7same odds\" and \"copy bet to SPBPS7minus odds\"");
        cricketBetEntryPage.placeBet(order,true);

        log("@Verify 1: User can place Cricket bets successfully with message 'The bet was placed successfully'");
        Assert.assertTrue(cricketBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+cricketBetEntryPage.getSuccessMessage());

        log("@Step 7: Click 'Bets' at CPB column of event at step 5 > observe");
        BetListPopup betListPopup = cricketBetEntryPage.openBetList(eventInfo.getHome()+"\n"+eventInfo.getAway());

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        order = betListPopup.verifyOrderInfoDisplay(order,CRICKET_MARKET_TYPE_BET_LIST.get(order.getMarketType()),"");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ order.getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",eventInfo.getSportName(),"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(order,true);

        log("@Postcondition: Delete the event");
        EventScheduleUtils.deleteEventByAPI(eventInfo,dateAPI);
        log("INFO: Executed completely");
    }

    @TestRails(id="191")
    @Test(groups = {"smoke","ethan"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC191(String accountCode,String accountCurrency) throws ParseException {
        log("@title: Validate Bet Slip info display correctly when open Soccer> FT>HDP>Home");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition: Having a valid account that can place bets (e.g. "+accountCode);
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String marketType = "HDP";

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Get the first Event of Frist League of Today Soccer");
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());

        log("@Step Precondition: Define order to place bet");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType(marketType)
                .stage("FT")
                .selection(eventInfo.getHome())
                .home(eventInfo.getHome())
                .away(eventInfo.getAway())
                .competitionName(eventInfo.getLeagueName())
                .build();
        lstOrder.add(order);

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on ... at the selected event, Column FT, HDP, Home");
        SoccerBetSlipPopup soccerBetSlipPopup = soccerBetEntryPage.openBetSlip(lstOrder.get(0).getAccountCode(),lstOrder.get(0).getSelection(),true,"HOME");

        log("@Verify: Verify info on bet slip display correctly: Competition name, Event Name, Market Type, Selection Type, Start date");
        soccerBetSlipPopup.verifyOrderInfoDisplay(lstOrder, eventInfo.getEventDate());
        log("INFO: Executed completely");
    }

    @TestRails(id="341")
    @Test(groups = {"smoke_qc","ethan5.0"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC341(String accountCode,String accountCurrency){
        log("@title: Validate can place bet for soccer with option copy bet to SPBPS7 as same odds\n");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition:Having a valid account that can place bets (e.g. "+accountCode);
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String accountSPBPS7 = "SPBPS7";
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        log("@Step Precondition: Get the first Event of Frist League of Today Soccer");
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());

        log("@Step Precondition: Define order to place bet");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        Order orderSPBPS7 = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountSPBPS7).accountCurrency("HKD")
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(orderSPBPS7);
        lstOrder.add(order);
        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet with Tick on option \"Tick here to Copy Bet to SPBPS7 as Same Odds\"");
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,true,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 8: Click 'Bets' at SPB column of event at step 6 > observe");
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());

        log("@Verify there are 2 bets created: 1 bet placed on account code with correct info and 1 bet placed on SPBPS7 with same info as Account Code ");
        lstOrder = betListPopup.verifyListOrderInfoDisplay(lstOrder,"Handicap","");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() + " + " + lstOrder.get(1).getBetId()+" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(1),true);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountSPBPS7);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);

        log("INFO: Executed completely");
    }

    @TestRails(id="1053")
    @Test(groups = {"smoke_qc"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC1053(String accountCode,String accountCurrency){
        log("@title: Validate can place bet for soccer with option copy bet to SPBPS7 as same odds\n");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition:Having a valid account that can place bets (e.g. "+accountCode);
        String accountSPBPS7 = "SPBPS7";

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,SOCCER,"");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Get the first Event of Frist League of Today Soccer");
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED,date,eventInfo.getLeagueName());

        log("@Step Precondition: Define order to place bet");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(SOCCER).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        Order orderSPBPS7 = new Order.Builder()
                .sport(SOCCER).isNegativeHdp(false).hdpPoint(1.75).price(2.14).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountSPBPS7).accountCurrency("HKD")
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet with Tick on option \"Tick here to Copy Bet to SPBPS7 Minus 0.01 Odds\"");
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,true,true);
        lstOrder.add(orderSPBPS7);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Step 8: Click 'Bets' at SPB column of event at step 6 > observe");
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());
        log("@Verify there are 2 bets created: 1 bet placed on account code with correct info and 1 bet placed on SPBPS7 with odds = place odds - 0.01, other info display as Account Code");
        lstOrder = betListPopup.verifyListOrderInfoDisplay(lstOrder,"Handicap","");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() + " + " + lstOrder.get(1).getBetId()+" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountSPBPS7);
        confirmBetsPage.deleteOrder(lstOrder.get(1),true);


        log("INFO: Executed completely");
    }

    @TestRails(id="868")
    @Test(groups = {"smoke_qc"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC868(String accountCode,String accountCurrency){
        log("@title: Validate users can place other market type of Soccer with correct info in Bet Slip");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition:Having a valid account that can place bets (e.g. "+accountCode);
        String marketType = "1x2";

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));

        log("@Step Precondition: Get the first Event of First League of Today Soccer");
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,SOCCER,"");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Define order to place bet");
        Order order = new Order.Builder()
                .sport(SOCCER)
                .hdpPoint(0.00)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType(marketType)
                .stage("Full Time")
                .selection("Home")
                .liveHomeScore(0)
                .liveAwayScore(0)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .event(eventInfo)
                .build();

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: At the event "+ eventInfo.getHome()+" Click (+) at More column of according event > select handicap value with inputting odds and stake");
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED,date,eventInfo.getLeagueName());
        SoccerSPBBetSlipPopup soccerSPBBetSlipPopup = soccerBetEntryPage.openSPBBetSlip(accountCode,eventInfo.getHome());

        log("@Step 6: Un-ticke on 2 option \"Tick here to Copy Bets to SPBPS7 as Same Odds\"\n" +
                "\"Tick here to Copy Bets to SPBPS7 Minus 0.01 Odds\" and click Place bet");
        soccerSPBBetSlipPopup.placeMoreBet(order,false,false,true);

        log("@Verify 1: User can place Soccer bets successfully with message 'The bet was placed successfully'");
        Assert.assertTrue(soccerBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+soccerBetEntryPage.getSuccessMessage());

        log("@Step 8: Click 'Bets' at SPB column of event at step 6 > observe");
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());

        Order order1 = new Order.Builder()
                .sport(SOCCER)
                .hdpPoint(0.00)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType(marketType)
                .stage("Full Time")
                .selection(eventInfo.getHome())
                .liveHomeScore(0)
                .liveAwayScore(0)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .event(eventInfo)
                .build();

        log("@Verify info in Bet slip popup display correctly");
        order = betListPopup.verifyOrderInfoDisplay(order1,marketType,"");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ order.getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending",SOCCER,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(order,true);

        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2189")
    public void BetEntry_2189(){
        log("Validate Bet Entry page for Soccer is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        log("Validate Soccer Bet Entry page for Soccer is displayed with correctly title");
        Assert.assertTrue(soccerBetEntryPage.getTitlePage().contains("Soccer"), "Failed! Soccer Bet Entry page is not displayed");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2190")
    public void BetEntry_2190(){
        log("Validate UI on Bet Entry for Soccer is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        log("Validate UI Info display correctly");
        soccerBetEntryPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2191")
    public void BetEntry_2191(){
        log("Validate Bet Entry page for Cricket is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Cricket' > select any League > click Show");
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("Validate Cricket Bet Entry page for Soccer is displayed with correctly title");
        Assert.assertTrue(cricketBetEntryPage.getTitlePage().contains("Cricket"), "Failed! Cricket Bet Entry page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2192")
    public void BetEntry_2192(){
        log("Validate UI on Bet Entry for Cricket is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Cricket' > select any League > click Show");
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("Validate UI Info display correctly");
        cricketBetEntryPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2193")
    public void BetEntry_2193(){
        log("Validate Bet Entry page for Mixed Sport is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Mixed Sport' > select any League > click Show");
        ManualBetBetEntryPage manualBetBetEntryPage = betEntryPage.goToMixedSports();
        log("Validate Manual Bet Entry page for Soccer is displayed with correctly title");
        Assert.assertTrue(manualBetBetEntryPage.getTitlePage().contains("Manual Bet"), "Failed! Manual Bet Entry page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2194")
    public void BetEntry_2194(){
        log("Validate UI on Bet Entry for Mixed Sport is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Mixed Sport' > select any League > click Show");
        ManualBetBetEntryPage manualBetBetEntryPage = betEntryPage.goToMixedSports();
        log("Validate UI Info display correctly");
        manualBetBetEntryPage.verifyUI();
        log("INFO: Executed completely");
    }
}

