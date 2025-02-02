package testcases.sb11test.accounting;

import com.paltech.driver.DriverManager;
import com.paltech.utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.accounting.CurrencyRatesPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import java.util.List;

import static common.SBPConstants.*;

public class CurrencyRatesTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2151")
    public void Currency_Rates_TC_2151(){
        log("@title: Validate Currency Rates page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Currency Rate");
        CurrencyRatesPage currencyRatesPage = welcomePage.navigatePage(ACCOUNTING,CURRENCY_RATES,CurrencyRatesPage.class);
        log("Validate Currency Rates page is displayed with correctly title");
        Assert.assertTrue(currencyRatesPage.getTitlePage().contains(CURRENCY_RATES), "Failed! Currency Rates page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @Parameters({"companyCurrency","companyName"})
    @TestRails(id = "2152")
    public void Currency_Rates_TC_2152(String companyCurrency, String companyName){
        log("@title: Validate Currency Rate page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Currency Rate");
        CurrencyRatesPage currencyRatesPage = welcomePage.navigatePage(ACCOUNTING,CURRENCY_RATES,CurrencyRatesPage.class);
        log("@Step 3: Filter with valid data");
        currencyRatesPage.filterRate(companyName,"");
        log(" Validate UI Info display correctly");
        log("Currency table header columns is correctly display");
        Assert.assertEquals(currencyRatesPage.lblDate.getText(),"Date","Failed! Date datetime picker is not displayed!");
        List<String> lstTblHeader = CurrencyRates.TABLE_HEADER;
        lstTblHeader.set(4,String.format(CurrencyRates.TABLE_HEADER.get(4), companyCurrency));
        Assert.assertEquals(currencyRatesPage.tblCurRate.getHeaderNameOfRows(), lstTblHeader,"FAILED! Currency Rate table header is incorrect display");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression"})
    @TestRails(id = "2153")
    public void Currency_Rates_TC_2153(){
        //TODO: implement this case
        log("@title: Validate can update New CL Rate successfully");
        Assert.assertTrue(false, "Need to implement this case");
        log("INFO: Executed Completely!");
    }

    @Test(groups = {"regression","ethan4.0"})
    @TestRails(id = "2154")
    public void Currency_Rates_TC_2154(){
        log("@title: Validate can export Currency list to Excel file successfully");
        String dowloadPath = DriverManager.getDriver().getDriverSetting().getDownloadPath() + "CurrencyRate.xlsx";
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Currency Rate");
        CurrencyRatesPage currencyRatesPage = welcomePage.navigatePage(ACCOUNTING,CURRENCY_RATES,CurrencyRatesPage.class);
        log("@Step 3: Filter with valid data");
        currencyRatesPage.filterRate("");
        log("@Step 4:  Click Export To Excel");
        currencyRatesPage.exportToExcel();
        log("Validate can export Currency list to Excel file successfully");
        Assert.assertTrue(FileUtils.doesFileNameExist(dowloadPath), "Failed to download Expected document");
        log("@Post-condition: delete download file");
//        try {
//            FileUtils.removeFile(dowloadPath);
//        } catch (IOException e) {
//            log(e.getMessage());
//        }


        log("INFO: Executed completely");
    }
}
