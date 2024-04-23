package pages.sb11.trading.popup;

import com.paltech.element.common.Label;
import org.testng.Assert;

public class AgentGroupClientListPopup {
    Label lblTitle = Label.xpath("//app-client-list-agent//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Label lblAgent = Label.xpath("//app-client-list-agent//span[contains(@class,'text-truncate')]");

    public void isAgentGroupClientListPopup(String agentCode) {
        Assert.assertTrue(getTitlePage().contains("Client List"),"Failed! Client List page is not displayed!");
        Assert.assertEquals(lblAgent.getText(),agentCode,"Failed! Agent Code is displayed incorrectly!");
    }
}
