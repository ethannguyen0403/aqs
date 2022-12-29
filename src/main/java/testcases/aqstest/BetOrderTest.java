package testcases.aqstest;

import com.paltech.utils.DateUtils;
import common.ESSConstants;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ess.popup.*;
import testcases.BaseCaseAQS;
import utils.aqs.GetOrdersUtils;
import utils.aqs.PlaceOrderUtils;
import utils.testraildemo.TestRails;

import java.text.ParseException;
import java.util.*;

import static common.ESSConstants.BetOrderPage.*;
import static common.ESSConstants.OrderLog.TABLE_HEADER;
import static common.ESSConstants.OrderLog.TABLE_HEADER_BET_LIST;
import static utils.aqs.PlaceOrderUtils.placeOverUnder;

public class BetOrderTest extends BaseCaseAQS {

    @TestRails(id = "465")
    @Test(groups = {"smoke1"})
    public void BetOrder_001(){
        log("@title: Verify Bet Order Page display by default after login");
        log("@Step 1: Login with valid account");
        log("Verify Menu AQS is active");
        Assert.assertTrue(betOrderPage.isMenuActive(betOrderPage.menuAQS), "FAILED! Menu AQS is not active");
        log("Verify Bet Order title is displayed");
        Assert.assertEquals(betOrderPage.lblBetOrders.getText(),"Bet Orders","FAILED! Bet Orders title is incorrect display");

        log("INFO: Executed completely");
    }

    @TestRails(id = "466")
    @Test(groups = {"smoke"})
    public void BetOrder_002(){
        log("@title: Verify Bet Order Page display by default after login");
        log("@Step 1: Login with valid account");
        log("Verify  Order bet form is correctly display");
        Assert.assertEquals(betOrderPage.lblSport.getText(),"Sport","FAILED! Sport title is incorrect display");
        Assert.assertTrue(betOrderPage.ddSport.isDisplayed(), "FAILED! Sport dropdown is not displayed");
        Assert.assertEquals(betOrderPage.lblFromDate.getText(),"From Date","FAILED! From Date title is incorrect display");
        Assert.assertTrue(betOrderPage.txtFromDate.isDisplayed(), "FAILED! From Date date time picker is not displayed");
        Assert.assertEquals(betOrderPage.lblToDate.getText(),"To Date","FAILED! To Date title is incorrect display");
        Assert.assertTrue(betOrderPage.txtToDate.isDisplayed(), "FAILED! To Date date time picker is not displayed");
        Assert.assertTrue(betOrderPage.btnShow.isDisplayed(), "FAILED! Show button is not displayed!");

        log("Verify Pending, Confirm, Cancelled table display with correct header");
        Assert.assertEquals(betOrderPage.lblPending.getText(),PENDING,"FAILED! Pending title is incorrect display");
        Assert.assertEquals(betOrderPage.tbPending.getHeaderNameOfRows(), ESSConstants.BetOrderPage.TABLE_HEADER,"FAILED! Pending table header is incorrect display");
        Assert.assertEquals(betOrderPage.lblConfirm.getText(),CONFIRM,"FAILED! Confirm title is incorrect display");
        Assert.assertEquals(betOrderPage.tbConfirm.getHeaderNameOfRows(), ESSConstants.BetOrderPage.TABLE_HEADER,"FAILED! Confirm table header is incorrect display");
        Assert.assertEquals(betOrderPage.lblCancel.getText(),CANCELLED,"FAILED! Cancelled title is incorrect display");
        Assert.assertEquals(betOrderPage.tbCancelled.getHeaderNameOfRows(), ESSConstants.BetOrderPage.TABLE_HEADER,"FAILED! Cancelled table header is incorrect display");
        log("INFO: Executed completely");
    }

   @TestRails(id = "467")
   @Test(groups = {"smoke"})
    public void BetOrder_003() throws ParseException {
        log("@title: Verify Bet List popup displayed correctly info: popup title, event info, table header");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-3,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-3,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log("@Step 2: Click on AQS menu");
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Click Bets link at Selection column  on any table: Pending,  Confirm, Cancelled");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CONFIRM);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Confirm order");
            return;}
        BetListPopup betListPopup = betOrderPage.openBetList(orderLst.get(0).getOrderId(),CONFIRM);

        log("Verify Bet List popup should display with correct info:");
        log("Verify 1. Title:AQS Bet Orders > Bet List");
        Assert.assertEquals(betListPopup.lblTitle.getText(),"AQS Bet Orders > Bet List", "FAILED! Bet list title is displayed incorrectly!");

        log("Verify 2. Event info: Match Date | Event Name | League name");
        String dateconvert = DateUtils.convertDateToNewTimeZone(orderLst.get(0).getEventDate(),"yyyy-MM-dd'T'HH:mm:ss.SSSXXX","","d/MM/yyyy HH:mm","GMT+8");
        Assert.assertTrue(betListPopup.lbleventInfo.getText().contains(dateconvert),"FAILED! Event date is displayed incorrectly!");
        Assert.assertTrue(betListPopup.lbleventInfo.getText().contains(orderLst.get(0).getHome()), "FAILED! Home team name is displayed incorrectly!");
        Assert.assertTrue(betListPopup.lbleventInfo.getText().contains(orderLst.get(0).getAway()), "FAILED! Away team name is displayed incorrectly!");
        Assert.assertTrue(betListPopup.lbleventInfo.getText().contains(orderLst.get(0).getCompetitionName()), "FAILED! League name is displayed incorrectly!");

        log("3. The table header is corrected displayed");
        Assert.assertEquals(betListPopup.tblOrder.getColumnNamesOfTable(),TABLE_HEADER_BET_LIST,String.format("Failed! Header Table is incorrect"));
        log("INFO: Executed completely");
    }

    @TestRails(id = "468")
    @Test(groups = {"smoke"})
    public void BetOrder_004() throws ParseException {
        log("@title: Verify Bet Slip in Pending section display with correct info");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log("@Step 2: Click on AQS menu");
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Click Bets link at Action column in Pending section");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Pending order");
            return;}
        BetSlipPopup betSlipPopup = betOrderPage.openBetSlip(orderLst.get(0).getOrderId(),PENDING);

        log("@Verify Bet Slip popup display with correct info: Start date, League - Event name - Market type");
        String dateconvert = DateUtils.convertDateToNewTimeZone(orderLst.get(0).getEventDate(),"yyyy-MM-dd'T'HH:mm:ss.SSSXXX","","dd/MM HH:mm","GMT+8");
        Assert.assertTrue(betSlipPopup.lblHeader.getText().contains(dateconvert),"FAILED! Event date is displayed incorrectly!");
        Assert.assertTrue(betSlipPopup.lblHeader.getText().contains(orderLst.get(0).getHome()), "FAILED! Home team name is displayed incorrectly!");
        Assert.assertTrue(betSlipPopup.lblHeader.getText().contains(orderLst.get(0).getAway()), "FAILED! Away team name is displayed incorrectly!");
        Assert.assertTrue(betSlipPopup.lblHeader.getText().contains(orderLst.get(0).getCompetitionName()), "FAILED! League name is displayed incorrectly!");
        Assert.assertTrue(betSlipPopup.lblHeader.getText().contains(orderLst.get(0).getMarketType()), "FAILED! Market type is displayed incorrectly!");

        log("INFO: Executed completely");
    }

    @TestRails(id = "469")
    @Test(groups = {"smoke"})
    public void BetOrder_005(){
        log("@title: Verify control in Bet Slip display correctly");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log("@Step 2: Click on AQS menu");
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Click Bets link at Action column in Pending section");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Pending order");
            return;}
        BetSlipPopup betSlipPopup = betOrderPage.openBetSlip(orderLst.get(0).getOrderId(),PENDING);

        log("@Verify control in Bet Slip display correctly");
        Assert.assertTrue(betSlipPopup.btnSelectAll.isDisplayed(),"Failed! Select All button is not displayed");
        Assert.assertTrue(betSlipPopup.btnDelSelect.isDisplayed(),"Failed! Delete Selected button is not displayed");
        Assert.assertTrue(betSlipPopup.btnPlaceBet.isDisplayed(),"Failed! Place Bet button is not displayed");
        Assert.assertTrue(betSlipPopup.ddMaster.isDisplayed(),"Failed! Dropdown Master Account is not displayed");
        Assert.assertTrue(betSlipPopup.btnClose.isDisplayed(),"Failed! Close button is not displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "470")
    @Test(groups = {"smoke"})
    public void BetOrder_006(){
        log("@title: Verify Bet Slip in Cancelled section is disable to place bet");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log("@Step 2: Click on AQS menu");
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Click Bets link at Action column  in Cancelled section");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CANCELLED);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Cancelled order");
            return;}
        BetSlipPopup betSlipPopup = betOrderPage.openBetSlip(orderLst.get(0).getOrderId(),CANCELLED);

        log("@Verify all control is visible and cannot place bet");
        Assert.assertTrue(betSlipPopup.btnSelectAll.isInvisible(1),"Failed! Select All button is displayed");
        Assert.assertTrue(betSlipPopup.btnSelectAll.isInvisible(1),"Failed! Delete Selected button is displayed");
        log("INFO: Executed completely");
    }

    @TestRails(id = "471")
    @Test(groups = {"smoke_qc"})
    public void BetOrder_007(){
        log("@title: Verify message confirm display when click on BET link in Action column in Confirm section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log("@Step 2: Click on AQS menu");
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Click Bets link at Action column in Confirm section");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CONFIRM);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Confirm order");
            return;}
        ConfirmPopup confirmPopup = betOrderPage.openConfirmPopup(orderLst.get(0).getOrderId());

        log("@Verify the message confirm display The order already settled! Do you want to continue? With yes no button");
        Assert.assertEquals(confirmPopup.lblConfirm.getText(), CONFIRM, "FAILED! Confirm title is not displayed!");
        Assert.assertEquals(confirmPopup.msgConfirm.getText(), "The order already settled! Do you want to continue?", "FAILED! Message is displayed incorrectly!");
        Assert.assertTrue(confirmPopup.btnYes.isDisplayed(),"Failed! Yes button is displayed!");
        Assert.assertTrue(confirmPopup.btnNo.isDisplayed(),"Failed! No button is displayed!");
        log("INFO: Executed completely");
    }

    @TestRails(id = "472")
    @Test(groups = {"smoke"})
    public void BetOrder_008(){
        log("@title: Verify Bet Slip popup display when Confirming in Confirm section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log("@Step 2: Click on AQS menu");
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Click Bets link at Action column in Confirm section");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer","CONFIRM");
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Confirm order");
            return;}
        ConfirmPopup confirmPopup = betOrderPage.openConfirmPopup(orderLst.get(0).getOrderId());

        log("@Step 4: Click Yes on confirm message popup");
        confirmPopup.clickConfirmPopup("YES");

        log("@Verify Bet Slip popup display");
        BetSlipPopup betSlipPopup = new BetSlipPopup();
        Assert.assertTrue(betSlipPopup.betSlipPopup.isDisplayed(),"Failed! Bet Slip popup is not displayed");

        log("INFO: Executed completely");
    }

    @TestRails(id = "473")
    @Test(groups = {"smoke"})
    public void BetOrder_009(){
        log("@title: Verify Bet Slip popup does not display when unconfirm in Confirm section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log("@Step 2: Click on AQS menu");
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Click Bets link at Action column in Confirm section");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer","CONFIRM");
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Confirm order");
            return;}
        ConfirmPopup confirmPopup = betOrderPage.openConfirmPopup(orderLst.get(0).getOrderId());

        log("@Step 4: Click No on confirm message popup");
        confirmPopup.clickConfirmPopup("NO");

        log("@Verify 1: Confirm message disappear");
        Assert.assertFalse(confirmPopup.msgConfirm.isDisplayed(),"Failed! Confirm message is still displayed");

        log("Verify 2: Bet Slip popup not display");
        BetSlipPopup betSlipPopup = new BetSlipPopup();
        Assert.assertFalse(betSlipPopup.betSlipPopup.isDisplayed(),"Failed! Bet Slip popup is still displayed");

        log("INFO: Executed completely");
    }

    @TestRails(id = "474")
    @Test(groups = {"smoke_qc"})
    public void BetOrder_010(){
        log("@title: Verify order display in Confirm when clicking Confirm link Pending section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-4,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-4,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Get Order API in Pending status");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data in Confirm order");
            return;}

        String orderID=orderLst.get(0).getOrderId();
        log("@Step 4: In Pending section, click on Confirm link of an order "+ orderID);
        betOrderPage.clickControlInTable(PENDING, orderID, CONFIRM);

        log("verify 1. Verify bet is move to Confirm section");
        betOrderPage.verifyOrderInfo(orderLst.get(0),CONFIRM);

        log("Post-condition: Move the order from Confirm to Pending section");
        betOrderPage.clickControlInTable(CONFIRM, orderID,PENDING);
        log("INFO: Executed completely");
    }

    @TestRails(id = "475")
    @Test(groups = {"smoke_qc"})
    public void BetOrder_011(){
        log("@title: Verify order display in Cancelled section when clicking Cancel link Pending section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-4,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-4,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Get Order API in Pending status");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data in Cancelled order");
            return;}

        String orderID=orderLst.get(0).getOrderId();
        log("@Step 4: In Pending section, click on Cancle link of an order "+ orderID);
        betOrderPage.clickControlInTable(PENDING, orderID, CANCEL);

        log("verify 1. Verify bet is move to Cancelled section");
        betOrderPage.verifyOrderInfo(orderLst.get(0),CANCELLED);

        log("Post-condition: Move the order from Cancelled to Pending section");
        betOrderPage.clickControlInTable(CANCELLED, orderID,PENDING);
        log("INFO: Executed completely");
    }

    @TestRails(id = "476")
    @Test(groups = {"smoke"})
    @Parameters(("username"))
    public void BetOrder_012(String username){
        log("@title: Verify order display in Pending when clicking Pending link Confirm section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-4,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-4,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));
        String orderID = "9fbe42d5-4308-46ec-isa805-autoid" + DateUtils.getMilliSeconds();

            log(String.format("@Precondition Step: Place an order %s", orderID));
            Map<String, String> headersParam = new HashMap<String, String>() {
                {
                    put(String.format("Operator-Name", "\"%s\""), username);
                    put("Agent-Name", "Simulator");
                    put("Content-Type", "application/json");
                }
            };
        try {
            PlaceOrderUtils.prepareOrder(orderID, headersParam);
            placeOverUnder(orderID, headersParam);

            log(String.format("@Step 2: Filter Soccer data from %s to %s ", fromDate, toDate));
            betOrderPage.filterBetOrders(fromDate, toDate, "Soccer", true);

            log(String.format("@Step 3: Click confirm link of order %s in Pending section", orderID));
            List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI, toDateAPI, "Soccer", CONFIRM);
            Order order = GetOrdersUtils.getOrderInfoById(orderLst, orderID);
            betOrderPage.clickControlInTable(PENDING, orderID, CONFIRM);

            log("@Step 4: In Confirm section, click on Pending link of the order " + orderID);
            betOrderPage.clickControlInTable(CONFIRM, orderID, PENDING);

            log("verify 11 Verify bet is move to Pending section");
            betOrderPage.verifyOrderInfo(order, PENDING);
        }finally {
            log("Post-condition: Move the order from Pending to Cancel section");
            PlaceOrderUtils.cancelOrder(orderID,headersParam);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id = "477")
    @Test(groups = {"smoke_qc"})
    @Parameters(("username"))
    public void BetOrder_013(String username){
        log("@title: Verify order display in Pending when clicking Pending link Cancelled section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-1,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));
        String orderID = "9fbe42d5-4308-46ec-isa805-autoid" + DateUtils.getMilliSeconds();
        Map<String, String> headersParam = new HashMap<String, String>()
        {
            {
                put(String.format("Operator-Name","\"%s\""),username) ;
                put("Agent-Name", "Simulator");
                put("Content-Type", "application/json");
            }
        };
        log("@Step 3: Place a Order API in Cancelled status");
        PlaceOrderUtils.prepareOrder(orderID,headersParam);
        PlaceOrderUtils.placeOverUnder(orderID, headersParam);
        PlaceOrderUtils.cancelOrder(orderID,headersParam);
        log(String.format("@Step 2: Filter Soccer data from %s to %s ", fromDate, toDate));
        betOrderPage.filterBetOrders(fromDate, toDate, "Soccer", true);
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI, toDateAPI, "Soccer", CANCELLED);
        Order order = GetOrdersUtils.getOrderInfoById(orderLst,orderID);
        try {
            log("@Step 4: In Cancelled section, click on Pending link of the order " + orderID);
            betOrderPage.clickControlInTable(CANCELLED, orderID, PENDING);

            log("verify 1 Verify bet is move to Pending section");
            betOrderPage.verifyOrderInfo(order, PENDING);

        }finally {
            log("Post-condition: Move the order from Pending to Cancelled section");
            PlaceOrderUtils.cancelOrder(orderID,headersParam);
        }

        log("INFO: Executed completely");
    }

    @TestRails(id = "478")
    @Test(groups = {"smoke_qc"})
    @Parameters(("username"))
    public void BetOrder_014(String username){
        log("@title: Verify order display in Confirm when clicking Confirm link Cancelled section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-9,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-9,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));
        String orderID = "9fbe42d5-4308-46ec-isa805-autoid" + DateUtils.getMilliSeconds();
        Map<String, String> headersParam = new HashMap<String, String>()
        {
            {
                put(String.format("Operator-Name","\"%s\""),username) ;
                put("Agent-Name", "Simulator");
                put("Content-Type", "application/json");
            }
        };
        PlaceOrderUtils.prepareOrder(orderID,headersParam);
        placeOverUnder(orderID, headersParam);
        PlaceOrderUtils.cancelOrder(orderID,headersParam);
        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI, toDateAPI, "Soccer", CANCELLED);
        Order order = GetOrdersUtils.getOrderInfoById(orderLst,orderID);

        log("@Step 3: Get Order API in Cancelled status");
      /*  List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CANCELLED);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data in Cancelled order");
            return;}*/
      //  String orderID=orderLst.get(0).getOrderId();

        log("@Step 4: In Cancelled section, click on Confirm link of the order "+orderID);
        try {
            betOrderPage.clickControlInTable(CANCELLED, orderID, CONFIRM);

            log("verify 11 Verify bet is move to Confirm section");
            betOrderPage.verifyOrderInfo(order, CONFIRM);
            Assert.assertFalse(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM,orderID, CONFIRM).isDisplayed(), "Failed! Confirm link is display for Confirm order");
            Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM,orderID, PENDING).isDisplayed(), "Failed! Pending link is not display for Confirm order");
            Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM,orderID, CANCEL).isDisplayed(), "Failed! Cancel link is not display for Confirm order");
            Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM, orderID, "Bet").isDisplayed(), "Failed! Bet link is not display for Confirm order");
        }finally {
            log("Post-condition: Move the order from Confirm to Cancelled section");
            PlaceOrderUtils.cancelOrder(orderID,headersParam);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id = "479")
    @Test(groups = {"smoke"})
    public void BetOrder_015(){
        log("@title: Verify there is no Pending link in Action column Pending section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Get Order API in Pending status");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data in Pending order");
            return;}

        log("verify 1 Verify there is no pending link, Confirm, Cancel, BET links are displayed");
        Assert.assertFalse(betOrderPage.getControlOnTableBasedOnOrderID(PENDING,orderLst.get(0).getOrderId(),PENDING).isDisplayed(),"Failed! Pending link is display for Pending order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(PENDING,orderLst.get(0).getOrderId(),CONFIRM).isDisplayed(),"Failed! Confirm link is not display for Pending order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(PENDING,orderLst.get(0).getOrderId(),CANCEL).isDisplayed(),"Failed! Cancel link is not display for Pending order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(PENDING,orderLst.get(0).getOrderId(),"Bet").isDisplayed(),"Failed! Bet link is not display for Pending order");

        log("INFO: Executed completely");
    }

    @TestRails(id = "480")
    @Test(groups = {"smoke"})
    public void BetOrder_016(){
        log("@title: Verify there is no Confirm link in Action column Confirm section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Get Order API in Pending status");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CONFIRM);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data in Confirm order");
            return;}

        log("verify 1 Verify there is no Confirm link,Pending, Cancel, BET links are displayed");
        Assert.assertFalse(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM,orderLst.get(0).getOrderId(),CONFIRM).isDisplayed(),"Failed! Confirm link is display for Confirm order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM,orderLst.get(0).getOrderId(),PENDING).isDisplayed(),"Failed! Pending link is not display for Confirm order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM,orderLst.get(0).getOrderId(),CANCEL).isDisplayed(),"Failed! Cancel link is not display for Confirm order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CONFIRM,orderLst.get(0).getOrderId(),"Bet").isDisplayed(),"Failed! Bet link is not display for Confirm order");

        log("INFO: Executed completely");
    }

    @TestRails(id = "481")
    @Test(groups = {"smoke"})
    public void BetOrder_017(){
        log("@title: Verify there is no Cancel link in Action column Cancelled section");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Get Order API in Cancelled status");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CANCELLED);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data in Cancelled order");
            return;}

        log("verify 1 Verify there is no Confirm link,Pending, Cancelled, BET links are displayed");
        Assert.assertFalse(betOrderPage.getControlOnTableBasedOnOrderID(CANCELLED,orderLst.get(0).getOrderId(),CANCEL).isDisplayed(),"Failed! Confirm link is display for Cancelled order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CANCELLED,orderLst.get(0).getOrderId(),PENDING).isDisplayed(),"Failed! Pending link is not display for Cancelled order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CANCELLED,orderLst.get(0).getOrderId(),CONFIRM).isDisplayed(),"Failed! Cancel link is not display for Cancelled order");
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CANCELLED,orderLst.get(0).getOrderId(),"Bet").isDisplayed(),"Failed! Bet link is not display for Cancelled order");

        log("INFO: Executed completely");
    }

    @TestRails(id = "482")
    @Test(groups = {"smoke"})
    public void BetOrder_018(){
        log("@title: Verify hide a column is worked");
        List<String> listHeader = Arrays.asList("#", "Selection", "Action", "Market","Event Date","Event English","Event Chinese","Agent - Hitter");

        log("@Step 1: Login with valid account");
        log("@Step 2: Click Hide column link and hide Bookie - OrderId column ");
        try {
            betOrderPage.hideColumnSetting("Bookie - OrderId");

            log("Verify the column Bookie - OrderId column is hidden in Pending, Cancel, Confirm table");
            Assert.assertEquals(betOrderPage.tbPending.getHeaderNameOfRows(), listHeader, "FAILED! Pending table header is incorrect display");
            Assert.assertEquals(betOrderPage.tbConfirm.getHeaderNameOfRows(), listHeader, "FAILED! Confirm table header is incorrect display");
            Assert.assertEquals(betOrderPage.tbCancelled.getHeaderNameOfRows(), listHeader, "FAILED! Cancelled table header is incorrect display");
            log("INFO: Executed completely");
        }finally {
            /**Post-condition: unhide Bookie - OrderId column**/
            betOrderPage.hideColumnSetting("Bookie - OrderId");
        }

    }

    @TestRails(id = "483")
    @Test(groups = {"smoke"})
    public void BetOrder_019(){
        log("@title: Verify hide/unhide all column is worked");
        log("@Step 1: Login with valid account");
        log("@Step 2: Hide all column setting");
        betOrderPage.hideColumnSetting("All");

        log("Verify 1: Table is not display when hide all columns");
        Assert.assertTrue(betOrderPage.tbPending.getHeaderNameOfRows().isEmpty(),"FAILED! Pending table header is incorrect display");
        Assert.assertTrue(betOrderPage.tbConfirm.getHeaderNameOfRows().isEmpty(),"FAILED! Confirm table header is incorrect display");
        Assert.assertTrue(betOrderPage.tbCancelled.getHeaderNameOfRows().isEmpty(),"FAILED! Cancelled table header is incorrect display");

        log("Step 3: Unhide all column setting");
        betOrderPage.hideColumnSetting("All");

        log("Verify 2: Table Pending, Confirm, Cancleled display all headers when unhide all columns");
        Assert.assertEquals(betOrderPage.tbPending.getHeaderNameOfRows(), ESSConstants.BetOrderPage.TABLE_HEADER,"FAILED! Pending table header is incorrect display");
        Assert.assertEquals(betOrderPage.tbConfirm.getHeaderNameOfRows(), ESSConstants.BetOrderPage.TABLE_HEADER,"FAILED! Confirm table header is incorrect display");
        Assert.assertEquals(betOrderPage.tbCancelled.getHeaderNameOfRows(), ESSConstants.BetOrderPage.TABLE_HEADER,"FAILED! Cancleled table header is incorrect display");
        log("INFO: Executed completely");
    }

    @TestRails(id = "484")
    @Test(groups = {"smoke"})
    public void BetOrder_020(){
        log("@title: Verify pending order info display in UI correctly");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Get Order API in Pending status");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);

        log("verify 1: Verify the info of pending order display correctly");
        betOrderPage.verifyDataOnTableMatchWithAPI(orderLst,PENDING);

        log("INFO: Executed completely");
    }

    @TestRails(id = "485")
    @Test(groups = {"smoke"})
    public void BetOrder_021(){
        log("@title: Verify Confirm order info display in UI correctly");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 2: Get all orders in Confirm status by API ");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CONFIRM);

        log("verify 1: Check order info in Confirm section");
        betOrderPage.verifyDataOnTableMatchWithAPI(orderLst,CONFIRM);

        log("INFO: Executed completely");
    }

    @TestRails(id = "486")
    @Test(groups = {"smoke"})
    public void BetOrder_022(){
        log("@title: Verify Cancelled order info display in UI correctly");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 2: Get all orders in Cancelled status by API ");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",CANCELLED);

        log("verify 1: Verify the info of Cancelled order display correctly");
        betOrderPage.verifyDataOnTableMatchWithAPI(orderLst,CANCELLED);

        log("INFO: Executed completely");
    }

    @TestRails(id = "487")
    @Test(groups = {"smoke"})
    public void BetOrder_023(){
        log("@title: Verify can open Order log");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s ",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log("@Step 3: Check order info in Pending section");
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Pending order");
            return;}

        log("@Step 4: In Market column of any section if having order(Pending, Confirm, Cancelled), Click on market name");
        OrderLogPopup orderLogPopup = betOrderPage.openOrderLog(orderLst.get(0).getOrderId(),PENDING);

        log("verify 1 Oder Log popup display with correct title: AQS Bet Orders  Order Logs ");
        Assert.assertEquals(orderLogPopup.lblTitle.getText(),"AQS Bet Orders Order Logs","Failed! Title popup is incorrect");

        log("verify 2 The table header is corrected displayed");
        Assert.assertEquals(orderLogPopup.tblOrder.getColumnNamesOfTable(),TABLE_HEADER,String.format("Failed! Header Table is incorrect"));

        log("INFO: Executed completely");
    }

    @TestRails(id = "488")
    @Test(groups = {"smoke"})
    public void BetOrder_024(){
        log("@title: Validate tooltip info display when click on I icon in # column");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String fromDateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT -4"));
        String toDateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT -4"));

        log(String.format("@Step 2: Filter Soccer data from %s to %s and get an order in pending section",fromDate,toDate));
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);
        List<Order> orderLst = GetOrdersUtils.getOrderByStatus(fromDateAPI,toDateAPI,"Soccer",PENDING);
        if(betOrderPage.isNodata(orderLst)){
            log("SKIP: No data to click on Market name to open order log popup in Pending order");
            return;}

        log("@Step 2  In #  column of any section if having order(Pending, Confrim, Cancelled), Click on icon i");
        betOrderPage.getControlOnTableBasedOnOrderID("PENDING", orderLst.get(0).getOrderId(),"TOOLTIP").click();

        log("verify 1 tooltip display with value: Create By, Create date, Confirm By, Confrim Date, Revert By, Revert Date ");
        Assert.assertTrue(betOrderPage.lblTooltip.isDisplayed(),"Failed! Tooltip is not display");
        String tooltipInfo = betOrderPage.lblTooltip.getText();
        Assert.assertTrue(tooltipInfo.contains("Created By"),"FAILED! No found Create By in Tooltip info");
        Assert.assertTrue(tooltipInfo.contains("Created Date"),"FAILED! No found Create By in Tooltip info");
        log("INFO: Executed completely");
    }

    @Test(groups = {"smoke_qc"})
    @Parameters(("username"))
    public void BetOrder_025(String username){
        log("@title: Validate can add order by API");
        log("@Step 1: Login with valid account");
        String fromDate = String.format(DateUtils.getDate(-2,"d/MM/yyyy","GMT -4"));
        String toDate = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT -4"));
        String orderID = "9fbe42d5-4308-46ec-isa805-autoid" + DateUtils.getMilliSeconds();
        Map<String, String> headersParam = new HashMap<String, String>()
        {
            {
                put("Operator-Name",username) ;
                put("Agent-Name", "Simulator");
                put("Content-Type", "application/json");
            }
        };
        log(String.format("@Step 1: Place "+orderID+" order by API",fromDate,toDate));
        PlaceOrderUtils.prepareOrder(orderID,headersParam);
        placeOverUnder(orderID, headersParam);
        log(String.format("@Step 2: Filter Soccer data from %s to %s and get an order in pending section",fromDate,toDate));
        betOrderPage.filterBetOrders("","","Soccer", true);

        log(String.format("Verify 1: The order place by API display in Pending section"));
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(PENDING,orderID,"BETS").isDisplayed(),"FAILED! The order "+orderID+"" +
                " not display in Pending list after creating by API");

        log(String.format("@Step 3: Cancle "+orderID+" order by API",fromDate,toDate));
        PlaceOrderUtils.cancelOrder(orderID,headersParam);
        betOrderPage.filterBetOrders(fromDate,toDate,"Soccer", true);

        log(String.format("Verify 1: The order cacnel by API display in Cancelled section"));
        Assert.assertTrue(betOrderPage.getControlOnTableBasedOnOrderID(CANCELLED,orderID,"BETS").isDisplayed(),"FAILED! The order "+orderID+"" +
                " not display in Cancelled list after cancelling by API");
        log("INFO: Executed completely");
    }
}
