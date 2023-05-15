package testcases.sb11test.accounting;

import com.paltech.utils.DateUtils;
import common.SBPConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.accounting.CurrencyRatesPage;
import pages.sb11.soccer.BBGPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class CurrencyRatesTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2151")
    public void Currency_Rates_TC_001(){
        log("@title: Validate Currency Rates page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Currency Rate");
        CurrencyRatesPage currencyRatesPage = welcomePage.navigatePage(ACCOUNTING,CURRENCY_RATES,CurrencyRatesPage.class);
        log("Validate Currency Rates page is displayed with correctly title");
        Assert.assertTrue(currencyRatesPage.getTitlePage().contains(CURRENCY_RATES), "Failed! Currency Rates page is not displayed");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2152")
    public void Currency_Rates_TC_002(){
        log("@title: Validate Currency Rate page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Currency Rate");
        CurrencyRatesPage currencyRatesPage = welcomePage.navigatePage(ACCOUNTING,CURRENCY_RATES,CurrencyRatesPage.class);
        log("@Step 3: Filter with valid data");
        currencyRatesPage.filterRate("");
        log(" Validate UI Info display correctly");
        log("Currency table header columns is correctly display");
        Assert.assertEquals(currencyRatesPage.lblDate.getText(),"Date","Failed! Date datetime picker is not displayed!");
        Assert.assertEquals(currencyRatesPage.tblCurRate.getHeaderNameOfRows(), CurrencyRates.TABLE_HEADER,"FAILED! Currency Rate table header is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2152")
    public void Currency_Rates_TC_003(){
        log("@title: Validate can export Currency list to Excel file successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Access Accounting > Currency Rate");
        CurrencyRatesPage currencyRatesPage = welcomePage.navigatePage(ACCOUNTING,CURRENCY_RATES,CurrencyRatesPage.class);
        log("@Step 3: Filter with valid data");
        currencyRatesPage.filterRate("");
        log("@Step 4:  Click Export To Excel");

        log("Validate can export Currency list to Excel file successfully");


        log("INFO: Executed completely");
    }
}
