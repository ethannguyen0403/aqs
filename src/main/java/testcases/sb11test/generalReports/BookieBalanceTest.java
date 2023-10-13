package testcases.sb11test.generalReports;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.generalReports.BookieBalancePage;
import pages.sb11.generalReports.bookiebalance.BalanceDetailPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

public class BookieBalanceTest extends BaseCaseAQS {
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4355")
    public void Client_Balance_4355() {
        log("@title: Validate that show currency 'HKD' in 'Bookie Balance' page when filtering Company Unit = All ");
        log("@Step 1: Go to General Report >> Bookie Balance");
        BookieBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.BOOKIE_BALANCE,BookieBalancePage.class);
        log("@Step 2: Select valid values with company unit 'All'");
        page.filter("All","","","","");
        log("@Step 3: Click on 'Show' button");
        log("@Step 4: Observe the result");
        log("@Verify 1: Show 'Total Balance HKD'.");
        Assert.assertFalse(page.tblBookie.getColumn(page.colTotalInHKD,false).isEmpty(),"FAILED! Total Balance display incorrect.");
        log("@Verify 2: Show Total In’ dropdown disable");
        Assert.assertFalse(page.ddShowTotalIn.isEnabled(),"FAILED! Show Total In dropdown display incorrect.");
        log("@Verify 3: In the ‘Balance Detail’, show ‘Total Balance in HKD’ and ‘Grand Total in HKD");
        String bookieName = page.tblBookie.getColumn(page.colBookie,false).get(0);
        BalanceDetailPage balanceDetailPage = page.openBalanceDetailByBookie(bookieName);
        Assert.assertTrue(balanceDetailPage.tblGrandTotal.getHeaderNameOfRows().contains("Grand Total in HKD"),"FAILED! Grand Total in HKD display incorrect");
        log("INFO: Executed completely");
    }
}
