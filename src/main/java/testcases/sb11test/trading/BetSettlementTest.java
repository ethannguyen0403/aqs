package testcases.sb11test.trading;

import com.code88.utils.FileUtils;
import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
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
import java.util.List;

import static common.SBPConstants.*;

public class BetSettlementTest extends BaseCaseAQS {

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
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,"",league);
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
    @Test(groups = {"smoke1"})
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
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);
        soccerBetEntryPage.showLeague(companyUnit,date,league);
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
        confirmBetsPage.filter(companyUnit,"","Pending",sport,"All","Specific Date","","",accountCode);
        confirmBetsPage.confirmBet(order);

        log("@Step 2:Navigate to Trading > Bet Settlement > Search bet of the account at precondition in Confirmed mode");
        BetSettlementPage betSettlementPage  = confirmBetsPage.navigatePage(TRADING, BET_SETTLEMENT, BetSettlementPage.class);
        betSettlementPage.filter("Confirmed","","","",accountCode);

        log("@Step 3.Select the bet and click Send Bet List Email > observe");
        betSettlementPage.sendBetListEmail(order);

        log("@Verify 1 .User can send Bet List email successfully with message 'Statement Email has been sent to your mail box'");
        Assert.assertTrue(soccerBetEntryPage.getSuccessMessage().contains("Statement Email has been sent to your mail box"),
                "Failed! Success message after place bet is incorrect Actual is "+soccerBetEntryPage.getSuccessMessage());

        log("@Post-condition: delete confirm bet");
        betSettlementPage.deleteOrder(order);
        log("INFO: Executed completely");
    }


}
