package pages.sb11.generalReports.popup.bookiestatement;

import com.paltech.element.common.Icon;
import controls.Table;

public class BookieMasterAgentDetailPopup {
    public Table tblMDetail = Table.xpath("//app-super-master-detail//table[@aria-label='table'][2]",10);
    public Icon closeIcon = Icon.xpath("//span[@class='cursor-pointer close-icon ml-3']");

}
