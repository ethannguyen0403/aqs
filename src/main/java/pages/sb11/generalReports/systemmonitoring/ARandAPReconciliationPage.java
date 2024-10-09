package pages.sb11.generalReports.systemmonitoring;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;
import utils.sb11.accounting.ChartOfAccountUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ARandAPReconciliationPage extends WelcomePage {
    public DropDownBox ddCompany = DropDownBox.xpath("//div[text()='Company Unit']//following-sibling::select");
    public DropDownBox ddDetailType = DropDownBox.xpath("//div[text()='Detail Type']//following-sibling::select");
    DropDownBox ddSubAcc = DropDownBox.xpath("//div[text()='Sub-account']//following-sibling::select");
    public TextBox txtBeginDate = TextBox.xpath("//div[text()='Beginning Date']//following-sibling::div//input");
    public TextBox txtEndingDate = TextBox.xpath("//div[text()='Ending Date']//following-sibling::div//input");
    public DateTimePicker dtpBeginDate = DateTimePicker.xpath(txtBeginDate,"//bs-datepicker-container");
    public DateTimePicker dtpEndingDate = DateTimePicker.xpath(txtEndingDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Table tblData = Table.xpath("//table[contains(@class,'table-custom')]",10);
    public Table tblHeader = Table.xpath("(//div[@class='ng-star-inserted']//table[contains(@class,'fbody')])[1]",10);
    public Table tblTodaySettle = Table.xpath("//table[contains(@class,'total-settlement')]",10);
    public Table tblSubAcc = Table.xpath("(//table)[2]",11);

    public void filter(String companyUnit, String detailType, String subAcc, String beginDate, String endingDate){
        if (!companyUnit.isEmpty()){
            ddCompany.selectByVisibleText(companyUnit);
        }
        if (!detailType.isEmpty()){
            ddDetailType.selectByVisibleContainsText(detailType);
            waitSpinnerDisappeared();
        }
        if (!subAcc.isEmpty()){
            ddSubAcc.selectByVisibleContainsText(subAcc);
        }
        if (!beginDate.isEmpty()){
            dtpBeginDate.selectDate(beginDate,"dd/MM/yyyy");
        }
        if (!endingDate.isEmpty()){
            dtpEndingDate.selectDate(endingDate,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public String getValueByDesc(String desc, String colName){
        return tblSubAcc.getControlBasedValueOfDifferentColumnOnRow(desc,1,tblSubAcc.getColumnIndexByName("Description"),1,"span",
                tblSubAcc.getColumnIndexByName(colName),"span",true,false).getText();
    }
    public void tickConfirmAuthorise(String desc, String colName){
        int indexRow = tblSubAcc.getRowIndexContainValue(desc,tblSubAcc.getColumnIndexByName("Description"),"span");
        CheckBox cbConfirm = CheckBox.xpath(tblSubAcc.getxPathOfCell(1,tblSubAcc.getColumnIndexByName(colName),indexRow,"input"));
        if (cbConfirm.isEnabled()){
            cbConfirm.click();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ConfirmPopup confirmPopup = new ConfirmPopup();
            confirmPopup.btnYes.click();
            waitSpinnerDisappeared();
        } else {
            System.out.println("Check box of "+desc+" is ticked");
        }
    }
    public boolean isCheckCanTick(String desc, String colName){
        int indexRow = tblSubAcc.getRowIndexContainValue(desc,tblSubAcc.getColumnIndexByName("Description"),"span");
        CheckBox cbConfirm = CheckBox.xpath(tblSubAcc.getxPathOfCell(1,tblSubAcc.getColumnIndexByName(colName),indexRow,"input"));
        return cbConfirm.isEnabled();
    }
    public String getSumDebitCredit(String debitcredit){
        List<String> lstData = tblSubAcc.getColumn(tblSubAcc.getColumnIndexByName(debitcredit),50,true);
        double sum = 0.00;
        for (int i = 1; i < lstData.size()-1;i++){
            if (!lstData.get(i).isEmpty()){
                sum = sum + Double.valueOf(lstData.get(i));
            }
        }
        return String.format("%.2f",sum);
    }
    public String getSumAuthorizedTrans(String authoriseName){
        double sum = 0.00;
        List<String> lstAuthor = tblSubAcc.getColumn(tblSubAcc.getColumnIndexByName("Authorised By"),50,true);
        List<String> lstDebit = tblSubAcc.getColumn(tblSubAcc.getColumnIndexByName("Debit"),50,true);
        for (int i = 2; i < lstAuthor.size()-1;i++){
            if (lstAuthor.get(i).equals(authoriseName)){
                sum = sum + Double.valueOf(lstDebit.get(i));
            }
        }
        return String.format("%.2f",sum);
    }
    public List<String> getLstDetailType(){
        List<String> lstEx = new ArrayList<>();
        List<WebElement> lstDetail = DriverManager.getDriver().findElements(By.xpath("//th[contains(@class,'title-ledger-group')]//span"));
        for (int i = 0; i< lstDetail.size();i++) {
            lstEx.add(lstDetail.get(i).getText());
        }
        return lstEx;
    }
    public List<String> getLstParentAccount(String detailType){
        List<String> lstEx = new ArrayList<>();
        List<WebElement> lstParentAcc = DriverManager.getDriver().findElements(By.xpath(
                String.format("(//span[contains(text(),'%s')]//ancestor::table)[1]/following-sibling::div//th[contains(@class,'title-ledger-parent')]//span",detailType)));
        for (int i = 0; i< lstParentAcc.size();i++) {
            lstEx.add(lstParentAcc.get(i).getText());
        }
        return lstEx;
    }
    public List<String> getLstSubAccount(String detailType, String parentAcc){
        List<String> lstEx = new ArrayList<>();
        List<WebElement> lstSubAcc = DriverManager.getDriver().findElements(By.xpath(
                String.format("(//span[contains(text(),'%s')]//ancestor::table)[1]/following-sibling::div//th[contains(@class,'title-ledger-parent')]//span[text()='%s']//ancestor::table//following-sibling::div//thead//span"
                        ,detailType,parentAcc)));
        for (int i = 0; i< lstSubAcc.size();i++) {
            lstEx.add(lstSubAcc.get(i).getText());
        }
        return lstEx;
    }
    public void verifyDetailTypeSortByAsc(List<String> lstSort){
        List<String> lstSorted = ChartOfAccountUtils.getLstLedgerGroup();
        List<String> lstEx = new ArrayList<>();
        for (String string : lstSorted){
            if (lstSort.contains(string)){
                lstEx.add(string);
            }
        }
        Assert.assertEquals(lstSort,lstEx,"FAILED! The list is not sorted by Ascending");
    }
    public void isSortByAsc(List<String> lstSort){
        List<String> lstSorted = new ArrayList<>();
        for (String string : lstSort){
            lstSorted.add(string);
        }
        Collections.sort(lstSorted);
        Assert.assertEquals(lstSort,lstSorted,"FAILED! The list is not sorted by Ascending");
    }
}
