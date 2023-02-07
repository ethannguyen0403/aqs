package pages.sb11.generalReports.popup.clientstatement;

import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;

public class ClientLedgerRecPayPopup {
    int summaryColTotal = 7;
    public int colDifferentOriginal = 2;

    Table tblRecPaySummary = Table.xpath("//app-report-dialog//table[@aria-label='table']",summaryColTotal);

    public String getDifferenceOriginalVal(int colIndex) {
        String returnValue = "";
        Label lblCellValue;
        Label lblFirstCol;
        int i = 1;
        while (true) {
            lblCellValue = Label.xpath(tblRecPaySummary.getxPathOfCell(1, colIndex, i, null));
            lblFirstCol = Label.xpath(tblRecPaySummary.getxPathOfCell(1, 1, i, null));
            if (!lblCellValue.isDisplayed()) {
                System.out.println("Difference row is not found in Summary table");
                return null;
            }
            if (lblFirstCol.getText().equalsIgnoreCase("Difference")) {
                returnValue = lblCellValue.getText();
                return returnValue;
            }
            i = i + 1;
        }
    }

}
