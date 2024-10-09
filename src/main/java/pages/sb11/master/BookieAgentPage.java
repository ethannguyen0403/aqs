package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.master.bookiesystempopup.AccountListPopup;
import utils.sb11.accounting.CompanySetUpUtils;

public class BookieAgentPage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//span[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpBookie = DropDownBox.xpath("//div[@class='card-body']//span[contains(text(),'Bookie')]//following::select[1]");
    public DropDownBox ddpSuper = DropDownBox.xpath("//span[contains(text(),'Super')]//following::select[1]");
    public DropDownBox ddpMaster = DropDownBox.xpath("//div[@class='card-body']//span[contains(text(),'Master')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");

    public TextBox txtAgentCode = TextBox.xpath("//span[contains(text(),'Agent Code')]//following::input[1]");

    public Button btnSearch = Button.xpath("//button[contains(text(),'Search')]");
    public Button btnMoreFilters = Button.xpath("//button[contains(text(),'More Filters')]");
    public Button btnAddAgent = Button.xpath("//button[contains(text(),'Add Agent')]");

    public Label lblAgentCode = Label.xpath("//div[@class='card-body']//span[contains(text(),'Agent Code')]");

    public Table tbAgent = Table.xpath("//table",11);
    int colAgentCode = 4;
    int colMembers = 10;
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");

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
        tbAgent.getControlOfCell(1,tbAgent.getColumnIndexByName("# Members"),rowIndex,null).click();
        waitSpinnerDisappeared();
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
    public void waitSpinnerDisappeared() {
        while(lblSpin.isDisplayed()) {
            lblSpin.waitForControlInvisible();
        }
    }

    public void verifyUI(String bookieName) {
        System.out.println("Dropdown: Company Unit, Bookie, Go To");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"Failed! Company Unit dropdown iss not displayed!");
        Assert.assertTrue(ddpBookie.getOptions().contains(bookieName),"Failed! Bookie dropdown is not displayed!");
        Assert.assertTrue(ddpSuper.getOptions().contains("[No Super]"),"Failed! Super dropdown is not displayed!");
        Assert.assertTrue(ddpMaster.isDisplayed(),"Failed! Master dropdown is not displayed!");
        Assert.assertEquals(ddpGoTo.getOptions(), SBPConstants.BookieSystem.GO_TO_LIST,"Failed! Go To dropdown is not displayed!");
        System.out.println("Textbox: Master Code");
        Assert.assertEquals(lblAgentCode.getText(),"Agent Code","Failed! Agent Code textbox is not displayed!");
        System.out.println("Button: Search, Add Agent, More Filter");
        Assert.assertEquals(btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(btnMoreFilters.getText(),"More Filters","Failed! More Filters button is not displayed!");
        Assert.assertEquals(btnAddAgent.getText(),"Add Agent","Failed! Add Agent button is not displayed!");
        System.out.println("Validate Agent list table is displayed with correct header");
        Assert.assertEquals(tbAgent.getHeaderNameOfRows(), SBPConstants.BookieSystem.TABLE_HEADER_AGENT,"Failed! Agent list table is displayed with incorrect header");
    }
}
