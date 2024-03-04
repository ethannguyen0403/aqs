package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import com.paltech.utils.StringUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.BalanceSheetPage;
import pages.sb11.financialReports.TrialBalancePage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class TrialBalanceTest extends BaseCaseAQS {
    String parentAccount = "500.000.000.000 - QA Ledger Group Asset";
    int currentMonth = DateUtils.getMonth("GMT +7");
    String transType = "Payment Other";
    String desc = "Automation test: Transaction " + DateUtils.getMilliSeconds();

    @TestRails(id = "2764")
    @Test(groups = {"Revise"})
    public void Trial_Balance_C2764() {
        log("@title: Validate Report data of 'Before CJE' and 'After CJE' will be different only when selecting 'Month' = July.");
        log("@Step 1: Navigate to SB11 > Financial Reports > Trial Balance");
        TrialBalancePage trialBalancePage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Verify 1: Report is disabled with 'Before CJE' option as default when Month is not 'July'");
        trialBalancePage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, "2023 - November", "");
        Assert.assertTrue(!trialBalancePage.ddReport.isEnabled(), "FAILED! The report dropdown is enable.");
        log("@Verify 2: Report is enabled with 2 options 'Before CJE' and 'After CJE' when Month = 'July'");
        trialBalancePage.ddMonth.selectByVisibleText("2023 - July");
        welcomePage.waitSpinnerDisappeared();
        Assert.assertTrue(trialBalancePage.ddReport.isEnabled() && trialBalancePage.ddReport.getOptions().equals(REPORT_TYPE),
                "FAILED! The report dropdown is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2773")
    @Test(groups = {"regression", "2023.11.30"})
    public void Trial_Balance_C2773() throws IOException {
        log("@title: Validate Debit/Credit data is matched correctly with Ledger Statement page");
        String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
        String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_DEBIT_ACC,true);
        String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_DEBIT_ACC,true);
        String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_CREDIT_ACC,true);
        String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_CREDIT_ACC,true);

        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                .amountDebit(1).amountCredit(1)
                .remark(desc)
                .transDate(currentDate)
                .transType(transType).build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_ASSET);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_ASSET);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
        try {
            log("@Precondition: Having a ledger transaction with parent Account: " + parentAccount);
            TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
            log("@Step 1: Navigate to General Reports > Ledger Statement and get Total Credit/Debit amount");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            String fromDate = DateUtils.getFirstDateOfMonth(DateUtils.getYear(GMT_7),currentMonth,"dd/MM/yyyy");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", parentAccount, fromDate, "", "");
            String totalDeCre = ledgerStatementPage.getTotalCreDeInOriginCurrency("Total in HKD");
            log("@Step 2: Navigate to SB11 > Financial Reports > Trial Balance");
            TrialBalancePage trialBalancePage =
                    welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with current Month");
            int curYear = DateUtils.getYear(GMT_7);
            trialBalancePage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, trialBalancePage.convertMonthToFilterMonth(currentMonth, curYear), "");
            String accountCode = ChartOfAccountUtils.getAccountNumber(parentAccount,true);
            String amountDebitCurrenMonth = trialBalancePage.getAmountValue(accountCode,trialBalancePage.colDeCurrentMonth);
            log("@Verify 1: Validate Debit/Credit data should match correctly with the Ledger Statement page");
            Assert.assertEquals(amountDebitCurrenMonth,totalDeCre, "FAILED!Debit/Credit data NOT match correctly with the Ledger Statement page");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerDebitAccountName).ledgerCreditNumber(ledgerDebitAccountNumber)
                    .ledgerDebit(ledgerCreditAccountNumber).ledgerDebitNumber(ledgerCreditAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addLedgerTxn(transactionPost,ledgerCreditAccountId,ledgerDebitAccountId,ledgerType);
            log("INFO: Executed completely");
        }
    }

    @TestRails(id = "2779")
    @Test(groups = {"regression_stg", "2023.11.30"})
    @Parameters({"password", "userNameOneRole"})
    public void Trial_Balance_C2779(String password, String userNameOneRole) throws Exception {
        log("@title: Validate user can not access Trial Balance page when having no permission");
        log("Precondition: Deactivate Trial Balance option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(TRIAL_BALANCE, false);
        roleManagementPage.selectRole("one role").switchPermissions(BALANCE_SHEET, true);
        log("@Step 1: Re-login with one role account account has 'Trial Balance' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        BalanceSheetPage balanceSheetPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET, BalanceSheetPage.class);
        log("@Verify 1: 'Trial Balance' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, TRIAL_BALANCE), "FAILED! Trial balance menu is displayed");
        log("INFO: Executed completely");
    }
    @TestRails(id = "21832")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21832() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Asset");
        log("Pre-condition 1: Having some txn with detail type = Asset");
        String parentDebit = "500.000.000.000 - QA Ledger Group Asset";
        String ledgerAccNega = "050.000.000.001 - AutoAssetCreditNega";
        String ledgerGroupNega = "QA Ledger Auto Asset";
        String ledgerNega = "500.000.000.001 - QA Ledger Auto Asset";
        String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
        String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_DEBIT_ACC,true);
        String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_DEBIT_ACC,true);
        String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(ledgerAccNega,true);
        String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerAccNega,true);

        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(currentDate)
                .transType(transType).build();

        String ledgerDebitGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_ASSET);
        String parentDebitId = ChartOfAccountUtils.getParentId(ledgerDebitGroupId, LEDGER_GROUP_NAME_ASSET);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentDebitId,ledgerDebitAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentDebitId,ledgerDebitAccountName);

        String ledgerCreditGroupId = ChartOfAccountUtils.getLedgerGroupId(ledgerGroupNega);
        String parentCreditId = ChartOfAccountUtils.getParentId(ledgerCreditGroupId, ledgerGroupNega);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentCreditId,ledgerCreditAccountName);
        try {
            TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            String fromDate = DateUtils.getFirstDateOfMonth(DateUtils.getYear(GMT_7),currentMonth,"dd/MM/yyyy");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", parentDebit, fromDate, "", "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal();

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", ledgerNega, fromDate, "", "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal();
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            page.filter(KASTRAKI_LIMITED,"","","");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colDeCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerDebitAccountName).ledgerCreditNumber(ledgerDebitAccountNumber)
                    .ledgerDebit(ledgerCreditAccountNumber).ledgerDebitNumber(ledgerCreditAccountNumber)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addLedgerTxn(transactionPost,ledgerCreditAccountId,ledgerDebitAccountId,ledgerType);
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21833")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21833() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Expenditure");
        log("Pre-condition 1: Having some txn with detail type = Expenditure");
        String parentDebit = "100.000.000.000 - QA Ledger Group Expenditure";
        String ledgerAccNega = "050.000.000.011 - AutoExpenditureCreditNega";
        String ledgerGroupNega = "QA Ledger Auto Expenditure";
        String ledgerNega = "100.000.000.001 - QA Ledger Auto Expenditure";

        String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
        String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_DEBIT_ACC,true);
        String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_DEBIT_ACC,true);
        String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(ledgerAccNega,true);
        String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerAccNega,true);

        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(currentDate)
                .transType(transType).build();

        String ledgerDebitGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_EXPENDITURE);
        String parentDebitId = ChartOfAccountUtils.getParentId(ledgerDebitGroupId, LEDGER_GROUP_NAME_EXPENDITURE);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentDebitId,ledgerDebitAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentDebitId,ledgerDebitAccountName);

        String ledgerCreditGroupId = ChartOfAccountUtils.getLedgerGroupId(ledgerGroupNega);
        String parentCreditId = ChartOfAccountUtils.getParentId(ledgerCreditGroupId, ledgerGroupNega);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentCreditId,ledgerCreditAccountName);
        try {
            TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            String fromDate = DateUtils.getFirstDateOfMonth(DateUtils.getYear(GMT_7),currentMonth,"dd/MM/yyyy");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", parentDebit, fromDate, "", "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal();

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", ledgerNega, fromDate, "", "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal();
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            page.filter(KASTRAKI_LIMITED,"","","");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colDeCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerDebitAccountName).ledgerCreditNumber(ledgerDebitAccountNumber)
                    .ledgerDebit(ledgerCreditAccountNumber).ledgerDebitNumber(ledgerCreditAccountNumber)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addLedgerTxn(transactionPost,ledgerCreditAccountId,ledgerDebitAccountId,ledgerType);
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21834")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21834() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Liability");
        log("Pre-condition 1: Having some txn with detail type = Liability");
        String parentDebit = "400.000.000.000 - QA Ledger Group Liability";
        String ledgerAccNega = "040.000.000.011 - AutoLiabilityCreditNega";
        String ledgerGroupNega = "QA Ledger Auto Liability";
        String ledgerNega = "400.000.000.001 - QA Ledger Auto Liability";

        String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
        String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_LIABILITY_DEBIT_ACC,true);
        String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_LIABILITY_DEBIT_ACC,true);
        String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(ledgerAccNega,true);
        String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerAccNega,true);

        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(currentDate)
                .transType(transType).build();

        String ledgerDebitGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_LIABILITY);
        String parentDebitId = ChartOfAccountUtils.getParentId(ledgerDebitGroupId, LEDGER_GROUP_NAME_LIABILITY);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentDebitId,ledgerDebitAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentDebitId,ledgerDebitAccountName);

        String ledgerCreditGroupId = ChartOfAccountUtils.getLedgerGroupId(ledgerGroupNega);
        String parentCreditId = ChartOfAccountUtils.getParentId(ledgerCreditGroupId, ledgerGroupNega);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentCreditId,ledgerCreditAccountName);
        try {
            TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            String fromDate = DateUtils.getFirstDateOfMonth(DateUtils.getYear(GMT_7),currentMonth,"dd/MM/yyyy");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", parentDebit, fromDate, "", "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal();

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", ledgerNega, fromDate, "", "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal();
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            page.filter(KASTRAKI_LIMITED,"","","");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colDeCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerDebitAccountName).ledgerCreditNumber(ledgerDebitAccountNumber)
                    .ledgerDebit(ledgerCreditAccountNumber).ledgerDebitNumber(ledgerCreditAccountNumber)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addLedgerTxn(transactionPost,ledgerCreditAccountId,ledgerDebitAccountId,ledgerType);
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21835")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21835() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Capital");
        log("Pre-condition 1: Having some txn with detail type = Capital");
        String parentDebit = "300.000.000.000 - QA Ledger Group Capital";
        String ledgerAccNega = "030.000.000.011 - AutoCapitalCreditNega";
        String ledgerGroupNega = "QA Ledger Auto Capital";
        String ledgerNega = "300.000.000.001 - QA Ledger Auto Capital";

        String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
        String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_CREDIT_ACC,true);
        String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_CREDIT_ACC,true);
        String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(ledgerAccNega,true);
        String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerAccNega,true);

        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerDebitAccountName).ledgerCreditNumber(ledgerDebitAccountNumber)
                .ledgerDebit(ledgerCreditAccountName).ledgerDebitNumber(ledgerCreditAccountNumber)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(currentDate)
                .transType(transType).build();

        String ledgerDebitGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_CAPITAL);
        String parentDebitId = ChartOfAccountUtils.getParentId(ledgerDebitGroupId, LEDGER_GROUP_NAME_CAPITAL);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentDebitId,ledgerDebitAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentDebitId,ledgerDebitAccountName);

        String ledgerCreditGroupId = ChartOfAccountUtils.getLedgerGroupId(ledgerGroupNega);
        String parentCreditId = ChartOfAccountUtils.getParentId(ledgerCreditGroupId, ledgerGroupNega);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentCreditId,ledgerCreditAccountName);
        try {
            TransactionUtils.addLedgerTxn(transaction,ledgerCreditAccountId,ledgerDebitAccountId,ledgerType);
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            String fromDate = DateUtils.getFirstDateOfMonth(DateUtils.getYear(GMT_7),currentMonth,"dd/MM/yyyy");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", parentDebit, fromDate, "", "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal();

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", ledgerNega, fromDate, "", "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal();
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            page.filter(KASTRAKI_LIMITED,"","","");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colCreCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colDeCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21836")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21836() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Income");
        log("Pre-condition 1: Having some txn with detail type = Income");
        String parentDebit = "000.000.001.000 - QA Ledger Group Income";
        String ledgerAccNega = "002.000.000.011 - AutoIncomeCreditNega";
        String ledgerGroupNega = "QA Ledger Auto Income";
        String ledgerNega = "000.000.001.001 - QA Ledger Auto Income";

        String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
        String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_DEBIT_ACC,true);
        String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_DEBIT_ACC,true);
        String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(ledgerAccNega,true);
        String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerAccNega,true);

        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(currentDate)
                .transType(transType).build();

        String ledgerDebitGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_INCOME);
        String parentDebitId = ChartOfAccountUtils.getParentId(ledgerDebitGroupId, LEDGER_GROUP_NAME_INCOME);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentDebitId,ledgerDebitAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentDebitId,ledgerDebitAccountName);

        String ledgerCreditGroupId = ChartOfAccountUtils.getLedgerGroupId(ledgerGroupNega);
        String parentCreditId = ChartOfAccountUtils.getParentId(ledgerCreditGroupId, ledgerGroupNega);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentCreditId,ledgerCreditAccountName);
        try {
            TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            String fromDate = DateUtils.getFirstDateOfMonth(DateUtils.getYear(GMT_7),currentMonth,"dd/MM/yyyy");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", parentDebit, fromDate, "", "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal();

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", ledgerNega, fromDate, "", "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal();
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            page.filter(KASTRAKI_LIMITED,"","","");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colDeCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerDebitAccountName).ledgerCreditNumber(ledgerDebitAccountNumber)
                    .ledgerDebit(ledgerCreditAccountNumber).ledgerDebitNumber(ledgerCreditAccountNumber)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addLedgerTxn(transactionPost,ledgerCreditAccountId,ledgerDebitAccountId,ledgerType);
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21837")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21837(){
        log("@title: Validate total balance of each Debit column");
        log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
        TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Step 2: Filter with Month that is having data");
        page.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: Total amounts = sum up all the absolute values of debit amounts in each column");
        Assert.assertEquals(page.getSumValueOfCol(page.colDeCurrentMonth),Double.valueOf(page.getTotalBalance(true,true)),"FAILED! Current Month Sum Debit displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id = "21838")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21838(){
        log("@title: Validate total balance of each Credit column");
        log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
        TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Step 2: Filter with Month that is having data");
        page.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: Total amounts = sum up all the absolute values of credit amounts in each column");
        Assert.assertEquals(page.getSumValueOfCol(page.colCreCurrentMonth),page.getTotalBalance(true,false),"FAILED! Current Month Sum Credit displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id = "21839")
    @Test(groups = {"regression", "2024.V.2.0"})
    public void Trial_Balance_C21839(){
        log("@title: Validate amount in 'Difference' row of each month");
        log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
        TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Step 2: Filter with Month that is having data");
        page.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: Difference = Total Debit - Total Credit");
        double difAc = DoubleUtils.roundUpWithTwoPlaces(page.getTotalBalance(true,true) - page.getTotalBalance(true,false));
        Assert.assertEquals(difAc,page.getDifferenceValue(page.curMonthDif),"FAILED! Current Month Sum Credit displays incorrect");
        log("INFO: Executed completely");
    }
}
