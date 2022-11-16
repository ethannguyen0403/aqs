package testcases.testrailtest.AQSTest;

import common.ESSConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ess.RolePage;
import testcases.BaseCaseAQSTestRails;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.ESSConstants.HomePage.ROLE;
import static common.ESSConstants.RolePage.PERMISSION_LIST;
import static common.ESSConstants.RolePage.ROLE_LIST;

public class RoleAQSTest extends BaseCaseAQSTestRails {

    /**
     * @title: Verify Role Page UI
     * @steps:   1.  Login with valid Username and Password
     * 2. Click on Role menu
     * @expect: Verify that
     * - Roles list is displayed with correct header
     * - Permissions list is displayed with correct header
     */
    @TestRails(id = "4")
    @Test(groups = {"smoke1"})
    public void RoleTC_001(){
        log("@title: Verify Role Page UI");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click on Role menu");
        RolePage rolePage = betOrderPage.activeMenu(ROLE,RolePage.class);

        log("Verify Roles list is displayed with correct header");
        Assert.assertEquals(rolePage.lblRoles.getText(), ESSConstants.RolePage.ROLES,"Failed! Role list is not displayed");

        log("Verify Permissions list is displayed with correct header");
        Assert.assertEquals(rolePage.lblPermissions.getText(),ESSConstants.RolePage.PERMISSIONS,"Failed! Permission list is not displayed");
        log("INFO: Executed completely");
    }

    /**
     * @title: Verify data in Role Page
     * @steps:   1.  Login with valid Username and Password
     * 2. Click on Role menu
     * @expect: Verify that Roles and  Permission display correctly:
     * - Role: Administrator and Agent
     * - Permissions: There are 14 permissions in the list
     */
    @TestRails(id = "6")
    @Test(groups = {"smoke"})
    public void RoleTC_002(){
        log("@title: Verify data in Role Page");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click on Role menu");
        RolePage rolePage = betOrderPage.activeMenu(ROLE,RolePage.class);

        log("Verify that Role list are displayed correctly");
        Assert.assertEquals(rolePage.getRoleList(), ROLE_LIST,"Failed! Agent role is not displayed");

        log("Verify that Permissions list permission is correctly");
        List<String> permissionLst = rolePage.getPermissionList();
        Assert.assertEquals(permissionLst, PERMISSION_LIST, "Failed! Permissions list is incorrect displayed");

        log("INFO: Executed completely");
    }
}
