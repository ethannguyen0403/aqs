package pages.sb11.invoice.popup.workflow;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.invoice.WorkflowSettingsPage;

import java.util.ArrayList;
import java.util.List;

public class WorkFLowViewPopup extends WorkflowSettingsPage {

    Button btnClose = Button.xpath("//span[contains(@class,'close-icon')]");

    private int colInvoiceNum = 2;
    private int totalCol = 9;
    private Table tblViewInvoice = Table.xpath("//div[@class='modal-view-invoice']//table", totalCol);


    public List<String> getInvoiceListInfo(String invoiceName) {
        int rowIndex = getInvoiceRowIndex(invoiceName);
        return getInvoiceListInfo(rowIndex);
    }

    public List<String> getInvoiceListInfo(int invoiceIndex) {
        List<String> invoiceList = new ArrayList<>();
        for (int i = 1; i <= totalCol; i++) {
            Label lbl = Label.xpath(tblViewInvoice.getxPathOfCell(1, i, invoiceIndex, null));
            invoiceList.add(lbl.getText().trim());
        }
        return invoiceList;
    }

    public void closePopup() {
        btnClose.click();
        waitPageLoad();
    }

    public int getInvoiceRowIndex(String invoiceName) {
        Label lbl;
        int i = 1;
        while (true) {
            lbl = Label.xpath(tblViewInvoice.getxPathOfCell(1, colInvoiceNum, i, null));
            if (!lbl.isDisplayed()) {
                System.out.println(String.format("Not found the value in the column in the table"));
                return -1;
            }
            lbl.getText().trim();
            if (lbl.getText().equals(invoiceName)) {
                System.out.println(String.format("Found the value in the column in the table"));
                return i;
            }
            i = i + 1;
        }
    }

}
