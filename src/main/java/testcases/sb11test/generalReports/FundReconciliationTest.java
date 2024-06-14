package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Transaction;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.SystemMonitoringPage;
import pages.sb11.generalReports.systemmonitoring.FundReconciliationPage;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.awt.*;
import java.io.IOException;

import static common.SBPConstants.*;


public class FundReconciliationTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2024.V.2.0","ethan2.0"})
    @TestRails(id = "17670")
    public void Fund_Reconciliation_TC_17670() throws IOException {
        log("@title: Validate Transaction Details of sub-accounts displays properly");
        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
        String ledgerName = "PC Kam HKD";
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        String parentName = "Pretty Cash";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 3: Filter transaction of");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
        log("@Verify 1: Debit/Credit will get from the debit/credit amounts of each transaction and putting in the corresponding column");
        Assert.assertEquals(page.getValueByDesc(desc,"Debit"),String.format("%.2f",valueDebit),"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan2.0"})
    @TestRails(id = "17671")
    public void Fund_Reconciliation_TC_17671() throws IOException {
        log("@title: Validate the Description displays correctly");
        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
        String ledgerName = "PC Kam HKD";
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        String parentName = "Pretty Cash";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 3: Filter transaction of");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
        log("@Verify 1: Description field will displays the Memo/Description of each transaction accordingly");
        Assert.assertEquals(page.getValueByDesc(desc,"Description"),desc,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan2.0"})
    @TestRails(id = "17672")
    @Parameters({"username"})
    public void Fund_Reconciliation_TC_17672(String username) throws IOException {
        log("@title: Validate the Processed By displays properly");
        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
        String ledgerName = "PC Kam HKD";
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        String parentName = "Pretty Cash";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 3: Filter transaction of");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
        log("@Step 4: Tick in the Confirm checkbox");
        log("@Step 5: Click Yes button");
        page.tickConfirmAuthorise(desc,"Confirm");
        log("@Verify 1: Processed By will display the user who ticked on the Confirm checkbox. Will display blank if the checkbox is unticked");
        Assert.assertEquals(page.getValueByDesc(desc,"Processed By"),username,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan2.0"})
    @TestRails(id = "17673")
    public void Fund_Reconciliation_TC_17673() throws IOException {
        log("@title: Validate the CUR displays correctly");
        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
        String curEx = "HKD";
        String ledgerName = "PC Kam HKD";
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        String parentName = "Pretty Cash";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 3: Filter transaction of");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
        log("@Verify 1: CUR will display the currency of the sub-account");
        Assert.assertEquals(page.getValueByDesc(desc,"CUR"),curEx,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan2.0"})
    @TestRails(id = "17674")
    public void Fund_Reconciliation_TC_17674() throws IOException {
        log("@title: Validate the Running Balance displays correctly");
        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
        String ledgerName = "PC Kam HKD";
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        String parentName = "Pretty Cash";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
        log("@Pre-condition 3: Get Running Bal in Ledger Statement");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        String detailType = "101.000.000.000 - Cash";
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,"","Asset",detailType,transDate,transDate,"");
        double runningBal = ledgerStatementPage.getValueAmount(ledgerNumber+" - "+ledgerName,ledgerStatementPage.colRunBalGBP);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
        FundReconciliationPage page = ledgerStatementPage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 3: Filter transaction of");
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
        log("@Verify 1: Running Balance will displays correctly:\n"+
                ". The 'Opening Balance' row: shows the Opening Balance of the sub-account in the filtered date (same as in Ledger Statement)\n" +
                "  . The transaction rows: is Opening balance +/- debit/credit amounts, according to the logic of Account Type");
        double openBalance = Double.valueOf(page.getValueByDesc("Opening Balance","Running Balance"));
        Assert.assertEquals(openBalance,runningBal,"FAILED! Opening Balance displays incorrect");
        String runningEx = String.format("%.2f",openBalance + Double.valueOf(page.getValueByDesc(desc,"Debit")));
        Assert.assertEquals(page.getValueByDesc(desc,"Running Balance"),runningEx,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan2.0"})
    @TestRails(id = "17680")
    @Parameters({"username"})
    public void Fund_Reconciliation_TC_17680(String username) throws IOException {
        log("@title: Validate the Authorised By displays correctly");
        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
        String ledgerName = "PC Kam HKD";
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        String parentName = "Pretty Cash";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 3: Filter transaction of");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
        log("@Step 4: Tick in the Authorise checkbox");
        log("@Step 5: Click Yes button");
        page.tickConfirmAuthorise(desc,"Authorise");
        log("@Verify 1: Authorised By will display the user who ticked on the Authorise checkbox of each transaction.");
        Assert.assertEquals(page.getValueByDesc(desc,"Authorised By"),username,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan2.0"})
    @TestRails(id = "17681")
    public void Fund_Reconciliation_TC_17681() throws IOException {
        log("@title: Validate the Closing Balance row displays correct value");
        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
        String ledgerName = "PC Kam HKD";
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        String parentName = "Pretty Cash";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 3: Filter transaction of");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
        log("@Verify 1: Closing Balance row will sums of all rows of Debit/Credit amounts\n" +
                "For Running Balance, it's the final amount of the last record.");
        String sumDebit = page.getSumDebitCredit("Debit");
        String sumCredit = page.getSumDebitCredit("Credit");
        Assert.assertEquals(page.getValueByDesc("Closing Balance","Debit"),sumDebit,"FAILED! Sum of Debit displays incorrect");
        Assert.assertEquals(page.getValueByDesc("Closing Balance","Credit"),sumCredit,"FAILED! Sum of Credit displays incorrect");
        Assert.assertEquals(page.tblSubAcc.getColumn(page.tblSubAcc.getColumnIndexByName("Running Balance"),50,true).get(page.tblSubAcc.getNumberOfRows(false,true)-2),
                page.getValueByDesc("Closing Balance","Running Balance"),"FAILED! Running Balance displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
    @TestRails(id = "17682")
    @Parameters({"username"})
    public void Fund_Reconciliation_TC_17682(String username) throws IOException {
        //TODO having improvement AQS-3982
        log("@title: Validate the Today's Settlement in HKD row displays correct value");
        Assert.assertTrue(false,"FAILED! There is an improvement AQS-3982");
//        log("@Pre-condition 1: Fund Reconciliation' permission is ON for any account");
//        log("@Pre-condition 2: Having some transaction of Fund Reconciliation for detail types");
//        String ledgerName = "PC Kam HKD";
//        String ledgerNumber = "101.000.001.000";
//        String groupName = "Cash";
//        String parentName = "Pretty Cash";
//        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
//        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
//        double valueDebit= 2.00;
//        Transaction transactionPost = new Transaction.Builder()
//                .ledgerCredit(ledgerName).ledgerCreditNumber(ledgerNumber)
//                .ledgerDebit(ledgerName).ledgerDebitNumber(ledgerNumber)
//                .amountDebit(valueDebit).amountCredit(valueDebit)
//                .remark(desc)
//                .transDate(currentDate)
//                .transType("Payment Other").build();
//        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,parentName,parentName,"");
//        log("@Step 1: Login by account at precondition");
//        log("@Step 2: Go to General Reports >> System Monitoring >>Fund Reconciliation");
//        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
//        log("@Step 3: Filter transaction of");
//        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
//        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,transDate,transDate);
//        log("@Step 4: Tick in the Authorise checkbox");
//        log("@Step 5: Click Yes button");
//        page.tickConfirmAuthorise(desc,"Authorise");
//        log("@Verify 1: Today's Settlement in HKD row will sums up all the amounts of the authorized transactions and then converts to HKD using rate of the filtered date.");
//        String todaySettleEx = page.getSumAuthorizedTrans(username);
//        Assert.assertEquals(page.tblTodaySettle.getControlOfCell(1,page.tblSubAcc.getColumnIndexByName("Debit"),1,"span").getText(),
//                todaySettleEx,"FAILED! Today's Settlement value display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29463")
    public void Fund_Reconciliation_TC_29463() {
        log("@title: Validate Sub-account lists displays properly");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: 'Having a Detail Type which have some sub-accounts");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Select Detail Type at precondition");
        log("@Step 6: Expand Sub-account dropdown list, observe");
        log("@Verify 1: Lists all sub-accounts at precondition properly");
        page.verifySubaccListDisplay();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29464")
    public void Fund_Reconciliation_TC_29464() {
        log("@title: Validate Sub-account is ordering by Sub-account Number ascendingly");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: 'Having a Detail Type which have some sub-accounts");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Select Detail Type at precondition");
        log("@Step 6: Expand Sub-account dropdown list");
        log("@Verify 1: Sub-account Number is ordering ascendingly");
        page.verifySubaccListSorted();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29465")
    public void Fund_Reconciliation_TC_29465() {
        log("@title: Validate there is a filtering by Beginning Date/Ending Date");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Verify 1: There is a filtering by Beginning Date/Ending Date");
        Assert.assertTrue(page.txtBeginDate.isEnabled(),"FAILED! Beginning Date text box display incorrect");
        Assert.assertTrue(page.txtEndingDate.isEnabled(),"FAILED! Ending Date text box display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29466")
    public void Fund_Reconciliation_TC_29466() {
        log("@title: Validate is able to filter transaction within 1 month");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: Having a Details type which has some sub-accounts");
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        log("@Pre-condition 4: Sub-accounts have transaction within 1 month");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Select Details type and Sub-account at precondition");
        log("@Step 6: Filter Beginning Date/Ending Date within 1 month");
        log("@Step 7: Click Show button");
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,DateUtils.getDate(-30,"dd/MM/yyyy",GMT_7),"");
        log("@Verify 1: All transactions of Sub-accounts display correctly");
        page.verifyTransWithinRangeDate(-30,0);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.4.0"})
    @TestRails(id = "29467")
    public void Fund_Reconciliation_TC_29467() {
        log("@title: Validate error message displays when tried to filter >1 month");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: Having a Details type which has some sub-accounts");
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        log("@Pre-condition 4: Sub-accounts have transaction within 1 month");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Select Details type and Sub-account at precondition");
        log("@Step 6: Filter Beginning Date/Ending Date >1 month");
        log("@Step 7: Click Show button");
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,DateUtils.getDate(-32,"dd/MM/yyyy",GMT_7),"");
        log("@Verify 1: Error message 'Date range should not be more than 1 month' will display");
        String mesAc = page.appArlertControl.getWarningMessage();
        Assert.assertEquals(mesAc, FundReconciliation.ERROR_MES_MORE_THAN_1_MONTH,"FAILED! Error message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29477")
    public void Fund_Reconciliation_TC_29477() {
        log("@title: Validate there is a Show Amount in 5 Decimals checkbox with unticked as default");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: Having a Details type which has some sub-accounts including transaction within 1 month");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Verify 1: There is 'Show Amount in 5 Decimals' checkbox with unticked as default");
        page.verifyShowAmountIn5DecimalsDisplay();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.4.0"})
    @TestRails(id = "29478")
    public void Fund_Reconciliation_TC_29478() {
        log("@title: Validate the report shows amounts with 2 decimals number if unticked checkbox");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: Having a Details type which has some sub-accounts including transaction within 1 month");
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Filter data of details type and sub-account at precondition within 1 month");
        log("@Step 6: Click Show button");
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,DateUtils.getDate(-30,"dd/MM/yyyy",GMT_7),"");
        log("@Verify 1: The report shows amounts with 2 decimals");
        page.verifyDecimalsPlaces(2);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.4.0"})
    @TestRails(id = "29479")
    public void Fund_Reconciliation_TC_29479() {
        log("@title: Validate the report shows amounts with 5 decimals number if ticked checkbox");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: Having a Details type which has some sub-accounts including transaction within 1 month");
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Filter data of details type and sub-account at precondition within 1 month");
        log("@Step 6: Check in 'Show Amount in 5 Decimals' checkbox");
        log("@Step 7: Click Show button");
        page.cbShowAmount5Decimals.click();
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,DateUtils.getDate(-30,"dd/MM/yyyy",GMT_7),"");
        log("@Verify 1: The report shows amounts with 2 decimals");
        page.verifyDecimalsPlaces(5);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.4.0"})
    @TestRails(id = "29480")
    public void Fund_Reconciliation_TC_29480() {
        log("@title: Validate there is a header row that displays info of the sub-account with correct format");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: Having a Details type which has some sub-accounts including transaction within 1 month");
        String ledgerNumber = "101.000.001.000";
        String ledgerName = "PC Kam HKD";
        String groupName = "Cash";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Filter data of details type and sub-account at precondition within 1 month");
        log("@Step 6: Click Show button");
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,DateUtils.getDate(-30,"dd/MM/yyyy",GMT_7),"");
        log("@Verify 1: There is a header row that displays info of the sub-account with the format '<Sub-account number> - <Sub-account name>'");
        Assert.assertEquals(page.lblSubAccountName.getText(),String.format("%s - %s",ledgerNumber,ledgerName),"FAILED! Sub account display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.4.0"})
    @TestRails(id = "29481")
    public void Fund_Reconciliation_TC_29481() {
        log("@title: Validate background color of header row is #d9e4f7");
        log("@Pre-condition 1: 'System Monitoring' permission is ON for any account");
        log("@Pre-condition 2: 'Fund Reconciliation' permission is ON for any account");
        log("@Pre-condition 3: Having a Details type which has some sub-accounts including transaction within 1 month");
        String ledgerNumber = "101.000.001.000";
        String groupName = "Cash";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports ' menu");
        log("@Step 3: Click on 'System Monitoring'");
        log("@Step 4: Click on 'Fund Reconciliation'");
        FundReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(FUND_RECONCILIATION, FundReconciliationPage.class);
        log("@Step 5: Filter data of details type and sub-account at precondition within 1 month");
        log("@Step 6: Click Show button");
        page.filter(KASTRAKI_LIMITED,groupName,ledgerNumber,DateUtils.getDate(-30,"dd/MM/yyyy",GMT_7),"");
        log("@Verify 1: The background color is #d9e4f7");
        Assert.assertEquals(Color.fromString(page.lblSubAccountName.getColour()).asHex(),"#d9e4f7","FAILED! Sub account display incorrect");
        log("INFO: Executed completely");
    }
}
