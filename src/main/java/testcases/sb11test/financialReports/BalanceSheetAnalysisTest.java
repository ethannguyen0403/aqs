package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
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
import utils.sb11.BalanceSheetAnalysisUtils;
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

    @Test(groups = {"regression", "2024.V.2.0"})
    @TestRails(id = "9146")
    public void BalanceSheetAnalysisTC_9146(){
        log("@title: Validate Total Balance row sums up correctly 3 amounts in 'Asset', 'Liability', 'Capital' rows of each columns");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data > observe");
        page.filter(COMPANY_UNIT, "", "", "");
        log("@Verify 1: Validate Total Balance row sums up correctly 3 amounts in 'Asset', 'Liability', 'Capital' rows of each columns");
        Double creditAssetSelectedMonth = Double.valueOf(
                page.getColumnDebitCreditOfAccountSelectedMonth("Asset", false).replace(",", ""));
        Double creditLiabilitySelectedMonth = Double.valueOf(
                page.getColumnDebitCreditOfAccountSelectedMonth("Liability", false).replace(",", ""));
        Double creditCapitalSelectedMonth = Double.valueOf(
                page.getColumnDebitCreditOfAccountSelectedMonth("Capital", false).replace(",", ""));
        double total = DoubleUtils.roundUpWithTwoPlaces(creditAssetSelectedMonth + creditLiabilitySelectedMonth + creditCapitalSelectedMonth);

        Assert.assertEquals(page.getDifTotalBalance("Total Balance",page.indexTotalSelectMonthCre), total,
                "FAILED! Asset is not correct");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.2.0"})
    @TestRails(id = "9147")
    public void BalanceSheetAnalysisTC_9147(){
        log("@title: Validate Difference row sums up correctly the Debit and Credit amounts");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(COMPANY_UNIT, "", "", "");
        log("@Step 3: Check the Difference value");
        double difEx = DoubleUtils.roundEvenWithTwoPlaces(page.getDifTotalBalance("Total Balance",page.indexTotalSelectMonthDe) - page.getDifTotalBalance("Total Balance",page.indexTotalSelectMonthCre) + 0.01);
        log("@Verify 1: Difference = absolute (Total Balance Debit) - absolute (Total Balance Credit)");
        Assert.assertEquals(page.getDifTotalBalance("Difference",page.indexDifSelectMonth),difEx,"FAILED! Difference is not correct");
        log("INFO: Executed completely");
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
    @Test(groups = {"regression", "2024.V.2.0"})
    @TestRails(id = "21840")
    public void BalanceSheetAnalysisTC_21840(){
        log("@title: Validate the Asset value is sum up all absolute values of credit/debit amounts in each column");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(COMPANY_UNIT, "", "", "Before CJE");
        log("@Step 3: Check Asset value");
        int year = DateUtils.getYear(GMT_7);
        int month = DateUtils.getMonth(GMT_7);
        double sumDeEx = BalanceSheetAnalysisUtils.getSumCreDe("Asset","totalDebit",year,month,false);
        double sumCreEx = BalanceSheetAnalysisUtils.getSumCreDe("Asset","totalCredit",year,month,false);
        log("@Verify 1: The Asset value = sum up all absolute values of credit/debit amounts in each column");
        Assert.assertTrue((Double.valueOf(page.getColumnDebitCreditOfAccountSelectedMonth("Asset",true).replace(",",""))-sumDeEx) <= 0.02,
                "FAILED! Debit sum display incorrect");
        Assert.assertTrue((Double.valueOf(page.getColumnDebitCreditOfAccountSelectedMonth("Asset",false).replace(",","")) - sumCreEx) <= 0.02,
                "FAILED! Debit sum display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression", "2024.V.2.0"})
    @TestRails(id = "21842")
    public void BalanceSheetAnalysisTC_21842(){
        log("@title: Validate the Liability value is sum up all absolute values of credit/debit amounts in each column");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(COMPANY_UNIT, "", "", "Before CJE");
        log("@Step 3: Check Liability value");
        int year = DateUtils.getYear(GMT_7);
        int month = DateUtils.getMonth(GMT_7);
        double sumDeEx = BalanceSheetAnalysisUtils.getSumCreDe("Liability","totalDebit",year,month,false);
        double sumCreEx = BalanceSheetAnalysisUtils.getSumCreDe("Liability","totalCredit",year,month,false);
        log("@Verify 1: The Liability value = sum up all absolute values of credit/debit amounts in each column");
        Assert.assertTrue((Double.valueOf(page.getColumnDebitCreditOfAccountSelectedMonth("Liability",true).replace(",","")) - sumDeEx) <= 0.02,"FAILED! Debit sum display incorrect");
        Assert.assertTrue((Double.valueOf(page.getColumnDebitCreditOfAccountSelectedMonth("Liability",false).replace(",","")) - sumCreEx) <= 0.02,"FAILED! Debit sum display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression", "2024.V.2.0"})
    @TestRails(id = "21843")
    public void BalanceSheetAnalysisTC_21843(){
        log("@title: Validate the Capital value is sum up all absolute values of credit/debit amounts in each column");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(COMPANY_UNIT, "", "", "Before CJE");
        log("@Step 3: Check Liability value");
        int year = DateUtils.getYear(GMT_7);
        int month = DateUtils.getMonth(GMT_7);
        double sumDeEx = BalanceSheetAnalysisUtils.getSumCreDe("Capital","totalDebit",year,month,false);
        double sumCreEx = BalanceSheetAnalysisUtils.getSumCreDe("Capital","totalCredit",year,month,false);
        log("@Verify 1: The Liability value = sum up all absolute values of credit/debit amounts in each column");
        Assert.assertTrue((Double.valueOf(page.getColumnDebitCreditOfAccountSelectedMonth("Capital",true).replace(",","")) - sumDeEx) <= 0.02,"FAILED! Debit sum display incorrect");
        Assert.assertTrue((Double.valueOf(page.getColumnDebitCreditOfAccountSelectedMonth("Capital",false).replace(",","")) - sumCreEx) <= 0.02,"FAILED! Debit sum display incorrect");
        log("INFO: Executed completely");
    }
}
