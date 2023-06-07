package pages.sb11.trading.popup;

import com.paltech.element.common.Label;
import controls.Table;

public class AgentGroupListPopup {
    Label lblTitle = Label.xpath("//app-group-list-agent//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public Table tbGroup = Table.xpath("//app-group-list-agent//table",8);

    public int getTotalGroupAmount(){
        return tbGroup.getNumberOfRowsPopup(false,false);
    }
}
