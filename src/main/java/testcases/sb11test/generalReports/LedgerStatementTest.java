package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import pages.sb11.master.AddressBookPage;
import testcases.BaseCaseAQS;
import utils.sb11.AccountSearchUtils;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.ClientSystemUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class LedgerStatementTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String transType = "Payment Other";
    String debitExpAcc = "AutoExpenditureDebit";
    String creditExpAcc = "AutoExpenditureCredit";
    String debitAstAcc = "AutoAssetDebit";
    String creditAstAcc = "AutoAssetCredit";
    String debitLibAcc = "AutoLiabilityDebit";
    String creditLibAcc = "AutoLiabilityCredit";
    String debitCapitalAcc = "AutoCapitalDebit";
    String creditCapitalAcc = "AutoCapitalCredit";
    String debitIncomeAcc = "AutoIncomeDebit";
    String creditIncomeAcc = "AutoIncomeCredit";
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
        try {
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
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
            String[] ledgerDebitAccountPart = LEDGER_EXPENDITURE_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_EXPENDITURE_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
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
            String[] ledgerDebitAccountPart = LEDGER_EXPENDITURE_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_EXPENDITURE_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Expenditure",lgExpenditureGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");

        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_EXPENDITURE_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_EXPENDITURE_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 5: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Expenditure",lgExpenditureGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerCredit());
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_EXPENDITURE_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_EXPENDITURE_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 5: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Asset",lgAssetGroup,"","","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgAssetGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_ASSET_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_ASSET_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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

    @TestRails(id="846")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC846() throws IOException {
        log("@title: Validate transaction Credit of Ledger Type = Asset");
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
        try {
            log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 5: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Asset",lgAssetGroup,"","","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgAssetGroup);
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_ASSET_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_ASSET_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 5: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Asset",lgAssetGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_ASSET_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_ASSET_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 5: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Asset",lgAssetGroup,"","","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_ASSET_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_ASSET_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 5: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
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
            String[] ledgerDebitAccountPart = LEDGER_LIABILITY_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_LIABILITY_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 5: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
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
            String[] ledgerDebitAccountPart = LEDGER_LIABILITY_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_LIABILITY_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
            log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_CAPITAL_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_CAPITAL_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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

        try {
            log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
            log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_CAPITAL_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_CAPITAL_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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

        try {
            log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to showy");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_CAPITAL_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_CAPITAL_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
            log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Capital",lgCapitalGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_CAPITAL_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_CAPITAL_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_INCOME_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_INCOME_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
            log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Observe value show on page");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_INCOME_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_INCOME_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 3: In Credit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 4: In Debit, select any available destination (Client, Bookie, Ledger) then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction\n");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            ledgerDetailPopup.closePopup();
            log("INFO: Executed completely");
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            String[] ledgerDebitAccountPart = LEDGER_INCOME_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_INCOME_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        try {
            log("@Step 3: In Credit, select any available source (Client, Bookie, Ledger) then click Add");
            log("@Step 4: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
            log("@Step 5: Input Amount for Debit and Credit (should be same e.g 10)");
            log("@Step 6: Choose Transaction Type = any and click Submit");
            journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

            log("Step 7: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);

            log("Step 8: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            ledgerStatementPage.showLedger(companyUnit,FINANCIAL_YEAR,"Income",lgIncomeGroup,"","","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

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
            String[] ledgerDebitAccountPart = LEDGER_INCOME_CREDIT_ACC.split("-");
            String[] ledgerCreditAccountPart = LEDGER_INCOME_DEBIT_ACC.split("-");
            String ledgerCreditAccountName = ledgerCreditAccountPart[1].replaceAll("\\s+","");
            String ledgerCreditAccountNumber = ledgerCreditAccountPart[0].replaceAll("\\s+","");
            String ledgerDebitAccountName = ledgerDebitAccountPart[1].replaceAll("\\s+","");
            String ledgerDebitAccountNumber = ledgerDebitAccountPart[0].replaceAll("\\s+","");
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
        int month = DateUtils.getMonth("GMT +7") - 1;
        int year = Integer.valueOf(FINANCIAL_YEAR.replaceAll("[a-zA-z]", "").trim().split("-")[0]);
        String fromDate = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String toDate = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 3: Filter ledger statement with data: ");
        ledgerStatementPage.showLedger(COMPANY_UNIT, FINANCIAL_YEAR, "Income", lgIncomeGroup, fromDate, toDate, REPORT_TYPE.get(1));
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
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "download.pdf";
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


}