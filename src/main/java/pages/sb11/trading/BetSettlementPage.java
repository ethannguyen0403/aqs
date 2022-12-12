package pages.sb11.trading;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import pages.sb11.WelcomePage;
import pages.sb11.control.ConfirmPopupControl;
import testcases.sb11test.trading.BetEntryTest;

public class BetSettlementPage extends WelcomePage {
    Label lblTitle = Label.xpath("//app-bet-settlement//div[@class='container-fluid cbody']//div[contains(@class,'main-box-header')]");
    public DropDownBox ddbStatus = DropDownBox.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[1]//select");
    public DropDownBox ddbMatchDeate = DropDownBox.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[2]//select");
    public TextBox txtAccStartWith = TextBox.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[3]//input");
    public TextBox txtAccountCode = TextBox.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[4]//input");
    public Button btnSearch = Button.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[4]//button[contains(@class,'icon-search-custom')]");
    public Link lnkShowAccount = Link.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[5]//span");
    public Link lnkMorFilter = Link.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[6]//button");
    public Link lnkResetAllFilter = Link.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[1]//div[contains(@class,'card-body')]/div[7]//span");
    public Button btnSettleSendSettlementEmail = Button.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[1]");
    public Button btnSendBetListEmail = Button.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[2]");
    public Button btnExportSlectedBet = Button.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[3]");
    public Button btnUpdate = Button.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[4]");
    public Button btnDelete = Button.xpath("//app-bet-settlement//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[5]");

    int colTotal = 14;
    int coli = 2;
    int colBetDateEventDate = 3;
    int colBRBettrefId = 4;
    int colSport = 5 ;
    int colEvenLeagueName = 6;
    int colSelection = 7;
    int colHdp = 8;
    int colLive = 9;
    int colOdds = 10;
    int colStake = 11;
    int colWinLoss =12;
    int colBetType = 13;
    int colSelect = 14;
    public Table tblOrder = Table.xpath("//app-bet-settlement//table",colTotal);
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    /**
     * Filter Confirm bets with input value
     * @param accStartWith
     * @param status
     * @param fromDate
     * @param toDate
     * @param accountCode
     */
    public void filter(String status, String fromDate, String toDate, String accStartWith, String accountCode){
       ddbStatus.selectByVisibleText(status);
       if(!fromDate.isEmpty())
       {

       }
       if(!toDate.isEmpty())
       {

       }
        if(!accStartWith.isEmpty())
        {
            txtAccStartWith.sendKeys(accStartWith);

        }
        txtAccountCode.sendKeys(accountCode);
        btnSearch.click();
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
            lblOrderID = Label.xpath(tblOrder.getxPathOfCell(1,colBRBettrefId,i,"span[@class='text-secondary']"));
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
     * @param order order id or bet id
     */
    public void deleteOrder(Order order){
        int rowIndex =getOrderIndex(order.getOrderId());
        Icon.xpath(tblOrder.getxPathOfCell(1,colSelect,rowIndex,"input")).click();
        btnDelete.click();
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

    /**
     *
     * @param order
     */
    public void verifyOrderInfo(Order order){

    }
}
