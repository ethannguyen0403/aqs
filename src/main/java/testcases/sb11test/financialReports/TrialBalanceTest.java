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
import utils.sb11.RoleManagementUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class TrialBalanceTest extends BaseCaseAQS {
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
    @Test(groups = {"regression", "2023.11.30","ethan4.0"})
    public void Trial_Balance_C2773() throws IOException {
        log("@title: Validate Debit/Credit data is matched correctly with Ledger Statement page");
        String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
        String parentAccount = LEDGER_GROUP_NUMBER_ASSET+" - "+LEDGER_GROUP_NAME_ASSET;
        int currentMonth = DateUtils.getMonth("GMT +7");
        String transType = "Payment Other";
        String desc = "Automation test: Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_ASSET_CREDIT_NAME).ledgerCreditNumber(LEDGER_ASSET_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_ASSET_DEBIT_NAME).ledgerDebitNumber(LEDGER_ASSET_DEBIT_NUMBER)
                .amountDebit(1).amountCredit(1)
                .remark(desc)
                .transDate(currentDate)
                .transType(transType).build();
        log("@Precondition: Having a ledger transaction with parent Account: " + parentAccount);
        TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,"");
        try {
            log("@Step 1: Navigate to General Reports > Ledger Statement and get Total Credit/Debit amount");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            String fromDate = DateUtils.getFirstDateOfMonth(DateUtils.getYear(GMT_7),currentMonth,"dd/MM/yyyy");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", parentAccount, fromDate, "", "");
            String totalRun = ledgerStatementPage.getTotalInHKD(LEDGER_GROUP_NAME_ASSET,"Shown in HKD","Running Bal.");
            log("@Step 2: Navigate to SB11 > Financial Reports > Trial Balance");
            TrialBalancePage trialBalancePage =
                    welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with current Month");
            int curYear = DateUtils.getYear(GMT_7);
            trialBalancePage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, trialBalancePage.convertMonthToFilterMonth(currentMonth, curYear), "");
            String accountCode = ChartOfAccountUtils.getAccountNumber(parentAccount,true);
            String amountDebitCurrenMonth = trialBalancePage.getAmountValue(accountCode,trialBalancePage.colDeCurrentMonth);
            log("@Verify 1: Validate Debit/Credit data should match correctly with the Ledger Statement page");
            Assert.assertEquals(amountDebitCurrenMonth,totalRun, "FAILED!Debit/Credit data NOT match correctly with the Ledger Statement page");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME).ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME).ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,"");
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "2779")
    @Test(groups = {"regression_stg", "2023.11.30","ethan4.0"})
    @Parameters({"password", "userNameOneRole"})
    public void Trial_Balance_C2779(String password, String userNameOneRole) throws Exception {
        log("@title: Validate user can not access Trial Balance page when having no permission");
        log("Precondition: Deactivate Trial Balance option in one role account");
        RoleManagementUtils.updateRolePermission("one role","Trial Balance","INACTIVE");
        log("@Step 1: Re-login with one role account account has 'Trial Balance' permission is OFF");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: 'Trial Balance' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, TRIAL_BALANCE), "FAILED! Trial balance menu is displayed");
        log("INFO: Executed completely");
    }
    @TestRails(id = "21832")
    @Test(groups = {"regression", "2024.V.2.0","ethan4.0"})
    public void Trial_Balance_C21832() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Asset");
        log("Pre-condition 1: Having some txn with detail type = Asset");
        String parentDebit = "500.000.000.000 - QA Ledger Group Asset";
        String ledgerAccName = "AutoAssetCreditNega";
        String ledgerAccNumber = "050.000.000.001";
        String ledgerGroupNega = "QA Ledger Auto Asset";
        String ledgerNega = "500.000.000.001 - QA Ledger Auto Asset";
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");
        String transType = "Payment Other";
        String desc = "Automation test: Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerAccName).ledgerCreditNumber(ledgerAccNumber)
                .ledgerDebit(LEDGER_ASSET_DEBIT_NAME).ledgerDebitNumber(LEDGER_ASSET_DEBIT_NUMBER)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(String.format("%s-%s-15",year,month))
                .transType(transType).build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_ASSET,ledgerGroupNega,LEDGER_PARENT_NAME_ASSET,ledgerGroupNega,"");
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", parentDebit, firstDayOfMonth, lastDayOfMonth, "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", ledgerNega, firstDayOfMonth, lastDayOfMonth, "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            String monthFilter = DateUtils.getMonthYear(GMT_7,-1,"yyyy - MMMM");
            page.filter(KASTRAKI_LIMITED,"",monthFilter,"");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colDeCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME).ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(ledgerAccName).ledgerDebitNumber(ledgerAccNumber)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(String.format("%s-%s-15",year,month))
                    .transType("Tax Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",ledgerGroupNega,LEDGER_GROUP_NAME_ASSET,ledgerGroupNega,LEDGER_PARENT_NAME_ASSET,"");
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21833")
    @Test(groups = {"regression", "2024.V.2.0","ethan3.0"})
    public void Trial_Balance_C21833() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Expenditure");
        log("Pre-condition 1: Having some txn with detail type = Expenditure");
        String parentDebit = "100.000.000.000 - QA Ledger Group Expenditure";
        String ledgerAccNegaName = "AutoExpenditureCreditNega";
        String ledgerAccNegaNum = "050.000.000.011";
        String ledgerGroupNega = "QA Ledger Auto Expenditure";
        String ledgerNega = "100.000.000.001 - QA Ledger Auto Expenditure";
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");
        String transType = "Payment Other";
        String desc = "Automation test: Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerAccNegaName).ledgerCreditNumber(ledgerAccNegaNum)
                .ledgerDebit(LEDGER_EXPENDITURE_DEBIT_NAME).ledgerDebitNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(String.format("%s-%s-15",year,month))
                .transType(transType).build();

        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_EXPENDITURE,ledgerGroupNega,LEDGER_PARENT_NAME_EXPENDITURE,ledgerGroupNega,"");
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", parentDebit, firstDayOfMonth, lastDayOfMonth, "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", ledgerNega, firstDayOfMonth, lastDayOfMonth, "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            String monthFilter = DateUtils.getMonthYear(GMT_7,-1,"yyyy - MMMM");
            page.filter(KASTRAKI_LIMITED,"",monthFilter,"");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colDeCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME).ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(ledgerAccNegaName).ledgerDebitNumber(ledgerAccNegaNum)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(String.format("%s-%s-15",year,month))
                    .transType("Tax Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",ledgerGroupNega,LEDGER_GROUP_NAME_EXPENDITURE,ledgerGroupNega,LEDGER_PARENT_NAME_EXPENDITURE,"");
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21834")
    @Test(groups = {"regression", "2024.V.2.0","ethan3.0"})
    public void Trial_Balance_C21834() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Liability");
        log("Pre-condition 1: Having some txn with detail type = Liability");
        String parentDebit = "400.000.000.000 - QA Ledger Group Liability";
        String ledgerAccNegaName = "AutoLiabilityCreditNega";
        String ledgerAccNegaNum = "040.000.000.011";
        String ledgerGroupNega = "QA Ledger Auto Liability";
        String ledgerNega = "400.000.000.001 - QA Ledger Auto Liability";
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");
        String transType = "Payment Other";
        String desc = "Automation test: Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerAccNegaName).ledgerCreditNumber(ledgerAccNegaNum)
                .ledgerDebit(LEDGER_LIABILITY_DEBIT_NAME).ledgerDebitNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(String.format("%s-%s-15",year,month))
                .transType(transType).build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_LIABILITY,ledgerGroupNega,LEDGER_PARENT_NAME_LIABILITY,ledgerGroupNega,"");
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", LEDGER_GROUP_NAME_LIABILITY, firstDayOfMonth, lastDayOfMonth, "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", ledgerNega, firstDayOfMonth, lastDayOfMonth, "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            String monthFilter = DateUtils.getMonthYear(GMT_7,-1,"yyyy - MMMM");
            page.filter(KASTRAKI_LIMITED,"",monthFilter,"");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getChartCode(ChartOfAccountUtils.getAccountName(parentDebit,true)),page.colDeCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getChartCode(ChartOfAccountUtils.getAccountName(ledgerNega,true)),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_LIABILITY_DEBIT_NAME).ledgerCreditNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                    .ledgerDebit(ledgerAccNegaName).ledgerDebitNumber(ledgerAccNegaNum)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(String.format("%s-%s-15",year,month))
                    .transType("Tax Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",ledgerGroupNega,LEDGER_GROUP_NAME_LIABILITY,ledgerGroupNega,LEDGER_PARENT_NAME_LIABILITY,"");
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21835")
    @Test(groups = {"regression", "2024.V.2.0","ethan4.0"})
    public void Trial_Balance_C21835() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Capital");
        log("Pre-condition 1: Having some txn with detail type = Capital");
        String parentDebit = "300.000.000.000 - QA Ledger Group Capital";
        String ledgerAccNegaName = "AutoCapitalCreditNega";
        String ledgerAccNegaNum = "030.000.000.011";
        String ledgerGroupNega = "QA Ledger Auto Capital";
        String ledgerNega = "300.000.000.100 - QA Ledger Auto Capital";
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");
        String transType = "Payment Other";
        String desc = "Automation test: Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_CAPITAL_CREDIT_NAME).ledgerCreditNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                .ledgerDebit(ledgerAccNegaName).ledgerDebitNumber(ledgerAccNegaNum)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(String.format("%s-%s-15",year,month))
                .transType(transType).build();

        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",ledgerGroupNega,LEDGER_GROUP_NAME_CAPITAL,ledgerGroupNega,LEDGER_PARENT_NAME_CAPITAL,"");
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", parentDebit, firstDayOfMonth, lastDayOfMonth, "");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", ledgerNega, firstDayOfMonth, lastDayOfMonth, "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            String monthFilter = DateUtils.getMonthYear(GMT_7,-1,"yyyy - MMMM");
            page.filter(KASTRAKI_LIMITED,"",monthFilter,"");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(parentDebit,true),page.colCreCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getAccountNumber(ledgerNega,true),page.colDeCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerAccNegaName).ledgerCreditNumber(ledgerAccNegaNum)
                    .ledgerDebit(LEDGER_CAPITAL_CREDIT_NAME).ledgerDebitNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(String.format("%s-%s-15",year,month))
                    .transType("Tax Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_CAPITAL,ledgerGroupNega,LEDGER_PARENT_NAME_CAPITAL,ledgerGroupNega,"");
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21836")
    @Test(groups = {"regression", "2024.V.2.0","ethan4.0"})
    public void Trial_Balance_C21836() throws IOException {
        log("@title: Validate amount shows in Credit/Debit column when detail type = Income");
        log("Pre-condition 1: Having some txn with detail type = Income");
        String parentDebit = "000.000.001.000 - QA Ledger Group Income";
        String ledgerAccNegaName = "AutoIncomeCreditNega";
        String ledgerAccNegaNum = "002.000.000.011";
        String ledgerGroupNega = "QA Ledger Auto Income";
        String ledgerNega = "000.000.001.001 - QA Ledger Auto Income";
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");
        String transType = "Payment Other";
        String desc = "Automation test: Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledgerAccNegaName).ledgerCreditNumber(ledgerAccNegaNum)
                .ledgerDebit(LEDGER_INCOME_DEBIT_NAME).ledgerDebitNumber(LEDGER_INCOME_DEBIT_NUMBER)
                .amountDebit(5).amountCredit(5)
                .remark(desc)
                .transDate(String.format("%s-%s-15",year,month))
                .transType(transType).build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_INCOME,ledgerGroupNega,LEDGER_PARENT_NAME_INCOME,ledgerGroupNega,"");
            log("Pre-condition 2: Get value of debit/credit that will display in trial balance");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, firstDayOfMonth, lastDayOfMonth, "Before CJE");
            String valueDeEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");

            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", ledgerNega, firstDayOfMonth, lastDayOfMonth, "");
            String valueCreEx = ledgerStatementPage.getGrandTotalByRunningBal("CUR Translation");
            log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
            TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with Month that is having data");
            String monthFilter = DateUtils.getMonthYear(GMT_7,-1,"yyyy - MMMM");
            page.filter(KASTRAKI_LIMITED,"",monthFilter,"");
            String valueDeAc = page.getAmountValue(ChartOfAccountUtils.getChartCode(ChartOfAccountUtils.getAccountName(parentDebit,true)),page.colCreCurrentMonth);
            String valueCreAc = page.getAmountValue(ChartOfAccountUtils.getChartCode(ChartOfAccountUtils.getAccountName(ledgerNega,true)),page.colCreCurrentMonth);
            log("@Verify 1: The amount is positive, display it in Debit");
            Assert.assertEquals(valueDeAc,valueDeEx,"FAILED! Value Debit display incorrect");
            log("@Verify 2: The amount is negative, display it in Credit");
            Assert.assertEquals(valueCreAc,valueCreEx,"FAILED! Value Debit display incorrect");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_INCOME_DEBIT_NAME).ledgerCreditNumber(LEDGER_INCOME_DEBIT_NUMBER)
                    .ledgerDebit(ledgerAccNegaName).ledgerDebitNumber(ledgerAccNegaNum)
                    .amountDebit(5).amountCredit(5)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(String.format("%s-%s-15",year,month))
                    .transType("Tax Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",ledgerGroupNega,LEDGER_GROUP_NAME_INCOME,ledgerGroupNega,LEDGER_PARENT_NAME_INCOME,"");
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "21837")
    @Test(groups = {"regression", "2024.V.2.0","ethan3.0"})
    public void Trial_Balance_C21837(){
        log("@title: Validate total balance of each Debit column");
        log("@Step 1: Access SB11 > Financial Reports > Trial Balance");
        TrialBalancePage page = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Step 2: Filter with Month that is having data");
        page.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: Total amounts = sum up all the absolute values of debit amounts in each column");
        page.verifyTotalBalance(page.colDeCurrentMonth,true,true);
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
        page.verifyTotalBalance(page.colCreCurrentMonth,true,false);
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
        page.verifyDifferenceValue(true);
        log("INFO: Executed completely");
    }
}
