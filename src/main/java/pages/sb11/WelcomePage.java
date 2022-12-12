package pages.sb11;


import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import controls.sb11.AppArlertControl;
import objects.Event;
import pages.sb11.sport.EventSchedulePage;
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

    /**
     * This method create the event if League does not have any event
     * @param event
     */
    public Event createEvent(Event event){
        // Get all list Event of a League by APU and check exist event or not
        String date = DateUtils.formatDate(event.getEventDate(),"dd/MM/yyyy","yyyy-MM-dd");
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(date,date,event.getSportName(),event.getLeagueName());
        //Create in EventSchedulePage when not existing
        if(Objects.isNull(eventInfo)){
            createEventOfSport(event);
        }else {
            if(!eventInfo.getHome().equals(event.getHome())){
                createEventOfSport (event);
            }
        }
        return GetSoccerEventUtils.setEventID(event);
    }
    
    private void createEventOfSport(Event event){
        EventSchedulePage eventSchedulePage = navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.goToSport(event.getSportName());
        eventSchedulePage.showLeague(event.getLeagueName(),event.getEventDate());
        eventSchedulePage.addEvent(event);
    }
    
    public String getSuccessMessage(){
        return appArlertControl.getSuscessMessage();
    }
}
