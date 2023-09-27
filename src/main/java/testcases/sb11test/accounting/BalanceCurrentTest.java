package testcases.sb11test.accounting;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.BalanceCurrentPage;

import pages.sb11.accounting.popup.MemberSummaryPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BalanceCurrentTest extends BaseCaseAQS {
    String accountCode = "QA Client (No.121 QA Client)";
    String companyUnit = "Kastraki Limited";
    String masterCode = "QATE";
    String agentCode = "QATE01-PT";

    @Test(groups = {"regression"})
    @TestRails(id = "2168")
    public void Balance_Current_TC_001(){
        log("@title: Validate Balance[Current] page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Balance[Current]");
        BalanceCurrentPage balanceCurrentPage = welcomePage.navigatePage(ACCOUNTING,BALANCE_CURRENT, BalanceCurrentPage.class);
        log("Validate Balance[Current] page is displayed with correctly title");
        Assert.assertTrue(balanceCurrentPage.getTitlePage().contains(BALANCE_CURRENT), "Failed! Balance[Current] page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2169")
    public void Balance_Current_TC_2169(){
        log("@title: Validate UI on Balance [Current] is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Balance [Current]");
        BalanceCurrentPage balanceCurrentPage = welcomePage.navigatePage(ACCOUNTING,BALANCE_CURRENT, BalanceCurrentPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Account");
        Assert.assertEquals(balanceCurrentPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertTrue(balanceCurrentPage.ddpAccount.getOptions().contains("QA2112 - "+accountCode),"Failed! Account dropdown is not displayed!");
        log("Button: Show button");
        Assert.assertEquals(balanceCurrentPage.btnShow.getText(),"Show","Show button is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2170")
    public void Balance_Current_TC_2170(){
        log("@title: Validate Member Summary of Master is displayed successfully when clicking on Master");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Balance [Current]");
        BalanceCurrentPage balanceCurrentPage = welcomePage.navigatePage(ACCOUNTING,BALANCE_CURRENT, BalanceCurrentPage.class);
        log("@Step 3: Select an account");
        log("@Step 4: Click Show");
        balanceCurrentPage.filterAccount(companyUnit,accountCode);
        log("@Step 5: Click any Master code");
        MemberSummaryPopup memberSummaryPopup = balanceCurrentPage.openMasterMemberSummaryPopup(masterCode);
        log("Validate Member Summary of Master is displayed with correctly title page and Master code on top right popup");
        Assert.assertTrue(memberSummaryPopup.getTitlePage().contains("Member Summary"),"Failed! Member Summary popup is not displayed!");
        Assert.assertTrue(memberSummaryPopup.getTitleMaster().contains(masterCode),"Failed! Master code is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2171")
    public void Balance_Current_TC_2171(){
        log("@title: Validate Member Summary of Agent is displayed successfully when clicking on Master");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Balance [Current]");
        BalanceCurrentPage balanceCurrentPage = welcomePage.navigatePage(ACCOUNTING,BALANCE_CURRENT, BalanceCurrentPage.class);
        log("@Step 3: Select an account");
        log("@Step 4: Click Show");
        balanceCurrentPage.filterAccount(companyUnit,accountCode);
        log("@Step 5: Click any Agent code");
        MemberSummaryPopup memberSummaryPopup = balanceCurrentPage.openAgentMemberSummaryPopup(agentCode);
        log("Validate Member Summary of Agent is displayed with correctly title page and Master code on top right popup");
        Assert.assertTrue(memberSummaryPopup.getTitlePage().contains("Member Summary"),"Failed! Member Summary popup is not displayed!");
        Assert.assertTrue(memberSummaryPopup.getTitleMaster().contains(agentCode),"Failed! Agent code is displayed incorrectly!");
        log("INFO: Executed completely");
    }
}

