package pages.sb11.invoice.popup;

import com.paltech.element.common.Label;

public class ViewInvoicePopup {
    public Label lblTitle = Label.xpath("//app-view-invoice//span[contains(@class,'modal-title')]");
    public Label lblPaymentDateValue = Label.xpath("//form[@id='my-table']/div[3]//div[contains(text(),'Payment Date')]/following::div[1]//span");
    public String getTitlePage (){return lblTitle.getText().trim();}

    public String getPaymentDateOfInvoice(){
        return lblPaymentDateValue.getText().trim();
    }
}
