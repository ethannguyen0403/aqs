package pages.sb11.generalReports.systemmonitoring;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DoubleUtils;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class WLRPCPage extends WelcomePage {
    public DropDownBox ddCompany = DropDownBox.xpath("//label[contains(text(),'Company Unit')]//following-sibling::div/select");
    public DropDownBox ddType = DropDownBox.xpath("//div[contains(text(),'Type')]//following-sibling::div/select");
    TextBox txtFromDate = TextBox.xpath("//div[contains(text(),'From Date')]//following-sibling::div/input");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container/div/div");
    TextBox txtToDate = TextBox.xpath("//div[contains(text(),'To Date')]//following-sibling::div/input");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container/div/div");
    public DropDownBox ddCurrency = DropDownBox.xpath("//div[contains(text(),'Currency')]//following-sibling::select");
    Button btnShow = Button.xpath("//button[text()='Show']");
    public Button btnExportToExcel = Button.xpath("//button[contains(text(),'Export To Excel')]");
    Label lblNoted = Label.xpath("//span[contains(text(),'Support from-date and to-date within 3 months')]");
    public Label lblNoRecord = Label.xpath("//div[contains(text(),'No records found.')]");
    String lblTotalValueXpath = "(//span[text()='%s']//ancestor::table)[%d]//div[contains(text(),'Total Balance in')]/parent::td//following-sibling::td[2]";
    String lblDifValueXpath = "//div[text()='%s']/parent::td/following-sibling::td/div";

    public void filter(String companyUnit,String type, String fromDate, String toDate, String currency){
        if (!companyUnit.isEmpty()){
            ddCompany.selectByVisibleText(companyUnit);
        }
        if (!type.isEmpty()){
            ddType.selectByVisibleText(type);
        }
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        }
        if (!currency.isEmpty()){
            ddCurrency.selectByVisibleText(currency);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public void verifyUI(){
        Assert.assertTrue(ddCompany.isDisplayed(),"FAILED! Company Unit dropdown display incorrect");
        Assert.assertTrue(ddType.isDisplayed(),"FAILED! Type dropdown display incorrect");
        Assert.assertTrue(txtFromDate.isDisplayed(),"FAILED! From Date text box display incorrect");
        Assert.assertTrue(txtToDate.isDisplayed(),"FAILED! To Date text box display incorrect");
        Assert.assertTrue(ddCurrency.isDisplayed(),"FAILED! Currency dropdown display incorrect");
        Assert.assertTrue(btnShow.isDisplayed(),"FAILED! Show button display incorrect");
        Assert.assertFalse(btnExportToExcel.isEnabled(),"FAILED! Export To Excel button display incorrect");
        Assert.assertTrue(lblNoted.isDisplayed(),"FAILED! Support from-date and to-date within 3 months text display incorrect");
    }

    public void verifyHeaderNameByType(String type) {
        if (type.equals("All")){
            String client = Label.xpath("(//table)[1]/preceding-sibling::div/span").getText().trim();
            String bookie = Label.xpath("(//table)[2]/preceding-sibling::div/span").getText().trim();
            Assert.assertEquals(client,"Client","FAILED! Client Table Name display incorrect");
            Assert.assertEquals(bookie,"Bookie","FAILED! Bookie Table Name display incorrect");
        } else {
            Table table = Table.xpath("//table",5);
            List<String> lstHeader = table.getHeaderNameOfRows();
            Assert.assertTrue(lstHeader.contains(type+" Name"));
        }
    }

    public void checkColorOfDifferenceValue() {
        Label lblDifValue =  Label.xpath("//div[text()='Difference']/parent::td//following-sibling::td[2]/div");
        for (int i = 1; i <= lblDifValue.getWebElements().size();i++){
            Label lblDifValueAc = Label.xpath(String.format("(//div[text()='Difference']/parent::td//following-sibling::td[2]/div)[%d]",i));
            if (lblDifValueAc.getText().trim().equals("0.00")){
                Assert.assertEquals(lblDifValueAc.getColour("Color"),"rgb(83, 165, 13)","FAILED! "+i+"th Difference Value display Color incorrect");
            } else {
                Assert.assertEquals(lblDifValueAc.getColour("Color"),"rgb(252, 0, 0)","FAILED! "+i+"th Difference Value display Color incorrect");
            }
        }
    }
    public String getTotalBalance(String type, String currency){
        String totalEx = "";
        int indexTable;
        if (type.equals("Client")){
            indexTable = 1;
        } else {
            indexTable = 2;
        }
        Label lblTotalValue = Label.xpath(String.format(lblTotalValueXpath,currency,indexTable));
        if (lblTotalValue.isDisplayed()){
            return lblTotalValue.getText().trim();
        }
        return totalEx;
    }
    public String getDifValue(String cur){
        return Label.xpath(String.format(lblDifValueXpath,cur)).getText().trim();
    }
    public List<String> getLstCurDisplay(){
        List<String> lstEx = new ArrayList<>();
        Label lblDifValue =  Label.xpath("//div[text()='Difference']/parent::td//following-sibling::td[1]/div");
        for (int i = 1; i <= lblDifValue.getWebElements().size();i++){
            Label lblDifValueAc = Label.xpath(String.format("(//div[text()='Difference']/parent::td//following-sibling::td[1]/div)[%d]",i));
            lstEx.add(lblDifValueAc.getText().trim());
        }
        return lstEx;
    }
    public void checkDifferenceValueDisplay() {
        List<String> lstCur = getLstCurDisplay();
        for (String cur : lstCur){
            String totalClient = getTotalBalance("Client",cur).replace(",","");
            String totalBookie = getTotalBalance("Bookie",cur).replace(",","");
            double difEx;
            if (totalClient.isEmpty()){
                difEx = Double.valueOf(totalBookie);
            } else if (totalBookie.isEmpty()){
                difEx = Double.valueOf(totalClient);
            } else {
                difEx = DoubleUtils.roundUpWithTwoPlaces(Double.valueOf(totalClient) - Double.valueOf(totalBookie));
            }
            double difAc = Double.valueOf(getDifValue(cur));
            Assert.assertEquals(difAc,difEx,"FAILED! Difference value of "+cur+" display incorrect");
        }
    }
}
