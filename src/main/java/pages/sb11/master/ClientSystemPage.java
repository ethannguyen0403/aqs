package pages.sb11.master;

import com.paltech.element.BaseElement;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.master.clientsystempopup.AccountListPopup;
import pages.sb11.master.clientsystempopup.AgentListPopup;
import pages.sb11.master.clientsystempopup.MasterListPopup;
import pages.sb11.master.clientsystempopup.MemberListPopup;
import utils.sb11.CompanySetUpUtils;

import static common.SBPConstants.COMPANY_UNIT_LIST;

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

    public Button btnShow = Button.xpath("//button[contains(@class,'btn-show')]");
    public Button btnAddClient = Button.xpath("//button[text()='Add Client']");
    public Button btnExportToExcel = Button.xpath("//button[contains(text(),'Export To Excel')]");

    public Table tbClient = Table.xpath("//div[@class='d-inline-block col-6']//table",6);
    public Table tbSuperMaster = Table.xpath("//div[@class='col-6 d-inline-block']//table",10);
    int colClientName = 3;
    int colMaster = 5;
    int colAgent = 6;
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
        int rowIndex = tbClient.getRowIndexContainValue(clientCode,tbClient.getColumnIndexByName("Client Name"),null);
        String client = tbClient.getControlOfCell(1,colClientName,rowIndex,null).getText();
        if (client.contains(clientCode))
            return true;
        return false;
    }

    public void exportClientList(){
        btnExportToExcel.scrollToTop();
        btnExportToExcel.click();
        waitSpinnerDisappeared();
    }

    public MasterListPopup openMasterList(String clientCode){
        int rowIndex = tbClient.getRowIndexContainValue(clientCode,tbClient.getColumnIndexByName("Client Name"),null);
        tbSuperMaster.getControlOfCell(1,colMaster,rowIndex,null).click();
        waitSpinnerDisappeared();
        return new MasterListPopup();
    }

    public AgentListPopup openAgentList(String clientCode){
        int rowIndex = tbClient.getRowIndexContainValue(clientCode,tbClient.getColumnIndexByName("Client Name"),null);
        tbSuperMaster.getControlOfCell(1,colAgent,rowIndex,null).click();
        return new AgentListPopup();
    }

    public AccountListPopup openAccountList(String clientCode){
        int rowIndex = tbClient.getRowIndexContainValue(clientCode,tbClient.getColumnIndexByName("Client Name"),null);
        tbSuperMaster.getControlOfCell(1, tbSuperMaster.getColumnIndexByName("#Memb"),rowIndex,null).click();
        waitSpinnerDisappeared();
        return new AccountListPopup();
    }

    public MemberListPopup openMemberList(String clientCode){
        int rowIndex = tbClient.getRowIndexContainValue(clientCode,tbClient.getColumnIndexByName("Client Name"),null);
        tbSuperMaster.getControlOfCell(1,tbSuperMaster.getColumnIndexByName("List"),rowIndex,null).click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
        return new MemberListPopup();
    }

    public BaseElement getControlXButton(String superCode) {
        int rowIndex = tbSuperMaster.getRowIndexContainValue(superCode,tbSuperMaster.getColumnIndexByName("Super Master"),null);
        return tbSuperMaster.getControlOfCell(1, tbSuperMaster.getColumnIndexByName(""), rowIndex, null);
    }

    public boolean verifyElementIsDisabled(BaseElement element, String attribute){
        return element.getAttribute(attribute).contains("disabled");
    }

    public String getTooltipText(BaseElement element){
        return element.getAttribute("title").trim();
    }

    public void verifyUI() {
        System.out.println("Dropdown: Company Unit, Support By, Currency, Status");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown is not displayed!");
        Assert.assertEquals(ddpClientList.getOptions(), SBPConstants.ClientSystem.CLIENT_LIST,"Failed! Client List dropdown is not displayed!");
        Assert.assertEquals(ddpCurrency.getOptions(), SBPConstants.ClientSystem.CURRENCY_LIST,"Failed! Currency dropdown is not displayed!");
        Assert.assertEquals(ddpStatus.getOptions(), SBPConstants.ClientSystem.STATUS_LIST,"Failed! Status dropdown is not displayed!");
        System.out.println("Textbox: Client Name");
        Assert.assertEquals(lblClientName.getText(),"Client Name","Failed! Client Name textbox is not displayed!");
        System.out.println("Button: Show, Add Bookie, Export To Excel");
        Assert.assertEquals(btnShow.getText(),"SHOW","Failed! Show button is not displayed!");
        Assert.assertEquals(btnAddClient.getText(),"Add Client","Failed! Add Client button is not displayed!");
        Assert.assertEquals(btnExportToExcel.getText(),"Export To Excel","Failed! Export To Excel button is not displayed!");
        System.out.println("Validate Client System table is displayed with correct header");
        Assert.assertEquals(tbClient.getHeaderNameOfRows(), SBPConstants.ClientSystem.TABLE_HEADER_CLIENT,"Failed! Client table header is displayed incorrectly!!");
        Assert.assertEquals(tbSuperMaster.getHeaderNameOfRows(), SBPConstants.ClientSystem.TABLE_HEADER_SUPER_MASTER,"Failed! Super Master table header is displayed incorrectly!!");
    }
}