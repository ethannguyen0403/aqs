package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.BalanceSheetAnalysisPage;
import pages.sb11.financialReports.TrialBalancePage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.List;

import static common.SBPConstants.*;

public class BalanceSheetAnalysisTest extends BaseCaseAQS {

    @Test(groups = {"regression_stg", "2023.12.29"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "2810")
    public void BalanceSheetAnalysisTC_2810(String password, String userNameOneRole) throws Exception{
        log("@title: Validate user can not access Balance Sheet - Analysis page when having no permission");
        log("Precondition: Having an account that is inactivated Balance Sheet - Analysis permission");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(BALANCE_SHEET_ANALYSIS, false);
        roleManagementPage.selectRole("one role").switchPermissions(TRIAL_BALANCE, true);
        log("@Step 1: Re-login with one role account account has 'Stockholders Equity' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Financial Reports > Balance Sheet - Analysis");
        log("@Verify 1: Balance Sheet - Analysis menu is hidden displays");
        TrialBalancePage trialBalancePage = welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS),
                "FAILED! Balance Sheet - Analysis menu is displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "2811")
    public void BalanceSheetAnalysisTC_2811() throws IOException {
        log("@title: Validate can Export To Excel successfully");
        String downloadPath = getDownloadPath() + "balance-sheet-analysis.xlsx";
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Step 4: Click to export excel button");
        balanceAnalysisPage.btnExportExcel.click();
        welcomePage.waitSpinnerDisappeared();
        try {
            log("@Verify 1: Validate excel file was downloaded successfully");
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! Excel file was not downloaded successfully");
            log("INFO: Executed completely");
        }finally {
            log("@Post-condition: delete download file");
            FileUtils.removeFile(downloadPath);
        }
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "2812")
    public void BalanceSheetAnalysisTC_2812(){
        log("@title: Validate data will sort by Parent Account Number ascendingly");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Data should sort by Parent Account Number ascendingly");
        List<String> assetAccount = balanceAnalysisPage.getParentAccountList("Asset");
        Assert.assertTrue(balanceAnalysisPage.verifyParentAccountSortAsc(assetAccount, true), "FAILED! Parent account of Asset is not sorted asc. List: "+ assetAccount);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "9143")
    public void BalanceSheetAnalysisTC_9143(){
        log("@title: Validate Debit column data is displayed correctly positive amount from Ledger Statement - 'Amounts are shown in HKD' section");
        log("@Precondition: Already have a transaction from ledger with Asset type that has positive amount");
        int month = 12;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");

        LedgerStatementPage ledgerPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerPage.showLedger(COMPANY_UNIT, FINANCIAL_YEAR, "Asset", QA_LEDGER_GROUP_ASSET_PARENT_ACCOUNT, firstDayOfMonth, lastDayOfMonth, REPORT_TYPE.get(0));
        String totalLedger = ledgerPage.getTotalAmountInOriginCurrency("Total in HKD");
        boolean isPositiveNumber = ledgerPage.isTotalAmountInOriginCurrencyPositiveNumber("Total in HKD");

        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has transactions at pre-condition");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Validate Debit column data is displayed correctly positive amount from Ledger Statement - 'Amounts are shown in HKD' section");
        String creditValue = balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth(LEDGER_GROUP_NAME_ASSET, isPositiveNumber);
        Assert.assertEquals(creditValue, totalLedger, "FAILED! Data of Balance sheet table is not correct");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "9144")
    public void BalanceSheetAnalysisTC_9144(){
        log("@title: Validate Credit column data is displayed correctly negative amount from Ledger Statement - 'Amounts are shown in HKD' section");
        log("@Precondition: Already have a transaction from ledger with Asset type that has positive amount");
        int month = 12;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");

        LedgerStatementPage ledgerPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerPage.showLedger(COMPANY_UNIT, FINANCIAL_YEAR, "Asset", QA_ASSET_PARENT_ACCOUNT_700, firstDayOfMonth, lastDayOfMonth, REPORT_TYPE.get(0));
        String totalLedger = ledgerPage.getTotalAmountInOriginCurrency("Total in HKD");
        boolean isPositiveNumber = ledgerPage.isTotalAmountInOriginCurrencyPositiveNumber("Total in HKD");

        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has transactions at pre-condition");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Validate Credit column data is displayed correctly negative amount from Ledger Statement - 'Amounts are shown in HKD' section");
        String debitValue = balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth(LEDGER_GROUP_NAME_ASSET_700, isPositiveNumber);
        Assert.assertEquals(debitValue, totalLedger, "FAILED! Data of Balance sheet table is not correct");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "9145")
    public void BalanceSheetAnalysisTC_9145(){
        log("@title: Validate Txns column data is taken Debit/Credit amount of the selected month then minus the amount in the previous month");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Validate Txns column data is displayed = Debit/Credit amount of the selected month then minus the amount in the previous month ");
        Double creditSelectedMonth = Double.valueOf(balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth(LEDGER_GROUP_NAME_ASSET, false));
        Double creditPreviousMonth = Double.valueOf(balanceAnalysisPage.getColumnDebitCreditOfAccountPreviousMonth(LEDGER_GROUP_NAME_ASSET, false));
        String creditTxns = balanceAnalysisPage.getColumnDebitCreditOfAccountTxns(LEDGER_GROUP_NAME_ASSET, false);
        Assert.assertEquals(creditTxns, String.format("%.2f", creditSelectedMonth-creditPreviousMonth), "FAILED! Txns data is not correct");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "9146")
    public void BalanceSheetAnalysisTC_9146(){
        log("@title: Validate 'Asset = Liability + Capital' row sums up correctly 3 amounts in 'Asset', 'Liability', 'Capital' rows of each columns");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Validate 'Asset = Liability + Capital' row sums up correctly 3 amounts in 'Asset', 'Liability', 'Capital' rows of each columns.");
        Double creditAssetSelectedMonth = Double.valueOf(
                balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth("Asset", false).replace(",", ""));
        Double creditLiabilitySelectedMonth = Double.valueOf(
                balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth("Liability", false).replace(",", ""));
        Double creditCapitalSelectedMonth = Double.valueOf(
                balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth("Capital", false).replace(",", ""));
        String total = balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth("Asset = Liability + Capital", false).replace(",", "");

        Assert.assertEquals(total, String.format("%.2f", creditLiabilitySelectedMonth + creditCapitalSelectedMonth + creditAssetSelectedMonth),
                "FAILED! Asset is not correct");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "9147")
    public void BalanceSheetAnalysisTC_9147(){
        log("@title: Validate 'Difference' row sums up correctly the Debit and Credit amounts in 'Asset = Liability + Capital' row for each section");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Validate 'Difference' row sums up correctly the Debit and Credit amounts in 'Asset = Liability + Capital' row for each section.");
        Double creditAsLiCaSelectedMonth = Double.valueOf(
                balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth("Asset = Liability + Capital", false).replace(",", ""));
        Double debitAsLiCaSelectedMonth = Double.valueOf(
                balanceAnalysisPage.getColumnDebitCreditOfAccountSelectedMonth("Asset = Liability + Capital", true).replace(",", ""));

        Assert.assertEquals(balanceAnalysisPage.lblDifferenceSelectedMonth.getText().trim().replace(",", ""),
                String.format("%.2f", debitAsLiCaSelectedMonth + (-creditAsLiCaSelectedMonth)).replace("-", ""),
                "FAILED! Difference is not correct");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "17623")
    public void BalanceSheetAnalysisTC_17623() throws IOException {
        log("@title: Validate can Export To PDF successfully");
        String downloadPath = getDownloadPath() + "balance-sheet-analysis.pdf";
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(COMPANY_UNIT, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Step 4: Click to export PDF button");
        balanceAnalysisPage.btnExportPDF.click();
        welcomePage.waitSpinnerDisappeared();
        try {
            log("@Verify 1: Validate PDF file was downloaded successfully");
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! PDF file was not downloaded successfully");
            log("INFO: Executed completely");
        }finally {
            log("@Post-condition: delete download file");
            FileUtils.removeFile(downloadPath);
        }
    }
}
