package testcases.sb11test.sport;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.SoccerResultEntryPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class SoccerResultEntryTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    public void Soccer_ResultEntry_TC001(){
        log("@title: Validate Result Entry for Soccer is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Soccer");
        SoccerResultEntryPage soccerResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, SoccerResultEntryPage.class);
        soccerResultEntryPage.goToSport("Soccer");
        log("Validate Result Entry page for Soccer sport is displayed with correctly title");
        Assert.assertTrue(soccerResultEntryPage.getTitlePage().contains("Soccer"), "Failed! Result Entry page for Soccer sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void Soccer_ResultEntry_TC002(){
        log("@title: Validate UI on Soccer  Result Entry  is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Soccer");
        SoccerResultEntryPage soccerResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, SoccerResultEntryPage.class);
        soccerResultEntryPage.goToSport("Soccer");
        log("Validate UI Info display correctly");
        log("Type, Date, Show League button, Leagues, Order By, Status and Show button");
        Assert.assertTrue(soccerResultEntryPage.ddpType.isDisplayed(),"Failed! Type dropdown is not displayed");
        Assert.assertTrue(soccerResultEntryPage.txtDateTime.isDisplayed(),"Failed! Date datetimepicker is not displayed");
        Assert.assertTrue(soccerResultEntryPage.btnShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(soccerResultEntryPage.ddpLeague.isDisplayed(),"Failed! League dropdown is not displayed");
        Assert.assertTrue(soccerResultEntryPage.ddpOrderBy.isDisplayed(),"Failed! Order By dropdown is not displayed");
        Assert.assertTrue(soccerResultEntryPage.ddpStatus.isDisplayed(),"Failed! Status dropdown is not displayed");
        Assert.assertTrue(soccerResultEntryPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("2 notes are displayed correctly");
        soccerResultEntryPage.filterResult("Normal","","All","KOT","All",true);

        Assert.assertEquals(soccerResultEntryPage.lblYellowcells.getText(), "Yellow cells are fields that 7M doesn't provide info.","Failed! Note 1 is not displayed");
        Assert.assertEquals(soccerResultEntryPage.lblUpdatenegative.getText(), "Update negative score to VOID bet, update score from negative to positive number will be UNVOID.","Failed! Note 2 is not displayed");
        log("Result Entry table header columns are correctly display");
        Assert.assertEquals(soccerResultEntryPage.tbResult.getHeaderNameOfRows(), ResultEntry.RESULT_SOCCER_TABLE_HEADER,"FAILED! Result table header is incorrect display");
        log("INFO: Executed completely");
    }


    @Test(groups = {"regression1"})
    public void Soccer_ResultEntry_TC003(){
        log("@title: Validate UI on Soccer  Result Entry  is correctly displayed");
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
