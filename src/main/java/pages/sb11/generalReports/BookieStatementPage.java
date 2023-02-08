package pages.sb11.generalReports;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import javafx.scene.input.KeyCode;
import org.openqa.selenium.Keys;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.bookiestatement.BookieMemberSummaryPopup;
import pages.sb11.generalReports.popup.bookiestatement.BookieSuperMasterDetailPopup;

import java.util.List;
import java.util.Objects;

public class BookieStatementPage extends WelcomePage {
    protected String _xpathTable = null;
    int totalColSummary = 10;
    public int colMasterTotal = 4;
    int colAgentCode = 2;
    int colMSlink = 9;
    public int colMember = 10;
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

    Button btnShowBookie = Button.xpath("//app-bookie-statement//div[@class='show-bookie pl-3']//a");
    Button btnConfirmShowBookie = Button.xpath("//app-bookie-statement//div[@class='list-account-bookie ng-star-inserted']//button[text()='Show']");
    Table tblSummary = Table.xpath("//table[@aria-label='table']", totalColSummary);
    Icon searchIcon = Icon.xpath("//app-bookie-statement//em[@class = 'fas fa-search']");

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
        currentDate = txtToDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        if(!bookieCode.isEmpty()) {
            txtBookieCode.sendKeys(bookieCode);
            txtBookieCode.sendKeys(Keys.ENTER);
        }
        if(!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        btnShow.click();

    }

    private void filterBookie(String bookieCode) throws InterruptedException {
        int i = 1;
        while (true) {
            Label lblSelectValue = Label.xpath(String.format("//div[@class='list-item-filter pl-5 pt-3']//div[%s]//label",i));
            if (!lblSelectValue.isDisplayed()) {
                System.out.println("Cannot find out Bookie Code in list of Bookies: " + bookieCode);
                break;
            }
            if(lblSelectValue.getText().equalsIgnoreCase(bookieCode)) {
                CheckBox cbBookieCode = CheckBox.xpath(String.format("//div[@class='list-item-filter pl-5 pt-3']//div[%s]//input",i));
                cbBookieCode.click();
                btnConfirmShowBookie.click();
                waitSpinnerDisappeared();
                break;
            }
            i = i + 1;
        }
    }

    public BookieSuperMasterDetailPopup openBookieSuperMasterDetailPopup(String superMasterCode) {
        Label lblSuperMasterCode;
        String xpath = String.format("//div[@class='content-filter pt-4 row ng-star-inserted']//div//div[contains(.,' %s ')]", superMasterCode);
        lblSuperMasterCode = Label.xpath(xpath);
        if (!lblSuperMasterCode.isDisplayed()) {
            System.out.println("Cannot find out Super Master in list of result table: " + superMasterCode);
            return null;
        } else {
            xpath = xpath + "//..//a[contains(text(),' Super Master R/P/C/RB/A ')]";
            lblSuperMasterCode = Label.xpath(xpath);
            lblSuperMasterCode.click();
            return new BookieSuperMasterDetailPopup();
        }
    }


    public String getSuperMasterCellValue (String superMasterCode, int colIndex) {
        Label lblSuperMasterCode;
        Label lblCellVal;
        String xpath = String.format("//div[@class='content-filter pt-4 row ng-star-inserted']//div//div[contains(.,' %s ')]",superMasterCode);
        lblSuperMasterCode = Label.xpath(xpath);
        if(!lblSuperMasterCode.isDisplayed()) {
            System.out.println("Cannot find out Super Master in list of result table: " + superMasterCode);
            return null;
        } else {
            xpath = xpath + "//.." + tblSummary.getxPathOfCell(1, colIndex, 1, null);
            lblCellVal = Label.xpath(xpath);
            return lblCellVal.getText();
        }
    }

    public String getAgentCellValue (String masterCode, String agentCode, int colIndex) {
        Label lblMasterCode;
        Label lblAgentCode;
        Label lblCellVal;
        String xpath = String.format("//div[@class='content-filter pt-4 row ng-star-inserted']//div//div[contains(.,' %s ')]",masterCode);
        lblMasterCode = Label.xpath(xpath);
        if(!lblMasterCode.isDisplayed()) {
            System.out.println("Cannot find out Master in list of result table: " + masterCode);
            return null;
        } else {
            String xpathAgentCode;
            int i = 1;
            while (true) {
                xpathAgentCode = xpath + "//.." + tblSummary.getxPathOfCell(1, colAgentCode, i, null) + "//a";
                lblAgentCode = Label.xpath(xpathAgentCode);
                if (lblAgentCode.getText().equalsIgnoreCase(agentCode)) {
                    xpath = xpath + "//.." + tblSummary.getxPathOfCell(1, colIndex, i, null);
                    lblCellVal = Label.xpath(xpath);
                    return lblCellVal.getText();
                }
                i+=i;
            }
        }
    }

    /**
     * This action click on MS label to open the popup General Reports>Bookie Statement>Agent Summary>Member Summary of according account code
     * For example File the table title contains value MA-QA1NS-QA Test and click on MS cell of the row contains the account A-QA1NS01-QA Test
     * @param masterCode it can be Master Code or Agent Code, the account display on the table Title: MA-QA1NS-QA Test e.g QA Bookie >> MA-QA1NS-QA Test
     * @param agentCode it is the account in the table: A-QA1NS01-QA Test
     * @return  General Reports>Bookie Statement>Agent Summary>Member Summary popup
     */
    public BookieMemberSummaryPopup openBookieMemberSummaryDetailPopup(String masterCode, String agentCode) {
        ////app-bookie-statement//div[contains(@class,'content-filter')][2]
        Table tblBookie = getTableContainsBookieAccount(masterCode);
        if(Objects.isNull(tblBookie)) {
            System.out.println("DEBUG!: NO table have the title contains account "+ masterCode);
            return null;
        }
        tblBookie.getControlBasedValueOfDifferentColumnOnRow(agentCode,1,colAgentCode,1,null,colMSlink,null,true,false).click();
        waitSpinnerDisappeared();
        return new BookieMemberSummaryPopup();
    }

    private Table getTableContainsBookieAccount(String account){
        int tableIndex = 1;
        String xpathFilterTable;// ="//app-bookie-statement//div[contains(@class,'content-filter')][1]/div[%s]";
        Label lblTableTitle;
        while (true) {
            xpathFilterTable = String.format("//app-bookie-statement//div[contains(@class,'content-filter')][1]/div[%s]", tableIndex);
            lblTableTitle = Label.xpath(String.format("%s//div[contains(@class,'title-filter')]", xpathFilterTable));
            if (!lblTableTitle.isDisplayed())
                return null;
            if (lblTableTitle.getText().contains(account))
                return  Table.xpath(String.format("%s//table", xpathFilterTable), colTotal);
            tableIndex = tableIndex + 1;
        }
    }
    public List<String> getDataRowofPlayer(String masterCode , String agentCode, String playerAccount){
        BookieMemberSummaryPopup bookieSummaryPopup = openBookieMemberSummaryDetailPopup(masterCode,agentCode);
        return bookieSummaryPopup.getDataRowofPlayer(playerAccount);
    }

    public String getWinLossofPlayer(String masterCode , String agentCode, String playerAccount){
        BookieMemberSummaryPopup bookieSummaryPopup = openBookieMemberSummaryDetailPopup(masterCode,agentCode);
        int winlosCol = bookieSummaryPopup.colWinLosePlayer;
        String winloss =  bookieSummaryPopup.getDataRowofPlayer(playerAccount).get(winlosCol-1);
        bookieSummaryPopup.closePopup();
        return winloss;
    }
}
