package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.generalReports.SystemMonitoringPage;
import pages.sb11.generalReports.systemmonitoring.WLRPCPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;
public class WLRPCTest extends BaseCaseAQS {
    @TestRails(id="23975")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23975(){
        log("@title: Validate that UI of WL & RPC displays correctly when no records found");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select From Date and To Date are the next of current day");
        log("@Step 3: Select Search button");
        String nextDay = DateUtils.getDate(+1, "dd/MM/yyyy",GMT_7);
        page.filter("","",nextDay,nextDay,"");
        log("Verify 1: UI of WL & RPC should display correctly when no records found");
        Assert.assertTrue(page.lblNoRecord.isDisplayed(),"FAILED! Message display incorrect");
        log("Verify 2: \"Export To Excel\" button should be disabled");
        page.verifyUI();
        log("INFO: Executed completely");
    }
    @TestRails(id="23980")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23980(){
        log("@title: Validate UI of WL & RPC displays correctly when showing data with Type is Client");
        log("@pre-condition 1: Client has some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is Client");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","Client",fromDate,"","");
        log("Verify 1: UI of WL & RPC should display correct table with Header is \"Client Name\"");
        page.verifyHeaderNameByType("Client");
        log("Verify 2: \"Export To Excel\" button should be enabled");
        Assert.assertTrue(page.btnExportToExcel.isEnabled(),"FAILED! Export To Excel button display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="23981")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23981(){
        log("@title: Validate UI of WL & RPC displays correctly when showing data with Type is All");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is All");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","",fromDate,"","");
        log("Verify 1: UI of WL & RPC should display correct tables with Header is \"Client Name\" on the left side and \"Bookie Name\" on the right side");
        page.verifyHeaderNameByType("All");
        log("Verify 2: \"Export To Excel\" button should be enabled");
        Assert.assertTrue(page.btnExportToExcel.isEnabled(),"FAILED! Export To Excel button display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="23982")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23982(){
        log("@title: Validate color of Difference value displays correctly when showing data with Type is All");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is All");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","",fromDate,"","");
        log("Verify 1: The color of Difference row of each Currency should show:\n" +
                "The green color if the amount is 0.00\n" +
                "The red color if the amount is different than 0.00 (both positive and negative)");
        page.checkColorOfDifferenceValue();
        log("INFO: Executed completely");
    }
    @TestRails(id="29503")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29503(){
        log("@title: Validate UI of WL & RPC displays correctly when showing data with Type is Bookie");
        log("@pre-condition 1: Bookie has some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is Bookie");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","Bookie",fromDate,"","");
        log("Verify 1: UI of WL & RPC should display correct tables with Header is \"Bookie Name\"");
        page.verifyHeaderNameByType("Bookie");
        log("Verify 2: \"Export To Excel\" button should be enabled");
        Assert.assertTrue(page.btnExportToExcel.isEnabled(),"FAILED! Export To Excel button display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="29506")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29506(){
        log("@title: Validate Difference value displays correctly when showing data with Type is All");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is All");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","",fromDate,"","");
        log("Verify 1: The value of Difference row of each Currency should show with method:\n" +
                "Difference = Total Balance in of Client - Total Balance in of Bookie");
        page.checkDifferenceValueDisplay();
        log("INFO: Executed completely");
    }
    @TestRails(id="23983")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23983(){
        log("@title: Validate the error message displays when showing data with invalid date range");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select invalid date range (out of the 3 months range)");
        String fromDate = DateUtils.getDate(-92,"dd/MM/yyyy",GMT_7);
        page.filter("","",fromDate,"","");
        log("@Step 3: Select Search button");
        page.btnShow.click();
        String mesAc = page.appArlertControl.getWarningMessage();
        log("Verify 1: Error message should display \"Invalid date range. You can see data up to 3 months.\"");
        Assert.assertEquals(mesAc,WLRPCT.MES_INVALID_DATE_RANGE,"FAILED! Message displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="23984")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23984(){
        log("@title: Validate user can export the excel report when showing data with Type is Client");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is Client");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-7,"dd/MM/yyyy",GMT_7);
        page.filter("","Client",fromDate,"","");
        log("@Step 5: Select Export To Excel button");
        page.exportToExcel();
        log("Verify 1: UI of WL & RPC should display correctly when showing with Type is Client");
        log("Verify 2: User should export the excel report with correct data successfully");
        page.verifyExcelFileDownloadCorrect("Client");
        try {
            FileUtils.removeFile(getDownloadPath()+"export-winloss.xlsx");
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }
    @TestRails(id="29535")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29535(){
        log("@title: Validate user can export the excel report when showing data with Type is Bookie");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is Bookie");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-7,"dd/MM/yyyy",GMT_7);
        page.filter("","Bookie",fromDate,"","");
        log("@Step 5: Select Export To Excel button");
        page.exportToExcel();
        log("Verify 1: UI of WL & RPC should display correctly when showing with Type is Bookie");
        log("Verify 2: User should export the excel report with correct data successfully");
        page.verifyExcelFileDownloadCorrect("Bookie");
        try {
             FileUtils.removeFile(getDownloadPath()+"export-winloss.xlsx");
        } catch (IOException e) {
             log(e.getMessage());
        }
        log("INFO: Executed completely");
    }
    @TestRails(id="23985")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23985(){
        log("@title: Validate user can export the excel report when showing data with Type is All");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is All");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-7,"dd/MM/yyyy",GMT_7);
        page.filter("","All",fromDate,"","");
        log("@Step 5: Select Export To Excel button");
        page.exportToExcel();
        log("Verify 1: UI of WL & RPC should display correctly when showing with Type is Client/Bookie");
        log("Verify 2: User should export the excel report with correct data successfully");
        page.verifyExcelFileDownloadCorrect("All");
        try {
            FileUtils.removeFile(getDownloadPath()+"export-winloss.xlsx");
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }
    @TestRails(id="23986")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_23986(){
        log("@title: Validate UI of WL & RPC displays correctly when filtering with available specific currency for Client");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select available currency (e.g. HKD)");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","Client",fromDate,"","HKD");
        log("Verify 1: UI should display correct Currency Code (e.g. HKD) for Client");
        Assert.assertEquals(page.getLstCurDisplay("Client").size(),1,"FAILED! UI display correct Currency Code incorrect");
        Assert.assertEquals(page.getLstCurDisplay("Client").get(0),"HKD","FAILED! UI display correct Currency Code incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="29536")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29536(){
        log("@title: Validate UI of WL & RPC displays correctly when filtering with available specific currency for Bookie");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select available currency (e.g. HKD)");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","Bookie",fromDate,"","HKD");
        log("Verify 1: UI should display correct Currency Code (e.g. HKD) for Bookie");
        Assert.assertEquals(page.getLstCurDisplay("Bookie").size(),1,"FAILED! UI display correct Currency Code incorrect");
        Assert.assertEquals(page.getLstCurDisplay("Bookie").get(0),"HKD","FAILED! UI display correct Currency Code incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="29537")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29537(){
        log("@title: Validate UI of WL & RPC displays correctly when filtering with available specific currency for All");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select available currency (e.g. HKD)");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","All",fromDate,"","HKD");
        log("Verify 1: UI should display Client table on the left side and Bookie table on the right side with correct Currency Code (e.g. HKD) for both tables.");
        Assert.assertEquals(page.getLstCurDisplay("All").size(),1,"FAILED! UI display correct Currency Code incorrect");
        Assert.assertEquals(page.getLstCurDisplay("All").get(0),"HKD","FAILED! UI display correct Currency Code incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="29505")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29505(){
        log("@title: Validate UI of WL & RPC displays correctly when filtering with unavailable specific currency for Client");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select unavailable currency (e.g. AED)");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","Client",fromDate,"","AED");
        log("Verify 1: UI should display \"No records found\"");
        Assert.assertTrue(page.lblNoRecord.isDisplayed(),"FAILED! UI display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="29538")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29538(){
        log("@title: Validate UI of WL & RPC displays correctly when filtering with unavailable specific currency for Bookie");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select unavailable currency (e.g. AED)");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","Bookie",fromDate,"","AED");
        log("Verify 1: UI should display \"No records found\"");
        Assert.assertTrue(page.lblNoRecord.isDisplayed(),"FAILED! UI display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="29539")
    @Test(groups = {"regression","2024.V.3.0"})
    public void WLRPCT_29539(){
        log("@title: Validate UI of WL & RPC displays correctly when filtering with unavailable specific currency for All");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select unavailable currency (e.g. AED)");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","All",fromDate,"","AED");
        log("Verify 1: UI should display \"No records found\"");
        Assert.assertTrue(page.lblNoRecord.isDisplayed(),"FAILED! UI display incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id="24002")
    @Test(groups = {"regression1","2024.V.3.0"})
    public void WLRPCT_24002(){
        //Wait a Andy for updating
        log("@title: Validate the Winlose Amount of Bookie type displays correctly");
        log("@pre-condition 1: Client and Bookie have some data");
        log("@Step 1: Go to General Reports > System Monitoring > WL & RPC");
        WLRPCPage page = welcomePage.navigatePage(GENERAL_REPORTS,SYSTEM_MONITORING, SystemMonitoringPage.class).goToTabName(WL_RPC, WLRPCPage.class);
        log("@Step 2: Select valid date range");
        log("@Step 3: Select Type is Bookie");
        log("@Step 4: Select Search button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        page.filter("","Bookie",fromDate,"","");
        log("@Step 5: Check the value in Winlose Amount column");
        log("Verify 1: Winlose Amount = Sum Win/Lose[1] amount of all Member Summary (in Bookie Statement)");
        Assert.assertTrue(page.lblNoRecord.isDisplayed(),"FAILED! UI display incorrect");
        log("INFO: Executed completely");
    }
}
