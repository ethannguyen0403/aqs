package testcases.sb11test.soccer;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.Last12DaysPerformancePage;
import pages.sb11.soccer.MonitorBetsPage;
import pages.sb11.soccer.PendingBetsPage;
import pages.sb11.soccer.PerformanceByMonthPage;
import testcases.BaseCaseAQS;

import java.sql.DriverManager;

import static common.SBPConstants.*;

public class MonitorBetsTest extends BaseCaseAQS {

    String sport = "Soccer";
    String smartType = "Master";
    String punterType = "Smart Punter";
    String betPlaceIn = "All Hours";
    String betCount = "Last 300 Bets";
    String lrbRule = "[LRB-Rule]";
    String liveNonLive = "ALL";
    String currency = "ALL";
    String stake = "ALL";
    String accountCode = "No.7 SPB";
    String accCur = "HKD";

    @Test(groups = {"regression"})
    public void MonitorBetsTC_001(){
        log("@title: Validate Monitor Bets page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("Validate Monitor Bets page is displayed with correctly title");
        Assert.assertTrue(monitorBetsPage.getTitlePage().contains(MONITOR_BETS), "Failed! Monitor Bets page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void MonitorBetsTC_002(){
        log("@title: Validate UI on Monitor Bets is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("Validate UI on BL Settings is correctly displayed");
        log("Smart Type, Punter Type, Bet Placed In, Bet Count, Live/NonLive, Currency, Stake");
        Assert.assertTrue(monitorBetsPage.ddpSport.isDisplayed(),"Failed! Dropdown Sport is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpSmartType.isDisplayed(),"Failed! Dropdown Smart Type is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpPunterType.isDisplayed(),"Failed! Dropdown Punter Type is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpBetPlacedIN.isDisplayed(),"Failed! Dropdown Bet Placed In is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpBetCount.isDisplayed(),"Failed! Dropdown Bet Count is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpLRBRule.isDisplayed(),"Failed! Dropdown LRB-Rule is not displayed");
        Assert.assertTrue(monitorBetsPage.cbTodayEvent.isDisplayed(),"Failed! Today Events(s) checkbox is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpLiveNonLive.isDisplayed(),"Failed! Dropdown Live/NonLive is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpCurrency.isDisplayed(),"Failed! Dropdown Currency is not displayed");
        Assert.assertTrue(monitorBetsPage.ddpStake.isDisplayed(),"Failed! Dropdown Stake is not displayed");
        log("Show Bet Types, Show Masters, Show Traders, Show Leagues, Show Events, Reset All Filters and Show button");
        Assert.assertTrue(monitorBetsPage.lblShowBetType.isDisplayed(),"Failed! Show Bet Types label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowLeagues.isDisplayed(),"Failed! Show Leagues label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowMaster.isDisplayed(),"Failed! Show Masters label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowEvents.isDisplayed(),"Failed! Show Events label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblShowTraders.isDisplayed(),"Failed! Show Traders label is not displayed");
        Assert.assertTrue(monitorBetsPage.lblResetAllFilters.isDisplayed(),"Failed! Reset All Filters label is not displayed");
        Assert.assertTrue(monitorBetsPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Validate Monitor Bets table header columns is correctly display");
        Assert.assertEquals(monitorBetsPage.tblOrder.getHeaderNameOfRows(), MonitorBets.TABLE_HEADER,"FAILED! Monitor Bets table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    public void MonitorBetsTC_003(){
        log("@title: Validate Performance By Month is displayed correctly when clicking account at AC column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at AC column");
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        log("Validate Performance By Month is displayed correctly title");
        PerformanceByMonthPage performanceByMonthPage = monitorBetsPage.openPerfByMonth(accountCode);
        Assert.assertTrue(performanceByMonthPage.getTitlePage().contains("Performance By Month"), "Failed! Performance By Month popup is not displayed");
        log("Validate group code name is displayed correctly on header with format [smart group name] - [smart group currency] - Last 12 Month Performance");
        Assert.assertEquals(performanceByMonthPage.getTableHeader(), accountCode + " - " + accCur + " - Last 12 Month Performance");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void MonitorBetsTC_004(){
        log("@title: Validate Pending Bets is displayed correctly when clicking currency at Stake column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at Stake column");
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        log("Validate Performance By Month is displayed correctly title");
        PendingBetsPage pendingBetsPage = monitorBetsPage.openPendingBets(accountCode);
        Assert.assertTrue(pendingBetsPage.getTitlePage().contains("Pending Bets"), "Failed! Pending Bets popup is not displayed");
        log("Validate group code name is displayed correctly on header with format [smart group name] - [smart group currency]");
        Assert.assertEquals(pendingBetsPage.getTableHeader(), accountCode + " - " + accCur);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void MonitorBetsTC_005(){
        log("@title: Validate Last 12 Days Performance is displayed correctly when clicking data on T column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Monitor Bets");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at T column");
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        log("Validate Performance By Month is displayed correctly title");
        Last12DaysPerformancePage last12DaysPerformancePage = monitorBetsPage.openLast12DaysPerf(accountCode);
        Assert.assertTrue(last12DaysPerformancePage.getTitlePage().contains("Pending Bets"), "Failed! Pending Bets popup is not displayed");
        log("Validate group code name is displayed correctly on header with format [smart group name] - [smart group currency]");
        Assert.assertEquals(last12DaysPerformancePage.getTableHeader(), accCur + " - " + accountCode);
        log("INFO: Executed completely");
    }
}
