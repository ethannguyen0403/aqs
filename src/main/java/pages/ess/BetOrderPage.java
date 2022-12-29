package pages.ess;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import org.testng.Assert;
import pages.ess.popup.*;

import java.util.List;

import static common.ESSConstants.HomePage.EN_US;
import static common.ESSConstants.HomePage.NORECORD;

public class BetOrderPage extends HomePage {
    public Label lblBetOrders = Label.xpath("//span[contains(text(), 'Bet Orders')]");
    public Label lblPending = Label.xpath("//div[contains(@class, 'PENDING')]");
    public Label lblConfirm = Label.xpath("//div[contains(@class, 'CONFIRM')]");
    public Label lblCancel = Label.xpath("//div[contains(@class, 'CANCELLED')]");
    public Label lblSport = Label.xpath("//div[contains(text(),'Sport')]");
    public Label lblFromDate = Label.xpath("//div[contains(text(),'From Date')]");
    public Label lblToDate = Label.xpath("//div[contains(text(),'To Date')]");
    public DropDownBox ddSport = DropDownBox.name("status");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public TextBox txtFromDate = TextBox.xpath("//div[@class='p-2']/div[contains(text(),'From Date')]//following::input[1]");
    public TextBox txtToDate = TextBox.xpath("//div[@class='p-2']/div[contains(text(),'To Date')]//following::input[1]");
    public Link lblHideColumn = Link.xpath("//div[contains(@class, 'text-right cursor-pointer')]");
    public Label lblTooltip = Label.xpath("//popover-container[@role='tooltip']");
    public Table tbPending = Table.xpath("//div[contains(@class, 'PENDING')]//following::table[1]", 9);
    public Table tbConfirm = Table.xpath("//div[contains(@class, 'CONFIRM')]//following::table[1]", 9);
    public Table tbCancelled = Table.xpath("//div[contains(@class, 'CANCELLED')]//following::table[1]", 9);
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");
    private int colNo =1;
    private int colSelection =2;
    private int colAction =3;
    private int colMarket =4;
    private int colEventDate =5;
    private int colEventEnglish =6;
    private int colEventChinese =7;
    private int colAgentHilter =8;
    private int colBookieOrderID = 9;

    public void filterBetOrders(String fromDate, String toDate, String sport, boolean isShow){
        String today = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        if(!fromDate.isEmpty()){
            if(!fromDate.equalsIgnoreCase(toDate)){
                dtpFromDate.selectDate(fromDate,"d/MM/yyyy");
            }
      }
        if(!toDate.isEmpty()) {
            if(!toDate.equalsIgnoreCase(today)){
                dtpToDate.selectDate(toDate, "d/MM/yyyy");
            }
        }
        ddSport.selectByVisibleContainsText(sport);
     /*   dtpFromDate.currentMonthWithDate(fromDate);
        dtpToDate.currentMonthWithDate(toDate);*/
        if(isShow) {
            btnShow.click();
            waitSpinLoad();
        }

    }

    private Table getTableByStatus(String status){
        String _status = status.toUpperCase();
        switch (_status){
            case "CONFIRM":
                return tbConfirm;
            case "CANCELLED":
                return tbCancelled;
            default:
                return tbPending;
        }
    }
    private void verifyDataInARowCorrectLyAsStatus(Table table, Order order, int rowIndex)
    {
        String selectionName = table.getControlOfCell(1,colSelection,rowIndex,"div[1]//span[1]").getText();
        String liveScore =table.getControlOfCell(1,colSelection,rowIndex,"div[1]//span[2]").getText();
        String stageType = table.getControlOfCell(1,colMarket,rowIndex,"a").getText(); // FT-OU
        String marketName = table.getControlOfCell(1,colMarket,rowIndex,"p").getText(); // Goald - IR
        String eventDate = table.getControlOfCell(1,colEventDate,rowIndex,"span").getText();
        String eventFullName =table.getControlOfCell(1,colEventEnglish,rowIndex,"span[1]").getText();
        String compeitionName =table.getControlOfCell(1,colEventEnglish,rowIndex,"span[2]").getText();
        String agent= table.getControlOfCell(1,colAgentHilter,rowIndex,"span[1]").getText();
        String hilter =table.getControlOfCell(1,colAgentHilter,rowIndex,"span[2]").getText();
        String operator =table.getControlOfCell(1,colAgentHilter,rowIndex,"span[3]").getText();
        String bookie = table.getControlOfCell(1,colBookieOrderID,rowIndex,"div[1]").getText();
        String orderID =table.getControlOfCell(1,colBookieOrderID,rowIndex,"div[2]").getText();

        Assert.assertEquals(selectionName,order.getSelection(),"Failed! Selection Name in Selection colum is incorrect");

        // Verify selection name in Selection column Live score
        String score = order.getClientMetadata().replace("{\"score\":\" ","").replace(" \"}","").replace(" - ",":");
        Assert.assertEquals(liveScore,score,"Failed! Score in Selection colum is incorrect");

        // Verify market column format: FT-OU
        String marketNamePhaseExpected = String.format("%s - %s",order.getMarketName(),EN_US.get(order.getPhase()));
        Assert.assertEquals(marketName,marketNamePhaseExpected,"Failed! Score in Selection colum is incorrect");

        // Verify market column format: Goals - IR
        String stageMarketType = String.format("%s-%s",EN_US.get(order.getStage()),EN_US.get(order.getMarketType()));
        Assert.assertEquals(stageType,stageMarketType,"Failed! Score in Selection colum is incorrect");

        // Verify event Date column
        //TODO: Do not know time in API to convert to UI- contact with dev for this VP
       // Assert.assertEquals(eventDate,order.getSelection(),"Failed! Score in Selection colum is incorrect");

        // Verify Event English column: Competition Name
        Assert.assertEquals(compeitionName,order.getCompetitionName(),"Failed! Competition Name in Event English colum is incorrect");

        // Verify event Event English column:Event Name
        String eventFullNameActual = String.format("%s -vs- %s",order.getHome(), order.getAway());
        Assert.assertEquals(eventFullName,eventFullNameActual,"Failed! Event Name in Event English colum is incorrect");

        // Verify Agent- Hitter column
        Assert.assertEquals(hilter,order.getHitter(),"Failed! Hitter in Agent- Hitter colum is incorrect");
        Assert.assertEquals(agent,order.getAgentName(),"Failed! Agent in Agent- Hitter colum is incorrect");
        Assert.assertEquals(operator,order.getOperator(),"Failed! Operator in Agent- Hitter colum is incorrect");

        // Verify Bookie-OrderId column
        Assert.assertEquals(bookie,order.getBookie(),"Failed! Bookie in Bookie-OrderId colum is incorrect");
        Assert.assertEquals(orderID,order.getOrderId(),"Failed! OrderId in Bookie-OrderId colum is incorrect");

    }

    private boolean verifySelectionCorrect(String actual, String expected){
        // Verify selection name in Selection column
        if(!actual.equals(expected))
        {
           // System.out.
            return false;
        }
        return true;
    }

    public boolean isNodata(List<Order> orders){
        return (orders.size() == 0) ? true : false;
    }

    public void verifyDataOnTableMatchWithAPI (List<Order> orders,String status){
        Table table = getTableByStatus(status);
        if(isNodata(orders)){
            System.out.println("There is no data in table "+status);
           Assert.assertEquals(table.getColumn(1,false).get(0).trim(),NORECORD,"Failed! No record message should display when have no data");
        }else{
            for(int i  =0 ;i < orders.size() ;i++){
                verifyDataInARowCorrectLyAsStatus(table,orders.get(i),i+1);
            }
        }
    }

    public void verifyOrderInfo (Order order,String status){
        Table table = getTableByStatus(status);
        int i = 1;
        while(true){
            Link lblOrderID =(Link)table.getControlOfCell(1,colBookieOrderID,i,"div[2]");
            if(!lblOrderID.isDisplayed()) {
                System.out.println("The order "+ order.getOrderId() +" does not display in the list");
                return ;
            }
            if(lblOrderID.getText().trim().equals(order.getOrderId())){
                verifyDataInARowCorrectLyAsStatus(table,order,i);
                return;
            }
            i= i+1;
        }

    }

    public BetSlipPopup openBetSlip(String orderID, String status){
        getControlOnTableBasedOnOrderID(status,orderID,"Bet").click();
        return new BetSlipPopup();
    }

    public BetSlipPopup openConfirmPopup (String orderID){
        getControlOnTableBasedOnOrderID("Confirm",orderID,"Bet").click();
        return new BetSlipPopup();
    }

    public BetListPopup openBetList(String orderID,String status){
        getControlOnTableBasedOnOrderID(status,orderID,"Bets").click();
        return new BetListPopup();

    }
    public OrderLogPopup openOrderLog(String orderID,String status){
        getControlOnTableBasedOnOrderID(status,orderID,"FTOU").click();
        DriverManager.getDriver().switchToWindow();
        OrderLogPopup orderLogPopup = new OrderLogPopup();
        orderLogPopup.tblOrder.isDisplayed();
        return orderLogPopup;

    }

    public void clickControlInTable(String status, String orderID, String controlName){
        getControlOnTableBasedOnOrderID(status, orderID,controlName).click();
        waitSpinLoad();
    }

    public Link getControlOnTableBasedOnOrderID(String status, String orderID,String controlName){
        Table table = getTableByStatus(status);
        String _controlName = controlName.toUpperCase();
        switch (_controlName){
            case "BETS":
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colSelection,"u",false,false);
            case "CONFIRM":
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colAction,"a[contains(@tooltip,'Confirm')]",false,false);
            case "CANCEL":
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colAction,"a[contains(@tooltip,'Cancel')]",false,false);
            case "PENDING":
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colAction,"a[contains(@tooltip,'Pending')]",false,false);
            case "BET":
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colAction,"span[contains(@tooltip,'Place Bet')]",false,false);
            case "TOOLTIP":
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colNo,"i",false,false);
            default:
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colMarket,"a",false,false);
           /* default:
                return (Link)table.getControlBasedValueOfDifferentColumnOnRow(orderID,1,colBookieOrderID,1,
                        "div[2]",colNo,"a",false,false);*/
        }
    }

    public void hideListColumns(List<String> lstColumn){
        ColumnSettingPopup popup = openUserColumnSetting();
        popup.hideListColumn(lstColumn);
        waitSpinLoad();
    }
    public void hideColumnSetting(String column){
        ColumnSettingPopup popup = openUserColumnSetting();
        popup.hideColumn(column);
        waitSpinLoad();
    }

    public ColumnSettingPopup openUserColumnSetting(){
        lblHideColumn.click();
        return new ColumnSettingPopup();
    }
}
