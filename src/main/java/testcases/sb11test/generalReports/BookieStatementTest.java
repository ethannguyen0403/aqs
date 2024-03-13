package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;
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

    @Test(groups = {"smoke"})
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
        String winLoseTotalVal = bookieMemberPopup.getTotalCellValue(bookieMemberPopup.colTotalWinLose,true).replace(",","");
        String opWinLoseTotalVal = bookieMemberPopup.getTotalCellValue(bookieMemberPopup.colTotalOpenWinLose,true).replace(",","");
        String rpcrbaTotalVal = bookieMemberPopup.getTotalCellValue(bookieMemberPopup.colTotalRPCRBA,true).replace(",","");
        String openRPCRBATotalVal = bookieMemberPopup.getTotalCellValue(bookieMemberPopup.colTotalOpenRPCRBA,true).replace(",","");
        String openBalanceTotalVal = bookieMemberPopup.getTotalCellValue(bookieMemberPopup.colTotalOpenBalance,true).replace(",","");
        String totalMemberGrandVal = bookieMemberPopup.getMemberTotalCellValue(bookieMemberPopup.colTotal,true).replace(",","");

        log("Validate the amount of Win/Lose, Op. Win/Lose, R/P/C/RB/A, Op. R/P/C/RB/A, Op. Balance in top table is matched with total in amount in the below table");
        Assert.assertEquals(totalVal,totalMemberGrandVal,"FAILED! Total Amount is not matched with Total Grand Amount in below table: Total Amount: " + totalVal
                + " Total Grand Amount: " + totalMemberGrandVal);
        log("Validate total in amount = Win/Lose + Op. Win/Lose + R/P/C/RB/A + Op. R/P/C/RB/A + Op. Balance");
        double expectedVal = Double.parseDouble(winLoseTotalVal) + Double.parseDouble(opWinLoseTotalVal) + Double.parseDouble(rpcrbaTotalVal) +
                Double.parseDouble(openRPCRBATotalVal) + Double.parseDouble(openBalanceTotalVal);
        Assert.assertEquals(String.format("%.2f",expectedVal),totalVal,"FAILED! Grand Total is not matched with sum value, Total: " + totalVal
                + " expected: " + expectedVal);
        bookieMemberPopup.closePopup();
        log("Validate total in amount is matched with amount at Member column in outside");
        String memberTotal = bookieStatementPage.getAgentCellValue(masterCode, agentCode,bookieStatementPage.colMember).replace(",","");
        Assert.assertEquals(memberTotal,totalMemberGrandVal,"FAILED! Total is not matched between inside/outside, Total Outside: " + memberTotal
                + " Total Inside: " + totalMemberGrandVal);

        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @TestRails(id = "184")
    public void BookieStatementTC_184() throws IOException, InterruptedException {
        String bookieName = "QA Bookie";
        String bookieCode = "QA01";
        String superMasterCode = "SM-QA1-QA Test";
        String level = "Super";
        String fromType = "Bookie";
        String accountCodeDebit = "SM-QA1-QA Test";
        String accountCodeCredit = "SM-FE9-QA Test";

        log("@title: Validate Payment transaction displays correctly");
        log("Precondition: Add txn for Bookie Super in Debit");
        String transDate = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Transaction transaction = new Transaction.Builder()
                .amountDebit(1)
                .amountCredit(1)
                .remark("Automation Testing Transaction Bookie: " + DateUtils.getMilliSeconds())
                .transDate(transDate)
                .transType("Tax Rebate")
                .level(level)
                .debitAccountCode(accountCodeDebit)
                .creditAccountCode(accountCodeCredit)
                .build();
        welcomePage.waitSpinnerDisappeared();
        String typeId = BookieInfoUtils.getBookieId(bookieName);
        String accountSuperIdDebit = AccountSearchUtils.getAccountId(accountCodeDebit);
        String accountSuperIdCredit = AccountSearchUtils.getAccountId(accountCodeCredit);
        try {
            TransactionUtils.addClientBookieTxn(transaction,accountSuperIdDebit,accountSuperIdCredit,fromType,typeId);

            log("@Step 1: Login with valid account");
            log("@Step 2: Click General Reports > Bookie Statement");
            BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT,BookieStatementPage.class);

            log("@Step 3: Filter with Bookie has made transaction");
            bookieStatementPage.txtBookieCode.sendKeys(bookieCode);
            bookieStatementPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR,"Super Master","","",bookieCode,"");
            log("@Step 4: Click on Super Master RPCRBA link");
            BookieSuperMasterDetailPopup bookiePopup = bookieStatementPage.openBookieSuperMasterDetailPopup(superMasterCode);
            String rpcrbaVal = bookiePopup.getSuperMasterCellValue(bookiePopup.colRPCRBA, true);
            log("Verify 1: Validate for Bookie Super account chosen in Debit section, value will show positive amount");
            Assert.assertEquals(String.format("%.2f",transaction.getAmountDebit()),rpcrbaVal,"FAILED! Amount Debit and RPCRBA Value does not show in positive, Amount Debit:  " + transaction.getAmountDebit()
                    + " RPCRBA: " + rpcrbaVal);
        } finally {
            log("Post-Condition: Add txn for Bookie Super in Credit");
            Transaction transactionPost = new Transaction.Builder()
                    .amountDebit(1)
                    .amountCredit(1)
                    .remark("Automation Testing Transaction Bookie Post-condition: " + DateUtils.getMilliSeconds())
                    .transDate(transDate)
                    .transType("Tax Rebate")
                    .level(level)
                    .debitAccountCode(accountCodeCredit)
                    .creditAccountCode(accountCodeDebit)
                    .build();
            TransactionUtils.addClientBookieTxn(transactionPost,accountSuperIdCredit,accountSuperIdDebit,fromType,typeId);
        }

        log("INFO: Executed completely");
    }
}
