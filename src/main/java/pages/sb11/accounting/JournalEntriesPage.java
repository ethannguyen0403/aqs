package pages.sb11.accounting;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Transaction;
import pages.sb11.WelcomePage;

public class JournalEntriesPage extends WelcomePage {
    public DropDownBox ddDebitFrom = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::select[1]");
    public DropDownBox ddCreditTo = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::select[1]");
    public DropDownBox ddDebitLedger = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::select[2]");
    public DropDownBox ddCreditLedger = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::select[2]");
    public Button btnDebitAdd = Button.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::button[1]");
    public Button btnCreditAdd = Button.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::button[1]");
    public TextBox txtRemark = TextBox.xpath("//app-transaction-creation//span[contains(text(),'Remark')]//following::input[1]");
    public TextBox txtDateTrans = TextBox.xpath("//app-transaction-creation//span[contains(text(),'Remark')]//following::input[2]");
    public DateTimePicker dtpTrans = DateTimePicker.xpath(txtDateTrans,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public DropDownBox ddTransactionType = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Transaction Type')]//following::select[1]");
    public Button btnSubmit = Button.xpath("//app-transaction-creation//span[contains(text(),'Submit')]");
    public TextBox txtDebitAmount = TextBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::table[1]//input");
    public TextBox txtCreditAmount = TextBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::table[1]//input");
    int totalCol = 7;
    public Table tbDebit = Table.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::table[1]",totalCol);
    public Table tbCredit = Table.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::table[1]",totalCol);



    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public void addLedgerTransaction(Transaction trans, boolean isSubmit){
        filterLedger(true, "Ledger", trans.getLedgerDebit(), true);
        txtDebitAmount.sendKeys(String.format("%.3f",trans.getAmountDebit()));

        filterLedger(false, "Ledger", trans.getLedgerCredit(), true);
        txtCreditAmount.sendKeys(String.format("%.3f",trans.getAmountCredit()));

        txtRemark.sendKeys(trans.getRemark());
        if (!trans.getTransDate().isEmpty()){
            dtpTrans.selectDate(trans.getTransDate(), "dd/MM/yyyy");
        }
        ddTransactionType.selectByVisibleText(trans.getTransType());
        if (isSubmit){
            btnSubmit.click();
        }
    }

    private void filterLedger (boolean isDebit,String fromType, String ledgername, boolean isAdd){
        if (isDebit){
            ddDebitFrom.selectByVisibleContainsText(fromType);
            ddDebitLedger.selectByVisibleContainsText(ledgername);
            if(isAdd){
                btnDebitAdd.click();
            }
        } else {
            ddCreditTo.selectByVisibleContainsText(fromType);
            ddCreditLedger.selectByVisibleContainsText(ledgername);
            if(isAdd){
                btnCreditAdd.click();
            }
        }


    }

}
