package testcases.sb11test.trading;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import objects.Event;
import objects.Order;
import objects.Team;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.sport.CricketLeagueSeasonTeamInfoPage;
import pages.sb11.sport.CricketResultEntryPage;
import pages.sb11.sport.EventSchedulePage;
import pages.sb11.sport.popup.ConfirmDeleteLeaguePopup;
import pages.sb11.sport.popup.CreateCricketLeaguePopup;
import pages.sb11.sport.popup.CreateCricketTeamPopup;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.BetSettlementPage;
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.CricketBetEntryPage;
import pages.sb11.trading.popup.BetListPopup;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.SBPConstants.*;
import static common.SBPConstants.BetSettlement.BET_LIST_STATEMENT_EMAIL;

public class CricketSportTest extends BaseCaseAQS {
    String leagueName = "QA Cricket Auto League";
    String country = "Asia";
    String sportName = "Cricket";
    @TestRails(id = "61")
    @Test(groups = {"regression","2023.11.30"})
    public void Cricket_Sport_61() {
        log("Validate users can add cricket leagues");
        log("@pre-condition: Account is activated permission");
        log("@Step 1: Login to SB11 >> Go to League/Season/Team Info");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("@Step 2: Click + icon to open the create dialog");
        CreateCricketLeaguePopup createCricketLeaguePopup = cricketLeagueSeasonTeamInfoPage.openAddLeaguePopup();
        log("@Step 3: Input valid values >> Click Submit");
        String leagueName = "Auto League";
        try {
            createCricketLeaguePopup.addLeague(leagueName, leagueName, country, "", "", true,true,true);
            log("Verify 1: Message 'Please input valid Account Code.' displays");
            cricketLeagueSeasonTeamInfoPage.filterLeague("All",country,leagueName);
            cricketLeagueSeasonTeamInfoPage.isLeagueDisplayed(leagueName);
        } finally {
            ConfirmDeleteLeaguePopup delPopup = cricketLeagueSeasonTeamInfoPage.openDeleteLeaguePopup(leagueName);
            delPopup.deleteLeague(true);
        }

        log("INFO: Executed completely");
    }
    @TestRails(id = "62")
    @Test(groups = {"regression","2023.11.30"})
    public void Cricket_Sport_62() {
        log("Validate users can add Team on Cricket leagues");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: There is an existing Cricket league");
        log("@Step 1: Login to SB11 >> Go to League/Season/Team Info");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("@Step 2: Filter and select the league >> Click + icon at 'Team List' to open the create dialog");
        cricketLeagueSeasonTeamInfoPage.filterLeague("All",country,leagueName);
        log("@Step 3: Input valid values >> Click Submit");
        CreateCricketTeamPopup createTeamPopup = cricketLeagueSeasonTeamInfoPage.openAddTeamPopup(leagueName,"");
        Team team = new Team.Builder()
                .teamName("QA Auto Team 1")
                .country("Other")
                .build();
        try {
            createTeamPopup.addNewTeam(team,true);
            log("Validate The team is created successfully and displayed in the list");
            Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.isTeamDisplayed(leagueName,"",team.getTeamName()),"FAILED! The team is not created");
        } finally {
            log("@post-condition: Delete Team");
            cricketLeagueSeasonTeamInfoPage.deleteTeamName(leagueName,"",team.getTeamName());
            log("@post-condition: Validate The team is deleted successfully");
            Assert.assertFalse(cricketLeagueSeasonTeamInfoPage.isTeamDisplayed(leagueName,"",team.getTeamName()),"FAILED! The team is not deleted");
        }
        log("INFO: Executed completely");
    }
    @TestRails(id = "63")
    @Test(groups = {"regression","2023.11.30"})
    public void Cricket_Sport_63() {
        log("Validate users can schedule events");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: There is an existing Cricket league");
        log("@Step 1: Login to SB11 >> Go to Even Schedule >> select the league");
        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.goToSport(sportName);
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        Event event = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(date)
                .home("Auto Team 1")
                .away("Auto Team 2")
                .openTime("17:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        log("@Step 3: Select status as Schedule >> Click Submit");
        eventSchedulePage.showLeague(event.getLeagueName(),event.getEventDate());
        eventSchedulePage.addEvent(event);
        log("Verify 1: The event is scheduled successfully and displayed in the schedule list.");
        Assert.assertTrue(eventSchedulePage.getSuccessMessage().contains("Event schedule is created successfully"),"FAILED! Success message is incorrect displayed");
        eventSchedulePage.showScheduleList(leagueName,true,event.getHome(),date);
        Assert.assertTrue(eventSchedulePage.verifyEventInSchedulelist(event),"Failed! Event info incorrect after created");
        log("@Step 4: Go to Bet Entry and Result Entry >> find the event");
        BetEntryPage betEntryPage = eventSchedulePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        cricketBetEntryPage.showLeague(COMPANY_UNIT,date,leagueName);
        log("@Verify 2: The event is able to find in Result Entry and Bet Entry");
        Assert.assertTrue(cricketBetEntryPage.isLeagueExist(leagueName),"FAILED! League "+ leagueName+" does not display in the list");
        Assert.assertTrue(cricketBetEntryPage.isEventExist(event), "FAILED! Event "+event.getHome() +" & "+ event.getAway()+" under league "+ leagueName+" does not display in the list");
        log("@Postcondition: Delete the event");
        eventSchedulePage = cricketBetEntryPage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.deleteEvent(event);
        log("INFO: Executed completely");
    }
    @TestRails(id = "64")
    @Test(groups = {"regression","2023.11.30"})
    public void Cricket_Sport_64() {
        log("Validate users can set result for an event");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: There is an existed Cricket league and event");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        Event event = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(date)
                .home("Auto Team 1")
                .away("Auto Team 2")
                .openTime("17:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        event = welcomePage.createEvent(event);
        log("@Step 1: Go to Result Entry >> select a date >> click Show Leagues >> Select a league");
        CricketResultEntryPage cricketResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, CricketResultEntryPage.class);
        cricketResultEntryPage.goToSport(sportName);
        cricketResultEntryPage.filterResult("Normal",date,leagueName,"KOT","All",true);
        log("@Step 2: Input Runs, Wkts for team A & B >> select Result >> Click Submit");
        cricketResultEntryPage.submitEvent(event,"","","151","5","156","7","");
        log("Verify 1: The inputted values save successfully");
        String expect = "Update "+event.getHome()+" -vs- "+event.getAway()+" successfully.";
        Assert.assertEquals(cricketResultEntryPage.getSuccessMessage(),expect);
        log("@Postcondition: Delete the event");
        EventSchedulePage eventSchedulePage = cricketResultEntryPage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.deleteEvent(event);
        log("INFO: Executed completely");
    }
    @TestRails(id = "65")
    @Test(groups = {"regression","2023.11.30"})
    @Parameters({"accountCode","accountCurrency"})
    public void Cricket_Sport_65 (String accountCode, String accountCurrency) {
        log("Validate users can place 1x2 bet with selection as Home");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: There is an existed Cricket league and event");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        Event event = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(date)
                .home("Auto Team 1")
                .away("Auto Team 2")
                .openTime("17:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        log("@Step 1: Login to SB11");
        log("@Step 2: Go to Bet Entry >> Cricket >> select a date");
        event = welcomePage.createEvent(event);
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("@Step 3: Select the league >> input an account code >> click Show");
        cricketBetEntryPage.showLeague(COMPANY_UNIT,date,event.getLeagueName());
        log("@Step 4: Click …. At 1x2 Home >> observe the dialog");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(event.getSportName())
                .isNegativeHdp(false)
                .price(2.00)
                .requireStake(100)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("1x2")
                .selection(event.getHome())
                .handicapRuns(9)
                .handicapWtks(10)
                .isLive(false)
                .home(event.getHome())
                .away((event.getAway()))
                .event(event)
                .build();
        lstOrder.add(order);
        log("Verify 1: Validate the info in the bet slip displays correctly.");
        log("@Step 5: Input odds as 2 >> input stake as 100");
        log("@Step 6: Click 'Place Bet'");
        cricketBetEntryPage.placeBet(order,true);
        log("Verify 2: The bet is placed successfully");
        Assert.assertTrue(cricketBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+cricketBetEntryPage.getSuccessMessage());
        BetListPopup betListPopup = cricketBetEntryPage.openBetList(event.getHome());
        order = betListPopup.verifyOrderInfoDisplay(order,CRICKET_MARKET_TYPE_BET_LIST.get(order.getMarketType()),"");
        betListPopup.close();
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        log("@Step 7: Go to Confirm Bets");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(COMPANY_UNIT,"","Pending",event.getSportName(),"All","Specific Date",date,"",accountCode);
        log("Verify 3: The bet is able to find on the 'Pending' mode and displays with correct info.");
        confirmBetsPage.verifyOrder(lstOrder.get(0));
        log("@Postcondition: Delete the event");
        EventSchedulePage eventSchedulePage = confirmBetsPage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.deleteEvent(event);
        log("INFO: Executed completely");
    }
    @TestRails(id = "68")
    @Test(groups = {"regression","2023.11.30"})
    @Parameters({"accountCode","accountCurrency"})
    public void Cricket_Sport_68 (String accountCode, String accountCurrency) {
        log("Validate users can place 1x2 bet with selection as Away");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: There is an existed Cricket league and event");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        Event event = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(date)
                .home("Auto Team 1")
                .away("Auto Team 2")
                .openTime("17:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        log("@Step 1: Login to SB11");
        log("@Step 2: Go to Bet Entry >> Cricket >> select a date");
        event = welcomePage.createEvent(event);
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("@Step 3: Select the league >> input an account code >> click Show");
        cricketBetEntryPage.showLeague(COMPANY_UNIT,date,event.getLeagueName());
        log("@Step 4: Click …. At 1x2 Away");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(event.getSportName())
                .isNegativeHdp(false)
                .price(2.00)
                .requireStake(100)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("1x2")
                .selection(event.getAway())
                .handicapRuns(9)
                .handicapWtks(10)
                .isLive(false)
                .home(event.getHome())
                .away((event.getAway()))
                .event(event)
                .build();
        lstOrder.add(order);
        log("Verify 1: Validate the info in the bet slip displays correctly.");
        log("@Step 5: Input odds as 2 >> input stake as 100");
        log("@Step 6: Click 'Place Bet'");
        cricketBetEntryPage.placeBet(order,true);
        log("Verify 2: The bet is placed successfully");
        Assert.assertTrue(cricketBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+cricketBetEntryPage.getSuccessMessage());
        BetListPopup betListPopup = cricketBetEntryPage.openBetList(event.getHome());
        order = betListPopup.verifyOrderInfoDisplay(order,CRICKET_MARKET_TYPE_BET_LIST.get(order.getMarketType()),"");
        betListPopup.close();
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        log("@Step 7: Go to Confirm Bets");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(COMPANY_UNIT,"","Pending",event.getSportName(),"All","Specific Date",date,"",accountCode);
        log("Verify 3: The bet is able to find on the 'Pending' mode and displays with correct info.");
        confirmBetsPage.verifyOrder(lstOrder.get(0));
        log("@Postcondition: Delete the event");
        EventSchedulePage eventSchedulePage = confirmBetsPage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.deleteEvent(event);
        log("INFO: Executed completely");
    }
    @TestRails(id = "66")
    @Test(groups = {"regression","2023.11.30"})
    @Parameters({"accountCode","accountCurrency","emailAddress","clientCode"})
    public void Cricket_Sport_66 (String accountCode, String accountCurrency, String emailAddress, String clientCode) throws InterruptedException {
        log("Validate 1x2 back bets settled correctly");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: The account placed 2 back bets on the settled event with bet detail\n" +
                "Bet 1 was placed on the Home selection, 1x2, stake = 100, odds = 2\n" +
                "Bet 2 was placed on the Away selection, 1x2, stake = 100, odds = 2\n" +
                "The event settled with result as Home Win");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        Event event = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(date)
                .home("Auto Team 1")
                .away("Auto Team 2")
                .openTime("15:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        event = welcomePage.createEvent(event);
        CricketResultEntryPage cricketResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY,CricketResultEntryPage.class);
        cricketResultEntryPage.goToSport(sportName);
        cricketResultEntryPage.filterResult("Normal",date,leagueName,"KOT","All",true);
        cricketResultEntryPage.submitEvent(event,"","","","","","",ResultEntry.RESULT_TYPE.get(1));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("@Step 3: Select the league >> input an account code >> click Show");
        cricketBetEntryPage.showLeague(COMPANY_UNIT,date,event.getLeagueName());
        log("@Step 4: Click …. at selection Draw");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(event.getSportName())
                .isNegativeHdp(false)
                .price(2.00)
                .requireStake(100)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("1x2")
                .selection(event.getHome())
                .isLive(false)
                .home(event.getHome())
                .away((event.getAway()))
                .event(event)
                .build();
        Order order1 = new Order.Builder()
                .sport(event.getSportName())
                .isNegativeHdp(false)
                .price(2.00)
                .requireStake(100)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("1x2")
                .selection(event.getAway())
                .isLive(false)
                .home(event.getHome())
                .away(event.getAway())
                .event(event)
                .build();
        lstOrder.add(order);
        lstOrder.add(order1);
        cricketBetEntryPage.placeBet(order,true);
        cricketBetEntryPage.placeBet(order1,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        log("@Step 1: Go to Confirm Bets");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(COMPANY_UNIT,"","Pending",event.getSportName(),"All","Specific Date",date,"",accountCode);
        confirmBetsPage.confirmMultipleBets(lstOrder);
        log("@Step 2: Go to Bet Settlement >> search the account >> observe the win/lose amount");
        BetSettlementPage betSettlementPage = confirmBetsPage.navigatePage(TRADING,BET_SETTLEMENT,BetSettlementPage.class);
        Thread.sleep(5000);
        betSettlementPage.filter("Confirmed",date,date,"",accountCode);
        log("Verify 1: Bet 1 : Win/Lose = 100\n" +
                "Bet 2 : Win/Lose = -50");
        Assert.assertEquals(betSettlementPage.getWinlossAmountofOrder(lstOrder.get(0)),"200");
        Assert.assertEquals(betSettlementPage.getWinlossAmountofOrder(lstOrder.get(1)),"-100");
        log("@Step 3: Select the bets >> click Settle and Send Settlement Email");
        betSettlementPage.settleAndSendSettlementEmail(order);
        log("Verify 2: Bets disappear from Confirm and move to the Settled section.");
        betSettlementPage.filter("Settled", date, date,"", accountCode);
        betSettlementPage.verifyOrderInfo(order);

        List<ArrayList<String>> emailInfo = betSettlementPage.getFirstActiveMailBox("https://yopmail.com/",emailAddress);
        List<String> expectedRow1 = Arrays.asList("Member Code: "+accountCode,"Member Name: "+accountCode);
        log("@Verify 3 .Information of Description, Selection, HDP, Live, Price, Stake, Win/Lose, Type, Date, Total Win, C/F (displayed), Balance show correctly");
        Assert.assertEquals(emailInfo.get(0).get(0),"Statement of Account for the Account "+accountCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(1),"Mr "+ clientCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(2),"Please find enclosed statement for account "+accountCode,"Failed! title of email is incorrect");
        Assert.assertTrue(emailInfo.get(0).get(3).contains(String.format("Therefore the amount + %s", accountCurrency)) ,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(4),"This amount shall be KIV to the next period.","Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(1), BET_LIST_STATEMENT_EMAIL, "Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(3).get(0), expectedRow1.get(0), "Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(3).get(1), expectedRow1.get(1), "Failed! title of email is incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id = "67")
    @Test(groups = {"regression","2023.11.30"})
    @Parameters({"accountCode","accountCurrency","emailAddress","clientCode"})
    public void Cricket_Sport_67 (String accountCode, String accountCurrency, String emailAddress, String clientCode) throws InterruptedException {
        log("Validate 1x2 lay bets settled correctly");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: The account placed 2 back bets on the settled event with bet detail\n" +
                "Bet 1 was placed on the Home selection, 1x2, stake = 100, odds = 2\n" +
                "Bet 2 was placed on the Away selection, 1x2, stake = 100, odds = 2\n" +
                "The event settled with result as Home Win");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        Event event = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(date)
                .home("Auto Team 1")
                .away("Auto Team 2")
                .openTime("17:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        event = welcomePage.createEvent(event);
        CricketResultEntryPage cricketResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY,CricketResultEntryPage.class);
        cricketResultEntryPage.goToSport(sportName);
        cricketResultEntryPage.filterResult("Normal",date,leagueName,"KOT","All",true);
        cricketResultEntryPage.submitEvent(event,"","","","","","",ResultEntry.RESULT_TYPE.get(2));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("@Step 3: Select the league >> input an account code >> click Show");
        cricketBetEntryPage.showLeague(COMPANY_UNIT,date,event.getLeagueName());
        log("@Step 4: Click …. at selection Draw");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(event.getSportName())
                .isNegativeHdp(false)
                .price(2.00)
                .requireStake(100)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("1x2")
                .selection(event.getHome())
                .isLive(false)
                .home(event.getHome())
                .away((event.getAway()))
                .event(event)
                .build();
        Order order1 = new Order.Builder()
                .sport(event.getSportName())
                .isNegativeHdp(false)
                .price(2.00)
                .requireStake(100)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("1x2")
                .selection(event.getAway())
                .isLive(false)
                .home(event.getHome())
                .away(event.getAway())
                .event(event)
                .build();
        lstOrder.add(order);
        lstOrder.add(order1);
        cricketBetEntryPage.placeBet(order,true);
        cricketBetEntryPage.placeBet(order1,true);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        log("@Step 1: Go to Confirm Bets");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(COMPANY_UNIT,"","Pending",event.getSportName(),"All","Specific Date",date,"",accountCode);
        confirmBetsPage.confirmMultipleBets(lstOrder);
        log("@Step 2: Go to Bet Settlement >> search the account >> observe the win/lose amount");
        BetSettlementPage betSettlementPage = confirmBetsPage.navigatePage(TRADING,BET_SETTLEMENT,BetSettlementPage.class);
        Thread.sleep(5000);
        betSettlementPage.filter("Confirmed",date,date,"",accountCode);
        log("Verify 1: Bet 1 : Win/Lose = 100\n" +
                "Bet 2 : Win/Lose = -50");
        Assert.assertEquals(betSettlementPage.getWinlossAmountofOrder(lstOrder.get(0)),"-100");
        Assert.assertEquals(betSettlementPage.getWinlossAmountofOrder(lstOrder.get(1)),"200");
        log("@Step 3: Select the bets >> click Settle and Send Settlement Email");
        betSettlementPage.settleAndSendSettlementEmail(order);
        log("Verify 2: Bets disappear from Confirm and move to the Settled section.");
        betSettlementPage.filter("Settled", date, date,"", accountCode);
        betSettlementPage.verifyOrderInfo(order);

        List<ArrayList<String>> emailInfo = betSettlementPage.getFirstActiveMailBox("https://yopmail.com/",emailAddress);
        List<String> expectedRow1 = Arrays.asList("Member Code: "+accountCode,"Member Name: "+accountCode);
        log("@Verify 3 .Information of Description, Selection, HDP, Live, Price, Stake, Win/Lose, Type, Date, Total Win, C/F (displayed), Balance show correctly");
        Assert.assertEquals(emailInfo.get(0).get(0),"Statement of Account for the Account "+accountCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(1),"Mr "+ clientCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(2),"Please find enclosed statement for account "+accountCode,"Failed! title of email is incorrect");
        Assert.assertTrue(emailInfo.get(0).get(3).contains(String.format("Therefore the amount + %s", accountCurrency)) ,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(4),"This amount shall be KIV to the next period.","Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(1), BET_LIST_STATEMENT_EMAIL, "Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(3).get(0), expectedRow1.get(0), "Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(3).get(1), expectedRow1.get(1), "Failed! title of email is incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id = "156")
    @Test(groups = {"regression","2023.11.30"})
    @Parameters({"accountCode","accountCurrency"})
    public void Cricket_Sport_156 (String accountCode, String accountCurrency) {
        log("Validate users can place bet on 1x2 market with selection as Draw");
        log("@pre-condition 1: Account is activated permission");
        log("@pre-condition 2: There is an existed Cricket league and event");
        String date = DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        Event event = new Event.Builder()
                .sportName(sportName)
                .leagueName(leagueName)
                .eventDate(date)
                .home("Auto Team 1")
                .away("Auto Team 2")
                .openTime("15:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();
        log("@Step 1: Login to SB11");
        log("@Step 2: Go to Bet Entry >> Cricket >> select a date");
        event = welcomePage.createEvent(event);
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
        log("@Step 3: Select the league >> input an account code >> click Show");
        cricketBetEntryPage.showLeague(COMPANY_UNIT,date,event.getLeagueName());
        log("@Step 4: Click …. at selection Draw");
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(event.getSportName())
                .isNegativeHdp(false)
                .price(2.00)
                .requireStake(100)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType("1x2")
                .selection("Draw")
                .handicapRuns(9)
                .handicapWtks(10)
                .isLive(false)
                .home(event.getHome())
                .away(event.getAway())
                .event(event)
                .build();
        lstOrder.add(order);
        log("Verify 1: Validate the info in the bet slip displays correctly.");
        log("@Step 5: Input odds as 2 >> input stake as 100");
        log("@Step 6: Click 'Place Bet'");
        cricketBetEntryPage.placeBet(order,true);
        log("Verify 2: The bet is placed successfully");
        Assert.assertTrue(cricketBetEntryPage.getSuccessMessage().contains(PLACE_BET_SUCCESS_MSG), "Failed! Success message after place bet is incorrect Actual is "+cricketBetEntryPage.getSuccessMessage());
        BetListPopup betListPopup = cricketBetEntryPage.openBetList(event.getHome());
        order = betListPopup.verifyOrderInfoDisplay(order,CRICKET_MARKET_TYPE_BET_LIST.get(order.getMarketType()),"");
        betListPopup.close();
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        log("@Step 7: Go to Confirm Bets");
        ConfirmBetsPage confirmBetsPage = cricketBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(COMPANY_UNIT,"","Pending",event.getSportName(),"All","Specific Date",date,"",accountCode);
        log("Verify 3: The bet is able to find on the 'Pending' mode and displays with correct info.");
        confirmBetsPage.verifyOrder(lstOrder.get(0));
        log("@Postcondition: Delete the event");
        EventSchedulePage eventSchedulePage = confirmBetsPage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.deleteEvent(event);
        log("INFO: Executed completely");
    }
    @TestRails(id = "339")
    @Test(groups = {"regression","2023.11.30"})
    @Parameters({"password"})
    public void Cricket_Sport_339(String password) throws Exception {
        String accountNoPermission = "onerole";
        log("Validate accounts without permission cannot access the Bet Entry page");
        log("@pre-condition: There is an account that has no permission on the Bet Entry page");
        log("@Step 1: Login to SB11 >> select Trading menu");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(accountNoPermission, StringUtils.decrypt(password));
        log("Verify 1: The Bet Entry page does not exsist");
        List<String> lstSubMenu = welcomePage.headerMenuControl.getListSubMenu();
        Assert.assertFalse(lstSubMenu.contains(BET_ENTRY),"FAILED! Bet Entry displayed incorrect!");
        log("INFO: Executed completely");
    }
    @TestRails(id = "340")
    @Test(groups = {"regression","2023.11.30"})
    public void Cricket_Sport_340() {
        log("Validate invalid accounts could not place bet");
        log("@Step 1: Login to SB11 >> select Bet Entry >> select Cricket");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        CricketBetEntryPage page = betEntryPage.goToCricket();
        log("@Step 2: Input invalid account code");
        page.txtAccountCode.sendKeys("123");
        log("@Step 3: Click Show");
        page.showLeague(COMPANY_UNIT,"","All");
        log("Verify 1: Message 'Please input valid Account Code.' displays");
        Assert.assertTrue(page.lblMsgInvalid.isDisplayed(),"FAILED! Message display incorrect!");
        log("INFO: Executed completely");
    }

}
