package testcases.sb11test.soccer;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.generalReports.BookieStatementPage;
import pages.sb11.generalReports.ClientStatementPage;
import pages.sb11.soccer.SPPPage;
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
        log("@title: Validate WL in Client Statement matched with SPP page (#AQS-2073)");
        log("Precondition:Group code ’37 Peter 27 l1’ has 1 player G60755A5A5AA026\n" +
                "The player has data on the filtered date (e.g. 15/11/2022)\n" +
                "Client: PO137 – Peter (No.37 Peter), client agent: PO1ID101\n");
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));
        String clientValue = String.format("%s - %s",superCode, clientCode );

        log("@Step 1: Go to Client Statement >> client point >> select the client");
        ClientStatementPage clientPage = welcomePage.navigatePage(GENERAL_REPORTS,CLIENT_STATEMENT,ClientStatementPage.class);
        clientPage.filter("Client Point","Kastraki Limited",FINANCIAL_YEAR,clientValue,date,date);

        log("@Step 2: Click the client agent >> find the player >> observe win/loss");
        String winlosePlayer = clientPage.getMemberSummary(agentCode,accountCode).get(8);

        log("@Step 3: Go to SPP >> select all leagues >> select the group");
        log("Step 4: Select the date 15/11/2022 >> click Show");
        SPPPage sppPage = clientPage.navigatePage(SOCCER,SPP,SPPPage.class);
        sppPage.filter("Group","Smart Group","QA Smart Master","QA Smart Agent",date,date);
        String winloseSPP = sppPage.getRowDataOfGroup(smartGroup).get(sppPage.colWL-1);

        log("@verify 1: Validate the win/loss in the Client statement (step 2) matches with the win/loss of the group in the SPP page");
        Assert.assertTrue(sppPage.verifyAmountDataMatch(winlosePlayer,winloseSPP),
                String.format("Failed! Please check winloss in client statemet %s and spp page %s",winlosePlayer,winloseSPP));

    }

    @Test(groups = {"smokeisa"})
    @Parameters({"bookieCode","accountCode","accountCurrency","bookieMasterCode","smartGroup"})
    @TestRails(id = "311")
    public void SPP_TC_311(String bookieCode, String accountCode, String accountCurrency,String bookieMasterCode,String smartGroup)  {
        log("@title:Validate WL in Bookie Statement matched with SPP page (#AQS-2073)");
        log("@Precondition: Group code ’37 Peter 27 l1’ has 1 player 'G60755A5A5AA026'\n" +
                "The player has data on the filtered date (e.g. 15/11/2022)\n" +
                "Bookie: BetISN, Master code: Ma-G60755A5A5-Peter, CUR: IDR");
        String date = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT +7"));

        log("@Step 1: Go to Bookie Statement >> select currency as IDR to limit the returned data");
        log("@Step 2: Input bookie code as BetISN >> click Show");
        log("@Step 3: Find the master code >> click MS link at the master code");
        log("@Step 4: Find the player >> observe win/loss");
        BookieStatementPage bookieStatementPage = welcomePage.navigatePage(GENERAL_REPORTS,BOOKIE_STATEMENT,BookieStatementPage.class);
        bookieStatementPage.filter("","","Super Master",date, date,bookieCode,accountCurrency);
        String winlosePlayer = bookieStatementPage.getMemberSummary(accountCode,bookieMasterCode,accountCode).get(7);

        log("@Step 5: Go to SPP >> select all leagues >> select the group");
        log("@Step 6: Select the date 15/11/2022 >> click Show");
        log("@Step 7: Observe the win/loss of the group");
        SPPPage sppPage = bookieStatementPage.navigatePage(SOCCER,SPP,SPPPage.class);
        sppPage.filter("Group","Smart Group","QA Smart Master","QA Smart Agent",date,date);
        String winloseSPP = sppPage.getRowDataOfGroup(smartGroup).get(sppPage.colWL);

        log("@verify 1: Validate the win/loss in the Client statement (step 2) matches with the win/loss of the group in the SPP page");
        Assert.assertTrue(sppPage.verifyAmountDataMatch(winlosePlayer,winloseSPP),
                String.format("Failed! Please check winloss in client statemet %s and spp page %s",winlosePlayer,winloseSPP));
    }
}
