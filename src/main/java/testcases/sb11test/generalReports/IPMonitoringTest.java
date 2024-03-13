package testcases.sb11test.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.generalReports.IPMonitoringPage;
import pages.sb11.soccer.MonitorBetsPage;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static common.SBPConstants.*;
public class IPMonitoringTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2024.V.2.0"})
    @TestRails(id = "21844")
    @Parameters({"password", "userNameOneRole"})
    public void IP_Monitoring_TC_21844(String password, String userNameOneRole) throws Exception{
        log("@title: Validate accounts without permission cannot see the menu");
        log("@Pre-condition: Login with account without 'IP Monitoring' permission");
        log("@Step 1: Click on General Reports");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Observe");
        log("@Verify 1: Validate accounts without permission cannot see the menu");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(GENERAL_REPORTS,IP_MONITORING));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "21845")
    @Parameters({"password", "userNameOneRole"})
    public void IP_Monitoring_TC_21845(String password, String userNameOneRole) throws Exception{
        log("@title: Validate accounts without permission cannot access page");
        log("@Pre-condition: Login with account without 'IP Monitoring' permission");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 1: Try to access IP Monitoring page by URL");
        String ipMonitoringPageUrl = environment.getSbpLoginURL() + "#/aqs-report/general-reports/ip-monitoring";
        DriverManager.getDriver().get(ipMonitoringPageUrl);
        log("@Step 2: Observe");
        log("@Verify 1: Validate accounts without permission cannot access page");
        Assert.assertFalse(new IPMonitoringPage().lblTitle.isDisplayed(), "FAILED! IP Monitoring page can access by external link");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "21846")
    public void IP_Monitoring_TC_21846() {
        log("@title: Validate IP Monitoring page is displayed when navigate");
        log("@Pre-condition: Login with account that have 'IP Monitoring' permission");
        log("@Step 1: Access General Reports > IP Monitoring");
        IPMonitoringPage page = welcomePage.navigatePage(GENERAL_REPORTS,IP_MONITORING, IPMonitoringPage.class);
        log("@Step 2: Observe");
        log("@Verify 1: Validate Account List page is displayed with correctly title");
        Assert.assertTrue(new IPMonitoringPage().lblTitle.isDisplayed(), "FAILED! IP Monitoring page can not access by external link");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "21847")
    @Parameters({"accountCode","bookieCode"})
    public void IP_Monitoring_TC_21847(String accountCode,String bookieCode) throws UnknownHostException {
        log("@title: Validate data table is displayed correctly account code when having bets from at least 2 account with same IP");
        log("@Pre-condition 1: Login with account that have 'IP Monitoring' permission");
        log("@Pre-condition 2: Having at least 2 account code with same IP are placed bets");
        String accountCode2 = "ClientCredit-AutoQC";
        String sport="Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("HDP").selection(event.getHome())
                .stage("FullTime").odds(1).handicap(-0.5).oddType("HK").requireStake(5.5).betType("BACK")
                .build();
        Order order1 = new Order.Builder().event(event).accountCode(accountCode2).marketName("Goals").marketType("HDP").selection(event.getHome())
                .stage("FullTime").odds(1).handicap(-0.5).oddType("HK").requireStake(5).betType("BACK")
                .build();
        BetEntrytUtils.placeBetAPI(order);
        BetEntrytUtils.placeBetAPI(order1);
        log("@Step 1: Access General Reports > IP Monitoring");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        IPMonitoringPage page = welcomePage.navigatePage(GENERAL_REPORTS,IP_MONITORING, IPMonitoringPage.class);
        log("@Step 2: Filter with account at pre-condition > observe");
        page.filter(bookieCode,"","","","","");
        log("@Verify 1: Validate accounts code at precondition should display correctly with same IP address");
        Assert.assertTrue(page.isAccountDisplay("Account Code",accountCode,accountCode2));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.2.0"})
    @TestRails(id = "21848")
    @Parameters({"accountCode","bookieCode","smartGroup"})
    public void IP_Monitoring_TC_21848(String accountCode,String bookieCode, String smartGroup) throws UnknownHostException {
        log("@title: Validate assigned Punter Code following account code is displayed correctly");
        log("@Pre-condition 1: Login with account that have 'IP Monitoring' permission");
        log("@Pre-condition 2: Having at least 2 account code with same IP are placed bets");
        String accountCode2 = "ClientCredit-AutoQC";
        String smartGroup2 = "QA Smart Group";
        String sport="Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("HDP").selection(event.getHome())
                .stage("FullTime").odds(1).handicap(-0.5).oddType("HK").requireStake(5.5).betType("BACK")
                .build();
        Order order1 = new Order.Builder().event(event).accountCode(accountCode2).marketName("Goals").marketType("HDP").selection(event.getHome())
                .stage("FullTime").odds(1).handicap(-0.5).oddType("HK").requireStake(5).betType("BACK")
                .build();
        BetEntrytUtils.placeBetAPI(order);
        BetEntrytUtils.placeBetAPI(order1);
        log("@Step 1: Access General Reports > IP Monitoring");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        IPMonitoringPage page = welcomePage.navigatePage(GENERAL_REPORTS,IP_MONITORING, IPMonitoringPage.class);
        log("@Step 2: Filter with account at pre-condition > observe");
        page.filter(bookieCode,"","","","","");
        log("@Verify 1: Validate assigned Punter Code following account code is displayed correctly");
        Assert.assertTrue(page.isAccountDisplay("Punter Code",smartGroup,smartGroup2));
        log("INFO: Executed completely");
    }
}
