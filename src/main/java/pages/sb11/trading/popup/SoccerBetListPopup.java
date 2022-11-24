package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import objects.Order;
import org.testng.Assert;

import java.util.List;

import static common.ESSConstants.HomePage.EN_US;

public class SoccerBetListPopup {
    private Label lblTitle = Label.xpath("//app-entry-bet-log//span[contains(@class,'modal-title')]");
    private Button btnClose = Button.xpath("//app-entry-bet-log//span[contains(@class,'close-icon')]");
    private Label lblInfo = Label.xpath("//app-entry-bet-log//div[contains(@class,'bet-list-content')]/div[2]");
    public Icon icRefresh = Icon.xpath("//app-entry-bet-log//div[contains(@class,'bet-list-content')]/div[2]/em");
    int totalCol = 11;
    int colAccount = 2;
    int colBetType = 3;
    int colSelection = 4;
    int colHDP = 5;
    int colLive =6;
    int colPrice = 7;
    int colStake = 8;
    int colCUR = 9;
    int colEntryDate = 10;
    int colBetrefId = 11;
    private Table tblOrder = Table.xpath("//app-entry-bet-log//div[@id='customTable']//table",totalCol);
    /**
     * Click on close icon on the top right of the popup
     */
    public void close(){
        btnClose.click();
    }

    /**
     * To verify bet info display as expect then set Betref ID back to expected Order
     * @param lstOrder
     * @param marketType
     * @param createDate
     */
    public List<Order> verifyListOrderInfoDisplay(List<Order> lstOrder, String marketType, String createDate){
        // to get row index with expected market type
       int startIndex = getStartRowWithMarketType(marketType);
       int i = startIndex +1;
       int orderIndex = 0;
       while(true){
           if(!tblOrder.getControlOfCell(1,colAccount,i,null).isDisplayed())
               return lstOrder;
           if(orderIndex >= lstOrder.size())
               return lstOrder;

           String accountCode = tblOrder.getControlOfCell(1,colAccount,i,null).getText().trim();
           if(!accountCode.equals(lstOrder.get(orderIndex).getAccountCode()))
           {
               System.out.println(String.format("Skip verity %s section at row %s because the account code is different with %s", marketType,i,accountCode ));
               i = i +1;
               continue;
           }else {
               System.out.println(String.format("verity data in %s section at row %s", marketType,i));
               String betType = tblOrder.getControlOfCell(1, colBetType, i, null).getText().trim();
               String selection = tblOrder.getControlOfCell(1, colSelection, i, null).getText().trim();
               String hdp = tblOrder.getControlOfCell(1, colHDP, i, null).getText().trim();
               String live = tblOrder.getControlOfCell(1, colLive, i, null).getText().trim();
               String price = tblOrder.getControlOfCell(1, colPrice, i, null).getText().trim();
               String stake = tblOrder.getControlOfCell(1, colStake, i, null).getText().trim();
               String cur = tblOrder.getControlOfCell(1, colCUR, i, null).getText().trim();
               String entryDate = tblOrder.getControlOfCell(1, colEntryDate, i, null).getText().trim();
               String betID = tblOrder.getControlOfCell(1, colBetrefId, i, null).getText().trim();

               Order expectedORder = lstOrder.get(orderIndex);
               String hdpSign = expectedORder.isNegativeHdp() ? "-" : "+";
               String expectedBetType = String.format("%s-%s",EN_US.get(expectedORder.getStage()),EN_US.get(expectedORder.getMarketType()));
               Assert.assertEquals(accountCode, expectedORder.getAccountCode(), "Failed! Account code is incorrect");
               Assert.assertEquals(betType, expectedBetType, "Failed! Bet Type is incorrect");
               Assert.assertEquals(selection, expectedORder.getSelection(), "Failed! Selection is incorrect");

               if (EN_US.get(expectedORder.getMarketType()).equals("HDP")) {
                   Assert.assertEquals(hdp, String.format("%s%s", hdpSign, expectedORder.getHdpPoint()), "Failed! HDP Handicap section is incorrect");
               } else
                   Assert.assertEquals(hdp, expectedORder.getHdpPoint(), "Failed! HDP Over/under is incorrect");

               if (!live.isEmpty()) {
                   Assert.assertEquals(live, String.format("%s:%s", expectedORder.getLiveHomeScore(), expectedORder.getLiveAwayScore()), "Failed! Live Score is incorrect is in correct");
               }
               Assert.assertEquals(price, String.format("%.3f (%s)", expectedORder.getPrice(), expectedORder.getOddType()), "Failed! Price is incorrect is in correct");
               Assert.assertEquals(stake, String.format("%,.2f", expectedORder.getRequireStake()), "Failed! Stake is incorrect is in correct");
               Assert.assertEquals(cur, expectedORder.getAccountCurrency(), "Failed! Cur is incorrect is in correct");
               if(!createDate.isEmpty()) {
                   Assert.assertEquals(entryDate, createDate, "Failed! EntryDate is incorrect is in correct");
               }
               Assert.assertFalse(betID.isEmpty(), "Failed! BetId should not empty");

               System.out.println("Set Betref ID "+ betID +" back to expected order to get order in API for clearing position");
               lstOrder.get(orderIndex).setBetId(betID);
               orderIndex = orderIndex +1;
           }
       }
    }

    /**
     * To verify bet info display as expect then set Betref ID to expected Order
     * @param order
     * @param marketType
     * @param createDate
     */
    public void verifyOrderInfoDisplay(Order order, String marketType, String createDate){
        // to get row index with expected market type
        int startIndex = getStartRowWithMarketType(marketType);
        int i = startIndex +1;
        while(true){
            if(!tblOrder.getControlOfCell(1,colAccount,i,null).isDisplayed())
                return;


            String accountCode = tblOrder.getControlOfCell(1,colAccount,i,null).getText().trim();
            if(!accountCode.equals(order.getAccountCode()))
            {
                System.out.println(String.format("Skip verity %s section at row %s because the account code is different with %s", marketType,i,accountCode ));
                i = i +1;
                continue;
            }else {
                System.out.println(String.format("verity %s section at row %s", marketType,i));
                String betType = tblOrder.getControlOfCell(1, colBetType, i, null).getText().trim();
                String selection = tblOrder.getControlOfCell(1, colSelection, i, null).getText().trim();
                String hdp = tblOrder.getControlOfCell(1, colHDP, i, null).getText().trim();
                String live = tblOrder.getControlOfCell(1, colLive, i, null).getText().trim();
                String price = tblOrder.getControlOfCell(1, colPrice, i, null).getText().trim();
                String stake = tblOrder.getControlOfCell(1, colStake, i, null).getText().trim();
                String cur = tblOrder.getControlOfCell(1, colCUR, i, null).getText().trim();
                String entryDate = tblOrder.getControlOfCell(1, colEntryDate, i, null).getText().trim();
                String betID = tblOrder.getControlOfCell(1, colBetrefId, i, null).getText().trim();

                Order expectedORder = order;
                String hdpSign = expectedORder.isNegativeHdp() ? "-" : "+";
                Assert.assertEquals(accountCode, expectedORder.getAccountCode(), "Failed! Account code is incorrect");
                Assert.assertEquals(betType, String.format("%s-%s", expectedORder.getBetType(), marketType), "Failed! Bet Type is incorrect");
                Assert.assertEquals(selection, expectedORder.getSelection(), "Failed! Selection is incorrect");

                if (marketType.equals("HANDICAP")) {
                    Assert.assertEquals(hdp, String.format("%s%s", hdpSign, expectedORder.getHdpPoint()), "Failed! HDP Handicap section is incorrect");
                } else
                    Assert.assertEquals(hdp, expectedORder.getHdpPoint(), "Failed! HDP Over/under is incorrect");

                if (!live.isEmpty()) {
                    Assert.assertEquals(live, String.format("%s:%s", expectedORder.getLiveHomeScore(), expectedORder.getLiveAwayScore()), "Failed! Live Score is incorrect is in correct");
                }
                Assert.assertEquals(price, String.format("%.2f (%s)", expectedORder.getPrice(), expectedORder.getOddType()), "Failed! Price is incorrect is in correct");
                Assert.assertEquals(stake, String.format("%,.2f", expectedORder.getPrice(), expectedORder.getOddType()), "Failed! Price is incorrect is in correct");
                Assert.assertEquals(cur, expectedORder.getAccountCurrency(), "Failed! Cur is incorrect is in correct");
                Assert.assertEquals(entryDate, createDate, "Failed! EntryDate is incorrect is in correct");
                Assert.assertFalse(betID.isEmpty(), "Failed! BetId should not empty");

                System.out.println("Set Betref ID back to expected order to get order in API for clearing position");
                order.setBetId(betID);
                return;
            }

        }
    }

    private int getStartRowWithMarketType(String marketType){
        int i = 1;
        Label lblMarketType;
        while (true){
            lblMarketType = Label.xpath(tblOrder.getxPathOfCell(1,1,i,null));
            if(!lblMarketType.isDisplayed()){
                System.out.println("The market type "+marketType+" does not display in the list");
                return 0;
            }
            if(lblMarketType.getText().equalsIgnoreCase(marketType))
                return i;
            i = i +1;
        }
    }


}
