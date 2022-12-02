package pages.sb11.trading;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.control.ConfirmPopupControl;
import pages.sb11.trading.popup.BetSlipPopup;

import static common.ESSConstants.HomePage.EN_US;

public class ConfirmBetsPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public DropDownBox ddbCompanyUnit = DropDownBox.id("company");
    public TextBox txtAccStartWith = TextBox.id("acc-starts-with");
    public DropDownBox ddbStatus = DropDownBox.id("status");
    public DropDownBox ddbSport = DropDownBox.id("sport");
    public DropDownBox ddbBetType = DropDownBox.id("betType");
    public DropDownBox ddbDateType = DropDownBox.id("dateType");
    public TextBox txtFromDate  = TextBox.id("fromDate");
    public TextBox txtToDate  = TextBox.id("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");
    public TextBox txtAccountCode = TextBox.id("accountCode");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    int colTotal = 16;
    int colEventDate = 2;
    int colCountry = 3;
    int colLeague = 4;
    int colEvent = 5 ;
    int colBetDate = 6;
    int colSelection = 7;
    int colHdp = 8;
    int colLive = 9;
    int colOdds = 10;
    int colBL = 11;
    int colStake =12;
    int colBT = 13;
    int colTra = 14;
    int colSelect = 15;
    int colDelete = 16;
    public Table tblOrder = Table.xpath("//app-confirm-bet//div[@id= 'customTable']//table[contains(@aria-label,'bet table')]",colTotal);
    public Button btnUpdateBet = Button.xpath("//button[text()='Update Bet']");
    public Button btnDuplcateBetForSPBPS7 = Button.xpath("//button[text()='Duplicate Bet For SPBPS7']");
    public Label lblSelectAll = Label.xpath("//button[text()='Select All']");
    public Label lblDeleteSelected = Label.xpath("//button[text()='Delete Selected']");
    public Button btnConfirmBet = Button.xpath("//button[text()='Confirm Bet']");
    public Label lblTotalStake = Label.xpath("//span[contains(@class,'total-stake-pending')]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    /**
     * Filter Confirm bets with input value
     * @param companyUnit
     * @param accStartWith
     * @param status
     * @param sport
     * @param betType
     * @param dateType
     * @param fromDate
     * @param toDate
     * @param accountCode
     */
    public void filter(String companyUnit, String accStartWith, String status, String sport, String betType,String dateType, String fromDate, String toDate, String accountCode){
        ddbCompanyUnit.selectByVisibleText(companyUnit);
        txtAccStartWith.sendKeys(accStartWith);
        ddbStatus.selectByVisibleText(status);
        ddbSport.selectByVisibleText(sport);
        ddbBetType.selectByVisibleText(betType);
        ddbDateType.selectByVisibleText(dateType);
        if(!fromDate.isEmpty())
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        if(!toDate.isEmpty())
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        txtAccountCode.sendKeys(accountCode);
        btnShow.click();
       // waitPageLoad();
    }

    /**
     * Get row index of the order in the table
     * @param orderId
     * @return
     */
    private int getOrderIndex(String orderId){
        int i = 1;
        Label lblOrderID;
        while (true){
            lblOrderID = Label.xpath(tblOrder.getxPathOfCell(1,colEvent,i,"span[@class='text-secondary']"));
            if(!lblOrderID.isDisplayed()){
                System.out.println("The order id "+ orderId +" does not display in the table");
                return 0;
            }
            if(lblOrderID.getText().contains(orderId)){
                System.out.println("Found order "+orderId+" at row "+ i);
                return i;
            }
            i = i+1;
        }
    }

    /**
     * Verify order info as expected
     * @param order
     * @return
     */
    public void verifyOrder(Order order){
        int orderIndex = getOrderIndex(order.getOrderId());
        System.out.println(String.format("verity order %s  at row %s", order.getOrderId(), orderIndex));
        String dateEvent = tblOrder.getControlOfCell(1, colEventDate, orderIndex, null).getText().trim();
        String country = tblOrder.getControlOfCell(1, colCountry, orderIndex, null).getText().trim();
        String league = tblOrder.getControlOfCell(1, colLeague, orderIndex, null).getText().trim();
        String eventName = tblOrder.getControlOfCell(1, colEvent, orderIndex, "div[1]").getText().trim();
        String orderID = tblOrder.getControlOfCell(1, colEvent, orderIndex, "div[2]").getText().trim();
        String betDate = tblOrder.getControlOfCell(1, colBetDate, orderIndex, null).getText().trim();
        String selection = tblOrder.getControlOfCell(1, colSelection, orderIndex, "select").getText().trim();
        String hdp = tblOrder.getControlOfCell(1, colHdp, orderIndex, "select").getText().trim();
        String liveHomeScore = tblOrder.getControlOfCell(1, colLive, orderIndex, "input[1]").getText().trim();
        String liveAwayScore = tblOrder.getControlOfCell(1, colLive, orderIndex, "input[2]").getText().trim();
        String odds = tblOrder.getControlOfCell(1, colOdds, orderIndex, "input").getText().trim();
        String oddsType = tblOrder.getControlOfCell(1, colOdds, orderIndex, "span").getText().trim();
        String bl = tblOrder.getControlOfCell(1, colBL, orderIndex, "select").getText().trim();
        String stake =tblOrder.getControlOfCell(1, colStake, orderIndex, "input").getText().trim();
        String bt =tblOrder.getControlOfCell(1, colBT, orderIndex, null).getText().trim();
        String trad=tblOrder.getControlOfCell(1, colTra, orderIndex, null).getText().trim();

        Assert.assertEquals(dateEvent, order.getEventDate(), "Failed! Event date is incorrect");
        Assert.assertEquals(country, "country of league", "Failed! Country is incorrect");
        Assert.assertEquals(league, order.getCompetitionName(), "Failed! Selection is incorrect");
        Assert.assertEquals(eventName, String.format("%s vs %s",order.getHome(),order.getAway()), "Failed! Event name is incorrect");
        Assert.assertEquals(orderID, String.format("%s / %s",order.getOrderId(), order.getBetId()), "Failed! Live Score is incorrect ");
        Assert.assertEquals(betDate,order.getCreateDate(), "Failed! Place time is incorrect");
        Assert.assertEquals(selection, order.getSelection(), "Failed! Stake is incorrect is in correct");
        Assert.assertEquals(hdp, order.getHdpPoint(), "Failed!HDP is incorrect");
        Assert.assertEquals(liveHomeScore, order.getLiveHomeScore(), "Failed!Home live score is incorrect");
        Assert.assertEquals(liveAwayScore, order.getLiveAwayScore(), "Failed!Away live score is incorrect");
        Assert.assertEquals(odds, order.getPrice(), "Failed!Odds is incorrect");
        Assert.assertEquals(oddsType,String.format("(%s)",order.getOddType()), "Failed! Odds Type is incorrect");
        Assert.assertEquals(bl,order.getBetType(), "Failed! Bet Type (Back/Lay )is incorrect");
        Assert.assertEquals(stake,order.getRequireStake(), "Failed! Stake is incorrect");
        Assert.assertEquals(bt,String.format("%s-%s",order.getStage(),order.getMarketType()), "Failed!BT is incorrect");
    }

    /**
     * Detete a order
     * @param orderId order id or bet id
     */
    public void confirmBet(String orderId){
        int rowIndex =getOrderIndex(orderId);
        btnConfirmBet.click();
        waitPageLoad();
    }
    /**
     * Detete a order
     * @param orderId order id or bet id
     */
    public void deleteOrder(String orderId){
        int rowIndex =getOrderIndex(orderId);
        Icon.xpath(tblOrder.getxPathOfCell(1,colDelete,rowIndex,"i")).click();
        ConfirmPopupControl confirmPopupControl = ConfirmPopupControl.xpath("//app-confirm");
        confirmPopupControl.confirmYes();
    }

    /**
     * To check an orderid display in confirm bet table or not
     * @param orderID
     * @return
     */
    public boolean isOrderDisplayInTheTable(String orderID){
        int index = getOrderIndex(orderID);
        if(index == 0)
            return false;
        return true;
    }
}
