package pages.sb11.generalReports;

import com.beust.ah.A;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import com.paltech.utils.DateUtils;
import com.paltech.utils.DoubleUtils;
import common.SBPConstants;
import controls.Cell;
import controls.DateTimePicker;
import controls.Table;
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
        String totalColumn = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 6, 6);
        String curGBP = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 7, 6);
        String curUSD = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 8, 6);
        String curEUR = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 9, 6);
        String curHKD = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 10, 6);

        String noValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 1, 7);
        String clientValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 2, 7);
        String picValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 3, 7);
        String depositValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 5, 7);
        String totalValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 6, 7);
        String curGBPValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 7, 7);
        String curUSDValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 8, 7);
        String curEURValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 9, 7);
        String curHKDValue = ExcelUtils.getCellByColumnAndRowIndex(downloadPath, "Consolidated Client Balance", 10, 7);

        Assert.assertEquals(companyUnit, SBPConstants.COMPANY_UNIT + " and Fair", "FAILED! Company unit display incorrect");
        Assert.assertEquals(namePage, SBPConstants.CONSOLIDATED_CLIENT_BALANCE, "FAILED! Name page display incorrect");
        Assert.assertEquals(rangeTime, DateUtils.getDate(0, "dd MMMM yyyy",SBPConstants.GMT_7), "FAILED! Range time display incorrect");
        Assert.assertEquals(viewBy, "View by: Client Point", "FAILED! View by type display incorrect");

        List<String> lstHeaderAc = tblInfo.getHeaderNameOfRows();
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Client") - 1), clientColumn, "FAILED! Client header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("PIC") - 1), picColumn, "FAILED! PIC header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Status") - 1), statusColumn, "FAILED! Status header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Deposit in Ori CUR") - 1), depositColumn, "FAILED! Deposit header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("Total Balance HKD") - 1), totalColumn, "FAILED! Total header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("GBP") - 1), curGBP, "FAILED! GBP header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("USD") - 1), curUSD, "FAILED! USD header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("EUR") - 1), curEUR, "FAILED! EUR header display incorrect");
        Assert.assertEquals(lstHeaderAc.get(tblInfo.getColumnIndexByName("HKD") - 1), curHKD, "FAILED! HKD header display incorrect");

        Assert.assertEquals(noValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName(SBPConstants.ConsolidatedClientBalance.HEADER_NAME.get(0)), 1, null).getText(), "FAILED! No column value display incorrect");
        Assert.assertEquals(clientValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("Client"), 1, null).getText(), "FAILED! Client column value display incorrect");
        Assert.assertEquals(picValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("PIC"), 1, null).getText(), "FAILED! PIC column value display incorrect");
        Assert.assertEquals(depositValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("Deposit in Ori CUR"), 1, null).getText(), "FAILED! Deposit column value display incorrect");
        Assert.assertEquals(totalValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("Total Balance HKD"), 1, null).getText(), "FAILED! Total Balance HKD column value display incorrect");
        Assert.assertEquals(curGBPValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("GBP"), 1, null).getText(), "FAILED! GBP column value display incorrect");
        Assert.assertEquals(curUSDValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("USD"), 1, null).getText(), "FAILED! USD column value display incorrect");
        Assert.assertEquals(curEURValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("EUR"), 1, null).getText(), "FAILED! EUR column value display incorrect");
        Assert.assertEquals(curHKDValue, tblInfo.getControlOfCellSPP(1, tblInfo.getColumnIndexByName("HKD"), 1, null).getText(), "FAILED! HKD column value display incorrect");
    }
    public List<String> getLstClientDisplay() {
        List<String> lstClient = new ArrayList<>();
        int numRow =  tblInfo.getNumberOfRows(true);
        for (int i = 1; i < numRow - 1;i++){
            lstClient.add(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Client"),i,null).getText());
        }
        return lstClient;
    }
    public void verifyFilterWorkProperly() {
        String clientExpect = "QA Client (No.121 QA Client)";
        String depositExpect = "HKD\n1,075,572,920.31";
        String totalExpect = "1,075,963,622.55";
        String hkdExpect = "1,075,807,350.56";
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Client"),1,null).getText().trim(),clientExpect,"FAILED! Client display incorrect");
        Assert.assertTrue(DropDownBox.xpath(tblInfo.getxPathOfCellSPP(1,tblInfo.getColumnIndexByName("Status"),1,"select")).isDisplayed(),"FAILED! Status display incorrect");
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Deposit in Ori CUR"),1,null).getText().trim(),depositExpect,"FAILED! Deposit in Ori CUR display incorrect");
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Total Balance HKD"),1,null).getText().trim(),totalExpect,"FAILED! Total Balance HKD display incorrect");
        Assert.assertEquals(tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("HKD"),1,null).getText().trim(),hkdExpect,"FAILED! HKD column display incorrect");
    }

    public void verifyDataTableDisplay() {
        List<String> lstTotal = getColumnSPP(tblInfo.getColumnIndexByName("Total Balance HKD"),155,true);
        String blackColorAc = tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("GBP"),2,null).getColour("color");
        String negativeColorAc = tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Total Balance HKD"),2,null).getColour("color");
        String positiveColorAc = tblInfo.getControlOfCellSPP(1,tblInfo.getColumnIndexByName("Total Balance HKD"),lstTotal.size()-1,null).getColour("color");
        Assert.assertTrue(Color.fromString(blackColorAc).asHex().equals("#212529"),"FAILED! Color of 0 display incorrect!");
        Assert.assertTrue(Color.fromString(negativeColorAc).asHex().equals("#dc3545"),"FAILED! Color of negative number display incorrect!");
        Assert.assertTrue(Color.fromString(positiveColorAc).asHex().equals("#007bff"),"FAILED! Color of positive number display incorrect!");
        Double beforeNumb = Double.valueOf(lstTotal.get(0).trim().replace(",",""));
        Double total = beforeNumb;
        //check Total Balance in HKD: Data will be sorted by ascendingly
        for (int i = 1; i < lstTotal.size();i++){
            total = DoubleUtils.roundWithTwoPlaces(RoundingMode.HALF_EVEN,total+Double.valueOf(lstTotal.get(i).trim().replace(",","")));
            if (beforeNumb > Double.valueOf(lstTotal.get(i).trim().replace(",",""))){
                Assert.assertTrue(false,"FAILED! Client "+i+" display incorrect");
            }
        }
        //check deviation of sum actual and sum expect
        Assert.assertTrue((Double.valueOf(lblTotalOfTotalBalance.getText().trim().replace(",",""))-total) < 0.02,"FAILED! Total of Total Balance HKD display incorrect");

    }
    public List<String> getColumnSPP(int columnOrder, int limit, boolean isMoved){
        List<String> lst = new ArrayList<String>();
        if (columnOrder < 1){
            System.out.println(String.format("Error: columnOrder %s is to be more than or equal to 1", columnOrder));
            return lst;
        }
        int i = 1;
        String cellXpath = String.format("%s%s%s", "//table", "//tbody/tr[%s]/", String.format("th[%s]", columnOrder));
        if(!Cell.xpath(String.format(cellXpath, i)).isDisplayed())
        {
            cellXpath = String.format("%s%s%s", "//table", "//body/tr[%s]/", String.format("th[%s]", columnOrder));
        }
        while(true) {
            Cell cell = Cell.xpath(String.format(cellXpath, i));
            if (!cell.isDisplayedShort(5)){
                return lst;
            }
            lst.add(cell.getText(5));
            if (lst.size() == limit) {
                return lst;
            }
            if (isMoved){
                cell.scrollDownInDistance();
            }
            i += 1;
        }
    }
}
