package testcases.sb11test.accounting;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.ChartOfAccountPage;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;
import static common.SBPConstants.LEDGER_PARENT_NAME_ASSET;

public class JournalEntriesTest extends BaseCaseAQS {

    String transType = "Payment Other";
    String level = "Super";
    String remark = "Auto Testing Transaction " + DateUtils.getMilliSeconds() + ".Please ignore this, Thank you!";
    String clientCode = "QA Client (No.121 QA Client)";
    String bookieCode = "QA Bookie";
    String companyUnit = "Kastraki Limited";
    String financialYear = "Year 2023-2024";
    String lgExpenditureGroup = "QA Ledger Group Expenditure";
    String debitExpAcc = "AutoExpenditureDebit";
    String creditExpAcc = "AutoExpenditureCredit";
    String lgDebitCur = "AUD";
    String lgCreditCur = "AUD";
    String descExpenditure = "Expenditure Transaction " + DateUtils.getMilliSeconds();

    @TestRails(id="864")
    @Test(groups = {"smoke"})
    @Parameters({"bookieCode","bookieSuperMasterCode"})
    public void Journal_Entries_864(String bookieCode, String bookieSuperMasterCode){
        log("@title: Validate users can make transactions successfully between bookies");
        Transaction transaction = new Transaction.Builder()
                .bookieDebit(bookieCode)
                .level(level)
                .debitAccountCode(bookieSuperMasterCode)
                .bookieCredit(bookieCode)
                .level(level)
                .creditAccountCode(bookieSuperMasterCode)
                .amountDebit(1)
                .amountCredit(1)
                .remark(remark)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Access Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

        log("@Step 2: In Debit section > From dropdown, select 'Bookie'");
        log("@Step 3: Choose a bookie from Bookie dropdown (e.g. 'QA Bookie')");
        log("@Step 4: Choose Client (e.g. QA Client (No.121 Client), and Level is 'Super'");
        log("@Step 5: Input super account in precondition (e.g. SM-QA1-QA Test)");
        log("@Step 6: In Credit section, also select Bookie for 'To' dropdown");
        log("@Step 7: Other information is selected with same condition as From section (Level is 'Super', and Account is a super account link to agent-COM)");
        log("@Step 8: Add two accounts to the below tables, then input amount");
        log("@Step 9: Choose Transaction Date and Transaction Types, input Remark if any. Click Submit");
        journalEntriesPage.addTransaction(transaction,"Bookie","Bookie",transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

        log("Validate Message informs 'Transaction has been created.' is displayed");
        Assert.assertTrue(journalEntriesPage.messageSuccess.getText().contains("Transaction has been created"), "Failed! Message is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2160")
    public void Journal_Entries_TC_2160(){
        log("@title: Validate Journal Entries page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("Validate Journal Entries page is displayed with correctly title");
        Assert.assertTrue(journalEntriesPage.getTitlePage().contains(JOURNAL_ENTRIES), "Failed! Journal Entries page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2161")
    public void Journal_Entries_TC_2161(){
        log("@title: Validate UI on Journal Entries is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: From/To, Client, Bookie, Ledger, Currency, Level and Transaction Type");
        Assert.assertEquals(journalEntriesPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddDebitFrom.getOptions(),JournalEntries.TYPE_LIST,"Failed! Debit type list dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddCreditTo.getOptions(),JournalEntries.TYPE_LIST,"Failed! Credit type list dropdown is not displayed");
        Assert.assertTrue(journalEntriesPage.ddCreditLedger.getOptions().contains(clientCode),"Failed! Credit Client dropdown is not displayed");
        Assert.assertTrue(journalEntriesPage.ddDebitLedger.getOptions().contains(clientCode),"Failed! Debit Client dropdown is not displayed");
        Assert.assertTrue(journalEntriesPage.ddCreditBookieClient.getOptions().contains(bookieCode),"Failed! Credit Bookie dropdown is not displayed");
        Assert.assertTrue(journalEntriesPage.ddDebitBookieClient.getOptions().contains(bookieCode),"Failed! Debit Bookie dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddDebitCurrency.getOptions(),CURRENCY_LIST,"Failed! Credit Bookie dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddCreditCurrency.getOptions(),CURRENCY_LIST,"Failed! Debit Bookie dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddTransactionType.getOptions(),JournalEntries.TRANSACTION_TYPE_LIST,"Failed! Debit Bookie dropdown is not displayed");
        log("Textbox: Account, Remark");
        Assert.assertEquals(journalEntriesPage.lblAccountCredit,"Account","Failed! Account Debit textbox is not displayed");
        Assert.assertEquals(journalEntriesPage.lblAccountCredit,"Account","Failed! Account Credit textbox is not displayed");
        Assert.assertEquals(journalEntriesPage.lblRemark,"Remark","Failed! Remark textbox is not displayed");
        log("Datetimepicker: Transaction Date");
        Assert.assertTrue(journalEntriesPage.txtDateTrans.isDisplayed(),"Failed! Transaction Date is not displayed!");
        log("Button: Add (Debit), Add (Credit) and Submit button");
        Assert.assertEquals(journalEntriesPage.btnDebitAdd.getText(),"Add","Failed! Add Debit button is not displayed");
        Assert.assertEquals(journalEntriesPage.btnCreditAdd.getText(),"Add","Failed! Add Debit button is not displayed");
        Assert.assertEquals(journalEntriesPage.btnSubmit.getText(),"Submit","Failed! Submit button is not displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id="2162")
    @Test(groups = {"regression"})
    @Parameters({"bookieCode","bookieSuperMasterCode"})
    public void Journal_Entries_TC_2162(String bookieCode, String bookieSuperMasterCode){
        log("@title: Validate users can make transactions successfully between bookies");
        Transaction transaction = new Transaction.Builder()
                .bookieDebit(bookieCode)
                .level(level)
                .debitAccountCode(bookieSuperMasterCode)
                .bookieCredit(bookieCode)
                .level(level)
                .creditAccountCode(bookieSuperMasterCode)
                .amountDebit(1)
                .amountCredit(1)
                .remark(remark)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Access Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

        log("@Step 2: In Debit section > From dropdown, select 'Bookie'");
        log("@Step 3: Choose a bookie from Bookie dropdown (e.g. 'QA Bookie')");
        log("@Step 4: Choose Client (e.g. QA Client (No.121 Client), and Level is 'Super'");
        log("@Step 5: Input super account in precondition (e.g. SM-QA1-QA Test)");
        log("@Step 6: In Credit section, also select Bookie for 'To' dropdown");
        log("@Step 7: Other information is selected with same condition as From section (Level is 'Super', and Account is a super account link to agent-COM)");
        log("@Step 8: Add two accounts to the below tables, then input amount");
        log("@Step 9: Choose Transaction Date and Transaction Types, input Remark if any. Click Submit");
        journalEntriesPage.addTransaction(transaction,"Bookie","Bookie",transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

        log("Validate Message informs 'Transaction has been created.' is displayed");
        Assert.assertTrue(journalEntriesPage.messageSuccess.getText().contains("Transaction has been created"), "Failed! Message is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @TestRails(id="2163")
    @Test(groups = {"regression"})
    @Parameters({"clientCode","bookieSuperMasterCode"})
    public void Journal_Entries_TC_2163(String clientCode, String bookieSuperMasterCode){
        log("@title: Validate users can make transactions successfully between client");
        Transaction transaction = new Transaction.Builder()
                .clientDebit(clientCode)
                .level(level)
                .debitAccountCode(bookieSuperMasterCode)
                .clientCredit(clientCode)
                .level(level)
                .creditAccountCode(bookieSuperMasterCode)
                .amountDebit(1)
                .amountCredit(1)
                .remark(remark)
                .transDate("")
                .transType(transType)
                .build();
        log("@Step 1: Access Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);

        log("@Step 2: In Debit section > From dropdown, select 'Bookie'");
        log("@Step 3: Choose a bookie from Bookie dropdown (e.g. 'QA Bookie')");
        log("@Step 4: Choose Client (e.g. QA Client (No.121 Client), and Level is 'Super'");
        log("@Step 5: Input super account in precondition (e.g. SM-QA1-QA Test)");
        log("@Step 6: In Credit section, also select Bookie for 'To' dropdown");
        log("@Step 7: Other information is selected with same condition as From section (Level is 'Super', and Account is a super account link to agent-COM)");
        log("@Step 8: Add two accounts to the below tables, then input amount");
        log("@Step 9: Choose Transaction Date and Transaction Types, input Remark if any. Click Submit");
        journalEntriesPage.addTransaction(transaction,"Client","Client",transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);

        log("Validate Message informs 'Transaction has been created.' is displayed");
        Assert.assertTrue(journalEntriesPage.messageSuccess.getText().contains("Transaction has been created"), "Failed! Message is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @TestRails(id="2164")
    @Test(groups = {"regression"})
    @Parameters({"clientCode","bookieSuperMasterCode"})
    public void Journal_Entries_TC_2164(){
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
}
