package testcases.sb11test.financialReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.CashFlowStatementPage;
import pages.sb11.financialReports.RetainedEarningsPage;
import pages.sb11.financialReports.popup.cashflowStatement.TransactionDetailsPopup;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static common.SBPConstants.*;
import static common.SBPConstants.CashFlowStatementConstants.HEADER_LIST_TOTAL_INCREASE_DECREASE_TABLE;


public class CashFlowStatementTest extends BaseCaseAQS {

    @TestRails(id = "8543")
    @Test(groups = {"regression", "2023.10.31"})
    public void Cash_Flow_Statement_TC8543() {
        log("@title: Validate Cash Flow Statement page is displayed when navigate ");
        log("@Step 1: Navigate to SB11 > Financial Reports > Cash Flow Statement");
        CashFlowStatementPage cashFlowStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, CASH_FLOW_STATEMENT, CashFlowStatementPage.class);
        log("@Verify 1: Validate Cash Flow Statement page displayed with correct title");
        Assert.assertTrue(cashFlowStatementPage.getPageTitle().contains(CASH_FLOW_STATEMENT), "FAILED! Page Title is incorrect displayed.");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8544")
    @Test(groups = {"revise"})
    public void Cash_Flow_Statement_TC8544() {
        String toDate =  DateUtils.getDate(0,"dd/MM/yyyy","GMT +7");
        String previousDate = DateUtils.getPreviousDate(toDate, "dd/MM/yyyy");
        log("@title: Validate UI on Cash Flow Statement is correctly displayed");
        log("@Step 1: Navigate to SB11 > Financial Reports > Cash Flow Statement");
        CashFlowStatementPage cashFlowStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, CASH_FLOW_STATEMENT, CashFlowStatementPage.class);
        log("@Step 2: Filter with default");
        cashFlowStatementPage.filter();
        log("@Verify 1: Validate Cash Flow Statement page displayed with correct filter");
        cashFlowStatementPage.verifyFilterSectionDisplay();
        log("@Verify 2: Validate Cash Flow Statement page displayed with correct table sections");
        Assert.assertEquals(cashFlowStatementPage.tblTransaction.getHeaderNameOfRows(), CashFlowStatementConstants.HEADER_LIST_TRANSACTION_TABLE,
                "FAILED! Transaction table header is displayed incorrect.");
        Assert.assertEquals(cashFlowStatementPage.tblCashInvest.getHeaderNameOfRows(), CashFlowStatementConstants.HEADER_LIST_CASH_INVEST_TABLE,
                "FAILED! Cash flow from investing activities header is displayed incorrect.");
        Assert.assertEquals(cashFlowStatementPage.tblCashFinancing.getHeaderNameOfRows(), CashFlowStatementConstants.HEADER_LIST_CASH_FINANCING_TABLE,
                "FAILED! Cash flow from financing activities header is displayed incorrect.");
        List<String> expectedList = Arrays.asList(HEADER_LIST_TOTAL_INCREASE_DECREASE_TABLE.get(0),
                String.format(HEADER_LIST_TOTAL_INCREASE_DECREASE_TABLE.get(1), previousDate)
                , String.format(HEADER_LIST_TOTAL_INCREASE_DECREASE_TABLE.get(2), toDate), HEADER_LIST_TOTAL_INCREASE_DECREASE_TABLE.get(3),
                HEADER_LIST_TOTAL_INCREASE_DECREASE_TABLE.get(4));
        cashFlowStatementPage.verifyTotalInOrDecreaseTableCorrectFormat(expectedList);
        log("INFO: Executed completely");
    }

    @TestRails(id = "8545")
    @Test(groups = {"regression", "2023.12.29"})
    public void Cash_Flow_Statement_TC8545() {
        log("@title: Validate Total in HKD data is matched correctly with total (Debit in HKD-Credit in HKD) on Transaction Details");
        String transType = "Payment Client";
        log("@Precondition:  Login the page\n" +
                "Already have a transaction from one of Detail Types");
        CashFlowStatementPage cashFlowStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, CASH_FLOW_STATEMENT, CashFlowStatementPage.class);
        log("@Step 1: Filter with valid data: Date 02/11/2023");
        cashFlowStatementPage.filter(KASTRAKI_LIMITED, FINANCIAL_YEAR, "02/11/2023", "02/11/2023");
        double totalHKDPayClient = cashFlowStatementPage.getAmountTotalInHKDTransTable(transType);
        log("@Step 2: Open Transaction detail of type: " +  transType);
        TransactionDetailsPopup popup = cashFlowStatementPage.goToTransactionDetails(transType);
        double totalCreHKD = popup.getTotalValue(6);
        double totalDebitHKD = popup.getTotalValue(5);
        log("@Verify 1: Validate Total in HKD data is matched correctly with total (Debit in HKD - Credit in HKD) at all currencies row on Transaction Details");
        Assert.assertEquals(totalHKDPayClient, totalDebitHKD-totalCreHKD, "FAILED! Total in HKD data is NOT matched");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8546")
    @Test(groups = {"regression_stg", "2023.10.31"})
    @Parameters({"password", "userNameOneRole"})
    public void Cash_Flow_Statement_TC8546(String password, String userNameOneRole) throws Exception {
        log("@title: Validate can't access Cash Flow Statement page when having no permission");
        log("Precondition: Deactivate Cash Flow Statement option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Cash Flow", false);
        log("@Step 1: Re-login with one role account account has 'Cash Flow Statement' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, TRIAL_BALANCE, RetainedEarningsPage.class);
        log("@Verify 1: Cash Flow Statement menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, CASH_FLOW_STATEMENT),
                "FAILED! Cash Flow Statement menu is displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "8547")
    @Test(groups = {"regression", "2023.10.31"})
    public void Cash_Flow_Statement_TC8547() {
        log("@title: Validate Total Increase (decrease) in cash and cash equivalents data");
        log("@Step 1: Navigate to SB11 > Financial Reports > Cash Flow Statement");
        CashFlowStatementPage cashFlowStatementPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, CASH_FLOW_STATEMENT, CashFlowStatementPage.class);
        log("@Step 2: Filter with default");
        cashFlowStatementPage.filter();
        log("@Verify 1: Validate 'Total Increase (decrease) in cash and cash equivalents' should be " +
                "sums of 'Net cash flow from operating activities', 'Net cash flow from investing activities', and 'Net cash flow from financing activities'.");
        double amountFinancing = cashFlowStatementPage.getNetAmountOfFinancingTable();
        double amountInvest = cashFlowStatementPage.getNetAmountOfInvestTable();
        double amountTransaction = cashFlowStatementPage.getNetAmountOfTransactionTable();
        double amountTotalInOrDecrease = cashFlowStatementPage.getNetAmountOfTotalInOrDecreaseTable();
        Assert.assertTrue(Objects.equals(amountTotalInOrDecrease, amountFinancing + amountInvest + amountTransaction),
                "FAILED! Amount Total Increase (decrease) in cash and cash NOT sums of 'Net cash flow from operating activities', 'Net cash flow from investing activities', " +
                        "and 'Net cash flow from financing activities'.");
    }

}
