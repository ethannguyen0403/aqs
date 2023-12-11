package pages.sb11.master;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.master.clientsystempopup.AccountListPopup;
import pages.sb11.master.clientsystempopup.AgentListPopup;
import pages.sb11.master.clientsystempopup.MasterListPopup;
import pages.sb11.master.clientsystempopup.MemberListPopup;

public class ClientSystemPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//label[text()='Company Unit']//following::select[1]");
    public DropDownBox ddpClientList = DropDownBox.xpath("//label[text()='Client List']//following::select[1]");
    public DropDownBox ddpCurrency = DropDownBox.xpath("//label[text()='Currency']//following::select[1]");
    public DropDownBox ddpStatus = DropDownBox.xpath("//label[text()='Status']//following::select[1]");

    public Label lblClientName = Label.xpath("//label[text()='Client Name']");
    public TextBox txtClientName = TextBox.id("name");

    public Button btnShow = Button.xpath("//button[text()='Show']");
    public Button btnAddClient = Button.xpath("//button[text()='Add Client']");
    public Button btnExportToExcel = Button.xpath("//button[contains(text(),'Export To Excel')]");

    public Table tbClient = Table.xpath("//div[@class='d-inline-block col-6']//table",6);
    public Table tbSuperMaster = Table.xpath("//div[@class='col-6 d-inline-block']//table",10);
    int colClientName = 3;
    int colXButton = 4;
    int colMaster = 5;
    int colAgent = 6;
    int colMember = 7;
    int colList = 10;

    public void filterClient(String companyUnit, String clientList, String clientName, String currency, String status) {
        if (!companyUnit.isEmpty())
            ddpCompanyUnit.selectByVisibleText(companyUnit);
        if (!clientList.isEmpty())
            ddpClientList.selectByVisibleText(clientList);
        if (!clientName.isEmpty())
            txtClientName.sendKeys(clientName);
        if (!currency.isEmpty())
            ddpCurrency.selectByVisibleText(currency);
        if (!status.isEmpty())
            ddpStatus.selectByVisibleText(status);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public boolean isClientCodeExist(String clientCode){
        int rowIndex = getClientCodeRowIndex(clientCode);
        String client = tbClient.getControlOfCell(1,colClientName,rowIndex,null).getText();
        if (client.contains(clientCode))
            return true;
        return false;
    }

    public void exportClientList(){
        btnExportToExcel.scrollToTop();
        btnExportToExcel.click();
    }

    public MasterListPopup openMasterList(String clientCode){
        int rowIndex = getClientCodeRowIndex(clientCode);
        tbSuperMaster.getControlOfCell(1,colMaster,rowIndex,null).click();
        waitSpinnerDisappeared();
        return new MasterListPopup();
    }

    public AgentListPopup openAgentList(String clientCode){
        int rowIndex = getClientCodeRowIndex(clientCode);
        tbSuperMaster.getControlOfCell(1,colAgent,rowIndex,null).click();
        return new AgentListPopup();
    }

    public AccountListPopup openAccountList(String clientCode){
        int rowIndex = getClientCodeRowIndex(clientCode);
        tbSuperMaster.getControlOfCell(1,colMember,rowIndex,null).click();
        return new AccountListPopup();
    }

    public MemberListPopup openMemberList(String clientCode){
        int rowIndex = getClientCodeRowIndex(clientCode);
        tbSuperMaster.getControlOfCell(1,colList,rowIndex,null).click();
        waitSpinnerDisappeared();
        return new MemberListPopup();
    }

    public BaseElement getControlXButton(String superCode) {
        int rowIndex = getSupeMasterCodeRowIndex(superCode);
        return tbSuperMaster.getControlOfCell(1, colXButton, rowIndex, null);
    }

    private int getClientCodeRowIndex(String clientCode){
        int i = 1;
        Label lblClientCode;
        while (true){
            lblClientCode = Label.xpath(tbClient.getxPathOfCell(1,colClientName,i,null));
            if(!lblClientCode.isDisplayed()){
                System.out.println("The client code "+clientCode+" does not display in the list");
                return 0;
            }
            if(lblClientCode.getText().contains(clientCode))
                return i;
            i = i +1;
        }
    }

    private int getSupeMasterCodeRowIndex(String superCode) {
        int i = 1;
        int colIndex = tbSuperMaster.getColumnIndexByName("Super Master");
        Label lblClientCode;
        while (true) {
            lblClientCode = Label.xpath(tbSuperMaster.getxPathOfCell(1, colIndex, i, null));
            if (!lblClientCode.isDisplayed()) {
                System.out.println("The master code " + superCode + " does not display in the list");
                return 0;
            }
            if (lblClientCode.getText().contains(superCode))
                return i;
            i = i + 1;
        }
    }

    public boolean verifyElementIsDisabled(BaseElement element, String attribute){
        return element.getAttribute(attribute).contains("disabled");
    }

    public String getTooltipText(BaseElement element){
        return element.getAttribute("title").trim();
    }
}