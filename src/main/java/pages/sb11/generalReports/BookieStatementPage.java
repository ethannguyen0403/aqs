package pages.sb11.generalReports;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.BookieMemberSummaryPopup;
import pages.sb11.generalReports.popup.BookieSuperMasterDetailPopup;

public class BookieStatementPage extends WelcomePage {
    protected String _xpathTable = null;
    int totalColSummary = 10;
    public int colMasterTotal = 4;
    int colAgentCode = 2;
    int colMSlink = 9;
    public int colMember = 10;
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-bookie-statement//div[@class='company-unit pl-3']//select");
    DropDownBox ddpFinancialYear = DropDownBox.xpath("//app-bookie-statement//div[@class='financial-year pl-3']//select");
    DropDownBox ddpAgentType = DropDownBox.xpath("//app-bookie-statement//div[@class='agent-type pl-3']//select");
    TextBox txtFromDate = TextBox.name("fromDate");
    TextBox txtToDate = TextBox.name("toDate");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");

    TextBox txtBookieCode = TextBox.xpath("//app-bookie-statement//div[@class='bookie-code pl-3']//input");
    Button btnShowBookie = Button.xpath("//app-bookie-statement//div[@class='show-bookie pl-3']//a");
    Button btnConfirmShowBookie = Button.xpath("//app-bookie-statement//div[@class='list-account-bookie ng-star-inserted']//button[text()='Show']");
    DropDownBox ddpCurrency = DropDownBox.xpath("//app-bookie-statement//div[@class='pl-3']//select");
    Button btnShow = Button.xpath("//app-bookie-statement//div[@class='pl-3']//button[text()='Show']");
    Table tblSummary = Table.xpath("//table[@aria-label='table']", totalColSummary);
    Icon searchIcon = Icon.xpath("//app-bookie-statement//em[@class = 'fas fa-search']");

    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public void filter(String companyUnit, String financialYear, String agentType, String fromDate, String toDate, String bookieCode) throws InterruptedException {
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpFinancialYear.selectByVisibleText(financialYear);
        ddpAgentType.selectByVisibleText(agentType);
        if(!fromDate.isEmpty())
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        if(!toDate.isEmpty())
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        //btnShowBookie.click();
        txtBookieCode.sendKeys(bookieCode);
        searchIcon.click();
        //filterBookie(bookieCode);
        btnConfirmShowBookie.click();
        waitSpinnerDisappeared();
        //btnShow.click();
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
}
