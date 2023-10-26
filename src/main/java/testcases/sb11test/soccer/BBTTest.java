package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.*;
import pages.sb11.soccer.BBTPage;
import testcases.BaseCaseAQS;
import utils.sb11.BBTUtils;
import utils.sb11.BetEntrytUtils;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class BBTTest extends BaseCaseAQS {

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
