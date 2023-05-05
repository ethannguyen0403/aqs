package testcases.sb11test.sport;

import objects.Team;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.SoccerLeagueSeasonTeamInfoPage;
import testcases.BaseCaseAQS;
import pages.sb11.sport.popup.CreateSoccerLeaguePopup;
import pages.sb11.sport.popup.CreateSoccerSeasonPopup;
import pages.sb11.sport.popup.CreateSoccerTeamPopup;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class SoccerLeagueSeasonTeamInfoTest extends BaseCaseAQS {
    String leagueName = "QA Soccer Auto League";
    String country = "Asia";
    String seasonName = "QA Soccer Auto Season";
    String teamName = "QA Soccer Auto Team";

    @Test(groups = {"regression"})
    @TestRails(id = "2071")
    public void Soccer_League_Season_Team_Info_TC_001(){
        log("@title: Validate League/Season/Team Info for Soccer is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        SoccerLeagueSeasonTeamInfoPage soccerLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("Validate the page is displayed with correct title page");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.lblTitleLeague.getText().contains("Soccer"), "Failed! League table is not displayed");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.lblTitleSeason.getText().contains("Season"), "Failed! Season table is not displayed");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.lblTitleTeam.getText().contains("Team"), "Failed! Team table is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2072")
    public void Soccer_League_Season_Team_Info_TC_002(){
        log("@title:Validate UI on League/Season/Team Info for Soccer is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        SoccerLeagueSeasonTeamInfoPage soccerLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("Validate the page is displayed with correct title page");
        log("League list table: Type, Country, League Name and Add button");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.ddTypeLeague.isDisplayed(),"Failed! League Type dropdown is not displayed!");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.ddCountryLeague.isDisplayed(),"Failed! League Country dropdown is not displayed!");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.txtLeagueName.isDisplayed(),"Failed! League Name textbox is not displayed!");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.btnAddLeague.isDisplayed(),"Failed! Add League button is not displayed!");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.btnSearchLeague.isDisplayed(),"Failed! Search League button is not displayed!");
        log("Season table: Add button");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.btnAddSeason.isDisplayed(),"Failed! Add Season button is not displayed!");
        log("Team List table: Country, Team Name, Add button");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.ddCountryTeam.isDisplayed(),"Failed! Team Country dropdown is not displayed!");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.txtTeamName.isDisplayed(),"Failed! Team Name textbox is not displayed!");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.btnAddTeam.isDisplayed(),"Failed! Add Team button is not displayed!");
        Assert.assertTrue(soccerLeagueSeasonTeamInfoPage.btnSearchTeam.isDisplayed(),"Failed! Search Team button is not displayed!");
        log("League list, Season and Team List table header columns are correctly displayed");
        Assert.assertEquals(soccerLeagueSeasonTeamInfoPage.tbLeague.getHeaderNameOfRows(), LeagueSeasonTeamInfo.LEAGUE_TABLE_HEADER,"FAILED! League table header is incorrect display");
        Assert.assertEquals(soccerLeagueSeasonTeamInfoPage.tbSeason.getHeaderNameOfRows(), LeagueSeasonTeamInfo.SEASON_TABLE_HEADER,"FAILED! Season table header is incorrect display");
        Assert.assertEquals(soccerLeagueSeasonTeamInfoPage.tbTeam.getHeaderNameOfRows(), LeagueSeasonTeamInfo.TEAM_TABLE_HEADER,"FAILED! Team table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2075")
    public void Soccer_League_Season_Team_Info_TC_003(){
        log("@title: Validate that can add new Soccer League successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        SoccerLeagueSeasonTeamInfoPage soccerLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("@Step 3:  Click + button on League table");
        CreateSoccerLeaguePopup createLeaguePopup = soccerLeagueSeasonTeamInfoPage.openAddLeaguePopup();
        log("@Step 4: Fill full info > click Submit");
        createLeaguePopup.addLeague(leagueName, leagueName, country, "", "", true,true,true);
        log("Validate that can add new Soccer List successfully");
        soccerLeagueSeasonTeamInfoPage.filterLeague("All",country,leagueName);
        soccerLeagueSeasonTeamInfoPage.isLeagueDisplayed(leagueName);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2076")
    public void Soccer_League_Season_Team_Info_TC_004(){
        log("@title: Validate that can add new Soccer season successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        SoccerLeagueSeasonTeamInfoPage soccerLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("@Step 3: Select a League > Click + button on Season table");
        CreateSoccerSeasonPopup createSeasonPopup = soccerLeagueSeasonTeamInfoPage.openAddSeasonPopup(leagueName);
        log("@Step 4: Fill full info > click Submit");
        createSeasonPopup.addSeason(seasonName,"","",true);
        log("Validate that can add new Soccer season successfully");
        soccerLeagueSeasonTeamInfoPage.isSeasonDisplayed(leagueName,seasonName);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2077")
    public void Soccer_League_Season_Team_Info_TC_005(){
        log("@title: Validate that can add new Soccer Team successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        SoccerLeagueSeasonTeamInfoPage soccerLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, SoccerLeagueSeasonTeamInfoPage.class);
        log("@Step 3: Select a League > Click + button on Team List table");
        CreateSoccerTeamPopup createTeamPopup = soccerLeagueSeasonTeamInfoPage.openAddTeamPopup(leagueName,"");
        log("@Step 4: Fill full info > click Submit");
        Team team = new Team.Builder()
                .teamName("Qatar(U23)")
                .country("Other")
                .build();
        createTeamPopup.addNewTeam(team,true);
        log("Validate that can add new Soccer Team successfully");
        soccerLeagueSeasonTeamInfoPage.isTeamDisplayed(leagueName,"",teamName);
        log("INFO: Executed completely");
    }
}
