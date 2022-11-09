package testcases.AQSHome;

import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.BaseCaseAQS;
import testcases.BaseCaseAQSTestRails;
import utils.testraildemo.TestRails;

public class LogoutTest extends BaseCaseAQSTestRails {

    /**
     * @title: Verify that can logout successfully
     * @steps:   1.  Login with valid Username and Password
     * 2. Click Logout button
     * @expect: Verify can logout success
     * - Username field display
     * - Password filed display
     * - Login button display
     */

    @TestRails(id = "460")
    @Test(groups = {"smoke1"})
    public void LogoutTC_001(){
        log("@title: Verify that can logout successfully");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click Logout button");
        betOrderPage.logout();
        loginPage.btnCopyRight.click();

        log("Verify that can logout successfully");
        Assert.assertTrue(loginPage.btnLogin.isDisplayed(), "FAILED! Login button does not display");
        Assert.assertTrue(loginPage.txtUsername.isDisplayed(),"FAILED! Username field does not display");
        Assert.assertTrue(loginPage.txtPassword.isDisplayed(),"FAILED! Password field does not display");
        log("INFO: Executed completely");
    }
}
