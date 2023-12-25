package testcases.sb11test.user;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.soccer.MonitorBetsPage;
import pages.sb11.user.UserActivityMonitoringPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;


public class UserActivityMonitoringTest extends BaseCaseAQS {
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9157")
    @Parameters({"password", "userNameOneRole"})
    public void UserActivityMonitoring_TC_9157(String password, String userNameOneRole) throws Exception {
        log("@title: Validate can't access User Activity Monitoring page when having no permission");
        log("@Pre-condition: Login the page with account not having User Activity Monitoring permission");
        log("@Step 1: Access Users > observe");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("Verify 1: User Activity Monitoring page should not show on Users menu");
        List<String> lstSubMenu = welcomePage.headerMenuControl.getListSubMenu();
        Assert.assertFalse(lstSubMenu.contains(SBPConstants.USER_ACTIVITY_MONITORING),"FAILED! User Activity Monitoring page displayed incorrect!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9158")
    public void UserActivityMonitoring_TC_9158() {
        log("@title: Validate can search username sucessfully");
        log("@Pre-condition: Login the page");
        log("@Step 1: Access Users > User Activity Monitoring");
        String username = "AutoQC";
        UserActivityMonitoringPage page = welcomePage.navigatePage(USER,USER_ACTIVITY_MONITORING,UserActivityMonitoringPage.class);
        log("@Step 2: Enter valid Username > Show");
        page.filter(username,"","");
        log("Verify 1: Searched username is displayed correctly on user list");
        page.verifyUsernameDisplay(username);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9159")
    public void UserActivityMonitoring_TC_9159() {
        log("@title: Validate can only filter maximum 1 week");
        log("@Pre-condition: Login the page");
        log("@Step 1: Access Users > User Activity Monitoring");
        UserActivityMonitoringPage page = welcomePage.navigatePage(USER,USER_ACTIVITY_MONITORING,UserActivityMonitoringPage.class);
        log("@Step 2: Filter with more than 1 week");
        String fromDate = DateUtils.getDate(-8,"dd/MM/yyyy",GMT_7);
        page.dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        log("Verify 1: Error message should display correctly as 'Date range should not be more than 7 days.");
        String warnMes = page.appArlertControl.getWarningMessage();
        Assert.assertEquals(warnMes,"Date range should not be more than 7 days.","FAILED! Warning message displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2023.12.29"})
    @TestRails(id = "9160")
    public void UserActivityMonitoring_TC_9160() {
        log("@title: Validate data table should sort by username alphabetically");
        log("@Pre-condition: Login the page");
        log("@Step 1: Access Users > User Activity Monitoring");
        UserActivityMonitoringPage page = welcomePage.navigatePage(USER,USER_ACTIVITY_MONITORING,UserActivityMonitoringPage.class);
        log("@Step 2: Observe data table sorting");
        log("Verify 1: Validate data table should sort by username alphabetically");
        page.verifyUserDataSortCorrect();
        log("INFO: Executed completely");
    }
}
