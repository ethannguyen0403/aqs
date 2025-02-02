package testcases.sb11test.user;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.user.TradingPermissionPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class TradingPermissionTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2066")
    public void Trading_Permission_TC_2066(){
        log("@title: Validate User Management page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("Validate the page is displayed with correct title page");
        Assert.assertTrue(tradingPermissionPage.getTitlePage().contains(TRADING_PERMISSION), "Failed! Trading Permission page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan5.0"})
    @TestRails(id = "2067")
    public void Trading_Permission_TC_2067(){
        log("@title: Validate UI on Trading Permission is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("Validate UI Info display correctly");
        tradingPermissionPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan5.0"})
    @TestRails(id = "2068")
    @Parameters("username")
    public void Trading_Permission_TC_2068(String username){
        log("@title: Validate that can search username succesfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("@Step 3: Select Company Unit, User Role and enter an exist username on list");
        log("@Step 4: Click Show");
        tradingPermissionPage.filterAccount(KASTRAKI_LIMITED,"All",username);
        log("Validate searched user is displayed correctly on Customer table");
        Assert.assertEquals(tradingPermissionPage.tbTradPermission.getColumn(tradingPermissionPage.colUsername,10,false).get(0),username,"Failed! " + username + " is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_stg","ethan5.0"})
    @TestRails(id = "2069")
    @Parameters("username")
    public void Trading_Permission_TC_2069(String username) throws InterruptedException {
        log("@title: Validate that all Permission is disabled after checking Auto-assigned All");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("@Step 3: Check Auto-assigned All checkbox of any item");
        tradingPermissionPage.filterAccount(KASTRAKI_LIMITED,"All",username);
        tradingPermissionPage.clickAutoAssignAll(username,true);
        log("Validate that all Permission is disabled: Client Agent, Client, Smart (M), Smart (A), Smart (G)");
        tradingPermissionPage.verifyPermissionEnabled(username,false);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression_stg","ethan5.0"})
    @TestRails(id = "2070")
    @Parameters("userNameOneRole")
    public void Trading_Permission_TC_2070(String username) throws InterruptedException {
        log("@title: Validate that all Permission is enabled after checking Auto-assigned All");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("@Step 3: Uncheck Auto-assigned All checkbox of any item");
        tradingPermissionPage.filterAccount(KASTRAKI_LIMITED,"All",username);
        log("Validate that all Permission is enabled and can be clickable");
        try{
            tradingPermissionPage.clickAutoAssignAll(username,false);
            tradingPermissionPage.verifyPermissionEnabled(username,true);
            log("INFO: Executed completely");
            log("Post condition: Enable auto assign");
        } finally {
            tradingPermissionPage.clickAutoAssignAll(username,true);
        }
    }
}
