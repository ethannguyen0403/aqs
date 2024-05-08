package testcases.sb11test.financialReports;

import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.RetainedEarningsPage;
import pages.sb11.financialReports.StockHoldersEquityPage;
import pages.sb11.financialReports.TrialBalancePage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.ExcelUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.SBPConstants.*;


public class StockholdersEquityTest extends BaseCaseAQS {

    @TestRails(id = "2802")
    @Test(groups = {"regression_stg", "2023.12.29"})
    @Parameters({"password", "userNameOneRole"})
    public void Stockholder_Equity_TC2802(String password, String userNameOneRole) throws Exception {
        log("@title: Validate Stockholders Equity menu is hidden if not active Stockholders Equity permission");
        log("Precondition: Stockholders Equity permission is OFF for any account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(STOCKHOLDERS_EQUITY, false);
        roleManagementPage.selectRole("one role").switchPermissions(TRIAL_BALANCE, true);
        log("@Step 1: Re-login with one role account account has 'Stockholders Equity' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        TrialBalancePage trialBalancePage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, TrialBalancePage.class);
        log("@Verify 1: 'Stockholders Equity' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY), "FAILED! Stockholders Equity menu is displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2803")
    @Test(groups = {"regression_stg", "2023.12.29"})
    @Parameters({"password", "userNameOneRole"})
    public void Stockholder_Equity_TC2803(String password, String userNameOneRole) throws Exception {
        log("@title: Validate Stockholders Equity menu displays if active Stockholders Equity permission");
        log("Precondition: Stockholders Equity permission is ON for any account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(STOCKHOLDERS_EQUITY, true);
        log("@Step 1: Re-login with one role account account has 'Stockholders Equity' permission is ON");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log("@Verify 1: 'Stockholders Equity' menu is hidden displays");
        Assert.assertTrue(welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY), "FAILED! Stockholders Equity menu is NOT displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2804")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2804()  {
        log("@title: Validate Stockholders Equity menu displays if active Stockholders Equity permission");
        log("Precondition: Stockholders Equity permission is ON for any account");
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log("@Step 2: Filter with valid data");
        stockPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        log("@Verify 1: Validate data should show correct");
        Assert.assertTrue(stockPage.tblStakeholder.isDisplayed(), "FAILED! Stockholders Equity table is not shown");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2805")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2805()  {
        log("@title: Validate there is a note 'Amounts are shown in [HKD]'");
        log("Precondition: Stockholders Equity permission is ON for any account");
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log("@Verify 1: There is a note 'Amounts are shown in [HKD]'");
        Assert.assertTrue(stockPage.lblAmountAreShow.isDisplayed(), "FAILED! Label 'Amounts are shown in [HKD]' is not shown");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2806")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2806()  {
        log("@title: Validate Description displays 2 rows 'Capital Issued - Capital Stock' and 'Retained Earnings''");
        log("Precondition: Stockholders Equity permission is ON for any account");
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log("@Step 2: Filter with valid data");
        stockPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        log("@Verify 1: Description displays 2 rows 'Capital Issued - Capital Stock' and 'Retained Earnings'");
        Assert.assertTrue(
                stockPage.getDesRowIndex(RETAINED_EARNING) != -1 && stockPage.getDesRowIndex(StockHoldersEquityPage.CAPITAL_ISSUED) != -1,
                "FAILED! Description NOT displays 2 rows 'Capital Issued - Capital Stock' and 'Retained Earnings'");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2807")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2807()  {
        //Financial year of Fair: From August - To July
        String fromDate = String.format("01/08/%s", FINANCIAL_YEAR_LIST.get(2).replace("Year ", "").split("-")[0]);
        String toDate = String.format("31/07/%s", FINANCIAL_YEAR_LIST.get(2).replace("Year ", "").split("-")[1]);
        log("@title: Validate correct Capital Issued - Capital Stock value displays");
        log("Precondition: Get Running Bal value of Parent Account 301.000.000.000 - Capital Stock from Ledger Statement > " +
                "'Amounts are shown in HKD' section > 'Running Bal.' column in any financial year (1)");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        ledgerStatementPage.showLedger(COMPANY_UNIT_LIST.get(3), FINANCIAL_YEAR_LIST.get(2), "", CAPITAL_PARENT_ACCOUNT, fromDate, toDate, "");
        String capitalAccountValue = ledgerStatementPage.getTotalAmountInOriginCurrency("Total in HKD");
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log(String.format("@Step 2: Filter with valid data company: %s, financial year: %s", COMPANY_UNIT_LIST.get(3), FINANCIAL_YEAR));
        stockPage.filter(COMPANY_UNIT_LIST.get(3), FINANCIAL_YEAR_LIST.get(2));
        log("@Verify 1: Capital Issued - Capital Stock = 301.000.000.000 - Capital Stock (1) at precondition");
        Assert.assertEquals(stockPage.getAmount(StockHoldersEquityPage.CAPITAL_ISSUED), capitalAccountValue, "FAILED! Capital issue is not correct from Ledger Statement");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2808")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2808()  {
        log("@title: Validate correct Retained Earnings value displays");
        log("Precondition: Get 'Retained Earning Ending' amount from Retained Earnings report - in the filtered financial year (e.g. Financial Year 2022-2023) (1)");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        retainedEarningsPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        String retainEar = retainedEarningsPage.lblAmountRetainedEnding.getText().trim();
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log(String.format("@Step 2: Filter with valid data company: %s, financial year: %s", KASTRAKI_LIMITED, FINANCIAL_YEAR));
        stockPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        log("@Verify 1: Validate Retained Earnings amount = value at precondition");
        Assert.assertEquals(stockPage.getAmount(StockHoldersEquityPage.RETAINED_EARNING), retainEar, "FAILED! Retained earnings is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2809")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2809()  {
        log("@title: Validate correct 'Total Stockholder's Equity' value displays");
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log(String.format("@Step 2: Filter with valid data company: %s, financial year: %s", KASTRAKI_LIMITED, FINANCIAL_YEAR));
        stockPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        log("@Verify 1: Validate  'Total Stockholder's Equity' value displays = Capital Issued + Retained Earnings");
        Double capitalIssued = Double.valueOf(stockPage.getAmount(StockHoldersEquityPage.CAPITAL_ISSUED).replace(",", ""));
        Double retainEarning = Double.valueOf(stockPage.getAmount(StockHoldersEquityPage.RETAINED_EARNING).replace(",", ""));
        String totalStock = stockPage.lblAmountTotalStock.getText().trim().replace(",", "");

        Assert.assertEquals(totalStock, String.format("%.2f", capitalIssued+retainEarning), "FAILED! 'Total Stockholder's Equity' value NOT EQUAL Capital Issued + Retained Earnings");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2813")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2813() throws IOException {
        String downloadPath = String.format("%s%s", getDownloadPath(), "stockholders-equity.xlsx");
        log("@title: Validate 'Export To Excel' button work properly ");
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log(String.format("@Step 2: Filter with valid data company: %s, financial year: %s", KASTRAKI_LIMITED, FINANCIAL_YEAR));
        stockPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        String capitalIssued = stockPage.getAmount(StockHoldersEquityPage.CAPITAL_ISSUED);
        String retainEarning = stockPage.getAmount(StockHoldersEquityPage.RETAINED_EARNING);

        log("@Step 3: Click 'Export To Excel' button");
        stockPage.btnExportExcel.click();
        welcomePage.waitSpinnerDisappeared();
        try {
            log("@Verify 1: Validate excel file was downloaded successfully");
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! Excel file was not downloaded successfully");
            log("@Verify 2: Data in excel file is correct");
            List<String> columExcel = Arrays.asList("No", "Description", "Amount");
            List<Map<String, String>>
                    actualExcelData = ExcelUtils.getDataTest(downloadPath, "Stockholders Equity", columExcel, "Total Stockholder's Equity");
            Assert.assertEquals(capitalIssued, actualExcelData.get(0).get(columExcel.get(2)), "FAILED! Excel value Capital Issued is not correct.");
            Assert.assertEquals(retainEarning, actualExcelData.get(1).get(columExcel.get(2)).replace("-", ""), "FAILED! Excel value Retained Earning from Operation is not correct.");
            log("INFO: Executed completely");
        }finally {
            log("@Post-condition: delete download file");
            // FileUtils.removeFile(downloadPath);
        }
    }

    @TestRails(id = "2814")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC2814() throws IOException {
        String downloadPath = String.format("%s%s", getDownloadPath(), "stockholders-equity.pdf");
        log("@title: Validate 'Export To Excel' button work properly ");
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log(String.format("@Step 2: Filter with valid data company: %s, financial year: %s", KASTRAKI_LIMITED, FINANCIAL_YEAR));
        stockPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        log("@Step 3: Click 'Export To PDF' button");
        stockPage.btnExportPDF.click();
        welcomePage.waitSpinnerDisappeared();
        try {
            log("@Verify 1: PDF file is exported and downloaded to user's device properly");
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! PDF file was not downloaded successfully");
            log("INFO: Executed completely");
        }finally {
            log("@Post-condition: delete download file");
            // FileUtils.removeFile(downloadPath);
        }
    }

    @TestRails(id = "16196")
    @Test(groups = {"regression", "2023.12.29"})
    public void Stockholder_Equity_TC16196()  {
        log("@title: Validate label 'Total Stockholder's Equity' is displayed in bold");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        retainedEarningsPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        log("@Step 1: Navigate Financial Report > Stockholders Equity");
        StockHoldersEquityPage stockPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, STOCKHOLDERS_EQUITY, StockHoldersEquityPage.class);
        log(String.format("@Step 2: Filter with valid data company: %s, financial year: %s", KASTRAKI_LIMITED, FINANCIAL_YEAR));
        stockPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR);
        log("@Verify 1: 'Total Stockholder's Equity' value is in bold");
        Assert.assertTrue(stockPage.verifyLabelIsBold(stockPage.lblTotalStock), "FAILED! Label format is not bold");
        log("INFO: Executed completely");
    }
}
