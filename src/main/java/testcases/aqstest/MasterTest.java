package testcases.aqstest;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ess.MasterAccountPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.ArrayList;

import static common.ESSConstants.HomePage.MASTERACCOUNT;

public class MasterTest extends BaseCaseAQS {

    @TestRails(id = "493")
    @Test(groups = {"smoke"})
    public void MasterTC_001(){
        log("@title: Verify Master Account Page UI");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click Master Account menu");
        MasterAccountPage masterAccountPage = betOrderPage.activeMenu(MASTERACCOUNT,MasterAccountPage.class);
        log("Verify Master Account table display with correct content");
        ArrayList<String> colMasterAccount = masterAccountPage.tbMasterAccount.getColumnNamesOfTable();

        Assert.assertTrue(colMasterAccount.contains("No"), "Failed! No column is not displayed!");
        Assert.assertTrue(colMasterAccount.contains("Master Account"), "Failed! Master Account column is not displayed!");
        Assert.assertTrue(masterAccountPage.btnAdd.isDisplayed(), "Failed! Add button is not displayed!");
        log("INFO: Executed completely");
    }
}
