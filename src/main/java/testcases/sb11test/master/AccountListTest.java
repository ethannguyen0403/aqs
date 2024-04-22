package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.AccountListPage;
import pages.sb11.master.AccountSearchPage;
import pages.sb11.master.popup.UpdatePTPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class AccountListTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2227")
    public void Account_List_TC_2227(){
        log("@title: Validate Account List page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Account List");
        AccountListPage accountListPage = welcomePage.navigatePage(MASTER, ACCOUNT_LIST,AccountListPage.class);
        log("Validate Account List page is displayed with correctly title");
        Assert.assertTrue(accountListPage.getTitlePage().contains(ACCOUNT_LIST),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2228")
    public void Account_List_TC_2228(){
        log("@title: Validate UI on Account List is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Account List");
        AccountListPage accountListPage = welcomePage.navigatePage(MASTER, ACCOUNT_LIST,AccountListPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Type, Client, CUR, Status, Creation Type");
        Assert.assertEquals(accountListPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(accountListPage.ddpType.getOptions(),AccountList.TYPE_LIST,"Failed! Type dropdown is not displayed!");
        Assert.assertEquals(accountListPage.ddpCUR.getOptions(),AccountList.CURRENCY_LIST,"Failed! CUR dropdown is not displayed!");
        Assert.assertEquals(accountListPage.ddpStatus.getOptions(),AccountList.STATUS_LIST,"Failed! Status dropdown is not displayed!");
        Assert.assertEquals(accountListPage.ddpCreationType.getOptions(),AccountList.CREATION_TYPE_LIST,"Failed! Creation Type dropdown is not displayed!");
        log("Textbox: Account Code");
        Assert.assertEquals(accountListPage.lblAccountCode.getText(),"Account Code","Failed! Account Code textbox is not displayed!");
        log("Button: Search");
        Assert.assertEquals(accountListPage.btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        log("Validate Account List table is displayed with correctly header column");
        Assert.assertEquals(accountListPage.tbAccountList.getHeaderNameOfRows(),AccountList.TABLE_HEADER,"Failed! Account List table is displayed incorrectly header column");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2229")
    @Parameters({"clientCode","accountCode"})
    public void Account_List_TC_2229(String clientCode, String accountCode){
        log("@title: Validate can update PT for an Account successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Account List");
        String type = "Client";
        AccountListPage accountListPage = welcomePage.navigatePage(MASTER, ACCOUNT_LIST,AccountListPage.class);
        log("@Step 3: Select Type = Client");
        log("@Step 4:  Enter account at pre-condition on Account Code");
        log("@Step 5:  Click Search");
        accountListPage.filterAccount(KASTRAKI_LIMITED,type,clientCode,"","","",accountCode);
        log("@Step 6:  Click Edit PT");
        UpdatePTPopup updatePTPopup = accountListPage.openUpdatePTPopup();
        log("@Step 7: Input PT for any product at Live/Non-Live");
        log("@Step 8: Click Submit");
        updatePTPopup.updatePT("Others","5","6",true);
        log("Validate Account List page is displayed with correctly title");
        accountListPage.verifyPT("Others","5","6");
        log("INFO: Executed completely");
    }
}
