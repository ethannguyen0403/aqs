package pages.sb11.financialReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.sb11.financialReports.BalanceSheetAnalysisUtils;

import java.util.ArrayList;
import java.util.List;

import static common.SBPConstants.GMT_7;

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
    String totalBalanceDifXpath = "//td[text()='%s']//following-sibling::td[%s]";
    public int indexTotalSelectMonthCre = 5;
    public int indexTotalSelectMonthDe = 4;
    public int indexDifSelectMonth = 3;
    public int colTotalPreMonthDe = 1;
    public int colTotalPreMonthCre = 2;
    public int colTotalCurMonthDe = 4;
    public int colTotalCurMonthCre = 5;
    public int colTotalCurMonthDeTxn = 7;
    public int colTotalCurMonthCreTxn = 8;
    String valueDeCreOfParentAcc = "//tr[contains(@class,'header-ledger-group')][%s]//td[1]//following-sibling::td[%s]";

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

    /**
     *
     * @param accountName input sub-acc
     * @param month input previous, current, txns
     * @param isDebit
     * @return
     */
    public String getValueDeCreOfSubAcc(String accountName, String month, boolean isDebit){
        int rowIndex = findRowIndexOfAccount(accountName);
        int colIndex = 0;
        switch (month){
            case "previous":
                if (isDebit){
                    colIndex = colDebitPreviousMonth;
                } else {
                    colIndex = colCreditPreviousMonth;
                }
                break;
            case "current":
                if (isDebit){
                    colIndex = colDebitCurrentMonth;
                } else {
                    colIndex = colCreditCurrentMonth;
                }
                break;
            case "txns":
                if (isDebit){
                    colIndex = colDebitTxns;
                } else {
                    colIndex = colCreditTxns;
                }
                break;
            default:
                System.out.println("Input wrongly month value!");
        }
        return Label.xpath(tblBalance.getxPathOfCell(1, colIndex, rowIndex, null)).getText().trim().replace(",","");
    }

    /**
     *
     * @param accountTypeName input Asset, Liability, Capital, Total Balance
     * @param month input previous, current, txns
     * @param isDebit
     * @return
     */
    public String getTotalOfAccountType(String accountTypeName, String month, boolean isDebit) {
        String total = null;
        int colIndex = 0;
        switch (month){
            case "previous":
                if (isDebit){
                    colIndex = colTotalPreMonthDe;
                } else {
                    colIndex = colTotalPreMonthCre;
                }
                break;
            case "current":
                if (isDebit){
                    colIndex = colTotalCurMonthDe;
                } else {
                    colIndex = colTotalCurMonthCre;
                }
                break;
            case "txns":
                if (isDebit){
                    colIndex = colTotalCurMonthDeTxn;
                } else {
                    colIndex = colTotalCurMonthCreTxn;
                }
                break;
            default:
                System.out.println("Input wrongly month value!");
        }
        return Label.xpath(String.format("//td[text()='%s']//following-sibling::td[%s]",accountTypeName,colIndex)).getText().trim().replace(",","");
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

    /**
     *
     * @param typeValue input value "Total Balance" or "Difference"
     * @param indexTotal input index of value column
     * @return
     */
    public double getDifTotalBalance(String typeValue,int indexTotal){
        Label lblTotal = Label.xpath(String.format(totalBalanceDifXpath,typeValue,indexTotal));
        if (lblTotal.getColour("color").equals("rgba(252, 0, 0, 1)")){
            return Double.valueOf("-"+lblTotal.getText().trim().replace(",",""));
        } else {
            return Double.valueOf(lblTotal.getText().trim().replace(",",""));
        }
    }

    /**
     *
     * @param month input previous, current, txns
     * @return
     */
    public void verifyDifferenceValueDisplay(String month) {
        double debitValueAc = Double.valueOf(getTotalOfAccountType("Total Balance", month,true));
        double creditValueAc = Double.valueOf(getTotalOfAccountType("Total Balance", month,false));
        double difEx = debitValueAc - creditValueAc;
        int indexCol = 0;
        switch (month){
            case "previous":
                indexCol = 1;
                break;
            case "current" :
                indexCol = 3;
                break;
            case "txns":
                indexCol = 5;
                break;
            default:
                System.out.println("Input wrongly month value!");
        }
        double difAc = getDifTotalBalance("Difference",indexCol);
        //BA accept difference 0.01
        Assert.assertEquals(difAc,difEx,0.011,"FAILED!"+difEx+" difference from "+difAc);
    }

    /**
     *
     * @param accountTypeName
     * @param month input previous, current, txns
     * @param isDebit
     * @return
     */
    public void verifySumColumnDeCreDisplay(String accountTypeName, String month, boolean isDebit) {
        int year = DateUtils.getYear(GMT_7);
        int monthEx = DateUtils.getMonth(GMT_7);
        String totalType = null;
        switch (month){
            case "previous":
                if (isDebit){
                    totalType = "totalDebitPrevMonth";
                } else {
                    totalType = "totalCreditPrevMonth";
                }
                break;
            case "current":
                if (isDebit){
                    totalType = "totalDebit";
                } else {
                    totalType = "totalCredit";
                }
                break;
            case "txns":
                if (isDebit){
                    totalType = "totalDifDebit";
                } else {
                    totalType = "totalDifCredit";
                }
                break;
            default:
                System.err.println("Input wrongly month");
        }
        double sumPreDeEx = BalanceSheetAnalysisUtils.getSumCreDe(accountTypeName,totalType,year,monthEx,false);
        String valueAc = getTotalOfAccountType(accountTypeName,month,isDebit);
        //BA accept difference 0.01
        Assert.assertEquals(Double.valueOf(valueAc),sumPreDeEx,0.01,"FAILED!"+sumPreDeEx+" difference from "+valueAc);
    }

    /**
     *
     * @param month input previous, current, txns
     * @param isDebit
     */
    public void verifyTotalBalanceDisplay(String month, boolean isDebit) {
        Double assetValue = Double.valueOf(getTotalOfAccountType("Asset", month,isDebit));
        Double liabilityValue = Double.valueOf(getTotalOfAccountType("Liability", month,isDebit));
        Double capitalValue = Double.valueOf(getTotalOfAccountType("Capital", month,isDebit));
        double totalBalanceEx = DoubleUtils.roundUpWithTwoPlaces(assetValue + liabilityValue + capitalValue);
        double totalBalanceAc = Double.valueOf(getTotalOfAccountType("Total Balance", month,isDebit));
        //BA accept difference 0.02
        Assert.assertEquals(totalBalanceAc,totalBalanceEx,0.02,"FAILED!"+totalBalanceEx+" difference from "+totalBalanceAc);
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

    public void verifyTxnValueOfAccount(String accountName) {
        Double creditSelectedMonth = Double.valueOf(getValueDeCreOfSubAcc(accountName, "current",false)) == 0.00 ?
                Double.valueOf(getValueDeCreOfSubAcc(accountName, "current",true)) : -Double.valueOf(getValueDeCreOfSubAcc(accountName, "current",false));

        Double creditPreviousMonth = Double.valueOf(getValueDeCreOfSubAcc(accountName, "previous",false)) == 0.00 ?
                Double.valueOf(getValueDeCreOfSubAcc(accountName, "previous",true)) : -Double.valueOf(getValueDeCreOfSubAcc(accountName, "previous",false));
        String creditTxns = getValueDeCreOfSubAcc(accountName,"txns", false).equals("0.00") ?
                getValueDeCreOfSubAcc(accountName,"txns", true) : getValueDeCreOfSubAcc(accountName,"txns", false);
        Assert.assertEquals(Double.valueOf(creditTxns), Double.valueOf(String.format("%.2f", creditSelectedMonth-creditPreviousMonth).replace("-","")),0.01, "FAILED! Txns data is not correct");
    }
}
