package pages.sb11.generalReports.popup.bookiestatement;

import com.paltech.driver.DriverManager;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import com.paltech.utils.DoubleUtils;
import controls.Row;
import controls.Table;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class BookieMemberSummaryPopup {
    int colTotalMember = 11;
    int colAccountCode = 3;
    public int colWinLosePlayer = 7;

    public int colTotal = 3;
    public int colTotalWinLose = 4;
    public int colTotalOpenWinLose = 5;
    public int colTotalRPCRBA = 6;
    public int colTotalOpenRPCRBA = 7;
    public int colTotalOpenBalance = 8;

    public int colOpeningTotal = 3;
    public int colWinLoseTotal = 4;
    public int colComissionTotal = 4;
    public int colRecPayTotal = 5;
    public int colMovementTotal = 6;
    public int colClosingTotal = 7;

    int colWinLose = 8;
    public int colLedgerRecPay = 7;
    public int colRecPay = 9;

    Table tblTotalDetail = Table.xpath("//app-member-summary//div[contains(@class,'table-responsive')][1]//table",colTotal);
    public Table tblMemberDetail = Table.xpath("//app-member-summary//div[contains(@class,'table-responsive')][2]//table",colTotalMember);
    Icon closeIcon = Icon.xpath("//span[contains(@class,'close-icon')]");

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

    public void closePopup() {
        closeIcon.click();
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

    public List<String> getDataRowofPlayer(String accountCode){
        List<String> lstData = new ArrayList<>();
        Label lblAccountCode;
        int i = 1;
        while (true) {
            lblAccountCode = Label.xpath(tblMemberDetail.getxPathOfCell(i, colAccountCode, 1, null));
            if (!lblAccountCode.isDisplayed()) {
                System.out.println("Account Code is not found in Summary table");
                return null;
            }
            if (lblAccountCode.getText().equalsIgnoreCase(accountCode)) {
                for(int col = 1; col <= colTotalMember; col ++){
                    lstData.add(Label.xpath(tblMemberDetail.getxPathOfCell(i, col,1, null)).getText());
                }
                return lstData;
            }
            i = i + 1;
        }
    }
    public MemberDetailPage openMemberDetailPage(String accountCode){
        Label lblAccCode = Label.xpath(String.format("//a[contains(text(),'%s')]",accountCode));
        lblAccCode.click();
        DriverManager.getDriver().switchToWindow();
        return new MemberDetailPage();
    }

    public boolean isGrandTotalValueDisplay() {
        double totalVal = Double.valueOf(getTotalCellValue(colTotal,true).replace(",",""));

        double winLoseTotalVal = Double.valueOf(getTotalCellValue(colTotalWinLose,true).replace(",",""));
        double opWinLoseTotalVal = Double.valueOf(getTotalCellValue(colTotalOpenWinLose,true).replace(",",""));
        double rpcrbaTotalVal = Double.valueOf(getTotalCellValue(colTotalRPCRBA,true).replace(",",""));
        double openRPCRBATotalVal = Double.valueOf(getTotalCellValue(colTotalOpenRPCRBA,true).replace(",",""));
        double openBalanceTotalVal = Double.valueOf(getTotalCellValue(colTotalOpenBalance,true).replace(",",""));
        double expectedVal = DoubleUtils.roundEvenWithTwoPlaces(winLoseTotalVal + opWinLoseTotalVal + rpcrbaTotalVal + openRPCRBATotalVal + openBalanceTotalVal);
        if ((totalVal - expectedVal) >= 0 && (totalVal - expectedVal) <= 0.1 || (expectedVal - totalVal) <= 0.1 && (expectedVal - totalVal) >= 0){
            return true;
        }
        System.out.println("FAILED! Grand Total is not matched with sum value, Total: " + totalVal + " expected: " + expectedVal);
        return false;
    }
}
