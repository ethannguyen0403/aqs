package testcases.sb11test.trading;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.soccer.MonitorBetsPage;
import pages.sb11.trading.StakeSizeGroupPage;
import pages.sb11.trading.popup.StakeSizeGroupPopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class StakeSizeGroupTest extends BaseCaseAQS {
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "29566")
    public void Stake_Size_Group_29566(String password, String userNameOneRole) throws Exception {
        log("@title: Validate the account without permission cannot see the menu");
        log("Precondition: Account is inactivated permission 'Stake Size Group'");
        log("@Step 1: Navigate to the site");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Check menu item 'Stake Size Group' under menu 'Trading'");
        log("@Verify 1: Menu 'Stake Size Group' is not shown");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(TRADING, STAKE_SIZE_GROUP), "FAILED! Stake Size Group menu is displayed");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29567")
    public void Stake_Size_Group_29567() {
        log("@title: Validate the account with permission can access page");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("@Step 1: Navigate to the site");
        log("@Step 2: Expand menu 'Trading' and access 'Stake Size Group' page");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Verify 1: Menu 'Stake Size Group' is not shown");
        Assert.assertTrue(page.lblTitle.getText().contains(STAKE_SIZE_GROUP),"FAILED! Stake Size Group page display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29568")
    public void Stake_Size_Group_29568() {
        log("@title: Validate Company Unit dropdown list");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Expand Company Unit dropdown");
        log("@Verify 1: Show all companies as in Accounting >> Company Set-up");
        Assert.assertEquals(page.ddCompanyUnit.getOptions(), COMPANY_UNIT_LIST_ALL,"FAILED! Stake Size Group page display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29569")
    public void Stake_Size_Group_29569() {
        log("@title: Validate Client dropdown list when selecting a company unit");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Select a company unit (e.g. Fair)");
        page.filter(KASTRAKI_LIMITED,"");
        log("@Step 3: Expand Client dropdown");
        log("@Verify 1: Show option 'All' and all clients according to selected company unit");
        page.verifyLstClient(KASTRAKI_LIMITED);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.4.0"})
    @Parameters({"clientCode","accountCurrency","username"})
    @TestRails(id = "29570")
    public void Stake_Size_Group_29570(String clientCode, String accountCurrency, String username) {
        log("@title: Validate the group is created successfully with valid values");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        String groupName = "Automation testing "+ DateUtils.getMilliSeconds();
        String dateUpdate = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Post-condition: Get stake range of client");
        double stakeRange = page.getLowestStakeRange(KASTRAKI_LIMITED,clientCode);
        log("@Step 2: Click on 'Add Group' button");
        log("@Step 3: Fill in all mandatory fields with all valid values");
        log("@Step 4: Click on 'Submit' button");
        page.addNewGroup(KASTRAKI_LIMITED,clientCode,groupName,stakeRange,stakeRange,true);
        log("@Verify 1: The group is created successfully and shown in data table");
        page.verifyGroupDisplay(clientCode,groupName,accountCurrency,String.format("From %s to %s",stakeRange,stakeRange).replace(".0",""),dateUpdate, username);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29571")
    public void Stake_Size_Group_29571() {
        log("@title: Validate the error message shows when leaving empty Client");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        String groupName = "Automation testing "+ DateUtils.getMilliSeconds();
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Click on 'Add Group' button");
        log("@Step 3: Fill in all mandatory fields with leaving empty Client");
        log("@Step 4: Click on 'Submit' button");
        page.addNewGroup(KASTRAKI_LIMITED,"",groupName,1.0 ,1.0,false);
        StakeSizeGroupPopup stakeSizeGroupPopup = new StakeSizeGroupPopup();
        stakeSizeGroupPopup.btnSubmit.click();
        String alertMes = stakeSizeGroupPopup.appArlertControl.getWarningMessage();
        log("@Verify 1: Show the error message: 'Please select Client!'");
        Assert.assertEquals(alertMes, SBPConstants.StakeSizeGroupNewPopup.ERROR_MES_CLIENT_EMPTY,"FAILED! Error message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "29572")
    public void Stake_Size_Group_29572(String clientCode) {
        log("@title: Validate the error message shows when leaving empty group name");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Click on 'Add Group' button");
        log("@Step 3: Fill in all mandatory fields with leaving empty Group Name");
        log("@Step 4: Click on 'Submit' button");
        page.addNewGroup(KASTRAKI_LIMITED,clientCode,"",1.0 ,1.0,false);
        StakeSizeGroupPopup stakeSizeGroupPopup = new StakeSizeGroupPopup();
        stakeSizeGroupPopup.btnSubmit.click();
        String alertMes = stakeSizeGroupPopup.appArlertControl.getWarningMessage();
        log("@Verify 1: Show the error message: 'Group name cannot be empty!'");
        Assert.assertEquals(alertMes, SBPConstants.StakeSizeGroupNewPopup.ERROR_MES_GROUP_NAME_EMPTY,"FAILED! Error message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "29573")
    public void Stake_Size_Group_29573(String clientCode) {
        log("@title: Validate the entered values are cleared when clicking on Clear");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        String groupName = "Automation testing "+ DateUtils.getMilliSeconds();
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Click on 'Add Group' button");
        log("@Step 3: Fill in all mandatory fields");
        log("@Step 4: Click on 'Clear'");
        page.addNewGroup(KASTRAKI_LIMITED,clientCode,groupName,1.0 ,1.0,false);
        StakeSizeGroupPopup stakeSizeGroupPopup = new StakeSizeGroupPopup();
        stakeSizeGroupPopup.btnClear.click();
        log("@Verify 1: The entered values are cleared");
        stakeSizeGroupPopup.verifyUIDefault();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode","accountCurrency","username"})
    @TestRails(id = "29574")
    public void Stake_Size_Group_29574(String clientCode, String accountCurrency, String username) {
        log("@title: Validate the group is updated successfully with valid values");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("Precondition: Create a group");
        String groupName = "Automation testing "+ DateUtils.getMilliSeconds();
        String dateUpdate = DateUtils.getDate(0,"dd/MM/yyyy",GMT_7);
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Post-condition: Get stake range of client");
        double stakeRange = page.getLowestStakeRange(KASTRAKI_LIMITED,clientCode);
        log("@Step 2: Click on 'Edit'");
        log("@Step 3: Fill in all mandatory fields");
        log("@Step 4: Click on 'Submit' button");
        page.editFirstGroupNameOfClient(clientCode,groupName,stakeRange,stakeRange,true);
        log("@Verify 1: The group is updated successfully with edited values");
        page.verifyGroupDisplay(clientCode,groupName,accountCurrency,String.format("From %s to %s",stakeRange,stakeRange).replace(".0",""),dateUpdate, username);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode","accountCurrency","username"})
    @TestRails(id = "29575")
    public void Stake_Size_Group_29575(String clientCode, String accountCurrency, String username) {
        log("@title: Validate the group is updated successfully with valid values");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("Precondition: The account 'admin' created a group");
        String groupName = "QA Client - Stake Group";
        String dateUpdate = "13/05/2024";
        double stakeRange = 100.0;
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Observe group info");
        page.filter(KASTRAKI_LIMITED,clientCode);
        log("@Verify 1: The group info is correct as at precondition");
        page.verifyGroupDisplay(clientCode,groupName,accountCurrency,String.format("From %s to %s",stakeRange,stakeRange).replace(".0",""),dateUpdate, username);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "29576")
    public void Stake_Size_Group_29576(String clientCode) {
        log("@title: Validate the group is updated successfully with valid values");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("Precondition: Created the group name");
        String groupName = "QA Client - Stake Group";
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Click on 'Add Group' button");
        log("@Step 3: Fill in all mandatory fields with group name 'QA Test Group'");
        log("@Step 4: Click on 'Submit' button");
        page.addNewGroup(KASTRAKI_LIMITED,clientCode,groupName,1.0,1.0,false);
        StakeSizeGroupPopup stakeSizeGroupPopup = new StakeSizeGroupPopup();
        stakeSizeGroupPopup.btnSubmit.click();
        String alertMes = stakeSizeGroupPopup.appArlertControl.getWarningMessage();
        log("@Verify 1: Show msg: 'Group name 'QA Test Group' is duplicated.'");
        Assert.assertEquals(alertMes,String.format(StakeSizeGroupNewPopup.ERROR_MES_GROUP_NAME_DUPLICATE,groupName),"FAILED! Error message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @Parameters({"clientCode"})
    @TestRails(id = "29577")
    public void Stake_Size_Group_29577(String clientCode) {
        log("@title: Validate the error message show when stake range is duplicated");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("Precondition: Created a group with stake range");
        String groupName = "QA Client - Stake Group";
        double stakeRange = 100.00;
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Click on 'Add Group' button");
        log("@Step 3: Fill in all mandatory fields with stake range");
        log("@Step 4: Click on 'Submit' button");
        page.addNewGroup(KASTRAKI_LIMITED,clientCode,"group-name",stakeRange,stakeRange,false);
        StakeSizeGroupPopup stakeSizeGroupPopup = new StakeSizeGroupPopup();
        stakeSizeGroupPopup.btnSubmit.click();
        String alertMes = stakeSizeGroupPopup.appArlertControl.getWarningMessage();
        log("@Verify 1: Show msg: 'Stake range 10 - 100 already set in the group QA Test Group, please double check!'");
        Assert.assertEquals(alertMes,String.format(StakeSizeGroupNewPopup.ERROR_MES_GROUP_STAKE_RANGE_DUPLICATE,stakeRange,stakeRange,groupName).replace(".0",""),"FAILED! Error message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29579")
    public void Stake_Size_Group_29579() {
        log("@title: Validate the data table is sorted by Updated Date in DESC");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Click on 'Show' button");
        page.btnShow.click();
        page.waitSpinnerDisappeared();
        log("@Verify 1: The data table is sorted by Updated Date in DESC");
        page.verifyDataTableIsSorted();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29582")
    public void Stake_Size_Group_29582() {
        log("@title: Validate the error message shows when not selecting Company Unit");
        log("Precondition: Account is activated permission 'Stake Size Group'");
        log("@Step 1: Go to Trading >> Stake Size Group");
        StakeSizeGroupPage page = welcomePage.navigatePage(TRADING,STAKE_SIZE_GROUP, StakeSizeGroupPage.class);
        log("@Step 2: Click on 'Add Group' button");
        log("@Step 3: Fill in all mandatory fields without selecting Company Unit");
        log("@Step 4: Click on 'Submit' button");
        StakeSizeGroupPopup stakeSizeGroupPopup = page.openStakeSizeGroupNewPopup();
        stakeSizeGroupPopup.btnSubmit.click();
        String alertMes = stakeSizeGroupPopup.appArlertControl.getWarningMessage();
        log("@Verify 1: Show the error message: 'Please select Company Unit!'");
        Assert.assertEquals(alertMes,StakeSizeGroupNewPopup.ERROR_MES_COMPANY_EMPTY,"FAILED! Error message display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29616")
    public void Stake_Size_Group_29616() {
        log("@title: Monitor Bets - Validate 'Stake Size Group' is added in 'Group Type'");
        log("@Step 1: Go to Soccer >> Monitor Bets");
        MonitorBetsPage page = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Expand 'Group Type' dropdown");
        log("@Verify 1: 'Group Type' dropdown contains 'Smart Group' (default value) and 'Stake Size Group'.");
        Assert.assertEquals(page.ddGroupType.getOptions(),SBPConstants.MonitorBets.GROUP_TYPE,"FAILED! Group Type display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.4.0"})
    @TestRails(id = "29617")
    public void Stake_Size_Group_29617() {
        log("@title: Monitor Bets - Validate the user can filter by Stake Size Group");
        log("@Step 1: Go to Soccer >> Monitor Bets");
        MonitorBetsPage page = welcomePage.navigatePage(SOCCER,MONITOR_BETS, MonitorBetsPage.class);
        log("@Step 2: Select Group Type = 'Stake Size Group'");
        page.goToGroupType("Stake Size Group");
        log("@Step 3: Select filters that have data with any option in 'Stake Size Group' dropdown");
        log("@Step 4: Click on 'Show' button");
        page.filterResult("All","All","All Hours","Last 10 Bets","ALL",true);
        log("@Verify 1: Show correct bets by selected option in 'Stake Size Group' dropdown");
        page.verifyBetsShowInStakeSizeGroup();
        log("INFO: Executed completely");
    }
}
