package pages.sb11.trading.popup;

import com.paltech.element.common.*;
import objects.Order;
import pages.sb11.trading.SoccerBetEntryPage;

import java.util.List;

public class SoccerSPBBetSlipPopup {
    private Label lblTitle = Label.xpath("//app-entry-bet-slip-more-option-form//div[contains(@class,'main-box-header')]//span[1]");
    private Label lblEvent = Label.xpath("//app-entry-bet-slip-more-option-form//div[contains(@class,'form-check')]/div[1]/label");
    private Label lblHomeName = Label.xpath("//app-entry-bet-slip-more-option-form//div[contains(@class,'form-check')]/div[1]/span[1]");
    private Label lblAway = Label.xpath("//app-entry-bet-slip-more-option-form//div[contains(@class,'form-check')]/div[1]/span[2]");
    private Label lblBetType = Label.xpath("//app-entry-bet-slip-more-option-form//div[contains(@class,'form-check')]/div[2]//label");
    private DropDownBox ddMarketType = DropDownBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'Bet Type')]//following::select[1]");
    private Label lblSelection = Label.xpath("//app-entry-bet-slip-more-option-form//div[contains(@class,'form-check')]/div[3]//label");
    private TextBox txtOdds = TextBox.xpath("//app-entry-bet-slip-more-option-form//label[text()=' Odds']//following::input[1]");
    private DropDownBox ddOddType = DropDownBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'Odds')]//following::select");
    private DropDownBox ddBetType = DropDownBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'Selection')]//following::select[1]");
    private DropDownBox ddUnder = DropDownBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'(-)')]//following::select[1]");
    private DropDownBox ddOver = DropDownBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'(+)')]//following::select[1]");
    private TextBox txtHomeScore = TextBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'Live Score')]//following::input[1]");
    private TextBox txtAwayScore = TextBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'Live Score')]//following::input[2]");
    private TextBox txtStake = TextBox.xpath("//app-entry-bet-slip-more-option-form//label[contains(text(),'Stake')]//following::input[1]");
    private RadioButton rbHome = RadioButton.id("radHome");
    private RadioButton rbAway = RadioButton.id("radAway");
    private RadioButton rbDraw = RadioButton.id("radDraw");
    private CheckBox cbCopyBetToSPBPS7SameOdds = CheckBox.xpath("//input[@id='sameOddMore']");
    private Label lblCopyBetToSPBPS7SameOdds = Label.xpath("//label[@for='sameOddMore']");
    private CheckBox cbCopyBetToSPBPS7MinusOdds = CheckBox.xpath("//input[@id='minusOddMore']");
    private Label lblCopyBetToSPBPS7MinusOdds = Label.xpath("//label[@for='minusOddMore']");
    private Button btnPlaceBet = Button.xpath("//app-entry-bet-slip-more-option-form//button[contains(text(),'Place Bet')]");
    private Button btnClose = Button.xpath("//app-entry-bet-slip-more-option-form//div[contains(@class,'main-box-header')]//span[2]");

    /**
     * Click on close icon on the top right of the popup
     */
    public void close(){
        btnClose.click();
    }

    public void placeMoreBet(Order placedOrder, boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
        inputFT1x2info(placedOrder);
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
    public  void placeHandicapCorners(Order placedOrder, String handicapType, boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
        inputFTHDPCornersInfo(placedOrder, handicapType);
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

    private void clickSelection (String selection){
        switch (selection){
            case "Home":
                rbHome.click();
                return;
            case "Away":
                rbAway.click();
                return;
            case "Draw":
                rbDraw.click();
                return;
        }
    }

    public void inputFT1x2info(Order order){
        clickSelection(order.getSelection());
        String marketType = String.format("%s - %s",order.getStage(),order.getMarketType());
        ddMarketType.selectByVisibleText(marketType);
        txtOdds.sendKeys(String.format("%.3f",order.getPrice()));
        ddOddType.selectByVisibleText(order.getOddType());
        ddBetType.selectByVisibleText(order.getBetType());
        txtHomeScore.sendKeys(String.format("%d",order.getLiveHomeScore()));
        txtAwayScore.sendKeys(String.format("%d",order.getLiveAwayScore()));
        txtStake.sendKeys(String.format("%.2f",order.getRequireStake()));
    }

    public void inputFTHDPCornersInfo (Order order, String handicapType){
        String marketType = String.format("%s - %s",order.getStage(),order.getMarketType());
        ddMarketType.selectByVisibleText(marketType);
        clickSelection(order.getSelection());
        if (handicapType == "Over"){
            ddOver.selectByVisibleText(String.valueOf(order.getHandicapRuns()));
        } else {
            ddUnder.selectByVisibleText(String.valueOf(order.getHandicapRuns()));
        }
        txtOdds.sendKeys(String.format("%.3f",order.getPrice()));
        ddOddType.selectByVisibleText(order.getOddType());
        ddBetType.selectByVisibleText(order.getBetType());
        txtHomeScore.sendKeys(String.format("%d",order.getLiveHomeScore()));
        txtAwayScore.sendKeys(String.format("%d",order.getLiveAwayScore()));
        txtStake.sendKeys(String.format("%.2f",order.getRequireStake()));
    }
}
