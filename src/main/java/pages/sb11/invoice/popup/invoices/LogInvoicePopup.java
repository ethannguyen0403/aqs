package pages.sb11.invoice.popup.invoices;

import com.paltech.element.common.Label;
import controls.Table;

import java.util.List;

public class LogInvoicePopup {
    private int totalCol = 6;
    public Label lblTitle = Label.xpath("//app-invoice-log//h5");
    public Table tblLogInvoice = Table.xpath("//app-invoice-log//table", totalCol);

    public LogInvoicePopup(){
    }

    public LogInvoicePopup(boolean isWaitLoad) {
        if (isWaitLoad) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public String getTitle(){return lblTitle.getText().trim();}

    public List<String> getHeaderList(){
        return  tblLogInvoice.getHeaderNameOfRows();
    }
}
