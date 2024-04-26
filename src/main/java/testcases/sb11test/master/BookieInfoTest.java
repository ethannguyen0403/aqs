package testcases.sb11test.master;

import com.paltech.driver.DriverManager;
import com.paltech.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import testcases.BaseCaseAQS;
import utils.sb11.CompanySetUpUtils;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class BookieInfoTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2202")
    public void Bookie_Info_2202(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("Verify that Page title is correctly display");
        Assert.assertEquals(bookieInfoPage.getTitlePage(),"Master Bookie Info","FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2203")
    public void Bookie_Info_2203(){
        log("@title: Validate UI on Bookie Info is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("Validate UI Info display correctly");
        bookieInfoPage.verifyUI();
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2204")
    @Parameters({"bookieCode"})
    public void Bookie_Info_2204(String bookieCode){
        log("@title: Validate can search Bookie successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("@Step 3: Enter an exist Bookie");
        log("@Step 4: Click Search");
        bookieInfoPage.filterBookie(KASTRAKI_LIMITED,bookieCode,"","","");
        log("Searched bookie should display correctly on Bookie List");
        Assert.assertTrue(bookieInfoPage.isBookieCodeExist(bookieCode),"Failed! Bookie Code " + bookieCode + " is not exist");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2205")
    public void Bookie_Info_2205(){
        String downloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "bookie-list.xlsx";
        log("@title: Validate can export Bookie List to Excel file successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("@Step 3: Click Export To Excel");
        bookieInfoPage.exportBookieList();
        log("Validate can export Bookie List to Excel file successfully with exported file name: bookie_list");
        Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath), "Failed to download Expected document");

        log("@Post-condition: delete download file");
        // try {
        //     FileUtils.removeFile(downloadPath);
        // } catch (IOException e) {
        //     log(e.getMessage());
        // }
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "16173")
    @Parameters({"bookieCode"})
    public void Bookie_Info_16173(String bookieCode){
        log("@title: Validate X button is disabled when having transaction on Bookie Info");
        log("@Precondition: Having Bookie account that already had transaction: " + bookieCode);
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("@Step 4: Filter with Bookie: " + bookieCode);
        bookieInfoPage.filterBookie(KASTRAKI_LIMITED, bookieCode, "", "", "");
        log("@Verify 1: Validate X button is disabled when having transaction on Bookie Info");
        Assert.assertTrue(bookieInfoPage.verifyElementIsDisabled(bookieInfoPage.getControlXButton(bookieCode), "class"),
                "Failed! Bookie Code " + bookieCode + " X button is enabled");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.12.29"})
    @TestRails(id = "16174")
    @Parameters({"bookieCode"})
    public void Bookie_Info_16174(String bookieCode){
        log("@title: Validate tooltip is displayed correctly when hovering on X button on Bookie Info");
        log("@Precondition: Having Bookie account that already had transaction: " + bookieCode);
        log("@Step 1: Login with valid account");
        log("@Step 2: Navigate Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("@Step 4: Filter with Bookie: " + bookieCode);
        bookieInfoPage.filterBookie(KASTRAKI_LIMITED, bookieCode, "", "", "");
        log("@Verify 1: Validate X button is disabled when having transaction on Bookie Info");
        Assert.assertEquals(bookieInfoPage.getTooltipText(bookieInfoPage.getControlXButton(bookieCode)),
                String.format(BookieSystem.TOOLTIP_MESSAGE, "Bookie"), "FAILED! Text on tool tip is not correct");
        log("INFO: Executed completely");
    }
}
