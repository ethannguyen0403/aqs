package pages.sb11.trading;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Table;
import objects.Event;
import objects.Order;
import pages.sb11.trading.popup.BetListPopup;
import pages.sb11.trading.popup.SoccerBetSlipPopup;
import pages.sb11.trading.popup.SoccerSPBBetSlipPopup;

import java.util.List;
import java.util.Random;

public class SoccerBetEntryPage extends BetEntryPage {
    private Label lblTitle = Label.xpath("//app-bet-entry-soccer//app-common-header-sport//div[contains(@class,'main-box-header')]/div[1]/span");
    private Label lblGoto = Label.xpath("//app-bet-entry-soccer//app-common-header-sport//div[contains(@class,'main-box-header')]/div[2]/span");
    private TextBox txtAccCode = TextBox.id("account-code");
    private DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-bet-entry-soccer//div[contains(@class,'filter-body')]/div[1]//select");
    private DropDownBox ddpLeague = DropDownBox.id("league");
    private DropDownBox ddpSearchBy = DropDownBox.xpath("//select[@class='form-control']");
    private TextBox txtDate = TextBox.xpath("//app-bet-entry-soccer//input[@name='fromDate']");
    private Button btnShow = Button.xpath("//app-bet-entry-soccer//button[contains(@class,'btn-show')]");
    private DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    private Button btnMore = Button.xpath("");
    int totalCol =16;
    private int colTime = 1;
    private int colEvent = 2;
    private int colFTHdp = 3;
    private int colFTHome = 4;
    private int colFTAway = 5;
    private int colFTGoal = 6;
    private int colFTOver = 7;
    private int colFTUnder = 8;
    private int colMore =15;
    private int colSPB = 16;
    public Table tblEvent = Table.xpath("//app-bet-entry-soccer//table",totalCol);
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    /**
     * To show a league with expected info
     * @param companyUnit
     * @param date
     * @param league
     */
    public void showLeague(String companyUnit, String date, String league){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        String currentDate = txtDate.getAttribute("value");
        if(!date.isEmpty()){
            if(!date.equals(currentDate)){
                dtpDate.selectDate(date,"dd/MM/yyyy");
            }

        }
        if(!league.isEmpty()) {
            ddpLeague.selectByVisibleText(league);
        }
        btnShow.click();
        //waitPageLoad();
    }

    /**
     * To check the League display in the table
     * @param leagueName
     * @return
     */
    public boolean isLeagueExist(String leagueName){
        int i = 1;
        Label lblLeague;
        while (true){
            lblLeague = Label.xpath("//app-bet-entry-soccer//table/tbody/td");
            if(!lblLeague.isDisplayed()) {
                System.out.println("Can NOT found the league "+leagueName+" in the table");
                return false;
            }
            if(lblLeague.getText().equals(leagueName)){
                System.out.println("Found the league "+leagueName+" in the table");
                return true;
            }
            i = i +1;
        }
    }
    /**
     * To check the League display in the table
     * @param event
     * @return
     */
    public boolean isEventExist(Event event){
        Label lblTime;
        int i = 1;
            while (true) {
                lblTime = Label.xpath(tblEvent.getxPathOfCell(1,colEvent,i,null));
                if(!lblTime.isDisplayed()) {
                    System.out.println("Can NOT found the event "+event.getHome()+" & "+ event.getAway()+" in the table");
                    return false;
                }
                // Get the row contains the event time
                String time = lblTime.getText().trim();
                if (!time.equals(event.getOpenTime())) {
                    continue;
                } else {
                    String eventName = Label.xpath(tblEvent.getxPathOfCell(1, colEvent, i, null)).getText().trim();
                    if (eventName.equals(event.getHome())) {
                        return true;
                    }
                }
                i = i + 1;
            }

    }
    /**
     * To check the Leagure display in the table
     * @param eventName
     * @return
     */
    public int getEventRowIndex(String eventName){
        int i = 1;
        Label lblEvent;
        while (true){
            lblEvent = Label.xpath(tblEvent.getxPathOfCell(1,colEvent,i,null));
            if(!lblEvent.isDisplayed()) {
                System.out.println("Can NOT found the league "+eventName+" in the table");
                return 0;
            }
            if(lblEvent.getText().contains(eventName)){
                System.out.println("Found the league "+eventName+" in the table");
                return i;
            }
            i = i +1;
        }
    }

    /**
     * Click on .... of according event, Full time, half time and selection to open bet slip
     * @param accountCode
     * @param eventName
     * @param isFullTime
     * @param type
     * @return
     */
    public SoccerBetSlipPopup openBetSlip(String accountCode, String eventName, boolean isFullTime, String type){
        txtAccCode.type(accountCode);
        btnShow.click();
        waitPageLoad();
        int rowIndex = getEventRowIndex(eventName);
        int colIndex = defineColumn(isFullTime,type);
        tblEvent.getControlOfCell(1,colIndex, rowIndex,"span").click();
        return new SoccerBetSlipPopup();

    }

    /**
     * Click on + of according event to open More bet slip
     * @param accountCode
     * @param eventName
     * @return
     */
    public SoccerSPBBetSlipPopup openSPBBetSlip(String accountCode, String eventName){
        txtAccCode.type(accountCode);
        btnShow.click();
        int rowIndex = getEventRowIndex(eventName);
        int colIndex = colMore;
        tblEvent.getControlOfCell(1,colIndex, rowIndex,"span").click();
        return new SoccerSPBBetSlipPopup();

    }

    public void placeMoreBet(Order order, boolean issamodd, boolean minusOdd, boolean isPlace){
        SoccerSPBBetSlipPopup popup =openSPBBetSlip(order.getAccountCode(), order.getEvent().getHome());
        popup.placeMoreBet(order,issamodd,minusOdd,isPlace);
    }
    /**
     * Define colum HDP , HOME, AWAY .... by Fultime or  halftime
     * @param isFullTime
     * @param type
     * @return
     */
    private int defineColumn(boolean isFullTime, String type){
        int cellIndex = 0;
        if(!isFullTime)
            cellIndex = 6;
        String _type = type.toUpperCase();
        switch (_type){
            case "HDP":
                return colFTHdp + cellIndex;
            case "HOME":
                return colFTHome + cellIndex;
            case "AWAY":
                return colFTAway + cellIndex;
            case "GOAL":
                return colFTGoal + cellIndex;
            case "OVER":
                return colFTOver + cellIndex;
            case "UNDER":
                return colFTUnder + cellIndex;
            default:
                return 0;
        }
    }

    /**
     * According event, open bet slip and place bet
     * @param accountCode
     * @param eventName
     * @param isFullTime
     * @param type
     * @param lstOrder
     * @param isCopySPBPS7SameOdds
     * @param isCopySPBPS7MinusOdds
     * @param isPlaceBet
     */
    public void placeBet(String accountCode, String eventName, boolean isFullTime, String type,List<Order> lstOrder,boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
        Order order = lstOrder.get(0);
        boolean fullTime = false;
        if(order.getStage().equalsIgnoreCase("FT") || order.getStage().equalsIgnoreCase("Full Time"))
        {
            fullTime = true;
        }
        /*String type1;
        if(order.getMarketType().equalsIgnoreCase("HDP") && order.getSelection().equals(order.getHome())){
            type1 = "Home";
        }
        if(order.getMarketType().equalsIgnoreCase("HDP") && order.getSelection().equals(order.getAway())){
            type1 = "Away";
        }
        // Type = Over or Type = Under
        type1 = order.getMarketType();*/
        SoccerBetSlipPopup soccerBetSlipPopup = openBetSlip(accountCode,lstOrder.get(0).getSelection(),fullTime,type);
        soccerBetSlipPopup.placeMultiBet(lstOrder,isCopySPBPS7SameOdds,isCopySPBPS7MinusOdds,isPlaceBet);
    }
    
    /**
     * Open Bet Slip of according event
     * @param eventName
     * @return
     */
    public BetListPopup openBetList(String eventName){
        int rowIndex = getEventRowIndex(eventName);
        tblEvent.getControlOfCell(1,colSPB, rowIndex,"span").click();
        BetListPopup soccerBetListPopup = new BetListPopup();
        soccerBetListPopup.icRefresh.isDisplayed();
        return soccerBetListPopup;

    }
    public String getFirstLeague(){

        List<String> lstLeague = getListLeague();
        try {
            // 0 Select, 1 All => get league from index = 2
            return lstLeague.get(2);
        }catch (Exception e){
            System.out.println("There is NO League on day "+ txtDate.getText());
            return null;
        }
    }

    public String getRandomLeague(){
        List<String> lstLeague = getListLeague();
        try {
            Random rd = new Random();
            int lstNumb = lstLeague.size();
            String league = lstLeague.get(rd.nextInt(lstNumb-2)+2);
            // 0 Select, 1 All => get league from index = 2
            return league;
        }catch (Exception e){
            System.out.println("There is NO League on day "+ txtDate.getText());
            return null;
        }
    }

    /**
     * Get all the League in League dropdown
     * @return
     */
    public List<String> getListLeague(){
        ddpLeague.waitForElementToBePresent(ddpSearchBy.getLocator());
        return ddpLeague.getOptions();
    }

}
