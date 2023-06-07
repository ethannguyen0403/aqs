package testcases.sb11test.trading;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.BLSettingPage;
import pages.sb11.trading.PhoneBettingPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class PhoneBettingTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2183")
    public void Phone_Betting_TC_001(){
        log("@title: Validate Phone Betting page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Phone Betting");
        PhoneBettingPage phoneBettingPage = welcomePage.navigatePage(TRADING,PHONE_BETTING, PhoneBettingPage.class);
        log("Validate BL Settings page is displayed with correctly title");
        Assert.assertTrue(phoneBettingPage.getTitlePage().contains("Phone Betting Report"), "Failed! Phone Betting page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2184")
    public void Phone_Betting_TC_002(){
        log("@title: Validate UI on Phone Betting is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Phone Betting");
        PhoneBettingPage phoneBettingPage = welcomePage.navigatePage(TRADING,PHONE_BETTING, PhoneBettingPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Financial Year, Sports");
        Assert.assertEquals(phoneBettingPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(phoneBettingPage.ddpFinancialYear.getOptions(),FINANCIAL_YEAR_LIST,"Failed! Financial Year dropdown is not displayed!");
        Assert.assertTrue(phoneBettingPage.ddpSport.getOptions().contains("Soccer"),"Failed! Sport dropdown is not displayed!");
        log("Datetimepicker: From Date, To Date");
        Assert.assertEquals(phoneBettingPage.lblFromDate.getText(),"From Date","Failed! From Date datetimepicker is not displayed!");
        Assert.assertEquals(phoneBettingPage.lblToDate.getText(),"To Date","Failed! To Date datetimepicker is not displayed!");
        log("Button: Show button");
        Assert.assertEquals(phoneBettingPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("INFO: Executed completely");
    }
}
