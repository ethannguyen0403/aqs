package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.ess.popup.ColumnSettingPopup;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.BetSlipPopup;

public class BetEntryPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public DropDownBox ddComp = DropDownBox.id("company-unit");
    public TextBox txtDate = TextBox.xpath("//div[@class='pl-3']/label[contains(text(),'Date')]//following::input[1]");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public DropDownBox ddLeague = DropDownBox.id("league");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public DropDownBox ddSearchBy = DropDownBox.xpath("//div[@class='pl-3']/label[contains(text(),'Search By')]//following::select[1]");
    public TextBox txtAccCode = TextBox.id("account-code");
    public Button btnSoccer = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Soccer')]");
    public Button btnCricket = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Cricket')]");
    public Button btnMixedSport = Button.xpath("//div[contains(@class,'card-body')]//span[contains(text(),'Mixed Sports')]");
    public Table tbEvent = Table.xpath("//table[contains(@class,'table')]",16);

    public void openBetSlipSoccer (String accCode, String betType){
        btnSoccer.click();
        ddLeague.selectByVisibleContainsText("All");
        txtAccCode.sendKeys(accCode);
        btnShow.click();
        String _betType = betType.toUpperCase();
        switch (_betType) {
            case "HOME-FT":
                tbEvent.getControlOfCell(1, 4, 1, "span[1]").click();
                return;
            case "AWAY-FT":
                tbEvent.getControlOfCell(1, 5, 1, "span[1]").click();
                return;
            case "OVER-FT":
                tbEvent.getControlOfCell(1, 7, 1, "span[1]").click();
                return;
            case "UNDER-FT":
                tbEvent.getControlOfCell(1, 8, 1, "span[1]").click();
                return;
            case "HOME-HT":
                tbEvent.getControlOfCell(1, 10, 1, "span[1]").click();
                return;
            case "AWAY-HT":
                tbEvent.getControlOfCell(1, 11, 1, "span[1]").click();
                return;
            case "OVER-HT":
                tbEvent.getControlOfCell(1, 13, 1, "span[1]").click();
                return;
            case "UNDER-HT":
                tbEvent.getControlOfCell(1, 14, 1, "span[1]").click();
                return;
            case "MORE":
                tbEvent.getControlOfCell(1, 15, 1, "span[1]").click();
                return;
        }
    }

    public BetSlipPopup placeSoccerBet(String handicap, String odds, String stake, boolean copySameOdds, boolean copyMinusOdds, boolean placebet){
        BetSlipPopup betSlipPopup = new BetSlipPopup();
        betSlipPopup.ddHandicap.selectByVisibleText(handicap);
        betSlipPopup.txtOdds.sendKeys(odds);
        betSlipPopup.txtStake.sendKeys(stake);
        if (copyMinusOdds){
            betSlipPopup.cbIncreaseOdds.click();
        }

        if (copySameOdds){
            betSlipPopup.cbSameOdds.click();
        }

        if (placebet){
            betSlipPopup.btnPlaceBet.click();
        }
        return new BetSlipPopup();
    }
    @Override
        public String getTitlePage() {return this.lblTitle.getText().trim();}
}

