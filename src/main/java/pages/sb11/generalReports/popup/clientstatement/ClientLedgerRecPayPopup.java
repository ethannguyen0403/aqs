package pages.sb11.generalReports.popup.clientstatement;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Label;
import controls.Table;

import java.util.Set;

public class ClientLedgerRecPayPopup {
    int summaryColTotal = 7;
    public int colDifferentOriginal = 2;

    public Table tblRecPaySummary = Table.xpath("//app-report-dialog//table[@aria-label='table']",summaryColTotal);

    public ClientLedgerRecPayPopup(boolean isWaitLoad) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
    public ClientLedgerRecPayPopup()  {
    }
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

    public void switchToFirstWindow(){
        Set<String> allWindows = DriverManager.getDriver().getWindowHandles();
        String[] allWindowArr = new String[allWindows.size()];
        allWindows.toArray(allWindowArr);
        DriverManager.getDriver().switchTo().window(allWindowArr[0]);
    }

}
