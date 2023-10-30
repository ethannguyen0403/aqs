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
import utils.ExcelUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.SBPConstants.*;

public class BalanceSheetTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2023.10.31"})
    @TestRails(id = "2780")
    @Parameters({"password"})
    public void Balance_Sheet_2780(String password) throws Exception {
        String accountNoPermission = "onerole";
        log("@title: Validate Balance Sheet menu is hidden if not active Balance Sheet permission");
        log("@Pre-condition: Balance Sheet permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(accountNoPermission,StringUtils.decrypt(password));
        log("@Step 2: Expand Financial Reports");
        log("@Verify 1: Balance Sheet menu does not display");
        Assert.assertFalse(welcomePage.isPageDisplayCorrect(FINANCIAL_REPORTS,BALANCE_SHEET),"FAILED! Balance Sheet displayed incorrect!");
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
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "2784")
    public void Balance_Sheet_2784() {
        log("@title: Validate an ASSET section displays on the left side");
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS,SBPConstants.BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
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
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
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
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
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
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
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
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
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
        ledgerStatementPage.showLedger(COMPANY_UNIT,financialYear,LedgerStatement.ACCOUNT_TYPE.get(1),detailTypeName,fromDate,toDate,report);
        log("@Step 4: Get 'Amounts are shown in HKD' section > Running Bal.' column at Total in HKD row");
        String totalAmountOfParentAcount = ledgerStatementPage.getGrandTotalByRunningBal();
        log("@Step 5: Get 'Amounts are shown in HKD' section > Running Bal.' column at Total in HKD row");
        BalanceSheetPage page = ledgerStatementPage.navigatePage(FINANCIAL_REPORTS,BALANCE_SHEET,BalanceSheetPage.class);
        log("@Step 6: Filter data of account: 500.000.000 - QA Ledger Group Asset on selected month at step #3\n" +
                "Financial Year = Year 2022-2023\n" +
                "Month = 2023 - July\n" +
                "Report = Before CJE");
        String month = "2023 - August";
        page.filter(COMPANY_UNIT,financialYear,month,report);
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
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
        log("@Verify 1: Total Assets' = sum of all balance amounts of Asset section");
        Assert.assertEquals(page.lblValueTotalOfAsset.getText(),page.getTotalOfAllParent("ASSET").replace("-",""),"FAILED! Balance Amount of Asset section display incorrect!");
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
        page.filter(SBPConstants.COMPANY_UNIT,SBPConstants.FINANCIAL_YEAR,"","");
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
        page.filter(SBPConstants.COMPANY_UNIT, SBPConstants.FINANCIAL_YEAR, "", "");
        log("@Verify 1: The UI displays properly:");
        Assert.assertTrue(page.lblTotalOfAsset.getAttribute("Class").contains("font-weight-bold"), "FAILED! Total numbers will not be bold");
        Assert.assertTrue(Label.xpath(String.format(page.titleSectionXpath,"ASSET")).getAttribute("Class").contains("font-weight-bold"),"FAILED! Name Section will not be bold");
        Assert.assertTrue(page.ddCompanyUnit.isEnabled(),"FAILED! CU Dropdown display incorrect!");
        Assert.assertTrue(page.ddFinancialYear.isEnabled(),"FAILED! FinancialYear Dropdown display incorrect!");
        Assert.assertTrue(page.ddMonth.isEnabled(),"FAILED! Month Dropdown display incorrect!");
        Assert.assertTrue(page.ddReport.isEnabled(),"FAILED! Report Dropdown display incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression1","2023.10.31"})
    @TestRails(id = "2793")
    public void Balance_Sheet_2793() {
        log("@title: Validate 'Export To Excel' button work properly");
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "balance_sheet.xlsx";
        log("@Pre-condition: Balance Sheet permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Financial Reports >> Balance Sheet page");
        BalanceSheetPage page = welcomePage.navigatePage(SBPConstants.FINANCIAL_REPORTS, SBPConstants.BALANCE_SHEET, BalanceSheetPage.class);
        log("@Step 3: Filter which has data");
        String financial = "Year 2022-2023";
        String month = "2023 - July";
        String monthExcel = "01 October 2023 - 31 October 2023";
        int excelColIDAccountAsset = 0;
        int excelColIDAccountLia = 3;
        int excelColParentNameAsset = 1;
        int excelColParentNameLia = 4;
        int excelColValueAsset = 2;
        int excelColValueLia = 5;
        page.filter(SBPConstants.COMPANY_UNIT, financial, month, "");
        ArrayList<String> lstDetailTypeAsset =  page.getDetailTypeNameByAccountType("ASSET");
        ArrayList<String> lstDetailTypeLia =  page.getDetailTypeNameByAccountType("LIABILITY");
        String detailTypeNameAsset = lstDetailTypeAsset.get(0);
        String valueDetailTypeNameAsset = page.checkValueCompareExcel(page.getTotalAmount(detailTypeNameAsset));
        String detailTypeNameLia = lstDetailTypeLia.get(0);
        String valueDetailTypeNameLia = page.checkValueCompareExcel(page.getTotalAmount(detailTypeNameLia));
        List<String> lstIdParentAccountAsset = page.getLstIdParentAccount(detailTypeNameAsset);
        List<String> lstIdParentAccountLia = page.getLstIdParentAccount(detailTypeNameLia);
        List<String> lstParentAssetName = page.getLstParentName(detailTypeNameAsset);
        List<String> lstParentLiaName = page.getLstParentName(detailTypeNameLia);
        List<String> lstParentValueAsset = page.getLstParentValue(detailTypeNameAsset);
        List<String> lstParentValueLia = page.getLstParentValue(detailTypeNameLia);

        List<String> columExcel1 = Arrays.asList(COMPANY_UNIT);
        List<String> columExcel2 = Arrays.asList(detailTypeNameAsset,"",valueDetailTypeNameAsset,detailTypeNameLia,"",valueDetailTypeNameLia);
        log("@Step 4: Click 'Export To Excel' button");
        page.btnExportToExcel.click();
        page.waitSpinnerDisappeared();
        log("@Step 5: Open exported file");
        List<Map<String, String>> actualExcelData1 = ExcelUtils.getDataTest(downloadPath, "Balance Sheet", columExcel1, detailTypeNameAsset);
        List<Map<String, String>> actualExcelData2 = ExcelUtils.getDataTest(downloadPath, "Balance Sheet", columExcel2, lstDetailTypeAsset.get(1));
        log("@Verify 1: Validate can export Retained Earnings to Excel file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Verify 2: Validate value in Excel report is correct'");
        Assert.assertTrue(page.isParentAccountDisplayCorrect(lstIdParentAccountAsset,actualExcelData2,columExcel2,excelColIDAccountAsset),"FAILED! Parent Account ASSET ID  display incorrect.");
        Assert.assertTrue(page.isParentAccountDisplayCorrect(lstIdParentAccountLia,actualExcelData2,columExcel2,excelColIDAccountLia),"FAILED! Parent Account LIABILITY ID  display incorrect.");
        Assert.assertTrue(page.isParentAccountDisplayCorrect(lstParentAssetName,actualExcelData2,columExcel2,excelColParentNameAsset),"FAILED! Parent Account ASSET Name display incorrect.");
        Assert.assertTrue(page.isParentAccountDisplayCorrect(lstParentLiaName,actualExcelData2,columExcel2,excelColParentNameLia),"FAILED! Parent Account LIABILITY Name display incorrect.");
        Assert.assertTrue(page.isParentAccountDisplayCorrect(lstParentValueAsset,actualExcelData2,columExcel2,excelColValueAsset),"FAILED! Parent Account ASSET value incorrect.");
        Assert.assertTrue(page.isParentAccountDisplayCorrect(lstParentValueLia,actualExcelData2,columExcel2,excelColValueLia),"FAILED! Parent Account LIABILITY value display incorrect.");
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
        page.filter(SBPConstants.COMPANY_UNIT, SBPConstants.FINANCIAL_YEAR, "", "");
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
}
