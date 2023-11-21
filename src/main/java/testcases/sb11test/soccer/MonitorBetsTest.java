package testcases.sb11test.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.soccer.BBTPage;
import pages.sb11.soccer.Last12DaysPerformancePage;
import pages.sb11.soccer.MonitorBetsPage;
import pages.sb11.soccer.PendingBetsPage;
import pages.sb11.soccer.PerformanceByMonthPage;
import testcases.BaseCaseAQS;
import utils.sb11.AccountSearchUtils;
import utils.sb11.BetEntrytUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.List;

import static common.SBPConstants.*;

public class MonitorBetsTest extends BaseCaseAQS {

    String sport = "Soccer";
    String smartType = "Master";
    String punterType = "Smart Punter";
    String betPlaceIn = "All Hours";
    String betCount = "Last 300 Bets";
    String lrbRule = "[LRB-Rule]";
    String liveNonLive = "ALL";
    String currency = "HKD";
    String stake = "ALL";
    String accountCode = "No.7 SPB";
    String accCur = "HKD";

    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "50")
    @Parameters({"password", "userNameOneRole"})
    public void MonitorBetsTC_50(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the 'Monitor Bets' menu");
        log("@Pre-condition: Account is inactivated permission 'Monitor Bets'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Check menu item 'Monitor Bets' under menu 'Soccer'");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("Verify 1: Menu 'Monitor Bets' is not shown");
        List<String> lstSubMenu = welcomePage.headerMenuControl.getListSubMenu();
        Assert.assertFalse(lstSubMenu.contains(MONITOR_BETS),"FAILED! Monitor Bets page displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "51")
    @Parameters({"password", "userNameOneRole"})
    public void MonitorBetsTC_51(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot access 'Monitor Bets' page");
        String monitorBetsPageUrl = environment.getSbpLoginURL() + "#/aqs-report/monitor-bets";
        log("@Pre-condition: Account is inactivated permission 'Monitor Bets'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Trying to access page by using url:");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        DriverManager.getDriver().get(monitorBetsPageUrl);
        log("Verify 1: User cannot access 'Monitor Bets' page");
        Assert.assertFalse(new MonitorBetsPage().lblTitle.isDisplayed(), "FAILED! Monitor Bets page can access by external link");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "52")
    public void MonitorBetsTC_52() {
        log("@title: Validate accounts with permission can access 'Monitor Bets' page");
        log("@Pre-condition: Account is inactivated permission 'Monitor Bets'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("Verify 1: User cannot access 'Monitor Bets' page");
        Assert.assertTrue(new MonitorBetsPage().lblTitle.isDisplayed(), "FAILED! Monitor Bets page can access by external link");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "53")
    public void MonitorBetsTC_53(){
        log("@title: Validate sound is played if a new bet comes when speaker is on");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: Punter account is added to a smart group in Trading Smart System Smart Group");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Validate when there's a new bet of the punter account come");
        log("Verify 1: Bet(s) is auto shown on the report without refreshing the page");
        Assert.assertTrue(monitorBetsPage.isCheckBetsUpdateCorrect(),"FAILED! Bets update incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "54")
    public void MonitorBetsTC_54() {
        log("@title: Validate new bets are auto added without refreshing page");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: Punter account is added to a smart group in Trading Smart System Smart Group");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Validate when there's a new bet of the punter account come");
        log("Verify 1: Bet(s) is auto shown on the report without refreshing the page");
        Assert.assertTrue(monitorBetsPage.isCheckBetsUpdateCorrect(),"FAILED! Bets update incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2101")
    public void MonitorBetsTC_2101(){
        log("@title: Validate Monitor Bets page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("Validate Monitor Bets page is displayed with correctly title");
        Assert.assertTrue(monitorBetsPage.getTitlePage().contains(MONITOR_BETS), "Failed! Monitor Bets page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2102")
    public void MonitorBetsTC_2102(){
        log("@title: Validate UI on Monitor Bets is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("Validate UI on BL Settings is correctly displayed");
        log("Smart Type, Punter Type, Bet Placed In, Bet Count, Live/NonLive, Currency, Stake");
        Assert.assertEquals(monitorBetsPage.ddpSport.getOptions(),MonitorBets.SPORT_LIST,"Failed! Dropdown Sport is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpSmartType.getOptions(), MonitorBets.SMART_TYPE_LIST,"Failed! Dropdown Smart Type is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpPunterType.getOptions(),MonitorBets.PUNTER_TYPE_LIST,"Failed! Dropdown Punter Type is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpBetPlacedIN.getOptions(),MonitorBets.BET_PLACED_IN,"Failed! Dropdown Bet Placed In is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpBetCount.getOptions(),MonitorBets.BET_COUNT,"Failed! Dropdown Bet Count is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpLRBRule.getOptions().get(0),"[LRB-Rule]","Failed! Dropdown LRB-Rule is not displayed");
        Assert.assertEquals(monitorBetsPage.lblTodayEvent.getText(),"Today Event(s)","Failed! Today Events(s) checkbox is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpLiveNonLive.getOptions(),LIVE_NONLIVE_LIST,"Failed! Dropdown Live/NonLive is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpCurrency.getOptions(),CURRENCY_LIST,"Failed! Dropdown Currency is not displayed");
        Assert.assertEquals(monitorBetsPage.ddpStake.getOptions(),STAKE_LIST_ALL,"Failed! Dropdown Stake is not displayed");
        log("Show Bet Types, Show Masters, Show Traders, Show Leagues, Show Events, Reset All Filters and Show button");
        Assert.assertTrue(monitorBetsPage.lblShowBetType.isDisplayed(),"Failed! Show Bet Types label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowLeagues.isDisplayed(),"Failed! Show Leagues label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowMaster.isDisplayed(),"Failed! Show Masters label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowEvents.isDisplayed(),"Failed! Show Events label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowTraders.isDisplayed(),"Failed! Show Traders label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblResetAllFilters.isDisplayed(),"Failed! Reset All Filters label is not displayed");
        Assert.assertEquals(monitorBetsPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("Validate Monitor Bets table header columns is correctly display");
        Assert.assertEquals(monitorBetsPage.tblOrder.getHeaderNameOfRows(), MonitorBets.TABLE_HEADER,"FAILED! Monitor Bets table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2103")
    public void MonitorBetsTC_2103(){
        log("@title: Validate Performance By Month is displayed correctly when clicking account at AC column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at AC column");
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        String accountName = monitorBetsPage.tblOrder.getColumn(monitorBetsPage.colAC,5,false).get(0).split("\n")[0];
//        String accCurrency = monitorBetsPage.tblOrder.getColumn(monitorBetsPage.colStake,5,false).get(0).split("\n")[2];
        log("Validate Performance By Month is displayed correctly title");
        PerformanceByMonthPage performanceByMonthPage = monitorBetsPage.openPerfByMonth(accountName);
        Assert.assertTrue(performanceByMonthPage.getTitlePage().contains("Performance By Month"), "Failed! Performance By Month popup is not displayed");
        log("Validate group code name is displayed correctly on header with format [smart group name] - [smart group currency] - Last 12 Month Performance");
        Assert.assertEquals(performanceByMonthPage.getTableHeader(), accountName + " - " + accCur + " - Last 12 Month Performance");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2104")
    public void MonitorBetsTC_2104(){
        log("@title: Validate Pending Bets is displayed correctly when clicking currency at Stake column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at Stake column");
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        String accountName = monitorBetsPage.tblOrder.getColumn(monitorBetsPage.colAC,5,false).get(0).split("\n")[0];
        String accCurrency = monitorBetsPage.tblOrder.getColumn(monitorBetsPage.colStake,5,false).get(0).split("\n")[2];
        log("Validate Performance By Month is displayed correctly title");
        PendingBetsPage pendingBetsPage = monitorBetsPage.openPendingBets(accountName);
        Assert.assertTrue(pendingBetsPage.getTitlePage().contains("Pending Bets"), "Failed! Pending Bets popup is not displayed");
        log("Validate group code name is displayed correctly on header with format [smart group name] - [smart group currency]");
        Assert.assertEquals(pendingBetsPage.getTableHeader(), accountName + " - " + accCurrency);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2105")
    public void MonitorBetsTC_2105(){
        log("@title: Validate Last 12 Days Performance is displayed correctly when clicking data on T column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at T column");
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        String accountName = monitorBetsPage.tblOrder.getColumn(monitorBetsPage.colAC,5,false).get(0).split("\n")[0];
        String accCurrency = monitorBetsPage.tblOrder.getColumn(monitorBetsPage.colStake,5,false).get(0).split("\n")[2];
        log("Validate Performance By Month is displayed correctly title");
        Last12DaysPerformancePage last12DaysPerformancePage = monitorBetsPage.openLast12DaysPerf(accountName);
        Assert.assertTrue(last12DaysPerformancePage.getTitlePage().contains("Last 12 Days Performance"), "Failed! Pending Bets popup is not displayed");
        log("Validate group code name is displayed correctly on header with format [smart group name] - [smart group currency]");
        Assert.assertEquals(last12DaysPerformancePage.getTableHeader(), accCurrency + " - " + accountName);
        log("INFO: Executed completely");
    }
}
