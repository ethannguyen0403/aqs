package pages.sb11.trading;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.TennisBetSlipPopup;

import java.util.List;

public class TennisBetEntryPage extends WelcomePage {
    public Label lblDate = Label.xpath("//label[text()='Date']");
    public Label lblAccountCode = Label.xpath("//label[contains(text(),'Account Code')]");
    private TextBox txtAccCode = TextBox.id("account-code");
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-bet-entry-header//label[contains(text(),'Company Unit')]/following::select[1]");
    public DropDownBox ddpLeague = DropDownBox.id("league");
    public DropDownBox ddpSearchBy = DropDownBox.xpath("//select[@class='form-control']");
    public TextBox txtAccountCode = TextBox.id("account-code");
    private TextBox txtDate = TextBox.xpath("//app-bet-entry-header//input[@name='fromDate']");
    public Button btnShow = Button.xpath("//app-bet-entry-header//button[contains(@class,'btn-show')]");
    private DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");

    int totalCol =16;
    private int colTime = 1;
    private int colEvent = 2;
    public Table tblEvent = Table.xpath("//app-bet-entry-table//table",totalCol);


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
    /**
     * Find column index of Home and Away
     * @param teamSelection include name Home or Away
     * @return
     */
    public int findColumnIndexOfBetType(String teamSelection) {
        List<String> headerList = tblEvent.getHeaderNameOfRows();
        int indexTeamPlaceBet = -1;
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.get(i).equalsIgnoreCase("1x2")) {
                indexTeamPlaceBet = i;
                break;
            }
        }
        if (indexTeamPlaceBet == -1) {
            System.out.println("NOT found selection: " + teamSelection);
            return indexTeamPlaceBet;
        }
        return teamSelection.equalsIgnoreCase("Home") ? indexTeamPlaceBet + 1 : indexTeamPlaceBet + 2;
    }

    public int findEventRowIndex(String eventName){
        int i = 1;
        Label lblEvent;
        while (true){
            lblEvent = Label.xpath(tblEvent.getxPathOfCell(1,colEvent,i,null));
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

    public TennisBetSlipPopup openBetSlip(String teamName, String eventName){
        int indexColLabel = findColumnIndexOfBetType(teamName);
        int indexRowEven = findEventRowIndex(eventName);
        tblEvent.getControlOfCell(1, indexColLabel, indexRowEven,"span").click();
        waitPageLoad();
        return new TennisBetSlipPopup();
    }
    public void placeBet(Order order, String teamName) {
        TennisBetSlipPopup betSlipPopup = this.openBetSlip(teamName, order.getEvent().getHome());
        betSlipPopup.fillBetSlipInfo1x2(order);
        betSlipPopup.btnPlaceBet.click();
    }

}
