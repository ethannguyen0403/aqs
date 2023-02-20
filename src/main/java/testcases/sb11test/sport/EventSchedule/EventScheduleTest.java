package testcases.sb11test.sport.EventSchedule;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.sport.EventSchedulePage;
import pages.sb11.trading.BetEntryPage;
import pages.sb11.trading.SoccerBetEntryPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;
import static common.SBPConstants.EVENT_SCHEDULE;

public class EventScheduleTest extends BaseCaseAQS {

    @TestRails(id="1041")
    @Parameters({"accountCode"})
    @Test(groups = {"smoke"})
    public void EventSchedule_TC1041(){
        log("@title: Validate users can place Mixed Sports bets successfully");
        log("@Precondition: Have a specific League Name, Home Team, Away Team for testing line\n" +
                "League: QA Soccer League No Bet\n" +
                "Home Team: Team Home Test\n" +
                "Away Team: Awa");
        String leagueName ="QA Soccer League";
        String companyUnit = "Kastraki Limited";
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        String dateAPI = String.format(DateUtils.getDate(0,"yyyy-MM-dd","GMT +7"));
        Event event = new Event.Builder()
                .sportName("Soccer")
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

        log("@Step 2: Navigate to Sport> Event Schedule");
        log("@Step 3: Select league name and date then click Show");
        log("@Step 4: Select home team and away team then input time: 13:00,and other fields: Live, No, TV");
        log("@Step 5: Click Submit");
        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.goToSport(event.getSportName());
        eventSchedulePage.showLeague(event.getLeagueName(),event.getEventDate());
        eventSchedulePage.addEvent(event);

        log("@Step 5: In the Schedules List section, select Show league link, find and select league\" QA Soccer League\" and click the show button");
        log("@Verify 1: Verify event info displayed correctly in the Schedule List");
        Assert.assertTrue(eventSchedulePage.getSuccessMessage().contains("Event schedule is created successfully"),"FAILED! Success message is incorrect displayed");
        eventSchedulePage.showScheduleList(true,"QA Team 01",date);
        Assert.assertTrue(eventSchedulePage.verifyEventInSchedulelist(event),"Failed! Event info incorrect after created");

        log("@Step 6: Navigate to Trading> Bet Entry go to Soccer page and filter the league in today");
        BetEntryPage betEntryPage = eventSchedulePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(companyUnit,date,leagueName);

        log("@Verify 2: League info is correctly displayed in Bet Entry page");
        Assert.assertTrue(soccerBetEntryPage.isLeagueExist(leagueName),"FAILED! League "+ leagueName+" does not display in the list");
        Assert.assertTrue(soccerBetEntryPage.isEventExist(event), "FAILED! Event "+event.getHome() +" & "+ event.getAway()+" under league "+ leagueName+" does not display in the list");

        log("@Step 5: Postcondition: Delete the event");
        eventSchedulePage = soccerBetEntryPage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.deleteEvent(event);
        log("INFO: Executed completely");
    }


}
