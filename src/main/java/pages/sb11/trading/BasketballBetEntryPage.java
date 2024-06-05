package pages.sb11.trading;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Event;
import objects.Order;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.BasketballBetSlipPopup;

import java.util.List;
import java.util.Locale;

public class BasketballBetEntryPage extends WelcomePage {

    public Label lblAccountCode = Label.xpath("//label[contains(text(),'Account Code')]");
    private TextBox txtAccCode = TextBox.id("account-code");
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-bet-entry-header//label[contains(text(),'Company Unit')]/following-sibling::select[1]");
    public DropDownBox ddpLeague = DropDownBox.id("league");
    public DropDownBox ddpSearchBy = DropDownBox.xpath("//select[@class='form-control']");
    private TextBox txtDate = TextBox.xpath("//app-bet-entry-header//input[@name='fromDate']");
    DateTimePicker dtpDate = DateTimePicker.xpath(txtDate, "//bs-datepicker-container");
    public Button btnShow = Button.xpath("//app-bet-entry-header//button[contains(@class,'btn-show')]");

    int colEvent = 2;
    Table tblBasketball = Table.xpath("//app-bet-entry-table//table", 13);

    public void showLeague(String companyUnit, String date, String league, String accountCode){
        ddpCompanyUnit.selectByVisibleText(companyUnit);

        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
            waitPageLoad();
        }
        if(!league.isEmpty()) {
            BaseElement ddpLeagueDropdown = new TextBox(ddpLeague.getLocator());
            ddpLeagueDropdown.doubleClick();
            ddpLeague.selectByVisibleText(league);
            waitPageLoad();

        }
        if (!accountCode.isEmpty())
            txtAccCode.sendKeys(accountCode);
        btnShow.click();
        waitPageLoad();
    }

    public int findEventRowIndex(String eventName){
        int i = 1;
        Label lblEvent;
        while (true){
            lblEvent = Label.xpath(tblBasketball.getxPathOfCell(1,colEvent,i,null));
            if(!lblEvent.isDisplayed()) {
                System.out.println("Can NOT found the league "+eventName+" in the table");
                return -1;
            }
            if(lblEvent.getText().contains(eventName)){
                System.out.println("Found the league "+eventName+" in the table");
                return i;
            }
            i = i +1;
        }
    }

    /**
    @param teamSelection name of team selection (e.g: Home, Away, Over, Under....)
    */
    public int findColumnIndexOfBetType(String typeName, String teamSelection){
        List<String> headerList = tblBasketball.getHeaderNameOfRows();
        int indexBetType = -1;
       for(int i=0; i< headerList.size(); i++){
            if(headerList.get(i).equalsIgnoreCase(typeName)){
                indexBetType = i;
                break;
            }
       }
       switch (typeName.toUpperCase()){
           case "1X2":
           case "HDP":
               return  teamSelection.equalsIgnoreCase("Home") ? indexBetType + 1 : indexBetType + 2;
           case "TOTAL POINTS":
               return  teamSelection.equalsIgnoreCase("Over") ? indexBetType + 1 : indexBetType + 2;
           default:
               System.out.println("NOT Found value in Basketball table");
               return -1;
       }
    }

    /**
     * @param eventName name of event usually name of Home team or Away team
      */
    public BasketballBetSlipPopup openBetSlip(String typeName, String teamSelection, String eventName){
        int indexColLabel = findColumnIndexOfBetType(typeName, teamSelection);
        int indexRowEven = findEventRowIndex(eventName);
        tblBasketball.getControlOfCell(1, indexColLabel, indexRowEven,"span").click();
        waitPageLoad();
        return new BasketballBetSlipPopup();
    }

    public void placeBet(Order order, String typeName, String teamSelection) {
        BasketballBetSlipPopup betSlipPopup = this.openBetSlip(typeName, teamSelection, order.getEvent().getHome());
        betSlipPopup.fillBetSlipInfo1x2(order);
        betSlipPopup.btnPlaceBet.click();
    }
}
