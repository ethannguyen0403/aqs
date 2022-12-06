package pages.sb11.trading;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.control.ConfirmPopupControl;

public class BetSettlementPage extends WelcomePage {
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
    public Button btnConfirm = Button.xpath("//button[text()='Confirm Bet']");
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
