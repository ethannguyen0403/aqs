package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.master.bookiesystempopup.AccountListPopup;
import pages.sb11.master.bookiesystempopup.AgentListPopup;

public class BookieAgentPage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpBookie = DropDownBox.xpath("//div[contains(text(),'Bookie')]//following::select[1]");
    public DropDownBox ddpSuper = DropDownBox.xpath("//div[contains(text(),'Super')]//following::select[1]");
    public DropDownBox ddpMaster = DropDownBox.xpath("//div[contains(text(),'Master')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");

    public TextBox txtAgentCode = TextBox.xpath("//div[contains(text(),'Agent Code')]//following::input[1]");

    public Button btnSearch = Button.xpath("//button[contains(text(),'Search')]");
    public Button btnMoreFilters = Button.xpath("//button[contains(text(),'More Filters')]");
    public Button btnAddAgent = Button.xpath("//button[contains(text(),'Add Agent')]");

    public Label lblAgentCode = Label.xpath("//div[contains(text(),'Agent Code')]");

    public Table tbAgent = Table.xpath("//table",10);
    int colAgentCode = 4;
    int colMembers = 10;

    public void filterAgentCode (String companyUnit, String bookieCode, String superCode, String masterCode, String agentCode){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpBookie.selectByVisibleText(bookieCode);
        ddpSuper.selectByVisibleText(superCode);
        ddpMaster.selectByVisibleText(masterCode);
        if (!agentCode.isEmpty())
            txtAgentCode.sendKeys(agentCode);
        btnSearch.click();
    }

    public AccountListPopup openAccountList(String agentCode){
        int rowIndex = getAgentCodeRowIndex(agentCode);
        tbAgent.getControlOfCell(1,colMembers,rowIndex,null).click();
        return new AccountListPopup();
    }

    private int getAgentCodeRowIndex(String agentCode){
        int i = 1;
        Label lblAgentCode;
        while (true){
            lblAgentCode = Label.xpath(tbAgent.getxPathOfCell(1,colAgentCode,i,null));
            if(!lblAgentCode.isDisplayed()){
                System.out.println("The Agent Code "+agentCode+" does not display in the list");
                return 0;
            }
            if(lblAgentCode.getText().contains(agentCode))
                return i;
            i = i +1;
        }
    }
}
