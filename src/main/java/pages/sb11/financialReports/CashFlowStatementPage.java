package pages.sb11.financialReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.financialReports.popup.cashflowStatement.TransactionDetailsPopup;

import java.util.ArrayList;
import java.util.List;

import static pages.sb11.financialReports.CashFlowStatementPage.CashFlowStatementTable.*;


public class CashFlowStatementPage extends WelcomePage {

    Label lblPageTitle = Label.xpath("//app-cash-flow//div[contains(@class, 'card-header main-box-header')]");
    DropDownBox ddbCompany = DropDownBox.xpath("//div[contains(text(),'Company')]/following::select[1]");
    DropDownBox ddbFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/following::select[1]");
    TextBox txtFromDate = TextBox.xpath("//div[contains(text(),'From Date')]/following::input[1]");
    TextBox txtToDate = TextBox.xpath("//div[contains(text(),'To Date')]/following::input[1]");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate, "//bs-datepicker-container");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate, "//bs-datepicker-container");
    Button btnShow = Button.name("btnShow");

    public int totalColTransaction = 3;
    int colNameTotalInOrDecrease = 1;
    int colNetAmount = 2;
    public Table tblTransaction = Table.xpath("(//app-cash-flow//table[contains(@class,'pt-2 mr-1 col-5 table-bookie')])[1]", totalColTransaction);
    public Table tblCashInvest = Table.xpath("(//app-cash-flow//table[contains(@class,'pt-2 mr-1 col-5 table-bookie')])[2]", 2);
    public Table tblCashFinancing = Table.xpath("(//app-cash-flow//table[contains(@class,'pt-2 mr-1 col-5 table-bookie')])[3]", 2);
    public Table tblTotalInOrDecrease = Table.xpath("(//app-cash-flow//table[contains(@class,'pt-2 mr-1 col-5 table-bookie')])[4]", 3);

    enum CashFlowStatementTable {
        TABLE_TRANSACTION, TABLE_INVEST, TABLE_FINANCING
    }

    public void filter() {
        filter("", "", "", "");
    }

    public void filter(String companyUnit, String financialYears, String fromDate, String toDate) {
        if (!companyUnit.equals("")) {
            ddbCompany.selectByVisibleText(companyUnit);
        }
        if (!financialYears.equals("")) {
            ddbFinancialYear.selectByVisibleText(financialYears);
        }
        if (!fromDate.isEmpty()) {
            dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
        }
        if (!toDate.isEmpty()) {
            dtpToDate.selectDate(toDate, "dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public double getAmountTotalInHKDTransTable(String transType){
        int rowIndex = findRowIndexByNameOfCell(tblTransaction, transType, TABLE_TRANSACTION);
        Label amount = Label.xpath(tblTransaction.getxPathOfCell(1, totalColTransaction, rowIndex, null));
        return Double.valueOf(amount.getText().replace(",", ""));
    }

    public double getNetAmountOfTransactionTable() {
        int rowIndexTransaction = tblTransaction.getNumberOfRows(false, false);
        Label amount = Label.xpath(tblTransaction.getxPathOfCell(1, colNetAmount, rowIndexTransaction, null));
        return Double.valueOf(amount.getText().replace(",", ""));
    }

    public double getNetAmountOfInvestTable() {
        int rowIndex = findRowIndexByNameOfCell(tblCashInvest, "Net cash flow from investing activities:", TABLE_INVEST);
        Label amount = Label.xpath(tblCashInvest.getxPathOfCell(1, colNetAmount, rowIndex, null));
        return Double.valueOf(amount.getText().replace(",", ""));
    }

    public double getNetAmountOfFinancingTable() {
        int rowIndex = findRowIndexByNameOfCell(tblCashFinancing, "Net cash flow from financing activities:", TABLE_FINANCING);
        Label amount = Label.xpath(tblCashFinancing.getxPathOfCell(1, colNetAmount, rowIndex, null));
        return Double.valueOf(amount.getText().replace(",", ""));
    }

    public double getNetAmountOfTotalInOrDecreaseTable() {
        return Double.valueOf(tblTotalInOrDecrease.getHeaderNameOfRows().get(2).replace(",", ""));
    }

    public int findRowIndexByNameOfCell(Table table, String name, CashFlowStatementTable tableType) {
        int i = 2;
        int colOrder = -1;
        Label lblCell;
        switch (tableType) {
            case TABLE_TRANSACTION:
                colOrder = 2;
                break;
            case TABLE_FINANCING:
            case TABLE_INVEST:
                colOrder = 1;
                break;
            default:
                System.out.println("Table type not matched");
                break;
        }
        while (true) {
            lblCell = Label.xpath(table.getxPathOfCell(1, colOrder, i, null));
            if (!lblCell.isDisplayed()) {
                System.out.println("Can NOT found the row with contains  " + name + " in the table");
                return 0;
            }
            if (lblCell.getText().trim().contains(name)) {
                System.out.println("Found the row with contains " + name + " in the table");
                return i;
            }
            i = i + 1;
        }
    }

    public TransactionDetailsPopup goToTransactionDetails(String transType){
        int rowIndex = findRowIndexByNameOfCell(tblTransaction, transType, TABLE_TRANSACTION);
        tblTransaction.getControlOfCell(1, 2, rowIndex, null).click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new TransactionDetailsPopup();
    }

    public String getPageTitle() {
        return lblPageTitle.getText().trim();
    }

    public void verifyTotalInOrDecreaseTableCorrectFormat(List<String> expectedList) {
        String headerDescription = tblTotalInOrDecrease.getHeaderNameOfRows().get(0);
        String footerDescription = tblTotalInOrDecrease.getControlOfCell(1, 1, 4, null).getText().trim();
        List<String> actualList = new ArrayList<>();
        for (int i = 1; i < expectedList.size() - 1; i++) {
            String columnName = tblTotalInOrDecrease.getControlOfCellSPP(1, colNameTotalInOrDecrease, i, null).getText().trim();
            actualList.add(columnName);
        }
        actualList.add(0, headerDescription);
        actualList.add(actualList.size(), footerDescription);
        Assert.assertEquals(actualList, expectedList,
                "FAILED! Total Increase (decrease) in cash and cash equivalents table description is not displayed correct");
    }

    public void verifyFilterSectionDisplay() {
        Assert.assertTrue(ddbCompany.isDisplayed(), "FAILED! Company dropdown is not displayed");
        Assert.assertTrue(ddbFinancialYear.isDisplayed(), "FAILED! Financial Year dropdown is not displayed");
        Assert.assertTrue(txtFromDate.isDisplayed(), "FAILED! From date is not displayed");
        Assert.assertTrue(txtToDate.isDisplayed(), "FAILED! To date is not displayed");
        Assert.assertTrue(btnShow.isDisplayed(), "FAILED! Button show is not displayed");
    }

}
