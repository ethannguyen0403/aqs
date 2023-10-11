package pages.sb11.invoice;

import com.paltech.element.common.*;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.invoice.popup.workflow.WorkFLowViewPopup;
import pages.sb11.invoice.popup.workflow.WorkflowNewInvoicePopup;

public class WorkflowSettingsPage extends WelcomePage {

    protected Button btnWorkflowInfo = Button.xpath("//input[@id='wfInfo']/following::label[1]");
    protected Button btnSearch = Button.xpath("//button[contains(@class,'icon-search-custom')]");
    protected Label lblInvoiceNumber = Label.xpath("//div[@class='modal-body invoice-header']//div[contains(text(), 'No:')]");
    protected DropDownBox ddbInvType = DropDownBox.xpath("//span[contains(text(),'Inv Type')]/following::select[1]");
    protected DropDownBox ddbProject = DropDownBox.xpath("//span[contains(text(),'Project')]/following::select[1]");
    protected DropDownBox ddbBusinessEntity = DropDownBox.xpath("//span[contains(text(),'Business Entity')]/following::select[1]");
    protected DropDownBox ddbAssociateType = DropDownBox.xpath("//span[contains(text(),'Associate Type')]/following::select[1]");
    protected DropDownBox ddbAssociate = DropDownBox.xpath("//span[contains(text(),'Associate')]/following::select[1]");
    protected DropDownBox ddbRecPayType = DropDownBox.xpath("//span[contains(text(),'Rec-Pay Type')]/following::select[1]");
    protected DropDownBox ddbPaymentType = DropDownBox.xpath("//span[contains(text(),'Payment Type')]/following::select[1]");
    protected DropDownBox ddbPaymentTerm = DropDownBox.xpath("//span[contains(text(),'Payment Term')]/following::select[1]");
    protected TextBox txtWorkflowName = TextBox.xpath("//span[contains(text(),'Workflow Name')]/following::input");

    private int totalCol = 18;
    private int colView = 16;
    private int colNew = 15;
    private int colWorkflowName = 7;
    public Table tblWorkflow = Table.xpath("//app-workflow-setting//table", totalCol);


    public String getInvoiceNumber(){
    return lblInvoiceNumber.getText().replace("No:", "").trim();
    }

    public WorkFLowViewPopup viewLinkInvoice(int index) {
        tblWorkflow.getControlOfCell(1, colView, index, null).click();
        return new WorkFLowViewPopup();
    }

    public WorkFLowViewPopup viewLinkInvoice(String workflowName) {
        int workflowIndex = getWorkflowNameRowIndex(workflowName);
        return viewLinkInvoice(workflowIndex);
    }

    public WorkflowNewInvoicePopup viewNewInvoice(int index) {
        tblWorkflow.getControlOfCell(1, colNew, index, null).click();
        return new WorkflowNewInvoicePopup();
    }

    public WorkflowSettingsPage selectWorkflowInfo() {
        btnWorkflowInfo.jsClick();
        waitSpinnerDisappeared();
        return this;
    }

    public void filter() {
        filter("", "", "", "", "", "", "", "", "");
    }

    public void filter(String invType, String project, String businessEntity, String associateType, String associate, String recPayType,
                       String paymentType, String paymentTerm,
                       String workflowName) {
        if (!invType.isEmpty())
            ddbInvType.selectByVisibleText(invType);
        if (!businessEntity.isEmpty())
            ddbBusinessEntity.selectByVisibleText(businessEntity);
        if (!associateType.isEmpty()) {
            ddbAssociateType.selectByVisibleText(associateType);
            waitSpinnerDisappeared();
        }
        if (!project.isEmpty() && ddbProject.isDisplayed())
            ddbProject.selectByVisibleText(project);
        if (!associate.isEmpty())
            ddbAssociate.selectByVisibleText(associate);
        if (!paymentType.isEmpty())
            ddbPaymentType.selectByVisibleText(paymentType);
        if (!recPayType.isEmpty())
            ddbRecPayType.selectByVisibleText(recPayType);
        if (!paymentTerm.isEmpty())
            ddbPaymentTerm.selectByVisibleText(paymentTerm);
        if (!workflowName.isEmpty())
            txtWorkflowName.sendKeys(workflowName);
        btnSearch.click();
        waitSpinnerDisappeared();
    }

    public int getWorkflowNameRowIndex(String workflowName) {
        Label lbl;
        int i = 1;
        while (true) {
            lbl = Label.xpath(tblWorkflow.getxPathOfCell(1, colWorkflowName, i, null));
            if (!lbl.isDisplayed()) {
                System.out.println(String.format("Not found the value in the column in the table"));
                return -1;
            }
            lbl.getText().trim();
            if (lbl.getText().equals(workflowName)) {
                System.out.println(String.format("Found the value in the column in the table"));
                return i;
            }
            i = i + 1;
        }
    }
}
