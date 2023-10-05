package testcases.sb11test.financialReports;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.financialReports.BalanceSheetPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

public class BalanceSheetTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2780")
    @Parameters({"password"})
    public void Balance_Sheet_2780(String password) throws Exception {
        String accountNoPermission = "onerole";
        log("@title: Validate Balance Sheet menu is hidden if not active Balance Sheet permission");
        log("@Pre-condition: Balance Sheet permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        welcomePage.logout();
        loginsb11("onerole",password,true);

        log("@Step 2: Expand Financial Reports");
        log("@Verify 1: Balance Sheet menu does not display");
        Assert.assertFalse(welcomePage.isPageDisplayCorrect(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET),"FAILED! Balance Sheet displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2781")
    public void Balance_Sheet_2781() {
        log("@title: Validate Balance Sheet menu displays if active Balance Sheet permission");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand Financial Reports");
        log("@Verify 1: Balance Sheet menu displays");
        Assert.assertTrue(welcomePage.isPageDisplayCorrect(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET),"Failed! Balance Sheet displayed incorrect!");
        log("@Step 3: Click Balance Sheet menu");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Verify 2: Will redirect to Balance Sheet page properly");
        Assert.assertTrue(page.getTitlePage().contains(SBPConstants.BALANCE_SHEET),"FAILED! Title page displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2784")
    public void Balance_Sheet_2784() {
        log("@title: Validate an ASSET section displays on the left side");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
        log("@Verify 1: An ASSET section displays on the left side");
        Assert.assertTrue(page.isSectionDisplayCorrect("ASSET"),"FAILED! ASSET section displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2785")
    public void Balance_Sheet_2785() {
        log("@title: Validate LIABILITY and CAPITAL section display on the right side");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
        log("@Verify 1: LIABILITY and CAPITAL section display on the right side");
        Assert.assertTrue(page.isSectionDisplayCorrect("LIABILITY"),"FAILED! LIABILITY section displays incorrect.");
        Assert.assertTrue(page.isSectionDisplayCorrect("CAPITAL"),"FAILED! CAPITAL section displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2786")
    public void Balance_Sheet_2786() {
        log("@title: Validate Parent Accounts will be grouped by Detail Types");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
        log("@Verify 1: Parent Accounts will be grouped by Detail Types");
        log("INFO: Executed completely");
    }
}
