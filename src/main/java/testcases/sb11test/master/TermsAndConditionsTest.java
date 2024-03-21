package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.master.TermsAndConditionsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class TermsAndConditionsTest extends BaseCaseAQS {
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "17957")
    public void Term_And_Conditions_17957(){
        log("@title: Validate 'All' option displays as default at Company Unit field");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("Verify 1: 'All' option should display as default at 'Company Unit' dropdown list");
        Assert.assertEquals(page.ddpCompanyUnit.getFirstSelectedOption(),"All","FAILED! Company Unit dropdown displays incorrect.");
        log("INFO: Executed completely");
    }
}
