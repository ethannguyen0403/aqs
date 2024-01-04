package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IncomeStatementAnalysisPage extends WelcomePage {
    DropDownBox ddbCompany = DropDownBox.xpath("//div[contains(text(),'Company')]/following::select[1]");
    DropDownBox ddbFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/following::select[1]");
    public DropDownBox ddMonth = DropDownBox.xpath("//div[text()= 'Month']/parent::div//select");
    DropDownBox ddReport = DropDownBox.xpath("//div[text()= 'Report']/parent::div//select");
    Button btnShow = Button.name("btnShow");
    public Label lblAmountAreShow = Label.xpath("//app-aqs-bet//label[contains(text(), 'Amounts are shown in')]");

    public Table tblIncomeAnalysis = Table.xpath("//div[@id='income-statement-analysis']//table", 7);
    public int colGroupCode = 1;
    public int colGroupName = 2;

    public void filter(String companyUnit, String financialYears, String month, String report) {
        if (!companyUnit.equals("")) {
            ddbCompany.selectByVisibleText(companyUnit);
        }
        if (!financialYears.equals("")) {
            ddbFinancialYear.selectByVisibleText(financialYears);
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

    /**
     * @param groupName the param with input values are: OPERATING INCOME, OPERATING EXPENSES, NON-OPERATING INCOME
     * */
    public List<String> getCodeListOfGroup(String groupName) {
        int startIndex = getRowIndexByGroup(groupName) + 1;
        List<String> listCode = new ArrayList<>();
        while (true) {
            Label lblAccountCode;
            lblAccountCode = Label.xpath(tblIncomeAnalysis.getxPathOfCell(1, colGroupCode, startIndex, null));
            if ( lblAccountCode.getText().trim().toLowerCase().contains(groupName.toLowerCase())) {
                System.out.println("Found the total row of: " + groupName + " break the loop");
                return listCode;
            }
            if (lblAccountCode.isDisplayed() && Character.isDigit(lblAccountCode.getText().trim().charAt(0))) {
                String code = lblAccountCode.getText().trim();
                listCode.add(code);
                System.out.println("Found the parent account: " + code + " in the table");
            }
            startIndex++;
        }
    }

    public String getCellValueOfMonthCol(String colMonthName, int rowIndex){
        int colIndex = tblIncomeAnalysis.getColumnIndexByName(colMonthName) + 1;
        return tblIncomeAnalysis.getControlOfCell(1, colIndex, rowIndex, null).getText().trim();
    }

    public int getRowIndexByGroup(String groupName){
        int i = 1;
        Label lblGroupName;
        while (true){
            lblGroupName = Label.xpath(tblIncomeAnalysis.getxPathOfCell(1, colGroupCode,i,null));
            if(!lblGroupName.isDisplayed()) {
                System.out.println("Can NOT found the group:  "+groupName+" in the table");
                return -1;
            }
            if(lblGroupName.getText().trim().equalsIgnoreCase(groupName)){
                System.out.println("Found the group "+groupName+" in the table");
                return i;
            }
            i = i +1;
        }
    }

    public String getChartCodeAccount(int rowIndex) {
        return String.format("%s - %s", tblIncomeAnalysis.getControlOfCell(1, colGroupCode, rowIndex, null).getText().trim(),
                tblIncomeAnalysis.getControlOfCell(1, colGroupName, rowIndex, null).getText().trim());
    }

    public void verifyPreviousMonthDisplay(String monthFilter, String expectedPreviousMonth){
        Label lblMonthFilter = Label.xpath(String.format("//th[text()='%s']/preceding-sibling::th[1]", monthFilter));
        Assert.assertEquals(lblMonthFilter.getText().trim(), expectedPreviousMonth, "FAILED! Previous month compare to the filtered month not correct");
    }
}
