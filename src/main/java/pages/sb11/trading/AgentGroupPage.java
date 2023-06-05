package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.*;

public class AgentGroupPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCurrency = DropDownBox.xpath("//div[contains(text(),'Currency')]//following::select[1]");
    public DropDownBox ddpOrderBy = DropDownBox.xpath("//div[contains(text(),'Order By')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");
    public TextBox txtAgentCode = TextBox.xpath("//div[contains(text(),'Agent Code')]//following::input[1]");
    public Button btnSearch = Button.xpath("//button[contains(@class,'btn-outline-secondary')]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Button btnAddAgent = Button.xpath("//button[contains(text(),'Add Agent')]");
    public Table tbAgent = Table.xpath("//app-agent-group//table",11);
    int colAgent = 3;
    int colGroup = 8;
    int colCL = 9;

    public void filterAgent (String currency, String orderBy, String agentCode){
        if (!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        ddpOrderBy.selectByVisibleText(orderBy);
        txtAgentCode.sendKeys(agentCode);
        btnShow.click();
    }

    public AgentGroupReportPopup openAgentGroupReport(String agentCode){
        int rowIndex = getAgentRowIndex(agentCode);
        tbAgent.getControlOfCell(1,colAgent,rowIndex,null).click();
        return new AgentGroupReportPopup();
    }

    public AgentGroupListPopup openGroupListPopup(String agentCode){
        int rowIndex = getAgentRowIndex(agentCode);
        tbAgent.getControlOfCell(1,colGroup,rowIndex,null).click();
        return new AgentGroupListPopup();
    }

    public AgentGroupClientListPopup openAgentGroupClientList(String agentCode){
        int rowIndex = getAgentRowIndex(agentCode);
        tbAgent.getControlOfCell(1,colCL,rowIndex,null).click();
        return new AgentGroupClientListPopup();
    }

    public String getGroupAmount(String agentCode){
        int rowIndex = getAgentRowIndex(agentCode);
        return tbAgent.getControlOfCell(1,colGroup,rowIndex,null).getText();
    }

    private int getAgentRowIndex(String agentCode){
        int i = 1;
        Label lblAgent;
        while (true){
            lblAgent = Label.xpath(tbAgent.getxPathOfCell(1,colAgent,i,null));
            if(!lblAgent.isDisplayed()) {
                System.out.println("Can NOT found the Agent code "+agentCode+" in the table");
                return 0;
            }
            if(lblAgent.getText().contains(agentCode)){
                System.out.println("Found the Agent code "+agentCode+" in the table");
                return i;
            }
            i = i +1;
        }
    }
}
