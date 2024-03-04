package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.BBGPage;
import pages.sb11.soccer.popup.bbg.BBGLastDaysPerformacesPopup;
import pages.sb11.soccer.popup.bbg.BetByTeamPricePopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class BBGTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2147")
    public void BBG_TC_2147(){
        log("@title: Validate BBG page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("Validate BBG page is displayed with correctly title");
        Assert.assertTrue(bbgPage.getTitlePage().contains("Bets By Group"), "Failed! BBG page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2148")
    public void BBG_TC_2148(){
        log("@title: Validate BBG page is displayed when navigate");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);
        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer", KASTRAKI_LIMITED,"Group", "Pending Bets",fromdate,todate,"All","All");
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, Punter Type, Sport, From Date, To Date and Show button");
        Assert.assertEquals(bbgPage.ddpSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed");
        Assert.assertEquals(bbgPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST_ALL,"Failed! Company Unit dropdown is not displayed");
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

    @Test(groups = {"regression"})
    @TestRails(id = "2149")
    public void BBG_TC_2149(){
        log("@title: Validate League Performance page is displayed successfully when clicking on Price");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);

        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer", KASTRAKI_LIMITED,"Group", "Pending Bets",fromdate,todate,"All","All");

        log("@Step 3:Click on any Price");
        String smartGroup = bbgPage.getFristSmartGroupName();
        BetByTeamPricePopup bbtPopup = bbgPage.clickFirstPriceCell();

        log("Verify 1 Validate League Performance is displayed correctly title");
        Assert.assertEquals(bbtPopup.getTitlePage(),"Bets By Team League Performance","Failed! Popup title is incorrect");

        log("Verify 2  Validate 2 tables should display with format: [smart group name] - League Performance for Last 1 Month");
        Assert.assertEquals(bbtPopup.lblLast1Month.getText(),String.format("%s - League Performance for Last 1 Month",smartGroup),"Failed! League Performance for Last 1 Month title is incorrect");

        log("Verify 3  Validate 2 tables should display with format: [smart group name] - League Performance for Last 3 Month");
        Assert.assertEquals(bbtPopup.lblLast3Month.getText(),String.format("%s - League Performance for Last 3 Months",smartGroup),"Failed! League Performance for Last 3 Month title is incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2150")
    public void BBG_TC_2150(){
        log("@title:Validate Last 12 Days Performance page is displayed successfully when clicking on Trader");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Access Soccer > BBG");
        BBGPage bbgPage = welcomePage.navigatePage(SOCCER,BBG,BBGPage.class);

        log("@Step 3: Filter with valid data");
        bbgPage.filter("Soccer", KASTRAKI_LIMITED,"Group", "Pending Bets",fromdate,todate,"All","All");

        log("@Step 3:Click on any Trader");
        String smartGroupCurrency = bbgPage.getFristSmartGroupCurrency();
        String smartGroup = bbgPage.getFristSmartGroupName();
        BBGLastDaysPerformacesPopup bbtPopup = bbgPage.clickFirstTraderCell();

        log("Verify 1 Validate League Performance is displayed correctly title");
        Assert.assertEquals(bbtPopup.getTitlePage(),"BBG Last 12 Days Performance","Failed! Popup title is incorrect");

        log("Verify 2  Validate 2 tables should display with format: [smart group name] - League Performance for Last 1 Month");
        Assert.assertEquals(bbtPopup.lblLast1Month.getText(),String.format("%s - %s",smartGroupCurrency,smartGroup),"Failed! League Performance for Last 1 Month title is incorrect");
        log("INFO: Executed completely");
    }

}
