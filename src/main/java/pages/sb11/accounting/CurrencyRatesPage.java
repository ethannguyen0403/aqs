package pages.sb11.accounting;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyRatesPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//label[contains(text(),'Company Unit')]//following::select[1]");
    public TextBox txtDate = TextBox.name("fromDate");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public Label lblDate = Label.xpath("//div[contains(text(),'Date')]");
    public Button btnExport = Button.xpath("//app-currency-rates//button[contains(text(),'Export To Excel')]");

    public int colCur = 4;
    int colOPRate= 5;
    public Table tblCurRate = Table.xpath("//app-currency-rates//table",5);

    public void filterRate (String date){
        if(!date.isEmpty())
            dtpDate.selectDate(date,"dd/MM/yyyy");
    }

    public void filterRate (String companyName, String date){
        if(!companyName.isEmpty())
            ddpCompanyUnit.selectByVisibleText(companyName);
        if(!date.isEmpty())
            dtpDate.selectDate(date,"dd/MM/yyyy");
    }

    public void exportToExcel(){
        btnExport.scrollToTop();
        btnExport.click();
    }

    public Map<String, String> getEntriesCurList(List<String> currencyList){
        Map<String, String> entriesList = new HashMap<>();
        for(int i=0; i< currencyList.size(); i++){
            int curValueIndex = findRowCurIndex(currencyList.get(i));
            String curValue = Label.xpath(tblCurRate.getxPathOfCell(1, colOPRate, curValueIndex, null)).getText().trim();
            entriesList.put(currencyList.get(i), curValue);
        }
        return entriesList;
    }

    public int findRowCurIndex(String curName){
        Label lbl;
        int i = 1;
        while (true) {
            lbl = Label.xpath(tblCurRate.getxPathOfCell(1, colCur, i, null));
            if (!lbl.isDisplayed()) {
                System.out.println(String.format("Not found the value: %s in the column in the table", curName));
                return -1;
            }
            lbl.getText().trim();
            if (lbl.getText().equals(curName)) {
                System.out.println(String.format("Found the value: %s in the column in the table", curName));
                return i;
            }
            i = i + 1;
        }
    }

}
