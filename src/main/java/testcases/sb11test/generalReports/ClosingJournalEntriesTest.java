package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.accounting.CompanySetupPage;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static common.SBPConstants.*;

public class ClosingJournalEntriesTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2023.12.29"})
    @TestRails(id = "9841")
    @Parameters({"password", "userNameOneRole"})
    public void Closing_Journal_Entries_9841(String password, String userNameOneRole) throws Exception {
        log("@title: Validate could not access 'Closing Journal Entries' page if not activate System Monitoring permission");
        log("@Pre-condition: System Monitoring' permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Expand General Reports menu");
        log("@Verify 1: System Monitoring' menu is hidden so that user could not access 'Closing Journal Entries' page");
        List<String> lstSubMenu = welcomePage.headerMenuControl.getListSubMenu();
        Assert.assertFalse(lstSubMenu.contains(SYSTEM_MONITORING),"FAILED! System Monitoring page displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9842")
    public void Closing_Journal_Entries_9842() {
        log("@title: Validate can access 'Closing Journal Entries' page if activate System Monitoring permission");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports menu");
        log("@Step 3: Click 'System Monitoring' menu");
        log("@Step 4: Select 'Closing Journal Entries' tab");
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Verify 1: System Monitoring' menu is hidden so that user could not access 'Closing Journal Entries' page");
        Assert.assertTrue(page.isClosingTabBehindCorrectBets());
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9843")
    public void Closing_Journal_Entries_9843() {
        log("@title: Validate Company Unit filter will list all existing companies in the system ");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Accounting >> Company Set-up page");
        CompanySetupPage companySetupPage = welcomePage.navigatePage(ACCOUNTING,COMPANY_SETUP,CompanySetupPage.class);
        log("@Step 3: Get all existing company names");
        List<String> lstCompany = companySetupPage.tblCompany.getColumn(companySetupPage.tblCompany.getColumnIndexByName("Company Name"),10,true);
        log("@Step 4: Click General Reports >> System Monitoring menu");
        SystemMonitoringPage systemMonitoringPage = companySetupPage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class);
        log("@Step 5: Click 'Closing Journal Entries' button");
        log("@Step 6: Expand Company Unit dropdown list");
        ClosingJournalEntriesPage page = systemMonitoringPage.goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        List<String> lstCUAc = page.ddCompany.getOptions();
        log("@Verify 1: All existing company names at step #3 will display");
        Assert.assertEquals(lstCUAc,lstCompany,"FAILED! List Company names display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9844")
    public void Closing_Journal_Entries_9844() {
        log("@title: Validate 'Month' filter will displays the 3 latest moths (that already had CJE) with the format of 'Year - Month'");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Expand Month dropdown list");
        log("@Verify 1: All existing company names at step #3 will display");
        page.verifyddMonthOption();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9845")
    public void Closing_Journal_Entries_9845() throws IOException {
        log("@title: Validate can perform CJE for the selected month for sub-accounts of the selected Company Unit by clicking 'Perform CJE' button");
        log("@Pre-condition 1: System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: There are some transactions perform after CJE");
        String numberLeder = "010.000.000.000";
        String ledger = "AutoCreditExpenditure";
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
        String month = s.format(new Date(cal.getTimeInMillis()));
        String transType = "Payment Other";
        String remark = "Auto Testing Closing Journal Entries. Please ignore this, Thank you!";
        String detailType = "QA Ledger Group Expenditure";

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(detailType);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, detailType);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledger);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledger);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledger);
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledger).ledgerCreditNumber(numberLeder)
                .ledgerDebit(ledger).ledgerDebitNumber(numberLeder)
                .amountDebit(1).amountCredit(1)
                .transDate(month+"-12")
                .remark(remark)
                .transType(transType).build();

        TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);

        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Select any Month in dropdown list (e.g. 2023-Octorber) of any company (e.g. Kastraki Limited)");
        log("@Step 5: Click Perform CJE button");
        log("@Step 6: Click Yes button");
        page.filter(COMPANY_UNIT,"",true);
        String sucMes = page.appArlertControl.getSuscessMessage();
        log("@Verify 1: The successful message 'Closing Journal Entry for <selected Month> (e.g. October) is completed.' displays");
        s = new SimpleDateFormat("MMMM");
        String nextMonth = s.format(new Date(cal.getTimeInMillis()));
        Assert.assertEquals(sucMes, String.format(ClosingJournalEntries.SUCCESS_MES_LAST_MONTH,nextMonth),"FAILED! Success message display incorrect");
        log("@Step 7: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = page.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 8: Filter data of detail type of accounts at precondition from 1/10/2023 To 31/10/2023");
        String firstDay = DateUtils.getFirstDateOfMonth(Integer.valueOf(month.split("-")[0]),Integer.valueOf(month.split("-")[1]),"dd/MM/yyyy");
        String lastDay = DateUtils.getLastDateOfMonth(Integer.valueOf(month.split("-")[0]),Integer.valueOf(month.split("-")[1]),"dd/MM/yyyy");

        ledgerStatementPage.showLedger(COMPANY_UNIT,"","Expenditure",detailType,firstDay,lastDay,"After CJE");
        log("@Step 9: Click on account at precondition (e.g. 000.000.001.003 - RB Feed Delete Debit1)");
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(ledger);
        log("@Verify 2: Running Bal. of Closing Journal 2023-OCTOBER is = 0.00");
        Assert.assertEquals(ledgerDetailPopup.getClosingValue(),"0.00","FAILED! Running Bal. of Closing Journal display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9846")
    public void Closing_Journal_Entries_9846() throws IOException {
        log("@title: Validate can perform CJE for the selected month for sub-accounts of the selected Company Unit by clicking 'Perform CJE' button");
        log("@Pre-condition 1: System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: There are some transactions perform after CJE");
        String numberLeder = "010.000.000.000";
        String ledger = "AutoCreditExpenditure";
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
        String month = s.format(new Date(cal.getTimeInMillis()));

        String transType = "Payment Other";
        String remark = "Auto Testing Closing Journal Entries. Please ignore this, Thank you!";
        String detailType = "QA Ledger Group Expenditure";

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(detailType);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, detailType);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledger);
        String ledgerCreditAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledger);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledger);
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(ledger).ledgerCreditNumber(numberLeder)
                .ledgerDebit(ledger).ledgerDebitNumber(numberLeder)
                .amountDebit(1).amountCredit(1)
                .transDate(month+"-12")
                .remark(remark)
                .transType(transType).build();

        TransactionUtils.addLedgerTxn(transaction,ledgerDebitAccountId,ledgerCreditAccountId,ledgerType);

        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Select Month before perform txn at precondition in dropdown list of any company (e.g. Kastraki Limited)");
        log("@Step 5: Click Perform CJE button then Yes in confirmation dialog");
        String ddMonth = page.ddMonth.getOptions().get(1).trim();
        page.filter(COMPANY_UNIT,ddMonth,true);
        log("@Step 6: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = page.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 7: Filter data of detail type of accounts at precondition from 1/9/2023 To 30/09/2023");
        String firstDay = DateUtils.getFirstDateOfMonth(Integer.valueOf(month.split("-")[0]),Integer.valueOf(month.split("-")[1]),"dd/MM/yyyy");
        String lastDay = DateUtils.getLastDateOfMonth(Integer.valueOf(month.split("-")[0]),Integer.valueOf(month.split("-")[1]),"dd/MM/yyyy");
        ledgerStatementPage.showLedger(COMPANY_UNIT,"","Expenditure",detailType,firstDay,lastDay,"After CJE");
        log("@Step 8: Click on account at precondition");
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(ledger);
        log("@Verify 1: Running Bal. of Closing Journal 2023-SEPTEMBER is = 0.00");
        Assert.assertEquals(ledgerDetailPopup.getClosingValue(),"0.00","FAILED! Running Bal. of Closing Journal display incorrect");
        ledgerDetailPopup.closePopup();
        log("@Step 9: Filter data of detail type of accounts at precondition from 1/10/2023 To 31/10/2023");
        cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        month = s.format(new Date(cal.getTimeInMillis()));
        firstDay = DateUtils.getFirstDateOfMonth(Integer.valueOf(month.split("-")[0]),Integer.valueOf(month.split("-")[1]),"dd/MM/yyyy");
        lastDay = DateUtils.getLastDateOfMonth(Integer.valueOf(month.split("-")[0]),Integer.valueOf(month.split("-")[1]),"dd/MM/yyyy");
        ledgerStatementPage.showLedger(COMPANY_UNIT,"","Expenditure",detailType,firstDay,lastDay,"Before CJE");
        log("@Step 10: Click on account at precondition");
        ledgerDetailPopup = ledgerStatementPage.openLedgerDetail(ledger);
        log("@Verify 2: Running Bal. of Closing Journal 2023-OCTOBER is <> 0.00");
        Assert.assertFalse(ledgerDetailPopup.getClosingValue().contains("0.00"),"FAILED! Running Bal. of Closing Journal display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "15742")
    public void Closing_Journal_Entries_15742() throws InterruptedException {
        log("@title: Validate can perform CJE for the selected month for sub-accounts of the selected Company Unit by clicking 'Perform CJE' button");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat s = new SimpleDateFormat("MMMM");
        String month = s.format(new Date(cal.getTimeInMillis()));
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Select any month is not the latest month");
        page.ddMonth.selectByIndex(1);
        log("@Step 5: Click Perform CJE button");
        page.btnPerformCJE.click();
        ConfirmPopup confirmPopup = new ConfirmPopup();
        String mesRemind = confirmPopup.getContentMessage();
        log("@Verify 1: The reminder message 'You will need to perform CJE for <Month(s) after the selected Month> to have the correct balances.' will display");
        Assert.assertEquals(mesRemind, String.format(ClosingJournalEntries.MES_REMINDER_BEFORE_2_MONTH,month),"FAILED! Reminder message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "15743")
    public void Closing_Journal_Entries_15743() throws InterruptedException {
        log("@title: Validate only the confirmation message displays if perform CJE for the lasted month");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat s = new SimpleDateFormat("MMMM - yyyy");
        String month = s.format(new Date(cal.getTimeInMillis()));
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Select any month is not the latest month");
        page.ddMonth.selectByIndex(0);
        log("@Step 5: Click Perform CJE button");
        page.btnPerformCJE.click();
        ConfirmPopup confirmPopup = new ConfirmPopup();
        String mesRemind = confirmPopup.getContentMessage();
        log("@Verify 1: The confirmation as 'Are you sure to perform Closing Journal Entry for <Month - Year>?' will display");
        Assert.assertEquals(mesRemind, String.format(ClosingJournalEntries.MES_REMINDER_BEFORE_1_MONTH,month),"FAILED! Reminder message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "15744")
    @Parameters({"username"})
    public void Closing_Journal_Entries_15744(String username) {
        log("@title: Validate no record is added in History/Log table if click No button in confirm dialog");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat s = new SimpleDateFormat("yyyy - MMMM");
        String month = s.format(new Date(cal.getTimeInMillis()));
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Click Perform CJE button");
        page.btnPerformCJE.click();
        log("@Step 5: Click No button in Confirm dialog");
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.btnNo.click();
        String performedDate = DateUtils.getDate(0,"dd/MM/yyy HH:mm",GMT_7);
        log("@Verify 1: Confirm dialog will close and No record will add in the History/Log table");
        Assert.assertFalse(page.isRecordHistoryDisplay(month,username,performedDate),"FAILED! The History/Log table display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "15745")
    @Parameters({"username"})
    public void Closing_Journal_Entries_15745(String username) {
        log("@title: Validate new record is added in History/Log table if click Yes button in confirm dialog");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat s = new SimpleDateFormat("yyyy - MMMM");
        String month = s.format(new Date(cal.getTimeInMillis()));
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Select a Company Unit");
        page.btnPerformCJE.click();
        log("@Step 5: Click Perform CJE button");
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.btnYes.click();
        String performedDate = DateUtils.getDate(0,"dd/MM/yyy HH:mm","GMT+8");
        log("@Verify 1: Confirm dialog will close and No record will add in the History/Log table");
        Assert.assertTrue(page.isRecordHistoryDisplay(month,username,performedDate),"FAILED! The History/Log table display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "15746")
    public void Closing_Journal_Entries_15746() {
        log("@title: Validate the success message displayed at the bottom-right corner when finished");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        SimpleDateFormat s = new SimpleDateFormat("yyyy - MMMM");
        String month = s.format(new Date(cal.getTimeInMillis()));
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        log("@Step 4: Select a Company Unit and Month");
        log("@Step 5: Click Perform CJE button");
        log("@Step 6: Click Yes button in Confirm dialog");
        page.filter(COMPANY_UNIT,month,true);
        String sucMes = page.appArlertControl.getSuscessMessage();
        log("@Verify 1: A success message will display at the bottom-right corner as 'Closing Journal Entry of <selected Month>is completed.");
        Assert.assertEquals(sucMes, String.format(ClosingJournalEntries.SUCCESS_MES_LAST_MONTH,month.split(" - ")[1]),"FAILED! The History/Log table display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "15748")
    public void Closing_Journal_Entries_15748() {
        log("@title: Validate History/Log table is sorted by Performed Date ascendingly");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        SimpleDateFormat s = new SimpleDateFormat("yyyy - MMMM");
        String month = s.format(new Date(cal.getTimeInMillis()));
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        page.ddMonth.selectByVisibleText(month);
        log("@Verify 1: History/Log table is sorted by Performed Date ascendingly");
        page.verifyPerformedDateSort();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "15762")
    public void Closing_Journal_Entries_15762() {
        log("@title: Validate History/Log table shows details logs on all created CJEs of the filtered company unit and month");
        log("@Pre-condition: System Monitoring' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click General Reports >> System Monitoring menu");
        log("@Step 3: Click 'Closing Journal Entries' button");
        log("@Step 4: Select company and month at precondition");
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        SimpleDateFormat s = new SimpleDateFormat("yyyy - MMMM");
        String month = s.format(new Date(cal.getTimeInMillis()));
        ClosingJournalEntriesPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING,SystemMonitoringPage.class).goToTabName(CLOSING_JOURNAL_ENTRIES,ClosingJournalEntriesPage.class);
        page.ddMonth.selectByVisibleText(month);
        log("@Verify 1: Only history/log of selected company (e.g. IB 01) and month (e.g. 2023-August) should display");
        page.verifyMonthColumnDisplay(month);
        log("INFO: Executed completely");
    }
}
