package testcases.sb11test.trading;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.trading.AccountPercentPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class AccountPercentTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2199")
    public void AccountPercent_TC001(){
        log("Validate AccountPercent page is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("Validate AccountPercent page is displayed when navigate");
        Assert.assertEquals(accountPercentPage.getTitlePage(),ACCOUNT_PERCENT, "Failed! Account Percent page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2200")
    @Parameters({"bookieCode"})
    public void AccountPercent_TC002(String bookieCode){
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

    @Test(groups = {"regression1"})
    @TestRails(id = "2201")
    @Parameters({"bookieCode","accountCode"})
    public void AccountPercent_TC003(String bookieCode, String accountCode){
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
