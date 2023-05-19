package pages.sb11.accounting;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import pages.sb11.WelcomePage;

public class CurrencyRatesPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public TextBox txtDate = TextBox.name("fromDate");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public Label lblDate = Label.xpath("//div[contains(text(),'Date')]");
    public Button btnExport = Button.xpath("//app-currency-rates//button[contains(text(),'Export To Excel')]");

    public Table tblCurRate = Table.xpath("//app-currency-rates//table",5);

    public void filterRate (String date){
        if(!date.isEmpty())
            dtpDate.selectDate(date,"dd/MM/yyyy");
    }

    public void exportToExcel(){
        btnExport.scrollToTop();
        btnExport.click();
    }
}
