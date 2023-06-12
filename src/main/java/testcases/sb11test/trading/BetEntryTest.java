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
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;
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
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);

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
        lstOrder.add(order);

        log("@Step 4: Input account at precondition on 'Account Code' field");
        log("@Step 5: Click on '.......' of any event > select handicap value with inputting odds and stake");
        log("@Step 6: In the first row Handicap input the required fields (Handicap _,+, handicap point, price, odds type, bet type, live score, stake)");
        log("@Step 7: Click Place Bet without select \"option copy bet to SPBPS7same odds\" and \"copy bet to SPBPS7minus odds\"");
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);

        log("@Verify 1: User can place Soccer bets successfully with message 'The bet was placed successfully'");
        Assert.assertTrue(soccerBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+soccerBetEntryPage.getSuccessMessage());

        log("@Step 7: Click 'Bets' at SPB column of event at step 5 > observe" +order.getBetId());
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        lstOrder = betListPopup.verifyListOrderInfoDisplay(lstOrder,"Handicap","");
        // lstOrder = GetSoccerEventUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder,eventInfo.getEventId());
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ lstOrder.get(0).getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
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
        String companyUnit = "Kastraki Limited";
        log("@Step 1: Login to SB11 site");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = new Event.Builder()
                .sportName("Cricket")
                .leagueName("QA League")
                .eventDate(dateAPI)
                .home("QA Team 01")
                .away("QA Team 02")
                .openTime("13:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();

        log("@Step prepare data: Add event for QA League in today local time and can filter in today in Trading>Bet Entry");
        eventInfo =  welcomePage.createEvent(eventInfo);

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
        BetListPopup betListPopup = cricketBetEntryPage.openBetList(eventInfo.getHome());

        log("@Verify 2: Bets information is displayed correctly in Bet List");
        order = betListPopup.verifyOrderInfoDisplay(order,CRICKET_MARKET_TYPE_BET_LIST.get(order.getMarketType()),"");
        betListPopup.close();

        log("@Post-Condition: Cancel Pending bet "+ order.getBetId() +" in Confirm Bet page");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",eventInfo.getSportName(),"All","Specific Date","","",accountCode);
        confirmBetsPage.deleteOrder(order,true);

        log("INFO: Executed completely");
    }

    @TestRails(id="191")
    @Test(groups = {"smoke"})
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
    @Test(groups = {"smoke_qc"})
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
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Get the first Event of Frist League of Today Soccer");
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);

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
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String accountSPBPS7 = "SPBPS7";

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Get the first Event of Frist League of Today Soccer");
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);

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
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.14).requireStake(15.50)
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
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountSPBPS7);
        confirmBetsPage.deleteOrder(lstOrder.get(1),true);


        log("INFO: Executed completely");
    }

    @TestRails(id="868")
    @Test(groups = {"smoke_qc"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetEntry_TC868(String accountCode,String accountCurrency){
        log("@title: Validate can place bet for soccer with option copy bet to SPBPS7 as same odds");
        log("Precondition: User has permission to access Bet Entry page");
        log("Precondition:Having a valid account that can place bets (e.g. "+accountCode);
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String marketType = "1x2";

        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);

        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();

        log("@Step Precondition: Get the first Event of First League of Today Soccer");
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);

        log("@Step Precondition: Define order to place bet");
        Order order = new Order.Builder()
                .sport(sport)
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
        log("@Step 5: Click (+) at More column of according event > select handicap value with inputting odds and stake");
        SoccerSPBBetSlipPopup soccerSPBBetSlipPopup = soccerBetEntryPage.openSPBBetSlip(accountCode,eventInfo.getHome());

        log("@Step 6: Click Place Bet with Tick on option \"Tick here to Copy Bet to SPBPS7 Minus 0.01 Odds\"");
        soccerSPBBetSlipPopup.placeMoreBet(order,false,false,true);

        log("@Verify 1: User can place Soccer bets successfully with message 'The bet was placed successfully'");
        Assert.assertTrue(soccerBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+soccerBetEntryPage.getSuccessMessage());

        log("@Step 8: Click 'Bets' at SPB column of event at step 6 > observe");
        BetListPopup betListPopup = soccerBetEntryPage.openBetList(eventInfo.getHome());

        Order order1 = new Order.Builder()
                .sport(sport)
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
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.deleteOrder(order,true);

        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2189")
    public void BetEntry_TC_001(){
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
    @Test(groups = {"regression1"})
    @TestRails(id = "2190")
    public void BetEntry_TC_002(){
        log("Validate UI on Bet Entry for Soccer is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Soccer' > select any League > click Show");
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Choose League, Search By");
        Assert.assertEquals(soccerBetEntryPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertTrue(soccerBetEntryPage.ddpLeague.getOptions().contains("Club Friendly"),"Failed! League dropdown is not displayed!");
        Assert.assertTrue(soccerBetEntryPage.ddpSearchBy.getOptions().contains("Code"),"Failed! Search By dropdown is not displayed!");
        log("Textbox: Account Code");
        Assert.assertTrue(soccerBetEntryPage.lblAccountCode.getText().contains("Account Code"),"Failed! Account Code textbox is not displayed!");
        log("Datetimepicker: Date");
        Assert.assertEquals(soccerBetEntryPage.lblDate.getText(),"Date","Failed! Date datetimepicker is not displayed!");
        log("Button: Show button");
        Assert.assertEquals(soccerBetEntryPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2191")
    public void BetEntry_TC_003(){
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

    @Test(groups = {"regression1"})
    @TestRails(id = "2192")
    public void BetEntry_TC_004(){
        log("Validate UI on Bet Entry for Cricket is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Cricket' > select any League > click Show");
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Choose League, Search By");
        Assert.assertEquals(cricketBetEntryPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertTrue(cricketBetEntryPage.ddpLeague.getOptions().contains("Club Friendly"),"Failed! League dropdown is not displayed!");
        Assert.assertTrue(cricketBetEntryPage.ddpSearchBy.getOptions().contains("Code"),"Failed! Search By dropdown is not displayed!");
        log("Textbox: Account Code");
        Assert.assertTrue(cricketBetEntryPage.lblAccountCode.getText().contains("Account Code"),"Failed! Account Code textbox is not displayed!");
        log("Datetimepicker: Date");
        Assert.assertEquals(cricketBetEntryPage.lblDate.getText(),"Date","Failed! Date datetimepicker is not displayed!");
        log("Button: Show button");
        Assert.assertEquals(cricketBetEntryPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2193")
    public void BetEntry_TC_005(){
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

    @Test(groups = {"regression1"})
    @TestRails(id = "2194")
    public void BetEntry_TC_006(){
        log("Validate UI on Bet Entry for Mixed Sport is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Mixed Sport' > select any League > click Show");
        ManualBetBetEntryPage manualBetBetEntryPage = betEntryPage.goToMixedSports();
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Sport, Selection, OddsType");
        Assert.assertEquals(manualBetBetEntryPage.ddCompany.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.ddSport.getOptions(), SBPConstants.BetEntryPage.SPORT_LIST,"Failed! Sport dropdown is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.ddBetType.getOptions(), SBPConstants.BetEntryPage.BET_TYPE,"Failed! Bet type dropdown is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.ddBetType.getOptions(), SBPConstants.BetEntryPage.ODD_TYPE,"Failed! Odd type dropdown is not displayed!");
        log("Textbox: Account Code, Bet Description, Selection, Bet type, Odds, Stake, WinLoss");
        Assert.assertTrue(manualBetBetEntryPage.lblAccountCode.getText().contains("Account Code"),"Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblClient.getText(),"Client","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblBalance.getText(),"Balance","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblBetDescription.getText(),"Bet Description","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblSelection.getText(),"Selection","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblBetType.getText(),"Bet type","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblOdds.getText(),"Odds","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblStake.getText(),"Stake","Failed! Account Code textbox is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.lblWinLoss.getText(),"Win/Loss or Comm Amount","Failed! Account Code textbox is not displayed!");
        log("Datetimepicker: Date");
        Assert.assertEquals(manualBetBetEntryPage.lblDate.getText(),"Date","Failed! Date datetimepicker is not displayed!");
        log("Button: Show button, Place Bet button");
        Assert.assertTrue(manualBetBetEntryPage.btnSearch.isDisplayed(),"Failed! Search button is not displayed!");
        Assert.assertEquals(manualBetBetEntryPage.btnPlaceBet,"Place bet","Failed! Place bet button is not displayed!");
        log("INFO: Executed completely");
    }
}

