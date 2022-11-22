package pages.sb11.trading.popup;

import com.paltech.element.common.*;
import objects.Order;
import pages.sb11.trading.controls.OrderRowControl;

import java.util.List;

public class SoccerBetSlipPopup {
    private Label lblEventStartTime = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[1]");
    private Label lblLeagureName = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[2]/span[1]");
    private Label lblEventName = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[2]/span[2]");
    private Label lblMarketType = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[2]/span[3]");
    private Button btnClose = Button.xpath("//app-match-odd-bet-slip//div[contains(@class,'close-btn ')]");
    private Label lblSelectionName = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'place-form ')]/label");
    private String xPathrderRowControl = "//app-match-odd-bet-slip//div[contains(@class,'place-form ')]//div[contains(@class,'order-row')]";
    private CheckBox cbCopyBetToSPBPS7SameOdds = CheckBox.xpath("//input[@id='sameOdd']");
    private Label lblCopyBetToSPBPS7SameOdds = Label.xpath("//input[@for='sameOdd']");
    private CheckBox cbCopyBetToSPBPS7MinusOdds = CheckBox.xpath("//input[@id='minusOdd']");
    private Label lblCopyBetToSPBPS7MinusOdds = Label.xpath("//input[@for='minusOdd']");
    private Button btnPlaceBet = Button.xpath("//button[contains(@class,'btn-place')]");
    private Label lblErrorMessage = Label.xpath("//button[contains(@class,'btn-place')]//ancestor::div[1]/span");

    /**
     * Click on close icon on the top right of the popup
     */
    public void close(){
        btnClose.click();
    }

    /**
     * Input info and click place bet
     * @param lstOrder the order info : is handicap (-,+) pirce, odds type, bet type, live score, stake
     * @param isCopySPBPS7SameOdds
     * @param isCopySPBPS7MinusOdds
     * @param isPlaceBet
     */
     public void placebet(List<Order> lstOrder, boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
        OrderRowControl orderRowControl ;
        Order order;
        for(int i = 0; i<lstOrder.size(); i++){
            order = lstOrder.get(i);
            orderRowControl = OrderRowControl.xpath(String.format("%s[%s]",xPathrderRowControl,i+1));
            orderRowControl.inputInfo(order.isNegativeHdp(),order.getHdpPoint(), order.getPrice(),order.getOddType(), order.getBetType(),
                    order.getLiveHomeScore(),order.getLiveAwayScore(),order.getRequireStake());
        }
        if(isCopySPBPS7SameOdds != cbCopyBetToSPBPS7SameOdds.isSelected()){
            cbCopyBetToSPBPS7SameOdds.click();
        }
        if(isCopySPBPS7MinusOdds != cbCopyBetToSPBPS7MinusOdds.isSelected()){
            cbCopyBetToSPBPS7MinusOdds.click();
        }
        if(isPlaceBet) {
            btnPlaceBet.click();
        }

    }


}
