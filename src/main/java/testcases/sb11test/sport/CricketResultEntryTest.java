package testcases.sb11test.sport;

import com.paltech.utils.DateUtils;
import objects.Event;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.CricketResultEntryPage;
import pages.sb11.sport.EventSchedulePage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class CricketResultEntryTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2087")
    public void Cricket_ResultEntry_TC001(){
        log("@title: Validate Result Entry for Cricket is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Cricket");
        CricketResultEntryPage cricketResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, CricketResultEntryPage.class);
        cricketResultEntryPage.goToSport("Cricket");
        log("Validate Result Entry page for Cricket sport is displayed with correctly title");
        Assert.assertTrue(cricketResultEntryPage.getTitlePage().contains("Cricket"), "Failed! Result Entry page for Cricket sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression1"})
    @TestRails(id = "2088")
    public void Cricket_ResultEntry_TC002(){
        log("@title: Validate UI on Cricket  Result Entry  is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Precondition: Have a specific League Name, Home Team, Away Team for testing line");
        String leagueName ="Australia NCL Women";
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        Event event = new Event.Builder()
                .sportName("Cricket")
                .leagueName(leagueName)
                .eventDate(date)
                .home("Ireland")
                .away("Nepal")
                .openTime("14:00")
                .eventStatus("Scheduled")
                .eventDate(date)
                .isLive(false)
                .isN(false)
                .build();

        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.goToSport(event.getSportName());
        eventSchedulePage.showLeague(event.getLeagueName(),event.getEventDate());
        eventSchedulePage.addEvent(event);
        log("@Step 2: Access Sport > Result Entry");
        log("@Step 3: Click Cricket");
        CricketResultEntryPage cricketResultEntryPage = welcomePage.navigatePage(SPORT,RESULT_ENTRY, CricketResultEntryPage.class);
        cricketResultEntryPage.goToSport("Cricket");
        log("Validate UI Info display correctly");
        log("Type, Date, Show League button, Leagues, Order By, Status and Show button");
        Assert.assertTrue(cricketResultEntryPage.ddpType.isDisplayed(),"Failed! Type dropdown is not displayed");
        Assert.assertTrue(cricketResultEntryPage.txtDateTime.isDisplayed(),"Failed! Date datetimepicker is not displayed");
        Assert.assertTrue(cricketResultEntryPage.btnShowLeagues.isDisplayed(),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(cricketResultEntryPage.ddpLeague.isDisplayed(),"Failed! League dropdown is not displayed");
        Assert.assertTrue(cricketResultEntryPage.ddpOrderBy.isDisplayed(),"Failed! Order By dropdown is not displayed");
        Assert.assertTrue(cricketResultEntryPage.ddpStatus.isDisplayed(),"Failed! Status dropdown is not displayed");
        Assert.assertTrue(cricketResultEntryPage.btnShow.isDisplayed(),"Failed! Show button is not displayed");
        log("Result Entry table header columns are correctly display");
        Assert.assertEquals(cricketResultEntryPage.tbResult.getHeaderNameOfRows(), ResultEntry.RESULT_CRICKET_TABLE_HEADER,"FAILED! Result table header is incorrect display");
        log("INFO: Executed completely");
    }
}
