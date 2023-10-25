package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class StockHoldersEquityPage extends WelcomePage {
    DropDownBox ddbCompany = DropDownBox.xpath("//div[contains(text(),'Company')]/following::select[1]");
    DropDownBox ddbFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/following::select[1]");
    Button btnShow = Button.name("btnShow");
    public Label lblAmountAreShow = Label.xpath("//app-aqs-bet//label[contains(text(), 'Amounts are shown in')]");

    public void filter(String companyUnit, String financialYears) {
        if (!companyUnit.equals("")) {
            ddbCompany.selectByVisibleText(companyUnit);
        }
        if (!financialYears.equals("")) {
            ddbFinancialYear.selectByVisibleText(financialYears);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
}
