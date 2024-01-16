package testcases.sb11test.trading;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.BetSettlementPage;
import pages.sb11.trading.ConfirmBetsPage;
import pages.sb11.trading.SoccerBetEntryPage;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.SBPConstants.*;
import static common.SBPConstants.BetSettlement.BET_LIST_STATEMENT_EMAIL;

public class BetSettlementTest extends BaseCaseAQS {
    @TestRails(id="187")
    @Test(groups = {"smoke1"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetSettlement_TC187(String accountCode,String accountCurrency) throws InterruptedException {
        log("@title: Validate Win/loss amounts are calculated correctly when having Account Percentage setting\");\n" +
                "        log(\"Precondition: User has permission to access Bet Settlement page\\n" +
                "Having an account with Confirmed bet settle Win/Lose and configuring Account Percentage");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";

        String date = String.format(DateUtils.getDate(1,"d/MM/yyyy","UTC+7:00"));
        String dateAPI = String.format(DateUtils.getDate(1,"yyyy-MM-dd","UTC+7:00"));
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

        log("@Step 2:Navigate to Trading > Bet Settlement ");
        log("@Step 3.Search account at precondition in Confirmed mode > observe any bet that is having Win/Lose data\n");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,date,"",accountCode);

        log("@Verify 1 : Win/loss amounts should calculate correctly with Account Percentage following \n"+
       " Confirmed status, Win/Lose = actual win/loss % * win/lose amount \n"+ " Settled Status, Win/Lose = actual win/loss % * win/lose amount \n"+
       " Win/Lose amount will be calculated following odds types below:\n"+ " Hongkong odds: Win = stake * odds. Lose = stake");
        String winLossAmount = betSettlementPage.getWinlossAmountofOrder(order);
        Assert.assertEquals(winLossAmount,"","");

        log("@Post-condition: delete confirm bet");
        betSettlementPage.deleteOrder(order);
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

            FileUtils.removeFile(dowloadPath);
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
    @Test(groups = {"smoke1"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetSettlement_TC205(String accountCode,String accountCurrency){
        log("@title: Validate that user can Settled and Send Settlement email successfully");
        log("Precondition: Already has account with Confirmed bet settle Win/Lose\n" +
                "The account is configured with email in Address Book");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String fromDate = String.format(DateUtils.getDate(-2,"dd/MM/yyyy","GMT +7"));
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
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,"",accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 2: Navigate to Trading > Bet Settlement and search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = welcomePage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",fromDate,date,"",accountCode);

        log("@Step 3. Select the bet and click Settle & Send Settlement Email button > Yes and observe message");
        log("@Step 4: Switch to Settled mode and search bet of the account then observe list result");
        betSettlementPage.settleAndSendSettlementEmail(order);
        List<String> listSuccessMessage = betSettlementPage.getListSuccessMessage();

        log("@Verify 1 .Successfully message displays with 2 popup:\n" +
                "Bet(s) is settled successfully.\n" +
                "Statement Email has been sent to your mail box.");
        Assert.assertTrue(listSuccessMessage.equals(BetSettlement.LST_MESSAGE_SETTLE_SENT_MAIL),
                "Failed! List Success message after Settle & Send Settlement Email bet is incorrect. Actual: " + listSuccessMessage + "\n");

        log("Verify 2. The bet settled displays in result list");
        betSettlementPage.filter("Settled",fromDate,date,"",accountCode);
        betSettlementPage.verifyOrderInfo(order);
        log("INFO: Executed completely");
    }

    @TestRails(id="206")
    @Test(groups = {"smoke1"})
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
}
