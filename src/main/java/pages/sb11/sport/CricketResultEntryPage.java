package pages.sb11.sport;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

public class CricketResultEntryPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    public Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    public Button btnShowLeagues = Button.xpath("//app-bet-entry//button[contains(text(),'Show Leagues')]");
    public Button btnShow = Button.xpath("//app-bet-entry//button[@class='btn btn-show']");
    public DropDownBox ddpType = DropDownBox.id("type");
    public DropDownBox ddpLeague = DropDownBox.id("sport");
    public DropDownBox ddpOrderBy = DropDownBox.id("betType");
    public DropDownBox ddpStatus = DropDownBox.id("status");
    public TextBox txtDateTime = TextBox.id("date");
    public Label lblDate = Label.xpath("//label[contains(text(),'Date')]");
    public DateTimePicker dtpDateTime = DateTimePicker.xpath(txtDateTime,"//bs-days-calendar-view");
    public Table tbResult = Table.xpath("//app-bet-entry//div[contains(@class,'main-box-header')]//following::table[1]",10);
    public DropDownBox ddGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");

    public void goToSport(String sport){
        if(sport.equals("Soccer"))
            btnSoccer.click();
        if(sport.equals("Cricket"))
            btnCricket.click();
    }

    public void filterResult(String type, String date, String league, String orderBy, String status, boolean isShow){
        ddpType.selectByVisibleText(type);
        if(!date.isEmpty()){
            dtpDateTime.selectDate(date,"dd/MM/yyyy");
            btnShowLeagues.click();
        }
        ddpLeague.selectByVisibleText(league);
        ddpOrderBy.selectByVisibleText(orderBy);
        ddpStatus.selectByVisibleText(status);
        if (isShow){
            btnShow.click();
        }
    }
}
