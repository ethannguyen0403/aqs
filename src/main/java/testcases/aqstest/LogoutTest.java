package testcases.aqstest;

import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

public class LogoutTest extends BaseCaseAQS {

    @TestRails(id = "460")
    @Test(groups = {"smoke"})
    public void LogoutTC_C460(){
        log("@title: Verify that can logout successfully");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click Logout button");
        betOrderPage.logout();
        loginAQSPage.btnCopyRight.click();

        log("Verify that can logout successfully");
        Assert.assertFalse(loginAQSPage.btnLogin.isDisplayed(), "FAILED! Login button does not display");
        Assert.assertFalse(loginAQSPage.txtUsername.isDisplayed(),"FAILED! Username field does not display");
        Assert.assertFalse(loginAQSPage.txtPassword.isDisplayed(),"FAILED! Password field does not display");
        Assert.assertTrue(loginAQSPage.tabHome.isDisplayed(),"FAILED! Tab Home is displat");
        log("INFO: Executed completely");
    }
}
