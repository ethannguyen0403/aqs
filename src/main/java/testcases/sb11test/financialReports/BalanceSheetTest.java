package testcases.sb11test.financialReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.BalanceSheetPage;
import pages.sb11.generalReports.LedgerStatementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class BalanceSheetTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2023.10.31"})
    @TestRails(id = "2780")
    @Parameters({"password", "userNameOneRole"})
    public void Balance_Sheet_2780(String password, String userNameOneRole) throws Exception {
        log("@title: Validate Balance Sheet menu is hidden if not active Balance Sheet permission");
        log("@Pre-condition: Balance Sheet permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole,StringUtils.decrypt(password));
        log("@Step 2: Expand Financial Reports");
        log("@Verify 1: Balance Sheet menu does not display");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS,BALANCE_SHEET));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2781")
    public void Balance_Sheet_2781() {
        log("@title: Validate Balance Sheet menu displays if active Balance Sheet permission");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand Financial Reports");
        log("@Verify 1: Balance Sheet menu displays");
        Assert.assertTrue(welcomePage.isPageDisplayCorrect(FINANCIAL_REPORTS,BALANCE_SHEET),"Failed! Balance Sheet displayed incorrect!");
        log("@Step 3: Click Balance Sheet menu");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Verify 2: Will redirect to Balance Sheet page properly");
        Assert.assertTrue(page.getTitlePage().contains(SBPConstants.BALANCE_SHEET),"FAILED! Title page displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0","ethan"})
    @TestRails(id = "2783")
    public void Balance_Sheet_2783() {
        log("@title: Validate Report filter enables and works properly if filtering any 'Month'");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand 'Financial Reports'");
        log("@Step 3: Click Balance Sheet menu");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 4: Select any Month (e.g. Month = July)");
        log("@Verify 1: Report filter enables properly");
        Assert.assertTrue(page.ddReport.isEnabled(),"FAILED! Report dropdown display incorrect!");
        log("@Step 5: Select 'After CJE' option");
        log("@Step 6: Click Show button");
        page.filter(KASTRAKI_LIMITED,"","","After CJE",false);
        log("@Verify 2: Data filtering correctly");
        Assert.assertFalse(page.getDetailTypeNameByAccountType("ASSET").size() == 0,"FAILED! Data filtering incorrectly");
        Assert.assertFalse(page.getDetailTypeNameByAccountType("LIABILITY").size() == 0,"FAILED! Data filtering incorrectly");
        Assert.assertFalse(page.getDetailTypeNameByAccountType("CAPITAL").size() == 0,"FAILED! Data filtering incorrectly");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2784")
    public void Balance_Sheet_2784() {
        log("@title: Validate an ASSET section displays on the left side");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED,SBPConstants.FINANCIAL_YEAR,"","",false);
        log("@Verify 1: An ASSET section displays on the left side");
        Assert.assertTrue(page.isSectionDisplayCorrect("ASSET"),"FAILED! ASSET section displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2785")
    public void Balance_Sheet_2785() {
        log("@title: Validate LIABILITY and CAPITAL section display on the right side");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED,SBPConstants.FINANCIAL_YEAR,"","",false);
        log("@Verify 1: LIABILITY and CAPITAL section display on the right side");
        Assert.assertTrue(page.isSectionDisplayCorrect("LIABILITY"),"FAILED! LIABILITY section displays incorrect.");
        Assert.assertTrue(page.isSectionDisplayCorrect("CAPITAL"),"FAILED! CAPITAL section displays incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2786")
    public void Balance_Sheet_2786() {
        log("@title: Validate Parent Accounts will be grouped by Detail Types");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED,SBPConstants.FINANCIAL_YEAR,"","",false);
        log("@Verify 1: Parent Accounts will be grouped by Detail Types");
        Assert.assertTrue(page.isParentAccountsDisplayCorrect(),"FAILED! ParentAccounts display Incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2787")
    public void Balance_Sheet_2787() {
        log("@title: Validate Data will be sorted by Chart Code of Detail Types ascendingly");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED,SBPConstants.FINANCIAL_YEAR,"","",false);
        log("@Verify 1: Data will be sorted by Chart Code of Detail Types ascendingly");
        Assert.assertTrue(page.isDetailTypeSortCorrect("ASSET"),"FAILED! Detail Type sort Incorrect in Asset table");
        Assert.assertTrue(page.isDetailTypeSortCorrect("LIABILITY"),"FAILED! Detail Type sort Incorrect in Liability table");
        Assert.assertTrue(page.isDetailTypeSortCorrect("CAPITAL"),"FAILED! Detail Type sort Incorrect in Capital table");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2788")
    public void Balance_Sheet_2788() {
        log("@title: Validate there is a total amount for each Detail Type");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED,SBPConstants.FINANCIAL_YEAR,"","",false);
        log("@Verify 1: There is a total amount for each Detail Type = sum of balance amounts of all Parent Accounts' under it");
        Assert.assertTrue(page.isTotalAmountDisplayCorrect(),"FAILED! Total amount of each Detail Type displays Incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2789")
    public void Balance_Sheet_2789() {
        log("@title: Validate the Balance Amount is taken from Ledger Statement > 'Amounts are shown in HKD' section > Running Bal.' column");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go To Ledger Statement");
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        log("@Step 3: Filter data of any ledger account as example as below:\n" +
                "Financial Year = Year 2023-2024\n" +
                "Account Type = Asset\n" +
                "Detail Type = 500.000.000 - QA Ledger Group Asset\n" +
                "From Date 1/7/2023 To Date 31/7/2023\n" +
                "Report = Before CJE");
        String fromDate = "01/08/2023";
        String toDate = "30/8/2023";
        String detailTypeName = "QA Ledger Group Asset";
        String financialYear = "Year 2023-2024";
        String report = "Before CJE";
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,financialYear,LedgerStatement.ACCOUNT_TYPE.get(1),detailTypeName,fromDate,toDate,report);
        log("@Step 4: Get 'Amounts are shown in HKD' section > Running Bal.' column at Total in HKD row");
        String totalAmountOfParentAcount = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Step 5: Get 'Amounts are shown in HKD' section > Running Bal.' column at Total in HKD row");
        BalanceSheetPage page = ledgerStatementPage.navigatePage(FINANCIAL_REPORTS,BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 6: Filter data of account: 500.000.000 - QA Ledger Group Asset on selected month at step #3\n" +
                "Financial Year = Year 2022-2023\n" +
                "Month = 2023 - July\n" +
                "Report = Before CJE");
        String month = "2023 - August";
        page.filter(KASTRAKI_LIMITED,financialYear,month,report,false);
        log("@Verify 1: Balance Amount = value that get at step");
        Assert.assertEquals(page.getTotalAmount(detailTypeName).getText(),totalAmountOfParentAcount,"FAILED! Balance Amount displays incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2790")
    public void Balance_Sheet_2790() {
        log("@title: Validate 'Total Assets' value is correct");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED,SBPConstants.FINANCIAL_YEAR,"","",false);
        log("@Verify 1: Total Assets' = sum of all balance amounts of Asset section");
        Assert.assertEquals(page.lblValueTotalOfAsset.getText().replace(",",""),page.getTotalOfAllParent("ASSET").replace("-",""),"FAILED! Balance Amount of Asset section display incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2791")
    public void Balance_Sheet_2791() {
        log("@title: Validate 'Total Liability and Capital' is correct");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED,SBPConstants.FINANCIAL_YEAR,"","",false);
        log("@Verify 1: 'Total Liability and Capital' is correct = total balance amounts of Liability + Capital sections value (plus blue number and deduct red number)");
        Double totalBoth = Double.valueOf(page.getTotalOfAllParent("LIABILITY")) + Double.valueOf(page.getTotalOfAllParent("CAPITAL"));
        Assert.assertEquals(page.checkNegativeValue(page.lblValueTotalLiabilityCapital),totalBoth);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2792")
    public void Balance_Sheet_2792() {
        log("@title: Validate the UI displays properl");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS, SBPConstants.BALANCE_SHEET, BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED, SBPConstants.FINANCIAL_YEAR, "", "",false);
        log("@Verify 1: The UI displays properly:");
        Assert.assertTrue(page.lblTotalOfAsset.getAttribute("Class").contains("font-weight-bold"), "FAILED! Total numbers will not be bold");
        Assert.assertTrue(Label.xpath(String.format(page.titleSectionXpath,"ASSET")).getAttribute("Class").contains("font-weight-bold"),"FAILED! Name Section will not be bold");
        Assert.assertTrue(page.ddCompanyUnit.isEnabled(),"FAILED! CU Dropdown display incorrect!");
        Assert.assertTrue(page.ddFinancialYear.isEnabled(),"FAILED! FinancialYear Dropdown display incorrect!");
        Assert.assertTrue(page.ddMonth.isEnabled(),"FAILED! Month Dropdown display incorrect!");
        Assert.assertTrue(page.ddReport.isEnabled(),"FAILED! Report Dropdown display incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "2793")
    public void Balance_Sheet_2793() {
        log("@title: Validate 'Export To Excel' button work properly");
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "balance_sheet.xlsx";
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS, SBPConstants.BALANCE_SHEET, BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED, "Year 2023-2024", "2023 - December", "",false);
        log("@Step 4: Click 'Export To Excel' button");
        page.btnExportToExcel.click();
        page.waitSpinnerDisappeared();
        log("@Step 5: Open exported file");
        log("@Verify 1: Validate can export Retained Earnings to Excel file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Verify 2: Validate value in Excel report is correct'");
        page.checkValueCompareExcel(downloadPath);
        try {
            FileUtils.removeFile(downloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2794")
    public void Balance_Sheet_2794() {
        log("@title: Validate 'Export To PDF' button work properly");
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "balance_sheet.pdf";
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS, SBPConstants.BALANCE_SHEET, BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.KASTRAKI_LIMITED, SBPConstants.FINANCIAL_YEAR, "", "",false);
        log("@Step 4: Click 'Export To PDF' button");
        page.btnExportToPDF.click();
        page.waitSpinnerDisappeared();
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
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "16195")
    public void Balance_Sheet_16195() {
        log("@title: Validate Asset/Liability/Capital detail type with balance = 0 are displayed when ticking 'Show Info with Balance/Txns' checkbox");
        log("@Pre-condition 1: Having Asset/Liability/Capital some detail type with total balance = 0");
        log("@Pre-condition 2: Login SB11 site");
        log("@Step 1: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS, SBPConstants.BALANCE_SHEET, BalanceSheetPage.class);
        log("@Step 2: Tick 'Show Info with Balance/Txns' checkbox");
        log("@Step 3: Filter with detail type at pre-condition > observe");
        page.filter(SBPConstants.KASTRAKI_LIMITED, SBPConstants.FINANCIAL_YEAR, "", "",true);
        log("@Verify 1: Validate Asset/Liability/Capital detail type with balance = 0 are displayed when ticking 'Show Info with Balance/Txns' checkbox");
        Assert.assertTrue(page.getTotalAmount("QA Ledger Auto Asset").getText().equals("0.00"),"FAILED! Balance of Asset displays incorrect");
        Assert.assertTrue(page.getTotalAmount("QA Ledger Auto Liability").getText().equals("0.00"),"FAILED! Balance of Asset displays incorrect");
        Assert.assertTrue(page.getTotalAmount("QA Ledger Auto Capital").getText().equals("0.00"),"FAILED! Balance of Asset displays incorrect");
        log("INFO: Executed completely");
    }
}
