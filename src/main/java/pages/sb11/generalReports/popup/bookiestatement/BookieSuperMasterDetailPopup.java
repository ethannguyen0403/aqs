package pages.sb11.generalReports.popup.bookiestatement;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.generalReports.BookieStatementPage;

public class BookieSuperMasterDetailPopup  {
    int summaryColTotal = 7;
    public int colOpeningBalance = 2;
    public int colOpeningRPCRBA = 3;
    public int colRPCRBA = 4;
    public int colOpeningComm = 5;
    public int colComm = 6;
    public int colGrandTotal = 7;

    Table tblSuperMasterDetail = Table.xpath("//app-super-master-detail//table[@aria-label='table'][1]",summaryColTotal);
    public Table tblSMDetail = Table.xpath("//app-super-master-detail//table[@aria-label='table'][2]",10);
    public Icon closeIcon = Icon.xpath("//span[@class='cursor-pointer close-icon ml-3']");

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
    public String getValueTransDetail(String des, String colName){
        //index column -1 because table header have Receipt/....
        return tblSMDetail.getControlBasedValueOfDifferentColumnOnRow(des,1,tblSMDetail.getColumnIndexByName("Description")-1,1,null,tblSMDetail.getColumnIndexByName(colName)-1,null,false,false).getText().trim();
    }

    public BookieStatementPage closeSuperMasterDetailPopup() {
        closeIcon.click();
        return new BookieStatementPage();
    }

}
