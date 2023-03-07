package pages.sb11.trading.popup;

import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import objects.Order;
import org.testng.Assert;
import pages.sb11.trading.controls.OrderRowControl;

import java.text.ParseException;
import java.util.List;

import static common.SBPConstants.SOCCER_MARKET_TYPE_BET_LIST;

public class SoccerBetSlipPopup {
    public Label lblEventStartTime = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[1]");
    private Label lblLeagueName = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[2]/span[1]");
    private Label lblEventName = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[2]/span[2]");
    public Label lblMarketType = Label.xpath("//app-match-odd-bet-slip//div[contains(@class,'box-dialog-header')]/div/div[2]/span[3]");
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
/*
    public void placebet(Order order, boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
        OrderRowControl orderRowControl ;
        orderRowControl = OrderRowControl.xpath(String.format("%s[%s]",xPathrderRowControl,1));
        orderRowControl.inputInfo(order.isNegativeHdp(),order.getHdpPoint(), order.getPrice(),order.getOddType(), order.getBetType(),
        order.getLiveHomeScore(),order.getLiveAwayScore(),order.getRequireStake());
        if(isCopySPBPS7SameOdds != cbCopyBetToSPBPS7SameOdds.isSelected()){
            cbCopyBetToSPBPS7SameOdds.click();
        }
        if(isCopySPBPS7MinusOdds != cbCopyBetToSPBPS7MinusOdds.isSelected()){
            cbCopyBetToSPBPS7MinusOdds.click();
        }
        if(isPlaceBet) {
            btnPlaceBet.click();
        }

    }*/

    /**
     * Input info for more than 1 row and click place bet
     * @param lstOrder the order info : is handicap (-,+) pirce, odds type, bet type, live score, stake

     */
    public void placeMultiBet(List<Order> lstOrder,boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
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

    public List<Order> verifyOrderInfoDisplay(List<Order> expectedOrder, String eventDate) throws ParseException {
        String competition = lblLeagueName.getText().trim();
        String eventName = lblEventName.getText().trim();
        String marketType = lblMarketType.getText().trim();
        String selectionType = lblSelectionName.getText().trim();
        String startDate = lblEventStartTime.getText().trim();
        String[] dateconvert = startDate.split("\\s");
        Assert.assertEquals(competition, expectedOrder.get(0).getCompetitionName(), "Failed! Competition name is incorrect");
        Assert.assertEquals(eventName, expectedOrder.get(0).getHome() + " -vs- " + expectedOrder.get(0).getAway(), "Failed! Event name is incorrect");
        Assert.assertEquals(marketType,  SOCCER_MARKET_TYPE_BET_LIST.get(expectedOrder.get(0).getMarketType()), "Failed! Market type is incorrect");
        Assert.assertEquals(selectionType, expectedOrder.get(0).getSelection(), "Failed! Selection type is incorrect");
        if(!eventDate.isEmpty()) {
            String eventDateConvert = DateUtils.convertDateToNewTimeZone(eventDate, "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "", "dd/MM", "");
            Assert.assertEquals(dateconvert[0], eventDateConvert, "Failed! Start date is incorrect");
        }
        return expectedOrder;
    }


}
