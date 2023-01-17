package pages.sb11;


import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import controls.sb11.AppArlertControl;
import objects.Event;
import pages.sb11.sport.EventSchedulePage;
import pages.sb11.yopmail.YopmailMailBoxPage;
import pages.sb11.yopmail.YopmailPage;
import utils.sb11.GetSoccerEventUtils;
import java.util.ArrayList;
import java.util.List;
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
        lblSpin.waitForControlInvisible();
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
    public List<String> getListSuccessMessage(){
        return appArlertControl.getListSuccessMessage();
    }

    public void waitSpinnerDisappeared() throws InterruptedException {
        while(lblSpin.isDisplayed()) {
            lblSpin.waitForControlInvisible();
        }
    }

    private YopmailPage navigatetoYopmail(String url){
        DriverManager.getDriver().newTab();
        DriverManager.getDriver().get(url);
        return new YopmailPage();
    }
    public List<ArrayList<String>> getFirstActiveMailBox(String url, String mailAđrress){
        YopmailPage yopmailPage = navigatetoYopmail(url);
        YopmailMailBoxPage  yopmailMailBoxPage = yopmailPage.navigateMailBox(mailAđrress);
        List<ArrayList<String>> contentInfoLst = new ArrayList<>();
        contentInfoLst.add(yopmailMailBoxPage.getInfo());
        contentInfoLst.add(yopmailMailBoxPage.getbetListHeader());
        for (ArrayList<String> row:yopmailMailBoxPage.getbetListInfo()
             ) {
            contentInfoLst.add(row);
        }
        return contentInfoLst;
    }


}
