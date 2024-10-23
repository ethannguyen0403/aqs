package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.BookieStatementPage;
import pages.sb11.generalReports.ClientStatementPage;
import pages.sb11.soccer.LeaguePerformancePage;
import pages.sb11.soccer.PerformanceByMonthPage;
import pages.sb11.soccer.SPPPage;
import pages.sb11.trading.BetSettlementPage;
import testcases.BaseCaseAQS;
import utils.sb11.master.AccountSearchUtils;
import utils.sb11.sport.EventMappingUtils;
import utils.sb11.sport.EventScheduleUtils;
import utils.sb11.trading.BetSettlementUtils;
import utils.sb11.trading.ConfirmBetsUtils;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;
import static common.SBPConstants.FINANCIAL_YEAR;

public class SPPTest extends BaseCaseAQS {
    @Test(groups = {"smoke", "ethan6.0"})
    @Parameters({"accountCode", "smartGroup", "superCode", "clientCode", "agentCode"})
    @TestRails(id = "1002")
    public void SPP_TC_1002(String accountCode, String smartGroup, String superCode, String clientCode, String agentCode) {
        /*NOTE: Create QA Smart Master and QA Smart Agent for STG and PR) for consistent data*/
        String date = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7");
        String clientValue = String.format("%s - %s", "QA2112", clientCode);
        log("@title: Validate WL in Client Statement matched with SPP page (#AQS-2073)");
        log("@Precondition: Having at least a settled bet. Group code ’" + smartGroup + " has 1 player " + accountCode + "\n" +
                "The player has data on the filtered date (e.g." + date + " \n " +
                "Client: " + clientValue + ", client agent: " + agentCode + "\n");
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "HDP", "Home", "FullTime", 1.5, -0.5, "HK", 15,
                "BACK", false, "5");
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed", date, "", "", accountCode);
        betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
        log("@Step 1: Go to Client Statement >> client point >> select the client");
        String dateFilter = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
        clientPage.filter("Client Point", KASTRAKI_LIMITED, FINANCIAL_YEAR, clientValue, "", "");

        log("@Step 2: Click the client agent >> find the player >> observe win/loss");
        String winlosePlayer = clientPage.getMemberSummary("QASAHK00", accountCode).get(7);

        log("@Step 3: Go to SPP >> select all leagues >> select the group");
        log(String.format("Step 4: Select the date %s >> click Show", date));
        SPPPage sppPage = clientPage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        sppPage.filter("All", "Group", "Smart Group", "QA Smart Master", "QA Smart Agent", dateFilter, dateFilter);
        String winloseSPP = sppPage.getRowDataOfGroup("Auto QC Group").get(sppPage.colWL);
        log("@verify 1: Validate the win/loss in the Client statement (step 2) matches with the win/loss of the group in the SPP page");
        sppPage.verifyAmountDataMatch(winlosePlayer, winloseSPP);

    }

    @Test(groups = {"smoke", "ethan6.0"})
    @Parameters({"bookieCode", "accountCode", "bookieMasterCode", "smartGroup", "bookieSuperMasterCode"})
    @TestRails(id = "311")
    public void SPP_TC_311(String bookieCode, String accountCode, String bookieMasterCode, String smartGroup, String bookieSuperMasterCode) throws InterruptedException {
        log("@title:Validate WL in Bookie Statement matched with SPP page (#AQS-2073)");
        log("@Precondition: Having at least a settled bet. Use a filter date with data Bookie: QA Bookie, Super Master: SM-QA1-QA Test, Account code: Auto-Account01");
        log("Precondition 1: Place and settled manual bet");
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, fromDate, true, accountCode, "Goals", "HDP", "Home", "FullTime", 1.5, -0.5,
                "HK", 15, "BACK", false, "5");
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed", fromDate, "", "", accountCode);
        betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
        log("@Pre-condition 2: Navigate to Trading > Bet Settlement and search bet of the account at precondition in Confirmed mode");
        log("@Step 1: Go to Bookie Statement >> filter Agent type: Super Master");
        log("@Step 2: Input bookie code as QA Bookie >> click Show");
        log("@Step 3: Find the master code: SM-QA1-QA Test >> click MS link at the master code");
        BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT, BookieStatementPage.class);
        bookieStatementPage.filter("", "", "Super Master", "", "", bookieCode, "");
        log("@Step 4: Find the player >> observe win/loss");
        String winlosePlayer = bookieStatementPage.getWinLossofPlayer(bookieSuperMasterCode, bookieMasterCode, accountCode);
        log("@Step 5: Go to SPP >> select all leagues >> select the group");
        log(String.format("Step 6: Select the date %s >> click Show", fromDate));
        log("@Step 7: Observe the win/loss of the group");
        SPPPage sppPage = bookieStatementPage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        sppPage.filter("All", "Group", "Smart Group", "QA Smart Master", "QA Smart Agent", "", "");
        String winloseSPP = sppPage.getRowDataOfGroup("Auto QC Group").get(sppPage.colWL);
        log("@verify 1: Validate the win/loss in the Client statement (step 2) matches with the win/loss of the group in the SPP page");
        sppPage.verifyAmountDataMatch(winlosePlayer, winloseSPP);
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2129")
    public void SPP_TC_2129() {
        log("@title: Validate SPP page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        log("Validate SPP page is displayed with correctly title");
        Assert.assertTrue(sppPage.getTitlePage().contains(SPP), "Failed! SPP page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan4.0"})
    @TestRails(id = "2130")
    public void SPP_TC_2130() {
        log("@title: Validate SPP page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        log("Validate UI Info display correctly");
        sppPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2131")
    public void SPP_TC_2131() {
        log("@title: Validate Tax column is displayed after checking Show Tax Amount");
        String date = String.format(DateUtils.getDate(-3, "dd/MM/yyyy", "GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        log("@Step 3: Filter with valid data");
        sppPage.filter("Soccer", "Group", "Smart Group", "[All]", "[All]", date, date);
        log("@Step 4: Check on Show Tax Amount checkbox");
        sppPage.cbShowTaxAmount.click();
        sppPage.waitSpinnerDisappeared();
        log("Validate Tax column is displayed after checking Show Tax Amount");
        Assert.assertEquals(sppPage.tblSPPTax.getHeaderNameOfRows(), SBPConstants.SPPPage.TABLE_HEADER_WITH_TAX, "FAILED! SPP Bets table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2132")
    @Parameters({"smartGroup"})
    public void SPP_TC_2132(String smartGroup) {
        log("@title: Validate League Performance page is displayed successfully when clicking on Group code");
        String fromdate = String.format(DateUtils.getDate(-5, "dd/MM/yyyy", "GMT +7"));
        String todate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        log("@Step 3: Filter with valid data");
        sppPage.filter("Soccer", "Group", "Smart Group", "QA Smart Master", "[All]", fromdate, todate);
        log("@Step 4: Click on any group code");
        LeaguePerformancePage leaguePerformancePage = sppPage.openLeaguePerformance(smartGroup);
        log("Validate League Performance is displayed correctly title");
        Assert.assertTrue(leaguePerformancePage.getTitlePage().contains("League Performance"), "Failed! League Performance page is not displayed");
        log("Validate 5 tables should displayed with format");
        String fromDateconvert = DateUtils.formatDate(fromdate, "dd/MM/yyyy", "yyyy-MM-dd");
        String toDateconvert = DateUtils.formatDate(todate, "dd/MM/yyyy", "yyyy-MM-dd");
        Assert.assertEquals(leaguePerformancePage.getTableHeaderInRange(), smartGroup + " - League Performance for " + fromDateconvert + " To " + toDateconvert);
        Assert.assertEquals(leaguePerformancePage.getTableHeader1Month(), smartGroup + " - League Performance for Last 1 Month");
        Assert.assertEquals(leaguePerformancePage.getTableHeader3Months(), smartGroup + " - League Performance for Last 3 Months");
        Assert.assertEquals(leaguePerformancePage.getTableHeader6Months(), smartGroup + " - League Performance for Last 6 Months");
        Assert.assertEquals(leaguePerformancePage.getTableHeader1Year(), smartGroup + " - League Performance for Last 1 Year");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2133")
    @Parameters({"smartGroup", "accountCurrency"})
    public void SPP_TC_2133(String smartGroup, String accountCurrency) {
        log("@title: Validate Performance by Month page is displayed succefully when clicking on MP");
        String fromdate = String.format(DateUtils.getDate(-5, "dd/MM/yyyy", "GMT +7"));
        String todate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        log("@Step 3: Filter with valid data");
        sppPage.filter("Soccer", "Group", "Smart Group", "QA Smart Master", "[All]", fromdate, todate);
        log("@Step 4: Click on any data at MP column");
        PerformanceByMonthPage performanceByMonthPage = sppPage.openPerfByMonth(smartGroup);
        log("Validate Performance By Month is displayed correctly title");
        Assert.assertTrue(performanceByMonthPage.getTitlePage().contains("Performance By Month"), "Failed! Performance By Month page is not displayed");
        log("Validate group code name is displayed correctly on header with format");
        Assert.assertEquals(performanceByMonthPage.getTableHeader(), smartGroup + " - " + accountCurrency + " - Last 12 Month Performance");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan2.0"})
    @TestRails(id = "2134")
    @Parameters({"smartGroup", "accountCode"})
    public void SPP_TC_2134(String smartGroup, String accountCode) {
        log("@title: Validate Account PT Performance page is displayed succefully when clicking on PT");
        // TODO: There is improvement AQS-4104
        Assert.assertTrue(false, "FAILED! There is an improvement AQS-4104");
//        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
//        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
//        log("@Step 1: Login with valid account");
//        log("@Step 2: Access Soccer > SPP");
//        SPPPage sppPage = welcomePage.navigatePage(SOCCER,SPP,SPPPage.class);
//        log("@Step 3: Filter with valid data");
//        sppPage.filter("Soccer", "Group","Smart Group","QA Smart Master","[All]",fromdate,todate);
//        String PT = sppPage.getRowDataOfGroup(smartGroup).get(sppPage.colPT-1);
//        log("@Step 4: Click on any data at PT column");
//        PTPerformancePopup ptPerformancePopup = sppPage.openAccountPTPerf(smartGroup);
//        log("Validate Account PT Performance page is displayed correctly title");
//        Assert.assertTrue(ptPerformancePopup.getTitlePage().contains("Account PT Performance"), "Failed! PT Performance page is not displayed");
//        log("Validate group code name is displayed correctly smart group name on header");
//        Assert.assertTrue(ptPerformancePopup.isGroupNameDisplayed(smartGroup),"Failed! Group name "+ smartGroup + " is not displayed!");
//        log("Validate PT% on SPP page is matched with PT% on Account PT Performance page");
//        Assert.assertTrue(ptPerformancePopup.isAccountPTMatched(accountCode,PT),"Failed! PT is not matched!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.10.31", "ethan5.0"})
    @TestRails(id = "2795")
    @Parameters({"accountCode", "smartGroup"})
    public void SPP_TC_2795(String accountCode, String smartGroup) {
        log("@title: Validate the Cricket Manual Bets display properly");
        log("@pre-condition 1: SPP permission is ON");
        log("@pre-condition 1: There are some placed and settled MB from Bet Entry > Mixed Sport page");
        String creatDate = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00")
                .eventStatus("Scheduled").eventDate(datePlace).isLive(false).isN(false).eventStatus("INRUNNING")
                .build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event, provider, dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET, provider, dateMAP);
        if (eventProvider == null) {
            System.out.println("There are not event in Provider today");
            return;
        }
        EventMappingUtils.mappingEvent(eventID, eventProvider, provider, sportName);
        try {
            log("@pre-condition 1.3: Place betlog");
            List<Order> lstOrder = welcomePage.placeBetAPI(sportName, datePlace, event, accountCode, "MatchBetting", "MatchBetting", event.getHome(), "FullTime", 1, 0.00,
                    "HK", 1.00, "BACK", false, "2.00");
            ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
            BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
            String fromDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
            betSettlementPage.filter("Confirmed", fromDate, "", "", accountCode);
            betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
            log("@Step 1: Go to Soccer >> SPP page");
            SPPPage page = betSettlementPage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
            log("@Step 2: Select Cricket Sport and filter date that settled bets at precondition");
            page.filter(sportName, "Group", "Smart Group", "QA Smart Master", "", fromDate, "");
            log("@Step 3: Open League Performance by Smart Group Name");
            LeaguePerformancePage leaguePerformancePage = page.openLeaguePerformance(smartGroup);
            log("@Verify 1: The Cricket Manual Bets display properly");
            fromDate = DateUtils.formatDate(fromDate, "dd/MM/yyyy", "yyyy-MM-dd");
            Assert.assertEquals(leaguePerformancePage.getTableHeaderInRange(), smartGroup + " - League Performance for " + fromDate + " To " + creatDate);
            log("INFO: Executed completely");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(), eventProvider, provider);
            EventScheduleUtils.deleteEventByAPI(event, dateAPI);
        }
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2796")
    @Parameters({"smartGroup", "accountCode"})
    public void SPP_TC_2796(String smartGroup, String accountCode) {
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String apiCurrentDate = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        log("@title: Validate the total bets of Cricket Manual Bets will display at MB column");
        log("Precondition: There are some placed and settled MB from Bet Entry >> Mixed Sport page of any account");
        String sportName = "Cricket";
        String leagueName = "QA Cricket Auto League";
        String dateAPI = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +8");
        String datePlace = DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +8");
        String provider = "MERITO";
        Event event = new Event.Builder().sportName(sportName).leagueName(leagueName).eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("18:00")
                .eventStatus("Scheduled").eventDate(datePlace).isLive(false).isN(false).eventStatus("INRUNNING")
                .build();
        EventScheduleUtils.addEventByAPI(event, dateAPI, SPORT_ID_MAP.get(sportName));
        log("@pre-condition 1.2: mapping event");
        String dateMAP = DateUtils.getDate(-1, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(event, provider, dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET, provider, dateMAP);
        if (eventProvider == null) {
            System.out.println("There are not event in Provider today");
            return;
        }
        EventMappingUtils.mappingEvent(eventID, eventProvider, provider, sportName);
        try {
            log("@pre-condition 1.3: Place betlog");
            List<Order> lstOrder = welcomePage.placeBetAPI(sportName, datePlace, event, accountCode, "MatchBetting", "MatchBetting", event.getHome(), "FullTime", 1, 0.00,
                    "HK", 1.00, "BACK", false, "2.00");
            ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
            BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
            betSettlementPage.filter("Confirmed", datePlace, "", "", accountCode);
            betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
            log("@Step 1: Login with valid account");
            log("@Step 2: Access Soccer > SPP");
            SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
            log("@Step 3: Filter with Cricket sport");
            sppPage.filter("Cricket", "Group", "Smart Group", "QA Smart Master", "[All]", "", currentDate);

            log("@Verify 1: Validate all Cricket Manual Bets display properly");
            int mBCricketBets = BetSettlementUtils.getListDoubleOfSettledBestJson("stake", apiCurrentDate, apiCurrentDate, accountCode, accountId, SPORT_ID_MAP.get("Cricket")).size();
            List<String> dataRowTable = sppPage.getRowDataOfGroup(smartGroup);
            Assert.assertEquals(mBCricketBets, Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("Bets") - 1)), "FAILED! The filer Cricket MB is not correct");
            log("INFO: Executed completely");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(), eventProvider, provider);
            EventScheduleUtils.deleteEventByAPI(event, dateAPI);
        }
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2797")
    @Parameters({"accountCode", "smartGroup"})
    public void SPP_TC_2797(String accountCode, String smartGroup) {
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String previousDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        log("@title: Validate the Wins/Lose/Draw of Cricket Manual Bets displays correctly");
        log("Precondition: There are some placed and settled MB from Bet Entry >> Mixed Sport page of any account");
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
        String eventID = EventMappingUtils.getEventID(event, provider, dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET, provider, dateMAP);
        if (eventProvider == null) {
            System.out.println("There are not event in Provider today");
            return;
        }
        EventMappingUtils.mappingEvent(eventID, eventProvider, provider, sportName);
        try {
            log("@pre-condition 1.3: Place betlog");
            List<Order> lstOrder = welcomePage.placeBetAPI(sportName, previousDate, event, accountCode, "MatchBetting", "MatchBetting", event.getHome(), "FullTime", 1, 0.00,
                    "HK", 1.00, "BACK", false, "2.00");
            ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
            BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
            betSettlementPage.filter("Confirmed", previousDate, "", "", accountCode);
            betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
            log("@Step 1: Login with valid account");
            log("@Step 2: Access Soccer > SPP");
            SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
            log(String.format("@Step 3: Filter with Smart Group: %s, and Bet Types: MB", smartGroup));
            sppPage.filter("Cricket", "Group", "Smart Group", "QA Smart Master", "[All]", previousDate, currentDate);
            log("@Verify 1: Validate The Wins of Cricket Manual Bets displays correct Win = the amount value >0 (If bet is win, the Win column > 0)");
            List<String> dataRowTable = sppPage.getRowDataOfGroup(smartGroup);
            Assert.assertTrue(Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("Wins") - 1)) > 0, "FAILED!  The Wins of Cricket Manual Bets is not correct");
            log("INFO: Executed completely");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(), eventProvider, provider);
            EventScheduleUtils.deleteEventByAPI(event, dateAPI);
        }
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2798")
    @Parameters({"accountCode", "smartGroup"})
    public void SPP_TC_2798(String accountCode, String smartGroup) {
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String previousDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        String apiCurrentDate = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
        String apiPreviousDate = DateUtils.formatDate(previousDate, "dd/MM/yyyy", "yyyy-MM-dd");
        String accountId = AccountSearchUtils.getAccountId(accountCode);

        log("@title: Validate the correct Avg Stake and Turnover of Cricket Manual Bets displays");
        log("Precondition:There are some placed and settled MB from Bet Entry >> Mixed Sport page of any account");
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
        String eventID = EventMappingUtils.getEventID(event, provider, dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET, provider, dateMAP);
        if (eventProvider == null) {
            System.out.println("There are not event in Provider today");
            return;
        }
        EventMappingUtils.mappingEvent(eventID, eventProvider, provider, sportName);
        try {
            log("@pre-condition 1.3: Place betlog");
            List<Order> lstOrder = welcomePage.placeBetAPI(sportName, previousDate, event, accountCode, "MatchBetting", "MatchBetting", event.getHome(), "FullTime", 1, 0.00,
                    "HK", 1.00, "BACK", false, "2.00");
            ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
            BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
            betSettlementPage.filter("Confirmed", previousDate, "", "", accountCode);
            betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
            log("@Step 1: Login with valid account");
            log("@Step 2: Access Soccer > SPP");
            SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
            log(String.format("@Step 3: Filter with Smart Group: %s, and Bet Types: MB", smartGroup));
            sppPage.filter("Cricket", "Group", "Smart Group", "QA Smart Master", "[All]", previousDate, currentDate);
            log("@Verify 1: Validate the correct Avg Stake and Turnover of Cricket Manual Bets displays");
            List<Double> stakeSettledBet = BetSettlementUtils.getListDoubleOfSettledBestJson("stake", apiPreviousDate, apiCurrentDate, accountCode, accountId, SPORT_ID_MAP.get("Cricket"));
            List<String> dataRowTable = sppPage.getRowDataOfGroup(smartGroup);
            int avg = sppPage.calculateAvg(stakeSettledBet);
            int turnOver = sppPage.calculateTotal(stakeSettledBet);
            Assert.assertEquals(Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("Avg Stake") - 1)), avg, "FAILED! Average stake is not correct");
            Assert.assertEquals(Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("Turnover") - 1)), turnOver, "FAILED! Turn Over is not correct");
            log("INFO: Executed completely");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(), eventProvider, provider);
            EventScheduleUtils.deleteEventByAPI(event, dateAPI);
        }
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2799")
    @Parameters({"accountCode", "smartGroup"})
    public void SPP_TC_2799(String accountCode, String smartGroup) {
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String previousDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        String apiCurrentDate = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
        String apiPreviousDate = DateUtils.formatDate(previousDate, "dd/MM/yyyy", "yyyy-MM-dd");
        String accountId = AccountSearchUtils.getAccountId(accountCode);

        log("@title: Validate the correct total WL displays");
        log("Precondition:There are some placed and settled MB from Bet Entry >> Mixed Sport page of any account");
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
        String eventID = EventMappingUtils.getEventID(event, provider, dateMAP);
        event.setEventId(eventID);
        Event eventProvider = EventMappingUtils.getFirstProviderEvent(CRICKET, provider, dateMAP);
        if (eventProvider == null) {
            System.out.println("There are not event in Provider today");
            return;
        }
        EventMappingUtils.mappingEvent(eventID, eventProvider, provider, sportName);
        try {
            log("@pre-condition 1.3: Place betlog");
            List<Order> lstOrder = welcomePage.placeBetAPI(sportName, previousDate, event, accountCode, "MatchBetting", "MatchBetting", event.getHome(), "FullTime", 1, 0.00,
                    "HK", 1.00, "BACK", false, "2.00");
            ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
            BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
            betSettlementPage.filter("Confirmed", previousDate, "", "", accountCode);
            betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
            log("@Step 1: Login with valid account");
            log("@Step 2: Access Soccer > SPP");
            SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
            log(String.format("@Step 3: Filter with Smart Group: %s, and Bet Types: MB", smartGroup));
            sppPage.filter("Cricket", "Group", "Smart Group", "QA Smart Master", "[All]", previousDate, currentDate);
            log("@Verify 1: Validate the correct Avg Stake and Turnover of Cricket Manual Bets displays");
            List<Double> wLSettledBet = BetSettlementUtils.getListDoubleOfSettledBestJson("winLose", apiPreviousDate, apiCurrentDate, accountCode, accountId, SPORT_ID_MAP.get("Cricket"));
            List<String> dataRowTable = sppPage.getRowDataOfGroup(smartGroup);
            int wLTotal = sppPage.calculateTotal(wLSettledBet);
            Assert.assertEquals(Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("W/L") - 1)), wLTotal, "FAILED! Win/Lose is not correct");
            log("INFO: Executed completely");
        } finally {
            EventMappingUtils.unMappingEvent(event.getEventId(), eventProvider, provider);
            EventScheduleUtils.deleteEventByAPI(event, dateAPI);
        }
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2800")
    @Parameters({"smartGroup"})
    public void SPP_TC_2800(String smartGroup) {
        log("@title: Validate the correct %WL displays");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String previousDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        log(String.format("@Step 3: Filter with Smart Group: %s, and Bet Types: MB", smartGroup));
        sppPage.filter(CRICKET, "Group", "Smart Group", "QA Smart Master", "[All]", previousDate, currentDate);
        log("@Verify 1: Validate Correct Win% = (W/L*100) / Turnover displays");
        List<String> dataRowTable = sppPage.getRowDataOfGroup(smartGroup);
        int wLPercent = Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("W/L") - 1)) * 100 / Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("Turnover") - 1));
        Assert.assertEquals(Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("Win%") - 1)), wLPercent,1, "FAILED! The value of WL% Cricket MB is not correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2801")
    @Parameters({"accountCode", "smartGroup"})
    public void SPP_TC_2801(String accountCode, String smartGroup) {
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String previousDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        String apiCurrentDate = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
        String apiPreviousDate = DateUtils.formatDate(previousDate, "dd/MM/yyyy", "yyyy-MM-dd");
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        int mBCricketBets = BetSettlementUtils.getListDoubleOfSettledBestJson("stake", apiPreviousDate, apiCurrentDate, accountCode, accountId, SPORT_ID_MAP.get("Cricket")).size();
        log("@title: Validate can filter by MB bet types");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER, SPP,"All", SPPPage.class);
        log(String.format("@Step 3: Filter with Smart Group: %s, and Bet Types: MB", smartGroup));
        sppPage.filter(CRICKET, "Group", "Smart Group", "QA Smart Master", "[All]", previousDate, currentDate);
        log("@Verify 1: Validate all Cricket Manual Bets display properly");
        List<String> dataRowTable = sppPage.getRowDataOfGroup(smartGroup);
        Assert.assertEquals(Integer.valueOf(dataRowTable.get(sppPage.tblSPP.getColumnIndexByName("Bets") - 1)), mBCricketBets, "FAILED! The filer Cricket MB is not correct");
        log("INFO: Executed completely");
    }

}
