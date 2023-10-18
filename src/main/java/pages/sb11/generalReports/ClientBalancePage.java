package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.clientBalance.ClientBalanceDetailPopup;


public class ClientBalancePage extends WelcomePage {
    public DropDownBox ddViewBy = DropDownBox.xpath("//div[contains(text(),'View By')]//following::select[1]");
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]//following::select[1]");
    TextBox txtDate = TextBox.xpath("//div[contains(text(),'Date')]/following::input[1]");
    DateTimePicker dtpDate = DateTimePicker.xpath(txtDate, "//bs-datepicker-container");
    public DropDownBox ddShowTotal = DropDownBox.xpath("//div[contains(text(),'Show Total')]//following::select[1]");
    protected Button btnShow = Button.name("btnShow");

    int totalCol = 5;
    int colClientName = 2;
    public Table tblClientBalance = Table.xpath("//table[@id='client-balance-summary']", totalCol);


    public void filter(String viewBy, String companyUnit, String financialYear, String date) {
        if (!viewBy.isEmpty())
            ddViewBy.selectByVisibleText(viewBy);
        if (!companyUnit.isEmpty())
            ddCompanyUnit.selectByVisibleText(companyUnit);
        waitSpinnerDisappeared();
        if (!financialYear.isEmpty())
            ddFinancialYear.selectByVisibleText(financialYear);
        if (!date.isEmpty())
            dtpDate.selectDate(date, "dd/MM/yyyy");
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public ClientBalanceDetailPopup goToClientDetail(int index){
        tblClientBalance.getControlOfCellSPP(1,colClientName,index,null).click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new ClientBalanceDetailPopup();
    }


}
