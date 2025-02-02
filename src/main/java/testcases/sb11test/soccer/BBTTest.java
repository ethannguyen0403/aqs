package testcases.sb11test.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.soccer.*;
import pages.sb11.soccer.BBTPage;
import pages.sb11.trading.*;
import pages.sb11.trading.BetEntryPage;
import testcases.BaseCaseAQS;
import utils.sb11.role.RoleManagementUtils;
import utils.sb11.soccer.BBTUtils;
import utils.sb11.sport.EventScheduleUtils;
import utils.sb11.trading.BetEntrytUtils;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.SBPConstants.*;
import static common.SBPConstants.BBTPage.*;

public class BBTTest extends BaseCaseAQS {

    @Test(groups = {"regression_stg", "2023.11.30", "ethan3.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "157")
    public void BBT_TC_157(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the menu");
        log("Precondition: Deactivate BBT option in one role account");
        RoleManagementUtils.updateRolePermission("one role", "BBT", "INACTIVE");
        log("@Step 1: Re-login with one role account account has 'BBT' permission is OFF");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: 'BBT' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER, BBT), "FAILED! Retained Earnings menu is displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_stg", "2023.11.30", "ethan3.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "158")
    public void BBT_TC_158(String password, String userNameOneRole) throws Exception {
        String bbtURL = environment.getSbpLoginURL() + "#/aqs-report/bbt";
        log("@title: Validate accounts without permission cannot access page ");
        RoleManagementUtils.updateRolePermission("one role", "BBT", "INACTIVE");
        log("Precondition: Deactivate BBT option in one role account");
        log("@Step 1: Re-login with one role account account has 'BBT' permission is OFF");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: BBT (Bets By Team) cannot access by external link " + bbtURL);
        DriverManager.getDriver().get(bbtURL);
        Assert.assertFalse(new BBTPage().lblTitle.isDisplayed(), "FAILED! BBT page can access by external link");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_stg", "2023.11.30", "ethan6.0"})
    @TestRails(id = "159")
    public void BBT_TC_159() {
        String bbtURL = environment.getSbpLoginURL() + "#/aqs-report/bbt/c/all";
        log("@title: Validate accounts with permission can access page ");
        log("Precondition: Account is activated permission 'BBT'");
        log("@Step 1: Re-login with one role account account has 'BBT' permission is ON");
        log("@Verify 1: BBT (Bets By Team) access successfully by external link " + bbtURL);
        DriverManager.getDriver().get(bbtURL);
        Assert.assertTrue(new BBTPage().lblTitle.getText().contains("Bets By Team"), "Failed! BBT page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "161")
    public void BBT_TC_161() {
        log("@title: Validate the filter 'Show Masters/Groups/Agents' displays accordingly when selected each value in Smart Type list");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, "All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Select an Smart Type option: Master");
        bbtPage.ddpSmartType.selectByVisibleText("Master");
        bbtPage.waitSpinnerDisappeared();
        log("@Verify 1: The Show Masters link will display");
        Assert.assertTrue(bbtPage.btnShowMaster.isDisplayed(), "FAILED! Button 'Show Master' is not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "162")
    @Parameters({"accountCode", "smartGroup", "accountCurrency"})
    public void BBT_TC_162(String accountCode, String smartGroup, String accountCurrency) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        log("@title: Validate the bets that placed by smart players belonging to Masters/Groups/Agents displays properly");
        log("@Precondition: Place bet with smart player to show bet on BBT page");
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER, currentDay, false, accountCode, "Goals", "HDP", "Home", "FullTime", 1.11, 1.75, "HK", 13.58,
                "BACK", false, "");
        lstOrder.get(0).setAccountCurrency(accountCurrency);
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, "All", BBTPage.class);
        log("@Step 2: Filter with group name: " + smartGroup);
        bbtPage.selectLeaguesFilter(false, lstOrder.get(0).getEvent().getLeagueName());
        bbtPage.selectSmartTypeFilter("Group", smartGroup);
        log("@Verify 1: The bets that placed by smart players belonging to Groups displays properly");
//        Assert.assertTrue(bbtPage.findRowIndexOfTeamTable(lstOrder.get(0), true) != -1, "FAILED! The bet is not show on BBT page");
        Assert.assertTrue(bbtPage.isBetDisplay(lstOrder.get(0), smartGroup, true, true), "FAILED! The bet is not show on BBT page");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "165")
    public void BBT_TC_165() {
        log("@title: Validate all events with unsettled bets that have start time within a filtered date range returns when filtering Pending Bets ");
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", GMT_8);
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, "All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter Pending bets with To day");
        bbtPage.filter("", "", "Pending Bets", currentDay, currentDay, "", "", "");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events", true);
        log("@Verify 1: All events start from 12:00 pm of filtering date GMT+8 to 11:59:59 am tomorrow returns");
        List<String> leagueTimeList = bbtPage.getListLeagueTime();
        Assert.assertTrue(bbtPage.verifyEventTimeDisplayCorrectWithTimeFilter(currentDay, currentDay, leagueTimeList), "FAILED! Event time is not correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30","ethan6.0"})
    @TestRails(id = "166")
    public void BBT_TC_166() {
        String fromDate = DateUtils.getDate(-5, "dd/MM/yyyy", "GMT +8");
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        log("@title: Validate all events with settled bets that have start time within a filtered date range return when filtering Settled Bets");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, "All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter Settled Bets with To day");
        bbtPage.filter("", "", "Settled Bets", fromDate, currentDay, "", "", "");
        log("@Verify 1: All events start from 12:00 pm of filtering date GMT+8 to 11:59:59 am tomorrow returns");
        List<String> leagueTimeList = bbtPage.getListLeagueTime();
        Assert.assertTrue(bbtPage.verifyEventTimeDisplayCorrectWithTimeFilter(fromDate, currentDay, leagueTimeList), "FAILED! Event time is not correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30","ethan6.0"})
    @TestRails(id = "167")
    public void BBT_TC_167() {
        String fromDate = DateUtils.getDate(-8, "dd/MM/yyyy", "GMT +8");
        log("@title: Validate max range to filter is 7 days");
        log("Precondition: Account is activated permission 'BBT'");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, "All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Select From Date - To Date over 7 dates");
        bbtPage.dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
        log("@Verify 1: Validate alert message appears");
        Assert.assertTrue(bbtPage.alert.getWarningMessage().contains(DATE_RANGE_ALERT_MESSAGE), "FAILED! The alert message is not displayed.");
        log("@Step 3: Click on show button with date ranges over 7 dates");
        bbtPage.btnShow.click();
        log("@Verify: Validate alert message appears");
        Assert.assertTrue(bbtPage.alert.getWarningMessage().contains(DATE_RANGE_ALERT_MESSAGE), "FAILED! The alert message is not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "168")
    @Parameters({"accountCurrency"})
    public void BBT_TC_168(String accountCurrency) {
        log("@title: Validate can filter bets that placed with the selected currency");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with currency: " + accountCurrency);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.filter("", "", "", "", "", "", accountCurrency, "");
        log("@Verify 1: Validate all bets that placed with the currency: " + accountCurrency);
        List<String> actualCurList = bbtPage.getCurrencyListOfAllEvents();
        Assert.assertTrue(bbtPage.verifyAllElementOfListAreTheSame(accountCurrency, actualCurList), "FAILED! All bets don't have the same currency");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "169")
    public void BBT_TC_169() {
        log("@title: Validate Stake includes All, Above 1K, Above 10K, Above 50K, Above 150K ");
        Assert.assertTrue(false, "FAILED! Because of improvement AQS-4355");
//        log("@Step 1: Navigate to Soccer > BBT");
//        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
//        bbtPage.stopInterval();
//        log("@Verify 1: Validate Stake dropdown list is correct");
//        Assert.assertEquals(bbtPage.ddpStake.getOptions(), SBPConstants.BBTPage.STAKE_LIST, "FAILED! Stake dropdown list is not correct.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "170")
    public void BBT_TC_170() {
        log("@title: Validate all bets that matched selected currency and stake display");
        Assert.assertTrue(false, "FAILED! Because of improvement AQS-4355");
//        log("@Step 1: Navigate to Soccer > BBT");
//        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
//        bbtPage.stopInterval();
//        log("@Step 2: Filter with option Stake: Above 1K");
//        bbtPage.filter("", "", "", "", "", "", "Above 1K", "", "");
//        log("@Verify 1: All bets that place with stake >= 1K return");
//        List<String> actualStakeList = bbtPage.getStakeListOfAllEvents();
//        Assert.assertTrue(bbtPage.verifyAllStakeCorrectFilter("Above 1K", actualStakeList), "FAILED! There are one or many stakes smaller than 1000");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30","ethan6.0"})
    @TestRails(id = "171")
    public void BBT_TC_171() {
        log("@title: Validate all bets type that have bets in filtered date range display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Click on Show Bet Type");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.btnShowBetTypes.click();
        bbtPage.stopInterval();
        bbtPage.waitSpinnerDisappeared();
        log("@Verify 1: All bets type (e.g. FT-HDP, HT-HDP, FT-Over/Under, HT-Over/Under) that have bets display");
        Assert.assertTrue(bbtPage.isOptionsFilterDisplay(SOCCER_BET_TYPES_LIST), "FAILED! Bet types are not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "172")
    public void BBT_TC_172() {
        String betType = "HT-OU";
        String date = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        log("@title: Validate all bets in selected bet types display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, "All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter Soccer with Bet type: " + betType);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.dtpFromDate.selectDate(date, "dd/MM/yyyy");
        bbtPage.waitSpinnerDisappeared();
        bbtPage.selectBetTypesFilter(SOCCER, betType);
        bbtPage.selectLeaguesFilter(true, "");
        log("@Verify 1: All bet HT-OU shows in results page");
        List<String> betTypesList = bbtPage.getBetTypeListOfAllEvents();
        Assert.assertTrue(bbtPage.verifyAllBetsIsOverUnder(betTypesList), "FAILED! There is one or many bets is not Over/Under");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30","ethan6.0"})
    @TestRails(id = "173")
    public void BBT_TC_173() {
        log("@title: Validate Masters/Groups/Agents list displays accordingly when selected any Smart Type value");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with Smart Type: Master, Master: " + QA_SMART_MASTER);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.ddpSmartType.selectByVisibleText("Master");
        bbtPage.stopInterval();
        bbtPage.waitSpinnerDisappeared();
        log("@Step 3: Click on Show Master link");
        bbtPage.btnShowMaster.click();
        bbtPage.stopInterval();
        log(String.format("@Verify 1: %s filtering option is displayed", QA_SMART_MASTER));
        Assert.assertTrue(bbtPage.isOptionsFilterDisplay(Arrays.asList(QA_SMART_MASTER)), "FAILED! Show master filter is not contain an option: " + QA_SMART_MASTER);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "174")
    @Parameters({"smartGroup"})
    public void BBT_TC_174(String smartGroup) {
        log("@title: Validate all Masters/Groups/Agents that have bets in the filtered date range displays");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with Smart Type: Group, Smart Group: " + smartGroup);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.selectLeaguesFilter(true,"");
        bbtPage.selectSmartTypeFilter("Group", smartGroup);
        log("@Verify 1: All Groups that have bets in the filtered date range displays");
        List<String> smartGroupName = bbtPage.getSmartGroupName();
        Assert.assertTrue(bbtPage.verifyAllElementOfListAreTheSame(smartGroup, smartGroupName), "FAILED! The Shows smart type incorrect with value: " + QA_SMART_GROUP);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "209")
    public void BBT_TC_209() {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);
        String firstLeague = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", dateAPI + " 12:00:00", dateAPI + " 12:00:00").get(0);
        log("@title: Validate all leagues that have events in filtered date range display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with selected league: " + firstLeague);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.selectLeaguesFilter(false, firstLeague);
        log("@Verify 1: All leagues: " + firstLeague + " that have events in filtered date range display");
        List<String> leaguesList = bbtPage.getListAllLeaguesName();
        Assert.assertTrue(bbtPage.verifyAllElementOfListAreTheSame(firstLeague, leaguesList), "FAILED! The Leagues filter shows incorrect results");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "210")
    public void BBT_TC_210() {
        log("@title: Validate all filters reset to default if click 'Reset All Filters' button");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        String date = DateUtils.getDate(-2, "dd/MM/yyyy", GMT_7);
        log("@Step 2: Filter with default option");
        bbtPage.filter("", SMART_TYPE_LIST.get(2), REPORT_TYPE_LIST.get(1), date, date, "Above 25K", "HKD", "");
        log("@Step 3: Click button Reset all filters");
        bbtPage.btnResetAllFilter.click();
        log("@Verify 1: Validate All filters were reset to default");
        bbtPage.verifyAllCountIconsResetToAll();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30","ethan6.0"})
    @TestRails(id = "211")
    public void BBT_TC_211() {
        log("@title: Validate More Filters contains values: Live, Non Live and All (default)");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Expand More Filters link");
        bbtPage.btnMoreFilter.click();
        log("@Verify 1: Validate Live status contains values: Live, Non Live and All (default)");
        Assert.assertEquals(bbtPage.ddpLiveStatus.getFirstSelectedOption(), LIVE_STATUS_LIST.get(0), "FAILED! Default option was not All");
        Assert.assertEquals(bbtPage.ddpLiveStatus.getOptions(), LIVE_STATUS_LIST, "FAILED! Live status contains values: Live, Non Live and All");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan6.0"})
    @TestRails(id = "212")
    public void BBT_TC_212() {
        log("@title: Validate all bets that placed on Live/Non live events display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Expand More Filters and select Non-live option");
        bbtPage.selectLiveNonLiveMoreFilter("Non-Live");
        log("@Verify 1: All bets that placed on Non live events display");
        Assert.assertTrue(bbtPage.lblFirstGroupName.isDisplayed(), "FAILED! No record label is not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30","ethan6.0"})
    @TestRails(id = "213")
    public void BBT_TC_213() {
        log("@title: Validate no records display if events do not have Live/Non Live bets");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Expand More Filters button");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.waitSpinnerDisappeared();
        bbtPage.ddpSport.selectByVisibleText("Basketball");
        bbtPage.selectLiveNonLiveMoreFilter("Live");
        log("@Verify 1: No records display if events do not have Live/Non Live bets");
        Assert.assertTrue(bbtPage.lblNoRecord.isDisplayed(), "FAILED! No record label is not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30","ethan6.0"})
    @TestRails(id = "242")
    public void BBT_TC_242() {
        log("@title: Validate the smart group will show under the event home team section or away team section if player of this group placed bets on home/away team");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with default option");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.filter("", "", "", "", "", "", "", "");
        log("@Verify 1: The smart group will show under the event home team section or away team section respectively");
        Assert.assertTrue(bbtPage.verifyFirstGroupNameUnderTeamName(), "FAILED! The first smart group name is not under the first team name");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30", "ethan7.0"})
    @TestRails(id = "254")
    @Parameters({"smartGroup"})
    public void BBT_TC_254(String smartGroup) {
        log("@title: Validate data table of each group shows correctly");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with default option");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.selectLeaguesFilter(true,"");
        bbtPage.selectGroupsFilter(smartGroup);
        bbtPage.waitSpinnerDisappeared();
        log("@Verify 1: Data table of BBT is correct");
        Assert.assertEquals(bbtPage.lblFirstGroupName.getText().trim(), smartGroup, "FAILED! Smart group name is not correct");
        Assert.assertEquals(bbtPage.lblFirstGroupLast12Day.getText().trim(), "-", "FAILED! T' column is not correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_stg", "2023.11.30","ethan6.0"})
    @Parameters({"password", "userNameOneRole", "username"})
    @TestRails(id = "314")
    public void BBT_TC_314(String password, String userNameOneRole, String username) throws Exception {
        log("@title: Validate can open 'Last 12 Days Performance' with correct values although SPP permission is OFF");
        log("Precondition: Account is activated permission 'BBT' and is deactivated 'SPP' permission");
        try {
            log("Precondition: Account is activated permission 'BBT' and is deactivated 'SPP' permission");
            RoleManagementUtils.updateRolePermission("one role", "BBT", "ACTIVE");
            RoleManagementUtils.updateRolePermission("one role", SPP, "INACTIVE");
            log("@Step 1: Re-login with one role account account has 'BBT' permission is OFF");
            LoginPage loginPage = welcomePage.logout();
            loginPage.login(userNameOneRole, StringUtils.decrypt(password));
            log("@Step 2: Navigate to Soccer > BBT");
            Assert.assertTrue(welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER, BBT), "FAILED! BBT menu is not displayed");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            log("@Step 3: Filter with default option");
            bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
            bbtPage.filter("", "", "", "", "", "", "", "");
            log("@Step 4: Click on 'T' column of any smart group");
            Last12DaysPerformancePage last12DaysPage = bbtPage.openLast12DayPerformanceFirstGroup();
            log("Verify 1: Validate Last 12 Days Performance is displayed correctly");
            Assert.assertTrue(last12DaysPage.getTitlePage().contains("Last 12 Days Performance"), "FAILED! Header of Last 12 Days Performance is not displayed correct");
            last12DaysPage.closePopup();
        } finally {
            LoginPage loginPage = welcomePage.logout();
            loginPage.login(username, StringUtils.decrypt(password));
            RoleManagementUtils.updateRolePermission("one role", "BBT", "INACTIVE");
            log("INFO: Executed completely");
        }
    }

    @Test(groups = {"regression","ethan6.0"})
    @TestRails(id = "2135")
    public void BBT_TC_2135() {
        log("@title: Validate BBT page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        log("Validate BBT page is displayed with correctly title");
        Assert.assertTrue(bbtPage.getTitlePage().contains("Bets By Team"), "Failed! BBT page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2136")
    public void BBT_TC_2136() {
        log("@title: Validate UI on BBT is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log(" Validate UI Info display correctly");
        bbtPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan7.0"})
    @TestRails(id = "2137")
    public void BBT_TC_2137() {
        log("@title: Validate Month Performance page is displayed successfully when clicking on Smart group code");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, "", "", "", lstLeagues.get(0));
        log("@Step 3: Click on any Smart group code");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        MonthPerformancePage monthPerformancePage = bbtPage.openMonthPerformanceFirstGroup();
        //Wait for title update
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String expectedHeader = lstFirstRowData.get(0) + " - " + lstFirstRowData.get(6) + " - " + "Last 12 Month Performance";

        log("Verify 1. Validate Last 12 Month Performance is displayed with format\n" +
                "[smart group name] - [smart group currency] - Last 12 Month Performance");
        Assert.assertEquals(monthPerformancePage.lblHeaderGroup.getText(), expectedHeader, "FAILED! Header of Month Performance is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan7.0"})
    @TestRails(id = "2138")
    public void BBT_TC_2138() {
        log("@title: Validate Last 50 Bets page is displayed successfully when clicking on HDP points");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.stopInterval();
        log("@Step 2: Filter with valid data");
        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, "", "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        Last50BetsPage last50BetsPage = bbtPage.openLast50BetsFirstGroup();
        String expectedHeader = lstFirstRowData.get(0) + " - " + lstFirstRowData.get(6);

        log("3. Validate Last 50 Bets is displayed correctly with format\n" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(last50BetsPage.lblHeaderGroup.getText(), expectedHeader, "FAILED! Header of Last 50 Bets is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan7.0"})
    @TestRails(id = "2139")
    public void BBT_TC_2139() {
        log("@title: Validate League Performance page is displayed successfully when clicking on Price");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.stopInterval();
        log("@Step 2: Filter with valid data");
        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, "", "", "", lstLeagues.get(0));
        log("@Step 3: Click on any Price");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        LeaguePerformancePage leaguePerformancePage = bbtPage.openLeaguePerformanceFirstGroup();
        String expectedHeaderTable1 = lstFirstRowData.get(0) + " - League Performance for Last 1 Month";
        String expectedHeaderTable2 = lstFirstRowData.get(0) + " - League Performance for Last 3 Months";

        log("3. Validate League Performance is displayed correctly title 2 tables header [smart group name] - League Performance for Last 1 Month and" +
                "[smart group name] - League Performance for Last 3 Months");
        Assert.assertEquals(leaguePerformancePage.lblTableHeader1Month.getText(), expectedHeaderTable1, "FAILED! Header of League Performance - Last 1 Month is not displayed correct");
        Assert.assertEquals(leaguePerformancePage.lblTableHeader3Months.getText(), expectedHeaderTable2, "FAILED! Header of League Performance - Last 3 Months is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2140")
    public void BBT_TC_2140() {
        log("@title: Validate Live Last 50 Bets page is displayed successfully when clicking on first Live column");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with valid data");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        LiveLast50BetsPage liveLast50BetsPage = bbtPage.openLiveLast50BetsFirstGroup();
        String expectedHeaderTable1 = "Top 6 Winner Performance - Last 1 Year[Live Bets]";
        String expectedHeaderTable2 = lstFirstRowData.get(0) + "-" + lstFirstRowData.get(6);

        log("Verify 3. Validate Live Last 50 Bets is displayed correctly title with 2 tables header Punter Performance - Last 1 Year[Live Bets] and" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(liveLast50BetsPage.lblSummaryTableHeader.getText(), expectedHeaderTable1, "FAILED! Header of Live Last 50 Bets - Punter Performance - Last 1 Year[Live Bets] is not displayed correct");
        Assert.assertEquals(liveLast50BetsPage.lblDetailTableHeader.getText(), expectedHeaderTable2, "FAILED! Header of Live Last 50 Bets - Smart Group is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2141")
    public void BBT_TC_2141() {
        log("@title: Validate NonLive Last 50 Bets page is displayed successfully when clicking on second Live column");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        NonLiveLast50BetsPage nonLiveLast50BetsPage = bbtPage.openNonLiveLast50BetsFirstGroup();
        String expectedHeaderTable1 = "Top 6 Winner Performance - Last 1 Year[NonLive Bets]";
        String expectedHeaderTable2 = lstFirstRowData.get(0) + "-" + lstFirstRowData.get(6);

        log("3. Validate NonLive Last 50 Bets is displayed correctly title with 2 tables header Punter Performance - Last 1 Year[NonLive Bets] and" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(nonLiveLast50BetsPage.lblSummaryTableHeader.getText(), expectedHeaderTable1, "FAILED! Header of NonLive Last 50 Bets - Punter Performance - Last 1 Year[NonLive Bets] is not displayed correct");
        Assert.assertEquals(nonLiveLast50BetsPage.lblDetailTableHeader.getText(), expectedHeaderTable2, "FAILED! Header of NonLive Last 50 Bets - Smart Group is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2142")
    public void BBT_TC_2142() {
        log("@title: Validate NonLive Last 50 Bets page is displayed successfully when clicking on second Live column");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, "", "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        PendingBetsPage pendingBetsPage = bbtPage.openPendingBetFirstGroup();
        String expectedHeader = lstFirstRowData.get(0) + " - " + lstFirstRowData.get(6);
        //Wait for popup update
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("3. Validate NonLive Last 50 Bets is displayed correctly title with 2 tables header Punter Performance - Last 1 Year[NonLive Bets] and" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(pendingBetsPage.getTableHeader(), expectedHeader, "FAILED! Header of NonLive Last 50 Bets - Punter Performance - Last 1 Year[NonLive Bets] is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2143")
    public void BBT_TC_2143() {
        log("@title: Validate Match Statistics (S) page is displayed succefully when clicking on S link");
//        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
//        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
//        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

//        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        log("@Step 2: Filter with valid data");
//        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(1), fromDate, toDate, "", "", "");
        bbtPage.btnShow.click();
        bbtPage.waitSpinnerDisappeared();
        log("@Step 3: Click on any HDP points");
        ReportS1Page reportS1Page = bbtPage.openReportSFirstGroup();
        log("Verify 1: Validate S is displayed correctly title");
        Assert.assertTrue(reportS1Page.lblTitlePage.getText().contains("S"), "FAILED! Header of S1 Report is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan7.0"})
    @TestRails(id = "2145")
    public void BBT_TC_2145() {
        log("@title: Validate Soccerway Analysis Last 2 Week Info (S12) page is displayed succefully when clicking on S12 link");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.stopInterval();
        log("@Step 2: Filter with valid data");
        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, "", "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        ReportS12Page reportS12Page = bbtPage.openReportS12FirstGroup();

        log("3. Validate Soccerway Analysis Last 2 Week Info (S12) is displayed correctly title");
        Assert.assertTrue(reportS12Page.lblTitlePage.getText().contains("Soccerway Analysis Last 2 Week Info (S12)"), "FAILED! Header of S12 Report is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan6.0"})
    @TestRails(id = "2146")
    public void BBT_TC_2146() {
        log("@title: Validate Last 12 Days Performance page is displayed succefully when clicking on Last 12 Days link");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_ID_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with valid data");
        bbtPage.btnShow.click();
        bbtPage.waitSpinnerDisappeared();
//        bbtPage.filter(SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        Last12DaysPerformancePage last12DaysPage = bbtPage.openLast12DayPerformanceFirstGroup();

        log("3. Validate Last 12 Days Performance is displayed correctly with format\n" +
                "[smart group currency] - [smart group name]");
        Assert.assertTrue(last12DaysPage.getTitlePage().contains("Last 12 Days Performance"), "FAILED! Header of Last 12 Days Performance is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29","ethan6.0"})
    @TestRails(id = "3800")
    public void BBT_TC_3800() {
        log("@title: Validate 'S' and 'S12' link should not displayed when filtering Basketball sport");
        log("@Precondition-Step 2: Place some Basketball 1x2 match bets");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter default option with Basketball sport");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.filter("Basketball", "", "", "05/12/2023", "05/12/2023", "", "", "");
        log("@Verify 1: Validate 'S' and 'S12' link should not displayed on Basketball");
        Label SLink = Label.xpath(bbtPage.tblBBT.getSLinkXpath(1, "S"));
        Label S12Link = Label.xpath(bbtPage.tblBBT.getSLinkXpath(1, "S12"));
        Assert.assertTrue(!SLink.isDisplayed() && !S12Link.isDisplayed(), "FAILED! 'S' and 'S12' link displayed on Basketball");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29", "ethan6.0"})
    @Parameters({"accountCode"})
    @TestRails(id = "3801")
    public void BBT_TC_3801(String accountCode) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        Event eventTennis =
                new Event.Builder().sportName("Tennis").leagueName("QA Tennis League").eventDate(currentDay)
                        .home("QA Tennis Team 01").away("QA Tennis Team 02")
                        .openTime("16:00").eventStatus("InRunning").isLive(true).isN(false).build();
        Order orderTennis = new Order.Builder().sport("Basketball").price(1.17).requireStake(13.25).oddType("HK").betType("Back")
                .home("QA Tennis Team 1").away("QA Tennis Team 2").selection("Home").isLive(false).event(eventTennis).build();

        try {
            log("@title: Validate Bet Type filter should only display 1x2 bet for Tennis sport");
            log("@Precondition: Having a bet for Tennis sport");
            log("@Precondition-Step 1: Have a specific League Name, Home Team, Away Team for testing line\n" +
                    "League: QA Tennis League\n" +
                    "Home Team: QA Tennis Team 1\n" +
                    "Away Team: QA Tennis Team 2");

            EventScheduleUtils.addEventByAPI(eventTennis, dateAPI, SPORT_ID_MAP.get(eventTennis.getSportName()));
            log("@Precondition-Step 2: Place some Tennis 1x2 match bets");
            BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
            TennisBetEntryPage tennisBetEntryPage = betEntryPage.goToTennis();
            tennisBetEntryPage.showLeague(KASTRAKI_LIMITED, "", eventTennis.getLeagueName(), accountCode);
            tennisBetEntryPage.placeBet(orderTennis, orderTennis.getSelection());
            log("@Step 1: Navigate to Soccer > BBT");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            log("@Step 2: Filter default option with Tennis sport");
            bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
            bbtPage.ddpSport.selectByVisibleText("Tennis");
            bbtPage.waitSpinnerDisappeared();
            log("@Verify 1: Validate Validate Bet Type filter should display 1x2 bet");
            bbtPage.btnShowBetTypes.click();
            bbtPage.waitSpinnerDisappeared();
            Assert.assertTrue(bbtPage.verifyFilterDisplayWithOption("1x2"), "FAILED! Bet type 1x2 is not displayed");
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Delete event Tennis id: ");
            EventScheduleUtils.deleteEventByAPI(eventTennis, dateAPI);
            log("INFO: Post condition completely");
        }

    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "3803")
    public void BBT_TC_3803() {
        log("@title: Validate Bet Type filter should only display 3 options which are '1x2', 'HDP', and 'Total Points' for Basketball sport");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter default option with Basketball sport");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.filter("Basketball", "", "", "05/12/2023", "05/12/2023", "", "", "");
        log("@Verify 1: Validate Bet Type filter should only display 3 options which are '1x2', 'HDP', and 'Total Points'");
        bbtPage.btnShowBetTypes.click();
        bbtPage.waitSpinnerDisappeared();
        Assert.assertTrue(bbtPage.verifyFilterDisplayWithOption("1x2", "HDP", "Total Points"), "FAILED! Bet type '1x2', 'HDP', and 'Total Points' is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29", "ethan4.0"})
    @Parameters({"accountCode"})
    @TestRails(id = "3804")
    public void BBT_TC_3804(String accountCode) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        int randomStake = Integer.valueOf(StringUtils.generateNumeric(2));
        Event eventBasketball =
                new Event.Builder().sportName("Basketball").leagueName("QA Basketball League").eventDate(currentDay)
                        .home("QA Basketball Team 1").away("QA Basketball Team 2")
                        .openTime("17:00").eventStatus("InRunning").isLive(true).isN(false).build();
        Order orderBasketball = new Order.Builder().sport("Basketball").price(2.15).requireStake(randomStake).oddType("HK").betType("Lay")
                .home("QA Basketball Team 1").away("QA Basketball Team 2").selection("Home").isLive(false).event(eventBasketball).build();
        try {
            log("@title: Validate Lay Basketball bet should display correctly on right table ");
            log("@Precondition: Place some Lay Basketball 1x2 match bets");
            EventScheduleUtils.addEventByAPI(eventBasketball, dateAPI, SPORT_ID_MAP.get("Basketball"));
            BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
            BasketballBetEntryPage basketballBetEntryPage = betEntryPage.goToBasketball();
            basketballBetEntryPage.showLeague(KASTRAKI_LIMITED, "", eventBasketball.getLeagueName(), accountCode);
            basketballBetEntryPage.placeBet(orderBasketball, "1x2", orderBasketball.getSelection());

            log("@Step 1: Navigate to Soccer > BBT");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            log("@Step 2: Filter with Basketball sport with valid data at pre-condition > observe");
            bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
            bbtPage.filter("Basketball", "", "", "", "", "", "", "");
            log("@Verify 1: Validate Lay Basketball bet should display correctly");
            Assert.assertTrue(bbtPage.findRowIndexOfTeamTable(orderBasketball, false) != -1, "FAILED! The bet is not show on BBT page");
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Delete event Basketball id: ");
            EventScheduleUtils.deleteEventByAPI(eventBasketball, dateAPI);
            log("INFO: Post condition completely");
        }

    }

    @Test(groups = {"regression", "2023.12.29", "ethan4.0"})
    @Parameters({"accountCode"})
    @TestRails(id = "3805")
    public void BBT_TC_3805(String accountCode) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        int randomStake = Integer.valueOf(StringUtils.generateNumeric(2));
        Event eventBasketball =
                new Event.Builder().sportName("Basketball").leagueName("QA Basketball League").eventDate(currentDay)
                        .home("QA Basketball Team 1").away("QA Basketball Team 2")
                        .openTime("17:00").eventStatus("InRunning").isLive(true).isN(false).build();
        Order orderBasketball = new Order.Builder().sport("Basketball").price(2.15).requireStake(randomStake).oddType("HK").betType("Lay")
                .home("QA Basketball Team 1").away("QA Basketball Team 2").selection("Home").isLive(false).event(eventBasketball).build();
        try {
            log("@title: Validate (Lay) text should display correctly after the odds for Lay bet");
            log("@Precondition: Place some Lay Basketball 1x2 match bets");
            EventScheduleUtils.addEventByAPI(eventBasketball, dateAPI, SPORT_ID_MAP.get("Basketball"));
            BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
            BasketballBetEntryPage basketballBetEntryPage = betEntryPage.goToBasketball();
            basketballBetEntryPage.showLeague(KASTRAKI_LIMITED, "", eventBasketball.getLeagueName(), accountCode);
            basketballBetEntryPage.placeBet(orderBasketball, "1x2", "Away");

            log("@Step 1: Navigate to Soccer > BBT");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            log("@Step 2: Filter with Basketball sport with valid data at pre-condition > observe");
            bbtPage.filter("Basketball", "", "", "", "", "", "", "");
            log("@Verify 1: Validate (Lay) text should display correctly after the odds for Lay bet");
            List<String> listPrice = bbtPage.getListColOfAllBBTTable(bbtPage.colPrice);
            Assert.assertTrue(listPrice.get(0).contains("(L)"), "FAILED! The bet is not show on BBT page");
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Delete event Basketball id: ");
            EventScheduleUtils.deleteEventByAPI(eventBasketball, dateAPI);
            log("INFO: Post condition completely");
        }
    }

    @Test(groups = {"regression", "2023.12.29", "ethan7.0"})
    @Parameters({"accountCode"})
    @TestRails(id = "3806")
    public void BBT_TC_3806() {
        log("@title: Validate Bet Type, Selection, HDP column should have no background color on Last 50 Bets, Pending Bets dialog");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter with Basketball sport with valid data > observe");
        bbtPage.filter("Basketball", "", "", "05/12/2023", "05/12/2023", "", "", "");
        log("@Step 2: Click on any HDP points to open Last 50 bets");
        Last50BetsPage last50BetsPage = bbtPage.openLast50BetsFirstGroup();
        log("@Verify 1: Validate Bet Type, Selection, HDP column should have no background color");
        if (last50BetsPage.lblNoRecord.isDisplayed()){
            return;
        }
        Assert.assertTrue(last50BetsPage.verifyCellHaveNoBackgroundColor(last50BetsPage.betTypeCol), "FAILED! Bet type have a background color");
        Assert.assertTrue(last50BetsPage.verifyCellHaveNoBackgroundColor(last50BetsPage.selectionCol), "FAILED! Selection have a background color");
        Assert.assertTrue(last50BetsPage.verifyCellHaveNoBackgroundColor(last50BetsPage.HDPCol), "FAILED! HDP have a background color");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29", "ethan7.0"})
    @Parameters({"accountCode", "accountCurrency"})
    @TestRails(id = "4184")
    public void BBT_TC_4184(String accountCode, String accountCurrency) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        log("@title: Validate link of S1 report display correctly on Cricket sport");
        log("@Precondition: Have a cricket Bet League Name, Home Team, Away Team for testing line");
        Event eventCricket = new Event.Builder().sportName("Cricket").leagueName("QA Cricket Auto League")
                .eventDate(dateAPI).home("Auto Team 1").away("Auto Team 2").openTime("17:00").eventStatus("InRunning").isLive(false).isN(false).build();
        List<Order> lstOrder = new ArrayList<>();
        // define order info
        Order order = new Order.Builder()
                .sport(eventCricket.getSportName()).isNegativeHdp(false).price(2.15)
                .requireStake(15.50).oddType("HK").betType("Back")
                .accountCode(accountCode).accountCurrency(accountCurrency).marketType("1x2")
                .selection(eventCricket.getHome()).handicapRuns(9.50).handicapWtks(10)
                .isLive(false).home(eventCricket.getHome()).away((eventCricket.getAway())).event(eventCricket).build();
        lstOrder.add(order);
        try {
            EventScheduleUtils.addEventByAPI(eventCricket, dateAPI, SPORT_ID_MAP.get("Cricket"));
            BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY, BetEntryPage.class);
            CricketBetEntryPage cricketBetEntryPage = betEntryPage.goToCricket();
            cricketBetEntryPage.showLeague(KASTRAKI_LIMITED, currentDay, eventCricket.getLeagueName());
            cricketBetEntryPage.placeBet(order, true);

            log("@Step 1: Navigate to Soccer > BBT");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
            log("@Step 2: Filter default option with Cricket sport");
            bbtPage.filter("Cricket", "", "", "", "", "", "", "");
            log("@Step 2: Click on S1 report");
            ReportS1Page reportS1Page = bbtPage.openReportS1FirstGroup();
            log("@Verify 1: Validate link of S1 report display correct url");
            Assert.assertEquals(reportS1Page.getCurrentURL(), reportS1Page.s1URL, "FAILED! URL of S1 Cricket report is not correct");
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Delete event Basketball id: ");
            EventScheduleUtils.deleteEventByAPI(eventCricket, dateAPI);
            log("INFO: Post condition completely");
        }

    }

    @Test(groups = {"regression", "2023.12.29","ethan7.0"})
    @TestRails(id = "9750")
    public void BBT_TC_9750() {
        log("@title: Validate can open multiple popups at once");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter default option with Soccer sport");
        bbtPage.clickToCheckBoxHeader("Hide no bet Events",true);
        bbtPage.filter(SOCCER, "", "", "29/09/2024", "29/09/2024", "", "", "");
        log("@Step 3: Click on S and S12 link");
        Label.xpath(bbtPage.tblBBT.getSLinkXpath(1, "S")).jsClick();
        Label.xpath(bbtPage.tblBBT.getSLinkXpath(1, "S12")).jsClick();
        log("@Verify 1: Validate can open multiple popups at once successfully");
        Assert.assertEquals(bbtPage.getNumberOfWindow(), 3, "FAILED! Can not open multiple popups S links");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "16177")
    public void BBT_TC_16177() {
        log("@title: Validate 'S' and 'S12' link should not displayed when filtering Tennis sport");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
        bbtPage.stopInterval();
        log("@Step 2: Filter default option with Tennis sport");
        bbtPage.filter("Tennis", "", "", "06/12/2023", "06/12/2023", "", "", "");
        log("@Verify 1: Validate 'S' and 'S12' link should not displayed on Tennis");
        Label SLink = Label.xpath(bbtPage.tblBBT.getSLinkXpath(1, "S"));
        Label S12Link = Label.xpath(bbtPage.tblBBT.getSLinkXpath(1, "S12"));
        Assert.assertTrue(!SLink.isDisplayed() && !S12Link.isDisplayed(), "FAILED! 'S' and 'S12' link displayed on Tennis");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @Parameters({"accountCode"})
    @TestRails(id = "16178")
    public void BBT_TC_16178(String accountCode) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        Event eventTennis =
                new Event.Builder().sportName("Tennis").leagueName("QA Tennis League").eventDate(currentDay)
                        .home("QA Tennis Team 01").away("QA Tennis Team 02")
                        .openTime("16:00").eventStatus("InRunning").isLive(true).isN(false).build();
        try {
            log("@title: Validate Back Tennis bet should display correctly on left table");
            log("@Precondition - Step 1: Already have some place Back bet on Home for Tennis sport");
            EventScheduleUtils.addEventByAPI(eventTennis, dateAPI, SPORT_ID_MAP.get("Tennis"));
            String leagueID = EventScheduleUtils.getLeagueID(eventTennis.getLeagueName(), SPORT_ID_MAP.get("Tennis"));
            String eventID = EventScheduleUtils.getEventID(dateAPI, leagueID);
            eventTennis.setEventId(eventID);
            List<Order> lstOrder = welcomePage.placeBetAPI("Tennis", currentDay, eventTennis, accountCode, "MatchOdds", "MatchOdds", eventTennis.getHome(), "FullTime", 1.17, 0, "HK",
                    13.25, "BACK", false, "");
            log("@Step 1: Navigate to Soccer > BBT");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            log("@Step 2: Filter with Tennis sport with valid data at pre-condition > observe");
            bbtPage.filter("Tennis", "", "", "", "", "", "", "");

            log(String.format("@Verify 1: Validate Home: %s and away: %s team should display correctly", eventTennis.getHome(), eventTennis.getAway()));
            bbtPage.verifyHomeAwayTeamNameCorrect(eventTennis.getHome(), eventTennis.getAway());
            log("@Verify 2: Validate Back Tennis bet should display correctly");
            Assert.assertTrue(bbtPage.findRowIndexOfTeamTable(lstOrder.get(0), true) != -1, "FAILED! The bet is not show on BBT page");
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Delete event Tennis id: ");
            EventScheduleUtils.deleteEventByAPI(eventTennis, dateAPI);
            log("INFO: Post condition completely");
        }
    }


    @Test(groups = {"regression", "2023.12.29", "ethan3.0"})
    @Parameters({"accountCode"})
    @TestRails(id = "16180")
    public void BBT_TC_16180(String accountCode) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        Event eventTennis =
                new Event.Builder().sportName("Tennis").leagueName("QA Tennis League").eventDate(currentDay)
                        .home("QA Tennis Team 01").away("QA Tennis Team 02")
                        .openTime("16:00").eventStatus("InRunning").isLive(true).isN(false).build();
        try {
            EventScheduleUtils.addEventByAPI(eventTennis, dateAPI, SPORT_ID_MAP.get(eventTennis.getSportName()));
            String leagueID = EventScheduleUtils.getLeagueID(eventTennis.getLeagueName(), SPORT_ID_MAP.get("Tennis"));
            String eventID = EventScheduleUtils.getEventID(dateAPI, leagueID);
            eventTennis.setEventId(eventID);
            log("@title: Validate LayTennis bet should display correctly on right table");
            log("@Precondition:  Already have some place Lay bet on Home for Tennis sport");
            List<Order> lstOrder = welcomePage.placeBetAPI("Tennis", currentDay, eventTennis, accountCode, "MatchOdds", "MatchOdds", eventTennis.getHome(), "FullTime", 1.17, 0, "HK",
                    13.25, "LAY", false, "");
            log("@Step 1: Navigate to Soccer > BBT");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            log("@Step 2: Filter with Tennis sport with valid data at pre-condition > observe");
            bbtPage.filter("Tennis", "", "", "", "", "", "", "");
            log("@Verify 1: Validate Back Tennis bet should display correctly");
            Assert.assertTrue(bbtPage.findRowIndexOfTeamTable(lstOrder.get(0), false) != -1, "FAILED! The bet is not show on BBT page");
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Delete event Tennis id: ");
            EventScheduleUtils.deleteEventByAPI(eventTennis, dateAPI);
            log("INFO: Post condition completely");
        }

    }

    @Test(groups = {"regression", "2023.12.29", "ethan4.0"})
    @Parameters({"accountCode"})
    @TestRails(id = "16181")
    public void BBT_TC_16181(String accountCode) {
        String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        int randomStake = Integer.valueOf(StringUtils.generateNumeric(2));
        Event eventBasketball =
                new Event.Builder().sportName("Basketball").leagueName("QA Basketball League").eventDate(currentDay)
                        .home("QA Basketball Team 1").away("QA Basketball Team 2")
                        .openTime("17:00").eventStatus("InRunning").isLive(true).isN(false).build();
        try {
            log("@title: Validate Back Basketball bet should display correctly on left table");
            log("@Precondition: Already have some place Back bet on Home for Basketball sport");
            EventScheduleUtils.addEventByAPI(eventBasketball, dateAPI, SPORT_ID_MAP.get(eventBasketball.getSportName()));
            String leagueID = EventScheduleUtils.getLeagueID(eventBasketball.getLeagueName(), SPORT_ID_MAP.get("Basketball"));
            String eventID = EventScheduleUtils.getEventID(dateAPI, leagueID);
            eventBasketball.setEventId(eventID);
            List<Order> lstOrder = welcomePage.placeBetAPI("Basketball", currentDay, eventBasketball, accountCode, "MatchOdds", "MatchOdds", eventBasketball.getHome(), "FullTime", 2.15, 0, "HK",
                    randomStake, "BACK", true, "");
            log("@Step 1: Navigate to Soccer > BBT");
            BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT,"All", BBTPage.class);
            bbtPage.stopInterval();
            log("@Step 2: Filter with Basketball sport with valid data at pre-condition > observe");
            bbtPage.filter("Basketball", "", "", "", "", "", "", "");
            log("@Verify 1: Validate Back Basketball bet should display correctly");
            Assert.assertTrue(bbtPage.findRowIndexOfTeamTable(lstOrder.get(0), true) != -1, "FAILED! The bet is not show on BBT page");
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Delete event Tennis id: ");
            EventScheduleUtils.deleteEventByAPI(eventBasketball, dateAPI);
            log("INFO: Post condition completely");
        }
    }

}
