package testcases.sb11test.sport;

import common.SBPConstants;
import objects.Team;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.CricketLeagueSeasonTeamInfoPage;
import testcases.BaseCaseAQS;
import pages.sb11.sport.popup.*;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;
import static common.SBPConstants.COUNTRY_LIST;

public class CricketLeagueSeasonTeamInfoTest extends BaseCaseAQS {
    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2073")
    public void Cricket_League_Season_Team_Info_TC_2073(){
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

    @Test(groups = {"regression","ethan5.0"})
    @TestRails(id = "2074")
    public void Cricket_League_Season_Team_Info_TC_2074(){
        log("@title:Validate UI on League/Season/Team Info for Cricket is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click on Sport > League/Season/Team Info page");
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("Validate the page is displayed with correct title page");
        cricketLeagueSeasonTeamInfoPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2078")
    public void Cricket_League_Season_Team_Info_TC_2078(){
        log("@title: Validate that can add new Cricket League successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        String leagueName = "QA Cricket League Auto 1";
        String country = "Asia";
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("@Step 3:  Click + button on League table");
        CreateCricketLeaguePopup createCricketLeaguePopup = cricketLeagueSeasonTeamInfoPage.openAddLeaguePopup();
        log("@Step 4: Fill full info > click Submit");
        createCricketLeaguePopup.addLeague(leagueName, leagueName, country, "", "", true,true,true);
        log("Validate that can add new Cricket List successfully");
        cricketLeagueSeasonTeamInfoPage.filterLeague("All",country,leagueName);
        Assert.assertFalse(cricketLeagueSeasonTeamInfoPage.tbLeague.getRowIndexContainValue(leagueName, cricketLeagueSeasonTeamInfoPage.tbLeague.getColumnIndexByName("League Name"),null)
        == 0, "FAILED! Can not add new Cricket List");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2079")
    public void Cricket_League_Season_Team_Info_TC_2079(){
        log("@title: Validate that can add new Cricket season successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        String leagueName = "QA Cricket League Auto";
        String seasonName = "QA Cricket Season Auto";
        CricketLeagueSeasonTeamInfoPage cricketLeagueSeasonTeamInfoPage = welcomePage.navigatePage(SPORT,LEAGUE_SEASON_TEAM_INFO, CricketLeagueSeasonTeamInfoPage.class);
        cricketLeagueSeasonTeamInfoPage.goToCricket();
        log("@Step 3: Select a League > Click + button on Season table");
        CreateCricketSeasonPopup createSeasonPopup = cricketLeagueSeasonTeamInfoPage.openAddSeasonPopup(leagueName);
        log("@Step 4: Fill full info > click Submit");
        createSeasonPopup.addSeason(seasonName,"","",true);
        log("Validate that can add new Cricket season successfully");
        Assert.assertFalse(cricketLeagueSeasonTeamInfoPage.tbSeason.getRowIndexContainValue(seasonName,cricketLeagueSeasonTeamInfoPage.tbSeason.getColumnIndexByName("Season Name"),null)
        == 0,"FAILED! Can not add new Cricket season");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2080")
    public void Cricket_League_Season_Team_Info_TC_2080(){
        log("@title: Validate that can add new Cricket Team successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > League/Season/Team Info");
        String leagueName = "QA Cricket League Auto";
        String teamName = "QA Cricket Team Auto";
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
