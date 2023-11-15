package testcases.sb11test.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.role.RoleManagementPage;
import pages.sb11.soccer.*;
import pages.sb11.soccer.BBTPage;
import testcases.BaseCaseAQS;
import utils.sb11.BBTUtils;
import utils.sb11.BetEntrytUtils;
import utils.testraildemo.TestRails;

import java.util.List;
import java.util.Map;

import static common.SBPConstants.*;
import static common.SBPConstants.BBTPage.*;

public class BBTTest extends BaseCaseAQS {
    String currentDay = DateUtils.getDate(0, "dd/MM/yyyy", "GMT +8");
    String groupName = "QA Smart Group";

    @Test(groups = {"regression_qc", "2023.11.30"})
    @Parameters({"password"})
    @TestRails(id = "157")
    public void BBT_TC_157(String password) throws Exception{
        String userNameOneRole = "onerole";
        log("@title: Validate accounts without permission cannot see the menu");
        log("Precondition: Deactivate BBT option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("BBT", false);
        roleManagementPage.selectRole("one role").switchPermissions(PT_RISK_CONTROL, true);
        log("@Step 1: Re-login with one role account account has 'BBT' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        PTRiskPage ptRiskPage =
                welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        log("@Verify 1: 'BBT' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER, BBT), "FAILED! Retained Earnings menu is displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_qc", "2023.11.30"})
    @Parameters({"password"})
    @TestRails(id = "158")
    public void BBT_TC_158(String password) throws Exception{
        String userNameOneRole = "onerole";
        String bbtURL = environment.getSbpLoginURL() + "#/aqs-report/bbt";
        log("@title: Validate accounts without permission cannot access page ");
        log("Precondition: Deactivate BBT option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("BBT", false);
        roleManagementPage.selectRole("one role").switchPermissions(PT_RISK_CONTROL, true);
        log("@Step 1: Re-login with one role account account has 'BBT' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: BBT (Bets By Team) cannot access by external link " + bbtURL);
        DriverManager.getDriver().get(bbtURL);
        Assert.assertFalse(new BBTPage().lblTitle.isDisplayed(), "FAILED! BBT page can access by external link");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_qc", "2023.11.30"})
    @Parameters({"password"})
    @TestRails(id = "159")
    public void BBT_TC_159(String password) throws Exception{
        String userNameOneRole = "onerole";
        String bbtURL = environment.getSbpLoginURL() + "#/aqs-report/bbt";
        log("@title: Validate accounts with permission can access page ");
        log("Precondition: Account is activated permission 'BBT'");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("BBT", true);
        log("@Step 1: Re-login with one role account account has 'BBT' permission is ON");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: BBT (Bets By Team) access successfully by external link " + bbtURL);
        DriverManager.getDriver().get(bbtURL);
        Assert.assertTrue(new BBTPage().lblTitle.getText().contains("Bets By Team"), "Failed! BBT page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "161")
    public void BBT_TC_161(){
        log("@title: Validate the filter 'Show Master/Group/Agent' displays accordingly when selected each value in Smart Type list");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with Smart Type dropdown: Group");
        bbtPage.filter("", "","Group", "", "", "", "","","");
        log("@Verify 1: Filter Group shows the results");
        Assert.assertTrue(bbtPage.lblFirstGroupName.isDisplayed() || bbtPage.lblNoRecord.isDisplayed(), "FAILED! There are no results");
        log("@Step 3: Filter with Smart Type dropdown: Master");
        bbtPage.filter("", "","Master", "", "", "", "","","");
        log("@Verify 2: Filter Group shows the results");
        Assert.assertTrue(bbtPage.lblFirstGroupName.isDisplayed() || bbtPage.lblNoRecord.isDisplayed(), "FAILED! There are no results");
        log("@Step 4: Filter with Smart Type dropdown: Agent");
        bbtPage.filter("", "","Agent", "", "", "", "","","");
        log("@Verify 3: Filter Group shows the results");
        Assert.assertTrue(bbtPage.lblFirstGroupName.isDisplayed() || bbtPage.lblNoRecord.isDisplayed(), "FAILED! There are no results");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "165")
    public void BBT_TC_165() {
        log("@title: Validate all events with unsettled bets that have start time within a filtered date range returns when filtering Pending Bets ");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter Pending bets with To day");
        bbtPage.filter("", "","", "Pending Bets", "", "", "","","");
        log("@Verify 1: All events start from 12:00 pm of filtering date GMT+8 to 11:59:59 am tomorrow returns");
        bbtPage.scrollToShowFullResults();
        Map<String, List<Integer>> dateTimeEntries = bbtPage.getListTimeEvent();
        bbtPage.verifyEventTimeDisplayCorrectWithTimeFilterInOneDay(currentDay, dateTimeEntries);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "166")
    public void BBT_TC_166() {
        log("@title: Validate all events with settled bets that have start time within a filtered date range return when filtering Settled Bets");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter Settled Bets with To day");
        String fromDate = DateUtils.getDate(-5, "dd/MM/yyyy", "GMT +8");
        bbtPage.filter("", "", "", "Settled Bets", fromDate, "", "", "", "");
        log("@Verify 1: All events start from 12:00 pm of filtering date GMT+8 to 11:59:59 am tomorrow returns");
        bbtPage.scrollToShowFullResults();
        Map<String, List<Integer>> dateTimeEntries = bbtPage.getListTimeEvent();
        bbtPage.verifyEventTimeDisplayCorrectWithTimeFilterInOneDay(fromDate, dateTimeEntries);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "167")
    public void BBT_TC_167() {
        String fromDate = DateUtils.getDate(-8, "dd/MM/yyyy", "GMT +8");
        log("@title: Validate max range to filter is 7 days");
        log("Precondition: Account is activated permission 'BBT'");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Select From Date - To Date over 7 dates");
        bbtPage.dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
        log("@Verify 1: Validate alert message appears");
        Assert.assertTrue(bbtPage.lblAlert.isDisplayed() && bbtPage.lblAlert.getText().contains(ALERT_MESSAGE), "FAILED! The alert message is not displayed.");
        log("@Step 3: Click on show button with date ranges over 7 dates");
        bbtPage.btnShow.click();
        log("@Verify: Validate alert message appears");
        Assert.assertTrue(bbtPage.lblAlert.isDisplayed() && bbtPage.lblAlert.getText().contains(ALERT_MESSAGE), "FAILED! The alert message is not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "168")
    @Parameters({"accountCurrency"})
    public void BBT_TC_168(String accountCurrency) {
        log("@title: Validate can filter bets that placed with the selected currency");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with currency: " + accountCurrency);
        bbtPage.filter("", "", "", "", "", "", "", accountCurrency, "");
        log("@Verify 1: Validate all bets that placed with the currency: " + accountCurrency);
        bbtPage.scrollToShowFullResults();
        List<String> actualCurList = bbtPage.getCurrencyListOfAllEvents();
        Assert.assertTrue(bbtPage.verifyAllElementOfListAreTheSame(accountCurrency, actualCurList), "FAILED! All bets don't have the same currency");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "169")
    public void BBT_TC_169() {
        log("@title: Validate Stake includes All, Above 1K, Above 10K, Above 50K, Above 150K ");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Verify 1: Validate Stake dropdown list is correct");
        Assert.assertEquals(bbtPage.ddpStake.getOptions(), SBPConstants.BBTPage.STAKE_LIST, "FAILED! Stake dropdown list is not correct.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "170")
    public void BBT_TC_170() {
        log("@title: Validate all bets that matched selected currency and stake display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with option Stake: Above 1K");
        bbtPage.filter("", "", "", "", "", "", "Above 1K", "", "");
        log("@Verify 1: All bets that place with stake >= 1K return");
        bbtPage.scrollToShowFullResults();
        List<String> actualStakeList = bbtPage.getStakeListOfAllEvents();
        Assert.assertTrue(bbtPage.verifyAllStakeCorrectFilter("Above 1K", actualStakeList), "FAILED! There are one or many stakes smaller than 1000");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "171")
    public void BBT_TC_171() {
        log("@title: Validate all bets type that have bets in filtered date range display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Click on Show Bet Type");
        bbtPage.btnShowBetTypes.click();
        bbtPage.waitSpinnerDisappeared();
        log("@Verify 1: All bets type (e.g. FT-HDP, HT-HDP, FT-Over/Under, HT-Over/Under) that have bets display");
        Assert.assertTrue(bbtPage.isOptionsFilterDisplay(SOCCER_BET_TYPES_LIST), "FAILED! Bet types are not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "174")
    public void BBT_TC_174() {
        log("@title: Validate all Masters/Groups/Agents that have bets in the filtered date range displays");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with Smart Type: Group, Smart Group: " + groupName);
        bbtPage.filter("", "", "Group", "", "", "", "", "", "");
        bbtPage.selectSmartTypeFilter( "Group", groupName);
        bbtPage.btnShow.click();
        bbtPage.waitSpinnerDisappeared();
        bbtPage.scrollToShowFullResults();
        log("@Verify 1: All Groups that have bets in the filtered date range displays");
        List<String> smartGroupName = bbtPage.getSmartGroupName();
        Assert.assertTrue(bbtPage.verifyAllElementOfListAreTheSame(groupName, smartGroupName), "FAILED! The Shows smart type incorrect with value: " + groupName);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "209")
    public void BBT_TC_209() {
        String dateAPI =  DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        String firstLeague = BBTUtils.getListAvailableLeagueBBT(""+0, SPORT_MAP.get(SOCCER), "PENDING", dateAPI + " 12:00:00", dateAPI + " 12:00:00").get(0);
        log("@title: Validate all leagues that have events in filtered date range display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        bbtPage.selectLeaguesFilter(firstLeague);
        log("@Step 2: Filter with selected league: " + firstLeague);
        bbtPage.btnShow.click();
        bbtPage.waitSpinnerDisappeared();
        bbtPage.scrollToShowFullResults();
        log("@Verify 1: All leagues: " + firstLeague + " that have events in filtered date range display");
        List<String> leaguesList = bbtPage.getListAllLeaguesName();
        Assert.assertTrue(bbtPage.verifyAllElementOfListAreTheSame(firstLeague, leaguesList), "FAILED! The Leagues filter shows incorrect results");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "210")
    public void BBT_TC_210() {
        log("@title: Validate all filters reset to default if click 'Reset All Filters' button");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with default option");
        bbtPage.filter("", "", "", "", "", "", "", "", "");
        log("@Step 3: Click button Reset all filters");
        bbtPage.btnResetAllFilter.click();
        log("@Verify 1: Validate All filters were reset to default");
        Assert.assertTrue(bbtPage.verifyAllCountIconsResetToAll(), "FAILED! Filters were not reset to default");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "211")
    public void BBT_TC_211() {
        log("@title: Validate More Filters contains values: Live, Non Live and All (default)");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with default option");
        bbtPage.filter("", "", "", "", "", "", "", "", "");
        log("@Step 3: Expand More Filters link");
        bbtPage.btnMoreFilter.click();
        log("@Verify 1: Validate Live status contains values: Live, Non Live and All (default)");
        Assert.assertEquals(bbtPage.ddpLiveStatus.getFirstSelectedOption(), LIVE_STATUS_LIST.get(0), "FAILED! Default option was not All");
        Assert.assertEquals(bbtPage.ddpLiveStatus.getOptions(), LIVE_STATUS_LIST, "FAILED! Live status contains values: Live, Non Live and All");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "212")
    public void BBT_TC_212() {
        log("@title: Validate all bets that placed on Live/Non live events display");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Expand More Filters and select Non-live option");
        bbtPage.selectLiveNonLiveMoreFilter("Non-Live");
        log("@Verify 1: All bets that placed on Non live events display");
        Assert.assertTrue(bbtPage.lblFirstGroupName.isDisplayed(), "FAILED! No record label is not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "213")
    public void BBT_TC_213() {
        log("@title: Validate no records display if events do not have Live/Non Live bets");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Expand More Filters button");
        bbtPage.waitSpinnerDisappeared();
        bbtPage.ddpSport.selectByVisibleText("Basketball");
        bbtPage.selectLiveNonLiveMoreFilter("Live");
        log("@Verify 1: No records display if events do not have Live/Non Live bets");
        Assert.assertTrue(bbtPage.lblNoRecord.isDisplayed(), "FAILED! No record label is not displayed.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "242")
    public void BBT_TC_242() {
        String dateAPI = DateUtils.formatDate(currentDay, "dd/MM/yyyy", "yyyy-MM-dd");
        log("@title: Validate the smart group will show under the event home team section or away team section if player of this group placed bets on home/away team");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with default option");
        bbtPage.filter("", "", "", "", "", "", "", "", "");
        log("@Verify 1: The smart group will show under the event home team section or away team section respectively");
        Assert.assertTrue(bbtPage.verifyGroupNameCorrectPosition(""+0, SPORT_MAP.get(SOCCER), "PENDING", dateAPI + " 12:00:00", dateAPI + " 12:00:00"), "FAILED! The smart group is not correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @TestRails(id = "254")
    public void BBT_TC_254() {
        String smartGroup = "QA Smart Group";
        log("@title: Validate data table of each group shows correctly");
        log("@Step 1: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with default option");
        bbtPage.selectGroupsFilter(smartGroup);
        bbtPage.filter("", "", "", "", "", "", "", "", "");
        log("@Verify 1: Data table of BBT is correct");
        Assert.assertEquals(bbtPage.lblFirstGroupName.getText().trim(), smartGroup, "FAILED! Smart group name is not correct");
        Assert.assertEquals(bbtPage.lblFirstGroupLast12Day.getText().trim(), "-", "FAILED! T' column is not correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.11.30"})
    @Parameters({"password"})
    @TestRails(id = "314")
    public void BBT_TC_314(String password) throws Exception{
        String userNameOneRole = "onerole";
        log("@title: Validate can open 'Last 12 Days Performance' with correct values although SPP permission is OFF");
        log("Precondition: Account is activated permission 'BBT' and is deactivated 'SPP' permission");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("BBT", true);
        roleManagementPage.selectRole("one role").switchPermissions("SPP", false);
        log("@Step 1: Re-login with one role account account has 'BBT' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 3: Filter with default option");
        bbtPage.filter("", "", "", "", "", "", "", "", "");
        log("@Step 3: Click on 'T' column of any smart group");
        Last12DaysPerformancePage last12DaysPage = bbtPage.openLast12DayPerformanceFirstGroup();
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER, BBT), "FAILED! Retained Earnings menu is displayed");
        log("3. Validate Last 12 Days Performance is displayed correctly");
        Assert.assertTrue(last12DaysPage.getTitlePage().contains("Last 12 Days Performance"), "FAILED! Header of Last 12 Days Performance is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2135")
    public void BBT_TC_2135() {
        log("@title: Validate BBT page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("Validate BBT page is displayed with correctly title");
        Assert.assertTrue(bbtPage.getTitlePage().contains("Bets By Team"), "Failed! BBT page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2136")
    public void BBT_TC_2136() {
        log("@title: Validate UI on BBT is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);

        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, Punter Type, Sport, From Date, To Date and Show button");
        Assert.assertEquals(bbtPage.ddpCompanyUnit.getOptions(), COMPANY_UNIT_LIST_ALL, "Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(bbtPage.ddpSport.getOptions(), SPORT_LIST, "Failed! Sport dropdown is not displayed");
        Assert.assertEquals(bbtPage.ddpSmartType.getOptions(), SBPConstants.BBTPage.SMART_TYPE_LIST, "Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(bbtPage.ddpReportType.getOptions(), SBPConstants.BBTPage.REPORT_TYPE_LIST, "Failed! Report Type dropdown is not displayed");
        Assert.assertEquals(bbtPage.lblFromDate.getText(), "From Date", "Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(bbtPage.lblToDate.getText(), "To Date", "Failed! To Date datetime picker is not displayed");
        log("Show Tax Amount, Show Bet Types, Show Leagues, Smart Group, Order By Win%, Reset All Filters and More Filters");
        Assert.assertTrue(bbtPage.btnShowBetTypes.getText().contains("Show Bet Types"), "Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(bbtPage.btnShowLeagues.getText().contains("Show Leagues"), "Failed! Show Leagues button is not displayed");
        Assert.assertTrue(bbtPage.btnShowGroup.getText().contains("Show Groups"), "Failed! Show Group button is not displayed");
        Assert.assertEquals(bbtPage.btnResetAllFilter.getText(), "Reset All Filters", "Failed! Reset button is not displayed");
        Assert.assertEquals(bbtPage.btnMoreFilter.getText(), "More Filters", "Failed! More Filters button is not displayed");
        Assert.assertEquals(bbtPage.btnShow.getText(), "Show", "Failed! Show button is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2137")
    public void BBT_TC_2137() {
        log("@title: Validate Month Performance page is displayed successfully when clicking on Smart group code");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any Smart group code");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        MonthPerformancePage monthPerformancePage = bbtPage.openMonthPerformanceFirstGroup();
        String expectedHeader = lstFirstRowData.get(0) + " - " + lstFirstRowData.get(6) + " - " + "Last 12 Month Performance";

        log("Verify 1. Validate Last 12 Month Performance is displayed with format\n" +
                "[smart group name] - [smart group currency] - Last 12 Month Performance");
        Assert.assertEquals(monthPerformancePage.lblHeaderGroup.getText(), expectedHeader, "FAILED! Header of Month Performance is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2138")
    public void BBT_TC_2138() {
        log("@title: Validate Last 50 Bets page is displayed successfully when clicking on HDP points");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        Last50BetsPage last50BetsPage = bbtPage.openLast50BetsFirstGroup();
        String expectedHeader = lstFirstRowData.get(0) + " - " + lstFirstRowData.get(6);

        log("3. Validate Last 50 Bets is displayed correctly with format\n" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(last50BetsPage.lblHeaderGroup.getText(), expectedHeader, "FAILED! Header of Last 50 Bets is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2139")
    public void BBT_TC_2139() {
        log("@title: Validate League Performance page is displayed successfully when clicking on Price");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
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

    @Test(groups = {"regression"})
    @TestRails(id = "2140")
    public void BBT_TC_2140() {
        log("@title: Validate Live Last 50 Bets page is displayed successfully when clicking on first Live column");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        LiveLast50BetsPage liveLast50BetsPage = bbtPage.openLiveLast50BetsFirstGroup();
        String expectedHeaderTable1 = "Punter Performance - Last 1 Year[Live Bets]";
        String expectedHeaderTable2 = lstFirstRowData.get(0) + "-" + lstFirstRowData.get(6);

        log("Verify 3. Validate Live Last 50 Bets is displayed correctly title with 2 tables header Punter Performance - Last 1 Year[Live Bets] and" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(liveLast50BetsPage.lblSummaryTableHeader.getText(), expectedHeaderTable1, "FAILED! Header of Live Last 50 Bets - Punter Performance - Last 1 Year[Live Bets] is not displayed correct");
        Assert.assertEquals(liveLast50BetsPage.lblDetailTableHeader.getText(), expectedHeaderTable2, "FAILED! Header of Live Last 50 Bets - Smart Group is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2141")
    public void BBT_TC_2141() {
        log("@title: Validate NonLive Last 50 Bets page is displayed successfully when clicking on second Live column");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        NonLiveLast50BetsPage nonLiveLast50BetsPage = bbtPage.openNonLiveLast50BetsFirstGroup();
        String expectedHeaderTable1 = "Punter Performance - Last 1 Year[NonLive Bets]";
        String expectedHeaderTable2 = lstFirstRowData.get(0) + "-" + lstFirstRowData.get(6);

        log("3. Validate NonLive Last 50 Bets is displayed correctly title with 2 tables header Punter Performance - Last 1 Year[NonLive Bets] and" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(nonLiveLast50BetsPage.lblSummaryTableHeader.getText(), expectedHeaderTable1, "FAILED! Header of NonLive Last 50 Bets - Punter Performance - Last 1 Year[NonLive Bets] is not displayed correct");
        Assert.assertEquals(nonLiveLast50BetsPage.lblDetailTableHeader.getText(), expectedHeaderTable2, "FAILED! Header of NonLive Last 50 Bets - Smart Group is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2142")
    public void BBT_TC_2142() {
        log("@title: Validate NonLive Last 50 Bets page is displayed successfully when clicking on second Live column");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        List<String> lstFirstRowData = bbtPage.getFirstRowGroupData();
        PendingBetsPage pendingBetsPage = bbtPage.openPendingBetFirstGroup();
        String expectedHeader = lstFirstRowData.get(0) + " - " + lstFirstRowData.get(6);

        log("3. Validate NonLive Last 50 Bets is displayed correctly title with 2 tables header Punter Performance - Last 1 Year[NonLive Bets] and" +
                "[smart group name] - [smart group currency]");
        Assert.assertEquals(pendingBetsPage.getTableHeader(), expectedHeader, "FAILED! Header of NonLive Last 50 Bets - Punter Performance - Last 1 Year[NonLive Bets] is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2143")
    public void BBT_TC_2143() {
        log("@title: Validate Match Statistics (S) page is displayed succefully when clicking on S link");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        ReportS1Page reportS1Page = bbtPage.openReportS1FirstGroup();

        log("3. Validate Match Statistics (S) is displayed correctly title");
        Assert.assertTrue(reportS1Page.lblTitlePage.getText().contains("Match Statistics (S)"), "FAILED! Header of S1 Report is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2145")
    public void BBT_TC_2145() {
        log("@title: Validate Soccerway Analysis Last 2 Week Info (S12) page is displayed succefully when clicking on S12 link");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        ReportS12Page reportS12Page = bbtPage.openReportS12FirstGroup();

        log("3. Validate Soccerway Analysis Last 2 Week Info (S12) is displayed correctly title");
        Assert.assertTrue(reportS12Page.lblTitlePage.getText().contains("Soccerway Analysis Last 2 Week Info (S12)"), "FAILED! Header of S12 Report is not displayed correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2146")
    public void BBT_TC_2146() {
        log("@title: Validate Last 12 Days Performance page is displayed succefully when clicking on Last 12 Days link");
        String fromDateAPI = String.format(DateUtils.getDate(-1, "yyyy-MM-dd", GMT_7));
        String toDateAPI = String.format(DateUtils.getDate(0, "yyyy-MM-dd", GMT_7));
        String fromDate = String.format(DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7));
        String toDate = String.format(DateUtils.getDate(0, "dd/MM/yyyy", GMT_7));
        int companyId = BetEntrytUtils.getCompanyID(COMPANY_UNIT);

        List<String> lstLeagues = BBTUtils.getListAvailableLeagueBBT(String.valueOf(companyId), SPORT_MAP.get(SOCCER), "PENDING", fromDateAPI + " 12:00:00", toDateAPI + " 12:00:00");
        log("@Step 1: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER, BBT, BBTPage.class);
        log("@Step 2: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT, SOCCER, SBPConstants.BBTPage.SMART_TYPE_LIST.get(0), SBPConstants.BBTPage.REPORT_TYPE_LIST.get(0), fromDate, toDate, "", "", lstLeagues.get(0));
        log("@Step 3: Click on any HDP points");
        Last12DaysPerformancePage last12DaysPage = bbtPage.openLast12DayPerformanceFirstGroup();

        log("3. Validate Last 12 Days Performance is displayed correctly with format\n" +
                "[smart group currency] - [smart group name]");
        Assert.assertTrue(last12DaysPage.getTitlePage().contains("Last 12 Days Performance"), "FAILED! Header of Last 12 Days Performance is not displayed correct");
        log("INFO: Executed completely");
    }

}
