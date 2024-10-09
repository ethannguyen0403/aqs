package testcases.sb11test.accounting;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.accounting.ChartOfAccountPage;
import pages.sb11.soccer.popup.coa.CreateDetailTypePopup;
import pages.sb11.soccer.popup.coa.CreateParentAccountPopup;
import pages.sb11.soccer.popup.coa.CreateSubAccountPopup;
import testcases.BaseCaseAQS;
import utils.sb11.accounting.ChartOfAccountUtils;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class ChartOfAccountTest extends BaseCaseAQS {
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

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2156")
    public void Chart_Of_Account_TC_2156(){
        log("@title: Validate UI on Chart Of Account is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("Validate UI Info display correctly");
        chartOfAccountPage.verifyUI();
        log("INFO: Executed completely");
    }


    @Test(groups = {"regression","2023.11.30","ethan3.0"})
    @TestRails(id = "2157")
    public void Chart_Of_Account_TC_2157(){
        log("@title: Validate can add new Detail Type successfully");
        log("@Step 1: Login with valid account");
        String detailType = "Auto QC Detail Type";
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("@Step 3: Click Add button at Detail Type table");
        CreateDetailTypePopup createDetailTypePopup = chartOfAccountPage.openCreateDetailTypePopup();
        log("@Step 4: Fill full info");
        try {
            log("@Step 5: Click Submit");
            createDetailTypePopup.addDetailType(detailType,"014","014","014","014","Asset", KASTRAKI_LIMITED,true);
            log("Validate that new Detail type is created successfully and display correctly on Detail Type list");
            chartOfAccountPage.filterDetail(KASTRAKI_LIMITED,detailType);
            Assert.assertTrue(chartOfAccountPage.isDetailTypeDisplayed(detailType),"Failed! Created Detail Type is not displayed!");
        } finally {
            log("INFO: Executed completely");
            log("Post-condition: Deleted Create Detail type");
            ChartOfAccountUtils.deleteLedgerGroup(detailType);
        }
    }
    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "2158")
    public void Chart_Of_Account_TC_2158(){
        log("@title: Validate can add new Parent Account successfully");
        log("@Step 1: Login with valid account");
        String detailType = "Auto QC Detail Type";
        String parentAccount = "Auto QC Parent Account";
        log("@Step 2: Access Accounting > Chart Of Account");
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("@Step 3: Create detail type and click Add button at Parent Account table");
        log("@Step 4: Fill full info");
        log("@Step 5: Click Submit");
        try {
            CreateDetailTypePopup createDetailTypePopup = chartOfAccountPage.openCreateDetailTypePopup();
            createDetailTypePopup.addDetailType(detailType,"014","014","014","014","Asset", KASTRAKI_LIMITED,true);
            chartOfAccountPage.filterDetail(KASTRAKI_LIMITED,detailType);
            CreateParentAccountPopup createParentAccountPopup = chartOfAccountPage.openCreateParentAccPopup();
            createParentAccountPopup.addParentAcc(parentAccount,"014","014","014","014",detailType,true);
            log("Validate that new Parent Account is created successfully and display correctly on Parent Account list");
            chartOfAccountPage.filterParent(KASTRAKI_LIMITED,detailType,parentAccount);
            Assert.assertTrue(chartOfAccountPage.isParentAccountDisplayed(parentAccount),"Failed! Created Parent Account is not displayed!");
        } finally {
            log("Post-condition: Deleted Create Detail type");
            chartOfAccountPage.deleteDetail(KASTRAKI_LIMITED,detailType);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.11.30"})
    @TestRails(id = "2159")
    public void Chart_Of_Account_TC_2159(){
        log("@title: Validate can add new Sub Account successfully");
        String detailType = "Auto QC Detail Type";
        String parentAccount = "Auto QC Parent Account";
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Chart Of Account");
        String currency = "HKD";
        String subAccount = "Auto QC Sub-Account";
        ChartOfAccountPage chartOfAccountPage = welcomePage.navigatePage(ACCOUNTING,CHART_OF_ACCOUNT,ChartOfAccountPage.class);
        log("@Step 3: Create Detail Type, Parent Account andClick Add button at Parent Account table");
        log("@Step 4: Fill full info");
        log("@Step 5: Click Submit");
        try {
            CreateDetailTypePopup createDetailTypePopup = chartOfAccountPage.openCreateDetailTypePopup();
            createDetailTypePopup.addDetailType(detailType,"014","014","014","014","Asset", KASTRAKI_LIMITED,true);
            chartOfAccountPage.filterDetail(KASTRAKI_LIMITED,detailType);
            CreateParentAccountPopup createParentAccountPopup = chartOfAccountPage.openCreateParentAccPopup();
            createParentAccountPopup.addParentAcc(parentAccount,"014","014","014","014",detailType,true);
            chartOfAccountPage.filterParent(KASTRAKI_LIMITED,detailType,parentAccount);
            CreateSubAccountPopup createSubAccountPopup = chartOfAccountPage.openCreateSubAccPopup();
            createSubAccountPopup.addSubAcc(subAccount,parentAccount,"014","014","014","014",currency,"","","",true);
            log("Validate that new Parent Account is created successfully and display correctly on Parent Account list");
            chartOfAccountPage.filterSubAcc(KASTRAKI_LIMITED,detailType,parentAccount,subAccount);
            Assert.assertTrue(chartOfAccountPage.isSubAccountDisplayed(subAccount),"Failed! Created Sub Account is not displayed!");
        } finally {
            log("Post-condition: Deleted Create Detail type");
            chartOfAccountPage.deleteDetail(KASTRAKI_LIMITED,detailType);
        }
        log("INFO: Executed completely");
    }
}
