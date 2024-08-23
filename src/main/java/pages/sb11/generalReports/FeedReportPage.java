package pages.sb11.generalReports;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.feedreport.ClientPopup;
import pages.sb11.generalReports.popup.feedreport.InvoicePopup;
import pages.sb11.generalReports.popup.feedreport.ProviderPopup;
import pages.sb11.popup.ConfirmPopup;
import utils.sb11.FeedReportUtils;

import java.io.IOException;

public class FeedReportPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//app-feed-report//span[contains(@class,'text-white')]");
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']//following-sibling::div/select");
    public DropDownBox ddFinancial = DropDownBox.xpath("//div[contains(text(),'Financial Year')]//following-sibling::select");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container");
    public TextBox txtTransactionDate = TextBox.name("transactionDate");
    public DateTimePicker dtpTransactionDate = DateTimePicker.xpath(txtTransactionDate,"//bs-datepicker-container");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Table tblTotalValue = Table.xpath("//app-feed-report//div[@class='ng-star-inserted']/table",4);
    public Button btnSubmitTrans = Button.xpath("//button[contains(text(),'Submit Transaction')]");
    public String txtAmountByProviderNameXpath = "//td[contains(text(),'%s Between')]//parent::tr//following-sibling::tr//th[contains(text(),'%s')]//following-sibling::td//input";
    public String txtAmountByClientNameXpath = "//td[contains(text(),'%s Between')]//parent::tr//following-sibling::tr//td[contains(text(),'%s')]//following-sibling::td//input";
    public Button btnProvider = Button.xpath("//button[contains(text(),'Provider')]");
    public Button btnClient = Button.xpath("//button[contains(text(),'Client')]");
    public Table tblProviderFirst = Table.xpath("(//table)[2]",7);


    public void filter(String companyUnit, String financialYear, String fromDate, String toDate) {
        if(!companyUnit.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyUnit);
        }
        if(!financialYear.isEmpty()){
            ddFinancial.selectByVisibleText(financialYear);
        }
        if(!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        }
        if(!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public InvoicePopup inputAmount(String providerClientTableName, String providerClientName, String valueAmount, String dateTrans, boolean provider , boolean submitTrans) {
        String txtAmountByNameXpath = txtAmountByProviderNameXpath;
        if (!provider){
            txtAmountByNameXpath = txtAmountByClientNameXpath;
        }
        try {
            TextBox txtAmount = TextBox.xpath(String.format(txtAmountByNameXpath,providerClientTableName,providerClientName));
            txtAmount.sendKeys(valueAmount);
            txtAmount.sendKeys(Keys.ENTER);
        } catch (Exception e){
            e.getMessage();
        }
        if (submitTrans){
            btnSubmitTrans.scrollToTop();
            dtpTransactionDate.selectDate(dateTrans,"dd/MM/yyyy");
            btnSubmitTrans.click();
            waitSpinnerDisappeared();
            ConfirmPopup confirmPopup = new ConfirmPopup();
            confirmPopup.confirm(true);
            waitSpinnerDisappeared();
            return new InvoicePopup();
        } else {
            return null;
        }
    }
    public String getTotalProvider(){
        return tblTotalValue.getControlOfCell(1,tblTotalValue.getColumnIndexByName("Provider"),1,null).getText();
    }

    public ProviderPopup openProviderPopup() {
        btnProvider.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ProviderPopup();
    }
    public ClientPopup openClientPopup() {
        btnClient.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ClientPopup();
    }

    public void verifySumTableValue(String fromDate, String toDate) {
        String providerEx = null;
        try {
            providerEx = FeedReportUtils.getSumProvider(fromDate,toDate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String clientEx = null;
        try {
            clientEx = FeedReportUtils.getSumClient(fromDate,toDate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String commissionEx = String.format("%.2f",Double.valueOf(clientEx) - Double.valueOf(providerEx));

        String providerAc = tblTotalValue.getControlBasedValueOfDifferentColumnOnRow("HKD",1,tblTotalValue.getColumnIndexByName("CUR"),1,null,tblTotalValue.getColumnIndexByName("Provider"),null,true,false).getText();
        String clientAc = tblTotalValue.getControlBasedValueOfDifferentColumnOnRow("HKD",1,tblTotalValue.getColumnIndexByName("CUR"),1,null,tblTotalValue.getColumnIndexByName("Client"),null,true,false).getText();
        String commissionAc = tblTotalValue.getControlBasedValueOfDifferentColumnOnRow("HKD",1,tblTotalValue.getColumnIndexByName("CUR"),1,null,tblTotalValue.getColumnIndexByName("Commission"),null,true,false).getText();
        Assert.assertTrue(providerAc.equals(providerEx),"FAILED! Provider display incorrect");
        Assert.assertTrue(clientAc.equals(clientEx),"FAILED! Client display incorrect");
        Assert.assertTrue(commissionAc.equals(commissionEx),"FAILED! Commission display incorrect");
    }
}
