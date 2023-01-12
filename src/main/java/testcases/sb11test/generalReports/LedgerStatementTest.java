package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.popup.LedgerDetailPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class LedgerStatementTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String transType = "Others";
    String financialYear = "Year 2022-2023";
    String debitExpAcc = "AutoExpenditureDebit";
    String creditExpAcc = "AutoExpenditureCredit";
    String debitAstAcc = "AutoAssetDebit";
    String creditAstAcc = "AutoAssetCredit";
    String lgDebitCur = "AUD";
    String lgCreditCur = "AUD";
    String ledgerGroup = "Auto Ledger Group";
    String descExpenditure = "Expenditure Transaction " + DateUtils.getMilliSeconds();
    String descAsset = "Asset Transaction " + DateUtils.getMilliSeconds();

    @TestRails(id="841")
    @Test(groups = {"smoke"})
    public void Bet_Entry_TC841(){
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
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",ledgerGroup,"","");
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerStatementPage.verifyLedgerTrans(transaction, true, ledgerGroup);
        log("INFO: Executed completely");
    }

    @TestRails(id="842")
    @Test(groups = {"smoke"})
    public void Bet_Entry_TC842(){
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
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",ledgerGroup,"","");
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerStatementPage.verifyLedgerTrans(transaction, false, ledgerGroup);
        log("INFO: Executed completely");
    }

    @TestRails(id="843")
    @Test(groups = {"smoke"})
    public void Bet_Entry_TC843(){
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
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",ledgerGroup,"","");

        log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());

        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
        log("INFO: Executed completely");
    }

    @TestRails(id="844")
    @Test(groups = {"smoke"})
    public void Bet_Entry_TC844(){
        log("@title: Validate value calculated correctly for Ledger Type = Expenditure (Credit)");
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
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",ledgerGroup,"","");

        log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerCredit());
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
        log("INFO: Executed completely");
    }

    @TestRails(id="845")
    @Test(groups = {"smoke1"})
    public void Bet_Entry_TC845(){
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
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",ledgerGroup,"","");
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerStatementPage.verifyLedgerTrans(transaction, true, ledgerGroup);
        log("INFO: Executed completely");
    }

    @TestRails(id="846")
    @Test(groups = {"smoke1"})
    public void Bet_Entry_TC846(){
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
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",ledgerGroup,"","");
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerStatementPage.verifyLedgerTrans(transaction, false, ledgerGroup);
        log("INFO: Executed completely");
    }

    @TestRails(id="847")
    @Test(groups = {"smoke"})
    public void Bet_Entry_TC847(){
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
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",ledgerGroup,"","");

        log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerDetailPopup.verifyLedgerTrans(transaction,true,transaction.getRemark());
        log("INFO: Executed completely");
    }

    @TestRails(id="848")
    @Test(groups = {"smoke"})
    public void Bet_Entry_TC848(){
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
        journalEntriesPage.addLedgerTransaction(transaction,true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Asset",ledgerGroup,"","");

        log("@Step 7: Click on Ledger Name and observe value show in popup with Tnx Date = the date make transaction");
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(transaction.getLedgerDebit());
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerDetailPopup.verifyLedgerTrans(transaction,false,transaction.getRemark());
        log("INFO: Executed completely");
    }
}