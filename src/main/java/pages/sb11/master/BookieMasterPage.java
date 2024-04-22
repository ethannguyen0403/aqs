package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.master.bookiesystempopup.AgentListPopup;
import pages.sb11.master.bookiesystempopup.MasterListPopup;
import utils.sb11.CompanySetUpUtils;

public class BookieMasterPage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpBookie = DropDownBox.xpath("//div[contains(text(),'Bookie')]//following::select[1]");
    public DropDownBox ddpSuper = DropDownBox.xpath("//div[contains(text(),'Super')]//following::select[1]");
    public DropDownBox ddpGoTo = DropDownBox.xpath("//span[contains(text(),'Go To')]//following::select[1]");

    public TextBox txtMasterCode = TextBox.xpath("//div[contains(text(),'Master Code')]//following::input[1]");

    public Button btnSearch = Button.xpath("//button[contains(text(),'Search')]");
    public Button btnMoreFilters = Button.xpath("//button[contains(text(),'More Filters')]");
    public Button btnAddMaster = Button.xpath("//button[contains(text(),'Add Master')]");

    public Label lblMasterCode = Label.xpath("//div[contains(text(),'Master Code')]");

    public Table tbMaster = Table.xpath("//table",10);
    Label lblSpin = Label.xpath("//div[contains(@class,'la-ball-clip-rotate')]");
    int colMasterCode = 4;
    public Table tblAgent = Table.xpath("(//table)[2]",1);

    public void filterMasterCode (String companyUnit, String bookieCode, String superCode, String masterCode){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpBookie.selectByVisibleText(bookieCode);
        ddpSuper.selectByVisibleText(superCode);
        if (!masterCode.isEmpty())
            txtMasterCode.sendKeys(masterCode);
        btnSearch.click();
        waitSpinnerDisappeared();
    }

    public AgentListPopup openAgentList(String masterCode){
        int rowIndex = getMasterCodeRowIndex(masterCode);
        tblAgent.getControlOfCell(1,tblAgent.getColumnIndexByName("# Agent"),rowIndex,null).click();
        waitSpinnerDisappeared();
        return new AgentListPopup();
    }

    private int getMasterCodeRowIndex(String masterCode){
        int i = 1;
        Label lblMasterCode;
        while (true){
            lblMasterCode = Label.xpath(tbMaster.getxPathOfCell(1,colMasterCode,i,null));
            if(!lblMasterCode.isDisplayed()){
                System.out.println("The Master Code "+masterCode+" does not display in the list");
                return 0;
            }
            if(lblMasterCode.getText().contains(masterCode))
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
        Assert.assertEquals(ddpGoTo.getOptions(), SBPConstants.BookieSystem.GO_TO_LIST,"Failed! Go To dropdown is not displayed!");
        System.out.println("Textbox: Master Code");
        Assert.assertEquals(lblMasterCode.getText(),"Master Code","Failed! Master Code textbox is not displayed!");
        System.out.println("Button: Search, Add Master, More Filter");
        Assert.assertEquals(btnSearch.getText(),"Search","Failed! Search button is not displayed!");
        Assert.assertEquals(btnMoreFilters.getText(),"More Filters","Failed! More Filters button is not displayed!");
        Assert.assertEquals(btnAddMaster.getText(),"Add Master","Failed! Add Master button is not displayed!");
        System.out.println("Validate Master list table is displayed with correct header");
        Assert.assertEquals(tbMaster.getHeaderNameOfRows(), SBPConstants.BookieSystem.TABLE_HEADER_MASTER,"Failed! Master list table is displayed with incorrect header");
    }
}
