package pages.sb11.generalReports;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Table;
import objects.Transaction;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.clientstatement.LedgerDetailPopup;
import utils.sb11.CompanySetUpUtils;
import utils.sb11.CurrencyRateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


import static common.SBPConstants.FINANCIAL_YEAR;
import static common.SBPConstants.FINANCIAL_YEAR_LIST;
import static java.lang.Double.parseDouble;

public class LedgerStatementPage extends WelcomePage {
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Financial Year')]//following::select[1]");
    public DropDownBox ddLedgerGroup = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Detail Type')]//following::select[1]");
    public DropDownBox ddLedgerName = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Account Type')]//following::select[1]");
    public DropDownBox ddReport = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Report')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public Label lblFromDate = Label.xpath("//div[text()='From Date']");
    public Label lblToDate = Label.xpath("//div[text()='To Date']");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//app-ledger-statement//button[contains(text(),'Show')]");
    public Button btnExportToExcel = Button.xpath("//button[contains(text(),'Export To Excel')]");
    public Button btnExportToPDF = Button.xpath("//button[contains(text(),'Export To PDF')]");
    public Label lblGrandTotalbyRunningBal = Label.xpath("//td[text()='Grand Total in HKD']/following-sibling::td[3]");
    public Label lblAmountShowCurrency = Label.xpath("//app-ledger-statement//table//thead//tr//th[contains(., 'CUR Translation in')]");
    public Label lblGrandTotalInOrigin = Label.xpath("//table//td[contains(., 'Grand Total in')]");
    int totalCol = 12;
    int colLedger = 2;
    int colTotal = 1;
    int colCur = 3;
    int colAmountTotalOriginCurrency = 4;
    int colCreDeTotalOriginCurrency = 3;
    int colToTalOriginCurrency = 1;
    int colAmountORG = 4;
    int colRunBalORG = 5;
    int colRunBalCTORG = 6;
    int colAmountGBP = 8;
    public int colRunBalGBP = 9;
    public Table tbLedger = Table.xpath("//app-ledger-statement//table",totalCol);
    public static final String RED_COLOR = "rgba(252, 0, 0, 1)";

    Label lblTitle = Label.xpath("//div[contains(@class,'header-filter')]//span[1]");
    public String getTitlePage () {return lblTitle.getText().trim();}

    public void showLedger (String companyUnit, String financialYear, String accountType, String ledgerGroup, String fromDate, String toDate,String report){
        if (!companyUnit.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if (!financialYear.isEmpty())
            ddFinancialYear.selectByVisibleText(financialYear);
        if (!accountType.isEmpty()){
            ddLedgerName.selectByVisibleContainsText(accountType);
            waitSpinnerDisappeared();
        }
        if (!ledgerGroup.isEmpty())
            ddLedgerGroup.selectByVisibleContainsText(ledgerGroup);
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate, "dd/MM/yyyy");
        }
        if(!report.isEmpty()){
            ddReport.selectByVisibleText(report);
        }
        btnShow.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction verifyLedgerTrans(Transaction trans, boolean isDebit, String ledgerGroup){
        int i = 1;
        Table tblLedgerGroup = Table.xpath(String.format("//span[contains(text(),'%s')]/ancestor::table",ledgerGroup),totalCol);
        while(true){
            String ledgerAccount = tblLedgerGroup.getControlOfCell(1,colLedger,i,null).getText().trim();
            if (isDebit){
                if(ledgerAccount.contains(trans.getLedgerDebit().split(" - ")[0])) {
                    System.out.println(String.format("Found transaction %s at row %s", ledgerAccount, i));
                    return verifyTransactionDisplayCorrectInRow(trans, true, i, ledgerGroup);
                }
            } else {
                if (ledgerAccount.contains(trans.getLedgerCredit().split(" - ")[0])){
                    System.out.println(String.format("Found transaction %s at row %s", ledgerAccount, i));
                    return verifyTransactionDisplayCorrectInRow(trans, false, i, ledgerGroup);
                }
            }
            i = i +1;
        }
    }

    public void exportExcel(){
        btnExportToExcel.scrollToTop();
        btnExportToExcel.click();
    }

    public void exportPDF(){
        btnExportToPDF.scrollToTop();
        btnExportToPDF.click();
    }

    /**
     *
     * @param subAccName
     * @param section input 'Original Currency', 'Shown in HKD', 'CUR Translation'
     * @param columnName
     * @return value of sub-acc
     */
    public String getValueOfSubAcc(String subAccName, String section, String columnName){
        int indexCol = 0;
        if (section.equals("Original Currency")){
            indexCol = tbLedger.getColumnIndexByName(columnName);
        } else if (section.equals("Shown in HKD")){
            switch (columnName){
                case "Credit/Debit":
                    indexCol = 8;
                    break;
                default:
                    indexCol = 9;
            }
        } else if (section.equals("CUR Translation")){
            switch (columnName){
                case "CT-Credit/Debit":
                    indexCol = 11;
                    break;
                default:
                    indexCol = 12;
            }
        }
//        return tbLedger.getControlBasedValueOfDifferentColumnOnRow(subAccName,1,colLedger,1,null,indexCol,null,false,false).getText().trim();
        return Label.xpath(String.format("//a[contains(text(),'%s')]//ancestor::tr//td[%d]",subAccName,indexCol)).getText().trim();
    }
    public String getTotalAmountInOriginCurrency(String toTalName){
        return tbLedger.getControlBasedValueOfDifferentColumnOnRow(toTalName,1,colTotal,1,null,colAmountTotalOriginCurrency,null,false,false).getText().trim();
    }

    public boolean isTotalAmountInOriginCurrencyPositiveNumber(String toTalName) {
        BaseElement lblTotal = tbLedger.getControlBasedValueOfDifferentColumnOnRow(toTalName,1,colTotal,1,null,colAmountTotalOriginCurrency,null,false,false);
        return lblTotal.getColour("color").contains(RED_COLOR) ? false : true;
    }

    public String getTotalCreDeInOriginCurrency(String toTalName){
        return tbLedger.getControlBasedValueOfDifferentColumnOnRow(toTalName,1,colTotal,1,null,colCreDeTotalOriginCurrency,null,false,false).getText().trim();
    }

    public String getDescriptionTotalAmountInOriginCurrency(String toTalName){
        return tbLedger.getControlBasedValueOfDifferentColumnOnRow(toTalName,1,colTotal,1,null,colToTalOriginCurrency,null,false,false).getText().trim();
    }

    public String getGrandTotalByRunningBal(){
        return lblGrandTotalbyRunningBal.getText();
    }

    private Transaction verifyTransactionDisplayCorrectInRow(Transaction transaction, boolean isDebit, int rowIndex, String parentAcc){
        Table tblLedgerGroup = Table.xpath(String.format("//span[contains(text(),'%s')]/ancestor::table",parentAcc),totalCol);
        // TODO: Johnny Use API to get OP Rate of according currency instead of init data in Constant class
        String ledgerAccount = tblLedgerGroup.getControlOfCell(1, colLedger, rowIndex, null).getText().trim();
        String cur = tblLedgerGroup.getControlOfCell(1, colCur, rowIndex, null).getText().trim();
        String amountORG = tblLedgerGroup.getControlOfCell(1, colAmountORG, rowIndex, null).getText().trim().replace(",","");
        String amountGBP = tblLedgerGroup.getControlOfCell(1, colAmountGBP, rowIndex, null).getText().trim().replace(",","");
        String runBalORG = tblLedgerGroup.getControlOfCell(1, colRunBalORG,rowIndex,null).getText().trim().replace(",","");
        String runBalCTORG = tblLedgerGroup.getControlOfCell(1,colRunBalCTORG,rowIndex,null).getText().trim().replace(",","");
        String runBalGBP = tblLedgerGroup.getControlOfCell(1, colRunBalGBP,rowIndex,null).getText().trim().replace(",","");

        if (isDebit){
            double curDebitRate = Double.parseDouble(CurrencyRateUtils.getOpRate("1",transaction.getLedgerDebitCur()));
            double amountDebitGBP = transaction.getAmountDebit() * curDebitRate;
            double runDebitGBP = (transaction.getDebitBalance() + transaction.getAmountDebit()) * curDebitRate;

            Assert.assertTrue(ledgerAccount.contains(transaction.getLedgerDebit().split(" - ")[0]), "Failed! Account code is incorrect");
            Assert.assertEquals(amountORG, String.format("%.2f", transaction.getAmountDebit()), "Failed! Credit/Debit ORG amount is incorrect");
            Assert.assertEquals(runBalORG, String.format("%.2f", transaction.getDebitBalance() + transaction.getAmountDebit()), "Failed! Debit Balance ORG amount is incorrect");
            Assert.assertEquals(runBalCTORG, String.format("%.2f", transaction.getDebitBalance() + transaction.getAmountDebit()), "Failed! Debit Balance CT ORG amount is incorrect");
            Assert.assertEquals(amountGBP, String.format("%.2f", amountDebitGBP), "Failed! Credit/Debit GBP amount is incorrect");
            Assert.assertEquals(runBalGBP, String.format("%.2f", runDebitGBP), "Failed! Running Balance GBP amount is incorrect");
            Assert.assertEquals(cur, transaction.getLedgerDebitCur(), "Failed! Debit Currency is incorrect is in correct");
        } else {
            double curCreditRate = Double.parseDouble(CurrencyRateUtils.getOpRate("1",transaction.getLedgerCreditCur()));
            double amountCreditGBP = transaction.getAmountCredit() * curCreditRate;
            double runCreditGBP = (transaction.getCreditBalance() + transaction.getAmountCredit()) * curCreditRate;

            Assert.assertTrue(ledgerAccount.contains(transaction.getLedgerCredit().split(" - ")[0]), "Failed! Account code is incorrect");
            Assert.assertEquals(amountORG, String.format("%.2f", transaction.getAmountCredit()), "Failed! Credit/Debit ORG amount is incorrect");
            Assert.assertEquals(runBalORG, String.format("%.2f", transaction.getCreditBalance() + transaction.getAmountCredit()), "Failed! Credit Balance ORG amount is incorrect");
            Assert.assertEquals(runBalCTORG, String.format("%.2f", transaction.getCreditBalance() + transaction.getAmountCredit()), "Failed! Debit Balance CT ORG amount is incorrect");
            Assert.assertEquals(amountGBP, String.format("%.2f", amountCreditGBP), "Failed! Credit/Debit ORG amount is incorrect");
            Assert.assertEquals(runBalGBP, String.format("%.2f", runCreditGBP), "Failed! Credit Balance ORG amount is incorrect");
            Assert.assertEquals(cur, transaction.getLedgerCreditCur(), "Failed! Credit Currency is incorrect is in correct");
        }
        return transaction;
    }

    /**
     *
     * @param parentName
     * @param ledgerName input number + name of ledger
     * @return
     */
    public LedgerDetailPopup openLedgerDetail (String parentName,String ledgerName){
        Table tblLedger = Table.xpath(String.format("//span[contains(text(),'%s')]/ancestor::table",parentName),totalCol);
        tblLedger.getControlBasedValueOfDifferentColumnOnRow(ledgerName,1,colLedger,1,null,colLedger,"a",false,false).click();
        waitSpinnerDisappeared();
        return new LedgerDetailPopup();
    }
    public LedgerDetailPopup openLedgerFirstDetail (){
        tbLedger.getControlOfCell(1,colLedger, 2,"a").click();
        return new LedgerDetailPopup();
    }

    public double getValueAmount(String ledgerName,int indexCol){
        String amountCreDeb = tbLedger.getControlBasedValueOfDifferentColumnOnRow(ledgerName,1,colLedger,1,null,indexCol,null,false,false).getText().trim().replace(",","");
        if (amountCreDeb.isEmpty()){
            amountCreDeb = String.valueOf(0);
        }
        return parseDouble(amountCreDeb);
    }

    private int getStartRowWithLedgerGroup(String ledgerGroup){
        int i = 1;
        Label lblLedgerGroup;
        Table tblLedgerGroup = Table.xpath(String.format("//a[contains(text(),'%s')]/ancestor::table",ledgerGroup),totalCol);
        while (true){
            lblLedgerGroup = Label.xpath(tblLedgerGroup.getxPathOfCell(1,1,i,null));
            if(!lblLedgerGroup.isDisplayed()){
                System.out.println("The ledger group "+ledgerGroup+" does not display in the list");
                return 0;
            }
            if(lblLedgerGroup.getText().contains(ledgerGroup))
                return i;
            i = i +1;
        }
    }

    public boolean isLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return currentDayOfMonth == lastDayOfMonth ? true : false;
    }

    public String getLastDateOfPreviousMonth(String formatDate, String gmt) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(gmt));
        return simpleDateFormat.format(calendar.getTime());
    }
    public String getLastDateAfterCJE(String format){
        String date = DateUtils.getDate(0,format, SBPConstants.GMT_7);
        date = isLastDayOfMonth() ? date : getLastDateOfPreviousMonth(format,SBPConstants.GMT_7);
        return date;
    }
    public String getBeginDateOfFinanYear(String financialYear){
        String yearBegin = financialYear.replace("Year ","").split("-")[0];
        return String.format("01/08/%s",yearBegin);
    }

    /**
     *
     * @param parentName
     * @param section input 'Shown in HKD', 'CUR Translation'
     * @param colName
     * @return total value
     */
    public String getTotalInHKD(String parentName, String section, String colName) {
        int indexCol = 0;
        if (section.equals("Shown in HKD")){
            switch (colName){
                case "Credit/Debit":
                    indexCol = 3;
                    break;
                default:
                    indexCol = 4;
            }
        } else if (section.equals("CUR Translation")){
            switch (colName){
                case "CT-Credit/Debit":
                    indexCol = 6;
                    break;
                default:
                    indexCol = 7;
            }
        }
        return Label.xpath(String.format("(//span[contains(text(),'%s')]/ancestor::tr//following-sibling::tr[@class='total-row']//td)[%s]",parentName,indexCol)).getText().trim();
    }

    public void verifyRunningBalAfterTriggering(double runBalBefore, double amountDebit, double amountCredit) {
        String ledgerName = "302.000.001.000 - PL for Current Year - HKD";
        double valueEx = DoubleUtils.roundEvenWithTwoPlaces(runBalBefore + (amountDebit-amountCredit));
        double valueAc = getValueAmount(ledgerName,colRunBalGBP);
        double valueFinal1 = valueAc - valueEx;
        double valueFinal2 = valueEx - valueAc;
        if (valueFinal1 <= 0.01 && valueFinal1 >= 0 || valueFinal2 <= 0.01 && valueFinal2 >= 0){
            Assert.assertTrue(true,"FAILED! Value displays incorrect.");
        } else {
            Assert.assertEquals(valueAc,valueEx,"FAILED! Value displays incorrect.");
        }
    }

    public void verifyUI(String detailType) {
        System.out.println("Dropdown: Company Unit, Financial Year, Account Type, Detail Type");
        Assert.assertEquals(ddCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(ddFinancialYear.getOptions(),FINANCIAL_YEAR_LIST,"Failed! Financial year dropdown is not displayed!");
        Assert.assertEquals(ddLedgerName.getOptions(), SBPConstants.LedgerStatement.ACCOUNT_TYPE,"Failed! Account Type dropdown is not displayed!");
        Assert.assertTrue(ddLedgerGroup.getOptions().contains(detailType),"Failed! Detail Type dropdown is not displayed!");
        System.out.println("Datetime picker: From Date, To Date");
        Assert.assertEquals(lblFromDate.getText(),"From Date","Failed! From Date datetimepicker is not displayed!");
        Assert.assertEquals(lblToDate.getText(),"To Date","Failed! To Date datetimepicker is not displayed!");
        System.out.println("Button: Show, Export To Excel, Export to PDF");
        Assert.assertEquals(btnShow.getText(),"Show","Failed! Show button is not displayed!");
        Assert.assertEquals(btnExportToExcel.getText(),"Export To Excel","Failed! Export To Excel button is not displayed!");
        Assert.assertEquals(btnExportToPDF.getText(),"Export To PDF","Failed! Export To PDF button is not displayed!");
        System.out.println("Validate Ledger Statement table is displayed with correctly header column");
        btnShow.click();
        waitSpinnerDisappeared();
        Assert.assertEquals(tbLedger.getHeaderNameOfRows(), SBPConstants.LedgerStatement.TABLE_HEADER,"Failed! Ledger Statement table is displayed with incorrectly header column");
    }
}
