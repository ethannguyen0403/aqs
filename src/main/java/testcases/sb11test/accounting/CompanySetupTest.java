package testcases.sb11test.accounting;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import objects.Transaction;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.accounting.CompanySetupPage;
import pages.sb11.accounting.CurrencyRatesPage;
import pages.sb11.accounting.popup.CompanySetupCreatePopup;
import pages.sb11.financialReports.*;
import pages.sb11.generalReports.BookieBalancePage;
import pages.sb11.generalReports.ClientBalancePage;
import pages.sb11.generalReports.ClientStatementPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.bookiebalance.BalanceDetailPage;
import pages.sb11.generalReports.popup.bookieBalance.BookieBalanceDetailPopup;
import pages.sb11.generalReports.*;
import pages.sb11.generalReports.popup.bookiestatement.*;
import pages.sb11.generalReports.popup.clientBalance.ClientBalanceDetailPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientMemberTransactionPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientSummaryPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientLedgerRecPayPopup;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.util.ArrayList;

import java.io.IOException;
import java.util.*;

import static common.SBPConstants.*;

public class CompanySetupTest extends BaseCaseAQS {
    String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
    String previousDate = DateUtils.getPreviousDate(currentDate, "yyyy-MM-dd");
    String viewBy = "Client Point";
    String superMasterCode = "QA2112 - ";
    String agentLedCode = "QATE00-LED";

    @TestRails(id = "4332")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password"})
    public void Company_Set_up_TC4332(String password, String usernameOneRole) throws Exception{
        log("@title: Validate accounts without permission cannot see the menu");
        log("@Precondition: Deactivate Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", false);
        roleManagementPage.switchPermissions(JOURNAL_ENTRIES, true);
        log("@Step 1: Re-login with one role account account has 'Company Set-up' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(usernameOneRole, StringUtils.decrypt(password));
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
    public void Company_Set_up_TC4333(String password, String usernameOneRole) throws Exception{
        String companyURL = environment.getSbpLoginURL() + "#/aqs-report/company-set-up";
        log("@title: Validate accounts without permission cannot access page by external link");
        log("@Precondition: Deactivate Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", false);
        log("@Step 1: Re-login with one role account account has 'Company Set-up' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(usernameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: Company Set-up menu can not access by external link: " + companyURL);
        DriverManager.getDriver().get(companyURL);
        Assert.assertFalse(new CompanySetupPage().lblTitle.isDisplayed(), "FAILED! Company Set-up page can access by external link");
        log("INFO: Executed completely");
    }


    @TestRails(id = "4334")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password", "userNameOneRole"})
    public void Company_Set_up_TC4334(String password, String userNameOneRole) throws Exception{
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

    @TestRails(id = "4341")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC4341(String companyName, String companyCurrency) {
        log("@title: Validate UI on 'Create Company' dialog is shown correctly");
        log("@Step 1: Navigate to Accounting > Currency");
        CurrencyRatesPage currencyRatesPage = welcomePage.navigatePage(ACCOUNTING, CURRENCY_RATES, CurrencyRatesPage.class);
        log("@Step 2: Filter with company: " + companyName);
        currencyRatesPage.filterRate(companyName,"");

        List<String> currencyList = currencyRatesPage.tblCurRate.getColumn(currencyRatesPage.colCur, false);
        log(String.format("@Verify 1: Validate currency: %s of Company: %s has value: 1", companyCurrency, companyName));
        log("@Verify 2: Validate show the converting rates grabbed from OANDA as daily is correct");
        currencyRatesPage.verifyCurCorrectFromOANDA(currencyList, companyCurrency, previousDate, currentDate);
        log("INFO: Executed completely");
    }

    @TestRails(id = "4343")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC4343(String companyName, String companyCurrency) {
        String toDayAvoidLastDayOfMonth = "";
        log("@title: Validate that show the reporting currency of Company correctly in 'Ledger Statement' page");
        String expectedText1 = "Total in " + companyCurrency;
        String expectedText2 = "Grand Total in " + companyCurrency;
        String expectedText3 = "Amounts are shown in " + companyCurrency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);

        toDayAvoidLastDayOfMonth = ledgerStatementPage.isLastDayOfMonth() ? previousDate : "";
        toDayAvoidLastDayOfMonth = DateUtils.formatDate(toDayAvoidLastDayOfMonth, "yyyy-MM-dd", "dd/MM/yyyy");
        log("@Step 2: Filter with Company: "+ companyName);
        ledgerStatementPage.showLedger(companyName, "","","","",toDayAvoidLastDayOfMonth, "");

        log(String.format("@Verify 1: Validate  the table has \"CUR transaction in %s\" is NOT displayed.", companyCurrency));
        Assert.assertFalse(ledgerStatementPage.lblAmountShowCurrency.isDisplayed(),  "FAILED! The table CUR transaction is shown");
        log("@Verify 2: Validate shows text correct with currency: " + companyCurrency);
        Assert.assertEquals(ledgerStatementPage.getDescriptionTotalAmountInOriginCurrency("Total in"), expectedText1, "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.lblGrandTotalInOrigin.getText().trim(),expectedText2,  "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");

        log("@Step 3: Click on first ledger name to open Ledger Detail");
        log("@Verify 3: Validate shows text correct with currency: " + companyCurrency);
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerFirstDetail();
        Assert.assertEquals(ledgerDetailPopup.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4345")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC4345(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Client Balance' page");
        String expectedText1 = "Total Balance " + companyCurrency;
        String expectedText2 = "Total Balance in " + companyCurrency;
        String expectedText3 = "Grand Total in " + companyCurrency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  General Reports >> Client Balance");
        ClientBalancePage clientBalancePage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_BALANCE, ClientBalancePage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        clientBalancePage.filter("", companyName, "", "", "");

        log(String.format("@Verify 1: Validate shows text correct with currency %s after submit filter", companyCurrency));
        Assert.assertEquals(clientBalancePage.ddShowTotal.getFirstSelectedOption(), companyCurrency, "FAILED! Text of Show Total dropdown is not correct");
        Assert.assertEquals(clientBalancePage.tblClientBalance.getHeaderNameOfRows().get(4), expectedText1, "FAILED! Text Total Balance is not correct");

        log("@Step 3: Click on Client Balance name >> Client Balance detail popup");
        ClientBalanceDetailPopup detailPopup = clientBalancePage.goToClientDetail(1);

        log(String.format("@Verify 2: Validate shows text correct with currency %s on Client detail popup", companyCurrency));
        Assert.assertEquals(detailPopup.tblClientSuper.getHeaderNameOfRows().get(4), expectedText2, "FAILED! Text in Client Balance detail popup is not correct");
        Assert.assertEquals(detailPopup.lblGrandTotalMaster.getText().trim(), expectedText3, "FAILED! Text Client Balance detail popup is not correct");
        Assert.assertEquals(detailPopup.lblGrandTotalFooter.getText().trim(), expectedText3, "FAILED! Text Client Balance detail popup is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4347")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC4347(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Bookie Balance' page");
        String expectedText1 = "Total In " + companyCurrency;
        String expectedText2 = "Grand Total in " + companyCurrency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  General Reports >> Bookie Balance");
        BookieBalancePage clientBalancePage = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_BALANCE, BookieBalancePage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        clientBalancePage.waitSpinnerDisappeared();
        clientBalancePage.filter(companyName, "", "", "", "");

        log(String.format("@Verify 1: Validate shows text correct with currency %s after submit filter", companyCurrency));
        Assert.assertEquals(clientBalancePage.ddShowTotal.getFirstSelectedOption(), companyCurrency, "");
        Assert.assertEquals(clientBalancePage.tblBookieBalance.getHeaderNameOfRows().get(2).trim(), expectedText1);

        log("@Step 3: Click on Bookie name >> Bookie Balance detail popup");
        BookieBalanceDetailPopup bookiePopup = clientBalancePage.goToBookieDetail(1);
        log(String.format("@Verify 2: Validate shows text correct with currency %s on Client detail popup", companyCurrency));
        Assert.assertEquals(bookiePopup.tblTotal.getHeaderNameOfRows().get(7), expectedText2, "FAILED! Text in Bookie Balance detail popup is not correct");
        Assert.assertEquals(bookiePopup.lblGrandTotalFooter.getText().trim(), expectedText2, "FAILED! Text Bookie Balance detail popup is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4349")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"clientCode"})
    public void Company_Set_up_TC4349(String clientCode) throws IOException {
        String ledgerCreditAccountId, ledgerCreditAccountName, ledgerCreditAccountNumber, ledgerDebitAccountId, ledgerDebitAccountName,
                ledgerDebitAccountNumber, ledgerType, ledgerGroupId, parentId;
        String companyName = "Kastraki Limited";
        String currency = "HKD";
        String expectedText1 = "Total in " + currency;
        String expectedText2 = "Grand Total in " + currency;

        log("@title: Validate that show the reporting currency of company correctly in 'Client Statement' page");
        log(String.format("@Precondition 1: Company %s has currency as %s", companyName, currency) );
        log("@Precondition 2: Add transaction for the Asset Ledger account to show value Rec/Pay and Member Transaction popup");
        String[] ledgerDebitAccountPart = LEDGER_ASSET_DEBIT_ACC.split("-");
        String[] ledgerCreditAccountPart = LEDGER_ASSET_CREDIT_ACC.split("-");
        ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
        ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
        ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
        ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                .amountDebit(1).amountCredit(1)
                .remark("Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds()).transDate(currentDate)
                .transType("Tax Rebate").build();
        welcomePage.waitSpinnerDisappeared();
        ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_ASSET);
        parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_PARENT_NAME_ASSET);
        ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
        ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
        ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
        try{
            TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
            log("@Step 1: Navigate to  General Reports >> Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
            log("@Step 2: Filter with default data with company name: " + companyName);
            clientPage.filter(viewBy,COMPANY_UNIT,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            clientPage.waitSpinnerDisappeared();
            log("@Verify 1: Show Grand total in: " + currency);
            Assert.assertEquals(clientPage.lblGrandTotal.getText().trim()+" "+clientPage.lblGrandTotalCur.getText().trim(), expectedText2, "FAILED! Text is incorrect");
            log("@Step 3: Open Summary popup of agent ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Verify 2: Show Total in: " + currency);
            Assert.assertEquals(popup.getGrandTotal(currency, 1)+ " " + popup.getGrandTotal(currency, 2), expectedText1, "FAILED! Text is incorrect");
            log("@Step 4: Open RecPay summary popup of player by click on link");
            ClientLedgerRecPayPopup recPayPopup = popup.openLedgerRecPaySummaryPopup(ledgerDebitAccountName,popup.colLedgerRecPay);

            log("@Verify 3: Validate Rec/Pay/CA/RB/Adj Txns dialog shows header with currency: " + currency);
            recPayPopup.verifyHeaderCorrectWithCompanyCur(ClientLedgerRecPayPopupConstants.HEADER_LIST, currency);
            recPayPopup.switchToFirstWindow();
            log("@Step 5: Open Transaction popup");
            ClientMemberTransactionPopup transPopup = popup.openMemberTransactionPopupLedger(ledgerDebitAccountName);
            log("@Verify 3:  Member Transactions popup shows header with currency: " + currency);
            transPopup.verifyHeaderCorrectWithCompanyCur(MemberTransactionPopupConstants.HEADER_LIST, currency);
        }finally {
            log("@Post-condition: Add transaction for the Asset Ledger account into Debit");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerDebitAccountName).ledgerCreditNumber(ledgerDebitAccountNumber)
                    .ledgerDebit(ledgerCreditAccountName).ledgerDebitNumber(ledgerCreditAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Client: Post-condition" + DateUtils.getMilliSeconds()).transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addLedgerTxn(transactionPost,ledgerCreditAccountId,ledgerDebitAccountId,ledgerType);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4350")
    @Parameters({"bookieSuperMasterCode","bookieMasterCode","accountCode", "companyName", "companyCurrency"})
    public void Company_Set_up_TC4350(String bookieSuperMasterCode, String bookieMasterCode, String accountCode, String companyName, String companyCurrency) throws InterruptedException {
        log("@title: Validate that show the reporting currency of company correctly in 'Bookie Statement' page");
        log(String.format("@Pre-condition: Company '%s' has currency as %s", companyName, companyCurrency));
        log("@Step 1: Go to General Report >> Bookie Statement");;
        String agentCode = "A-QA10101-QA Test";
        String fromDate = DateUtils.getDate(-10,"dd/MM/yyyy",GMT_7);
        BookieStatementPage page = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT,BookieStatementPage.class);
        log("@Step 2: Select valid values with company unit: " + companyName);
        log("@Step 3: Select agent type 'Super Master'");
        log("@Step 4: Click on 'Show' button and observe");
        page.filter(companyName,"","Super Master",fromDate,"","QA Bookie","");
        log(String.format("@Verify 1: Show text to ‘Grand Total in [%s]’ in the bottom report", companyCurrency));
        Assert.assertEquals(page.tblGrandTotal.getHeaderNameOfRows().get(1),"Grand Total in","FAILED! Grand total in display incorrrect.");
        Assert.assertEquals(page.tblGrandTotal.getHeaderNameOfRows().get(2),companyCurrency,"FAILED! Cur of Grand total in display incorrrect.");
        log("@Step 5: Click on master code and observe");
        BookieMasterAgentDetailPopup bookieMasterAgentDetailPopup = page.openBookieMasterAgentDetailPopup(bookieSuperMasterCode,bookieMasterCode);
        log("@Verify 2: show header table text: 'Debit [%s]', 'Credit [%s]', 'Running [%s]'.".replaceAll("%s", companyCurrency));
        ArrayList<String> lstHeader1 = bookieMasterAgentDetailPopup.tblMDetail.getHeaderNameOfRows();
        Assert.assertTrue(lstHeader1.contains(String.format("Debit [%s]", companyCurrency)),"FAILED! Debit display incorrect.");
        Assert.assertTrue(lstHeader1.contains(String.format("Credit [%s]", companyCurrency)),"FAILED! Credit display incorrect.");
        Assert.assertTrue(lstHeader1.contains(String.format("Running [%s]", companyCurrency)),"FAILED! Running display incorrect.");
        log("@Step 6: Close the dialog");
        bookieMasterAgentDetailPopup.closeIcon.click();
        log("@Step 7: Click on 'AS'");
        BookieAgentSummaryPopup bookieAgentSummaryPopup = page.openBookieAgentSummary(bookieSuperMasterCode,bookieMasterCode);
        BookieAgentDetailPopup bookieAgentDetailPopup = bookieAgentSummaryPopup.openAgentDetailPopup(agentCode);
        log("@Verify 3: show header table text: 'Debit [%s]', 'Credit [%s]', 'Running [%s]'.".replaceAll("%s", companyCurrency));
        ArrayList<String> lstHeader2 = bookieAgentDetailPopup.tblAgentDetail.getHeaderNameOfRows();
        Assert.assertTrue(lstHeader2.contains(String.format("Debit [%s]", companyCurrency)),"FAILED! Debit display incorrect.");
        Assert.assertTrue(lstHeader2.contains(String.format("Credit [%s]", companyCurrency)),"FAILED! Credit display incorrect.");
        Assert.assertTrue(lstHeader2.contains(String.format("Running [%s]", companyCurrency)),"FAILED! Running display incorrect.");
        log("@Step 8: Close dialog");
        bookieAgentDetailPopup.closeIcon.click();
        bookieAgentSummaryPopup.closeIcon.click();
        log("@Step 9: Click on 'MS' link and observe");
        BookieMemberSummaryPopup memberSummaryPopup = page.openBookieMemberSummaryDetailPopup(bookieSuperMasterCode,bookieMasterCode);
        MemberDetailPage memberDetailPage = memberSummaryPopup.openMemberDetailPage(accountCode);
        log("@Step 10: Click on account code and observe");
        log("@Verify 6: show header table text: 'Debit [%s]', 'Credit [%s]', 'Running [%s]'.".replaceAll("%s", companyCurrency));
        ArrayList<String> lstHeader3 = memberDetailPage.tblMemberDetail.getHeaderNameOfRows();
        Assert.assertTrue(lstHeader3.contains(String.format("Debit [%s]", companyCurrency)),"FAILED! Debit display incorrect.");
        Assert.assertTrue(lstHeader3.contains(String.format("Credit [%s]", companyCurrency)),"FAILED! Credit display incorrect.");
        Assert.assertTrue(lstHeader3.contains(String.format("Running [%s]", companyCurrency)),"FAILED! Running display incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4352")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC4352(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Trial Balance' page");
        String expectedText = String.format("Amounts are shown in [%s]", companyCurrency);
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  General Reports >> Trial Balance");
        TrialBalancePage trialBalancePage = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        trialBalancePage.filter(companyName, "","","");
        log(String.format("@Verify 1: Validate shows text correct with currency %s after submit filter", companyCurrency));
        Assert.assertEquals(trialBalancePage.tblTrial.getHeaderNameOfRows().get(0), expectedText, "FAILED! Text is incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8570")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC8570(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Balance Sheet' page");
        String expectedText = String.format("Amounts are shown in [%s]", companyCurrency);
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  General Reports >> Balance Sheet");
        BalanceSheetPage balanceSheetPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET, BalanceSheetPage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        balanceSheetPage.filter(companyName, "","","");
        log(String.format("@Verify 1: Validate shows text 'Amounts are shown in [%s]' correct", companyCurrency));
        Assert.assertEquals(balanceSheetPage.lblAmountAreShow.getText().trim(), expectedText, "FAILED! Text is incorrect");
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
        page.ddCompanyUnit.selectByVisibleText("Aquifer");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 1: financial Year filter will list options as a single year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_1_YEAR),"FAILED! Financial 1 year display incorrect.");
        log("@Step 3: Select company unit 'Kastraki' and observe financial year");
        page.ddCompanyUnit.selectByVisibleText(COMPANY_UNIT);
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 2: financial Year filter will list options as period of 2 year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST),"FAILED! Financial period of 2 year display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4361")
    public void Company_Set_up_TC4361() {
        log("@title: Validate that display financial year correctly by company's accounting period in 'Client Balance' page");
        log("@pre-condition: Login with valid account");
        log("@Step 1: Go to General Report >> Client Balance");
        ClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_BALANCE,ClientBalancePage.class);
        log("@Step 2: Select company unit 'Aquifer' and observe financial year");
        page.ddCompanyUnit.selectByVisibleText("Aquifer");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 1: financial Year filter will list options as a single year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_1_YEAR),"FAILED! Financial 1 year display incorrect.");
        log("@Step 3: Select company unit 'Kastraki' and observe financial year");
        page.ddCompanyUnit.selectByVisibleText(COMPANY_UNIT);
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 2: financial Year filter will list options as period of 2 year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST),"FAILED! Financial period of 2 year display incorrect.");
        log("@Step 4: Select company unit 'All' and observe financial year");
        page.ddCompanyUnit.selectByVisibleText("All");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 3: financial Year filter will list all options of both kind of financial year");
        Assert.assertTrue(page.ddFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_NEW),"FAILED! all options of both kind of financial year display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4362")
    public void Company_Set_up_TC4362() {
        log("@title: Validate that display financial year correctly by company's accounting period in 'Bookie Balance' page");
        log("@pre-condition: Login with valid account");
        log("@Step 1: Go to General Report >> Bookie Balance");
        BookieBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,BOOKIE_BALANCE,BookieBalancePage.class);
        log("@Step 2: Select company unit 'Aquifer' and observe financial year");
        page.ddCompanyUnit.selectByVisibleText("Aquifer");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 1: financial Year filter will list options as a single year");
        Assert.assertTrue(page.ddFinancial.getOptions().equals(FINANCIAL_YEAR_LIST_1_YEAR),"FAILED! Financial 1 year display incorrect.");
        log("@Step 3: Select company unit 'Kastraki' and observe financial year");
        page.ddCompanyUnit.selectByVisibleText(COMPANY_UNIT);
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 2: financial Year filter will list options as period of 2 year");
        Assert.assertTrue(page.ddFinancial.getOptions().equals(FINANCIAL_YEAR_LIST),"FAILED! Financial period of 2 year display incorrect.");
        log("@Step 4: Select company unit 'Kastraki' and observe financial year");
        page.ddCompanyUnit.selectByVisibleText("All");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 3: financial Year filter will list all options of both kind of financial year");
        Assert.assertTrue(page.ddFinancial.getOptions().equals(FINANCIAL_YEAR_LIST_NEW),"FAILED! all options of both kind of financial year display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4363")
    public void Company_Set_up_TC4363() {
        log("@title: Validate that display financial year correctly by company's accounting period in 'Client Statement' page");
        log("@pre-condition: Login with valid account");
        log("@Step 1: Go to General Report >> Client Statement");
        ClientStatementPage page = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        log("@Step 2: Select company unit 'Aquifer' and observe financial year");
        page.ddpCompanyUnit.selectByVisibleText("Aquifer");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 1: financial Year filter will list options as a single year");
        Assert.assertTrue(page.ddpFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_1_YEAR),"FAILED! Financial 1 year display incorrect.");
        log("@Step 3: Select company unit 'Kastraki' and observe financial year");
        page.ddpCompanyUnit.selectByVisibleText(COMPANY_UNIT);
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 2: financial Year filter will list options as period of 2 year");
        Assert.assertTrue(page.ddpFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST),"FAILED! Financial period of 2 year display incorrect.");
        log("@Step 4: Select company unit 'Kastraki' and observe financial year");
        page.ddpCompanyUnit.selectByVisibleText("All");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 3: financial Year filter will list all options of both kind of financial year");
        Assert.assertTrue(page.ddpFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_NEW),"FAILED! all options of both kind of financial year display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4364")
    public void Company_Set_up_TC4364() {
        log("@title: Validate that display financial year correctly by company's accounting period in 'Bookie Statement' page");
        log("@pre-condition: Login with valid account");
        log("@Step 1: Go to General Report >> Bookie Statement");
        BookieStatementPage page = welcomePage.navigatePage(GENERAL_REPORTS,BOOKIE_STATEMENT,BookieStatementPage.class);
        log("@Step 2: Select company unit 'Aquifer' and observe financial year");
        page.ddpCompanyUnit.selectByVisibleText("Aquifer");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 1: financial Year filter will list options as a single year");
        Assert.assertTrue(page.ddpFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_1_YEAR),"FAILED! Financial 1 year display incorrect.");
        log("@Step 3: Select company unit 'Kastraki' and observe financial year");
        page.ddpCompanyUnit.selectByVisibleText(COMPANY_UNIT);
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 2: financial Year filter will list options as period of 2 year");
        Assert.assertTrue(page.ddpFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST),"FAILED! Financial period of 2 year display incorrect.");
        log("@Step 4: Select company unit 'Kastraki' and observe financial year");
        page.ddpCompanyUnit.selectByVisibleText("All");
        welcomePage.waitSpinnerDisappeared();
        log("@Verify 3: financial Year filter will list all options of both kind of financial year");
        Assert.assertTrue(page.ddpFinancialYear.getOptions().equals(FINANCIAL_YEAR_LIST_NEW),"FAILED! all options of both kind of financial year display incorrect.");
        log("INFO: Executed completely");
    }
    @TestRails(id = "8571")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC8571(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Income Statement' page");
        String expectedText = String.format("Amounts are shown in [%s]", companyCurrency);
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  General Reports >> Income Statement");
        IncomeStatementPage incomeStatementPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT, IncomeStatementPage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        incomeStatementPage.filterIncomeReport(companyName, "","","");
        log(String.format("@Verify 1: Validate shows text 'Amounts are shown in [%s]' correct", companyCurrency));
        Assert.assertEquals(incomeStatementPage.lblAmountAreShow.getText().trim(), expectedText, "FAILED! Text is incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8572")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC8572(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Retained Earning' page");
        String expectedText = String.format("Amounts are shown in [%s]", companyCurrency);
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  Financial Reports >> Retained Earning");
        RetainedEarningsPage retainedEarningsPage = welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        retainedEarningsPage.filterRetainedEarnings(companyName, "");
        log(String.format("@Verify 1: Validate shows text 'Amounts are shown in [%s]' correct", companyCurrency));
        Assert.assertEquals(retainedEarningsPage.lblAmountAreShow.getText().trim(), expectedText, "FAILED! Text is incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8573")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC8573(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Stockholders Equity' page");
        String expectedText = String.format("Amounts are shown in [%s]", companyCurrency);
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  Financial Reports >> Stockholders Equity");
        StockHoldersEquityPage stockHoldersPage = welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        stockHoldersPage.filter(companyName, "");
        log(String.format("@Verify 1: Validate shows text 'Amounts are shown in [%s]' correct", companyCurrency));
        Assert.assertEquals(stockHoldersPage.lblAmountAreShow.getText().trim(), expectedText, "FAILED! Text is incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8574")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC8574(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Balance Sheet - Analysis' page");
        String expectedText = String.format("Amounts are shown in [%s]", companyCurrency);
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  Financial Reports >> Balance Sheet - Analysis");
        BalanceSheetAnalysisPage blAnalysis = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        blAnalysis.filter(companyName, "", "", "");
        log(String.format("@Verify 1: Validate shows text 'Amounts are shown in [%s]' correct", companyCurrency));
        Assert.assertEquals(blAnalysis.lblAmountAreShow.getText().trim(), expectedText, "FAILED! Text is incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8575")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC8575(String companyName, String companyCurrency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Income Statement - Analysis' page");
        String expectedText = String.format("Amounts are shown in [%s]", companyCurrency);
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency));
        log("@Step 1: Navigate to  Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnalysis = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        incomeAnalysis.filter(companyName, "", "", "");
        log(String.format("@Verify 1: Validate shows text 'Amounts are shown in [%s]' correct", companyCurrency));
        Assert.assertEquals(incomeAnalysis.lblAmountAreShow.getText().trim(), expectedText, "FAILED! Text is incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "9148")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "companyCurrency"})
    public void Company_Set_up_TC9148(String companyName, String companyCurrency) {
        String lastDateOfPreviousMonth = "";
        log("@title: Validate that show the reporting currency of Company correctly in 'Ledger Statement' page if the last day of month has rate");
        String expectedText1 = "Total in " + companyCurrency;
        String expectedText2 = "Grand Total in " + companyCurrency;
        String expectedText3 = "Amounts are shown in " + companyCurrency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, companyCurrency) );
        log("@Step 1: Navigate to  General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);

        lastDateOfPreviousMonth = ledgerStatementPage.getLastDateOfPreviousMonth("dd/MM/yyyy", "GMT +7");
        log(String.format("@Step 2: Filter with Company: %s on the last date of previous month", companyName));
        ledgerStatementPage.showLedger(companyName, "", "", "", lastDateOfPreviousMonth, lastDateOfPreviousMonth, "");

        log(String.format("@Verify 1: Validate  the table has \"CUR transaction in %s\" is displayed.", companyCurrency));
        Assert.assertTrue(ledgerStatementPage.lblAmountShowCurrency.isDisplayed(),  "FAILED! The table CUR transaction is not shown");
        log("@Verify 2: Validate shows text correct with currency: " + companyCurrency);
        Assert.assertEquals(ledgerStatementPage.getDescriptionTotalAmountInOriginCurrency("Total in"), expectedText1, "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.lblGrandTotalInOrigin.getText().trim(),expectedText2,  "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");

        log("@Step 3: Click on first ledger name to open Ledger Detail");
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerFirstDetail();
        log("@Step 4: Select 'With Translation modes' of ShowTxn dropdown");
        ledgerDetailPopup.selectShowTxnDropDown("With");
        log(String.format("@Verify 3: Validate shows text correct with currency: %s ", companyCurrency));
        Assert.assertEquals(ledgerDetailPopup.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");

        log("@Step 4: Select 'Without Translation modes' of ShowTxn dropdown");
        ledgerDetailPopup.selectShowTxnDropDown("Without");
        log(String.format("@Verify 4: Validate shows text correct with currency: %s ", companyCurrency));
        Assert.assertEquals(ledgerDetailPopup.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");
        log("INFO: Executed completely");
    }

}
