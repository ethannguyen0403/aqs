package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class IncomeStatementPage extends WelcomePage {

    protected DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()= 'Company Unit']/parent::div//select");
    protected DropDownBox ddFinancialYear = DropDownBox.xpath("//div[text()= 'Financial Year']/parent::div//select");
    protected DropDownBox ddMonth = DropDownBox.xpath("//div[text()= 'Month']/parent::div//select");
    protected DropDownBox ddReport = DropDownBox.xpath("//div[text()= 'Report']/parent::div//select");
    public Button btnExportToExcel = Button.xpath("//button//i[contains(@class, 'fa-file-excel')]");
    public Button btnExportToPDF = Button.xpath("//button//em[contains(@class, 'fa-file-pdf')]");
    protected Button btnShow = Button.xpath("//button[contains(@class, 'btn-show')]");
    protected Label lblNetProfit = Label.xpath("//td[text()='Net Profit (Loss)']/following-sibling::td[1]");
    public Label lblAmountAreShow = Label.xpath("//app-income-statement//label[contains(text(), 'Amounts are shown in')]");
    public Table tblIncome = Table.xpath("//app-income-statement//table", 3);
    public int colCode = 1;


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

    public boolean verifyCodeStartingInRange(List<String> accountCodeList, int startRange, int endRange) {
        if (accountCodeList == null || accountCodeList.isEmpty()) return false;
        for (String accountCode : accountCodeList) {
            int firstChartCode = Integer.valueOf(accountCode.split("\\.")[0].trim());
            if (firstChartCode < startRange || firstChartCode > endRange)
                return false;
        }
        return true;
    }

    public boolean verifyAllCodeStartWithNumber(List<String> accountCodeList, String... startNumber) {
        if (accountCodeList == null || accountCodeList.isEmpty()) return false;
        List<String> expectedList = new ArrayList<>(Arrays.asList(startNumber));
        for (String accountCode : accountCodeList) {
            String firstNumber = String.valueOf(accountCode.charAt(0));
            if (!expectedList.contains(firstNumber))
                return false;
        }
        return true;
    }

    public List<String> getListAccountCode(int tBodyOrder) {
        List<String> firstColValue = new ArrayList<>();
        int i = 1;
        while (true) {
            Label lblAccountCode = Label.xpath(tblIncome.getxPathOfCell(tBodyOrder, colCode, i, null));
            if (!lblAccountCode.isDisplayed()) {
                System.out.println("Can NOT found the account code in the table");
                return firstColValue;
            }
            if (lblAccountCode.isDisplayed()) {
                i++;
                String colValue = lblAccountCode.getText().trim();
                if (Character.isDigit(colValue.charAt(0))) {
                    firstColValue.add(colValue);
                    System.out.println("Found the the account code in the table");
                }
            }
        }
    }

    /** Find index of 3 table: OPERATING INCOME, OPERATING EXPENSES, NON-OPERATING INCOME to match with tBodyOrder
     * */
    public int findTableIndex(String tableName){
        int i = 1;
        Label lblTableHeader;
        while (true) {
            lblTableHeader = Label.xpath(String.format("(//table//tbody)[%s]/tr[1]", i));
            if (!lblTableHeader.isDisplayed()) {
                System.out.println("Can NOT found table " + tableName );
                return 0;
            }
            if (lblTableHeader.getText().equalsIgnoreCase(tableName)) {
                System.out.println("Found the the table " + tableName );
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

    public String getNetProfitLoss(){
        return lblNetProfit.getText();
    }

}
