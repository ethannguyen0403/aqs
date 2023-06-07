package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import testcases.BaseCaseAQS;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;
import utils.sb11.BookieInfoUtils;
import utils.sb11.ClientSystemUtils;

import static common.SBPConstants.*;

public class LedgerStatementTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String transType = "Others";
    String financialYear = "Year 2022-2023";
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
    String lgDebitCur = "AUD";
    String lgCreditCur = "AUD";
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
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",lgExpenditureGroup,"","");
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerStatementPage.verifyLedgerTrans(transaction, true, lgExpenditureGroup);
        log("INFO: Executed completely");
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
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",lgExpenditureGroup,"","");
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerStatementPage.verifyLedgerTrans(transaction, false, lgExpenditureGroup);
        log("INFO: Executed completely");
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
        log("@Step 4: Input Amount for Debit and Credit (should be same e.g 10)");
        log("@Step 5: Choose Transaction Type = any and click Submit");
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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

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
    }
}