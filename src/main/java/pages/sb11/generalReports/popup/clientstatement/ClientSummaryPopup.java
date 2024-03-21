package pages.sb11.generalReports.popup.clientstatement;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.*;
import controls.Table;
import pages.sb11.WelcomePage;

import java.util.ArrayList;
import java.util.List;

public class ClientSummaryPopup {
    int summaryColTotal = 11;
    public int colOpeningTotal = 3;
    public int colWinLoseTotal = 4;
    public int colComissionTotal = 4;
    public int colRecPayTotal = 5;
    public int colMovementTotal = 6;
    public int colClosingTotal = 7;
    int colAccountCode = 4;
    int colLedgerAccountCode = 3;
    public int colWinLose = 8;
    public int colLedgerRecPay = 7;
    public int colRecPay = 9;
    public int colOpening = 7;
    public int colClosing = 11;

    Table tblSummary = Table.xpath("//app-member-summary//table[@aria-label='table']",summaryColTotal);
    Table tblLedgerSummary = Table.xpath("//app-ledger-member-summary//table[@aria-label='table']",summaryColTotal);
    Icon closeIcon = Icon.xpath("//span[contains(@class,'close-icon')]");

    public String getGrandTotal(String currency, int colIndex) {
        String returnValue;
        Label lblCellValue;
        String xpath;
        switch (currency) {
            case "GBP":
                xpath = String.format("//table[@aria-label='table']//tbody[1]//tr[contains(@class,'total-in')][2]//td[%s]", colIndex);
                lblCellValue = Label.xpath(xpath);
                if (!lblCellValue.isDisplayed()) {
                    System.out.println("There's no value display in the GBP row");
                    return null;
                } else {
                    returnValue = lblCellValue.getText();
                    return returnValue;
                }
            default:
                xpath = String.format("//table[@aria-label='table']//tbody[1]//tr[contains(@class,'total-in')][1]//td[%s]", colIndex);
                lblCellValue = Label.xpath(xpath);
                if (!lblCellValue.isDisplayed()) {
                    System.out.println("There's no value display in the HKD row");
                    return null;
                } else {
                    returnValue = lblCellValue.getText();
                    return returnValue;
                }
        }
    }

    public String getSummaryCellValue(String accountCode, int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblAccountCode;
        int i = 1;
        while (true) {
            lblCellValue = Label.xpath(tblSummary.getxPathOfCell(1, colIndex, i, null));
            lblAccountCode = Label.xpath(tblSummary.getxPathOfCell(1, colAccountCode, i, null));
            if (!lblCellValue.isDisplayed()) {
                System.out.println("Account Code is not found in Summary table");
                return null;
            }
            if (lblAccountCode.getText().equalsIgnoreCase(accountCode)) {
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i + 1;
        }
    }
    public String getLedgerSummaryCellValue(String accountCode, int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblAccountCode;
        int i = 1;
        while (i < 50) {
            lblCellValue = Label.xpath(tblLedgerSummary.getxPathOfCell(1, colIndex, i, null));
            lblAccountCode = Label.xpath(tblLedgerSummary.getxPathOfCell(1, colLedgerAccountCode, i, null));
            if (!lblCellValue.isDisplayed()) {
                System.out.println("Ledger Code is not found in Summary table");
                return null;
            }
            if (lblAccountCode.getText().equalsIgnoreCase(accountCode)) {
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i + 1;
        }
        return null;
    }

    public List<String> getMemeberSummaryData(String accountCode){
        List<String> lstData = new ArrayList<>();
        String celValue;
        Label lblAccountCode;
        int i = 1;
        while (true) {
            lblAccountCode = Label.xpath(tblSummary.getxPathOfCell(1, colAccountCode, i, null));
            if (!lblAccountCode.isDisplayed()) {
                System.out.println("Account Code is not found in Summary table");
                return null;
            }
            if (lblAccountCode.getText().equalsIgnoreCase(accountCode)) {
                for(int col = 1; col <= summaryColTotal; col ++){
                    lstData.add(Label.xpath(tblSummary.getxPathOfCell(1, col, i, null)).getText());
                }
                return lstData;
            }
            i = i + 1;
        }
    }

    public void closeSummaryPopup() {
        closeIcon.click();
    }

    public ClientSummaryWinlosePopup openWinLoseSummaryPopup(String accountCode) {
        Label lblAccountCode;
        Label lblWinlose;
        int i = 1;
        while (true){
            lblAccountCode = Label.xpath(tblSummary.getxPathOfCell(1,colAccountCode,i,null));
            lblWinlose = Label.xpath(tblSummary.getxPathOfCell(1,colWinLose,i,null) + "//a");
            if(lblAccountCode.getText().equalsIgnoreCase(accountCode)){
                lblWinlose.click();
                DriverManager.getDriver().switchToWindow();
                return new ClientSummaryWinlosePopup();
            }
            i = i+1;
        }
    }

    public ClientMemberTransactionPopup openMemberTransactionPopup(String accountCode) {
        Label lblAccountCode;
        int i = 1;
        while (true){
            lblAccountCode = Label.xpath(tblSummary.getxPathOfCell(1,colAccountCode,i,null));
            if(lblAccountCode.getText().equalsIgnoreCase(accountCode)){
                lblAccountCode.click();
                DriverManager.getDriver().switchToWindow();
                return new ClientMemberTransactionPopup();
            }
            i = i+1;
        }
    }

    public ClientMemberTransactionPopup openMemberTransactionPopupLedger(String accountCode) {
        Label lblAccountCode;
        int i = 1;
        while (true){
            lblAccountCode = Label.xpath(tblLedgerSummary.getxPathOfCell(1,colLedgerAccountCode,i,"a"));
            if(lblAccountCode.getText().equalsIgnoreCase(accountCode)){
                lblAccountCode.click();
                DriverManager.getDriver().switchToWindow();
                return new ClientMemberTransactionPopup();
            }
            if(i>40){
                System.out.println("Not found " + accountCode);
                return null;
            }
            i = i+1;
        }
    }

    public ClientLedgerRecPayPopup openLedgerRecPaySummaryPopup(String accountCode, int colIndex) {
        Label lblCellValueA;
        Label lblCellValueSpan;
        Label lblAccountCode;
        int i = 1;
        while (true) {
            lblCellValueA = Label.xpath(tblLedgerSummary.getxPathOfCell(1, colIndex, i, null) + "//a");
            lblCellValueSpan = Label.xpath(tblLedgerSummary.getxPathOfCell(1, colIndex, i, null) + "//span");
            lblAccountCode = Label.xpath(tblLedgerSummary.getxPathOfCell(1, colLedgerAccountCode, i, null));
            if (!lblCellValueA.isDisplayed() && !lblCellValueSpan.isDisplayed()) {
                System.out.println("Ledger Code is not found in Summary table");
                return null;
            }
            if (lblAccountCode.getText().equalsIgnoreCase(accountCode)) {
                lblCellValueA.click();
                DriverManager.getDriver().switchToWindow();
                return new ClientLedgerRecPayPopup(true);
            }
            i = i + 1;
        }
    }


}
