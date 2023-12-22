package testcases.sb11test.accounting;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.systemmonitoring.ClosingJournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.popup.ConfirmPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import static common.SBPConstants.*;

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
    public void Journal_Entries_864(String bookieCode, String bookieSuperMasterCode) throws InterruptedException {
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
        //wait for message display
        Thread.sleep(1000);
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
    @TestRails(id = "4178")
    public void Journal_Entries_TC_4178(){
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
        Assert.assertTrue(journalEntriesPage.ddCreditBookieClient.getOptions().contains("[All]"),"Failed! Credit Bookie dropdown is not displayed");
        Assert.assertTrue(journalEntriesPage.ddDebitBookieClient.getOptions().contains("[All]"),"Failed! Debit Bookie dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddDebitLevel.getOptions(),JournalEntries.LEVEL_LIST,"Failed! Debit Level dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddCreditLevel.getOptions(),JournalEntries.LEVEL_LIST,"Failed! Credit Level dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddDebitCurrency.getOptions(),JournalEntries.CURRENCY_LIST,"Failed! Credit Bookie dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddCreditCurrency.getOptions(),JournalEntries.CURRENCY_LIST,"Failed! Debit Bookie dropdown is not displayed");
        Assert.assertEquals(journalEntriesPage.ddTransactionType.getOptions(),JournalEntries.TRANSACTION_TYPE_LIST,"Failed! Debit Bookie dropdown is not displayed");
        log("Textbox: Account, Remark");
        Assert.assertEquals(journalEntriesPage.lblAccountDebit.getText(),"Account","Failed! Account Debit textbox is not displayed");
        Assert.assertEquals(journalEntriesPage.lblAccountCredit.getText(),"Account","Failed! Account Credit textbox is not displayed");
        Assert.assertEquals(journalEntriesPage.lblRemark.getText(),"Remark","Failed! Remark textbox is not displayed");
        log("Datetimepicker: Transaction Date");
        Assert.assertTrue(journalEntriesPage.txtDateTrans.isDisplayed(),"Failed! Transaction Date is not displayed!");
        log("Button: Add (Debit), Add (Credit) and Submit button");
        Assert.assertEquals(journalEntriesPage.btnDebitAdd.getText(),"Add","Failed! Add Debit button is not displayed");
        Assert.assertEquals(journalEntriesPage.btnCreditAdd.getText(),"Add","Failed! Add Debit button is not displayed");
        Assert.assertEquals(journalEntriesPage.btnSubmit.getText(),"Submit","Failed! Submit button is not displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id="4179")
    @Test(groups = {"regression"})
    @Parameters({"bookieCode","bookieSuperMasterCode"})
    public void Journal_Entries_TC_4179(String bookieCode, String bookieSuperMasterCode) throws InterruptedException {
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
        //wait for showwing message
        Thread.sleep(1000);
        log("Validate Message informs 'Transaction has been created.' is displayed");
        Assert.assertTrue(journalEntriesPage.messageSuccess.getText().contains("Transaction has been created"), "Failed! Message is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @TestRails(id="4180")
    @Test(groups = {"regression"})
    @Parameters({"clientCode","bookieSuperMasterCode"})
    public void Journal_Entries_TC_4180(String clientCode, String bookieSuperMasterCode) throws InterruptedException {
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
        //wait for showwing message
        Thread.sleep(1000);
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
                .ledgerDebit("AutoExpenditureCredit1")
                .ledgerCredit("AutoCapitalDebit")
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
        ledgerStatementPage.showLedger(companyUnit,financialYear,"Expenditure",lgExpenditureGroup,"","","");
        log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
        log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
        ledgerStatementPage.verifyLedgerTrans(transaction, true, lgExpenditureGroup);
        log("INFO: Executed completely");
    }
    @TestRails(id="15749")
    @Test(groups = {"regression","2023.11.30"})
    public void Journal_Entries_TC_15749(){
        log("@title: Validate there is a 'Closing Journal Entries' link behind the 'Submit' button");
        log("@Pre-condition 1: 'Journal Entries' and 'Journal Entries Ledger' permissions are ON in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click Accounting >> Journal Entries menu");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Verify 1: There is a 'Closing Journal Entries' link behind the 'Submit' button");
        Assert.assertTrue(journalEntriesPage.lblClosingJournalEntries.isDisplayed(),"FAILED Closing Journal Entries button display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="15750")
    @Test(groups = {"regression","2023.11.30"})
    public void Journal_Entries_TC_15750(){
        log("@title: Validate user navigates to General Reports >> System Monitoring >> Closing Journal Entries page when clicked Closing Journal Entries link");
        log("@Pre-condition 1: Journal Entries' and 'Journal Entries Ledger' permissions are ON in any account");
        log("@Pre-condition 2: System Monitoring permission is ON");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click Accounting >> Journal Entries menu");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: Click 'Closing Journal Entries' link");
        ClosingJournalEntriesPage closingJournalEntriesPage = journalEntriesPage.openClosingJournalEntriesPage();
        log("@Verify 1: User will navigate to General Reports >> System Monitoring >> Closing Journal Entries page");
        Assert.assertEquals(closingJournalEntriesPage.tabActive.getText().trim(),CLOSING_JOURNAL_ENTRIES,"FAILED! Closing Journal Entries page display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="15751")
    @Test(groups = {"regression","2023.11.30"})
    public void Journal_Entries_TC_15751() throws InterruptedException {
        log("@title: Validate reminder displays if perform txn in the last 3 months that have CJE");
        log("@Pre-condition 1: Journal Entries' and 'Journal Entries Ledger' permissions are ON in any account");
        log("@Step 1: Click Accounting >> Journal Entries menu");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 2: Input all valid data into Debit and Credit section");
        String previousMonth = String.valueOf(DateUtils.getMonth(GMT_7)-1);
        String txtMonth = Month.of(DateUtils.getMonth(GMT_7)-1).getDisplayName(TextStyle.FULL.FULL, Locale.CANADA);
        String curYear = String.valueOf(DateUtils.getYear(GMT_7));
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit("AutoExpenditureCredit1")
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("15/"+previousMonth+"/"+curYear)
                .transType(transType)
                .build();
        log("@Step 3: Select txn date on the last 3 months has CJE (e.g. 20/10/2023) and Transaction Type");
        log("@Step 4: Click Submit button");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        ConfirmPopup confirmPopup = new ConfirmPopup();
        String actualMes = confirmPopup.getContentMessage();
        log("@Verify 1: A reminder as 'After submitting this transaction, you will need to perform Closing Journal Entries for <Month of the transaction date>. Please click on the 'Closing Journal Entries' link and perform.' will display");
        Assert.assertEquals(actualMes,String.format(JournalEntries.MES_IF_TXN_IN_3_LAST_MONTH,curYear,txtMonth),"FAILED! A reminder display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="15752")
    @Test(groups = {"regression","2023.11.30"})
    public void Journal_Entries_TC_15752() throws InterruptedException {
        log("@title: Validate reminder displays if perform txn before the last 3 months that have CJE");
        log("@Pre-condition 1: Journal Entries' and 'Journal Entries Ledger' permissions are ON in any account");
        log("@Step 1: Click Accounting >> Journal Entries menu");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 2: Input all valid data into Debit and Credit section");
        String previousMonth = String.valueOf(DateUtils.getMonth(GMT_7)-4);
        String txtMonth = Month.of(DateUtils.getMonth(GMT_7)-4).getDisplayName(TextStyle.FULL.FULL, Locale.CANADA);
        String curYear = String.valueOf(DateUtils.getYear(GMT_7));
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit("AutoExpenditureCredit1")
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("15/"+previousMonth+"/"+curYear)
                .transType(transType)
                .build();
        log("@Step 3: Select txn date before the last 3 months has CJE (e.g. 20/07/2023) and Transaction Type");
        log("@Step 4: Click Submit button");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        ConfirmPopup confirmPopup = new ConfirmPopup();
        String actualMes = confirmPopup.getContentMessage();
        log("@Verify 1: A reminder as 'After submitting this transaction, you will need to perform Closing Journal Entries for <Month of the transaction date>. Please contact Support Team to perform this CJE.' will display");
        Assert.assertEquals(actualMes,String.format(JournalEntries.MES_IF_TXN_BEFORE_3_LAST_MONTH,curYear,txtMonth),"FAILED! A reminder display incorrect");
        log("INFO: Executed completely");
    }

}
