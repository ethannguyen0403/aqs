package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.*;
import pages.sb11.master.bookiesystempopup.AccountListPopup;
import pages.sb11.master.bookiesystempopup.AgentListPopup;
import pages.sb11.master.bookiesystempopup.MasterListPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BookieSystemTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";

    @Test(groups = {"regression"})
    @TestRails(id = "2214")
    public void Bookie_System_TC_001(){
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

    @Test(groups = {"regression"})
    @TestRails(id = "2215")
    @Parameters({"bookieCode"})
    public void Bookie_System_TC_002(String bookieCode){
        log("@title: Validate UI on Bookie System for Super is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Super");
        BookieSuperPage bookieSuperPage = bookieSystemPage.goToSuper();
        log("Validate UI on Client System is correctly displayed");
        log("Dropdown: Company Unit, Bookie, Go To");
        Assert.assertEquals(bookieSuperPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown iss not displayed!");
        Assert.assertTrue(bookieSuperPage.ddpBookie.getOptions().contains(bookieCode),"Failed! Bookie dropdown is not displayed!");
        Assert.assertEquals(bookieSuperPage.ddpGoTo.getOptions(),BookieSystem.GO_TO_LIST,"Failed! Go To dropdown is not displayed!");
        log("Textbox: Super Code");
        Assert.assertEquals(bookieSuperPage.lblSuperCode.getText(),"Super Code","Failed! Super Code textbox is not displayed!");
        log("Button: Search, Add Super, More Filter");
        Assert.assertEquals(bookieSuperPage.btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(bookieSuperPage.btnMoreFilters.getText(),"More Filters","Failed! More Filters button is not displayed!");
        Assert.assertEquals(bookieSuperPage.btnAddSuper.getText(),"Add Super","Failed! Add Super button is not displayed!");
        log("Validate Super list table is displayed with correct header");
        Assert.assertEquals(bookieSuperPage.tbSuper.getHeaderNameOfRows(),BookieSystem.TABLE_HEADER_SUPER,"Failed! Super list table is displayed with incorrect header");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2216")
    @Parameters({"superCode","bookieCode"})
    public void Bookie_System_TC_003(String superCode, String bookieCode){
        log("@title: Validate Master List popup is displayed when clicking data on #Master column");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Super");
        BookieSuperPage bookieSuperPage = bookieSystemPage.goToSuper();
        bookieSuperPage.filterSuperCode(companyUnit,bookieCode,"");
        log("Step 4:  Click value of client at pre-condition at #Master column");
        MasterListPopup masterListPopup = bookieSuperPage.openMasterList(superCode);
        log("Validate Master List popup is displayed with correctly title");
        Assert.assertTrue(masterListPopup.getTitlePage().contains("Master List"),"Failed! Master List popup is displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2217")
    public void Bookie_System_TC_004(){
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

    @Test(groups = {"regression"})
    @TestRails(id = "2218")
    @Parameters({"bookieCode"})
    public void Bookie_System_TC_005(String bookieCode){
        log("@title: Validate UI on Bookie System for Master is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Master");
        BookieMasterPage bookieMasterPage = bookieSystemPage.goToMaster();
        log("Validate UI on Client System is correctly displayed");
        log("Dropdown: Company Unit, Bookie, Go To");
        Assert.assertEquals(bookieMasterPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown iss not displayed!");
        Assert.assertTrue(bookieMasterPage.ddpBookie.getOptions().contains(bookieCode),"Failed! Bookie dropdown is not displayed!");
        Assert.assertTrue(bookieMasterPage.ddpSuper.getOptions().contains("[No Super]"),"Failed! Super dropdown is not displayed!");
        Assert.assertEquals(bookieMasterPage.ddpGoTo.getOptions(),BookieSystem.GO_TO_LIST,"Failed! Go To dropdown is not displayed!");
        log("Textbox: Master Code");
        Assert.assertEquals(bookieMasterPage.lblMasterCode.getText(),"Master Code","Failed! Master Code textbox is not displayed!");
        log("Button: Search, Add Master, More Filter");
        Assert.assertEquals(bookieMasterPage.btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(bookieMasterPage.btnMoreFilters.getText(),"More Filters","Failed! More Filters button is not displayed!");
        Assert.assertEquals(bookieMasterPage.btnAddMaster.getText(),"Add Master","Failed! Add Master button is not displayed!");
        log("Validate Master list table is displayed with correct header");
        Assert.assertEquals(bookieMasterPage.tbMaster.getHeaderNameOfRows(),BookieSystem.TABLE_HEADER_MASTER,"Failed! Master list table is displayed with incorrect header");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2219")
    @Parameters({"superCode","bookieCode","masterCode"})
    public void Bookie_System_TC_006(String superCode, String bookieCode, String masterCode){
        log("@title: Validate Agent List popup is displayed when clicking data on #Agent column");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Agent");
        BookieMasterPage bookieMasterPage = bookieSystemPage.goToMaster();
        bookieMasterPage.filterMasterCode(companyUnit,bookieCode,superCode,"");
        log("Step 4:  Click value of client at pre-condition at #Agent column");
        AgentListPopup agentListPopup = bookieMasterPage.openAgentList(masterCode);
        log("Validate Agent List popup is displayed with correctly title");
        Assert.assertTrue(agentListPopup.getTitlePage().contains("Agent List"),"Failed! Agent List popup is displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2220")
    public void Bookie_System_TC_007(){
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
    @Test(groups = {"regression"})
    @TestRails(id = "2221")
    @Parameters({"bookieCode"})
    public void Bookie_System_TC_008(String bookieCode){
        log("@title: Validate UI on Bookie System for Agent is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Agent");
        BookieAgentPage bookieAgentPage = bookieSystemPage.goToAgent();
        log("Validate UI on Client System is correctly displayed");
        log("Dropdown: Company Unit, Bookie, Go To");
        Assert.assertEquals(bookieAgentPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown iss not displayed!");
        Assert.assertTrue(bookieAgentPage.ddpBookie.getOptions().contains(bookieCode),"Failed! Bookie dropdown is not displayed!");
        Assert.assertTrue(bookieAgentPage.ddpSuper.getOptions().contains("[No Super]"),"Failed! Super dropdown is not displayed!");
        Assert.assertTrue(bookieAgentPage.ddpMaster.isDisplayed(),"Failed! Master dropdown is not displayed!");
        Assert.assertEquals(bookieAgentPage.ddpGoTo.getOptions(),BookieSystem.GO_TO_LIST,"Failed! Go To dropdown is not displayed!");
        log("Textbox: Master Code");
        Assert.assertEquals(bookieAgentPage.lblAgentCode.getText(),"Agent Code","Failed! Agent Code textbox is not displayed!");
        log("Button: Search, Add Agent, More Filter");
        Assert.assertEquals(bookieAgentPage.btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(bookieAgentPage.btnMoreFilters.getText(),"More Filters","Failed! More Filters button is not displayed!");
        Assert.assertEquals(bookieAgentPage.btnAddAgent.getText(),"Add Agent","Failed! Add Agent button is not displayed!");
        log("Validate Agent list table is displayed with correct header");
        Assert.assertEquals(bookieAgentPage.tbAgent.getHeaderNameOfRows(),BookieSystem.TABLE_HEADER_AGENT,"Failed! Agent list table is displayed with incorrect header");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2222")
    @Parameters({"superCode","bookieCode","masterCode"})
    public void Bookie_System_TC_009(String superCode, String bookieCode, String masterCode, String agentCode){
        log("@title: Validate Agent List popup is displayed when clicking data on #Agent column");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        BookieSystemPage bookieSystemPage = welcomePage.navigatePage(MASTER, BOOKIE_SYSTEM,BookieSystemPage.class);
        log("Step 3: Click Agent");
        BookieAgentPage bookieAgentPage = bookieSystemPage.goToAgent();
        bookieAgentPage.filterAgentCode(companyUnit,bookieCode,superCode,masterCode,"");
        log("Step 4:  Click value of client at pre-condition at #Agent column");
        AccountListPopup accountListPopup = bookieAgentPage.openAccountList(agentCode);
        log("Validate Account List popup is displayed with correctly title");
        Assert.assertTrue(accountListPopup.getTitlePage().contains("Account List"),"Failed! Account List popup is displayed!");
        log("INFO: Executed completely");
    }
}
