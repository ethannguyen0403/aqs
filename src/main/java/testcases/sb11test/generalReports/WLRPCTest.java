package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.generalReports.SystemMonitoringPage;
import pages.sb11.generalReports.systemmonitoring.WLRPCPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

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
        page.filter("","Client","","","");
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
        page.filter("","","","","");
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
        page.filter("","","","","");
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
        page.filter("","Bookie","","","");
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
        page.filter("","","","","");
        log("Verify 1: The value of Difference row of each Currency should show with method:\n" +
                "Difference = Total Balance in of Client - Total Balance in of Bookie");
        page.checkDifferenceValueDisplay();
        log("INFO: Executed completely");
    }
}
