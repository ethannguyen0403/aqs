package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import static common.SBPConstants.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.accounting.CompanySetupPage;
import pages.sb11.generalReports.ClientBalancePage;
import pages.sb11.generalReports.ConsolidatedClientBalancePage;
import testcases.BaseCaseAQS;
import utils.sb11.RoleManagementUtils;
import utils.sb11.UserManagementUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.List;

public class ConsolidatedClientBalanceTest extends BaseCaseAQS {
    @TestRails(id="5280")
    @Test(groups = {"regression_stg","2023.12.29"})
    @Parameters({"password", "userNameOneRole"})
    public void Consolidated_Client_Balance_5280(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the menu");
        log("@pre-condition: Account is inactivated permission 'Company Set-up'");
        RoleManagementUtils.updateRolePermission("one role","Consolidated Client Balance","INACTIVE");
        log("@Step 1: Navigate to the site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Check menu item 'Consolidated Client Balance' under menu 'General Reports'");
        log("@Verify 1: Menu 'Consolidated Client Balance' is not shown");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE));
        log("INFO: Executed completely");
    }
    @TestRails(id="5281")
    @Test(groups = {"regression","2023.12.29","ethan7.0"})
    @Parameters({"password", "userNameOneRole"})
    public void Consolidated_Client_Balance_5281(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot access page");
        String url = environment.getSbpLoginURL() + "#/aqs-report/general-reports/consolidated-client-balance";
        log("@pre-condition: Account is inactivated permission 'Consolidated Client Balance'");
        RoleManagementUtils.updateRolePermission("one role","Consolidated Client Balance","INACTIVE");
        log("@Step 1: Navigate to the site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Trying to access page by using url");
        DriverManager.getDriver().get(url);
        log("@Verify 1: User cannot access page");
        Assert.assertFalse(new ConsolidatedClientBalancePage().lblTitle.isDisplayed(), "FAILED! Consolidated Client Balance page can access by external link");
        log("INFO: Executed completely");
    }
    @TestRails(id="5282")
    @Test(groups = {"regression","2023.12.29"})
    public void Consolidated_Client_Balance_5282() {
        log("@title: Validate accounts with permission can access page");
        log("@pre-condition: Account is inactivated permission 'Consolidated Client Balance'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'General Reports' and access 'Consolidated Client Balance' page");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Verify 1: User can access page successfully");
        Assert.assertTrue(page.lblTitle.getText().contains("Consolidated Client Balance"),"FAILED! Title page displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="5283")
    @Test(groups = {"regression","2023.12.29","ethan7.0"})
    public void Consolidated_Client_Balance_5283() {
        log("@title: Validate UI on Consolidated Client Balance is correctly displayed");
        log("@pre-condition: Account is activated permission 'Company Set-up'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Observe the page");
        log("@Verify 1:  Validate UI Info display correctly:\n" +
                "View By: contains the only option 'Client Point' as we only support this view in the report for now\n" +
                "Financial Year, Date: same as Client Balance report (allows filtering up to today)\n" +
                "Client Name: to search clients by report name/client name.\n" +
                "There are two buttons Export to Excel and Export to PDF to allow exporting the data.\n");
        page.verifyUIDisplay();
        log("INFO: Executed completely");
    }
    @TestRails(id="5284")
    @Test(groups = {"regression","2023.12.29","ethan"})
    public void Consolidated_Client_Balance_5284() {
        log("@title: Validate 'Export To Excel' button work properly");
        String downloadPath = getDownloadPath() + "consolidated-client-balance.xlsx";

        log("@pre-condition: Account is activated permission 'Consolidated Client Balance'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Filter which have data");
        page.filter("","","","");
        log("@Step 3: Click 'Export To Excel' button");
        page.exportFile("Excel");
        log("@Verify 1: Excel file is exported and downloaded to user's device properly");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Step 4: Open exported file");
        log("@Verify 2: The correct data should display and should have format 'comma' for thousand number");
        page.checkExcelData(downloadPath);
        // try {
        //     FileUtils.removeFile(downloadPath);
        // } catch (IOException e) {
        //     log(e.getMessage());
        // }
        // log("INFO: Executed completely");
    }
    @TestRails(id="5285")
    @Test(groups = {"regression","2023.12.29","ethan3.0"})
    public void Consolidated_Client_Balance_5285() {
        log("@title: Validate 'Export To PDF' button work properly");
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "consolidated-client-balance-pdf.pdf";
        log("@pre-condition: Account is activated permission 'Consolidated Client Balance'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Filter which have data");
        page.filter("","","","");
        log("@Step 3: Click 'Export To Excel' button");
        page.exportFile("PDF");
        log("@Verify 1: Excel file is exported and downloaded to user's device properly");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        // try {
        //     FileUtils.removeFile(downloadPath);
        // } catch (IOException e) {
        //     log(e.getMessage());
        // }
        // log("INFO: Executed completely");
    }
    @TestRails(id="5286")
    @Parameters({"clientCode"})
    @Test(groups = {"regression","2024.V.1.0","ethan7.0"})
    public void Consolidated_Client_Balance_5286(String clientCode) {
        log("@title: Validate filters work properly");
        log("@pre-condition 1: Consolidated Client Balance permission is ON");
        log("@pre-condition 2: Get deposit, total balance, HKD value in client balance page of current day");
        String clientName = "QA Client (No.121 QA Client)";
        ClientBalancePage clientBalancePage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_BALANCE,ClientBalancePage.class);
        clientBalancePage.filter("Client Point",KASTRAKI_LIMITED,"","",clientCode);
        List<String> lstEx = clientBalancePage.getValueOfFirstClient();
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = clientBalancePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Select financial year");
        log("@Step 3: Select date which has data");
        log("@Step 3: Click the Show button");
        page.filter("","","",clientCode);
        log("@Verify 1: Data shows correctly by filters");
        page.verifyFilterWorkProperly(clientName,lstEx.get(0),lstEx.get(1),lstEx.get(2));
        log("INFO: Executed completely");
    }
    @TestRails(id="5287")
    @Test(groups = {"regression","2023.12.29"})
    @Parameters({"clientCode"})
    public void Consolidated_Client_Balance_5287(String clientCode) {
        log("@title: Validate can search clients by report name/client name successfully");
        log("@pre-condition: Account is activated permission 'Consolidated Client Balance'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Enter an exist client name");
        log("@Step 3: Click Search");
        page.filter("","","",clientCode);
        log("@Verify 1: Searched client should display correctly on Client list");
        List<String> lstClient = page.getLstClientDisplay();
        Assert.assertTrue(lstClient.contains(clientCode));
        log("INFO: Executed completely");
    }
    @TestRails(id="5288")
    @Test(groups = {"regression","2024.V.1.0","ethan7.0"})
    public void Consolidated_Client_Balance_5288() {
        log("@title: Validate filters work properly");
        log("@pre-condition: Consolidated Client Balance permission is ON");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Select financial year");
        log("@Step 3: Select date which has data");
        log("@Step 4: Click the Show button");
        page.filter("","","","");
        log("@Verify 1: Total Balance in HKD: Data will be sorted by ascendingly\n" +
                "Total: sums up amounts in each column\n" +
                "Positive amount will have blue font, negative amount will have red font and 0 will be in black.");
        page.verifyDataTableDisplay();
        log("INFO: Executed completely");
    }
    @TestRails(id="5289")
    @Test(groups = {"regression","2024.V.1.0"})
    public void Consolidated_Client_Balance_5289() {
        log("@title: Validate the error message displays if date does not belong to Financial Year");
        log("@pre-condition: Consolidated Client Balance permission is ON");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Select a Financial Year (e.g. 2022-2023)");
        int oldYear = DateUtils.getYear(GMT_7)-2;
        int preYear = DateUtils.getYear(GMT_7)-1;
        log("@Step 3: Select date range not belongs to Financial Year at step #2");
        log("@Step 4: Click Show button");
        String financialYear = String.format("Year %s-%s",oldYear,preYear);
        page.ddFinancial.selectByVisibleText(financialYear);
        page.btnShow.click();
        log("@Verify 1: The error message 'Please select in range 01/08/<Year> and 31/07/<Year +1>' displays");
        String errorMes = page.appArlertControl.getWarningMessage();
        Assert.assertEquals(errorMes,String.format(ConsolidatedClientBalance.ERROR_MES_FINANCIAL_YEAR,oldYear,preYear),
                "FAILED! Error message displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="17616")
    @Parameters({"clientCode"})
    @Test(groups = {"regression","2024.V.1.0"})
    public void Consolidated_Client_Balance_17616(String clientCode) {
        log("@title: Validate the error message displays if date does not belong to Financial Year");
        log("@pre-condition: Consolidated Client Balance permission is ON");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(GENERAL_REPORTS,CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Filter values which has data");
        page.filter("","","",clientCode);
        log("@Step 3: Click on client name");
        log("@Verify 1: Nothing happens after clicking on Client Name");
        //check href == null
        Assert.assertTrue(page.tblInfo.getControlOfCellSPP(1,page.tblInfo.getColumnIndexByName("Client"),2,null).getAttribute("href") == null,"FAILED! Client name display incorrect");
        log("INFO: Executed completely");
    }
}
