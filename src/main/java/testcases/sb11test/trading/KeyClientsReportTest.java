package testcases.sb11test.trading;
import com.paltech.utils.DateUtils;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;
import pages.sb11.trading.AccountPercentPage;
import pages.sb11.trading.KeyClientsReportsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

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
        Assert.assertEquals(keyClientsReportsPage.getTotal(keyClientsReportsPage.colCricket).getColour("color"),blackColor,"FAILED! Positive amounts display incorrect");
        Assert.assertEquals(keyClientsReportsPage.getTotal(keyClientsReportsPage.colOther).getColour("color"),redColor,"FAILED! Negative amounts display incorrect");
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
    @TestRails(id = "8569")
    public void Key_Clients_Report_8569(){
        log("@title: Validate error message displays if tried to filter more than 1 month");
        log("@pre-condition: Key Clients Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading >> Key Clients Report");
        KeyClientsReportsPage keyClientsReportsPage = welcomePage.navigatePage(TRADING,KEY_CLIENTS_REPORT,KeyClientsReportsPage.class);
        log("@Step 3: Select From Date To Date more than 1 month");
        log("@Step 4: Click Show button");
        String fromDate = DateUtils.getDate(-32,"dd/MM/yyyy",GMT_7);
        keyClientsReportsPage.dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        keyClientsReportsPage.btnShow.click();
        log("Verify 3: Error message 'Date range should not be more than 1 month.' displays");
        Assert.assertTrue(keyClientsReportsPage.lblAlertThan1Month.isDisplayed(),"FAILED! Error message display incorrect.");
        log("INFO: Executed completely");
    }
}
