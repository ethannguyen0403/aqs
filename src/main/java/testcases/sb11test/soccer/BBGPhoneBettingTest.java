package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.soccer.BBGPhoneBettingPage;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.BetSettlementUtils;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.util.Date;

import static common.SBPConstants.*;

public class BBGPhoneBettingTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2121")
    public void BBGPhoneBettingTC_2121(){
        log("@title: Validate BBG-Phone Betting page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG-Phone Betting");
        BBGPhoneBettingPage bbgPhoneBettingPage = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("Validate BBG-Phone Betting page is displayed with correctly title");
        Assert.assertTrue(bbgPhoneBettingPage.getTitlePage().contains(BBG_PHONE_BETTING), "Failed! BBG-Phone Betting page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2122")
    public void BBGPhoneBettingTC_2122(){
        log("@title: Validate BBG-Phone Betting page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > BBG-Phone Betting");
        BBGPhoneBettingPage bbgPhoneBettingPage = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, From Date, To Date");
        Assert.assertTrue(bbgPhoneBettingPage.ddpCompanyUnit.isDisplayed(),"Failed! Company Unit dropdown is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.ddpReportBy.isDisplayed(),"Failed! Report By dropdown is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.txtFromDate.isDisplayed(),"Failed! From Date datetime picker is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.txtToDate.isDisplayed(),"Failed! To Date datetime picker is not displayed");

        log("Show Bet Types, Show Leagues, Show Win/Lose and Show button");
        Assert.assertTrue(bbgPhoneBettingPage.btnShowBetTypes.isDisplayed(),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.btnShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.btnShowWinLose.isDisplayed(),"Failed! Show Win/Lose button is not displayed");
        Assert.assertTrue(bbgPhoneBettingPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Event table header columns is correctly display");
        Assert.assertEquals(bbgPhoneBettingPage.tblOrder.getHeaderNameOfRows(), BBGPhoneBetting.TABLE_HEADER,"FAILED! BBG-Phone Betting Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29419")
    @Parameters({"password", "userNameOneRole"})
    public void BBGPhoneBettingTC_29419(String password, String userNameOneRole) throws Exception {
        log("@title: Validate accounts without permission cannot see the menu");
        log("@Step 1: Navigate to the site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Check menu item 'BBG - Phone Betting' under menu 'Soccer'");
        log("Validate BBG-Phone Betting page is displayed with correctly title");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(SOCCER,BBG_PHONE_BETTING),"FAILED! BBG-Phone Betting displays incorrect. ");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29420")
    public void BBGPhoneBettingTC_29420(){
        log("@title: Validate accounts with permission can access page");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("Verify 1: User can access page successfully");
        Assert.assertTrue(page.getTitlePage().contains(BBG_PHONE_BETTING), "Failed! BBG-Phone Betting page is not displayed");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29421")
    public void BBGPhoneBettingTC_29421(){
        log("@title: Validate the error message shows when selecting date range > 7 days");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("@Step 3: Select date range > 7 days");
        String fromDate = DateUtils.getDate(-8,"dd/MM/yyyy",GMT_7);
        page.filter("","",fromDate,"","","","");
        log("@Step 4: Click on 'Show' button");
        page.btnShow.click();
        String message = page.appArlertControl.getWarningMessage();
        log("Verify 1: Show the error message: 'Date range should not be more than 7 days.'");
        Assert.assertEquals(message, BBGPhoneBetting.MES_MORE_THAN_7_DAYS, "Failed! Show the error message incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29422")
    public void BBGPhoneBettingTC_29422(){
        log("@title: Validate the bet type shows correctly when filtering");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("@Step 3: Select filters which have data");
        log("@Step 4: Select one option in 'Show Bet Types' (e.g.FT-1x2)");
        log("@Step 5: Click on 'Show' button");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",GMT_7);
        String toDate = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter("","",fromDate,toDate,"FT-1x2","","");
        log("Verify 1: Show all records that have selected bet type");
        page.verifyShowBetTypeCorrect("FT-1x2");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29423")
    public void BBGPhoneBettingTC_29423(){
        log("@title: Validate the league shows correctly when filtering");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("@Step 3: Select filters which have data");
        log("@Step 4: Select one option in 'Show Leagues'");
        log("@Step 5: Click on 'Show' button");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter("","",date,date,"","","");
        //Double filter to get infor of League Name in a day
        String leagueName = page.getLstLeague().get(0);
        page.filter("","","","","",leagueName,"");
        log("Verify 1: Show correct selected league");
        page.verifyShowLeagueNameCorrect(leagueName);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29424")
    public void BBGPhoneBettingTC_29424(){
        log("@title: Validate the win bets shows correctly when selecting option 'Win' in 'Show Win/Lose'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("@Step 3: Select filters which have data");
        log("@Step 4: Select option 'Win' in 'Show Win/Lose'");
        log("@Step 5: Click on 'Show' button");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter("","",date,"","","","WIN");
        log("Verify 1: Show all bets that have positive amount in 'Win/Lose' column");
        page.verifyShowBetWinLoseCorrect("Win");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29425")
    public void BBGPhoneBettingTC_29425(){
        log("@title: Validate the lost bets shows correctly when selecting option 'Lose' in 'Show Win/Lose'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("@Step 3: Select filters which have data");
        log("@Step 4: Select option 'Lose' in 'Show Win/Lose'");
        log("@Step 5: Click on 'Show' button");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter("","",date,"","","","LOSE");
        log("Verify 1: Show all bets that have positive amount in 'Win/Lose' column");
        page.verifyShowBetWinLoseCorrect("Lose");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29426")
    public void BBGPhoneBettingTC_29426(){
        log("@title: Validate the draw bets shows correctly when selecting option 'Draw' in 'Show Win/Lose'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("@Step 3: Select filters which have data");
        log("@Step 4: Select option 'Draw' in 'Show Win/Lose'");
        log("@Step 5: Click on 'Show' button");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter("","",date,date,"","","DRAW");
        log("Verify 1: Show all bets that have positive amount in 'Win/Lose' column");
        page.verifyShowBetWinLoseCorrect("Draw");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29427")
    public void BBGPhoneBettingTC_29427(){
        log("@title: Validate pending bets show correctly when account is checked 'Is Telebet'");
        log("@Pre-condition 1: Account is activated permission 'BBG - Phone Betting'");
        log("@Pre-condition 2: The player account is checked 'Is Telebet Account' in Master >> Client System >> Account");
        String accountCode = "QALKR";
        log("@Pre-condition 3: The player account placed an bet");
        String sport="Soccer";
        String dateAPI = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        welcomePage.placeBetAPI(sport, dateAPI,false,accountCode,"Goals","HDP","Home","FullTime",1,-0.5,"HK",5.5,
                "BACK",false,"");
        //Wait for Order display
        BetSettlementUtils.waitForBetIsUpdate(5);
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Soccer' and access 'BBG - Phone Betting' page");
        BBGPhoneBettingPage page = welcomePage.navigatePage(SOCCER,BBG_PHONE_BETTING, BBGPhoneBettingPage.class);
        log("@Step 3: Select filters which contains bets of player account at the precondition with Report By = Pending Bets");
        log("@Step 4: Click on 'Show' button");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        page.filter(KASTRAKI_LIMITED,"Pending Bets",date,"","","","");
        log("Verify 1: Show correct bet of player account");
        Assert.assertTrue(page.tblOrder.getColumn(page.tblOrder.getColumnIndexByName("Account Code"),false).contains(accountCode),"FAILED! "+accountCode+" does not display");
        log("INFO: Executed completely");
    }

}
