package testcases.sb11test.financialReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.IncomeStatementPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.financialReports.RetainedEarningsPage;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.ExcelUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.SBPConstants.*;

public class RetainedEarningsTest extends BaseCaseAQS {
    private String detailTypeRetained = "302.000.000.000 - Retained Earnings";
    private String detailTypeDividend = "303.000.000.000 - Dividend";

    @TestRails(id = "2815")
    @Test(groups = {"smoke_qc", "2023.10.31"})
    @Parameters({"password", "userNameOneRole"})
    public void Retained_Earnings_TC2815(String password, String userNameOneRole) throws Exception {
        log("@title: Validate 'Retained Earnings' menu is hidden if not active Retained Earnings permission");
        log("Precondition: Deactivate Retained Earnings option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions(RETAINED_EARNING, false);
        roleManagementPage.selectRole("one role").switchPermissions(TRIAL_BALANCE, true);
        log("@Step 1: Re-login with one role account account has 'Retained Earnings' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, RetainedEarningsPage.class);
        log("@Verify 1: 'Retained Earnings' menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, RETAINED_EARNING), "FAILED! Retained Earnings menu is displayed");
        log("INFO: Executed completely");
    }


    @TestRails(id = "2816")
    @Test(groups = {"smoke", "2023.10.31"})
    public void Retained_Earnings_TC2816() {
        log("@title: Validate 'Retained Earnings' menu displays if active Retained Earnings permission");
        log("@Step 1: Login to SB11 site with account has 'Retained Earnings' permission is ON");
        log("@Step 2: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Verify 1: 'Retained Earnings' menu displays");
        log("@Verify 2: Validate page displayed with correct title");
        Assert.assertTrue(retainedEarningsPage.getTitle().contains(RETAINED_EARNING),
                String.format("FAILED! Page Title is incorrect displayed. Actual: %s, expected: %s", retainedEarningsPage.getTitle(),
                        RETAINED_EARNING));
        log("INFO: Executed completely");
    }

    @TestRails(id="2817")
    @Test(groups =  {"smoke", "2023.10.31"})
    public void Retained_Earnings_TC2817() {
        log("@title: Validate Description displays 3 rows 'Retained Earnings', 'Net Income/Loss from Operation' and 'Dividends'");
        log("@Step 1: Login to SB11 site with account has 'Retained Earnings' permission is ON");
        log("@Step 2: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 3: Filter Retained Earnings with default data");
        retainedEarningsPage.filterRetainedEarnings();
        log("@Verify 1: Validate page displayed 3 rows 'Retained Earnings', 'Net Income/Loss from Operation' and 'Dividends'");
        Assert.assertTrue(retainedEarningsPage.getDescriptionListValue().equals(RetainedEarningsConstants.DESCRIPTION_LIST),
                "FAILED! Description cell value is displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2818")
    @Test(groups =  {"smoke", "2023.10.31"})
    public void Retained_Earnings_TC2818() {
        String ledgerValue = "";
        log("@title: Validate correct Retained Earnings value displays");
        log("Precondition: Get value of Parent Account '302.000.000.000 - Retained Earnings' from Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.showLedger(COMPANY_UNIT, FINANCIAL_YEAR, "All", detailTypeRetained, "", "","");
        ledgerValue = ledgerStatementPage.getTotalAmountInOriginCurrency("Total in HKD");
        log("@Step 1: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 2: Filter Retained Earnings with default data");
        retainedEarningsPage.filterRetainedEarnings();
        log("@Verify 1: Validate page displayed Total retained correct'");
        Assert.assertEquals(ledgerValue, retainedEarningsPage.getTotalRetained(), "FAILED! Total retained is displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2819")
    @Test(groups =  {"smoke", "2023.10.31"})
    public void Retained_Earnings_TC2819() {
        String netProfitLoss = "";

        log("@title: Validate correct Net Income/Loss from Operation value displays");
        log("@Step 1: Navigate to Financial Reports > Income Statement");
        log("@Step 2: Get value of Net Profit (Loss) with default filter");
        IncomeStatementPage incomeStatementPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT, IncomeStatementPage.class);
        incomeStatementPage.filterIncomeReport(COMPANY_UNIT, "", "", REPORT_TYPE.get(0));
        netProfitLoss = incomeStatementPage.getNetProfitLoss();
        log("@Step 3: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 4: Filter Retained Earnings with Financial Year = Year 2022-2023");
        retainedEarningsPage.filterRetainedEarnings(COMPANY_UNIT, FINANCIAL_YEAR_LIST.get(2));
        log("@Verify 1: Validate page displayed value of 'Net Income/Loss from Operation' correct");
        Assert.assertEquals(netProfitLoss, retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(1)),
                "FAILED! Description cell value Net Profit (Loss) is displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2820")
    @Test(groups =  {"smoke", "2023.10.31"})
    public void Retained_Earnings_TC2820() {
        String ledgerValue = "";

        log("@title: Validate correct Dividends value displays");
        log("Precondition: Get value of Parent Account '303.000.000.000 - Dividend' from Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.showLedger(COMPANY_UNIT, FINANCIAL_YEAR, "All", detailTypeDividend, "", "","");
        ledgerValue = ledgerStatementPage.getTotalAmountInOriginCurrency("Total in HKD");
        log("@Step 1: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 2: Filter Retained Earnings with default data");
        retainedEarningsPage.filterRetainedEarnings(COMPANY_UNIT, FINANCIAL_YEAR);
        log("@Verify 1: Validate page displayed value 'Dividend' correct");
        Assert.assertEquals(ledgerValue, retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(2)),
                "FAILED! Total retained is displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2821")
    @Test(groups =  {"smoke", "2023.10.31"})
    public void Retained_Earnings_TC2821() {
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "retained-earnings.xlsx";
        List<String> columExcel = Arrays.asList("No", "Description", "Amount");
        log("@title: Validate 'Export To Excel' button work properly");
        log("@Step 1: Login to SB11 site with account has 'Retained Earnings' permission is ON");
        log("@Step 2: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 3: Filter Retained Earnings with default data");
        retainedEarningsPage.filterRetainedEarnings().exportExcel();
        String amountRetained = retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(0));
        String amountNetProfit = retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(1));
        String amountDividend = retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(2));
        List<Map<String, String>> actualExcelData = ExcelUtils.getDataTest(downloadPath, "Retained Earnings", columExcel, "Retained Earning Ending");
        log("@Step 4: Click on 'Export To Excel' button");
        log("@Verify 1: Validate can export Retained Earnings to Excel file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Verify 2: Validate value in Excel report is correct'");
        Assert.assertEquals(amountRetained, actualExcelData.get(0).get(columExcel.get(2)), "FAILED! Excel value Retained Earnings is not correct.");
        Assert.assertEquals(amountNetProfit, actualExcelData.get(1).get(columExcel.get(2)), "FAILED! Excel value Net Income/Loss from Operation is not correct.");
        Assert.assertEquals(amountDividend, actualExcelData.get(2).get(columExcel.get(2)), "FAILED! Excel value Dividends is not correct.");
        log("@Verify 3: Validate data should have format 'comma' for thousand number");
        Assert.assertTrue(retainedEarningsPage.isAmountNumberCorrectFormat(amountRetained), "FAILED! Amount number Retained is not correct format");
        Assert.assertTrue(retainedEarningsPage.isAmountNumberCorrectFormat(amountNetProfit), "FAILED! Amount number Net Profit is not correct format");
        Assert.assertTrue(retainedEarningsPage.isAmountNumberCorrectFormat(amountDividend), "FAILED! Amount number Dividend is not correct format");
        log("@Post-condition: delete download file");
        try {
            FileUtils.removeFile(downloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="2822")
    @Test(groups =  {"smoke", "2023.10.31"})
    public void Retained_Earnings_TC2822() {
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "retained-earnings.pdf";
        log("@title: Validate 'Export To PDF' button work properly");
        log("@Step 1: Login to SB11 site with account has 'Retained Earnings' permission is ON");
        log("@Step 2: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 3: Filter Retained Earnings with default data");
        retainedEarningsPage.filterRetainedEarnings().exportPDF();
        log("@Step 4: Click on 'Export To PDF' button");
        log("@Verify 1: Validate can export Retained Earnings to PDF file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Post-condition: delete download file");
        try {
            FileUtils.removeFile(downloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }

    }

