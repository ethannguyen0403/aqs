package testcases.sb11test.restapi;

import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.restapi.RestAPI;
import pages.sb11.trading.BetSettlementPage;
import testcases.BaseCaseAQS;
import utils.sb11.sport.EventMappingUtils;
import utils.sb11.sport.EventScheduleUtils;
import utils.sb11.sport.ResultEntryUtils;
import utils.sb11.trading.BetEntrytUtils;
import utils.sb11.trading.BetSettlementUtils;
import utils.sb11.trading.ConfirmBetsUtils;
import utils.testraildemo.TestRails;

import java.util.*;

import static common.SBPConstants.*;

public class RestAPITest extends BaseCaseAQS {
    @TestRails(id="3977")
    @Parameters({"accountCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Rest_API_3977(String accountCode){
        log("@title: Validate able to send request with param eventDate");
        log("@pre-condition 1: Already having CRI bet settled for an event (e.g 23-06-2023) from any source (e.g Bet Entry)\n" +
                "The event has been mapped with event from Fair manually");
        log("@pre-condition 1.1: Create event");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String date = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00").eventStatus("Scheduled").eventDate(date)
                .isLive(false).isN(false).eventStatus("INRUNNING").build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event,provider,dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET,provider,dateMAP);
        if (Objects.isNull(eventProvider)){
            return;
        }
        EventMappingUtils.mappingEvent(eventID,eventProvider,provider,sportName);
        log("@pre-condition 1.3: Place log");
        List<Order> lstOrder = welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getHome(),"FullTime",1,0.00,
                "HK",1.00,"BACK",false,"1.00");
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,"","",accountCode);
        betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        log("@Step 1: Send request e.g https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=2023-06-07&accountCode=Cricket15A");
        JSONArray jsonArray = RestAPI.sendRequestByEventDate(dateAPI);
        log("@Step 2: Observe result");
        try {
            log("@Verify 1: Result is returned for bet settled of the event at precondition with format\n" +
                    "\n" +
                    "{\n" +
                    "    \"betfairEventId\": <event_mapped>,\n" +
                    "    \"accountCode\": \"account_place_bet\",\n" +
                    "    \"currencyCode\": \"currency_account\",\n" +
                    "    \"home\": \"home_name\",\n" +
                    "    \"away\": \"away_name\",\n" +
                    "    \"homeWinLose\": decimal value,\n" +
                    "    \"awayWinLose\": decimal value\n" +
                    "}");
            Assert.assertTrue(RestAPI.isBetSettleDisplay(jsonArray,lstOrder.get(0)),"FAILED! Result display incorrect");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(),eventProvider,provider);
            EventScheduleUtils.deleteEventByAPI(event,dateAPI);
        }
        log("INFO: Executed completely");
    }
    @TestRails(id="3978")
    @Parameters({"accountCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Rest_API_3978(String accountCode){
        log("@title: Validate able to send request with param eventDate and accountCode");
        log("@pre-condition 1: Already having CRI bet settled for an event (e.g 23-06-2023) from any source (e.g Bet Entry)\n" +
                "The event has been mapped with event from Fair manually");
        log("@pre-condition 1.1: Create event");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String date = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00")
                .eventStatus("Scheduled").eventDate(date).isLive(false).isN(false).eventStatus("INRUNNING")
                .build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event,provider,dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET,provider,dateMAP);
        if (Objects.isNull(eventProvider)){
            return;
        }
        EventMappingUtils.mappingEvent(eventID,eventProvider,provider,sportName);
        log("@pre-condition 1.3: Place betlog");
        List<Order> lstOrder = welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getHome(),"FullTime",1,0.00,
                "HK",1.00,"BACK",false,"2.00");
        ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,"","",accountCode);
        betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        log("@Step 1: Send request e.g https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=2023-06-07&accountCode=Cricket15A");
        JSONArray jsonArray = RestAPI.sendRequestByAccountCode(dateAPI,accountCode);
        log("@Step 2: Observe result");
        try {
            log("@Verify 1: Result is returned for bet settled of the event at precondition with format\n" +
                    "\n" +
                    "{\n" +
                    "    \"betfairEventId\": <event_mapped>,\n" +
                    "    \"accountCode\": \"account_place_bet\",\n" +
                    "    \"currencyCode\": \"currency_account\",\n" +
                    "    \"home\": \"home_name\",\n" +
                    "    \"away\": \"away_name\",\n" +
                    "    \"homeWinLose\": decimal value,\n" +
                    "    \"awayWinLose\": decimal value\n" +
                    "}");
            Assert.assertTrue(RestAPI.isBetSettleDisplay(jsonArray,lstOrder.get(0)),"FAILED! Result display incorrect");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(),eventProvider,provider);
            EventScheduleUtils.deleteEventByAPI(event,dateAPI);
        }
        log("INFO: Executed completely");
    }
    @TestRails(id="3979")
    @Parameters({"accountCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Rest_API_3979(String accountCode){
        log("@title: Validate result return for BT 1x2 and DNB only");
        log("@pre-condition 1: Already having CRI bet settled for an event (e.g 23-06-2023) from any source (e.g Bet Entry)\n" +
                "The event has been mapped with event from Fair manually");
        log("@pre-condition 1.1: Create event");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String date = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00")
                .eventStatus("Scheduled").eventDate(date).isLive(false).isN(false).eventStatus("INRUNNING")
                .build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event,provider,dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET,provider,dateMAP);
        if (Objects.isNull(eventProvider)){
            return;
        }
        EventMappingUtils.mappingEvent(eventID,eventProvider,provider,sportName);
        log("@pre-condition 1.3: Place bet");
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("OverUnder").marketType("OverUnder").runs(1.00).selection("Over").sport(sportName)
                .stage("FullTime").createDate(date).eventDate(dateAPI + " 23:59:00").odds(1).handicap(0.00).oddType("HK").requireStake(1.00).betType("OU")
                .winLose(2.00).isWinLose(true).build();
        BetEntrytUtils.placeBetAPI(order);
        List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(order);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        BetSettlementUtils.sendBetSettleAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        log("@Step 1: Send request e.g https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=2023-06-07&accountCode=Cricket15A");
        JSONArray jsonArray = RestAPI.sendRequestByAccountCode(dateAPI,accountCode);
        log("@Step 2: Observe result");
        try {
            log("@Verify 1: No result return");
            Assert.assertFalse(RestAPI.isBetSettleDisplay(jsonArray,lstOrder.get(0)),"FAILED! Result display incorrect");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(),eventProvider,provider);
            EventScheduleUtils.deleteEventByAPI(event,dateAPI);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="3980")
    @Parameters({"accountCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Rest_API_3980(String accountCode){
        log("@title: Validate result return for bets from Fair");
        log("@pre-condition 1: Already having CRI bet settled for an event (e.g 23-06-2023) from any source (e.g Bet Entry)\n" +
                "The event has been mapped with event from Fair manually");
        log("@pre-condition 1.1: Create event");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String date = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00").eventStatus("Scheduled")
                .eventDate(date).isLive(false).isN(false).eventStatus("INRUNNING").build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event,provider,dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET,provider,dateMAP);
        if (Objects.isNull(eventProvider)){
            return;
        }
        EventMappingUtils.mappingEvent(eventID,eventProvider,provider,sportName);
        log("@pre-condition 1.3: Place bet");
        List<Order> lstOrder = welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getHome(),"FullTime",1,0.00,
                "HK",1.00,"BACK",false,"1.00");
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        BetSettlementUtils.sendBetSettleAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        log("@Step 1: Send request e.g https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=2023-06-07&accountCode=Cricket15A");
        JSONArray jsonArray = RestAPI.sendRequestByAccountCode(dateAPI,accountCode);
        log("@Step 2: Observe result");
        try {
            log("@Verify 1: Result is returned for bet settled of the event at precondition with format\n" +
                    "\n" +
                    "{\n" +
                    "    \"betfairEventId\": <event_mapped>,\n" +
                    "    \"accountCode\": \"account_place_bet\",\n" +
                    "    \"currencyCode\": \"currency_account\",\n" +
                    "    \"home\": \"home_name\",\n" +
                    "    \"away\": \"away_name\",\n" +
                    "    \"homeWinLose\": decimal value,\n" +
                    "    \"awayWinLose\": decimal value\n" +
                    "}");
            Assert.assertTrue(RestAPI.isBetSettleDisplay(jsonArray,lstOrder.get(0)),"FAILED! Result display incorrect");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(),eventProvider,provider);
            EventScheduleUtils.deleteEventByAPI(event,dateAPI);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="3982")
    @Parameters({"accountCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Rest_API_3982(String accountCode){
        log("@title: Validate no result return if total win/lose = 0 for both Home/Away");
        log("@pre-condition 1: Already having CRI bet settled for an event (e.g 23-06-2023) from any source (e.g Bet Entry) that got result with Back Home W/L = 10, Lay Draw W/L = 10, Back Away W/L = 20\n" +
                "The event has been mapped with event from Fair manually");
        log("@pre-condition 1.1: Create event");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String date = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00").eventStatus("Scheduled")
                .eventDate(date).isLive(false).isN(false).eventStatus("INRUNNING").build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event,provider,dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET,provider,dateMAP);
        if (Objects.isNull(eventProvider)){
            return;
        }
        EventMappingUtils.mappingEvent(eventID,eventProvider,provider,sportName);
        log("@pre-condition 1.3: Set Result of Event");
        ResultEntryUtils.setResultCricket(event,"SETTLED","Home Win");
        log("@pre-condition 1.4: Place bet");
        List<Order> lstOrder = welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getHome(),"FullTime",1,0.00,
                "HK",10,"BACK",false,"10.00");
        lstOrder.add(welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting","Draw","FullTime",1,0.00,
                "HK",10,"LAY",false,"10.00").get(0));
        lstOrder.add(welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getAway(),"FullTime",1,0.00,
                "HK",20,"BACK",false,"-20.00").get(0));
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        BetSettlementUtils.sendBetSettleAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        log("@Step 1: Send request e.g https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=2023-06-07&accountCode=Cricket15A");
        JSONArray jsonArray = RestAPI.sendRequestByAccountCode(dateAPI,accountCode);
        log("@Step 2: Observe result");
        try {
            log("@Verify 1: No result return when both sum home/away w/l = 0");
            Assert.assertFalse(RestAPI.isBetSettleDisplay(jsonArray,lstOrder.get(2)),"FAILED! Result display incorrect");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(),eventProvider,provider);
            EventScheduleUtils.deleteEventByAPI(event,dateAPI);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="3983")
    @Parameters({"accountCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Rest_API_3983(String accountCode){
        log("@title: Validate win/lose shows on correct selection");
        log("@pre-condition 1: Already having CRI bet settled for an event (e.g 23-06-2023) from any source (e.g Bet Entry) that does not get result with Back Home W/L = 10, Stake = 10, Lay Draw W/L = 10, Stake = 10, Back Away W/L = 30, Stake = 10\n" +
                "The event has been mapped with event from Fair manually");
        log("@pre-condition 1.1: Create event");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String date = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00").eventStatus("Scheduled")
                .eventDate(date).isLive(false).isN(false).eventStatus("INRUNNING").build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event,provider,dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET,provider,dateMAP);
        if (Objects.isNull(eventProvider)){
            return;
        }
        EventMappingUtils.mappingEvent(eventID,eventProvider,provider,sportName);
        log("@pre-condition 1.3: Set Result of Event");
        ResultEntryUtils.setResultCricket(event,"SETTLED","Home Win");
        log("@pre-condition 1.4: Place bet");
        List<Order> lstOrder = welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getHome(),"FullTime",1,0.00,
                "HK",10,"BACK",false,"10.00");
        lstOrder.add(welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting","Draw","FullTime",1,0.00,
                "HK",10,"LAY",false,"10.00").get(0));
        lstOrder.add(welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getAway(),"FullTime",1,0.00,
                "HK",10,"BACK",false,"-10.00").get(0));
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        BetSettlementUtils.sendBetSettleAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        log("@Step 1: Send request e.g https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=2023-06-07&accountCode=Cricket15A");
        JSONArray jsonArray = RestAPI.sendRequestByAccountCode(dateAPI,accountCode);
        log("@Step 2: Observe result");
        try {
            log("@Verify 1: W/L amount show on homeWinLose cause sum stake at Home > sum stake at Away");
            Assert.assertTrue(RestAPI.getWinLose(jsonArray,lstOrder.get(2),"homeWinLose").equals("10.0"),"FAILED! Result display incorrect");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(),eventProvider,provider);
            EventScheduleUtils.deleteEventByAPI(event,dateAPI);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="3984")
    @Parameters({"accountCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Rest_API_3984(String accountCode){
        log("@title: Validate win/lose amount value shows correctly");
        log("@pre-condition 1: Already having CRI bet settled for an event (e.g 23-06-2023) from any source (e.g Bet Entry) that got result with Back Home W/L = 10, Stake = 10, Lay Draw W/L = 10, Stake = 10, Back Away W/L = -30, Stake = 30\n" +
                "The event has been mapped with event from Fair manually");
        log("@pre-condition 1.1: Create event");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String date = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00")
                .eventStatus("Scheduled").eventDate(date).isLive(false).isN(false).eventStatus("INRUNNING")
                .build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event,provider,dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET,provider,dateMAP);
        if (Objects.isNull(eventProvider)){
            return;
        }
        EventMappingUtils.mappingEvent(eventID,eventProvider,provider,sportName);
        log("@pre-condition 1.3: Set Result of Event");
        ResultEntryUtils.setResultCricket(event,"SETTLED","Home Win");
        log("@pre-condition 1.4: Place bet");
        List<Order> lstOrder = welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getHome(),"FullTime",1,0.00,
                "HK",10,"BACK",false,"10.00");
        lstOrder.add(welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting","Draw","FullTime",1,0.00,
                "HK",10,"LAY",false,"10.00").get(0));
        lstOrder.add(welcomePage.placeBetAPI(sportName,datePlace,event,accountCode,"MatchBetting","MatchBetting",event.getAway(),"FullTime",1,0.00,
                "HK",30,"BACK",false,"-30.00").get(0));
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        BetSettlementUtils.sendBetSettleAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(7);
        log("@Step 2: Select a Home Team and Away Team >> input Time value");
        log("@Step 1: Send request e.g https://aqsapi.beatus88.com/aqs-api/v1/cricket-pnl?eventDate=2023-06-07&accountCode=Cricket15A");
        JSONArray jsonArray = RestAPI.sendRequestByAccountCode(dateAPI,accountCode);
        log("@Step 2: Observe result");
        try {
            log("@Verify 1: W/L amount show on awayWinLose cause sum stake at Home > sum stake at Away");
            Assert.assertTrue(RestAPI.getWinLose(jsonArray,lstOrder.get(2),"awayWinLose").equals("-10.0"),"FAILED! Result display incorrect");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(),eventProvider,provider);
            EventScheduleUtils.deleteEventByAPI(event,dateAPI);
        }
        log("INFO: Executed completely");
    }
}
