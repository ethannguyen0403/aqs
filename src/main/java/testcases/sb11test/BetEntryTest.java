package testcases.sb11test;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.popup.BetSlipPopup;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class BetEntryTest extends BaseCaseAQS {

    @Test(groups = {"smoke1"})
    public void Bookie_Info_TC_001(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Trading > Bet Entry");
        BetEntryPage betEntryPage = welcomePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        betEntryPage.openBetSlipSoccer("JO-TEL-AC-01","HOME-FT");
        betEntryPage.placeSoccerBet("1.25","1","20",false,false,true);

        log("Verify that Page title is correctly display");

        log("INFO: Executed completely");
    }
}
