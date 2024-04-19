package pages.sb11.generalReports.systemmonitoring;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DoubleUtils;
import com.paltech.utils.FileUtils;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.ExcelUtils;

import java.util.ArrayList;
import java.util.List;

public class WLRPCPage extends WelcomePage {
    public DropDownBox ddCompany = DropDownBox.xpath("//label[contains(text(),'Company Unit')]//following-sibling::div/select");
    public DropDownBox ddType = DropDownBox.xpath("//div[contains(text(),'Type')]//following-sibling::div/select");
    TextBox txtFromDate = TextBox.xpath("//div[contains(text(),'From Date')]//following-sibling::div/input");
    TextBox txtToDate = TextBox.xpath("//div[contains(text(),'To Date')]//following-sibling::div/input");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container/div/div");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container/div/div");
    public DropDownBox ddCurrency = DropDownBox.xpath("//div[contains(text(),'Currency')]//following-sibling::select");
    public Button btnShow = Button.xpath("//button[text()='Show']");
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
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
            waitSpinnerDisappeared();
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
            Table tblCLient = Table.xpath("(//table)[1]",4);
            Table tblBookie = Table.xpath("(//table)[2]",4);
            Assert.assertEquals(tblCLient.getHeaderNameOfRows(), SBPConstants.WLRPCT.HEADER_NAME_TYPE_ALL_CLIENT,"FAILED! Client header table displays incorrect.");
            Assert.assertEquals(tblBookie.getHeaderNameOfRows(), SBPConstants.WLRPCT.HEADER_NAME_TYPE_ALL_BOOKIE,"FAILED! Bookie header table displays incorrect.");
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
    public List<String> getLstCurDisplay(String type){
        List<String> lstEx = new ArrayList<>();
        if (type.equals("All")){
            Label lblCur =  Label.xpath("//div[text()='Difference']/parent::td//following-sibling::td[1]/div");
            for (int i = 1; i <= lblCur.getWebElements().size();i++){
                Label lblCurAc = Label.xpath(String.format("(//div[text()='Difference']/parent::td//following-sibling::td[1]/div)[%d]",i));
                lstEx.add(lblCurAc.getText().trim());
            }
        } else {
            Label lblCur =  Label.xpath("//div[contains(text(),'Total Balance in')]//parent::td/following-sibling::td[1]//span");
            for (int i = 1; i <= lblCur.getWebElements().size();i++){
                Label lblCurAc = Label.xpath(String.format("(//div[contains(text(),'Total Balance in')]//parent::td/following-sibling::td[1]//span)[%d]",i));
                lstEx.add(lblCurAc.getText().trim());
            }

        }
        return lstEx;
    }
    public void checkDifferenceValueDisplay() {
        List<String> lstCur = getLstCurDisplay("All");
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
            Assert.assertEquals(difAc,Math.abs(difEx),"FAILED! Difference value of "+cur+" display incorrect");
        }
    }
    public void exportToExcel(){
        btnExportToExcel.click();
        //Wait for file Excel export
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyExcelFileDownloadCorrect(String type) {
        String downloadPath;
        if (type.equals("All")){
            downloadPath = getDownloadPath() + "export-winloss (1).xlsx";
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath),"FAILED! Excel file displays incorrect.");
            Table tblCLient = Table.xpath("(//table)[1]",4);
            Table tblBookie = Table.xpath("(//table)[2]",4);
            List<String> lstHeaderClient = tblCLient.getHeaderNameOfRows();
            List<String> lstHeaderBookie = tblBookie.getHeaderNameOfRows();
            //Verify header name in excel
            Assert.assertEquals(lstHeaderClient.get(tblCLient.getColumnIndexByName("No")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",1,1),"FAILED! No column displays incorrect,");
            Assert.assertEquals(lstHeaderClient.get(tblCLient.getColumnIndexByName("Client Name")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",3,1),"FAILED! Client Name column displays incorrect,");
            Assert.assertEquals(lstHeaderClient.get(tblCLient.getColumnIndexByName("Currency Code")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",4,1),"FAILED! Currency Code column displays incorrect,");
            Assert.assertEquals(lstHeaderClient.get(tblCLient.getColumnIndexByName("Winlose Amount")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",5,1),"FAILED! Winlose Amount column displays incorrect,");
            Assert.assertEquals(lstHeaderBookie.get(tblBookie.getColumnIndexByName("Bookie Name")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",7,1),"FAILED! Bookie Name column displays incorrect,");
            Assert.assertEquals(lstHeaderBookie.get(tblBookie.getColumnIndexByName("Currency Code")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",8,1),"FAILED! Currency Code column displays incorrect,");
            Assert.assertEquals(lstHeaderBookie.get(tblBookie.getColumnIndexByName("Winlose Amount")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",9,1),"FAILED! Winlose Amount column displays incorrect,");
            //Verify data in row 1
            Assert.assertEquals(tblCLient.getControlOfCell(1,tblCLient.getColumnIndexByName("No"),1,"div").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",1,2));
            Assert.assertEquals(tblCLient.getControlOfCell(1,tblCLient.getColumnIndexByName("Client Name"),1,"span").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",3,2));
            Assert.assertEquals(tblCLient.getControlOfCell(1,tblCLient.getColumnIndexByName("Currency Code"),1,"span").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",4,2));
            Assert.assertEquals(tblCLient.getControlOfCell(1,tblCLient.getColumnIndexByName("Winlose Amount"),1,null).getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",5,2));
            Assert.assertEquals(tblBookie.getControlOfCell(1,tblBookie.getColumnIndexByName("Bookie Name"),1,"span").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",7,2));
            Assert.assertEquals(tblBookie.getControlOfCell(1,tblBookie.getColumnIndexByName("Currency Code"),1,"span").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",8,2));
            Assert.assertEquals(tblBookie.getControlOfCell(1,tblBookie.getColumnIndexByName("Winlose Amount"),1,null).getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",9,2));
        } else {
            if (type.equals("Client")){
                downloadPath = getDownloadPath() + "export-winloss.xlsx";
            } else {
                downloadPath = getDownloadPath() + "export-winloss (2).xlsx";
            }
            Assert.assertTrue(FileUtils.doesFileNameExist(downloadPath),"FAILED! Excel file displays incorrect.");
            Table table = Table.xpath("(//table)[1]",5);
            List<String> lstHeader = table.getHeaderNameOfRows();
            //Verify header name in excel
            Assert.assertEquals(lstHeader.get(table.getColumnIndexByName("No")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",1,1),"FAILED! No column displays incorrect,");
            Assert.assertEquals(lstHeader.get(table.getColumnIndexByName(type+" Name")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",3,1),"FAILED! "+type+" Name column displays incorrect,");
            Assert.assertEquals(lstHeader.get(table.getColumnIndexByName("Currency Code")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",4,1),"FAILED! Currency Code column displays incorrect,");
            Assert.assertEquals(lstHeader.get(table.getColumnIndexByName("Winlose Amount")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",5,1),"FAILED! Winlose Amount column displays incorrect,");
            Assert.assertEquals(lstHeader.get(table.getColumnIndexByName("Rec Pay Ca Rb Adj")-1), ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",6,1),"FAILED! Rec Pay Ca Rb Adj Amount column displays incorrect,");
            //Verify data in row 1
            Assert.assertEquals(table.getControlOfCell(1,table.getColumnIndexByName("No"),1,"div").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",1,2));
            Assert.assertEquals(table.getControlOfCell(1,table.getColumnIndexByName(type+" Name"),1,"span").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",3,2));
            Assert.assertEquals(table.getControlOfCell(1,table.getColumnIndexByName("Currency Code"),1,"span").getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",4,2));
            Assert.assertEquals(table.getControlOfCell(1,table.getColumnIndexByName("Winlose Amount"),1,null).getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",5,2));
            Assert.assertEquals(table.getControlOfCell(1,table.getColumnIndexByName("Rec Pay Ca Rb Adj"),1,null).getText().trim(),
                    ExcelUtils.getCellByColumnAndRowIndex(downloadPath,"export-wl-rpcamount",6,2));
        }



    }
    //Get value in Table with type Client/Bookie by currency
    public String getBookieClientValueByCur(String clientCode, String currency, String colName) {
        Table tableCur = Table.xpath(String.format("//span[text()='%s']//ancestor::table",currency),5);
        return Label.xpath(tableCur.getControlxPathBasedValueOfDifferentColumnOnRow(clientCode,1,2,1,"span",tableCur.getColumnIndexByName(colName),null,true,false)).getText().trim();
    }
}
