package pages.sb11.trading.popup;

import com.paltech.element.common.Label;

public class AgentGroupReportPopup {
    Label lblTitle = Label.xpath("//app-agent-group-detail//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public Label lblAgentCode = Label.xpath("//app-agent-group-detail//th[contains(text(),'Agent Code')]//following::td[1]");
}
