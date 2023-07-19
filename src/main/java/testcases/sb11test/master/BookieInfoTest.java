package testcases.sb11test.master;

import com.paltech.driver.DriverManager;
import com.paltech.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.BookieInfoPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.io.IOException;

import static common.SBPConstants.*;

public class BookieInfoTest extends BaseCaseAQS {
    String companyUnit = "Kastraki Limited";

    @Test(groups = {"regression"})
    @TestRails(id = "2202")
    public void Bookie_Info_TC_001(){
        log("@title: Verify that can login successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("Verify that Page title is correctly display");
        Assert.assertEquals(bookieInfoPage.getTitlePage(),"Master Bookie Info","FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2203")
    public void Bookie_Info_TC_002(){
        log("@title: Validate UI on Bookie Info is correctly displayed");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("Validate UI Info display correctly");
        log("Dropdown: Company Unit, Support By, Currency, Status");
        Assert.assertEquals(bookieInfoPage.ddpCompanyUnit.getOptions(),COMPANY_UNIT_LIST,"Failed! Company Unit dropdown is not displayed!");
        Assert.assertTrue(bookieInfoPage.ddpSupportBy.getOptions().contains("qa"),"Failed! Support By dropdown is not displayed!");
        Assert.assertEquals(bookieInfoPage.ddpCurrency.getOptions(),BookieInfo.CURRENCY_LIST,"Failed! Currency dropdown is not displayed!");
        Assert.assertEquals(bookieInfoPage.ddpStatus.getOptions(),BookieInfo.STATUS_LIST,"Failed! Status dropdown is not displayed!");
        log("Textbox: Bookie");
        Assert.assertEquals(bookieInfoPage.lblBookie.getText(),"Bookie","Failed! Bookie textbox is not displayed!");
        log("Button: Search, Show, Add Bookie, Export To Excel");
        Assert.assertEquals(bookieInfoPage.btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(bookieInfoPage.btnShow.getText(),"Show","Failed! Show button is not displayed!");
        Assert.assertEquals(bookieInfoPage.btnAddBookie.getText(),"Add Bookie","Failed! Add Bookie button is not displayed!");
        Assert.assertEquals(bookieInfoPage.btnExportToExcel.getText(),"Export To Excel","Failed! Export To Excel button is not displayed!");
        log("Validate Bookie Info table is displayed with correct header");
        Assert.assertEquals(bookieInfoPage.tbBookie.getHeaderNameOfRows(),BookieInfo.TABLE_HEADER,"Failed! Table header is displayed incorrectly~");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2204")
    @Parameters({"bookieCode"})
    public void Bookie_Info_TC_003(String bookieCode){
        log("@title: Validate can search Bookie successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Bookie Info");
        BookieInfoPage bookieInfoPage = welcomePage.navigatePage(MASTER, BOOKIE_INFO,BookieInfoPage.class);
        log("@Step 3: Enter an exist Bookie");
        log("@Step 4: Click Search");
        bookieInfoPage.filterBookie(companyUnit,bookieCode,"","","");
        log("Searched bookie should display correctly on Bookie List");
        Assert.assertTrue(bookieInfoPage.isBookieCodeExist(bookieCode),"Failed! Bookie Code " + bookieCode + " is not exist");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2205")
    public void Bookie_Info_TC_004(){
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
        try {
            FileUtils.removeFile(downloadPath);
        } catch (IOException e) {
            log(e.getMessage());
        }
        log("INFO: Executed completely");
    }

}
