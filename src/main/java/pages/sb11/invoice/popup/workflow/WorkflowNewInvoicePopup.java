package pages.sb11.invoice.popup.workflow;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import objects.Invoice;
import pages.sb11.invoice.WorkflowSettingsPage;

import java.util.Objects;

public class WorkflowNewInvoicePopup extends WorkflowSettingsPage {
    protected TextBox txtDescription = TextBox.xpath("//div[.='Description']/following::textarea[1]");
    DropDownBox ddlCurrency = DropDownBox.name("curSelect");
    TextBox txtAmount = TextBox.name("totalAmount");
    Button btnSubmit = Button.xpath("//button[@type='submit']");

    public void createNewInvoice(Invoice invoice) {
        if (invoice.getInvoiceDescription() != null)
            txtDescription.sendKeys(invoice.getInvoiceDescription());
        if (Objects.nonNull(invoice.getAmount()))
            txtAmount.sendKeys(String.valueOf(invoice.getAmount()));
        if (invoice.getCurrency() != null)
            ddlCurrency.selectByVisibleText(invoice.getCurrency());
        btnSubmit.click();
        waitSpinnerDisappeared();
    }

}
