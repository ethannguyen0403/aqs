package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.IncomeStatementAnalysisPage;
import pages.sb11.generalReports.LedgerStatementPage;
import testcases.BaseCaseAQS;
import utils.ExcelUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.SBPConstants.*;

public class IncomeStatementAnalysisTest extends BaseCaseAQS {
    @TestRails(id = "2825")
    @Test(groups = {"regression_stg", "2024.V.1.0","ethan"})
    @Parameters({"password", "userNameOneRole"})
    public void IncomeStatement_Analysis_TC2825(String password, String userNameOneRole) throws Exception {
        log("@title: Validate 'Income Statement – Analysis' menu is hidden if not active Income Statement – Analysis permission");
        log("Precondition: Deactivate Income Statement – Analysis option in one role account");
        log("@Step 1: Re-login with one role account account has 'Income Statement – Analysis' permission is OFF");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: Income Statement – Analysis menu is hidden displays");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS),
                "FAILED!  Income Statement – Analysis menu is displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "2826")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC2826() {
        log("@title: Validate 'Income Statement – Analysis' menu displays if active Income Statement – Analysis permission");
        log("Precondition: Active Income Statement – Analysis option in one role account");
        log("@Step 1: Re-login with one role account account has 'Income Statement – Analysis' permission is OFF");
        log("@Verify 1: 'Income Statement – Analysis' menu displays");
        Assert.assertTrue(welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS),
                "FAILED!  Income Statement – Analysis menu is NOT displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3405")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3405() {
        log("@title: Validate 'Income Statement – Analysis' menu displays if active Income Statement – Analysis permission");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: There are 3 groups which will be fixed OPERATING INCOME, OPERATING EXPENSES, and NON-OPERATING INCOME");
        Assert.assertTrue(incomeAnaPage.getRowIndexByGroup(OPERATING_INCOME)!=-1, String.format("FAILED! %s is not displayed", OPERATING_INCOME));
        Assert.assertTrue(incomeAnaPage.getRowIndexByGroup(OPERATING_EXPENSES)!=-1, String.format("FAILED! %s is not displayed", OPERATING_EXPENSES));
        Assert.assertTrue(incomeAnaPage.getRowIndexByGroup(NON_OPERATING_INCOME)!=-1, String.format("FAILED! %s is not displayed", NON_OPERATING_INCOME));
        log("INFO: Executed completely");
    }

    @TestRails(id = "3406")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3406() {
        log("@title: Validate Operating Income is only displayed details types with chart codes from 400 to 459 ");
        log("@Precondition: Already have a detail type with chart codes from 400 to 459 that contain transaction");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: Validate Operating Income is only displayed details types with chart codes from 400 to 459");
        List<String> codeList = incomeAnaPage.getCodeListOfGroup(OPERATING_INCOME);
        Assert.assertTrue(incomeAnaPage.verifyCodeStartingInRange(codeList, 400, 459), "FAILED! Chart code Operation Income NOT in range 400 to 459");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3407")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3407() {
        log("@title: Validate Operating Income is only displayed details types with chart codes from 400 to 459 ");
        log("@Precondition: Already have a detail type with chart codes starting with 5, 6 that contain transaction");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: Validate chart code of Operating expenses are started with 5, 6");
        List<String> codeList = incomeAnaPage.getCodeListOfGroup(OPERATING_EXPENSES);
        Assert.assertTrue(incomeAnaPage.verifyAllCodeStartWithNumber(codeList, "5", "6"), "FAILED! Chart code Operation expenses are not start with 5, 6");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3408")
    @Test(groups = {"regression", "2024.V.3.0"})
    public void IncomeStatement_Analysis_TC3408() {
        log("@title: Validate Non-Operating Income is only displayed details types with cart code starting from 460 to 499");
        log("@Precondition: Already have a detail type with chart codes starting from 460 to 499 that contain transaction");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter with month that contain transaction at pre-condition");
        incomeAnaPage.filter(KASTRAKI_LIMITED,"","","");
        log("@Verify 1: Chart code, name, amount of parent accounts that belong to the detail types should display accordingly on NON-OPERATING INCOME");
        List<String> codeList = incomeAnaPage.getCodeListOfGroup(NON_OPERATING_INCOME);
        Assert.assertTrue(incomeAnaPage.verifyCodeStartingInRange(codeList, 460, 499), "FAILED! Chart code Operation Income NOT in range 460 to 499");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3409")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3409() {
        log("@title: Validate [Previous month compare to the filtered month] shows amounts in the previous month accordingly");
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        String previousMonth = DateUtils.getMonthYear("GMT +7", -1, "MMMM/yyyy").split("/")[0];
        String previousYear = DateUtils.getMonthYear("GMT +7", -1, "MMMM/yyyy").split("/")[1];
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which has data");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "",String.format("%s - %s", year, month),"");
        log("@Verify 1: [Previous month compare to the filtered month] has the format 'Month-YYYY' (e.g.JUNE-2022')\n" +
                "and Amounts of parent accounts in the previous month should display accordingly");
        incomeAnaPage.verifyPreviousMonthDisplay(String.format("%s - %s", month, year), String.format("%s - %s", previousMonth, previousYear));
        log("INFO: Executed completely");
    }

    @TestRails(id = "3410")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3410() {
        log("@title: Validate [Filtered month] amounts displays correctly with Before CJE option");
        int firstCodeIndex = 3;
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        int previousYear = DateUtils.getYear("GMT +7") - 1;
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data with Before CJE option");
        String timeFilter = String.format("%s - %s", year, month);
        incomeAnaPage.filter(KASTRAKI_LIMITED, "", timeFilter, REPORT_TYPE.get(0));
        String colTime = String.format("%s - %s", month, year);
        String amountIncome = incomeAnaPage.getCellValueOfMonthCol(colTime, firstCodeIndex).replace("-", "");
        String numberCodeAccount = incomeAnaPage.getChartCodeAccount(firstCodeIndex).split(" - ")[0];

        log("@Step 3: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log(String.format("@Step 4: Get value of Account: %s on Ledger Statement page", numberCodeAccount));
        log("@Step info: For example, when filtering Year 2021-2022 and Month '2022-July':\n" +
                "Data shows in JUNE-2022 gets from 01-08-2021 to 30-06-2022 in the ledger statement");
        int yearLedger = DateUtils.getYear(GMT_7);
        int monthLedger = DateUtils.getMonth(GMT_7);
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, "", "All", numberCodeAccount, String.format("01/08/%s", previousYear), DateUtils.getLastDateOfMonth(yearLedger,monthLedger,"dd/MM/yyyy"),REPORT_TYPE.get(0));
        String amountLedger = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Verify 1: The amounts of parent accounts and detail types in the filtered month displays accordingly");
        Assert.assertEquals(amountIncome, amountLedger, "FAILED!  The amounts of parent accounts and detail types in the filtered month displays incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3411")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3411() {
        log("@title: Validate [Filtered month] amounts displays correctly with After CJE option");
        int firstCodeIndex = 3;
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        int previousYear = DateUtils.getYear("GMT +7") - 1;
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data with After CJE option");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "", String.format("%s - %s", year, month), REPORT_TYPE.get(1));
        String amountIncome = incomeAnaPage.getCellValueOfMonthCol(String.format("%s - %s", month, year), firstCodeIndex).replace("-", "");
        String numberCodeAccount = incomeAnaPage.getChartCodeAccount(firstCodeIndex).split(" - ")[0];

        log("@Step 3: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log(String.format("@Step 4: Get value of Account: %s on Ledger Statement page", numberCodeAccount));
        log("@Step info: For example, when filtering Year 2021-2022 and Month '2022-July':\n" +
                "Data shows in JUNE-2022 gets from 01-08-2021 to 30-06-2022 in the ledger statement");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, "", "All", numberCodeAccount, String.format("01/08/%s", previousYear),
                DateUtils.getLastDateOfMonth(DateUtils.getYear("GMT +7"), DateUtils.getMonth("GMT +7"), "dd/MM/yyyy"),REPORT_TYPE.get(1));
        ledgerStatementPage.waitSpinnerDisappeared();
        String amountLedger = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Verify 1: The amounts of parent accounts and detail types in the filtered month displays accordingly");
        Assert.assertEquals(amountIncome, amountLedger, "FAILED!  The amounts of parent accounts and detail types in the filtered month displays incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3412")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3412() {
        log("@title: Validate Txns. [filtered month] calculates properly");
        int firstCodeIndex = 3;
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        String previousMonth = DateUtils.getMonthYear("GMT +7", -1, "MMMM/yyyy").split("/")[0];
        String previousYear = DateUtils.getMonthYear("GMT +7", -1, "MMMM/yyyy").split("/")[1];
        String lblTxns = String.format("Txns. %s", String.format("%s - %s", month, year));

        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "", String.format("%s - %s", year, month), REPORT_TYPE.get(0));

        log("@Verify 1: Validate Txns. [filtered month] = the amount in the filtered month - amount in the previous month");
        double amountCurrentMonth =
                Double.valueOf(incomeAnaPage.getCellValueOfMonthCol(String.format("%s - %s", month, year), firstCodeIndex));
        double amountPreviousMonth =
                Double.valueOf(incomeAnaPage.getCellValueOfMonthCol(String.format("%s - %s", previousMonth, previousYear), firstCodeIndex));
        double amountTxns = Double.valueOf(incomeAnaPage.getCellValueOfMonthCol(lblTxns, firstCodeIndex));
        Assert.assertEquals(amountTxns, amountCurrentMonth-amountPreviousMonth, "FAILED! Txns. [filtered month] value is NOT correct");
        log("@Verify 2: Validate the red amount will be treated as negative and the blue amount will be treated as Positive");
        incomeAnaPage.verifyAmountTxnsColorIsCorrect(amountCurrentMonth, amountPreviousMonth,
                incomeAnaPage.getCellControlOfMonthCol(lblTxns, firstCodeIndex));
        log("INFO: Executed completely");
    }

    @TestRails(id = "3413")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3413() {
        log("@title: Validate [previous financial year] displays correct amount ");
        int firstCodeIndex = 3;
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        int previousYear = DateUtils.getYear("GMT +7") - 1;
        String lblFilterPreviousYear = String.format("Year %s-%s", previousYear - 1, previousYear);

        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data with Before CJE option");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "", String.format("%s - %s", year, month), REPORT_TYPE.get(0));
        String amountIncome = incomeAnaPage.getCellValueOfMonthCol(lblFilterPreviousYear.toUpperCase(), firstCodeIndex).replace("-", "");
        String chartCodeAccount = incomeAnaPage.getChartCodeAccount(firstCodeIndex);

        log("@Step 3: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log(String.format("@Step 4: Get value of Account: %s and filter time: 01/08/%s - 31/07/%s on Ledger Statement page",
                chartCodeAccount, previousYear - 1, previousYear));
        log("@Step info: If filter financial year =2022-2023, then amounts of previous financial year is the YEAR 2021-2022 can get from ledger statement from 01-08-2021 to 31-07-2022");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, lblFilterPreviousYear, "All", chartCodeAccount,
                String.format("01/08/%s", previousYear - 1),
                String.format("31/07/%s", previousYear), REPORT_TYPE.get(0));
        String amountLedger = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Verify 1: Validate [previous financial year] display amounts of details type, parent accounts in the previous financial year accordingly");
        Assert.assertEquals(amountIncome, amountLedger, "FAILED! The amounts of [previous financial year] displays incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "3414")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC3414() {
        log("@title: Validate [Filtered financial year] displays correct amount");
        int firstCodeIndex = 3;
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        int previousYear = DateUtils.getYear("GMT +7") - 1;
        String lblFilterYear = String.format("Year %s-%s", previousYear, year);

        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data with Before CJE option");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "", String.format("%s - %s", year, month), REPORT_TYPE.get(0));
        String amountIncome = incomeAnaPage.getCellValueOfMonthCol(lblFilterYear.toUpperCase(), firstCodeIndex).replace("-", "");
        String chartCodeAccount = incomeAnaPage.getChartCodeAccount(firstCodeIndex);

        log("@Step 3: Go to General Reports >> Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        ledgerStatementPage.waitSpinnerDisappeared();
        log(String.format("@Step 4: Get value of Account: %s and filter time: 01/08/%s - 31/07/%s on Ledger Statement page",
                chartCodeAccount, previousYear, year));
        log("@Step info: If filter financial year =2022-2023, then amounts of previous financial year is the YEAR 2021-2022 can get from ledger statement from 01-08-2021 to 31-07-2022");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, lblFilterYear, "All", chartCodeAccount, String.format("01/08/%s", previousYear),
                String.format("31/07/%s", year), REPORT_TYPE.get(0));
        String amountLedger = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Verify 1: Validate display amounts of details type, parent accounts in the filtered financial year accordingly");
        Assert.assertEquals(amountIncome, amountLedger, "FAILED! The amounts of [Filtered financial year] displays incorrect");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4026")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC4026() {
        log("@title:Validate [Net Profit (Loss)] displays correct amoun");
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "", String.format("%s - %s", year, month), REPORT_TYPE.get(0));
        log("@Verify 1: Net Profit (Loss) = Total Operating Income - Total Operating Expenses + Total Non-Operating Income");
        incomeAnaPage.verifyValueNetProfitDisplay(month,year);
        log("@Verify 2: Validate the red amount will be treated as negative and the blue amount will be treated as Positive");
        incomeAnaPage.verifyAmountNetProfitColorIsCorrect(month,year);
        log("INFO: Executed completely");
    }

    @TestRails(id = "4027")
    @Test(groups = {"regression", "2024.V.1.0","ethan"})
    public void IncomeStatement_Analysis_TC4027() throws IOException {
        log("@title: Validate 'Export To Excel' button work properly");
        String downloadPath = getDownloadPath() + "income-statement-analysis.xlsx";
        String month = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[0];
        String year = DateUtils.getMonthYear("GMT +7", 0, "MMMM/yyyy").split("/")[1];
        String lblYear =String.format("%s - %s", month, year);
        log("@Precondition: Income Statement – Analysis permission is ON");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "","","");
        log("@Step 4: Click to export excel button");
        incomeAnaPage.exportFile("Excel");

        try {
            log("@Verify 1: Validate excel file was downloaded successfully");
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! Excel file was not downloaded successfully");
            log("@Verify 2: The correct data should display and should have format 'comma' for thousand number");
            List<String> headerList = Arrays.asList("December - 2023", "January - 2024", "Txns January - 2024", "YEAR 2022-2023", "YEAR 2023-2024");
            List<Map<String, String>> excelData = ExcelUtils.getDataTest(downloadPath, INCOME_STATEMENT_ANALYSIS, headerList, "");
            incomeAnaPage.verifyExcelDataInCommaFormat(excelData, lblYear);
        }finally {
            log("@Post-condition: delete download file");
            // FileUtils.removeFile(downloadPath);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id = "4028")
    @Test(groups = {"regression", "2024.V.1.0","ethan2.0"})
    public void IncomeStatement_Analysis_TC4028() throws IOException {
        log("@title: Validate 'Export To PDF' button work properly ");
        String downloadPath = getDownloadPath() + "income-statement-analysis.pdf";
        log("@Precondition: Income Statement – Analysis permission is ON");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage incomeAnaPage = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter which have data");
        incomeAnaPage.filter(KASTRAKI_LIMITED, "","","");
        log("@Step 4: Click to export PDF button");
        incomeAnaPage.exportFile("PDF");
        try {
            log("@Verify 1: Validate excel file was downloaded successfully");
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "FAILED! PDF file was not downloaded successfully");
        }finally {
            log("@Post-condition: delete download file");
            // FileUtils.removeFile(downloadPath);
        }
        log("INFO: Executed completely");
    }
    @TestRails(id = "23954")
    @Test(groups = {"regression", "2024.V.2.0","ethan"})
    public void IncomeStatement_Analysis_TC23954() {
        log("@title: Validate data are sorted by Chart Code of Detail Types ascendingly");
        log("@pre-condition: Income Statement - Analysis permission is ON");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(KASTRAKI_LIMITED, "", "", "");
        log("@Step 3: Observe the sorting");
        log("@Verify 1: Data are sorted by Chart Code of Detail Types ascendingly");
        page.verifySortDetailType("OPERATING INCOME");
        page.verifySortDetailType("OPERATING EXPENSES");
        page.verifySortDetailType("NON-OPERATING INCOME");
        log("INFO: Executed completely");
    }
    @TestRails(id = "23955")
    @Test(groups = {"regression", "2024.V.2.0","ethan"})
    public void IncomeStatement_Analysis_TC23955() {
        log("@title: Validate Parent Accounts are sorted by Parent Account Number ascendingly within the same Detail Types");
        log("@pre-condition: Income Statement - Analysis permission is ON");
        log("@Step 1: Go to Financial Reports >> Income Statement - Analysis");
        IncomeStatementAnalysisPage page = welcomePage.navigatePage(FINANCIAL_REPORTS, INCOME_STATEMENT_ANALYSIS, IncomeStatementAnalysisPage.class);
        log("@Step 2: Filter with month that has data");
        page.filter(KASTRAKI_LIMITED, "", "", "");
        log("@Step 3: Observe the sorting of Parent account within the same Detail Types");
        log("@Verify 1: Parent Accounts are sorted by Parent Account Number ascendingly");
        page.verifySortParentAcc("OPERATING INCOME");
        page.verifySortParentAcc("OPERATING EXPENSES");
        page.verifySortParentAcc("NON-OPERATING INCOME");
        log("INFO: Executed completely");
    }

}
