package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import controls.Table;
import pages.sb11.WelcomePage;

public class TrialBalancePage extends WelcomePage {
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']/parent::div//select");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()='Financial Year']/parent::div//select");
    public DropDownBox ddMonth = DropDownBox.xpath("//div[text()='Month']/parent::div//select");
    public DropDownBox ddReport = DropDownBox.xpath("//div[text()='Report']/parent::div//select");
    public Table tblTrial = Table.xpath("//app-trial-balance//table", 7);
    Button btnShow = Button.name("btnShow");

    public void filter(String companyUnit, String financialYear, String month, String reportType) {
        if (!companyUnit.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if (!financialYear.isEmpty()){
            ddFinancialYear.selectByVisibleText(financialYear);
        }
        if (!month.isEmpty()){
            ddMonth.selectByVisibleText(month);
        }
        if (!reportType.isEmpty()){
            ddReport.selectByVisibleText(reportType);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

}
