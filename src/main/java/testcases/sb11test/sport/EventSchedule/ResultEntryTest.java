package testcases.sb11test.sport.EventSchedule;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.ResultEntryPage;
import pages.sb11.sport.SoccerLeagueSeasonTeamInfoPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class ResultEntryTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    public void ResultEntry_TC001(){
        log("@title: Validate Result Entry for Soccer is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Soccer");
        ResultEntryPage resultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, ResultEntryPage.class);
        resultEntryPage.goToSport("Soccer");
        log("Validate Result Entry page for Soccer sport is displayed with correctly title");
        Assert.assertTrue(resultEntryPage.getTitlePage().contains("Soccer"), "Failed! Result Entry page for Soccer sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void ResultEntry_TC002(){
        log("@title: Validate UI on Soccer  Result Entry  is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Soccer");
        ResultEntryPage resultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, ResultEntryPage.class);
        resultEntryPage.goToSport("Soccer");
        log("Validate UI Info display correctly");
        log("Type, Date, Show League button, Leagues, Order By, Status and Show button");
        log("2 notes are displayed correctly");
        log("Result Entry table header columns are correctly display");


        log("INFO: Executed completely");
    }
}
