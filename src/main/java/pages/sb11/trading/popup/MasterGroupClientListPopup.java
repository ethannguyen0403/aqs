package pages.sb11.trading.popup;

import com.paltech.element.common.Label;

public class MasterGroupClientListPopup {
    Label lblTitle = Label.xpath("//app-client-list//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Label lblMaster = Label.xpath("//app-client-list//span[contains(@class,'text-truncate')]");
}
