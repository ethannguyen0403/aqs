package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.generalReports.WinLossDetailPage;
import pages.sb11.generalReports.popup.winlossDetail.MemberTransactionsPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class WinLossDetailTest extends BaseCaseAQS {
    @TestRails(id="23718")
    @Parameters({"password", "userNameOneRole"})
    @Test(groups = {"regression","2024.V.3.0"})
    public void Win_Loss_Detail_TC23718(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the menu");
        log("@pre-condition 1: Login with account without 'Win Loss Detail' permission");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 1: Click on General Reports");
        log("@Step 2: Observe");
        log("Verify 1: Validate accounts without permission cannot see the menu");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(GENERAL_REPORTS,WIN_LOSS_DETAIL),"FAILED! Win Loss Detail sub-menu is displayed incorrect.");
        log("INFO: Executed completely");
    }
    @TestRails(id="23719")
    @Parameters({"password", "userNameOneRole"})
    @Test(groups = {"regression","2024.V.3.0"})
    public void Win_Loss_Detail_TC23719(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot access page");
        String url = environment.getSbpLoginURL() + "#/aqs-report/general-reports/merito-winloss-detail";
        log("@pre-condition 1: Login with account without 'Win Loss Detail' permission");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 1: Try to access Win Loss Detail page by URL");
        DriverManager.getDriver().get(url);
        log("@Step 2: Observe");
        log("Verify 1: Validate accounts without permission cannot access page");
        Assert.assertFalse(new WinLossDetailPage().lblTitle.isDisplayed(),"FAILED! Win Loss Detail page is displayed incorrect.");
        log("INFO: Executed completely");
    }
    @TestRails(id="23720")
    @Test(groups = {"regression","2024.V.3.0"})
    public void Win_Loss_Detail_TC23720() {
        log("@title: Validate Win Loss Detail page is displayed when navigate");
        log("@pre-condition 1: Login with account that have 'Win Loss Detail' permission");
        log("@Step 1: Access General Reports > Win Loss Detail");
        WinLossDetailPage page = welcomePage.navigatePage(GENERAL_REPORTS,WIN_LOSS_DETAIL, WinLossDetailPage.class);
        log("@Step 2: Observe");
        log("Verify 1: Validate Account List page is displayed with correctly title");
        Assert.assertTrue(page.lblTitle.isDisplayed(),"FAILED! Win Loss Detail page is displayed incorrect.");
        log("INFO: Executed completely");
    }
    @TestRails(id="23721")
    @Parameters({"env"})
    @Test(groups = {"regression","2024.V.4.0"})
    public void Win_Loss_Detail_TC23721(String env) {
        log("@title: Validate data table is displayed correctly data that was scraped from Merito");
        log("@pre-condition 1: Login with account that have 'Win Loss Detail' permission");
        log("@Step 1: Access General Reports > Win Loss Detail");
        WinLossDetailPage page = welcomePage.navigatePage(GENERAL_REPORTS,WIN_LOSS_DETAIL, WinLossDetailPage.class);
        log("@Step 2: Compare data with BO > Win Loss Detail report");
        String date = "06/05/2024";
        page.filter("","",date,date,"Show","All");
        log("Verify 1: Validate data should be displayed same with BO - Win Loss Detail report");
        page.verifyDataInBOSite(env);
        log("INFO: Executed completely");
    }
    @TestRails(id="23722")
    @Parameters({"env"})
    @Test(groups = {"regression","2024.V.4.0"})
    public void Win_Loss_Detail_TC23722(String env) {
        log("@title: Validate Member Transaction dialog is displayed correctly when clicking on the bet number link");
        log("@pre-condition 1: Login with account that have 'Win Loss Detail' permission");
        log("@Step 1: Access General Reports > Win Loss Detail");
        WinLossDetailPage page = welcomePage.navigatePage(GENERAL_REPORTS,WIN_LOSS_DETAIL, WinLossDetailPage.class);
        log("@Step 2: Filter till having data");
        log("@Step 3: Click on the bet number link");
        String date = "06/05/2024";
        String username = null;
        if (env.equals("stg")){
            username = "AN";
        } else {
            username = "FB";
        }
        String brand = "FairExchange";
        String level = "Portal";
        page.filter("","",date,date,"Show","All");
        MemberTransactionsPopup memberTransactionsPopup = page.openMemberTransDialog(brand,level,username);
        log("Verify 1: Validate Member Transaction dialog displays bet details from all players under the viewing level");
        Assert.assertTrue(memberTransactionsPopup.lblTitle.getText().contains(MEMBER_TRANSACTIONS));
        log("Verify 2: Validate bets of each product will be displayed in 1 product tab and will be sorted by Product name alphabetically");
        memberTransactionsPopup.verifyBetsDisplay();
        memberTransactionsPopup.verifyTabNameSort();
        log("INFO: Executed completely");
    }
}
