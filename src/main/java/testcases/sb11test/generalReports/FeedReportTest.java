package testcases.sb11test.generalReports;

import com.paltech.element.common.DropDownBox;
import com.paltech.utils.DateUtils;
import com.paltech.utils.StringUtils;
import common.SBPConstants;
import objects.Transaction;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.LoginPage;
import pages.sb11.accounting.JournalReportsPage;
import pages.sb11.generalReports.FeedReportPage;
import pages.sb11.generalReports.popup.feedreport.ClientPopup;
import pages.sb11.generalReports.popup.feedreport.ProviderPopup;
import pages.sb11.popup.ConfirmPopup;
import testcases.BaseCaseAQS;
import utils.sb11.FeedReportUtils;
import utils.sb11.feedreport.ClientPopupUtils;
import utils.sb11.feedreport.ProviderPopupUtils;
import utils.testraildemo.TestRails;

public class FeedReportTest extends BaseCaseAQS {
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4089")
    @Parameters({"password", "userNameOneRole"})
    public void Feed_Report_4089(String password, String userNameOneRole) throws Exception {
        log("@title: Validate could not access 'Closing Journal Entries' page if not activate System Monitoring permission");
        log("@Pre-condition: Feed Report permission is OFF for any account");
        log("@Step 1: Login by account at precondition");
        LoginPage loginPage = welcomePage.logout();
        loginPage.login(userNameOneRole, StringUtils.decrypt(password));
        log("Verify 1: 'Feed Report' menu is hidden");
        Assert.assertFalse(welcomePage.headerMenuControl.isSubmenuDisplay(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4090")
    public void Feed_Report_4090() {
        log("@title: Validate 'Feed Report' menu displays if active Feed Report permission");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Expand General Reports menu");
        log("Verify 1: 'Feed Report' menu is hidden");
        Assert.assertTrue(welcomePage.headerMenuControl.isSubmenuDisplay(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT));
        log("@Step 3: Select Feed Report item");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("Verify 2: Feed Report page displays properly");
        Assert.assertTrue(page.lblTitle.getText().contains("Feed Report"),"FAILED! Title page displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4091")
    public void Feed_Report_4091() {
        log("@title: Validate Company Unit just shows the only option 'Kastraki Limited'");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Expand Company Unit dropdown list");
        log("Verify 1: Only displays option 'Kastraki Limited'");
        Assert.assertTrue(page.ddCompanyUnit.getNumberOfItems() == 1,"FAILED! Company Unit dropdown display incorrect");
        Assert.assertTrue(page.ddCompanyUnit.getOptions().get(0).equals(SBPConstants.COMPANY_UNIT),"FAILED! Company Unit dropdown display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4092")
    public void Feed_Report_4092() {
        log("@title: Validate From/To Date displays properly according to selected Financial Year");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Select Financial Year (e.g. Year 2022-2023)");
        String fromDate = "01/07/2023";
        String toDate = "31/07/2023";
        page.filter(SBPConstants.COMPANY_UNIT,"Year 2022-2023",fromDate,toDate);
        log("@Step 4: Click From/To Date field");
        log("Verify 1: From/To Date field displays based on selected Financial Year at step #3\n" +
                "e.g. Year 2022-2023 -> can select date from 1/8/2022 to 31/7/2023");
        Assert.assertTrue(page.txtFromDate.getAttribute("value").equals(fromDate));
        Assert.assertTrue(page.txtToDate.getAttribute("value").equals(toDate));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4093")
    public void Feed_Report_4093() {
        log("@title: Validate can filter txn Date within 3 months");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Select Financial Year (e.g. Year 2022-2023)");
        String fromDate = "01/08/2022";
        String toDate = "31/07/2023";
        log("@Step 4: Select From Date (e.g. 1/8/2022)");
        log("@Step 5: Select To Date more than 3 months from date (e.g. 1/7/2023)");
        log("@Step 6: Click Show button");
        page.filter(SBPConstants.COMPANY_UNIT,"Year 2022-2023",fromDate,toDate);
        log("Verify 1: Error message 'Invalid time range. You can see data up to 3 months.' displays");
        page.btnShow.click();
        Assert.assertEquals(page.appArlertControl.getWarningMessage(),SBPConstants.FeedReport.ERROR_MES_INVALID_TIME_RANGE,"FAILED! Error message display incorrect");
        log("@Step 7: Select To Date within 3 months from date (e.g. 1/11/2022)");
        log("@Step 8: Click Show button");
        fromDate = "01/07/2023";
        page.filter(SBPConstants.COMPANY_UNIT,"Year 2022-2023",fromDate,toDate);
        log("Verify 2: Proper data displays");
        Assert.assertTrue(page.tblProviderFirst.getControlOfCell(1,1,1,null).getText().trim().contains(fromDate+" - "+toDate));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4094")
    public void Feed_Report_4094() {
        log("@title: Validate Submit Transaction button disabled if there is no inputted Amount");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Filter which has data and do not input amount in any Provider/Client row");
        page.filter("","","","");
        log("Verify 1: Submit Transaction button should disable");
        Assert.assertFalse(page.btnSubmitTrans.isEnabled(),"FAILED! Submit Transaction button display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4095")
    public void Feed_Report_4095() {
        log("@title: Validate Submit Transaction button is enable if there is inputted Amount");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        String providerName = "Auto Test";
        String valueAmount = "1.00";
        String date = DateUtils.getDate(0,"dd/MM/yyyy",SBPConstants.GMT_7);
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Filter which has data");
        page.filter(SBPConstants.COMPANY_UNIT,"",date,date);
        log("@Step 4: Input amount in any Provider/Client row");
        page.inputAmount(providerName,providerName,valueAmount,date,true,false);
        log("Verify 1: Submit Transaction button should be enable");
        Assert.assertTrue(page.btnSubmitTrans.isEnabled(),"FAILED! Submit Transaction button display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4096")
    public void Feed_Report_4096() {
        log("@title: Validate can perform auto transaction with correct amount after clicked Submit Transaction button");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        String providerName = "Auto Test";
        String valueAmount = "1.00";
        String date = DateUtils.getDate(-1,"dd/MM/yyyy",SBPConstants.GMT_7);
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Filter which has data");
        log("@Step 4: Select transaction date");
        page.filter(SBPConstants.COMPANY_UNIT,"",date,date);
        String previousProvider = page.getTotalProvider();
        log("@Step 5: Input amount (e.g. 1080) into any existing provider/client row");
        log("@Step 6: Click Submit Transaction button");
        log("@Step 7: Click Yes button in confirmation dialog");
        page.inputAmount(providerName,providerName,valueAmount,date,true,true);
        log("Verify 1: Payment = current Payment + inputted amount");
        Assert.assertTrue(page.getTotalProvider().equals(String.format("%.2f",Double.valueOf(previousProvider)+Double.valueOf(valueAmount))));
        log("@Step 8: Go to Accounting >> Journal Reports");
        JournalReportsPage journalReportsPage = page.navigatePage(SBPConstants.ACCOUNTING,SBPConstants.JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 9: Filter by transaction date at step #4:\n" +
                "Date Type = Transaction Date\n" +
                "From Date To Date = <transaction date at step #4>\n" +
                "Account Type = All");
        journalReportsPage.filterReports(SBPConstants.COMPANY_UNIT,"Transaction Date",date,date,"All","","Payment Feed","");
        log("Verify 2:  Feed Provider/Client at step #5 will have Sub-Account in Debit and Sub-Account in Credit, so:\n" +
                "The inputted amount at step #5 of Sub-account in Debit will show in Debit column");
        Transaction transaction = new Transaction.Builder()
                .ledgerDebit("AutoCapitalDebit")
                .ledgerCredit("AutoCapitalCredit")
                .ledgerDebitCur("HKD")
                .ledgerCreditCur("HKD")
                .amountDebit(Double.valueOf(valueAmount))
                .amountCredit(Double.valueOf(valueAmount))
                .remark("Feed Transaction of "+providerName)
                .transDate("")
                .transType("Payment Feed")
                .build();
        journalReportsPage.verifyTxn(transaction,true);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4097")
    public void Feed_Report_4097() {
        log("@title: Validate the dialog allows viewing/editing/creating providers opens when clicked Provider button");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Provider button");
        ProviderPopup providerPopup = page.openProviderPopup();
        log("Verify 1: Provider dialog should open properly");
        Assert.assertTrue(providerPopup.lblTitle.isDisplayed(),"FAILED! Provider Popup displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4098")
    public void Feed_Report_4098() throws InterruptedException {
        log("@title: Validate can create providers with proper information in Add Provider state");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Provider button");
        ProviderPopup providerPopup = page.openProviderPopup();
        log("@Step 4: Click Add Provider button");
        log("@Step 5: Input/select proper information");
        String providerName = "Automation testing";
        String subaccDebit = "AutoExpenditureDebit - 011.000.000.000";
        String subaccCrebit = "AutoExpenditureCredit - 010.000.000.000";
        String cur = "HKD";
        try {
            providerPopup.addProvider(providerName,providerName,cur,subaccDebit,subaccCrebit,false);
            log("@Step 6: Click Save button");
            ConfirmPopup confirmPopup = providerPopup.clickToSaveProvider(1);
            log("Verify 1: Confirmation message 'Are you sure to create <provider name>?' should display");
            Assert.assertEquals(confirmPopup.getContentMessage(),String.format(SBPConstants.FeedReport.CONFIRM_MES_ADDING_PROVIDER,providerName)
                    ,"FAILED! Confirmation message display incorrect");
            log("@Step 7: Click Yes button in confirmation message");
            confirmPopup.btnYes.click();
            log("Verify 2: New provider should create successfully without any error");
            Assert.assertTrue(providerPopup.isProviderDisplay(providerName,providerName,cur,subaccDebit,subaccCrebit));
        } finally {
            ProviderPopupUtils.deleteProvider(providerName);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4099")
    public void Feed_Report_4099() {
        log("@title: Validate Sub-account in Debit/Credit list down all accounts that have the same currency as the selected currency of Provider");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Provider button");
        ProviderPopup providerPopup = page.openProviderPopup();
        log("@Step 4: Click Add Provider button");
        log("@Step 5: Select any CUR");
        providerPopup.btnAddProvider.click();
        log("@Step 6: Expand Sub-account in Debit or Sub-account in Credit dropdown list");
        String subaccDebit = "AutoExpenditureDebit - 011.000.000.000";
        String cur = "HKD";
        providerPopup.addProvider("","",cur,"","",false);
        log("Verify 1: Validate Sub-account that have the same currency as the selected currency of Provider");
        Assert.assertTrue(DropDownBox.xpath(providerPopup.tblProvider.getxPathOfCell(1,providerPopup.tblProvider.getColumnIndexByName("Sub-account in Credit"),1,"select")).getOptions().contains(subaccDebit),"FAILED! Sub-account Dropdown displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4100")
    public void Feed_Report_4100() {
        log("@title: Validate click X icon will close/hide the row without any changes");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Provider button");
        ProviderPopup providerPopup = page.openProviderPopup();
        log("@Step 4: Click Edit link of an existing");
        String providerName = "Automation testing";
        String reProviderName = "Testing Automation";
        String subaccDebit = "AutoExpenditureDebit - 011.000.000.000";
        String subaccCrebit = "AutoExpenditureCredit - 010.000.000.000";
        String cur = "HKD";
        try {
            providerPopup.addProvider(providerName,providerName,cur,subaccDebit,subaccCrebit,true);
            int indexProvider = providerPopup.tblProvider.getRowIndexContainValue(providerName,providerPopup.tblProvider.getColumnIndexByName("Provider Name"),"span");
            providerPopup.editProvider(providerName,reProviderName,"","","","",false);
            log("@Step 5: Edit any field then click X icon");
            providerPopup.clickToXIcon(indexProvider);
            log("Verify 1: Edited row will close/hide without any changes");
            Assert.assertTrue(providerPopup.isProviderDisplay(providerName,providerName,cur,subaccDebit,subaccCrebit));
        } finally {
            ProviderPopupUtils.deleteProvider(providerName);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4101")
    public void Feed_Report_4101() {
        log("@title: Validate data table will display information of all created providers accordingly");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Provider button");
        ProviderPopup providerPopup = page.openProviderPopup();
        log("@Post-step 4: add a provider");
        String providerName = "Automation testing";
        String subaccDebit = "AutoExpenditureDebit - 011.000.000.000";
        String subaccCrebit = "AutoExpenditureCredit - 010.000.000.000";
        String cur = "HKD";
        try {
            providerPopup.addProvider(providerName,providerName,cur,subaccDebit,subaccCrebit,true);
            log("Verify 1: Data table will display information of created providers accordingly");
            Assert.assertTrue(providerPopup.isProviderDisplay(providerName,providerName,cur,subaccDebit,subaccCrebit));
        } finally {
            ProviderPopupUtils.deleteProvider(providerName);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4102")
    public void Feed_Report_4102() throws InterruptedException {
        log("@title: Validate data table will display information of all created providers accordingly");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Provider button");
        ProviderPopup providerPopup = page.openProviderPopup();
        log("@Post-step 4: add a provider");
        String providerName = "Automation testing";
        String subaccDebit = "AutoExpenditureDebit - 011.000.000.000";
        String subaccCrebit = "AutoExpenditureCredit - 010.000.000.000";
        String cur = "HKD";
        try {
            providerPopup.addProvider(providerName,providerName,cur,subaccDebit,subaccCrebit,true);
            log("@Step 5: Click X icon of an existing provider at any row");
            log("@Step 6: Click Yes button");
            providerPopup.deleteProvider(providerName,true);
            log("Verify 1: Confirmation dialog 'Are you sure to delete <provider name>?' will display");
            log("Verify 1: Data table will display information of created providers accordingly");
            Assert.assertFalse(providerPopup.isProviderDisplay(providerName,providerName,cur,subaccDebit,subaccCrebit));
        } finally {
            ProviderPopupUtils.deleteProvider(providerName);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4103")
    public void Feed_Report_4103() {
        log("@title: Validate the dialog allows viewing/editing/creating clients opens when clicked Client button");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Client button");
        ClientPopup clientPopup = page.openClientPopup();
        log("Verify 1: The Client dialog should display for viewing/editing/creating clients");
        Assert.assertTrue(clientPopup.lblTitle.isDisplayed(),"FAILED! Client Popup displays incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4104")
    public void Feed_Report_4104() throws InterruptedException {
        log("@title: Validate can create client with proper information in Add Client state");
        log("@Pre-condition: Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Client button");
        ClientPopup clientPopup = page.openClientPopup();
        log("@Step 4: Click Add Client button");
        log("@Step 5: Input/select proper information");
        String providerName = "Auto Test";
        String clientName = "Auto Client testing";
        String subaccDebit = "AutoExpenditureDebit - 011.000.000.000";
        String subaccCrebit = "AutoExpenditureCredit - 010.000.000.000";
        String cur = "HKD";
        try {
            clientPopup.addClient(providerName,clientName,clientName,cur,subaccDebit,subaccCrebit,false);
            log("@Step 6: Click Save button");
            ConfirmPopup confirmPopup = clientPopup.clickToSaveProvider(1);
            log("Verify 1: Confirmation message 'Are you sure to create <provider name>?' should display");
            Assert.assertEquals(confirmPopup.getContentMessage(),String.format(SBPConstants.FeedReport.CONFIRM_MES_ADDING_PROVIDER,clientName)
                    ,"FAILED! Confirmation message display incorrect");
            log("@Step 7: Click Yes button in confirmation message");
            confirmPopup.btnYes.click();
            log("Verify 2: New provider should create successfully without any error");
            Assert.assertTrue(clientPopup.isClientDisplay(providerName,clientName,clientName,cur,subaccDebit,subaccCrebit));
        } finally {
            ClientPopupUtils.deleteClient(clientName);
        }
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4105")
    public void Feed_Report_4105() {
        log("@title: Validate data table of Provider will sort by Provider Name alphabetically ascending");
        log("@Pre-condition 1: Feed Report permission is ON for any account");
        log("@Pre-condition 2: Having some existing Providers");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Client button");
        ClientPopup clientPopup = page.openClientPopup();
        log("Verify 1: Data table of Provider will sort by Provider Name alphabetically ascending");
        Assert.assertTrue(clientPopup.isColumnSorted("Provider"));
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4106")
    public void Feed_Report_4106() {
        log("@title: Validate data table of Client will sort by Provider Name alphabetically ascending and by Client Name if having the same provider");
        log("@Pre-condition 1: Feed Report permission is ON for any account");
        log("@Pre-condition 2: Having some existing Providers");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Client button");
        ClientPopup clientPopup = page.openClientPopup();
        log("Verify 1: Data table of Client will sort by Provider Name alphabetically ascending and by Client Name if having the same provider");
        clientPopup.isDataTableSorted();
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression","2024.V.1.0"})
    @TestRails(id = "4107")
    public void Feed_Report_4107() {
        log("@title: Validate Summary table displays correct data");
        log("@Pre-condition : Feed Report permission is ON for any account");
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Filter which has data");
        String fromDate = DateUtils.getDate(-10,"dd/MM/yyyy",SBPConstants.GMT_7);
        String toDate = DateUtils.getDate(0,"dd/MM/yyyy",SBPConstants.GMT_7);
        page.filter(SBPConstants.COMPANY_UNIT,"",fromDate,toDate);
        log("Verify 1: Summary table displays correct data\n" +
                "CUR: HKD (as all the amount in this summary table will be in HKD)\n" +
                "Provider = sum up 'Total in HKD' amounts of all providers (1)\n" +
                "Client = sum up 'Total in HKD' amounts of all clients (2)\n" +
                "Commission = 'Client' - 'Provider' = (2) - (1)");
        page.verifySumTableValue(fromDate,toDate);
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4108")
    public void Feed_Report_4108() {
        log("@title: Validate Provider table displays correct data in Feed report");
        log("@Pre-condition 1: Feed Report permission is ON for any account");
        log("@Pre-condition 2: Get Sub-account of an existing provider");
        String providerName = "Auto Test";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Filter which has data");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",SBPConstants.GMT_7);
        page.filter(SBPConstants.COMPANY_UNIT,"",fromDate,fromDate);
        log("@Step 4: Get Payment (1)\n" +
                "Get Payment[HKD] (2) of Provider at Precondition");
        page.inputAmount(providerName,providerName,"1.00",fromDate,true,true);
        String payment = FeedReportUtils.getPaymentValue(providerName,"","Payment",fromDate,fromDate);
        String paymentHKD = FeedReportUtils.getPaymentValue(providerName,"","Payment[HKD]",fromDate,fromDate);
        log("@Step 5: Go to Accounting >> Journal Reports");
        JournalReportsPage journalReportsPage = page.navigatePage(SBPConstants.ACCOUNTING,SBPConstants.JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 6: Filter by data of provider at step #3");
        journalReportsPage.filterReports(SBPConstants.COMPANY_UNIT,"Transaction Date",fromDate,fromDate,"All","","Payment Feed","");
        log("@Step 7: Get Total Foreign Debit/Total Foreign Credit of Sub-account at precondition (3)\n" +
                "Get Total Foreign Debit in HKD/Total Foreign Credit in HKD of Sub-account at precondition (4)");
        String foreignDebit = journalReportsPage.getTotalByColumn(providerName,"Foreign Debit");
        String foreignCredit = journalReportsPage.getTotalByColumn(providerName,"Foreign Credit");
        String totalDebitHKD = journalReportsPage.getTotalByColumn(providerName,"Debit in HKD");
        String totalCreditHKD = journalReportsPage.getTotalByColumn(providerName,"Credit in HKD");
        log("Verify 1: Payment (1) = Total Foreign Debit/Total Foreign Credit of Sub-account at precondition (2)\n" +
                "Payment[HKD] (2) = Total Foreign Debit in HKD/Total Foreign Credit in HKD of Sub-account at precondition (4)");
        Assert.assertTrue(payment.equals(foreignDebit),"FAILED! Foreign Debit is incorrect");
        Assert.assertTrue(payment.equals(foreignCredit),"FAILED! Foreign Credit is incorrect");
        Assert.assertTrue(paymentHKD.equals(totalDebitHKD),"FAILED! Debit in HKD is incorrect");
        Assert.assertTrue(paymentHKD.equals(totalCreditHKD),"FAILED! Credit in HKD is incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4109")
    public void Feed_Report_4109() {
        log("@title: Validate Client table displays correct data in Feed report ");
        log("@Pre-condition 1: Feed Report permission is ON for any account");
        log("@Pre-condition 2: Get Sub-account of an existing provider");
        String providerName = "Auto Test";
        String clientName = "Auto Client";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Filter which has data");
        String fromDate = DateUtils.getDate(-3,"dd/MM/yyyy",SBPConstants.GMT_7);
        page.filter(SBPConstants.COMPANY_UNIT,"",fromDate,fromDate);
        log("@Step 4: Get Payment (1)\n" +
                "Get Payment[HKD] (2) of Provider at Precondition");
        page.inputAmount(providerName + " CLIENT",clientName,"1.00",fromDate,false,true);
        String payment = FeedReportUtils.getPaymentValue(providerName,clientName,"Payment",fromDate,fromDate);
        String paymentHKD = FeedReportUtils.getPaymentValue(providerName,clientName,"Payment[HKD]",fromDate,fromDate);
        log("@Step 5: Go to Accounting >> Journal Reports");
        JournalReportsPage journalReportsPage = page.navigatePage(SBPConstants.ACCOUNTING,SBPConstants.JOURNAL_REPORTS,JournalReportsPage.class);
        log("@Step 6: Filter by data of provider at step #3");
        journalReportsPage.filterReports(SBPConstants.COMPANY_UNIT,"Transaction Date",fromDate,fromDate,"All","","Received Feed","");
        log("@Step 7: Get Total Foreign Debit/Total Foreign Credit of Sub-account at precondition (3)\n" +
                "Get Total Foreign Debit in HKD/Total Foreign Credit in HKD of Sub-account at precondition (4)");
        String foreignDebit = journalReportsPage.getTotalByColumn(providerName + " Client","Foreign Debit");
        String foreignCredit = journalReportsPage.getTotalByColumn(providerName + " Client","Foreign Credit");
        String totalDebitHKD = journalReportsPage.getTotalByColumn(providerName + " Client","Debit in HKD");
        String totalCreditHKD = journalReportsPage.getTotalByColumn(providerName + " Client","Credit in HKD");
        log("Verify 1: Payment (1) = Total Foreign Debit/Total Foreign Credit of Sub-account at precondition (2)\n" +
                "Payment[HKD] (2) = Total Foreign Debit in HKD/Total Foreign Credit in HKD of Sub-account at precondition (4)");
        Assert.assertTrue(payment.equals(foreignDebit),"FAILED! Foreign Debit is incorrect");
        Assert.assertTrue(payment.equals(foreignCredit),"FAILED! Foreign Credit is incorrect");
        Assert.assertTrue(paymentHKD.equals(totalDebitHKD),"FAILED! Debit in HKD is incorrect");
        Assert.assertTrue(paymentHKD.equals(totalCreditHKD),"FAILED! Credit in HKD is incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "4135")
    public void Feed_Report_4135() {
        log("@title: Validate unable to edit any info of Providers/Clients that had feed transactions");
        log("@Pre-condition 1: Feed Report permission is ON for any account");
        log("@Pre-condition 2: There are some Providers/Clients that had Feed transactions");
        String providerName = "Auto Test";
        String clientName = "Auto Client";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Provider button");
        ProviderPopup providerPopup = page.openProviderPopup();
        log("Verify 1: There is a note 'Can not edit Provider/Client that had feed transactions'");
        Assert.assertEquals(providerPopup.lblNote.getText(),SBPConstants.FeedReport.MES_NOTE_PROVIDER_CLIENT,"FAILED! Note displays incorrect");
        log("@Step 4: Click Edit link of provider that had transaction at precondition");
        log("@Step 5: Try to edit info");
        log("Verify 2: Could not edit info");
        Assert.assertFalse(providerPopup.isBtnEditCanClick(providerName),"FAILED! Edit button display incorrect");
        log("@Step 6: Close Provider dialog, click Client button");
        providerPopup.closeToPopup();
        ClientPopup clientPopup = page.openClientPopup();
        log("Verify 3: There is a note 'Can not edit Provider/Client that had feed transactions'");
        Assert.assertEquals(clientPopup.lblNote.getText(),SBPConstants.FeedReport.MES_NOTE_PROVIDER_CLIENT,"FAILED! Note displays incorrect");
        log("@Step 7: Click Edit link of client that had transaction at precondition");
        log("@Step 8: Try to edit info");
        log("Verify 4: Could not edit info");
        Assert.assertFalse(clientPopup.isBtnEditCanClick(clientName),"FAILED! Edit button display incorrect");
        log("INFO: Executed completely");
    }
    @Test(groups = {"regression_stg","2024.V.1.0"})
    @TestRails(id = "17953")
    public void Feed_Report_17953() throws InterruptedException {
        log("@title: Validate can delete client successfully");
        log("@Pre-condition: Feed Report permission is ON for any account");
        String providerName = "Auto Test";
        String clientName = "Client Automation Testing";
        log("@Step 1: Login by account at precondition");
        log("@Step 2: Go to 'General Reports' >> 'Feed Report' page");
        FeedReportPage page = welcomePage.navigatePage(SBPConstants.GENERAL_REPORTS,SBPConstants.FEED_REPORT, FeedReportPage.class);
        log("@Step 3: Click Client button");
        ClientPopup clientPopup = page.openClientPopup();
        String subaccDebit = "AutoExpenditureDebit - 011.000.000.000";
        String subaccCrebit = "AutoExpenditureCredit - 010.000.000.000";
        String cur = "HKD";
        try {
            log("@@Post-step 4: add a Client");
            clientPopup.addClient(providerName,clientName,clientName,cur,subaccDebit,subaccCrebit,true);
            log("@Step 5: Click X icon of an existing client at any row");
            log("@Step 6: Click Yes button");
            clientPopup.deleteClient(clientName,true);
            log("Verify 1: Client will delete successfully");
            Assert.assertFalse(clientPopup.isClientDisplay(providerName,clientName,clientName,cur,subaccDebit,subaccCrebit));
        } finally {
            ClientPopupUtils.deleteClient(clientName);
        }
        log("INFO: Executed completely");
    }
}
