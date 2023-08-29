package testcases.sb11test.accounting;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.accounting.JournalReportsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class JournalReportsTest extends BaseCaseAQS {
    String companyUnit = "Kastraki Limited";
    String clientCode = "QA Client Test";
    String dateType = "Created Date";
    String debitExpAcc = "AutoExpenditureDebit";
    String creditExpAcc = "AutoExpenditureCredit";
    String lgDebitCur = "AUD";
    String lgCreditCur = "AUD";
    String descExpenditure = "Expenditure Transaction " + DateUtils.getMilliSeconds();
    String transType = "Others";

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
    public void Journal_Reports_TC_002(){
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
    public void Journal_Reports_TC_003(){
        log("@title: Validate transaction in Journal Entries should display correctly on Journal Report");
        log("@Step 1: Login with valid account");
        log("@Step 2:  Already making a transaction on Journal Entries page");
        JournalEntriesPage journalEntriesPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES,JournalEntriesPage.class);
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
        journalEntriesPage.addTransaction(transaction,AccountType.LEDGER,AccountType.LEDGER,transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true);
        log("@Step 3: Access Accounting > Journal Reports");
        JournalReportsPage journalReportsPage = welcomePage.navigatePage(ACCOUNTING,JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 4: Filter with transaction at pre-condition");
        journalReportsPage.filterReports(companyUnit,dateType,"","","All","",transType,debitExpAcc);
        log("Validate transaction in Journal Entries should display correctly on Journal Report");
        journalReportsPage.verifyTxn(transaction,true);
        log("INFO: Executed completely");
    }
}
