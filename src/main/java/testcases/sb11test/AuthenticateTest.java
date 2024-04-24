package testcases.sb11test;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

public class AuthenticateTest extends BaseCaseAQS {

    @Test(groups = {"regression","ethan2.0"})
    @Parameters("username")
    @TestRails(id = "2059")
    public void Authenticate_2059(String username){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("Verify that can login successfully");
        Assert.assertEquals(welcomePage.lblUserName.getText(),username,"FAILED! Username is not displayed");
        Assert.assertTrue(welcomePage.btnLogout.isDisplayed(),"FAILED! Profile icon is not displayed");
        Assert.assertTrue(welcomePage.lblWelcome.isDisplayed(),"FAILED! Profile icon is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @Parameters("username")
    @TestRails(id = "2060")
    public void Authenticate_2060(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Logout button");
        welcomePage.logout();
        log("Verify that Camouflag page should display correctly with 6 tabs: HOME, ASIA, NEW ZEALAND, EUROPE, NORTH AMERICA, SOUTH AMERICA");
        log("INFO: Executed completely");
    }
}
