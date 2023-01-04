package pages.sb11.generalReports.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;
import objects.Transaction;
import org.testng.Assert;

import static common.SBPConstants.CURRENCY_RATE;

public class LedgerDetailPopup {
    public Label lblTitle = Label.xpath("//app-ledger-detail//span[contains(@class,'font-weight-bolder')]");
    public Button btnClose = Button.xpath("//app-ledger-detail//em[contains(@class,'fa-times')]");
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
    public void close(){
        btnClose.click();
    }

    public Transaction verifyLedgerTrans(Transaction trans, boolean isDebit, String description){
        int startIndex = 1;
        int i = startIndex +1;
        while(true){
            String txnDate = tbLedger.getControlOfCell(1,colDescription,i,null).getText().trim();
            if (isDebit){
                if(txnDate.contains(description))
                    return verifyTransInfoDisplayCorrectInRow(trans, true, i);
                System.out.println(String.format("Skip verify section at row %s because the description is different with %s", i,description));
            } else {
                if(txnDate.contains(description))
                    return verifyTransInfoDisplayCorrectInRow(trans, false, i);
                System.out.println(String.format("Skip verify section at row %s because the description is different with %s", i,description));
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
            Assert.assertTrue(txnDate.contains(transaction.getTransDate()), "Failed! Txn Date is incorrect");
            Assert.assertEquals(debitORG, String.format("%.2f", transaction.getAmountDebit()), "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(runBalORG, String.format("%.2f", transaction.getDebitBalance() + transaction.getAmountDebit()), "Failed! Running Balance ORG amount is incorrect");
            double amountDebitGBP = transaction.getAmountDebit() * CURRENCY_RATE.get(transaction.getLedgerDebitCur());
            double runDebitGBP = (transaction.getDebitBalance() + transaction.getAmountDebit()) * CURRENCY_RATE.get(transaction.getLedgerDebitCur());
            Assert.assertEquals(debitGBP, String.format("%.2f", amountDebitGBP), "Failed! Debit GBP amount is incorrect");
            Assert.assertEquals(runBalGBP, String.format("%.2f", runDebitGBP), "Failed! Running Balance GBP amount is incorrect");
            verifyTransTotal(transaction,true);
        } else {
            Assert.assertTrue(txnDate.contains(transaction.getTransDate()), "Failed! Txn Date is incorrect");
            Assert.assertEquals(creditORG, String.format("%.2f", transaction.getAmountCredit()), "Failed! Credit ORG amount is incorrect");
            Assert.assertEquals(runBalORG, String.format("%.2f", transaction.getCreditBalance() + transaction.getAmountCredit()), "Failed! Running Balance ORG amount is incorrect");
            double amountCreditGBP = transaction.getAmountCredit() * CURRENCY_RATE.get(transaction.getLedgerCreditCur());
            double runCreditGBP = (transaction.getCreditBalance() + transaction.getAmountCredit()) * CURRENCY_RATE.get(transaction.getLedgerCreditCur());
            Assert.assertEquals(creditGBP, String.format("%.2f", amountCreditGBP), "Failed! Credit GBP amount is incorrect");
            Assert.assertEquals(runBalGBP, String.format("%.2f", runCreditGBP), "Failed! Credit Balance GBP amount is incorrect");
            verifyTransTotal(transaction,false);
        }
        Assert.assertEquals(description, transaction.getRemark(), "Failed! Description is incorrect");
        return transaction;
    }

    private Transaction verifyTransTotal(Transaction transaction, boolean isDebit){
        int totalRow = getTotalRow();
        String totalcredORG = tbLedger.getControlOfCell(1,colCredTotalORG,totalRow,null).getText().trim();
        String totaldebORG = tbLedger.getControlOfCell(1,colDebTotalORG,totalRow,null).getText().trim();
        String totalrunORG = tbLedger.getControlOfCell(1,colRunTotalORG,totalRow,null).getText().trim();
        String totalcredGBP = tbLedger.getControlOfCell(1, colCredTotalGBP, totalRow, null).getText().trim();
        String totaldebGBP = tbLedger.getControlOfCell(1, colDebTotalGBP, totalRow, null).getText().trim();
        String totalrunGBP = tbLedger.getControlOfCell(1,colRunTotalGBP,totalRow,null).getText().trim();

        if (isDebit){
            Assert.assertEquals(totalcredORG, "0.00", "Failed! Credit ORG amount is incorrect");
            Assert.assertEquals(totaldebORG, String.format("%.2f", transaction.getAmountDebit()), "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(totalrunORG, String.format("%.2f", transaction.getDebitBalance()), "Failed! Running Balance ORG amount is incorrect");
            double runDebitGBP = transaction.getDebitBalance() * CURRENCY_RATE.get(transaction.getLedgerDebitCur());
            Assert.assertEquals(totalrunGBP, String.format("%.2f", runDebitGBP), "Failed! Running Balance ORG amount is incorrect");
        } else {
            Assert.assertEquals(totalcredORG, String.format("%.2f", transaction.getAmountCredit()), "Failed! Credit ORG amount is incorrect");
            Assert.assertEquals(totaldebORG, "0.00", "Failed! Debit ORG amount is incorrect");
            Assert.assertEquals(totalcredGBP, String.format("%.2f", transaction.getAmountCredit()), "Failed! Credit ORG amount is incorrect");
            double runCreditGBP = transaction.getCreditBalance() * CURRENCY_RATE.get(transaction.getLedgerCreditCur());
            Assert.assertEquals(totaldebGBP, String.format("%.2f", runCreditGBP), "Failed! Debit ORG amount is incorrect");
        }
        return transaction;
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
}