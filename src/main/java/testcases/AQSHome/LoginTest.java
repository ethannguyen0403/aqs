package testcases.AQSHome;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testcases.BaseCaseAQS;
import testcases.BaseCaseAQSTestRails;
import utils.testraildemo.TestRails;

public class LoginTest extends BaseCaseAQS {

    /**
     * @title: Verify that can login successfully
     * @steps:   1. Input Username/Password
     * 2. Click Login button
     * @expect: Verify can login success
     * - Username label display
     * - Profile icon display
     */
    @TestRails(id = "459")
    @Test(groups = {"smoke"})
    @Parameters("username")
    public void LoginTC_001(String username){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("Verify that can login successfully");
        Assert.assertEquals(betOrderPage.lblUserName.getText(),username,"FAILED! Username is not displayed");
        Assert.assertTrue(betOrderPage.profileIcon.isDisplayed(),"FAILED! Profile icon is not displayed");
        log("INFO: Executed completely");
    }
}
