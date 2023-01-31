package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import objects.Order;
import org.testng.Assert;

import java.util.List;

import static common.ESSConstants.HomePage.EN_US;

public class BetListPopup {
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
                System.out.println(String.format("Skip verity %s section at row %s because the account code is different with %s", marketType,i,accountCode));
                i = i +1;
                continue;
            }else {
                verifyOrderInfoDisplayCorrectInRow(lstOrder.get(orderIndex),createDate,i);
                orderIndex = orderIndex +1;
            }
            i = i +1;
        }
    }

    public boolean isOrderDisplay(String orderId){
        // to get row index with expected market type
        int i = 1;
        while(true){
            if(!tblOrder.getControlOfCell(1,colAccount,i,null).isDisplayed()) {
                System.out.println("Not found bet id "+orderId+" in the table");
                return false;
            }
            String betId = tblOrder.getControlOfCell(1,colBetrefId,i,null).getText().trim();
            if(!betId.equals(orderId))
            {
                return true;
            }
            i = i +1;
        }
    }

    public Order verifyOrderInfoDisplay(Order order, String marketType, String createDate){
        // to get row index with expected market type
        int startIndex = getStartRowWithMarketType(marketType);
        int i = startIndex +1;
        while(true){
            String accountCode = tblOrder.getControlOfCell(1,colAccount,i,null).getText().trim();
            if(accountCode.equals(order.getAccountCode()))
                return verifyOrderInfoDisplayCorrectInRow(order,createDate,i);
            System.out.println(String.format("Skip verity %s section at row %s because the account code is different with %s", marketType,i,accountCode ));
            i = i +1;
        }
    }

    /**
     * To verify bet info display as expect then set Betref ID to expected Order
     * @param expectedOrder
     * @param createDate
     */
    private Order verifyOrderInfoDisplayCorrectInRow(Order expectedOrder, String createDate, int rowIndex){
        String marketType = expectedOrder.getMarketType();
        System.out.println(String.format("verity data in %s section at row %s", marketType, rowIndex));
        String accountCode = tblOrder.getControlOfCell(1, colAccount, rowIndex, null).getText().trim();
        String betType = tblOrder.getControlOfCell(1, colBetType, rowIndex, null).getText().trim();
        String selection = tblOrder.getControlOfCell(1, colSelection, rowIndex, null).getText().trim();
        String hdp = tblOrder.getControlOfCell(1, colHDP, rowIndex, null).getText().trim();
        String live = tblOrder.getControlOfCell(1, colLive, rowIndex, null).getText().trim().replace(" ","");
        String price = tblOrder.getControlOfCell(1, colPrice, rowIndex, null).getText().trim();
        String stake = tblOrder.getControlOfCell(1, colStake, rowIndex, null).getText().trim();
        String cur = tblOrder.getControlOfCell(1, colCUR, rowIndex, null).getText().trim();
        String entryDate = tblOrder.getControlOfCell(1, colEntryDate, rowIndex, null).getText().trim();
        String betID = tblOrder.getControlOfCell(1, colBetrefId, rowIndex, null).getText().trim();
        String hdpSign = expectedOrder.isNegativeHdp() ? "-" : "+";
        String expectedBetType;
        String expectedHDP;
        String expectedLive = "";

        // Define value base on Sport name
        if (expectedOrder.getSport().equalsIgnoreCase("Soccer")) {
            if (marketType.contains("1x2")){
                expectedBetType = String.format("%s-%s", EN_US.get(expectedOrder.getStage()), "1x2");
                expectedHDP = String.format("%.2f", expectedOrder.getHdpPoint());
            } else {
                expectedBetType = String.format("%s-%s", EN_US.get(expectedOrder.getStage()), EN_US.get(expectedOrder.getMarketType()));
                expectedHDP = String.format("%s%.2f", hdpSign, expectedOrder.getHdpPoint());
            }
            if (expectedOrder.getLiveHomeScore() == 0 && expectedOrder.getLiveAwayScore() == 0) {
                expectedLive = String.format("%s:%s", expectedOrder.getLiveHomeScore(), expectedOrder.getLiveAwayScore()).replace(" ","");
            }

        } else {
            // option for Cricket
            expectedBetType = expectedOrder.getMarketType();
            if (expectedBetType.contains("HDP")) {
                expectedHDP = String.format("%s%s / %s%s", hdpSign, expectedOrder.getHandicapWtks(), hdpSign, expectedOrder.getHandicapRuns());
            } else if (expectedBetType.equals("OU")) {
                expectedHDP = String.format("%s%s", hdpSign, expectedOrder.getRuns());
            } else
                expectedHDP = "";
        }
        Assert.assertEquals(accountCode, expectedOrder.getAccountCode(), "Failed! Account code is incorrect");
        Assert.assertEquals(betType, expectedBetType, "Failed! Bet Type is incorrect");
        Assert.assertEquals(selection, expectedOrder.getSelection(), "Failed! Selection is incorrect");
        Assert.assertEquals(hdp, expectedHDP, "Failed! HDP is incorrect");
        Assert.assertEquals(live, expectedLive, "Failed! Live Score is incorrect is in correct");
        Assert.assertEquals(price, String.format("%.3f (%s)", expectedOrder.getPrice(), expectedOrder.getOddType()), "Failed! Price is incorrect is in correct");
        Assert.assertEquals(stake, String.format("%,.2f", expectedOrder.getRequireStake()), "Failed! Stake is incorrect is in correct");
        Assert.assertEquals(cur, expectedOrder.getAccountCurrency(), "Failed! Cur is incorrect is in correct");
        if (!createDate.isEmpty()) {
            Assert.assertEquals(entryDate, createDate, "Failed! EntryDate is incorrect is in correct");
        }
        Assert.assertFalse(betID.isEmpty(), "Failed! BetId should not empty");
        System.out.println("Set Betref ID " + betID + " back to expected order to get order in API for clearing position");
        expectedOrder.setBetId(betID);
        return expectedOrder;
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
