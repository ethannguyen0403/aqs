package testcases.sb11test.financialReports;

import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.BalanceSheetPage;
import pages.sb11.financialReports.TrialBalancePage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class TrialBalanceTest extends BaseCaseAQS {

    @TestRails(id = "2764")
    @Test(groups = {"Revise"})
    public void Trial_Balance_C2764() {
        log("@title: Validate Report data of 'Before CJE' and 'After CJE' will be different only when selecting 'Month' = July.");
        log("@Step 1: Navigate to SB11 > Financial Reports > Trial Balance");
        TrialBalancePage trialBalancePage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Verify 1: Report is disabled with 'Before CJE' option as default when Month is not 'July'");
        trialBalancePage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - November", "");
        Assert.assertTrue(!trialBalancePage.ddReport.isEnabled(), "FAILED! The report dropdown is enable.");
        log("@Verify 2: Report is enabled with 2 options 'Before CJE' and 'After CJE' when Month = 'July'");
        trialBalancePage.ddMonth.selectByVisibleText("2023 - July");
        welcomePage.waitSpinnerDisappeared();
        Assert.assertTrue(trialBalancePage.ddReport.isEnabled() && trialBalancePage.ddReport.getOptions().equals(REPORT_TYPE),
                "FAILED! The report dropdown is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2773")
    @Test(groups = {"regression", "2023.11.30"})
    public void Trial_Balance_C2773() {
        log("@title: Validate Debit/Credit data is matched correctly with Ledger Statement page");
        log("@Step 1: Navigate to SB11 > Financial Reports > Trial Balance");
        TrialBalancePage trialBalancePage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Verify 1: Report is disabled with 'Before CJE' option as default when Month is not 'July'");
        trialBalancePage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - November", "");
        Assert.assertTrue(!trialBalancePage.ddReport.isEnabled(), "FAILED! The report dropdown is enable.");
        log("@Verify 2: Report is enabled with 2 options 'Before CJE' and 'After CJE' when Month = 'July'");
        trialBalancePage.ddMonth.selectByVisibleText("2023 - July");
        welcomePage.waitSpinnerDisappeared();
        Assert.assertTrue(trialBalancePage.ddReport.isEnabled() && trialBalancePage.ddReport.getOptions().equals(REPORT_TYPE),
                "FAILED! The report dropdown is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2779")
    @Test(groups = {"regression", "2023.11.30"})
    @Parameters({"password", "userNameOneRole"})
    public void Trial_Balance_C2779(String password, String userNameOneRole) throws Exception {
        log("@title: Validate user can not access Trial Balance page when having no permission");
        log("Precondition: Deactivate Trial Balance option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(TRIAL_BALANCE, false);
        roleManagementPage.selectRole("one role").switchPermissions(BALANCE_SHEET, true);
        log("@Step 1: Re-login with one role account account has 'Trial Balance' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        BalanceSheetPage balanceSheetPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET, BalanceSheetPage.class);
        log("@Verify 1: 'Trial Balance' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, TRIAL_BALANCE), "FAILED! Trial balance menu is displayed");
        log("INFO: Executed completely");
    }

}
