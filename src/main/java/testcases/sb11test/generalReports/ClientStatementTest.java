package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import objects.Order;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.ClientStatementPage;
import pages.sb11.generalReports.popup.clientstatement.ClientLedgerRecPayPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientMemberTransactionPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientSummaryPopup;
import pages.sb11.generalReports.popup.clientstatement.ClientSummaryWinlosePopup;
import testcases.BaseCaseAQS;
import utils.sb11.accounting.JournalReportsUtils;
import utils.sb11.accounting.TransactionUtils;
import utils.sb11.master.AccountSearchUtils;
import utils.sb11.trading.BetEntrytUtils;
import utils.sb11.trading.BetSettlementUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import static common.SBPConstants.*;

public class ClientStatementTest extends BaseCaseAQS {
    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "309")
    public void ClientStatementTC_309(String clientCode) throws ParseException {
        String superMasterCode = "QA2112 - ";
        clientCode = superMasterCode + clientCode;
        String actualVal;
        String openingVal;
        String winLossVal;
        String commissionVal;
        String recPayVal;
        String viewBy = "Client Point";
        log("@title: Validate that Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, "","");

        log("Validate Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening);
        winLossVal = clientPage.getSuperCellValue(clientPage.colWinLoss);
        commissionVal = clientPage.getSuperCellValue(clientPage.colCommission);
        recPayVal = clientPage.getSuperCellValue(clientPage.colRecPay);
        actualVal = clientPage.getSuperCellValue(clientPage.colClosing);

        double expectedVal = DecimalFormat.getNumberInstance().parse(openingVal).doubleValue() + DecimalFormat.getNumberInstance().parse(winLossVal).doubleValue() +
                DecimalFormat.getNumberInstance().parse(commissionVal).doubleValue() + DecimalFormat.getNumberInstance().parse(recPayVal).doubleValue();

        Assert.assertEquals(Math.round(expectedVal*100.0)/100.0,DecimalFormat.getNumberInstance().parse(actualVal).doubleValue(),0.01,"FAILED! Closing Balance is not calculated correctly, actual:"+actualVal+" and expected:"+expectedVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"deprecated"})
    @Parameters("clientCode")
    @TestRails(id = "310")
    public void ClientStatementTC_310(String clientCode) {
        String superMasterCode = "QA2112 - ";
        clientCode = superMasterCode + clientCode;
        String openingVal;
        String closingVal;
        String viewBy = "Client Point";
        log("@title: Validate that Opening value today is Closing of yesterday");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();

        log("@Step 2.1: Filter a client with client point view on current date");
        clientPage.filter(viewBy, KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, "","");

        log("@Step 2.2: Get Opening value");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening);

        log("@Step 3: Filter a client with client point view on current date - 1");
        clientPage.filter(viewBy, KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"),
                DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"));

        log("@Step 3.1: Get Closing value");
        closingVal = clientPage.getSuperCellValue(clientPage.colClosing);

        log("@Validate that Opening value today is Closing of yesterday");
        Assert.assertEquals(closingVal,openingVal,"FAILED! Closing Balance of previous date is not equal to Opening Balance of current date, " +
                "Opening:"+openingVal+" and Closing:"+closingVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan5.0"})
    @Parameters("clientCode")
    @TestRails(id = "587")
    public void ClientStatementTC_587(String clientCode) {
        String superMasterCode = "QA2112 - ";
        clientCode = superMasterCode + clientCode;
        String totalGrandMasterVal;
        String totalGrandHKDVal;
        String viewBy = "Client Point";
        log("@title: Validate total in HKD of Master match with Grand Total in HKD at bottom");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, "","");

        log("Validate total in HKD of Master match with Grand Total in HKD at bottom");
        totalGrandMasterVal = clientPage.getMasterCellValue("Total in", clientPage.colClosing);
        totalGrandHKDVal = clientPage.getGrandTotal("HKD");

        Assert.assertEquals(totalGrandMasterVal,totalGrandHKDVal,"FAILED! Grand Master value is not equal Grand HKD, Grand Master:"+totalGrandMasterVal+" and Grand HKD:"+totalGrandHKDVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan5.0"})
    @Parameters("clientCode")
    @TestRails(id = "588")
    public void ClientStatementTC_588(String clientCode) {
        String superMasterCode = "QA2112 - ";
        clientCode = superMasterCode + clientCode;
        String openingVal;
        String winLossVal;
        String commissionVal;
        String recPayVal;
        String movementVal;
        String closingVal;
        String viewBy = "Client Point";
        log("@title: Validate values are the same but opposite each other when viewing by Company Point and Client Point");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, "","");

        //TODO need enhancement as currently is workingaround by remove "," out of string before reverse and assert
        log("Verify Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening).replace(",","");
        winLossVal = clientPage.getSuperCellValue(clientPage.colWinLoss).replace(",","");
        commissionVal = clientPage.getSuperCellValue(clientPage.colCommission).replace(",","");
        recPayVal = clientPage.getSuperCellValue(clientPage.colRecPay).replace(",","");
        movementVal = clientPage.getSuperCellValue(clientPage.colMovement).replace(",","");
        closingVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");
        ArrayList <String> lstActual = new ArrayList<>();
        Collections.addAll(lstActual,openingVal,winLossVal,commissionVal,recPayVal,movementVal,closingVal);

        clientPage.filter("Company Point", KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, "","");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening).replace(",","");
        winLossVal = clientPage.getSuperCellValue(clientPage.colWinLoss).replace(",","");
        commissionVal = clientPage.getSuperCellValue(clientPage.colCommission).replace(",","");
        recPayVal = clientPage.getSuperCellValue(clientPage.colRecPay).replace(",","");
        movementVal = clientPage.getSuperCellValue(clientPage.colMovement).replace(",","");
        closingVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");
        ArrayList <String> lstExpected = new ArrayList<>();
        Collections.addAll(lstExpected,openingVal,winLossVal,commissionVal,recPayVal,movementVal,closingVal);

        Assert.assertTrue(clientPage.verifyValueIsOpposite(lstActual,lstExpected),"FAILED! Closing Balance is not calculated correctly");
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "861")
    public void ClientStatementTC_861(String clientCode) {
        String agentCode = "QASAHK00";
        String superMasterCode = "QA2112 - ";
        clientCode = superMasterCode + clientCode;
        String openingVal;
        String winLossVal;
        String recPayVal;
        String movementVal;
        String closingVal;
        String actualOpeningVal;
        String actualWinloseVal;
        String actualRecPayVal;
        String actualMovementVal;
        String actualClosingVal;
        String viewBy = "Client Point";
        log("@title: Validate value of agent (not COM, LED) in main page match with member summary page");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, "","");

        log("Validate value of agent (not COM, LED) in main page match with member summary page");
        openingVal = clientPage.getAgentCellValue(agentCode,clientPage.colOpening);
        winLossVal = clientPage.getAgentCellValue(agentCode,clientPage.colWinLoss);
        recPayVal = clientPage.getAgentCellValue(agentCode,clientPage.colRecPay);
        movementVal = clientPage.getAgentCellValue(agentCode,clientPage.colMovement);
        closingVal = clientPage.getAgentCellValue(agentCode,clientPage.colClosing);

        ClientSummaryPopup popup = clientPage.openSummaryPopup(agentCode);
        actualOpeningVal = popup.getGrandTotal("HKD",popup.colOpeningTotal);
        actualWinloseVal = popup.getGrandTotal("HKD",popup.colWinLoseTotal);
        actualRecPayVal = popup.getGrandTotal("HKD",popup.colRecPayTotal);
        actualMovementVal = popup.getGrandTotal("HKD",popup.colMovementTotal);
        actualClosingVal = popup.getGrandTotal("HKD",popup.colClosingTotal);

        Assert.assertEquals(openingVal,actualOpeningVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualOpeningVal+" and expected:"+openingVal);
        Assert.assertEquals(winLossVal,actualWinloseVal,"FAILED! WinLose Balance is not calculated correctly, actual:"+actualWinloseVal+" and expected:"+winLossVal);
        Assert.assertEquals(recPayVal,actualRecPayVal,"FAILED! RecPay Balance is not calculated correctly, actual:"+actualRecPayVal+" and expected:"+recPayVal);
        Assert.assertEquals(movementVal,actualMovementVal,"FAILED! Movement Balance is not calculated correctly, actual:"+actualMovementVal+" and expected:"+movementVal);
        Assert.assertEquals(closingVal,actualClosingVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualClosingVal+" and expected:"+closingVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "865")
    public void ClientStatementTC_865(String clientCode) {
        String agentComCode = "QATE01-COM";
        String superMasterCode = "QA2112 - ";
        clientCode = superMasterCode + clientCode;
        String openingVal;
        String commissionVal;
        String recPayVal;
        String movementVal;
        String closingVal;
        String actualOpeningVal;
        String actualCommVal;
        String actualRecPayVal;
        String actualMovementVal;
        String actualClosingVal;
        String viewBy = "Client Point";
        log("@title: Validate value of agent COM in main page match with member summary page");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, KASTRAKI_LIMITED, FINANCIAL_YEAR, clientCode, "","");
        clientPage.tickFilter(false,true,true,true);

        log("Validate value of agent COM in main page match with member summary page");
        openingVal = clientPage.getAgentCellValue(agentComCode,clientPage.colOpening);
        commissionVal = clientPage.getAgentCellValue(agentComCode,clientPage.colCommission);
        recPayVal = clientPage.getAgentCellValue(agentComCode,clientPage.colRecPay);
        movementVal = clientPage.getAgentCellValue(agentComCode,clientPage.colMovement);
        closingVal = clientPage.getAgentCellValue(agentComCode,clientPage.colClosing);

        ClientSummaryPopup popup = clientPage.openSummaryPopup(agentComCode);
        actualOpeningVal = popup.getGrandTotal("HKD",popup.colOpeningTotal);
        actualCommVal = popup.getGrandTotal("HKD",popup.colComissionTotal);
        actualRecPayVal = popup.getGrandTotal("HKD",popup.colRecPayTotal);
        actualMovementVal = popup.getGrandTotal("HKD",popup.colMovementTotal);
        actualClosingVal = popup.getGrandTotal("HKD",popup.colClosingTotal);

        Assert.assertEquals(openingVal,actualOpeningVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualOpeningVal+" and expected:"+openingVal);
        Assert.assertEquals(commissionVal,actualCommVal,"FAILED! Commission Balance is not calculated correctly, actual:"+actualCommVal+" and expected:"+commissionVal);
        Assert.assertEquals(recPayVal,actualRecPayVal,"FAILED! RecPay Balance is not calculated correctly, actual:"+actualRecPayVal+" and expected:"+recPayVal);
        Assert.assertEquals(movementVal,actualMovementVal,"FAILED! Movement Balance is not calculated correctly, actual:"+actualMovementVal+" and expected:"+movementVal);
        Assert.assertEquals(closingVal,actualClosingVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualClosingVal+" and expected:"+closingVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "866")
    public void ClientStatementTC_866(String clientCode) throws IOException {
        log("@Validate the balance is deducted from the account if a Client account and an amount are inputted into the 'Debit' form");
        String agentCode = "QASAHK00";
        String level = "Player";
        String actualRecPayVal;
        String expectedRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("Precondition: Add transaction for the Client account into Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desClient = "TC_866 Automation Testing Transaction Client: " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .clientDebit(clientCode).clientCredit(clientCode)
                .amountDebit(1).amountCredit(1).remark(desClient)
                .transDate(transDate).transType("Payment Other").level(level)
                .debitAccountCode(CLIENT_DEBIT_ACC)
                .creditAccountCode(CLIENT_CREDIT_ACC)
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Client",CLIENT_DEBIT_ACC,CLIENT_CREDIT_ACC,"","",clientCode);
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Client",CLIENT_DEBIT_ACC,"Payment Other",desClient);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            clientPage.waitSpinnerDisappeared();
            log("@Step 3: Open Summary popup of agent of the player");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentCode);
            popup.waitSpinnerDisappeared();
            log("@Verify the balance is deducted from the Client account properly");
            expectedRecPayVal = clientPage.reverseValue(String.format("%.2f",transaction.getAmountDebit()));
            actualRecPayVal = popup.getSummaryCellValue(CLIENT_CREDIT_ACC,popup.tblSummary.getColumnIndexByName("Rec/Pay/CA/RB/Adj")).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Client Debit balance is not deducted correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Client account into Credit");
            String desTxn = "TC_866 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .clientDebit(clientCode).clientCredit(clientCode)
                    .amountDebit(1).amountCredit(1).remark(desTxn)
                    .transDate(transDate).transType("Received Comm/Rebate").level(level)
                    .debitAccountCode(CLIENT_CREDIT_ACC)
                    .creditAccountCode(CLIENT_DEBIT_ACC)
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Client",CLIENT_CREDIT_ACC,CLIENT_DEBIT_ACC,"","",clientCode);
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Client",CLIENT_DEBIT_ACC,"Received Comm/Rebate",desTxn);
        }

        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "867")
    public void ClientStatementTC_867(String clientCode) throws IOException {
        log("@Validate the balance is added from the account if a Client account and an amount are inputted into the 'Credit' form");
        String agentCode = "QASAHK00";
        String level = "Player";
        String actualRecPayVal;
        String expectedRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("Precondition: Add transaction for the Client account into Credit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desClient = "TC_867 Automation Testing Transaction Client: " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .clientDebit(clientCode).clientCredit(clientCode)
                .amountDebit(1).amountCredit(1).remark(desClient)
                .transDate(transDate).transType("Payment Other").level(level)
                .debitAccountCode(CLIENT_DEBIT_ACC)
                .creditAccountCode(CLIENT_CREDIT_ACC)
                .build();
        try {
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the player");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentCode);
            log("@Verify the balance is added to the Client account properly");
            actualRecPayVal = popup.getSummaryCellValue(CLIENT_DEBIT_ACC,popup.colRecPay).replace(",","");
            popup.closeSummaryPopup();
            TransactionUtils.addTransByAPI(transaction,"Client",CLIENT_DEBIT_ACC,CLIENT_CREDIT_ACC,"","",clientCode);
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Client",CLIENT_DEBIT_ACC,"Payment Other",desClient);
            expectedRecPayVal = String.format("%.2f",transaction.getAmountDebit()+ Double.valueOf(actualRecPayVal));
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            clientPage.openSummaryPopup(agentCode);
            actualRecPayVal = popup.getSummaryCellValue(CLIENT_DEBIT_ACC,popup.colRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Client Credit balance is not added correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Client account into Credit");
            String desTxn = "TC_867 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .clientDebit(clientCode).clientCredit(clientCode)
                    .amountDebit(1).amountCredit(1)
                    .remark(desTxn)
                    .transDate(transDate).transType("Received Comm/Rebate").level(level)
                    .debitAccountCode(CLIENT_CREDIT_ACC)
                    .creditAccountCode(CLIENT_DEBIT_ACC)
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Client",CLIENT_CREDIT_ACC,CLIENT_DEBIT_ACC,"","",clientCode);
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Client",CLIENT_DEBIT_ACC,"Received Comm/Rebate",desTxn);
        }

        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "871")
    public void ClientStatementTC_871(String clientCode) throws IOException {
        log("@Validate the balance is added to the account if a Ledger type = Asset and an amount are inputted into the 'Debit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Asset Ledger account into Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desAs = "TC_871 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_ASSET_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_ASSET_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_ASSET_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_ASSET_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desAs)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_ASSET_CREDIT_NAME,"Payment Other",desAs);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is added to the account Asset 'Debit' correctly");
            expectedRecPayVal = String.format("%.2f",transaction.getAmountDebit());
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_ASSET_DEBIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Asset 'Debit' balance is not added correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Asset Ledger account into Credit");
            String remarkTXN = "TC_871 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_ASSET_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "872")
    public void ClientStatementTC_872(String clientCode) throws IOException {
        log("@Validate the balance is deducted from the account if a Ledger type = Asset and an amount are inputted into the 'Credit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Asset Ledger account into Credit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desAs = "TC_872 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_ASSET_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_ASSET_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_ASSET_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_ASSET_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desAs)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_ASSET_CREDIT_NAME,"Payment Other",desAs);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is deducted from the account Asset 'Credit' correctly");
            expectedRecPayVal = clientPage.reverseValue(String.format("%.2f",transaction.getAmountDebit()));
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_ASSET_CREDIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Asset 'Credit' balance is not deducted correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Asset Ledger account into Debit");
            String remarkTXN = "TC_872 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_ASSET_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "873")
    public void ClientStatementTC_873(String clientCode) throws IOException {
        log("@Validate the balance is deducted from the account if a Ledger type = Liability and an amount are inputted into the 'Debit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Liability Ledger account into Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desLia = "TC_873 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_LIABILITY_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_LIABILITY_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desLia)
                .transDate(transDate)
                .transType("Payment Operational")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_LIABILITY,LEDGER_GROUP_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,"");
            welcomePage.waitSpinnerDisappeared();
             JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_LIABILITY_CREDIT_NAME,"Payment Operational",desLia);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is deducted from the account Liability 'Debit' correctly");
            expectedRecPayVal = clientPage.reverseValue(String.format("%.2f",transaction.getAmountDebit()));
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_LIABILITY_DEBIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Liability 'Debit' balance is not deducted correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Liability Ledger account into Credit");
            String remarkTXN = "TC_873 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_LIABILITY_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_LIABILITY_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_LIABILITY,LEDGER_GROUP_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_LIABILITY_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "874")
    public void ClientStatementTC_874(String clientCode) throws IOException {
        log("@Validate the balance is added from the account if a Ledger type = Liability and an amount are inputted into the 'Credit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Liability Ledger account into Credit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desLia = "TC_874 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_LIABILITY_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_LIABILITY_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desLia)
                .transDate(transDate)
                .transType("Payment Operational")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_LIABILITY,LEDGER_GROUP_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_LIABILITY_CREDIT_NAME,"Payment Operational",desLia);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is added to the account Liability 'Credit' correctly");
            expectedRecPayVal = String.format("%.2f",transaction.getAmountDebit());
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_LIABILITY_CREDIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Liability 'Credit' balance is not added correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Liability Ledger account into Debit");
            String remarkTXN = "TC_874 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_LIABILITY_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_LIABILITY_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_LIABILITY_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_LIABILITY_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Payment Operational")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_LIABILITY,LEDGER_GROUP_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,LEDGER_PARENT_NAME_LIABILITY,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_LIABILITY_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "875")
    public void ClientStatementTC_875(String clientCode) throws IOException {
        log("Validate the balance is deducted from the account if a Ledger type = Capital and an amount are inputted into the 'Debit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Capital Ledger account into Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desCap = "TC_875 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_CAPITAL_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_CAPITAL_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desCap)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_CAPITAL,LEDGER_GROUP_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_CAPITAL_CREDIT_NAME,"Payment Other",desCap);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is deducted from the account Capital 'Debit' correctly");
            expectedRecPayVal = clientPage.reverseValue(String.format("%.2f",transaction.getAmountDebit()));
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_CAPITAL_DEBIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Capital 'Debit' balance is not deducted correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Capital Ledger account into Credit");
            String remarkTXN = "TC_875 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_CAPITAL_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_CAPITAL_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_CAPITAL,LEDGER_GROUP_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_CAPITAL_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "876")
    public void ClientStatementTC_876(String clientCode) throws IOException {
        log("@Validate the balance is added from the account if a Ledger type = Capital and an amount are inputted into the 'Credit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Capital Ledger account into Credit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd",GMT_7));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desCap = "TC_876 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_CAPITAL_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_CAPITAL_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desCap)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_CAPITAL,LEDGER_GROUP_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_CAPITAL_CREDIT_NAME,"Payment Other",desCap);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is added from the account Capital 'Credit' correctly");
            expectedRecPayVal = String.format("%.2f",transaction.getAmountDebit());
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_CAPITAL_CREDIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Capital 'Credit' balance is not added correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Capital Ledger account into Debit");
            String remarkTXN = "TC_876 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_CAPITAL_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_CAPITAL_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_CAPITAL_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_CAPITAL_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_CAPITAL,LEDGER_GROUP_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,LEDGER_PARENT_NAME_CAPITAL,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_CAPITAL_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "877")
    public void ClientStatementTC_877(String clientCode) throws IOException {
        log("@Validate the balance is deducted from the account if a Ledger type = Income and an amount are inputted into the 'Debit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Income Ledger account into Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desInc = "TC_877 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_INCOME_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_INCOME_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_INCOME_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_INCOME_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desInc)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_INCOME,LEDGER_GROUP_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_INCOME_CREDIT_NAME,"Payment Other",desInc);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is deducted from the account Income 'Debit' correctly");
            expectedRecPayVal = clientPage.reverseValue(String.format("%.2f",transaction.getAmountDebit()));
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_INCOME_DEBIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Income 'Debit' balance is not deducted correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Income Ledger account into Credit");
            String remarkTXN = "TC_877 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_INCOME_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_INCOME_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_INCOME_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_INCOME_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_INCOME,LEDGER_GROUP_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_INCOME_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "878")
    public void ClientStatementTC_878(String clientCode) throws IOException {
        log("@Validate the balance is added from the account if a Ledger type = Income and an amount are inputted into the 'Credit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Income Ledger account into Credit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desInc = "TC_878 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_INCOME_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_INCOME_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_INCOME_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_INCOME_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desInc)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_INCOME,LEDGER_GROUP_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_INCOME_CREDIT_NAME,"Payment Other",desInc);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is added from the account Income 'Credit' correctly");
            expectedRecPayVal = String.format("%.2f",transaction.getAmountDebit());
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_INCOME_CREDIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Income 'Credit' balance is not added correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Income Ledger account into Debit");
            String remarkTXN = "TC_878 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_INCOME_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_INCOME_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_INCOME_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_INCOME_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_INCOME,LEDGER_GROUP_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,LEDGER_PARENT_NAME_INCOME,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_INCOME_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "879")
    public void ClientStatementTC_879(String clientCode) throws IOException {
        log("@Validate the balance is added from the account if a Ledger type = Expenditure and an amount are inputted into the 'Debit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Expenditure Ledger account into Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desExp = "TC_879 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_EXPENDITURE_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_EXPENDITURE_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desExp)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_EXPENDITURE_CREDIT_NAME,"Payment Other",desExp);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is added from the account Expenditure 'Debit' correctly");
            expectedRecPayVal = String.format("%.2f",transaction.getAmountDebit());
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_EXPENDITURE_DEBIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Expenditure 'Debit' balance is not added correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Expenditure Ledger account into Credit");
            String remarkTXN = "TC_879 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_EXPENDITURE_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_EXPENDITURE_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "880")
    public void ClientStatementTC_880(String clientCode) throws IOException {
        log("@Validate the balance is deducted from the account if a Ledger type = Expenditure and an amount are inputted into the 'Credit' form");
        String agentLedCode = "QATE00-LED";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Expenditure Ledger account into Credit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desExp = "TC_880 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_EXPENDITURE_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_EXPENDITURE_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desExp)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate,fromDate,"Ledger",LEDGER_EXPENDITURE_CREDIT_NAME,"Payment Other",desExp);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent of the ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Validate the balance is deducted from the account Expenditure 'Credit' correctly");
            expectedRecPayVal = clientPage.reverseValue(String.format("%.2f",transaction.getAmountDebit()));
            actualRecPayVal = popup.getLedgerSummaryCellValue(LEDGER_EXPENDITURE_CREDIT_NAME,popup.colLedgerRecPay).replace(",","");
            Assert.assertEquals(actualRecPayVal,expectedRecPayVal,"FAILED! Expenditure 'Credit' balance is not deducted correctly, actual:"+actualRecPayVal+" and expected:"+expectedRecPayVal);
        } finally {
            log("@Post-condition: Add transaction for the Expenditure Ledger account into Debit");
            String remarkTXN = "TC_880 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_EXPENDITURE_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_EXPENDITURE_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_EXPENDITURE_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_EXPENDITURE_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_GROUP_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,LEDGER_PARENT_NAME_EXPENDITURE,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_EXPENDITURE_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan5.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "882")
    public void ClientStatementTC_882(String clientCode) {
        log("@Validate Win/Lose Summary dialog displays with properly value when clicked on Win/Lose link");
        String agentCode = "QATE01-PT";
        String expectedRecPayVal;
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("Precondition: Add Manual Bet and settle with win/loss value = 5.0");
        welcomePage.waitSpinnerDisappeared();
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Order order = new Order.Builder()
                .price(1.5).requireStake(15)
                .oddType("HK").accountCode("QATE02-PT")
                .createDate(transDate)
                .eventDate(transDate + " 23:59:00")
                .selection("Home " + DateUtils.getMilliSeconds())
                .build();
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);
        String accountId = AccountSearchUtils.getAccountId("QATE02-PT");
        BetEntrytUtils.placeManualBetAPI(companyId,accountId, SPORT_ID_MAP.get("Soccer"),order);
        BetSettlementUtils.sendManualBetSettleJson("QATE02-PT","Soccer",order);
        log("@Step 1: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();
        log("@Step 2: Filter the Client with Client Point view");
        clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
        log("@Step 3: Open Summary popup of agent and get Win/Lose of the player");
        ClientSummaryPopup popup = clientPage.openSummaryPopup(agentCode);
        expectedRecPayVal = popup.getSummaryCellValue("QATE02-PT",popup.colWinLose);
        log("@Step 4: Open win lose summary popup of player");
        ClientSummaryWinlosePopup winlosePopup = popup.openWinLoseSummaryPopup("QATE02-PT");
        winlosePopup.waitSpinnerDisappeared();
        log("@Validate the WinLose balance show properly");
        actualRecPayVal = winlosePopup.getGrandTotal(winlosePopup.colWinLoseTotal);
        Assert.assertEquals(expectedRecPayVal,actualRecPayVal,"FAILED! Win/Loss balance is not shown correctly, actual: "+actualRecPayVal+ " and expected: "+expectedRecPayVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan2.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "999")
    public void ClientStatementTC_999(String clientCode) {
        log("@Validate Member Transactions dialog displays with properly value when clicked on Account link");
        String agentCode = "QASAHK00";
        String expectOpeningRunningVal;
        String expectClosingRunningVal;
        String actualOpeningRunningVal;
        String actualClosingRunningVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("Precondition: Add Manual Bet and settle with win/loss value = 5.0");
        welcomePage.waitSpinnerDisappeared();
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Order order = new Order.Builder()
                .price(1.5).requireStake(15)
                .oddType("HK").accountCode(CLIENT_DEBIT_ACC)
                .createDate(transDate)
                .eventDate(transDate + " 23:59:00")
                .selection("Home " + DateUtils.getMilliSeconds())
                .build();
        int companyId = BetEntrytUtils.getCompanyID(KASTRAKI_LIMITED);
        String accountId = AccountSearchUtils.getAccountId(CLIENT_DEBIT_ACC);
        BetEntrytUtils.placeManualBetAPI(companyId,accountId, SPORT_ID_MAP.get("Soccer"),order);
        welcomePage.waitSpinnerDisappeared();
        int betId = BetSettlementUtils.getConfirmedBetId(accountId, SPORT_ID_MAP.get("Soccer"),order);
        int wagerId = BetSettlementUtils.getConfirmedBetWagerId(accountId, SPORT_ID_MAP.get("Soccer"),order);
        BetSettlementUtils.sendManualBetSettleJson(accountId,order,betId,wagerId, SPORT_ID_MAP.get("Soccer"));

        log("@Step 1: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        clientPage.waitSpinnerDisappeared();
        log("@Step 2: Filter the Client with Client Point view");
        clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
        log("@Step 3: Open Summary popup of agent");
        ClientSummaryPopup popup = clientPage.openSummaryPopup(agentCode);
        expectOpeningRunningVal = popup.getSummaryCellValue(CLIENT_DEBIT_ACC,popup.colOpening);
        expectClosingRunningVal = popup.getSummaryCellValue(CLIENT_DEBIT_ACC,popup.colClosing);
        log("@Verify the balance Total Running is calculated properly");
        ClientMemberTransactionPopup transPopup = popup.openMemberTransactionPopup(CLIENT_DEBIT_ACC);

        //TODO need enhancement as currently is workingaround by remove "," out of string before calculate
        actualOpeningRunningVal = transPopup.getOpeningRunning(transPopup.colOpeningRunning).replace(",","");
        actualClosingRunningVal = transPopup.getTotalRunning(transPopup.colTotalRunning).replace(",","");

        Assert.assertEquals(expectOpeningRunningVal,actualOpeningRunningVal,"FAILED! Opening Running is not calculated correctly, actual: "+ actualOpeningRunningVal + " and expected: "+expectOpeningRunningVal);
        Assert.assertEquals(expectClosingRunningVal,actualClosingRunningVal,"FAILED! Closing Running is not calculated correctly, actual: "+ actualClosingRunningVal + " and expected: "+expectClosingRunningVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan6.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "1004")
    public void ClientStatementTC_1004(String clientCode) throws IOException {
        log("@Validate Rec/Pay/CA/RB/Adj Txns dialog displays with properly value when clicked on Rec/Pay/CA/RB/Adj link");
        String agentLedCode = "QATE00-LED";
        String actualRecPayVal;
        String viewBy = "Client Point";
        String superMasterCode = "QA2112 - ";
        log("@Precondition: Add transaction for the Asset Ledger account to show value Rec/Pay");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        String fromDate = DateUtils.formatDate(transDate,"yyyy-MM-dd","dd/MM/yyyy");
        String desAs = "TC_1004 Automation Testing Transaction Client: Pre-condition " + DateUtils.getMilliSeconds();
        Transaction transaction = new Transaction.Builder()
                .ledgerCredit(LEDGER_ASSET_CREDIT_NAME)
                .ledgerCreditNumber(LEDGER_ASSET_CREDIT_NUMBER)
                .ledgerDebit(LEDGER_ASSET_DEBIT_NAME)
                .ledgerDebitNumber(LEDGER_ASSET_DEBIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(desAs)
                .transDate(transDate)
                .transType("Payment Other")
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,"");
            welcomePage.waitSpinnerDisappeared();
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_ASSET_CREDIT_NAME, "Payment Other", desAs);
            log("@Step 1: Navigate to General Reports > Client Statement");
            ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
            clientPage.waitSpinnerDisappeared();
            log("@Step 2: Filter the Client with Client Point view");
            clientPage.filter(viewBy, KASTRAKI_LIMITED,FINANCIAL_YEAR,superMasterCode + clientCode,"","");
            log("@Step 3: Open Summary popup of agent ledger");
            ClientSummaryPopup popup = clientPage.openSummaryPopup(agentLedCode);
            log("@Step 4: Open RecPay summary popup of player by click on link");
            ClientLedgerRecPayPopup recPayPopup = popup.openLedgerRecPaySummaryPopup(LEDGER_ASSET_DEBIT_NAME,popup.colLedgerRecPay);
            log("@Validate Difference show correctly with Rec/Pay/CA/RB/Adj link");
            actualRecPayVal = recPayPopup.getDifferenceOriginalVal(recPayPopup.colDifferentOriginal);
            Assert.assertEquals(String.format("%.2f",transaction.getAmountDebit()),actualRecPayVal,"FAILED! Total Running is not calculated correctly, actual: "+ actualRecPayVal + " and expected: "+transaction.getAmountDebit());
        } finally {
            log("@Post-condition: Add transaction for the Asset Ledger account into Debit");
            String remarkTXN = "TC_1004 Automation Testing Transaction Client: Post-condition for txn";
            Transaction transactionPost = new Transaction.Builder()
                    .ledgerCredit(LEDGER_ASSET_DEBIT_NAME)
                    .ledgerCreditNumber(LEDGER_ASSET_DEBIT_NUMBER)
                    .ledgerDebit(LEDGER_ASSET_CREDIT_NAME)
                    .ledgerDebitNumber(LEDGER_ASSET_CREDIT_NUMBER)
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark(remarkTXN)
                    .transDate(transDate)
                    .transType("Received Comm/Rebate")
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Ledger",LEDGER_GROUP_NAME_ASSET,LEDGER_GROUP_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,LEDGER_PARENT_NAME_ASSET,"");
            welcomePage.waitSpinnerDisappeared();
            log("@Post-condition: authorize transaction");
            JournalReportsUtils.tickAuthorize(fromDate, fromDate, "Ledger", LEDGER_ASSET_CREDIT_NAME, "Received Comm/Rebate", remarkTXN);
        }
        log("INFO: Executed completely");
    }
}
