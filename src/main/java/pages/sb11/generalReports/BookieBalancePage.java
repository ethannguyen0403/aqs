package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.bookiebalance.BalanceDetailPage;
import pages.sb11.generalReports.clientbalance.ClientBalanceDetailPage;
import pages.sb11.generalReports.popup.bookieBalance.BookieBalanceDetailPopup;


public class BookieBalancePage extends WelcomePage {
    DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]/parent::div//select");
    DropDownBox ddFinancial = DropDownBox.xpath("//div[contains(text(),'Financial Year')]/parent::div//select");
    TextBox txtDate = TextBox.name("fromDate");
    DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container");
    TextBox txtBookieCode = TextBox.xpath("//div[contains(text(),'Bookie Code')]/parent::div//input");
    public DropDownBox ddShowTotalIn = DropDownBox.xpath("//div[contains(text(),'Show Total In')]/parent::div//select");
    Button btnShow = Button.xpath("//button[text()='Show']");
    public int colBookie = 2;
    public int colTotalInHKD = 3;
    public Table tblBookie = Table.xpath("(//table)[1]",3);
    public Table tblCurrency = Table.xpath("(//table)[2]",4);
    public int colBookieName = 2;
    public Table tblBookieBalance = Table.xpath("//div[@id='bookie-balance-summary']//table[not(contains(@style,' '))]", 3);
    public DropDownBox ddShowTotal = DropDownBox.xpath("//div[contains(text(),'Show Total')]//following::select[1]");
    public void filter(String companyUnit, String financialYear, String date, String bookieCode, String showTotalIn) {
        if(!companyUnit.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if(!financialYear.isEmpty()){
            ddFinancial.selectByVisibleText(financialYear);
        }
        if(!date.isEmpty()){
            dtpDate.selectDate(date,"dd/MM/yyyy");
        }
        if(!bookieCode.isEmpty()){
            txtBookieCode.sendKeys(bookieCode);
        }
        if(!showTotalIn.isEmpty()){
            ddShowTotalIn.selectByVisibleText(showTotalIn);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public BalanceDetailPage openBalanceDetailByBookie(String bookieName){
        filter("","","",bookieName.split("-")[0].trim(),"");
        tblBookie.getControlOfCell(1,colBookie,1,null).click();
        DriverManager.getDriver().switchToWindow();
        return new BalanceDetailPage();
    }
    public BookieBalanceDetailPopup goToBookieDetail(int index){
        tblBookieBalance.getControlOfCell(1,colBookieName,index,null).click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new BookieBalanceDetailPopup();
    }
}