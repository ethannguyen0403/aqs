package testcases.sb11test.trading;
import com.paltech.utils.DateUtils;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.trading.KeyClientsReportsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class KeyClientsReportTest extends BaseCaseAQS {
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4142")
    public void Key_Clients_Report_4142(){
        log("@title: Validate the UI displays properly");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading >> Key Clients Report");
        KeyClientsReportsPage keyClientsReportsPage = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 3: Filter which has data");
        String fromDate = "15/09/2023";
        String toDate = "30/09/2023";
        String blackColor = "rgba(33, 37, 41, 1)";
        String redColor = "rgba(33, 37, 41, 1)";
        keyClientsReportsPage.filter("","",fromDate,toDate);

        log("Validate The UI displays properly");
        Assert.assertTrue(keyClientsReportsPage.lblAmountNote.isDisplayed(),"FAILED! A note as 'Amounts are shown in [HKD]' display incorrect");
        Assert.assertEquals(keyClientsReportsPage.tblGroup.getHeaderNameOfRows(),KeyClientsReport.TABLE_HEADER,"FAILED! Header table name display incorrect");
        Assert.assertEquals(keyClientsReportsPage.getTotal(keyClientsReportsPage.tblGroup.getColumnIndexByName("Cricket")-1).getColour("color"),blackColor,"FAILED! Positive amounts display incorrect");
        Assert.assertEquals(keyClientsReportsPage.getTotal(keyClientsReportsPage.tblGroup.getColumnIndexByName("Others")-1).getColour("color"),redColor,"FAILED! Negative amounts display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4143")
    public void Key_Clients_Report_4143(){
        log("@title: Validate Group and Sport dropdown work properly");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading >> Key Clients Report");
        KeyClientsReportsPage keyClientsReportsPage = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 3: Expand 'Group' drop-down list");
        log("Verify 1: Validate There are 2 options: S1 (default) and No.7 CRI");
        Assert.assertEquals(keyClientsReportsPage.ddGroup.getOptions(),KeyClientsReport.GROUP_LIST,"FAILED! Group list display incorrect.");
        log("@Step 4: Select 'S1' then expand Sport dropdown list");
        log("Verify 2: Validate There are 2 options: All (default) and Cricket");
        Assert.assertEquals(keyClientsReportsPage.ddSport.getOptions(),KeyClientsReport.SPORT_LIST,"FAILED! Sport list display incorrect.");
        log("@Step 5: Select 'No.7 CRI' then expand Sport dropdown list");
        keyClientsReportsPage.ddGroup.selectByVisibleText("No.7 CRI");
        log("Verify 3: Sport dropdown list is disabled");
        Assert.assertFalse(keyClientsReportsPage.ddSport.isEnabled(),"FAILED! Sport dropdown display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "4148")
    public void Key_Clients_Report_4148(){
        log("@title: Validate UI of No.7 CRI group");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Go to Trading > Key Clients Report");
        KeyClientsReportsPage page = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 2: Filter From Date - To date which has data with filter as Group = 'No.7 CRI' (range date filter for 1 week)");
        String fromDate = DateUtils.getDate(-10,"dd/MM/yyyy",GMT_7);
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        String group = "No.7 CRI";
        page.filter(group,"",fromDate,toDate);
        log("Verify 1: Data table will have title following the format as 'No.7 CRI [<filtered time range>]'");
        Assert.assertEquals(page.tblGroup.getHeaderNameOfRows().get(0),String.format(KeyClientsReport.TABLE_TITLE,group,fromDate,toDate),"FAILED! Table title display incorrect.");
        log("Verify 2: Table header name display correct");
        Assert.assertEquals(page.getLstTableHeader(),KeyClientsReport.TABLE_HEADER_NAME,"FAILED! Table Header display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31","ethan2.0"})
    @TestRails(id = "4149")
    public void Key_Clients_Report_4149(){
        log("@title: Validate data table of No.7 CRI group");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Go to Trading > Key Clients Report");
        KeyClientsReportsPage page = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 2: Filter From Date - To date which has data with filter as Group = 'No.7 CRI' (range date filter for 1 week)");
        String fromDate = DateUtils.getDate(-9,"dd/MM/yyyy",GMT_7);
        String group = "No.7 CRI";
        page.filter(group,"",fromDate,"");
        log("Verify 1: C/F. ToDate: the first C/F. ToDate = Final Result today and the next C/F. ToDate =  Final Result today + C/F. ToDate yesterday");
        page.verifyCFToDate("Cricket");
        log("Verify 2: Total row: Sums up all amounts of Home, Away, and Win/Loss columns display correct");
        page.verifyTotalByColumnName("Home");
        page.verifyTotalByColumnName("Away");
        page.verifyTotalByColumnName("Final Result");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31","ethan2.0"})
    @TestRails(id = "8569")
    public void Key_Clients_Report_8569(){
        log("@title: Validate error message displays if tried to filter more than 1 month");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading >> Key Clients Report");
        KeyClientsReportsPage keyClientsReportsPage = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 3: Select From Date To Date more than 1 month");
        log("@Step 4: Click Show button");
        String fromDate = DateUtils.getDate(-33,"dd/MM/yyyy",GMT_7);
        keyClientsReportsPage.dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        keyClientsReportsPage.btnShow.click();
        log("Verify 3: Error message 'Date range should not be more than 1 month.' displays");
        Assert.assertTrue(keyClientsReportsPage.lblAlertThan1Month.isDisplayed(),"FAILED! Error message display incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "9162")
    public void Key_Clients_Report_9162(){
        log("@title: Validate UI of S1 CRI group");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Go to Trading > Key Clients Report");
        KeyClientsReportsPage page = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 2: Filter From Date - To date which has data with filter as Group = 'S1' and 'Sport' = 'All'");
        String fromDate = DateUtils.getDate(-9,"dd/MM/yyyy",GMT_7);
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        String group = "S1";
        String sport = "All";
        page.filter(group,sport,fromDate,toDate);
        log("Verify 1: table title 'S1 COMPLETED [filtered time range]' and header name table name");
        Assert.assertEquals(page.tblGroup.getHeaderNameOfRows().get(0),String.format(KeyClientsReport.TABLE_TITLE,group + " COMPLETED",fromDate,toDate),"FAILED! Table title display incorrect.");
        Assert.assertEquals(page.getLstTableHeader(),KeyClientsReport.TABLE_HEADER_S1_ALL,"FAILED! Table Header display incorrect");
        log("@Step 2: Filter From Date - To date which has data with filter as Group = 'S1' and 'Sport' = 'Cricket'");
        sport = "Cricket";
        page.filter(group,sport,fromDate,toDate);
        log("Verify 2: table title 'S1 CRI [filtered time range]' and header name table name");
        Assert.assertEquals(page.tblGroup.getHeaderNameOfRows().get(0),String.format(KeyClientsReport.TABLE_TITLE,group + " CRI",fromDate,toDate),"FAILED! Table title display incorrect.");
        Assert.assertEquals(page.getLstTableHeader(),KeyClientsReport.TABLE_HEADER_NAME,"FAILED! Table Header display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "9163")
    public void Key_Clients_Report_9163(){
        log("@title: Validate data table of S1 CRI group with sport = All");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Go to Trading > Key Clients Report");
        KeyClientsReportsPage page = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 2: Filter From Date - To date which has data with filter as Group = 'S1' and 'Sport' = 'All'");
        String fromDate = DateUtils.getDate(-9,"dd/MM/yyyy",GMT_7);
        String group = "S1";
        String sport = "All";
        page.filter(group,sport,fromDate,"");
        log("Verify 1: C/F. ToDate: the first C/F. ToDate = Total and the next C/F. ToDate =  Total today + C/F. ToDate yesterday");
        page.verifyCFToDate(sport);
        log("Verify 2: Total row: Sums up all amounts of Cricket, Soccer, Tennis, Others and Total columns display correct");
        page.verifyTotalByColumnName("Cricket");
        page.verifyTotalByColumnName("Soccer");
        page.verifyTotalByColumnName("Tennis");
        page.verifyTotalByColumnName("Others");
        page.verifyTotalByColumnName("Total");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.10.31"})
    @TestRails(id = "9164")
    public void Key_Clients_Report_9164(){
        log("@title: Validate data table of S1 CRI group with sport = Cricket");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Go to Trading > Key Clients Report");
        KeyClientsReportsPage page = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 2: Filter From Date - To date which has data with filter as Group = 'No.7 CRI' (range date filter for 1 week)");
        String fromDate = DateUtils.getDate(-9,"dd/MM/yyyy",GMT_7);
        String group = "S1";
        String sport = "Cricket";
        page.filter(group,sport,fromDate,"");
        log("Verify 1: C/F. ToDate: the first C/F. ToDate = Final Result today and the next C/F. ToDate =  Final Result today + C/F. ToDate yesterday");
        page.verifyCFToDate(sport);
        log("Verify 2: Total row: Sums up all amounts of Home, Away, and Win/Loss columns display correct");
        page.verifyTotalByColumnName("Home");
        page.verifyTotalByColumnName("Away");
        page.verifyTotalByColumnName("Final Result");
        log("INFO: Executed completely");
    }
}
