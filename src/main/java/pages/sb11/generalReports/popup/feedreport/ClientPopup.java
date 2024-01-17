package pages.sb11.generalReports.popup.feedreport;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.sb11.popup.ConfirmPopup;

import java.util.*;

public class ClientPopup {
    public Label lblTitle = Label.xpath("//app-client//h5[contains(@class,'modal-title')]");
    public Label lblNote = Label.xpath("//app-client//label");
    public Table tblClient = Table.xpath("//app-client//table",8);
    public Button btnAddClient = Button.xpath("//button[contains(text(),'Add Client')]");
    String clientXpathByProvider = "//tbody//tr//td[2]//span[text()='%s']//parent::td/following-sibling::td[1]/span";

    public void addClient(String providerName, String clientName, String accountCode, String cur, String subaccDebit, String subaccCredit, boolean save) {
        btnAddClient.click();
        inputInformation(1,providerName,clientName,accountCode,cur,subaccDebit,subaccCredit);
        if (save){
            ConfirmPopup confirmPopup = clickToSaveProvider(1);
            confirmPopup.btnYes.click();
            //wait for loading
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void inputInformation(int indexRow, String providerName,String clientName, String accountCode, String cur, String subaccDebit, String subaccCredit){
        DropDownBox ddProviderName = DropDownBox.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName("Provider"),indexRow,"select"));
        TextBox txtClientName = TextBox.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName("Client Name"),indexRow,"input"));
        TextBox txtAccountCode = TextBox.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName("Account Code"),indexRow,"input"));
        DropDownBox ddCur = DropDownBox.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName("CUR"),indexRow,"select"));
        DropDownBox ddSubaccDebit = DropDownBox.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName("Sub-account in Debit"),indexRow,"select"));
        DropDownBox ddSubaccCrebit = DropDownBox.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName("Sub-account in Credit"),indexRow,"select"));
        if (!providerName.isEmpty()){
            ddProviderName.selectByVisibleText(providerName);
            waitForUpload(1500);
        }
        if (!clientName.isEmpty()){
            txtClientName.sendKeys(clientName);
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
    public ConfirmPopup clickToSaveProvider(int indexRow){
        Button btnSave = Button.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName(""),indexRow,"em[contains(@class,'fa-save')]"));
        btnSave.click();
        return new ConfirmPopup();
    }
    public boolean isClientDisplay(String providerName, String clientName, String accountCode, String cur, String subaccDebit, String subaccCredit){
        int indexClient = tblClient.getRowIndexContainValue(clientName,tblClient.getColumnIndexByName("Client Name"),"span");
        if (indexClient == 0){
            System.out.println("FAILED! Client Name is not displayed");
            return false;
        }
        String providerEx = tblClient.getControlOfCell(1,tblClient.getColumnIndexByName("Provider"),indexClient,"span").getText();
        String accountCodeEx = tblClient.getControlOfCell(1,tblClient.getColumnIndexByName("Account Code"),indexClient,"span").getText();
        String curEx = tblClient.getControlOfCell(1,tblClient.getColumnIndexByName("CUR"),indexClient,"span").getText();
        String subaccDebitEx = tblClient.getControlOfCell(1,tblClient.getColumnIndexByName("Sub-account in Debit"),indexClient,"span").getText();
        String subaccCreditEx = tblClient.getControlOfCell(1,tblClient.getColumnIndexByName("Sub-account in Credit"),indexClient,"span").getText();
        if (!providerName.equals(providerEx)){
            System.out.println("FAILED! Provider displays incorrect");
            return false;
        }
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

    /**
     * @param columnName is not Client Name column
     * */
    public boolean isColumnSorted(String columnName){
        List<String> lstColumnValue = tblClient.getColumn(tblClient.getColumnIndexByName(columnName),50,true);
        List<String> sortedValues = new ArrayList<>(lstColumnValue);
        Collections.sort(sortedValues, String.CASE_INSENSITIVE_ORDER);
        return lstColumnValue.equals(sortedValues);
    }
    public void isDataTableSorted(){
        isColumnSorted("Provider");
        List<String> lstProvider = tblClient.getColumn(tblClient.getColumnIndexByName("Provider"),50,true);
        for (int i = 0; i < lstProvider.size()-1;i++){
            String currentProvider = lstProvider.get(i);
            String nextProvider = lstProvider.get(i+1);
            if (currentProvider.equals(nextProvider)){
                Assert.assertTrue(isClientSorted(currentProvider));
            }
        }
    }
    private boolean isClientSorted(String providerName){
        List<WebElement> clientNames = DriverManager.getDriver().findElements(By.xpath(String.format(clientXpathByProvider,providerName)));
        List<String> lstClient = new ArrayList<>();
        for (WebElement element : clientNames){
            lstClient.add(element.getText());
        }
        List<String> sortedClients = new ArrayList<>(lstClient);
        Collections.sort(sortedClients, String.CASE_INSENSITIVE_ORDER);
        return lstClient.equals(sortedClients);
    }

    public boolean isBtnEditCanClick(String clientName) {
        int indexProvider = tblClient.getRowIndexContainValue(clientName,tblClient.getColumnIndexByName("Client Name"),"span");
        Button btnEdit = Button.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName(""),indexProvider,"span[text()='Edit']/parent::span"));
        if (btnEdit.getAttribute("Class").contains("disable-btn")){
            System.out.println("Edit button disabled");
            return false;
        }
        return true;
    }
    public void clickToXIcon(int indexClient){
        Button btnX = Button.xpath(tblClient.getxPathOfCell(1,tblClient.getColumnIndexByName(""),indexClient,"em[contains(@class,'fa-times-circle')]"));
        btnX.click();
    }
    public void deleteClient(String clientName, boolean yes) throws InterruptedException {
        int indexClient = tblClient.getRowIndexContainValue(clientName,tblClient.getColumnIndexByName("Client Name"),"span");
        clickToXIcon(indexClient);
        if (yes){
            ConfirmPopup confirmPopup = new ConfirmPopup();
            //Wait for getting message
            waitForUpload(1000);
            Assert.assertEquals(confirmPopup.getContentMessage(), String.format(SBPConstants.FeedReport.CONFIRM_MES_DELETE_PROVIDER,clientName),"FAILED! Deleting Message displays incorrect");
            confirmPopup.btnYes.click();
            //wait for loading
            waitForUpload(3000);
        }
    }
}
