package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.master.AutoCreatedAccountsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class AutoCreatedAccountsTest extends BaseCaseAQS {

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2232")
    public void Auto_Created_Accounts_TC_2232(){
        log("@title: Validate Auto-created Accounts page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Auto-created Accounts");
        AutoCreatedAccountsPage autoCreatedAccountsPage = welcomePage.navigatePage(MASTER, AUTO_CREATED_ACCOUNTS,AutoCreatedAccountsPage.class);
        log("Validate Auto-created Accounts page is displayed with correctly title");
        Assert.assertTrue(autoCreatedAccountsPage.getTitlePage().contains(AUTO_CREATED_ACCOUNTS),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2233")
    public void Auto_Created_Accounts_TC_2233(){
        log("@title: Validate UI on Auto-created Accounts is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Auto-created Accounts");
        AutoCreatedAccountsPage autoCreatedAccountsPage = welcomePage.navigatePage(MASTER, AUTO_CREATED_ACCOUNTS,AutoCreatedAccountsPage.class);
        log("Validate UI Info display correctly");
        log("Datetime picker: From Date, To Date");
        Assert.assertTrue(autoCreatedAccountsPage.lblFromDate.getText().contains("From Date"),"Failed! From Date datetimepicker is not displayed!");
        Assert.assertTrue(autoCreatedAccountsPage.lblToDate.getText().contains("To Date"),"Failed! To Date datetimepicker is not displayed!");
        log("Button: Search");
        Assert.assertEquals(autoCreatedAccountsPage.btnShow.getText(),"SHOW","Failed! Show button is not displayed!");
        log("Validate Auto-created Accounts table is displayed with correctly header column");
        Assert.assertEquals(autoCreatedAccountsPage.tbAutoCreate.getHeaderNameOfRows(), AutoCreatedAccounts.TABLE_HEADER,"Failed! Auto-created Accounts table is displayed with incorrectly header column");
        log("INFO: Executed completely");
    }
}
