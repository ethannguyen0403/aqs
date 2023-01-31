package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Transaction;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.LedgerDetailPopup;

import static common.SBPConstants.*;
import static testcases.BaseCaseAQS.welcomePage;

public class LedgerStatementPage extends WelcomePage {
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Financial Year')]//following::select[1]");
    public DropDownBox ddLedgerGroup = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Detail Type')]//following::select[1]");
    public DropDownBox ddLedgerName = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Account Type')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public Button btnShow = Button.xpath("//app-ledger-statement//button[contains(text(),'Show')]");
    int totalCol = 8;
    int colLedger = 2;
    int colCur = 3;
    int colAmountORG = 4;
    int colRunBalORG = 5;
    int colRunBalCTORG = 6;
    int colAmountGBP = 8;
    int colRunBalGBP = 9;
    public Table tbLedger = Table.xpath("//app-ledger-statement//table",totalCol);

    Label lblTitle = Label.xpath("//div[contains(@class,'header-filter')]//span[1]");
    public String getTitlePage () {return lblTitle.getText().trim();}

    public void showLedger (String companyUnit, String financialYear, String accountType, String ledgerGroup, String fromDate, String toDate){
        ddCompanyUnit.selectByVisibleText(companyUnit);
        ddFinancialYear.selectByVisibleText(financialYear);
        ddLedgerName.selectByVisibleContainsText(accountType);
        ddLedgerGroup.selectByVisibleContainsText(ledgerGroup);
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate, "dd/MM/yyyy");
        }
        btnShow.click();
    }

    public Transaction verifyLedgerTrans(Transaction trans, boolean isDebit, String ledgerGroup){
        int startIndex = getStartRowWithLedgerGroup(ledgerGroup);
        int i = startIndex +1;
        while(true){
            String ledgerAccount = tbLedger.getControlOfCell(1,colLedger,i,null).getText().trim();
            if(ledgerAccount.contains(trans.getLedgerDebit())){
                System.out.println(String.format("Found transaction %s at row %s", ledgerAccount,i));
                if (isDebit)
                    return verifyTransactionDisplayCorrectInRow(trans, true, i);
                return verifyTransactionDisplayCorrectInRow(trans, false, i);
            }
            i = i +1;
        }
    }

    private Transaction verifyTransactionDisplayCorrectInRow(Transaction transaction, boolean isDebit, int rowIndex){
        // TODO: Johnny Use API to get OP Rate of according currency instead of init data in Constant class
        String ledgerAccount = tbLedger.getControlOfCell(1, colLedger, rowIndex, null).getText().trim();
        String cur = tbLedger.getControlOfCell(1, colCur, rowIndex, null).getText().trim();
        String amountORG = tbLedger.getControlOfCell(1, colAmountORG, rowIndex, null).getText().trim();
        String amountGBP = tbLedger.getControlOfCell(1, colAmountGBP, rowIndex, null).getText().trim();
        String runBalORG = tbLedger.getControlOfCell(1, colRunBalORG,rowIndex,null).getText().trim();
        String runBalCTORG = tbLedger.getControlOfCell(1,colRunBalCTORG,rowIndex,null).getText().trim();
        String runBalGBP = tbLedger.getControlOfCell(1, colRunBalGBP,rowIndex,null).getText().trim();

        if (isDebit){
            Assert.assertTrue(ledgerAccount.contains(transaction.getLedgerDebit()), "Failed! Account code is incorrect");
            Assert.assertEquals(amountORG, String.format("%.2f", transaction.getAmountDebit()), "Failed! Credit/Debit ORG amount is incorrect");
            Assert.assertEquals(runBalORG, String.format("%.2f", transaction.getDebitBalance() + transaction.getAmountDebit()), "Failed! Debit Balance ORG amount is incorrect");
            Assert.assertEquals(runBalCTORG, String.format("%.2f", transaction.getDebitBalance() + transaction.getAmountDebit()), "Failed! Debit Balance CT ORG amount is incorrect");
            double amountDebitGBP = transaction.getAmountDebit() * CURRENCY_RATE.get(transaction.getLedgerDebitCur());
            double runDebitGBP = (transaction.getDebitBalance() + transaction.getAmountDebit()) * CURRENCY_RATE.get(transaction.getLedgerDebitCur());
            Assert.assertEquals(amountGBP, String.format("%.2f", amountDebitGBP), "Failed! Credit/Debit GBP amount is incorrect");
            Assert.assertEquals(runBalGBP, String.format("%.2f", runDebitGBP), "Failed! Running Balance GBP amount is incorrect");
            Assert.assertEquals(cur, transaction.getLedgerDebitCur(), "Failed! Debit Currency is incorrect is in correct");
        } else {
            Assert.assertTrue(ledgerAccount.contains(transaction.getLedgerCredit()), "Failed! Account code is incorrect");
            Assert.assertEquals(amountORG, String.format("%.2f", transaction.getAmountCredit()), "Failed! Credit/Debit ORG amount is incorrect");
            Assert.assertEquals(runBalORG, String.format("%.2f", transaction.getCreditBalance() + transaction.getAmountCredit()), "Failed! Credit Balance ORG amount is incorrect");
            Assert.assertEquals(runBalCTORG, String.format("%.2f", transaction.getCreditBalance() + transaction.getAmountCredit()), "Failed! Debit Balance CT ORG amount is incorrect");
            double amountCreditGBP = transaction.getAmountCredit() * CURRENCY_RATE.get(transaction.getLedgerCreditCur());
            double runCreditGBP = (transaction.getCreditBalance() + transaction.getAmountCredit()) * CURRENCY_RATE.get(transaction.getLedgerCreditCur());
            Assert.assertEquals(amountGBP, String.format("%.2f", amountCreditGBP), "Failed! Credit/Debit ORG amount is incorrect");
            Assert.assertEquals(runBalGBP, String.format("%.2f", runCreditGBP), "Failed! Credit Balance ORG amount is incorrect");
            Assert.assertEquals(cur, transaction.getLedgerCreditCur(), "Failed! Credit Currency is incorrect is in correct");
        }
        return transaction;
    }

    public LedgerDetailPopup openLedgerDetail (String ledgerName){
        int rowIndex = getLedgerRowIndex(ledgerName);
        tbLedger.getControlOfCell(1,colLedger, rowIndex,"a").click();
        return new LedgerDetailPopup();
    }

    public double getCreditDebitAmount(String ledgerName){
        int rowIndex = getLedgerRowIndex(ledgerName);
        String amountCreDeb = tbLedger.getControlOfCell(1, colAmountORG, rowIndex, null).getText().trim();
        if (amountCreDeb.isEmpty()){
            amountCreDeb = String.valueOf(0);
        }
        return Double.parseDouble(amountCreDeb);
    }

    private int getLedgerRowIndex(String ledgername){
        int i = 1;
        Label lblLedger;
        while (true){
            lblLedger = Label.xpath(tbLedger.getxPathOfCell(1,colLedger,i,null));
            if(!lblLedger.isDisplayed()) {
                System.out.println("Can NOT found the ledger name "+ledgername+" in the table");
                return 0;
            }
            if(lblLedger.getText().contains(ledgername)){
                System.out.println("Found the ledger name "+ledgername+" in the table");
                return i;
            }
            i = i +1;
        }
    }

    private int getStartRowWithLedgerGroup(String ledgerGroup){
        int i = 1;
        Label lblLedgerGroup;
        while (true){
            lblLedgerGroup = Label.xpath(tbLedger.getxPathOfCell(1,1,i,null));
            if(!lblLedgerGroup.isDisplayed()){
                System.out.println("The ledger group "+ledgerGroup+" does not display in the list");
                return 0;
            }
            if(lblLedgerGroup.getText().contains(ledgerGroup))
                return i;
            i = i +1;
        }
    }
}
