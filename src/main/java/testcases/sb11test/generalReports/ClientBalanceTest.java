package testcases.sb11test.generalReports;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.ClientBalancePage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class ClientBalanceTest extends BaseCaseAQS {

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "15739")
    public void ClientBalanceTC_15739(){
        log("@title: Validate column 'PIC' is displayed correctly next to Status column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access General Reports > Client Balance");
        ClientBalancePage clientBalancePage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_BALANCE, ClientBalancePage.class);
        log("@Verify 1: Validate column 'PIC' is displayed correctly next to Status column");
        List<String> headerClient = clientBalancePage.tblClient.getHeaderNameOfRows();
        Assert.assertTrue(headerClient.indexOf("PIC") - headerClient.indexOf("Status") == 1,
                "FAILED! Column 'PIC' is NOT displayed  next to Status column");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @Parameters({"superCode", "clientCode"})
    @TestRails(id = "15739")
    public void ClientBalanceTC_15741(String superCode, String clientCode){
        log("@title: Validate can filter with SM code");
        log("@Precondition: Login the page\n" +
                " Already have valid SM code (e.g: QA2112)");
        log("@Step 1: Access General Reports > Client Balance");
        ClientBalancePage clientBalancePage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_BALANCE, ClientBalancePage.class);
        log("@Step 2: Filter with valid data at precondition");
        clientBalancePage.filter("", "", "", "", superCode);
        log("@Verify 1: Validate the Client of SM should be displayed");
        String clientName = String.format("%s - %s", superCode, clientCode);
        Assert.assertEquals(clientBalancePage.findRowClientIndex(clientName), 1, "FAILED! Not found the client: "+ clientName);
    }
}
