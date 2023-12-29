package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.List;

public class BalanceSheetAnalysisPage extends WelcomePage {
    DropDownBox ddbCompany = DropDownBox.xpath("//div[contains(text(),'Company')]/following::select[1]");
    DropDownBox ddbFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/following::select[1]");
    DropDownBox ddMonth = DropDownBox.xpath("//div[text()= 'Month']/parent::div//select");
    DropDownBox ddReport = DropDownBox.xpath("//div[text()= 'Report']/parent::div//select");
    Button btnShow = Button.name("btnShow");
    public Button btnExportExcel = Button.xpath("//*[contains(@class, 'far fa-file-excel')]");
    public Button btnExportPDF = Button.xpath("//*[contains(@class, 'far fa-file-pdf')]");
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

    public boolean verifyParentAccountSortAsc(List<String> accountList, boolean isAsc){
        List<Double> newAccountList = new ArrayList<>();
        for (String account: accountList){
            newAccountList.add(Double.valueOf(account.replace(".", "")));
        }
        return isSorted(newAccountList, isAsc);
    }

    private boolean isSorted(List<Double> accountList, boolean isAsc) {
        for (int i = 1; i < accountList.size(); i++) {
            if (accountList.get(i-1) == accountList.get(i)) {
                continue;
            }
            if ((accountList.get(i-1) > accountList.get(i)) == isAsc) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param parentType the param with input values are: Asset, Liability, Capital
     * */
    public List<String> getParentAccountList(String parentType) {
        int startIndex = findRowIndexOfAccount(parentType.toUpperCase()) + 1;
        List<String> listParent = new ArrayList<>();
        while (true) {
            Label lblParentAccount;
            lblParentAccount = Label.xpath(tblBalance.getxPathOfCell(1, colAccount, startIndex, null));
            if (lblParentAccount.getText().trim().equalsIgnoreCase(parentType)) {
                System.out.println("Found the total row of: " + parentType + " break the loop");
                return listParent;
            }
            if (lblParentAccount.isDisplayed() && Character.isDigit(lblParentAccount.getText().trim().charAt(0))) {
                String parentAccount = lblParentAccount.getText().trim();
                listParent.add(parentAccount);
                System.out.println("Found the parent account: " + parentAccount + " in the table");
            }
            startIndex++;
        }
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
