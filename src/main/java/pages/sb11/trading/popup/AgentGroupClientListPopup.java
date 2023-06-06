package pages.sb11.trading.popup;

import com.paltech.element.common.Label;

public class AgentGroupClientListPopup {
    Label lblTitle = Label.xpath("//app-client-list-agent//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Label lblAgent = Label.xpath("//app-client-list-agent//span[contains(@class,'text-truncate')]");
}
