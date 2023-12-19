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
    public void EventMappingTC_2091(){
        log("@title: Validate Event Mapping page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Event Mapping");
        EventMappingPage eventMappingPage = welcomePage.navigatePage(SPORT,EVENT_MAPPING, EventMappingPage.class);
        log("Validate Event Mapping page is displayed with correctly title");
        log("Date, Sport, League, Event, Provider, Provider League, Provider Event, Provider Event Date, Submit button and Map button");
        Assert.assertEquals(eventMappingPage.lblDate.getText(), "Date","Failed! Date datetime picker is not displayed!");
        Assert.assertEquals(eventMappingPage.ddSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddLeague.getOptions().contains("All"),"Failed! League dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddEvent.getOptions().contains("All"),"Failed! Event dropdown is not displayed!");
        Assert.assertEquals(eventMappingPage.ddProvider.getOptions(),PROVIDER_LIST,"Failed! Provider dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddProviderLeague.getOptions().contains("All"),"Failed! Provider League dropdown is not displayed!");
        Assert.assertTrue(eventMappingPage.ddProviderEvent.getOptions().contains("All"),"Failed! Provider Event dropdown is not displayed!");
        Assert.assertEquals(eventMappingPage.lblProviderEventDate.getText(),"Provider Event Date","Failed! Provider Event Date datetime picker is not displayed!");
        Assert.assertEquals(eventMappingPage.btnSubmit.getText(),"Submit","Failed! Submit button is not displayed!");
        Assert.assertEquals(eventMappingPage.btnMap.getText(),"Map","Failed! Map button is not displayed!");
        log("Event, Provider Event and Mapped List table header columns are correctly display");
        Assert.assertEquals(eventMappingPage.tbEvent.getHeaderNameOfRows(), EventMapping.EVENT_TABLE_HEADER,"FAILED! Event table header is incorrect display");
        Assert.assertEquals(eventMappingPage.tbProviderEvent.getHeaderNameOfRows(), EventMapping.PROVIDER_EVENT_TABLE_HEADER,"FAILED! Provider Event table header is incorrect display");
        Assert.assertEquals(eventMappingPage.tbMappedList.getHeaderNameOfRows(), EventMapping.MAPPED_LIST_TABLE_HEADER,"FAILED! Mapped List table header is incorrect display");
        log("INFO: Executed completely");
    }
}
