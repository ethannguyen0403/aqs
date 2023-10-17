package testcases.sb11test.accounting;

import com.paltech.driver.DriverManager;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.accounting.CompanySetupPage;
import pages.sb11.accounting.popup.CompanySetupCreatePopup;
import pages.sb11.generalReports.ClientBalancePage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.generalReports.popup.clientBalance.ClientBalanceDetailPopup;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import pages.sb11.role.RoleManagementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class CompanySetupTest extends BaseCaseAQS {


    @TestRails(id = "4332")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password"})
    public void Company_Set_up_TC4332(String password) throws Exception{
        String userNameOneRole = "onerole";
        log("@title: Validate accounts without permission cannot see the menu");
        log("@Precondition: Deactivate Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", false);
        log("@Step 1: Re-login with one role account account has 'Company Set-up' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        JournalEntries retainedEarningsPage =
                welcomePage.navigatePage(ACCOUNTING, JOURNAL_ENTRIES, JournalEntries.class);
        log("@Verify 1: Company Set-up menu is hidden displays");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(ACCOUNTING, COMPANY_SETUP),
                "FAILED! Cash Flow Statement menu is displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4333")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password"})
    public void Company_Set_up_TC4333(String password) throws Exception{
        String userNameOneRole = "onerole";
        String companyURL = environment.getSbpLoginURL() + "#/aqs-report/company-set-up";
        log("@title: Validate accounts without permission cannot access page by external link");
        log("@Precondition: Deactivate Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", false);
        log("@Step 1: Re-login with one role account account has 'Company Set-up' permission is OFF");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: Company Set-up menu can not access by external link: " + companyURL);
        DriverManager.getDriver().get(companyURL);
        Assert.assertFalse(new CompanySetupPage().lblTitle.isDisplayed(), "FAILED! Company Set-up page can access by external link");
        log("INFO: Executed completely");
    }


    @TestRails(id = "4334")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"password"})
    public void Company_Set_up_TC4334(String password) throws Exception{
        String userNameOneRole = "onerole";
        log("@title: Validate accounts with permission can access page");
        log("@Precondition: Active Company Set-up option in one role account");
        RoleManagementPage roleManagementPage = welcomePage.navigatePage(ROLE, ROLE_MANAGEMENT, RoleManagementPage.class);
        roleManagementPage.selectRole("one role").switchPermissions("Company Set-up", true);
        log("@Step 1: Login to SB11 site with account has 'Company Set-up' permission is ON");
        LoginPage loginPage = roleManagementPage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Navigate to Accounting > Company Set-up");
        CompanySetupPage companySetupPage = welcomePage.navigatePage(ACCOUNTING, COMPANY_SETUP, CompanySetupPage.class);
        log("@Verify 1: Validate page is displayed");
        Assert.assertTrue(companySetupPage.lblTitle.getText().contains(COMPANY_SETUP), "FAILED! Page Title is incorrect displayed.");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4335")
    @Test(groups = {"regression", "2023.10.31"})
    public void Company_Set_up_TC4335() {
        log("@title: Validate UI on 'Create Company' dialog is shown correctly");
        log("@Step 1: Navigate to Accounting > Company Set-up");
        CompanySetupPage companySetupPage = welcomePage.navigatePage(ACCOUNTING, COMPANY_SETUP, CompanySetupPage.class);
        log("@Step 2: Click on create button");
        CompanySetupCreatePopup createPopup = companySetupPage.openCreatePopup();
        log("@Verify 1: Validate Create Company popup displayed correct");
        Assert.assertTrue(createPopup.txtCompanyName.isDisplayed() && createPopup.txtCompanyName.isEnabled(), "Failed! Company name text box is not displayed");
        Assert.assertTrue(createPopup.txtCompanyAddress.isDisplayed() && createPopup.txtCompanyName.isEnabled(), "Failed! Company address text box is not displayed");
        Assert.assertTrue(createPopup.btnClear.isDisplayed(), "Failed! Button Clear is not displayed");
        Assert.assertTrue(createPopup.btnSubmit.isDisplayed(), "Failed! Button Submit is not displayed");
        Assert.assertEquals(createPopup.ddlFirstMonth.getOptions(), MONTH_NAME_LIST, "Failed! Accounting Period - First month option list is not correct");
        Assert.assertEquals(createPopup.ddlClosingMonth.getOptions(), MONTH_NAME_LIST, "Failed! Accounting Period - Closing month option list is not correct");
        Assert.assertEquals(createPopup.ddlCurrency.getOptions(), CURRENCY_LIST_WITHOUT_ALL,"Failed! Dropdown Currency option list is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4343")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "currency"})
    public void Company_Set_up_TC4343(String companyName, String currency) {
        String toDayAvoidLastDayOfMonth = "";
        log("@title: Validate that show the reporting currency of Company correctly in 'Ledger Statement' page");
        String expectedText1 = "Total in " + currency;
        String expectedText2 = "Grand Total in " + currency;
        String expectedText3 = "Amounts are shown in " + currency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, currency) );
        log("@Step 1: Navigate to  General Reports > Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);

        toDayAvoidLastDayOfMonth = ledgerStatementPage.isLastDayOfMonth()? ledgerStatementPage.getDateAvoidLastDayOfMonth("dd/MM/yyyy") : toDayAvoidLastDayOfMonth;
        log("@Step 2: Filter with Company: "+ companyName);
        ledgerStatementPage.showLedger(companyName, "","","","",toDayAvoidLastDayOfMonth, "");

        log(String.format("@Verify 1: Validate  the table has \"CUR transaction in %s\" is NOT displayed.", currency));
        Assert.assertFalse(ledgerStatementPage.lblAmountAreShowHeader.isDisplayed(),  "FAILED! The table CUR transaction is shown");
        log("@Verify 2: Validate shows text correct with currency: " + currency);
        Assert.assertEquals(ledgerStatementPage.getDescriptionTotalAmountInOriginCurrency("Total in"), expectedText1, "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.getDescriptionGrandTotalAmountInOriginCurrency(),expectedText2,  "FAILED! Text is not correct");
        Assert.assertEquals(ledgerStatementPage.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");

        log("@Step 3: Click on first ledger name to open Ledger Detail");
        log("@Verify 3: Validate shows text correct with currency: " + currency);
        LedgerDetailPopup ledgerDetailPopup = ledgerStatementPage.openLedgerFirstDetail();
        Assert.assertEquals(ledgerDetailPopup.tbLedger.getHeaderNameOfRows().get(3), expectedText3, "FAILED! Text is not correct");
        log("INFO: Executed completely");
    }

    @TestRails(id = "4345")
    @Test(groups = {"regression", "2023.10.31"})
    @Parameters({"companyName", "currency"})
    public void Company_Set_up_TC4345(String companyName, String currency) {
        log("@title: Validate that show the reporting currency of company correctly in 'Client Balance' page");
        String expectedText1 = "Total Balance " + currency;
        String expectedText2 = "Total Balance in " + currency;
        String expectedText3 = "Grand Total in " + currency;
        log(String.format("@Precondition: Company %s has currency as %s", companyName, currency) );
        log("@Step 1: Navigate to  General Reports >> Client Balance");
        ClientBalancePage clientBalancePage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_BALANCE, ClientBalancePage.class);
        log("@Step 2: Filter with default data with company name: " + companyName);
        clientBalancePage.filter("", companyName, "", "");

        log(String.format("@Verify 1: Validate shows text correct with currency %s after submit filter", currency));
        Assert.assertEquals(clientBalancePage.ddShowTotal.getFirstSelectedOption(), currency, "FAILED! Text of Show Total dropdown is not correct");
        Assert.assertEquals(clientBalancePage.tblClientBalance.getHeaderNameOfRows().get(4), expectedText1, "FAILED! Text Total Balance is not correct");

        log("@Step 3: Click on Client Balance name >> Client Balance detail popup");
        ClientBalanceDetailPopup detailPopup = clientBalancePage.goToClientDetail(1);

        log(String.format("@Verify 2: Validate shows text correct with currency %s on Client detail popup", currency));
        Assert.assertEquals(detailPopup.tblClientSuper.getHeaderNameOfRows().get(4), expectedText2, "FAILED! Text in Client Balance detail popup is not correct");
        Assert.assertEquals(detailPopup.lblGrandTotalMaster.getText().trim(), expectedText3, "FAILED! Text Client Balance detail popup is not correct");
        Assert.assertEquals(detailPopup.lblGrandTotalFooter.getText().trim(), expectedText3, "FAILED! Text Client Balance detail popup is not correct");
        log("INFO: Executed completely");
    }

}
