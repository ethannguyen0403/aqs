package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import common.SBPConstants;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.SystemMonitoringPage;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import pages.sb11.generalReports.systemmonitoring.ClosingJournalEntriesPage;
import testcases.BaseCaseAQS;
import utils.sb11.CompanySetUpUtils;
import utils.sb11.JournalReportsUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class LedgerStatementTest extends BaseCaseAQS {
    @TestRails(id = "841")
    @Test(groups = {"smoke","ethan5.0"})
    public void Ledger_Statement_TC841() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Expenditure");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Other";
        String debitExpAcc = LEDGER_EXPENDITURE_DEBIT_NAME + " - " + LEDGER_EXPENDITURE_DEBIT_NUMBER;
        String creditExpAcc = LEDGER_EXPENDITURE_CREDIT_NAME + " - " + LEDGER_EXPENDITURE_CREDIT_NUMBER;
        String descExpenditure = "TC_841 Expenditure Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, transType, descExpenditure);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, "", "", "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (convert to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, LEDGER_GROUP_NAME_EXPENDITURE);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME).ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_EXPENDITURE_CREDIT_NAME).ledgerDebitNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "842")
    @Test(groups = {"smoke","ethan6.0"})
    public void Ledger_Statement_TC842() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Expenditure");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Other";
        String debitExpAcc = LEDGER_EXPENDITURE_DEBIT_NAME + " - " + LEDGER_EXPENDITURE_DEBIT_NUMBER;
        String creditExpAcc = LEDGER_EXPENDITURE_CREDIT_NAME + " - " + LEDGER_EXPENDITURE_CREDIT_NUMBER;
        String descExpenditure = "TC_842 Expenditure Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, transType, descExpenditure);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", LEDGER_GROUP_NAME_EXPENDITURE, "", "", "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, LEDGER_GROUP_NAME_EXPENDITURE);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME).ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_EXPENDITURE_CREDIT_NAME).ledgerDebitNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "843")
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"clientCode"})
    public void Ledger_Statement_TC843() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Expenditure (Debit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Feed";
        String debitExpAcc = LEDGER_EXPENDITURE_DEBIT_NAME + " - " + LEDGER_EXPENDITURE_DEBIT_NUMBER;
        String creditExpAcc = LEDGER_EXPENDITURE_CREDIT_NAME + " - " + LEDGER_EXPENDITURE_CREDIT_NUMBER;
        String descExpenditure = "TC_843 Expenditure Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, transType, descExpenditure);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", LEDGER_GROUP_NAME_EXPENDITURE, "", "", "");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, LEDGER_EXPENDITURE_DEBIT_ACC);

            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");

        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME).ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_EXPENDITURE_CREDIT_NAME).ledgerDebitNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "844")
    @Test(groups = {"smoke","ethan5.0"})
    public void Ledger_Statement_TC844() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Expenditure (Credit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Feed";
        String debitExpAcc = LEDGER_EXPENDITURE_DEBIT_NAME + " - " + LEDGER_EXPENDITURE_DEBIT_NUMBER;
        String creditExpAcc = LEDGER_EXPENDITURE_CREDIT_NAME + " - " + LEDGER_EXPENDITURE_CREDIT_NUMBER;
        String descExpenditure = "TC_844 Expenditure Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, transType, descExpenditure);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Expenditure", LEDGER_GROUP_NAME_EXPENDITURE, "", "", "");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, LEDGER_EXPENDITURE_CREDIT_ACC);
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME).ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_EXPENDITURE_CREDIT_NAME).ledgerDebitNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, SBPConstants.LEDGER_GROUP_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, LEDGER_PARENT_NAME_EXPENDITURE, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitExpAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "845")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC845() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Asset");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Other";
        String debitAstAcc = LEDGER_ASSET_DEBIT_NAME + " - " + LEDGER_ASSET_DEBIT_NUMBER;
        String creditAstAcc = LEDGER_ASSET_CREDIT_NAME + " - " + LEDGER_ASSET_CREDIT_NUMBER;
        String descAsset = "TC_845 Asset Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        String date = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate(date)
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, transType, descAsset);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", LEDGER_GROUP_NAME_ASSET, date, date, "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, LEDGER_GROUP_NAME_ASSET);
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME).ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME).ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_ASSET, SBPConstants.LEDGER_GROUP_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id = "846")
    @Test(groups = {"smoke_qc","ethan6.0"})
    public void Ledger_Statement_TC846() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Asset");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Other";
        String debitAstAcc = LEDGER_ASSET_DEBIT_NAME + " - " + LEDGER_ASSET_DEBIT_NUMBER;
        String creditAstAcc = LEDGER_ASSET_CREDIT_NAME + " - " + LEDGER_ASSET_CREDIT_NUMBER;
        String descAsset = "TC_846 Asset Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        String date = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate(date)
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, transType, descAsset);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", LEDGER_GROUP_NAME_ASSET, date, date, "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, LEDGER_GROUP_NAME_ASSET);
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME).ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME).ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_ASSET, SBPConstants.LEDGER_GROUP_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id = "847")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC847() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Asset (Debit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Feed";
        String debitAstAcc = LEDGER_ASSET_DEBIT_NAME + " - " + LEDGER_ASSET_DEBIT_NUMBER;
        String creditAstAcc = LEDGER_ASSET_CREDIT_NAME + " - " + LEDGER_ASSET_CREDIT_NUMBER;
        String descAsset = "TC_847 Asset Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, transType, descAsset);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", LEDGER_GROUP_NAME_ASSET, "", "", "");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_ASSET, LEDGER_ASSET_DEBIT_ACC);
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME).ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME).ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_ASSET, SBPConstants.LEDGER_GROUP_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "848")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC848() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Asset (Credit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Feed";
        String debitAstAcc = LEDGER_ASSET_DEBIT_NAME + " - " + LEDGER_ASSET_DEBIT_NUMBER;
        String creditAstAcc = LEDGER_ASSET_CREDIT_NAME + " - " + LEDGER_ASSET_CREDIT_NUMBER;
        String descAsset = "TC_848 Asset Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");

        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate("")
                .transType(transType)
                .build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, transType, descAsset);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", LEDGER_GROUP_NAME_ASSET, "", "", "");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_ASSET, LEDGER_ASSET_CREDIT_ACC);
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME).ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME).ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_ASSET, SBPConstants.LEDGER_GROUP_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, LEDGER_PARENT_NAME_ASSET, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitAstAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "849")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC849() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Liability");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Other";
        String debitLibAcc = LEDGER_LIABILITY_DEBIT_NAME + " - " + LEDGER_LIABILITY_DEBIT_NUMBER;
        String creditLibAcc = LEDGER_LIABILITY_CREDIT_NAME + " - " + LEDGER_LIABILITY_CREDIT_NUMBER;
        String descLiability = "TC_849 Liability Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder().ledgerDebit(debitLibAcc).ledgerCredit(creditLibAcc).ledgerDebitCur("HKD").ledgerCreditCur("HKD")
                .amountDebit(1).amountCredit(1).remark(descLiability).transDate("").transType(transType).build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLibAcc, transType, descLiability);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", LEDGER_GROUP_NAME_LIABILITY, "", "", "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, LEDGER_GROUP_NAME_LIABILITY);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_LIABILITY_DEBIT_NAME).ledgerCreditNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_LIABILITY_CREDIT_NAME).ledgerDebitNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_LIABILITY, SBPConstants.LEDGER_GROUP_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLibAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "850")
    @Test(groups = {"smoke_qc","ethan6.0"})
    public void Ledger_Statement_TC850() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Liability");
        log("@Step 1: Login to SB11 site");
        log("@Step Precondition: Get Credit/Debit amount on Ledger Statement page");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        String transType = "Payment Other";
        String debitLibAcc = LEDGER_LIABILITY_DEBIT_NAME + " - " + LEDGER_LIABILITY_DEBIT_NUMBER;
        String creditLibAcc = LEDGER_LIABILITY_CREDIT_NAME + " - " + LEDGER_LIABILITY_CREDIT_NUMBER;
        String descLiability = "TC_850 Liability Transaction " + DateUtils.getMilliSeconds();
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitLibAcc)
                .ledgerCredit(creditLibAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(descLiability)
                .transDate("")
                .transType(transType)
                .build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLibAcc, transType, descLiability);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", LEDGER_GROUP_NAME_LIABILITY, "", "", "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, LEDGER_GROUP_NAME_LIABILITY);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_LIABILITY_DEBIT_NAME).ledgerCreditNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_LIABILITY_CREDIT_NAME).ledgerDebitNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_LIABILITY, SBPConstants.LEDGER_GROUP_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLibAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "851")
    @Test(groups = {"smoke_qc", "ethan6.0"})
    public void Ledger_Statement_TC851() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Liability (Credit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Liability");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        String debitLiaAcc = LEDGER_LIABILITY_DEBIT_NAME + " - " + LEDGER_LIABILITY_DEBIT_NUMBER;
        String creditLiaAcc = LEDGER_LIABILITY_CREDIT_NAME + " - " + LEDGER_LIABILITY_CREDIT_NUMBER;
        String cur = "HKD";
        String descLia = "TC_851 Liability Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitLiaAcc)
                .ledgerCredit(creditLiaAcc)
                .ledgerDebitCur(cur)
                .ledgerCreditCur(cur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descLia)
                .transDate("")
                .transType(JournalEntries.TRANSACTION_TYPE_LIST.get(5))
                .build();
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLiaAcc, JournalEntries.TRANSACTION_TYPE_LIST.get(5), descLia);
        try {
            log("@Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", SBPConstants.LEDGER_GROUP_NAME_LIABILITY, "", "", "");

            log("@Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_LIABILITY, LEDGER_LIABILITY_CREDIT_ACC);
            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records");
            log("@Verify 2: Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_LIABILITY_DEBIT_NAME).ledgerCreditNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_LIABILITY_CREDIT_NAME).ledgerDebitNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_LIABILITY, SBPConstants.LEDGER_GROUP_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLiaAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "852")
    @Test(groups = {"smoke_qc", "2024.V.3.0","ethan5.0"})
    public void Ledger_Statement_TC852() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Liability (Debit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Liability");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        String debitLiaAcc = LEDGER_LIABILITY_DEBIT_NAME + " - " + LEDGER_LIABILITY_DEBIT_NUMBER;
        String creditLiaAcc = LEDGER_LIABILITY_CREDIT_NAME + " - " + LEDGER_LIABILITY_CREDIT_NUMBER;
        String cur = "HKD";
        String descLia = "TC_852 Liability Transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitLiaAcc)
                .ledgerCredit(creditLiaAcc)
                .ledgerDebitCur(cur)
                .ledgerCreditCur(cur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descLia)
                .transDate("")
                .transType(JournalEntries.TRANSACTION_TYPE_LIST.get(5))
                .build();
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLiaAcc, JournalEntries.TRANSACTION_TYPE_LIST.get(5), descLia);
        try {
            log("@Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Liability", SBPConstants.LEDGER_GROUP_NAME_LIABILITY, "", "", "");

            log("@Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_LIABILITY, LEDGER_LIABILITY_DEBIT_ACC);
            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records");
            log("@Verify 2: Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_LIABILITY_DEBIT_NAME).ledgerCreditNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_LIABILITY_CREDIT_NAME).ledgerDebitNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_LIABILITY, SBPConstants.LEDGER_GROUP_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, LEDGER_PARENT_NAME_LIABILITY, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitLiaAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "853")
    @Test(groups = {"smoke_qc","ethan6.0"})
    public void Ledger_Statement_TC853() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Capital");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        String transType = "Payment Other";
        String debitCapitalAcc = LEDGER_CAPITAL_DEBIT_NAME + " - " + LEDGER_CAPITAL_DEBIT_NUMBER;
        String creditCapitalAcc = LEDGER_CAPITAL_CREDIT_NAME + " - " + LEDGER_CAPITAL_CREDIT_NUMBER;
        String desCap = "TC_853 Auto run for Capital transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desCap)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, transType, desCap);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", LEDGER_GROUP_NAME_CAPITAL, "", "", "");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, LEDGER_GROUP_NAME_CAPITAL);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_CAPITAL_DEBIT_NAME).ledgerCreditNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_CAPITAL_CREDIT_NAME).ledgerDebitNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_CAPITAL, SBPConstants.LEDGER_GROUP_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "854")
    @Test(groups = {"smoke_qc","ethan6.0"})
    public void Ledger_Statement_TC854() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Capital");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        String transType = "Payment Other";
        String debitCapitalAcc = LEDGER_CAPITAL_DEBIT_NAME + " - " + LEDGER_CAPITAL_DEBIT_NUMBER;
        String creditCapitalAcc = LEDGER_CAPITAL_CREDIT_NAME + " - " + LEDGER_CAPITAL_CREDIT_NUMBER;
        String desCap = "TC_854 Auto run for Capital transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desCap)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, transType, desCap);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", LEDGER_GROUP_NAME_CAPITAL, "", "", "");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, LEDGER_GROUP_NAME_CAPITAL);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_CAPITAL_DEBIT_NAME).ledgerCreditNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_CAPITAL_CREDIT_NAME).ledgerDebitNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_CAPITAL, SBPConstants.LEDGER_GROUP_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "855")
    @Test(groups = {"smoke_qc","ethan6.0"})
    public void Ledger_Statement_TC855() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Capital (Credit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        String transType = "Payment Feed";
        String debitCapitalAcc = LEDGER_CAPITAL_DEBIT_NAME + " - " + LEDGER_CAPITAL_DEBIT_NUMBER;
        String creditCapitalAcc = LEDGER_CAPITAL_CREDIT_NAME + " - " + LEDGER_CAPITAL_CREDIT_NUMBER;
        String desCap = "TC_855 Auto run for Capital transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desCap)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, transType, desCap);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", LEDGER_GROUP_NAME_CAPITAL, "", "", "");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_CAPITAL, LEDGER_CAPITAL_CREDIT_ACC);

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to showy");
            ledgerDetailPopup.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_CAPITAL_DEBIT_NAME).ledgerCreditNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_CAPITAL_CREDIT_NAME).ledgerDebitNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_CAPITAL, SBPConstants.LEDGER_GROUP_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "856")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC856() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Capital (Debit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        String transType = "Payment Feed";
        String debitCapitalAcc = LEDGER_CAPITAL_DEBIT_NAME + " - " + LEDGER_CAPITAL_DEBIT_NUMBER;
        String creditCapitalAcc = LEDGER_CAPITAL_CREDIT_NAME + " - " + LEDGER_CAPITAL_CREDIT_NUMBER;
        String desCap = "TC_856 Auto run for Capital transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desCap)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, transType, desCap);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Capital", LEDGER_GROUP_NAME_CAPITAL, "", "", "");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_CAPITAL, LEDGER_CAPITAL_DEBIT_ACC);

            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_CAPITAL_DEBIT_NAME).ledgerCreditNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_CAPITAL_CREDIT_NAME).ledgerDebitNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_CAPITAL, SBPConstants.LEDGER_GROUP_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, LEDGER_PARENT_NAME_CAPITAL, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitCapitalAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "857")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC857() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Income");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        String transType = "Payment Other";
        String debitIncomeAcc = LEDGER_INCOME_DEBIT_NAME + " - " + LEDGER_INCOME_DEBIT_NUMBER;
        String creditIncomeAcc = LEDGER_INCOME_CREDIT_NAME + " - " + LEDGER_INCOME_CREDIT_NUMBER;
        String desInc = "TC_857 Auto run for Income transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desInc)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, transType, desInc);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, "", "", "");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, LEDGER_GROUP_NAME_INCOME);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_INCOME_DEBIT_NAME).ledgerCreditNumber(LEDGER_INCOME_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_INCOME_CREDIT_NAME).ledgerDebitNumber(LEDGER_INCOME_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_INCOME, SBPConstants.LEDGER_GROUP_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "858")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC858() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Income");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        String transType = "Payment Other";
        String debitIncomeAcc = LEDGER_INCOME_DEBIT_NAME + " - " + LEDGER_INCOME_DEBIT_NUMBER;
        String creditIncomeAcc = LEDGER_INCOME_CREDIT_NAME + " - " + LEDGER_INCOME_CREDIT_NUMBER;
        String desInc = "TC_858 Auto run for Income transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desInc)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, transType, desInc);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, "", "", "");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_INCOME, LEDGER_INCOME_DEBIT_ACC);

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_INCOME_DEBIT_NAME).ledgerCreditNumber(LEDGER_INCOME_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_INCOME_CREDIT_NAME).ledgerDebitNumber(LEDGER_INCOME_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_INCOME, SBPConstants.LEDGER_GROUP_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "859")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC859() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Income (Credit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        String transType = "Payment Feed";
        String debitIncomeAcc = LEDGER_INCOME_DEBIT_NAME + " - " + LEDGER_INCOME_DEBIT_NUMBER;
        String creditIncomeAcc = LEDGER_INCOME_CREDIT_NAME + " - " + LEDGER_INCOME_CREDIT_NUMBER;
        String desInc = "TC_859 Auto run for Income transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desInc)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, transType, desInc);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction\n");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, "", "", "");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_INCOME, LEDGER_INCOME_CREDIT_ACC);

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_INCOME_DEBIT_NAME).ledgerCreditNumber(LEDGER_INCOME_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_INCOME_CREDIT_NAME).ledgerDebitNumber(LEDGER_INCOME_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_INCOME, SBPConstants.LEDGER_GROUP_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @TestRails(id = "860")
    @Test(groups = {"smoke_qc","ethan5.0"})
    public void Ledger_Statement_TC860() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Income (Debit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        String transType = "Payment Feed";
        String debitIncomeAcc = LEDGER_INCOME_DEBIT_NAME + " - " + LEDGER_INCOME_DEBIT_NUMBER;
        String creditIncomeAcc = LEDGER_INCOME_CREDIT_NAME + " - " + LEDGER_INCOME_CREDIT_NUMBER;
        String desInc = "TC_860 Auto run for Income transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(1)
                .amountCredit(1)
                .remark(desInc)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, false);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        String transDate = DateUtils.getDate(0, "dd/MM/yyyy", GMT_7);
        JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, transType, desInc);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, "", "", "");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(SBPConstants.LEDGER_GROUP_NAME_INCOME, LEDGER_INCOME_DEBIT_ACC);

            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show\n" +
                    "\n");
            ledgerDetailPopup.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String remarkTXN = String.format("Automation Testing Transaction Ledger: Post-condition for txn %s", DateUtils.getMilliSeconds());
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_INCOME_DEBIT_NAME).ledgerCreditNumber(LEDGER_INCOME_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_INCOME_CREDIT_NAME).ledgerDebitNumber(LEDGER_INCOME_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(currentDate)
                    .transType("Received Comm/Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost, "Ledger", SBPConstants.LEDGER_GROUP_NAME_INCOME, SBPConstants.LEDGER_GROUP_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, LEDGER_PARENT_NAME_INCOME, "");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(transDate, transDate, "Ledger", debitIncomeAcc, "Received Comm/Rebate", remarkTXN);
        }
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "2160")
    public void Ledger_Statement_2160() {
        log("@title: Validate at the end of each month all Income and Expenditure sub-accounts (ledgers) must have balance = 0");
        String monthYear = DateUtils.getMonthYear(GMT_7, -1, "MM/yyyy");
        String fromDate = DateUtils.getFirstDateOfMonth(Integer.valueOf(monthYear.split("/")[1]), Integer.valueOf(monthYear.split("/")[0]), "dd/MM/yyyy");
        String toDate = DateUtils.getLastDateOfMonth(Integer.valueOf(monthYear.split("/")[1]), Integer.valueOf(monthYear.split("/")[0]), "dd/MM/yyyy");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log("@Step 3: Filter ledger statement with data: ");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, fromDate, toDate, REPORT_TYPE.get(1));
        log("@Verify 1: Validate running Bal of Total in HKD at Amounts are shown in HKD section should be 0");
        Assert.assertEquals(ledgerStatementPage.getTotalAmountInOriginCurrency("Total in HKD"), "0.00", "FAILED! Running Bal of Total in HKD is not equal to 0");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2769")
    public void Ledger_Statement_2769() {
        log("@title: Validate Ledger Statement page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        log("Validate Ledger Statement page is displayed with correctly title");
        Assert.assertTrue(ledgerStatementPage.getTitlePage().contains(LEDGER_STATEMENT), "FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "ethan2.0"})
    @TestRails(id = "2770")
    public void Ledger_Statement_2770() {
        String detailType = "000.000.001.000 - QA Ledger Group Income";
        log("@title: Validate UI on Ledger Statement is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        log("Validate UI Info display correctly");
        ledgerStatementPage.verifyUI(detailType);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2772")
    public void Ledger_Statement_2772() {
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "ledger-statement.xlsx";
        log("@title: Validate can export Ledger Statement to Excel file successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log("@Step 3: Click Export To Excel");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, "", "", "");
        ledgerStatementPage.exportExcel();
        log("Validate can export Ledger Statement to Excel file successfully");
        Assert.assertTrue(FileUtils.doesFileNameExist(dowloadPath), "Failed to download Expected document");
        log("@Post-condition: delete download file");
//        try {
//            FileUtils.removeFile(dowloadPath);
//        } catch (IOException e) {
//            log(e.getMessage());
//        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2768")
    public void Ledger_Statement_2768() {
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "ledger-statement.pdf";
        log("@title: Validate can export Ledger Statement to PDF file successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log("@Step 3: Click Export To PDF");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", LEDGER_GROUP_NAME_INCOME, "", "", "");
        ledgerStatementPage.exportPDF();
        log("Validate can export Ledger Statement to Excel file successfully");
        Assert.assertTrue(FileUtils.doesFileNameExist(dowloadPath), "Failed to download Expected document");
        log("@Post-condition: delete download file");
//        try {
//            FileUtils.removeFile(dowloadPath);
//        } catch (IOException e) {
//            log(e.getMessage());
//        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_stg", "2024.V.2.0", "ethan"})
    @TestRails(id = "15754")
    public void Ledger_Statement_15754() {
        log("@title: Validate there is only 1 CJE Income transaction with the updated/latest amounts after users trigger a CJE manually");
        log("@pre-condition 1: Ledger Statement permission is ON");
        log("@pre-condition 2: Journal Entry and Journal Entry Ledger permission is ON");
        log("@pre-condition 3: System Monitoring permission is ON");
        String ledgerGroup = "302.000.000.000 - Retained Earnings";
        String ledgerName = "302.000.001.000 - PL for Current Year - HKD";
        int year = DateUtils.getYear(GMT_7);
        int month = DateUtils.getMonth(GMT_7);
        String fromDate = DateUtils.getFirstDateOfMonth(year, month - 1, "dd/MM/yyyy");
        String toDate = DateUtils.getLastDateOfMonth(year, month - 1, "dd/MM/yyyy");
        String transType = "Payment Other";
        String debitIncomeAcc = LEDGER_INCOME_DEBIT_NAME + " - " + LEDGER_INCOME_DEBIT_NUMBER;
        String creditIncomeAcc = LEDGER_INCOME_CREDIT_NAME + " - " + LEDGER_INCOME_CREDIT_NUMBER;
        log("@Step 1: Go to General Reports >> Ledger Statement page");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log("Step 2: Filter data of 302.000.000.000 - Retained Earnings in testing month\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Financial Year = 2023-2024\n" +
                "Account Type = Capital\n" +
                "Detail Type = 302.000.000.000 - Retained Earnings\n" +
                "Report = After CJE");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, "", "Capital", ledgerGroup, fromDate, toDate, "After CJE");
        log("@Step 3: Get Running Bal. of account 302.000.001.000 - PL for Current Year - HKD at 'Amounts are shown in HKD' column (A)");
        double valueA = ledgerStatementPage.getValueAmount(ledgerName, ledgerStatementPage.colRunBalGBP);
        log("@Step 4: Go to Accounting >> Journal Entries");
        JournalEntriesPage journalEntriesPage = ledgerStatementPage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 5: Perform a txn in the last 3 months which has CJE for an Income Ledger account\n" +
                "e.g. Debit: Amount = 106.9HKD (B)\n" +
                "Credit: Amount = 106.9HKD (C)");
        double valueB = 100.9;
        double valueC = 100.9;
        String desInc = "TC_15754 Auto run for Income transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(valueB)
                .amountCredit(valueC)
                .remark(desInc)
                .transDate(fromDate)
                .transType(transType)
                .build();
        log("@Step 6: Click Submit button");
        log("@Step 7: Click Yes button in reminder dialog");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, true);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", debitIncomeAcc, transType, desInc);
        log("@Step 8: Go to General Reports >> System Monitoring page");
        log("@Step 9: Click Closing Journal Entries button");
        ClosingJournalEntriesPage closingJournalEntriesPage = journalEntriesPage.navigatePage(GENERAL_REPORTS, SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES, ClosingJournalEntriesPage.class);
        log("@Step 10: Click Perform CJE for month that perform txn at step #5\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Mont = 2023 - October");
        log("@Step 11: Click Yes button in confirm dialog");
        closingJournalEntriesPage.performCJE(KASTRAKI_LIMITED, "", true);
        log("@Step 12: Back to General Reports >> Ledger Statement page");
        ledgerStatementPage = closingJournalEntriesPage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log("@Step 13: Filter data as step #2");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, "", "Capital", ledgerGroup, fromDate, toDate, "After CJE");
        log("@Verify 1: Running Bal. in 'Amounts are shown in HKD' column = (A) + [(B) -(C)]");
        ledgerStatementPage.verifyRunningBalAfterTriggering(valueA, valueB, valueC);
    }

    @Test(groups = {"regression_stg", "2024.V.2.0", "ethan3.0"})
    @TestRails(id = "23700")
    public void Ledger_Statement_23700() {
        log("@title: Validate there is only 1 CJE Expenditure transaction with the updated/latest amounts after users trigger a CJE manually");
        log("@pre-condition 1: Ledger Statement permission is ON");
        log("@pre-condition 2: Journal Entry and Journal Entry Ledger permission is ON");
        log("@pre-condition 3: System Monitoring permission is ON");
        String ledgerGroup = "302.000.000.000 - Retained Earnings";
        String ledgerName = "302.000.001.000 - PL for Current Year - HKD";
        int year = DateUtils.getYear(GMT_7);
        int month = DateUtils.getMonth(GMT_7);
        String fromDate = DateUtils.getFirstDateOfMonth(year, month - 1, "dd/MM/yyyy");
        String toDate = DateUtils.getLastDateOfMonth(year, month - 1, "dd/MM/yyyy");
        String transType = "Payment Other";
        String debitExpAcc = LEDGER_EXPENDITURE_DEBIT_NAME + " - " + LEDGER_EXPENDITURE_DEBIT_NUMBER;
        String creditExpAcc = LEDGER_EXPENDITURE_CREDIT_NAME + " - " + LEDGER_EXPENDITURE_CREDIT_NUMBER;
        log("@Step 1: Go to General Reports >> Ledger Statement page");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        ledgerStatementPage.waitSpinnerDisappeared();
        log("Step 2: Filter data of 302.000.000.000 - Retained Earnings in testing month\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Financial Year = 2023-2024\n" +
                "Account Type = Capital\n" +
                "Detail Type = 302.000.000.000 - Retained Earnings\n" +
                "Report = After CJE");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, "", "Capital", ledgerGroup, fromDate, toDate, "After CJE");
        log("@Step 3: Get Running Bal. of account 302.000.001.000 - PL for Current Year - HKD at 'Amounts are shown in HKD' column (A)");
        double valueA = ledgerStatementPage.getValueAmount(ledgerName, ledgerStatementPage.colRunBalGBP);
        log("@Step 4: Go to Accounting >> Journal Entries");
        JournalEntriesPage journalEntriesPage = ledgerStatementPage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 5: Perform a txn in the last 3 months which has CJE for an Expenditure Ledger account\n" +
                "e.g. Debit: Amount = 106.9HKD (B)\n" +
                "Credit: Amount = 106.4HKD (C)");
        double valueB = 100.9;
        double valueC = 100.9;
        String desExp = "TC_23700 Auto run for Expenditure transaction " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(valueB)
                .amountCredit(valueC)
                .remark(desExp)
                .transDate(fromDate)
                .transType(transType)
                .build();
        log("@Step 6: Click Submit button");
        log("@Step 7: Click Yes button in reminder dialog");
        journalEntriesPage.addTransaction(transaction, AccountType.LEDGER, AccountType.LEDGER, transaction.getRemark(), transaction.getTransDate(), transaction.getTransType(), true, true);
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Post-condition: authorize transaction");
        JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", debitExpAcc, transType, desExp);
        log("@Step 8: Go to General Reports >> System Monitoring page");
        log("@Step 9: Click Closing Journal Entries button");
        ClosingJournalEntriesPage closingJournalEntriesPage = journalEntriesPage.navigatePage(GENERAL_REPORTS, SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES, ClosingJournalEntriesPage.class);
        log("@Step 10: Click Perform CJE for month that perform txn at step #5\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Mont = 2023 - October");
        log("@Step 11: Click Yes button in confirm dialog");
        closingJournalEntriesPage.performCJE(KASTRAKI_LIMITED, "", true);
        log("@Step 12: Back to General Reports >> Ledger Statement page");
        ledgerStatementPage = closingJournalEntriesPage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log("@Step 13: Filter data as step #2");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, "", "Capital", ledgerGroup, fromDate, toDate, "After CJE");
        log("@Verify 1: Running Bal. in 'Amounts are shown in HKD' column = (A) + [(B) -(C)]");
        ledgerStatementPage.verifyRunningBalAfterTriggering(valueA, valueB, valueC);
    }


}