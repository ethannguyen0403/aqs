package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.financialReports.BalanceSheetAnalysisPage;
import pages.sb11.generalReports.LedgerStatementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BalanceSheetAnalysisTest extends BaseCaseAQS {

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
}
