package pages.sb11.generalReports.popup;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.ClientStatementPage;

public class ClientSummaryWinlosePopup extends WelcomePage {
    int summaryColTotal = 8;
    public int colWinLoseTotal = 4;

    Table tblWinloseSummary = Table.xpath("//app-win-loss-detail-log//table[@aria-label='group table']",summaryColTotal);

    public String getGrandTotal(int colIndex) {
        String returnValue = null;
        Label lblCellValue;
        lblCellValue = Label.xpath(tblWinloseSummary.getxPathOfCell(1, colIndex, 5, null));
        returnValue = lblCellValue.getText();
        return returnValue;
    }

}
