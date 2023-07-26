package pages.sb11.invoice;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.invoice.popup.ViewInvoicePopup;

import java.util.ArrayList;

public class InvoicesPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public Button btnSearch = Button.xpath("//button[contains(@class,'icon-search-custom')]");
    public DropDownBox ddbInvType = DropDownBox.xpath("//div[contains(text(),'Inv Type')]//following::select[1]");
    public DropDownBox ddbProject = DropDownBox.xpath("//div[contains(text(),'Project')]//following::select[1]");
    public DropDownBox ddbBusinessEntity = DropDownBox.xpath("//div[contains(text(),'Business Entity')]//following::select[1]");
    public DropDownBox ddbAssociateType = DropDownBox.xpath("//div[contains(text(),'Associate Type')]//following::select[1]");
    public DropDownBox ddbAssociate = DropDownBox.xpath("//div[contains(text(),'Associate')]//following::select[1]");
    public DropDownBox ddbPaymentType = DropDownBox.xpath("//div[contains(text(),'Payment Type')]//following::select[1]");
    public DropDownBox ddbInvStatus = DropDownBox.xpath("//div[contains(text(),'Inv Status')]//following::select[1]");
    public TextBox txtInvoiceNumber = TextBox.name("txtAccountCode");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    int totalColumnNumber = 17;
    int coli = 2;
    int colAction= 3;
    int colView= 4;
    int colInvoiceNumber =5;
    public int colDate = 6;
    public Table tblInvoice = Table.xpath("//table[contains(@class,'table')]",totalColumnNumber);

    public void filter(String invType, String project, String businessEntity, String associateType , String associate, String paymentType, String invStatus, String invoiceNumber){
        if(!invType.isEmpty())
            ddbInvType.selectByVisibleText(invType);
        if(!project.isEmpty())
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

    public ArrayList<String> getFirstInvoce(){
        return  tblInvoice.getRows(1,false).get(0);
    }

    public ViewInvoicePopup viewInvoice(int index){
        tblInvoice.getControlOfCell(1,colView,index,null);
        return new ViewInvoicePopup();
    }


}
