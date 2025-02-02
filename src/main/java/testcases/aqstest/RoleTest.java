package testcases.aqstest;

import common.ESSConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ess.RolePage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.ESSConstants.HomePage.ROLE;
import static common.ESSConstants.RolePage.PERMISSION_LIST;
import static common.ESSConstants.RolePage.ROLE_LIST;

public class RoleTest extends BaseCaseAQS {


    @TestRails(id = "489")
    @Test(groups = {"smoke"})
    public void RoleTC_489(){
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


    @TestRails(id = "490")
    @Test(groups = {"smoke"})
    public void RoleTC_490(){
        log("@title: Verify data in Role Page");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click on Role menu");
        RolePage rolePage = betOrderPage.activeMenu(ROLE,RolePage.class);

        log("Verify that Role list are displayed correctly");
        List<String> roleList= rolePage.getRoleList();
        Assert.assertTrue(roleList.contains("Administrator"),"Failed! Administrator role is not displayed");
        Assert.assertTrue(roleList.contains("Agent"),"Failed! Agent role is not displayed");
        //[Isabella] Should Check data in stg and pro is different
       // Assert.assertTrue(roleList.contains("Trader"),"Failed! Trader role is not displayed");
        log("Verify that Permissions list permission is correctly");
        List<String> permissionLst = rolePage.getPermissionList();
        Assert.assertEquals(permissionLst, PERMISSION_LIST, "Failed! Permissions list is incorrect displayed");

        log("INFO: Executed completely");
    }
}
