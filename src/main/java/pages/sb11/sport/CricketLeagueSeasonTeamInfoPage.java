package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;
import pages.sb11.sport.popup.*;

import static common.SBPConstants.COUNTRY_LIST;

public class CricketLeagueSeasonTeamInfoPage extends WelcomePage {
    int colLeagueName = 4;
    int colSeasonName = 3;
    int colTeamName = 3;
    int colDeleteLeague = 8;
    int colDeleteTeam = 6;
    public Label lblTitleLeague = Label.xpath("//app-league//div[contains(@class,'main-box-header')]//span[1]");
    public Label lblTitleSeason = Label.xpath("//app-season//div[contains(@class,'main-box-header')]//span[1]");
    public Label lblTitleTeam = Label.xpath("//app-team//div[contains(@class,'main-box-header')]//span[1]");
    public DropDownBox ddGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");
    public DropDownBox ddTypeLeague = DropDownBox.id("typeSelected");
    public DropDownBox ddCountryLeague = DropDownBox.id("countrySelected");
    public TextBox txtLeagueName = TextBox.xpath("//app-league//div[contains(text(),'League Name')]//following::input[1]");
    public Button btnSearchLeague = Button.xpath("//app-league//div[contains(text(),'League Name')]//following::button[1]");
    public DropDownBox ddCountryTeam = DropDownBox.xpath("//app-team//div[contains(text(),'Country')]//following::select[1]");
    public TextBox txtTeamName = TextBox.xpath("//app-team//div[contains(text(),'Team Name')]//following::input[1]");
    public Button btnSearchTeam = Button.xpath("//app-league//div[contains(text(),'League Name')]//following::button[1]");
    public Button btnAddLeague = Button.xpath("//app-league//div[contains(@class,'main-box-header')]//span[1]//following::button[1]");
    public Button btnAddSeason = Button.xpath("//app-season//div[contains(@class,'main-box-header')]//span[1]//following::button[1]");
    public Button btnAddTeam = Button.xpath("//app-team//div[contains(@class,'main-box-header')]//span[1]//following::button[1]");
    public Table tbLeague = Table.xpath("//app-league//div[contains(@class,'main-box-header')]//following::table[1]",8);
    public Table tbSeason = Table.xpath("//app-season//div[contains(@class,'main-box-header')]//following::table[1]",7);
    public Table tbTeam = Table.xpath("//app-team//div[contains(@class,'main-box-header')]//following::table[1]",7);
    public Label lblLeagueName = Label.xpath("//div[contains(text(),'League Name')]");
    public Label lblTeamName = Label.xpath("//div[contains(text(),'Team Name')]");

    public void goToCricket(){
        ddGoTo.selectByVisibleText("Cricket");
        waitSpinnerDisappeared();
    }

    public CreateCricketLeaguePopup openAddLeaguePopup(){
        btnAddLeague.click();
        return new CreateCricketLeaguePopup();
    }

    public CreateCricketSeasonPopup openAddSeasonPopup(String leagueName){
        filterLeague("All","All",leagueName);
        btnAddSeason.click();
        return new CreateCricketSeasonPopup();
    }

    public CreateCricketTeamPopup openAddTeamPopup(String leagueName, String seasonName){
        filterLeague("All","All",leagueName);
        if (seasonName != ""){
            selectSeason(seasonName);
            btnAddTeam.click();
        } else {
            btnAddTeam.click();
        }
        return new CreateCricketTeamPopup();
    }

    public void filterLeague(String typeLeague, String countryLeague, String leagueName){
        ddTypeLeague.selectByVisibleText(typeLeague);
        waitSpinnerDisappeared();
        ddCountryLeague.selectByVisibleText(countryLeague);
        txtLeagueName.sendKeys(leagueName);
        btnSearchLeague.click();
        waitSpinnerDisappeared();
    }

    public ConfirmDeleteLeaguePopup openDeleteLeaguePopup(String leagueName){
        filterLeague("All","All",leagueName);
        waitSpinnerDisappeared();
        int index = tbLeague.getRowIndexContainValue(leagueName,tbLeague.getColumnIndexByName("League Name"),null);
        tbLeague.getControlOfCell(1,colDeleteLeague,index,null).click();
        return new ConfirmDeleteLeaguePopup();
    }
    public boolean isTeamDisplayed(String leagueName, String seasonName, String teamName){
        filterLeague("All","All",leagueName);
        if (seasonName != ""){
            selectSeason(seasonName);
        }
        txtTeamName.sendKeys(teamName);
        btnSearchTeam.click();
        int index = tbTeam.getRowIndexContainValue(teamName,tbTeam.getColumnIndexByName("Team Name"),null);
        if(index==0)
            return false;
        return true;
    }

    public void selectSeason(String seasonName){
        int index = tbSeason.getRowIndexContainValue(seasonName,tbSeason.getColumnIndexByName("Season Name"),null);
        tbSeason.getControlOfCell(1,colSeasonName,index,null).click();
    }

    public void deleteTeamName(String leagueName, String seasonName, String teamName) {
        filterLeague("All","All",leagueName);
        if (seasonName != ""){
            selectSeason(seasonName);
        }
        txtTeamName.sendKeys(teamName);
        btnSearchTeam.click();
        waitSpinnerDisappeared();
        int index = tbTeam.getRowIndexContainValue(teamName,tbTeam.getColumnIndexByName("Team Name"),null);
        if(index==0){
            System.err.println("ERROR! "+teamName+" does not display");
        } else {
            tbTeam.getControlOfCell(1,colDeleteTeam,index,"i").click();
            ConfirmPopup popup = new ConfirmPopup();
            popup.confirm(true);
            waitSpinnerDisappeared();
        }
    }

    public void verifyUI() {
        System.out.println("League list table: Type, Country, League Name and Add button");
        Assert.assertEquals(ddTypeLeague.getOptions(), SBPConstants.LeagueSeasonTeamInfo.SOCCER_TYPE,"Failed! League Type dropdown is not displayed!");
        Assert.assertEquals(ddCountryLeague.getOptions(), COUNTRY_LIST,"Failed! League Country dropdown is not displayed!");
        Assert.assertEquals(lblLeagueName.getText(),"League Name","Failed! League Name textbox is not displayed!");
        Assert.assertTrue(btnAddLeague.isDisplayed(),"Failed! Add League button is not displayed!");
        Assert.assertTrue(btnSearchLeague.isDisplayed(),"Failed! Search League button is not displayed!");
        System.out.println("Season table: Add button");
        Assert.assertTrue(btnAddSeason.isDisplayed(),"Failed! Add Season button is not displayed!");
        System.out.println("Team List table: Country, Team Name, Add button");
        Assert.assertEquals(ddCountryTeam.getOptions(), COUNTRY_LIST,"Failed! Team Country dropdown is not displayed!");
        Assert.assertEquals(lblTeamName.getText(),"Team Name","Failed! Team Name textbox is not displayed!");
        Assert.assertTrue(btnAddTeam.isDisplayed(),"Failed! Add Team button is not displayed!");
        Assert.assertTrue(btnSearchTeam.isDisplayed(),"Failed! Search Team button is not displayed!");
        System.out.println("League list, Season and Team List table header columns are correctly displayed");
        Assert.assertEquals(tbLeague.getHeaderNameOfRows(), SBPConstants.LeagueSeasonTeamInfo.LEAGUE_TABLE_HEADER,"FAILED! League table header is incorrect display");
        Assert.assertEquals(tbSeason.getHeaderNameOfRows(), SBPConstants.LeagueSeasonTeamInfo.SEASON_TABLE_HEADER,"FAILED! Season table header is incorrect display");
        Assert.assertEquals(tbTeam.getHeaderNameOfRows(), SBPConstants.LeagueSeasonTeamInfo.TEAM_TABLE_HEADER,"FAILED! Team table header is incorrect display");
    }
}
