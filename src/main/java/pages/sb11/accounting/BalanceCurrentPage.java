package pages.sb11.accounting;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.accounting.popup.MemberSummaryPopup;

import java.net.HttpURLConnection;

public class BalanceCurrentPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'row header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[text()='Company Unit']//following::select[1]");
    public DropDownBox ddpAccount = DropDownBox.xpath("//div[text()='Account']//following::select[1]");
    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");

    public Table tbMaster = Table.xpath("//app-client-current-balance/div/div[3]/table",5);
    public Table tbAgent = Table.xpath("//app-client-current-balance/div/div[4]/table",5);
    int colMasterAgent = 3;
    int colCur = 4;

    public void filterAccount (String companyUnit, String account){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpAccount.selectByVisibleContainsText(account);
        btnShow.click();
    }

    public MemberSummaryPopup openMasterMemberSummaryPopup (String masterCode){
        int rowIndex = getMasterRowIndex(masterCode);
        tbMaster.getControlOfCell(1,colMasterAgent, rowIndex,"a").click();
        return new MemberSummaryPopup();
    }

    public MemberSummaryPopup openAgentMemberSummaryPopup (String agentCode){
        int rowIndex = getAgentRowIndex(agentCode);
        tbAgent.getControlOfCell(1,colMasterAgent, rowIndex,"a").click();
        return new MemberSummaryPopup();
    }

    private int getMasterRowIndex(String masterCode){
        int i = 1;
        Label lblMaster;
        while (true){
            lblMaster = Label.xpath(tbMaster.getxPathOfCell(1,colMasterAgent,i,null));
            if(!lblMaster.isDisplayed()) {
                System.out.println("Can NOT found the master "+masterCode+" in the table");
                return 0;
            }
            if(lblMaster.getText().contains(masterCode)){
                System.out.println("Found the Master "+masterCode+" in the table");
                return i;
            }
            i = i +1;
        }
    }

    private int getAgentRowIndex(String agentCode){
        int i = 2;
        Label lblAgent;
        while (true){
            lblAgent = Label.xpath(tbAgent.getxPathOfCell(1,colMasterAgent,i,null));
            if(!lblAgent.isDisplayed()) {
                System.out.println("Can NOT found the agent "+agentCode+" in the table");
                return 0;
            }
            if(lblAgent.getText().contains(agentCode)){
                System.out.println("Found the agent "+agentCode+" in the table");
                return i;
            }
            i = i +1;
        }
    }
}
