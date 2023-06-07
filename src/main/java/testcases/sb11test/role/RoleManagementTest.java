package testcases.sb11test.role;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class RoleManagementTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2064")
    public void Role_Management_TC_001(){
        log("@title: Validate Role Management page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Role > Role Management");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE,ROLE_MANAGEMENT,RoleManagementPage.class);
        log("Validate the page is displayed with correct 2 tables: Roles and Permissions");
        Assert.assertTrue(roleManagementPage.getTitlePage().contains("Roles"), "Failed! Role page is not displayed");
        Assert.assertTrue(roleManagementPage.tbRole.isDisplayed(),"Failed! Role table is not displayed");
        Assert.assertTrue(roleManagementPage.tbPermissions.isDisplayed(),"Failed! Permission table is not displayed");
        log("INFO: Executed completely");
    }
}
