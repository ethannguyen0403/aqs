package testcases.sb11test.master;

import com.paltech.driver.DriverManager;
import com.paltech.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import pages.sb11.master.ClientSystemPage;
import pages.sb11.master.popup.AccountListPopup;
import pages.sb11.master.popup.AgentListPopup;
import pages.sb11.master.popup.MasterListPopup;
import pages.sb11.master.popup.MemberListPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class ClientSystemTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String clientList = "With Super";

    @Test(groups = {"smoke"})
    @TestRails(id = "2206")
    public void Client_System_TC_001(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("Verify that Page title is correctly display");
        Assert.assertTrue(clientSystemPage.getTitlePage().contains("Client System"),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke"})
    @TestRails(id = "2207")
    public void Client_System_TC_002(){
        log("@title: Validate UI on Client System is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Client System");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("Validate UI on Client System is correctly displayed");
        log("Dropdown: Company Unit, Support By, Currency, Status");
        Assert.assertEquals(clientSystemPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(clientSystemPage.ddpClientList.getOptions(),ClientSystem.CLIENT_LIST,"Failed! Client List dropdown is not displayed!");
        Assert.assertEquals(clientSystemPage.ddpCurrency.getOptions(),ClientSystem.CURRENCY_LIST,"Failed! Currency dropdown is not displayed!");
        Assert.assertEquals(clientSystemPage.ddpStatus.getOptions(),ClientSystem.STATUS_LIST,"Failed! Status dropdown is not displayed!");
        log("Textbox: Client Name");
        Assert.assertEquals(clientSystemPage.lblClientName.getText(),"Client Name","Failed! Client Name textbox is not displayed!");
        log("Button: Show, Add Bookie, Export To Excel");
        Assert.assertEquals(clientSystemPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        Assert.assertEquals(clientSystemPage.btnAddClient.getText(),"Add Client","Failed! Add Client button is not displayed!");
        Assert.assertEquals(clientSystemPage.btnExportToExcel.getText(),"Export To Excel","Failed! Export To Excel button is not displayed!");
        log("Validate Client System table is displayed with correct header");
        Assert.assertEquals(clientSystemPage.tbClient.getHeaderNameOfRows(),ClientSystem.TABLE_HEADER_CLIENT,"Failed! Client table header is displayed incorrectly!!");
        Assert.assertEquals(clientSystemPage.tbSuperMaster.getHeaderNameOfRows(),ClientSystem.TABLE_HEADER_SUPER_MASTER,"Failed! Super Master table header is displayed incorrectly!!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2208")
    @Parameters({"clientCode"})
    public void Client_System_TC_003(String clientCode){
        log("@title: Validate can search Bookie successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Client System");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("@Step 3: Enter an exist Client");
        log("@Step 4: Click Search");
        clientSystemPage.filterClient(companyUnit,clientList,clientCode,"","");
        log("Searched bookie should display correctly on Bookie List");
        Assert.assertTrue(clientSystemPage.isClientCodeExist(clientCode),"Failed! Client Code " + clientCode + " is not exist");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2209")
    public void Client_System_TC_004(){
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "client-list.xlsx";
        log("@title: Validate can export Client List to Excel file successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Client Info");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("@Step 3: Click Export To Excel");
        clientSystemPage.exportClientList();
        log("Validate can export Client List to Excel file successfully with exported file name: bookie_list");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");

        log("@Post-condition: delete download file");
        try {
            FileUtils.removeFile(downloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2210")
    @Parameters({"clientCode"})
    public void Client_System_TC_005(String clientCode){
        log("@title: Validate Master List popup is displayed when clicking data on Master column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Client System");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("@Step 3: Click value of client at pre-condition at #Master column");
        MasterListPopup masterListPopup = clientSystemPage.openMasterList(clientCode);
        log("Validate Master List popup is displayed with correctly title");
        Assert.assertTrue(masterListPopup.getTitlePage().contains("Master List"),"Failed! Master List popup is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2211")
    @Parameters({"clientCode"})
    public void Client_System_TC_006(String clientCode){
        log("@title: Validate Agent List popup is displayed when clicking data on Master column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Client System");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("@Step 3: Click value of client at pre-condition at #Agents column");
        AgentListPopup agentListPopup = clientSystemPage.openAgentList(clientCode);
        log("Validate Agent List popup is displayed with correctly title");
        Assert.assertTrue(agentListPopup.getTitlePage().contains("Agent List"),"Failed! Agent List popup is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2212")
    @Parameters({"clientCode"})
    public void Client_System_TC_007(String clientCode){
        log("@title: Validate Account List popup is displayed when clicking data on Master column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Client System");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("@Step 3: Click value of client at pre-condition at #Memb column");
        AccountListPopup accountListPopup = clientSystemPage.openAccountList(clientCode);
        log("Validate Account List popup is displayed with correctly title");
        Assert.assertTrue(accountListPopup.getTitlePage().contains("Account List"),"Failed! Account List popup is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2213")
    @Parameters({"clientCode"})
    public void Client_System_TC_008(String clientCode){
        log("@title: Validate Member List popup is displayed when clicking data on Master column");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Client System");
        ClientSystemPage clientSystemPage = welcomePage.navigatePage(MASTER, CLIENT_SYSTEM,ClientSystemPage.class);
        log("@Step 3: Click value of client at pre-condition at #Memb column");
        MemberListPopup memberListPopup = clientSystemPage.openMemberList(clientCode);
        log("Validate Member List popup is displayed with correctly title");
        Assert.assertTrue(memberListPopup.getTitlePage().contains("Member List"),"Failed! Member List popup is not displayed!");
        log("INFO: Executed completely");
    }
}
