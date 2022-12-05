package pages.sb11.trading.popup;

import com.paltech.element.common.*;
import objects.Order;
import pages.sb11.trading.SoccerBetEntryPage;

import java.util.List;

public class SoccerSPBBetSlipPopup {
    private Label lblTitle = Label.xpath("//app-more-bet-option//div[contains(@class,'main-box-header')]//span[1]");
    private Label lblEvent = Label.xpath("//app-more-bet-option//div[contains(@class,'form-check')]/div[1]/label");
    private Label lblHomeName = Label.xpath("//app-more-bet-option//div[contains(@class,'form-check')]/div[1]/span[1]");
    private Label lblAway = Label.xpath("//app-more-bet-option//div[contains(@class,'form-check')]/div[1]/span[2]");
    private Label lblBetType = Label.xpath("//app-more-bet-option//div[contains(@class,'form-check')]/div[2]//label");
    private DropDownBox ddBetType = DropDownBox.xpath("//app-more-bet-option//label[contains(text(),'Bet Type')]//following::select[1]");
    private Label lblSelection = Label.xpath("//app-more-bet-option//div[contains(@class,'form-check')]/div[3]//label");
    private TextBox txtOdds = TextBox.xpath("//app-more-bet-option//label[contains(text(),'Odds')]//following::input[1]");
    private DropDownBox ddOddType = DropDownBox.xpath("//app-more-bet-option//label[contains(text(),'Odds')]//following::select");
    private DropDownBox ddBetSelection = DropDownBox.xpath("//app-more-bet-option//label[contains(text(),'Selection')]//following::select[1]");
    private TextBox txtHomeScore = TextBox.xpath("//app-more-bet-option//label[contains(text(),'Live Score')]//following::input[1]");
    private TextBox txtAwayScore = TextBox.xpath("//app-more-bet-option//label[contains(text(),'Live Score')]//following::input[2]");
    private TextBox txtStake = TextBox.xpath("//app-more-bet-option//label[contains(text(),'Stake')]//following::input[1]");
    private RadioButton rbHome = RadioButton.id("radHome");
    private RadioButton rbAway = RadioButton.id("radAway");
    private RadioButton rbDraw = RadioButton.id("radDraw");
    private CheckBox cbCopyBetToSPBPS7SameOdds = CheckBox.xpath("//input[@id='sameOddMore']");
    private Label lblCopyBetToSPBPS7SameOdds = Label.xpath("//label[@for='sameOddMore']");
    private CheckBox cbCopyBetToSPBPS7MinusOdds = CheckBox.xpath("//input[@id='minusOddMore']");
    private Label lblCopyBetToSPBPS7MinusOdds = Label.xpath("//label[@for='minusOddMore']");
    private Button btnPlaceBet = Button.xpath("//app-more-bet-option//button[contains(text(),'Place Bet')]");
    private Button btnClose = Button.xpath("//app-more-bet-option//div[contains(@class,'main-box-header')]//span[2]");

    /**
     * Click on close icon on the top right of the popup
     */
    public void close(){
        btnClose.click();
    }

    public void placeMoreBet(Order placedOrder, boolean isCopySPBPS7SameOdds, boolean isCopySPBPS7MinusOdds, boolean isPlaceBet){
        SoccerBetEntryPage soccerBetEntryPage = new SoccerBetEntryPage();
        soccerBetEntryPage.openSPBBetSlip(placedOrder.getAccountCode(),placedOrder.getSelection());
        SoccerSPBBetSlipPopup soccerSPBBetSlipPopup = new SoccerSPBBetSlipPopup();
        soccerSPBBetSlipPopup.inputFT1x2info(placedOrder.isHome(),placedOrder.isAway(),placedOrder.getPrice(), placedOrder.getMarketType(), placedOrder.getOddType(),placedOrder.getBetType(),
                placedOrder.getLiveHomeScore(), placedOrder.getLiveAwayScore(), placedOrder.getRequireStake());
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

    public void inputFT1x2info(boolean isHome,boolean isAway, double price, String betType, String oddsType, String betSelection, int liveHomeScore, int liveAwayScore, double stake){
        if(isHome){
            rbHome.click();
        } else if (isAway){
            rbAway.click();
        } else rbDraw.click();
        ddBetType.selectByVisibleText(betType);
        txtOdds.sendKeys(String.format("%.3f",price));
        ddOddType.selectByVisibleText(oddsType);
        ddBetSelection.selectByVisibleText(betSelection);
        txtHomeScore.sendKeys(String.format("%d",liveHomeScore));
        txtAwayScore.sendKeys(String.format("%d",liveAwayScore));
        txtStake.sendKeys(String.format("%.2f",stake));
    }
}
