package pages.sb11.trading;

import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.control.ConfirmPopupControl;

import java.util.concurrent.TimeUnit;

import static common.ESSConstants.HomePage.EN_US;
import static common.SBPConstants.SPORT_SIGN_MAP;

public class BetSettlementPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[@class='container-fluid cbody']//div[contains(@class,'main-box-header')]");
    public DropDownBox ddbStatus = DropDownBox.xpath("//select[@name='dropStatus']");
    public DropDownBox ddbMatchDate = DropDownBox.xpath("//select[@name='dropMatchDate']");
    public TextBox txtFromDate = TextBox.xpath("//div[text()=' From Date ']//..//input");
    public TextBox txtToDate = TextBox.xpath("//div[text()=' To Date ']//..//input");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");
    public TextBox txtAccStartWith = TextBox.xpath("//input[@name='txtAccStartsWith']");
    public TextBox txtAccountCode = TextBox.xpath("//input[@name='txtAccountCode']");
    public Button btnSearch = Button.xpath("//div[@class='container-fluid cbody']//button[contains(@class,'icon-search-custom')]");
    public Link lnkShowAccount = Link.xpath("//span[contains(@class='cursor-pointer show-acc')]");
    public Link lnkMoreFilter = Link.xpath("//button[contains(@containerclass,'dropdown-list-btn')]");
    public Link lnkResetAllFilter = Link.xpath("//span[contains(@class,' reset-all-filters-btn')]");
    public Button btnSettleSendSettlementEmail = Button.xpath("//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[1]");
    public Button btnSendBetListEmail = Button.xpath("//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[2]");
    public Button btnExportSelectedBet = Button.xpath("//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[3]");
    public Button btnUpdate = Button.xpath("//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[4]");
    public Button btnDelete = Button.xpath("//div[@class='container-fluid cbody']/div[2]//div[contains(@class,' d-inline-block')]/div[2]//button[5]");
    public Label lblAccStart = Label.xpath("//div[contains(text(),'Acc Starts With')]");
    public Label lblAccCode = Label.xpath("//div[contains(text(),'Account Code')]");

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
    public Table tblOrder = Table.xpath("//table",colTotal);
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
           if(ddbMatchDate.isDisplayed()) {
               ddbMatchDate.selectByVisibleContainsText("Specific Date");
           }
           String currentFromDate = txtFromDate.getAttribute("value");
           if(!fromDate.equals(currentFromDate)){
               dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
           }
       }
       if(!toDate.isEmpty())
       {
           String currentToDate = txtFromDate.getAttribute("value");
           if(!toDate.equals(currentToDate)){
               dtpToDate.selectDate(toDate,"dd/MM/yyyy");
           }
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
            lblOrderID = Label.xpath(tblOrder.getxPathOfCell(1,colBRBettrefId,i,null));
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

    private void selectOrder(Order order){
        int rowIndex =getOrderIndex(order.getBetId());
        CheckBox cb = CheckBox.xpath(tblOrder.getxPathOfCell(1,colSelect,rowIndex,"input"));
        cb.scrollToThisControl(false);
        cb.click();
    }

    /**
     * Detete a order
     * @param order order id or bet id
     */
    public void deleteOrder(Order order){
        selectOrder(order);
        btnDelete.scrollToTop();
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

    public String getWinlossAmountofOrder(Order order)  {
        int rowindex = getOrderIndex(order.getBetId());
        String winloss = tblOrder.getControlOfCell(1, colWinLoss,rowindex,null).getText();
        if(winloss.equals("")){
            //wait for the bet is settled in 3s
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return winloss;
    }

    /**
     * Verify order in Bet Settlement page
     * @param order order info
     */
    public void verifyOrderInfo(Order order){
        int rowindex = getOrderIndex(order.getBetId());
        String brBetrefID = tblOrder.getControlOfCell(1, colBRBettrefId,rowindex,null).getText();
        String sport = tblOrder.getControlOfCell(1, colSport,rowindex,null).getText();
        String eventLeague = tblOrder.getControlOfCell(1, colEvenLeagueName,rowindex,null).getText();
        String selection = tblOrder.getControlOfCell(1, colSelection,rowindex,null).getText();
        String hdp = tblOrder.getControlOfCell(1, colHdp,rowindex,null).getText();
        String live = tblOrder.getControlOfCell(1, colLive,rowindex,null).getText();
        String odds = tblOrder.getControlOfCell(1, colOdds,rowindex,null).getText();
        String stake = tblOrder.getControlOfCell(1, colStake,rowindex,null).getText();
     //   String winloss = tblOrder.getControlOfCell(1, colWinLoss,rowindex,null).getText();
        String beType = tblOrder.getControlOfCell(1, colBetType,rowindex,null).getText();
        Assert.assertTrue(brBetrefID.contains(brBetrefID), "Failed BR BetRef id 123 SB Betfef id at row "+rowindex+" is incorrect");
        Assert.assertEquals(sport,SPORT_SIGN_MAP.get(order.getEvent().getSportName()), "Failed Sport at row "+rowindex+" is incorrect");
        Assert.assertEquals(eventLeague,String.format("%s -vs- %s\n[%s]",order.getEvent().getHome(), order.getEvent().getAway(), order.getEvent().getLeagueName().toUpperCase()), "Failed! Event League Name at row "+rowindex+" is incorrect");
        Assert.assertEquals(selection,order.getSelection(), "Failed! Selection at row "+rowindex+" is incorrect");

        String expectedBetType = order.getMarketType();
        String expectedHDP ="";
        String hdpSign = order.isNegativeHdp() ? "-" : "";
        if(order.getEvent().getSportName().equalsIgnoreCase("Soccer")){
            if (order.getMarketType().contains(("1x2"))){
                expectedBetType = String.format("%s-%s", EN_US.get(order.getStage()), "1x2");
            } else {

                expectedBetType = String.format("%s-%s", EN_US.get(order.getStage()), EN_US.get(expectedBetType));
                expectedHDP = String.format("%s%s", hdpSign, order.getHdpPoint());
            }
            Assert.assertEquals(live,  String.format("%s - %s", order.getLiveHomeScore(),order.getLiveHomeScore()), "Failed! Live is incorrect");
        }else if (order.getEvent().getSportName().equalsIgnoreCase("Cricket")) {
            expectedHDP = String.format("%s%s / %s%s", hdpSign, order.getHandicapWtks(), hdpSign, order.getHandicapRuns());
            Assert.assertEquals(live, "", "Failed! Live is incorrect");
        }else {
            expectedBetType = "MB";
        }

        Assert.assertEquals(hdp,expectedHDP, "Failed! HDP at row "+rowindex+" is incorrect");
        Assert.assertEquals(odds,String.format("%.3f (%s)", order.getPrice(),order.getOddType()), "Failed! Odds at row "+rowindex+" is incorrect");
        Assert.assertEquals(stake,String.format("%.2f", order.getRequireStake()), "Failed! Stake at row "+rowindex+" is incorrect");
        //Assert.assertEquals(winloss,"", "Failed!  Win loss at row "+rowindex+" is incorrect");
        Assert.assertEquals(beType,expectedBetType, "Failed! Bet Type at row "+rowindex+" is incorrect");
    }

    /**
     * To check an orderid display in confirm bet table or not
     * @param order
     * @return
     */
    public boolean isOrderDisplayInTheTable(Order order){
        int index = getOrderIndex(order.getBetId());
        if(index == 0)
            return false;
        return true;
    }

    public void settleAndSendSettlementEmail(Order order){
        try {
            Thread.sleep(8000); //hard code sleep action for waiting report to generate Win/Lose
            btnSearch.click();
            waitSpinnerDisappeared();
            selectOrder(order);
            btnSettleSendSettlementEmail.scrollToTop();
            btnSettleSendSettlementEmail.click();
            ConfirmPopupControl confirmPopupControl = ConfirmPopupControl.xpath("//app-confirm");
            confirmPopupControl.confirmYes();
        } catch (InterruptedException e) {
            System.out.println("Failed! Win/Lose data is not shown!");
        }
    }
    public void sendBetListEmail(Order order){
        //to wait the order is have win loss result
        getWinlossAmountofOrder(order);
        selectOrder(order);
        btnSendBetListEmail.scrollToTop();
        btnSendBetListEmail.click();
    }
    public void exportSelectedBEt(Order order){
        selectOrder(order);
        btnExportSelectedBet.scrollToTop();
        btnExportSelectedBet.click();
    }
}
