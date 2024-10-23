package pages.sb11.generalReports.popup.clientstatement;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.utils.DoubleUtils;
import controls.Table;
import objects.Transaction;
import org.testng.Assert;
import pages.sb11.generalReports.LedgerStatementPage;
import utils.sb11.accounting.CurrencyRateUtils;

import java.util.List;

public class LedgerDetailPopup {
    public Label lblTitle = Label.xpath("//app-ledger-detail//span[contains(@class,'font-weight-bolder')]");
    public Button btnClose = Button.xpath("//app-ledger-detail//em[contains(@class,'fa-times')]");
    DropDownBox  ddShowTxn = DropDownBox.xpath("//app-ledger-detail//select[contains(@class, 'justify-content-center')]");
    int totalCol = 10;
    int colTxnDate = 2;
    int colDescription = 3;
    int colCredORG = 4;
    int colDebORG = 5;
    int colRunORG = 6;
    int colCredGBP = 8;
    int colDebGBP = 9;
    int colRunGBP = 10;
    int colCredTotalORG = 2;
    int colDebTotalORG = 3;
    int colRunTotalORG = 4;
    int colCredTotalGBP = 6;
    int colDebTotalGBP = 7;
    int colRunTotalGBP = 8;

    public Table tbLedger = Table.xpath("//app-ledger-detail//table",totalCol);

    /**
     * Click on close icon on the top right of the popup
     */
    public LedgerStatementPage closePopup(){
        btnClose.click();
        return new LedgerStatementPage();
    }

    public void selectShowTxnDropDown(String options){
        ddShowTxn.selectByVisibleText(options);
    }
    public Transaction verifyLedgerTrans(Transaction trans, boolean isDebit, String description){
        int i = 1;
        while(true){
            if (!tbLedger.getControlOfCell(1,colDescription,i,null).isDisplayed()){
                System.out.println("Can't find element");
                return null;
            } else {
                String txnDate = tbLedger.getControlOfCell(1,colDescription,i,null).getText().trim();
                if(txnDate.equalsIgnoreCase(description))
                {
                    if (isDebit)
                        return verifyTransInfoDisplayCorrectInRow(trans, true, i);
                    return verifyTransInfoDisplayCorrectInRow(trans, false, i);
                }
            }
            i = i +1;
        }
    }

    private Transaction verifyTransInfoDisplayCorrectInRow(Transaction transaction, boolean isDebit, int rowIndex){
        String txnDate = tbLedger.getControlOfCell(1, colTxnDate, rowIndex, null).getText().trim();
        String description = tbLedger.getControlOfCell(1, colDescription, rowIndex, null).getText().trim();
        String creditORG = tbLedger.getControlOfCell(1, colCredORG, rowIndex, null).getText().trim();
        String debitORG = tbLedger.getControlOfCell(1, colDebORG, rowIndex, null).getText().trim();
        String runBalORG = tbLedger.getControlOfCell(1, colRunORG, rowIndex, null).getText().trim();
        String creditGBP = tbLedger.getControlOfCell(1, colCredGBP, rowIndex, null).getText().trim();
        String debitGBP = tbLedger.getControlOfCell(1, colDebGBP, rowIndex, null).getText().trim();
        String runBalGBP = tbLedger.getControlOfCell(1, colRunGBP, rowIndex, null).getText().trim();

        if (isDebit){
            double curDebitRate = Double.parseDouble(CurrencyRateUtils.getOpRate("1",transaction.getLedgerDebitCur()));
            double amountDebitGBP = DoubleUtils.roundUpWithTwoPlaces(transaction.getAmountDebit() * curDebitRate);
            double runDebitGBP = DoubleUtils.roundUpWithTwoPlaces(transaction.getDebitBalance() + transaction.getAmountDebit()) * curDebitRate;

            Assert.assertTrue(txnDate.contains(transaction.getTransDate()), "Failed! Txn Date is incorrect");
            Assert.assertEquals(debitORG.replace(",",""), String.format("%.2f", transaction.getAmountDebit()), "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(runBalORG.replace(",",""), String.format("%.2f", transaction.getDebitBalance() + transaction.getAmountDebit()), "Failed! Running Balance ORG amount is incorrect");

            Assert.assertEquals(debitGBP.replace(",",""), String.format("%.2f", amountDebitGBP), "Failed! Debit GBP amount is incorrect");
            Assert.assertEquals(runBalGBP.replace(",",""), String.format("%.2f", runDebitGBP), "Failed! Running Balance GBP amount is incorrect");
            verifyTransTotal(transaction,true);
        } else {
            double curCreditRate = Double.parseDouble(CurrencyRateUtils.getOpRate("1",transaction.getLedgerCreditCur()));
            double amountCreditGBP = transaction.getAmountCredit() * curCreditRate;
            double runCreditGBP = (transaction.getCreditBalance() + transaction.getAmountCredit()) * curCreditRate;

            Assert.assertTrue(txnDate.contains(transaction.getTransDate()), "Failed! Txn Date is incorrect");
            Assert.assertEquals(creditORG.replace(",",""), String.format("%.2f", transaction.getAmountCredit()), "Failed! Credit ORG amount is incorrect");
            Assert.assertEquals(runBalORG.replace(",",""), String.format("%.2f", transaction.getCreditBalance() + transaction.getAmountCredit()), "Failed! Running Balance ORG amount is incorrect");

            Assert.assertEquals(creditGBP.replace(",",""), String.format("%.2f", amountCreditGBP), "Failed! Credit GBP amount is incorrect");
            Assert.assertEquals(runBalGBP.replace(",",""), String.format("%.2f", runCreditGBP), "Failed! Credit Balance GBP amount is incorrect");
            //TODO: Assign to Johnny:  Wrong VP at Total Row when have more transactions. Should sum all transactions and compare with total row
            verifyTransTotal(transaction,false);
        }
        Assert.assertEquals(description, transaction.getRemark(), "Failed! Description is incorrect");
        return transaction;
    }

    private Transaction verifyTransTotal(Transaction transaction, boolean isDebit){
        int totalRow = getTotalRow();
        String sumCredORG = sumAllTransactionByColumn(totalRow, colCredORG);
        String sumDebORG = sumAllTransactionByColumn(totalRow, colDebORG);
        String lastRunORG = getLastRunningBalance(totalRow, colRunORG);
        String sumCredGBP = sumAllTransactionByColumn(totalRow, colCredGBP);
        String sumDebGBP = sumAllTransactionByColumn(totalRow, colDebGBP);
        String lastRunGBP = getLastRunningBalance(totalRow, colRunGBP);

        String totalcredORG = tbLedger.getControlOfCell(1,colCredTotalORG,totalRow,null).getText().trim();
        String totaldebORG = tbLedger.getControlOfCell(1,colDebTotalORG,totalRow,null).getText().trim();
        String totalrunORG = tbLedger.getControlOfCell(1,colRunTotalORG,totalRow,null).getText().trim();
        String totalcredGBP = tbLedger.getControlOfCell(1, colCredTotalGBP, totalRow, null).getText().trim();
        String totaldebGBP = tbLedger.getControlOfCell(1, colDebTotalGBP, totalRow, null).getText().trim();
        String totalrunGBP = tbLedger.getControlOfCell(1,colRunTotalGBP,totalRow,null).getText().trim();

        if (isDebit){
            Assert.assertEquals(totaldebORG, sumDebORG, "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(totaldebGBP, sumDebGBP, "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(totalrunORG, lastRunORG, "Failed! Running Balance ORG amount is incorrect");
            Assert.assertEquals(totalrunGBP, lastRunGBP, "Failed! Running Balance GBP amount is incorrect");
        } else {
            Assert.assertEquals(totalcredORG, sumCredORG, "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(totalcredGBP, sumCredGBP, "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(totalrunORG, lastRunORG, "Failed! Running Balance ORG amount is incorrect");
            Assert.assertEquals(totalrunGBP, lastRunGBP, "Failed! Running Balance GBP amount is incorrect");
        }
        return transaction;
    }

    private String sumAllTransactionByColumn(int totalRow, int colIndex) {
        double sumValue = 0;
        for(int i = 1; i < totalRow; i++) {
            String amount = tbLedger.getControlOfCell(1,colIndex,i,null).getText().trim();
            if (!amount.isEmpty()) {
                sumValue += Double.parseDouble(amount);
            }
        }
        return String.format("%.2f", sumValue);
    }

    private String getLastRunningBalance(int totalRow, int colIndex) {
        String amount = tbLedger.getControlOfCell(1,colIndex,totalRow-1,null).getText().trim();
        return amount;
    }

    private int getTotalRow(){
        int i = 1;
        Label lbTotal;
        while (true){
            lbTotal = Label.xpath(tbLedger.getxPathOfCell(1,1,i,null));
            if(!lbTotal.isDisplayed()) {
                System.out.println("Can NOT found the Total row in the table");
                return 0;
            }
            if(lbTotal.getText().contains("Total")){
                System.out.println("Found the Total row in the table");
                return i;
            }
            i = i +1;
        }
    }
    public String getClosingValue(){
        List<String> lstValue = tbLedger.getColumn(colRunGBP,true);
        return lstValue.get(lstValue.size() - 1);
    }
}