package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Table;
import controls.sb11.AppArlertControl;
import org.openqa.selenium.By;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.positionTakingReport.AccountPopup;

import java.util.List;

public class PositionTakingReportPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span");
    public DropDownBox ddCUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following-sibling::div/select");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]//following-sibling::div/select");
    public DropDownBox ddBookie = DropDownBox.xpath("//div[contains(text(),'Bookies')]//following-sibling::div/select");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public AppArlertControl alert = AppArlertControl.xpath("//app-alert");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public int bookieTblCol = 4;
    int clientTblCol = 4;
    public int clientNameCol = 2;
    public Table tblBookie = Table.xpath("(//table)[1]",bookieTblCol);
    public Table tblClient = Table.xpath("(//table)[2]",clientTblCol);
    public Label lblTotalWLBookie = Label.xpath("(//table)[1]//tbody//td[text()='Total']//following-sibling::td[2]");
    public Label lblTotalWLClient = Label.xpath("(//table)[2]//tbody//td[text()='Total']//following-sibling::td[2]");
    public Button btnNext = Button.xpath("//button[contains(text(),'Next')]");
    public Button btnPrevious = Button.xpath("//button[contains(text(),'Previous')]");

    public void filter(String companyUnit, String financialYear, String bookies, String fromDate, String toDate) {
        if (!companyUnit.isEmpty()){
            ddCUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
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
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
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

    public boolean isFormatBookieNameDisplay(String bookieName, String bookieCurrency, String mainCurrency) {
        String formatBookie = "[%s >> %s] %s";
        String formatEx = String.format(formatBookie,bookieCurrency,mainCurrency,bookieName);
        int indexBookie = tblBookie.getRowIndexContainValue(formatEx,tblBookie.getColumnIndexByName("Bookie Name"),"a");
        String formatAc = tblBookie.getControlOfCell(1,tblBookie.getColumnIndexByName("Bookie Name"),indexBookie,"a").getText().trim();
        if (!formatAc.equals(formatEx)){
            System.out.println(formatEx+" is not displayed!");
            return false;
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

    public boolean isTotalWinLoseDisplay() {
        bookieTblCol = getColTable("Bookie")-1;
        tblBookie = Table.xpath("(//table)[1]",bookieTblCol);
        double winLoseEx = Double.valueOf(lblTotalWLBookie.getText().replace(",",""));
        double winLoseAc = 0.00;
        for (int i = 4; i < bookieTblCol;i++){
            String lblwinLoseDate = "(//table)[1]//tbody//td[text()='Total']//following-sibling::td[%s]";
            String winloseDate = Label.xpath(String.format(lblwinLoseDate,i)).getText().replace(",","");
            winLoseAc = DoubleUtils.roundEvenWithTwoPlaces(winLoseAc + Double.valueOf(winloseDate));
        }
        double value1 = winLoseAc - winLoseEx;
        double value2 = winLoseEx - winLoseAc;
        if (value1 <= 0.02 && value1 >= 0 || value2 <= 0.02 && value2 >= 0){
            return true;
        }
        System.out.println(winLoseEx + " difference from "+winLoseAc);
        return false;
    }

    public void verifyDateColumnDisplay(String toDate, int rangeDate) {
        tblBookie = Table.xpath("(//table)[1]",9);
        List<String> lstNameCol = tblBookie.getHeaderNameOfRows();
        String nameCol = toDate;
        for (int i = 1; i <= rangeDate; i++){
            Assert.assertTrue(lstNameCol.contains(nameCol),"FAILED! Name Column display incorrect!");
            nameCol = DateUtils.getPreviousDate(nameCol,"dd-MMM-yy");
            if (i%4==0){
                btnNext.click();
                waitSpinnerDisappeared();
                lstNameCol = tblBookie.getHeaderNameOfRows();
            }
        }
    }

    public void verifyDefaultHeadNameDisplay(String toDate) {
        bookieTblCol = 4;
        tblBookie = Table.xpath("(//table)[1]",bookieTblCol);
        String tillColumn = "Till "+toDate;
        List<String> lstHeaderAc = tblBookie.getHeaderNameOfRows();
        List<String> lstHeaderEx = SBPConstants.PositionTakingReport.DEFAULT_BOOKIE_HEADER_NAME;
        lstHeaderEx.set(tblBookie.getColumnIndexByName(tillColumn)-1,tillColumn);
        Assert.assertEquals(lstHeaderAc,lstHeaderEx,"FAILED! Default header name display incorrect");
    }

    public AccountPopup clickToClientName(String clientName) {
        int colIndex = tblClient.getColumnIndexByName("Client Name");
        Label lblClientName = Label.xpath(tblClient.getxPathOfCell(1,colIndex,
                tblClient.getRowIndexContainValue(clientName,colIndex,"a"),"a"));
        lblClientName.click();
        waitSpinnerDisappeared();
        return new AccountPopup();
    }

    public String getValueTillColumn(String bookieName, String tillToDate) {
        return tblBookie.getControlBasedValueOfDifferentColumnOnRow(bookieName,1,tblBookie.getColumnIndexByName("Bookie Name"),1,"a",
                tblBookie.getColumnIndexByName(tillToDate),null,false,false).getText();
    }
    public List<String> getLstClientName(){
        return tblClient.getColumn(tblClient.getColumnIndexByName("Client Name"),10,true);
    }


    public boolean isBookieDisplay(List<String> lstBookieAc) {
        List<String> lstBookie = ddBookie.getOptions();
        lstBookie.remove("All");
        lstBookieAc.add("Hhhuat-Ath");
        for (String bookieName : lstBookie){
            if (!lstBookieAc.contains(bookieName)){
                System.out.println(bookieName+" is not belong to Bookie list");
                return false;
            }
        }
        return true;
    }
}
