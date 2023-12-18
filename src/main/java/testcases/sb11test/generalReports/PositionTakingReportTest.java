package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.BalanceSheetPage;
import pages.sb11.generalReports.ClientStatementPage;
import pages.sb11.generalReports.PositionTakingReportPage;
import pages.sb11.generalReports.popup.clientstatement.ClientSummaryPopup;
import pages.sb11.generalReports.popup.positionTakingReport.AccountPopup;
import pages.sb11.master.AccountSearchPage;
import pages.sb11.master.ClientSystemPage;
import pages.sb11.soccer.MonitorBetsPage;
import pages.sb11.trading.AccountPercentPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class PositionTakingReportTest extends BaseCaseAQS {
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4185")
    @Parameters({"password", "userNameOneRole"})
    public void Position_Taking_Report_4185(String password, String userNameOneRole) throws Exception {
        log("@title: Validate 'Position Taking Report' menu is hidden if not active Position Taking Report permission");
        log("@Pre-condition: Position Taking Report permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Expand General Reports menu");
        log("@Verify 1: 'Position Taking Report' menu is hidden");
        List<String> lstSubMenu = welcomePage.headerMenuControl.getListSubMenu();
        Assert.assertFalse(lstSubMenu.contains(POSITION_TAKING_REPORT),"FAILED! Monitor Bets page displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4186")
    public void Position_Taking_Report_4186() {
        log("@title: Validate 'Position Taking Report' menu displays if active Position Taking Report permission");
        log("@Pre-condition: Position Taking Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports menu");
        log("@Step 3: Select Position Taking Report item");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Verify 1: Position Taking Report page displays properly");
        Assert.assertTrue(page.lblTitle.getText().contains(POSITION_TAKING_REPORT), "FAILED! Position Taking report displays incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4187")
    public void Position_Taking_Report_4187() {
        log("@title: Validate error message displays if date range not belongs to Financial Year");
        log("@Pre-condition: Position Taking Report is ON in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Select a Financial Year");
        page.ddFinancialYear.selectByVisibleText(FINANCIAL_YEAR_LIST.get(2));
        log("@Step 4: Select date range not belongs to Financial Year at step #2");
        log("@Step 5: Click Show button");
        page.btnShow.click();
        log("@Verify 1: Error message 'Please select in range 01/08/<Year> and 31/07/<Year +1>' displays\n" +
                "As example, error message 'Please select in range 01/08/2022 and 31/07/2023.' displays");
        String mesWarning = page.alert.getWarningMessage();
        Assert.assertEquals(mesWarning,PositionTakingReport.WARNING_FINANCIAL_YEAR_MES,"FAILED! Error message display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4188")
    public void Position_Taking_Report_4188() {
        log("@title: Validate the filtered range is limited to 1 month");
        log("@Pre-condition: Position Taking Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Select a Financial Year");
        page.ddFinancialYear.selectByVisibleText(FINANCIAL_YEAR_LIST.get(3));
        log("@Step 4: Try to filter date range > 1 month");
        String dateNo = DateUtils.getDate(-40,"dd/MM/yyyy",GMT_7);
        page.dtpFromDate.selectDate(dateNo,"dd/MM/yyyy");
        log("@Step 5: Click Show button");
        page.btnShow.click();
        log("@Verify 1: Error message 'Invalid time range. You can see data up to 1 months.' displays");
        String mesWarning = page.alert.getWarningMessage();
        Assert.assertEquals(mesWarning,PositionTakingReport.INVALID_TIME_MES,"FAILED! Error message display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regressio","2023.12.29"})
    @TestRails(id = "4194")
    public void Position_Taking_Report_4194() {
        //Bug AQS-3800
        log("@title: Validate Bookie list is associated with 'PSM Group Limited' client");
        log("@Pre-condition 1: Position Taking Report permission is ON for any account");
        log("@Pre-condition 2: Get the list bookies of 'PSM Group Limited' members in Client System page");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER,CLIENT_SYSTEM,ClientSystemPage.class);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data e.g.\n" +
                "Company Unit = Kastraki Limited\n" +
                "Financial Year = Year 2022-2023\n" +
                "Bookie = All\n" +
                "From Date 5/9/2023 To Date 5/9/2023");

        log("@Step 4: Expand Bookie dropdown list and compare with list bookie at precondition");

        log("@Verify 1: Bookies list is associated with 'PSM Group Limited' client");

        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4195")
    public void Position_Taking_Report_4195() {
        log("@title: Validate data table has 2 sections: Bookies and Clients");
        log("@Pre-condition 1: Position Taking Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All","","");
        log("@Verify 1: Data table displays with 2 sections: one is showing data for Bookies and one is showing data for Clients");
        page.verifyBookieClientSectionDisplay();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4196")
    public void Position_Taking_Report_4196() {
        log("@title: Validate the Total winloss of the 2 sections are the same");
        log("@Pre-condition 1: Position Taking Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All","","");
        log("@Verify 1: The Total winloss of the 2 sections are the same");
        Assert.assertEquals(page.lblTotalWLBookie.getText(),page.lblTotalWLClient.getText(),"FAILED! Total Win Lose of 2 sections are not the same");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4197")
    public void Position_Taking_Report_4197() {
        log("@title: Validate the Bookie Name (show as a link) with the correct main CUR");
        log("@Pre-condition 1: Position Taking Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All","","");
        log("@Verify 1: The Bookie Name shows as a link with the format [<Account CUR> >> <Main CUR = HKD>] <bookie name of account that is associated with> (e.g. [CNY >> HKD] Pinnacle2)");
        Assert.assertTrue(page.isFormatBookieNameDisplay(),"FAILED! The Bookie Names do not show as a link with the format");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4198")
    public void Position_Taking_Report_4198() {
        log("@title: Validate correct Till [selected To date] displays");
        log("@Pre-condition 1: Position Taking Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page" +
                "From Date 1/7/2023 To Date current day");
        String accountCode = "6EU0288-PT";
        String clientStatement = "PSM1000 - PSM Group Limited";
        String agentCode = "PSMEU02-PT";
        String bookieName = "[EUR >> HKD] Pinnacle2";
        String fromDate = "01/08/2023";
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All","","");
        log("@Step 4: Click any bookie");
        AccountPopup accountPopup = page.clickToBookieName(bookieName);
        log("@Step 5: Get the DR-Win/Loss value of any account");
        String tillToDate = "Till "+DateUtils.getDate(0,"dd-MMM-yyyy",GMT_7);
        String tillDateValueAc = accountPopup.getTillDateValue(accountCode,tillToDate);
        accountPopup.clickToClosePopup();
        log("@Step 6: Go to 'Client Statement >> Client Detail' page");
        ClientStatementPage clientStatementPage = page.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        log("@Step 7: Filter data with the same client in the same company/date range at step #2, e.g.\n" +
                "View By = Client Point\n" +
                "Client = PSM Group Limited");
        clientStatementPage.filter("Client Point",COMPANY_UNIT,FINANCIAL_YEAR,clientStatement,fromDate,"");
        log("@Step 8: Click agent-PT of account at step #4 (e.g. PSMEU02-PT)");
        ClientSummaryPopup clientSummaryPopup = clientStatementPage.openSummaryPopup(agentCode);
        log("@Step 9: Get Win/Lose value");
        String winloseEx = clientSummaryPopup.getSummaryCellValue(accountCode,clientSummaryPopup.colWinLose);
        log("@Step 10: Compare value at step #4 and step #8");
        log("@Verify 1: The correct Win/Lose displays => values at step #4 and step #8 are the same");
        Assert.assertTrue(tillDateValueAc.contains(winloseEx),"FAILED! Win/Lose value displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4338")
    public void Position_Taking_Report_4338() {
        log("@title: Validate correct DR-Win/Loss displays");
        log("@Pre-condition 1: Position Taking Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String accountCode = "6EU0288-PT";
        String clientStatement = "PSM1000 - PSM Group Limited";
        String agentCode = "PSMEU02-PT";
        String bookieName = "[EUR >> HKD] Pinnacle2";
        String fromDate = DateUtils.getDate(-10,"dd/MM/yyyy",GMT_7);
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",fromDate,"");
        log("@Step 4: Click any bookie");
        AccountPopup accountPopup = page.clickToBookieName(bookieName);
        log("@Step 5: Get the DR-Win/Loss value of any account");
        String winloseAc = accountPopup.getWinLoseValue(accountCode);
        accountPopup.clickToClosePopup();
        log("@Step 6: Go to 'Client Statement >> Client Detail' page");
        ClientStatementPage clientStatementPage = page.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        log("@Step 7: Filter data with the same client in the same company/date range at step #2, e.g.\n" +
                "View By = Client Point\n" +
                "Client = PSM Group Limited");
        clientStatementPage.filter("Client Point",COMPANY_UNIT,FINANCIAL_YEAR,clientStatement,fromDate,"");
        log("@Step 8: Click agent-PT of account at step #4 (e.g. PSMEU02-PT)");
        ClientSummaryPopup clientSummaryPopup = clientStatementPage.openSummaryPopup(agentCode);
        log("@Step 9: Get Win/Lose value");
        String winloseEx = clientSummaryPopup.getSummaryCellValue(accountCode,clientSummaryPopup.colWinLose);
        log("@Step 10: Compare value at step #4 and step #8");
        log("@Verify 1: The correct Win/Lose displays => values at step #4 and step #8 are the same");
        Assert.assertEquals(winloseAc,winloseEx,"FAILED! Win/Lose value displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4339")
    public void Position_Taking_Report_4339() {
        log("@title: Validate correct [selected To/From date] displays");
        log("@Pre-condition: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String accountCode = "6EU0288-PT";
        String clientStatement = "PSM1000 - PSM Group Limited";
        String agentCode = "PSMEU02-PT";
        String bookieName = "[EUR >> HKD] Pinnacle2";
        String fromDate = DateUtils.getDate(-5,"dd/MM/yyyy",GMT_7);
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",fromDate,toDate);
        log("@Step 4: Click any bookie");
        AccountPopup accountPopup = page.clickToBookieName(bookieName);
        log("@Step 5: Get the [selected To/From date] (e.g. 05-Sep-23) value of any account");
        String selectDate = DateUtils.getDate(-1,"dd-MMM-yy",GMT_7);
        String winloseAc = accountPopup.getValueSelectDate(accountCode,selectDate);
        accountPopup.clickToClosePopup();
        log("@Step 6: Go to 'Client Statement >> Client Detail' page");
        ClientStatementPage clientStatementPage = page.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        log("@Step 7: Filter data with the same client in the same company/date range at step #2, e.g.\n" +
                "View By = Client Point\n" +
                "Client = PSM Group Limited\n"+
                "From Date To Date ((=Todate check at step #4)");
        clientStatementPage.filter("Client Point",COMPANY_UNIT,FINANCIAL_YEAR,clientStatement,toDate,toDate);
        log("@Step 8: Click agent-PT of account at step #4 (e.g. PSMEU02-PT)");
        ClientSummaryPopup clientSummaryPopup = clientStatementPage.openSummaryPopup(agentCode);
        log("@Step 9: Get Win/Lose value");
        String winloseEx = clientSummaryPopup.getSummaryCellValue(accountCode,8);
        log("@Step 10: Compare value at step #4 and step #8");
        log("@Verify 1: The correct Win/Lose displays => values at step #4 and step #8 are the same");
        Assert.assertEquals(winloseAc,winloseEx,"FAILED! Win/Lose value displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4340")
    public void Position_Taking_Report_4340() {
        log("@title: Validate correct Total in Bookie section displays");
        log("@Pre-condition: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String fromDate = DateUtils.getDate(-4,"dd/MM/yyyy",GMT_7);
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",fromDate,toDate);
        log("@Step 4: Check the Total value");
        log("@Verify 1: Total win/lose value = sum amount of all bookies for each column displays");
        page.verifyTotalWinLose();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4342")
    public void Position_Taking_Report_4342() {
        log("@title: Validate the correct client displays if there is a client of a similar account");
        log("@Pre-condition: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String bookie = "Pinnacle2";
        String fromDate = DateUtils.getDate(-4,"dd/MM/yyyy",GMT_7);
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,bookie,fromDate,toDate);
        log("@Step 4: Click to the first client name");
        String clientName = page.getLstClientName().get(0);
        AccountPopup accountPopup = page.clickToClientName(clientName);
        log("@Step 5: Get the first Account Code and bookie name");
        String accountDisplay = accountPopup.getFirstAccountCode();
        accountPopup.clickToClosePopup();
        log("@Step 6: Go to Master >> Account Search");
        AccountSearchPage accountSearchPage = page.navigatePage(MASTER,ACCOUNT_SEARCH,AccountSearchPage.class);
        log("@Step 7: Search Account Code similar");
        accountSearchPage.filterAccount(COMPANY_UNIT,"Account Code",accountDisplay);
        log("@Verify 1: Client Name of similar account at step 4 displays");
        Assert.assertTrue(accountSearchPage.isClientNameDisplay(clientName));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4344")
    public void Position_Taking_Report_4344() {
        log("@title: Validate 'PSM Group Limited' client displays if there is no client of a similar account");
        log("@Pre-condition: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All","","");
        log("@Verify 1: Client Name (e.g. PSM Group Limited) displays");
        Assert.assertEquals(page.tblClient.getControlOfCell(1,page.clientNameCol,1,"a").getText(),"PSM Group Limited","FAILED! Client Name display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4346")
    public void Position_Taking_Report_4346() {
        log("@title: Validate there is a pagination if filtering more than 4 days");
        log("@Pre-condition: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data on more than 4 day");
        String fromDate = DateUtils.getDate(-4,"dd/MM/yyyy",GMT_7);
        String toDate = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",fromDate,toDate);
        log("@Verify 1: The pagination will displays as below:\n" +
                "[selected To date](e.g. 05-Sep-23)\n"+
                "Each page will show max is 4 days then click Next/Previous to view data on other days on the next /previous page");
        String selectToDate = DateUtils.getDate(0,"dd-MMM-yy",GMT_7);
        page.verifyDateColumnDisplay(selectToDate,5);
        log("@Step 4: Click Next/Previous button");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",fromDate,toDate);
        page.btnNext.click();
        page.waitSpinnerDisappeared();
        log("@Verify 2: Columns Bookie Name/Client Name, Till [selected To date], DR-Win/Loss still keep when clicking Next/Previous button");
        String toDateHeaderName = DateUtils.getDate(0,"dd-MMM-yyyy",GMT_7);
        page.verifyDefaultHeadNameDisplay(toDateHeaderName);
        page.btnPrevious.click();
        page.waitSpinnerDisappeared();
        page.verifyDefaultHeadNameDisplay(toDateHeaderName);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4348")
    public void Position_Taking_Report_4348() {
        log("@title: Validate the Position Taking Report >> Account(s) dialog displays when clicked Bookie/Client link");
        log("@Pre-condition: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String bookieName = "[EUR >> HKD] Pinnacle2";
        String clientName = "PSM Group Limited";
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All","","");
        log("@Step 4: Click a Bookie link");
        AccountPopup accountPopup = page.clickToBookieName(bookieName);
        log("@Verify 1: The Position Taking Report >> Account(s) dialog displays");
        Assert.assertTrue(accountPopup.lblTitle.getText().trim().contains("Account(s)"),"FAILED! Title Account(s) popup display incorrect");
        accountPopup.clickToClosePopup();
        log("@Step 5: Click a Client link");
        accountPopup = page.clickToClientName(clientName);
        log("@Verify 2: The Position Taking Report >> Account(s) dialog displays");
        Assert.assertTrue(accountPopup.lblTitle.getText().trim().contains("Account(s)"),"FAILED! Title Account(s) popup display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4351")
    public void Position_Taking_Report_4351() {
        log("@title: Validate the Grand Total in HKD of each column in detail is matched with main page");
        log("@Pre-condition: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String bookieName = "[EUR >> HKD] Pinnacle2";
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",toDate,toDate);
        log("@Step 4: Get Total value of each column in any Bookie  (e.g. [EUR >> HKD] BETISN -> Till 05-Sep-2023 =-25,179.78)");
        String tillToDate = "Till "+ DateUtils.getDate(-1,"dd-MMM-yyyy",GMT_7);
        String valueTillAc = page.getValueTillColumn(bookieName,tillToDate);
        log("@Step 5: Click the bookie link at step #4");
        AccountPopup accountPopup = page.clickToBookieName(bookieName);
        log("@Step 6: Get the Total/Grand Total in HKD of each column");
        String totalInHDK = accountPopup.getValueBookieTotalTillColumn();
        log("@Verify 1: The Total value of each column is matched each other");
        Assert.assertEquals(valueTillAc,totalInHDK,"FAILED! The Total value of Till column is not matched");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4353")
    public void Position_Taking_Report_4353() {
        log("@title: Validate the correct PT% of account displays");
        log("@Pre-condition 1: Position Taking Report permission is ON");
        log("@Pre-condition 2: Go to Account Percent >> get value Actual WinLoss% of account that if having");
        String accountPT = "6EU0206001";
        String bookie = "Pinnacle2";
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT, AccountPercentPage.class);
        accountPercentPage.filterAccount(bookie,"",accountPT);
        String accWL = accountPercentPage.getAccPT(accountPT);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String bookieName = "[EUR >> HKD] Pinnacle2";
        PositionTakingReportPage page = accountPercentPage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",toDate,toDate);
        log("@Step 4: Click any Bookie/Client link");
        String accountCode = "6EU0288-PT";
        AccountPopup accountPopup = page.clickToBookieName(bookieName);
        log("@Verify 1: Show the Account Percent if having or showing as 0.00 if there is no setting");
        accountPopup.checkPTOfAccount(accountCode,accWL);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "4358")
    public void Position_Taking_Report_4358() {
        log("@title: Validate the correct W/L% displays");
        log("@Pre-condition 1: Position Taking Report permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> Position Taking Report page");
        String bookieName = "[EUR >> HKD] Pinnacle2";
        PositionTakingReportPage page = welcomePage.navigatePage(GENERAL_REPORTS,POSITION_TAKING_REPORT, PositionTakingReportPage.class);
        log("@Step 3: Filter which has data");
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter(COMPANY_UNIT,FINANCIAL_YEAR,"All",toDate,toDate);
        log("@Step 4: Click any Bookie/Client link");
        String accountCode = "6EU0288-PT";
        AccountPopup accountPopup = page.clickToBookieName(bookieName);
        log("@Verify 1: The correct W/L%: = (DR-Win/Loss/ DR – Turnover) * 100 displays");
        accountPopup.verifyWL(accountCode);
        log("INFO: Executed completely");
    }
}
