package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.SystemMonitoringPage;
import pages.sb11.generalReports.systemmonitoring.ARandAPReconciliationPage;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;
import utils.sb11.TransactionUtils;
import utils.sb11.UserManagementUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.List;

import static common.SBPConstants.*;

public class ARandAPReconciliationTest extends BaseCaseAQS {
    @TestRails(id="17658")
    @Test(groups = {"regression","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17658(){
        log("@title: Validate Company Unit dropdown list displays properly");
        log("@pre-condition: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Expand 'Company Unit' dropdown list");
        log("@Verify 1: Company Unit list will display with 2 options: Kastraki Limited (default) and Fair");
        Assert.assertEquals(page.ddCompany.getOptions(),ARandAPReconciliation.COMPANY_UNIT_LIST,"FAILED! Company Unit list displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17659")
    @Test(groups = {"regression","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17659(){
        log("@title: Validate Details type list if Company Unit = Kastraki Limited");
        log("@pre-condition: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Select Company Unit = Kastraki Limited");
        page.ddCompany.selectByVisibleText(KASTRAKI_LIMITED);
        page.waitSpinnerDisappeared();
        log("@Step 4: Expand Detail Type dropdown list");
        log("@Verify 1: Detail Types list will include the following items:\n"+
                "105.000.000.000 - A/R Project\n" +
                "107.000.000.000 - Other Receivables\n" +
                "202.000.000.000 - Other Payable");
        Assert.assertEquals(page.ddDetailType.getOptions(),ARandAPReconciliation.DETAIL_TYPE_LIST_KASTRAKI_LIMITED,"FAILED! Detail type list displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17660")
    @Test(groups = {"regression","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17660(){
        log("@title: Validate Details type list if Company Unit = Fair");
        log("@pre-condition: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Select Company Unit = Fair");
        page.ddCompany.selectByVisibleText("Fair");
        page.waitSpinnerDisappeared();
        log("@Step 4: Expand Detail Type dropdown list");
        log("@Verify 1: Detail Types list will include the following items:\n"+
                "105.000.000.000 - A/R Project\n" +
                "107.000.000.000 - Other Receivables\n" +
                "202.000.000.000 - Other Payable");
        Assert.assertEquals(page.ddDetailType.getOptions(),ARandAPReconciliation.DETAIL_TYPE_LIST_FAIR,"FAILED! Detail type list displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17665")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17665() throws IOException {
        log("@title: Validate the Description displays correctly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: Description field will displays the Memo/Description of each transaction accordingly");
        //number column index - 1 because list header include parent account name row
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,desc,page.tblHeader.getColumnIndexByName("Description")-1),desc,
                "FAILED! Description displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17666")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17666() throws IOException {
        log("@title: Validate the CUR displays correctly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String curEx = "HKD";
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: CUR will display the currency of the sub-account");
        //number column index - 1 because list header include parent account name row
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,desc,page.tblHeader.getColumnIndexByName("CUR")-1),curEx,
                "FAILED! CUR displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17667")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17667() throws IOException {
        log("@title: Validate the Debit/Credit displays correctly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: Debit/Credit will get from the debit/credit amounts of each transaction");
        //number column index - 1 because list header include parent account name row
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,desc,page.tblHeader.getColumnIndexByName("Debit")-1),String.format("%.2f",valueDebit),
                "FAILED! Debit displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17668")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17668() throws IOException {
        log("@title: Validate the Running Balance displays correctly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "203.000.000.001 - Auto Other Payable";
        String groupName = "Other Payable";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Pre-condition 3: Get Running Bal in Ledger Statement");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        String detailType = "202.000.000.000 - Other Payable";
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,"",ledgerType,detailType,transDate,transDate,"");
        double runningBal = ledgerStatementPage.getValueAmount(ledgerAccountName,ledgerStatementPage.colRunBalGBP);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = ledgerStatementPage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: Running Balance will displays correctly:\n" +
                "The 'Opening Balance' row: shows the Opening Balance of the sub-account in the filtered date (same as in Ledger Statement)\n" +
                "The transaction rows: is Opening balance +/- debit/credit amounts, according to the logic of Account Type");
        //number column index - 1 because list header include parent account name row
        double openBalance = Double.valueOf(page.getValueByDesc(ledgerDebit,"Opening Balance",page.tblHeader.getColumnIndexByName("Running Balance")-1));
        Assert.assertEquals(openBalance,runningBal,"FAILED! Opening Balance displays incorrect");
        String runningEx = String.format("%.2f",openBalance + Double.valueOf(page.getValueByDesc(ledgerDebit, desc,page.tblHeader.getColumnIndexByName("Debit")-1)));
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,desc,page.tblHeader.getColumnIndexByName("Running Balance")-1),runningEx,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17669")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17669() throws IOException {
        log("@title: Validate the confirm checkbox works properly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Step 4: Tick in the Confirm checkbox");
        log("@Step 5: Click Yes button");
        page.tickConfirmAuthorise(ledgerDebit,desc,"Confirm");
        log("@Step 6: Try to untick the Confirm checkbox");
        log("@Verify 1: The checkbox cannot be unticked");
        Assert.assertFalse(page.isCheckCanTick(ledgerDebit, desc,"Confirm"),"FAILED! Checkbox can tick.");
        log("INFO: Executed completely");
    }
    @TestRails(id="17675")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17675() throws IOException {
        log("@title: Validate the Authorise checkbox works properly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Step 4: Tick in the Authorise checkbox");
        log("@Step 5: Click Yes button");
        page.tickConfirmAuthorise(ledgerDebit,desc,"Authorise");
        log("@Step 6: Try to untick the Authorise checkbox");
        log("@Verify 1: The checkbox cannot be unticked");
        Assert.assertFalse(page.isCheckCanTick(ledgerDebit,desc,"Authorise"),"FAILED! Checkbox can tick.");
        log("INFO: Executed completely");
    }
    @TestRails(id="17676")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    @Parameters({"username"})
    public void AR_and_AP_ReconciliationTest_17676(String username) throws IOException {
        log("@title: Validate the Processed By displays correctly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Step 4: Tick in the Confirm checkbox");
        log("@Step 5: Click Yes button");
        page.tickConfirmAuthorise(ledgerDebit,desc,"Confirm");
        log("@Verify 1: Processed By will display the user who ticked on the Confirm checkbox.");
        //number column index - 1 because list header include parent account name row
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,desc,page.tblHeader.getColumnIndexByName("Processed By")-1),username,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17677")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    @Parameters({"username"})
    public void AR_and_AP_ReconciliationTest_17677(String username) throws IOException {
        log("@title: Validate the Authorised By displays correctly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Step 4: Tick in the Authorise checkbox");
        log("@Step 5: Click Yes button");
        page.tickConfirmAuthorise(ledgerDebit, desc,"Authorise");
        log("@Verify 1: Authorised By will display the user who ticked on the Authorise checkbox of each transaction.");
        //number column index - 1 because list header include parent account name row
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,desc,page.tblHeader.getColumnIndexByName("Authorised By")-1),username,"FAILED! Transaction displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17678")
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void AR_and_AP_ReconciliationTest_17678() throws IOException {
        log("@title: Validate the Closing Balance row displays correct value");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebit = "107.000.000.001 - Auto Other Receivables";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        String ledgerAccountName = ChartOfAccountUtils.getAccountName(ledgerDebit,true);
        String ledgerAccountNumber = ChartOfAccountUtils.getAccountNumber(ledgerDebit,true);
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerAccountName).ledgerCreditNumber(ledgerAccountNumber)
                .ledgerDebit(ledgerAccountName).ledgerDebitNumber(ledgerAccountNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();

        String ledgerGroupId = ChartOfAccountUtils.getLedgerGroupId(groupName);
        String parentId = ChartOfAccountUtils.getParentId(ledgerGroupId, groupName);
        String ledgerType = ChartOfAccountUtils.getLedgerType(parentId,ledgerAccountName);
        String ledgerDebitAccountId = ChartOfAccountUtils.getLedgerAccountId(parentId,ledgerAccountName);
        TransactionUtils.addLedgerTxn(transactionPost,ledgerDebitAccountId,ledgerDebitAccountId,ledgerType);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: Closing Balance row will sums of all rows of Debit/Credit amounts\n" +
                "For Running Balance, it's the final amount of the last record.");
        String sumDebit = page.getSumDebitCredit(ledgerDebit,"Debit");
        String sumCredit = page.getSumDebitCredit(ledgerDebit, "Credit");
        //number column index - 1 because list header include parent account name row
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,"Closing Balance",page.tblHeader.getColumnIndexByName("Debit")-1),sumDebit,"FAILED! Sum of Debit displays incorrect");
        Assert.assertEquals(page.getValueByDesc(ledgerDebit,"Closing Balance",page.tblHeader.getColumnIndexByName("Credit")-1),sumCredit,"FAILED! Sum of Credit displays incorrect");
        Assert.assertEquals(page.tblData.getColumn(page.tblHeader.getColumnIndexByName("Running Balance")-1,50,true).get(page.tblData.getNumberOfRows(false,true)-2),
                page.getValueByDesc(ledgerDebit,"Closing Balance",page.tblHeader.getColumnIndexByName("Running Balance")-1),"FAILED! Running Balance displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17679")
    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
    @Parameters({"username"})
    public void AR_and_AP_ReconciliationTest_17679(String username) throws IOException {
        log("@title: Validate the Today's Settlement in HKD row displays correct value");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebitName = "Auto Other Receivables";
        String ledgerDebitNumber = "107.000.000.001";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerDebitName).ledgerCreditNumber(ledgerDebitNumber)
                .ledgerDebit(ledgerDebitName).ledgerDebitNumber(ledgerDebitNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,groupName,groupName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        String detailType = "107.000.000.000 - Other Receivables";
        page.filter(KASTRAKI_LIMITED,detailType,transDate);
        log("@Step 4: Tick in the Authorise checkbox");
        log("@Step 5: Click Yes button");
        page.tickConfirmAuthorise(ledgerDebitName, desc,"Authorise");
        log("@Verify 1: Today's Settlement in HKD row will sums up all the amounts of the authorized transactions and then converts to HKD using rate of the filtered date.");
        String todaySettleEx = page.getSumAuthorizedTrans(ledgerDebitNumber,username);
        //number column index - 1 because list header include parent account name row
        Assert.assertEquals(page.tblTodaySettle.getControlOfCell(1,page.tblHeader.getColumnIndexByName("Debit")-1,1,"span").getText(),
                todaySettleEx,"FAILED! Today's Settlement value display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17949")
    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
    public void AR_and_AP_ReconciliationTest_17949() throws IOException {
        log("@title: Validate Detail Types will sort by code/number ascendingly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerName = "Auto Other Payable";
        String ledgerNumber = "203.000.000.001";
        String groupName = "Other Payable";
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

        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName, groupName, groupName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        log("@Step 4: Click Show button");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: Detail Types will sort by code/number ascendingly");
        List<String> lstDetail = page.getLstDetailType();
        page.verifyDetailTypeSortByAsc(lstDetail);
        log("INFO: Executed completely");
    }
    @TestRails(id="17950")
    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
    public void AR_and_AP_ReconciliationTest_17950() throws IOException {
        log("@title: Validate Parent Accounts will sort by code/number ascendingly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebitName = "Auto Other Receivables";
        String ledgerDebitNumber = "107.000.000.001";
        String ledgerCreditName = "Other Auto Receivables";
        String ledgerCreditNumber = "107.000.001.001";
        String groupName = "Other Receivables";
        String parentCredit = "Auto Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerCreditName).ledgerCreditNumber(ledgerCreditNumber)
                .ledgerDebit(ledgerDebitName).ledgerDebitNumber(ledgerDebitNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,groupName,parentCredit,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        log("@Step 4: Click Show button");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: Parent Accounts will sort by code/number ascendingly");
        List<String> lstParent = page.getLstParentAccount("Other Receivables");
        page.isSortByAsc(lstParent);
        log("INFO: Executed completely");
    }
    @TestRails(id="17951")
    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
    public void AR_and_AP_ReconciliationTest_17951() throws IOException {
        log("@title: Validate sub-accounts will sort by code/number ascendingly");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebitName = "Auto Other Receivables";
        String ledgerDebitNumber = "107.000.000.001";
        String ledgerCreditName = "Other Receivables Auto";
        String ledgerCreditNumber = "107.000.000.002";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerCreditName).ledgerCreditNumber(ledgerCreditNumber)
                .ledgerDebit(ledgerDebitName).ledgerDebitNumber(ledgerDebitNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,groupName,groupName,"");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
        ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
        log("@Step 3: Filter transaction");
        log("@Step 4: Click Show button");
        String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        page.filter(KASTRAKI_LIMITED,"",transDate);
        log("@Verify 1: Sub-accounts will sort by code/number ascendingly");
        List<String> lstSubAcc = page.getLstSubAccount(groupName,groupName);
        page.isSortByAsc(lstSubAcc);
        log("INFO: Executed completely");
    }
    @TestRails(id="17952")
    @Test(groups = {"regression_stg","2024.V.2.0","ethan"})
    @Parameters({"userNameOneRole","username","password"})
    public void AR_and_AP_ReconciliationTest_17952(String userNameOneRole, String username, String password) throws Exception {
        log("@title: Validate could not authorize any transaction if the user does not have 'Head Finance permission'");
        log("@pre-condition 1: 'A/R and A/P Reconciliation' permission is ON for any account");
        log("@pre-condition 2: An Accounts/Finance user role does not have 'Head Finance permission' (at User >> User Management page >> Edit User Account)");
        UserManagementUtils.editUserManagament(userNameOneRole,"One role","ACTIVE","6",false);
        log("@pre-condition 3: Having some transaction of A/R and A/P Reconciliation for detail types");
        String ledgerDebitName = "Auto Other Receivables";
        String ledgerDebitNumber = "107.000.000.001";
        String groupName = "Other Receivables";
        String desc = "Automation Testing Transaction " + DateUtils.getMilliSeconds();
        String currentDate = DateUtils.getDate(-1, "yyyy-MM-dd", "GMT +7");
        double valueDebit= 2.00;
        Transaction transactionPost = new Transaction.Builder()
                .ledgerCredit(ledgerDebitName).ledgerCreditNumber(ledgerDebitNumber)
                .ledgerDebit(ledgerDebitName).ledgerDebitNumber(ledgerDebitNumber)
                .amountDebit(valueDebit).amountCredit(valueDebit)
                .remark(desc)
                .transDate(currentDate)
                .transType("Payment Other").build();
        TransactionUtils.addTransByAPI(transactionPost,"Ledger",groupName,groupName,groupName,groupName,"");
        try {
            log("@Step 1: Login by account at precondition");
            LoginPage loginPage = welcomePage.logout();
            loginPage.login(userNameOneRole, StringUtils.decrypt(password));
            log("@Step 2: Go to General Reports >> System Monitoring >> A/R and A/P Reconciliation");
            ARandAPReconciliationPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(AR_AND_AP_RECONCILIATION, ARandAPReconciliationPage.class);
            log("@Step 3: Filter transaction");
            log("@Step 4: Click Show button");
            String transDate = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
            String detailType = "107.000.000.000 - Other Receivables";
            page.filter(KASTRAKI_LIMITED,detailType,transDate);
            log("@Verify 1: The user could not authorize any transaction");
            Assert.assertFalse(page.isCheckCanTick(ledgerDebitName,desc,"Authorise"),"FAILED! The user could authorize any transaction");
        } finally {
            LoginPage loginPage = welcomePage.logout();
            loginPage.login(username,StringUtils.decrypt(password));
            UserManagementUtils.editUserManagament(userNameOneRole,"One role","ACTIVE","40",false);
            log("INFO: Executed completely");
        }
    }
}
