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
    public TextBox txtTransDate = TextBox.name("fromDate");
    public DateTimePicker dtpTransDate = DateTimePicker.xpath(txtTransDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Table tblData = Table.xpath("//table[contains(@class,'table-custom')]",10);
    public Table tblHeader = Table.xpath("(//div[@class='ng-star-inserted']//table[contains(@class,'fbody')])[1]",10);
    public Table tblTodaySettle = Table.xpath("//table[contains(@class,'total-settlement')]",10);
    public void filter(String companyUnit, String detailType, String transDate){
        if (!companyUnit.isEmpty()){
            ddCompany.selectByVisibleText(companyUnit);
        }
        if (!detailType.isEmpty()){
            ddDetailType.selectByVisibleText(detailType);
        }
        if (!transDate.isEmpty()){
            dtpTransDate.selectDate(transDate,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public String getValueByDesc(String desc, int indexColum){
        int indexRow = tblData.getRowIndexContainValue(desc,tblHeader.getColumnIndexByName("Description")-1,"span");
        return Label.xpath(tblData.getxPathOfCell(1,indexColum,indexRow,"span")).getText();
    }
    public void tickConfirmAuthorise(String desc, String colName){
        int indexRow = tblData.getRowIndexContainValue(desc,tblHeader.getColumnIndexByName("Description")-1,"span");
        CheckBox cbConfirm = CheckBox.xpath(tblData.getxPathOfCell(1,tblHeader.getColumnIndexByName(colName)-1,indexRow,"input"));
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
    public String getSumDebitCredit(String debitcredit){
        List<String> lstData = tblData.getColumn(tblHeader.getColumnIndexByName(debitcredit)-1,50,true);
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
        List<String> lstAuthor = tblData.getColumn(tblHeader.getColumnIndexByName("Authorised By")-1,50,true);
        List<String> lstDebit = tblData.getColumn(tblHeader.getColumnIndexByName("Debit")-1,50,true);
        for (int i = 2; i < lstAuthor.size()-1;i++){
            if (lstAuthor.get(i).equals(authoriseName)){
                sum = sum + Double.valueOf(lstDebit.get(i));
            }
        }
        return String.format("%.2f",sum);
    }
}
