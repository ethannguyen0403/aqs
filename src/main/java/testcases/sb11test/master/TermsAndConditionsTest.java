package testcases.sb11test.master;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.master.TermsAndConditionsPage;
import pages.sb11.master.termsAndConditionsPopup.LogPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.ArrayList;
import java.util.List;

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
        log("@title: Validate all existing companies display in Company Unit dropdown list");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Expand Company Unit dropdown list");
        List<String> lstCU = page.ddpCompanyUnit.getOptions();
        log("Verify 1: 'All existing companies will display");
        Assert.assertEquals(lstCU, COMPANY_UNIT_LIST_ALL,"FAILED! Term and Conditions page display incorrect");
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
    @Parameters({"clientCode"})
    @TestRails(id = "17971")
    public void Term_And_Conditions_17971(String clientCode){
        log("@title: Validate is able to search Clients by name");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Input valid Client name");
        log("@Step 4: Click Show button");
        page.filter("","Client",clientCode);
        log("Verify 1: All existing Clients according to the search key should display");
        Assert.assertTrue(page.isClientNameDisplay(clientCode));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
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
        page.verifyClientListDropdownDisplay(KASTRAKI_LIMITED);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "17974")
    public void Term_And_Conditions_17974(){
        log("@title: Validate the proper client name displays at Client Name column");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Select any company unit (e.g. Fair)");
        log("@Step 4: Click Show button");
        page.filter(KASTRAKI_LIMITED,"Client","");
        log("Verify 1: Correct Client Name of filtering company displays at Client Name column");
        Assert.assertTrue(page.isClientNameDisplay("All"));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "17977")
    public void Term_And_Conditions_17977(String clientCode){
        log("@title: Validate the current Terms and Conditions value will prepopulate to edit/input in Edit mode");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Search by any client name that has inputted Terms and Conditions");
        page.filter(KASTRAKI_LIMITED,"Client",clientCode);
        log("@Step 4: Click Edit link");
        List<ArrayList<String>> lstBefore = page.tblData.getRowsWithoutHeader(1,true);
        page.clickEdit(clientCode);
        log("Verify 1: The current Terms and Conditions value will prepopulate in textarea");
        //Verify Provider term and Payment term column
        page.verifyDataAfterClickingEdit(lstBefore,clientCode,"Provider term and Payment term");
        //Verify Downline term in PT column
        page.verifyDataAfterClickingEdit(lstBefore,clientCode,"Downline term in PT");
        //Verify Downline payment term column
        page.verifyDataAfterClickingEdit(lstBefore,clientCode,"Downline payment term");
        //Verify Sales Incharge column
        page.verifyDataAfterClickingEdit(lstBefore,clientCode,"Sales Incharge");
        //Verify Comment column
        page.verifyDataAfterClickingEdit(lstBefore,clientCode,"Comment");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "17978")
    public void Term_And_Conditions_17978(String clientCode){
        log("@title: Validate Edit column will display Save & Cancel button in Edit mode");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Search by any client name that has inputted Terms and Conditions");
        page.filter(KASTRAKI_LIMITED,"Client",clientCode);
        log("@Step 4: Click Edit link");
        page.clickEdit(clientCode);
        log("Verify 1: Edit' column will display Save & Cancel buttons");
        page.verifyEditButtonDisplay(clientCode);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "17979")
    public void Term_And_Conditions_17979(String clientCode){
        log("@title: Validate can save the new values by clicked on Save button");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        String newProviderTerm = "Automation Testing " + DateUtils.getMilliSeconds();
        String newDownlineTerm = "Automation Testing " + DateUtils.getMilliSeconds();
        String newDownlinePayment = "Automation Testing " + DateUtils.getMilliSeconds();
        String newSalesIncharge = "Automation Testing " + DateUtils.getMilliSeconds();
        String newComment = "Automation Testing " + DateUtils.getMilliSeconds();
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Search by any client name that has inputted Terms and Conditions");
        page.filter(KASTRAKI_LIMITED,"Client",clientCode);
        log("@Step 4: Click Edit link");
        log("@Step 5: Add new Terms and Conditions");
        log("@Step 6: Click Save button");
        log("Verify 1: New values will save correctly");
        Assert.assertFalse(page.isClientBookieEdited(clientCode,newProviderTerm,newDownlineTerm,newDownlinePayment,newSalesIncharge,newComment,true),"FAILED! It can not edit");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "20218")
    public void Term_And_Conditions_20218(String clientCode){
        log("@title: Validate new changes will not save if clicked on X button");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        String newProviderTerm = "Automation Testing " + DateUtils.getMilliSeconds();
        String newDownlineTerm = "Automation Testing " + DateUtils.getMilliSeconds();
        String newDownlinePayment = "Automation Testing " + DateUtils.getMilliSeconds();
        String newSalesIncharge = "Automation Testing " + DateUtils.getMilliSeconds();
        String newComment = "Automation Testing " + DateUtils.getMilliSeconds();
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Search by any client name that has inputted Terms and Conditions");
        page.filter(KASTRAKI_LIMITED,"Client",clientCode);
        log("@Step 4: Click Edit link");
        log("@Step 5: Input/clear Terms and Conditions");
        log("@Step 6: Click X button");
        log("Verify 1: Edit' column will display Save & Cancel buttons");
        Assert.assertTrue(page.isClientBookieEdited(clientCode,newProviderTerm,newDownlineTerm,newDownlinePayment,newSalesIncharge,newComment,false),"FAILED! It is edited");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "20219")
    public void Term_And_Conditions_20219(String clientCode){
        log("@title: Validate the textarea will be size-adjustable and have no space restraints");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Search by any client name that has inputted Terms and Conditions");
        page.filter(KASTRAKI_LIMITED,"Client",clientCode);
        log("@Step 4: Click Edit link");
        log("@Step 5: Input about 8 rows 'Terms and Conditions' text");
        log("Verify 1: The textarea will be size-adjustable and have no space restraints (the scrollbar will display)");
        page.verifyTheScrollbarDisplay(clientCode);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode","username"})
    @TestRails(id = "20220")
    public void Term_And_Conditions_20220(String clientCode, String username){
        log("@title: Validate the Terms and Conditions >> Log dialog displays when clicked on the Log link");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Search by any client name that has inputted Terms and Conditions");
        page.filter(KASTRAKI_LIMITED,"Client",clientCode);
        log("@Step 4: Click Log link");
        LogPopup logPopup = page.openLog(clientCode);
        logPopup.waitSpinnerDisappeared();
        log("Verify 1: Terms and Conditions >> Log dialog will display");
        Assert.assertTrue(logPopup.lblTitle.getText().contains("Log"),"FAILED! Log is not displayed");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode","username"})
    @TestRails(id = "20221")
    public void Term_And_Conditions_20221(String clientCode, String username){
        log("@title: Validate the Terms and Conditions >> Log dialog displays when clicked on the Log link");
        log("@pre-condition: 'Terms and Conditions' permission actives in any account");
        String modifiedTime = DateUtils.getDate(0,"dd/MM/yyyy",GMT_8);
        String newProviderTerm = "Automation Testing " + DateUtils.getMilliSeconds();
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Master >> Terms and Conditions page");
        TermsAndConditionsPage page = welcomePage.navigatePage(MASTER,TERMS_AND_CONDITIONS, TermsAndConditionsPage.class);
        log("@Step 3: Search by any client name that has inputted Terms and Conditions");
        page.filter(KASTRAKI_LIMITED,"Client",clientCode);
        log("@Step 4: Click Edit link");
        log("@Step 5: Input Terms and Conditions text");
        log("@Post-condtion: Get value before editing");
        String oldProviderTerm = page.getValueOfClientBookie(clientCode,"Provider term and Payment term");
        log("@Step 6: Click Save button");
        page.editBookieClient(clientCode,newProviderTerm,"","","","",true);
        log("@Step 7: Click Log link");
        LogPopup logPopup = page.openLog(clientCode);
        log("Verify 1: All the log of changes on the terms and conditions of each Client will display the correct values as below:\n" +
                "\n" +
                "Client Name of edited Terms and conditions\n" +
                "From: old value before changing\n" +
                "To: new value after changing\n" +
                "Modified By\n" +
                "Modified Time (GMT+8)");
        logPopup.isLogDisplay(clientCode,"Provider term and Payment term",oldProviderTerm,newProviderTerm,username,modifiedTime);
        log("INFO: Executed completely");
    }
}
