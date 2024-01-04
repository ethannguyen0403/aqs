package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.IncomeStatementAnalysisPage;
import pages.sb11.financialReports.TrialBalancePage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class IncomeStatementAnalysisTest extends BaseCaseAQS {
    @TestRails(id = "2825")
    @Test(groups = {"regression_stg", "SB11.2024.V.1.0"})
    @Parameters({"password", "userNameOneRole"})
    public void IncomeStatement_Analysis_TC2825(String password, String userNameOneRole) throws Exception {
        log("@title: Validate 'Income Statement – Analysis' menu is hidden if not active Income Statement – Analysis permission");
        log("Precondition: Deactivate Income Statement – Analysis option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(TRIAL_BALANCE, true);
        roleManagementPage.selectRole("one role").switchPermissions(INCOME_STATEMENT_ANALYSIS, false);
        log("@Step 1: Re-login with one role account account has 'Income Statement – Analysis' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        TrialBalancePage page =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Verify 1: Income Statement – Analysis menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS),
                "FAILED!  Income Statement – Analysis menu is displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2826")
    @Test(groups = {"regression_stg", "SB11.2024.V.1.0"})
    @Parameters({"password", "userNameOneRole"})
    public void IncomeStatement_Analysis_TC2826(String password, String userNameOneRole) throws Exception {
        log("@title: Validate 'Income Statement – Analysis' menu displays if active Income Statement – Analysis permission");
        log("Precondition: Active Income Statement – Analysis option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(INCOME_STATEMENT_ANALYSIS, true);
        log("@Step 1: Re-login with one role account account has 'Income Statement – Analysis' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        TrialBalancePage page =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Verify 1: 'Income Statement – Analysis' menu displays");
        Assert.assertTrue(welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS),
                "FAILED!  Income Statement – Analysis menu is NOT displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3405")
    @Test(groups = {"regression", "SB11.2024.V.1.0"})
    public void IncomeStatement_Analysis_TC3405() {
        log("@title: Validate 'Income Statement – Analysis' menu displays if active Income Statement – Analysis permission");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(COMPANY_UNIT,"","","");
        log("@Verify 1: There are 3 groups which will be fixed OPERATING INCOME, OPERATING EXPENSES, and NON-OPERATING INCOME");
        Assert.assertTrue(incomeAnaPage.getRowIndexByGroup(OPERATING_INCOME)!=-1, String.format("FAILED! %s is not displayed", OPERATING_INCOME));
        Assert.assertTrue(incomeAnaPage.getRowIndexByGroup(OPERATING_EXPENSES)!=-1, String.format("FAILED! %s is not displayed", OPERATING_EXPENSES));
        Assert.assertTrue(incomeAnaPage.getRowIndexByGroup(NON_OPERATING_INCOME)!=-1, String.format("FAILED! %s is not displayed", NON_OPERATING_INCOME));
        log("INFO: Executed completely");
    }

    @TestRails(id = "3406")
    @Test(groups = {"regression", "SB11.2024.V.1.0"})
    public void IncomeStatement_Analysis_TC3406() {
        log("@title: Validate Operating Income is only displayed details types with chart codes from 400 to 459 ");
        log("@Precondition: Already have a detail type with chart codes from 400 to 459 that contain transaction");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(COMPANY_UNIT,"","","");
        log("@Verify 1: Validate Operating Income is only displayed details types with chart codes from 400 to 459");
        List<String> codeList = incomeAnaPage.getCodeListOfGroup(OPERATING_INCOME);
        Assert.assertTrue(incomeAnaPage.verifyCodeStartingInRange(codeList, 400, 459), "FAILED! Chart code Operation Income NOT in range 400 to 459");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3407")
    @Test(groups = {"regression", "SB11.2024.V.1.0"})
    public void IncomeStatement_Analysis_TC3407() {
        log("@title: Validate Operating Income is only displayed details types with chart codes from 400 to 459 ");
        log("@Precondition: Already have a detail type with chart codes starting with 5, 6 that contain transaction");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(COMPANY_UNIT,"","","");
        log("@Verify 1: Validate chart code of Operating expenses are started with 5, 6");
        List<String> codeList = incomeAnaPage.getCodeListOfGroup(OPERATING_EXPENSES);
        Assert.assertTrue(incomeAnaPage.verifyAllCodeStartWithNumber(codeList, "5", "6"), "FAILED! Chart code Operation expenses are not start with 5, 6");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3408")
    @Test(groups = {"regression", "SB11.2024.V.1.0"})
    public void IncomeStatement_Analysis_TC3408() {
        log("@title: Validate Non-Operating Income is only displayed details types with cart code starting from 460 to 599");
        log("@Precondition: Already have a detail type with chart codes starting from 460 to 599 that contain transaction");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(COMPANY_UNIT,"","","");
        log("@Verify 1: Validate chart code of Operating expenses are started with 5, 6");
        List<String> codeList = incomeAnaPage.getCodeListOfGroup(NON_OPERATING_INCOME);
        Assert.assertTrue(incomeAnaPage.verifyCodeStartingInRange(codeList, 460, 500), "FAILED! Chart code Operation Income NOT in range 460 to 5xx");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3409")
    @Test(groups = {"regression", "SB11.2024.V.1.0"})
    public void IncomeStatement_Analysis_TC3409() {
        log("@title: Validate [Previous month compare to the filtered month] shows amounts in the previous month accordingly");
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        String previousMonth = DateUtils.getMonthYear("GMT +7", -1, "MMMM/yyyy").split("/")[0];
        String previousYear = DateUtils.getMonthYear("GMT +7", -1, "MMMM/yyyy").split("/")[1];
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(COMPANY_UNIT, "",String.format("%s - %s", year, month),"");
        log("@Verify 1: [Previous month compare to the filtered month] has the format 'Month-YYYY' (e.g.JUNE-2022')\n" +
                "and Amounts of parent accounts in the previous month should display accordingly");
        incomeAnaPage.verifyPreviousMonthDisplay(String.format("%s - %s", month, year), String.format("%s - %s", previousMonth, previousYear));
        log("INFO: Executed completely");
    }

    @TestRails(id = "3410")
    @Test(groups = {"regression", "SB11.2024.V.1.0"})
    public void IncomeStatement_Analysis_TC3410() {
        log("@title: Validate [Filtered month] amounts displays correctly with Before CJE option");
        int firstCodeIndex = 3;
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        int previousYear = DateUtils.getYear("GMT +7") - 1;
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data with Before CJE option");
        incomeAnaPage.filter(COMPANY_UNIT, "", String.format("%s - %s", year, month), REPORT_TYPE.get(0));
        String amountIncome = incomeAnaPage.getCellValueOfMonthCol(String.format("%s - %s", month, year), firstCodeIndex);
        String chartCodeAccount = incomeAnaPage.getChartCodeAccount(firstCodeIndex);

        log("@Step 3: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        log(String.format("@Step 4: Get value of Account: %s on Ledger Statement page", chartCodeAccount));
        log("@Step info: For example, when filtering Year 2021-2022 and Month '2022-July':\n" +
                "Data shows in JUNE-2022 gets from 01-08-2021 to 30-06-2022 in the ledger statement");
        ledgerStatementPage.showLedger(COMPANY_UNIT, "", "All", chartCodeAccount, String.format("01/08/%s", previousYear),
                DateUtils.getLastDateOfMonth(DateUtils.getYear("GMT +7"), DateUtils.getMonth("GMT +7"), "dd/MM/yyyy"),REPORT_TYPE.get(0));
        String amountLedger = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Verify 1: The amounts of parent accounts and detail types in the filtered month displays accordingly");
        Assert.assertEquals(amountIncome, amountLedger, "FAILED!  The amounts of parent accounts and detail types in the filtered month displays incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3411")
    @Test(groups = {"regression", "SB11.2024.V.1.0"})
    public void IncomeStatement_Analysis_TC3411() {
        log("@title: Validate [Filtered month] amounts displays correctly with After CJE option");
        int firstCodeIndex = 3;
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        int previousYear = DateUtils.getYear("GMT +7") - 1;
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data with After CJE option");
        incomeAnaPage.filter(COMPANY_UNIT, "", String.format("%s - %s", year, month), REPORT_TYPE.get(1));
        String amountIncome = incomeAnaPage.getCellValueOfMonthCol(String.format("%s - %s", month, year), firstCodeIndex);
        String chartCodeAccount = incomeAnaPage.getChartCodeAccount(firstCodeIndex);

        log("@Step 3: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        log(String.format("@Step 4: Get value of Account: %s on Ledger Statement page", chartCodeAccount));
        log("@Step info: For example, when filtering Year 2021-2022 and Month '2022-July':\n" +
                "Data shows in JUNE-2022 gets from 01-08-2021 to 30-06-2022 in the ledger statement");
        ledgerStatementPage.showLedger(COMPANY_UNIT, "", "All", chartCodeAccount, String.format("01/08/%s", previousYear),
                DateUtils.getLastDateOfMonth(DateUtils.getYear("GMT +7"), DateUtils.getMonth("GMT +7"), "dd/MM/yyyy"),REPORT_TYPE.get(1));
        String amountLedger = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Verify 1: The amounts of parent accounts and detail types in the filtered month displays accordingly");
        Assert.assertEquals(amountIncome, amountLedger, "FAILED!  The amounts of parent accounts and detail types in the filtered month displays incorrect");
        log("INFO: Executed completely");
    }


}
