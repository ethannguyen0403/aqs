package pages.ess.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.Popup;

public class BetSlipPopup {
    public Popup betSlipPopup = Popup.id("betModel");
    public Label lblHeader = Label.xpath("//app-place-bet-dialog//div[contains(@class,'bet-list-header')]");
    public Button btnClose = Button.xpath("//app-place-bet-dialog//div[contains(@class,'bet-list-header')]//em");
    public Button btnSelectAll = Button.xpath("//app-place-bet-dialog//div[contains(text(),'Select All')]");
    public Button btnDelSelect = Button.xpath("//app-place-bet-dialog//div[contains(text(),'Delete Selected')]");
    public Button btnPlaceBet = Button.xpath("//app-place-bet-dialog//button[contains(text(),'Place Bet')]");
    public DropDownBox ddMaster = DropDownBox.id("masterAccount");
    public DropDownBox ddOdds = DropDownBox.xpath("//app-place-bet-dialog//div[contains(@class, 'place-form')]//select");

}


