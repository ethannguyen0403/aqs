package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.generalReports.ConsolidatedClientBalancePage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.List;

public class ConsolidatedClientBalanceTest extends BaseCaseAQS {
    @TestRails(id="5280")
    @Test(groups = {"regression","2023.12.29"})
    @Parameters({"password", "userNameOneRole"})
    public void Consolidated_Client_Balance_5280(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the menu");
        log("@pre-condition: Account is inactivated permission 'Company Set-up'");
        log("@Step 1: Navigate to the site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Check menu item 'Consolidated Client Balance' under menu 'General Reports'");
        log("@Verify 1: Menu 'Consolidated Client Balance' is not shown");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(SBPConstants.GENERAL_REPORTS,SBPConstants.CONSOLIDATED_CLIENT_BALANCE));
        log("INFO: Executed completely");
    }
    @TestRails(id="5281")
    @Test(groups = {"regression","2023.12.29"})
    @Parameters({"password", "userNameOneRole"})
    public void Consolidated_Client_Balance_5281(String password, String userNameOneRole) throws Exception {
        //Bug AQS-3858
        log("@title: Validate accounts without permission cannot access page");
        String url = environment.getSbpLoginURL() + "#/aqs-report/general-reports/consolidated-client-balance";
        log("@pre-condition: Account is inactivated permission 'Consolidated Client Balance'");
        log("@Step 1: Navigate to the site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Trying to access page by using url");
        DriverManager.getDriver().get(url);
        log("@Verify 1: User cannot access page");
        log("INFO: Executed completely");
    }
    @TestRails(id="5282")
    @Test(groups = {"regression","2023.12.29"})
    public void Consolidated_Client_Balance_5282() {
        log("@title: Validate accounts with permission can access page");
        log("@pre-condition: Account is inactivated permission 'Consolidated Client Balance'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'General Reports' and access 'Consolidated Client Balance' page");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Verify 1: User can access page successfully");
        Assert.assertTrue(page.lblTitle.getText().contains("Consolidated Client Balance"),"FAILED! Title page displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="5283")
    @Test(groups = {"regression","2023.12.29"})
    public void Consolidated_Client_Balance_5283() {
        log("@title: Validate UI on Consolidated Client Balance is correctly displayed");
        log("@pre-condition: Account is activated permission 'Company Set-up'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
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
    @Test(groups = {"regression","2023.12.29"})
    public void Consolidated_Client_Balance_5284() {
        log("@title: Validate 'Export To Excel' button work properly");
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "consolidated-client-balance.xlsx";
        log("@pre-condition: Account is activated permission 'Consolidated Client Balance'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Filter which have data");
        page.filter("","","","");
        log("@Step 3: Click 'Export To Excel' button");
        page.btnExportExcel.click();
        page.waitSpinnerDisappeared();
        log("@Verify 1: Excel file is exported and downloaded to user's device properly");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Step 4: Open exported file");
        log("@Verify 2: The correct data should display and should have format 'comma' for thousand number");
        page.checkExcelData(downloadPath);
        try {
            FileUtils.removeFile(downloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }
    @TestRails(id="5285")
    @Test(groups = {"regression","2023.12.29"})
    public void Consolidated_Client_Balance_5285() {
        log("@title: Validate 'Export To PDF' button work properly");
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "consolidated-client-balance-pdf.pdf";
        log("@pre-condition: Account is activated permission 'Consolidated Client Balance'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Filter which have data");
        page.filter("","","","");
        log("@Step 3: Click 'Export To Excel' button");
        page.btnExportPDF.click();
        page.waitSpinnerDisappeared();
        log("@Verify 1: Excel file is exported and downloaded to user's device properly");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        try {
            FileUtils.removeFile(downloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }
    @TestRails(id="5287")
    @Test(groups = {"regression","2023.12.29"})
    @Parameters({"clientCode"})
    public void Consolidated_Client_Balance_5287(String clientCode) {
        log("@title: Validate can search clients by report name/client name successfully");
        log("@pre-condition: Account is activated permission 'Consolidated Client Balance'");
        log("@Step 1: Go to General Reports >> Consolidated Client Balance");
        ConsolidatedClientBalancePage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.CONSOLIDATED_CLIENT_BALANCE, ConsolidatedClientBalancePage.class);
        log("@Step 2: Enter an exist client name");
        log("@Step 3: Click Search");
        page.filter("","","",clientCode);
        log("@Verify 1: Searched client should display correctly on Client list");
        List<String> lstClient = page.getLstClientDisplay();
        Assert.assertTrue(lstClient.contains(clientCode));
        log("INFO: Executed completely");
    }
}
