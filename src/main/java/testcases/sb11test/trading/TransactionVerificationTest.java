package testcases.sb11test.trading;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;
import pages.sb11.trading.TransactionVerificationPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class TransactionVerificationTest extends BaseCaseAQS {

    String website ="Fair999";
    String siteLogin = "SA01";

    @Test(groups = {"regression"})
    @TestRails(id = "2197")
    public void TransactionVerification_2197(){
        log("Validate Transaction Verification page is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Transaction Verification");
        TransactionVerificationPage transactionVerificationPage = welcomePage.navigatePage(TRADING,TRANSACTION_VERIFICATION,TransactionVerificationPage.class);
        log("Validate Transaction Verification page is displayed when navigate");
        Assert.assertTrue(transactionVerificationPage.getTitlePage().contains(TRANSACTION_VERIFICATION), "Failed! Transaction Verification page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2198")
    public void TransactionVerification_2198(){
        log("Validate UI on Transaction Verification is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Transaction Verification");
        TransactionVerificationPage transactionVerificationPage = welcomePage.navigatePage(TRADING,TRANSACTION_VERIFICATION,TransactionVerificationPage.class);
        log("Validate UI Info display correctly");
        transactionVerificationPage.filterTxn(website,siteLogin,"");
        log("Dropdown: Website, Site Login");
        Assert.assertEquals(transactionVerificationPage.ddpWebsite.getOptions(),TransactionVerification.WEBSITE,"Failed! Website dropdown is not displayed!");
        Assert.assertTrue(transactionVerificationPage.ddpSiteLogin.getOptions().contains(siteLogin),"Failed! Site Login dropdown is not displayed!");
        log("Datetime picker: Txn Date");
        Assert.assertEquals(transactionVerificationPage.lblDate.getText(),"Txn Date","Failed! Txn Date datetimepicker is not displayed!");
        log("Button: Show");
        Assert.assertEquals(transactionVerificationPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("INFO: Executed completely");
    }
}
