package testcases.AQSHome;

import common.ESSConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ess.RolePage;
import testcases.BaseCaseAQS;

import static common.ESSConstants.HomePage.ROLE;

public class RoleTest extends BaseCaseAQS {

    /**
     * @title: Verify Role Page UI
     * @steps:   1.  Login with valid Username and Password
     * 2. Click on Role menu
     * @expect: Verify that
     * - Roles list is displayed with correct header
     * - Permissions list is displayed with correct header
     */

    @Test(groups = {"smoke"})
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

    @Test(groups = {"smoke"})
    public void RoleTC_002(){
        log("@title: Verify data in Role Page");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click on Role menu");
        RolePage rolePage = homePage.activeMenu(ROLE,RolePage.class);

        log("Verify that Role: Administrator and Agent are displayed correctly");
        Assert.assertTrue(rolePage.isRoleDisplayed("Administrator"),"Failed! Administrator role is not displayed");
        Assert.assertTrue(rolePage.isRoleDisplayed("Agent"),"Failed! Agent role is not displayed");
        log("Verify that Permissions: There are 14 permissions in the list");
        int ttRowPermissions = rolePage.tbPermissions.getNumberOfRows(false,false);
        Assert.assertEquals(ttRowPermissions, 14, "Failed! Only " + ttRowPermissions + " permissions are displayed!");
        log("INFO: Executed completely");
    }
}
