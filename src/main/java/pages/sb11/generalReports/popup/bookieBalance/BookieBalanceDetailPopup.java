package pages.sb11.generalReports.popup.bookieBalance;


import com.paltech.element.common.Label;
import controls.Table;

public class BookieBalanceDetailPopup {
    public Table tblTotal = Table.xpath("//app-bookie-balance-detail//div[contains(@class, 'total-table')]//table", 8);
    public Label lblGrandTotalFooter = Label.xpath("//div[contains(@class, 'filter-table')]//th[contains(., 'Grand Total in')]/span");

}
