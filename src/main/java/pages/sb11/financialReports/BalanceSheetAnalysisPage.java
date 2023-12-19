package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

public class BalanceSheetAnalysisPage extends WelcomePage {
    DropDownBox ddbCompany = DropDownBox.xpath("//div[contains(text(),'Company')]/following::select[1]");
    DropDownBox ddbFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/following::select[1]");
    DropDownBox ddMonth = DropDownBox.xpath("//div[text()= 'Month']/parent::div//select");
    DropDownBox ddReport = DropDownBox.xpath("//div[text()= 'Report']/parent::div//select");
    Button btnShow = Button.name("btnShow");
    public Label lblDifferenceSelectedMonth = Label.xpath("//td[contains(text(), 'Difference')]/following-sibling::td[3]");
    public Label lblAmountAreShow = Label.xpath("//app-aqs-bet//label[contains(text(), 'Amounts are shown in')]");
    public Table tblBalance = Table.xpath("//app-balance-sheet-analysis//table", 14);
    public int colAccount = 1;
    public int colDebitPreviousMonth = 2;
    public int colCreditPreviousMonth = 3;
    public int colDebitCurrentMonth = 5;
    public int colCreditCurrentMonth = 6;
    public int colDebitTxns = 8;
    public int colCreditTxns = 9;

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

    public String getColumnDebitCreditOfAccountSelectedMonth(String accountName, boolean isDebit) {
        int rowIndex = findRowIndexOfAccount(accountName);
        int creOrDeCol = isDebit ? colDebitCurrentMonth : colCreditCurrentMonth;
        return Label.xpath(tblBalance.getxPathOfCell(1, creOrDeCol, rowIndex, null)).getText().trim();
    }

    public String getColumnDebitCreditOfAccountPreviousMonth(String accountName, boolean isDebit) {
        int rowIndex = findRowIndexOfAccount(accountName);
        int creOrDeCol = isDebit ? colDebitPreviousMonth : colCreditPreviousMonth;
        return Label.xpath(tblBalance.getxPathOfCell(1, creOrDeCol, rowIndex, null)).getText().trim();
    }

    public String getColumnDebitCreditOfAccountTxns(String accountName, boolean isDebit) {
        int rowIndex = findRowIndexOfAccount(accountName);
        int creOrDeCol = isDebit ? colDebitTxns : colCreditTxns;
        return Label.xpath(tblBalance.getxPathOfCell(1, creOrDeCol, rowIndex, null)).getText().trim();
    }

    public int findRowIndexOfAccount(String accountName){
        int i = 1;
        Label lblLedger;
        Label lblBlank;
        while (true){
            //Handle for case row /tr is blank and not contains any /td tag
            lblLedger = Label.xpath(tblBalance.getxPathOfCell(1,colAccount,i,null));
            lblBlank = Label.xpath(String.format("//app-balance-sheet-analysis//table//tbody[1]//tr[%s]", i));
            if(!lblLedger.isDisplayed() && !lblBlank.isDisplayed()) {
                System.out.println("Can NOT found the account name "+accountName+" in the table");
                return -1;
            }
            if(lblLedger.getText().trim().equals(accountName)){
                System.out.println("Found the account name "+accountName+" in the table");
                return i;
            }
            i++;
        }
    }
}
