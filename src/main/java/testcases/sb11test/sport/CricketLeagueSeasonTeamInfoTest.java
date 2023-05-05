package testcases.sb11test.sport;

import common.SBPConstants;
import objects.Team;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.CricketLeagueSeasonTeamInfoPage;
import testcases.BaseCaseAQS;
import pages.sb11.sport.popup.*;
import utils.testraildemo.TestRails;

import static common.SBPConstants.LEAGUE_SEASON_TEAM_INFO;
import static common.SBPConstants.SPORT;

public class CricketLeagueSeasonTeamInfoTest extends BaseCaseAQS {

    String leagueName = "QA Cricket Auto League";
    String country = "Asia";
    String seasonName = "QA Cricket Auto Season";
    String teamName = "QA Cricket Auto Team";

    @Test(groups = {"regression"})
    @TestRails(id = "2073")
    public void Cricket_League_Season_Team_Info_TC_001(){
        log("@title: Validate League/Season/Team Info for Cricket is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        log("@Step 3: Select Cricket at Go to dropdown");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("Validate the page is displayed with correct title page");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.lblTitleLeague.getText().contains("Cricket"), "Failed! League table is not displayed");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.lblTitleSeason.getText().contains("Season"), "Failed! Season table is not displayed");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.lblTitleTeam.getText().contains("Team"), "Failed! Team table is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2074")
    public void Cricket_League_Season_Team_Info_TC_002(){
        log("@title:Validate UI on League/Season/Team Info for Cricket is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("Validate the page is displayed with correct title page");
        log("League list table: Type, Country, League Name and Add button");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.ddTypeLeague.isDisplayed(),"Failed! League Type dropdown is not displayed!");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.ddCountryLeague.isDisplayed(),"Failed! League Country dropdown is not displayed!");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.txtLeagueName.isDisplayed(),"Failed! League Name textbox is not displayed!");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.btnAddLeague.isDisplayed(),"Failed! Add League button is not displayed!");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.btnSearchLeague.isDisplayed(),"Failed! Search League button is not displayed!");
        log("Season table: Add button");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.btnAddSeason.isDisplayed(),"Failed! Add Season button is not displayed!");
        log("Team List table: Country, Team Name, Add button");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.ddCountryTeam.isDisplayed(),"Failed! Team Country dropdown is not displayed!");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.txtTeamName.isDisplayed(),"Failed! Team Name textbox is not displayed!");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.btnAddTeam.isDisplayed(),"Failed! Add Team button is not displayed!");
        Assert.assertTrue(cricketLeagueSeasonTeamInfoPage.btnSearchTeam.isDisplayed(),"Failed! Search Team button is not displayed!");
        log("League list, Season and Team List table header columns are correctly displayed");
        Assert.assertEquals(cricketLeagueSeasonTeamInfoPage.tbLeague.getHeaderNameOfRows(), SBPConstants.LeagueSeasonTeamInfo.LEAGUE_TABLE_HEADER,"FAILED! League table header is incorrect display");
        Assert.assertEquals(cricketLeagueSeasonTeamInfoPage.tbSeason.getHeaderNameOfRows(), SBPConstants.LeagueSeasonTeamInfo.SEASON_TABLE_HEADER,"FAILED! Season table header is incorrect display");
        Assert.assertEquals(cricketLeagueSeasonTeamInfoPage.tbTeam.getHeaderNameOfRows(), SBPConstants.LeagueSeasonTeamInfo.TEAM_TABLE_HEADER,"FAILED! Team table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2078")
    public void Cricket_League_Season_Team_Info_TC_003(){
        log("@title: Validate that can add new Cricket League successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("@Step 3:  Click + button on League table");
        CreateCricketLeaguePopup createCricketLeaguePopup = cricketLeagueSeasonTeamInfoPage.openAddLeaguePopup();
        log("@Step 4: Fill full info > click Submit");
        createCricketLeaguePopup.addLeague(leagueName, leagueName, country, "", "", true,true,true);
        log("Validate that can add new Cricket List successfully");
        cricketLeagueSeasonTeamInfoPage.filterLeague("All",country,leagueName);
        cricketLeagueSeasonTeamInfoPage.isLeagueDisplayed(leagueName);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2079")
    public void Cricket_League_Season_Team_Info_TC_004(){
        log("@title: Validate that can add new Cricket season successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("@Step 3: Select a League > Click + button on Season table");
        CreateCricketSeasonPopup createSeasonPopup = cricketLeagueSeasonTeamInfoPage.openAddSeasonPopup(leagueName);
        log("@Step 4: Fill full info > click Submit");
        createSeasonPopup.addSeason(seasonName,"","",true);
        log("Validate that can add new Cricket season successfully");
        cricketLeagueSeasonTeamInfoPage.isSeasonDisplayed(leagueName,seasonName);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2080")
    public void Cricket_League_Season_Team_Info_TC_005(){
        log("@title: Validate that can add new Cricket Team successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("@Step 3: Select a League > Click + button on Team List table");
        CreateCricketTeamPopup createTeamPopup = cricketLeagueSeasonTeamInfoPage.openAddTeamPopup(leagueName,"");
        log("@Step 4: Fill full info > click Submit");
        Team team = new Team.Builder()
                .teamName("Qatar(U23)")
                .country("Other")
                .build();
        createTeamPopup.addNewTeam(team,true);
        log("Validate that can add new Cricket Team successfully");
        cricketLeagueSeasonTeamInfoPage.isTeamDisplayed(leagueName,"",teamName);
        log("INFO: Executed completely");
    }
}
