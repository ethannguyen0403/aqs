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

    @Test(groups = {"regression","ethan3.0"})
    @TestRails(id = "2087")
    public void Cricket_ResultEntry_2087() {
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

    @Test(groups = {"regression","ethan5.0"})
    @TestRails(id = "2088")
    public void Cricket_ResultEntry_2088(){
        log("@title: Validate UI on Cricket  Result Entry  is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Precondition: Have a specific League Name, Home Team, Away Team for testing line");
        String leagueName ="QA League";
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        Event event = new Event.Builder()
                .sportName(CRICKET)
                .leagueName(leagueName)
                .eventDate(date)
                .home("QA Team 01")
                .away("QA Team 02")
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
        cricketResultEntryPage.verifyUI();
        log("INFO: Executed completely");
    }
}
