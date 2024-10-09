package testcases.sb11test.trading;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.trading.PhoneBettingPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class PhoneBettingTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2183")
    public void Phone_Betting_2183(){
        log("@title: Validate Phone Betting page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Phone Betting");
        PhoneBettingPage phoneBettingPage = welcomePage.navigatePage(TRADING,PHONE_BETTING, PhoneBettingPage.class);
        log("Validate BL Settings page is displayed with correctly title");
        Assert.assertTrue(phoneBettingPage.getTitlePage().contains("Phone Betting Report"), "Failed! Phone Betting page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2184")
    public void Phone_Betting_2184(){
        log("@title: Validate UI on Phone Betting is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Phone Betting");
        PhoneBettingPage phoneBettingPage = welcomePage.navigatePage(TRADING,PHONE_BETTING, PhoneBettingPage.class);
        log("Validate UI Info display correctly");
        phoneBettingPage.verifyUI();
        log("INFO: Executed completely");
    }
}
