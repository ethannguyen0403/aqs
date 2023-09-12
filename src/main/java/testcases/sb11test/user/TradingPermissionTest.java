package testcases.sb11test.user;

import com.paltech.element.common.Icon;
import common.ESSConstants;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.user.TradingPermissionPage;
import pages.sb11.user.UserManagementPage;
import pages.sb11.user.popup.ClientAgentPermissionPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class TradingPermissionTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2066")
    public void Trading_Permission_TC_001(){
        log("@title: Validate User Management page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("Validate the page is displayed with correct title page");
        Assert.assertTrue(tradingPermissionPage.getTitlePage().contains(TRADING_PERMISSION), "Failed! Trading Permission page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2067")
    public void Trading_Permission_TC_002(){
        log("@title: Validate User Management page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("Validate UI Info display correctly");
        log("Controls: Company Unit, User Role, Username and Show button");
        Assert.assertEquals(tradingPermissionPage.ddpCompanyUnit.getOptions(), COMPANY_UNIT_LIST,"FAILED! Company Unit dropdown is not displayed!");
        Assert.assertTrue(tradingPermissionPage.ddpUserRole.getOptions().contains("Administrator"), "FAILED! Company Unit dropdown is not displayed!");
        Assert.assertEquals(tradingPermissionPage.lblUsername.getText(), "Username","FAILED! Username textbox is not displayed!");
        Assert.assertEquals(tradingPermissionPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("Customer table header columns is correctly display");
        Assert.assertEquals(tradingPermissionPage.tbTradPermission.getHeaderNameOfRows(), TradingPermission.TABLE_HEADER,"FAILED! Pending table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2068")
    @Parameters("username")
    public void Trading_Permission_TC_2068(String username){
        log("@title: Validate that can search username succesfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("@Step 3: Select Company Unit, User Role and enter an exist username on list");
        log("@Step 4: Click Show");
        tradingPermissionPage.filterAccount(COMPANY_UNIT,"All",username);
        log("Validate searched user is displayed correctly on Customer table");
        Assert.assertEquals(tradingPermissionPage.tbTradPermission.getColumn(tradingPermissionPage.colUsername,10,false).get(0),username,"Failed! " + username + " is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2069")
    @Parameters("username")
    public void Trading_Permission_TC_2069(String username){
        log("@title: Validate that all Permission is disabled after checking Auto-assigned All");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("@Step 3: Check Auto-assigned All checkbox of any item");
        tradingPermissionPage.filterAccount(COMPANY_UNIT,"All",username);
        log("Validate that all Permission is disabled: Client Agent, Client, Smart (M), Smart (A), Smart (G)");
        tradingPermissionPage.verifyPermissionEnabled(username,false);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2070")
    @Parameters("username")
    public void Trading_Permission_TC_2070(String username){
        log("@title: Validate that all Permission is enabled after checking Auto-assigned All");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on User > Trading Permission page");
        TradingPermissionPage tradingPermissionPage = welcomePage.navigatePage(USER,TRADING_PERMISSION,TradingPermissionPage.class);
        log("@Step 3: Uncheck Auto-assigned All checkbox of any item");
        tradingPermissionPage.filterAccount(COMPANY_UNIT,"All",username);
        log("Validate that all Permission is enabled and can be clickable");
        try{
            tradingPermissionPage.autoAssignAll(username,true);
            tradingPermissionPage.verifyPermissionEnabled(username,true);
            log("INFO: Executed completely");
            log("Post condition: Enable auto assign");
        } finally {
            tradingPermissionPage.autoAssignAll(username,true);
        }
    }
}
