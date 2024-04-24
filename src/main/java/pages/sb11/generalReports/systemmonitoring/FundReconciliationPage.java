package pages.sb11.generalReports.systemmonitoring;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;

import java.util.List;

public class FundReconciliationPage extends WelcomePage {
    DropDownBox ddCompany = DropDownBox.xpath("//div[text()='Company Unit']//following-sibling::select");
    DropDownBox ddDetailType = DropDownBox.xpath("//div[text()='Detail Type']//following-sibling::select");
    DropDownBox ddSubAcc = DropDownBox.xpath("//div[text()='Sub-account']//following-sibling::select");
    public TextBox txtBeginDate = TextBox.xpath("//div[text()='Beginning Date']//following-sibling::div//input");
    public TextBox txtEndingDate = TextBox.xpath("//div[text()='Ending Date']//following-sibling::div//input");
    public DateTimePicker dtpBeginDate = DateTimePicker.xpath(txtBeginDate,"//bs-datepicker-container");
    public DateTimePicker dtpEndingDate = DateTimePicker.xpath(txtEndingDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Table tblData = Table.xpath("//table[contains(@class,'table-custom')]",10);
    public Table tblTodaySettle = Table.xpath("//table[contains(@class,'total-settlement')]",10);
    public Table tblSubAcc = Table.xpath("(//table)[2]",11);
    public void filter(String companyUnit, String detailType,String subAcc, String beginDate, String endingDate){
        if (!companyUnit.isEmpty()){
            ddCompany.selectByVisibleText(companyUnit);
        }
        if (!detailType.isEmpty()){
            ddDetailType.selectByVisibleContainsText(detailType);
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
        int indexCol = tblSubAcc.getColumnIndexByName(colName);
        int indexRow = tblSubAcc.getRowIndexContainValue(desc,tblSubAcc.getColumnIndexByName("Description"),"span");
        return Label.xpath(tblSubAcc.getxPathOfCell(1,indexCol,indexRow,"span")).getText();
    }
    public void tickConfirmAuthorise(String desc, String colName){
        int indexRow = tblSubAcc.getRowIndexContainValue(desc,tblSubAcc.getColumnIndexByName("Description"),"span");
        CheckBox cbConfirm = CheckBox.xpath(tblSubAcc.getxPathOfCell(1,tblSubAcc.getColumnIndexByName(colName),indexRow,"input"));
        if (cbConfirm.isEnabled()){
            cbConfirm.click();
            waitSpinnerDisappeared();
            ConfirmPopup confirmPopup = new ConfirmPopup();
            confirmPopup.btnYes.click();
            waitSpinnerDisappeared();
        } else {
            System.out.println("Check box of "+desc+" is ticked");
        }
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
}
