package pages.sb11.generalReports;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.popup.clientBalance.ClientBalanceDetailPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClientBalancePage extends WelcomePage {
    public DropDownBox ddViewBy = DropDownBox.xpath("//div[contains(text(),'View By')]//following::select[1]");
    public DropDownBox ddCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddFinancialYear = DropDownBox.xpath("//div[contains(text(),'Financial Year')]//following::select[1]");
    TextBox txtDate = TextBox.name("fromDate");
    DateTimePicker dtpDate = DateTimePicker.xpath(txtDate, "//bs-datepicker-container");
    public DropDownBox ddShowTotal = DropDownBox.xpath("//div[contains(text(),'Show Total')]//following::select[1]");
    protected Button btnShow = Button.name("btnShow");
    TextBox txtSMCode = TextBox.xpath("//div[contains(text(),'SM Code')]/parent::div//input");
    public int totalCol = 11;
    public int colClientName = 2;
    public Table tblClientBalance = Table.xpath("//table[@id='client-balance-summary']", totalCol);
    public int colBookieName = 2;
    public Table tblBookieBalance = Table.xpath("//div[@id='bookie-balance-summary']//table[not(contains(@style,' '))]", 3);

    public Table tblClient = Table.xpath("//app-client-balance//table", 6);


    public void filter(String viewBy, String companyUnit, String financialYear, String date, String smCode) {
        if (!viewBy.isEmpty())
            ddViewBy.selectByVisibleText(viewBy);
        if (!companyUnit.isEmpty())
            ddCompanyUnit.selectByVisibleText(companyUnit);
        waitSpinnerDisappeared();
        if (!financialYear.isEmpty())
            ddFinancialYear.selectByVisibleText(financialYear);
        if (!date.isEmpty())
            dtpDate.selectDate(date, "dd/MM/yyyy");
        if (!smCode.isEmpty())
            txtSMCode.sendKeys(smCode);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public int findRowClientIndex(String clientName){
        int i = 1;
        Label lblClientName;
        while (true) {
            lblClientName = Label.xpath(tblClient.getxPathOfCell(1, colClientName, i, null));
            if (!lblClientName.isDisplayed()) {
                System.out.println("Can NOT found the client name " + clientName + " in the table");
                return -1;
            }
            if (lblClientName.getText().trim().equalsIgnoreCase(clientName)) {
                System.out.println("Found the client name " + clientName + " in the table");
                return i;
            }
            i++;
        }
    }
    public ClientBalanceDetailPopup goToClientDetail(String clientName){
        filter("","","","",clientName.split("-")[0].trim());
        tblClientBalance.getControlOfCellSPP(1,colClientName,1,null).click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new ClientBalanceDetailPopup();
    }

    public ClientBalanceDetailPopup goToClientDetail(int index){
        tblClientBalance.getControlOfCellSPP(1,colClientName,index,null).click();
        waitSpinnerDisappeared();
        DriverManager.getDriver().switchToWindow();
        return new ClientBalanceDetailPopup();
    }

    /**
     * Method get value on columns: deposit, total balance hkd, hkd
     * @return
     */
    public List<String> getValueOfFirstClient(){
        List<String> lstValue = new ArrayList<>();
        List<String> colEx = Arrays.asList("Deposit","Total Balance HKD","HKD");
        for (int i = 0; i < colEx.size(); i++){
            lstValue.add(tblClientBalance.getControlOfCellSPP(1,tblClientBalance.getColumnIndexByName(colEx.get(i)),1,null).getText());
        }
        return lstValue;
    }
}
