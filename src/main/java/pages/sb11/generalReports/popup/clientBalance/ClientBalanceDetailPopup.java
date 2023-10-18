package pages.sb11.generalReports.popup.clientBalance;

import com.paltech.element.common.Label;
import controls.Table;


public class ClientBalanceDetailPopup {
    public Table tblClientSuper = Table.xpath("(//app-client-balance-detail//table)[1]", 5);
    public Label lblGrandTotalMaster = Label.xpath("(//app-client-balance-detail//table)[2]//tr[contains(@class, 'total-row')]/td[1]");
    public Label lblGrandTotalFooter = Label.xpath("(//app-client-balance-detail//table)[4]//tr/td[1]");

}
