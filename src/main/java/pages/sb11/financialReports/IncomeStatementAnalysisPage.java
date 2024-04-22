package pages.sb11.financialReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.utils.DoubleUtils;
import com.relevantcodes.extentreports.LogStatus;
import controls.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import pages.sb11.WelcomePage;
import testcases.BaseCaseAQS;
import utils.sb11.ChartOfAccountUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IncomeStatementAnalysisPage extends WelcomePage {
    DropDownBox ddbCompany = DropDownBox.xpath("//div[contains(text(),'Company')]/following::select[1]");
    DropDownBox ddbFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/following::select[1]");
    public DropDownBox ddMonth = DropDownBox.xpath("//div[text()= 'Month']/parent::div//select");
    DropDownBox ddReport = DropDownBox.xpath("//div[text()= 'Report']/parent::div//select");
    Button btnShow = Button.name("btnShow");
    public Button btnExportExcel = Button.xpath("//button//*[contains(@class, 'far fa-file-excel')]");
    public Button btnExportPDF = Button.xpath("//button//*[contains(@class, 'far fa-file-pdf')]");
    public Label lblAmountAreShow = Label.xpath("//app-aqs-bet//label[contains(text(), 'Amounts are shown in')]");

    public Table tblIncomeAnalysis = Table.xpath("//div[@id='income-statement-analysis']//table", 7);
    public int colGroupCode = 1;
    public int colGroupName = 2;
    public final static String COLOR_BLUE = "rgb(0, 123, 255)";
    public final static String COLOR_RED = "rgb(220, 53, 69)";
    public final static String COLOR_BLACK = "rgba(33, 37, 41, 1)";

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

    /**
     *
     * @param month input value with format MMMM
     * @param year input value with format yyyy
     */
    public void verifyAmountNetProfitColorIsCorrect(String month, String year) {
        String lblYear = String.format("%s - %s", month, year);
        double income = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getTotalAmount(lblYear, getRowIndexByGroup("Total Operating Income")).replace(",","")));
        double expense = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getTotalAmount(lblYear, getRowIndexByGroup("Total Operating Expenses")).replace(",","")));
        double nonIncome = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getTotalAmount(lblYear, getRowIndexByGroup("Total Non-Operating Income")).replace(",","")));
        BaseElement cellControl = getNetProfitLossControl(lblYear);
        if (income - expense + nonIncome > 0) {
            verifyElementColorIsCorrect(cellControl, true);
        } else if (income - expense + nonIncome < 0) {
            verifyElementColorIsCorrect(cellControl, false);
        } else {
            String message = "@Verify-Info: Amount equal to 0.0, validate element with Black color";
            BaseCaseAQS.logger.log(LogStatus.INFO, message);
            Reporter.log(message);
            Assert.assertEquals(cellControl.getColour("color"), COLOR_BLACK, "FAILED! Element is not in Black");
        }
    }

    public void verifyAmountTxnsColorIsCorrect(double currentMonth, double previousMonth, BaseElement cellControl) {
        if (currentMonth - previousMonth > 0) {
            verifyElementColorIsCorrect(cellControl, true);
        } else if (currentMonth - previousMonth < 0) {
            verifyElementColorIsCorrect(cellControl, false);
        } else {
            String message = "@Verify-Info: Amount equal to 0.0, validate element with Black color";
            BaseCaseAQS.logger.log(LogStatus.INFO, message);
            Reporter.log(message);
            Assert.assertEquals(cellControl.getColour("color"), COLOR_BLACK, "FAILED! Element is not in Black");
        }
    }

    public void verifyElementColorIsCorrect(BaseElement element, boolean isPositive) {
        if (isPositive) {
            Assert.assertEquals(element.getColour("Color"), COLOR_BLUE, "FAILED! Element with Css color is not Blue");
        } else {
            Assert.assertEquals(element.getColour("Color"), COLOR_RED, "FAILED! Element with Css color is not Red");
        }
    }

    private String addMinusToNegativeNumber(BaseElement element) {
        if (element.getColour("Color").equalsIgnoreCase(COLOR_RED)) {
            return "-" + element.getText().trim();
        } else {
            return element.getText().trim();
        }
    }

    public String getCellValueOfMonthCol(String colMonthName, int rowIndex) {
        int colIndex = tblIncomeAnalysis.getColumnIndexByName(colMonthName) + 1;
        BaseElement amount = tblIncomeAnalysis.getControlOfCell(1, colIndex, rowIndex, null);
        return addMinusToNegativeNumber(amount);
    }

    public String getTotalAmount(String colMonthName, int rowIndex) {
        int colIndex = tblIncomeAnalysis.getColumnIndexByName(colMonthName);
        BaseElement amount = tblIncomeAnalysis.getControlOfCell(1, colIndex, rowIndex, null);
        return addMinusToNegativeNumber(amount);
    }

    public String getNetProfitLoss(String colMonthName) {
        int colIndex = tblIncomeAnalysis.getColumnIndexByName(colMonthName);
        Label amount = Label.xpath(String.format("//tfoot//tr//td[%s]", colIndex));
        return addMinusToNegativeNumber(amount);
    }

    public BaseElement getNetProfitLossControl(String colMonthName) {
        int colIndex = tblIncomeAnalysis.getColumnIndexByName(colMonthName);
        return Label.xpath(String.format("//tfoot//tr//td[%s]", colIndex));
    }

    public BaseElement getCellControlOfMonthCol(String colMonthName, int rowIndex){
        int colIndex = tblIncomeAnalysis.getColumnIndexByName(colMonthName) + 1;
        return tblIncomeAnalysis.getControlOfCell(1, colIndex, rowIndex, null);
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
        String numberAcc = Label.xpath(tblIncomeAnalysis.getxPathOfCell(1, colGroupCode, rowIndex, null)).getText().trim();
        String nameAcc = Label.xpath(tblIncomeAnalysis.getxPathOfCell(1, colGroupName, rowIndex, null)).getText().trim();
        return String.format("%s - %s", numberAcc, nameAcc);
    }

    public void verifyPreviousMonthDisplay(String monthFilter, String expectedPreviousMonth){
        Label lblMonthFilter = Label.xpath(String.format("//th[text()='%s']/preceding-sibling::th[1]", monthFilter));
        Assert.assertEquals(lblMonthFilter.getText().trim(), expectedPreviousMonth, "FAILED! Previous month compare to the filtered month not correct");
    }

    public boolean verifyExcelDataInCommaFormat(List<Map<String, String>> exelData, String colMonthName){
        for(Map<String, String> month: exelData){
            if(!isAmountNumberCorrectCommaFormat(month.get(colMonthName).replace("-",""))){
                return false;
            }
        }
        return true;
    }

    public boolean isAmountNumberCorrectCommaFormat(String number){
        Pattern pattern = Pattern.compile("^\\d{1,3}([ ,]?\\d{3})*([.,]\\d+)?$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    public List<String> getLstDetailTypeOfGroup(String groupName){
        List<String> lstDetailTypeEx = new ArrayList<>();
        List<WebElement> lstDetailType = DriverManager.getDriver().findElements(By.xpath(String.format("//table//td[text()='%s']/parent::tr/following-sibling::tr",groupName)));
        for (int i = 0; i < lstDetailType.size(); i++){
            if (lstDetailType.get(i).getAttribute("class").contains("tbl-total-head")){
                lstDetailTypeEx.add(Label.xpath(String.format("(//table//td[text()='%s']/parent::tr/following-sibling::tr)[%s]//td[1]",groupName,i+1)).getText());
            } else if (lstDetailType.get(i).getAttribute("class").contains("tbl-total-foot")){
                break;
            }
        }
        return lstDetailTypeEx;
    }

    public List<String> getLstParentAccInGroup(String detaiType){
        List<String> lstParentEx = new ArrayList<>();
        List<WebElement> lstParent = DriverManager.getDriver().findElements(By.xpath(String.format("//table//td[text()='%s']/parent::tr/following-sibling::tr",detaiType)));
        for (int i = 0; i < lstParent.size(); i++){
            if (lstParent.get(i).getAttribute("class").contains("tbl-total-parent")){
                lstParentEx.add(Label.xpath(String.format("(//table//td[text()='%s']/parent::tr/following-sibling::tr)[%s]//td[1]",detaiType,i+1)).getText());
            } else if (lstParent.get(i).getAttribute("class").contains("tbl-total-head") || lstParent.get(i).getAttribute("class").contains("tbl-total-foot")){
                break;
            }
        }
        return lstParentEx;
    }

    public void verifySortDetailType(String groupName) {
        List<String> lstDetailType = getLstDetailTypeOfGroup(groupName);
        if (lstDetailType.size()==1){
            Assert.assertTrue(true);
        } else {
            List<String> lstSorted = ChartOfAccountUtils.getLstLedgerGroup();
            List<String> lstEx = new ArrayList<>();
            for (String string : lstSorted){
                if (lstDetailType.contains(string)){
                    lstEx.add(string);
                }
            }
            Assert.assertEquals(lstDetailType,lstEx,"FAILED! The list is not sorted by Ascending");
        }
    }

    public void verifySortParentAcc(String groupName) {
        List<String> lstDetailType = getLstDetailTypeOfGroup(groupName);
        for (int i = 0; i < lstDetailType.size();i++){
            List<String> lstParent = getLstParentAccInGroup(lstDetailType.get(i));
            isSortByAsc(lstParent);
        }
    }
    public void isSortByAsc(List<String> lstSort){
        List<String> lstSorted = new ArrayList<>();
        for (String string : lstSort){
            lstSorted.add(string);
        }
        Collections.sort(lstSorted);
        Assert.assertEquals(lstSort,lstSorted,"FAILED! The list is not sorted by Ascending");
    }
    /**
     *
     * @param month input value with format MMMM
     * @param year input value with format yyyy
     */
    public void verifyValueNetProfitDisplay(String month, String year) {
        String lblYear = String.format("%s - %s", month, year);
        double amountIncome = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getTotalAmount(lblYear, getRowIndexByGroup("Total Operating Income")).replace(",","")));
        double amountExpense = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getTotalAmount(lblYear, getRowIndexByGroup("Total Operating Expenses")).replace(",","")));
        double amountNonIncome = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getTotalAmount(lblYear, getRowIndexByGroup("Total Non-Operating Income")).replace(",","")));
        double amountNetProfitAc = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(getNetProfitLoss(lblYear).replace(",","")));
        double amountNetProfitEx = DoubleUtils.roundUpWithTwoPlaces(amountIncome - amountExpense + amountNonIncome);
        //BA accept difference 0.01
        Assert.assertEquals(amountNetProfitAc,amountNetProfitEx,0.01,"FAILED!"+amountNetProfitEx+" difference from "+amountNetProfitAc);
    }

    public void exportFile(String type) {
        if (type.equals("Excel")){
            btnExportExcel.click();
        } else {
            btnExportPDF.click();
        }
        waitSpinnerDisappeared();
        //wait for computer download file
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
