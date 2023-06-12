package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BookieInfoTest extends BaseCaseAQS {

    @Test(groups = {"smoke"})
    @TestRails(id = "2202")
    public void Bookie_Info_TC_001(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("Verify that Page title is correctly display");
        Assert.assertEquals(bookieInfoPage.getTitlePage(),"Master Bookie Info","FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }
    @Test(groups = {"smoke"})
    @TestRails(id = "2203")
    public void Bookie_Info_TC_002(){
        log("@title: Validate UI on Bookie Info is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Support By, Currency, Status");
        Assert.assertEquals(bookieInfoPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertTrue(bookieInfoPage.ddpSupportBy.getOptions().contains("qa"),"Failed! Support By dropdown is not displayed!");
        Assert.assertEquals(bookieInfoPage.ddpCurrency.getOptions(),CURRENCY_LIST,"Failed! Currency dropdown is not displayed!");
        Assert.assertEquals(bookieInfoPage.ddpStatus.getOptions(),BookieInfo.STATUS_LIST,"Failed! Status dropdown is not displayed!");
        log("Textbox: Bookie");
        Assert.assertEquals(bookieInfoPage.lblBookie.getText(),"Bookie","Failed! Bookie textbox is not displayed!");
        log("Button: Search, Show, Add Bookie, Export To Excel");
        Assert.assertEquals(bookieInfoPage.btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(bookieInfoPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("Validate Bookie Info table is displayed with correct header");
        log("INFO: Executed completely");
    }
}
