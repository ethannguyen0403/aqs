package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.MatchOddsLiabilityPage;
import pages.sb11.soccer.PTRiskPage;
import pages.sb11.soccer.popup.PTRiskBetListPopup;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;
import pages.sb11.trading.popup.SoccerSPBBetSlipPopup;
import testcases.BaseCaseAQS;
import utils.sb11.*;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class PTRiskControlTest extends BaseCaseAQS {
    String superMasterCode = "QA2112 - ";
    Double percent = 0.5;

    @Test(groups = {"smoke1"})
    @Parameters({"clientCode","accountCode","accountCurrency"})
    @TestRails(id = "1386")
    public void ClientStatementTC_1386(String clientCode, String accountCode, String accountCurrency) throws InterruptedException, IOException {
        welcomePage.waitSpinnerDisappeared();
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String clientId = ClientSystemUtils.getClientId(clientCode);
        clientCode = superMasterCode + clientCode;
        String actualPTVal;

        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (HK)");
        log("Precondition: Having account with Pending HPD/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        String sport="Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode,percent);
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(COMPANY_UNIT,"","All");
        String league = soccerBetEntryPage.getRandomLeague();
        Event eventInfo = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(1.6).requireStake(12)
                .oddType("HK").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        soccerBetEntryPage.waitSpinnerDisappeared();

        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode,COMPANY_UNIT,"Normal","All","","", eventInfo.getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(eventInfo.getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent),actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type HK on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("10","-3","-6",true));
    }

    @Test(groups = {"smoke1"})
    @Parameters({"clientCode","accountCode","accountCurrency"})
    @TestRails(id = "192")
    public void ClientStatementTC_192(String clientCode, String accountCode, String accountCurrency) throws InterruptedException, IOException {
        welcomePage.waitSpinnerDisappeared();
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String clientId = ClientSystemUtils.getClientId(clientCode);
        clientCode = superMasterCode + clientCode;
        String actualPTVal;

        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (EU)");
        log("Precondition: Having account with Pending HPD/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        String sport="Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode,percent);
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(COMPANY_UNIT,"","All");
        String league = soccerBetEntryPage.getRandomLeague();
        Event eventInfo = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(2.0).requireStake(12)
                .oddType("EU").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        soccerBetEntryPage.waitSpinnerDisappeared();

        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode,COMPANY_UNIT,"Normal","All","","", eventInfo.getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(eventInfo.getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent),actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type EU on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("6","-3","-6",true));
    }

    @Test(groups = {"smoke"})
    @Parameters({"clientCode","accountCode","accountCurrency"})
    @TestRails(id = "1387")
    public void ClientStatementTC_1387(String clientCode, String accountCode, String accountCurrency) throws InterruptedException, IOException {
        welcomePage.waitSpinnerDisappeared();
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String clientId = ClientSystemUtils.getClientId(clientCode);
        clientCode = superMasterCode + clientCode;
        String actualPTVal;

        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (MY)");
        log("Precondition: Having account with Pending HPD/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        String sport="Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode,percent);
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(COMPANY_UNIT,"","All");
        String league = soccerBetEntryPage.getRandomLeague();
        Event eventInfo = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(0.5).requireStake(12)
                .oddType("MY").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        soccerBetEntryPage.waitSpinnerDisappeared();

        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode,COMPANY_UNIT,"Normal","All","","", eventInfo.getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(eventInfo.getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent),actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type MY on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("3","-3","-6",true));
    }

    @Test(groups = {"smoke"})
    @Parameters({"clientCode","accountCode","accountCurrency"})
    @TestRails(id = "1388")
    public void ClientStatementTC_1388(String clientCode, String accountCode, String accountCurrency) throws InterruptedException, IOException {
        welcomePage.waitSpinnerDisappeared();
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String clientId = ClientSystemUtils.getClientId(clientCode);
        clientCode = superMasterCode + clientCode;
        String actualPTVal;

        log("@title: Validate that Win/Loss amounts are calculated correctly if having Account Percentage setting (ID)");
        log("Precondition: Having account with Pending HPD/OU bet and \n" +
                "The account is configured with percentage in Account Percent");
        String sport="Soccer";
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode,percent);
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(COMPANY_UNIT,"","All");
        String league = soccerBetEntryPage.getRandomLeague();
        Event eventInfo = GetSoccerEventUtils.getRandomEvent(dateAPI,dateAPI,sport,league);
        List<Order> lstOrder = new ArrayList<>();
        Order order = new Order.Builder()
                .sport(sport).isNegativeHdp(false).hdpPoint(1.75).price(1.5).requireStake(12)
                .oddType("ID").betType("Back").liveHomeScore(0).liveAwayScore(0).accountCode(accountCode).accountCurrency(accountCurrency)
                .marketType("HDP")
                .stage("FT")
                .selection(eventInfo.getHome())
                .event(eventInfo)
                .build();
        lstOrder.add(order);
        soccerBetEntryPage.placeBet(accountCode,eventInfo.getHome(),true,"Home",lstOrder,false,false,true);
        soccerBetEntryPage.waitSpinnerDisappeared();

        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode,COMPANY_UNIT,"Normal","All","","", eventInfo.getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(eventInfo.getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent),actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast of odds type ID on HDP row show correctly");
        Assert.assertTrue(ptPage.isForecastCorrect("9","-3","-6",true));
    }

    @Test(groups = {"regression"})
    public void PTRiskControlTC_001(){
        log("@title: Validate PT Risk Control page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER,PT_RISK_CONTROL, PTRiskPage.class);
        log("Validate PT Risk Control page is displayed with correctly title");
        Assert.assertTrue(ptRiskPage.getTitlePage().contains(PT_RISK_CONTROL), "Failed! PT Risk Control page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void PTRiskControlTC_002(){
        log("@title: Validate UI on PT Risk Control is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER,PT_RISK_CONTROL, PTRiskPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, Live/NonLive, From Date, To Date, Order By, Bet Types, Leagues, Events, Clients, Smart Master, Smart Agent, Show button and Copy Report");
        Assert.assertTrue(ptRiskPage.ddpCompanyUnit.isDisplayed(),"Company Unit dropdown box is not displayed!");
        Assert.assertTrue(ptRiskPage.ddpReportType.isDisplayed(),"Report Type dropdown box is not displayed!");
        Assert.assertTrue(ptRiskPage.ddpLiveNonLive.isDisplayed(),"Live/Non Live dropdown box is not displayed!");
        Assert.assertTrue(ptRiskPage.txtFromDate.isDisplayed(),"From Date is not displayed!");
        Assert.assertTrue(ptRiskPage.txtToDate.isDisplayed(),"To Date is not displayed!");
        Assert.assertTrue(ptRiskPage.ddpOrderBy.isDisplayed(),"Live/Non Live dropdown box is not displayed!");
        Assert.assertTrue(ptRiskPage.btnBetTypes.isDisplayed(),"Bet Types dropdown is not displayed!");
        Assert.assertTrue(ptRiskPage.btnLeagues.isDisplayed(),"Leagues dropdown is not displayed!");
        Assert.assertTrue(ptRiskPage.btnEvents.isDisplayed(),"Events dropdown is not displayed!");
        Assert.assertTrue(ptRiskPage.btnClient.isDisplayed(),"Clients dropdown is not displayed!");
        Assert.assertTrue(ptRiskPage.btnSmartMaster.isDisplayed(),"Smart Master dropdown is not displayed!");
        Assert.assertTrue(ptRiskPage.btnSmartAgent.isDisplayed(),"Smart Agent dropdown is not displayed!");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @Parameters({"accountCode","accountCurrency"})
    public void PTRiskControlTC_003(String accountCode,String accountCurrency){
        log("@title: Validate Full Time Handicap bet is displayed correctly on PT Risk Control > Handicap tab");
        log("@Pre-condition: Having an Full Time Handicap bet which have been placed on Bet Entry");
        String sport="Soccer";
        String companyUnit = "Kastraki Limited";
        String marketType = "1x2";
        String date = String.format(DateUtils.getDate(0,"d/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING,BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage =betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,"All");
        String league = soccerBetEntryPage.getFirstLeague();
        Event eventInfo = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,league);

        log("@Step Precondition: Define order to place bet");
        Order order = new Order.Builder()
                .sport(sport)
                .hdpPoint(0.00)
                .price(2.15)
                .requireStake(15.50)
                .oddType("HK")
                .betType("Back")
                .accountCode(accountCode)
                .accountCurrency(accountCurrency)
                .marketType(marketType)
                .stage("Full Time")
                .selection("Home")
                .liveHomeScore(0)
                .liveAwayScore(0)
                .home(eventInfo.getHome())
                .away((eventInfo.getAway()))
                .event(eventInfo)
                .build();

        SoccerSPBBetSlipPopup soccerSPBBetSlipPopup = soccerBetEntryPage.openSPBBetSlip(accountCode,eventInfo.getHome());
        soccerSPBBetSlipPopup.placeMoreBet(order,false,false,true);

        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER,PT_RISK_CONTROL, PTRiskPage.class);
        log("@Step 3: Filter with event that having bet at Pre-condition > Click Show");
        log("@Step 4: Click on event name > Click Handicap tab");
        log("Validate Full Time Handicap bet is displayed correctly on PT Risk Control > Handicap tab");

        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    public void PTRiskControlTC_006(){
        log("@title: Validate that can copy report successfully");
        String date = String.format(DateUtils.getDate(-3,"d/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > PT Risk Control");
        PTRiskPage ptRiskPage = welcomePage.navigatePage(SOCCER,PT_RISK_CONTROL, PTRiskPage.class);
        log("@Step 3: Click Copy Report");
        ptRiskPage.dtpFromDate.selectDate(date,"dd/MM/yyyy");
        ptRiskPage.btnShow.click();
        ptRiskPage.btnCopy.click();
        log("Message success should display correctly as \"Copied!\"");
        Assert.assertEquals(ptRiskPage.messageSuccess.getText(),"Copied","Failed! Copy button is not worked");
        log("INFO: Executed completely");
    }
}
