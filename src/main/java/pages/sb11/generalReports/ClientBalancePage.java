package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.clientbalance.ClientBalanceDetailPage;

public class ClientBalancePage extends WelcomePage {
    DropDownBox ddViewBy = DropDownBox.xpath("//div[contains(text(),'View By')]/parent::div//select");
    DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]/parent::div//select");
    DropDownBox ddFinancial = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/parent::div//select");
    TextBox txtDate = TextBox.name("fromDate");
    DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container");
    TextBox txtSMCode = TextBox.xpath("//div[contains(text(),'SM Code')]/parent::div//input");
    public DropDownBox ddShowTotalIn = DropDownBox.xpath("//div[contains(text(),'Show Total In')]/parent::div//select");
    Button btnShow = Button.xpath("//button[text()='Show']");
    public int colTotalBalance = 5;
    public int colClient = 2;
    public Table tblBalance = Table.xpath("//table[@id='client-balance-summary']",9);
    public void filter(String viewBy, String companyUnit, String financialYear, String date, String smCode, String showTotalIn) {
        if(!viewBy.isEmpty()){
            ddViewBy.selectByVisibleText(viewBy);
        }
        if(!companyUnit.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if(!financialYear.isEmpty()){
            ddFinancial.selectByVisibleText(financialYear);
        }
        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
        }
        if(!smCode.isEmpty()){
            txtSMCode.sendKeys(smCode);
        }
        if(!showTotalIn.isEmpty()){
            ddShowTotalIn.selectByVisibleText(showTotalIn);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public ClientBalanceDetailPage openBalanceDetailByClient(String clientName){
        filter("","","","",clientName.split("-")[0].trim(),"");
        tblBalance.getControlOfCellSPP(1,colClient,1,null).click();
        DriverManager.getDriver().switchToWindow();
        return new ClientBalanceDetailPage();
    }
}
