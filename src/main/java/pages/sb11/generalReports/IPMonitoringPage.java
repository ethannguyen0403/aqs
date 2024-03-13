package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.List;

public class IPMonitoringPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    DropDownBox ddBookie = DropDownBox.xpath("//div[text()='Bookie']/following-sibling::select");
    DropDownBox ddCountry = DropDownBox.xpath("//div[text()='Country']/following-sibling::select");
    TextBox txtIP = TextBox.xpath("//div[text()='IP']/following-sibling::input");
    TextBox txtAccountCode = TextBox.xpath("//div[text()='Account Code']/following-sibling::input");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Table tblData = Table.xpath("//table",7);
    public void filter(String bookie, String country, String ip, String accountCode, String fromDate, String toDate){
        if (!bookie.isEmpty()){
            ddBookie.selectByVisibleText(bookie);
        }
        if (!country.isEmpty()){
            ddCountry.selectByVisibleText(country);
        }
        if (!ip.isEmpty()){
            txtIP.sendKeys(ip);
        }
        if (!accountCode.isEmpty()){
            txtAccountCode.sendKeys(accountCode);
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

    public boolean isAccountDisplay(String colName, String... valueEx) {
        List<String> lstEx = new ArrayList<>();
        for (String value : valueEx){
            lstEx.add(value);
        }
        List<WebElement> lstBody = DriverManager.getDriver().findElements(By.xpath("//table//tbody"));
        for (int i = 1; i <= lstBody.size();i++){
            List<String> lstAcc = new ArrayList<>();
            List<WebElement> lstRow = DriverManager.getDriver().findElements(By.xpath("//table//tbody//tr"));
            int col = tblData.getColumnIndexByName(colName) - 1;
            for (int j = 1; j <= lstRow.size();j++){
                if (j == 1){
                    lstAcc.add(tblData.getControlOfCell(i,tblData.getColumnIndexByName(colName),j,null).getText());
                } else {
                    lstAcc.add(tblData.getControlOfCell(i,col,j,null).getText());
                }
            }
            if (lstAcc.containsAll(lstEx)){
                return true;
            }
        }
        return false;
    }
}
