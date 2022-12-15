package pages.sb11.generalReports;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

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
    Table tblAgent = Table.xpath("//app-client-detail//table[@id='table-agent']",colTotal);

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
        if(!fromDate.isEmpty())
            dtpFromDate.selectDate(fromDate,"dd/MM/yyyy");
        if(!toDate.isEmpty())
            dtpToDate.selectDate(toDate,"dd/MM/yyyy");
        btnShow.click();
        //waitPageLoad();
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
            colIndex = 8;
            colLevel = 1;
        }
        int i = 1;
        while (true){
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
    }
    public String getAgentCellValue(String agentCode, int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblAgentCode;
        int i = 2;
        while (true){
            lblCellValue = Label.xpath(tblAgent.getxPathOfCell(1,colIndex,i,null));
            lblAgentCode = Label.xpath(tblAgent.getxPathOfCell(1,colLevel,i,null));
            if(!lblCellValue.isDisplayed()){
                System.out.println("There's no value display in the Master table");
                return null;
            }
            if(lblAgentCode.getText().equalsIgnoreCase(agentCode)){
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i+1;
        }
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
}
