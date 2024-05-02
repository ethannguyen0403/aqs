package testcases.sb11test.financialReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.financialReports.IncomeStatementPage;
import pages.sb11.generalReports.LedgerStatementPage;
import pages.sb11.financialReports.RetainedEarningsPage;
import testcases.BaseCaseAQS;
import utils.ExcelUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static common.SBPConstants.*;

public class RetainedEarningsTest extends BaseCaseAQS {

    @TestRails(id = "2815")
    @Test(groups = {"regression_stg", "2023.10.31","ethan"})
    @Parameters({"password", "userNameOneRole"})
    public void Retained_Earnings_TC2815(String password, String userNameOneRole) throws Exception {
        log("@title: Validate 'Retained Earnings' menu is hidden if not active Retained Earnings permission");
        log("Precondition: Deactivate Retained Earnings option in one role account");
        log("@Step 1: Re-login with one role account account has 'Retained Earnings' permission is OFF");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: 'Retained Earnings' menu is hidden displays");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(FINANCIAL_REPORTS, RETAINED_EARNING), "FAILED! Retained Earnings menu is displayed");
        log("INFO: Executed completely");
    }


    @TestRails(id = "2816")
    @Test(groups = {"regression", "2023.10.31","ethan2.0"})
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
    @Test(groups =  {"regression", "2023.10.31","ethan2.0"})
    public void Retained_Earnings_TC2817() {
        log("@title: Validate Description displays 3 rows 'Retained Earnings', 'Net Income/Loss from Operation' and 'Dividends'");
        log("@Step 1: Login to SB11 site with account has 'Retained Earnings' permission is ON");
        log("@Step 2: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage = welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 3: Filter Retained Earnings with default data");
        retainedEarningsPage.filter("","");
        log("@Verify 1: Validate page displayed 3 rows 'Retained Earnings', 'Net Income/Loss from Operation' and 'Dividends'");
        Assert.assertEquals(retainedEarningsPage.getDescriptionListValue(),RetainedEarningsConstants.DESCRIPTION_LIST,
                "FAILED! Description cell value is displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2818")
    @Test(groups =  {"regression", "2023.10.31","ethan"})
    public void Retained_Earnings_TC2818() {
        String ledgerValue = "";
        log("@title: Validate correct Retained Earnings value displays");
        log("Precondition: Get value of Sub-Account '302.000.002.000 - Accum PL' from Ledger Statement > 'CUR Translation in HKD' > 'Running Bal' section in selected Financial Year");
        String detailTypeRetained = "302.000.000.000 - Retained Earnings";
        String ledgerName = "302.000.002.000 - Accumulated Profit (Loss) for Previous Year";
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String fromDate = String.format("01/08/%s",year-1);
        String lastdayOfPreMonth = DateUtils.getLastDateOfMonth(year,month,"dd/MM/yyyy");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "All", detailTypeRetained, fromDate, lastdayOfPreMonth,"After CJE");
        ledgerValue = ledgerStatementPage.getValueOfSubAcc(ledgerName,"CUR Translation","Running Bal.");
        log("@Step 1: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 2: Filter Retained Earnings with default data");
        retainedEarningsPage.filter(KASTRAKI_LIMITED,FINANCIAL_YEAR);
        log("@Verify 1: Beginning Retained Earnings value should be = value at precondition (1)");
        Assert.assertEquals(ledgerValue, retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(0)), "FAILED! Beginning Retained Earnings value displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2819")
    @Test(groups =  {"regression", "2023.10.31","ethan"})
    public void Retained_Earnings_TC2819() {
        String detailTypeRetained = "302.000.000.000 - Retained Earnings";
        String netProfitLoss = "";
        log("@title: Validate correct Net Income/Loss from Operation value displays");
        log("@pre-condition: Get value of Sub-Account '302.000.001.000 - PL for Current Year' from Ledger Statement > 'CUR Translation in HKD' > 'Running Bal' section in selected Financial Year");
        String ledgerName = "302.000.001.000 - PL for Current Year - HKD";
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String fromDate = String.format("01/08/%s",year-1);
        String lastdayOfPreMonth = DateUtils.getLastDateOfMonth(year,month,"dd/MM/yyyy");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "All", detailTypeRetained, fromDate, lastdayOfPreMonth,"After CJE");
        netProfitLoss = ledgerStatementPage.getValueOfSubAcc(ledgerName,"CUR Translation","Running Bal.");
        log("@Step 1: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 2: Filter Retained Earnings with default data");
        retainedEarningsPage.filter(KASTRAKI_LIMITED,FINANCIAL_YEAR);
        log("@Verify 1: Net Income/Loss from Operation should be = value at precondition (1)");
        Assert.assertEquals(netProfitLoss, retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(1)),
                "FAILED! Description cell value Net Profit (Loss) is displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2820")
    @Test(groups =  {"regression", "2023.10.31","ethan"})
    public void Retained_Earnings_TC2820() {
        String detailTypeDividend = "303.000.000.000 - Dividend";
        String ledgerValue = "";
        log("@title: Validate correct Dividends value displays");
        log("@pre-condition: Get value of Parent Account '303.000.000.000 - Dividend' from Ledger Statement > 'CUR Translation in HKD' > 'Running Bal.' column in selected Financial Year");
        String ledgerName = "303.000.000.000 - Dividend";
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS, LEDGER_STATEMENT, LedgerStatementPage.class);
        int month = DateUtils.getMonth(GMT_7) - 1;
        int year = DateUtils.getYear("GMT +7");
        String fromDate = String.format("01/08/%s",year-1);
        String lastdayOfPreMonth = DateUtils.getLastDateOfMonth(year,month,"dd/MM/yyyy");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED, FINANCIAL_YEAR, "All", detailTypeDividend, fromDate, lastdayOfPreMonth,"After CJE");
        ledgerValue = ledgerStatementPage.getTotalInHKD(ledgerName,"CUR Translation","Running Bal.");
        log("@Step 1: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 2: Filter Retained Earnings with default data");
        retainedEarningsPage.filter(KASTRAKI_LIMITED,FINANCIAL_YEAR);
        log("@Verify 1: Validate page displayed value 'Dividend' correct");
        Assert.assertEquals(ledgerValue, retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(2)),
                "FAILED! Total retained is displayed incorrect.");
        log("INFO: Executed completely");
    }

    @TestRails(id="2821")
    @Test(groups =  {"regression", "2023.10.31","ethan"})
    public void Retained_Earnings_TC2821() {
        String downloadPath = getDownloadPath()  + "retained-earnings.xlsx";
        List<String> columExcel = Arrays.asList("No", "Description", "Amount");
        log("@title: Validate 'Export To Excel' button work properly");
        log("@Step 1: Login to SB11 site with account has 'Retained Earnings' permission is ON");
        log("@Step 2: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 3: Filter Retained Earnings with default data");
        retainedEarningsPage.filter("","");
        log("@Step 4: Click on 'Export To Excel' button");
        retainedEarningsPage.exportExcel();
        String amountRetained = retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(0));
        String amountNetProfit = retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(1));
        String amountDividend = retainedEarningsPage.getAmount(RetainedEarningsConstants.DESCRIPTION_LIST.get(2));
        log("@Verify 1: Validate can export Retained Earnings to Excel file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Verify 2: Validate value in Excel report is correct'");
        Assert.assertEquals(amountRetained, ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Beginning Retained Earnings",3,7).replace("-",""), "FAILED! Excel value Retained Earnings is not correct.");
        Assert.assertEquals(amountNetProfit, ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Beginning Retained Earnings",3,8).replace("-",""), "FAILED! Excel value Net Income/Loss from Operation is not correct.");
        Assert.assertEquals(amountDividend, ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"Beginning Retained Earnings",3,9).replace("-",""), "FAILED! Excel value Dividends is not correct.");
        log("@Verify 3: Validate data should have format 'comma' for thousand number");
        Assert.assertTrue(retainedEarningsPage.isAmountNumberCorrectFormat(amountRetained), "FAILED! Amount number Retained is not correct format");
        Assert.assertTrue(retainedEarningsPage.isAmountNumberCorrectFormat(amountNetProfit), "FAILED! Amount number Net Profit is not correct format");
        Assert.assertTrue(retainedEarningsPage.isAmountNumberCorrectFormat(amountDividend), "FAILED! Amount number Dividend is not correct format");
        log("@Post-condition: delete download file");
        // try {
        //     FileUtils.removeFile(downloadPath);
        // } catch (IOException e) {
        //     log(e.getMessage());
        // }
        log("INFO: Executed completely");
    }

    @TestRails(id="2822")
    @Test(groups =  {"regression", "2023.10.31","ethan2.0"})
    public void Retained_Earnings_TC2822() {
        String downloadPath = getDownloadPath() + "retained-earnings.pdf";
        log("@title: Validate 'Export To PDF' button work properly");
        log("@Step 1: Login to SB11 site with account has 'Retained Earnings' permission is ON");
        log("@Step 2: Navigate to Financial Reports > Retained Earnings");
        RetainedEarningsPage retainedEarningsPage =
                welcomePage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 3: Filter Retained Earnings with default data");
        retainedEarningsPage.filter("","");
        log("@Step 4: Click on 'Export To PDF' button");
        retainedEarningsPage.exportPDF();
        log("@Verify 1: Validate can export Retained Earnings to PDF file successfully'");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");
        log("@Post-condition: delete download file");
        // try {
        //     FileUtils.removeFile(downloadPath);
        // } catch (IOException e) {
        //     log(e.getMessage());
        // }
        log("INFO: Executed completely");
    }
    @TestRails(id="23952")
    @Test(groups =  {"regression", "2024.V.3.0"})
    public void Retained_Earnings_TC23952() {
        log("@title: Validate Kastraki values are calculated up to the latest month that had passed the last day for the current financial year");
        log("@Pre-condition 1: 'Retained Earnings' permission is ON for any account");
        log("@Pre-condition 2: Get value of these accounts from Ledger Statement > CUR Translation in HKD > Running Bal. column in current Financial Year (e.g. 2023-2024) and Company Unit = Kastraki\n" +
                "\n" +
                "Beginning Retained Earnings = 302.000.002.000 - Accum PL (Kastraki) (1)                            \n" +
                "\n" +
                "Net Income/Loss from Operation = 302.000.001.000 - PL for Current Year (2)\n" +
                "\n" +
                "Dividends =  303.000.000.000 - Dividend (3)\n" +
                "Today is not the last day of Month (e.g. today is 4/3/2024)\n" +
                "\n" +
                "=> (1), (2), (3) = values that take from the beginning of financial year (1/8/2023) till 29/02/2024");
        String accountType = "Capital";
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        String fromDate = ledgerStatementPage.getBeginDateOfFinanYear(FINANCIAL_YEAR);
        String toDate = ledgerStatementPage.getLastDateAfterCJE("dd/MM/yyyy");
        ledgerStatementPage.showLedger(KASTRAKI_LIMITED,FINANCIAL_YEAR,accountType,LEDGER_GROUP_RETAINED_EARNING_ACCOUNT,fromDate,toDate,"After CJE");
        String beginningRetainedEarningsAc = ledgerStatementPage.getValueOfSubAcc("302.000.001.000","CUR Translation","Running Bal.");
        String netIncomeAc = ledgerStatementPage.getValueOfSubAcc("302.000.002.000","CUR Translation","Running Bal.");
        ledgerStatementPage.showLedger("","","",LEDGER_GROUP_DIVIDEND_ACCOUNT,"","","");
        String dividendsAc = ledgerStatementPage.getTotalInHKD(LEDGER_GROUP_DIVIDEND_ACCOUNT,"CUR Translation","Running Bal.");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand 'Financial Reports' menu");
        log("@Step 3: Click 'Retained Earnings'");
        RetainedEarningsPage page =
                ledgerStatementPage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 4: Filter data on the same Financial Year as precondition");
        page.filter(KASTRAKI_LIMITED,FINANCIAL_YEAR);
        log("@Verify 1: Beginning Retained Earnings value should be = value at precondition (1)\n" +
                "Net Income/Loss from Operation value should be = value at precondition (2)\n" +
                "Dividends value should be = value at precondition (3)");
        Assert.assertEquals(netIncomeAc,page.getAmount("Beginning Retained Earnings"),"FAILED! Beginning Retained Earnings amount displays incorrect.");
        Assert.assertEquals(beginningRetainedEarningsAc,page.getAmount("Net Income/Loss from Operation"),"FAILED! Net Income/Loss from Operation amount displays incorrect.");
        Assert.assertEquals(dividendsAc,page.getAmount("Dividends"),"FAILED! Beginning Retained Earnings amount displays incorrect.");
        log("INFO: Executed completely");
    }
    @TestRails(id="23953")
//    @Test(groups =  {"regression1", "2024.V.3.0"})
    public void Retained_Earnings_TC23953() {
        //TODO: need to revise test case because of having new improvement
        log("@title: Validate Fair values are calculated up to the latest month that had passed the last day for the current financial year");
        log("@Pre-condition 1: 'Retained Earnings' permission is ON for any account");
        log("@Pre-condition 2: Get value of these accounts from Ledger Statement > CUR Translation in HKD > Running Bal. column in current Financial Year (e.g. 2023-2024) and Company Unit = Fair\n" +
                "\n" +
                "Beginning Retained Earnings = sum (302.000.001.001 - Retained Earnings (USD), 302.000.002.000 - Accumulated Profit (Loss) for Previous Year (USD)) (1)\n" +
                "Net Income/Loss from Operation = 302.000.001.000 - PL for Current Year (2)\n" +
                "Dividends =  303.000.000.000 - Dividend (3)\n" +
                "Today is not the last day of Month (e.g. today is 4/3/2024)\n" +
                "\n" +
                "=> (1), (2), (3) = values that take from the beginning of financial year (1/8/2023) till 29/02/2024");
        String accountType = "Capital";
        LedgerStatementPage ledgerStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,LEDGER_STATEMENT,LedgerStatementPage.class);
        String fromDate = ledgerStatementPage.getBeginDateOfFinanYear(FINANCIAL_YEAR);
        String toDate = ledgerStatementPage.getLastDateAfterCJE("dd/MM/yyyy");
        ledgerStatementPage.showLedger(FAIR,FINANCIAL_YEAR,accountType,LEDGER_GROUP_RETAINED_EARNING_ACCOUNT,fromDate,toDate,"After CJE");
        String beginningRetainedEarningsAc = ledgerStatementPage.getValueOfSubAcc("302.000.001.000","CUR Translation","Running Bal.");
        String netIncomeAc = ledgerStatementPage.getValueOfSubAcc("302.000.002.000","CUR Translation","Running Bal.");
        ledgerStatementPage.showLedger("","","",LEDGER_GROUP_DIVIDEND_ACCOUNT,"","","");
        String dividendsAc = ledgerStatementPage.getTotalInHKD(LEDGER_GROUP_DIVIDEND_ACCOUNT,"CUR Translation","Running Bal.");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand 'Financial Reports' menu");
        log("@Step 3: Click 'Retained Earnings'");
        RetainedEarningsPage page =
                ledgerStatementPage.navigatePage(FINANCIAL_REPORTS, RETAINED_EARNING, RetainedEarningsPage.class);
        log("@Step 4: Filter data on the same Financial Year as precondition");
        page.filter(FAIR,FINANCIAL_YEAR);
        log("@Verify 1: Beginning Retained Earnings value should be = value at precondition (1)\n" +
                "Net Income/Loss from Operation value should be = value at precondition (2)\n" +
                "Dividends value should be = value at precondition (3)");
        Assert.assertEquals(netIncomeAc,page.getAmount("Beginning Retained Earnings"),"FAILED! Beginning Retained Earnings amount displays incorrect.");
        Assert.assertEquals(beginningRetainedEarningsAc,page.getAmount("Net Income/Loss from Operation"),"FAILED! Net Income/Loss from Operation amount displays incorrect.");
        Assert.assertEquals(dividendsAc,page.getAmount("Dividends"),"FAILED! Beginning Retained Earnings amount displays incorrect.");
        log("INFO: Executed completely");
    }

}

