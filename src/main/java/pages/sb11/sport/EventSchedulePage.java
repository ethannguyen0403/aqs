package pages.sb11.sport;

import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Table;
import objects.Event;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.control.ConfirmPopupControl;
import pages.sb11.popup.ConfirmPopup;
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
    TextBox txtEventNumber = TextBox.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//input");
    Label lblClear = Label.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//span[contains(@class,'text-clear')]");
    RadioButton rbHome = RadioButton.xpath("//app-schedule-list//div[@class='league-header']//input[@id='homeTeamCheck']");
    RadioButton rbAway = RadioButton.xpath("//app-schedule-list//div[@class='league-header']//input[@id='homeTeamCheck']");
    Link lnkShowLeague = Link.xpath("//app-schedule-list//div[@class='league-header']//span[contains(@class,'text-show-league')]");
    TextBox txtScheduleListDateTime = TextBox.xpath("//app-schedule-list//div[@class='league-header']//input[contains(@class,'league-date')]");
    TextBox txtTeam = TextBox.xpath("//app-schedule-list//div[@class='card-body league-card-body']//input[contains(@class,'team-name-input')]");
    DateTimePicker dtpScheduleListDateTime= DateTimePicker.xpath(txtScheduleListDateTime,"//bs-days-calendar-view");
    Button btnShowSchedule = Button.xpath("//app-schedule-list//div[@class='league-header']//button[contains(@class,'show-btn-league')]");
    int totalEventScheduleColumn = 11;
    int colNum =1;
    int coli = 2;
    int colDate = 3;
    int colHomeTeamEventTbl= 4;
    int colAwayTeamEventTbl= 5;
    int colTimeEventTbl= 6;
    int colLiveEventTbl= 7;
    int colNEventTbl= 8;
    int colTVEventTbl= 9;
    int colStatusEventTbl= 10;
    int colActionEventTbl = 11;
    Table tblEventBody = Table.xpath("//app-schedule-list//div[contains(@class,'event-body')]//table",totalEventScheduleColumn);
    Table tblLeagueBody = Table.xpath("//app-schedule-list//div[contains(@class,'league-body')]//table",totalEventScheduleColumn);
    public DropDownBox ddGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");
    public void goToSport(String sport){
        if(sport.equals("Soccer"))
            btnSoccer.click();
        if(sport.equals("Cricket"))
            ddGoTo.selectByVisibleText("Cricket");
    }

    public void showLeague(String league, String date){
        //To wait page completely loaded the list league
        waitPageLoad();
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
    public void deleteEvent(Event event){
        goToSport(event.getSportName());
        showScheduleList(true, event.getHome(),event.getEventDate());
        int index = getEventIndex(event);
        if(index == 0)
            System.out.println("Not found the event in the list for deleting");
        Icon.xpath(tblEventBody.getxPathOfCell(1,colActionEventTbl,index,"i[contains(@class,'fa-times-circle')]")).click();
        ConfirmPopup popup = new ConfirmPopup();
        popup.confirm(true);
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

    public void showScheduleList(boolean isHomeTeam, String teamName, String date){
        if (isHomeTeam)
            rbHome.click();
        else
            rbAway.click();
        if(!teamName.isEmpty()){
            txtTeam.sendKeys(teamName);
        }

        if(!date.isEmpty()){
            if(!date.equals(txtScheduleListDateTime.getAttribute("value").trim())){
                dtpScheduleListDateTime.selectDate(date,"dd/MM/yyyy");
            }
        }
        btnShowSchedule.click();
        waitSpinnerDisappeared();
    }

    private int getEventIndex(Event event){
        int i = 1;
        Label lblLeagueName ;
//        int leagueIndex = tblEventBody.getRowIndexContainValue(event.getLeagueName(),1,null);
//        Assert.assertTrue( leagueIndex!=0,"Failed! Not found league name "+event.getLeagueName()+" int Schedule list");
        int index = i;
        TextBox txtDate ;
        while (true){
            lblLeagueName = Label.xpath(tblEventBody.getxPathOfCell(1,1,i,null));
            if(!lblLeagueName.isDisplayed())
            {
                System.out.println("Failed! Not found league name "+event.getLeagueName()+" int Schedule list");
                return 0;
            }
            // if display the expect league name
            if(lblLeagueName.getText().equals(event.getLeagueName())){
                index = i + 1;
                while (true){
                    txtDate = TextBox.xpath(tblEventBody.getxPathOfCell(1,colDate,index,"input"));
                    if(!txtDate.isDisplayed())
                    {
                        break;
                    }
                    String time  = TextBox.xpath(tblEventBody.getxPathOfCell(1,colTimeEventTbl,index,"input")).getAttribute("value").trim();
                    String date = txtDate.getAttribute("value").trim();
                    String homeTeam = Label.xpath(tblEventBody.getxPathOfCell(1,colHomeTeamEventTbl,index,null)).getText();
                    String awayTeam = Label.xpath(tblEventBody.getxPathOfCell(1,colAwayTeamEventTbl,index,null)).getText();
                    if(homeTeam.equals(event.getHome()) && awayTeam.equals(event.getAway())){
                        if(date.equals( event.getEventDate()) || time.equals(event.getOpenTime())){
                          return index;
                        }
                        index = index + 1;
                        continue;
                    }
                }
            }
            i = index + 1;
        }
    }
    public boolean verifyEventInSchedulelist(Event event){
        int index = getEventIndex(event);
        if(index == 0)
        {
            System.out.println("Not fount the event in the list");
            return false;
        }
        TextBox txtDate  = TextBox.xpath(tblEventBody.getxPathOfCell(1,colDate,index,"input"));
        String time  = TextBox.xpath(tblEventBody.getxPathOfCell(1,colTimeEventTbl,index,"input")).getAttribute("value").trim();
        String date = txtDate.getAttribute("value").trim();
        Assert.assertEquals(date, event.getEventDate(),"Failed! Event Date is incorrect");
        Assert.assertEquals(time, event.getOpenTime(),"Failed!Time is incorrect");
        String homeTeam = Label.xpath(tblEventBody.getxPathOfCell(1,colHomeTeamEventTbl,index,null)).getText();
        String awayTeam = Label.xpath(tblEventBody.getxPathOfCell(1,colAwayTeamEventTbl,index,null)).getText();
        boolean isLive = CheckBox.xpath(tblEventBody.getxPathOfCell(1,colLiveEventTbl,index,"input")).isSelected();
        boolean isN = CheckBox.xpath(tblEventBody.getxPathOfCell(1,colLiveEventTbl,index,"input")).isSelected();
        String status = DropDownBox.xpath(tblEventBody.getxPathOfCell(1, colStatusEventTbl,index,"select")).getFirstSelectedOption();
        Assert.assertEquals(homeTeam,event.getHome(),"Failed! Home Team is incorrect");
        Assert.assertEquals(awayTeam,event.getAway(),"Failed! Home Team is incorrect");
        Assert.assertEquals(isLive,event.isLive(),"FAiled! Event is incorrect");
        Assert.assertEquals(isN, event.isN(),"Failed, N is incorrect");
        Assert.assertEquals(status,event.getEventStatus(),"Failed! Status is incorrect");
        return true;

    /*    int i = 1;
        Label lblLeagueName ;
//        int leagueIndex = tblEventBody.getRowIndexContainValue(event.getLeagueName(),1,null);
//        Assert.assertTrue( leagueIndex!=0,"Failed! Not found league name "+event.getLeagueName()+" int Schedule list");
        int index = i;
        TextBox txtDate ;
        boolean foundLeague = false;
        while (true){
            lblLeagueName = Label.xpath(tblEventBody.getxPathOfCell(1,1,i,null));
            if(!lblLeagueName.isDisplayed())
            {
                System.out.println("Failed! Not found league name "+event.getLeagueName()+" int Schedule list");
                return false;
            }
            // if display the expect league name
            if(lblLeagueName.getText().equals(event.getLeagueName())){
                index = i + 1;
                while (true){
                    txtDate = TextBox.xpath(tblEventBody.getxPathOfCell(1,colDate,index,"input"));
                    if(!txtDate.isDisplayed())
                    {
                        break;
                    }
                    String time  = TextBox.xpath(tblEventBody.getxPathOfCell(1,colTimeEventTbl,index,"input")).getAttribute("value").trim();
                    String date = txtDate.getAttribute("value").trim();
                    if(!date.equals( event.getEventDate()) || !time.equals(event.getOpenTime())){
                        index = index + 1;
                        continue;
                    }
                    // Only check the row with correct data
                    Assert.assertEquals(date, event.getEventDate(),"Failed! Event Date is incorrect");
                    Assert.assertEquals(time, event.getOpenTime(),"Failed!Time is incorrect");
                    String homeTeam = Label.xpath(tblEventBody.getxPathOfCell(1,colHomeTeamEventTbl,index,null)).getText();
                    String awayTeam = Label.xpath(tblEventBody.getxPathOfCell(1,colAwayTeamEventTbl,index,null)).getText();
                    boolean isLive = CheckBox.xpath(tblEventBody.getxPathOfCell(1,colLiveEventTbl,index,"input")).isSelected();
                    boolean isN = CheckBox.xpath(tblEventBody.getxPathOfCell(1,colLiveEventTbl,index,"input")).isSelected();
                    String status = DropDownBox.xpath(tblEventBody.getxPathOfCell(1, colStatusEventTbl,index,"select")).getFirstSelectedOption();
                    Assert.assertEquals(homeTeam,event.getHome(),"Failed! Home Team is incorrect");
                    Assert.assertEquals(awayTeam,event.getAway(),"Failed! Home Team is incorrect");
                    Assert.assertEquals(isLive,event.isLive(),"FAiled! Event is incorrect");
                    Assert.assertEquals(isN, event.isN(),"Failed, N is incorrect");
                    Assert.assertEquals(status,event.getEventStatus(),"Failed! Status is incorrect");
                    return true;
                }
            }
            if(foundLeague){
                return true;
            }
            i = index + 1;
        }*/
    }

}
