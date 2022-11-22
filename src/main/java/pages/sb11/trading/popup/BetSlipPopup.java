package pages.sb11.trading.popup;

import com.paltech.element.common.*;

public class BetSlipPopup {
    public Popup betSlipPopup = Popup.xpath("//app-match-odd-bet-slip");
    public Label lblHeader = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]");
    public Button btnPlaceBet = Button.xpath("//button[contains(text(),'Place Bet')]");
    public CheckBox cbSameOdds = CheckBox.name("sameOdd");
    public CheckBox cbIncreaseOdds = CheckBox.name("increaseOdd");
    public DropDownBox ddHandicap = DropDownBox.xpath("//div[contains(@class,'pl-3')]//div[contains(@class,'w-25')]//select");
    public TextBox txtOdds = TextBox.xpath("//div[contains(@class,'pl-3')]//div[contains(@class,'w-25')]//input");
    public TextBox txtStake = TextBox.xpath("//div[contains(@class,'pl-3')]//div[@class='input-group']//input");

}
