package pages.sb11.generalReports.popup.feedreport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.popup.ConfirmPopup;

public class ProviderPopup {
    public Label lblTitle = Label.xpath("//app-provider//h5[contains(@class,'modal-title')]");
    public Label lblNote = Label.xpath("//app-provider//label");
    public Table tblProvider = Table.xpath("//app-provider//table",7);
    public Button btnAddProvider = Button.xpath("//button[text()='Add Provider']");
    public Button btnClosePopup = Button.xpath("//app-provider//span[contains(@class,'close-icon')]");
    public void addProvider(String providerName, String accountCode, String cur, String subaccDebit, String subaccCredit, boolean save) {
        btnAddProvider.click();
        inputInformation(1,providerName,accountCode,cur,subaccDebit,subaccCredit);
        if (save){
            ConfirmPopup confirmPopup = clickToSaveProvider(1);
            confirmPopup.btnYes.click();
            //wait for loading
            waitForUpload(3000);
        }
    }
    public void inputInformation(int indexRow, String providerName, String accountCode, String cur, String subaccDebit, String subaccCredit){
        TextBox txtProviderName = TextBox.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName("Provider Name"),indexRow,"input"));
        TextBox txtAccountCode = TextBox.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName("Account Code"),indexRow,"input"));
        DropDownBox ddCur = DropDownBox.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName("CUR"),indexRow,"select"));
        DropDownBox ddSubaccDebit = DropDownBox.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName("Sub-account in Debit"),indexRow,"select"));
        DropDownBox ddSubaccCrebit = DropDownBox.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName("Sub-account in Credit"),indexRow,"select"));
        if (!providerName.isEmpty()){
            txtProviderName.sendKeys(providerName);
        }
        if (!accountCode.isEmpty()){
            txtAccountCode.sendKeys(accountCode);
        }
        if (!cur.isEmpty()){
            ddCur.selectByVisibleText(cur);
        }
        //wait for sub-acc dropdown update
        waitForUpload(1500);
        if (!subaccDebit.isEmpty()){
            ddSubaccDebit.selectByVisibleText(subaccDebit);
        }
        if (!subaccCredit.isEmpty()){
            ddSubaccCrebit.selectByVisibleText(subaccCredit);
        }
    }
    public void editProvider(String providerName, String renameProvidername, String renameAccountCode, String renameCur, String renameSubaccDebit, String renameSubaccCredit, boolean save){
        int indexProvider = tblProvider.getRowIndexContainValue(providerName,tblProvider.getColumnIndexByName("Provider Name"),"span");
        buttonEdit(providerName).click();
        inputInformation(indexProvider,renameProvidername,renameAccountCode,renameCur,renameSubaccDebit,renameSubaccCredit);
        if (save){
            ConfirmPopup confirmPopup = clickToSaveProvider(indexProvider);
            confirmPopup.btnYes.click();
            //wait for loading
            waitForUpload(3000);
        }
    }
    public Button buttonEdit(String providerName){
        int indexProvider = tblProvider.getRowIndexContainValue(providerName,tblProvider.getColumnIndexByName("Provider Name"),"span");
        return Button.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName(""),indexProvider,"span[text()='Edit']"));
    }
    public void deleteProvider(String providerName, boolean yes) throws InterruptedException {
        int indexProvider = tblProvider.getRowIndexContainValue(providerName,tblProvider.getColumnIndexByName("Provider Name"),"span");
        clickToXIcon(indexProvider);
        if (yes){
            ConfirmPopup confirmPopup = new ConfirmPopup();
            //Wait for getting message
            waitForUpload(1000);
            Assert.assertEquals(confirmPopup.getContentMessage(), String.format(SBPConstants.FeedReport.CONFIRM_MES_DELETE_PROVIDER,providerName),"FAILED! Deleting Message displays incorrect");
            confirmPopup.btnYes.click();
            //wait for loading
            waitForUpload(3000);
        }
    }

    public ConfirmPopup clickToSaveProvider(int indexRow){
        Button btnSave = Button.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName(""),indexRow,"em[contains(@class,'fa-save')]"));
        btnSave.click();
        return new ConfirmPopup();
    }
    public void clickToXIcon(int indexProvider){
        Button btnX = Button.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName(""),indexProvider,"em[contains(@class,'fa-times-circle')]"));
        btnX.click();
    }
    public boolean isProviderDisplay(String providerName, String accountCode, String cur, String subaccDebit, String subaccCredit){
        int indexProvider = tblProvider.getRowIndexContainValue(providerName,tblProvider.getColumnIndexByName("Provider Name"),"span");
        if (indexProvider == 0){
            System.out.println("FAILED! Provider Name is not displayed");
            return false;
        }
        String accountCodeEx = tblProvider.getControlOfCell(1,tblProvider.getColumnIndexByName("Account Code"),indexProvider,"span").getText();
        String curEx = tblProvider.getControlOfCell(1,tblProvider.getColumnIndexByName("CUR"),indexProvider,"span").getText();
        String subaccDebitEx = tblProvider.getControlOfCell(1,tblProvider.getColumnIndexByName("Sub-account in Debit"),indexProvider,"span").getText();
        String subaccCreditEx = tblProvider.getControlOfCell(1,tblProvider.getColumnIndexByName("Sub-account in Credit"),indexProvider,"span").getText();
        if (!accountCode.equals(accountCodeEx)){
            System.out.println("FAILED! Account Code displays incorrect");
            return false;
        }
        if (!cur.equals(curEx)){
            System.out.println("FAILED! Cur displays incorrect");
            return false;
        }
        if (!subaccDebit.equals(subaccDebitEx)){
            System.out.println("FAILED! Sub-account in Debit displays incorrect");
            return false;
        }
        if (!subaccCredit.equals(subaccCreditEx)){
            System.out.println("FAILED! Sub-account in Credit displays incorrect");
            return false;
        }
        return true;
    }
    private void waitForUpload(int milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeToPopup() {
        btnClosePopup.click();
        waitForUpload(2000);
    }

    public boolean isBtnEditCanClick(String providerName) {
        int indexProvider = tblProvider.getRowIndexContainValue(providerName,tblProvider.getColumnIndexByName("Provider Name"),"span");
        Button btnEdit = Button.xpath(tblProvider.getxPathOfCell(1,tblProvider.getColumnIndexByName(""),indexProvider,"span[text()='Edit']/parent::span"));
        if (btnEdit.getAttribute("Class").contains("disable-btn")){
            System.out.println("Edit button disabled");
            return false;
        }
        return true;
    }
}
