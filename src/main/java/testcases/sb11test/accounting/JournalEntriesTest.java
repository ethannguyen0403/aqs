package testcases.sb11test.accounting;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.systemmonitoring.ClosingJournalEntriesPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.popup.ConfirmPopup;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.CompanySetUpUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import static common.SBPConstants.*;

public class JournalEntriesTest extends BaseCaseAQS {

    @TestRails(id="864")
    @Test(groups = {"smoke","ethan"})
    @Parameters({"bookieCode","bookieSuperMasterCode"})
    public void Journal_Entries_864(String bookieCode, String bookieSuperMasterCode) throws InterruptedException {
        String level = "Super";
        String remark = "Auto Testing Transaction C864 " + DateUtils.getMilliSeconds() + ".Please ignore this, Thank you!";
        String transType = "Payment Other";
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
        journalEntriesPage.waitSpinnerDisappeared();
        log("@Step 2: In Debit section > From dropdown, select 'Bookie'");
        log("@Step 3: Choose a bookie from Bookie dropdown (e.g. 'QA Bookie')");
        log("@Step 4: Choose Client (e.g. QA Client (No.121 Client), and Level is 'Super'");
        log("@Step 5: Input super account in precondition (e.g. SM-QA1-QA Test)");
        log("@Step 6: In Credit section, also select Bookie for 'To' dropdown");
        log("@Step 7: Other information is selected with same condition as From section (Level is 'Super', and Account is a super account link to agent-COM)");
        log("@Step 8: Add two accounts to the below tables, then input amount");
        log("@Step 9: Choose Transaction Date and Transaction Types, input Remark if any. Click Submit");
        journalEntriesPage.addTransaction(transaction,"Bookie","Bookie",transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true,false);
        //wait for message display
        Thread.sleep(1000);
        String transMes = journalEntriesPage.appArlertControl.getSuscessMessage();
        log("Validate Message informs 'Transaction has been created.' is displayed");
        Assert.assertEquals(transMes, JournalEntries.MES_TRANS_HAS_BEEN_CREATED, "Failed! Message is displayed incorrectly!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "4177")
    public void Journal_Entries_TC_4177(){
        log("@title: Validate Journal Entries page is displayed when navigate");
        log("@Step 1: Access Soccer > Journal Entries");
        JournalEntriesPage page = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("Verify 1: Validate Journal Entries page is displayed with correctly title");
        Assert.assertTrue(page.getTitlePage().contains(JOURNAL_ENTRIES),String.format("FAILED! Page Title is incorrect displayed. Actual: %s, expected: %s",page.getTitlePage(),JOURNAL_ENTRIES));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","ethan2.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "4178")
    public void Journal_Entries_TC_4178(String clientCode){
        log("@title: Validate UI on Journal Entries is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        journalEntriesPage.waitSpinnerDisappeared();
        log("Validate UI Info display correctly");
        journalEntriesPage.verifyUI(clientCode);
        log("INFO: Executed completely");
    }

    @TestRails(id="4179")
    @Test(groups = {"regression","ethan3.0"})
    @Parameters({"bookieCode","bookieSuperMasterCode"})
    public void Journal_Entries_TC_4179(String bookieCode, String bookieSuperMasterCode) throws InterruptedException {
        String level = "Super";
        String remark = "Auto Testing Transaction C4179 " + DateUtils.getMilliSeconds() + ".Please ignore this, Thank you!";
        String transType = "Payment Other";
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
        journalEntriesPage.addTransaction(transaction,"Bookie","Bookie",transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true,false);
        //wait for showwing message
        Thread.sleep(1000);
        log("Validate Message informs 'Transaction has been created.' is displayed");
        Assert.assertTrue(journalEntriesPage.messageSuccess.getText().contains("Transaction has been created"), "Failed! Message is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @TestRails(id="4180")
    @Test(groups = {"regression","ethan3.0"})
    @Parameters({"clientCode","bookieSuperMasterCode"})
    public void Journal_Entries_TC_4180(String clientCode, String bookieSuperMasterCode) throws InterruptedException {
        String level = "Super";
        String remark = "Auto Testing Transaction C4180 " + DateUtils.getMilliSeconds() + ".Please ignore this, Thank you!";
        String transType = "Payment Other";
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
        journalEntriesPage.addTransaction(transaction,"Client","Client",transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true,false);
        //wait for showwing message
        Thread.sleep(1000);
        log("Validate Message informs 'Transaction has been created.' is displayed");
        Assert.assertTrue(journalEntriesPage.messageSuccess.getText().contains("Transaction has been created"), "Failed! Message is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @TestRails(id="2164")
    @Test(groups = {"regression","ethan"})
    @Parameters({"companyName"})
    public void Journal_Entries_TC_2164(String companyName) throws IOException {
        String lgDebitCur = "HKD";
        String lgCreditCur = "HKD";
        String descExpenditure = "Expenditure Transaction C2164" + DateUtils.getMilliSeconds();
        String transType = "Payment Other";
        String lgExpenditureGroup = "QA Ledger Group Expenditure";
        log("@title: Validate transaction Debit of Ledger Type = Expenditure");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Journal Entries");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit("AutoExpenditureDebit - 011.000.000.000")
                .ledgerCredit("AutoExpenditureCredit - 010.000.000.000")
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
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true,false);
        try {
            log("@Step 6: Navigate to General > Ledger Statement and search the transaction of ledger at precondition");
            LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
            ledgerStatementPage.waitSpinnerDisappeared();
            ledgerStatementPage.showLedger(companyName,FINANCIAL_YEAR,"Expenditure",lgExpenditureGroup,"","","");
            log("@Verify 1: Original Currency: Ledger column with Ledger Group and Ledger Name, CUR column with ledger currency, Credit/Debit column = value inputted at step 5 in blue, Running Bal and Running Bal CT displayed");
            log("@Verify 2: Amounts in GBP (conver to GBP): Credit/Debit column =  value inputted at step 5 in blue , Running Bal get value from Original Currency");
            ledgerStatementPage.verifyLedgerTrans(transaction, true, lgExpenditureGroup);
        } finally {
            log("@Post-condition: Revert transaction amount for Credit/Debit Expenditure Ledger in case throws exceptions");
            String currentDate = DateUtils.getDate(0, "yyyy-MM-dd", "GMT +7");
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME).ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_EXPENDITURE_CREDIT_NAME).ledgerDebitNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                    .amountDebit(1).amountCredit(1)
                    .remark("Automation Testing Transaction Ledger: Post-condition for txn")
                    .transDate(currentDate)
                    .transType("Tax Rebate").build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_GROUP_NAME_EXPENDITURE,"");
        }
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
        String transType = "Payment Other";
        String creditExpAcc = "AutoExpenditureCredit - 010.000.000.000";
        String lgDebitCur = "AUD";
        String lgCreditCur = "AUD";
        String descExpenditure = "Expenditure Transaction C15751 " + DateUtils.getMilliSeconds();
        log("@title: Validate reminder displays if perform txn in the last 3 months that have CJE");
        log("@Pre-condition 1: Journal Entries' and 'Journal Entries Ledger' permissions are ON in any account");
        log("@Step 1: Click Accounting >> Journal Entries menu");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 2: Input all valid data into Debit and Credit section");
        String date = journalEntriesPage.getDayOfMonth(15,-1,"MM/yyyy");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit("AutoCreditExpenditure - 012.000.000.000")
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate(date)
                .transType(transType)
                .build();
        log("@Step 3: Select txn date on the last 3 months has CJE (e.g. 20/10/2023) and Transaction Type");
        log("@Step 4: Click Submit button");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true,false);
        ConfirmPopup confirmPopup = new ConfirmPopup();
        String actualMes = confirmPopup.getContentMessage();
        log("@Verify 1: A reminder as 'After submitting this transaction, you will need to perform Closing Journal Entries for <Month of the transaction date>. Please click on the 'Closing Journal Entries' link and perform.' will display");
        String txtMonth = Month.of(Integer.parseInt(date.split("/")[1])).getDisplayName(TextStyle.FULL.FULL, Locale.CANADA);
        Assert.assertEquals(actualMes,String.format(JournalEntries.MES_IF_TXN_IN_3_LAST_MONTH,date.split("/")[2],txtMonth),"FAILED! A reminder display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="15752")
    @Test(groups = {"regression","2023.11.30"})
    public void Journal_Entries_TC_15752() throws InterruptedException {
        String transType = "Payment Other";
        String creditExpAcc = "AutoExpenditureCredit - 010.000.000.000";
        String lgDebitCur = "AUD";
        String lgCreditCur = "AUD";
        String descExpenditure = "Expenditure Transaction C15751 " + DateUtils.getMilliSeconds();
        log("@title: Validate reminder displays if perform txn before the last 3 months that have CJE");
        log("@Pre-condition 1: Journal Entries' and 'Journal Entries Ledger' permissions are ON in any account");
        log("@Step 1: Click Accounting >> Journal Entries menu");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        log("@Step 2: Input all valid data into Debit and Credit section");
        String date = journalEntriesPage.getDayOfMonth(15,-4,"MM/yyyy");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit("AutoCreditExpenditure - 012.000.000.000")
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate(date)
                .transType(transType)
                .build();
        log("@Step 3: Select txn date before the last 3 months has CJE (e.g. 20/07/2023) and Transaction Type");
        log("@Step 4: Click Submit button");
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true,false);
        ConfirmPopup confirmPopup = new ConfirmPopup();
        String actualMes = confirmPopup.getContentMessage();
        log("@Verify 1: A reminder as 'After submitting this transaction, you will need to perform Closing Journal Entries for <Month of the transaction date>. Please contact Support Team to perform this CJE.' will display");
        String txtMonth = Month.of(Integer.parseInt(date.split("/")[1])).getDisplayName(TextStyle.FULL.FULL, Locale.CANADA);
        Assert.assertEquals(actualMes,String.format(JournalEntries.MES_IF_TXN_BEFORE_3_LAST_MONTH,date.split("/")[2],txtMonth),"FAILED! A reminder display incorrect");
        log("INFO: Executed completely");
    }

}
