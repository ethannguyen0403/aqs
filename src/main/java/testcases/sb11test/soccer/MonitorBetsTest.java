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
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.SmartGroupPage;
import pages.sb11.trading.SmartSystemPage;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class MonitorBetsTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2023.11.29"})
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
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER,MONITOR_BETS),"FAILED! Monitor Bets page displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2023.11.29"})
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
    @Test(groups = {"regression","2023.11.29"})
    @TestRails(id = "52")
    public void MonitorBetsTC_52() {
        log("@title: Validate accounts with permission can access 'Monitor Bets' page");
        log("@Pre-condition: Account is inactivated permission 'Monitor Bets'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("Verify 1: User cannot access 'Monitor Bets' page");
        Assert.assertTrue(monitorBetsPage.lblTitle.isDisplayed(), "FAILED! Monitor Bets page can access by external link");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.29"})
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
    @Test(groups = {"regression","2023.11.29"})
    @TestRails(id = "132")
    public void MonitorBetsTC_132() {
        log("@title: Validate normal punters are only shown when filter Normal Punter type");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is not added to any smart group in Trading Smart System Smart Group");
        String accNoAdd = "QALKR";
        log("@Pre-condition 3: The account has placed bet(s)");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,false,accNoAdd,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Observe the data by the default filters, Punter Type = Smart Punter");
        monitorBetsPage.filterResult("","","Smart Punter","","",false,"","","","",true);
        log("Verify 1: Bet(s) of normal punter is not shown when filter Smart Punter");
        Assert.assertFalse(monitorBetsPage.isCheckACDisplay(accNoAdd),"FAILED! Bet(s) of normal punter is shown when filter Smart Punter");
        log("@Step 4: Filter Punter Type = Normal Punter and observe the result");
        monitorBetsPage.filterResult("","","Normal Punter","","",false,"","","","",true);
        log("Verify 2: Bet(s) of normal punter is shown when filter Normal Punter");
        Assert.assertTrue(monitorBetsPage.isCheckACDisplay(accNoAdd),"FAILED! Bet(s) of normal punter is shown when filter Normal Punter");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.29"})
    @TestRails(id = "133")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_133(String accountCode) {
        log("@title: Validate smart punter are shown when filter Smart Punter type");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed bet(s)");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Observe the data by the default filters, Punter Type = Smart Punter");
        monitorBetsPage.filterResult("","","Smart Punter","","",false,"","","","",true);
        log("Verify 1: Bet(s) of smart punter is shown when filter Smart Punter");
        Assert.assertTrue(monitorBetsPage.isCheckACDisplay(accountCode),"FAILED! Bet(s) of smart punter is not shown when filter Smart Punter");
        log("@Step 4: Filter Punter Type = Normal Punter and observe the result");
        monitorBetsPage.filterResult("","","Normal Punter","","",false,"","","","",true);
        log("Verify 2: Bet(s) of smart punter is not shown when filter Normal Punter");
        Assert.assertFalse(monitorBetsPage.isCheckACDisplay(accountCode),"FAILED! Bet(s) of smart punter is not shown when filter Normal Punter");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "134")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_134(String accountCode) {
        log("@title: Validate only today events are shown when check on Today Event(s)");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed Today and Yesterday events bet(s)");
        String dateToday = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        String dateYes = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        //Place bet today
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,dateToday,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        //Place bet yesterday
        lstOrder.add(welcomePage.placeBetAPI(SOCCER,dateYes,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"").get(0));
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Check on Today Event(s) checkbox");
        monitorBetsPage.filterResult("","","","","",true,"","","","",true);
        log("Verify 1: Only bets that placed today will show");
        Assert.assertTrue(monitorBetsPage.isEventDisplayCorrect(lstOrder.get(0)),"FAILED! Bet(s) that placed today will not show");
        Assert.assertFalse(monitorBetsPage.isEventDisplayCorrect(lstOrder.get(1)),"FAILED! Bet(s) that placed yesterday will show");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "135")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_135(String accountCode) {
        log("@title: Validate only Live bets are shown when filter Live");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed Live and Non-Live events bet(s)");
        String dateToday = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        String dateYes = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        //Place bet today
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,dateToday,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        //Place bet yesterday
        lstOrder.add(welcomePage.placeBetAPI(SOCCER,dateYes,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"").get(0));
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Select Live option at Live/Non Live dropdown list");
        monitorBetsPage.filterResult("","","","","",false,"","Live","","",true);
        log("Verify 1: Only Live bets are shown");
        Assert.assertTrue(monitorBetsPage.isEventDisplayCorrect(lstOrder.get(0)),"FAILED! Bet(s) that placed live will not show");
        Assert.assertFalse(monitorBetsPage.isEventDisplayCorrect(lstOrder.get(1)),"FAILED! Bet(s) that placed non-live shows");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "136")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_136(String accountCode) {
        log("@title: Validate only non live bets are shown when filter Non Live");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed Live and Non-Live events bet(s)");
        String dateToday = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        String dateYes = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        //Place bet today
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,dateToday,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        //Place bet yesterday
        lstOrder.add(welcomePage.placeBetAPI(SOCCER,dateYes,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"").get(0));
        log("@Step 1: Login to the site");
        log("@Step 2: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Select Live option at Live/Non Live dropdown list");
        monitorBetsPage.filterResult("","","","","",false,"","Non-Live","","",true);
        log("Verify 1: Only Non-Live bets are shown");
        Assert.assertTrue(monitorBetsPage.isEventDisplayCorrect(lstOrder.get(0)),"FAILED! Bet(s) that placed non-live will not show");
        Assert.assertFalse(monitorBetsPage.isEventDisplayCorrect(lstOrder.get(1)),"FAILED! Bet(s) that placed live shows");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "137")
    @Parameters({"accountCode","masterCode"})
    public void MonitorBetsTC_137(String accountCode, String masterCode) {
        log("@title: Validate clicking on T column will show Last 12 Days Performance report");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account have some settled Bet(s) within 12 days");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.ddpBetPlacedIN.selectByVisibleText("All Hours");
        monitorBetsPage.showMasterByName(true,masterCode);
        log("@Step 3: Click T column");
        Last12DaysPerformancePage last12DaysPerformancePage = monitorBetsPage.openLast12DaysPerf(accountCode);
        log("Verify 1: Last 12 Days Performance report are shown");
        Assert.assertTrue(last12DaysPerformancePage.getTitlePage().contains("SPP Last 12 Days Performance"),"FAILED! Last 12 Days Performance report are not shown");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "138")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_138(String accountCode) throws IOException, UnsupportedFlavorException {
        log("@title: Validate the function of copy bet content works");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account has placed bet(s)");
        String smartType = "Master";
        String punterType = "Smart Punter";
        String betPlaceIn = "All Hours";
        String betCount = "Last 300 Bets";
        String lrbRule = "[LRB-Rule]";
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","HDP","Home","FullTime",1.121,-0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.filterResult(SOCCER,smartType,punterType,betPlaceIn,betCount,false,lrbRule,"ALL","HKD","ALL",true);
        log("@Step 3: Click copy button then paste the text");
        monitorBetsPage.clickToCopyByAccountCode(accountCode);
        log("Verify 1: The copied text should display properly as Report column");
        String myActual = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        String txtExpected = monitorBetsPage.getReportByAccountCode(accountCode);
        Assert.assertEquals(myActual,txtExpected,"FAILED! The copied text is wrong");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "139")
    @Parameters({"accountCode","masterCode"})
    public void MonitorBetsTC_139(String accountCode, String masterCode) {
        log("@title: Validate color of L and NL columns are shown correctly");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account have some settled Bet(s) which has %L/%NL (e.g. %L=180%, %NL =1.77%)");
        log("@Step 1: Access 'Monitor Bets' page");
        String smartType = "Master";
        String punterType = "Smart Punter";
        String betPlaceIn = "All Hours";
        String betCount = "Last 300 Bets";
        String lrbRule = "[LRB-Rule]";
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showMasterByName(false,masterCode);
        monitorBetsPage.filterResult(SOCCER,smartType,punterType,betPlaceIn,betCount,false,lrbRule,"ALL","HKD","ALL",true);
        log("@Step 3: Inspect bg color element of %L and %NL column");
        String colorL =  monitorBetsPage.getBGColorByColumnName("L",accountCode);
        String colorNL = monitorBetsPage.getBGColorByColumnName("NL",accountCode);
        log("Verify 1: Color of L and NL columns are shown correctly as mentioned");
        Assert.assertTrue(MonitorBets.COLOR_CODE_L_COLUMN.contains(colorL),"FAILED! Color of L column is shown incorrect");
        Assert.assertTrue(MonitorBets.COLOR_CODE_L_COLUMN.contains(colorNL),"FAILED! Color of NL column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "140")
    @Parameters({"accountCode","smartGroup","masterCode"})
    public void MonitorBetsTC_140(String accountCode, String smartGroup, String masterCode) {
        log("@title: Validate background color at AC column is shown as smart group defined");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        String smartType = "Master";
        String punterType = "Smart Punter";
        String betPlaceIn = "All Hours";
        String lrbRule = "[LRB-Rule]";
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM, SmartSystemPage.class);
        SmartGroupPage smartGroupPage = smartSystemPage.goToSmartGroup();
        smartGroupPage.filterSmartGroup("All","Default",smartGroup);
        log("@Pre-condition 3: The account have some settled Bet(s)");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = smartGroupPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showMasterByName(false,masterCode);
        monitorBetsPage.filterResult(SOCCER,smartType,punterType,betPlaceIn,"Last 10 Bets",false,lrbRule,"ALL","HKD","ALL",true);
        log("@Step 3: Inspect bg color element of %L and %NL column");
        String colorAC =  monitorBetsPage.getBGColorByColumnName("AC",accountCode);
        log("Verify 1: Color of L and NL columns are shown correctly as mentioned");
        Assert.assertTrue(MonitorBets.COLOR_CODE_L_COLUMN.contains(colorAC),"FAILED! Color of L column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "141")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_141(String accountCode) {
        log("@title: Validate HDP background is no color when market type is FT HDP");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT HDP in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","HDP","Home","FullTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-HDP");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays properly as below: FT HDP = no color");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT HDP"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "16182")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_16182(String accountCode) {
        log("@title: Validate HDP background is gold (#DBDB70) when market type is HT HDP");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type HT HDP in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","HDP","Home","HalfTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"HT-HDP");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays gold (#DBDB70)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("HT HDP"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "16183")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_16183(String accountCode) {
        log("@title: Validate HDP background is gold (#DBDB70) when market type is HT OU");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type HT OU in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","OU","Over","HalfTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"HT-OU");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays gold (#DBDB70)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("HT OU"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "16186")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_16186(String accountCode) {
        log("@title: Validate HDP background is light blue (#E0FFFF) when market type is FT OU");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT OU in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","OU","Over","FullTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-OU");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays light blue (#E0FFFF)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT OU"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "16188")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_16188(String accountCode) {
        log("@title: Validate HDP background is light pink (#FFE1FF) when market type is FT HDP - Corner");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT HDP - Corner in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Corners","HDP","Home","FullTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-HDP-CN");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays light pink (#FFE1FF)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT HDP - Corner"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "16189")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_16189(String accountCode) {
        log("@title: Validate HDP background is light pink (#FFE1FF) when market type is FT OU - Corner");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type FT OU - Corner in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Corners","OU","Over","FullTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"FT-OU-CN");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays light pink (#FFE1FF)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("FT OU - Corner"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "16191")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_16191(String accountCode) {
        log("@title: Validate HDP background is gold (#DBDB70) when market type is HT OU - Corner");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type HT OU - Corner in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Corners","OU","Over","HalfTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"HT-OU-CN");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays gold (#DBDB70)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("HT OU - Corner"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "16192")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_16192(String accountCode) {
        log("@title: Validate HDP background is gold (#DBDB70) when market type is HT HDP - Corner");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: In Smart System > Smart Group, 'Smart Group' is created with BG");
        log("@Pre-condition 3: The account placed bets for market type HT HDP - Corner in Bet Entry page");
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Corners","HDP","Home","HalfTime",1,0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Filter data of player account at precondition");
        monitorBetsPage.showBetType(true,"HT-HDP-CN");
        log("@Step 3: Inspect bg color element of HDP column for bets at precondition");
        String colorHDP = monitorBetsPage.getBGColorByColumnName("HDP",accountCode);
        log("Verify 1: The background color column displays gold (#DBDB70)");
        Assert.assertEquals(colorHDP,MonitorBets.COLOR_CODE_HDP_COLUMN.get("HT HDP - Corner"),"FAILED! Color of HDP column is shown incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "142")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_142(String accountCode) {
        log("@title: Validate data is shown according to the filtered stake amount");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: At 'Stake' column in 'Monitor Bets' page\n" +
                "'Smart Group' = 1,500 HKD\n" +
                "'Smart Group' = 500 HKD");
        String master = "QA Smart Master";
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                1500,"BACK",false,"");
        welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Corners","HDP","Home","FullTime",1,-0.5,"HK",
                500,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Select filters with 'Stake' = 'Above 1K");
        monitorBetsPage.ddpStake.selectByVisibleText("Above 1K");
        log("@Step 3: Click on 'Show' button");
        monitorBetsPage.showMasterByName(true,master);
        log("Verify 1: Show 'Smart Group' bet 1500 HKD");
        Assert.assertTrue(monitorBetsPage.isOrdersValidStake("Above 1K"),"FAILED! Show wrongly bet");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "143")
    @Parameters({"accountCode"})
    public void MonitorBetsTC_143(String accountCode) {
        log("@title: Validate order info display/disappear when the order is placed and deleted");
        log("@Pre-condition 1: Login account is activated permission 'Monitor Bets");
        log("@Pre-condition 2: The player account is added to any smart group in Trading Smart System Smart Group");
        log("@Pre-condition 3: The account placed bets");
        String master = "QA Smart Master";
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,dateAPI,true,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.55,"BACK",false,"");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Select filters which has bet of precondition");
        log("@Step 3: Click on 'Show' button");
        monitorBetsPage.showMasterByName(true,master);
        log("Verify 1: Validate informations of Account code in Ac column, Bet Type in Event column, Select column, HDP column");
        Assert.assertTrue(monitorBetsPage.isOrderDisplayCorrect(lstOrder.get(0)));
        log("@Step 4: Go to Confirm bets > delete bet of precondition");
        ConfirmBetsPage confirmBetsPage = monitorBetsPage.navigatePage(TRADING, CONFIRM_BETS, ConfirmBetsPage.class);
        confirmBetsPage.filter(KASTRAKI_LIMITED,"","Pending","Soccer","All","Specific Date","","",accountCode);
        confirmBetsPage.deleteOrder(lstOrder.get(0),true);
        log("Verify 2: Validate bet of precondition is not shown in Monitor Bets");
        monitorBetsPage = confirmBetsPage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        monitorBetsPage.filterResult("","","","Last 10 Min","",false,"","","","",true);
        Assert.assertFalse(monitorBetsPage.isOrderDisplayCorrect(lstOrder.get(0)));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "144")
    public void MonitorBetsTC_144() {
        log("@title: Validate data is shown according to the filtered currency");
        log("@Step 1: Access 'Monitor Bets' page");
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Select filters with 'Currency' = 'INR'");
        log("@Step 3: Click on 'Show' button");
        monitorBetsPage.filterResult("","","","All Hours","",false,"","","INR","",true);
        log("Verify 1: Verify Stake column is INR");
        Assert.assertTrue(monitorBetsPage.isCurrencyDisplayCorrect("INR"),"FAILED! Currency displays incorrect");
        log("@Step 4: Select filters with 'Currency' = 'HKD'");
        log("@Step 5: Click on 'Show' button");
        monitorBetsPage.filterResult("","","","All Hours","",false,"","","HKD","",true);
        log("Verify 2: Verify Stake column is HKD");
        Assert.assertTrue(monitorBetsPage.isCurrencyDisplayCorrect("HKD"),"FAILED! Currency displays incorrect");
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
        String accCur = "HKD";
        String smartType = "Master";
        String punterType = "Smart Punter";
        String betPlaceIn = "All Hours";
        String betCount = "Last 300 Bets";
        String lrbRule = "[LRB-Rule]";
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at AC column");
        monitorBetsPage.filterResult(SOCCER,smartType,punterType,betPlaceIn,betCount,false,lrbRule,"ALL","HKD","ALL",true);
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
        String stake = "ALL";
        String smartType = "Master";
        String punterType = "Smart Punter";
        String betPlaceIn = "All Hours";
        String betCount = "Last 300 Bets";
        String lrbRule = "[LRB-Rule]";
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at Stake column");
        monitorBetsPage.filterResult(SOCCER,smartType,punterType,betPlaceIn,betCount,false,lrbRule,"ALL","HKD",stake,true);
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
        String stake = "ALL";
        String smartType = "Master";
        String punterType = "Smart Punter";
        String betPlaceIn = "All Hours";
        String betCount = "Last 300 Bets";
        String lrbRule = "[LRB-Rule]";
        MonitorBetsPage monitorBetsPage = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 3: Filter with valid info and click Show");
        log("@Step 4: Click any currency at T column");
        monitorBetsPage.filterResult(SOCCER,smartType,punterType,betPlaceIn,betCount,false,lrbRule,"ALL","HKD",stake,true);
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
