package pages.sb11;


import com.paltech.element.common.Label;
import controls.sb11.AppArlertControl;
import objects.Event;
import pages.sb11.sport.EventSchedulePage;
import pages.sb11.trading.BetEntryPage;
import utils.sb11.GetSoccerEventUtils;

import java.util.Objects;

import static common.SBPConstants.*;

public class WelcomePage extends Header{
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");
    private Label lblTitle = Label.xpath("//span[@class='text-white']");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public AppArlertControl appArlertControl = AppArlertControl.xpath("//app-alert//div[@class='message-box']");

    public void waitPageLoad(){
        lblSpin.isDisplayed(3);
    }

    public Event createCricketEvent(String fromDate, String toDate, String sport, String league){
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(fromDate,toDate,sport,league);
        if(Objects.isNull(eventInfo)){
            EventSchedulePage eventSchedulePage = navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
            //TODO: add function create event for leage
        }
        return eventInfo;
    }

    public String getSuccessMessage(){
        return appArlertControl.getSuscessMessage();
    }
}
