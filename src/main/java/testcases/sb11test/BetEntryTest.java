package testcases.sb11test;

import common.ESSConstants;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.ManualBetBetEntryPage;
import pages.sb11.trading.popup.BetSlipPopup;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class BetEntryTest extends BaseCaseAQS {

    /**
     * @title: Validate users can place Mixed Sports bets successfully
     * @steps:   1. Login to SB11 site
     * 2. Navigate to Trading > Bet Entry
     * 3. Click on 'Mixed Sports' > Input account at precondition on 'Account Code' field > click glass icon
     * 4. Inputting Odds, Stake and Win/Loss or Comm Amount > click Place Bet
     * 5. Click Yes on Confirm dialog > observe
     * @expect: Validate user can place Mixed Sports bets successfully with message 'Placed successfully!"
     */

    @Test(groups = {"smoke1"})
    public void Bet_Entry_TC862(){
        log("@title: Validate users can place Mixed Sports bets successfully");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Navigate to Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        log("@Step 3: Click on 'Mixed Sports' > Input account at precondition on 'Account Code' field > click glass icon");
        log("@Step 4: Inputting Odds, Stake and Win/Loss or Comm Amount > click Place Bet");
        log("@Step 5: Click Yes on Confirm dialog > observe");
        betEntryPage.goToMixedSports();

        ManualBetBetEntryPage manualBetBetEntryPage = new ManualBetBetEntryPage();
        String messageSuccess = manualBetBetEntryPage.placeManualBet("Kastraki Limited ","22/11/2022", "JO-TEL-AC-01", "Soccer",
                "Manual Bet Testing", null,null,"1.25","10","1",true);

        log("Validate user can place Mixed Sports bets successfully with message 'Placed successfully!");
        Assert.assertTrue(messageSuccess.contains(SBPConstants.BetEntryPage.MESSAGE_SUCCESS_MANUAL_BET), "FAILED! Incorrect place bet successful");
        log("INFO: Executed completely");
    }
}
