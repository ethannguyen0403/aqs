package pages.sb11.trading.analyse;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.popup.ConfirmPopup;

public class CreateNewLinePopup {
    public Label lblTitle = Label.xpath("//app-manage-lines//h4[text()='Create New Line']");
    DropDownBox ddLevel = DropDownBox.xpath("//app-manage-lines//div[text()='Level']//following-sibling::select");
    TextBox txtLineName = TextBox.xpath("//app-manage-lines//div[text()='Line Name']//following-sibling::input");
    Button btnCreate = Button.xpath("//app-manage-lines//button[text()='Create']");
    public Button btnBack = Button.xpath("//app-manage-lines//button[contains(@class,'btn-outline')]");
    public int colLine = 4;
    public int colMapped = 5;
    public int colAction = 8;
    public Table tblData = Table.xpath("//app-manage-lines//table",8);
    public void createNewLine(String level, String lineName, String... masterAgent){
        ddLevel.selectByVisibleText(level);
        waitForUpdate(2000);
        for (String item : masterAgent){
            selectMasterAgent(item,false);
        }
        txtLineName.sendKeys(lineName);
        btnCreate.click();
        waitForUpdate(3000);
    }
    private void selectMasterAgent(String masterAgent, boolean inTableData){
        String table = "";
        if (inTableData){
            table = "//table";
        }
        Button btnMasterAgent = Button.xpath(String.format("//app-manage-lines%s//angular2-multiselect",table));
        btnMasterAgent.click();
        waitForUpdate(5000);
        Label lblMasterAgent = Label.xpath(String.format("//app-manage-lines%s//angular2-multiselect//ul//label[text()='%s']",table,masterAgent));
        lblMasterAgent.scrollToThisControl(true);
        waitForUpdate(5000);
        lblMasterAgent.click();
        waitForUpdate(1000);
        btnMasterAgent.click();
    }
    private void waitForUpdate(int minis){
        try {
            Thread.sleep(minis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteLine(String lineName){
        int indexLine = tblData.getRowIndexContainValue(lineName,colLine,null);
        Button btnDelete = Button.xpath(tblData.getxPathOfCell(1,colAction,indexLine,"em[contains(@class,'fa-times-circle')]"));
        btnDelete.scrollToTop();
        btnDelete.click();
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.btnYes.click();
    }
    public String getMappedUserOfLine(String lineName){
        int indexLine = tblData.getRowIndexContainValue(lineName,colLine,null);
        return Label.xpath(tblData.getxPathOfCell(1,colMapped,indexLine,"div")).getText();
    }
    public void editLine(String lineName, String lineEdit, boolean editMapped, String... mappedUserEdit){
        int indexLine = tblData.getRowIndexContainValue(lineName,colLine,null);
        Button btnDelete = Button.xpath(tblData.getxPathOfCell(1,colAction,indexLine,"em[contains(@class,'fa-edit')]"));
        btnDelete.scrollToTop();
        btnDelete.click();
        waitForUpdate(3000);
        if (!lineEdit.isEmpty()){
            TextBox txtLine = TextBox.xpath(tblData.getxPathOfCell(1,colLine,indexLine,"input"));
            txtLine.sendKeys(lineEdit);
        }
        if (editMapped){
            Button btnClear = Button.xpath("//app-manage-lines//table//angular2-multiselect//span[contains(@class,'clear-all')]");
            btnClear.click();
            for (String item : mappedUserEdit){
                selectMasterAgent(item,true);
            }
        }
        Button btnSave = Button.xpath(tblData.getxPathOfCell(1,colAction,indexLine,"em[contains(@class,'fa-save')]"));
        btnSave.click();
        ConfirmPopup confirmPopup = new ConfirmPopup();
        confirmPopup.btnYes.click();
        waitForUpdate(3000);
    }
}
