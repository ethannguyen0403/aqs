package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ess.popup.ConfirmPopup;
import pages.sb11.LoginPage;
import pages.sb11.soccer.BBGPage;
import pages.sb11.soccer.popup.bbg.BBGLastDaysPerformacesPopup;
import pages.sb11.soccer.popup.bbg.BetByTeamPricePopup;
import testcases.BaseCaseAQS;
import utils.sb11.*;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class BBGTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2147")
    public void BBG_TC_2147(){
        log("@title: Validate BBG page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("Validate BBG page is displayed with correctly title");
        Assert.assertTrue(bbgPage.getTitlePage().contains("Bets By Group"), "Failed! BBG page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2148")
    public void BBG_TC_2148(){
        log("@title: Validate BBG page is displayed when navigate");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer", KASTRAKI_LIMITED,"Group", "Pending Bets",fromdate,todate,"All","All");
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, Punter Type, Sport, From Date, To Date and Show button");
        Assert.assertEquals(bbgPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST_ALL,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpSmartType.getOptions(), SBPConstants.BBGPage.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpReportType.getOptions(), SBPConstants.BBGPage.REPORT_TYPE_LIST,"Failed! Report Type dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpWinLose.getOptions(), SBPConstants.BBGPage.WIN_LOSE_TYPE_LIST,"Failed! Win/Lose dropdown is not displayed");
        Assert.assertEquals(bbgPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(bbgPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        log("Show Tax Amount, Show Bet Types, Show Leagues, Smart Group, Order By Win%, Reset All Filters and More Filters");
        Assert.assertTrue(bbgPage.btnShowBetTypes.getText().contains("Show Bet Types"),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(bbgPage.btnShowLeagues.getText().contains("Show Leagues"),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(bbgPage.btnShowGroup.getText().contains("Show Groups"),"Failed! Show Group button is not displayed");
        Assert.assertEquals(bbgPage.btnResetAllFilter.getText(),"Reset All Filters","Failed! Reset button is not displayed");
        Assert.assertTrue(bbgPage.btnShowEvent.getText().contains("Show Events"),"Failed! Show Events button is not displayed");
        Assert.assertEquals(bbgPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2149")
    public void BBG_TC_2149(){
        log("@title: Validate League Performance page is displayed successfully when clicking on Price");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);

        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer", KASTRAKI_LIMITED,"Group", "Pending Bets",fromdate,todate,"All","All");

        log("@Step 3:Click on any Price");
        String smartGroup = bbgPage.getFristSmartGroupName();
        BetByTeamPricePopup bbtPopup = bbgPage.clickFirstPriceCell();

        log("Verify 1 Validate League Performance is displayed correctly title");
        Assert.assertEquals(bbtPopup.getTitlePage(),"Bets By Team League Performance","Failed! Popup title is incorrect");

        log("Verify 2  Validate 2 tables should display with format: [smart group name] - League Performance for Last 1 Month");
        Assert.assertEquals(bbtPopup.lblLast1Month.getText(),String.format("%s - League Performance for Last 1 Month",smartGroup),"Failed! League Performance for Last 1 Month title is incorrect");

        log("Verify 3  Validate 2 tables should display with format: [smart group name] - League Performance for Last 3 Month");
        Assert.assertEquals(bbtPopup.lblLast3Month.getText(),String.format("%s - League Performance for Last 3 Months",smartGroup),"Failed! League Performance for Last 3 Month title is incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2150")
    public void BBG_TC_2150(){
        log("@title:Validate Last 12 Days Performance page is displayed successfully when clicking on Trader");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);

        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer", KASTRAKI_LIMITED,"Group", "Pending Bets",fromdate,todate,"All","All");

        log("@Step 3:Click on any Trader");
        String smartGroupCurrency = bbgPage.getFristSmartGroupCurrency();
        String smartGroup = bbgPage.getFristSmartGroupName();
        BBGLastDaysPerformacesPopup bbtPopup = bbgPage.clickFirstTraderCell();

        log("Verify 1 Validate League Performance is displayed correctly title");
        Assert.assertEquals(bbtPopup.getTitlePage(),"BBG Last 12 Days Performance","Failed! Popup title is incorrect");

        log("Verify 2  Validate 2 tables should display with format: [smart group name] - League Performance for Last 1 Month");
        Assert.assertEquals(bbtPopup.lblLast1Month.getText(),String.format("%s - %s",smartGroupCurrency,smartGroup),"Failed! League Performance for Last 1 Month title is incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "23622")
    public void BBG_TC_23622(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the menu");
        log("@pre-condition: Analyse permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Expand Trading menu");
        log("@Verify 1: 'Analyse' menu is hidden");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER,BBT),"FAILED! BBT page displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23623")
    public void BBG_TC_23623() {
        log("@title: Validate Company Unit drop-down list");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG' page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Expand Company Unit dropdown list");
        log("@Verify 1: Company Unit includes all created company units in Accounting >> Company Set-up");
        List<String> lstEx = CompanySetUpUtils.getListCompany();
        Assert.assertTrue(page.ddpCompanyUnit.getOptions().containsAll(lstEx),"FAILED! Company Unit dropdown displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23624")
    public void BBG_TC_23624() {
        log("@title: Validate bets display accordingly when selecting each value in Company Unit list");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters which have data with a Company Unit option (e.g. Kastraki Limited)");
        log("@Step 4: Click on 'Show' button");
        page.filter("",KASTRAKI_LIMITED,"","","","","","");
        log("@Verify 1: Show all bets of all smart accounts belonging to Kastraki Limited");
        String firstAcc = page.getFirstRowGroupData().get(page.tblBBG.getColumnIndexByName("Account")-1);
        Assert.assertTrue(page.verifyAccBelongToKastraki(firstAcc),"FAILED! Account displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23637")
    public void BBG_TC_23637() {
        log("@title: Validate Sport dropdown list");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG' page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Expand Sport dropdown list");
        log("@Verify 1: Sport includes Soccer, Cricket, Basketball, Tennis, American Football and Ice Hockey");
        Assert.assertEquals(page.ddpSport.getOptions(),SPORT_LIST,"FAILED! Sport dropdown displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23638")
    public void BBG_TC_23638() {
        log("@title: Validate bets display accordingly when selecting each value in Sport list");
        log("@pre-condition 1: Account is activated permission 'BBG'");
        log("@pre-condition 2: Placed a Soccer bet");
        String accountCode = "ClientCredit-AutoQC";
        String smartGroup = "QA Smart Group";
        String sportName = "Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sportName,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("HDP").selection(event.getHome())
                .stage("FullTime").odds(1).handicap(-0.5).oddType("HK").requireStake(5.5).betType("BACK")
                .build();
        BetEntrytUtils.placeBetAPI(order);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters which have data with a Sport option (e.g. Soccer)");
        log("@Step 4: Click on 'Show' button");
        page.filter("Soccer",KASTRAKI_LIMITED,"","","","","","");
        page.filterGroups(smartGroup);
        page.filterBetTypes("FT-HDP");
        //wait for table update
        page.waitSpinnerDisappeared();
        log("@Verify 1: Show all 'Soccer' bets");
        List<String> rowEx = page.getFirstRowGroupData();
        Assert.assertEquals(order.getAccountCode(),rowEx.get(page.tblBBG.getColumnIndexByName("Account")-1),
                "FAILED! Account code displays incorrect.");
        Assert.assertEquals(order.getSelection(),rowEx.get(page.tblBBG.getColumnIndexByName("Selection")-1),
                "FAILED! Selection displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23639")
    public void BBG_TC_23639() {
        log("@title: Validate Smart Type dropdown list");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG' page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Expand Smart Type dropdown list");
        log("@Verify 1: Smart Type includes Group, Master, Agent");
        Assert.assertEquals(page.ddpSmartType.getOptions(), SBPConstants.BBGPage.SMART_TYPE_LIST,"FAILED! Smart Type dropdown displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23640")
    @Parameters({"accountCode","smartGroup"})
    public void BBG_TC_23640(String accountCode,String smartGroup) {
        log("@title: Validate bets display accordingly when selecting each value in Smart Type list");
        log("@pre-condition 1: Account is activated permission 'BBG'");
        log("@pre-condition 2: Placed a Soccer bet");
        String sportName = "Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sportName,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("HDP").selection(event.getHome())
                .stage("FullTime").odds(1).handicap(-0.5).oddType("HK").requireStake(5.5).betType("BACK")
                .build();
        BetEntrytUtils.placeBetAPI(order);
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG' page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters which have data with a Smart Type option (e.g. Group)");
        log("@Step 4: Click on 'Show' button");
        page.filter("Soccer",KASTRAKI_LIMITED,"","","","","","");
        page.filterGroups(smartGroup);
        log("@Verify 1: Show all bets of all smart accounts");
        List<String> rowEx = page.getFirstRowGroupData();
        Assert.assertEquals(order.getAccountCode(),rowEx.get(page.tblBBG.getColumnIndexByName("Account")-1),
                "FAILED! Account code displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23641")
    public void BBG_TC_23641() {
        log("@title: Validate Report Type dropdown list");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG' page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Expand Report Type dropdown list");
        log("@Verify 1: Report Type includes Pending Bets, Settled Bets");
        Assert.assertEquals(page.ddpReportType.getOptions(), SBPConstants.BBGPage.REPORT_TYPE_LIST,"FAILED! Report type dropdown displays incorrect.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23642")
    public void BBG_TC_23642() {
        log("@title: Validate bets display accordingly when selecting each value in Report Type list");
        log("@pre-condition 1: Account is activated permission 'BBG'");
        log("@pre-condition 2: Placed a Soccer bet");
        String accountCode = "ClientCredit-AutoQC";
        String smartGroup = "QA Smart Group";
        String sportName = "Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sportName,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("HDP").selection(event.getHome())
                .eventDate(dateAPI).stage("FullTime").odds(1).handicap(-0.5).oddType("HK").requireStake(5.5).betType("BACK")
                .build();
        BetEntrytUtils.placeBetAPI(order);
        List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(order);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
        BetSettlementUtils.waitForBetIsUpdate(7);
        BetSettlementUtils.sendBetSettleAPI(lstOrder.get(0));
        BetSettlementUtils.waitForBetIsUpdate(20);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters which have data with a Sport option (e.g. Soccer)");
        log("@Step 4: Click on 'Show' button");
        page.filter("Soccer",KASTRAKI_LIMITED,"","Settled Bets","","","","");
        page.filterGroups(smartGroup);
        page.filterBetTypes("FT-HDP");
        //wait for table update
        page.waitSpinnerDisappeared();
        log("@Verify 1: Show all settled bets");
        List<String> rowEx = page.getFirstRowGroupData();
        Assert.assertEquals(order.getAccountCode(),rowEx.get(page.tblBBG.getColumnIndexByName("Account")-1),
                "FAILED! Account code displays incorrect.");
        Assert.assertEquals(order.getSelection(),rowEx.get(page.tblBBG.getColumnIndexByName("Selection")-1),
                "FAILED! Selection displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "23643")
    public void BBG_TC_23643() {
        log("@title: Validate max range to filter is 7 days");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Try to select over 7 dates at From Date and To date field");
        String fromDate = String.format(DateUtils.getDate(-9,"dd/MM/yyyy",GMT_7));
        page.filter("","","","",fromDate,"","","");
        log("@Step 4: Click on 'Show' button");
        page.btnShow.click();
        String mesAc = page.appArlertControl.getWarningMessage();
        log("@Verify 1: Error message 'Date range should not be more than 7 days.' displays");
        Assert.assertEquals(mesAc, SBPConstants.BBGPage.MES_OVER_THAN_7,"FAILED! Message displays incorrect.");
        log("INFO: Executed completely");
    }

}
