package pages.sb11.sport;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import objects.Event;
import pages.sb11.WelcomePage;

public class CricketResultEntryPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    public Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    public Button btnShowLeagues = Button.xpath("//button[contains(text(),'Show Leagues')]");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    public DropDownBox ddpType = DropDownBox.id("type");
    public DropDownBox ddpLeague = DropDownBox.id("sport");
    public DropDownBox ddpOrderBy = DropDownBox.id("betType");
    public DropDownBox ddpStatus = DropDownBox.id("status");
    public TextBox txtDateTime = TextBox.id("date");
    public Label lblDate = Label.xpath("//label[contains(text(),'Date')]");
    public DateTimePicker dtpDateTime = DateTimePicker.xpath(txtDateTime,"//bs-datepicker-container");
    int colTime = 3;
    int colEvent = 4;
    int colStatus = 6;
    int colBatFirst = 7;
    int colRunHome = 8;
    int colWtksHome = 9;
    int colRunAway = 10;
    int colWtksAway = 11;
    int colHdp = 12;
    int colResult = 13;
    public Table tbResult = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[1]",13);
    public DropDownBox ddGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");
    public Button btnSubmit = Button.xpath("//button[contains(text(),'Submit')]");
    public Button btnRevert = Button.xpath("//button[contains(text(),'Revert')]");

    public void goToSport(String sport) {
        if(sport.equals("Soccer"))
            btnSoccer.click();
        if(sport.equals("Cricket"))
            btnCricket.click();
        waitSpinnerDisappeared();
    }

    public void filterResult(String type, String date, String league, String orderBy, String status, boolean isShow){
        ddpType.selectByVisibleText(type);
        waitSpinnerDisappeared();
        if(!date.isEmpty()){
            dtpDateTime.selectDate(date,"dd/MM/yyyy");
            waitSpinnerDisappeared();
            btnShowLeagues.click();
            waitSpinnerDisappeared();
            waitSpinnerDisappeared();
        }
        ddpLeague.selectByVisibleText(league);
        waitSpinnerDisappeared();
        ddpOrderBy.selectByVisibleText(orderBy);
        waitSpinnerDisappeared();
        ddpStatus.selectByVisibleText(status);
        waitSpinnerDisappeared();
        if (isShow){
            btnShow.click();
            waitSpinnerDisappeared();
        }
    }
    public int getEventIndex(Event event){
        Label lblEvent, lblTime;
        String eventExpect = event.getHome() + "\n-vs-\n" + event.getAway();
        int i = 2;
        while (i < 10) {
            lblEvent = Label.xpath(tbResult.getxPathOfCell(1,colEvent,i,"div[contains(@class,'d-inline-flex')]"));
            if (!lblEvent.isDisplayed()){
                System.out.println("Event is not display");
                return -1;
            }
            if (lblEvent.getText().equals(eventExpect)){
                return i;
            }
            i++;
        }
        return -1;
    }
    public void submitEvent(Event event, String status, String batFirst, String runHome, String wtksHome, String runAway, String wtksAway, String result){
        int eventIndex = getEventIndex(event);
        DropDownBox ddStatus = DropDownBox.xpath(tbResult.getxPathOfCell(1,colStatus,eventIndex,"select"));
        DropDownBox ddBatfirst = DropDownBox.xpath(tbResult.getxPathOfCell(1,colBatFirst,eventIndex,"select"));
        DropDownBox ddResult = DropDownBox.xpath(tbResult.getxPathOfCell(1,colResult,eventIndex,"select"));
        TextBox txbRunHome = TextBox.xpath(tbResult.getxPathOfCell(1,colRunHome,eventIndex,"input"));
        TextBox txbWtksHome = TextBox.xpath(tbResult.getxPathOfCell(1,colWtksHome,eventIndex,"input"));
        TextBox txbRunAway = TextBox.xpath(tbResult.getxPathOfCell(1,colRunAway,eventIndex,"input"));
        TextBox txbWtksAway = TextBox.xpath(tbResult.getxPathOfCell(1,colWtksAway,eventIndex,"input"));
        if (!status.isEmpty())
            ddStatus.selectByVisibleText(status);
        if (!batFirst.isEmpty())
            ddBatfirst.selectByVisibleText(batFirst);
        if (!runHome.isEmpty())
            txbRunHome.sendKeys(runHome);
            txbWtksHome.sendKeys(wtksHome);
            txbRunAway.sendKeys(runAway);
            txbWtksAway.sendKeys(wtksAway);
        if (!result.isEmpty())
            ddResult.selectByVisibleText(result);
        btnSubmit.click();
    }
}
