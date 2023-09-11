package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.sport.popup.*;

public class CricketLeagueSeasonTeamInfoPage extends WelcomePage {
    int colLeagueName = 4;
    int colSeasonName = 3;
    int colTeamName = 3;
    int colDeleteLeague = 8;
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
        ddCountryLeague.selectByVisibleText(countryLeague);
        txtLeagueName.sendKeys(leagueName);
        btnSearchLeague.click();
        waitSpinnerDisappeared();
    }

    public ConfirmDeleteLeaguePopup openDeleteLeaguePopup(String leagueName){
        filterLeague("All","All",leagueName);
        waitSpinnerDisappeared();
        int index = getRowContainsLeagueName(leagueName);
        tbLeague.getControlOfCell(1,colDeleteLeague,index,null).click();
        return new ConfirmDeleteLeaguePopup();
    }

    public boolean isLeagueDisplayed(String leagueName){
        int index = getRowContainsLeagueName(leagueName);
        if(index==0)
            return false;
        return true;
    }

    public boolean isSeasonDisplayed(String leagueName, String seasonName){
        filterLeague("All","All",leagueName);
        int index = getRowContainsSeasonName(seasonName);
        if(index==0)
            return false;
        return true;
    }

    public boolean isTeamDisplayed(String leagueName, String seasonName, String teamName){
        filterLeague("All","All",leagueName);
        if (seasonName != ""){
            selectSeason(seasonName);
        }
        txtTeamName.sendKeys(teamName);
        btnSearchTeam.click();
        int index = getRowContainsTeamName(teamName);
        if(index==0)
            return false;
        return true;
    }


    public void selectSeason(String seasonName){
        int index = getRowContainsSeasonName(seasonName);
        tbSeason.getControlOfCell(1,colSeasonName,index,null).click();
    }

    private int getRowContainsLeagueName(String leagueName){
        int i = 1;
        Label lblLeagueName;
        while (true){
            lblLeagueName = Label.xpath(tbLeague.getxPathOfCell(1,colLeagueName,i,null));
            if(!lblLeagueName.isDisplayed()){
                System.out.println("The League "+leagueName+" does not display in the list");
                return 0;
            }
            if(lblLeagueName.getText().contains(leagueName))
                return i;
            i = i +1;
        }
    }

    private int getRowContainsSeasonName(String seasonName){
        int i = 1;
        Label lblSeasonName;
        while (true){
            lblSeasonName = Label.xpath(tbSeason.getxPathOfCell(1,colSeasonName,i,null));
            if(!lblSeasonName.isDisplayed()){
                System.out.println("The Season "+seasonName+" does not display in the list");
                return 0;
            }
            if(lblSeasonName.getText().contains(seasonName))
                return i;
            i = i +1;
        }
    }

    private int getRowContainsTeamName(String teamName){
        int i = 1;
        Label lblTeamName;
        while (true){
            lblTeamName = Label.xpath(tbTeam.getxPathOfCell(1,colTeamName,i,null));
            if(!lblTeamName.isDisplayed()){
                System.out.println("The Team "+teamName+" does not display in the list");
                return 0;
            }
            if(lblTeamName.getText().contains(teamName))
                return i;
            i = i +1;
        }
    }

}
