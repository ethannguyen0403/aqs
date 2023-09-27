package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.BookieStatementPage;
import pages.sb11.generalReports.ClientStatementPage;
import pages.sb11.soccer.LeaguePerformancePage;
import pages.sb11.soccer.PerformanceByMonthPage;
import pages.sb11.soccer.SPPPage;
import pages.sb11.soccer.popup.PTPerformancePopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;
import static common.SBPConstants.FINANCIAL_YEAR;

public class SPPTest extends BaseCaseAQS {
    @Test(groups = {"smoke"})
    @Parameters({"accountCode","smartGroup","superCode","clientCode","agentCode"})
    @TestRails(id = "1002")
    public void SPP_TC_1002(String accountCode, String smartGroup, String superCode,String clientCode,String agentCode){
         /*NOTE: Create QA Smart Master and QA Smart Agent for STG and PR) for consistent data*/
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));
        String clientValue = String.format("%s - %s",superCode, clientCode );
        log("@title: Validate WL in Client Statement matched with SPP page (#AQS-2073)");
        log("Precondition:Group code ’"+smartGroup+" has 1 player "+accountCode+"\n" +
                "The player has data on the filtered date (e.g."+date+" \n "+
                "Client: "+clientValue+", client agent: "+agentCode+"\n");

        log("@Step 1: Go to Client Statement >> client point >> select the client");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        clientPage.filter("Client Point","Kastraki Limited",FINANCIAL_YEAR,clientValue,date,date);

        log("@Step 2: Click the client agent >> find the player >> observe win/loss");
        String winlosePlayer = clientPage.getMemberSummary(agentCode,accountCode).get(7);

        log("@Step 3: Go to SPP >> select all leagues >> select the group");
        log("Step 4: Select the date 15/11/2022 >> click Show");
        SPPPage sppPage = clientPage.navigatePage(SOCCER,SPP,SPPPage.class);
        sppPage.filter("Soccer", "Group","Smart Group","QA Smart Master","QA Smart Agent",date,date);
        String winloseSPP = sppPage.getRowDataOfGroup(smartGroup).get(sppPage.colWL-1);

        log("@verify 1: Validate the win/loss in the Client statement (step 2) matches with the win/loss of the group in the SPP page");
        Assert.assertTrue(sppPage.verifyAmountDataMatch(winlosePlayer,winloseSPP),
                String.format("Failed! Please check winloss in client statemet %s and spp page %s",winlosePlayer,winloseSPP));

    }

    @Test(groups = {"smoke"})
    @Parameters({"bookieCode","accountCode","accountCurrency","bookieMasterCode","smartGroup","bookieSuperMasterCode"})
    @TestRails(id = "311")
    public void SPP_TC_311(String bookieCode,String accountCode, String accountCurrency,String bookieMasterCode,String smartGroup,String bookieSuperMasterCode)  {
        log("@title:Validate WL in Bookie Statement matched with SPP page (#AQS-2073)");
        log("@Precondition: Group code ’37 Peter 27 l1’ has 1 player 'G60755A5A5AA026'\n" +
                "The player has data on the filtered date (e.g. 15/11/2022)\n" +
                "Bookie: BetISN, Master code: Ma-G60755A5A5-Peter, CUR: IDR");
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy","GMT +7"));

        log("@Step 1: Go to Bookie Statement >> select currency as IDR to limit the returned data");
        log("@Step 2: Input bookie code as BetISN >> click Show");
        log("@Step 3: Find the master code >> click MS link at the master code");
        log("@Step 4: Find the player >> observe win/loss");
        BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,BOOKIE_STATEMENT,BookieStatementPage.class);
        bookieStatementPage.filter("","","Super Master",date, date,bookieCode,accountCurrency);
        String winlosePlayer = bookieStatementPage.getWinLossofPlayer(bookieSuperMasterCode, bookieMasterCode,accountCode);

        log("@Step 5: Go to SPP >> select all leagues >> select the group");
        log("@Step 6: Select the date e.g.15/11/2022 >> click Show");
        log("@Step 7: Observe the win/loss of the group");
        SPPPage sppPage = bookieStatementPage.navigatePage(SOCCER,SPP,SPPPage.class);
        sppPage.filter("Soccer", "Group","Smart Group","QA Smart Master","QA Smart Agent",date,date);
        String winloseSPP = sppPage.getRowDataOfGroup(smartGroup).get(sppPage.colWL-1);

        log("@verify 1: Validate the win/loss in the Client statement (step 2) matches with the win/loss of the group in the SPP page");
        Assert.assertTrue(sppPage.verifyAmountDataMatch(winlosePlayer,winloseSPP),
                String.format("Failed! Please check winloss in client statemet %s and spp page %s",winlosePlayer,winloseSPP));
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2129")
    public void SPP_TC_2129(){
        log("@title: Validate SPP page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER,SPP,SPPPage.class);
        log("Validate SPP page is displayed with correctly title");
        Assert.assertTrue(sppPage.getTitlePage().contains(SPP), "Failed! SPP page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2130")
    public void SPP_TC_2130(){
        log("@title: Validate SPP page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER,SPP,SPPPage.class);
        log(" Validate UI Info display correctly");
        log("Company Unit, Report By, Punter Type, Smart Master, Smart Agent, From Date, To Date and Show button");
        Assert.assertEquals(sppPage.ddSport.getOptions(),SPORT_LIST_ALL,"Failed! Sport dropdown is not displayed");
        Assert.assertTrue(sppPage.ddpReportBy.getOptions().contains("Group"),"Failed! Report By dropdown is not displayed");
        Assert.assertTrue(sppPage.ddpPunterType.getOptions().contains("Smart Group"),"Failed! Punter Type dropdown is not displayed");
        Assert.assertTrue(sppPage.ddpSmartMaster.getOptions().contains("QA Smart Master"),"Failed! Smart Master dropdown is not displayed");
        Assert.assertTrue(sppPage.ddpSmartAgent.getOptions().contains("QA Smart Agent"),"Failed! Smart Agent dropdown is not displayed");
        Assert.assertEquals(sppPage.lblFromDate.getText(),"From Date","Failed! From Date datetime picker is not displayed");
        Assert.assertEquals(sppPage.lblToDate.getText(),"To Date","Failed! To Date datetime picker is not displayed");
        log("Show Tax Amount, Show Bet Types, Show Leagues, Smart Group, Order By Win%, Reset All Filters and More Filters");
        Assert.assertEquals(sppPage.lblShowTaxAmount.getText(),"Show Tax Amount","Failed! Show Tax Amount checkbox is not displayed");
        Assert.assertTrue(sppPage.btnShowBetTypes.getText().contains("Show Bet Types"),"Failed! Show Bet Types button is not displayed");
        Assert.assertTrue(sppPage.btnShowLeagues.getText().contains("Show Leagues"),"Failed! Show Leagues button is not displayed");
        Assert.assertTrue(sppPage.btnSmartGroup.getText().contains("Smart Group"),"Failed! Smart Group button is not displayed");
        Assert.assertEquals(sppPage.btnReset.getText(),"Reset All Filters","Failed! Reset button is not displayed");
        Assert.assertEquals(sppPage.btnMoreFilters.getText(),"More Filters","Failed! More Filters button is not displayed");
        Assert.assertEquals(sppPage.btnShow.getText(),"Show","Failed! Show button is not displayed");
        log("SPP table header columns is correctly display");
        Assert.assertEquals(sppPage.tblSPP.getHeaderNameOfRows(), SBPConstants.SPPPage.TABLE_HEADER,"FAILED! SPP Bets table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2131")
    public void SPP_TC_2131(){
        log("@title: Validate Tax column is displayed after checking Show Tax Amount");
        String date = String.format(DateUtils.getDate(-3,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER,SPP,SPPPage.class);
        log("@Step 3: Filter with valid data");
        sppPage.filter("Soccer", "Group","Smart Group","[All]","[All]",date,date);
        log("@Step 4: Check on Show Tax Amount checkbox");
        sppPage.cbShowTaxAmount.click();
        log("Validate Tax column is displayed after checking Show Tax Amount");
        Assert.assertEquals(sppPage.tblSPPTax.getHeaderNameOfRows(), SBPConstants.SPPPage.TABLE_HEADER_WITH_TAX,"FAILED! SPP Bets table header is incorrect display");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2132")
    @Parameters({"smartGroup"})
    public void SPP_TC_2132(String smartGroup){
        log("@title: Validate League Performance page is displayed successfully when clicking on Group code");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER,SPP,SPPPage.class);
        log("@Step 3: Filter with valid data");
        sppPage.filter("Soccer", "Group","Smart Group","[All]","[All]",fromdate,todate);
        log("@Step 4: Click on any group code");
        LeaguePerformancePage leaguePerformancePage = sppPage.openLeaguePerformance(smartGroup);
        log("Validate League Performance is displayed correctly title");
        Assert.assertTrue(leaguePerformancePage.getTitlePage().contains("League Performance"), "Failed! League Performance page is not displayed");
        log("Validate 5 tables should displayed with format");
        String fromDateconvert = DateUtils.formatDate(fromdate,"dd/MM/yyyy","yyyy-MM-dd");
        String toDateconvert = DateUtils.formatDate(todate,"dd/MM/yyyy","yyyy-MM-dd");
        Assert.assertEquals(leaguePerformancePage.getTableHeaderInRange(), smartGroup + " - League Performance for " + fromDateconvert + " To " + toDateconvert);
        Assert.assertEquals(leaguePerformancePage.getTableHeader1Month(), smartGroup + " - League Performance for Last 1 Month");
        Assert.assertEquals(leaguePerformancePage.getTableHeader3Months(), smartGroup + " - League Performance for Last 3 Months");
        Assert.assertEquals(leaguePerformancePage.getTableHeader6Months(), smartGroup + " - League Performance for Last 6 Months");
        Assert.assertEquals(leaguePerformancePage.getTableHeader1Year(), smartGroup + " - League Performance for Last 1 Year");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2133")
    @Parameters({"smartGroup","accountCurrency"})
    public void SPP_TC_2133(String smartGroup, String accountCurrency){
        log("@title: Validate Performance by Month page is displayed succefully when clicking on MP");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER,SPP,SPPPage.class);
        log("@Step 3: Filter with valid data");
        sppPage.filter("Soccer", "Group","Smart Group","[All]","[All]",fromdate,todate);
        log("@Step 4: Click on any data at MP column");
        PerformanceByMonthPage performanceByMonthPage = sppPage.openPerfByMonth(smartGroup);
        log("Validate Performance By Month is displayed correctly title");
        Assert.assertTrue(performanceByMonthPage.getTitlePage().contains("Performance By Month"), "Failed! Performance By Month page is not displayed");
        log("Validate group code name is displayed correctly on header with format");
        Assert.assertEquals(performanceByMonthPage.getTableHeader(), smartGroup + " - " + accountCurrency + " - Last 12 Month Performance");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2134")
    @Parameters({"smartGroup","accountCode"})
    public void SPP_TC_2134(String smartGroup, String accountCode){
        log("@title: Validate Account PT Performance page is displayed succefully when clicking on PT");
        String fromdate = String.format(DateUtils.getDate(-5,"dd/MM/yyyy","GMT +7"));
        String todate = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Soccer > SPP");
        SPPPage sppPage = welcomePage.navigatePage(SOCCER,SPP,SPPPage.class);
        log("@Step 3: Filter with valid data");
        sppPage.filter("Soccer", "Group","Smart Group","[All]","[All]",fromdate,todate);
        String PT = sppPage.getRowDataOfGroup(smartGroup).get(sppPage.colPT-1);
        log("@Step 4: Click on any data at PT column");
        PTPerformancePopup ptPerformancePopup = sppPage.openAccountPTPerf(smartGroup);
        log("Validate Account PT Performance page is displayed correctly title");
        Assert.assertTrue(ptPerformancePopup.getTitlePage().contains("Account PT Performance"), "Failed! PT Performance page is not displayed");
        log("Validate group code name is displayed correctly smart group name on header");
        Assert.assertTrue(ptPerformancePopup.isGroupNameDisplayed(smartGroup),"Failed! Group name "+ smartGroup + " is not displayed!");
        log("Validate PT% on SPP page is matched with PT% on Account PT Performance page");
        Assert.assertTrue(ptPerformancePopup.isAccountPTMatched(accountCode,PT),"Failed! PT is not matched!");
        log("INFO: Executed completely");
    }
}
