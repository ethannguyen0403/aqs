package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.*;
import pages.sb11.master.bookiesystempopup.AccountListPopup;
import pages.sb11.master.bookiesystempopup.AgentListPopup;
import pages.sb11.master.bookiesystempopup.MasterListPopup;
import testcases.BaseCaseAQS;
import utils.sb11.CompanySetUpUtils;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BookieSystemTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2214")
    public void Bookie_System_TC_2214(){
        log("@title: Validate Bookie System page for Super is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Bookie System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Super");
        BookieSuperPage bookieSuperPage = bookieSystemPage.goToSuper();
        log("Validate Bookie System page for Super is displayed with correctly title");
        Assert.assertTrue(bookieSuperPage.getTitlePage().contains("Super"),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2215")
    @Parameters({"bookieCode"})
    public void Bookie_System_TC_2215(String bookieCode){
        log("@title: Validate UI on Bookie System for Super is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Super");
        BookieSuperPage bookieSuperPage = bookieSystemPage.goToSuper();
        log("Validate UI on Client System is correctly displayed");
        bookieSuperPage.verifyUI(bookieCode);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan"})
    @TestRails(id = "2216")
    @Parameters({"superCode","bookieCode"})
    public void Bookie_System_TC_2216(String superCode, String bookieCode){
        log("@title: Validate Master List popup is displayed when clicking data on #Master column");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Super");
        BookieSuperPage bookieSuperPage = bookieSystemPage.goToSuper();
        bookieSuperPage.filterSuperCode(KASTRAKI_LIMITED,bookieCode,"");
        log("Step 4:  Click value of client at pre-condition at #Master column");
        MasterListPopup masterListPopup = bookieSuperPage.openMasterList(superCode);
        log("Validate Master List popup is displayed with correctly title");
        Assert.assertTrue(masterListPopup.getTitlePage().contains("Master List"),"Failed! Master List popup is displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2217")
    public void Bookie_System_TC_2217(){
        log("@title: Validate Bookie System page for Master is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Bookie System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Master");
        BookieMasterPage bookieMasterPage = bookieSystemPage.goToMaster();
        log("Validate Bookie System page for Master is displayed with correctly title");
        Assert.assertTrue(bookieMasterPage.getTitlePage().contains("Master"),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2218")
    @Parameters({"bookieCode"})
    public void Bookie_System_TC_2218(String bookieCode){
        log("@title: Validate UI on Bookie System for Master is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Master");
        BookieMasterPage bookieMasterPage = bookieSystemPage.goToMaster();
        log("Validate UI on Client System is correctly displayed");
        bookieMasterPage.verifyUI(bookieCode);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan"})
    @TestRails(id = "2219")
    @Parameters({"bookieSuperMasterCode","bookieCode","bookieMasterCode"})
    public void Bookie_System_TC_2219(String bookieSuperMasterCode, String bookieCode, String bookieMasterCode){
        log("@title: Validate Agent List popup is displayed when clicking data on #Agent column");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Agent");
        BookieMasterPage bookieMasterPage = bookieSystemPage.goToMaster();
        bookieMasterPage.filterMasterCode(KASTRAKI_LIMITED,bookieCode,bookieSuperMasterCode,"");
        log("Step 4:  Click value of client at pre-condition at #Agent column");
        AgentListPopup agentListPopup = bookieMasterPage.openAgentList(bookieMasterCode);
        log("Validate Agent List popup is displayed with correctly title");
        Assert.assertTrue(agentListPopup.getTitlePage().contains("Agent List"),"Failed! Agent List popup is displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2220")
    public void Bookie_System_TC_2220(){
        log("@title: Validate Bookie System page for Agent is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Bookie System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Agent");
        BookieAgentPage bookieAgentPage = bookieSystemPage.goToAgent();
        log("Validate Bookie System page for Agent is displayed with correctly title");
        Assert.assertTrue(bookieAgentPage.getTitlePage().contains("Agent"),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2221")
    @Parameters({"bookieCode"})
    public void Bookie_System_TC_2221(String bookieCode){
        log("@title: Validate UI on Bookie System for Agent is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Agent");
        BookieAgentPage bookieAgentPage = bookieSystemPage.goToAgent();
        log("Validate UI on Client System is correctly displayed");
        bookieAgentPage.verifyUI(bookieCode);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan"})
    @TestRails(id = "2222")
    @Parameters({"bookieSuperMasterCode","bookieCode","bookieMasterCode"})
    public void Bookie_System_TC_2222(String bookieSuperMasterCode, String bookieCode, String bookieMasterCode){
        log("@title: Validate Agent List popup is displayed when clicking data on #Agent column");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        String agentCode = "A-QA10101-QA Test";
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Agent");
        BookieAgentPage bookieAgentPage = bookieSystemPage.goToAgent();
        bookieAgentPage.filterAgentCode(KASTRAKI_LIMITED,bookieCode,bookieSuperMasterCode,bookieMasterCode,"");
        log("Step 4:  Click value of client at pre-condition at #Agent column");
        AccountListPopup accountListPopup = bookieAgentPage.openAccountList(agentCode);
        log("Validate Account List popup is displayed with correctly title");
        Assert.assertTrue(accountListPopup.getTitlePage().contains("Account List"),"Failed! Account List popup is displayed!");
        log("INFO: Executed completely");
    }
}
