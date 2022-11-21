package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;

public class MixedSportsPopup {
    public DropDownBox ddCompany = DropDownBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Company Unit')]//following::select[1]");
    public TextBox txtAccCode = TextBox.id("//app-bet-entry-mixed-sport//input[@type='search']");
    public Button btnSearch = Button.xpath("//app-bet-entry-mixed-sport//button[contains(@class,'btn-success')]");
    public DropDownBox ddSport = DropDownBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Sport')]//following::select[1]");
    public TextBox txtDate = TextBox.name("dp");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public TextBox txtBetDescription = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Bet Description')]//following::textarea");
    public TextBox txtSelection = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Selection')]//following::input[1]");
    public TextBox txtBetType = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Bet type')]//following::input[1]");
    public TextBox txtOdds = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Odds')]//following::input[1]");
    public TextBox txtStake = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Stake')]//following::input[1]");
    public TextBox txtWinLose = TextBox.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'Win/Loss or Comm Amount')]//following::input[1]");
    public DropDownBox ddBetType = DropDownBox.xpath("//app-bet-entry-mixed-sport//div[contains(@class,'col-2')]//select");
    public DropDownBox ddOddType = DropDownBox.xpath("//app-bet-entry-mixed-sport//div[contains(@class,'col-7')]//select");
    public Button btnClear = Button.xpath("//app-bet-entry-mixed-sport//span[contains(text(),'CLEAR')]");
    public Button btnPlaceBet = Button.xpath("//app-bet-entry-mixed-sport//button[contains(text(),'Place bet')]");
}
