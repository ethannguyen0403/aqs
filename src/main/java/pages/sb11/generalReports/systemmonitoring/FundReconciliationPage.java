package pages.sb11.generalReports.systemmonitoring;

import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.popup.ConfirmPopup;
import utils.sb11.ChartOfAccountUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class FundReconciliationPage extends WelcomePage {
    DropDownBox ddCompany = DropDownBox.xpath("//div[text()='Company Unit']//following-sibling::select");
    DropDownBox ddDetailType = DropDownBox.xpath("//div[text()='Detail Type']//following-sibling::select");
    DropDownBox ddSubAcc = DropDownBox.xpath("//div[text()='Sub-account']//following-sibling::select");
    public TextBox txtBeginDate = TextBox.xpath("//div[text()='Beginning Date']//following-sibling::div//input");
    public TextBox txtEndingDate = TextBox.xpath("//div[text()='Ending Date']//following-sibling::div//input");
    public DateTimePicker dtpBeginDate = DateTimePicker.xpath(txtBeginDate,"//bs-datepicker-container");
    public DateTimePicker dtpEndingDate = DateTimePicker.xpath(txtEndingDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Table tblEndingBalance = Table.xpath("//table[1]",1);
    public Table tblTodaySettle = Table.xpath("//table[contains(@class,'total-settlement')]",10);
    public Table tblSubAcc = Table.xpath("(//table)[2]",11);
    Label lblNoRecordFound = Label.xpath("//div[contains(text(),'No records found.')]");
    Label lblShowAmount5Decimals = Label.xpath("//span[text()='Show Amount in 5 Decimals']");
    public CheckBox cbShowAmount5Decimals = CheckBox.xpath("//span[text()='Show Amount in 5 Decimals']//preceding-sibling::input");
    public Label lblSubAccountName = Label.xpath("(//table[1]//thead//tr)[1]//th");
    public void filter(String companyUnit, String detailType,String subAcc, String beginDate, String endingDate){
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
    private List<String> getLstSubAccOfDetailtype(String detailTypeName){
        List<String> lstParent = ChartOfAccountUtils.getLstParentName(ChartOfAccountUtils.getLedgerGroupId(detailTypeName.split(" - ")[1]));
        List<String> lstSubAccEx = new ArrayList<>();
        for (String parentName : lstParent){
            List<String> lstSubAcc = ChartOfAccountUtils.getLstLedgerAccount(detailTypeName.split(" - ")[1],parentName);
            for (String subAcc : lstSubAcc){
                lstSubAccEx.add(subAcc.trim().replace("\t",""));
            }
        }
        return lstSubAccEx;
    }
    public void verifySubaccListDisplay() {
        for (String detailType : ddDetailType.getOptions()){
            ddDetailType.selectByVisibleText(detailType);
            waitSpinnerDisappeared();
            List<String> lstSubAccEx = getLstSubAccOfDetailtype(detailType);
            for (String subAcc : ddSubAcc.getOptions()){
                if (!lstSubAccEx.contains(subAcc)){
                    Assert.assertTrue(false,"FAILED! "+subAcc+" is not belong to "+detailType);
                }
            }
        }
    }

    public void verifySubaccListSorted() {
        for (String detailType : ddDetailType.getOptions()){
            ddDetailType.selectByVisibleText(detailType);
            waitSpinnerDisappeared();
            List<String> lstSubAccEx = ddSubAcc.getOptions();
            Collections.sort(lstSubAccEx);
            Assert.assertEquals(lstSubAccEx,ddSubAcc.getOptions(),"FAILED! Sub account dropdown display incorrect");
        }
    }
    public boolean isWithihRange(String fromDate, String toDate, String date){
        LocalDate fromDateEx = LocalDate.of(Integer.valueOf(fromDate.split("-")[0]),Integer.valueOf(fromDate.split("-")[1]),Integer.valueOf(fromDate.split("-")[2]));
        LocalDate toDateEx = LocalDate.of(Integer.valueOf(toDate.split("-")[0]),Integer.valueOf(toDate.split("-")[1]),Integer.valueOf(toDate.split("-")[2]));
        LocalDate dateAc = LocalDate.of(Integer.valueOf(date.split("-")[0]),Integer.valueOf(date.split("-")[1]),Integer.valueOf(date.split("-")[2]));
        return !dateAc.isBefore(fromDateEx) && !dateAc.isAfter(toDateEx);
    }
    public void verifyTransWithinRangeDate(int fromDate, int toDate) {
        String fromdate = DateUtils.getDate(fromDate,"yyyy-MM-dd", SBPConstants.GMT_7);
        String todate = DateUtils.getDate(toDate,"yyyy-MM-dd", SBPConstants.GMT_7);
        if (lblNoRecordFound.isDisplayed()){
            System.out.println("There is no transactions in 1 month");
        } else {
           List<String> lstTransDate =  tblSubAcc.getColumn(tblSubAcc.getColumnIndexByName("Transaction Date"),true);
           for (int i = 1; i < lstTransDate.size()-1;i++){
               if (!isWithihRange(fromdate,todate,lstTransDate.get(i))){
                   Assert.assertTrue(false,"FAILED! "+lstTransDate.get(i)+" is not in range date");
               }
           }
        }
    }

    public void verifyShowAmountIn5DecimalsDisplay() {
        Assert.assertTrue(lblShowAmount5Decimals.isDisplayed(),"FAILED! Show Amount in 5 Decimals label is not displayed");
        Assert.assertTrue(!cbShowAmount5Decimals.isSelected(),"FAILED! Show Amount in 5 Decimals label is not displayed");
    }
    public boolean isDecimalPlacesDisplay(int decimals, String number){
        String regex = String.format("\\d+\\.\\d{%d}",decimals);
        return Pattern.matches(regex,number);
    }
    public void verifyDecimalsPlaces(int decimals) {
        //Verify Debit column
        List<String> lstDebitCol = tblSubAcc.getColumn(tblSubAcc.getColumnIndexByName("Debit"),false);
        for (String number : lstDebitCol){
            if (!number.isEmpty()){
                Assert.assertTrue(isDecimalPlacesDisplay(decimals,number.trim()),"FAILED! Debit "+number+" has not "+decimals+" decimal places");
            }
        }
        //Verify Credit column
        List<String> lstCreditCol = tblSubAcc.getColumn(tblSubAcc.getColumnIndexByName("Credit"),false);
        for (String number : lstCreditCol){
            if (!number.isEmpty()){
                Assert.assertTrue(isDecimalPlacesDisplay(decimals,number.trim()),"FAILED! Credit "+number+" has not "+decimals+" decimal places");
            }
        }
        //Verify Running Balance
        List<String> lstRunningBalaceCol = tblSubAcc.getColumn(tblSubAcc.getColumnIndexByName("Running Balance"),false);
        for (String number : lstRunningBalaceCol){
            if (!number.isEmpty()){
                Assert.assertTrue(isDecimalPlacesDisplay(decimals,number.trim()),"FAILED! Running Balance "+number+" has not "+decimals+" decimal places");
            }
        }
    }

}
