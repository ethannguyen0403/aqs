package testcases.sb11test.accounting;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.accounting.CompanySetupPage;
import pages.sb11.accounting.popup.CompanySetupCreatePopup;
import pages.sb11.generalReports.*;
import pages.sb11.generalReports.bookiebalance.BalanceDetailPage;
import pages.sb11.generalReports.popup.bookiestatement.*;
import pages.sb11.generalReports.popup.clientBalance.ClientBalanceDetailPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientMemberTransactionPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientSummaryPopup;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.ArrayList;

import static common.SBPConstants.*;

public class CompanySetupTest extends BaseCaseAQS {
    String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
    String previousDate = DateUtils.getPreviousDate(currentDate, "yyyy-MM-dd");

    @TestRails(id = "4332")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password"})
    public void Company_Set_up_TC4332(String password) throws Exception{
        String userNameOneRole = "onerole";
        log("@title: Validate accounts without permission cannot see the menu");
        log("@Precondition: Deactivate Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", false);
        log("@Step 1: Re-login with one role account account has 'Company Set-up' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        JournalEntries retainedEarningsPage =
                welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntries.class);
        log("@Verify 1: Company Set-up menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(ACCOUNTING, COMPANY_SETUP),
                "FAILED! Cash Flow Statement menu is displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4333")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password"})
    public void Company_Set_up_TC4333(String password) throws Exception{
        String userNameOneRole = "onerole";
        String companyURL = environment.getSbpLoginURL() + "#/aqs-report/company-set-up";
        log("@title: Validate accounts without permission cannot access page by external link");
        log("@Precondition: Deactivate Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", false);
        log("@Step 1: Re-login with one role account account has 'Company Set-up' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: Company Set-up menu can not access by external link: " + companyURL);
        DriverManager.getDriver().get(companyURL);
        Assert.assertFalse(new CompanySetupPage().lblTitle.isDisplayed(), "FAILED! Company Set-up page can access by external link");
        log("INFO: Executed completely");
    }


    @TestRails(id = "4334")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password"})
    public void Company_Set_up_TC4334(String password) throws Exception{
        String userNameOneRole = "onerole";
        log("@title: Validate accounts with permission can access page");
        log("@Precondition: Active Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", true);
        log("@Step 1: Login to SB11 site with account has 'Company Set-up' permission is ON");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Navigate to Accounting > Company Set-up");
        CompanySetupPage companySetupPage = welcomePage.navigatePage(ACCOUNTING, COMPANY_SETUP, CompanySetupPage.class);
        log("@Verify 1: Validate page is displayed");
        Assert.assertTrue(companySetupPage.lblTitle.getText().contains(COMPANY_SETUP), "FAILED! Page Title is incorrect displayed.");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4335")
    @Test(groups = {"regression", "2023.10.31"})
    public void Company_Set_up_TC4335() {
        log("@title: Validate UI on 'Create Company' dialog is shown correctly");
        log("@Step 1: Navigate to Accounting > Company Set-up");
        CompanySetupPage companySetupPage = welcomePage.navigatePage(ACCOUNTING, COMPANY_SETUP, CompanySetupPage.class);
        log("@Step 2: Click on create button");
        CompanySetupCreatePopup createPopup = companySetupPage.openCreatePopup();
        log("@Verify 1: Validate Create Company popup displayed correct");
        Assert.assertTrue(createPopup.txtCompanyName.isDisplayed() && createPopup.txtCompanyName.isEnabled(), "Failed! Company name text box is not displayed");
        Assert.assertTrue(createPopup.txtCompanyAddress.isDisplayed() && createPopup.txtCompanyName.isEnabled(), "Failed! Company address text box is not displayed");
        Assert.assertTrue(createPopup.btnClear.isDisplayed(), "Failed! Button Clear is not displayed");
        Assert.assertTrue(createPopup.btnSubmit.isDisplayed(), "Failed! Button Submit is not displayed");
        Assert.assertEquals(createPopup.ddlFirstMonth.getOptions(), MONTH_NAME_LIST, "Failed! Accounting Period - First month option list is not correct");
        Assert.assertEquals(createPopup.ddlClosingMonth.getOptions(), MONTH_NAME_LIST, "Failed! Accounting Period - Closing month option list is not correct");
        Assert.assertEquals(createPopup.ddlCurrency.getOptions(), CURRENCY_LIST_WITHOUT_ALL,"Failed! Dropdown Currency option list is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4343")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "currency"})
    public void Company_Set_up_TC4343(String companyName, String currency) {
        String toDayAvoidLastDayOfMonth = "";
        log("@title: Validate that show the reporting currency of Company correctly in 'Ledger Statement' page");
        String expectedText1 = "Total in " + currency;
        String expectedText2 = "Grand Total in " + currency;
        String expectedText3 = "Amounts are shown in " + currency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, currency) );
        log("@Step 1: Navigate to  General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);

        toDayAvoidLastDayOfMonth = ledgerStatementPage.isLastDayOfMonth() ? previousDate : "";
        log("@Step 2: Filter with Company: "+ companyName);
        ledgerStatementPage.showLedger(companyName, "","","","",toDayAvoidLastDayOfMonth, "");

        log(String.format("@Verify 1: Validate  the table has \"CUR transaction in %s\" is NOT displayed.", currency));
        Assert.assertFalse(ledgerStatementPage.lblAmountShowCurrency.isDisplayed(),  "FAILED! The table CUR transaction is shown");
        log("@Verify 2: Validate shows text correct with currency: " + currency);
        Assert.assertEquals(ledgerStatementPage.getDescriptionTotalAmountInOriginCurrency("Total in"), expectedText1, "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.lblGrandTotalInOrigin.getText().trim(),expectedText2,  "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");

        log("@Step 3: Click on first ledger name to open Ledger Detail");
        log("@Verify 3: Validate shows text correct with currency: " + currency);
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerFirstDetail();
        Assert.assertEquals(ledgerDetailPopup.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4345")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "currency"})
    public void Company_Set_up_TC4345(String companyName, String currency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Client Balance' page");
        String expectedText1 = "Total Balance " + currency;
        String expectedText2 = "Total Balance in " + currency;
        String expectedText3 = "Grand Total in " + currency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, currency) );
        log("@Step 1: Navigate to  General Reports >> Client Balance");
        ClientBalancePage clientBalancePage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_BALANCE, ClientBalancePage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        clientBalancePage.filter("", companyName, "", "","");

        log(String.format("@Verify 1: Validate shows text correct with currency %s after submit filter", currency));
        Assert.assertEquals(clientBalancePage.ddShowTotal.getFirstSelectedOption(), currency, "FAILED! Text of Show Total dropdown is not correct");
        Assert.assertEquals(clientBalancePage.tblClientBalance.getHeaderNameOfRows().get(4), expectedText1, "FAILED! Text Total Balance is not correct");

        log("@Step 3: Click on Client Balance name >> Client Balance detail popup");
        ClientBalanceDetailPopup detailPopup = clientBalancePage.goToClientDetail("sd");

        log(String.format("@Verify 2: Validate shows text correct with currency %s on Client detail popup", currency));
        Assert.assertEquals(detailPopup.tblClientSuper.getHeaderNameOfRows().get(4), expectedText2, "FAILED! Text in Client Balance detail popup is not correct");
        Assert.assertEquals(detailPopup.lblGrandTotalMaster.getText().trim(), expectedText3, "FAILED! Text Client Balance detail popup is not correct");
        Assert.assertEquals(detailPopup.lblGrandTotalFooter.getText().trim(), expectedText3, "FAILED! Text Client Balance detail popup is not correct");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4354")
    public void Company_Set_up_TC4354() {
        log("@title: Validate that show currency 'HKD' in 'Client Balance' page when filtering Company Unit = All");
        log("@Step 1: Go to General Report >> Client Balance");
        ClientBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.CLIENT_BALANCE,ClientBalancePage.class);
        log("@Step 2: Select valid values with company unit 'All'");
        page.filter("","All","","","");
        log("@Step 3: Click on 'Show' button");
        log("@Step 4: Observe the result");
        log("@Verify 1: Show 'Total Balance HKD'.");
        Assert.assertTrue(page.tblClientBalance.getControlOfCellSPP(1,page.totalCol,1,null).isDisplayed(),"FAILED! Total Balance display incorrect.");
        log("@Verify 2: Show Total In’ dropdown disable");
        Assert.assertFalse(page.ddShowTotal.isEnabled(),"FAILED! Show Total In dropdown display incorrect.");
        log("@Verify 3: In the ‘Balance Detail’, show ‘Total Balance in HKD’ and ‘Grand Total in HKD");
        String clientName = page.tblClientBalance.getControlOfCellSPP(1,page.colClientName,1,null).getText();
        ClientBalanceDetailPopup clientBalanceDetailPage = page.goToClientDetail(clientName);
        Assert.assertEquals(clientBalanceDetailPage.tblClientSuper.getControlOfCell(1, clientBalanceDetailPage.colTotalBalance,1,"span").getText(),
                clientBalanceDetailPage.lblValueGrandTotalFooter.getText(),"FAILED! Total Balance in HKD display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4355")
    public void Company_Set_up_TC4355() {
        log("@title: Validate that show currency 'HKD' in 'Bookie Balance' page when filtering Company Unit = All ");
        log("@Step 1: Go to General Report >> Bookie Balance");
        BookieBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.BOOKIE_BALANCE,BookieBalancePage.class);
        log("@Step 2: Select valid values with company unit 'All'");
        page.filter("All","","","","");
        log("@Step 3: Click on 'Show' button");
        log("@Step 4: Observe the result");
        log("@Verify 1: Show 'Total Balance HKD'.");
        Assert.assertFalse(page.tblBookie.getColumn(page.colTotalInHKD,false).isEmpty(),"FAILED! Total Balance display incorrect.");
        log("@Verify 2: Show Total In’ dropdown disable");
        Assert.assertFalse(page.ddShowTotalIn.isEnabled(),"FAILED! Show Total In dropdown display incorrect.");
        log("@Verify 3: In the ‘Balance Detail’, show ‘Total Balance in HKD’ and ‘Grand Total in HKD");
        String bookieName = page.tblBookie.getColumn(page.colBookie,false).get(0);
        BalanceDetailPage balanceDetailPage = page.openBalanceDetailByBookie(bookieName);
        Assert.assertTrue(balanceDetailPage.tblGrandTotal.getHeaderNameOfRows().contains("Grand Total in HKD"),"FAILED! Grand Total in HKD display incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4356")
    @Parameters({"clientCode","accountCode"})
    public void Company_Set_up_TC4356(String clientCode, String accountCode) {
        log("@title: Validate that show currency 'HKD' in 'Client Statement' page when filtering Company Unit = All ");
        log("@Step 1: Go to General Report >> Client Statement");
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        log("@Step 2: Select valid values with company unit 'All'");
        log("@Step 3: Click on 'Show' button");
        clientCode = superMasterCode + clientCode;
        String fromDate = String.format("01/%s/%s",DateUtils.getMonth(GMT_7), DateUtils.getYear(GMT_7));
        clientPage.filter(viewBy,COMPANY_UNIT,FINANCIAL_YEAR,clientCode,fromDate,"");
        log("@Step 4: Observe the result");
        log("@Step 5: Open Member Summary");
        ClientSummaryPopup clientSummaryPopup = clientPage.openSummaryPopup("QASAHK00");
        log("@Step 6: Open Member Transactions");
        ClientMemberTransactionPopup clientMemberTransactionPopup = clientSummaryPopup.openMemberTransactionPopup(accountCode);
        log("@Verify 1: show ‘Credit [HKD]’, ‘Debit [HKD]’ and ‘Running [HKD]’");
        ArrayList<String> lstHeader = clientMemberTransactionPopup.tblWinloseSummary.getHeaderNameOfRows();
        Assert.assertTrue(lstHeader.contains("Credit [HKD]"),"FAILED! Credit [HKD] display incorrect");
        Assert.assertTrue(lstHeader.contains("Debit [HKD]"),"FAILED! Debit [HKD] display incorrect");
        Assert.assertTrue(lstHeader.contains("Running [HKD]"),"FAILED! Running [HKD] display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4357")
    @Parameters({"bookieSuperMasterCode","bookieMasterCode","accountCode"})
    public void Company_Set_up_TC4357(String bookieSuperMasterCode, String bookieMasterCode, String accountCode) throws InterruptedException {
        log("@title: Validate that show currency 'HKD' in 'Bookie Statement' page when filtering Company Unit = All");
        log("@pre-condition: Login with valid account");
        log("@Step 1: Go to General Report >> Bookie Statement");;
        String agentCode = "A-QA10101-QA Test";
        String fromDate = DateUtils.getDate(-10,"dd/MM/yyyy",GMT_7);
        BookieStatementPage page = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT,BookieStatementPage.class);
        log("@Step 2: Select valid values with company unit 'All'");
        log("@Step 3: Select agent type 'Super Master'");
        log("@Step 4: Click on 'Show' button and observe");
        page.filter("All","","Super Master",fromDate,"","QA Bookie","");
        log("@Verify 1: Show text to ‘Grand Total in [HKD]’ in the bottom report");
        Assert.assertEquals(page.tblGrandTotal.getHeaderNameOfRows().get(1),"Grand Total in","FAILED! Grand total in display incorrrect.");
        Assert.assertEquals(page.tblGrandTotal.getHeaderNameOfRows().get(2),"HKD","FAILED! Cur of Grand total in display incorrrect.");
        log("@Step 5: Click on master code and observe");
        BookieMasterAgentDetailPopup bookieMasterAgentDetailPopup = page.openBookieMasterAgentDetailPopup(bookieSuperMasterCode,bookieMasterCode);
        log("@Verify 2: show header table text: 'Debit [HKD]', 'Credit [HKD]', 'Running [HKD]'.");
        ArrayList<String> lstHeader1 = bookieMasterAgentDetailPopup.tblMDetail.getHeaderNameOfRows();
        Assert.assertTrue(lstHeader1.contains("Debit [HKD]"),"FAILED! Debit [HKD] display incorrect.");
        Assert.assertTrue(lstHeader1.contains("Credit [HKD]"),"FAILED! Credit [HKD] display incorrect.");
        Assert.assertTrue(lstHeader1.contains("Running [HKD]"),"FAILED! Running [HKD] display incorrect.");
        log("@Step 6: Close the dialog");
        bookieMasterAgentDetailPopup.closeIcon.click();
        log("@Step 7: Click on 'AS'");
        BookieAgentSummaryPopup bookieAgentSummaryPopup = page.openBookieAgentSummary(bookieSuperMasterCode,bookieMasterCode);
        BookieAgentDetailPopup bookieAgentDetailPopup = bookieAgentSummaryPopup.openAgentDetailPopup(agentCode);
        log("@Verify 3: show header table text: 'Debit [HKD]', 'Credit [HKD]', 'Running [HKD]'.");
        ArrayList<String> lstHeader2 = bookieAgentDetailPopup.tblAgentDetail.getHeaderNameOfRows();
        Assert.assertTrue(lstHeader2.contains("Debit [HKD]"),"FAILED! Debit [HKD] display incorrect.");
        Assert.assertTrue(lstHeader2.contains("Credit [HKD]"),"FAILED! Credit [HKD] display incorrect.");
        Assert.assertTrue(lstHeader2.contains("Running [HKD]"),"FAILED! Running [HKD] display incorrect.");        log("@Step 8: Click on MS and observe");
        log("@Step 8: Close dialog");
        bookieAgentDetailPopup.closeIcon.click();
        bookieAgentSummaryPopup.closeIcon.click();
        log("@Step 9: Click on 'MS' link and observe");
        BookieMemberSummaryPopup memberSummaryPopup = page.openBookieMemberSummaryDetailPopup(bookieSuperMasterCode,bookieMasterCode);
        MemberDetailPage memberDetailPage = memberSummaryPopup.openMemberDetailPage(accountCode);
        log("@Step 10: Click on account code and observe");
        log("@Verify 6: show header table text: ‘Balance [HKD]’,'Debit [HKD]', 'Credit [HKD]', 'Running [HKD]'.");
        ArrayList<String> lstHeader3 = memberDetailPage.tblMemberDetail.getHeaderNameOfRows();
        Assert.assertTrue(lstHeader3.contains("Debit [HKD]"),"FAILED! Debit [HKD] display incorrect.");
        Assert.assertTrue(lstHeader3.contains("Credit [HKD]"),"FAILED! Credit [HKD] display incorrect.");
        Assert.assertTrue(lstHeader3.contains("Running [HKD]"),"FAILED! Running [HKD] display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4360")
    public void Company_Set_up_TC4360() {
        log("@title: Validate that display financial year correctly by company's accounting period in 'Ledger Statement' page");
        log("@pre-condition: Login with valid account");
        log("@Step 1: Go to General Report >> Ledger Statement");
        LedgerStatementPage page = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 2: Select company unit 'Aquifer' and observe financial year");
        page.showLedger("Aquifer","","","","","","");
        log("@Verify 1: financial Year filter will list options as a single year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_1_YEAR),"FAILED! Financial 1 year display incorrect.");
        log("@Step 3: Select company unit 'Kastraki' and observe financial year");
        page.showLedger(COMPANY_UNIT,"","","","","","");
        log("@Verify 2: financial Year filter will list options as period of 2 year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST),"FAILED! Financial period of 2 year display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4361")
    public void Company_Set_up_TC4361() {
        log("@title: Validate that display financial year correctly by company's accounting period in 'Ledger Statement' page");
        log("@pre-condition: Login with valid account");
        log("@Step 1: Go to General Report >> Ledger Statement");
        ClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_BALANCE,ClientBalancePage.class);
        log("@Step 2: Select company unit 'Aquifer' and observe financial year");
        page.filter("","Aquifer","","","");
        log("@Verify 1: financial Year filter will list options as a single year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_1_YEAR),"FAILED! Financial 1 year display incorrect.");
        log("@Step 3: Select company unit 'Kastraki' and observe financial year");
        page.filter("",COMPANY_UNIT,"","","");
        log("@Verify 2: financial Year filter will list options as period of 2 year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST),"FAILED! Financial period of 2 year display incorrect.");
        log("@Step 4: Select company unit 'Kastraki' and observe financial year");
        page.filter("","All","","","");
        log("@Verify 3: financial Year filter will list all options of both kind of financial year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_NEW),"FAILED! all options of both kind of financial year display incorrect.");
        log("INFO: Executed completely");
    }

}
