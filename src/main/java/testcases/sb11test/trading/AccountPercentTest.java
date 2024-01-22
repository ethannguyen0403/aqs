package testcases.sb11test.trading;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.ESSConstants;
import common.SBPConstants;
import objects.Event;
import objects.Order;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.soccer.SPPPage;
import pages.sb11.trading.AccountPercentPage;
import pages.sb11.trading.BetSettlementPage;
import testcases.BaseCaseAQS;
import utils.sb11.*;
import utils.testraildemo.TestRails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.*;

public class AccountPercentTest extends BaseCaseAQS {

    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "56")
    @Parameters({"bookieCode","accountCode","clientCode"})
    public void AccountPercent_56(String bookieCode, String accountCode, String clientCode) throws IOException {
        log("@Title: Validate account is able to set in both Account Listing and Account Listing");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        String ptSet  = "1.50000";
        log("@Step 3: Search the account configured in Account Listing page");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        log("@Step 4: Click Edit icon on the account in column Actual WinLoss %");
        try {
            log("@Step 5: Input percent value and click Save icon");
            accountPercentPage.editPercent(accountCode,ptSet,true);
            log("Verify 1: Able to set percent as normally without any error message");
            Assert.assertEquals(accountPercentPage.getAccPT(accountCode),ptSet,"Failed! Able not to set percent");
        } finally {
            log("@Post-step: set old pt value");
            String accountId = AccountSearchUtils.getAccountId(accountCode);
            String clientId = ClientSystemUtils.getClientId(clientCode);
            Double pt = 1.00000;
            AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode, pt);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "57")
    @Parameters({"password", "userNameOneRole"})
    public void AccountPercent_57(String password, String userNameOneRole) throws Exception{
        log("@Title: Validate accounts without permission cannot access page");
        String accountPercentUrl = environment.getSbpLoginURL() + "#/aqs-report/trading/account-percent";
        log("@Pre-conditions: Account is inactivated permission 'Account Percent'");
        log("@Step 1: Login to SB11 site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        DriverManager.getDriver().get(accountPercentUrl);
        log("@Step 2: https://bggqat.beatus88.com/#/aqs-report/trading/account-percent");
        log("Verify 1: User cannot access page");
        Assert.assertFalse(new AccountPercentPage().lblTitle.isDisplayed(), "FAILED! Account Percent page can access by external link");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "58")
    public void AccountPercent_58() {
        log("@Title: Validate accounts with permission can access page");
        log("@Pre-conditions: Account is activated permission 'Account Percent'");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Expand menu 'Soccer' and access 'Account Percent' page");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("Verify 1: User can access page 'Account Percent' page successfully");
        Assert.assertTrue(accountPercentPage.getTitlePage().contains(ACCOUNT_PERCENT), "FAILED! Account Percent page can not access");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "59")
    @Parameters({"bookieCode"})
    public void AccountPercent_59(String bookieCode) {
        log("@Title: Validate no record found when searching an invalid account code");
        log("@Pre-conditions 1: Account is activated permission 'Account Percent'");
        log("@Pre-conditions 2: Account logged in to site");
        log("@Step 1: Access Soccer > Account Percent page");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 2: Input Account Code with invalid value with sql query");
        accountPercentPage.filterAccount(bookieCode,"","*from");
        log("Verify 1: Message informs 'No record found.'");
        Assert.assertTrue(accountPercentPage.tbAccPercent.getControlOfCell(1,1,1,null).getText().contains(ESSConstants.NO_RECORD_FOUND), "FAILED! Account Percent page displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "60")
    public void AccountPercent_60() {
        log("@Title: Validate layout of page");
        log("@Pre-conditions: User has permission access to Account Percent page");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Observe layout of page");
        log("Verify 1: Page show with\n" +
                "Breadcrumb: Account Percent\n" +
                "Bookie and Type dropdown list\n" +
                "Account Code textbox with Search icon\n" +
                "Result table with columns: #, i, Account Code, Actual WinLoss%, CUR, Client Name");
        accountPercentPage.verifyLayoutOfPage();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "145")
    public void AccountPercent_145() {
        log("@Title: Validate the default values are shown on page ");
        log("@Pre-conditions: User has permission access to Account Percent page");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Observe default values show on page");
        log("Verify 1: Bookie = [Choose One]\n" +
                "Type = All\n" +
                "Account Code = blank\n" +
                "Result Table = No record found");
        Assert.assertEquals(accountPercentPage.ddpBookie.getFirstSelectedOption(),"[Choose One]","FAILED! Bookie dropdown displays incorrect");
        Assert.assertEquals(accountPercentPage.ddpType.getFirstSelectedOption(),AccountPercent.TYPE_LIST.get(0),"FAILED! Type dropdown displays incorrect");
        Assert.assertEquals(accountPercentPage.txtAccountCode.getAttribute("value"),"","FAILED! Account Code textbox displays incorrect");
        Assert.assertTrue(accountPercentPage.tbAccPercent.getControlOfCell(1,1,1,null).getText().contains(ESSConstants.NO_RECORD_FOUND),"FAILED! Table data displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "146")
    public void AccountPercent_146() {
        log("@Title: Validate Bookie is required");
        log("@Pre-conditions: User has permission access to Account Percent page");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Leave Bookie as default without choosing a bookie item");
        log("@Step 4: Click on Search icon and observe");
        accountPercentPage.btnSearch.click();
        log("Verify 1: Bookie field is colored by red border with message \"Please input required field(s)\"");
        Assert.assertEquals(accountPercentPage.appArlertControl.getWarningMessage(), AccountPercent.INPUT_REQUIRE_MES,"FAILED! Message displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "147")
    @Parameters({"bookieCode"})
    public void AccountPercent_147(String bookieCode) {
        log("@Title: Validate value of dropdown list Bookie and Type");
        log("@Pre-conditions: User has permission to access Account Percent page");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Click on Bookie dropdown list and observe value");
        log("Verify 1: Bookie: list out bookie name available");
        Assert.assertTrue(accountPercentPage.ddpBookie.getOptions().contains(bookieCode),"FAILED! Bookie dropdown displays incorrect");
        log("@Step 4: Click on Type dropdown list and observe value");
        log("Verify 2: Type: displays 'All', 'With Percent', 'Without Percent'");
        Assert.assertEquals(accountPercentPage.ddpType.getOptions(),AccountPercent.TYPE_LIST,"FAILED! Bookie dropdown displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "148")
    @Parameters({"bookieCode","accountCode"})
    public void AccountPercent_148(String bookieCode, String accountCode) {
        log("@Title: Validate able to search successfully");
        log("@Pre-conditions 1: User has permission to access Account Percent page");
        log("@Pre-conditions 2: Already has Bookie with Account created");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Click on Bookie dropdown list and select Bookie name in pre-condition 2");
        log("@Step 4: Input value into Account Code in pre-condition 2");
        log("@Step 5: Click Search icon");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        log("Verify 1: Result is displayed correctly");
        Assert.assertTrue(accountPercentPage.tbAccPercent.getControlOfCell(1,accountPercentPage.tbAccPercent.getColumnIndexByName("Account Code"),1,"span").getText().equals(accountCode),
                "FAILED! Data table displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "150")
    @Parameters({"bookieCode","accountCode","username"})
    public void AccountPercent_150(String bookieCode, String accountCode, String username) {
        log("@Title: Validate details of information column");
        log("@Pre-conditions 1: User has permission to access Account Percent page");
        log("@Pre-conditions 2: Already has Bookie with Account created");
        String accountIDEx = AccountSearchUtils.getAccountId(accountCode);
        String createdByEx = "qa";
        String createdDateEx = "18/01/2023 12:08";
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Click on Bookie dropdown list and select");
        log("@Step 4: Input Account Code into Account Code textbox");
        log("@Step 5: Click Search icon");
        log("@Step 6: Under information column, hover mouse over i icon of record and observe details");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        log("Verify 1: Details popup displays with AccountID, CreatedBy, CreatedDate, ModifiedBy, ModifiedDate and data show on each field is correct");
        accountPercentPage.verifyTooltipDisplay(accountCode,accountIDEx,createdByEx,createdDateEx,username);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "151")
    @Parameters({"bookieCode","accountCode","clientCode"})
    public void AccountPercent_151(String bookieCode, String accountCode, String clientCode) throws IOException {
        log("@Title: Validate able to save winloss % value");
        log("@Pre-conditions 1: User has permission to access Account Percent page");
        log("@Pre-conditions 2: Already has Bookie with Account created");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Click on Bookie dropdown list and select");
        log("@Step 4: Input Account Code into Account Code textbox");
        log("@Step 5: Click Search icon");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        try {
            log("@Step 6: Under Actual WinLoss% column, click on Edit icon");
            log("@Step 7: Input WinLoss value into field and click Save icon > Yes");
            accountPercentPage.editPercent(accountCode,"1.5",true);
            log("Verify 1: The successful message \"Account percent setting updated successfully\" displays and the inputted value is saved correctly.");
            Assert.assertEquals(accountPercentPage.appArlertControl.getSuscessMessage(),AccountPercent.EDIT_SUCCESS_MES,"FAILED! Edit success message displays incorrect");
        } finally {
            log("@Post-step: set old pt value");
            String accountId = AccountSearchUtils.getAccountId(accountCode);
            String clientId = ClientSystemUtils.getClientId(clientCode);
            Double pt = 1.00000;
            AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode, pt);
        }

        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "152")
    @Parameters({"bookieCode","accountCode"})
    public void AccountPercent_152(String bookieCode, String accountCode) {
        log("@Title: Validate able to cancel saving winloss % value");
        log("@Pre-conditions 1: User has permission to access Account Percent page");
        log("@Pre-conditions 2: Already has Bookie with Account created");
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Click on Bookie dropdown list and select");
        log("@Step 4: Input Account Code into Account Code textbox");
        log("@Step 5: Click Search icon");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        String ptDefault = accountPercentPage.getAccPT(accountCode);
        log("@Step 6: Under Actual WinLoss% column, click on Edit icon");
        log("@Step 7: Input WinLoss value into field and click Cancel icon");
        accountPercentPage.editPercent(accountCode,"1.5",false);
        log("Verify 1: Inputted value does not save");
        Assert.assertTrue(ptDefault.equals(accountPercentPage.getAccPT(accountCode)));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "155")
    @Parameters({"masterCode","agentCode","smartGroup","accountCode","clientCode"})
    public void AccountPercent_155(String masterCode, String agentCode,String smartGroup, String accountCode, String clientCode) throws IOException {
        log("@Title: Validate account percent is applied on SPP correctly");
        log("@Pre-conditions 1: User has permission to access SPP page");
        log("@Pre-conditions 2: User is assigned to Smart Group (the smart group assigned to Smart Master + Smart Agent) already");
        log("@Pre-conditions 3: User has been set Account Percent value");
        String accountId = AccountSearchUtils.getAccountId(accountCode);
        String clientId = ClientSystemUtils.getClientId(clientCode);
        Double pt = 1.00000;
        AccountPercentUtils.setAccountPercentAPI(accountId,accountCode,clientId,clientCode, pt);
        log("@Pre-conditions 4: User already have settled bet for Soccer");
        String sport="Soccer";
        String date = String.format(DateUtils.getDate(-1,"dd/MM/yyyy",GMT_7));
        String dateAPI = String.format(DateUtils.getDate(-1,"yyyy-MM-dd",GMT_7));
        Event event = GetSoccerEventUtils.getFirstEvent(dateAPI,dateAPI,sport,"");
        event.setEventDate(dateAPI);
        Order order = new Order.Builder()
                .event(event)
                .accountCode(accountCode)
                .marketName("Goals")
                .marketType("HDP")
                .selection(event.getHome())
                .stage("FullTime")
                .odds(1)
                .handicap(-0.25)
                .oddType("HK")
                .requireStake(1.00)
                .betType("BACK")
                .build();
        BetEntrytUtils.placeBetAPI(order);
        List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(order);
        lstOrder = BetEntrytUtils.setOrderIdBasedBetrefIDForListOrder(lstOrder);
        ConfirmBetsUtils.confirmBetAPI(lstOrder.get(0));
        BetSettlementUtils.waitForBetIsUpdate(10);
        BetSettlementPage betSettlementPage = welcomePage.navigatePage(TRADING,BET_SETTLEMENT,BetSettlementPage.class);
        betSettlementPage.filter("Confirmed",date,date,"",accountCode);
        BetSettlementUtils.waitForBetIsUpdate(20);
        String winLossEx = betSettlementPage.getWinlossSettledofOrder(order);
        betSettlementPage.settleAndSendSettlementEmail(order);
        log("@Step 1: Login to site");
        log("@Step 2: Navigate to Soccer > SPP");
        SPPPage sppPage = betSettlementPage.navigatePage(SOCCER,SPP,SPPPage.class);
        log("@Step 3: Search with Smart Group and Smart Master + Smart Agent at precondition");
        sppPage.filter("Soccer", "Group","Smart Group",masterCode,agentCode,date,"");
        log("@Step 4: Observe Win/Lose of the bet");
        String winLossAc = sppPage.getRowDataOfGroup(smartGroup).get(sppPage.tblSPP.getColumnIndexByName("W/L"));
        log("Verify 1: Win/Lose of the bet is shown with value applied the Account Percent setting");
        Assert.assertEquals(winLossAc,winLossEx,"FAILED! Win lose displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2199")
    public void AccountPercent_2199(){
        log("Validate AccountPercent page is displayed when navigate");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("Validate AccountPercent page is displayed when navigate");
        Assert.assertTrue(accountPercentPage.getTitlePage().contains(ACCOUNT_PERCENT), "Failed! Account Percent page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2200")
    @Parameters({"bookieCode"})
    public void AccountPercent_2200(String bookieCode){
        log("Validate UI on Account Percent is correctly displayed");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("Validate UI on Account Percent is correctly displayed");
        log("Dropdown: Bookie, Type");
        Assert.assertTrue(accountPercentPage.ddpBookie.getOptions().contains(bookieCode),"Failed! Bookie dropdown is not displayed");
        Assert.assertEquals(accountPercentPage.ddpType.getOptions(),AccountPercent.TYPE_LIST,"Failed! Type dropdown is not displayed");
        log("Textbox: Account Code");
        Assert.assertEquals(accountPercentPage.lblAccountCode.getText(),"Account Code","Failed! Type dropdown is not displayed");
        log("Button: Search");
        Assert.assertTrue(accountPercentPage.btnSearch.isDisplayed(),"Failed! Search button is not displayed!");
        log("Validate Account Percent table is displayed with correct header");
        Assert.assertEquals(accountPercentPage.tbAccPercent.getHeaderNameOfRows(),AccountPercent.TABLE_HEADER,"Failed! Account Percent table is displayed incorrect header");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2201")
    @Parameters({"bookieCode","accountCode"})
    public void AccountPercent_2201(String bookieCode, String accountCode){
        String PT  = "1.00000";
        log("Validate can update account percent successfully");
        log("@Step 1: Login to SB11 site");
        log("@Step 2: Access Trading > Account Percent");
        AccountPercentPage accountPercentPage = welcomePage.navigatePage(TRADING,ACCOUNT_PERCENT,AccountPercentPage.class);
        log("@Step 3: Enter Account Code");
        log("@Step 4: Click Search");
        accountPercentPage.filterAccount(bookieCode,"",accountCode);
        log("@Step 5: Click Edit button");
        log("@Step 6: Enter account percent data > click Save");
        accountPercentPage.editPercent(accountCode,PT,true);
        log("Validate updated account percent is displayed correctly at Actual WinLoss % column");
        Assert.assertEquals(accountPercentPage.getAccPT(accountCode),PT,"Failed! Updated account percent is not matched!");
        log("INFO: Executed completely");
    }

}
