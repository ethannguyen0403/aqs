package pages.sb11.trading;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

public class KeyClientsReportsPage extends WelcomePage {
    public DropDownBox ddGroup = DropDownBox.xpath("//div[text()='Group']/parent::div/select");
    public DropDownBox ddSport = DropDownBox.xpath("//div[text()='Sport']/parent::div/select");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[text()='Show']");
    public Label lblAmountNote = Label.xpath("//label[text()='Amounts are shown in [HKD]']");
    public Label lblAlertThan1Month = Label.xpath("//alert//span[contains(text(),'Date range should not be more than 1 month')]");
    int totalColumn = 8;
   public int colCricket = 3;
   public int colOther = 6;
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
}
