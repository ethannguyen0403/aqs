package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.bookieBalance.BookieBalanceDetailPopup;

public class BookieBalancePage extends WelcomePage {
    public DropDownBox ddCompanyUnit = com.paltech.element.common.DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddFinancialYear = com.paltech.element.common.DropDownBox.xpath("//div[contains(text(),'Financial Year')]//following::select[1]");
    TextBox txtDate = TextBox.xpath("//div[contains(text(),'Date')]/following::input[1]");
    DateTimePicker dtpDate = DateTimePicker.xpath(txtDate, "//bs-datepicker-container");
    public DropDownBox ddShowTotal = DropDownBox.xpath("//div[contains(text(),'Show Total')]//following::select[1]");
    protected Button btnShow = Button.name("btnShow");

    public int colBookieName = 2;
    public Table tblBookieBalance = Table.xpath("//div[@id='bookie-balance-summary']//table[not(contains(@style,' '))]", 3);

    public void filter(String companyUnit, String financialYear, String date){
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

    public BookieBalanceDetailPopup goToBookieDetail(int index){
        tblBookieBalance.getControlOfCell(1,colBookieName,index,null).click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new BookieBalanceDetailPopup();
    }

}
