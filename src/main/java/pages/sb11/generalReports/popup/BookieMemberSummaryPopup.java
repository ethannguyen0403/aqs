package pages.sb11.generalReports.popup;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Row;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.generalReports.BookieStatementPage;

public class BookieMemberSummaryPopup extends WelcomePage {
    int colTotalMember = 11;
    public int colTotal = 3;
    public int colTotalWinLose = 4;
    public int colTotalOpenWinLose = 5;
    public int colTotalRPCRBA = 6;
    public int colTotalOpenRPCRBA = 7;
    public int colTotalOpenBalance = 8;
    Table tblTotalDetail = Table.xpath("//div[@class='p-4 table-responsive ng-star-inserted']//table[@aria-label='table']",colTotal);
    Table tblMemberDetail = Table.xpath("//div[@class='infinite-scroll p-4 table-responsive table-scroll']//table[@aria-label='table']",colTotalMember);
    Icon closeIcon = Icon.xpath("//span[@class='cursor-pointer close-icon ml-3']");

    public String getTotalCellValue(int colIndex, boolean isHKD) {
        String returnValue;
        Label lblCellValue;
        if (!isHKD) {
            lblCellValue = Label.xpath(tblTotalDetail.getxPathOfCell(1, colIndex, 2, null));
            returnValue = lblCellValue.getText();
        } else {
            lblCellValue = Label.xpath(tblTotalDetail.getxPathOfCell(1, colIndex, 1, null));
            returnValue = lblCellValue.getText();
        }
        return returnValue;
    }

    public String getMemberTotalCellValue(int colIndex, boolean isHKD) {
        String returnValue;
        Label lblCellValue;
        int totalRow = getNumberOfRows("//div[@class='infinite-scroll p-4 table-responsive table-scroll']//table[@aria-label='table']",false,true);

        if (!isHKD) {
            lblCellValue = Label.xpath(tblMemberDetail.getxPathOfCell(totalRow-1,colIndex,1,null));
            returnValue = lblCellValue.getText();
            return returnValue;
        } else {
            lblCellValue = Label.xpath(tblMemberDetail.getxPathOfCell(totalRow-2,colIndex,2,null));
            returnValue = lblCellValue.getText();
            return returnValue;
        }
    }

    public BookieStatementPage closeSuperMasterDetailPopup() {
        closeIcon.click();
        return new BookieStatementPage();
    }

    private int getNumberOfRows(String xpathTable, boolean isCountHeaderRow, boolean isMoving){
        int numberRows = 0;
        if(isCountHeaderRow){
            Row row = Row.xpath(String.format("%s%s", xpathTable, "/thead/tr[1]"));
            if (row.isDisplayed(3)){
                numberRows +=1;
            } else {
                return numberRows;
            }
        }

        String rowXpath = String.format("%s%s", xpathTable, "//tbody[%d]/tr[1]");
        int i = 1;
        while (true){
            Row iRow = Row.xpath(String.format(rowXpath, i));
            if (!iRow.isDisplayed(3)){
                return numberRows + 1;
            } else{
                numberRows +=1;
                i++;
                if (isMoving) {
                    iRow.scrollDownInDistance();
                }
            }
        }/**/
    }
}
