package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

public class IncomeStatementPage extends WelcomePage {

    protected DropDownBox ddCompanyUnit = DropDownBox.xpath("//*[contains(text(), 'Company Unit')]/..//select");
    protected DropDownBox ddFinancialYear = DropDownBox.xpath("//*[contains(text(), 'Financial Year')]/..//select");
    protected DropDownBox ddMonth = DropDownBox.xpath("//*[contains(text(), 'Month')]/..//select");
    protected DropDownBox ddReport = DropDownBox.xpath("//*[contains(text(), 'Report')]/..//select");
    protected Button btnShow = Button.xpath("//button[contains(@class, 'btn-show')]");
    protected Label lblNetProfit = Label.xpath("//*[contains(text(), 'Net Profit (Loss)')]/following-sibling::td[1]");

    private String netProfitLoss ;

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

    public void setNetProfitLoss() {
         netProfitLoss = lblNetProfit.getText();
    }

    public String getNetProfitLoss(){
        setNetProfitLoss();
        return netProfitLoss;
    }

}
