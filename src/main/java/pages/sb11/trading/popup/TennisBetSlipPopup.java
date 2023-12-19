package pages.sb11.trading.popup;

import com.paltech.element.common.*;
import objects.Order;

public class TennisBetSlipPopup {
    TextBox txtOdds = TextBox.xpath("//app-group-odds-bet-type-field//label/following::input[1]");
    DropDownBox ddbOddType = DropDownBox.xpath("//app-group-odds-bet-type-field//label/following::select[1]");
    DropDownBox  ddbBetType = DropDownBox.xpath("//app-group-odds-bet-type-field//label/following::select[2]");
    CheckBox chkIsLive = CheckBox.xpath("//app-live-checkbox-field//input");
    TextBox txtStake = TextBox.xpath("//app-stake-field//input");
    public  Button  btnPlaceBet = Button.xpath("//app-entry-bet-slip//button[contains(text(),'Place Bet')]");

    public void fillBetSlipInfo1x2(Order order){
        txtOdds.sendKeys(String.format("%.3f",order.getPrice()));
        ddbOddType.selectByVisibleText(order.getOddType());
        ddbBetType.selectByVisibleText(order.getBetType());
        if(order.isLive())
            chkIsLive.select();
        txtStake.sendKeys(String.format("%.2f",order.getRequireStake()));
    }


}
