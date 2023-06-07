package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.BBGPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BBGTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2135")
    public void BBG_TC_001(){
        log("@title: Validate BBG page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("Validate BBG page is displayed with correctly title");
        Assert.assertTrue(bbgPage.getTitlePage().contains("Bets By Group"), "Failed! BBG page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2136")
    public void BBG_TC_002(){
        log("@title: Validate BBG page is displayed when navigate");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer",COMPANY_UNIT,"Group", "Pending Bets",fromdate,todate,"All","All");
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, Punter Type, Sport, From Date, To Date and Show button");
        Assert.assertEquals(bbgPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpSmartType.getOptions(), SBPConstants.BBGPage.SMART_TYPE_LIST,"Failed! Smart Type dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpReportType.getOptions(), SBPConstants.BBGPage.REPORT_TYPE_LIST,"Failed! Report Type dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpWinLose.getOptions(), SBPConstants.BBGPage.WIN_LOSE_TYPE_LIST,"Failed! Win/Lose dropdown is not displayed");
        Assert.assertEquals(bbgPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(bbgPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        log("Show Tax Amount, Show Bet Types, Show Leagues, Smart Group, Order By Win%, Reset All Filters and More Filters");
        Assert.assertTrue(bbgPage.btnShowBetTypes.getText().contains("Show Bet Types"),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(bbgPage.btnShowLeagues.getText().contains("Show Leagues"),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(bbgPage.btnShowGroup.getText().contains("Show Groups"),"Failed! Show Group button is not displayed");
        Assert.assertEquals(bbgPage.btnResetAllFilter.getText(),"Reset All Filters","Failed! Reset button is not displayed");
        Assert.assertTrue(bbgPage.btnShowEvent.getText().contains("Show Events"),"Failed! Show Events button is not displayed");
        Assert.assertEquals(bbgPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("INFO: Executed completely");
    }
}
