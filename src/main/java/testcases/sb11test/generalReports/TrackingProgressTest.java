package testcases.sb11test.generalReports;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.generalReports.TrackingProgressPage;
import testcases.BaseCaseAQS;
import utils.sb11.RoleManagementUtils;
import utils.sb11.TrackingProgressUtils;
import utils.testraildemo.TestRails;

import java.util.Arrays;
import java.util.List;

import static common.SBPConstants.*;

public class TrackingProgressTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg", "2024.V.5.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "23976")
    public void Tracking_Progress_23976(String password, String userNameOneRole) throws Exception {
        log("@title: Validate Tracking Progress menu is hidden if not active Tracking Progress permission");
        log("Precondition: 'Tracking Progress' permission is OFF for any account");
        RoleManagementUtils.updateRolePermission("one role", "Tracking Progress", "INACTIVE");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 1: Expand 'General Reports' menu");
        log("@Verify 1: 'Tracking Progress' menu is hidden");
        Assert.assertTrue(!welcomePage.headerMenuControl.isSubmenuDisplay(GENERAL_REPORTS, TRACKING_PROGRESS), "FAILED! Tracking Progress menu is displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "23977")
    public void Tracking_Progress_23977() {
        log("@title: Validate Tracking Progress menu displays if active Tracking Progress permission");
        log("Precondition: 'Tracking Progress' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand 'General Reports' menu");
        log("@Step 2: Click 'Tracking Progress'");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Verify 1: 'Tracking Progress' menu is hidden");
        Assert.assertEquals(page.lblTitle.getText(), "General Reports Tracking Progress", "FAILED! Stake Size Group page display incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "23978")
    public void Tracking_Progress_23978() {
        log("@title: Validate there is a Progress Status Ball displayed in header menu if having Tracking Progress permission");
        log("Precondition: 'Tracking Progress' permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Verify 1: There will be a Progress Status Ball in header menu, in front of the notification icon (bell icon)");
        Assert.assertTrue(welcomePage.icTrackingProgress.isDisplayed(), "FAILED! Tracking Progress icon display incorrect.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @Parameters({"password", "userNameOneRole"})
    @TestRails(id = "23979")
    public void Tracking_Progress_23979(String password, String userNameOneRole) throws Exception {
        log("@title: Validate there is no a Progress Status Ball in header menu if having no Tracking Progress permission");
        log("Precondition: 'Tracking Progress' permission is OFF for any account");
        RoleManagementUtils.updateRolePermission("one role", "Tracking Progress", "INACTIVE");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Verify 1: Progress Status Ball will not display in header menu");
        Assert.assertFalse(welcomePage.icTrackingProgress.isDisplayed(), "FAILED! Tracking Progress icon display incorrect.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24006")
    public void Tracking_Progress_24006() {
        log("@title: Validate the ball is green when all the crawling/generating processes in the system are finished, and all data is ready in relative reports");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: All the crawling/generating processes in the system are finished, and all data is ready in relative reports (if all data from the released date of the Tracking Progress page (5 Nov 2023) till yesterday are available (all steps are green)).");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Observe the ball in header menu");
        log("@Verify 1: The ball is green");
        welcomePage.verifyIconTrackingProgressDisplay("FINISHED");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24007")
    public void Tracking_Progress_24007() {
        log("@title: Validate 'Data is readily available and can be found in all General reports.' displays if hovering over the green ball");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: All the crawling/generating processes in the system are finished, and all data is ready in relative reports");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Hover over the green ball icon in header menu");
        log("@Verify 1: The tooltip 'Data is readily available and can be found in all General reports.' displays");
        welcomePage.verifyToolTipOfTrackingBall("FINISHED", "Data is readily available and can be found in all General reports.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24008")
    public void Tracking_Progress_24008() {
        log("@title: Validate navigate to Tracking Progress page when click on the green ball");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: All the crawling/generating processes in the system are finished, and all data is ready in relative reports");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click the green ball icon in header menu");
        TrackingProgressPage page = welcomePage.clickToTrackingBall();
        log("@Verify 1: Will navigate to Tracking Progress page");
        Assert.assertEquals(page.lblTitle.getText(), "General Reports Tracking Progress", "FAILED! Stake Size Group page display incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24009")
    public void Tracking_Progress_24009() {
        log("@title: Validate today being filtered if clicking on the green ball");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: All the crawling/generating processes in the system are finished, and all data is ready in relative reports");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click the green ball icon in header menu");
        TrackingProgressPage page = welcomePage.clickToTrackingBall();
        log("@Step 3: Observe the filtering");
        log("@Verify 1: Today being filtered");
        page.verifyDateAfterClickingTrackingBall("FINISHED");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24010")
    public void Tracking_Progress_24010() {
        log("@title: Validate the ball is yellow when there is at least 1 day that has unready data");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: There is at least 1 day that has unready data");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Observe the ball in header menu");
        log("@Verify 1: The ball is yellow");
        welcomePage.verifyIconTrackingProgressDisplay("IN PROGRESS");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24011")
    public void Tracking_Progress_24011() {
        log("@title: Validate the proper tooltip displays if hovering over the yellow ball");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: There is at least 1 day that has unready data");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Hover over the yellow ball in header menu");
        log("@Verify 1: The ball is yellow");
        welcomePage.verifyToolTipOfTrackingBall("IN PROGRESS", "Data is not readily available.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24012")
    public void Tracking_Progress_24012() {
        log("@title: Validate navigate to Tracking Progress page when click on the yellow ball");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: There is at least 1 day that has unready data");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click the yellow ball in header menu");
        TrackingProgressPage page = welcomePage.clickToTrackingBall();
        log("@Verify 1: Will navigate to Tracking Progress page");
        Assert.assertEquals(page.lblTitle.getText(), "General Reports Tracking Progress", "FAILED! Stake Size Group page display incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24013")
    public void Tracking_Progress_24013() {
        log("@title: Validate error/in-progress date being filtered if clicking on the yellow ball");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: There is at least 1 day that has unready data");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Click the yellow ball in header menu");
        TrackingProgressPage page = welcomePage.clickToTrackingBall();
        log("@Step 2: Observe the filtering");
        log("@Verify 1: Error/in-progress date being filtered");
        page.verifyDateAfterClickingTrackingBall("IN PROGRESS");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24014")
    public void Tracking_Progress_24014() {
        log("@title: Validate there are data from 3 providers BETISN, Pinnacle, and Fair999 in Tracking Progress page");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Observe the data in page at precondition");
        log("@Verify 1: This page will show data from 3 providers BETISN, Pinnacle, and Fair999.");
        page.verifyLineCodeDisplayByProvider("BetISN");
        page.verifyLineCodeDisplayByProvider("Pinnacle");
        page.verifyLineCodeDisplayByProvider("Fair999");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24015")
    public void Tracking_Progress_24015() {
        log("@title: Validate Provider contains options as 'All' (default), BetISN, Pinnacle, Fair999");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Expand Provider dropdown list, observe");
        log("@Verify 1: Provider contains options as 'All' (default), BetISN, Pinnacle, Fair999");
        Assert.assertEquals(page.ddProvider.getOptions(), TrackingProgress.PROVIDER_LIST, "FAILED! Provider list display incorrect.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24016")
    public void Tracking_Progress_24016() {
        log("@title: Validate proper list line codes displays");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        List<String> lstEx = Arrays.asList("A601", "N611", "PS40", "PW90", "AN", "Y2");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Expand Line Code dropdown list, observe");
        page.ddLineCode.click();
        log("@Verify 1: List line codes should display");
        page.verifyLineCodeDisplaysInDropdown(lstEx);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24017")
    public void Tracking_Progress_24017() {
        log("@title: Validate is able to select multiple lines from the selected Provider(s)");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        String lineCode1 = TrackingProgressUtils.getLineCodeByProvider(TrackingProgress.PROVIDER_LIST.get(1)).get(0);
        String lineCode2 = TrackingProgressUtils.getLineCodeByProvider(TrackingProgress.PROVIDER_LIST.get(1)).get(1);
        log("@Step 1: Select a provider at Provider dropdown list");
        log("@Step 2: Expand Line Code then try to select multiple lines, observe");
        page.selectLineCode(lineCode1, lineCode2);
        log("@Verify 1: User is able to select multiple lines");
        page.verifyLineCodeWasSelected(lineCode1, lineCode2);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24018")
    public void Tracking_Progress_24018() {
        log("@title: Validate Line Code filter option will be sorted by providers and then by alphabet if filtering all providers");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Select 'All' option at Provider dropdown list");
        page.ddProvider.selectByVisibleText("All");
        log("@Step 2: Expand Line Code then observe the sorting");
        page.ddLineCode.click();
        log("@Verify 1: Line Code will be sorted by providers and then by alphabet");
        page.verifyLineCodeDropDownSorted();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24019")
    public void Tracking_Progress_24019() {
        log("@title: Validate is able to filter report date in SB11 by selecting Specific Date");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        String date = DateUtils.getDate(-1, "dd/MM/yyyy", GMT_7);
        String provider = TrackingProgress.PROVIDER_LIST.get(1);
        String lineCode = TrackingProgressUtils.getLineCodeByProvider(provider).get(3);
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Select a provider at Provider dropdown list");
        log("@Step 2: Select a Line Code");
        log("@Step 3: Select a Specific Date then click Show");
        page.filter(provider, lineCode, date);
        log("@Verify 1: Can view data table properly");
        Assert.assertTrue(page.isDataLineCodeDisplay(provider, lineCode), "FAILED! " + lineCode + " does not have enough data");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24020")
    public void Tracking_Progress_24020() {
        log("@title: Validate Job Process Reminders note displays correctly");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Observe Job Process Reminders note");
        log("@Verify 1: 'Job Process Reminders' contains 3 notes as below:\n" +
                "\n" +
                "When all of the status symbols are GREEN, it signifies that the data is readily available and can be found in all General reports.\n" +
                "You can restart all processes corresponding to the line code if either errors occur (the status symbol turns RED) or when the status is finished.\n" +
                "After pressing the restart button, please wait for 1 hour because the system requires time to schedule and proceed with the steps step-by-step.");
        Assert.assertEquals(page.lblReminderMessage.getText(), SBPConstants.TrackingProgress.JOB_PROCESS_REMINDER_MES, "FAILED! Job Process Reminder message displays incorrect.");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24021")
    public void Tracking_Progress_24021() {
        log("@title: Validate Job Status Symbol displays correctly");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Observe Job Status Symbol");
        log("@Verify 1: Job Status Symbol displays as below:\n" +
                "Not Started: white circle\n" +
                "In Progress: yellow circle\n" +
                "Finished: green circle\n" +
                "Error: red circle");
        page.verifyJobStatusSymbolDisplay("NOT STARTED");
        page.verifyJobStatusSymbolDisplay("IN PROGRESS");
        page.verifyJobStatusSymbolDisplay("FINISHED");
        page.verifyJobStatusSymbolDisplay("ERROR");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24022")
    public void Tracking_Progress_24022() {
        log("@title: Validate data of each provider will be displayed in one section");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has data");
        page.filter("All", "", "");
        log("@Step 2: Observe the data of each provider at data table");
        log("@Verify 1: Data of each provider will be displayed in one section");
        page.verifyProviderDisplay(TrackingProgress.PROVIDER_LIST.get(1));
        page.verifyProviderDisplay(TrackingProgress.PROVIDER_LIST.get(2));
        page.verifyProviderDisplay(TrackingProgress.PROVIDER_LIST.get(3));
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24023")
    public void Tracking_Progress_24023() {
        log("@title: Validate the line code display the existing Lines of each provider accordingly");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has data");
        page.filter("All", "", "");
        log("@Step 2: Observe the Line Code at data table");
        log("@Verify 1: Display the existing Lines of each provider accordingly:");
        page.verifyExistingLinesOfProviderDisplay(TrackingProgress.PROVIDER_LIST.get(1));
        page.verifyExistingLinesOfProviderDisplay(TrackingProgress.PROVIDER_LIST.get(2));
        page.verifyExistingLinesOfProviderDisplay(TrackingProgress.PROVIDER_LIST.get(3));
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24024")
    public void Tracking_Progress_24024() {
        log("@title: Validate Lines are sorted by code alphabetically");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has data");
        page.filter("All", "", "");
        log("@Step 2: Observe the sorting of Line Code at data table");
        log("@Verify 1: Observe the sorting of Line Code at data table");
        page.verifyLineCodeOfProviderSorted(TrackingProgress.PROVIDER_LIST.get(1));
        page.verifyLineCodeOfProviderSorted(TrackingProgress.PROVIDER_LIST.get(2));
        page.verifyLineCodeOfProviderSorted(TrackingProgress.PROVIDER_LIST.get(3));
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24025")
    public void Tracking_Progress_24025() {
        log("@title: Validate Data Crawling Stage contains Step 1 to Step 3 for BetISN");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has BetISN data");
        page.filter("BetISN", "", "");
        log("@Step 2: Observe the Data Crawling Stage");
        log("@Verify 1: Data Crawling Stage contains Step 1 to Step 3");
        page.verifyStepNumberOfStageDisplay("BetISN", "Data Crawling Stage", 3);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24026")
    public void Tracking_Progress_24026() {
        log("@title: Validate Data Crawling Stage contains Step 1 to Step 3 for Pinnacle");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has Pinnacle data");
        page.filter("Pinnacle", "", "");
        page.waitSpinnerDisappeared();
        log("@Step 2: Observe the Data Crawling Stage");
        log("@Verify 1: Data Crawling Stage contains Step 1 to Step 3");
        page.verifyStepNumberOfStageDisplay("Pinnacle", "Data Crawling Stage", 3);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24027")
    public void Tracking_Progress_24027() {
        log("@title: Validate Data Crawling Stage contains Step 1 for Fair999");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has Fair999 data");
        page.filter("Fair999", "", "");
        log("@Step 2: Observe the Data Crawling Stage");
        log("@Verify 1: Data Crawling Stage contains Step 1 only");
        page.verifyStepNumberOfStageDisplay("Fair999", "Data Crawling Stage", 1);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24028")
    public void Tracking_Progress_24028() {
        log("@title: Validate the proper tooltip displays when hovering the (i) icon at Data Crawling Stage");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has data");
        page.filter("Fair999", "", "");
        log("@Step 2: Hover over (i) icon at Data Crawling Stage");
        log("@Verify 1: The proper tooltip 'The initial phase involves automated collection of data from diverse sources ensuring its quality and completeness. This stage is pivotal as it directly impacts the subsequent Report Generating Stage where this data is transformed into actionable insights, influencing decisions and strategies' displays");
        Assert.assertEquals(page.getToolTipMesOfSection("Data Crawling Stage"), TrackingProgress.DATA_CRAWLING_STAGE_TOOLTIP_MES, "FAILED! Message displays incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24029")
    public void Tracking_Progress_24029() {
        log("@title: Validate Report Generating Stage contains Step 4 to Step 8 for BetISN");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has BetISN data");
        page.filter("BetISN", "", "");
        log("@Step 2: Observe the Report Generating Stage");
        log("@Verify 1: Report Generating Stage contains Step 4 to Step 8");
        page.verifyStepNumberOfStageDisplay("BetISN", "Report Generating Stage", 5);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24030")
    public void Tracking_Progress_24030() {
        log("@title: Validate Report Generating Stage contains Step 4 to Step 8 for Pinnacle");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has Pinnacle data");
        page.filter("Pinnacle", "", "");
        log("@Step 2: Observe the Report Generating Stage");
        log("@Verify 1: Report Generating Stage contains Step 4 to Step 8");
        page.verifyStepNumberOfStageDisplay("Pinnacle", "Report Generating Stage", 5);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24031")
    public void Tracking_Progress_24031() {
        log("@title: Validate Report Generating Stage contains Step 2 to Step 6 for Fair999");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has Fair999 data");
        page.filter("Fair999", "", "");
        log("@Step 2: Observe the Report Generating Stage");
        log("@Verify 1: Report Generating Stage contains Step 2 to Step 6");
        page.verifyStepNumberOfStageDisplay("Fair999", "Report Generating Stage", 5);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2024.V.5.0"})
    @TestRails(id = "24032")
    public void Tracking_Progress_24032() {
        log("@title: Validate the proper tooltip displays when hovering the (i) icon at Report Generating Stage");
        log("Precondition 1: 'Tracking Progress' permission is ON for any account");
        log("Precondition 2: User is in Tracking Progress page");
        TrackingProgressPage page = welcomePage.navigatePage(GENERAL_REPORTS, TRACKING_PROGRESS, TrackingProgressPage.class);
        log("@Step 1: Filter which has data");
        page.filter("Fair999", "", "");
        log("@Step 2: Hover over (i) icon at Report Generating Stage");
        log("@Verify 1: The proper tooltip 'This critical phase transforms data into insightful reports, influencing strategic decisions. The reports generated here serve as valuable tools for data-driven decision-making and are typically utilized for General reports.' displays");
        Assert.assertEquals(page.getToolTipMesOfSection("Report Generating Stage"), TrackingProgress.REPORT_GENERATING_STAGE_TOOLTIP_MES, "FAILED! Message displays incorrect");
        log("INFO: Executed completely");
    }

}
