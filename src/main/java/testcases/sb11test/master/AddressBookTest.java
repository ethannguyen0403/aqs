package testcases.sb11test.master;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.sb11.master.AddressBookPage;
import pages.sb11.master.AutoCreatedAccountsPage;
import pages.sb11.master.popup.EmailSendingHistoryPopup;
import testcases.BaseCaseAQS;
import utils.sb11.CompanySetUpUtils;
import utils.testraildemo.TestRails;

import static common.SBPConstants.*;

public class AddressBookTest extends BaseCaseAQS {
    @Test(groups = {"regression"})
    @TestRails(id = "2234")
    public void Address_Book_TC_2234(){
        log("@title: Validate Address Book page is displayed when navigate");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Address Book");
        AddressBookPage addressBookPage = welcomePage.navigatePage(MASTER, ADDRESS_BOOK,AddressBookPage.class);
        log("Validate Address Book page is displayed with correctly title");
        Assert.assertTrue(addressBookPage.getTitlePage().contains(ADDRESS_BOOK),"FAILED! Page Title is incorrect display");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression","ethan2.0"})
    @TestRails(id = "2235")
    @Parameters({"clientCode"})
    public void Address_Book_TC_2235(String clientCode){
        log("@title: Validate UI on Address Book is correctly displayed");
        log("@Step 1: Login with valid account");
        log("Step 2: Click Master > Address Book");
        AddressBookPage addressBookPage = welcomePage.navigatePage(MASTER, ADDRESS_BOOK,AddressBookPage.class);
        log("Validate UI Info display correctly");
        addressBookPage.verifyUI(clientCode);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2236")
    @Parameters({"clientCode","accountCode"})
    public void Address_Book_TC_003(String clientCode, String accountCode){
        log("@title: VValidate can update email for an account successfully");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Address Book");
        AddressBookPage addressBookPage = welcomePage.navigatePage(MASTER, ADDRESS_BOOK,AddressBookPage.class);
        log("@Step 3: Enter account at pre-condition on Account Code");
        log("@Step 4: Click Search");
        addressBookPage.filterAddress(KASTRAKI_LIMITED,clientCode,accountCode);
        log("@Step 5: Click Edit");
        log("@Step 6: Enter name and email at To column");
        log("@Step 7: Click Save");
        addressBookPage.inputInfo("","","auto,autoacc001stg@yopmail.com");
        log("Validate successful message is displayed correctly");
        addressBookPage.verifyInfo("","","","","auto,autoacc001stg@yopmail.com");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression"})
    @TestRails(id = "2237")
    public void Address_Book_TC_004(){
        log("@title: Validate Email Sending History popup is displayed correctly when clicking History");
        log("@Step 1: Login with valid account");
        log("@Step 2: Click Master > Address Book");
        AddressBookPage addressBookPage = welcomePage.navigatePage(MASTER, ADDRESS_BOOK,AddressBookPage.class);
        log("@Step 3: Click History");
        EmailSendingHistoryPopup emailSendingHistoryPopup = addressBookPage.openEmailSendingHistoryPopup();
        log("Validate Address Book page is displayed with correctly title");
        Assert.assertTrue(emailSendingHistoryPopup.getTitlePage().contains("Email Sending History"),"Failed! Email Sending History popup is not displayed!");
        log("INFO: Executed completely");
    }
}
