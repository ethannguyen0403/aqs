package pages.sb11.generalReports;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.bookiestatement.BookieMemberSummaryPopup;
import pages.sb11.generalReports.popup.bookiestatement.BookieSuperMasterDetailPopup;
import pages.sb11.generalReports.popup.bookiestatement.BookieSummaryPopup;

import java.util.List;

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

    public BookieMemberSummaryPopup openBookieMemberSummaryDetailPopup(String masterCode, String agentCode) {
        Label lblSuperMasterCode;
        Label lblAgentCode;
        Label lblMSLink;
        String xpathSuperMaster = String.format("//div[@class='content-filter pt-4 row ng-star-inserted']//div//div[contains(.,' %s ')]", masterCode);
        String xpathAgentCode;
        String xpathMSLink;
        lblSuperMasterCode = Label.xpath(xpathSuperMaster);
        if (!lblSuperMasterCode.isDisplayed()) {
            System.out.println("Cannot find out Master in list of result table: " + masterCode);
            return null;
        } else {
            int i = 1;
            while (true) {
                xpathAgentCode = xpathSuperMaster + "//.." + tblSummary.getxPathOfCell(1, colAgentCode, i, null) + "//a";
                lblAgentCode = Label.xpath(xpathAgentCode);
                if (lblAgentCode.getText().equalsIgnoreCase(agentCode)) {
                    xpathMSLink = xpathSuperMaster + "//.." + tblSummary.getxPathOfCell(1, colMSlink, i, null) + "//a";
                    lblMSLink = Label.xpath(xpathMSLink);
                    lblMSLink.click();
                    return new BookieMemberSummaryPopup();
                }
            }
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

    public BookieMemberSummaryPopup openSummaryPopup(String agentCode) {
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
                return new BookieMemberSummaryPopup();
            }
            if(lblAgentCode.getText().equalsIgnoreCase("Grand Total in")) {
                break;
            }
            i = i+1;
        }
        return null;
    }

    public List<String> getMemberSummary(String masterCode ,String masterBookie, String accountCode){
        BookieMemberSummaryPopup bookieSummaryPopup = openBookieMemberSummaryDetailPopup(masterBookie,accountCode);
        return bookieSummaryPopup.getMemeberSummaryData(accountCode);
    }
}
