package testcases.aqstest;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ess.AccountInfoPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.ESSConstants.HomePage.ACCOUNT;

public class AccountTest extends BaseCaseAQS {

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
