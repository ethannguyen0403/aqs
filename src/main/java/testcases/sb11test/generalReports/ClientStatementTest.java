package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.ClientStatementPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;
import static common.SBPConstants.GENERAL_REPORTS;
import static common.SBPConstants.CLIENT_STATEMENT;

public class ClientStatementTest extends BaseCaseAQS {

    @Test(groups = {"smoke"})
    @TestRails(id = "309")
    public void ClientStatementTC_309(){
        String viewBy = "Client Point";
        String companyUnit = "Kastraki Limited";
        String financialYear = "Year 2022-2023";
        String clients = "QA_TEST - QA Client Test";
        String openingVal;
        String winLossVal;
        String commissionVal;
        String recPayVal;
        String actualVal;

        log("@title: Verify that Closing of Super = Opening + Win/Loss + Commission + Rec/Pay/CA/RB/Adj");
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
        actualVal = clientPage.getSuperCellValue(clientPage.colClosing).replace(",","");

        double expectedVal = Double.parseDouble(openingVal) + Double.parseDouble(winLossVal) + Double.parseDouble(commissionVal)
                + Double.parseDouble(recPayVal);

        Assert.assertEquals(String.valueOf(expectedVal),actualVal,"FAILED! Closing Balance is not calculated correctly, actual:"+actualVal+" and expected:"+expectedVal);
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke1"})
    @TestRails(id = "310")
    public void ClientStatementTC_310(){
        String viewBy = "Client Point";
        String companyUnit = "Kastraki Limited";
        String financialYear = "Year 2022-2023";
        String clients = "QA_TEST - QA Client Test";
        String openingVal;
        String closingVal;

        log("@title: Verify that Opening value today is Closing of yesterday");
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

        log("@Verify that Opening value today is Closing of yesterday");
        Assert.assertEquals(closingVal,openingVal,"FAILED! Closing Balance of previous date is not equal to Opening Balance of current date, " +
                "Opening:"+openingVal+" and Closing:"+closingVal);
        log("INFO: Executed completely");
    }
}
