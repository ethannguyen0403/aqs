package testcases.sb11test.trading;

import com.paltech.driver.DriverManager;
import com.paltech.utils.StringUtils;
import common.ESSConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.trading.AccountPercentPage;
import testcases.BaseCaseAQS;
import utils.sb11.AccountPercentUtils;
import utils.sb11.AccountSearchUtils;
import utils.sb11.ClientSystemUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class AccountPercentTest extends BaseCaseAQS {

    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "56")
    @Parameters({"bookieCode","accountCode","clientCode"})
    public void AccountPercent_56(String bookieCode, String accountCode, String clientCode) throws IOException {
        log("@Title: Validate account is able to set in both Account Listing and Account Listing");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        String ptSet  = "1.50000";
        log("@Step 3: Search the account configured in Account Listing page");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        log("@Step 4: Click Edit icon on the account in column Actual WinLoss %");
        try {
            log("@Step 5: Input percent value and click Save icon");
            accountPercentPage.editPercent(accountCode,ptSet);
            log("Verify 1: Able to set percent as normally without any error message");
            Assert.assertEquals(accountPercentPage.getAccPT(accountCode),ptSet,"Failed! Able not to set percent");
        } finally {
            log("@Post-step: set old pt value");
            String accountId = AccountSearchUtils.getAccountId(accountCode);
            String clientId = ClientSystemUtils.getClientId(clientCode);
            Double pt = 1.00000;
            AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode, pt);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "57")
    @Parameters({"password", "userNameOneRole"})
    public void AccountPercent_57(String password, String userNameOneRole) throws Exception{
        log("@Title: Validate accounts without permission cannot access page");
        String accountPercentUrl = environment.getSbpLoginURL() + "#/aqs-report/trading/account-percent";
        log("@Pre-conditions: Account is inactivated permission 'Account Percent'");
        log("@Step 1: Login to SB11 site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        DriverManager.getDriver().get(accountPercentUrl);
        log("@Step 2: https://bggqat.beatus88.com/#/aqs-report/trading/account-percent");
        log("Verify 1: User cannot access page");
        Assert.assertFalse(new AccountPercentPage().lblTitle.isDisplayed(), "FAILED! Account Percent page can access by external link");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "58")
    public void AccountPercent_58() {
        log("@Title: Validate accounts with permission can access page");
        log("@Pre-conditions: Account is activated permission 'Account Percent'");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Expand menu 'Soccer' and access 'Account Percent' page");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("Verify 1: User can access page 'Account Percent' page successfully");
        Assert.assertTrue(accountPercentPage.getTitlePage().contains(ACCOUNT_PERCENT), "FAILED! Account Percent page can not access");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "59")
    @Parameters({"bookieCode"})
    public void AccountPercent_59(String bookieCode) {
        log("@Title: Validate no record found when searching an invalid account code");
        log("@Pre-conditions 1: Account is activated permission 'Account Percent'");
        log("@Pre-conditions 2: Account logged in to site");
        log("@Step 1: Access Soccer > Account Percent page");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 2: Input Account Code with invalid value with sql query");
        accountPercentPage.filterAccount(bookieCode,"","*from");
        log("Verify 1: Message informs 'No record found.'");
        Assert.assertTrue(accountPercentPage.tbAccPercent.getControlOfCell(1,1,1,null).getText().contains(ESSConstants.NO_RECORD_FOUND), "FAILED! Account Percent page displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "60")
    public void AccountPercent_60() {
        log("@Title: Validate layout of page");
        log("@Pre-conditions: User has permission access to Account Percent page");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Observe layout of page");
        log("Verify 1: Page show with\n" +
                "Breadcrumb: Account Percent\n" +
                "Bookie and Type dropdown list\n" +
                "Account Code textbox with Search icon\n" +
                "Result table with columns: #, i, Account Code, Actual WinLoss%, CUR, Client Name");
        accountPercentPage.verifyLayoutOfPage();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2199")
    public void AccountPercent_2199(){
        log("Validate AccountPercent page is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("Validate AccountPercent page is displayed when navigate");
        Assert.assertTrue(accountPercentPage.getTitlePage().contains(ACCOUNT_PERCENT), "Failed! Account Percent page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2200")
    @Parameters({"bookieCode"})
    public void AccountPercent_2200(String bookieCode){
        log("Validate UI on Account Percent is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("Validate UI on Account Percent is correctly displayed");
        log("Dropdown: Bookie, Type");
        Assert.assertTrue(accountPercentPage.ddpBookie.getOptions().contains(bookieCode),"Failed! Bookie dropdown is not displayed");
        Assert.assertEquals(accountPercentPage.ddpType.getOptions(),AccountPercent.TYPE_LIST,"Failed! Type dropdown is not displayed");
        log("Textbox: Account Code");
        Assert.assertEquals(accountPercentPage.lblAccountCode.getText(),"Account Code","Failed! Type dropdown is not displayed");
        log("Button: Search");
        Assert.assertTrue(accountPercentPage.btnSearch.isDisplayed(),"Failed! Search button is not displayed!");
        log("Validate Account Percent table is displayed with correct header");
        Assert.assertEquals(accountPercentPage.tbAccPercent.getHeaderNameOfRows(),AccountPercent.TABLE_HEADER,"Failed! Account Percent table is displayed incorrect header");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2201")
    @Parameters({"bookieCode","accountCode"})
    public void AccountPercent_2201(String bookieCode, String accountCode){
        String PT  = "1.00000";
        log("Validate can update account percent successfully");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Enter Account Code");
        log("@Step 4: Click Search");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        log("@Step 5: Click Edit button");
        log("@Step 6: Enter account percent data > click Save");
        accountPercentPage.editPercent(accountCode,PT);
        log("Validate updated account percent is displayed correctly at Actual WinLoss % column");
        Assert.assertEquals(accountPercentPage.getAccPT(accountCode),PT,"Failed! Updated account percent is not matched!");
        log("INFO: Executed completely");
    }

}
