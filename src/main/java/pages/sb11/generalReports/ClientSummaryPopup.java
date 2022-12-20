package pages.sb11.generalReports;

import com.paltech.element.common.*;
import pages.sb11.WelcomePage;

public class ClientSummaryPopup extends WelcomePage {
    int summaryColTotal = 11;
    public int colOpeningTotal = 3;
    public int colWinLoseTotal = 4;
    public int colComissionTotal = 4;
    public int colRecPayTotal = 5;
    public int colMovementTotal = 6;
    public int colClosingTotal = 7;

    //Table tblSummary = Table.xpath("//app-member-summary//table[@aria-label='table']",summaryColTotal);
    Icon closeIcon = Icon.xpath("//span[contains(@class,'close-icon')]");
//    Row totalHKDRow = Row.xpath("//table[@aria-label='table']//tbody[1]//tr[contains(@class,'total-in')][1]");
//    Row totalGBPRow = Row.xpath("//table[@aria-label='table']//tbody[1]//tr[contains(@class,'total-in')][2]");

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

    public ClientStatementPage closeSummaryPopup() {
        closeIcon.click();
        return new ClientStatementPage();
    }

}
