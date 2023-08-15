package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import pages.sb11.master.AddressBookPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class LedgerStatementTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String transType = "Payment Other";
    String financialYear = "Year 2023-2024";
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
    public void Ledger_Statement_TC841(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditExpAcc)
                .ledgerCredit(debitExpAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",lgExpenditureGroup,"","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (convert to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgExpenditureGroup);
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="842")
    @Test(groups = {"smoke"})
    public void Ledger_Statement_TC842(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditExpAcc)
                .ledgerCredit(debitExpAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",lgExpenditureGroup,"","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgExpenditureGroup);
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="843")
    @Test(groups = {"smoke"})
    public void Ledger_Statement_TC843(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditExpAcc)
                .ledgerCredit(debitExpAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",lgExpenditureGroup,"","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            log("INFO: Executed completely");
            ledgerDetailPopup.closePopup();
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="844")
    @Test(groups = {"smoke"})
    public void Ledger_Statement_TC844(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditExpAcc)
                .ledgerCredit(debitExpAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",lgExpenditureGroup,"","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerCredit());
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            log("INFO: Executed completely");
            ledgerDetailPopup.closePopup();
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="845")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC845(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditAstAcc)
                .ledgerCredit(debitAstAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",lgAssetGroup,"","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgAssetGroup);
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="846")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC846(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditAstAcc)
                .ledgerCredit(debitAstAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",lgAssetGroup,"","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgAssetGroup);
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="847")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC847(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditAstAcc)
                .ledgerCredit(debitAstAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",lgAssetGroup,"","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="848")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC848(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditAstAcc)
                .ledgerCredit(debitAstAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",lgAssetGroup,"","");

            log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Asset Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="849")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC849(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditLibAcc)
                .ledgerCredit(debitLibAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Liability",lgLiabilityGroup,"","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgLiabilityGroup);
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="850")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC850(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditLibAcc)
                .ledgerCredit(debitLibAcc)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Liability",lgLiabilityGroup,"","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, lgLiabilityGroup);
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Liability Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="853")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC853(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditCapitalAcc)
                .ledgerCredit(debitCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Capital",lgCapitalGroup,"","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="854")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC854(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditCapitalAcc)
                .ledgerCredit(debitCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Capital",lgCapitalGroup,"","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="855")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC855(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditCapitalAcc)
                .ledgerCredit(debitCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Capital",lgCapitalGroup,"","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to showy");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="856")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC856(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditCapitalAcc)
                .ledgerCredit(debitCapitalAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Capital",lgCapitalGroup,"","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Capital Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="857")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC857(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditIncomeAcc)
                .ledgerCredit(debitIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Income",lgIncomeGroup,"","");

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, false, transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="858")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC858(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditIncomeAcc)
                .ledgerCredit(debitIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Income",lgIncomeGroup,"","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Result page shows with 2 parts:\n" +
                    "Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in red, Running Bal and Running Bal CT displayed\n" +
                    "Amounts in GBP (conver to GBP): Credit/Debit column = value inputted at step 5 in red, Running Bal get value from Original Currency");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());

            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }

    @TestRails(id="859")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC859(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditIncomeAcc)
                .ledgerCredit(debitIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Income",lgIncomeGroup,"","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Debit show value = 0 in black, Credit show value = 10 in blue, Running Bal = Opening Balance (+Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show");
            ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());

            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }

    }
    @TestRails(id="860")
    @Test(groups = {"smoke_qc"})
    public void Ledger_Statement_TC860(){
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
        Transaction transactionPost = new Transaction.Builder()
                .ledgerDebit(creditIncomeAcc)
                .ledgerCredit(debitIncomeAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
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
            ledgerStatementPage.showLedger(companyUnit,financialYear,"Income",lgIncomeGroup,"","");
            LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

            log("@Verify 1: Original Currency: Debit show value = 10 in red, Credit show value = 0 in black, Running Bal = Opening Balance (+ Credit - Debit) value\n" +
                    "Total column is sum of records\n" +
                    "Amounts in GBP will get value from Original Currency then convert to GBP to show\n" +
                    "\n");
            ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
            log("INFO: Executed completely");
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger back to = 0");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);

        } catch (Exception | AssertionError e) {
            log("@Post-condition: Revert transaction amount for Credit/Debit Income Ledger in case throws exceptions");
            welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
            journalEntriesPage.addTransaction(transactionPost,AccountType.LEDGER,AccountType.LEDGER,transactionPost.getRemark(),transactionPost.getTransDate(),transactionPost.getTransType(),true);
            throw new Error("FAILED Test!", e);
        }
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2769")
    public void Ledger_Statement_TC_001(){
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
    public void Ledger_Statement_TC_002(){
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
        log("Header is " + ledgerStatementPage.tbLedger.getHeaderNameOfRows());
        Assert.assertEquals(ledgerStatementPage.tbLedger.getHeaderNameOfRows(),LedgerStatement.TABLE_HEADER,"Failed! Ledger Statement table is displayed with incorrectly header column");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2770")
    public void Ledger_Statement_TC_003(){
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "ledger-statement.xlsx";
        log("@title: Validate can export Ledger Statement to Excel file successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 3: Click Export To Excel");
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Income",lgIncomeGroup,"","");
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

    @Test(groups = {"regression1"})
    @TestRails(id = "2768")
    public void Ledger_Statement_TC_004(){
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "download.pdf";
        log("@title: Validate can export Ledger Statement to PDF file successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 3: Click Export To PDF");
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Income",lgIncomeGroup,"","");
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