package pages.sb11.master.bookiesystempopup;

import com.paltech.element.common.Label;

public class AgentListPopup {
    Label lblTitle = Label.xpath("//app-agent-list//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
