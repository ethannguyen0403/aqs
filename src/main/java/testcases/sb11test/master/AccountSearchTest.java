package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.AccountSearchPage;
import pages.sb11.master.BookieSuperPage;
import pages.sb11.master.BookieSystemPage;
import testcases.BaseCaseAQS;
import utils.sb11.CompanySetUpUtils;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class AccountSearchTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2223")
    public void Account_Search_TC_2223(){
        log("@title: Validate Account Search page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Account Search");
        AccountSearchPage accountSearchPage = welcomePage.navigatePage(MASTER, ACCOUNT_SEARCH,AccountSearchPage.class);
        log("Validate Account Search page is displayed with correctly title");
        Assert.assertTrue(accountSearchPage.getTitlePage().contains(ACCOUNT_SEARCH),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2224")
    public void Account_Search_TC_2224(){
        log("@title: Validate UI on Account Search is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Account Search");
        AccountSearchPage accountSearchPage = welcomePage.navigatePage(MASTER, ACCOUNT_SEARCH,AccountSearchPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Type");
        Assert.assertEquals(accountSearchPage.ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(accountSearchPage.ddpType.getOptions(),AccountSearch.TYPE_LIST,"Failed! Type dropdown is not displayed!");
        log("Textbox: Search");
        Assert.assertTrue(accountSearchPage.txtAccountSearch.isDisplayed(),"Failed! Show button is not displayed!");
        log("Button: Show");
        Assert.assertEquals(accountSearchPage.btnShow.getText(),"SHOW","Failed! Show button is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2225")
    @Parameters({"accountCode"})
    public void Account_Search_TC_2225(String accountCode){
        log("@title: Validate can search with Account Code successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Account Search");
        AccountSearchPage accountSearchPage = welcomePage.navigatePage(MASTER, ACCOUNT_SEARCH,AccountSearchPage.class);
        log("@Step 3: Enter account code of account at pre-condition");
        log("@Step 4: Click Show");
        accountSearchPage.filterAccount(KASTRAKI_LIMITED,"Account Code",accountCode);
        log("Validate there are 13 tables displayed:");
        log("Account Info\n" +
                "Bookie Info\n" +
                "Client Info\n" +
                "Super Info\n" +
                "CAS Super Info\n" +
                "Master Info\n" +
                "CAS Master Info\n" +
                "Agent Info\n" +
                "CAS Agent Info\n" +
                "Account - Email Header Info\n" +
                "CAS Super - Email Header Info\n" +
                "Account - Address Book\n" +
                "Smart Group Info");
        Assert.assertEquals(accountSearchPage.lblAccountInfo.getText(),"Account Info","Failed! Account Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblBookieInfo.getText(),"Bookie Info","Failed! Bookie Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblClientInfo.getText(),"Client Info","Failed! Client Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblSuperInfo.getText(),"Super Info","Failed! Super Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblCASSuperInfo.getText(),"CAS Super Info","Failed! CAS Super Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblMasterInfo.getText(),"Master Info","Failed! Master Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblCASMasterInfo.getText(),"CAS Master Info","Failed! CAS Master Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblAgentInfo.getText(),"Agent Info","Failed! Agent Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblCASAgentInfo.getText(),"CAS Agent Info","Failed! CAS Agent Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblAccountEmailHeader.getText(),"Account - Email Header Info","Failed! Account - Email Header Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblSuperEmailHeader.getText(),"CAS Super - Email Header Info","Failed! CAS Super - Email Header Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblAddressBook.getText(),"Account - Address Book","Failed! Account - Address Book table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblSmartGroupInfo.getText(),"Smart Group Info","Failed! Smart Group Info table is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2226")
    @Parameters({"accountId"})
    public void Account_Search_TC_2226(String accountId){
        log("@title: Validate can search with Account Id successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Account Search");
        AccountSearchPage accountSearchPage = welcomePage.navigatePage(MASTER, ACCOUNT_SEARCH,AccountSearchPage.class);
        log("@Step 3: Enter account id of account at pre-condition");
        log("@Step 4: Click Show");
        accountSearchPage.filterAccount(KASTRAKI_LIMITED,"Account Id",accountId);
        log("Validate there are 13 tables displayed:");
        log("Account Info\n" +
                "Bookie Info\n" +
                "Client Info\n" +
                "Super Info\n" +
                "CAS Super Info\n" +
                "Master Info\n" +
                "CAS Master Info\n" +
                "Agent Info\n" +
                "CAS Agent Info\n" +
                "Account - Email Header Info\n" +
                "CAS Super - Email Header Info\n" +
                "Account - Address Book\n" +
                "Smart Group Info");
        Assert.assertEquals(accountSearchPage.lblAccountInfo.getText(),"Account Info","Failed! Account Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblBookieInfo.getText(),"Bookie Info","Failed! Bookie Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblClientInfo.getText(),"Client Info","Failed! Client Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblSuperInfo.getText(),"Super Info","Failed! Super Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblCASSuperInfo.getText(),"CAS Super Info","Failed! CAS Super Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblMasterInfo.getText(),"Master Info","Failed! Master Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblCASMasterInfo.getText(),"CAS Master Info","Failed! CAS Master Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblAgentInfo.getText(),"Agent Info","Failed! Agent Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblCASAgentInfo.getText(),"CAS Agent Info","Failed! CAS Agent Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblAccountEmailHeader.getText(),"Account - Email Header Info","Failed! Account - Email Header Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblSuperEmailHeader.getText(),"CAS Super - Email Header Info","Failed! CAS Super - Email Header Info table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblAddressBook.getText(),"Account - Address Book","Failed! Account - Address Book table is not displayed!");
        Assert.assertEquals(accountSearchPage.lblSmartGroupInfo.getText(),"Smart Group Info","Failed! Smart Group Info table is not displayed!");
        log("INFO: Executed completely");
    }
}
