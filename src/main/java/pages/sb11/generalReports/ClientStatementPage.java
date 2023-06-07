package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.clientstatement.ClientSummaryPopup;

import java.util.ArrayList;
import java.util.List;

public class ClientStatementPage extends WelcomePage {
    int colTotal = 10;
    public int colLevel = 3;
    public int colOpening = 5;
    public int colWinLoss = 6;
    public int colCommission = 7;
    public int colRecPay = 8;
    public int colMovement = 9;
    public int colClosing = 10;

    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    DropDownBox ddpViewBy = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][1]//select");
    DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][2]//select");
    DropDownBox ddpFinancialYear = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][3]//select");
    DropDownBox ddpClients = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][4]//select");
    TextBox txtFromDate = TextBox.name("fromDate");
    TextBox txtToDate = TextBox.name("toDate");
    Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");

    Table tblSuper = Table.xpath("//app-client-detail//table[@id='table-super']",colTotal);
    Table tblMaster = Table.xpath("//app-client-detail//table[@id='table-master']",colTotal);
    //Table tblAgent = Table.xpath("//app-client-detail//div[%s]//table[@id='table-agent']",colTotal);

    @Override
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public void filter(String viewBy, String companyUnit, String financialYear, String clients, String fromDate, String toDate){
        ddpViewBy.selectByVisibleText(viewBy);
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpFinancialYear.selectByVisibleText(financialYear);
        ddpClients.selectByVisibleText(clients);
        String currentDate = txtFromDate.getAttribute("value");
        if(!fromDate.isEmpty())
            if(!currentDate.equals(fromDate))
                dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        currentDate = txtToDate.getAttribute("value");
        if(!toDate.isEmpty())
            if(!currentDate.equals(toDate))
                dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        btnShow.click();
        waitSpinnerDisappeared();
    }
    public String getSuperCellValue(int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        lblCellValue = Label.xpath(tblSuper.getxPathOfCell(1,colIndex,1,null));
        if(!lblCellValue.isDisplayed()){
            System.out.println("There's no value display in the Super table");
            return null;
        } else {
            returnValue = lblCellValue.getText();
            return returnValue;
        }
    }
    public String getMasterCellValue(String masterCode, int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblMasterCode;
        if (masterCode.equalsIgnoreCase("Total in")) {
            colIndex = colIndex-2;
            colLevel = 1;
        }
        int i = 1;
        while (i < 50){
            lblCellValue = Label.xpath(tblMaster.getxPathOfCell(1,colIndex,i,null));
            lblMasterCode = Label.xpath(tblMaster.getxPathOfCell(1,colLevel,i,null));
            if(!lblCellValue.isDisplayed()){
                System.out.println("There's no value display in the Master table");
                return null;
            }
            if(lblMasterCode.getText().equalsIgnoreCase(masterCode)){
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i+1;
        }
        return returnValue;
    }
    public String getAgentCellValue(String agentCode, int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblAgentCode;
        Label lblFirstColumn;
        int i = 2;
        int j = 1;
        while (i < 50){
            String xpath = String.format("//app-client-detail//div[contains(@class,'col-12')][%s]//table[@class='table table-bordered table-custom table-hover table-striped text-center bg-white mb-0 fbody ng-star-inserted']",j);
            Table tblAgent = Table.xpath(xpath,colTotal);
            lblCellValue = Label.xpath(tblAgent.getxPathOfCell(1,colIndex,i,null));
            lblAgentCode = Label.xpath(tblAgent.getxPathOfCell(1,colLevel,i,null));
            lblFirstColumn = Label.xpath(tblAgent.getxPathOfCell(1,1,i,null));
            if(lblFirstColumn.getText().equalsIgnoreCase("Total in")) {
                j = j + 1;
                i = 1;
            }
            if(lblAgentCode.getText().equalsIgnoreCase(agentCode)){
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            if(lblAgentCode.getText().equalsIgnoreCase("Grand Total in")) {
                break;
            }
            i = i+1;
        }
        return returnValue;
    }
    public String getGrandTotal(String currency) {
        String returnValue;
        Label lblCellValue;
        switch (currency){
            case "GBP":
                lblCellValue = Label.xpath("//app-client-detail//table[@id='grand-total']//tr[2]//th[10]");
                if(!lblCellValue.isDisplayed()){
                    System.out.println("There's no value display in the GrandTotal GBP table");
                    return null;
                } else {
                    returnValue = lblCellValue.getText();
                    return returnValue;
                }
            default:
                lblCellValue = Label.xpath("//app-client-detail//table[@id='grand-total']//tr[1]//th[10]");
                if(!lblCellValue.isDisplayed()){
                    System.out.println("There's no value display in the GrandTotal HKD table");
                    return null;
                } else {
                    returnValue = lblCellValue.getText();
                    return returnValue;
                }
        }
    }
    public String reverseValue(String value) {
        String returnVal = value;
        if (Float.parseFloat(value) > 0) {
            returnVal = "-" + value;
            return returnVal;
        } else if (Float.parseFloat(value) < 0) {
            returnVal = value.replace("-","");
            return returnVal;
        }
        return returnVal;
    }
    public ClientSummaryPopup openSummaryPopup(String agentCode) {
        Label lblAgentCode;
        Label lblFirstColumn;
        int i = 2;
        int j = 1;
        while (i < 20){
            String xpath = String.format("(//app-client-detail//div[@id='client-statement-summary']//div[@class='row px-custom mt-3 ng-star-inserted'][2]//table)[%s]",j);
            Table tblAgent = Table.xpath(xpath,colTotal);
            lblAgentCode = Label.xpath(tblAgent.getxPathOfCell(1,colLevel,i,null));
            lblFirstColumn = Label.xpath(tblAgent.getxPathOfCell(1,1,i,null));
            if(lblAgentCode.getText().equalsIgnoreCase(agentCode)){
                lblAgentCode.click();
                waitSpinnerDisappeared();
                return new ClientSummaryPopup();
            }
            if(lblFirstColumn.getText().equalsIgnoreCase("Total in")) {
                j = j + 1;
                i = 1;
            }
            if(lblAgentCode.getText().equalsIgnoreCase("Grand Total in")) {
                break;
            }
            i = i+1;
        }
        return null;
    }
    public boolean verifyValueIsOpposite(ArrayList lstActual, ArrayList lstExpect) {
        String reverseVal;
        boolean isOpposite = false;
            for (int i = 0; i < lstActual.size(); i++) {
                reverseVal = reverseValue(lstActual.get(i).toString());
                Assert.assertEquals(reverseVal,lstExpect.get(i).toString());
                isOpposite = true;
            }
            return isOpposite;
    }
    public List<String> getMemberSummary(String agentCode, String accountCode){
        ClientSummaryPopup clientSummaryPopup = openSummaryPopup(agentCode);
        List<String> lstData = clientSummaryPopup.getMemeberSummaryData(accountCode);
        clientSummaryPopup.closeSummaryPopup();
        return lstData;
    }
}
