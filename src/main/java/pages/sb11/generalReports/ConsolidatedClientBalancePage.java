package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import common.SBPConstants;
import controls.Cell;
import controls.DateTimePicker;
import controls.Row;
import controls.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import utils.ExcelUtils;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ConsolidatedClientBalancePage extends WelcomePage {
    public Label lblTitle = Label.xpath("//app-consolidated-client-balance//span[contains(@class,'text-white')]");
    public DropDownBox ddViewBy = DropDownBox.xpath("//div[contains(text(),'View By')]//following-sibling::div//select");
    public DropDownBox ddFinancial = DropDownBox.xpath("//div[contains(text(),'Financial Year')]//following-sibling::div//select");
    public DropDownBox ddStatus = DropDownBox.xpath("//div[contains(text(),'Status')]//following-sibling::div//select");
    TextBox txtDate = TextBox.name("fromDate");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-datepicker-container");
    public TextBox txtClientName = TextBox.xpath("//div[contains(text(),'Client Name')]//following-sibling::div//input");
    public Button btnShow = Button.name("btnShow");
    public Button btnExportExcel = Button.xpath("//button[contains(text(),'Export To Excel')]");
    public Button btnExportPDF = Button.xpath("//button[contains(text(),'Export To PDF')]");
    public Button btnCreateClient = Button.xpath("//button[text()='Create/Manage Client Groups']");
    int numCol = 12;
    public Table tblInfo = Table.xpath("//table",numCol);
    Label lblTotalOfTotalBalance = Label.xpath("//th[text()=' Total']//following-sibling::th[5]");


    public void verifyUIDisplay() {
        //View By: contains the only option 'Client Point' as we only support this view in the report for now
        Assert.assertEquals(ddViewBy.getNumberOfItems(),1,"FAILED! Do not contains the only option 'Client Point'");
        Assert.assertTrue(ddViewBy.getOptions().get(0).contains("Client Point"),"FAILED! Do not contains the only option 'Client Point'");

        Assert.assertTrue(ddFinancial.isEnabled(),"FAILED! Financial Year dropdown display incorrect");
        Assert.assertTrue(ddStatus.isEnabled(),"FAILED! Status dropdown display incorrect");
        Assert.assertTrue(txtDate.isEnabled(),"FAILED! Date textbox display incorrect");
        Assert.assertTrue(txtClientName.isEnabled(),"FAILED! Client Name textbox display incorrect");
        Assert.assertTrue(btnShow.isEnabled(),"FAILED! Show button display incorrect");
        Assert.assertFalse(btnExportExcel.isEnabled(),"FAILED! Export to excel button display incorrect");
        Assert.assertFalse(btnExportPDF.isEnabled(),"FAILED! Export to PDF button display incorrect");
        Assert.assertFalse(btnCreateClient.isEnabled(),"FAILED! Create/Manage Client Groups button display incorrect");

        List<String> lstHeader = tblInfo.getHeaderNameOfRows();
        Assert.assertEquals(lstHeader,SBPConstants.ConsolidatedClientBalance.HEADER_NAME,"FAILED! Header name display incorrect");
    }

    public void filter(String financialYear, String toDate, String status, String clientName) {
        if (!financialYear.isEmpty()){
            ddFinancial.selectByVisibleText(financialYear);
        }
        if (!toDate.isEmpty()){
            dtpDate.selectDate(toDate,"dd/MM/yyyy");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (!status.isEmpty()){
            ddStatus.selectByVisibleText(status);
        }
        if (!clientName.isEmpty()){
            txtClientName.sendKeys(clientName);
        }
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void checkExcelData(String downloadPath) {
        String companyUnit = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 1, 1);
        String namePage = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 1, 2);
        String rangeTime = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 1, 3);
        String viewBy = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 1, 4);
        String clientColumn = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 2, 6);
        String picColumn = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 3, 6);
        String statusColumn = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 4, 6);
        String depositColumn = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 5, 6);
        String totalColumn = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 7, 6);
        String curGBP = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 8, 6);
        String curUSD = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 9, 6);
        String curEUR = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 10, 6);
        String curHKD = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 11, 6);

        String noValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 1, 8);
        String clientValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 2, 8);
        String picValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 3, 8);
        String depositValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 5, 8);
        String totalValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 7, 8);
        String curGBPValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 8, 8);
        String curUSDValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 9, 8);
        String curEURValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 10, 8);
        String curHKDValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 11, 8);

        Assert.assertEquals(companyUnit, SBPConstants.KASTRAKI_LIMITED + " and Fair", "FAILED! Company unit display incorrect");
        Assert.assertEquals(namePage, SBPConstants.CONSOLIDATED_CLIENT_BALANCE, "FAILED! Name page display incorrect");
        Assert.assertEquals(rangeTime, DateUtils.getDate(0, "dd MMMM yyyy",SBPConstants.GMT_7), "FAILED! Range time display incorrect");
        Assert.assertEquals(viewBy, "View by: Client Point", "FAILED! View by type display incorrect");

        List<String> lstHeaderAc = tblInfo.getHeaderNameOfRows();
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Client") - 1), clientColumn, "FAILED! Client header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("PIC") - 1), picColumn, "FAILED! PIC header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Status") - 1), statusColumn, "FAILED! Status header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Deposit") - 1), depositColumn, "FAILED! Deposit header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Total Balance HKD") - 1), totalColumn, "FAILED! Total header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("GBP") - 1), curGBP, "FAILED! GBP header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("USD") - 1), curUSD, "FAILED! USD header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("EUR") - 1), curEUR, "FAILED! EUR header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("HKD") - 1), curHKD, "FAILED! HKD header display incorrect");

        Assert.assertEquals(noValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName(SBPConstants.ConsolidatedClientBalance.HEADER_NAME.get(0)), 2, null).getText(), "FAILED! No column value display incorrect");
        Assert.assertEquals(clientValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("Client"), 2, null).getText(), "FAILED! Client column value display incorrect");
        Assert.assertEquals(picValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("PIC"), 2, null).getText(), "FAILED! PIC column value display incorrect");
        Assert.assertEquals(depositValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("Deposit"), 2, null).getText(), "FAILED! Deposit column value display incorrect");
        Assert.assertEquals(totalValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("Total Balance HKD"), 2, null).getText(), "FAILED! Total Balance HKD column value display incorrect");
        Assert.assertEquals(curGBPValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("GBP"), 2, null).getText(), "FAILED! GBP column value display incorrect");
        Assert.assertEquals(curUSDValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("USD"), 2, null).getText(), "FAILED! USD column value display incorrect");
        Assert.assertEquals(curEURValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("EUR"), 2, null).getText(), "FAILED! EUR column value display incorrect");
        Assert.assertEquals(curHKDValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("HKD"), 2, null).getText(), "FAILED! HKD column value display incorrect");
    }
    public List<String> getLstClientDisplay() {
        List<String> lstClient = new ArrayList<>();
        int numRow =  tblInfo.getNumberOfRows(true);
        for (int i = 1; i < numRow - 1;i++){
            lstClient.add(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Client"),i,null).getText());
        }
        return lstClient;
    }
    public void verifyFilterWorkProperly(String clientName, String deposit, String totalBalance, String hkdValue) {
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Client"),2,null).getText().trim(),clientName,"FAILED! Client display incorrect");
        Assert.assertTrue(DropDownBox.xpath(tblInfo.getxPathOfCellSPP(1,tblInfo.getColumnIndexByName("Status"),2,"select")).isDisplayed(),"FAILED! Status display incorrect");
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Deposit"),2,null).getText().trim(),deposit,"FAILED! Deposit display incorrect");
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Total Balance HKD"),2,null).getText().trim(),totalBalance,"FAILED! Total Balance HKD display incorrect");
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("HKD"),2,null).getText().trim(),hkdValue,"FAILED! HKD column display incorrect");
    }

    public void verifyDataTableDisplay() {
        int numberRow = getNumberRowByGroup("Player");
        String blackColorAc = getCellOfRow("Player","GBP",1).getColour("color");
        String negativeColorAc = getCellOfRow("Player","Total Balance HKD",1).getColour("color");
        String positiveColorAc = getCellOfRow("Player","Total Balance HKD",numberRow).getColour("color");
        Assert.assertTrue(Color.fromString(blackColorAc).asHex().equals("#212529"),"FAILED! Color of 0 display incorrect!");
        Assert.assertTrue(Color.fromString(negativeColorAc).asHex().equals("#dc3545"),"FAILED! Color of negative number display incorrect!");
        Assert.assertTrue(Color.fromString(positiveColorAc).asHex().equals("#007bff"),"FAILED! Color of positive number display incorrect!");
        Double beforeNumb = Double.valueOf(getCellOfRow("Player","Total Balance HKD",1).getText().trim().replace(",",""));
        Double total = beforeNumb;
        //check Total Balance in HKD: Data will be sorted by ascendingly
        for (int i = 2; i <= numberRow;i++){
            Double afterNum = Double.valueOf(getCellOfRow("Player","Total Balance HKD",i).getText().trim().replace(",",""));
            total = DoubleUtils.roundWithTwoPlaces(RoundingMode.HALF_EVEN,total + afterNum);
            if (beforeNumb > afterNum){
                Assert.assertTrue(false,"FAILED! Client "+i+" display incorrect");
            }
        }
    }
    public int getNumberRowByGroup(String groupName){
        int numberRow = 0;
        List<WebElement> lstInfo = DriverManager.getDriver().findElements(By.xpath(String.format("//table//span[contains(text(),'%s')]//ancestor::tr//following-sibling::tr",groupName)));
        for (WebElement element : lstInfo){
            if (element.getAttribute("Class").contains("bg-white")){
                numberRow = numberRow + 1;
            } else if (element.getAttribute("Class").contains("consolidated-group-row")){
                return numberRow;
            }
        }
        return numberRow;
    }
    public Label getCellOfRow(String groupName, String columnName, int rowIndex){
        int columnIndex = tblInfo.getColumnIndexByName(columnName);
        return Label.xpath(String.format("(//table//span[contains(text(),'%s')]//ancestor::tr//following-sibling::tr[contains(@class,'bg-white')])[%s]//th[%s]",groupName,rowIndex,columnIndex));
    }
}
