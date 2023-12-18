package pages.sb11.accounting;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Transaction;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.sb11.CurrencyRateUtils;

public class JournalReportsPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//span[text()='Company Unit']//following::select[1]");
    public DropDownBox ddpDateType = DropDownBox.xpath("//span[text()='Date Type']//following::select[1]");
    public DropDownBox ddpAccountType = DropDownBox.xpath("//span[text()='Account Type']//following::select[1]");
    public DropDownBox ddpClientBookieLedger = DropDownBox.xpath("//select[@formcontrolname = 'client']");
    public DropDownBox ddpTransactionType = DropDownBox.xpath("//span[(text()='Transaction Type')]//following::select[1]");
    public DropDownBox ddpDetailType = DropDownBox.xpath("//span[(text()='Detail Type')]//following::select[1]");
    public TextBox txtAccountName = TextBox.xpath("//app-journal-reports//span[text() = 'Account Name']//following::input[1]");
    public TextBox txtFromDate = TextBox.xpath("//app-journal-reports//span[text() = 'From Date']//following::input[1]");
    public TextBox txtToDate = TextBox.xpath("//app-journal-reports//span[text() = 'To Date']//following::input[1]");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public Button btnSearch = Button.xpath("//button[@type='submit']");

    public Table tbJournalReport = Table.xpath("//app-journal-reports//table[@id='journal-reports']",14);
    public Label lblFromDate = Label.xpath("//app-journal-reports//span[text() = 'From Date']");
    public Label lblToDate = Label.xpath("//app-journal-reports//span[text() = 'To Date']");
    public Label lblAccountName = Label.xpath("//app-journal-reports//span[text() = 'Account Name']");

    int colAccName = 8;
    int colTransType = 2;
    int colDes = 7;
    int colCur = 10;
    int colAmountDebit = 11;
    int colAmountCredit = 12;
    int colAmountDebitHKD = 13;
    int colAmountCreditHKD = 14;

    public void filterReports(String companyUnit, String dateType, String fromDate, String toDate, String accountType, String clientBookieLedger, String transactionType, String accountName){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpDateType.selectByVisibleText(dateType);
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate, "dd/MM/yyyy");
        }
        if (!accountType.isEmpty()){
            ddpAccountType.selectByVisibleText(accountType);
        }
        if (!clientBookieLedger.isEmpty()){
            ddpClientBookieLedger.selectByVisibleText(clientBookieLedger);
        }
        if (!transactionType.isEmpty()){
            ddpTransactionType.selectByVisibleText(transactionType);
        }
        txtAccountName.sendKeys(accountName);
        btnSearch.click();
    }

    public Transaction verifyTxn(Transaction trans, boolean isDebit){
        int rowIndex = getAccountRowIndex(trans.getLedgerDebit(),trans.getRemark());
        String accountName = tbJournalReport.getControlOfCell(1,colAccName,rowIndex,null).getText();
        String transType = tbJournalReport.getControlOfCell(1,colTransType,rowIndex,null).getText();
        String description = tbJournalReport.getControlOfCell(1,colDes,rowIndex,null).getText();
        String cur = tbJournalReport.getControlOfCell(1,colCur,rowIndex,null).getText().trim();
        String amountDebit = tbJournalReport.getControlOfCell(1,colAmountDebit,rowIndex,null).getText().trim();
        String amountCredit = tbJournalReport.getControlOfCell(1,colAmountCredit,rowIndex,null).getText().trim();
        String amountDebitHKD = tbJournalReport.getControlOfCell(1,colAmountDebitHKD,rowIndex,null).getText().trim();
        String amountCreditHKD = tbJournalReport.getControlOfCell(1,colAmountCreditHKD,rowIndex,null).getText().trim();

        Assert.assertTrue(transType.contains(trans.getTransType()),"Failed! Transaction Type is incorrect");
        Assert.assertTrue(description.contains(trans.getRemark()),"Failed! Description is incorrect");
        if (isDebit){
            double curDebitRate = Double.parseDouble(CurrencyRateUtils.getOpRate("1",trans.getLedgerDebitCur()));
            double debitHKD = trans.getAmountDebit() * curDebitRate;

            Assert.assertTrue(accountName.contains(trans.getLedgerDebit()),"Failed! Account name is incorrect");
            Assert.assertTrue(cur.contains(trans.getLedgerDebitCur()),"Failed! Debit currency is incorrect");
            Assert.assertEquals(amountDebit, String.format("%.2f", trans.getAmountDebit()), "Failed! Foreign Debit amount is incorrect");
            Assert.assertEquals(amountDebitHKD, String.format("%.2f", debitHKD), "Failed! Debit in HKD amount is incorrect");
        } else {
            double curCreditRate = Double.parseDouble(CurrencyRateUtils.getOpRate("1",trans.getLedgerCreditCur()));
            double creditHKD = trans.getAmountDebit() * curCreditRate;

            Assert.assertTrue(accountName.contains(trans.getLedgerCredit()),"Failed! Account name is incorrect");
            Assert.assertTrue(cur.contains(trans.getLedgerCreditCur()),"Failed! Debit currency is incorrect");
            Assert.assertEquals(amountDebit, String.format("%.2f", trans.getAmountCredit()), "Failed! Foreign Credit amount is incorrect");
            Assert.assertEquals(amountDebitHKD, String.format("%.2f", creditHKD), "Failed! Credit in HKD amount is incorrect");
        }

        return trans;
    }


    private int getAccountRowIndex(String accountName, String transDes){
        int lstRow = tbJournalReport.getNumberOfRows(false,false);
        Label lblAccName,lblTransDes;
        for (int i = 1; i < lstRow; i++){
            lblAccName = Label.xpath(tbJournalReport.getxPathOfCell(1,colAccName,i,null));
            lblTransDes = Label.xpath(tbJournalReport.getxPathOfCell(1,colDes,i,null));
            if(!lblAccName.isDisplayed()) {
                continue;
            }
            if(lblAccName.getText().contains(accountName) && lblTransDes.getText().contains(transDes)){
                System.out.println("Found the account name "+accountName+" in the table");
                return i;
            }
        }
        System.out.println("Can NOT found the account name "+accountName+" in the table");
        return 0;
    }
}
