package pages.sb11.generalReports.popup.clientstatement;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.ClientStatementPage;

public class ClientSummaryWinlosePopup {
    int summaryColTotal = 8;
    public int colWinLoseTotal = 3;
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span");

    Table tblWinloseSummary = Table.xpath("//app-win-loss-detail-log//table[@aria-label='group table']",summaryColTotal);
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");

    public String getGrandTotal(int colIndex) {
        return Label.xpath(String.format("(//td[text()='Grand Total']/following-sibling::td)[%s]",colIndex)).getText().trim();
    }
    public void waitSpinnerDisappeared() {
        while(lblSpin.isDisplayed()) {
            lblSpin.waitForControlInvisible();
        }
    }
}
