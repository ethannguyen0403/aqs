package testcases.sb11test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import pages.sb11.master.ClientSystemPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class ClientSystemTest extends BaseCaseAQS {

    @Test(groups = {"smoke"})
    public void Client_System_TC_001(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Bookie Info");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);

        log("Verify that Page title is correctly display");
        Assert.assertEquals(clientSystemPage.getTitlePage(),"Master Client System","FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }
}
