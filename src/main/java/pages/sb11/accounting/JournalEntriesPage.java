package pages.sb11.accounting;

import com.paltech.element.common.*;
import com.paltech.utils.DateUtils;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Table;
import objects.Transaction;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.systemmonitoring.ClosingJournalEntriesPage;
import pages.sb11.popup.ConfirmPopup;
import utils.sb11.CompanySetUpUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JournalEntriesPage extends WelcomePage {
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddDebitFrom = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::span[text()='From']//following-sibling::select");
    public DropDownBox ddCreditTo = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::span[text()='To']//following-sibling::select");
    public DropDownBox ddDebitLedger = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::span[text()='Ledger']//following-sibling::select");
    public DropDownBox ddCreditLedger = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::span[text()='Ledger']//following-sibling::select");
    public DropDownBox ddDebitBookieClient = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::span[text()='Bookie']//following-sibling::select | //app-transaction-creation//span[contains(text(),'Debit')]//following::span[text()='Client']//following-sibling::select");
    public DropDownBox ddCreditBookieClient = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::span[text()='Bookie']//following-sibling::select | //app-transaction-creation//span[contains(text(),'Credit')]//following::span[text()='Client']//following-sibling::select");
    public DropDownBox ddDebitCurrency = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::span[text()='Currency']//following-sibling::select");
    public DropDownBox ddCreditCurrency = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::span[text()='Currency']//following-sibling::select");
    public DropDownBox ddDebitLevel = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Debit')]//following::span[text()='Level']//following-sibling::select");
    public DropDownBox ddCreditLevel = DropDownBox.xpath("//app-transaction-creation//span[contains(text(),'Credit')]//following::span[text()='Level']//following-sibling::select");
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

    public void addTransaction(Transaction trans, String debitAccountType, String creditAccountType, String remark, String transDate, String transType, boolean isSubmit,boolean isConfirm){
        if (debitAccountType == "Ledger") {
            filterLedger(true, "Ledger", trans.getLedgerDebit(), true);
        } else if (debitAccountType == "Bookie") {
            filterClientBookie("Bookie",true, trans.getBookieDebit(), trans.getLevel(),trans.getDebitAccountCode(),true);
        } else {
            filterClientBookie("Client",true, trans.getClientDebit(), trans.getLevel(),trans.getDebitAccountCode(),true);
        }
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
        if (isConfirm){
            ConfirmPopup confirmPopup = new ConfirmPopup();
            confirmPopup.btnOK.click();
        }
    }

    private void filterLedger (boolean isDebit,String fromType, String ledgername, boolean isAdd){
        if (isDebit){
            ddDebitFrom.selectByVisibleText(fromType);
            waitSpinnerDisappeared();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ddDebitLedger.selectByVisibleText(ledgername);
            if(isAdd){
                btnDebitAdd.click();
            }
        } else {
            ddCreditTo.selectByVisibleText(fromType);
            waitSpinnerDisappeared();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ddCreditLedger.selectByVisibleText(ledgername);
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
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ddDebitBookieClient.selectByVisibleText(clientBookieCode);
            waitSpinnerDisappeared();
            ddDebitLevel.selectByVisibleText(level);
            waitSpinnerDisappeared();
            txtDebitAccount.sendKeys(accountCode);
            txtDebitAccount.sendKeys(accountCode);
            if (isAdd) {
                btnDebitAdd.click();
            }
        } else {
            ddCreditTo.selectByVisibleText(fromType);
            waitSpinnerDisappeared();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ddCreditBookieClient.selectByVisibleText(clientBookieCode);
            waitSpinnerDisappeared();
            ddCreditLevel.selectByVisibleText(level);
            waitSpinnerDisappeared();
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

    /**
     *
     * @param expectedDay
     * @param expectedMonth
     * @param format input pattern of Month and Year
     * @return
     */
    public String getDayOfMonth(int expectedDay, int expectedMonth, String format) {
        Calendar cal  = Calendar.getInstance();
        cal.add(Calendar.MONTH, expectedMonth);
        SimpleDateFormat s = new SimpleDateFormat(format);
        return String.format("%s/%s",expectedDay,s.format(new Date(cal.getTimeInMillis())));
    }

    public void verifyUI(String clientCode) {
        System.out.println("Dropdown: From/To, Client, Bookie, Ledger, Currency, Level and Transaction Type");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed");
        Assert.assertEquals(ddDebitFrom.getOptions(), SBPConstants.JournalEntries.TYPE_LIST,"Failed! Debit type list dropdown is not displayed");
        Assert.assertEquals(ddCreditTo.getOptions(), SBPConstants.JournalEntries.TYPE_LIST,"Failed! Credit type list dropdown is not displayed");
        Assert.assertTrue(ddCreditBookieClient.getOptions().contains(clientCode),"Failed! Credit Client dropdown is not displayed");
        Assert.assertTrue(ddDebitBookieClient.getOptions().contains(clientCode),"Failed! Debit Client dropdown is not displayed");
        Assert.assertEquals(ddDebitLevel.getOptions(), SBPConstants.JournalEntries.LEVEL_LIST,"Failed! Debit Level dropdown is not displayed");
        Assert.assertEquals(ddCreditLevel.getOptions(), SBPConstants.JournalEntries.LEVEL_LIST,"Failed! Credit Level dropdown is not displayed");
        Assert.assertEquals(ddDebitCurrency.getOptions(), SBPConstants.JournalEntries.CURRENCY_LIST,"Failed! Credit Bookie dropdown is not displayed");
        Assert.assertEquals(ddCreditCurrency.getOptions(), SBPConstants.JournalEntries.CURRENCY_LIST,"Failed! Debit Bookie dropdown is not displayed");
        Assert.assertEquals(ddTransactionType.getOptions(), SBPConstants.JournalEntries.TRANSACTION_TYPE_LIST,"Failed! Debit Bookie dropdown is not displayed");
        System.out.println("Textbox: Account, Remark");
        Assert.assertEquals(lblAccountDebit.getText(),"Account","Failed! Account Debit textbox is not displayed");
        Assert.assertEquals(lblAccountCredit.getText(),"Account","Failed! Account Credit textbox is not displayed");
        Assert.assertEquals(lblRemark.getText(),"Remark","Failed! Remark textbox is not displayed");
        System.out.println("Datetimepicker: Transaction Date");
        Assert.assertTrue(txtDateTrans.isDisplayed(),"Failed! Transaction Date is not displayed!");
        System.out.println("Button: Add (Debit), Add (Credit) and Submit button");
        Assert.assertEquals(btnDebitAdd.getText(),"Add","Failed! Add Debit button is not displayed");
        Assert.assertEquals(btnCreditAdd.getText(),"Add","Failed! Add Debit button is not displayed");
        Assert.assertEquals(btnSubmit.getText(),"Submit","Failed! Submit button is not displayed");
    }
}
