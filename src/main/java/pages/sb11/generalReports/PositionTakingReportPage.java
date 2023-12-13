package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import controls.DateTimePicker;
import controls.Table;
import controls.sb11.AppArlertControl;
import org.openqa.selenium.By;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.positionTakingReport.AccountPopup;
import utils.sb11.CurrencyRateUtils;

import java.util.List;

public class PositionTakingReportPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span");
    public DropDownBox ddCUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following-sibling::div/select");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]//following-sibling::div/select");
    public DropDownBox ddBookie = DropDownBox.xpath("//div[contains(text(),'Bookies')]//following-sibling::div/select");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public AppArlertControl alert = AppArlertControl.xpath("//app-alert");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    int bookieTblCol = 4;
    int clientTblCol = 4;
    public Table tblBookie = Table.xpath("(//table)[1]",bookieTblCol);
    public Table tblClient = Table.xpath("(//table)[2]",clientTblCol);
    public Label lblTotalWLBookie = Label.xpath("(//table)[1]//tbody//td[text()='Total']//following-sibling::td[2]");
    public Label lblTotalWLClient = Label.xpath("(//table)[2]//tbody//td[text()='Total']//following-sibling::td[2]");

    public void filter(String companyUnit, String financialYear, String bookies, String fromDate, String toDate) {
        if (!companyUnit.isEmpty()){
            ddCUnit.selectByVisibleText(companyUnit);
        }
        if (!financialYear.isEmpty()){
            ddFinancialYear.selectByVisibleText(financialYear);
        }
        if (!bookies.isEmpty()){
            ddBookie.selectByVisibleText(bookies);
        }
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(fromDate,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public int getColTable(String tableName){
        switch (tableName){
            case "Bookie":
                return DriverManager.getDriver().findElements(By.xpath("(//table)[1]//thead//th")).size();
            default:
                return DriverManager.getDriver().findElements(By.xpath("(//table)[2]//thead//th")).size();
        }
    }
    public void verifyBookieClientSectionDisplay() {
        List<String> bookieHeaders = tblBookie.getHeaderNameOfRows();
        List<String> clientHeaders = tblClient.getHeaderNameOfRows();
        Assert.assertTrue(bookieHeaders.contains("Bookie Name"),"FAILED! Bookie Name is not displayed");
        Assert.assertTrue(clientHeaders.contains("Client Name"),"FAILED! Client Name is not displayed");
    }

    public boolean isFormatBookieNameDisplay() {
        List<String> lstBookieName = tblBookie.getColumn(tblBookie.getColumnIndexByName("Bookie Name"),18,true);
        List<String> lstBookies = ddBookie.getOptions().subList(1,ddBookie.getNumberOfItems());
        List<String> lstCurCode = CurrencyRateUtils.getLstCurrencyCode("1");
        for (int i = 0; i < lstBookieName.size();i++){
            String bookieName = lstBookieName.get(i).trim();
            String curCode1 = bookieName.substring(1,4);
            String curCode2 = bookieName.substring(8,11);
            String bookieActual = bookieName.split(" ")[3];
            if (!lstCurCode.contains(curCode1)){
                System.out.println("Bookie Name "+i+" display wrongly");
                return false;
            }
            if (!curCode2.equals("HKD")){
                System.out.println("Bookie Name "+i+" display wrongly");
                return false;
            }
            if (!lstBookies.contains(bookieActual)){
                System.out.println("Bookie Name "+i+" display wrongly");
                return false;
            }
        }
        return true;
    }

    public AccountPopup clickToBookieName(String bookieName) {
        int indexBookie = getIndexBookieName(bookieName);
        Label lblBookie = Label.xpath(tblBookie.getxPathOfCell(1,tblBookie.getColumnIndexByName("Bookie Name"),indexBookie,"a"));
        lblBookie.click();
        waitSpinnerDisappeared();
        return new AccountPopup();
    }

    private int getIndexBookieName(String bookieName) {
        Label lblBookie;
        int i = 1;
        while (true){
            lblBookie = Label.xpath(tblBookie.getxPathOfCell(1,tblBookie.getColumnIndexByName("Bookie Name"),i,"a"));
            if (!lblBookie.isDisplayed()){
                System.out.println("Bookie is not display!");
                return 0;
            }
            if (lblBookie.getText().equals(bookieName)){
                return i;
            }
            i = i + 1;
        }
    }
}
