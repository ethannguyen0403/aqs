package pages.sb11;


import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import controls.sb11.AppArlertControl;
import objects.Event;
import objects.Order;
import pages.sb11.popup.ChangePasswordPopup;
import pages.sb11.sport.EventSchedulePage;
import pages.sb11.yopmail.YopmailMailBoxPage;
import pages.sb11.yopmail.YopmailPage;
import utils.sb11.trading.BetEntrytUtils;
import utils.sb11.trading.GetSoccerEventUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static common.SBPConstants.*;

public class WelcomePage extends Header{

    private Label lblTitle = Label.xpath("//span[@class='text-white']");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public AppArlertControl appArlertControl = AppArlertControl.xpath("//app-alert//div[@class='message-box']");
    public Label lblWelcome = Label.xpath("//app-welcome-page//h2[contains(text(),'Welcome to')]");


    /**
     * This method create the event if League does not have any event
     * @param event
     */
    public Event createEvent(Event event){
        // to add wait page load to call the end point GetSoccerEventUtils.getFirstEvent
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Get all list Event of a League by APU and check exist event or not
        String date = DateUtils.formatDate(event.getEventDate(),"dd/MM/yyyy","yyyy-MM-dd");
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(date,date,event.getSportName(),event.getLeagueName());
        //Create in EventSchedulePage when not existing
        if(Objects.isNull(eventInfo)){
            createEventOfSport(event);
        }else {
            if(!eventInfo.getHome().equals(event.getHome())){
                createEventOfSport(event);
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

    public void waitSpinnerDisappeared() {
        while(lblSpin.isDisplayed()) {
            lblSpin.waitForControlInvisible();
        }
    }

    private YopmailPage navigatetoYopmail(String url){
        DriverManager.getDriver().newTab();
        DriverManager.getDriver().switchToWindow();
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

    public String changePassword(String oldPassword, String newPassword, String confirmPassword, boolean isClose){
        ChangePasswordPopup changePasswordPopup = openChangePasswordPopup();
        return changePasswordPopup.changePassword(oldPassword,newPassword,confirmPassword,isClose);
    }
    public String getDownloadPath(){
        return DriverManager.getDriver().getDriverSetting().getDownloadPath();
    }

    public List<Order> placeBetAPI(String sport, String date, Event event, String accountCode, String marketName, String marketType, String selection, String stage, double price, double handicap,
                                   String oddType, double requireStake, String betType, boolean isLive,String winlose){
        String dateAPI = DateUtils.formatDate(date,"dd/MM/yyyy","yyyy-MM-dd");
        event.setEventDate(dateAPI);
        boolean isWinLose = winlose.isEmpty() ? false : true;
        return placeBetAPI(sport, event, accountCode, marketName, marketType, selection, stage, price, handicap, oddType, requireStake, betType, isLive, isWinLose, winlose);
    }
    public List<Order> placeBetAPI(String sport, String date, boolean randomEvent, String accountCode, String marketName, String marketType, String selection, String stage, double price, double handicap,
                                   String oddType, double requireStake, String betType, boolean isLive,String winlose){
        String dateAPI = DateUtils.formatDate(date,"dd/MM/yyyy","yyyy-MM-dd");
        Event event;
        if (randomEvent) {
            event = GetSoccerEventUtils.getRandomEvent(dateAPI, dateAPI, sport, "");
        } else {
            event = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        }
        event.setEventDate(dateAPI);
        if (selection.equals("Home")){
            selection = event.getHome();
        } else if (selection.equals("Away")){
            selection = event.getAway();
        }
        boolean isWinLose = winlose.isEmpty() ? false : true;
        return placeBetAPI(sport, event, accountCode, marketName, marketType, selection, stage, price, handicap, oddType, requireStake, betType, isLive, isWinLose, winlose);
    }
    private List<Order> placeBetAPI(String sport, Event event, String accountCode, String marketName, String marketType, String selection, String stage, double price, double handicap,
                                    String oddType, double requireStake, String betType, boolean isLive,boolean isWinLose,String winlose){
        Order order = null;
        if (!sport.equals("Cricket")){
            order = new Order.Builder().event(event).accountCode(accountCode).marketName(marketName).marketType(marketType).selection(selection).sport(sport).stage(stage).price(price)
                    .odds(price).hdpPoint(handicap).handicap(handicap).oddType(oddType).requireStake(requireStake).betType(betType).isLive(isLive).build();
        } else if (sport.equals("Cricket")){
            order = new Order.Builder().event(event).accountCode(accountCode).marketName(marketName).marketType(marketType).selection(selection).sport(sport)
                    .stage(stage).createDate(DateUtils.getDate(0, "yyyy-MM-dd", "GMT +8")).eventDate(event.getEventDate() + " 23:59:00").odds(price).price(price).handicap(handicap).oddType(oddType).requireStake(requireStake).betType(betType)
                    .isWinLose(isWinLose).build();
        }
        if (!winlose.isEmpty()){
            order.setWinLose(Double.valueOf(winlose));
        }
        BetEntrytUtils.placeBetAPI(order);
        List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(order);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        //wait for order update
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return lstOrder;
    }

    public String pickFinancialYear(String financialYear) {
        if (DateUtils.getDate(0,"MM",GMT_7).equals("08")){
            String previousYear = String.valueOf(Integer.valueOf(financialYear.split(" ")[1].split("-")[0]) - 1);
            String lastYear = String.valueOf(Integer.valueOf(financialYear.split(" ")[1].split("-")[1]) - 1);
            return String.format("Year %s-%s",previousYear,lastYear);
        }
        return financialYear;
    }
    public String getBeginDateOfFinanYear(String financialYear){
        String yearBegin = financialYear.replace("Year ","").split("-")[0];
        return String.format("01/08/%s",yearBegin);
    }
}
