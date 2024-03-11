package testcases.sb11test.trading;

import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import org.bouncycastle.jcajce.provider.symmetric.RC6;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.trading.AnalysePage;
import pages.sb11.trading.analyse.CreateNewLinePopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;
import common.SBPConstants;

public class AnalyseTest extends BaseCaseAQS {
    @TestRails(id = "5278")
    @Parameters({"password", "userNameOneRole"})
    @Test(groups = {"regression_stg","2024.V.2.0"})
    public void Analyse_TC_5278(String password, String userNameOneRole) throws Exception{
        log("@title: Validate 'Analyse' menu is hidden if not active Analyse permission");
        log("@pre-condition: Analyse permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("@Step 2: Expand Trading menu");
        log("@Verify 1: 'Analyse' menu is hidden");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(SBPConstants.TRADING,SBPConstants.ANALYSE));
        log("INFO: Executed completely");
    }
    @TestRails(id = "5279")
    @Test(groups = {"regression","2024.V.2.0"})
    public void Analyse_TC_5279(){
        log("@title: Validate 'Analyse' menu displays if active Analyse permission");
        log("@pre-condition: Analyse permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand Trading menu");
        log("@Verify 1: Analyse' menu displays");
        Assert.assertTrue(welcomePage.headerMenuControl.isSubmenuDisplay(SBPConstants.TRADING,SBPConstants.ANALYSE));
        log("@Step 3: Select Analyse item");
        AnalysePage page = welcomePage.navigatePage(SBPConstants.TRADING,SBPConstants.ANALYSE, AnalysePage.class);
        log("@Verify 2: Analyse report page displays properly");
        Assert.assertTrue(page.lblTitle.getText().contains("Analyse"),"FAILED! Analyse report page displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id = "5292")
    @Test(groups = {"regression","2024.V.2.0"})
    public void Analyse_TC_5292(){
        log("@title: Validate Analyse modal displays when click Create/Manage Lines button");
        log("@pre-condition: Analyse permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading menu >> Analyse item");
        AnalysePage page = welcomePage.navigatePage(SBPConstants.TRADING,SBPConstants.ANALYSE, AnalysePage.class);
        log("@Step 3: Click Create/Manage Lines button");
        CreateNewLinePopup createNewLinePopup = page.openCreateNewLinePopup();
        log("@Verify 1: Analyse modal displays for user create or manage existing line");
        //wait for popup
        page.waitPageLoad();
        Assert.assertTrue(createNewLinePopup.lblTitle.getText().contains("Create New Line"),"FAILED! Analyse modal displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id = "5293")
    @Test(groups = {"regression","2024.V.2.0"})
    public void Analyse_TC_5293(){
        log("@title: Validate is able to create new line without any error");
        log("@pre-condition: Analyse permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading menu >> Analyse item");
        String lineName = "AutoQC";
        String masterAgent1 = "CN7TT";
        String masterAgent2 = "03XE2";
        AnalysePage page = welcomePage.navigatePage(SBPConstants.TRADING,SBPConstants.ANALYSE, AnalysePage.class);
        log("@Step 3: Click Create/Manage Lines button");
        CreateNewLinePopup createNewLinePopup = page.openCreateNewLinePopup();
        log("@Step 4: Select Level (e.g. MA)");
        log("@Step 5: Select >=1 scrapped [Level] from Pinnacle dropdown list (e.g. CN7TT,03XE2)");
        log("@Step 6: Input Line Name then click Create button");
        createNewLinePopup.createNewLine("MA",lineName,masterAgent1,masterAgent2);
        try {
            log("@Verify 1: New Line is created successfully in data table. IF there are >=2 mapped username, it will separate by comma (e.g. CN7TT,03XE2)");
            Assert.assertEquals(createNewLinePopup.getMappedUserOfLine(lineName),masterAgent1+","+masterAgent2,"FAILED! New Line displays mapped user incorrect");
        } finally {
            log("@Post-condition: Delete Line");
            createNewLinePopup.deleteLine(lineName);
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "5294")
    @Test(groups = {"regression","2024.V.2.0"})
    public void Analyse_TC_5294(){
        log("@title: Validate is able to search/update and delete line without any error");
        log("@pre-condition: Analyse permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading menu >> Analyse item");
        String lineName = "AutoQC";
        String masterAgent1 = "CN7TT";
        String masterAgent2 = "03XE2";
        AnalysePage page = welcomePage.navigatePage(SBPConstants.TRADING,SBPConstants.ANALYSE, AnalysePage.class);
        log("@Step 3: Click Create/Manage Lines button");
        CreateNewLinePopup createNewLinePopup = page.openCreateNewLinePopup();
        log("@Step 4: Create a new line");
        createNewLinePopup.createNewLine("MA",lineName,masterAgent1);
        log("@Step 5: Select Level and Input Line name");
        log("@Step 6: Click Edit button line at step 4");
        log("@Step 7: Update Line name/unmapped/mapped");
        log("@Step 8: Click Save button");
        createNewLinePopup.editLine(lineName,"",true,masterAgent2);
        try {
            log("@Verify 1: Line is updated correctly");
            Assert.assertEquals(createNewLinePopup.getMappedUserOfLine(lineName),masterAgent2,"FAILED! Line updated mapped user incorrect");
        } finally {
            log("@Step 9: Click X button of any line at Action column");
            log("@Step 10: Click Yes/No button in confirm dialog");
            createNewLinePopup.deleteLine(lineName);
            log("INFO: Executed completely");
        }
    }
    @TestRails(id = "5295")
    @Test(groups = {"regression","2024.V.2.0"})
    public void Analyse_TC_5295(){
        log("@title: Validate is able to back to Analyse main page by clicked Back button");
        log("@pre-condition: Analyse permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading menu >> Analyse item");
        AnalysePage page = welcomePage.navigatePage(SBPConstants.TRADING,SBPConstants.ANALYSE, AnalysePage.class);
        log("@Step 3: Click Create/Manage Lines button");
        CreateNewLinePopup createNewLinePopup = page.openCreateNewLinePopup();
        log("@Step 4: Click Back button");
        createNewLinePopup.btnBack.click();
        page.waitSpinnerDisappeared();
        log("@Verify 1: It will come back to Analyse main page properly");
        Assert.assertTrue(page.lblTitle.getText().contains("Analyse"),"FAILED! Analyse report page displays incorrect");
        log("INFO: Executed completely");
    }
    @TestRails(id = "5296")
    @Test(groups = {"regression","2024.V.2.0"})
    public void Analyse_TC_5296(){
        log("@title: Validate error message displays if tried to filter > 1 month");
        log("@pre-condition: Analyse permission is ON for any account");
        String fromDate = DateUtils.getDate(-35,"dd/MM/yyyy", SBPConstants.GMT_7);
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to Trading menu >> Analyse item");
        AnalysePage page = welcomePage.navigatePage(SBPConstants.TRADING,SBPConstants.ANALYSE, AnalysePage.class);
        log("@Step 3: Flter From Date To Date > 1 month");
        page.filter("","","",fromDate,"");
        log("@Step 4: Click Show button");
        page.btnShow.click();
        log("@Verify 1: Error message 'Date range should not be more than 1 month' displays");
        Assert.assertEquals(page.appArlertControl.getWarningMessage(),SBPConstants.Analyse.ERROR_MES_MORE_1_MONTH,"FAILED! Analyse report page displays incorrect");
        log("INFO: Executed completely");
    }
}
