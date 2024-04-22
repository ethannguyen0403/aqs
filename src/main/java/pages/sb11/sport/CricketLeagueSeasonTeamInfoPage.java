package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;
import pages.sb11.sport.popup.*;

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
}
