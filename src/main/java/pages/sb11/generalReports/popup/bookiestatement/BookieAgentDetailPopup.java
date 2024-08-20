package pages.sb11.generalReports.popup.bookiestatement;

import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Row;
import controls.Table;

import java.util.ArrayList;
import java.util.List;

public class BookieAgentDetailPopup {
    public Table tblAgentDetail = Table.xpath("//app-super-master-detail//table[@aria-label='table'][2]", 10);
    public Icon closeIcon = Icon.xpath("//app-super-master-detail//span[@class='cursor-pointer close-icon ml-3']");

    public List<String> getHeaderAgentDetailList() {
        List<String> lstHeader = new ArrayList<>();
        Row row = Row.xpath("//app-super-master-detail//table[@aria-label='table'][2]/thead/tr/th");
        for (int i = 1; i <= row.getWebElements().size(); i++) {
            lstHeader.add(Label.xpath(String.format("(//app-super-master-detail//table[@aria-label='table'][2]/thead/tr/th)[%d]",i)).getText().trim());
        }
        return lstHeader;
    }

}
