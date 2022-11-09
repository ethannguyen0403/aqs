package testcases.AQSHome;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ess.AccountInfoPage;
import pages.ess.ManagerPage;
import testcases.BaseCaseAQS;
import testcases.BaseCaseAQSTestRails;
import utils.testraildemo.TestRails;

import java.util.ArrayList;

import static common.ESSConstants.HomePage.ACCOUNT;

public class AccountTest extends BaseCaseAQSTestRails {

    /**
     * @title: Verify Account Info Page UI
     * @steps:   1.  Login with valid Username and Password
     * 2. Click Account menu
     * @expect: Validate Account Info table display with correct content:
     * - Account Name
     * - Full Name
     * - Role
     */
    @TestRails(id = "492")
    @Test(groups = {"smoke"})
    public void AccountTC_001(){
        log("@title: Verify Account Page UI");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click Account menu");
        AccountInfoPage accountInfoPage = betOrderPage.activeMenu(ACCOUNT,AccountInfoPage.class);
        log("Validate Account Info table display with correct content:");
        Assert.assertTrue(accountInfoPage.lblAccName.isDisplayed(),"FAILED! Account Name is not displayed");
        Assert.assertTrue(accountInfoPage.lblRole.isDisplayed(),"FAILED! Role is not displayed");
        Assert.assertTrue(accountInfoPage.lblFullName.isDisplayed(),"FAILED! Full Name is not displayed");
        log("INFO: Executed completely");
    }
}
