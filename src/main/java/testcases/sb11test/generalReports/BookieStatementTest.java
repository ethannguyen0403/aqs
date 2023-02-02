package testcases.sb11test.generalReports;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.BookieStatementPage;
import pages.sb11.generalReports.popup.BookieSuperMasterDetailPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BookieStatementTest extends BaseCaseAQS {
    String bookieName = "QA Bookie";
    String superMasterCode = "SM-QA1-QA Test";

    @Test(groups = {"smoke"})
    @TestRails(id = "183")
    @Parameters({"bookieCode"})
    public void BookieStatementTC_183(String bookieCode) throws InterruptedException {
        bookieCode = bookieCode + " - " + bookieName;
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
        log("Step 2: Click General Reports > Bookie Statement");
        BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, BOOKIE_STATEMENT,BookieStatementPage.class);

        log("Filter with Bookie has made transaction");
        bookieStatementPage.filter(COMPANY_UNIT, FINANCIAL_YEAR,"Super Master","","",bookieCode);
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

        Assert.assertEquals(String.format("%.2f",expectedVal),superMasterTotalHKDVal,"FAILED");
        Assert.assertEquals(superMasterTotalHKDVal,superGrandTotalHKDVal);

        log("INFO: Executed completely");
    }
}
