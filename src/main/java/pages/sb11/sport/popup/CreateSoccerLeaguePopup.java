package pages.sb11.sport.popup;

import com.paltech.element.common.*;

public class CreateSoccerLeaguePopup {
    Label lblTitle = Label.xpath("//app-dialog-create-league//div[contains(@class,'modal-title')]");
    public String getTitlePage (){return lblTitle.getText().trim();}
    public TextBox txtLeagueName = TextBox.xpath("//app-dialog-create-league//span[contains(text(),'League Name')]//following::input[1]");
    public TextBox txtShortName = TextBox.xpath("//app-dialog-create-league//span[contains(text(),'Short Name')]//following::input[1]");
    public DropDownBox ddCountry = DropDownBox.xpath("//app-dialog-create-league//span[contains(text(),'Country')]//following::select[1]");
    public TextBox txtFGColor = TextBox.xpath("//app-dialog-create-league//span[contains(text(),'FG Color')]//following::input[1]");
    public TextBox txtBGColor = TextBox.xpath("//app-dialog-create-league//span[contains(text(),'BG Color')]//following::input[1]");
    public CheckBox cbMainLeague = CheckBox.xpath("//app-dialog-create-league//span[contains(text(),'Is Main League')]//following::input[1]");
    public CheckBox cbCup = CheckBox.xpath("//app-dialog-create-league//span[contains(text(),'Cup')]//following::input[1]");
    public Button btnSubmit = Button.xpath("//app-dialog-create-league//button[contains(text(),'Submit')]");
    public Button btnClear = Button.xpath("//app-dialog-create-league//span[contains(text(),'Clear')]");
    public Button btnClose = Button.xpath("//app-dialog-create-league//span[contains(@class,'close-icon')]");
    public void close(){btnClose.click();}

    public void addLeague (String leagueName, String shortName, String country, String fgColor, String bgColor, boolean isMainLeague, boolean isCup, boolean isSubmit){
        txtLeagueName.sendKeys(leagueName);
        txtShortName.sendKeys(shortName);
        ddCountry.selectByVisibleText(country);
        if (!fgColor.isEmpty()){
            txtFGColor.sendKeys(fgColor);
        }
        if (!bgColor.isEmpty()){
            txtBGColor.sendKeys(bgColor);
        }
        if (isMainLeague){
            cbMainLeague.click();
        }
        if (isCup){
            cbCup.click();
        }
        if (isSubmit){
            btnSubmit.click();
        }
    }

}
