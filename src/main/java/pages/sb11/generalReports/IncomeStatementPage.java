package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

public class IncomeStatementPage extends WelcomePage {

    protected DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()= 'Company Unit']/parent::div//select");
    protected DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()= 'Financial Year']/parent::div//select");
    protected DropDownBox ddMonth = DropDownBox.xpath("//div[text()= 'Month']/parent::div//select");
    protected DropDownBox ddReport = DropDownBox.xpath("//div[text()= 'Report']/parent::div//select");
    protected Button btnShow = Button.xpath("//button[contains(@class, 'btn-show')]");
    protected Label lblNetProfit = Label.xpath("//td[text()='Net Profit (Loss)']/following-sibling::td[1]");


    public void filterIncomeReport(String companyUnit, String financialYears, String month, String report) {
        if (!companyUnit.equals("")) {
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if (!financialYears.equals("")) {
            ddFinancialYear.selectByVisibleText(financialYears);
        }
        if (!month.equals("")) {
            ddMonth.selectByVisibleText(month);
        }
        if (!report.equals("")) {
            ddReport.selectByVisibleText(report);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public String getNetProfitLoss(){
        return lblNetProfit.getText();
    }

}
