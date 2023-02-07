package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.ClientSummaryPopup;
import pages.sb11.generalReports.popup.bookiestatement.BookieSummaryPopup;

import java.util.ArrayList;
import java.util.List;

public class BookieStatementPage extends WelcomePage {
    int colTotal = 10;
    public int colLevel = 3;
    public int colOpening = 5;
    public int colWinLoss = 6;
    public int colCommission = 7;
    public int colRecPay = 8;
    public int colMovement = 9;
    public int colClosing = 10;

    Label lblTitle = Label.xpath("//app-bookie-statement//span[contains(@class,'card-header main-box-header')]");
    DropDownBox ddpViewBy = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][1]//select");
    DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-bookie-statement//div[contains(@class,'company-unit')]//select");
    DropDownBox ddpFinancialYear = DropDownBox.xpath("//app-bookie-statement//div[contains(@class,'financial-year')]//select");
    DropDownBox ddpAgentType = DropDownBox.xpath("//app-bookie-statement//div[contains(@class,'agent-type')]//select");
    TextBox txtFromDate = TextBox.xpath("//app-bookie-statement//div[contains(@class,'form-date')]//input");
    TextBox txtToDate = TextBox.xpath("//app-bookie-statement//div[contains(@class,'to-date')]//input");
    TextBox txtBookieCode = TextBox.xpath("//app-bookie-statement//div[contains(@class,'bookie-code')]//input");
    DropDownBox ddpCurrency = DropDownBox.xpath("//app-bookie-statement//div[text()='Currency']//following::select[1]");
    Button btnShow = Button.xpath("//app-bookie-statement//button[contains(@class,'btn-show')]");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");

    Table tblSuper = Table.xpath("//app-client-detail//table[@id='table-super']",colTotal);
    Table tblMaster = Table.xpath("//app-client-detail//table[@id='table-master']",colTotal);
    //Table tblAgent = Table.xpath("//app-client-detail//div[%s]//table[@id='table-agent']",colTotal);

    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public void filter( String companyUnit, String financialYear, String agentType, String fromDate, String toDate, String bookieCode, String currency){
        if(!companyUnit.isEmpty())
            ddpCompanyUnit.selectByVisibleText(companyUnit);
        if(!financialYear.isEmpty())
            ddpFinancialYear.selectByVisibleText(financialYear);
        if(!agentType.isEmpty())
            ddpAgentType.selectByVisibleText(agentType);
        String currentDate = txtFromDate.getAttribute("value");
        if(!fromDate.isEmpty())
            if(!currentDate.equals(fromDate))
                dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        currentDate = txtFromDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        if(!bookieCode.isEmpty())
            txtBookieCode.sendKeys(bookieCode);
        if(!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        btnShow.click();

    }
    public BookieSummaryPopup openSummaryPopup(String agentCode) {
        Label lblAgentCode;
        Label lblFirstColumn;
        int i = 2;
        int j = 1;
        while (true){
            String xpath = String.format("//app-client-detail//div[contains(@class,'col-12')][%s]//table[@id='table-agent']",j);
            Table tblAgent = Table.xpath(xpath,colTotal);
            lblAgentCode = Label.xpath(tblAgent.getxPathOfCell(1,colLevel,i,null));
            lblFirstColumn = Label.xpath(tblAgent.getxPathOfCell(1,1,i,null));
            if(lblFirstColumn.getText().equalsIgnoreCase("Total in")) {
                j = j + 1;
                i = 1;
            }
            if(lblAgentCode.getText().equalsIgnoreCase(agentCode)){
                lblAgentCode.click();
                return new BookieSummaryPopup();
            }
            if(lblAgentCode.getText().equalsIgnoreCase("Grand Total in")) {
                break;
            }
            i = i+1;
        }
        return null;
    }

    public List<String> getMemberSummary(String masterBookie, String accountCode){
        BookieSummaryPopup bookieSummaryPopup = openSummaryPopup(masterBookie);
        return bookieSummaryPopup.getMemeberSummaryData(accountCode);
    }
}
