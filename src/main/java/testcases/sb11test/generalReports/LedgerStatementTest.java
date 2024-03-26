package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import com.paltech.utils.FileUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.SystemMonitoringPage;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import pages.sb11.generalReports.systemmonitoring.ClosingJournalEntriesPage;
import pages.sb11.popup.ConfirmPopup;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class LedgerStatementTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String transType = "Payment Other";
    String debitExpAcc = "AutoExpenditureDebit - 011.000.000.000";
    String creditExpAcc = "AutoExpenditureCredit - 010.000.000.000";
    String debitAstAcc = "AutoAssetDebit - 055.000.000.000";
    String creditAstAcc = "AutoAssetCredit - 050.000.000.000";
    String debitLibAcc = "AutoLiabilityDebit - 044.000.000.000";
    String creditLibAcc = "AutoLiabilityCredit - 040.000.000.000";
    String debitCapitalAcc = "AutoCapitalDebit - 033.000.000.000";
    String creditCapitalAcc = "AutoCapitalCredit - 030.000.000.000";
    String debitIncomeAcc = "AutoIncomeDebit - 002.200.000.000";
    String creditIncomeAcc = "AutoIncomeCredit - 002.000.000.000";
    String lgDebitCur = "HKD";
    String lgCreditCur = "HKD";
    String lgExpenditureGroup = "QA Ledger Group Expenditure";
    String lgAssetGroup = "QA Ledger Group Asset";
    String lgLiabilityGroup = "QA Ledger Group Liability";
    String lgIncomeGroup = "QA Ledger Group Income";
    String lgCapitalGroup = "QA Ledger Group Capital";
    String descExpenditure = "Expenditure Transaction " + DateUtils.getMilliSeconds();
    String descAsset = "Asset Transaction " + DateUtils.getMilliSeconds();
    String descLiability = "Liability Transaction " + DateUtils.getMilliSeconds();

    @TestRails(id="841")
    @Test(groups = {"smoke"})
    public void Ledger_Statement_TC841() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Expenditure");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Expenditure",lgExpenditureGroup,"","","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (convert to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgExpenditureGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_EXPENDITURE);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_EXPENDITURE);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="842")
    @Test(groups = {"smoke"})
    public void Ledger_Statement_TC842() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Expenditure");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Expenditure",lgExpenditureGroup,"","","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgExpenditureGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_EXPENDITURE);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_EXPENDITURE);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="843")
    @Test(groups = {"smoke"})
    @Parameters({"clientCode"})
    public void Ledger_Statement_TC843() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Expenditure (Debit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Expenditure",lgExpenditureGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit().split(" - ")[0]);

            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");

        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_EXPENDITURE);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_EXPENDITURE);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="844")
    @Test(groups = {"smoke"})
    public void Ledger_Statement_TC844() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Expenditure (Credit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Expenditure",lgExpenditureGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerCredit().split(" - ")[0]);
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_EXPENDITURE_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_EXPENDITURE);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_EXPENDITURE);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="845")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC845() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Asset");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        String date = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate(date)
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit, FINANCIAL_YEAR, "Asset", lgAssetGroup, date, date, "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgAssetGroup);
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_ASSET);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_ASSET);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="846")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC846() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Asset");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        String date = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate(date)
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit, FINANCIAL_YEAR, "Asset", lgAssetGroup, date, date, "");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgAssetGroup);
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_ASSET);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_ASSET);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="847")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC847() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Asset (Debit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");

        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate("")
                .transType(transType)
                .build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Asset",lgAssetGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit().split(" - ")[0]);
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_ASSET);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_ASSET);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="848")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC848() throws IOException {
        log("@title: Validate value calculated correctly for Ledger Type = Asset (Credit)");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");

        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitAstAcc)
                .ledgerCredit(creditAstAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descAsset)
                .transDate("")
                .transType(transType)
                .build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Asset",lgAssetGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerCredit().split(" - ")[0]);
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_ASSET_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_ASSET_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_ASSET);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_ASSET);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="849")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC849() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Liability");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder().ledgerDebit(debitLibAcc).ledgerCredit(creditLibAcc).ledgerDebitCur(lgDebitCur).ledgerCreditCur(lgCreditCur)
                .amountDebit(1).amountCredit(1).remark(descLiability).transDate("").transType(transType).build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Liability",lgLiabilityGroup,"","","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgLiabilityGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = LEDGER_LIABILITY_DEBIT_NAME;
            String ledgerCreditAccountNumber = LEDGER_LIABILITY_DEBIT_NUMBER;
            String ledgerDebitAccountName = LEDGER_LIABILITY_CREDIT_NAME;
            String ledgerDebitAccountNumber = LEDGER_LIABILITY_CREDIT_NUMBER;
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_LIABILITY,LEDGER_GROUP_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,"");
        }

    }

    @TestRails(id="850")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC850() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Liability");
        log("@Step 1: Login to SB11 site");
        log("@Step Precondition: Get Credit/Debit amount on Ledger Statement page");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitLibAcc)
                .ledgerCredit(creditLibAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descLiability)
                .transDate("")
                .transType(transType)
                .build();

        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Liability",lgLiabilityGroup,"","","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgLiabilityGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_LIABILITY_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_LIABILITY_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_LIABILITY_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_LIABILITY_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_LIABILITY);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_LIABILITY);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }
    //TODO: implement case 851 852
    @TestRails(id="853")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC853() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Capital");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Capital transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgCapitalGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_CAPITAL);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_CAPITAL);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="854")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC854() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Capital");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Capital transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgCapitalGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_CAPITAL);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_CAPITAL);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="855")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC855() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Capital (Credit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Capital transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerCredit().split(" - ")[0]);

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to showy");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_CAPITAL);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_CAPITAL);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);        }

    }

    @TestRails(id="856")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC856() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Capital (Debit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Capital");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitCapitalAcc)
                .ledgerCredit(creditCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Capital transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit().split(" - ")[0]);

            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_CAPITAL_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_CAPITAL_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_CAPITAL);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_CAPITAL);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="857")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC857() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Income");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Income transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgIncomeGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_INCOME);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_INCOME);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }

    @TestRails(id="858")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC858() throws IOException {
        log("@title: Validate transaction Debit of Ledger Type = Income");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Income transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit().split(" - ")[0]);

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_INCOME);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_INCOME);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);        }

    }

    @TestRails(id="859")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC859() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Income (Credit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Income transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction\n");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerCredit().split(" - ")[0]);

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_INCOME);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_INCOME);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }

    }
    @TestRails(id="860")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC860() throws IOException {
        log("@title:Validate value calculated correctly for Ledger Type = Income (Debit)");
        log("@Step Precondition: Already have ledger account created in Accounting > Chart of Account with Account Type = Income");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark( "Auto run for Income transaction " + DateUtils.getMilliSeconds())
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
        log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 6: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        try {
            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit().split(" - ")[0]);

            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show\n" +
                    "\n");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String ledgerCreditAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerCreditAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_DEBIT_ACC,true);
            String ledgerDebitAccountName = ChartOfAccountUtils.getAccountName(LEDGER_INCOME_CREDIT_ACC,true);
            String ledgerDebitAccountNumber = ChartOfAccountUtils.getAccountNumber(LEDGER_INCOME_CREDIT_ACC,true);
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(ledgerCreditAccountName).ledgerCreditNumber(ledgerCreditAccountNumber)
                    .ledgerDebit(ledgerDebitAccountName).ledgerDebitNumber(ledgerDebitAccountNumber)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();

            String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(LEDGER_GROUP_NAME_INCOME);
            String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, LEDGER_GROUP_NAME_INCOME);
            String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerDebitAccountName);
            String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerCreditAccountName);
            String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerDebitAccountName);
            TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);
        }
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "2160")
    public void Ledger_Statement_2160(){
        log("@title: Validate at the end of each month all Income and Expenditure sub-accounts (ledgers) must have balance = 0");
        String monthYear = DateUtils.getMonthYear(GMT_7,-1,"MM/yyyy");
        String fromDate = DateUtils.getFirstDateOfMonth(Integer.valueOf(monthYear.split("/")[1]),Integer.valueOf(monthYear.split("/")[0]),"dd/MM/yyyy");
        String toDate = DateUtils.getLastDateOfMonth(Integer.valueOf(monthYear.split("/")[1]),Integer.valueOf(monthYear.split("/")[0]),"dd/MM/yyyy");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 3: Filter ledger statement with data: ");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Income", lgIncomeGroup, fromDate, toDate, REPORT_TYPE.get(1));
        log("@Verify 1: Validate running Bal of Total in HKD at Amounts are shown in HKD section should be 0");
        Assert.assertEquals(ledgerStatementPage.getTotalAmountInOriginCurrency("Total in HKD"), "0.00", "FAILED! Running Bal of Total in HKD is not equal to 0");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2769")
    public void Ledger_Statement_2769(){
        log("@title: Validate Ledger Statement page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("Validate Ledger Statement page is displayed with correctly title");
        Assert.assertTrue(ledgerStatementPage.getTitlePage().contains(LEDGER_STATEMENT),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2770")
    public void Ledger_Statement_2770(){
        String detailType = "000.000.001.000 - QA Ledger Group Income";
        log("@title: Validate UI on Ledger Statement is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Financial Year, Account Type, Detail Type");
        Assert.assertEquals(ledgerStatementPage.ddCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(ledgerStatementPage.ddFinancialYear.getOptions(),FINANCIAL_YEAR_LIST,"Failed! Financial year dropdown is not displayed!");
        Assert.assertEquals(ledgerStatementPage.ddLedgerName.getOptions(),LedgerStatement.ACCOUNT_TYPE,"Failed! Account Type dropdown is not displayed!");
        Assert.assertTrue(ledgerStatementPage.ddLedgerGroup.getOptions().contains(detailType),"Failed! Detail Type dropdown is not displayed!");
        log("Datetime picker: From Date, To Date");
        Assert.assertEquals(ledgerStatementPage.lblFromDate.getText(),"From Date","Failed! From Date datetimepicker is not displayed!");
        Assert.assertEquals(ledgerStatementPage.lblToDate.getText(),"To Date","Failed! To Date datetimepicker is not displayed!");
        log("Button: Show, Export To Excel, Export to PDF");
        Assert.assertEquals(ledgerStatementPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        Assert.assertEquals(ledgerStatementPage.btnExportToExcel.getText(),"Export To Excel","Failed! Export To Excel button is not displayed!");
        Assert.assertEquals(ledgerStatementPage.btnExportToPDF.getText(),"Export To PDF","Failed! Export To PDF button is not displayed!");
        log("Validate Ledger Statement table is displayed with correctly header column");
        ledgerStatementPage.btnShow.click();
        ledgerStatementPage.waitSpinnerDisappeared();
        Assert.assertEquals(ledgerStatementPage.tbLedger.getHeaderNameOfRows(),LedgerStatement.TABLE_HEADER,"Failed! Ledger Statement table is displayed with incorrectly header column");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2772")
    public void Ledger_Statement_2772(){
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "ledger-statement.xlsx";
        log("@title: Validate can export Ledger Statement to Excel file successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 3: Click Export To Excel");
        ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
        ledgerStatementPage.exportExcel();
        log("Validate can export Ledger Statement to Excel file successfully");
        Assert.assertTrue(FileUtils.doesFileNameExist(dowloadPath), "Failed to download Expected document");
        log("@Post-condition: delete download file");
        try {
            FileUtils.removeFile(dowloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2768")
    public void Ledger_Statement_2768(){
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "ledger-statement.pdf";
        log("@title: Validate can export Ledger Statement to PDF file successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 3: Click Export To PDF");
        ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
        ledgerStatementPage.exportPDF();
        log("Validate can export Ledger Statement to Excel file successfully");
        Assert.assertTrue(FileUtils.doesFileNameExist(dowloadPath), "Failed to download Expected document");
        log("@Post-condition: delete download file");
        try {
            FileUtils.removeFile(dowloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
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
        String fromDate = DateUtils.getFirstDateOfMonth(year,month-1,"dd/MM/yyyy");
        String toDate = DateUtils.getLastDateOfMonth(year,month-1,"dd/MM/yyyy");
        log("@Step 1: Go to General Reports >> Ledger Statement page");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("Step 2: Filter data of 302.000.000.000 - Retained Earnings in testing month\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Financial Year = 2023-2024\n" +
                "Account Type = Capital\n" +
                "Detail Type = 302.000.000.000 - Retained Earnings\n" +
                "Report = After CJE");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,"","Capital",ledgerGroup,fromDate,toDate,"After CJE");
        log("@Step 3: Get Running Bal. of account 302.000.001.000 - PL for Current Year - HKD at 'Amounts are shown in HKD' column (A)");
        double valueA = ledgerStatementPage.getValueAmount(ledgerName,ledgerStatementPage.colRunBalGBP);
        log("@Step 4: Go to Accounting >> Journal Entries");
        JournalEntriesPage journalEntriesPage = ledgerStatementPage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 5: Perform a txn in the last 3 months which has CJE for an Income Ledger account\n" +
                "e.g. Debit: Amount = 106.9HKD (B)\n" +
                "Credit: Amount = 106.9HKD (C)");
        double valueB = 100.9;
        double valueC = 100.9;
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitIncomeAcc)
                .ledgerCredit(creditIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(valueB)
                .amountCredit(valueC)
                .remark( "Auto run for Income transaction " + DateUtils.getMilliSeconds())
                .transDate(fromDate)
                .transType(transType)
                .build();
        log("@Step 6: Click Submit button");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        log("@Step 7: Click Yes button in reminder dialog");
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.btnOK.click();
        log("@Step 8: Go to General Reports >> System Monitoring page");
        log("@Step 9: Click Closing Journal Entries button");
        ClosingJournalEntriesPage closingJournalEntriesPage = journalEntriesPage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 10: Click Perform CJE for month that perform txn at step #5\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Mont = 2023 - October");
        log("@Step 11: Click Yes button in confirm dialog");
        closingJournalEntriesPage.performCJE(KASTRAKI_LIMITED,"",true);
        log("@Step 12: Back to General Reports >> Ledger Statement page");
        ledgerStatementPage = closingJournalEntriesPage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 13: Filter data as step #2");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,"","Capital",ledgerGroup,fromDate,toDate,"After CJE");
        log("@Verify 1: Running Bal. in 'Amounts are shown in HKD' column = (A) + [(B) -(C)]");
        ledgerStatementPage.verifyRunningBalAfterTriggering(valueA,valueB,valueC);
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
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
        String fromDate = DateUtils.getFirstDateOfMonth(year,month-1,"dd/MM/yyyy");
        String toDate = DateUtils.getLastDateOfMonth(year,month-1,"dd/MM/yyyy");
        log("@Step 1: Go to General Reports >> Ledger Statement page");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("Step 2: Filter data of 302.000.000.000 - Retained Earnings in testing month\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Financial Year = 2023-2024\n" +
                "Account Type = Capital\n" +
                "Detail Type = 302.000.000.000 - Retained Earnings\n" +
                "Report = After CJE");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,"","Capital",ledgerGroup,fromDate,toDate,"After CJE");
        log("@Step 3: Get Running Bal. of account 302.000.001.000 - PL for Current Year - HKD at 'Amounts are shown in HKD' column (A)");
        double valueA = ledgerStatementPage.getValueAmount(ledgerName,ledgerStatementPage.colRunBalGBP);
        log("@Step 4: Go to Accounting >> Journal Entries");
        JournalEntriesPage journalEntriesPage = ledgerStatementPage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 5: Perform a txn in the last 3 months which has CJE for an Expenditure Ledger account\n" +
                "e.g. Debit: Amount = 106.9HKD (B)\n" +
                "Credit: Amount = 106.4HKD (C)");
        double valueB = 100.9;
        double valueC = 100.9;
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit(debitExpAcc)
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(valueB)
                .amountCredit(valueC)
                .remark( "Auto run for Expenditure transaction " + DateUtils.getMilliSeconds())
                .transDate(fromDate)
                .transType(transType)
                .build();
        log("@Step 6: Click Submit button");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        log("@Step 7: Click Yes button in reminder dialog");
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.btnOK.click();
        log("@Step 8: Go to General Reports >> System Monitoring page");
        log("@Step 9: Click Closing Journal Entries button");
        ClosingJournalEntriesPage closingJournalEntriesPage = journalEntriesPage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 10: Click Perform CJE for month that perform txn at step #5\n" +
                "e.g. Company Unit = Kastraki Limited\n" +
                "Mont = 2023 - October");
        log("@Step 11: Click Yes button in confirm dialog");
        closingJournalEntriesPage.performCJE(KASTRAKI_LIMITED,"",true);
        log("@Step 12: Back to General Reports >> Ledger Statement page");
        ledgerStatementPage = closingJournalEntriesPage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 13: Filter data as step #2");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,"","Capital",ledgerGroup,fromDate,toDate,"After CJE");
        log("@Verify 1: Running Bal. in 'Amounts are shown in HKD' column = (A) + [(B) -(C)]");
        ledgerStatementPage.verifyRunningBalAfterTriggering(valueA,valueB,valueC);
    }


}