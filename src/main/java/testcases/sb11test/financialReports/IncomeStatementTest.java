package testcases.sb11test.financialReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.IncomeStatementPage;
import pages.sb11.financialReports.RetainedEarningsPage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;


public class IncomeStatementTest extends BaseCaseAQS {

    @TestRails(id = "2774")
    @Test(groups = {"regression", "2023.11.30","ethan4.0"})
    public void Income_Statement_TC2774() {
        log("@title: Validate Operating Income is only displayed details types with chart codes from 400 to 459");
        log("@Step 1: Access to SB11 > Financial Reports > Income Statement");
        IncomeStatementPage incomeStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT, IncomeStatementPage.class);
        log("@Step 2: Filter with current Month");
        incomeStatementPage.filterIncomeReport(KASTRAKI_LIMITED, "", "", "");
        log("@Verify 1: Validate chart code of Operating Income in range 400 to 459");
        List<String> chartCodeOperationIncome = incomeStatementPage.getListAccountCode(incomeStatementPage.findTableIndex("OPERATING INCOME"));
        Assert.assertTrue(incomeStatementPage.verifyCodeStartingInRange(chartCodeOperationIncome, 400, 470), "FAILED! Chart code Operation Income NOT in range 400 to 459");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2775")
    @Test(groups = {"regression", "2023.11.30"})
    public void Income_Statement_TC2775() {
        log("@title: Validate Operating Expense is only displayed detail types with chart codes starting with 5, 6");
        log("@Step 1: Access to SB11 > Financial Reports > Income Statement");
        IncomeStatementPage incomeStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT, IncomeStatementPage.class);
        log("@Step 2: Filter with current Month");
        incomeStatementPage.filterIncomeReport(KASTRAKI_LIMITED, "", "", "");
        log("@Verify 1: Validate chart code of Operating expenses are started with 5, 6");
        List<String> chartCodeOperationIncome = incomeStatementPage.getListAccountCode(incomeStatementPage.findTableIndex("OPERATING EXPENSES"));
        Assert.assertTrue(incomeStatementPage.verifyAllCodeStartWithNumber(chartCodeOperationIncome, "5", "6"), "FAILED! Chart code Operation expenses are not start with 5, 6");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2776")
    @Test(groups = {"regression", "2023.11.30","ethan5.0"})
    public void Income_Statement_TC2776() {
        log("@title: Validate Non-Operating Income is only displayed details types with chart code starting from 460 to 5xx");
        log("@Step 1: Access to SB11 > Financial Reports > Income Statement");
        IncomeStatementPage incomeStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT, IncomeStatementPage.class);
        log("@Step 2: Filter with current Month");
        incomeStatementPage.filterIncomeReport(KASTRAKI_LIMITED, "", "", "");
        log("@Verify 1: Validate chart code of Non-Operating Income in range 460 to 5xx");
        List<String> chartCodeOperationIncome = incomeStatementPage.getListAccountCode(incomeStatementPage.findTableIndex("NON-OPERATING INCOME"));
        Assert.assertTrue(incomeStatementPage.verifyCodeStartingInRange(chartCodeOperationIncome, 460, 500), "FAILED! Chart code Operation Income NOT in range 460 to 5xx");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2777")
    @Test(groups = {"regression", "2023.11.30","ethan4.0"})
    public void Income_Statement_TC2777() {
        String downloadPathExcel = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "income-statement.xlsx";
        String downloadPathPDF = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "income-statement.pdf";
        log("@title: Validate can Export To Excel and Export to PDF successfully ");
        log("@Step 1: Access to SB11 > Financial Reports > Income Statement");
        IncomeStatementPage incomeStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT, IncomeStatementPage.class);
        log("@Step 2: Filter with current Month");
        incomeStatementPage.filterIncomeReport(KASTRAKI_LIMITED, "", "", "");
        log("@Step 2: Click on Export to excel and Export to pdf button");
        incomeStatementPage.exportFile("Excel");
        incomeStatementPage.exportFile("PDF");
        log("@Verify 1: Validate can export Income statement to PDF file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPathPDF), "Failed to download pdf Expected document");
        log("@Verify 1: Validate can export Income statement to excel file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPathExcel), "Failed to download excel Expected document");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2778")
    @Test(groups = {"regression_stg", "2023.11.30"})
    @Parameters({"password", "userNameOneRole"})
    public void Income_Statement_TC2778(String password, String userNameOneRole) throws Exception {
        log("@title: Validate user can not access Income Statement page when having no permission");
        log("Precondition: Deactivate Income Statement option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(INCOME_STATEMENT, false);
        roleManagementPage.selectRole("one role").switchPermissions(TRIAL_BALANCE, true);
        log("@Step 1: Re-login with one role account account has 'Retained Earnings' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, RetainedEarningsPage.class);
        log("@Verify 1: 'Retained Earnings' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, INCOME_STATEMENT), "FAILED! Income Statement menu is displayed");
        log("INFO: Executed completely");
    }
}
