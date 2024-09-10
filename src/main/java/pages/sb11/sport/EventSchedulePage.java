package pages.sb11.sport;

import com.paltech.element.common.*;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.DropDownList;
import controls.Table;
import objects.Event;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;

public class EventSchedulePage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    TextBox txtLeague = TextBox.xpath("//div[@role='combobox']/input");
    DropDownList ddpLeague = DropDownList.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-header']//div[@class='filter-league']//ng-select","//div//span[contains(@class,'ng-option-label')]");
    TextBox txtDateTime = TextBox.xpath("//span[text()='Date Time']//parent::div//following-sibling::div/input");
    DateTimePicker dtpDateTime = DateTimePicker.xpath(txtDateTime,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-header']//div[@class='card-body']//button[contains(@class,'btn-show')]");
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
    Table tblLeague = Table.xpath("//app-schedule-list//table[contains(@class,'tbl-create-event-schedule')]",3);
    Button btnAdd = Button.xpath("//div[contains(@class,'modal-footer')]//button[contains(@class,'btn')][1]");
    Button btnSubmit = Button.xpath("//div[contains(@class,'modal-footer')]//button[contains(@class,'btn') and text()='Submit']");
    TextBox txtEventNumber = TextBox.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//input");
    Label lblClear = Label.xpath("//app-event-schedule-entry//app-league-entry//div[@class='league-body']//div[contains(@class,'modal-footer')]//span[contains(@class,'text-clear')]");
    RadioButton rbHome = RadioButton.xpath("//app-schedule-list//div[@class='league-header']//input[@id='homeTeamCheck']");
    RadioButton rbAway = RadioButton.xpath("//app-schedule-list//div[@class='league-header']//input[@id='homeTeamCheck']");
    Link lnkShowLeague = Link.xpath("//app-schedule-list//div[@class='league-header']//span[contains(@class,'text-show-league')]");
    TextBox txtScheduleListDateTime = TextBox.xpath("//app-schedule-list//div[@class='league-header']//input[contains(@class,'league-date')]");
    TextBox txtTeam = TextBox.xpath("//app-schedule-list//div[@class='card-body league-card-body']//input[contains(@class,'team-name-input')]");
    DateTimePicker dtpScheduleListDateTime= DateTimePicker.xpath(txtScheduleListDateTime,"//bs-days-calendar-view");
    public Button btnShowSchedule = Button.xpath("//app-schedule-list//button[contains(@class,'btn-show')]");
    int totalEventScheduleColumn = 11;
    int totalLeagueColumn = 8;
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
    public Table tblEventBody = Table.xpath("//app-schedule-list//table",totalEventScheduleColumn);
    public Table tblLeagueBody = Table.xpath("//app-league-entry//table",totalLeagueColumn);
    DropDownBox ddpSport = DropDownBox.xpath("//app-league-entry//div[@class='league-header']//div[contains(@class,'card-header')]//select");
    public Label lblLeagueEntry = Label.xpath("//app-league-entry//div[contains(@class,'card-header')]//span[1]");
    public Label lblScheduleList = Label.xpath("//app-schedule-list//div[contains(@class,'card-header')]//span[1]");
    public Label lblLeague = Label.xpath("//span[text()='League']");
    public Label lblDateTime = Label.xpath("//span[text()='Date Time']");
    public Label lblHome = Label.xpath("//label[text()='Home']");
    public Label lblAway = Label.xpath("//label[text()='Away']");
    public Label lblTeam = Label.xpath("//span[text()='Team']");
    public Button btnShowLeague = Button.xpath("//span[text()='Show League']");
    public void goToSport(String sport){
      ddpSport.selectByVisibleContainsText(sport);
      waitSpinnerDisappeared();
    }

    public void showLeague(String league, String date){
        //wait for league dropdown upload
        waitSpinnerDisappeared();
        selectLeague(league);
        waitSpinnerDisappeared();
        if(!date.isEmpty()){
            dtpDateTime.selectDate(date,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public void selectLeague(String league){
        txtLeague.waitForControlInvisible();
        txtLeague.sendKeys(league);
        waitSpinnerDisappeared();
        Label lblLeague = Label.xpath(String.format("//div[contains(@class,'ng-option')]"));
        if (lblLeague.isDisplayed()){
            lblLeague.click();
            waitSpinnerDisappeared();
            return;
        }
        System.out.println("The league is not displayed");
    }

    public void selectLeagueInScheduleList(String league){
        //To wait page completely loaded the list league
        lnkShowLeague.click();
        waitPageLoad();
        int index = getLeagueRowIndex(league);
        tblLeague.getControlOfCell(1,2, index,"input").jsClick();
    }

    /**
     * To check the league display in the table
     * @param leagueName
     * @return
     */
    public int getLeagueRowIndex(String leagueName){
        int i = 1;
        Label lblLeague;
        while (true){
            lblLeague = Label.xpath(tblLeague.getxPathOfCell(1,3,i,null));
            lblLeague.scrollToThisControl(true);
            if(!lblLeague.isDisplayed()) {
                System.out.println("Can NOT found the league "+leagueName+" in the table");
                return 0;
            }
            if(lblLeague.getText().contains(leagueName)){
                System.out.println("Found the league "+leagueName+" in the table");
                return i;
            }
            i = i +1;
        }
    }
    public void addEvent(Event event){
        fillEventInfo(event,1);
        btnSubmit.click();
        waitSpinnerDisappeared();
    }
    public void deleteEvent(Event event){
        goToSport(event.getSportName());
        showScheduleList(event.getLeagueName(),true, event.getHome(),event.getEventDate());
        int index = getEventIndex(event);
        if(index == 0){
            System.out.println("Not found the event in the list for deleting");
            return;
        }
        Icon.xpath(tblEventBody.getxPathOfCell(1,colActionEventTbl,index,"i[contains(@class,'fa-times-circle')]")).click();
        ConfirmPopup popup = new ConfirmPopup();
        popup.confirm(true);
    }

    private void fillEventInfo(Event event, int rowIndex){
        waitSpinnerDisappeared();
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

    public void showScheduleList(String league, boolean isHomeTeam, String teamName, String date){

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
        selectLeagueInScheduleList(league);
        btnShowSchedule.jsClick();
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

    public void verifyUI() {
        System.out.println("Event Schedule table: League, Date Time and Show button");
        Assert.assertEquals(lblLeague.getText(),"League","Failed! League dropdown is not displayed!");
        Assert.assertEquals(lblDateTime.getText(),"Date Time","Failed! Date Time datetime picker is not displayed!");
        Assert.assertEquals(btnShow.getText(),"SHOW","Failed! Show button is not displayed!");
        System.out.println("Schedule List table: Home, Away, Team, Show League, Datetime and Show button");
        Assert.assertEquals(lblHome.getText(),"Home","Failed! Home radio button is not displayed!");
        Assert.assertEquals(lblAway.getText(),"Away","Failed! Away radio button is not displayed!");
        Assert.assertEquals(lblTeam.getText(),"Team","Failed! Team searchbox is not displayed!");
        Assert.assertEquals(btnShowLeague.getText(),"Show League","Failed! Show League button is not displayed!");
        Assert.assertEquals(btnShowSchedule.getText(),"SHOW","Failed! Show button is not displayed!");
        System.out.println("Event Schedule and Schedule List table header columns are correctly display");
        Assert.assertEquals(tblLeagueBody.getHeaderNameOfRows(), SBPConstants.EventSchedule.TABLE_HEADER_LEAGUE_LIST,"FAILED! League table header is incorrect display");
        Assert.assertEquals(tblEventBody.getHeaderNameOfRows(), SBPConstants.EventSchedule.TABLE_HEADER_SCHEDULE_LIST,"FAILED! Schedule table header is incorrect display");
    }
}
