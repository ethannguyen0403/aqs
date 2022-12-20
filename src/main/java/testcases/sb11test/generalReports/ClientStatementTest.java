package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.ClientStatementPage;
import pages.sb11.generalReports.ClientSummaryPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class ClientStatementTest extends BaseCaseAQS {
    String viewBy = "Client Point";
    String companyUnit = "Kastraki Limited";
    String agentCode = "QASAHK00";
    String agentComCode = "QATE01-COM";
    String openingVal;
    String winLossVal;
    String commissionVal;
    String recPayVal;
    String movementVal;
    String closingVal;
    @Test(groups = {"smoke"})
    @Parameters({"clientCode"})
    @TestRails(id = "309")
    public void ClientStatementTC_309(String clientCode){
        String actualVal;

        log("@title: Validate that Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, companyUnit, FINANCIAL_YEAR, clientCode, "","");

        log("Validate Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening).replace(",","");
        winLossVal = clientPage.getSuperCellValue(clientPage.colWinLoss).replace(",","");
        commissionVal = clientPage.getSuperCellValue(clientPage.colCommission).replace(",","");
        recPayVal = clientPage.getSuperCellValue(clientPage.colRecPay).replace(",","");
        actualVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");

        double expectedVal = Double.parseDouble(openingVal) + Double.parseDouble(winLossVal) + Double.parseDouble(commissionVal)
                + Double.parseDouble(recPayVal);

        Assert.assertEquals(String.valueOf(expectedVal),actualVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualVal+" and expected:"+expectedVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @Parameters("clientCode")
    @TestRails(id = "310")
    public void ClientStatementTC_310(String clientCode){
        log("@title: Validate that Opening value today is Closing of yesterday");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2.1: Filter a client with client point view on current date");
        clientPage.filter(viewBy, companyUnit, FINANCIAL_YEAR, clientCode, "","");

        log("@Step 2.2: Get Opening value");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening).replace(",","");

        log("@Step 3: Filter a client with client point view on current date - 1");
        clientPage.filter(viewBy, companyUnit, FINANCIAL_YEAR, clientCode, DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"),
                DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"));

        log("@Step 3.1: Get Closing value");
        closingVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");

        log("@Validate that Opening value today is Closing of yesterday");
        Assert.assertEquals(closingVal,openingVal,"FAILED! Closing Balance of previous date is not equal to Opening Balance of current date, " +
                "Opening:"+openingVal+" and Closing:"+closingVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @Parameters("clientCode")
    @TestRails(id = "587")
    public void ClientStatementTC_587(String clientCode){
        String totalGrandMasterVal;
        String totalGrandHKDVal;

        log("@title: Validate total in HKD of Master match with Grand Total in HKD at bottom");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, companyUnit, FINANCIAL_YEAR, clientCode, "","");

        log("Validate total in HKD of Master match with Grand Total in HKD at bottom");
        totalGrandMasterVal = clientPage.getMasterCellValue("Total in", clientPage.colClosing).replace(",","");
        totalGrandHKDVal = clientPage.getGrandTotal("HKD").replace(",","");

        Assert.assertEquals(totalGrandMasterVal,totalGrandHKDVal,"FAILED! Grand Master value is not equal Grand HKD, Grand Master:"+totalGrandMasterVal+" and Grand HKD:"+totalGrandHKDVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @Parameters("clientCode")
    @TestRails(id = "588")
    public void ClientStatementTC_588(String clientCode){
        String reversedOpeningVal;
        String reversedWinLossVal;
        String reversedCommissionVal;
        String reversedRecPayVal;
        String reversedMovementVal;
        String reversedClosingVal;

        log("@title: Validate values are the same but opposite each other when viewing by Company Point and Client Point");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, companyUnit, FINANCIAL_YEAR, clientCode, "","");

        log("Verify Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening).replace(",","");
        winLossVal = clientPage.getSuperCellValue(clientPage.colWinLoss).replace(",","");
        commissionVal = clientPage.getSuperCellValue(clientPage.colCommission).replace(",","");
        recPayVal = clientPage.getSuperCellValue(clientPage.colRecPay).replace(",","");
        movementVal = clientPage.getSuperCellValue(clientPage.colMovement).replace(",","");
        closingVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");
        reversedOpeningVal = clientPage.reverseValue(openingVal);
        reversedWinLossVal = clientPage.reverseValue(winLossVal);
        reversedCommissionVal = clientPage.reverseValue(commissionVal);
        reversedRecPayVal = clientPage.reverseValue(recPayVal);
        reversedMovementVal = clientPage.reverseValue(movementVal);
        reversedClosingVal = clientPage.reverseValue(closingVal);

        clientPage.filter("Company Point", companyUnit, FINANCIAL_YEAR, clientCode, "","");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening).replace(",","");
        winLossVal = clientPage.getSuperCellValue(clientPage.colWinLoss).replace(",","");
        commissionVal = clientPage.getSuperCellValue(clientPage.colCommission).replace(",","");
        recPayVal = clientPage.getSuperCellValue(clientPage.colRecPay).replace(",","");
        movementVal = clientPage.getSuperCellValue(clientPage.colMovement).replace(",","");
        closingVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");

        Assert.assertEquals(openingVal,reversedOpeningVal,"FAILED! Closing Balance is not calculated correctly, actual:"+openingVal+" and expected:"+reversedOpeningVal);
        Assert.assertEquals(winLossVal,reversedWinLossVal,"FAILED! Closing Balance is not calculated correctly, actual:"+winLossVal+" and expected:"+reversedWinLossVal);
        Assert.assertEquals(commissionVal,reversedCommissionVal,"FAILED! Closing Balance is not calculated correctly, actual:"+commissionVal+" and expected:"+reversedCommissionVal);
        Assert.assertEquals(recPayVal,reversedRecPayVal,"FAILED! Closing Balance is not calculated correctly, actual:"+recPayVal+" and expected:"+reversedRecPayVal);
        Assert.assertEquals(movementVal,reversedMovementVal,"FAILED! Closing Balance is not calculated correctly, actual:"+movementVal+" and expected:"+reversedMovementVal);
        Assert.assertEquals(closingVal,reversedClosingVal,"FAILED! Closing Balance is not calculated correctly, actual:"+closingVal+" and expected:"+reversedClosingVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @Parameters({"clientCode"})
    @TestRails(id = "861")
    public void ClientStatementTC_861(String clientCode){
        String actualOpeningVal;
        String actualWinloseVal;
        String actualRecPayVal;
        String actualMovementVal;
        String actualClosingVal;

        log("@title: Validate value of agent (not COM, LED) in main page match with member summary page");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, companyUnit, FINANCIAL_YEAR, clientCode, "","");

        log("Validate value of agent (not COM, LED) in main page match with member summary page");
        openingVal = clientPage.getAgentCellValue(agentCode,clientPage.colOpening).replace(",","");
        winLossVal = clientPage.getAgentCellValue(agentCode,clientPage.colWinLoss).replace(",","");
        recPayVal = clientPage.getAgentCellValue(agentCode,clientPage.colRecPay).replace(",","");
        movementVal = clientPage.getAgentCellValue(agentCode,clientPage.colMovement).replace(",","");
        closingVal = clientPage.getAgentCellValue(agentCode,clientPage.colClosing).replace(",","");

        ClientSummaryPopup popup = clientPage.openSummaryPopup(agentCode);
        actualOpeningVal = popup.getGrandTotal("HKD",popup.colOpeningTotal).replace(",","");
        actualWinloseVal = popup.getGrandTotal("HKD",popup.colWinLoseTotal).replace(",","");
        actualRecPayVal = popup.getGrandTotal("HKD",popup.colRecPayTotal).replace(",","");
        actualMovementVal = popup.getGrandTotal("HKD",popup.colMovementTotal).replace(",","");
        actualClosingVal = popup.getGrandTotal("HKD",popup.colClosingTotal).replace(",","");

        Assert.assertEquals(openingVal,actualOpeningVal,"FAILED! Closing Balance is not calculated correctly, actual:"+openingVal+" and expected:"+actualOpeningVal);
        Assert.assertEquals(winLossVal,actualWinloseVal,"FAILED! WinLose Balance is not calculated correctly, actual:"+winLossVal+" and expected:"+actualWinloseVal);
        Assert.assertEquals(recPayVal,actualRecPayVal,"FAILED! RecPay Balance is not calculated correctly, actual:"+recPayVal+" and expected:"+actualRecPayVal);
        Assert.assertEquals(movementVal,actualMovementVal,"FAILED! Movement Balance is not calculated correctly, actual:"+movementVal+" and expected:"+actualMovementVal);
        Assert.assertEquals(closingVal,actualClosingVal,"FAILED! Closing Balance is not calculated correctly, actual:"+closingVal+" and expected:"+actualClosingVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @Parameters({"clientCode"})
    @TestRails(id = "865")
    public void ClientStatementTC_865(String clientCode){
        String actualOpeningVal;
        String actualCommVal;
        String actualRecPayVal;
        String actualMovementVal;
        String actualClosingVal;

        log("@title: Validate value of agent COM in main page match with member summary page");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, companyUnit, FINANCIAL_YEAR, clientCode, "","");

        log("Validate value of agent COM in main page match with member summary page");
        openingVal = clientPage.getAgentCellValue(agentComCode,clientPage.colOpening).replace(",","");
        commissionVal = clientPage.getAgentCellValue(agentComCode,clientPage.colCommission).replace(",","");
        recPayVal = clientPage.getAgentCellValue(agentComCode,clientPage.colRecPay).replace(",","");
        movementVal = clientPage.getAgentCellValue(agentComCode,clientPage.colMovement).replace(",","");
        closingVal = clientPage.getAgentCellValue(agentComCode,clientPage.colClosing).replace(",","");

        ClientSummaryPopup popup = clientPage.openSummaryPopup(agentComCode);
        actualOpeningVal = popup.getGrandTotal("HKD",popup.colOpeningTotal).replace(",","");
        actualCommVal = popup.getGrandTotal("HKD",popup.colComissionTotal).replace(",","");
        actualRecPayVal = popup.getGrandTotal("HKD",popup.colRecPayTotal).replace(",","");
        actualMovementVal = popup.getGrandTotal("HKD",popup.colMovementTotal).replace(",","");
        actualClosingVal = popup.getGrandTotal("HKD",popup.colClosingTotal).replace(",","");

        Assert.assertEquals(openingVal,actualOpeningVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualOpeningVal+" and expected:"+openingVal);
        Assert.assertEquals(commissionVal,actualCommVal,"FAILED! Commission Balance is not calculated correctly, actual:"+actualCommVal+" and expected:"+commissionVal);
        Assert.assertEquals(recPayVal,actualRecPayVal,"FAILED! RecPay Balance is not calculated correctly, actual:"+actualRecPayVal+" and expected:"+recPayVal);
        Assert.assertEquals(movementVal,actualMovementVal,"FAILED! Movement Balance is not calculated correctly, actual:"+actualMovementVal+" and expected:"+movementVal);
        Assert.assertEquals(closingVal,actualClosingVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualClosingVal+" and expected:"+closingVal);
        log("INFO: Executed completely");
    }
}
