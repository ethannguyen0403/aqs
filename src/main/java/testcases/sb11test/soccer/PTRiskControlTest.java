package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.soccer.PTRiskPage;
import pages.sb11.soccer.popup.PTRiskBetListPopup;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;
import testcases.BaseCaseAQS;
import utils.sb11.*;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class PTRiskControlTest extends BaseCaseAQS {
    String companyUnit = "Kastraki Limited";
    String superMasterCode = "QA2112 - ";
    Double percent = 0.5;

    @Test(groups = {"smoke"})
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
        soccerBetEntryPage.showLeague(companyUnit,"","All");
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

        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate to Soccer > PT Risk Control");
        PTRiskPage ptPage = welcomePage.navigatePage(SOCCER, PT_RISK_CONTROL, PTRiskPage.class);
        ptPage.waitSpinnerDisappeared();
        log("@Step 3: Filter with Report Type = Normal with League and Client placed bet");
        ptPage.filter(clientCode,companyUnit,"Normal","All","","", eventInfo.getLeagueName());
        log("Step 4: Open bet list of league");
        PTRiskBetListPopup ptRiskPopup = ptPage.openBetList(eventInfo.getHome());
        actualPTVal = ptRiskPopup.getBetListCellValue(accountCode, ptRiskPopup.colPTPercent);
        log("@Validate data of account setting Account Percent show on PT% column");
        Assert.assertEquals(String.valueOf(percent),actualPTVal);
        ptRiskPopup.closeBetListPopup();
        log("@Validate win/lose forecast on HDP row");
        Assert.assertTrue(ptPage.verifyForecastCorrect("10","-3","-6",true));
    }


}
