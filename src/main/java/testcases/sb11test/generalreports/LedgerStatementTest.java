package testcases.sb11test.generalreports;

import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.accounting.ChartOfAccountPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.ManualBetBetEntryPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;
import static common.SBPConstants.CHART_OF_ACCOUNT;

public class LedgerStatementTest extends BaseCaseAQS {

    @TestRails(id="841")
    @Test(groups = {"smoke1"})
    public void Bet_Entry_TC841(){
        log("@title: Validate users can place Mixed Sports bets successfully");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Accounting > Payment");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("@Step 3: In Debit, select From = Ledger, Ledger = ledger account at precondition then click Add");




        log("INFO: Executed completely");
    }
}
