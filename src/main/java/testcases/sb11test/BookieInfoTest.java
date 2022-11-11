package testcases.sb11test;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.BOOKIE_INFO;
import static common.SBPConstants.MASTER;

public class BookieInfoTest extends BaseCaseAQS {

    @Test(groups = {"smoke"})
    public void Bookie_Info_TC_001(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);

        log("Verify that Page title is correctly display");
        Assert.assertEquals(bookieInfoPage.getTitlePage(),"Master Bookie Info","FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }
}
