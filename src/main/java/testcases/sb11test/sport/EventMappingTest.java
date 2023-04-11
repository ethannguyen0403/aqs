package testcases.sb11test.sport;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.sport.EventMappingPage;
import pages.sb11.sport.SoccerLeagueSeasonTeamInfoPage;
import testcases.BaseCaseAQS;

import static common.SBPConstants.*;

public class EventMappingTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    public void EventMappingTC_001(){
        log("@title: Validate Event Mapping page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Mapping");
        EventMappingPage eventMappingPage = welcomePage.navigatePage(SPORT,EVENT_MAPPING, EventMappingPage.class);
        log("Validate Event Mapping page is displayed with correctly title");
        Assert.assertTrue(eventMappingPage.getTitlePage().contains(EVENT_MAPPING), "Failed! Event Mapping page for Cricket sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    public void EventMappingTC_002(){
        log("@title: Validate Event Mapping page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Mapping");
        EventMappingPage eventMappingPage = welcomePage.navigatePage(SPORT,EVENT_MAPPING, EventMappingPage.class);
        log("Validate Event Mapping page is displayed with correctly title");

        log("INFO: Executed completely");
    }
}
