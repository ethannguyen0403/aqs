package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

public class SmartSystemPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public Button btnMasterGroup = Button.xpath("//span[text()='Master Group']");
    public Button btnAgentGroup = Button.xpath("//span[text()='Agent Group']");
    public Button btnSmartGroup = Button.xpath("//span[text()='Smart Group']");

    public MasterGroupPage goToMasterGroup(){
        btnMasterGroup.click();
        waitPageLoad();
        return new MasterGroupPage();
    }
    public AgentGroupPage goToAgentGroup(){
        btnAgentGroup.click();
        waitPageLoad();
        return new AgentGroupPage();
    }
    public SmartGroupPage goToSmartGroup(){
        btnSmartGroup.click();
        waitPageLoad();
        return new SmartGroupPage();
    }
}
