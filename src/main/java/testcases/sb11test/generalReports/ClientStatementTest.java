package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.generalReports.ClientStatementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;
import static common.SBPConstants.GENERAL_REPORTS;
import static common.SBPConstants.CLIENT_STATEMENT;

public class ClientStatementTest extends BaseCaseAQS {
    String viewBy = "Client Point";
    String companyUnit = "Kastraki Limited";
    String financialYear = "Year 2022-2023";
    String clients = "QA_TEST - QA Client Test";

    @Test(groups = {"smoke"})
    @TestRails(id = "309")
    public void ClientStatementTC_309(){
        String openingVal;
        String winLossVal;
        String commissionVal;
        String recPayVal;
        String actualVal;

        log("@title: Validate that Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, companyUnit, financialYear, clients, "","");

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
    @TestRails(id = "310")
    public void ClientStatementTC_310(){
        String openingVal;
        String closingVal;

        log("@title: Validate that Opening value today is Closing of yesterday");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2.1: Filter a client with client point view on current date");
        clientPage.filter(viewBy, companyUnit, financialYear, clients, "","");

        log("@Step 2.2: Get Opening value");
        openingVal = clientPage.getSuperCellValue(clientPage.colOpening).replace(",","");

        log("@Step 3: Filter a client with client point view on current date - 1");
        clientPage.filter(viewBy, companyUnit, financialYear, clients, DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"),
                DateUtils.getDateBeforeCurrentDate(1,"dd/MM/yyyy"));

        log("@Step 3.1: Get Closing value");
        closingVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");

        log("@Validate that Opening value today is Closing of yesterday");
        Assert.assertEquals(closingVal,openingVal,"FAILED! Closing Balance of previous date is not equal to Opening Balance of current date, " +
                "Opening:"+openingVal+" and Closing:"+closingVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @TestRails(id = "587")
    public void ClientStatementTC_587(){
        String totalGrandMasterVal;
        String totalGrandHKDVal;

        log("@title: Validate total in HKD of Master match with Grand Total in HKD at bottom");
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to General Reports > Client Statement");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS, CLIENT_STATEMENT, ClientStatementPage.class);

        log("@Step 2: Filter a client with client point view");
        clientPage.filter(viewBy, companyUnit, financialYear, clients, "","");

        log("Validate total in HKD of Master match with Grand Total in HKD at bottom");
        totalGrandMasterVal = clientPage.getMasterCellValue("Total in", clientPage.colClosing).replace(",","");
        totalGrandHKDVal = clientPage.getGrandTotal("HKD").replace(",","");

        Assert.assertEquals(totalGrandMasterVal,totalGrandHKDVal,"FAILED! Grand Master value is not equal Grand HKD, Grand Master:"+totalGrandMasterVal+" and Grand HKD:"+totalGrandHKDVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @TestRails(id = "588")
    public void ClientStatementTC_588(){
        String openingVal;
        String winLossVal;
        String commissionVal;
        String recPayVal;
        String movementVal;
        String closingVal;
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
        clientPage.filter(viewBy, companyUnit, financialYear, clients, "","");

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

        clientPage.filter("Company Point", companyUnit, financialYear, clients, "","");
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
}
