package pages.sb11.invoice.popup.invoices;

import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import objects.Invoice;
import org.testng.Assert;

import java.util.Objects;

public class ViewInvoicePopup {

    Label lblTitle = Label.xpath("//app-view-invoice//span[contains(@class,'modal-title')]");
    Label lblInvoiceNumber = Label.xpath("//div[@class='modal-body invoice-header']//div[contains(text(), 'No:')]");
    Label lblPaymentDateValue = Label.xpath("//form[@id='my-table']/div[3]//div[contains(text(),'Payment Date')]/following::div[1]//span");
    Label lblDescription = Label.xpath("//form[@id='my-table']/div[1]//div[contains(text(), 'Description')]/following::span[1]");
    Label lblCur = Label.xpath("//form[@id='my-table']/div[3]//span[text()='CUR']//following::span[1]");
    Label lblAmount = Label.xpath("//form[@id='my-table']/div[3]//div[contains(text(),'Amount')]/following::div[1]//span");
    Label lblRSEntity = Label.xpath("//form[@id='my-table']/div[2]//div[contains(text(),'Receive Send Entity')]/following::span[1]");
    Label lblApprovalStatus = Label.xpath("//form[@id='my-table']/div[5]//div[contains(text(),'Approval Status:')]/following::span[1]");
    Label lblInvoiceStatus = Label.xpath("//form[@id='my-table']/div[5]//div[contains(text(),'Invoice Status:')]/following::span[1]");
    protected Label btnSaveAsPDF = Label.xpath("//button[text()='Save As PDF']");

    public ViewInvoicePopup(){
    }

    public ViewInvoicePopup(boolean isWaitLoad) {
        if (isWaitLoad) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public String getTitlePage (){return lblTitle.getText().trim();}

    public String getPaymentDateOfInvoice(){
        return lblPaymentDateValue.getText().replace(":", "").trim();
    }

    public void exportPDF(){
        btnSaveAsPDF.click();
    }

    public void verifyInvoice(Invoice invoice) {
        String actualRsEntity = lblRSEntity.getText().replace(":", "").trim();
        String actualInvoiceNumber = lblInvoiceNumber.getText().replace("No:", "").trim();
        String actualDate = DateUtils.formatDate(getPaymentDateOfInvoice(), "dd MMMM yyyy", "dd/MM/yyyy");
        String actualDescription = lblDescription.getText().trim();
        String actualAmount = lblAmount.getText().replace(":", "").trim();
        String actualCurrency = lblCur.getText().replace(":", "").trim();
        String actualApprovalStatus = lblApprovalStatus.getText().trim();
        String actualInvoiceStatus = lblInvoiceStatus.getText().trim();

        if (invoice.getDate() != null)
            Assert.assertEquals(actualDate, invoice.getDate(), "Failed! Date of Invoice is incorrect");
        if (invoice.getInvoiceNumber() != null)
            Assert.assertEquals(actualInvoiceNumber, invoice.getInvoiceNumber(), "Failed! Number of Invoice is incorrect");
        if (invoice.getRsEntity() != null)
            Assert.assertEquals(actualRsEntity, invoice.getRsEntity(), "Failed! Rs Entity of Invoice is incorrect");
        if (invoice.getInvoiceDescription() != null)
            Assert.assertEquals(actualDescription, invoice.getInvoiceDescription(), "Failed! Description of Invoice is incorrect");
        if (!Objects.equals(invoice.getAmount(), 0.0))
            Assert.assertEquals(actualAmount, String.valueOf(String.format("%.2f", invoice.getAmount())),
                    "Failed! Amount of Invoice is incorrect");
        if (invoice.getCurrency() != null)
            Assert.assertEquals(actualCurrency, invoice.getCurrency(), "Failed! Currency of Invoice is incorrect");
        if (invoice.getApprovalStatus() != null)
            Assert.assertEquals(actualApprovalStatus, invoice.getApprovalStatus(), "Failed! Approval status of Invoice is incorrect");
        if (invoice.getInvoiceStatus() != null)
            Assert.assertEquals(actualInvoiceStatus, invoice.getInvoiceStatus(), "Failed! Status of Invoice is incorrect");
    }

}
