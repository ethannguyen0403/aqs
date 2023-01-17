package pages.sb11.generalReports.popup;

import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

public class ClientMemberTransactionPopup extends WelcomePage {
    int summaryColTotal = 10;
    public int colOpeningRunning = 5;
    public int colTotalRunning = 4;

    Table tblWinloseSummary = Table.xpath("//app-report-dialog//table[@aria-label='transaction table']",summaryColTotal);

    public String getTotalRunning(int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblTotal;
        int i = 1;
        while (true) {
            lblCellValue = Label.xpath(tblWinloseSummary.getxPathOfCell(1, colIndex, i, null));
            lblTotal = Label.xpath(tblWinloseSummary.getxPathOfCell(1, 1, i, null));
            if (!lblCellValue.isDisplayed()) {
                System.out.println("Total row is not found in Summary table");
                return null;
            }
            if (lblTotal.getText().equalsIgnoreCase("Total")) {
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i + 1;
        }
    }

    public String getOpeningRunning(int colIndex) {
        String returnValue = null;
        Label lblCellValue;
        lblCellValue = Label.xpath(tblWinloseSummary.getxPathOfCell(1, colIndex, 1, null));
        returnValue = lblCellValue.getText();
        return returnValue;
    }

}
