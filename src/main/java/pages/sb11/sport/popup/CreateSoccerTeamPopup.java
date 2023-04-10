package pages.sb11.sport.popup;

import com.paltech.element.common.*;
import controls.Table;
import objects.Team;

public class CreateSoccerTeamPopup {
    int colTeamName = 2;
    int colSelect = 4;
    Label lblTitle = Label.xpath("//app-dialog-add-team//div[contains(@class,'modal-title')]");
    public String getTitlePage (){return lblTitle.getText().trim();}
    public DropDownBox ddAddCountry = DropDownBox.xpath("//app-dialog-add-team//div[contains(@class,'mb-4')]//select");
    public TextBox txtAddTeamName = TextBox.xpath("//app-dialog-add-team//div[contains(@class,'mb-4')]//input");
    public DropDownBox ddCountry = DropDownBox.id("countrySelected");
    public TextBox txtTeamName = TextBox.name("teamName");
    public Button btnAdd = Button.xpath("//app-dialog-add-team//button[contains(text(),'Add')]");
    public Button btnSearch = Button.xpath("//app-dialog-add-team//button[contains(text(),'Search')]");
    public Button btnSubmit = Button.xpath("//app-dialog-add-team//button[contains(text(),'Submit')]");
    public Button btnClear = Button.xpath("//app-dialog-add-team//span[contains(text(),'Clear')]");
    public Button btnClose = Button.xpath("//app-dialog-add-team//span[contains(@class,'close-icon')]");
    public void close(){btnClose.click();}
    public Table tbSelectedTeam = Table.xpath("//app-dialog-add-team//div[contains(@class,'no-gutters')]",4);
    public Table tbAddTeam = Table.xpath("//app-dialog-add-team//div[contains(@class,'mb-2')]//table",4);

    public void addNewTeam (Team team, boolean isSubmit){
        ddAddCountry.selectByVisibleText(team.getCountry());
        txtAddTeamName.sendKeys(team.getTeamName());
        btnAdd.click();
        if (isSubmit){
            btnSubmit.click();
        }
    }

    public void addExistTeam (Team team, boolean isSubmit){
        selectTeam(team);
        if (isSubmit){
            btnSubmit.click();
        }
    }

    public void selectTeam(Team team){
        txtTeamName.sendKeys(team.getTeamName());
        ddCountry.selectByVisibleText(team.getCountry());
        btnSearch.click();

        int index = getRowContainsTeamName(team.getTeamName());
        tbAddTeam.getControlOfCell(1,colSelect,index,null).click();
    }

    private int getRowContainsTeamName(String teamName){
        int i = 1;
        Label lblTeamName;
        while (true){
            lblTeamName = Label.xpath(tbAddTeam.getxPathOfCell(1,colTeamName,i,null));
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
