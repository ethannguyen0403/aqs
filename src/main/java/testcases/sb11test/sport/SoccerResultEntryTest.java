package testcases.sb11test.sport;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.SoccerResultEntryPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class SoccerResultEntryTest extends BaseCaseAQS {

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2085")
    public void Soccer_ResultEntry_2085(){
        log("@title: Validate Result Entry for Soccer is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Soccer");
        SoccerResultEntryPage soccerResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, SoccerResultEntryPage.class);
        soccerResultEntryPage.goToSport("Soccer");
        log("Validate Result Entry page for Soccer sport is displayed with correctly title");
        Assert.assertTrue(soccerResultEntryPage.getTitlePage().equalsIgnoreCase("Soccer"), "Failed! Result Entry page for Soccer sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2086")
    public void Soccer_ResultEntry_2086(){
        log("@title: Validate UI on Soccer  Result Entry  is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Soccer");
        SoccerResultEntryPage soccerResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, SoccerResultEntryPage.class);
        soccerResultEntryPage.goToSport("Soccer");
        log("Validate UI Info display correctly");
        soccerResultEntryPage.verifyUI();
        log("INFO: Executed completely");
    }


    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2089")
    public void Soccer_ResultEntry_2089(){
        log("@title: Validate League list is displayed correctly when clicking Show Leagues ");
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Soccer");
        SoccerResultEntryPage soccerResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, SoccerResultEntryPage.class);
        soccerResultEntryPage.goToSport("Soccer");
        log("Validate Result Entry page for Soccer sport is displayed with correctly title");
        soccerResultEntryPage.filterResult("Normal",date,"All","KOT","All",false);
        int leagueSize = soccerResultEntryPage.ddpLeague.getNumberOfItems();
        Assert.assertTrue(leagueSize > 0, "Failed! League list is having no item" );
        log("INFO: Executed completely");
    }
}
