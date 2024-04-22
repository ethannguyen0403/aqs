package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.generalReports.WinLossDetailPage;
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
        log("@Step 1: Observe");
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
        log("@Step 1: Observe");
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
        log("@Step 1: Observe");
        log("Verify 1: Validate Account List page is displayed with correctly title");
        Assert.assertTrue(page.lblTitle.isDisplayed(),"FAILED! Win Loss Detail page is displayed incorrect.");
        log("INFO: Executed completely");
    }
}
