package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.utils.DoubleUtils;
import controls.Table;
import pages.sb11.WelcomePage;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    String totalBalanceXpath = "//td[text()='Total Balance']/following-sibling::td[%s]//span";
    public int previousMonthDif = 1;
    public int curMonthDif = 3;
    String differenceXpath = "//td[text()='Difference']//following-sibling::td[%s]//span";

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

    public String getAmountTrialTable(int rowIndex, int colIndex){
        Label amount = Label.xpath(tblTrial.getxPathOfCell(1, colIndex, rowIndex, "span"));
        return amount.getText().trim().replace(",","");
    }

    public int findRowIndexOfParentAccount(String accountCode){
        int i = 1;
        Label lblAccountCode;
        while (true) {
            lblAccountCode = Label.xpath(tblTrial.getxPathOfCell(1, colCode, i, "span"));
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
    public String getAmountValue(String accountCode, int colIndex){
        int rowIndex = findRowIndexOfParentAccount(accountCode);
        return getAmountTrialTable(rowIndex,colIndex);
    }
    public double getTotalBalance(boolean currentMonth, boolean debit){
        int indexCol = -1;
        if (currentMonth){
            if (debit){
                indexCol = 4;
            } else {
                indexCol = 5;
            }
        } else {
            if (debit){
                indexCol = 1;
            } else {
                indexCol = 2;
            }
        }
        return Double.valueOf(Label.xpath(String.format(totalBalanceXpath,indexCol)).getText().trim().replace(",",""));
    }
    public double getSumValueOfCol(int colIndex){
        double sum = 0.01;
        int sumCol = tblTrial.getNumberOfRows(false,true);
        for (int i = 1; i <= sumCol-2; i++){
            sum = DoubleUtils.roundEvenWithTwoPlaces(sum + DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getAmountTrialTable(i,colIndex))));
        }
        return sum;
    }
    public double getDifferenceValue(int indexDif){
        return Double.valueOf(Label.xpath(String.format(differenceXpath,indexDif)).getText().trim().replace(",",""));
    }

    public boolean verifyTotalBalance(int colIndex, boolean currentMonth, boolean debit) {
        double sumAc = getSumValueOfCol(colIndex);
        double sumEx = getTotalBalance(currentMonth,debit);
        if (sumAc==sumEx){
            return true;
        } else if (DoubleUtils.roundWithTwoPlaces(RoundingMode.HALF_EVEN,sumAc-sumEx) >= 0.01 || DoubleUtils.roundWithTwoPlaces(RoundingMode.HALF_EVEN,sumEx-sumAc) == 0.01){
            return true;
        }
        return false;
    }
    public boolean verifyDifferenceValue(boolean currentMonth){
        double difAc = Math.abs(DoubleUtils.roundUpWithTwoPlaces(getTotalBalance(currentMonth,true) - getTotalBalance(currentMonth,false)));
        int colIndex;
        if (currentMonth){
            colIndex = 3;
        } else {
            colIndex = 1;
        }
        double difEx = getDifferenceValue(colIndex);
        if (difAc==difEx){
            return true;
        } else if (DoubleUtils.roundWithTwoPlaces(RoundingMode.HALF_EVEN,difAc-difEx) >= 0.01 || DoubleUtils.roundWithTwoPlaces(RoundingMode.HALF_EVEN,difEx-difAc) == 0.01){
            return true;
        }
        return false;
    }
}
