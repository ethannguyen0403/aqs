package pages.sb11.generalReports.popup.bookiestatement;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;

import java.util.ArrayList;
import java.util.List;

public class BookieSummaryPopup {
    int summaryColTotal = 11;
    public int colOpeningTotal = 3;
    public int colWinLoseTotal = 4;
    public int colComissionTotal = 4;
    public int colRecPayTotal = 5;
    public int colMovementTotal = 6;
    public int colClosingTotal = 7;
    int colAccountCode = 4;
    int colLedgerAccountCode = 3;
    int colWinLose = 8;
    public int colLedgerRecPay = 7;
    public int colRecPay = 9;

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
        while (true) {
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
    }



}
