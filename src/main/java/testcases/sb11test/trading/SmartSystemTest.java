package testcases.sb11test.trading;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.trading.AgentGroupPage;
import pages.sb11.trading.MasterGroupPage;
import pages.sb11.trading.SmartGroupPage;
import pages.sb11.trading.SmartSystemPage;
import pages.sb11.trading.popup.*;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class SmartSystemTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2172")
    public void Smart_System_2172(){
        log("@title: Validate Smart System page for Master Group is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Master Group");
        MasterGroupPage masterGroupPage = smartSystemPage.goToMasterGroup();
        log("Validate Smart System page for Master Group is displayed with correctly title");
        Assert.assertTrue(masterGroupPage.getTitlePage().contains("Master Group"), "Failed! Master Group page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2173")
    public void Smart_System_2173(){
        log("@title: Validate Smart System page for Agent Group is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Master Group");
        AgentGroupPage agentGroupPage = smartSystemPage.goToAgentGroup();
        log("Validate Smart System page for Master Group is displayed with correctly title");
        Assert.assertTrue(agentGroupPage.getTitlePage().contains("Agent Group"), "Failed! Agent Group page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2174")
    public void Smart_System_TC_003(){
        log("@title: Validate Smart System page for Smart Group is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Smart Group");
        SmartGroupPage smartGroupPage = smartSystemPage.goToSmartGroup();
        log("Validate Smart System page for Master Group is displayed with correctly title");
        Assert.assertTrue(smartGroupPage.getTitlePage().contains("Smart Group"), "Failed! Smart Group page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2175")
    @Parameters({"masterCode"})
    public void Smart_System_2175(String masterCode){
        log("@title: Validate Master Group Report is displayed when clicking on Master Code");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Master Group");
        MasterGroupPage masterGroupPage = smartSystemPage.goToMasterGroup();
        log("@Step 4: Click Master Code");
        masterGroupPage.filterMaster("","Default",masterCode);
        MasterGroupReportPopup masterGroupReportPopup = masterGroupPage.openMasterGroupReport(masterCode);
        log("Validate Master Group Report page is displayed with correctly title");
        Assert.assertTrue(masterGroupReportPopup.getTitlePage().contains("Master Group Report"),"Failed! Master Group Report popup is not displayed!");
        Assert.assertTrue(masterGroupReportPopup.lblMasterCode.getText().contains(masterCode),"Failed! Master Code is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2176")
    @Parameters({"masterCode"})
    public void Smart_System_2176(String masterCode){
        log("@title: Validate Master Group List is displayed when clicking on #Group");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Master Group");
        MasterGroupPage masterGroupPage = smartSystemPage.goToMasterGroup();
        log("@Step 4: Click any #Group of a Master Code");
        masterGroupPage.filterMaster("","Default",masterCode);
        int groupAmount = Integer.parseInt(masterGroupPage.getGroupAmount(masterCode));
        MasterGroupListPopup groupListPopup = masterGroupPage.openGroupListPopup(masterCode);
        log("Validate Group List page is displayed with correctly title page");
        Assert.assertTrue(groupListPopup.getTitlePage().contains("Group List"),"Failed! Group List popup is not displayed!");
        log("Validate group amount on Master Group page should match with group amount on Group List");
        int totalGroup = groupListPopup.getTotalGroupAmount();
        Assert.assertEquals(groupAmount,totalGroup,"Failed! Total group amount is not matched!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2177")
    @Parameters({"masterCode"})
    public void Smart_System_2177(String masterCode){
        log("@title: Validate Master Client List is displayed when clicking on CL");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Master Group");
        MasterGroupPage masterGroupPage = smartSystemPage.goToMasterGroup();
        log("@Step 4: Click any CL of a Master Code");
        masterGroupPage.filterMaster("","Default",masterCode);
        MasterGroupClientListPopup masterGroupClientListPopup = masterGroupPage.openMasterGroupClientList(masterCode);
        log("Validate Client List page is displayed with correctly title page and Master code below");
        Assert.assertTrue(masterGroupClientListPopup.getTitlePage().contains("Client List"),"Failed! Client List page is not displayed!");
        Assert.assertEquals(masterGroupClientListPopup.lblMaster.getText(),masterCode,"Failed! Master Code is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2178")
    @Parameters({"agentCode"})
    public void Smart_System_2178(String agentCode){
        log("@title: Validate Agent Group Report is displayed when clicking on Master Code");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Agent Group");
        AgentGroupPage agentGroupPage = smartSystemPage.goToAgentGroup();
        log("@Step 4: Click Agent Code");
        agentGroupPage.filterAgent("","Default",agentCode);
        AgentGroupReportPopup agentGroupReportPopup = agentGroupPage.openAgentGroupReport(agentCode);
        log("Validate Master Group Report page is displayed with correctly title");
        Assert.assertTrue(agentGroupReportPopup.getTitlePage().contains("Agent Group Report"),"Failed! Agent Group Report popup is not displayed!");
        Assert.assertTrue(agentGroupReportPopup.lblAgentCode.getText().contains(agentCode),"Failed! Agent Code is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2179")
    @Parameters({"agentCode"})
    public void Smart_System_2179(String agentCode){
        log("@title: Validate Agent Group List is displayed when clicking on #Group");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Agent Group");
        AgentGroupPage agentGroupPage = smartSystemPage.goToAgentGroup();
        log("@Step 4: Click any #Group of a Agent Code");
        agentGroupPage.filterAgent("","Default",agentCode);
        int groupAmount = Integer.parseInt(agentGroupPage.getGroupAmount(agentCode));
        AgentGroupListPopup groupListPopup = agentGroupPage.openGroupListPopup(agentCode);
        log("Validate Group List page is displayed with correctly title page");
        Assert.assertTrue(groupListPopup.getTitlePage().contains("Group List"),"Failed! Group List popup is not displayed!");
        log("Validate group amount on Agent Group page should match with group amount on Group List");
        int totalGroup = groupListPopup.getTotalGroupAmount();
        Assert.assertEquals(groupAmount,totalGroup,"Failed! Total group amount is not matched!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2180")
    @Parameters({"agentCode"})
    public void Smart_System_2180(String agentCode){
        log("@title: Validate Agent Client List is displayed when clicking on CL");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Agent Group");
        AgentGroupPage agentGroupPage = smartSystemPage.goToAgentGroup();
        log("@Step 4: Click any CL of a Agent Code");
        agentGroupPage.filterAgent("","Default",agentCode);
        AgentGroupClientListPopup agentGroupClientListPopup = agentGroupPage.openAgentGroupClientList(agentCode);
        log("Validate Client List page is displayed with correctly title page and Agent code below");
        Assert.assertTrue(agentGroupClientListPopup.getTitlePage().contains("Client List"),"Failed! Client List page is not displayed!");
        Assert.assertEquals(agentGroupClientListPopup.lblAgent.getText(),agentCode,"Failed! Agent Code is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2181")
    @Parameters({"masterCode","smartGroup"})
    public void Smart_System_2181(String masterCode, String smartGroup){
        log("@title: Validate Smart Group Report is displayed when clicking on Master Code");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Smart Group");
        SmartGroupPage smartGroupPage = smartSystemPage.goToSmartGroup();
        log("@Step 4: Click Group Code");
        smartGroupPage.filterSmartGroup(masterCode,"Default",smartGroup);
        SmartGroupReportPopup smartGroupReportPopup = smartGroupPage.openSmartGroupReport(smartGroup);
        log("Validate Master Group Report page is displayed with correctly title");
        Assert.assertTrue(smartGroupReportPopup.getTitlePage().contains("Smart Group Report"),"Failed! Smart Group Report popup is not displayed!");
        Assert.assertTrue(smartGroupReportPopup.lblGroupCode.getText().contains(smartGroup),"Failed! Group Code is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2182")
    @Parameters({"masterCode","smartGroup"})
    public void Smart_System_2182(String masterCode, String smartGroup){
        log("@title: Validate Member popup is displayed when clicking on #Member");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Trading > Smart System");
        SmartSystemPage smartSystemPage = welcomePage.navigatePage(TRADING,SMART_SYSTEM,SmartSystemPage.class);
        log("@Step 3: Click Smart Group");
        SmartGroupPage smartGroupPage = smartSystemPage.goToSmartGroup();
        log("@Step 4: Click any #Member");
        smartGroupPage.filterSmartGroup(masterCode,"Default",smartGroup);
        MemberGroupPopup memberGroupPopup = smartGroupPage.openMemberGroupPopup(smartGroup);
        log("Validate Member popup is displayed with correctly title page");
        Assert.assertTrue(memberGroupPopup.getTitlePage().contains("Members"),"Failed! Member popup is not displayed!");
        log("Validate 3 tables should displayed with correctly tile: Member List; Member-UnAssigned; Member-Selected");
        Assert.assertTrue(memberGroupPopup.lblTitleMemberList.getText().contains("Member List"),"Failed! Member List table is not displayed!");
        Assert.assertTrue(memberGroupPopup.lblTitleMemberUnAssigned.getText().contains("Member - UnAssigned"),"Failed! Member-UnAssigned table is not displayed!");
        Assert.assertTrue(memberGroupPopup.lblTitleMemberSelected.getText().contains("Member - Selected"),"Failed! Member-Selected table is not displayed!");
        log("INFO: Executed completely");
    }
}
