package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.BalanceSheetAnalysisPage;
import pages.sb11.generalReports.LedgerStatementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.List;

import static common.SBPConstants.*;

public class BalanceSheetAnalysisTest extends BaseCaseAQS {

    @Test(groups = {"regression_stg", "2023.12.29","ethan"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "2810")
    public void BalanceSheetAnalysisTC_2810(String password, String userNameOneRole) throws Exception{
        log("@title: Validate user can not access Balance Sheet - Analysis page when having no permission");
        log("Precondition: Having an account that is inactivated Balance Sheet - Analysis permission");
        log("@Step 1: Re-login with one role account account has 'Stockholders Equity' permission is OFF");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Financial Reports > Balance Sheet - Analysis");
        log("@Verify 1: Balance Sheet - Analysis menu is hidden displays");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS),
                "FAILED! Balance Sheet - Analysis menu is displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29","ethan2.0"})
    @TestRails(id = "2811")
    public void BalanceSheetAnalysisTC_2811() {
        log("@title: Validate can Export To Excel successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Step 4: Click to export excel button");
        balanceAnalysisPage.exportFile("Excel");
        log("@Verify 1: Validate excel file was downloaded successfully");
        String downloadPath = getDownloadPath() + "balance-sheet-analysis.xlsx";
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! Excel file was not downloaded successfully");
        log("INFO: Executed completely");
        log("@Post-condition: delete download file");
    }

    @Test(groups = {"regression", "2023.12.29","ethan"})
    @TestRails(id = "2812")
    public void BalanceSheetAnalysisTC_2812(){
        log("@title: Validate data will sort by Parent Account Number ascendingly");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Data should sort by Parent Account Number ascendingly");
        List<String> assetAccount = balanceAnalysisPage.getParentAccountList("Asset");
        Assert.assertTrue(balanceAnalysisPage.verifyParentAccountSortAsc(assetAccount, true), "FAILED! Parent account of Asset is not sorted asc. List: "+ assetAccount);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29","ethan"})
    @TestRails(id = "9143")
    public void BalanceSheetAnalysisTC_9143(){
        log("@title: Validate Debit column data is displayed correctly positive amount from Ledger Statement - 'Amounts are shown in HKD' section");
        log("@Precondition: Already have a transaction from ledger with Asset type that has positive amount");
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");

        LedgerStatementPage ledgerPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerPage.waitSpinnerDisappeared();
        ledgerPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", QA_LEDGER_GROUP_ASSET_PARENT_ACCOUNT, firstDayOfMonth, lastDayOfMonth, REPORT_TYPE.get(0));
        String totalLedger = ledgerPage.getTotalInHKD(QA_LEDGER_GROUP_ASSET_PARENT_ACCOUNT,"CUR Translation","Running Bal.");
        boolean isPositiveNumber = ledgerPage.isTotalAmountPositiveNumber("Total in HKD", "CUR Translation", "Running Bal.");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has transactions at pre-condition");
        String monthFilter = DateUtils.getLastDateOfMonth(year, month, "yyyy - MMMM");
        balanceAnalysisPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, monthFilter, REPORT_TYPE.get(0));
        log("@Verify 1: Validate Debit column data is displayed correctly positive amount from Ledger Statement - 'Amounts are shown in HKD' section");
        String creditValue = balanceAnalysisPage.getValueDeCreOfSubAcc(LEDGER_GROUP_NAME_ASSET, "current",isPositiveNumber);
        Assert.assertEquals(creditValue, totalLedger, "FAILED! Data of Balance sheet table is not correct");
    }

    @Test(groups = {"regression", "2023.12.29","ethan3.0"})
    @TestRails(id = "9144")
    public void BalanceSheetAnalysisTC_9144(){
        log("@title: Validate Credit column data is displayed correctly negative amount from Ledger Statement - 'Amounts are shown in HKD' section");
        log("@Precondition: Already have a transaction from ledger with Asset type that has positive amount");
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String firstDayOfMonth = DateUtils.getFirstDateOfMonth(year, month, "dd/MM/yyyy");
        String lastDayOfMonth = DateUtils.getLastDateOfMonth(year, month, "dd/MM/yyyy");

        LedgerStatementPage ledgerPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerPage.waitSpinnerDisappeared();
        ledgerPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "Asset", QA_ASSET_PARENT_ACCOUNT_700, firstDayOfMonth, lastDayOfMonth, REPORT_TYPE.get(0));
        String totalLedger = ledgerPage.getTotalInHKD(QA_ASSET_PARENT_ACCOUNT_700,"CUR Translation","Running Bal.");
        boolean isPositiveNumber = ledgerPage.isTotalAmountPositiveNumber("Total in HKD", "CUR Translation", "Running Bal.");

        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has transactions at pre-condition");
        String monthFilter = DateUtils.getLastDateOfMonth(year, month, "yyyy - MMMM");
        balanceAnalysisPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, monthFilter, REPORT_TYPE.get(0));
        log("@Verify 1: Validate Credit column data is displayed correctly negative amount from Ledger Statement - 'Amounts are shown in HKD' section");
        String debitValue = balanceAnalysisPage.getValueDeCreOfSubAcc(LEDGER_GROUP_NAME_ASSET_700, "current",isPositiveNumber);
        Assert.assertEquals(debitValue, totalLedger, "FAILED! Data of Balance sheet table is not correct");
    }

    @Test(groups = {"regression", "2023.12.29","ethan"})
    @TestRails(id = "9145")
    public void BalanceSheetAnalysisTC_9145(){
        log("@title: Validate Txns column data is taken Debit/Credit amount of the selected month then minus the amount in the previous month");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, "2023 - December", REPORT_TYPE.get(0));
        log("@Verify 1: Validate Txns column data is displayed = Debit/Credit amount of the selected month then minus the amount in the previous month ");
        Double creditSelectedMonth = Double.valueOf(balanceAnalysisPage.getValueDeCreOfSubAcc(LEDGER_GROUP_NAME_ASSET, "current",false));
        Double creditPreviousMonth = Double.valueOf(balanceAnalysisPage.getValueDeCreOfSubAcc(LEDGER_GROUP_NAME_ASSET, "previous",false));
        String creditTxns = balanceAnalysisPage.getValueDeCreOfSubAcc(LEDGER_GROUP_NAME_ASSET,"txns", false);
        Assert.assertEquals(creditTxns, String.format("%.2f", creditSelectedMonth-creditPreviousMonth), "FAILED! Txns data is not correct");
    }

    @Test(groups = {"regression", "2024.V.2.0","ethan2.0"})
    @TestRails(id = "9146")
    public void BalanceSheetAnalysisTC_9146(){
        log("@title: Validate Total Balance row sums up correctly 3 amounts in 'Asset', 'Liability', 'Capital' rows of each columns");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data > observe");
        page.filter(KASTRAKI_LIMITED, "", "", "");
        log("@Verify 1: Validate Total Balance row sums up correctly 3 amounts in 'Asset', 'Liability', 'Capital' rows of each columns");
        page.verifyTotalBalanceDisplay("previous",true);
        page.verifyTotalBalanceDisplay("previous",false);
        page.verifyTotalBalanceDisplay("current",true);
        page.verifyTotalBalanceDisplay("current",false);
        page.verifyTotalBalanceDisplay("txns",true);
        page.verifyTotalBalanceDisplay("txns",false);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.2.0","ethan"})
    @TestRails(id = "9147")
    public void BalanceSheetAnalysisTC_9147(){
        log("@title: Validate Difference row sums up correctly the Debit and Credit amounts");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(KASTRAKI_LIMITED, "", "", "");
        log("@Step 3: Check the Difference value");
        log("@Verify 1: Difference = absolute (Total Balance Debit) - absolute (Total Balance Credit)");
        page.verifyDifferenceValueDisplay("previous");
        page.verifyDifferenceValueDisplay("current");
        page.verifyDifferenceValueDisplay("txns");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29","ethan3.0"})
    @TestRails(id = "17623")
    public void BalanceSheetAnalysisTC_17623() throws IOException {
        log("@title: Validate can Export To PDF successfully");
        String downloadPath = getDownloadPath() + "balance-sheet-analysis.pdf";
        log("@Step 1: Login with valid account");
        log("@Step 2: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage balanceAnalysisPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 3: Filter with month that has data");
        balanceAnalysisPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, "", REPORT_TYPE.get(0));
        log("@Step 4: Click to export PDF button");
        balanceAnalysisPage.exportFile("PDF");
        log("@Verify 1: Validate PDF file was downloaded successfully");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! PDF file was not downloaded successfully");
        log("INFO: Executed completely");
        log("@Post-condition: delete download file");
        // FileUtils.removeFile(downloadPath);
    }
    @Test(groups = {"regression", "2024.V.2.0","ethan"})
    @TestRails(id = "21840")
    public void BalanceSheetAnalysisTC_21840(){
        log("@title: Validate the Asset value is sum up all absolute values of credit/debit amounts in each column");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(KASTRAKI_LIMITED, "", "", "Before CJE");
        log("@Step 3: Check Asset value");
        log("@Verify 1: The Asset value = sum up all absolute values of credit/debit amounts in each column");
        page.verifySumColumnDeCreDisplay("Asset","previous",true);
        page.verifySumColumnDeCreDisplay("Asset","previous",false);
        page.verifySumColumnDeCreDisplay("Asset","current",true);
        page.verifySumColumnDeCreDisplay("Asset","current",false);
        page.verifySumColumnDeCreDisplay("Asset","txns",true);
        page.verifySumColumnDeCreDisplay("Asset","txns",false);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression", "2024.V.2.0","ethan"})
    @TestRails(id = "21842")
    public void BalanceSheetAnalysisTC_21842(){
        log("@title: Validate the Liability value is sum up all absolute values of credit/debit amounts in each column");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(KASTRAKI_LIMITED, "", "", "Before CJE");
        log("@Step 3: Check Liability value");
        log("@Verify 1: The Liability value = sum up all absolute values of credit/debit amounts in each column");
        page.verifySumColumnDeCreDisplay("Liability","previous",true);
        page.verifySumColumnDeCreDisplay("Liability","previous",false);
        page.verifySumColumnDeCreDisplay("Liability","current",true);
        page.verifySumColumnDeCreDisplay("Liability","current",false);
        page.verifySumColumnDeCreDisplay("Liability","txns",true);
        page.verifySumColumnDeCreDisplay("Liability","txns",false);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression", "2024.V.2.0","ethan"})
    @TestRails(id = "21843")
    public void BalanceSheetAnalysisTC_21843(){
        log("@title: Validate the Capital value is sum up all absolute values of credit/debit amounts in each column");
        log("@Step 1: Access to SB11 > Financial Reports > Balance Sheet - Analysis");
        BalanceSheetAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, BALANCE_SHEET_ANALYSIS, BalanceSheetAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(KASTRAKI_LIMITED, "", "", "Before CJE");
        log("@Step 3: Check Capital value");
        log("@Verify 1: The Capital value = sum up all absolute values of credit/debit amounts in each column");
        page.verifySumColumnDeCreDisplay("Capital","previous",true);
        page.verifySumColumnDeCreDisplay("Capital","previous",false);
        page.verifySumColumnDeCreDisplay("Capital","current",true);
        page.verifySumColumnDeCreDisplay("Capital","current",false);
        page.verifySumColumnDeCreDisplay("Capital","txns",true);
        page.verifySumColumnDeCreDisplay("Capital","txns",false);
        log("INFO: Executed completely");
    }
}
