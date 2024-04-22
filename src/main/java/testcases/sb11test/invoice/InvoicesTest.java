package testcases.sb11test.invoice;

import com.paltech.driver.DriverManager;
import com.paltech.utils.DateUtils;
import com.paltech.utils.FileUtils;
import com.paltech.utils.StringUtils;
import objects.Invoice;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sb11.invoice.InvoicesPage;
import pages.sb11.invoice.WorkflowSettingsPage;
import pages.sb11.invoice.popup.invoices.ActionInvoicePopup;
import pages.sb11.invoice.popup.invoices.LogInvoicePopup;
import pages.sb11.invoice.popup.invoices.ViewInvoicePopup;
import pages.sb11.invoice.popup.workflow.WorkFLowViewPopup;
import pages.sb11.invoice.popup.workflow.WorkflowNewInvoicePopup;
import testcases.BaseCaseAQS;
import utils.testraildemo.TestRails;
import java.io.IOException;
import java.util.List;


import static common.SBPConstants.*;

public class InvoicesTest extends BaseCaseAQS {
    @Test(groups = {"regression", "2023.10.31","ethan"})
    @TestRails(id = "2238")
    public void Invoices_TC_2238() {
        String newInvoiceNumber = "";
        String workflowName = "QA-Workflow01Up";
        String today = String.format(DateUtils.getDate(0,"dd/MM/yyyy","GMT -4"));
        String invoiceDescription = "Auto Testing Invoice Description " + DateUtils.getMilliSeconds();
        String currency = "HKD";
        String approvalStatus = "Approved";
        String invoiceStatus = "Cancelled";
        double amount = Double.parseDouble(StringUtils.generateNumeric(2));
        try {
            log("@title: Validate invoices info in Workflow Settings are displayed correctly Invoices page");
            Invoice invoiceObject = new Invoice.Builder()
                    .invoiceDescription(invoiceDescription).invoiceStatus(invoiceStatus)
                    .approvalStatus(approvalStatus).amount(amount)
                    .currency(currency).date(today).build();

            log("Precondition: Add new Invoice to Workflow: QA-Workflow01Up");
            WorkflowSettingsPage workflowSettingsPage = welcomePage.navigatePage(INVOICE, WORKFLOW_SETTINGS, WorkflowSettingsPage.class);
            workflowSettingsPage.selectWorkflowInfo().filter("", INVOICE_PROJECT, "", "", "", "", "", "", "");
            int workflowIndex = workflowSettingsPage.getWorkflowNameRowIndex(workflowName);
            WorkflowNewInvoicePopup newInvoicePopup = workflowSettingsPage.viewNewInvoice(workflowIndex);
            newInvoiceNumber = newInvoicePopup.getInvoiceNumber();
            newInvoicePopup.createNewInvoice(invoiceObject);

            log("@Step 1: Navigate to SB11 > Invoice > Workflow Settings");
            log("@Step 2: Click on Workflow info button");
            log("@Step 3: Click on View link and get invoice info");
            WorkFLowViewPopup workFLowViewPopup = workflowSettingsPage.viewLinkInvoice(workflowIndex);
            List<String> expectedListInvoice = workFLowViewPopup.getInvoiceListInfo(newInvoiceNumber);
            Invoice newInvoiceObject = new Invoice.Builder().invoiceNumber(newInvoiceNumber)
                    .invoiceDescription(expectedListInvoice.get(3)).invoiceStatus(expectedListInvoice.get(8))
                    .approvalStatus(expectedListInvoice.get(7)).amount(Double.valueOf(expectedListInvoice.get(6)))
                    .currency(expectedListInvoice.get(5)).date(expectedListInvoice.get(2))
                    .rsEntity(expectedListInvoice.get(4)).build();
            workFLowViewPopup.closePopup();

            log("@Step 4: Navigate to Invoice > Invoices");
            InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICE, INVOICES, InvoicesPage.class);
            log("@Step 5: Filter Invoices with invoice number: " + newInvoiceNumber);
            invoicesPage.filter("", "", "", "", "", "", "", newInvoiceNumber);
            log("@Verify 1: Verify invoice's information is displayed with the correct information");
            invoicesPage.verifyInvoice(newInvoiceObject, 1);
        } finally {
            log("Post-Condition: Modify status of created Invoice with ApprovalStatus: Rejected, InvoiceStatus: Cancelled");
            InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICE, INVOICES, InvoicesPage.class);
            invoicesPage.filter("", INVOICE_PROJECT, "", "", "", "", "", newInvoiceNumber);
            ActionInvoicePopup actionInvoicePopup = invoicesPage.actionInvoice(newInvoiceNumber);
            Invoice cancelledInvoice = new Invoice.Builder().approvalStatus("Rejected").invoiceStatus("Cancelled").build();
            actionInvoicePopup.editInvoice(cancelledInvoice);
            log("INFO: Executed completely");
        }
    }

    @Test(groups = {"regression", "2023.10.31","ethan"})
    @TestRails(id = "2239")
    public void Invoices_TC_2239(){
        log("@title: Validate payment date is consistent in summary and details");
        log("@Step 1: Navigate to SB11 > Invoice > Invoices");
        InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICE,INVOICES,InvoicesPage.class);

        log("@Step 2: Get the Date value of any invoices e.g 30/04/2023  ");
        invoicesPage.filter();
        String date = invoicesPage.getFirstInvoice().get(invoicesPage.colDate-1);
        date = DateUtils.formatDate(date, "dd/MM/yyyy", "dd MMMM yyyy");

        log("@Step 3: Click on View of according to invoice and get payment date");
        String paymentDate = invoicesPage.viewInvoice(1).getPaymentDateOfInvoice();

        log("@Verify 1: Verify the date is matched with payment date");
        Assert.assertEquals(date, paymentDate, "Failed! Payment date is summary mismatched with details");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.10.31","ethan"})
    @TestRails(id = "2240")
    public void Invoices_TC_2240(){
        log("@title: Validate information of invoices is updated accordingly after editing invoice ");
        log("@Step 1: Navigate to SB11 > Invoice > Invoices");
        String invoiceDescription = "Auto Testing Invoice Description " + DateUtils.getMilliSeconds();
        String currency = "HKD";
        String approvalStatus = "Approved";
        String invoiceStatus = "Cancelled";
        double amount = Double.parseDouble(StringUtils.generateNumeric(2));
        InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICE,INVOICES,InvoicesPage.class);

        log("@Step 2: Filter Invoices with project: QA-Project01");
        invoicesPage.filter("", INVOICE_PROJECT, "", "", "","","","");

        log("@Step 3: Click on Action link of first invoice");
        ActionInvoicePopup actionInvoicePopup = invoicesPage.actionInvoice(1);

        log("@Step 4: Update invoice information");
        Invoice invoiceObject = new Invoice.Builder()
                .amount(amount).invoiceDescription(invoiceDescription)
                .currency(currency).approvalStatus(approvalStatus)
                .invoiceStatus(invoiceStatus).build();
        actionInvoicePopup.editInvoice(invoiceObject);

        log("@Verify 1: Verify Invoice's information is updated accordingly after editing");
        invoicesPage.verifyInvoice(invoiceObject, 1);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.10.31","ethan"})
    @TestRails(id = "2241")
    public void Invoices_TC_2241(){
        log("@title: Validate Invoice info displayed correct when viewing");
        log("=========>>  @Precondition: Get invoice data on View link of Workflow Settings page  <<=========");
        log("@Precondition-step 1: Navigate to SB11 > Invoice > Workflow Settings");
        String workflowName = "QA-Workflow01Up";
        WorkflowSettingsPage workflowSettingsPage = welcomePage.navigatePage(INVOICE, WORKFLOW_SETTINGS, WorkflowSettingsPage.class);
        log("@Precondition-step 2: Click on Workflow info button");
        workflowSettingsPage.selectWorkflowInfo();
        log("@Precondition-step 3: Filter invoice and click on View link");
        workflowSettingsPage.filter("", INVOICE_PROJECT, "", "", "", "", "", "", "");
        WorkFLowViewPopup workFLowViewPopup = workflowSettingsPage.viewLinkInvoice(workflowName);
        log("@Precondition-step 4: Get Invoice data");
        List<String> expectedListInvoice = workFLowViewPopup.getInvoiceListInfo(1);
        Invoice invoiceObject = new Invoice.Builder().invoiceNumber(expectedListInvoice.get(1))
                .invoiceDescription(expectedListInvoice.get(3)).invoiceStatus(expectedListInvoice.get(8))
                .approvalStatus(expectedListInvoice.get(7)).amount(Double.valueOf(expectedListInvoice.get(6)))
                .currency(expectedListInvoice.get(5)).date(expectedListInvoice.get(2))
                .rsEntity(expectedListInvoice.get(4)).build();
        workFLowViewPopup.closePopup();

        log("@Step 1: Navigate to Invoice > Invoices");
        InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICE, INVOICES, InvoicesPage.class);

        log("@Step 2: Filter invoice number in precondition");
        invoicesPage.filter("", "", "", "", "", "", "", invoiceObject.getInvoiceNumber());
        log("@Step 3: Click on View of the invoice");
        ViewInvoicePopup viewInvoicePopup = invoicesPage.viewInvoice(1);
        log("@Verify 1: Verify invoice info displayed correctly in the View popup");
        viewInvoicePopup.verifyInvoice(invoiceObject);
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.10.31","ethan"})
    @TestRails(id = "2767")
    public void Invoices_TC_2767(){
        log("@title: Validate Log link works");
        log("@Step 1: Navigate to SB11 > Invoice > Invoices");
        InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICE, INVOICES, InvoicesPage.class);

        log("@Step 2: Filter Invoices with default");
        invoicesPage.filter();

        log("@Step 3: Click on Log of the first invoice");
        LogInvoicePopup logInvoicePopup = invoicesPage.logInvoice(1);

        log("@Verify 1: Verify title of Log popup is displayed correct");
        Assert.assertEquals(logInvoicePopup.getTitle(), LogInvoicePopupConstants.POPUP_TITLE, "Failed! Title of Log Invoice popup is incorrect");
        log("@Verify 2: Verify header list of Log popup table is displayed correct");
        Assert.assertEquals(logInvoicePopup.getHeaderList(), LogInvoicePopupConstants.HEADER_LIST, "Failed! Header list of Log Invoice table is incorrect");
        log("INFO: Executed completely");
    }

    @Test(groups = {"regression", "2023.10.31","ethan"})
    @TestRails(id = "2771")
    public void Invoices_TC_2771() {
        String downloadPath = getDownloadPath();
        log("@title: Validate user can save invoice as PDF file successfully");
        log("@Step 1: Navigate to SB11 > Invoice > Invoices");
        InvoicesPage invoicesPage = welcomePage.navigatePage(INVOICE, INVOICES, InvoicesPage.class);

        log("@Step 2: Filter Invoices with default");
        invoicesPage.filter();
        String invoiceNumber = invoicesPage.getFirstInvoice().get(invoicesPage.colInvoiceNumber - 1).replaceAll("\\s","");

        log("@Step 3: Click on View of the first invoice");
        ViewInvoicePopup viewInvoicePopup = invoicesPage.viewInvoice(1);

        log("@Step 4: Click on 'Save as PDF' to download invoice");
        viewInvoicePopup.exportPDF();

        log("@Verify 1: Verify invoice was saved successfully as PDF file");
        String filePath = downloadPath + invoiceNumber.replace("/", "_") + ".pdf";
        Assert.assertTrue(FileUtils.doesFileNameExist(filePath),
                "Failed to download Expected document");
        log("@Post-condition: delete download file");
//        try {
//            FileUtils.removeFile(filePath);
//        } catch (IOException e) {
//            log(e.getMessage());
//        }
        log("INFO: Executed completely");
    }

}
