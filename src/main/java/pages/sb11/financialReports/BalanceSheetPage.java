package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class BalanceSheetPage extends WelcomePage {
    Label lblTitle = Label.xpath("//app-balance-sheet//div[contains(@class,'card-header main-box-header')]/span");
    DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']/parent::div//select");
    DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()='Financial Year']/parent::div//select");
    DropDownBox ddMonth = DropDownBox.xpath("//div[text()='Month']/parent::div//select");
    DropDownBox ddReport = DropDownBox.xpath("//div[text()='Report']/parent::div//select");
    Button btnShow = Button.name("btnShow");

    String titleSectionXpath = "//div[@class='col-6']//span[text()='%s']";
    @Override
    public String getTitlePage() { return lblTitle.getText().trim();}

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
    public boolean isSectionDisplayCorrect(String sectionName){
        return Label.xpath(String.format(titleSectionXpath,sectionName)).isDisplayed();
    }
}
