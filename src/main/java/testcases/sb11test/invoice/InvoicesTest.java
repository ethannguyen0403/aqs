package testcases.sb11test.invoice;

import com.paltech.utils.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.invoice.InvoicesPage;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;

import static common.SBPConstants.INVOICES;

public class InvoicesTest extends BaseCaseAQS {

    @Test(groups = {"regression"})
    @TestRails(id = "2239")
    public void Invoices_TC_2239(){
        log("@title: Validate payment date is consistent in summary and details");
        log("@Step 1: Navigate to SB11 > Invoice > Invoices");
        InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICES,INVOICES,InvoicesPage.class);

        log("@Step 2: Get the Date value of any invoices e.g 30/04/2023  ");
        invoicesPage.filter("","","","","","","","");
        String date = invoicesPage.getFirstInvoce().get(invoicesPage.colDate-1);
        DateUtils.formatDate(date,"DD/MM/YYY","DD MMMM YYYY");

        log("@Step 3: Click on View of according to invoice and get payment date: 30 April 2023");
        String paymentDate = invoicesPage.viewInvoice(1).getPaymentDateOfInvoice();

        log("@Verify 1: Verify the date is matched with payment date");
        Assert.assertEquals(date, paymentDate, "Failed! Payment date is summary mismatched with  details");
        log("INFO: Executed completely");
    }


}
