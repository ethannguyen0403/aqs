package pages.sb11.generalReports.popup;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.BookieStatementPage;

public class BookieSuperMasterDetailPopup extends WelcomePage {
    int summaryColTotal = 7;
    public int colOpeningBalance = 2;
    public int colOpeningRPCRBA = 3;
    public int colRPCRBA = 4;
    public int colOpeningComm = 5;
    public int colComm = 6;
    public int colGrandTotal = 7;

    Table tblSuperMasterDetail = Table.xpath("//app-super-master-detail//table[@aria-label='table']",summaryColTotal);
    Icon closeIcon = Icon.xpath("//span[@class='cursor-pointer close-icon ml-3']");

    public String getSuperMasterCellValue(int colIndex, boolean isHKD) {
        String returnValue;
        Label lblCellValue;
        if (!isHKD) {
            lblCellValue = Label.xpath(tblSuperMasterDetail.getxPathOfCell(1, colIndex, 2, null));
            returnValue = lblCellValue.getText();
        } else {
            lblCellValue = Label.xpath(tblSuperMasterDetail.getxPathOfCell(1, colIndex, 1, null));
            returnValue = lblCellValue.getText();
        }
        return returnValue;
    }

    public BookieStatementPage closeSuperMasterDetailPopup() {
        closeIcon.click();
        return new BookieStatementPage();
    }

}
