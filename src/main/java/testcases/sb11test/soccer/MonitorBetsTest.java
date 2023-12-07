package testcases.sb11test.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.soccer.Last12DaysPerformancePage;
import pages.sb11.soccer.MonitorBetsPage;
import pages.sb11.soccer.PendingBetsPage;
import pages.sb11.soccer.PerformanceByMonthPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.SmartGroupPage;
import pages.sb11.trading.SmartSystemPage;
import testcases.BaseCaseAQS;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
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
    String master = "QA Smart Master";

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
    @TestRails(id = "132")
    @Parameters({"accountCurrency"})
    public void MonitorBetsTC_132(String accountCurrency) {
        log("@title: Validate normal punters are only shown when filter Normal Punter type");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is not added to any smart group in Trading Smart System Smart Group");
        String accNoAdd = "QALKR";
        log("@Pre-condition 3: The account has placed bet(s)");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        List<Order> lstOrder = betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accNoAdd,accountCurrency,"HDP",false,false );
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Observe the data by the default filters, Punter Type = Smart Punter");
        monitorBetsPage.ddpPunterType.selectByVisibleText("Smart Punter");
        monitorBetsPage.btnShow.click();
        log("Verify 1: Bet(s) of normal punter is not shown when filter Smart Punter");
        Assert.assertFalse(monitorBetsPage.isCheckACDisplay(accNoAdd),"FAILED! Bet(s) of normal punter is shown when filter Smart Punter");
        log("@Step 4: Filter Punter Type = Normal Punter and observe the result");
        monitorBetsPage.ddpPunterType.selectByVisibleText("Normal Punter");
        monitorBetsPage.btnShow.click();
        log("Verify 2: Bet(s) of normal punter is shown when filter Normal Punter");
        Assert.assertTrue(monitorBetsPage.isCheckACDisplay(accNoAdd),"FAILED! Bet(s) of normal punter is shown when filter Normal Punter");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "133")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_133(String accountCode, String accountCurrency) {
        log("@title: Validate smart punter are shown when filter Smart Punter type");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed bet(s)");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Observe the data by the default filters, Punter Type = Smart Punter");
        monitorBetsPage.ddpPunterType.selectByVisibleText("Smart Punter");
        monitorBetsPage.btnShow.click();
        log("Verify 1: Bet(s) of smart punter is shown when filter Smart Punter");
        Assert.assertTrue(monitorBetsPage.isCheckACDisplay(accountCode),"FAILED! Bet(s) of smart punter is not shown when filter Smart Punter");
        log("@Step 4: Filter Punter Type = Normal Punter and observe the result");
        monitorBetsPage.ddpPunterType.selectByVisibleText("Normal Punter");
        monitorBetsPage.btnShow.click();
        log("Verify 2: Bet(s) of smart punter is not shown when filter Normal Punter");
        Assert.assertFalse(monitorBetsPage.isCheckACDisplay(accountCode),"FAILED! Bet(s) of smart punter is not shown when filter Normal Punter");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "134")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_134(String accountCode, String accountCurrency) {
        log("@title: Validate only today events are shown when check on Today Event(s)");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed Today and Yesterday events bet(s)");
        int dateYesterday = -1;
        int dateToday = 0;
        String dateAPI = String.format(DateUtils.getDate(dateToday,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,"Soccer","");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateYesterday,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        betEntryPage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateToday,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Check on Today Event(s) checkbox");
        monitorBetsPage.cbTodayEvent.click();
        monitorBetsPage.btnShow.click();
        log("Verify 1: Only bets that placed today will show");
        Assert.assertTrue(monitorBetsPage.isCheckBetDisplayCorrect(accountCode,eventInfo),"FAILED! Bet(s) that placed today will not show");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "135")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_135(String accountCode, String accountCurrency) {
        log("@title: Validate only Live bets are shown when filter Live");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed Live and Non-Live events bet(s)");
        int dateYesterday = -1;
        int dateToday = 0;
        String dateAPI = String.format(DateUtils.getDate(dateYesterday,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,"Soccer","");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateYesterday,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        betEntryPage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateToday,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Select Live option at Live/Non Live dropdown list");
        monitorBetsPage.ddpLiveNonLive.selectByVisibleText("Live");
        monitorBetsPage.btnShow.click();
        log("Verify 1: Only Live bets are shown");
        Assert.assertTrue(monitorBetsPage.isCheckBetDisplayCorrect(accountCode,eventInfo),"FAILED! Bet(s) that placed live will not show");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "136")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_136(String accountCode, String accountCurrency) {
        log("@title: Validate only non live bets are shown when filter Non Live");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed Live and Non-Live events bet(s)");
        int dateYesterday = -1;
        int dateToday = 0;
        String dateAPI = String.format(DateUtils.getDate(dateToday,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,"Soccer","");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateYesterday,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        betEntryPage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateToday,"Home",false,true,
                0.5,2.12,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Select Live option at Live/Non Live dropdown list");
        monitorBetsPage.ddpLiveNonLive.selectByVisibleText("Non-Live");
        monitorBetsPage.btnShow.click();
        log("Verify 1: Only Non-Live bets are shown");
        Assert.assertTrue(monitorBetsPage.isCheckBetDisplayCorrect(accountCode,eventInfo),"FAILED! Bet(s) that placed non-live will not show");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "137")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_137(String accountCode) {
        log("@title: Validate clicking on T column will show Last 12 Days Performance report");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account have some settled Bet(s) within 12 days");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.ddpBetPlacedIN.selectByVisibleText("All Hours");
        monitorBetsPage.showMasterByName(true,master);
        log("@Step 3: Click T column");
        Last12DaysPerformancePage last12DaysPerformancePage = monitorBetsPage.openLast12DaysPerf(accountCode);
        log("Verify 1: Last 12 Days Performance report are shown");
        Assert.assertTrue(last12DaysPerformancePage.getTitlePage().contains("SPP Last 12 Days Performance"),"FAILED! Last 12 Days Performance report are not shown");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "138")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_138(String accountCode, String accountCurrency) throws IOException, UnsupportedFlavorException {
        log("@title: Validate the function of copy bet content works");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed bet(s)");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Home",false,true,
                0.5,2.121,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        log("@Step 3: Click copy button then paste the text");
        monitorBetsPage.clickToCopyByAccountCode(accountCode);
        log("Verify 1: The copied text should display properly as Report column");
        String myActual = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        String txtExpected = monitorBetsPage.getReportByAccountCode(accountCode);
        Assert.assertEquals(myActual,txtExpected,"FAILED! The copied text is wrong");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "139")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_139(String accountCode) {
        log("@title: Validate color of L and NL columns are shown correctly");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account have some settled Bet(s) which has %L/%NL (e.g. %L=180%, %NL =1.77%)");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showMasterByName(false,master);
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,betCount,false,lrbRule,liveNonLive,currency,stake,true);
        log("@Step 3: Inspect bg color element of %L and %NL column");
        String colorL =  monitorBetsPage.getBGColorByColumnName("L",accountCode);
        String colorNL = monitorBetsPage.getBGColorByColumnName("NL",accountCode);
        log("Verify 1: Color of L and NL columns are shown correctly as mentioned");
        Assert.assertTrue(MonitorBets.COLOR_CODE_L_COLUMN.contains(colorL),"FAILED! Color of L column is shown incorrect");
        Assert.assertTrue(MonitorBets.COLOR_CODE_L_COLUMN.contains(colorNL),"FAILED! Color of NL column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "140")
    @Parameters({"accountCode","smartGroup"})
    public void MonitorBetsTC_140(String accountCode, String smartGroup) {
        log("@title: Validate background color at AC column is shown as smart group defined");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM, SmartSystemPage.class);
        SmartGroupPage smartGroupPage = smartSystemPage.goToSmartGroup();
        smartGroupPage.filterSmartGroup("All","Default",smartGroup);
        String colorExpected = smartGroupPage.getColorByGroupCode(smartGroup);
        log("@Pre-condition 3: The account have some settled Bet(s)");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = smartGroupPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showMasterByName(false,master);
        monitorBetsPage.filterResult(sport,smartType,punterType,betPlaceIn,"Last 10 Bets",false,lrbRule,liveNonLive,currency,stake,true);
        log("@Step 3: Inspect bg color element of %L and %NL column");
        String colorAC =  monitorBetsPage.getBGColorByColumnName("AC",accountCode);
        log("Verify 1: Color of L and NL columns are shown correctly as mentioned");
        Assert.assertTrue(MonitorBets.COLOR_CODE_L_COLUMN.contains(colorAC),"FAILED! Color of L column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "141")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_141(String accountCode, String accountCurrency) {
        log("@title: Validate HDP background is no color when market type is FT HDP");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT HDP in Bet Entry page");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Home",true,false,
                0.5,2.121,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-HDP");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays properly as below: FT HDP = no color");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT HDP"),"FAILED! Color of L column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "16182")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_16182(String accountCode, String accountCurrency) {
        log("@title: Validate HDP background is gold (#DBDB70) when market type is HT HDP");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type HT HDP in Bet Entry page");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Home",false,false,
                0.5,2.121,"HK","Back",0,0,5.5,accountCode,accountCurrency,"HDP",false,false );
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"HT-HDP");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays gold (#DBDB70)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("HT HDP"),"FAILED! Color of L column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "16183")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_16183(String accountCode, String accountCurrency) {
        log("@title: Validate HDP background is gold (#DBDB70) when market type is HT OU");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type HT OU in Bet Entry page");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Over",false,false,
                0.5,2.121,"HK","Back",0,0,5.5,accountCode,accountCurrency,"OU",false,false);
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"HT-OU");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays gold (#DBDB70)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("HT OU"),"FAILED! Color of L column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "16186")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_16186(String accountCode, String accountCurrency) {
        log("@title: Validate HDP background is light blue (#E0FFFF) when market type is FT OU");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT OU in Bet Entry page");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Over",true,false,
                0.5,2.121,"HK","Back",0,0,5.5,accountCode,accountCurrency,"OU",false,false);
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = betEntryPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-OU");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays light blue (#E0FFFF)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT OU"),"FAILED! Color of L column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "16188")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_16188(String accountCode, String accountCurrency) {
        log("@title: Validate HDP background is light pink (#FFE1FF) when market type is FT HDP - Corner");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT HDP - Corner in Bet Entry page");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeMoreSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Home",true,false,
                0.25,2.121,"HK","Back",0,0,5.5,accountCode,accountCurrency,"Handicap - Corners",false,false);
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-HDP-CN");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays light pink (#FFE1FF)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT HDP - Corner"),"FAILED! Color of L column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.31"})
    @TestRails(id = "16189")
    @Parameters({"accountCode","accountCurrency"})
    public void MonitorBetsTC_16189(String accountCode, String accountCurrency) {
        log("@title: Validate HDP background is light pink (#FFE1FF) when market type is FT OU - Corner");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT OU - Corner in Bet Entry page");
        int dateNo = 0;
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        betEntryPage.placeMoreSoccerBet(COMPANY_UNIT,"Soccer","",dateNo,"Over",true,false,
                0.25,2.121,"HK","Back",0,0,5.5,accountCode,accountCurrency,"Over Under - Corners",false,false);
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-OU-CN");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays light pink (#FFE1FF)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT OU - Corner"),"FAILED! Color of L column is shown incorrect");
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
