package pages.sb11.invoice.popup.invoices;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.TextBox;
import objects.Invoice;
import pages.sb11.invoice.InvoicesPage;

import java.util.Objects;

public class ActionInvoicePopup extends InvoicesPage {
    TextBox txtDescription = TextBox.xpath("//textarea[@name='txtArea1']");
    DropDownBox ddlCurrency = DropDownBox.xpath("//select[@name='curSelect']");
    DropDownBox ddlApprovalStatus = DropDownBox.xpath("//div[.='Approval Status:']/following::select[1]");
    DropDownBox ddlInvoiceStatus = DropDownBox.xpath("//div[.='Invoice Status:']/following::select[1]");
    TextBox txtAmount = TextBox.name("totalAmount");
    Button btnSubmit = Button.xpath("//button[@type='submit']");

    public void editInvoice(Invoice invoice) {
        if (invoice.getInvoiceDescription() != null)
            txtDescription.sendKeys(invoice.getInvoiceDescription());
        if (!Objects.equals(invoice.getAmount(), 0.0))
            txtAmount.sendKeys(String.valueOf(invoice.getAmount()));
        if (invoice.getCurrency() != null)
            ddlCurrency.selectByVisibleText(invoice.getCurrency());
        if (invoice.getApprovalStatus() != null)
            ddlApprovalStatus.selectByVisibleText(invoice.getApprovalStatus());
        if (invoice.getInvoiceStatus() != null)
            ddlInvoiceStatus.selectByVisibleText(invoice.getInvoiceStatus());
        btnSubmit.click();
        waitSpinnerDisappeared();
    }
}
