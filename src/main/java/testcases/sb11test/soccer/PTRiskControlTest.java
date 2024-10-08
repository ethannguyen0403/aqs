package testcases.sb11test.soccer;

import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.PTRiskPage;
import pages.sb11.soccer.popup.PTRiskBetListPopup;
import pages.sb11.trading.ConfirmBetsPage;
import testcases.BaseCaseAQS;
import utils.sb11.master.AccountListUtils;
import utils.sb11.sport.EventMappingUtils;
import utils.sb11.sport.EventScheduleUtils;
import utils.sb11.trading.AccountPercentUtils;
import utils.sb11.trading.BetSettlementUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.List;

import static common.SBPConstants.*;

public class PTRiskControlTest extends BaseCaseAQS {
    @Test(groups = {"smoke", "ethan4.0"})
    @Parameters({"clientCode", "accountCode"})
    @TestRails(id = "1386")
    public void PTRiskControlTC_1386(String clientCode, String accountCode) throws IOException {
        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (HK)");
        log("Precondition: Having account with Pending HDP/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        //Configurate Account Percent
        String superMasterCode = "QA2112 - ";
        Double percent = 1.5;
        AccountPercentUtils.setAccountPercentAPI(accountCode, clientCode, superMasterCode, percent);
        clientCode = superMasterCode + clientCode;
        //Place bet HDP
        String date = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "HDP", "Home", "FullTime", 1.6, 1.75, "HK",
                12, "BACK", false, "");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode, KASTRAKI_LIMITED, SOCCER, "Normal", "All", "", "", lstOrder.get(0).getEvent().getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(lstOrder.get(0).getEvent().getHome());
        String actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent), actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type HK on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("29", "-9", "-18", true));
    }

    @Test(groups = {"smoke", "ethan4.0"})
    @Parameters({"clientCode", "accountCode"})
    @TestRails(id = "192")
    public void PTRiskControlTC_192(String clientCode, String accountCode) throws IOException {
        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (EU)");
        log("Precondition: Having account with Pending HPD/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        String date = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7");
        //Set Account Percent
        String superMasterCode = "QA2112 - ";
        String actualPTVal;
        Double percent = 1.5;
        AccountPercentUtils.setAccountPercentAPI(accountCode, clientCode, superMasterCode, percent);

        clientCode = superMasterCode + clientCode;

        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "HDP", "Home", "FullTime", 2.0, 1.75, "EU",
                12, "BACK", false, "");
        //Wait for Bet update in PT page
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode, KASTRAKI_LIMITED, SOCCER, "Normal", "All", "", "", lstOrder.get(0).getEvent().getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(lstOrder.get(0).getEvent().getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent), actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type EU on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("18", "-9", "-18", true));
    }

    @Test(groups = {"smoke", "ethan4.0"})
    @Parameters({"clientCode", "accountCode"})
    @TestRails(id = "1387")
    public void PTRiskControlTC_1387(String clientCode, String accountCode) throws IOException {
        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (MY)");
        log("Precondition: Having account with Pending HPD/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        String date = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7");
        //Set account percent
        String superMasterCode = "QA2112 - ";
        Double percent = 1.5;
        AccountPercentUtils.setAccountPercentAPI(accountCode, clientCode, superMasterCode, percent);
        String actualPTVal;
        clientCode = superMasterCode + clientCode;

        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "HDP", "Home", "FullTime", 0.5, 1.75, "MY",
                12, "BACK", false, "");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode, KASTRAKI_LIMITED, SOCCER, "Normal", "All", "", "", lstOrder.get(0).getEvent().getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(lstOrder.get(0).getEvent().getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent), actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type MY on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("9", "-9", "-18", true));
    }

    @Test(groups = {"smoke", "ethan4.0"})
    @Parameters({"clientCode", "accountCode"})
    @TestRails(id = "1388")
    public void PTRiskControlTC_1388(String clientCode, String accountCode) throws IOException {
        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (ID)");
        log("Precondition: Having account with Pending HPD/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        String date = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7");
        String superMasterCode = "QA2112 - ";
        String actualPTVal;
        Double percent = 1.5;
        AccountPercentUtils.setAccountPercentAPI(accountCode, clientCode, superMasterCode, percent);
        clientCode = superMasterCode + clientCode;

        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "HDP", "Home", "FullTime", 1.5, 1.75, "ID",
                12, "BACK", false, "");
        //Wait for Bet update in PT page
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode, KASTRAKI_LIMITED, SOCCER, "Normal", "All", "", "", lstOrder.get(0).getEvent().getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(lstOrder.get(0).getEvent().getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent), actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type ID on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("27", "-9", "-18", true));
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2123")
    public void PTRiskControlTC_2123() {
        log("@title: Validate PT Risk Control page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        log("Validate PT Risk Control page is displayed with correctly title");
        Assert.assertTrue(ptRiskPage.getTitlePage().contains(PT_RISK_CONTROL), "Failed! PT Risk Control page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan4.0"})
    @TestRails(id = "2124")
    public void PTRiskControlTC_2124() {
        log("@title: Validate UI on PT Risk Control is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        log(" Validate UI Info display correctly");
        ptRiskPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan3.0"})
    @Parameters({"accountCode", "accountCurrency"})
    @TestRails(id = "2125")
    public void PTRiskControlTC_2125(String accountCode, String accountCurrency) {
        log("@title: Validate Full Time Handicap bet is displayed correctly on PT Risk Control > Handicap tab");
        log("@Pre-condition: Having an Full Time Handicap bet which have been placed on Bet Entry");
        String date = String.format(DateUtils.getDate(0, "dd/MM/yyyy", "GMT +7"));
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "HDP", "Home", "FullTime", 2.15, 1, "HK", 15.5, "BACK", false, "");
        lstOrder.get(0).setAccountCurrency(accountCurrency);
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition > Click Show");
        String dateFilter = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        ptRiskPage.filter("", KASTRAKI_LIMITED, SOCCER, "Normal", "All", dateFilter, "", lstOrder.get(0).getEvent().getLeagueName());
        log("@Step 4: Click on event name > Click Handicap tab");
        PTRiskBetListPopup ptRiskBetListPopup = ptRiskPage.openBetList(lstOrder.get(0).getEvent().getHome());
        ptRiskBetListPopup.activeTab("Handicap");
        log("Validate Full Time Handicap bet is displayed correctly on PT Risk Control > Handicap tab");
        Assert.assertTrue(ptRiskBetListPopup.verifyOrder(lstOrder.get(0)));
        log("INFO: Executed completely");
    }

    @TestRails(id = "2128")
    @Test(groups = {"regression", "ethan6.0"})
    public void PTRiskControlTC_2128() {
        log("@title: Validate that can copy report successfully");
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        log("@Step 3: Click Copy Report");
        ptRiskPage.filter("","","","","",date,date,"");
        ptRiskPage.lblNoRecord.waitForControlInvisible();
        ptRiskPage.btnCopy.click();
        log("Message success should display correctly as \"Copied!\"");
        Assert.assertEquals(ptRiskPage.messageSuccess.getText(), "Copied", "Failed! Copy button is not worked");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2126")
    @Test(groups = {"regression", "ethan5.0"})
    @Parameters({"accountCode", "accountCurrency"})
    public void PTRiskControlTC_2126(String accountCode, String accountCurrency) {
        log("@title: Validate Full Time Over Under bet is displayed correctly on PT Risk Control > Over Under tab");
        log("@Precondition Step:  Having an Full Time Over Under bet which have been placed on Bet Entry");
        String date = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", "GMT +7"));
        log("@Precondition Step:  Having Half time Handicap bet which have been placed on Bet Entry");
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "OU", "Over", "FullTime", 2.12, 1, "HK", 5.5,
                "BACK", false, "");
        lstOrder.get(0).setAccountCurrency(accountCurrency);
        log("@Step 1: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        log("@Step 2: Filter with event that having bet at Pre-condition");
        ptRiskPage.filter("", KASTRAKI_LIMITED, SOCCER, "Normal", "All", date, "", lstOrder.get(0).getEvent().getLeagueName());
        log("@Step 3: Click on event name");
        PTRiskBetListPopup ptRiskBetListPopup = ptRiskPage.openBetList(lstOrder.get(0).getEvent().getHome());
        log("@Step 4: Click Over Under tab");
        ptRiskBetListPopup.activeTab("Over Under");
        log("@Verify 1: Validate Full Time Over Under bet is displayed correctly on PT Risk Control > Over Under tab");
        Assert.assertTrue(ptRiskBetListPopup.verifyOrder(lstOrder.get(0)));
        log("INFO: Executed completely");
    }

    @TestRails(id = "2127")
    @Test(groups = {"regression", "ethan6.0"})
    @Parameters({"accountCode", "accountCurrency"})
    public void PTRiskControlTC_2127(String accountCode, String accountCurrency) {
        log("@title: Validate Half time Handicap bet is displayed and disappear when place bet in Bet Entry then removed");
        int dateNo = -1;
        String smartMaster = "QA Smart Master";
        String date = String.format(DateUtils.getDate(dateNo, "dd/MM/yyyy", GMT_7));

        log("@Precondition Step:  Having Half time Handicap bet which have been placed on Bet Entry");
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, date, true, accountCode, "Goals", "HDP", "Home", "HalfTime", 2.12, 0.5, "HK", 5.5,
                "BACK", false, "");
        lstOrder.get(0).setAccountCurrency(accountCurrency);
        BetSettlementUtils.waitForBetIsUpdate(10);
        log("@Step 1: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);

        log("@Step 2: Filter with the event that has bet at Pre-condition");
        ptRiskPage.filter("", KASTRAKI_LIMITED, SOCCER, "Normal", "All", date, date, lstOrder.get(0).getEvent().getLeagueName());
        ptRiskPage.filterSmartMaster(smartMaster);
        log("@Step 3: Click on the event name");
        PTRiskBetListPopup ptRiskBetListPopup = ptRiskPage.openBetList(lstOrder.get(0).getEvent().getHome());

        log("@Step 4: Click the Half-time tab and verify bet info is correctly");
        ptRiskBetListPopup.activeTab("Half-time");

        log("Verify 1: Verify Half time Handicap bet info is correct when the bet is placed from Bet Entry");
        Assert.assertTrue(ptRiskBetListPopup.verifyOrderHafttime(lstOrder.get(0)), "Failed Order info is incorrect");
        ptRiskBetListPopup.closeBetListPopup();

        log("@Step 5: Access Trading > Confirm Bet");
        ConfirmBetsPage confirmBetsPage = ptRiskPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED, "", "Pending", "Soccer", "", "Specific Date", date, date, accountCode);
        log("@Step 6: Filter the account place bet and remove this bet");
        confirmBetsPage.deleteOrder(lstOrder.get(0), true);

        log("@Step 7: Access Soccer > PT Risk Control and filter the event and click on the event then check on Haft-Time info");
        ptRiskPage = confirmBetsPage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);

        log("Verify 2: verify Bet is removed when bet is removed in Confirm bet");
        ptRiskPage.filter("", KASTRAKI_LIMITED, SOCCER, "Normal", "All", date, date, "");
        ptRiskPage.filterSmartMaster(smartMaster);
        Assert.assertTrue(ptRiskPage.verifyRemoveBet(lstOrder.get(0)), "FAILED! Bet still display");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3415")
    @Test(groups = {"regression", "2023.12.29"})
    public void PTRiskControlTC_3415() {
        log("@title: Validate placed Tennis bet from Merito is displayed correctly in PT Risk Control");
        log("@Precondition: Having a Merito account that is already mapped with SB11\n" +
                "Mapped Merito account is already placed some Tennis bet");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        log("@Step 3: Filter with valid data: Sport Tennis, Company Fair, Date 22/05/2023");
        ptRiskPage.filter("", "Fair", "Tennis", "Normal", "All", "22/05/2023", "22/05/2023", "");
        log("@Step 4: Open Bet list");
        PTRiskBetListPopup betListPopup = ptRiskPage.openBetList("Dom Stricker");
        log("Verify 2: Validate data of mapped Merito account is correct");
        Assert.assertTrue(!betListPopup.getBetListCellValue("JO20000000", betListPopup.colStake).isEmpty(), "FAILED! No data of mapped account");
    }

    @TestRails(id = "3416")
    @Test(groups = {"regression", "2023.10.31", "ethan5.0"})
    @Parameters({"accountCode"})
    public void PTRiskControlTC_3416(String accountCode) {
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String dateAPI = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
        log("@title: Validate can filter by Basket ball 1x2 matched bets ");
        log("@Precondition: There are some matched Basket ball 1x2 matched bets from Pinnacle/BetISN/Fair or Bet Entry page");
        log("@Precondition-Step 1: Have a specific League Name, Home Team, Away Team for testing line\n" +
                "League: QA Basketball League\n" +
                "Home Team: QA Basketball Team 1\n" +
                "Away Team: QA Basketball Team 2");
        Event eventBasketball =
                new Event.Builder().sportName("Basketball").leagueName("QA Basketball League").eventDate(currentDate)
                        .home("QA Basketball Team 1").away("QA Basketball Team 2")
                        .openTime("17:00").eventStatus("InRunning").isLive(true).isN(false).build();
        EventScheduleUtils.addEventByAPI(eventBasketball, dateAPI, SPORT_ID_MAP.get("Basketball"));
        welcomePage.waitSpinnerDisappeared();
        String leagueID = EventScheduleUtils.getLeagueID(eventBasketball.getLeagueName(), SPORT_ID_MAP.get("Basketball"));
        String eventID = EventScheduleUtils.getEventID(dateAPI, leagueID);
        eventBasketball.setEventId(eventID);
        log("@Precondition-Step 2: Place some Basketball 1x2 match bets");
        try {
            List<Order> lstOrder = welcomePage.placeBetAPI("Basketball", currentDate, eventBasketball, accountCode, "MatchOdds", "MatchOdds", eventBasketball.getHome(), "FullTime", 2.15, 0, "HK",
                    15.50, "BACK", false, "");
            log("@Step 1: Navigate to Soccer > PT Risk Control");
            PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
            ptPage.waitSpinnerDisappeared();
            log("@Step 2: Filter with Report Type = Normal with League and Client placed bet");
            ptPage.filter("", KASTRAKI_LIMITED, "Basketball", "PT + Throw Bets", "All", "", "", eventBasketball.getLeagueName());

            log("Verify 1: Validate Basket ball bets return properly");
            PTRiskBetListPopup ptRiskBetListPopup = ptPage.openBetList(eventBasketball.getHome());
            Assert.assertTrue(ptRiskBetListPopup.getBetListCellValue(accountCode, 1).contains(lstOrder.get(0).getBetId()), "FAILED!Bet not shown properly.");
            log("INFO: Executed test completely");
        } finally {
            log("@Post-condition: Deleted the event Basketball: " + eventID);
            EventScheduleUtils.deleteEventByAPI(eventBasketball, dateAPI);
            log("INFO: Executed Pos-condition completely");
        }
    }

    @TestRails(id = "3417")
    @Test(groups = {"regression", "2023.10.31", "ethan5.0"})
    @Parameters({"accountCode"})
    public void PTRiskControlTC_3417(String accountCode) {
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String dateAPI = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
        log("@title: Validate Bet Types only shows option '1x2'");
        log("@Precondition: There are some matched Basket ball 1x2 matched bets from Pinnacle/BetISN/Fair or Bet Entry page");
        log("@Precondition-Step 1: Have a specific League Name, Home Team, Away Team for testing line\n" +
                "League: QA Basketball League\n" +
                "Home Team: QA Basketball Team 1\n" +
                "Away Team: QA Basketball Team 2");
        String provider = "MERITO";
        Event eventBasketball =
                new Event.Builder().sportName("Basketball").leagueName("QA Basketball League").eventDate(currentDate)
                        .home("QA Basketball Team 1").away("QA Basketball Team 2")
                        .openTime("17:00").eventStatus("InRunning").isLive(true).isN(false).build();
        EventScheduleUtils.addEventByAPI(eventBasketball, dateAPI, SPORT_ID_MAP.get("Basketball"));
        welcomePage.waitSpinnerDisappeared();
        String dateMAP = DateUtils.getDate(0, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(eventBasketball, provider, dateMAP);
        eventBasketball.setEventId(eventID);
        log("@Precondition-Step 2: Place some Basketball 1x2 match bets");
        try {
            welcomePage.placeBetAPI("Basketball", currentDate, eventBasketball, accountCode, "MatchOdds", "MatchOdds", eventBasketball.getHome(), "FullTime", 2.15, 0, "HK",
                    15.50, "BACK", false, "");
            log("@Step 2: Navigate to Soccer > PT Risk Control");
            PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
            ptPage.waitSpinnerDisappeared();
            log("@Step 3: Filter with League and Client placed bet");
            String dateFilter = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
            ptPage.filter("", KASTRAKI_LIMITED, "Basketball", "PT + Throw Bets", "All", dateFilter, "", eventBasketball.getLeagueName());
            log("Verify 1: Validate Bet Types only shows option '1x2'");
            Assert.assertTrue(ptPage.isBetTypeBasketIs1X2(), "FAILED! Bet type of Basketball is not correct");
            log("INFO: Executed test completely");
        } finally {
            log("@Post-condition: Deleted the event Basketball: ");
            EventScheduleUtils.deleteEventByAPI(eventBasketball, dateAPI);
            log("INFO: Executed Pos-condition completely");
        }
    }

    @TestRails(id = "3418")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"accountCode"})
    public void PTRiskControlTC_3418(String accountCode) {
        //TODO having improvement AQS-4080
        Assert.assertTrue(false, "FAILED! Need to revise because improvement AQS-4080");
//        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
//        String dateAPI = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
//        log("@title: Validate forecast table displays the correct value");
//        log("@Precondition: There are some matched Basket ball 1x2 matched bets from Pinnacle/BetISN/Fair or Bet Entry page");
//        log("@Precondition-Step 1: Have a specific League Name, Home Team, Away Team for testing line\n" +
//                "League: QA Basketball League\n" +
//                "Home Team: QA Basketball Team 1\n" +
//                "Away Team: QA Basketball Team 2");
//        Event eventBasketball =
//                new Event.Builder().sportName("Basketball").leagueName("QA Basketball League").eventDate(currentDate)
//                        .home("QA Basketball Team 1").away("QA Basketball Team 2")
//                        .openTime("17:00").eventStatus("InRunning").isLive(true).isN(false).build();
//        Order orderBasketball = new Order.Builder().sport("Basketball").price(2.15).requireStake(15.50).oddType("HK").betType("Back")
//                .selection(eventBasketball.getHome()).isLive(false).event(eventBasketball).accountCode(accountCode).build();
//        EventScheduleUtils.addEventByAPI(eventBasketball, dateAPI, SPORT_ID_MAP.get("Basketball"));
//        log("@Precondition-Step 2: Place some Basketball 1x2 match bets");
//        try {
//            BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
//            BasketballBetEntryPage basketballBetEntryPage = betEntryPage.goToBasketball();
//            basketballBetEntryPage.showLeague(KASTRAKI_LIMITED, "", eventBasketball.getLeagueName(), accountCode);
//            basketballBetEntryPage.placeBet(orderBasketball, "1x2", orderBasketball.getSelection());
//            log("@Step 1: Navigate to Soccer > PT Risk Control");
//            PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
//            ptPage.waitSpinnerDisappeared();
//
//            log("@Step 2: Filter with Report Type = PT + Throw Bets, League and Client placed bet");
//            ptPage.filter("Basketball", "", KASTRAKI_LIMITED, "PT + Throw Bets", "All", "", "", eventBasketball.getLeagueName());
//            log("Verify 1: Forecast win/loss amount at 'X' selection always shows as 0");
//            Map<String, String> entryValuePTRiskThrows =
//                    ptPage.getEntriesValueOfTableSport(eventBasketball.getLeagueName(), eventBasketball.getHome(), "1", "2");
//            Assert.assertEquals(entryValuePTRiskThrows.get("x"), "0",
//                    "FAILED! Forecast win/loss amount at 'X' selection NOT shows as 0");
//            log("Verify 2: Validate Forecast PT + Throw Bets is correct");
//            PTRiskBetListPopup ptRiskBetListPopup = ptPage.openBetList(eventBasketball.getHome());
//            Assert.assertEquals(entryValuePTRiskThrows.get("1"),
//                    "-" + String.valueOf(ptRiskBetListPopup.getAmountForeCastHK1X2InBetSlip("1", "PT + Throw Bets")),
//                    "FAILED! Amount forecast 1 is not correct");
//            Assert.assertEquals(entryValuePTRiskThrows.get("2"),
//                    String.valueOf(ptRiskBetListPopup.getAmountForeCastHK1X2InBetSlip("2", "PT + Throw Bets")),
//                    "FAILED! Amount forecast 2 is not correct");
//            ptRiskBetListPopup.closeBetListPopup();
//
//            log("@Step 3: Filter with Report Type = Normal, League and Client placed bet");
//            ptPage.filter("Basketball", "", KASTRAKI_LIMITED, "Normal", "All", "", "", eventBasketball.getLeagueName());
//            String headerRowIndex = "1";
//            String valueRowIndex = "2";
//            Map<String, String> entryValuePTRiskNormal =
//                    ptPage.getEntriesValueOfTableSport(eventBasketball.getLeagueName(), eventBasketball.getHome(), headerRowIndex, valueRowIndex);
//            ptRiskBetListPopup = ptPage.openBetList(eventBasketball.getHome());
//            log("Verify 3: Validate Forecast Normal is correct");
//            Assert.assertEquals(entryValuePTRiskNormal.get("x"), "0",
//                    "FAILED! Forecast win/loss amount at 'X' selection NOT shows as 0");
//            Assert.assertEquals(entryValuePTRiskNormal.get("1"),
//                    String.valueOf(ptRiskBetListPopup.getAmountForeCastHK1X2InBetSlip("1", "Normal")),
//                    "FAILED! Amount forecast 1 is not correct");
//            Assert.assertEquals(entryValuePTRiskNormal.get("2"),
//                    "-" + String.valueOf(ptRiskBetListPopup.getAmountForeCastHK1X2InBetSlip("2", "Normal")),
//                    "FAILED! Amount forecast 2 is not correct");
//        } finally {
//            log("@Post-condition: Deleted the event Basketball: ");
//            EventScheduleUtils.deleteEventByAPI(eventBasketball,dateAPI);
//            log("INFO: Executed Pos-condition completely");
//        }
    }

    @TestRails(id = "3419")
    @Test(groups = {"regression_stg", "2023.10.31", "ethan3.0"})
    @Parameters({"accountCode"})
    public void PTRiskControlTC_3419(String accountCode) throws IOException {
        String percent = "6";
        String currentDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        String dateAPI = DateUtils.formatDate(currentDate, "dd/MM/yyyy", "yyyy-MM-dd");
        log("@title: Validate the Bet list displays the correct value ");
        log("@Precondition: There are some matched Basket ball 1x2 matched bets from Pinnacle/BetISN/Fair or Bet Entry page");
        log("@Precondition-Step 1: Have a specific League Name, Home Team, Away Team for testing line\n" +
                "League: QA Basketball League\n" +
                "Home Team: QA Basketball Team 1\n" +
                "Away Team: QA Basketball Team 2");
        String provider = "MERITO";
        Event eventBasketball =
                new Event.Builder().sportName("Basketball").leagueName("QA Basketball League").eventDate(currentDate)
                        .home("QA Basketball Team 1").away("QA Basketball Team 2")
                        .openTime("17:00").eventStatus("InRunning").isLive(true).isN(false).build();
        EventScheduleUtils.addEventByAPI(eventBasketball, dateAPI, SPORT_ID_MAP.get("Basketball"));
        String dateMAP = DateUtils.getDate(0, "yyyy-MM-dd HH:mm:ss", GMT_7);
        String eventID = EventMappingUtils.getEventID(eventBasketball, provider, dateMAP);
        eventBasketball.setEventId(eventID);
        log("@Precondition-Step 2: Set % PT of Basketball on Account List");
        AccountListUtils.setAccountListPTAPI(accountCode, percent, true, AccountListUtils.SportName.basketball);
        log("@Precondition-Step 3: Place some Basketball 1x2 match bets");
        try {
            welcomePage.placeBetAPI("Basketball", currentDate, eventBasketball, accountCode, "MatchOdds", "MatchOdds", eventBasketball.getHome(), "FullTime", 2.15, 0, "HK",
                    15.50, "BACK", false, "");
            log("@Step 1: Navigate to Soccer > PT Risk Control");
            PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
            ptPage.waitSpinnerDisappeared();
            log("@Step 2: Filter with League and Client placed bet");
            String dateFilter = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
            ptPage.filter("", KASTRAKI_LIMITED, "Basketball", "PT + Throw Bets", "All", dateFilter, "", eventBasketball.getLeagueName());
            log("@Step 3: Open Bet List");
            PTRiskBetListPopup ptRiskBetListPopup = ptPage.openBetList(eventBasketball.getHome());
            log("Verify 1: There will be only 1 tab '1x2'");
            Assert.assertTrue(ptRiskBetListPopup.lblTabList.getWebElements().size() == 1 &&
                    Label.xpath(String.format(ptRiskBetListPopup.lblTabxPath, "1x2")).isDisplayed(), "FAILED! The tab 1x2 is not correct");
            log("Verify 2: PT% column shows PT% setting if an account that placed bets, has a PT% setting on account list");
            List<String> listPTValue = ptRiskBetListPopup.tblBetList.getColumn(ptRiskBetListPopup.tblBetList.getColumnIndexByName("PT%"), false);
            Assert.assertTrue(listPTValue.contains(String.format("CO: %s%%", percent)), "FAILED! The PT value is missing on bet list");
        } finally {
            log("@Post-condition: Deleted the event Basketball: ");
            EventScheduleUtils.deleteEventByAPI(eventBasketball, dateAPI);
            log("INFO: Executed Pos-condition completely");
        }
    }
}
