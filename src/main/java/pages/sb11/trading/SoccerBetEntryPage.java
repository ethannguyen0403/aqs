package pages.sb11.trading;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import pages.sb11.Header;
import pages.sb11.trading.popup.SoccerBetListPopup;
import pages.sb11.trading.popup.SoccerBetSlipPopup;

import java.util.List;

public class SoccerBetEntryPage extends BetEntryPage {
    private Label lblTitle = Label.xpath("//app-bet-entry-soccer//app-common-header-sport//div[contains(@class,'main-box-header')]/div[1]/span");
    private Label lblGoto = Label.xpath("//app-bet-entry-soccer//app-common-header-sport//div[contains(@class,'main-box-header')]/div[2]/span");
    private DropDownBox ddbSport = DropDownBox.id("navigate-page");
    private DropDownBox ddpCompanyUnit = DropDownBox.id("company-unit");
    private DropDownBox ddpLeague = DropDownBox.id("league");
    private DropDownBox ddpSearchBy = DropDownBox.xpath("//select[@class='form-control']");
    private TextBox txtAccountCode = TextBox.id("account-code");
    private TextBox txtDate = TextBox.xpath("//input[@name='fromDate']");
    private DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    int totalCol =14;
    private int colTime = 1;
    private int colEvent = 2;
    private int colFTHdp = 3;
    private int colFTHome = 4;
    private int colFTAway = 5;
    private int colFTGoal = 6;
    private int colFTOver = 7;
    private int colFTUnder = 8;
    private int colMore =13;
    private int colSPB = 14;
    public Table tblEvent = Table.xpath("//app-bet-entry-soccer//table",totalCol);
    public Label lblSuccessPlaceBetMessage = Label.xpath("//app-bet-entry-soccer//div[@class='message-box']div[contains(@class,'alert-success')]");
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
        dtpDate.selectDate(date,"dd/MM/yyyy");
        ddpLeague.selectByVisibleText(league);
        btnShow.click();
        waitPageLoad();
    }

    /**
     * To check the Leagure display in the table
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
     * To check the Leagure display in the table
     * @param eventName
     * @return
     */
    public int getEventRowIndex(String eventName){
        int i = 1;
        Label lblEvent;
        while (true){
            lblEvent = Label.xpath(tblEvent.getxPathOfCell(1,1,i,null));
            if(!lblEvent.isDisplayed()) {
                System.out.println("Can NOT found the league "+eventName+" in the table");
                return 0;
            }
            if(lblEvent.getText().equals(eventName)){
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
        txtAccCode.sendKeys(accountCode);
        int rowIndex = getEventRowIndex(eventName);
        int colIndex = defineColumn(isFullTime,type);
        tblEvent.getControlOfCell(1,colIndex, rowIndex,"span").click();
        return new SoccerBetSlipPopup();

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
    public void placeBet(String accountCode, String eventName, boolean isFullTime, String type, List<Order> lstOrder,boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
         SoccerBetSlipPopup soccerBetSlipPopup = openBetSlip(accountCode,eventName,isFullTime,type);
         soccerBetSlipPopup.placebet(lstOrder,isCopySPBPS7SameOdds,isCopySPBPS7MinusOdds,isPlaceBet);
    }

    /**
     * Open Bet Slip of according event
     * @param eventName
     * @return
     */
    public SoccerBetListPopup openBetList(String eventName){
        int rowIndex = getEventRowIndex(eventName);
        tblEvent.getControlOfCell(1,colSPB, rowIndex,"span").click();
        SoccerBetListPopup soccerBetListPopup = new SoccerBetListPopup();
        soccerBetListPopup.icRefresh.isDisplayed();
        return soccerBetListPopup;

    }

}
