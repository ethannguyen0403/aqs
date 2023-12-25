package testcases.sb11test.accounting;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.accounting.JournalReportsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class JournalReportsTest extends BaseCaseAQS {
    String companyUnit = "Kastraki Limited";
    String clientCode = "QA Client (No.121 QA Client)";
    String dateType = "Created Date";
    String debitExpAcc = "AutoExpenditureDebit";
    String creditExpAcc = "AutoExpenditureCredit";
    String lgDebitCur = "HKD";
    String lgCreditCur = "HKD";
    String descExpenditure = "Expenditure Transaction " + DateUtils.getMilliSeconds();
    String transType = "Payment Other";

    @Test(groups = {"regression"})
    @TestRails(id = "2165")
    public void Journal_Report_TC_001(){
        log("@title: Validate Journal Report page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Report");
        JournalReportsPage journalReportsPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_REPORTS,JournalReportsPage.class);
        log("Validate Journal Report page is displayed with correctly title");
        Assert.assertTrue(journalReportsPage.getTitlePage().contains(JOURNAL_REPORTS), "Failed! Journal Reports page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2166")
    public void Journal_Reports_TC_2166(){
        log("@title: Validate UI on Journal Reports is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Reports");
        JournalReportsPage journalReportsPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_REPORTS,JournalReportsPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Date Type, Account Type, Client/Bookie/Ledger, Transaction Type");
        Assert.assertEquals(journalReportsPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(journalReportsPage.ddpDateType.getOptions(),JournalReports.DATE_TYPE,"Failed! Date Type dropdown is not displayed");
        Assert.assertEquals(journalReportsPage.ddpAccountType.getOptions(),JournalReports.ACCOUNT_TYPE,"Failed! Account Type dropdown is not displayed");
        Assert.assertTrue(journalReportsPage.ddpClientBookieLedger.getOptions().contains(clientCode),"Failed! Client dropdown is not displayed");
        Assert.assertEquals(journalReportsPage.ddpTransactionType.getOptions(),JournalReports.TRANSACTION_TYPE_LIST,"Failed! Transaction Type dropdown is not displayed");
        log("Textbox: Account Name");
        Assert.assertEquals(journalReportsPage.lblAccountName.getText(),"Account Name","Failed! Account Name textbox is not displayed!");
        log("Datetimepicker: From Date, To Date");
        Assert.assertEquals(journalReportsPage.lblFromDate.getText(),"From Date", "Failed! From Date datetimepicker is not displayed!");
        Assert.assertEquals(journalReportsPage.lblToDate.getText(),"To Date", "Failed! To Date datetimepicker is not displayed!");
        log("Button: Search button");
        Assert.assertTrue(journalReportsPage.btnSearch.isDisplayed(),"Failed! Search button is not displayed!");
        log("Journal Reports should displayed with correctly header name");
        Assert.assertEquals(journalReportsPage.tbJournalReport.getHeaderNameOfRows(),JournalReports.TABLE_HEADER,"Failed! Journal Report table is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2167")
    public void Journal_Reports_TC_2167(){
        log("@title: Validate transaction in Journal Entries should display correctly on Journal Report");
        log("@Step 1: Login with valid account");
        log("@Step 2:  Already making a transaction on Journal Entries page");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit("AutoExpenditureCredit1")
                .ledgerCredit(creditExpAcc)
                .ledgerDebitCur(lgDebitCur)
                .ledgerCreditCur(lgCreditCur)
                .amountDebit(1)
                .amountCredit(1)
                .remark(descExpenditure)
                .transDate("")
                .transType(transType)
                .build();
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        log("@Step 3: Access Accounting > Journal Reports");
        JournalReportsPage journalReportsPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 4: Filter with transaction at pre-condition");
        journalReportsPage.filterReports(companyUnit,dateType,"","","All","",transType,"AutoExpenditureCredit1");
        log("Verify 1: Validate transaction in Journal Entries should display correctly on Journal Report");
        journalReportsPage.verifyTxn(transaction,true);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "2161")
    public void Journal_Report_TC_2161(){
        log("@title: Validate there is the log of the Closing Journal ");
        String date = "31/07/2023";
        String year = date.split("/")[2];
        String month = DateUtils.formatDate(date.split("/")[1], "MM", "MMMM").toUpperCase();
        String logClosing = String.format("Closing Journal %s-%s", year, month);
        log("@Precondition: Has closing journal at end of a month: " + month);
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Report");
        JournalReportsPage journalReportsPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 4: Filter the closing journal at end of a month");
        journalReportsPage.filterReports(COMPANY_UNIT,"Transaction Date",date,date,"All","","Contra CUR","");
        List<String> descriptList = journalReportsPage.tbJournalReport.getColumn(journalReportsPage.colDes, false);
        log("Verify 1: There is the log of the Closing Journal");
        Assert.assertTrue(descriptList.contains(logClosing), "FAILED! No log of the Closing Journal");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "2162")
    public void Journal_Report_TC_2162(){
        log("@title: Validate the Total Debit = Total Credit after closing journal");
        String date = "31/07/2023";
        String year = date.split("/")[2];
        String month = DateUtils.formatDate(date.split("/")[1], "MM", "MMMM").toUpperCase();
        String logClosing = String.format("Closing Journal %s-%s", year, month);
        log("@Precondition: Has closing journal at end of a month: " + month);
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Report");
        JournalReportsPage journalReportsPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 4: Filter the closing journal at end of a month");
        journalReportsPage.filterReports(COMPANY_UNIT,"Transaction Date",date,date,"All","","Contra CUR","");
        String totalDebit = journalReportsPage.getTotalDebitOrCredit(logClosing, true);
        String totalCredit = journalReportsPage.getTotalDebitOrCredit(logClosing, false);
        log("Verify 1: The Total Debit = Total Credit");
        Assert.assertEquals(totalDebit, totalCredit, "FAILED! Credit and Debit are not equal with each other");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "2163")
    public void Journal_Report_TC_2163(){
        log("@title: Validate the correct PL for Current Year - HKD - 302.000.001.000 displays");
        String date = "31/07/2023";
        String year = date.split("/")[2];
        String month = DateUtils.formatDate(date.split("/")[1], "MM", "MMMM").toUpperCase();
        String logClosing = String.format("Closing Journal %s-%s", year, month);
        log("@Precondition: Has closing journal at end of a month: " + month);
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Journal Report");
        JournalReportsPage journalReportsPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 4: Filter the closing journal at end of a month");
        journalReportsPage.filterReports(COMPANY_UNIT_LIST.get(3),"Transaction Date",date,date,"All","","Contra CUR","");
        List<String> debitIncome = journalReportsPage.getDeOrCreValueByAccountType("", logClosing, "Ledger - Income", true);
        List<String> creditIncome = journalReportsPage.getDeOrCreValueByAccountType("", logClosing, "Ledger - Income", false);
        List<String> debitExpense = journalReportsPage.getDeOrCreValueByAccountType("", logClosing, "Ledger - Expenditure", true);
        List<String> creditExpense = journalReportsPage.getDeOrCreValueByAccountType("", logClosing, "Ledger - Expenditure", false);
        log("@Step 4: Sum all Debit of Ledger - Income and Ledger - Expenditure (A)\n" +
                "Sum all Credit of Ledger - Income and Ledger - Expenditure (B)\n" +
                "Get (A) - (B)");
        log("Verify 1: If the Total Credit (B) < Total Debit (A) => the transaction for Profit (Loss) >> Current Year will be Credit (= value at step #5)\n" +
                "If the Total Debit (A) < Total Credit (B) => the transaction for Profit (Loss) >> Current Year will be Debit (= value at step #5)\n" +
                "\n");
        journalReportsPage.verifyPLCurrentYearIsCorrect(journalReportsPage.calTotalFromList(debitIncome, debitExpense), journalReportsPage.calTotalFromList(creditIncome, creditExpense), logClosing);
        log("INFO: Executed completely");
    }
}
