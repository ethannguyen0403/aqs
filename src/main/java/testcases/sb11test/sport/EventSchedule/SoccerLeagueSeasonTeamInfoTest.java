package testcases.sb11test.sport.EventSchedule;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.SoccerLeagueSeasonTeamInfoPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class SoccerLeagueSeasonTeamInfoTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    public void Soccer_League_Season_Team_Info_TC_001(){
        log("@title: Validate League/Season/Team Info for Soccer is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        SoccerLeagueSeasonTeamInfoPage leagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("Validate the page is displayed with correct title page");
        Assert.assertTrue(leagueSeasonTeamInfoPage.lblTitleLeague.getText().contains("Soccer"), "Failed! League table is not displayed");
        Assert.assertTrue(leagueSeasonTeamInfoPage.lblTitleSeason.getText().contains("Season"), "Failed! Season table is not displayed");
        Assert.assertTrue(leagueSeasonTeamInfoPage.lblTitleTeam.getText().contains("Team"), "Failed! Team table is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void Soccer_League_Season_Team_Info_TC_002(){
        log("@title:Validate UI on League/Season/Team Info for Soccer is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        SoccerLeagueSeasonTeamInfoPage leagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("Validate the page is displayed with correct title page");
        log("League list table: Type, Country, League Name and Add button");
        Assert.assertTrue(leagueSeasonTeamInfoPage.ddTypeLeague.isDisplayed(),"Failed! League Type dropdown is not displayed!");
        Assert.assertTrue(leagueSeasonTeamInfoPage.ddCountryLeague.isDisplayed(),"Failed! League Country dropdown is not displayed!");
        Assert.assertTrue(leagueSeasonTeamInfoPage.txtLeagueName.isDisplayed(),"Failed! League Name textbox is not displayed!");
        Assert.assertTrue(leagueSeasonTeamInfoPage.btnAddLeague.isDisplayed(),"Failed! Add League button is not displayed!");
        Assert.assertTrue(leagueSeasonTeamInfoPage.btnSearchLeague.isDisplayed(),"Failed! Search League button is not displayed!");
        log("Season table: Add button");
        Assert.assertTrue(leagueSeasonTeamInfoPage.btnAddSeason.isDisplayed(),"Failed! Add Season button is not displayed!");
        log("Team List table: Country, Team Name, Add button");
        Assert.assertTrue(leagueSeasonTeamInfoPage.ddCountryTeam.isDisplayed(),"Failed! Team Country dropdown is not displayed!");
        Assert.assertTrue(leagueSeasonTeamInfoPage.txtTeamName.isDisplayed(),"Failed! Team Name textbox is not displayed!");
        Assert.assertTrue(leagueSeasonTeamInfoPage.btnAddTeam.isDisplayed(),"Failed! Add Team button is not displayed!");
        Assert.assertTrue(leagueSeasonTeamInfoPage.btnSearchTeam.isDisplayed(),"Failed! Search Team button is not displayed!");
        log("League list, Season and Team List table header columns are correctly displayed");
        Assert.assertEquals(leagueSeasonTeamInfoPage.tbLeague.getHeaderNameOfRows(), LeagueSeasonTeamInfo.LEAGUE_TABLE_HEADER,"FAILED! League table header is incorrect display");
        Assert.assertEquals(leagueSeasonTeamInfoPage.tbSeason.getHeaderNameOfRows(), LeagueSeasonTeamInfo.SEASON_TABLE_HEADER,"FAILED! Season table header is incorrect display");
        Assert.assertEquals(leagueSeasonTeamInfoPage.tbTeam.getHeaderNameOfRows(), LeagueSeasonTeamInfo.TEAM_TABLE_HEADER,"FAILED! Team table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    public void Soccer_League_Season_Team_Info_TC_003(){
        log("@title: Validate that can add new Soccer League successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        SoccerLeagueSeasonTeamInfoPage leagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("@Step 3: Select Cricket at Go to dropdown");
        log("Validate the page is displayed with correct title page");
        Assert.assertTrue(leagueSeasonTeamInfoPage.lblTitleLeague.getText().contains("Cricket"), "Failed! League table is not displayed");
        Assert.assertTrue(leagueSeasonTeamInfoPage.lblTitleSeason.getText().contains("Season"), "Failed! Season table is not displayed");
        Assert.assertTrue(leagueSeasonTeamInfoPage.lblTitleTeam.getText().contains("Team"), "Failed! Team table is not displayed");
        log("INFO: Executed completely");
    }
}
