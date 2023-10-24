package pages.sb11.generalReports.clientbalance;

import com.paltech.element.common.Label;
import com.paltech.element.common.Tab;
import controls.Table;

public class ClientBalanceDetailPage {
    public int colTotalBalance = 5;
    public Table tblTotalBalance = Table.xpath("(//table)[1]",5);
    public Label lblValueGrandTotal = Label.xpath("(//td[contains(text(),'Grand Total in HKD')]/following-sibling::td)[1]");
}
