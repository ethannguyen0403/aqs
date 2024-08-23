package pages.sb11.trading;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.List;

public class KeyClientsReportsPage extends WelcomePage {
    public DropDownBox ddGroup = DropDownBox.xpath("//div[text()='Group']/parent::div/select");
    public DropDownBox ddSport = DropDownBox.xpath("//div[text()='Sport']/parent::div/select");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Label lblAmountNote = Label.xpath("//label[text()='Amounts are shown in [HKD]']");
    public Label lblAlertThan1Month = Label.xpath("//alert//span[contains(text(),'Date range should not be more than 1 month')]");
    int totalColumn = 8;
    public Table tblGroup = Table.xpath("//table",totalColumn);
    public void filter(String group,String sport,String fromDate, String toDate) {
        if (!group.isEmpty()){
            ddGroup.selectByVisibleText(group);
        }
        if (!sport.isEmpty()){
            ddSport.selectByVisibleText(sport);
        }
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public Label getTotal(int headerColumn){
        int totalIndex = tblGroup.getRowIndexContainValue("Total",1,null);
        Label lblTotal = Label.xpath(tblGroup.getxPathOfCell(1,headerColumn-1,totalIndex,null));
        return lblTotal;
    }
    public List<String> getLstTableHeader(){
        ArrayList<String> lstExpected = new ArrayList<>();
        ArrayList<String> lstTableHeader =  tblGroup.getHeaderNameOfRows();
        for (int i = 1; i < lstTableHeader.size();i++){
            lstExpected.add(lstTableHeader.get(i));
        }
        return lstExpected;
    }

    public void verifyCFToDate(String sport) {
        List<String> lstCFToDate = tblGroup.getColumn(tblGroup.getColumnIndexByName("C/F. ToDate")-1,20,false);
        List<String> lstFinalResult = getlstFinalResult(sport);
        for (int i = 0; i < lstCFToDate.size()-1;i++){
            if (i == 0){
                Assert.assertEquals(lstCFToDate.get(i).replace(",","").trim(),lstFinalResult.get(0),String.format("FAILED! C/F. ToDate %s display incorrect",i+1));
            } else {
                Assert.assertEquals(lstCFToDate.get(i).replace(",","").trim(),String.format("%.2f",Double.valueOf(lstCFToDate.get(i-1).replace(",","").trim()) + Double.valueOf(lstFinalResult.get(i))),
                        String.format("FAILED! C/F. ToDate %s display incorrect",i+1));
            }
        }
    }
    public List<String> getlstFinalResult (String sport){
        List<String> lstFinalResult = new ArrayList<>();
        switch (sport){
            case "All":
                lstFinalResult = tblGroup.getColumn(tblGroup.getColumnIndexByName("Total")-1,20,false);
                break;
            default:
                lstFinalResult = tblGroup.getColumn(tblGroup.getColumnIndexByName("Final Result")-1,20,false);
        }
        List<String> lstExpected = new ArrayList<>();
        for (int i = 0; i < lstFinalResult.size()-1;i++){
            if (lstFinalResult.get(i).isEmpty()){
                lstExpected.add(i,String.format("%.2f", 0.00));
            } else {
                lstExpected.add(i,lstFinalResult.get(i).replace(",","").trim());
            }
        }
        return lstExpected;
    }

    public void verifyTotalByColumnName(String columnName) {
        List<String> lstTotal = tblGroup.getColumn(tblGroup.getColumnIndexByName(columnName)-1,20,false);
        Double totalExpect = 0.00;
        for (int i = 0; i < lstTotal.size()-1;i++){
            if (!lstTotal.get(i).isEmpty()){
                totalExpect = totalExpect + Double.valueOf(lstTotal.get(i).replace(",","").trim());
            }
        }
        Assert.assertEquals(Double.valueOf(getTotal(tblGroup.getColumnIndexByName(columnName)-1).getText().replace(",","")),totalExpect,0.01,
                String.format("FAILED! Total at %s column display incorrect",columnName));
    }
}
