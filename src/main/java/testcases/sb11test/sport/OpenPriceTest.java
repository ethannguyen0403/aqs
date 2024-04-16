package testcases.sb11test.sport;

import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.soccer.BBTPage;
import pages.sb11.sport.BLSettingPage;
import pages.sb11.sport.EventMappingPage;
import pages.sb11.sport.OpenPricePage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.awt.*;

import static common.SBPConstants.*;

public class OpenPriceTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2092")
    public void OpenPriceTC_2092(){
        log("@title: Validate Open Price page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("Validate Event Mapping page is displayed with correctly title");
        Assert.assertTrue(openPricePage.getTitlePage().contains(OPEN_PRICE), "Failed! Open Price page for Cricket sport is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2093")
    public void OpenPriceTC_2093(){
        log("@title: Validate UI on Open Price is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("Verify 1: Validate UI on Open Price is correctly displayed");
        log("Date, Show League button, Leagues and Show button");
        openPricePage.filterResult("","All",true);
        Assert.assertEquals(openPricePage.lblDate.getText(),"Date","Failed! Date datetime picker is not displayed!");
        Assert.assertEquals(openPricePage.btnShowLeagues.getText(),"Show Leagues","Failed! Show League button is not displayed!");
        Assert.assertEquals(openPricePage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        log("Event table header columns is correctly display");
        log("Header is " + openPricePage.tbOpenPrice.getHeaderNameOfRows());
        Assert.assertEquals(openPricePage.tbOpenPrice.getHeaderNameOfRows(), OpenPrice.TABLE_HEADER,"FAILED! Open Price table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2094")
    public void OpenPriceTC_2094(){
        log("@title: Validate League list is displayed correctly when clicking Show Leagues");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("@Step 3:  Open League filter");
        openPricePage.openLeagueFilter();
        log("Validate League list is displayed correctly");
        Assert.assertTrue(openPricePage.getAllOptionNameFilter().size()>0, "FAILED! League list is having no item");
//        int leagueSize = openPricePage.ddpLeague.getNumberOfItems();
//        Assert.assertTrue(leagueSize > 0, "Failed! League list is having no item" );
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2095")
    public void OpenPriceTC_2095(){
        log("@title: Validate selected League is displayed correctly when clicking Show");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Sport > Open Price");
        OpenPricePage openPricePage = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("@Step 3:  Select Date and click Show League ");
        String league = openPricePage.getFirstLeague();
        log("@Step 4: Select a league and click Show");
        openPricePage.filterResult("",league,true);
        log("Validate selected League is displayed correctly when clicking Show");
        Assert.assertTrue(openPricePage.isLeagueExist(league),"FAILED! League "+ league+" does not display in the list");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","2024.V.3.0"})
    @TestRails(id = "2096")
    public void OpenPriceTC_2096(){
        log("@title: Validate updated Open Price is displayed correctly on BBT page");
        String ft12HAHome = "1.00";
        String ft12HAAway = "2.00";
        String ft12Draw = "3.00";
        String ftHDPHome = "0.25";
        String ftHDPAway = "1.75";
        String ftHDPPriceHome = "4.00";
        String ftHDPPriceAway = "5.00";
        String ftOUHDPHome = "0.75";
        String ftOUHDPAway = "1.25";
        String ftOUPriceHome = "6.00";
        String ftOUPriceAway = "7.00";
        log("@Step 1: Access Sport > Open Price");
        OpenPricePage page = welcomePage.navigatePage(SPORT,OPEN_PRICE, OpenPricePage.class);
        log("@Step 2: Select Date and click Show League");
        log("@Step 3: Select a league and click Show");
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7);
        String league = page.showFirstLeague(date);
        String homeTeamName = page.getFirstEvent().split("\n")[0];
        String awayTeamName = page.getFirstEvent().split("\n")[1];
        log("@Step 4: Fill result on any event and click Submit");
        page.fillOpenPriceFirstEvent(ft12HAHome,ft12HAAway,ft12Draw,ftHDPHome,ftHDPAway,ftHDPPriceHome,ftHDPPriceAway,ftOUHDPHome,ftOUHDPAway,ftOUPriceHome,ftOUPriceAway,true);
        log("@Step 5: Navigate to Soccer > BBT");
        BBTPage bbtPage = welcomePage.navigatePage(SOCCER,BBT, BBTPage.class);
        log("@Step 6: Filter with event at step 4");
        bbtPage.filter("", "","","",date,date,"","",league);
        log("Verify 1: Updated Open Price is displayed correctly on BBT page");
        bbtPage.verifyOpenPriceDisplay(homeTeamName, ft12HAHome,ft12HAAway,ft12Draw,ftHDPHome,ftHDPAway,ftHDPPriceHome,ftHDPPriceAway,ftOUHDPHome,ftOUHDPAway,ftOUPriceHome,ftOUPriceAway,true);
        bbtPage.verifyOpenPriceDisplay(awayTeamName, ft12HAHome,ft12HAAway,ft12Draw,ftHDPHome,ftHDPAway,ftHDPPriceHome,ftHDPPriceAway,ftOUHDPHome,ftOUHDPAway,ftOUPriceHome,ftOUPriceAway,false);
        log("INFO: Executed Completely!");
    }
}
