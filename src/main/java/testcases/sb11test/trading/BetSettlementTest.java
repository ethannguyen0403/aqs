package testcases.sb11test.trading;

import com.code88.utils.FileUtils;
import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.trading.*;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.popup.BetListPopup;
import testcases.BaseCaseAQS;
import utils.sb11.BetEntrytUtils;
import utils.sb11.GetSoccerEventUtils;
import utils.testraildemo.TestRails;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.SBPConstants.*;
import static common.SBPConstants.BetSettlement.BET_LIST_STATEMENT_EMAIL;

public class BetSettlementTest extends BaseCaseAQS {
    @TestRails(id="187")
    @Test(groups = {"smoke3"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetSettlement_TC187(String accountCode,String accountCurrency) throws InterruptedException {
        log("@title: Validate Win/loss amounts are calculated correctly when having Account Percentage setting");
        log("Precondition: User has permission to access Bet Settlement page\n" +
                "Having an account with Confirmed bet settle Win/Lose and configuring Account Percentage");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";

        String date = String.format(DateUtils.getDate(1,"d/MM/yyyy","UTC+7:00"));
        String dateAPI = String.format(DateUtils.getDate(1,"yyyy-MM-dd","UTC+7:00"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
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
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(true).hdpPoint(0.25).price(1.050).requireStake(12)
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
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date",date,date,accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 3: Update info of precondition bet, change bet type to Lay");
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
    @Parameters({"accountCode","accountCurrency"})
    public void BetSettlement_TC203(String accountCode,String accountCurrency){
        log("@title: Validate that user can export file successfully");
        log("Precondition: User has permission to access Bet Settlement page \n" +
                "Having an account with Confirmed bet settle Win/Lose");
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "Bet_Settlement.xlsx";
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";

        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
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

        log("@Step 2:Navigate to Trading > Bet Settlement > Search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,date,"",accountCode);

        log("@Step 3.Select the bet and click Export Selected Bet (s) > observe");
        betSettlementPage.exportSelectedBEt(order);

        log("@Verify: User can export file successfully with exported file name: Bet_Settlement");
        Assert.assertTrue(FileUtils.doesFileNameExist(dowloadPath), "Failed to download Expected document");

        log("@Post-condition: delete download file");
        try {
            FileUtils.removeFile(dowloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("@Post-condition: delete confirm bet");
        betSettlementPage.deleteOrder(order);
        log("INFO: Executed completely");
    }

    @TestRails(id="204")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetSettlement_TC204(String accountCode,String accountCurrency){
        log("@title: Validate that user can send Bets List email successfully");
        log("Precondition: User has permission to access Bet Settlement page \n" +
                "Having an account with Confirmed bet settle Win/Lose and configuring with email in Address Book");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(-1,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        soccerBetEntryPage.showLeague(companyUnit,"",league);
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
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

        log("@Step 2:Navigate to Trading > Bet Settlement > Search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,"","",accountCode);

        log("@Step 3.Select the bet and click Send Bet List Email > observe");
        betSettlementPage.sendBetListEmail(order);

        log("@Verify 1 .User can send Bet List email successfully with message 'Statement Email has been sent to your mail box'");
        Assert.assertTrue(betSettlementPage.getSuccessMessage().contains("Statement Email has been sent to your mail box"),
                "Failed! Success message after place bet is incorrect Actual is "+betSettlementPage.getSuccessMessage());

        log("@Post-condition: delete confirm bet");
        betSettlementPage.deleteOrder(order);
        log("INFO: Executed completely");
    }
    @TestRails(id="205")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency"})
    public void BetSettlement_TC205(String accountCode,String accountCurrency){
        log("@title: Validate that user can Settled and Send Settlement email successfully");
        log("Precondition: Already has account with Confirmed bet settle Win/Lose\n" +
                "The account is configured with email in Address Book");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(-1,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);

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
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,"","",accountCode);

        log("@Step 3. Select the bet and click Settle & Send Settlement Email button > Yes and observe message");
        log("@Step 4: Switch to Settled mode and search bet of the account then observe list result");
        betSettlementPage.settleAndSendSettlementEmail(order);

        log("@Verify 1 .Successfully message displays with 2 popup:\n" +
                "Bet(s) is settled successfully.\n" +
                "Statement Email has been sent to your mail box.");
        Assert.assertTrue(betSettlementPage.getListSuccessMessage().equals(BetSettlement.LST_MESSAGE_SETTLE_SENT_MAIL),"Failed! List Success message after Settle & Send Settlement Email bet is incorrect");

        log("Verify 2. The bet settled displays in result list");
        betSettlementPage.filter("Settled",date,date,"",accountCode);
        betSettlementPage.verifyOrderInfo(order);
        log("INFO: Executed completely");
    }

    @TestRails(id="206")
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","accountCurrency","emailAddress","clientName"})
    public void BetSettlement_TC206(String accountCode,String accountCurrency,String emailAddress,String clientName){
        log("@title: Validate all information display correctly in statement email (AQS-2020)");
        log("Precondition:Already has account with some Confirmed bet settle Win/Lose (includes MB, Soccer, Cricket)\n" +
                "The account is configured with email in Address Book");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(-1,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
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
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,"","",accountCode);

        log("@Step 3. Select the bet and click Settle & Send Settement Email button > Yes");
        betSettlementPage.settleAndSendSettlementEmail(order);

        log("@Step 4: Login to mailbox and verify infor displays in statement");
        List<ArrayList<String>> emailInfo = betSettlementPage.getFirstActiveMailBox("https://yopmail.com/",emailAddress);
        List<String> expectedRow1 = Arrays.asList("Member Code "+accountCode,"Member Name: "+accountCode);
        List<String> expectedRow2 = Arrays.asList("Member Code "+accountCode,"Member Name: "+accountCode);
        log("@Verify 1 .Information of Description, Selection, HDP, Live, Price, Stake, Win/Lose, Type, Date, Total Win, C/F (displayed), Balance show correctly");
        Assert.assertEquals(emailInfo.get(0).get(0),"Statement of Account for the Account "+accountCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(1),"Mr "+ clientName,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(2),"Please find enclosed statement for account ISA_ACC001 "+accountCode,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(3),"Therefore the amount + "+accountCurrency+" 19.00 ","Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(4),"This amount shall be KIV to the next period.","Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(5),BET_LIST_STATEMENT_EMAIL,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(6),expectedRow1,"Failed! title of email is incorrect");
        Assert.assertEquals(emailInfo.get(0).get(7),expectedRow2,"Failed! title of email is incorrect");

        log("@Post-condition: delete confirm bet");
        betSettlementPage.deleteOrder(order);

        log("INFO: Executed completely");
    }
}
