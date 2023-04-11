package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;

public class SoccerLeagueSeasonTeamInfoPage extends WelcomePage {
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
}
