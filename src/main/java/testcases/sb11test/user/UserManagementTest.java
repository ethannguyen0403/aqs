package testcases.sb11test.user;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.user.UserManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class UserManagementTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2065")
    @Parameters("username")
    public void User_Management_TC_2065(String username){
        log("@title: Validate User Management page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > User Management");
        UserManagementPage userManagementPage = welcomePage.navigatePage(USER,USER_MANAGEMENT,UserManagementPage.class);
        log("Validate the page is displayed with correct title page");
        Assert.assertTrue(userManagementPage.getTitlePage().contains(USER_MANAGEMENT), "Failed! User Management page is not displayed");
        log("Validate that list account contains the login account");
        Assert.assertTrue(userManagementPage.isAccountDisplayed(username), "Failed! " + username + " is not displayed!");
        log("INFO: Executed completely");
    }
}
