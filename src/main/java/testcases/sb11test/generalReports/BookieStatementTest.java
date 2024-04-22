package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import common.SBPConstants;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.JournalEntriesPage;
import pages.sb11.generalReports.BookieStatementPage;
import pages.sb11.generalReports.popup.bookiestatement.*;
import testcases.BaseCaseAQS;
import utils.sb11.AccountSearchUtils;
import utils.sb11.BookieInfoUtils;
import utils.sb11.TransactionUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class BookieStatementTest extends BaseCaseAQS {

    @Test(groups = {"smoke"})
    @TestRails(id = "183")
    public void BookieStatementTC_183() throws InterruptedException {
        String bookieCode = "QA01";
        String superMasterCode = "SM-QA1-QA Test";
        String superGrandTotalHKDVal;
        String superMasterTotalHKDVal;
        String openBalanceVal;
        String openRPCRBAVal;
        String rpcrbaVal;
        String openCommVal;
        String commVal;
        Double expectedVal;

        log("@title: Validate amount in details and outside are matched for Super Master R/P/C/RB/A");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click General Reports > Bookie Statement");
        BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT,BookieStatementPage.class);
        Thread.sleep(20000);
        log("@Step 3: Filter with Bookie has made transaction");
        bookieStatementPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR,"Super Master",DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"),
                "",bookieCode,"");
        superMasterTotalHKDVal = bookieStatementPage.getSuperMasterCellValue(superMasterCode, bookieStatementPage.colMasterTotal);
        BookieSuperMasterDetailPopup bookiePopup = bookieStatementPage.openBookieSuperMasterDetailPopup(superMasterCode);
        openBalanceVal = bookiePopup.getSuperMasterCellValue(bookiePopup.colOpeningBalance, true);
        openRPCRBAVal = bookiePopup.getSuperMasterCellValue(bookiePopup.colOpeningRPCRBA, true);
        rpcrbaVal = bookiePopup.getSuperMasterCellValue(bookiePopup.colRPCRBA, true);
        openCommVal = bookiePopup.getSuperMasterCellValue(bookiePopup.colOpeningComm, true);
        commVal = bookiePopup.getSuperMasterCellValue(bookiePopup.colComm, true);
        superGrandTotalHKDVal = bookiePopup.getSuperMasterCellValue(bookiePopup.colGrandTotal, true);
        expectedVal = Double.parseDouble(openBalanceVal) + Double.parseDouble(openRPCRBAVal) + Double.parseDouble(rpcrbaVal) +
                Double.parseDouble(openCommVal) + Double.parseDouble(commVal);

        log("Validate amount in details and outside are matched for Super Master R/P/C/RB/A");
        Assert.assertEquals(String.format("%.2f",expectedVal),superMasterTotalHKDVal,"FAILED! Grand Total is not matched with sum value, Grand Total: " + superGrandTotalHKDVal
                + " expected: " + expectedVal);
        Assert.assertEquals(superMasterTotalHKDVal,superGrandTotalHKDVal,"FAILED! Master Total and Grand Total is not matched, Master Total: " + superMasterTotalHKDVal
                + " Grand Total: " + superGrandTotalHKDVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan2.0"})
    @TestRails(id = "1639")
    public void BookieStatementTC_1639() throws InterruptedException {
        String bookieName = "QA Bookie";
        String masterCode = "Ma-QA101-QA Test";
        String agentCode = "A-QA10101-QA Test";

        log("@title: Validate total WL and detailed value are matched each other");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click General Reports > Bookie Statement");
        BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT,BookieStatementPage.class);
        bookieStatementPage.waitSpinnerDisappeared();
        Thread.sleep(20000);
        log("@Step 3: Filter with Bookie has made transaction amd open MS link");
        bookieStatementPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR,"Agent",DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"),
                "",bookieName,"");
        BookieMemberSummaryPopup bookieMemberPopup = bookieStatementPage.openBookieMemberSummaryDetailPopup(masterCode,agentCode);

        String totalVal = bookieMemberPopup.getTotalCellValue(bookieMemberPopup.colTotal,true).replace(",","");
        String totalMemberGrandVal = bookieMemberPopup.getMemberTotalCellValue(bookieMemberPopup.colTotal,true).replace(",","");
        log("Validate the amount of Win/Lose, Op. Win/Lose, R/P/C/RB/A, Op. R/P/C/RB/A, Op. Balance in top table is matched with total in amount in the below table");
        Assert.assertEquals(totalVal,totalMemberGrandVal,"FAILED! Total Amount is not matched with Total Grand Amount in below table: Total Amount: " + totalVal
                + " Total Grand Amount: " + totalMemberGrandVal);
        log("Validate total in amount = Win/Lose + Op. Win/Lose + R/P/C/RB/A + Op. R/P/C/RB/A + Op. Balance");
        Assert.assertTrue(bookieMemberPopup.isGrandTotalValueDisplay());
        bookieMemberPopup.closePopup();
        log("Validate total in amount is matched with amount at Member column in outside");
        String memberTotal = bookieStatementPage.getAgentCellValue(masterCode, agentCode,bookieStatementPage.colMember).replace(",","");
        double valueAc = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(memberTotal));
        double valueEx = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(totalMemberGrandVal));
        Assert.assertEquals(valueAc,valueEx,0.01,"FAILED! Total is not matched between inside/outside, Total Outside: " + memberTotal
                + " Total Inside: " + totalMemberGrandVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke","ethan2.0"})
    @TestRails(id = "184")
    public void BookieStatementTC_184() throws IOException, InterruptedException {
        String bookieName = "QA Bookie";
        String bookieCode = "QA01";
        String superMasterCode = "SM-QA1-QA Test";
        String level = "Super";
        String accountCodeDebit = "SM-QA1-QA Test";
        String accountCodeCredit = "SM-FE9-QA Test";

        log("@title: Validate Payment transaction displays correctly");
        log("Precondition: Add txn for Bookie Super in Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Transaction transaction = new Transaction.Builder()
                .amountDebit(1).amountCredit(1).remark("Automation Testing Transaction Bookie: " + DateUtils.getMilliSeconds())
                .transDate(transDate).transType("Tax Rebate").level(level)
                .debitAccountCode(accountCodeDebit).creditAccountCode(accountCodeCredit)
                .build();
        try {
            TransactionUtils.addTransByAPI(transaction,"Bookie",accountCodeDebit,accountCodeCredit,"","",bookieName);

            log("@Step 1: Login with valid account");
            log("@Step 2: Click General Reports > Bookie Statement");
            BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT,BookieStatementPage.class);

            log("@Step 3: Filter with Bookie has made transaction");
            bookieStatementPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR,"Super Master","","",bookieCode,"");
            log("@Step 4: Click on Super Master RPCRBA link");
            BookieSuperMasterDetailPopup bookiePopup = bookieStatementPage.openBookieSuperMasterDetailPopup(superMasterCode);
            String valueDebit = bookiePopup.getValueTransDetail(transaction.getRemark(),"Debit");
            log("Verify 1: Validate for Bookie Super account chosen in Debit section, value will show positive amount");
            Assert.assertEquals(valueDebit,String.format("%.2f",transaction.getAmountDebit()),"FAILED! Amount Debit and RPCRBA Value does not show in positive, Amount Debit:  " + transaction.getAmountDebit()
                    + " RPCRBA: " + valueDebit);
        } finally {
            log("Post-Condition: Add txn for Bookie Super in Credit");
            Transaction transactionPost = new Transaction.Builder()
                    .amountDebit(1).amountCredit(1).remark("Automation Testing Transaction Bookie Post-condition: " + DateUtils.getMilliSeconds())
                    .transDate(transDate).transType("Tax Rebate").level(level)
                    .debitAccountCode(accountCodeCredit).creditAccountCode(accountCodeDebit)
                    .build();
            TransactionUtils.addTransByAPI(transactionPost,"Bookie",accountCodeCredit,accountCodeDebit,"","",bookieName);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"smoke","2024.V.3.0"})
    @TestRails(id = "1052")
    @Parameters({"bookieCode","bookieSuperMasterCode"})
    public void BookieStatementTC_1052(String bookieCode, String bookieSuperMasterCode) throws IOException, InterruptedException {
        String level = "Super";
        String remark = "Transaction between bookie and ledger" + DateUtils.getMilliSeconds();
        String transType = "Payment Other";
        log("@title: Validate users can make transactions successfully between bookie and ledger");
        log("Precondition: A super account in Bookie System that linked to agent-COM in Client System (e.g. SM-QA1-QA Test)\n" +
                "A Ledger account");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        Transaction transaction = new Transaction.Builder()
                .bookieDebit(bookieCode)
                .level(level)
                .debitAccountCode(bookieSuperMasterCode)
                .ledgerCredit(LEDGER_ASSET_CREDIT_NAME+" - "+LEDGER_ASSET_CREDIT_NUMBER)
                .amountDebit(1)
                .amountCredit(1)
                .remark(remark)
                .transDate(date)
                .transType(transType)
                .build();
        log("@Step 1: Access Accounting > Journal Entries");
        JournalEntriesPage page = welcomePage.navigatePage(ACCOUNTING,JOURNAL_ENTRIES, JournalEntriesPage.class);
        log("@Step 2: In Debit section > From dropdown, select 'Bookie'");
        log("@Step 3: Choose a bookie from Bookie dropdown (e.g. 'QA Bookie')");
        log("@Step 4: Choose Client (e.g. QA Client (No.121 Client), and Level is 'Super'");
        log("@Step 5: Input super account in precondition (e.g. SM-QA1-QA Test)");
        log("@Step 6: In Credit section > To dropdown, select 'Ledger'");
        log("@Step 7: Select Ledger account (e.g. QC - HKD - Asset 000.000.000.010)");
        log("@Step 8: Add two accounts to the below tables, then input amount");
        log("@Step 9: Choose Transaction Date and Transaction Types, input Remark if any. Click Submit");
        page.addTransaction(transaction,"Bookie","Ledger",transaction.getRemark(),transaction.getTransDate(),transaction.getTransType(),true,false);
        log("Verify 1: Message informs 'Transaction has been created'");
        String mesAc = page.appArlertControl.getSuscessMessage();
        Assert.assertEquals(mesAc, JournalEntries.MES_TRANS_HAS_BEEN_CREATED,"FAILED! Message displays incorrect");
        log("INFO: Executed completely");
    }
}
