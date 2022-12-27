package testcases.aqstest;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ess.ManagerPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.ArrayList;

import static common.ESSConstants.HomePage.MANAGER;

public class ManagerTest extends BaseCaseAQS {

    @TestRails(id = "491")
    @Test(groups = {"smoke"})
    @Parameters("username")
    public void ManagerTC_001(String username){
        log("@title: Verify Manager Page UI");
        log("@Step 1: Login with valid Username and Password");
        log("@Step 2: Click Manager menu");
        ManagerPage managerPage = betOrderPage.activeMenu(MANAGER,ManagerPage.class);

        log("Verify User Management table display with correct header");
        ArrayList<String> colUserManagement = managerPage.tbUserManagement.getColumnNamesOfTable();

        Assert.assertTrue(colUserManagement.contains("No"), "Failed! No column is not displayed!");
        Assert.assertTrue(colUserManagement.get(1).contains("Account Name"), "Failed! Account Name column is not displayed!");
        Assert.assertTrue(managerPage.btnCreate.isDisplayed(), "Failed! Button Create is not displayed!");
        Assert.assertTrue(colUserManagement.contains("Full Name"), "Failed! Full Name column is not displayed!");
        Assert.assertTrue(colUserManagement.contains("Role"), "Failed! Role column is not displayed!");
        Assert.assertTrue(colUserManagement.contains("Status"), "Failed! Status column is not displayed!");
        log("Verify that list account contains the login account");
        Assert.assertTrue(managerPage.isAccountDisplayed(username.toLowerCase()), "Failed! " + username + " is not displayed!");
        log("INFO: Executed completely");
    }
}


