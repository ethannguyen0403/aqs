package pages.sb11.invoice;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import objects.Invoice;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.invoice.popup.invoices.ActionInvoicePopup;
import pages.sb11.invoice.popup.invoices.LogInvoicePopup;
import pages.sb11.invoice.popup.invoices.ViewInvoicePopup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class InvoicesPage extends WelcomePage {
    protected Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    protected Button btnSearch = Button.xpath("//button[contains(@class,'icon-search-custom')]");
    protected DropDownBox ddbInvType = DropDownBox.xpath("//div[contains(text(),'Inv Type')]//following::select[1]");
    protected DropDownBox ddbProject = DropDownBox.xpath("//div[contains(text(),'Project')]//following::select[1]");
    protected DropDownBox ddbBusinessEntity = DropDownBox.xpath("//div[contains(text(),'Business Entity')]//following::select[1]");
    protected DropDownBox ddbAssociateType = DropDownBox.xpath("//div[contains(text(),'Associate Type')]//following::select[1]");
    protected DropDownBox ddbAssociate = DropDownBox.xpath("//div[contains(text(),'Associate')]//following::select[1]");
    protected DropDownBox ddbPaymentType = DropDownBox.xpath("//div[contains(text(),'Payment Type')]//following::select[1]");
    protected DropDownBox ddbInvStatus = DropDownBox.xpath("//div[contains(text(),'Inv Status')]//following::select[1]");
    protected TextBox txtInvoiceNumber = TextBox.name("txtAccountCode");

    int totalColumnNumber = 17;
    public int colAction= 3;
    public int colView= 4;
    public int colInvoiceNumber = 5;
    public int colDate = 6;
    public int colDescription = 8;
    public int colRsEntity = 9;
    public int colCUR = 10;
    public int colAmount = 11;
    public int colApproval = 14;
    public int colStatus = 15;
    public int colLog = 17;

    public Table tblInvoice = Table.xpath("//table[contains(@class,'table')]",totalColumnNumber);

    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public void filter(){
        filter("", "", "", "", "", "", "", "");
    }

    public void filter(String invType, String project, String businessEntity, String associateType , String associate, String paymentType, String invStatus, String invoiceNumber){
        if(!invType.isEmpty())
            ddbInvType.selectByVisibleText(invType);
        if(!project.isEmpty() && ddbProject.isDisplayed())
            ddbProject.selectByVisibleText(project);
        if(!businessEntity.isEmpty())
            ddbBusinessEntity.selectByVisibleText(businessEntity);
        if(!associateType.isEmpty()) {
            ddbAssociateType.selectByVisibleText(associateType);
            waitSpinnerDisappeared();
        }
        if(!associate.isEmpty())
            ddbAssociate.selectByVisibleText(associate);
        if(!paymentType.isEmpty())
            ddbPaymentType.selectByVisibleText(paymentType);
        if(!invStatus.isEmpty())
            ddbInvStatus.selectByVisibleText(invStatus);
        if(!invoiceNumber.isEmpty())
            txtInvoiceNumber.sendKeys(invoiceNumber);
        btnSearch.click();
        waitSpinnerDisappeared();
    }

    public ArrayList<String> getFirstInvoice(){
        return  tblInvoice.getRows(1,false).get(1);
    }

    public ArrayList<String> getInvoiceByIndex(int index){
        return tblInvoice.getRows(1,false).get(index);
    }

    public void verifyInvoice(Invoice invoice, int invoiceIndex) {
        List<String> invoiceListCol = getInvoiceByIndex(invoiceIndex);
        String actualRsEntity = invoiceListCol.get(colRsEntity - 1);
        String actualInvoiceNumber = invoiceListCol.get(colInvoiceNumber - 1);
        String actualDate = invoiceListCol.get(colDate - 1);
        String actualDescription = invoiceListCol.get(colDescription - 1);
        String actualAmount = invoiceListCol.get(colAmount - 1);
        String actualCurrency = invoiceListCol.get(colCUR - 1);
        String actualApprovalStatus = invoiceListCol.get(colApproval - 1);
        String actualInvoiceStatus = invoiceListCol.get(colStatus - 1);

        if (invoice.getDate() != null)
            Assert.assertEquals(actualDate, invoice.getDate(), "Failed! Date of Invoice is incorrect");
        if (invoice.getInvoiceNumber() != null)
            Assert.assertEquals(actualInvoiceNumber, invoice.getInvoiceNumber(), "Failed! Number of Invoice is incorrect");
        if (invoice.getRsEntity() != null)
            Assert.assertEquals(actualRsEntity, invoice.getRsEntity(), "Failed! Rs Entity of Invoice is incorrect");
        if (invoice.getInvoiceDescription() != null)
            Assert.assertEquals(actualDescription, invoice.getInvoiceDescription(), "Failed! Description of Invoice is incorrect");
        if (!Objects.equals(invoice.getAmount(), 0.0))
            Assert.assertEquals(String.valueOf(String.format("%.2f", invoice.getAmount())), actualAmount,
                    "Failed! Amount of Invoice is incorrect");
        if (invoice.getCurrency() != null)
            Assert.assertEquals(actualCurrency, invoice.getCurrency(), "Failed! Currency of Invoice is incorrect");
        if (invoice.getApprovalStatus() != null)
            Assert.assertEquals(actualApprovalStatus, invoice.getApprovalStatus(), "Failed! Approval status of Invoice is incorrect");
        if (invoice.getInvoiceStatus() != null)
            Assert.assertEquals(actualInvoiceStatus, invoice.getInvoiceStatus(), "Failed! Status of Invoice is incorrect");
    }


    public ViewInvoicePopup viewInvoice(int index){
        tblInvoice.getControlOfCell(1,colView,index,null).click();
        return new ViewInvoicePopup(true);
    }

    public LogInvoicePopup logInvoice(int index) {
        tblInvoice.getControlOfCell(1, colLog, index, null).click();
        return new LogInvoicePopup(true);
    }

    public ActionInvoicePopup actionInvoice(int index) {
        tblInvoice.getControlOfCell(1, colAction, index, null).click();
        waitSpinnerDisappeared();
        return new ActionInvoicePopup();
    }

    public ActionInvoicePopup actionInvoice(String invoiceName) {
        int invoiceRowIndex = getInvoiceRowIndex(invoiceName);
        waitSpinnerDisappeared();
        return actionInvoice(invoiceRowIndex);
    }



    public int getInvoiceRowIndex(String invoiceName) {
        Label lbl;
        int i = 1;
        while (true) {
            lbl = Label.xpath(tblInvoice.getxPathOfCell(1, colInvoiceNumber, i, null));
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
