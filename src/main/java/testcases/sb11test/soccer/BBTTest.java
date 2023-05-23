package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.BBTPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BBTTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2135")
    public void BBT_TC_001(){
        log("@title: Validate BBT page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER,BBT,BBTPage.class);
        log("Validate BBT page is displayed with correctly title");
        Assert.assertTrue(bbtPage.getTitlePage().contains("Bets By Team"), "Failed! BBT page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2136")
    public void BBT_TC_002(){
        log("@title: Validate BBT page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER,BBT,BBTPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, Punter Type, Sport, From Date, To Date and Show button");
        Assert.assertEquals(bbtPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(bbtPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(bbtPage.ddpSmartType.getOptions(), SBPConstants.BBTPage.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(bbtPage.ddpReportType.getOptions(), SBPConstants.BBTPage.REPORT_TYPE_LIST,"Failed! Report Type dropdown is not displayed");
        Assert.assertEquals(bbtPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(bbtPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        log("Show Tax Amount, Show Bet Types, Show Leagues, Smart Group, Order By Win%, Reset All Filters and More Filters");
        Assert.assertTrue(bbtPage.btnShowBetTypes.getText().contains("Show Bet Types"),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(bbtPage.btnShowLeagues.getText().contains("Show Leagues"),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(bbtPage.btnShowGroup.getText().contains("Show Groups"),"Failed! Show Group button is not displayed");
        Assert.assertEquals(bbtPage.btnResetAllFilter.getText(),"Reset All Filters","Failed! Reset button is not displayed");
        Assert.assertEquals(bbtPage.btnMoreFilter.getText(),"More Filters","Failed! More Filters button is not displayed");
        Assert.assertEquals(bbtPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    @TestRails(id = "2137")
    public void BBT_TC_003(){
        log("@title: Validate Month Performance page is displayed successfully when clicking on Smart group code");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER,BBT,BBTPage.class);
        log("@Step 3: Filter with valid data");
        bbtPage.filter(COMPANY_UNIT,"Soccer","Group", "Pending Bets",fromdate,todate,"All","All");
        log("@Step 4: Click on any Smart group code");

        log("Validate Last 12 Month Performance is displayed correctly title");

        log("Validate group code name is displayed correctly on header with format");

        log("INFO: Executed completely");
    }

}
