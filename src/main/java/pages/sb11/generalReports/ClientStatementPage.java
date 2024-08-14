package pages.sb11.generalReports;

import com.paltech.element.common.*;
import com.paltech.utils.DoubleUtils;
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
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][2]//select");
    public DropDownBox ddpFinancialYear = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][3]//select");
    DropDownBox ddpClients = DropDownBox.xpath("//div[contains(@class,'p-2 pb-4 pr-0 filter')][4]//select");
    TextBox txtFromDate = TextBox.name("fromDate");
    TextBox txtToDate = TextBox.name("toDate");
    Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate, "//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate, "//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public Label lblGrandTotal = Label.xpath("//app-client-detail//table[@id='grand-total']//th[3]");
    public Label lblGrandTotalCur = Label.xpath("//app-client-detail//table[@id='grand-total']//th[3]/following-sibling::th[1]");
    Table tblSuper = Table.xpath("//app-client-detail//table[@id='table-super']", colTotal);
    Table tblMaster = Table.xpath("//app-client-detail//table[@id='table-master']",colTotal);
    //Table tblAgent = Table.xpath("//app-client-detail//div[%s]//table[@id='table-agent']",colTotal);

    @Override
    public String getTitlePage() {
        return this.lblTitle.getText().trim();
    }

    public void filter(String viewBy, String companyUnit, String financialYear, String clients, String fromDate, String toDate) {
        if (!viewBy.isEmpty()){
            ddpViewBy.selectByVisibleText(viewBy);
            waitSpinnerDisappeared();
        }
        if (!companyUnit.isEmpty()){
            ddpCompanyUnit.selectByVisibleText(companyUnit);
            waitSpinnerDisappeared();
        }
        if (!financialYear.isEmpty()){
            ddpFinancialYear.selectByVisibleText(financialYear);
            waitSpinnerDisappeared();
        }
        ddpClients.selectByVisibleText(clients);
        if (!fromDate.isEmpty()){
            dtpFromDate.selectDate(fromDate, "dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        if (!toDate.isEmpty()){
            dtpToDate.selectDate(toDate, "dd/MM/yyyy");
            waitSpinnerDisappeared();
        }
        btnShow.click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
    }

    public String getSuperCellValue(int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        lblCellValue = Label.xpath(tblSuper.getxPathOfCell(1, colIndex, 1, null));
        if (!lblCellValue.isDisplayed()) {
            System.out.println("There's no value display in the Super table");
            return null;
        } else {
            returnValue = lblCellValue.getText();
            return returnValue;
        }
    }

    public String getMasterCellValue(String masterCode, int colIndex) {
        if (masterCode.equalsIgnoreCase("total in")) {
            colIndex = colIndex - 2;
        } else {
            masterCode = " " + masterCode + " ";
        }
        String xpath = String.format("//table[@id='table-master']//td[text()='%s']//..//td[%s]", masterCode, colIndex);
        Label lblCellValue = Label.xpath(xpath);
        if (!lblCellValue.isDisplayed()) {
            System.out.println("Cannot find out master cell value");
            return null;
        } else {
            return lblCellValue.getText();
        }
    }

    public String getAgentCellValue(String agentCode, int colIndex) {
        String xpath = String.format("//div[@id='client-statement-summary']//table[not(@id)]//a[text()=' %s']//..//..//td[%s]", agentCode, colIndex);
        Label lblCellValue = Label.xpath(xpath);
        if (!lblCellValue.isDisplayed()) {
            System.out.println("Cannot find out column index of inputted agent");
            return null;
        } else {
            return lblCellValue.getText();
        }
    }

    public String getGrandTotal(String currency) {
        String returnValue;
        Label lblCellValue;
        switch (currency) {
            case "GBP":
                lblCellValue = Label.xpath("//app-client-detail//table[@id='grand-total']//tr[2]//th[10]");
                if (!lblCellValue.isDisplayed()) {
                    System.out.println("There's no value display in the GrandTotal GBP table");
                    return null;
                } else {
                    returnValue = lblCellValue.getText();
                    return returnValue;
                }
            default:
                lblCellValue = Label.xpath("//app-client-detail//table[@id='grand-total']//tr[1]//th[10]");
                if (!lblCellValue.isDisplayed()) {
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
            returnVal = value.replace("-", "");
            return returnVal;
        }
        return returnVal;
    }

    public ClientSummaryPopup openSummaryPopup(String agentCode) {
        String xpath = String.format("//div[@id='client-statement-summary']//table[not(@id)]//a[contains(text(),'%s')]", agentCode);
        Label lblCellAgent = Label.xpath(xpath);
        if (!lblCellAgent.isDisplayed()) {
            System.out.println(String.format("Cannot find out agent %s in result", agentCode));
            return null;
        } else {
            lblCellAgent.scrollToThisControl(false);
            lblCellAgent.click();
//            Label.xpath(xpath + "/a").click();
            Label lblNoRecord = Label.xpath("//app-ledger-member-summary//td[contains(text(),'No Records Found.')]");
            int i = 0;
            while (i < 10){
                if (!lblNoRecord.isDisplayed()){
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return new ClientSummaryPopup();
        }
    }

    public boolean verifyValueIsOpposite(ArrayList lstActual, ArrayList lstExpect) {
        String reverseVal;
        boolean isOpposite = false;
        for (int i = 0; i < lstActual.size(); i++) {
            reverseVal = reverseValue(lstActual.get(i).toString());
            Assert.assertEquals(reverseVal, lstExpect.get(i).toString());
            isOpposite = true;
        }
        return isOpposite;
    }

    public List<String> getMemberSummary(String agentCode, String accountCode) {
        ClientSummaryPopup clientSummaryPopup = openSummaryPopup(agentCode);
        List<String> lstData = clientSummaryPopup.getMemeberSummaryData(accountCode);
        clientSummaryPopup.closeSummaryPopup();
        return lstData;
    }

    public double getMasterValueByCur(String clientName, String currency, String colName) {
        double valueEx = 0;
        int indexClientCol = tblMaster.getColumnIndexByName("Client Name");
        int indexCurCol = tblMaster.getColumnIndexByName("CUR");
        int indexColName = tblMaster.getColumnIndexByName(colName);
        for (int i = 1; i < tblMaster.getNumberOfRows(false,false);i++){
            Label lblClientName = Label.xpath(tblMaster.getxPathOfCell(1,indexClientCol,i,"span"));
            Label lblCUR = Label.xpath(tblMaster.getxPathOfCell(1,indexCurCol,i,null));
            Label lblColName = Label.xpath(tblMaster.getxPathOfCell(1,indexColName,i,null));
            if (lblClientName.getText().trim().equals(clientName) && lblCUR.getText().trim().equals(currency)){
                valueEx = DoubleUtils.roundUpWithTwoPlaces(valueEx + DoubleUtils.roundEvenWithTwoPlaces(Double.valueOf(lblColName.getText().trim().replace(",",""))));
            }
        }
        return valueEx;
    }
}
