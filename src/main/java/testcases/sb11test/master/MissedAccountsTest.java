package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.master.AccountListPage;
import pages.sb11.master.MissedAccountsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class MissedAccountsTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2230")
    public void Missed_Accounts_TC_001(){
        log("@title: Validate Missed Accounts page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Missed Accounts");
        MissedAccountsPage missedAccountsPage = welcomePage.navigatePage(MASTER, MISSED_ACCOUNTS,MissedAccountsPage.class);
        log("Validate Missed Accounts page is displayed with correctly title");
        Assert.assertTrue(missedAccountsPage.getTitlePage().contains(MISSED_ACCOUNTS),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2231")
    public void Missed_Accounts_TC_2231(){
        log("@title: Validate UI on Missed Accounts page is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Missed Accounts");
        MissedAccountsPage missedAccountsPage = welcomePage.navigatePage(MASTER, MISSED_ACCOUNTS,MissedAccountsPage.class);
        log("Validate there are 3 tables displayed with correctly title:BetISN, Pinnacle, Fair999");
        Assert.assertEquals(missedAccountsPage.lblBetISN.getText(),"BET ISN","Failed! BetISN table is not displayed!");
        Assert.assertEquals(missedAccountsPage.lblPinnacle.getText(),"PINNACLE","Failed! Pinnacle table is not displayed!");
        Assert.assertEquals(missedAccountsPage.lblFair999.getText(),"Fair999","Failed! Fair999 table is not displayed!");

        log("INFO: Executed completely");
    }
}
