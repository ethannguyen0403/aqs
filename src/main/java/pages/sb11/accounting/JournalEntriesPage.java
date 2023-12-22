package pages.sb11.accounting;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import objects.Transaction;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.systemmonitoring.ClosingJournalEntriesPage;

public class JournalEntriesPage extends WelcomePage {
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddDebitFrom = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::select[1]");
    public DropDownBox ddCreditTo = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::select[1]");
    public DropDownBox ddDebitLedger = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::select[2]");
    public DropDownBox ddCreditLedger = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::select[2]");
    public DropDownBox ddDebitBookieClient = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::select[3]");
    public DropDownBox ddCreditBookieClient = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::select[3]");
    public DropDownBox ddDebitCurrency = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::select[4]");
    public DropDownBox ddCreditCurrency = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::select[4]");
    public DropDownBox ddDebitLevel = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::select[5]");
    public DropDownBox ddCreditLevel = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::select[5]");
    public TextBox txtDebitAccount = TextBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::input[1]");
    public TextBox txtCreditAccount = TextBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::input[1]");
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
    int colBalance = 5;
    public Table tbDebit = Table.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::table[1]",totalCol);
    public Table tbCredit = Table.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::table[1]",totalCol);
    public Label messageSuccess = Label.xpath("//div[contains(@class, 'message-box')]");
    public Label lblAccountDebit = Label.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::span[text()='Account'][1]");
    public Label lblAccountCredit = Label.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::span[text()='Account'][1]");
    public Label lblRemark = Label.xpath("//span[text()='Remark']");

    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public Link lblClosingJournalEntries = Link.xpath("//span[text()='Closing Journal Entries']");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

//    public void addLedgerTransaction(Transaction trans, boolean isSubmit){
//        //TODO: Assign Johnny: Should revise the input parameters of this function as following step:
//        /* a transaction should include object: Debit(AccountType), Credit(AccountType), Remark(String), Date(String), Transaction Type(String)
//        AccountType is a Parent Object. Ledger, Bookie, Client will be extent AccountType Object
//         */
//
//        filterLedger(true, "Ledger", trans.getLedgerDebit(), true);
//        double debitBalance = Math.abs(Double.parseDouble(tbDebit.getControlOfCell(1, colBalance, 1, null).getText().trim()));
//        System.out.println("Debit Balance is " + debitBalance);
//        trans.setDebitBalance(debitBalance);
//        txtDebitAmount.sendKeys(String.format("%.3f",trans.getAmountDebit()));
//
//        filterLedger(false, "Ledger", trans.getLedgerCredit(), true);
//        double creditBalance = Math.abs(Double.parseDouble(tbCredit.getControlOfCell(1, colBalance, 1, null).getText().trim()));
//        System.out.println("Credit Balance is " + creditBalance);
//        trans.setCreditBalance(creditBalance);
//        txtCreditAmount.sendKeys(String.format("%.3f",trans.getAmountCredit()));
//
//        txtRemark.sendKeys(trans.getRemark());
//        if (!trans.getTransDate().isEmpty()){
//            dtpTrans.selectDate(trans.getTransDate(), "dd/MM/yyyy");
//        }
//        ddTransactionType.selectByVisibleText(trans.getTransType());
//
//        if (isSubmit){
//            btnSubmit.click();
//        }
//    }

    public void addTransaction(Transaction trans, String debitAccountType, String creditAccountType, String remark, String transDate, String transType, boolean isSubmit){
        if (debitAccountType == "Ledger") {
            filterLedger(true, "Ledger", trans.getLedgerDebit(), true);
        } else if (debitAccountType == "Bookie") {
            filterClientBookie("Bookie",true, trans.getBookieDebit(), trans.getLevel(),trans.getDebitAccountCode(),true);
        } else {
            filterClientBookie("Client",true, trans.getClientDebit(), trans.getLevel(),trans.getDebitAccountCode(),true);
        }
        waitSpinnerDisappeared();
        double debitBalance =
                Math.abs(Double.parseDouble(tbDebit.getControlOfCell(1, colBalance, 1, "span").getText().trim().replace(",", "").replace("-","")));
        System.out.println("Debit Balance is " + debitBalance);
        trans.setDebitBalance(debitBalance);
        txtDebitAmount.sendKeys(String.format("%.3f",trans.getAmountDebit()));

        if (creditAccountType == "Ledger") {
            filterLedger(false, "Ledger", trans.getLedgerCredit(), true);
        } else if (creditAccountType == "Bookie") {
            filterClientBookie("Bookie",false, trans.getBookieCredit(), trans.getLevel(),trans.getCreditAccountCode(),true);
        } else {
            filterClientBookie("Client",false, trans.getClientCredit(), trans.getLevel(),trans.getCreditAccountCode(),true);
        }
        waitSpinnerDisappeared();
        double creditBalance =
                Math.abs(Double.parseDouble(tbCredit.getControlOfCell(1, colBalance, 1, "span").getText().trim().replace(",", "")));
        System.out.println("Credit Balance is " + creditBalance);
        trans.setCreditBalance(creditBalance);
        txtCreditAmount.sendKeys(String.format("%.3f",trans.getAmountCredit()));

        txtRemark.sendKeys(remark);
        if (!transDate.isEmpty()){
            dtpTrans.selectDate(transDate, "dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        ddTransactionType.selectByVisibleText(transType);

        if (isSubmit){
            btnSubmit.click();
        }
    }

    private void filterLedger (boolean isDebit,String fromType, String ledgername, boolean isAdd){
        if (isDebit){
            ddDebitFrom.selectByVisibleText(fromType);
            waitSpinnerDisappeared();
            ddDebitFrom.selectByVisibleText(fromType);
            ddDebitLedger.selectByVisibleContainsText(ledgername);
            if(isAdd){
                btnDebitAdd.click();
            }
        } else {
            ddCreditTo.selectByVisibleText(fromType);
            waitSpinnerDisappeared();
            ddCreditLedger.selectByVisibleContainsText(ledgername);
            if(isAdd){
                btnCreditAdd.click();
            }
        }
    }

    public void addClientBookieTransaction(Transaction trans, String fromType, boolean isSubmit){
        filterClientBookie(fromType,true, trans.getClientDebit(), trans.getLevel(),trans.getDebitAccountCode(),true);
        txtDebitAmount.sendKeys(String.format("%.3f",trans.getAmountDebit()));

        filterClientBookie(fromType,false, trans.getClientCredit(), trans.getLevel(),trans.getCreditAccountCode(),true);
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

    public void addBookieClientTransaction(Transaction trans, String fromType, boolean isSubmit){
        filterClientBookie(fromType,true, trans.getBookieDebit(), trans.getLevel(),trans.getDebitAccountCode(),true);
        txtDebitAmount.sendKeys(String.format("%.3f",trans.getAmountDebit()));

        filterClientBookie(fromType,false, trans.getBookieCredit(), trans.getLevel(),trans.getCreditAccountCode(),true);
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

    private void filterClientBookie(String fromType, boolean isDebit, String clientBookieCode, String level, String accountCode, boolean isAdd) {
        if (isDebit) {
            ddDebitFrom.selectByVisibleText(fromType);
            waitSpinnerDisappeared();
            ddDebitLedger.selectByVisibleContainsText(clientBookieCode);
            ddDebitLevel.selectByVisibleText(level);
            txtDebitAccount.sendKeys(accountCode);
            txtDebitAccount.sendKeys(accountCode);
            if (isAdd) {
                btnDebitAdd.click();
            }
        } else {
            ddCreditTo.selectByVisibleText(fromType);
            waitSpinnerDisappeared();
            ddCreditLedger.selectByVisibleContainsText(clientBookieCode);
            ddCreditLevel.selectByVisibleText(level);
            txtCreditAccount.sendKeys(accountCode);
            txtCreditAccount.sendKeys(accountCode);
            if (isAdd) {
                btnCreditAdd.click();
            }
        }

    }

    public ClosingJournalEntriesPage openClosingJournalEntriesPage() {
        lblClosingJournalEntries.click();
        waitSpinnerDisappeared();
        return new ClosingJournalEntriesPage();
    }
}
