package testcases.sb11test.soccer;

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
import pages.sb11.soccer.BBGPage;
import pages.sb11.soccer.popup.bbg.BBGLastDaysPerformacesPopup;
import pages.sb11.soccer.popup.bbg.BetByTeamPricePopup;
import testcases.BaseCaseAQS;
import utils.sb11.*;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2148")
    public void BBG_TC_2148(){
        log("@title: Validate BBG page is displayed when navigate");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer", KASTRAKI_LIMITED,"Group", "Pending Bets",fromdate,"","All","All");
        log(" Validate UI Info display correctly");
        bbgPage.verifyUI();
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
    @Test(groups = {"regression_stg","2024.V.2.0","ethan3.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "23622")
    public void BBG_TC_23622(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the menu");
        log("@pre-condition: Analyse permission is OFF for any account");
        RoleManagementUtils.updateRolePermission("one role","BBG","INACTIVE");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Expand Trading menu");
        log("@Verify 1: 'Analyse' menu is hidden");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER,BBG),"FAILED! BBT page displays incorrect.");
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
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        List<Order> lstOrder =  welcomePage.placeBetAPI(SOCCER,dateAPI,false,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters which have data with a Sport option (e.g. Soccer)");
        log("@Step 4: Click on 'Show' button");
        page.filter("Soccer",KASTRAKI_LIMITED,"","","","","","");
        page.filterAdvance("Group",smartGroup);
        page.filterAdvance("Bet Types","FT-HDP");
        //wait for table update
        page.waitSpinnerDisappeared();
        log("@Verify 1: Show all 'Soccer' bets");
        List<String> rowEx = page.getFirstRowGroupData();
        Assert.assertEquals(lstOrder.get(0).getAccountCode(),rowEx.get(page.tblBBG.getColumnIndexByName("Account")-1),
                "FAILED! Account code displays incorrect.");
        Assert.assertEquals(lstOrder.get(0).getSelection(),rowEx.get(page.tblBBG.getColumnIndexByName("Selection")-1),
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
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,dateAPI,false,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG' page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters which have data with a Smart Type option (e.g. Group)");
        log("@Step 4: Click on 'Show' button");
        page.filter("Soccer",KASTRAKI_LIMITED,"","","","","","");
        page.filterAdvance("Group",smartGroup);
        log("@Verify 1: Show all bets of all smart accounts");
        List<String> rowEx = page.getFirstRowGroupData();
        Assert.assertEquals(lstOrder.get(0).getAccountCode(),rowEx.get(page.tblBBG.getColumnIndexByName("Account")-1),
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

    @Test(groups = {"regression","2024.V.2.0","ethan3.0"})
    @TestRails(id = "23642")
    public void BBG_TC_23642() {
        log("@title: Validate bets display accordingly when selecting each value in Report Type list");
        log("@pre-condition 1: Account is activated permission 'BBG'");
        log("@pre-condition 2: Placed a Soccer bet");
        String accountCode = "ClientCredit-AutoQC";
        String smartGroup = "QA Smart Group";
        String dateAPI = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        List<Order> lstOrder = welcomePage.placeBetAPI(SOCCER,dateAPI,false,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",
                5.5,"BACK",false,"");
        ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
        BetSettlementUtils.waitForBetIsUpdate(7);
        lstOrder.get(0).setEventDate(DateUtils.formatDate(dateAPI,"dd/MM/yyyy","yyyy-MM-dd"));
        BetSettlementUtils.sendBetSettleAPI(lstOrder.get(0));
        BetSettlementUtils.waitForBetIsUpdate(20);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters which have data with a Sport option (e.g. Soccer)");
        log("@Step 4: Click on 'Show' button");
        page.filter("Soccer",KASTRAKI_LIMITED,"","Settled Bets","","","","");
        page.filterAdvance("Group",smartGroup);
        page.filterAdvance("Bet Types","FT-HDP");
        //wait for table update
        page.waitSpinnerDisappeared();
        log("@Verify 1: Show all settled bets");
        List<String> rowEx = page.getFirstRowGroupData();
        Assert.assertEquals(lstOrder.get(0).getAccountCode(),rowEx.get(page.tblBBG.getColumnIndexByName("Account")-1),
                "FAILED! Account code displays incorrect.");
        Assert.assertEquals(lstOrder.get(0).getSelection(),rowEx.get(page.tblBBG.getColumnIndexByName("Selection")-1),
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

    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23644")
    @Parameters({"accountCode","accountCurrency", "smartGroup"})
    public void BBG_TC_23644(String accountCode, String accountCurrency,String smartGroup) {
        log("@title: Validate can filter bets that placed with the selected currency");
        log("@pre-condition 1: Account is activated permission 'BBG'");
        log("@pre-condition 2: Player account: AC88080003 (EUR) belonging to smart group '056-liability-fin'");
        log("@pre-condition 3: Player account had Settle on 04/04/2024 - 04/09/2024");
        String date = DateUtils.getDate(-2,"dd/MM/yyyy",GMT_7);
        Order orderSettled = BetSettlementUtils.getOrderInDayByAccountCode(accountCode,date,"Soccer");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select filters with currency 'EUR' as the preconditions");
        log("@Step 4: Click on 'Show' button");
        page.filter("","","","Settled Bets",date,"","",accountCurrency);
        page.filterAdvance("Group",smartGroup);
        log("@Verify 1: Display the bet at the preconditions");
        Assert.assertTrue(page.isOrderDisplayCorrect(orderSettled,smartGroup));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23645")
    public void BBG_TC_23645() {
        log("@title: Validate Stake includes All, Above 1K, Above 10K, Above 50K, Above 150K");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Expand Stake dropdown list");
        log("@Verify 1: All bets that placed with the selected currency display");
        Assert.assertEquals(page.ddpStake.getOptions(), SBPConstants.BBGPage.STAKE_LIST,"FAILED! Stake list displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23646")
    public void BBG_TC_23646() {
        log("@title: Validate all bets that matched selected currency and stake display");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Select any currency (e.g. CNY)");
        String cur = "CNY";
        log("@Step 4: Expand Stake dropdown list then select any option (e.g. >1K)");
        log("@Step 5: Click Show button");
        String date = DateUtils.getDate(-2,"dd/MM/yyyy",GMT_7);
        page.filter("","","","Settled Bets",date,"",SBPConstants.BBGPage.STAKE_LIST.get(1),cur);
        log("@Verify 1: All bets that place with stake > 1K CNY return");
        page.verifyAllBetsShowWithStake(SBPConstants.BBGPage.STAKE_LIST.get(1));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23647")
    public void BBG_TC_23647() {
        log("@title: Validate all bets type that have bets in filtered date range display");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter From Date - To date which has data and other fields are default value");
        log("@Step 4: Click Show button");
        log("@Step 5: Click Show Bet Types dropdown list");
        String date = DateUtils.getDate(-2,"dd/MM/yyyy",GMT_7);
        page.filter("","","","Settled Bets",date,"","","");
        log("@Verify 1: All bets type (e.g. FT-HDP, HT-HDP, FT-Over/Under, HT-Over/Under) that have bets display");
        List<String> lstBetTypes = page.getLstNameInAdvanceFilter("Bet Types");
        page.verifyBetsShowCorrectByColumnName("Bet Type",lstBetTypes);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23648")
    public void BBG_TC_23648() {
        log("@title: Validate all bets in selected bet types display");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter From Date - To date which has data and other fields are default value");
        log("@Step 4: Click Show button");
        log("@Step 5: Click Show Bet Types dropdown list");
        log("@Step 6: Select any bet type (e.g. FT-HDP) then click Set Selection button");
        log("@Step 7: Click Show button");
        String date = DateUtils.getDate(-2,"dd/MM/yyyy",GMT_7);
        page.filter("","","","Settled Bets",date,"","","");
        page.filterAdvance("Bet Types","FT-HDP");
        log("@Verify 1: All bets type (e.g. FT-HDP, HT-HDP, FT-Over/Under, HT-Over/Under) that have bets display");
        List<String> lstBetType = Arrays.asList("FT-HDP");
        page.verifyBetsShowCorrectByColumnName("Bet Type",lstBetType);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23649")
    public void BBG_TC_23649() {
        log("@title: Validate all leagues that have events in filtered date range display");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter From Date - To date which has data and other fields are default value");
        log("@Step 4: Click Show button");
        log("@Step 5: Click Show Leagues dropdown list");
        log("@Step 6: Select any league then click Set Selection button");
        log("@Step 7: Click Show button");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter("","","","Settled Bets",date,"","","");
        log("@Verify 1: All leagues that have events in filtered date range display");
        String league = page.getLstNameInAdvanceFilter("Leagues").get(0);
        page.filterAdvance("Leagues",league);
        List<String> lstEvent = page.getLstNameInAdvanceFilter("Events");
        page.verifyBetsShowCorrectByColumnName("Event",lstEvent);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23650")
    public void BBG_TC_23650() {
        log("@title: Validate all groups that have bets in filtered date range display");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter From Date - To date which has data and other fields are default value");
        log("@Step 4: Click Show button");
        log("@Step 5: Click Show Groups dropdown list");
        log("@Step 6: Select any group then click Set Selection button");
        log("@Step 7: Click Show button");
        String date = DateUtils.getDate(-2,"dd/MM/yyyy",GMT_7);
        page.filter("","","","Settled Bets",date,"","","");
        String groupEx = page.getLstNameInAdvanceFilter("Group").get(0);
        page.filterAdvance("Group",groupEx);
        log("@Verify 1: Selected group in filtered date range display");
        page.verifySelectedGroupDisplay(groupEx);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23651")
    public void BBG_TC_23651() {
        log("@title: Validate all events in filtered date range display");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter From Date - To date which has data and other fields are default value");
        log("@Step 4: Click Show button");
        log("@Step 5: Click Show Events dropdown list");
        log("@Step 6: Select any event then click Set Selection button");
        log("@Step 7: Click Show button");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter("","","","Settled Bets",date,date,"","");
        List<String> lstEvent = page.getLstNameInAdvanceFilter("Events");
        log("@Verify 1: Selected group in filtered date range display");
        page.verifyBetsShowCorrectByColumnName("Event",lstEvent);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23652")
    public void BBG_TC_23652() {
        log("@title: Validate all filters reset to default if click 'Reset All Filters' button");
        log("@pre-condition: Account is activated permission 'BBG'");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Soccer >> BBG page");
        BBGPage page = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter by any field (e.g. Smart Type, Leagues) then click Show button");
        String date = DateUtils.getDate(-2,"dd/MM/yyyy",GMT_7);
        page.filter("",KASTRAKI_LIMITED,"Master","Settled Bets",date,"","Above 1K","HKD");
        log("@Step 4: Click 'Reset All Filters' link");
        page.btnResetAllFilter.click();
        page.waitSpinnerDisappeared();
        log("@Verify 1: Selected group in filtered date range display");
        page.verifyDefaultFilter();
        log("INFO: Executed completely");
    }

}
