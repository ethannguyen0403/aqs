package pages.sb11.sport;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import objects.Event;
import pages.sb11.WelcomePage;
import pages.sb11.trading.CricketBetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;

public class EventSchedulePage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    DropDownBox ddpLeague = DropDownBox.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-header']//div[@class='filter-league']//select");
    TextBox txtDateTime = TextBox.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-header']//div[@class='card-body']//input");
    DateTimePicker dtpDateTime = DateTimePicker.xpath(txtDateTime,"//bs-days-calendar-view");
    Button btnShow = Button.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-header']//div[@class='card-body']//button[contains(@class,'btn-show')]");
    int totalTblEventCol = 8;
    int colD = 1;
    int colHomeTeam = 2;
    int colAwayTeam = 3;
    int colTime = 4;
    int colLive = 5;
    int colN = 6;
    int colTv = 7;
    int colStatus = 8;
    Table tblEvent = Table.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//table",totalTblEventCol);
    Button btnAdd = Button.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//button[contains(@class,'btn-show')][1]");
    Button btnSubmit = Button.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//button[contains(@class,'btn-show')][2]");
    TextBox txtEventNumbe = TextBox.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//input");
    Label lblClear = Label.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//span[contains(@class,'text-clear')]");
    RadioButton rbHome = RadioButton.xpath("//app-schedule-list//div[@class='league-header']//input[@id='homeTeamCheck']");
    RadioButton rbAway = RadioButton.xpath("//app-schedule-list//div[@class='league-header']//input[@id='homeTeamCheck']");
    Link lnkShowLeagure = Link.xpath("//app-schedule-list//div[@class='league-header']//span[contains(@class,'text-show-league')]");
    TextBox txtScheduleListDateTime = TextBox.xpath("//app-schedule-list//div[@class='league-header']//input[contains(@class,'league-date')]");
    DateTimePicker dtpScheduleListDateTime= DateTimePicker.xpath(txtScheduleListDateTime,"//bs-days-calendar-view");
    Button btnShowSchedule = Button.xpath("//app-schedule-list//div[@class='league-header']//button[contains(@class,'show-btn-league')]");
    int totalEventScheduleColumn = 11;
    Table tblEventBody = Table.xpath("//app-schedule-list//div[contains(@class,'event-body')]//table",totalEventScheduleColumn);
    Table tblLeagueBody = Table.xpath("//app-schedule-list//div[contains(@class,'league-body')]//table",totalEventScheduleColumn);
    public void goToSport(String sport){
        if(sport.equals("Soccer"))
            btnSoccer.click();
        if(sport.equals("Cricket"))
            btnCricket.click();
    }

    public void showLeague(String league, String date){
        ddpLeague.selectByVisibleText(league);
        if(!date.isEmpty()){
            if(!date.equals(txtDateTime.getAttribute("value").trim())){
                dtpDateTime.selectDate(date,"dd/MM/yyyy");
            }
        }
        btnShow.click();
        waitPageLoad();
    }

    public void addEvent(Event event){
        fillEventInfo(event,1);
        btnSubmit.click();
    }

    private void fillEventInfo(Event event, int rowIndex){
        DropDownBox ddpHomeTeam = DropDownBox.xpath(tblEvent.getxPathOfCell(1,colHomeTeam,rowIndex,"select"));
        DropDownBox ddpAwayTeam = DropDownBox.xpath(tblEvent.getxPathOfCell(1,colAwayTeam,rowIndex,"select"));
        TextBox txtTime = TextBox.xpath(tblEvent.getxPathOfCell(1,colTime,rowIndex,"input"));
        CheckBox cbLive = CheckBox.xpath(tblEvent.getxPathOfCell(1,colLive,rowIndex,"input"));
        CheckBox cbN = CheckBox.xpath(tblEvent.getxPathOfCell(1, colN,rowIndex,"input"));
        DropDownBox ddpStatus = DropDownBox.xpath(tblEvent.getxPathOfCell(1,colStatus,rowIndex,"select"));
        ddpHomeTeam.selectByVisibleText(event.getHome());
        ddpAwayTeam.selectByVisibleText(event.getAway());
        txtTime.sendKeys(event.getOpenTime());
        if(cbLive.isSelected() != event.isLive())
            cbLive.click();
        if(cbN.isSelected() != event.isN())
            cbN.click();
        ddpStatus.selectByVisibleText(event.getEventStatus());
    }

}
