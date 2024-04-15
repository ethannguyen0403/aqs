package testcases.sb11test.trading;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.BetSettlementPage;
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.SoccerBetEntryPage;
import testcases.BaseCaseAQS;
import utils.sb11.*;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.SBPConstants.*;
import static common.SBPConstants.BetSettlement.BET_LIST_STATEMENT_EMAIL;

public class BetSettlementTest extends BaseCaseAQS {
    @TestRails(id="187")
    @Test(groups = {"smoke","ethan"})
    @Parameters({"accountCode","clientCode"})
    public void BetSettlement_TC187(String accountCode,String clientCode) throws IOException {
        log("@title: Validate Win/loss amounts are calculated correctly when having Account Percentage setting");
        log("@pre-condition 1: User has permission to access Bet Settlement page");
        log("@pre-condition 2: Having an account with Confirmed bet settle Win/Lose and configuring the Account Percentage");
        log("@pre-condition 3: Active Trading > Account Percentage and Search the account then get Actual WinLoss % value of the account");
        String superMasterCode = "QA2112 - ";
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String clientId = ClientSystemUtils.getClientId(clientCode);
        clientCode = superMasterCode + clientCode;
        AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode,1.0);
        //Having Pending Bet
        String sport = "Soccer";
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder()
                .event(event)
                .accountCode(accountCode)
                .marketName("Goals")
                .marketType("HDP")
                .selection(event.getHome())
                .stage("FullTime")
                .odds(1.75)
                .handicap(2.15)
                .oddType("HK")
                .requireStake(15.50)
                .betType("BACK")
                .build();
        BetEntrytUtils.placeBetAPI(order);
        List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(order);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        //Confirm Bet
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(60);

        log("@Step 2:Navigate to Trading > Bet Settlement ");
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        log("@Step 3.Search account at precondition in Confirmed mode > observe any bet that is having Win/Lose data\n");
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7));
        betSettlementPage.filter("Confirmed",date,date,"",accountCode);
        log("@Verify 1 : Win/loss amounts should calculate correctly with Account Percentage following \n"+
       " Confirmed status, Win/Lose = actual win/loss % * win/lose amount \n"+ " Settled Status, Win/Lose = actual win/loss % * win/lose amount \n"+
       " Win/Lose amount will be calculated following odds types below:\n"+ " Hongkong odds: Win = stake * odds. Lose = stake");
        String winLossAmount = betSettlementPage.getWinlossAmountofOrder(lstOrder.get(0));
        Assert.assertEquals(winLossAmount,"","");

        log("@Post-condition: delete confirm bet");
        betSettlementPage.deleteOrder(lstOrder.get(0));
        log("INFO: Executed completely");
    }

    @TestRails(id="188")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetSettlement_TC188(String accountCode,String accountCurrency) throws InterruptedException {
        log("@title: Validate bet in Bet settlement is re-settled with correct winloss after update bet info in Confirm Bets page");
        log("Precondition: Have a soccer bet in confirmed status and have win/lose amount\n" +
                "For example bet is place back, HDP -0.25 odds 1.050 HKD, stake 12 and winlose value 12.6\n" +
                "\n");
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(-1,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,SOCCER,"");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(SOCCER).isNegativeHdp(false).hdpPoint(0.25).price(1.110).requireStake(11)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        order = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder).get(0);

        log("@Step 2:Navigate to Trading > Bet Settlement > Search bet of the account at precondition in Confirmed mode");
        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",SOCCER,"All","Specific Date",date,date,accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 3: Update info of precondition bet, change bet type to Lay");
        confirmBetsPage.filter(companyUnit,"","Confirmed",SOCCER,"All","Specific Date",date,date,accountCode);
        order.setBetType("Lay");
        confirmBetsPage.updateOrder(order,false);

        log("@Step 4. Active Bet Settlement page and filter the bet in confirmed status in precondition");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,date,"",accountCode);
        String winLossAmount = betSettlementPage.getWinlossAmountofOrder(order);

        System.out.println("Winloss amount is "+winLossAmount);
        log("@Verify: Bet is re-settled correctly win/loss value");

        log("@Post-condition: delete confirm bet");
        betSettlementPage.deleteOrder(order);

        log("INFO: Executed completely");
    }

    @TestRails(id="203")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode"})
    public void BetSettlement_TC203(String accountCode) throws IOException {
        log("@title: Validate that user can export file successfully");
        log("Precondition: User has permission to access Bet Settlement page \n" +
                "Having an account with Confirmed bet settle Win/Lose");
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "Bet_Settlement.xlsx";
        log("@Step 2:Navigate to Trading > Bet Settlement > Search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",accountCode);
        String order =betSettlementPage.getOrderIndex(1);

        log("@Step 3.Select the bet and click Export Selected Bet (s) > observe");
        betSettlementPage.exportSelectedOrderID(order);
        try {
        log("@Verify: User can export file successfully with exported file name: Bet_Settlement");
        Assert.assertTrue(FileUtils.doesFileNameExist(dowloadPath), "Failed to download Expected document "+ dowloadPath);
        } finally {
            log("@Post-condition: delete download file");

//            FileUtils.removeFile(dowloadPath);
        }
        log("INFO: Executed completely");
    }

    @TestRails(id="204")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode"})
    public void BetSettlement_TC204(String accountCode){
        log("@title: Validate that user can send Bets List email successfully");
        log("Precondition: User has permission to access Bet Settlement page \n" +
                "Having an account with Confirmed bet settle Win/Lose and configuring with email in Address Book");

        log("@Step 2:Navigate to Trading > Bet Settlement > Search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",accountCode);
        String orderID =betSettlementPage.getOrderIndex(1);
        Order order = new Order.Builder()
                .betId(orderID)
                .build();

        log("@Step 3.Select the bet and click Send Bet List Email > observe");
        betSettlementPage.sendBetListEmail(order);
        String expectedMessage = betSettlementPage.getSuccessMessage();

        log("@Verify 1 .User can send Bet List email successfully with message 'Statement Email has been sent to your mail box'");
        Assert.assertTrue(expectedMessage.contains("Statement Email has been sent to your mail box"),
                "Failed! Success message after place bet is incorrect Actual is "+ expectedMessage);

        log("INFO: Executed completely");
    }
    @TestRails(id="205")
    @Test(groups = {"smoke","ethan"})
    @Parameters({"accountCode"})
    public void BetSettlement_TC205(String accountCode){
        log("@title: Validate that user can Settled and Send Settlement email successfully");
        log("Precondition: Already has account with Confirmed bet settle Win/Lose\n" +
                "The account is configured with email in Address Book");
        String fromDate = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        String sport = "Soccer";
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("HDP").selection(event.getHome()).stage("FullTime").price(1.75)
                .odds(1.75).oddType("HK").hdpPoint(2.15).handicap(2.15).oddType("HK").requireStake(15.50).betType("BACK").isLive(false).build();
        BetEntrytUtils.placeBetAPI(order);
        List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(order);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(60);
        log("@Step 1: Navigate to Trading > Bet Settlement and search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",fromDate,"","",accountCode);

        log("@Step 2. Select the bet and click Settle & Send Settlement Email button > Yes and observe message");
        log("@Step 3: Switch to Settled mode and search bet of the account then observe list result");

        betSettlementPage.settleAndSendSettlementEmail(lstOrder.get(0));
        List<String> listSuccessMessage = betSettlementPage.getListSuccessMessage();

        log("@Verify 1 .Successfully message displays with 2 popup:\n" +
                "Bet(s) is settled successfully.\n" +
                "Statement Email has been sent to your mail box.");
        Assert.assertTrue(listSuccessMessage.equals(BetSettlement.LST_MESSAGE_SETTLE_SENT_MAIL),
                "Failed! List Success message after Settle & Send Settlement Email bet is incorrect. Actual: " + listSuccessMessage + "\n");

        log("Verify 2. The bet settled displays in result list");
        betSettlementPage.filter("Settled",fromDate,"","",accountCode);
        betSettlementPage.verifyOrderInfo(lstOrder.get(0));
        log("INFO: Executed completely");
    }

    @TestRails(id="206")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency","emailAddress","clientCode"})
    public void BetSettlement_TC206(String accountCode,String accountCurrency,String emailAddress,String clientCode){
        log("@title: Validate all information display correctly in statement email (AQS-2020)");
        log("Precondition:Already has account with some Confirmed bet settle Win/Lose (includes MB, Soccer, Cricket)\n" +
                "The account is configured with email in Address Book");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(-2,"dd/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-2,"yyyy-MM-dd","GMT +7"));
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,eventInfo.getLeagueName());
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.15).requireStake(15.50)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        order = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder).get(0);

        ConfirmBetsPage confirmBetsPage = soccerBetEntryPage.navigatePage(TRADING, CONFIRM_BETS,ConfirmBetsPage.class);
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,date,accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 2: Navigate to Trading > Bet Settlement and search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,"","",accountCode);

        log("@Step 3. Select the bet and click Settle & Send Settement Email button > Yes");
        betSettlementPage.settleAndSendSettlementEmail(order);

        log("@Step 4: Login to mailbox and verify infor displays in statement");
        List<ArrayList<String>> emailInfo = betSettlementPage.getFirstActiveMailBox("https://yopmail.com/",emailAddress);
        List<String> expectedRow1 = Arrays.asList("Member Code: "+accountCode,"Member Name: "+accountCode);
        log("@Verify 1 .Information of Description, Selection, HDP, Live, Price, Stake, Win/Lose, Type, Date, Total Win, C/F (displayed), Balance show correctly");
        Assert.assertEquals(emailInfo.get(0).get(0),"Statement of Account for the Account "+accountCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(1),"Mr "+ clientCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(2),"Please find enclosed statement for account "+accountCode,"Failed! title of email is incorrect");
        Assert.assertTrue(emailInfo.get(0).get(3).contains(String.format("Therefore the amount + %s", accountCurrency)) ,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(4),"This amount shall be KIV to the next period.","Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(1), BET_LIST_STATEMENT_EMAIL, "Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(3).get(0), expectedRow1.get(0), "Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(3).get(1), expectedRow1.get(1), "Failed! title of email is incorrect");


        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2195")
    public void BetSettlement_2195(){
        log("Validate Bet Settlement page is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Settlement");
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        log("Validate Bet Settlement page is displayed with correctly title");
        Assert.assertTrue(betSettlementPage.getTitlePage().contains(BET_SETTLEMENT),"Failed! Bet Settlement page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2196")
    public void BetSettlement_2196(){
        log("Validate UI on Bet Settlement is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Settlement");
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Status, Match Date");
        Assert.assertEquals(betSettlementPage.ddbStatus.getOptions(),BetSettlement.STATUS_LIST,"Failed! Status dropdown is not displayed!");
        Assert.assertEquals(betSettlementPage.ddbMatchDate.getOptions(),BetSettlement.MATCH_DATE,"Failed! Match Date dropdown is not displayed!");
        log("Textbox: Acc Starts With, Account Code");
        Assert.assertEquals(betSettlementPage.lblAccStart.getText(),"Acc Starts With","Failed! Acc Starts With textbox is not displayed!");
        Assert.assertEquals(betSettlementPage.lblAccCode.getText(),"Account Code","Failed! Account Code textbox is not displayed!");
        log("Button: Search, Show Account, More Filters, Reset All Filters");
        Assert.assertTrue(betSettlementPage.btnSearch.isDisplayed(),"Failed! Search button is not displayed!");
        Assert.assertEquals(betSettlementPage.lnkShowAccount.getText(),"Show Account","Failed! Show Account button is not displayed!");
        Assert.assertEquals(betSettlementPage.lnkMoreFilter.getText(),"More Filters","Failed! More Filters button is not displayed!");
        Assert.assertEquals(betSettlementPage.lnkResetAllFilter.getText(),"Reset All Filters","Failed! Reset All Filters button is not displayed!");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "23694")
    @Parameters({"password", "userNameOneRole"})
    public void BetSettlement_23694(String password, String userNameOneRole) throws Exception{
        log("Validate: Validate accounts without permission cannot see the menu");
        log("@Pre-condition: Account is inactivated permission 'Bet Settlement'");
        log("@Step 1: Navigate to the site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Check menu item 'Bet Settlement' under menu 'Trading'");
        log("Verify 1: Menu 'Bet Settlement' is not shown");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(TRADING,BET_SETTLEMENT),"FAILED! Win Loss Detail sub-menu is displayed incorrect.");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "29437")
    @Parameters({"accountCode"})
    public void BetSettlement_29437(String accountCode){
        log("Validate: Validate confirmed bets can delete successfully");
        log("@Pre-condition 1: User has permission to access Bet Settlement page");
        log("@Pre-condition 2: Having an account with Confirmed bet settle Win/Lose");
        String fromDate = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        String sport = "Soccer";
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder().event(event).accountCode(accountCode).marketName("Goals").marketType("HDP").selection(event.getHome()).stage("FullTime").price(1.75)
                .odds(1.75).oddType("HK").hdpPoint(2.15).handicap(2.15).oddType("HK").requireStake(15.50).betType("BACK").isLive(false).build();
        BetEntrytUtils.placeBetAPI(order);
        List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(order);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsUtils.confirmBetAPI(lstOrder);
        BetSettlementUtils.waitForBetIsUpdate(60);
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Settlement > Search bet of the account at precondition in Confirmed mode");
        BetSettlementPage page  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        page.filter("Confirmed",fromDate,"","",accountCode);
        log("@Step 3: Tick on the bet");
        log("@Step 4: Click on 'Delete' button");
        log("@Step 5: Click on 'Yes' button");
        page.deleteOrder(lstOrder.get(0));
        String mesAc = page.appArlertControl.getSuscessMessage();
        log("Verify 1: Show the successful message: 'Bet(s) is deleted successfully.'");
        Assert.assertEquals(mesAc,BetSettlement.MES_DELETED_ORDER_SUC,"FAILED! Message displayed incorrect.");
        log("Verify 2: The bet is removed from bet list");
        page.isOrderDisplayInTheTable(lstOrder.get(0));
        log("INFO: Executed completely");
    }
}
