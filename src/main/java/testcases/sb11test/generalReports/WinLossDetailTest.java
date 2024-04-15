package testcases.sb11test.generalReports;

import com.paltech.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class WinLossDetailTest extends BaseCaseAQS {
    @TestRails(id="23718")
    @Parameters({"password", "userNameOneRole"})
    @Test(groups = {"regression1","2024.V.3.0"})
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
}
