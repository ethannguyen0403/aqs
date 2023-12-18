package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import controls.Table;
import pages.sb11.WelcomePage;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class TrialBalancePage extends WelcomePage {
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']/parent::div//select");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()='Financial Year']/parent::div//select");
    public DropDownBox ddMonth = DropDownBox.xpath("//div[text()='Month']/parent::div//select");
    public DropDownBox ddReport = DropDownBox.xpath("//div[text()='Report']/parent::div//select");
    public Table tblTrial = Table.xpath("//app-trial-balance//table", 7);
    public int colCode = 1;
    public int colDeCurrentMonth = 6;
    public int colCreCurrentMonth = 7;
    public Button btnShow = Button.name("btnShow");

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

    public double getAmountTrialTable(int rowIndex, int colIndex){
        Label amount = Label.xpath(tblTrial.getxPathOfCell(1, colIndex, rowIndex, null));
        return Double.valueOf(amount.getText().trim());
    }

    public int findRowIndexOfParentAccount(String accountCode){
        int i = 1;
        Label lblAccountCode;
        while (true) {
            lblAccountCode = Label.xpath(tblTrial.getxPathOfCell(1, colCode, i, null));
            if (!lblAccountCode.isDisplayed()) {
                System.out.println("Can NOT found the account code  " + accountCode + " in the table");
                return 0;
            }
            if (lblAccountCode.getText().contains(accountCode)) {
                System.out.println("Found the the account code " + accountCode + " in the table");
                return i;
            }
            i++;
        }
    }

    /** Gets the month-of-year field from 1 to 12 to correct format of Filter Month 'year - monthName'
     * */
    public String convertMonthToFilterMonth(int month, int year){
         Month monthM = Month.of(month);
        return String.format("%s - %s",year , monthM.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
    }

}
