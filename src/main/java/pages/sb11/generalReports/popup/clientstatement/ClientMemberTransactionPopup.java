package pages.sb11.generalReports.popup.clientstatement;

import com.paltech.element.common.Label;
import controls.Table;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;


public class ClientMemberTransactionPopup {
    int summaryColTotal = 10;
    public int colOpeningRunning = 5;
    public int colTotalRunning = 4;

    public Table tblWinloseSummary = Table.xpath("//app-report-dialog//table[@aria-label='transaction table']",summaryColTotal);

    public void verifyHeaderCorrectWithCompanyCur(List<String> expectedHeader, String currency) {
        List<String> newList = new ArrayList<>();
        for (String s : expectedHeader) {
            newList.add(String.format(s, currency));
        }
        ArrayList<String> actualHeaderList = tblWinloseSummary.getHeaderNameOfRows();
        Assert.assertEquals(actualHeaderList, newList, "FAILED! Text is incorrect");
    }

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
