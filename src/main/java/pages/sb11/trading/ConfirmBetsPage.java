package pages.sb11.trading;

import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.control.ConfirmPopupControl;
import java.util.List;
import java.util.Objects;

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
    public Table tblOrder = Table.xpath("//app-confirm-bet//div[@id='customTable']//table[contains(@aria-label,'bet table')]",colTotal);
    public Button btnUpdateBet = Button.xpath("//button[text()='Update Bet']");
    public Button btnDuplcateBetForSPBPS7 = Button.xpath("//button[text()='Duplicate Bet For SPBPS7']");
    public Label lblSelectAll = Label.xpath("//app-confirm-bet//span[text()='Select All']");
    public Label lblDeleteSelected = Label.xpath("//app-confirm-bet//span[text()='Delete Selected']");
    public Button btnConfirmBet = Button.xpath("//app-confirm-bet//button[text()='Confirm Bet']");
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
        waitPageLoad();
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
        String orderID = tblOrder.getControlOfCell(1, colEvent, orderIndex, "div[contains(@class,'row')][2]/div[contains(@class,'col')][1]/span").getText().trim();
        String betDate = tblOrder.getControlOfCell(1, colBetDate, orderIndex, null).getText().trim();
        String selection =  DropDownBox.xpath(tblOrder.getxPathOfCell(1, colSelection, orderIndex, "select")).getFirstSelectedOption().trim();
        String odds = TextBox.xpath(tblOrder.getxPathOfCell(1, colOdds, orderIndex, "input")).getAttribute("value").trim();
        String oddsType = tblOrder.getControlOfCell(1, colOdds, orderIndex, "span").getText().trim();
        String bl = DropDownBox.xpath(tblOrder.getxPathOfCell(1, colBL, orderIndex, "select")).getFirstSelectedOption().trim();
        String stake =TextBox.xpath(tblOrder.getxPathOfCell(1, colStake, orderIndex, "input")).getAttribute("value").trim();
        String bt =tblOrder.getControlOfCell(1, colBT, orderIndex, null).getText().trim();
        String trad=tblOrder.getControlOfCell(1, colTra, orderIndex, null).getText().trim();
        String hdp="";
        if(order.getSport().equalsIgnoreCase("Cricket")){
            String handicapWtks = TextBox.xpath(tblOrder.getxPathOfCell(1, colHdp, orderIndex, "input[1]")).getAttribute("value").trim();
            String handicapRuns = TextBox.xpath(tblOrder.getxPathOfCell(1, colHdp, orderIndex, "input[2]")).getAttribute("value").trim();
            String live = Label.xpath(tblOrder.getxPathOfCell(1, colLive, orderIndex, "input[1]")).getText().trim();
            Assert.assertEquals(handicapWtks, String.format("%s",order.getHandicapWtks()), "Failed!HDP Handicap Wtks is incorrect");
            Assert.assertEquals(handicapRuns, String.format("%s",order.getHandicapRuns()), "Failed!HDP Handicap Runs is incorrect");
            Assert.assertEquals(live, "", "Failed! Live is incorrect");
        }else {
            hdp = DropDownBox.xpath(tblOrder.getxPathOfCell(1, colHdp, orderIndex, "select")).getFirstSelectedOption().trim();
            String liveHomeScore = TextBox.xpath(tblOrder.getxPathOfCell(1, colLive, orderIndex, "input[1]")).getAttribute("value").trim();
            String liveAwayScore =  TextBox.xpath(tblOrder.getxPathOfCell(1, colLive, orderIndex, "input[2]")).getAttribute("value").trim();
            Assert.assertEquals(hdp, String.format("%.2f",order.getHdpPoint()), "Failed!HDP is incorrect");
            Assert.assertEquals(liveHomeScore, String.format("%d",order.getLiveHomeScore()), "Failed!Home live score is incorrect");
            Assert.assertEquals(liveAwayScore,String.format("%d", order.getLiveAwayScore()), "Failed!Away live score is incorrect");
        }
        Assert.assertEquals(league, order.getEvent().getLeagueName(), "Failed! Selection is incorrect");
        Assert.assertEquals(eventName, String.format("%s\n" + "vs\n" +"%s",order.getEvent().getHome(),order.getEvent().getAway()), "Failed! Event name is incorrect");
        Assert.assertEquals(orderID, String.format("%s / %s",order.getOrderId(), order.getBetId()), "Failed! Order id and Bet Id is incorrect ");
        Assert.assertEquals(selection, order.getSelection(), "Failed! Stake is incorrect is in correct");
        Assert.assertEquals(odds,String.format("%.3f",order.getPrice()), "Failed!Odds is incorrect");
        Assert.assertEquals(oddsType,String.format("(%s)",order.getOddType()), "Failed! Odds Type is incorrect");
        Assert.assertEquals(bl,order.getBetType(), "Failed! Bet Type (Back/Lay )is incorrect");
        Assert.assertEquals(stake,String.format("%.2f",order.getRequireStake()), "Failed! Stake is incorrect");
        Assert.assertEquals(trad,"", "Failed! Trad is incorrect");
        String btExpected = EN_US.get(order.getMarketType());
        if(Objects.nonNull(order.getStage())){
            btExpected = String.format("%s-%s",EN_US.get(order.getStage()),EN_US.get(order.getMarketType()));
        }
        Assert.assertEquals(bt,btExpected, "Failed!BT is incorrect");
        //TODO: check script covert event start date, create bet date correct as UI, and country of League
       /* String dateconvert="";
        try {
            dateconvert = DateUtils.convertDateToNewTimeZone(order.getEvent().getEventDate(),"yyyy-MM-dd'T'HH:mm:ss.SSSXXX","","dd/MM HH:mm","GMT+8");
            Assert.assertEquals(betDate,order.getCreateDate(), "Failed! Place time is incorrect");
            Assert.assertEquals(dateEvent, order.getEventDate(), "Failed! Event date is incorrect");
        } catch (ParseException e) {
            Assert.assertTrue(false, "Failed! convert event date "+ e.toString());
        }
        Assert.assertEquals(dateEvent, dateconvert, "Failed! Event date is incorrect");
       Assert.assertEquals(country, "country of league", "Failed! Country is incorrect");*/
    }

    /**
     * Select an order then click on Confirm Bet button
     * @param order order id or bet id
     */
    public void confirmBet(Order order){
        selectBet(order,true);
        btnConfirmBet.click();
        waitPageLoad();
    }

    /**
     * Select an order then update the info
     * @param order order id or bet id
     * @param  isPending  the status of bet true is for Bet Pending, false for Bet Confirmed
     */
    public void updateOrder(Order order,boolean isPending){
        fillInfo(order,isPending);
        btnUpdateBet.click();
        waitPageLoad();
    }
    public void confirmMultipleBets(List<Order> lstOrder){
       selectBets(lstOrder,true);
       btnConfirmBet.click();
    }
    /**
     * Detete a order
     * @param order order id or bet id
     * @param isPending to define column delete icon based on filter Pending (true) or Confirm(false) status
     */
    public void deleteOrder(Order order, boolean isPending){
        int rowIndex =getOrderIndex(order.getBetId());
        Icon.xpath(tblOrder.getxPathOfCell(1,defineDeleteColIndex(isPending),rowIndex,"i")).click();
        ConfirmPopupControl confirmPopupControl = ConfirmPopupControl.xpath("//app-confirm");
        confirmPopupControl.confirmYes();
        waitPageLoad();
    }

    /**
     * This action will select multiple order then click on Delete Selected label
     * @param lstOrder the list order
     */
    public void deleteSelectedOrders(List<Order> lstOrder, boolean isPending){
        selectBets(lstOrder,isPending);
        lblDeleteSelected.click();
        ConfirmPopupControl confirmPopupControl = ConfirmPopupControl.xpath("//app-confirm");
        confirmPopupControl.confirmYes();
        waitPageLoad();

    }

    private void selectBets(List<Order> lstOrder,boolean isPending){
        for (Order order: lstOrder
        ) {
            selectBet(order,isPending);
        }
    }

    private int defineSelectColIndex(boolean isPending){
        if(isPending)
            return colSelect;
        return colSelect + 1;
    }
    private int defineDeleteColIndex(boolean isPending){
        if(isPending)
            return colDelete;
        return colDelete +1;
    }
    private void selectBet(Order order, boolean isPendingBet){
        int rowIndex =getOrderIndex(order.getBetId());
        Icon.xpath(tblOrder.getxPathOfCell(1,defineSelectColIndex(isPendingBet),rowIndex,"input")).click();
    }

    private void fillInfo(Order order,boolean isPendingBet){
        int orderIndex =getOrderIndex(order.getBetId());
        int colSelect =defineSelectColIndex(isPendingBet);
        Icon.xpath(tblOrder.getxPathOfCell(1,colSelect,orderIndex,"input")).click();
        System.out.println(String.format("Update order %s  at row %s", order.getOrderId(), orderIndex));
        DropDownBox.xpath(tblOrder.getxPathOfCell(1, colSelection, orderIndex, "select")).selectByVisibleText(order.getSelection());
        DropDownBox.xpath(tblOrder.getxPathOfCell(1, colBL, orderIndex, "select")).selectByVisibleText(order.getBetType());
        //TODO: [Isabella 12/12/20022]find the solution to update value for Odds and stake textbox in this Comfirm bet page
       // TextBox.xpath(tblOrder.getxPathOfCell(1, colOdds, orderIndex, "input[1]")).sendKeys(String.format("%s",order.getPrice()));
       /* TextBox txtOdds = TextBox.xpath(tblOrder.getxPathOfCell(1, colOdds, orderIndex, "input[1]")).sendKeys(String.format("%s",order.getPrice()));
        clearAndUpdateTextBoxValue(txtOdds,String.format("%s",order.getPrice()));*/
       // TextBox.xpath(tblOrder.getxPathOfCell(1, colStake, orderIndex, "input[1]")).sendKeys(String.format("%s",order.getRequireStake()));
     /*   TextBox txtStake =TextBox.xpath(tblOrder.getxPathOfCell(1, colStake, orderIndex, "input[1]"));//.sendKeys(String.format("%s",order.getRequireStake()));
        clearAndUpdateTextBoxValue(txtStake,String.format("%s",order.getRequireStake()));*/
        if(order.getSport().equalsIgnoreCase("Cricket")){
            TextBox.xpath(tblOrder.getxPathOfCell(1, colHdp, orderIndex, "input[1]")).sendKeys(String.format("%s",order.getHandicapWtks()));
            TextBox.xpath(tblOrder.getxPathOfCell(1, colHdp, orderIndex, "input[2]")).sendKeys(String.format("%s",order.getHandicapRuns()));
        }else {
            DropDownBox.xpath(tblOrder.getxPathOfCell(1, colHdp, orderIndex, "select")).selectByVisibleText(String.format("%.2f",order.getHdpPoint()));
        }
    }

    private void clearAndUpdateTextBoxValue(TextBox txt, String value){
        txt.doubleClick();
        txt.sendKeys(value);
    }
    /**
     * To check an orderid display in confirm bet table or not
     * @param order
     * @return
     */
    public boolean isOrderDisplayInTheTable(Order order){
        int index = getOrderIndex(order.getOrderId());
        if(index == 0)
            return false;
        return true;
    }
    /**
     * To check an orderid display in confirm bet table or not
     * @param lstOrder
     * @return
     */
    public boolean isOrdersDisplayInTheTable(List<Order> lstOrder){
        for (Order order: lstOrder
             ) {
            int index = getOrderIndex(order.getBetId());
            if(index == 0)
            {
                System.out.println("The order "+ order.getBetId()+" does not display in the ");
                return false;
            }
        }
        return true;
    }
}
