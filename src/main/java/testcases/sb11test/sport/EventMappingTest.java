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

    @Test(groups = {"regression"})
    @TestRails(id = "2091")
    public void EventMappingTC_002(){
        log("@title: Validate Event Mapping page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Mapping");
        EventMappingPage eventMappingPage = welcomePage.navigatePage(SPORT,EVENT_MAPPING, EventMappingPage.class);
        log("Validate Event Mapping page is displayed with correctly title");
        log("Date, Sport, League, Event, Provider, Provider League, Provider Event, Provider Event Date, Submit button and Map button");
        Assert.assertTrue(eventMappingPage.txtDate.isDisplayed(),"Failed! Date datetime picker is not displayed!");
        Assert.assertTrue(eventMappingPage.ddSport.isDisplayed(),"Failed! Sport dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddLeague.isDisplayed(),"Failed! League dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddEvent.isDisplayed(),"Failed! Event dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddProvider.isDisplayed(),"Failed! Provider dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddProviderLeague.isDisplayed(),"Failed! Provider League dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddProviderEvent.isDisplayed(),"Failed! Provider Event dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.txtProviderDate.isDisplayed(),"Failed! Provider Event Date datetime picker is not displayed!");
        Assert.assertTrue(eventMappingPage.btnSubmit.isDisplayed(),"Failed! Submit button is not displayed!");
        Assert.assertTrue(eventMappingPage.btnMap.isDisplayed(),"Failed! Map button is not displayed!");
        log("Event, Provider Event and Mapped List table header columns are correctly display");
        Assert.assertEquals(eventMappingPage.tbEvent.getHeaderNameOfRows(), EventMapping.EVENT_TABLE_HEADER,"FAILED! Event table header is incorrect display");
        Assert.assertEquals(eventMappingPage.tbProviderEvent.getHeaderNameOfRows(), EventMapping.PROVIDER_EVENT_TABLE_HEADER,"FAILED! Provider Event table header is incorrect display");
        Assert.assertEquals(eventMappingPage.tbMappedList.getHeaderNameOfRows(), EventMapping.MAPPED_LIST_TABLE_HEADER,"FAILED! Mapped List table header is incorrect display");
        log("INFO: Executed completely");
    }
}
