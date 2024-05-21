package testcases.sb11test.master;

import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.master.TermsAndConditionsPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class TermsAndConditionsTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2024.V.4.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "17954")
    public void Term_And_Conditions_17954(String password, String userNameOneRole) throws Exception {
        log("@title: Validate cannot access 'Terms and Conditions' page if having no permission");
        log("@pre-condition: 'Terms and Conditions' permission does not active in any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Expand Master menu");
        log("Verify 1: There is no 'Terms and Conditions' menu");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(MASTER, TERMS_AND_CONDITIONS), "FAILED! Term and Conditions menu is displayed");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "17955")
    public void Term_And_Conditions_17955() {
        log("@title: Validate can access 'Terms and Conditions' page if having permission ");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand Master menu");
        log("@Step 3: Click 'Terms and Conditions' menu");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("Verify 1: 'Terms and Conditions' page displays properly");
        Assert.assertTrue(page.lblTitle.getText().contains("Terms and Conditions"),"FAILED! Term and Conditions page display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "17956")
    public void Term_And_Conditions_17956() {
        log("@title: Validate can access 'Terms and Conditions' page if having permission ");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand Master menu");
        log("@Step 3: Click 'Terms and Conditions' menu");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("Verify 1: 'Terms and Conditions' page displays properly");
        Assert.assertTrue(page.lblTitle.getText().contains("Terms and Conditions"),"FAILED! Term and Conditions page display incorrect");
        log("INFO: Executed completely");
    }
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
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "17971")
    public void Term_And_Conditions_17971(){
        log("@title: Validate is able to search Clients by name");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Input valid Client name");
        log("@Step 4: Click Show button");
        page.filter("","Client","");
        log("Verify 1: All existing Clients according to the search key should display");
        page.verifyAllClientDisplay();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression1","2024.V.4.0"})
    @TestRails(id = "17973")
    public void Term_And_Conditions_17973(){
        log("@title: Validate is able to filter Clients by company");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Select any company unit (e.g. Fair)");
        log("@Step 4: Click Show button");
        page.filter(KASTRAKI_LIMITED,"","");
        log("Verify 1: All existing Clients according to the filtering should display");
        page.verifyAllClientDisplay();
        log("INFO: Executed completely");
    }
}
