package pages.sb11.trading.popup;

import com.paltech.element.common.*;
import objects.Order;

import static common.ESSConstants.HomePage.EN_US;

public class CricketBetSlipPopup {
    private Label lblTitle = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'main-box-header')]//span[1]");
    private Label lblEvent = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[1]/label");
    private Label lblHomeName = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[1]/span[1]");
    private Label lblAway = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[1]/span[2]");
    private Label lblBetType = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[2]//label");
    private Label lblBetTypeValue = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[2]//label/following::span[1]");
    private Label lblSelection = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[3]//label");
    private Label lblSelectionValue = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[3]//label/following::span[1]");
    private Button btnClose = Button.xpath("//app-cricket-bet-slip//div[contains(@class,'main-box-header')]//span[2]");
    private Label lblHandicapWtks = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[4]//label[1]");
    private TextBox txtHandicapWtks = TextBox.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[4]//label[1]/following::input[1]");
    private Label lblHandicapRuns = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[4]//label[2]");
    private TextBox txtHandicapRuns = TextBox.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[4]//label[2]/following::input[1]");
    private Label lblRuns = Label.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[4]//label");
    private TextBox txtRuns = TextBox.xpath("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[4]//label/following::input[1]");
    private Label lblOdds ;
    private TextBox txtOdds ;
    private DropDownBox ddbOddType ;
    private DropDownBox ddbBetType ;
    private Label lblIsLive ;
    private CheckBox cbIsLive;
    private Label lblStake ;
    private TextBox txtStake ;
    private Button btnPlaceBet ;
    private Label lblErrorMessage;

    /**
     * As the control is dynamic display basic on market type. This action to define xPath dynamic accordingly
     * @param marketType
     */
    public void defineControlBasedMarketType(String marketType){
        String _marketType = marketType.toUpperCase();
        int startDivIndex = 4;
        if(_marketType.equals("OU")|| _marketType.equals("MATCH-HDP"))
            startDivIndex = 5;
        lblOdds = Label.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label",startDivIndex));
        txtOdds = TextBox.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label/following::input[1]",startDivIndex));
        ddbOddType = DropDownBox.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label/following::select[1]",startDivIndex));
        ddbBetType = DropDownBox.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label/following::select[2]",startDivIndex));
        lblIsLive = Label.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label",startDivIndex+1));
        cbIsLive = CheckBox.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label/following::input[1]",startDivIndex+1));
        lblStake = Label.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label",startDivIndex+2));
        txtStake = TextBox.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//label/following::input[1]",startDivIndex+2));
        btnPlaceBet = Button.xpath(String.format("//app-cricket-bet-slip//div[contains(@class,'form-check')]/div[%s]//button",startDivIndex+3));
    }


    /**
     * Click on close icon on the top right of the popup
     */
    public void close(){
        btnClose.click();
    }

    /**
     * Input info to place bet
     * @param order
     * @param isPlaceBet
     */
     public void placeBet(Order order, boolean isPlaceBet){
         defineControlBasedMarketType(order.getMarketType());
         if(order.getRuns()!=0){
             txtRuns.sendKeys(String.format("%.2f",order.getRuns()));
         }
         if(order.getHandicapWtks()!=0){
             txtHandicapWtks.sendKeys(String.format("%s",order.getHandicapWtks()));
         }
         if(order.getHandicapRuns()!=0){
             txtHandicapRuns.sendKeys(String.format("%.2f",order.getHandicapRuns()));
         }
         txtOdds.sendKeys(String.format("%.3f",order.getPrice()));
         ddbOddType.selectByVisibleText(order.getOddType());
         ddbBetType.selectByVisibleText(order.getBetType());
         if(order.isLive())
             cbIsLive.click();
          txtStake.sendKeys(String.format("%.2f",order.getRequireStake()));
         if(isPlaceBet) {
             btnPlaceBet.click();
         }


     }


}
