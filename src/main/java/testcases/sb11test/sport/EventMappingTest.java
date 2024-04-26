package testcases.sb11test.sport;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.EventMappingPage;
import pages.sb11.sport.SoccerLeagueSeasonTeamInfoPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class EventMappingTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2090")
    public void EventMappingTC_001(){
        log("@title: Validate Event Mapping page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Mapping");
        EventMappingPage eventMappingPage = welcomePage.navigatePage(SPORT,EVENT_MAPPING, EventMappingPage.class);
        log("Validate Event Mapping page is displayed with correctly title");
        Assert.assertTrue(eventMappingPage.getTitlePage().contains(EVENT_MAPPING), "Failed! Event Mapping page for Cricket sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2091")
    public void EventMappingTC_2091(){
        log("@title: Validate Event Mapping page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Mapping");
        EventMappingPage eventMappingPage = welcomePage.navigatePage(SPORT,EVENT_MAPPING, EventMappingPage.class);
        log("Validate Event Mapping page is displayed with correctly title");
        eventMappingPage.verifyUI();
        log("INFO: Executed completely");
    }
}
