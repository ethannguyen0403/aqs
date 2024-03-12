package pages.sb11.soccer;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Row;
import controls.Table;
import org.json.JSONObject;
import pages.sb11.WelcomePage;
import pages.sb11.soccer.controls.BetByGroupTableControl;
import pages.sb11.soccer.popup.bbg.BBGLastDaysPerformacesPopup;
import pages.sb11.soccer.popup.bbg.BetByTeamPricePopup;
import utils.sb11.AccountSearchUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Label lblFirstAccount = Label.xpath("(//app-bbg//table//tbody//tr//td[2])[1]");
    public Button btnSetSelection = Button.xpath("//button[contains(text(),'Set Selection')]");

    public void filter(String sport, String companyUnit, String smartType, String reportType, String fromDate, String toDate, String stake, String currency){
        if (!companyUnit.isEmpty())
            ddpCompanyUnit.selectByVisibleText(companyUnit);
        if(!sport.isEmpty())
            ddpSport.selectByVisibleText(sport);
        if(!smartType.isEmpty())
            ddpSmartType.selectByVisibleText(smartType);
        if(!reportType.isEmpty())
            ddpReportType.selectByVisibleText(reportType);
        if(!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if(!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if(!stake.isEmpty())
            ddpStake.selectByVisibleText(stake);
        if(!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public void filterGroups(String groupCode){
        btnShowGroup.click();
        waitSpinnerDisappeared();
        CheckBox cbGroup = CheckBox.xpath("//div[contains(@class,'card-columns')]//span[text()='"+groupCode+"']//preceding::input[1]");
        cbGroup.jsClick();
        btnSetSelection.click();
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public void filterBetTypes(String type){
        btnShowBetTypes.click();
        waitSpinnerDisappeared();
        CheckBox cbGroup = CheckBox.xpath("//div[contains(@class,'card-columns')]//span[text()='"+type+"']//preceding::input[1]");
        cbGroup.jsClick();
        btnSetSelection.click();
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
    public List<String> getFirstRowGroupData() {
        List <String> lstData = new ArrayList<>();
        if (!lblFirstAccount.isDisplayed()) {
            System.out.println("There is no Group available for opening");
            return null;
        } else {
            Row firstGroupRow = Row.xpath("(//app-bbg//table//tbody//tr)[1]/td");
            for (int i = 0; i < firstGroupRow.getWebElements().size(); i++) {
                String xpathData = String.format("(//app-bbg//table//tbody//tr)[1]//td[%s]",i+1);
                Label lblDataCell = Label.xpath(xpathData);
                lstData.add(lblDataCell.getText());
            }
            return lstData;
        }
    }

    public boolean verifyAccBelongToKastraki(String firstAcc) {
        JSONObject jsonObject = AccountSearchUtils.getAccountInfoJson(firstAcc);
        if (Objects.nonNull(jsonObject)){
            return true;
        }
        return false;
    }

}
