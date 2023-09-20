package testcases.sb11test.soccer;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.Last12DaysPerformancePage;
import pages.sb11.soccer.MonitorBetsPage;
import pages.sb11.soccer.PendingBetsPage;
import pages.sb11.soccer.PerformanceByMonthPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

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
