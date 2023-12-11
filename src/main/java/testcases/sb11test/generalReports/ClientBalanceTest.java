package testcases.sb11test.generalReports;

import org.testng.Assert;
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
}
