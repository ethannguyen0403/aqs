package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import pages.sb11.trading.popup.CricketBetSlipPopup;
import pages.sb11.trading.popup.BetListPopup;

import java.util.List;

public class CricketBetEntryPage extends BetEntryPage {
    private Label lblTitle = Label.xpath("//app-bet-entry-header//div[contains(@class,'main-box-header')]/div[1]/span");
    private Label lblGoto = Label.xpath("//app-bet-entry-header//div[contains(@class,'main-box-header')]/div[2]/span");
    public Label lblDate = Label.xpath("//label[text()='Date']");
    public Label lblAccountCode = Label.xpath("//label[contains(text(),'Account Code')]");
    private TextBox txtAccCode = TextBox.id("account-code");
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-bet-entry-cricket//app-common-header-sport//label[contains(text(),'Company Unit')]/following::select[1]");
    public DropDownBox ddpLeague = DropDownBox.id("league");
    public DropDownBox ddpSearchBy = DropDownBox.xpath("//select[@class='form-control']");
    private TextBox txtAccountCode = TextBox.id("account-code");
    private TextBox txtDate = TextBox.xpath("//app-bet-entry-cricket//input[@name='fromDate']");
    public Button btnShow = Button.xpath("//app-bet-entry-cricket//button[contains(@class,'btn-show')]");
    private DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    int totalCol =16;
    private int colTime = 1;
    private int colEvent = 2;
    private int colMOHome = 3;
    private int colMOAway = 4;
    private int colMODraw = 5;
    private int colHDPHome = 6;
    private int colHDPAway =7;
    private int colOUOver = 8;
    private int colOUUnder =9;
    private int colOEOdd =10;
    private int colOEEven = 11;
    private int colDNBHome = 12;
    private int colDNBAway = 13;
    private int colCPB = 14;

    public Table tblEvent = Table.xpath("//app-bet-entry-cricket//table",totalCol);
    //app-bet-entry-soccer

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
        String dateValue = txtDate.getAttribute("value");
        if(!date.isEmpty()){
            if(!date.equals(dateValue)){
                dtpDate.selectDate(date,"dd/MM/yyyy");
            }

        }
        ddpLeague.selectByVisibleText(league);
        btnShow.click();
        //waitPageLoad();
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
     * @param order
     * @return
     */
    public CricketBetSlipPopup openBetSlip(Order order){
        txtAccCode.type(order.getAccountCode());
        btnShow.click();
        int rowIndex = getEventRowIndex(order.getHome());
        String selection = "DRAW";
        if(order.getSelection().equals(order.getHome()))
            selection= "HOME";
        if(order.getSelection().equals(order.getAway()))
            selection="AWAY";
        int colIndex = defineColumn(order.getMarketType(), selection);
        tblEvent.getControlOfCell(1,colIndex, rowIndex,"span").click();
        return new CricketBetSlipPopup();

    }

    /**
     * Define colum HDP , HOME, AWAY .... by Fultime or  halftime
     * @param marketType
     * @param selection
     * @return
     */
    private int defineColumn(String marketType,String selection){
        String _marketType = marketType.toUpperCase();
        String _selection = selection.toUpperCase();
        switch (_marketType){
            case "1x2":
                switch (_selection){
                    case "HOME":
                        return colMOHome;
                    case "AWAY":
                        return colMOAway;
                    case "DRAW":
                        return colMODraw;
                }
            case "MATCH-HDP":
                switch (_selection){
                    case "HOME":
                        return colHDPHome;
                    case "AWAY":
                        return colHDPAway;
                }
            case "OU":
                switch (_selection){
                    case "OVER":
                        return colOUOver;
                    case "UNDER":
                        return colOUUnder;
                }
            case "OE":
                switch (_selection){
                    case "ODD":
                        return colOEOdd;
                    case "EVEN":
                        return colOEEven;
                }
            case "DNB":
                switch (_selection){
                    case "HOME":
                        return colDNBHome;
                    case "AWAY":
                        return colDNBAway;
                }
            default:
                return 0;
        }
    }

     /**
         * According event, open bet slip and place bet
         * @param order
         * @param isPlaceBet
         */
    public void placeBet( Order order, boolean isPlaceBet){
        CricketBetSlipPopup cricketBetSlipPopup = openBetSlip(order);
        cricketBetSlipPopup.placeBet(order,isPlaceBet);
    }

    /**
     * Open Bet Slip of according event
     * @param eventName
     * @return
     */
    public BetListPopup openBetList(String eventName){
        int rowIndex = getEventRowIndex(eventName);
        tblEvent.getControlOfCell(1,colCPB, rowIndex,"span").click();
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

    /**
     * Get all the League in League dropdown
     * @return
     */
    public List<String> getListLeague(){
        return ddpLeague.getOptions();
    }
}
