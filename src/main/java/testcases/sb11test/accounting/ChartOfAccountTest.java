package testcases.sb11test.accounting;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.accounting.ChartOfAccountPage;
import pages.sb11.accounting.CurrencyRatesPage;
import pages.sb11.soccer.popup.coa.CreateDetailTypePopup;
import pages.sb11.soccer.popup.coa.CreateParentAccountPopup;
import pages.sb11.soccer.popup.coa.CreateSubAccountPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class ChartOfAccountTest extends BaseCaseAQS {

    String companyUnit = "Kastraki Limited";
    String detailType = "Auto QC Detail Type";
    String parentAccount = "Auto QC Parent Account";
    String subAccount = "Auto QC Sub-Account";
    String currency = "HKD";
    String client = "QA_Client_Test_2";
    String master = "Master_QA_Client_Test";
    String agent = "Master_QA_Client_Test00-LED";

    @Test(groups = {"regression"})
    @TestRails(id = "2155")
    public void Chart_Of_Account_TC_2155(){
        log("@title: Validate Chart Of Account page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("Validate Chart Of Account page is displayed with correctly title");
        Assert.assertTrue(chartOfAccountPage.getTitlePage().contains(CHART_OF_ACCOUNT), "Failed! Chart Of Account page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2156")
    public void Chart_Of_Account_TC_2156(){
        log("@title: Validate UI on Chart Of Account is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("Validate UI Info display correctly");
        log("Detail Type table: Company Unit, Detail Type Name, Search button and Add button");
        Assert.assertEquals(chartOfAccountPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(chartOfAccountPage.lblDetailTypeName.getText(),"Detail Type Name","Failed! Detail Type Name search box is not displayed!");
        Assert.assertTrue(chartOfAccountPage.btnSearchDetail.isDisplayed(),"Failed! Button Search Detail is not displayed!");
        Assert.assertTrue(chartOfAccountPage.btnAddDetail.isDisplayed(),"Failed! Button Add Detail is not displayed!");
        log("Parent Account List: Parent Account Name, Search Button and Add button");
        Assert.assertEquals(chartOfAccountPage.lblParentAccName.getText(),"Parent Account Name","Failed! Parent Account Name search box is not displayed!");
        Assert.assertTrue(chartOfAccountPage.btnSearchParent.isDisplayed(),"Failed! Button Search Parent is not displayed!");
        Assert.assertTrue(chartOfAccountPage.btnAddParent.isDisplayed(),"Failed! Button Add Parent is not displayed!");
        log("Sub-Account List: Sub-Account Name, Search button and Add button");
        Assert.assertEquals(chartOfAccountPage.lblSubAccName.getText(),"Sub-Account Name","Failed! Sub-Account Name search box is not displayed!");
        Assert.assertTrue(chartOfAccountPage.btnSearchSubAcc.isDisplayed(),"Failed! Button Search Sub-Account is not displayed!");
        Assert.assertTrue(chartOfAccountPage.btnAddSubAcc.isDisplayed(),"Failed! Button Add Sub-Account is not displayed!");
        log("Detail Type, Parent Account List, Sub-Account List table header columns is correctly display");
        Assert.assertEquals(chartOfAccountPage.tbDetail.getHeaderNameOfRows(),ChartOfAccount.TABLE_HEADER_DETAIL,"Failed! Detail Type table header is displayed incorrectly!");
        Assert.assertEquals(chartOfAccountPage.tbParent.getHeaderNameOfRows(),ChartOfAccount.TABLE_HEADER_PARENT,"Failed! Parent Account List table header is displayed incorrectly!");
        Assert.assertEquals(chartOfAccountPage.tbSubAcc.getHeaderNameOfRows(),ChartOfAccount.TABLE_HEADER_SUB_ACCOUNT,"Failed! Sub-Account table header is displayed incorrectly!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"Deprecated"})
    @TestRails(id = "2157")
    public void Chart_Of_Account_TC_2157(){
        log("@title: Validate can add new Detail Type successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("@Step 3: Click Add button at Detail Type table");
        CreateDetailTypePopup createDetailTypePopup = chartOfAccountPage.openCreateDetailTypePopup();
        log("@Step 4: Fill full info");
        try {
            log("@Step 5: Click Submit");
            createDetailTypePopup.addDetailType(detailType,"456","456","456","456","Asset",companyUnit,true);
            log("Validate that new Detail type is created successfully and display correctly on Detail Type list");
            chartOfAccountPage.filterDetail(companyUnit,detailType);
            Assert.assertTrue(chartOfAccountPage.isDetailTypeDisplayed(detailType),"Failed! Created Detail Type is not displayed!");
        } finally {
            log("INFO: Executed completely");
            log("Post-condition: Deleted Create Detail type");
            chartOfAccountPage.deleteDetail(companyUnit,detailType);
        }
    }

    @Test(groups = {"Deprecated"})
    @TestRails(id = "2158")
    public void Chart_Of_Account_TC_2158(){
        log("@title: Validate can add new Parent Account successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("@Step 3: Click Add button at Parent Account table");
        CreateParentAccountPopup createParentAccountPopup = chartOfAccountPage.openCreateParentAccPopup();
        log("@Step 4: Fill full info");
        log("@Step 5: Click Submit");
        createParentAccountPopup.addParentAcc(parentAccount,"456","456","456","456",detailType,false);
        log("Validate that new Parent Account is created successfully and display correctly on Parent Account list");
        chartOfAccountPage.filterParent(companyUnit,detailType,parentAccount);
        Assert.assertTrue(chartOfAccountPage.isParentAccountDisplayed(parentAccount),"Failed! Created Parent Account is not displayed!");
        log("INFO: Executed completely");
        log("Post-condition: Deleted Created Parent Account");
        chartOfAccountPage.deleteParent(companyUnit,detailType,parentAccount);
    }

    @Test(groups = {"Deprecated"})
    @TestRails(id = "2159")
    public void Chart_Of_Account_TC_006(){
        log("@title: Validate can add new Sub Account successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("@Step 3: Click Add button at Parent Account table");
        CreateSubAccountPopup createSubAccountPopup = chartOfAccountPage.openCreateSubAccPopup();
        log("@Step 4: Fill full info");
        log("@Step 5: Click Submit");
        createSubAccountPopup.addSubAcc(subAccount,parentAccount,"789","789","789","789",currency,client,master,agent,false);
        log("Validate that new Parent Account is created successfully and display correctly on Parent Account list");
        chartOfAccountPage.filterSubAcc(companyUnit,detailType,parentAccount,subAccount);
        Assert.assertTrue(chartOfAccountPage.isSubAccountDisplayed(subAccount),"Failed! Created Sub Account is not displayed!");
        log("INFO: Executed completely");
        log("Post-condition: Deleted Created Parent Account");
        chartOfAccountPage.deleteSubAcc(companyUnit,detailType,parentAccount,subAccount);
    }
}
