package testcases.sb11test.generalReports;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.generalReports.ClientBalancePage;
import pages.sb11.generalReports.clientbalance.ClientBalanceDetailPage;
import pages.sb11.generalReports.popup.clientBalance.ClientBalanceDetailPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;


public class ClientBalanceTest extends BaseCaseAQS {
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4354")
    public void Client_Balance_4354() {
        log("@title: Validate that show currency 'HKD' in 'Client Balance' page when filtering Company Unit = All");
        log("@Step 1: Go to General Report >> Client Balance");
        ClientBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.CLIENT_BALANCE,ClientBalancePage.class);
        log("@Step 2: Select valid values with company unit 'All'");
        page.filter("","All","","","");
        log("@Step 3: Click on 'Show' button");
        log("@Step 4: Observe the result");
        log("@Verify 1: Show 'Total Balance HKD'.");
        Assert.assertTrue(page.tblClientBalance.getControlOfCellSPP(1,page.totalCol,1,null).isDisplayed(),"FAILED! Total Balance display incorrect.");
        log("@Verify 2: Show Total In’ dropdown disable");
        Assert.assertFalse(page.ddShowTotal.isEnabled(),"FAILED! Show Total In dropdown display incorrect.");
        log("@Verify 3: In the ‘Balance Detail’, show ‘Total Balance in HKD’ and ‘Grand Total in HKD");
        String clientName = page.tblClientBalance.getControlOfCellSPP(1,page.colClientName,1,null).getText();
        ClientBalanceDetailPopup clientBalanceDetailPage = page.goToClientDetail(clientName);
        Assert.assertEquals(clientBalanceDetailPage.tblClientSuper.getControlOfCell(1, clientBalanceDetailPage.colTotalBalance,1,"span").getText(),
                clientBalanceDetailPage.lblGrandTotalFooter.getText(),"FAILED! Total Balance in HKD display incorrect");
        log("INFO: Executed completely");
    }
}
