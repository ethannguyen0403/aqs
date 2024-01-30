package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.financialReports.BalanceSheetPage;
import pages.sb11.financialReports.TrialBalancePage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class TrialBalanceTest extends BaseCaseAQS {
    String parentAccount = "500.000.000.000 - QA Ledger Group Asset";
    int currentMonth = DateUtils.getMonth("GMT +7");
    String debitAstAcc = "AutoAssetDebit - 055.000.000.000";
    String creditAstAcc = "AutoAssetCredit - 050.000.000.000";
    String lgDebitCur = "HKD";
    String lgCreditCur = "HKD";
    String transType = "Payment Other";
    String descAsset = "Automation test: Asset Transaction " + DateUtils.getMilliSeconds();

    @TestRails(id = "2764")
    @Test(groups = {"Revise"})
    public void Trial_Balance_C2764() {
        log("@title: Validate Report data of 'Before CJE' and 'After CJE' will be different only when selecting 'Month' = July.");
        log("@Step 1: Navigate to SB11 > Financial Reports > Trial Balance");
        TrialBalancePage trialBalancePage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Verify 1: Report is disabled with 'Before CJE' option as default when Month is not 'July'");
        trialBalancePage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - November", "");
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
    public void Trial_Balance_C2773() {
        log("@title: Validate Debit/Credit data is matched correctly with Ledger Statement page");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc).ledgerDebitCur(lgDebitCur).ledgerCreditCur(lgCreditCur)
                .amountDebit(2)
                .amountCredit(1).remark(descAsset).transDate("").transType(transType).build();
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditAstAcc).ledgerCredit(debitAstAcc).ledgerDebitCur(lgDebitCur).ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(2).remark("Reverse " + descAsset).transDate("").transType(transType).build();
        try {
            log("@Precondition: Having a ledger transaction with parent Account: " + parentAccount);
            JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(),
                    transaction.getTransDate(), transaction.getTransType(), true);

            log("@Step 1: Navigate to General Reports > Ledger Statement and get Total Credit/Debit amount");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(COMPANY_UNIT, FINANCIAL_YEAR, "Asset", parentAccount, "", "", "");
            double totalDeCre = Double.valueOf(ledgerStatementPage.getTotalCreDeInOriginCurrency("Total in HKD"));
            log("@Step 2: Navigate to SB11 > Financial Reports > Trial Balance");
            TrialBalancePage trialBalancePage =
                    welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
            log("@Step 2: Filter with current Month");
            trialBalancePage.filter(COMPANY_UNIT, FINANCIAL_YEAR, trialBalancePage.convertMonthToFilterMonth(currentMonth, 2023), "");
            String accountCode = parentAccount.split("-")[0].trim();
            double amountDebitCurrenMonth = trialBalancePage.getAmountTrialTable(trialBalancePage.findRowIndexOfParentAccount(accountCode),
                    trialBalancePage.colDeCurrentMonth);
            log("@Verify 1: Validate Debit/Credit data should match correctly with the Ledger Statement page");
            Assert.assertEquals(amountDebitCurrenMonth,totalDeCre, "FAILED!Debit/Credit data NOT match correctly with the Ledger Statement page");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
            welcomePage.waitSpinnerDisappeared();
            journalEntriesPage.addTransaction(transactionPost, AccountType.LEDGER, AccountType.LEDGER, transactionPost.getRemark(),
                    transactionPost.getTransDate(), transactionPost.getTransType(), true);
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

}
