package testcases.sb11test.sport;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import objects.Event;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.ChartOfAccountPage;
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
    @Test(groups = {"smoke","ethan"})
    public void EventSchedule_TC1041(){
        log("@title: Validate the events is added in Schedule list and show correctly in Bet entry");
        log("@Precondition: Have a specific League Name, Home Team, Away Team for testing line\n" +
                "League: QA Soccer League No Bet\n" +
                "Home Team: Team Home Test\n" +
                "Away Team: Awa");
        String leagueName = "QA Soccer League";
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
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
        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        log("@Step 3: Select league name and date then click Show");
        log("@Step 4: Select home team and away team then input time: 13:00,and other fields: Live, No, TV");
        log("@Step 5: Click Submit");
        eventSchedulePage.showLeague(event.getLeagueName(),event.getEventDate());
        eventSchedulePage.addEvent(event);
        log("@Step 6: In the Schedules List section, select Show league link, find and select league\" QA Soccer League\" and click the show button");
        log("@Verify 1: Verify event info displayed correctly in the Schedule List");
        Assert.assertTrue(eventSchedulePage.getSuccessMessage().contains("Event schedule is created successfully"),"FAILED! Success message is incorrect displayed");
        eventSchedulePage.showScheduleList(leagueName,true,"QA Team 01",date);
        Assert.assertTrue(eventSchedulePage.verifyEventInSchedulelist(event),"Failed! Event info incorrect after created");

        log("@Step 7: Navigate to Trading> Bet Entry go to Soccer page and filter the league in today");
        BetEntryPage betEntryPage = eventSchedulePage.navigatePage(TRADING, BET_ENTRY,BetEntryPage.class);
        SoccerBetEntryPage soccerBetEntryPage = betEntryPage.goToSoccer();
        soccerBetEntryPage.showLeague(KASTRAKI_LIMITED,date,leagueName);

        log("@Verify 2: League info is correctly displayed in Bet Entry page");
        Assert.assertTrue(soccerBetEntryPage.isLeagueExist(leagueName),"FAILED! League "+ leagueName+" does not display in the list");
        Assert.assertTrue(soccerBetEntryPage.isEventExist(event), "FAILED! Event "+event.getHome() +" & "+ event.getAway()+" under league "+ leagueName+" does not display in the list");

        log("@Postcondition: Delete the event");
        eventSchedulePage = soccerBetEntryPage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        eventSchedulePage.deleteEvent(event);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2081")
    public void EventSchedule_TC001(){
        log("@title: Validate Event Schedule for Soccer is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Schedule");
        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        log("Validate the page is displayed with 2 tables:Event Schedule Soccer and Schedule List");
        Assert.assertTrue(eventSchedulePage.lblLeagueEntry.getText().contains("Soccer"), "Failed! League Entry table is not displayed");
        Assert.assertTrue(eventSchedulePage.lblScheduleList.getText().contains("Schedule List"), "Failed! Schedule List table is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2082")
    public void EventSchedule_TC002(){
        log("@title: Validate UI on Soccer Event Schedule is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Schedule");
        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        log("Validate UI Info display correctly");
        log("Event Schedule table: League, Date Time and Show button");
        Assert.assertEquals(eventSchedulePage.lblLeague.getText(),"League","Failed! League dropdown is not displayed!");
        Assert.assertEquals(eventSchedulePage.lblDateTime.getText(),"Date Time","Failed! Date Time datetime picker is not displayed!");
        Assert.assertEquals(eventSchedulePage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("Schedule List table: Home, Away, Team, Show League, Datetime and Show button");
        Assert.assertEquals(eventSchedulePage.lblHome.getText(),"Home","Failed! Home radio button is not displayed!");
        Assert.assertEquals(eventSchedulePage.lblAway.getText(),"Away","Failed! Away radio button is not displayed!");
        Assert.assertEquals(eventSchedulePage.lblTeam.getText(),"Team","Failed! Team searchbox is not displayed!");
        Assert.assertEquals(eventSchedulePage.btnShowLeague.getText(),"Show League","Failed! Show League button is not displayed!");
        Assert.assertEquals(eventSchedulePage.btnShowSchedule.getText(),"Show","Failed! Show button is not displayed!");
        log("Event Schedule and Schedule List table header columns are correctly display");
        Assert.assertEquals(eventSchedulePage.tblLeagueBody.getHeaderNameOfRows(), EventSchedule.TABLE_HEADER_LEAGUE_LIST,"FAILED! League table header is incorrect display");
        Assert.assertEquals(eventSchedulePage.tblEventBody.getHeaderNameOfRows(), EventSchedule.TABLE_HEADER_SCHEDULE_LIST,"FAILED! Schedule table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2083")
    public void EventSchedule_TC003(){
        log("@title: Validate Event Schedule for Cricket is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Schedule");
        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        log("@Step 3: Select Cricket Sport");
        eventSchedulePage.goToSport("Cricket");
        log("Validate the page is displayed with 2 tables:Event Schedule Soccer and Schedule List");
        Assert.assertTrue(eventSchedulePage.lblLeagueEntry.getText().contains("Cricket"), "Failed! League Entry table is not displayed");
        Assert.assertTrue(eventSchedulePage.lblScheduleList.getText().contains("Schedule List"), "Failed! Schedule List table is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2084")
    public void EventSchedule_TC004(){
        log("@title: Validate UI on Cricket Event Schedule is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Schedule");
        EventSchedulePage eventSchedulePage = welcomePage.navigatePage(SPORT,EVENT_SCHEDULE, EventSchedulePage.class);
        log("@Step 3: Select Cricket Sport");
        eventSchedulePage.goToSport("Cricket");
        log("Validate UI Info display correctly");
        log("Event Schedule table: League, Date Time and Show button");
        Assert.assertEquals(eventSchedulePage.lblLeague.getText(),"League","Failed! League dropdown is not displayed!");
        Assert.assertEquals(eventSchedulePage.lblDateTime.getText(),"Date Time","Failed! Date Time datetime picker is not displayed!");
        Assert.assertEquals(eventSchedulePage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("Schedule List table: Home, Away, Team, Show League, Datetime and Show button");
        Assert.assertEquals(eventSchedulePage.lblHome.getText(),"Home","Failed! Home radio button is not displayed!");
        Assert.assertEquals(eventSchedulePage.lblAway.getText(),"Away","Failed! Away radio button is not displayed!");
        Assert.assertEquals(eventSchedulePage.lblTeam.getText(),"Team","Failed! Team searchbox is not displayed!");
        Assert.assertEquals(eventSchedulePage.btnShowLeague.getText(),"Show League","Failed! Show League button is not displayed!");
        Assert.assertEquals(eventSchedulePage.btnShowSchedule.getText(),"Show","Failed! Show button is not displayed!");
        log("Event Schedule and Schedule List table header columns are correctly display");
        Assert.assertEquals(eventSchedulePage.tblLeagueBody.getHeaderNameOfRows(), EventSchedule.TABLE_HEADER_LEAGUE_LIST,"FAILED! League table header is incorrect display");
        Assert.assertEquals(eventSchedulePage.tblEventBody.getHeaderNameOfRows(), EventSchedule.TABLE_HEADER_SCHEDULE_LIST,"FAILED! Schedule table header is incorrect display");
        log("INFO: Executed completely");
    }


}
