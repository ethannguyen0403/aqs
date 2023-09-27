package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.controls.BetByGroupTableControl;
import pages.sb11.soccer.popup.bbg.BBGLastDaysPerformacesPopup;
import pages.sb11.soccer.popup.bbg.BetByTeamPricePopup;

public class BBGPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
    private String xPathBetByGroupTableControl = "//app-bets-by-group-table//div[contains(@class,'table-contain')]";
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpReportType = DropDownBox.xpath("//div[contains(text(),'Report Type')]//following::select[1]");
    public DropDownBox ddpSmartType = DropDownBox.xpath("//div[contains(text(),'Smart Type')]//following::select[1]");
    public DropDownBox ddpSport = DropDownBox.xpath("//div[contains(text(),'Sport')]//following::select[1]");
    public DropDownBox ddpWinLose = DropDownBox.xpath("//div[contains(text(),'Win/Lose')]//following::select[1]");
    public DropDownBox ddpStake = DropDownBox.xpath("//div[contains(text(),'Stake')]//following::select[1]");
    public DropDownBox ddpCurrency = DropDownBox.xpath("//div[contains(text(),'Currency')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");
    public Label lblFromDate = Label.xpath("//div[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//div[contains(text(),'To Date')]");

    public Button btnShowBetTypes = Button.xpath("//div[contains(text(),'Show Bet Types')]");
    public Button btnShowLeagues = Button.xpath("//div[contains(text(),'Show Leagues')]");
    public Button btnShowGroup = Button.xpath("//div[contains(text(),'Show Groups')]");
    public Button btnShowEvent = Button.xpath("//div[contains(text(),'Show Events')]");
    public Button btnResetAllFilter = Button.xpath("//span[contains(text(),'Reset All Filters')]");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    BetByGroupTableControl  firstBetByGroupTableControl = BetByGroupTableControl.xpath(String.format("%s[%d]", xPathBetByGroupTableControl, 1));

    int totalColumnNumber = 13;
    public Table tblBBG = Table.xpath("//app-bbg//table",totalColumnNumber);

    public void filter(String sport, String companyUnit, String smartType, String reportType, String fromDate, String toDate, String stake, String currency){
        if (!companyUnit.isEmpty())
            ddpCompanyUnit.selectByVisibleText(companyUnit);
        if(!sport.isEmpty())
            ddpSport.selectByVisibleText(sport);
        if(!smartType.isEmpty())
            ddpSmartType.selectByVisibleText(smartType);
        if(!reportType.isEmpty())
            ddpReportType.selectByVisibleText(reportType);
        String currentDate = txtFromDate.getAttribute("value");
        if(!fromDate.isEmpty())
            if(!currentDate.equals(fromDate))
                dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        currentDate = txtToDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        waitSpinnerDisappeared();
        if(!stake.isEmpty())
            ddpStake.selectByVisibleText(stake);
        if(!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public BetByTeamPricePopup clickPrice(String account){
        BetByGroupTableControl bbgTable = findAccount(account);
        bbgTable.clickPriceColumn(account);
        return new BetByTeamPricePopup();
    }
    public String getFristSmartGroupName() {
       return firstBetByGroupTableControl.getSmartGroupName();
    }
    public String  getFristSmartGroupCurrency() {
        return firstBetByGroupTableControl.getSmartGroupCurr();
    }

    public BetByTeamPricePopup clickFirstPriceCell(){
       firstBetByGroupTableControl.clickPriceColumn("");
       DriverManager.getDriver().switchToWindow();
       return new BetByTeamPricePopup();
    }
    public BBGLastDaysPerformacesPopup clickFirstTraderCell(){
        firstBetByGroupTableControl.clickTraderColumn("");
        DriverManager.getDriver().switchToWindow();
        return new BBGLastDaysPerformacesPopup();
    }
    private BetByGroupTableControl findAccount(String account){
        int i = 1;
        BetByGroupTableControl betByGroupTableControl;
        while (true){
            betByGroupTableControl = BetByGroupTableControl.xpath(String.format("%s[%d]", xPathBetByGroupTableControl, i));
            if(!betByGroupTableControl.isDisplayed())
                return null;
            if(betByGroupTableControl.getRowIndexContainAccount(account)!= 0)
                return betByGroupTableControl;
            i = i+1;
        }
    }
}
