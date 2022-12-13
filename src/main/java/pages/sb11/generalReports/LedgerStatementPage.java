package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import objects.Order;
import objects.Transaction;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import static common.ESSConstants.HomePage.EN_US;

public class LedgerStatementPage extends WelcomePage {
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Financial Year')]//following::select[1]");
    public DropDownBox ddLedgerGroup = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Ledger Group')]//following::select[1]");
    public DropDownBox ddLedgerName = DropDownBox.xpath("//app-ledger-statement//div[contains(text(),'Account Type')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public Button btnShow = Button.xpath("//app-ledger-statement//button[contains(text(),'Show')]");
    int totalCol = 8;
    int colLedger = 2;
    int colCur = 3;
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

    public Transaction verifyLedgerTrans(Transaction trans, String ledgerGroup){
        int startIndex = getStartRowWithLedgerGroup(ledgerGroup);
        int i = startIndex +1;
        while(true){
            String ledgerAccount = tbLedger.getControlOfCell(1,colLedger,i,null).getText().trim();
            if(ledgerAccount.equals(trans.getLedgerDebit()))
                return verifyOrderInfoDisplayCorrectInRow(trans,i);
            System.out.println(String.format("Skip verity section at row %s because the account code is different with %s", i,ledgerAccount));
            i = i +1;
        }
    }

    private Transaction verifyOrderInfoDisplayCorrectInRow(Transaction transaction, int rowIndex){
        String ledgerAccount = tbLedger.getControlOfCell(1, colLedger, rowIndex, null).getText().trim();
        String cur = tbLedger.getControlOfCell(1, colCur, rowIndex, null).getText().trim();

        Assert.assertEquals(ledgerAccount, transaction.getLedgerDebit(), "Failed! Account code is incorrect");
        Assert.assertEquals(cur, transaction.getLedgerDebitCur(), "Failed! Cur is incorrect is in correct");
        return transaction;
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
            if(lblLedgerGroup.getText().equalsIgnoreCase(ledgerGroup))
                return i;
            i = i +1;
        }
    }
}
