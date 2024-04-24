package pages.sb11.user;

import com.paltech.element.common.*;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.user.popup.*;
import utils.sb11.CompanySetUpUtils;

public class TradingPermissionPage extends WelcomePage {
    int colTotal = 9;
    public int colUsername = 3;
    int colAuto = 4;
    int colClientAgent = 5;
    int colClient = 6;
    int colSmartM = 7;
    int colSmartA = 8;
    int colSmartG = 9;
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpUserRole = DropDownBox.xpath("//div[contains(text(),'User Role')]//following::select[1]");
    public Label lblUsername = Label.xpath("//div[contains(text(),'Username')]");
    public TextBox txtUsername = TextBox.name("user-name");
    public Button btnShow = Button.xpath("//app-trading-permission//button[text()='Show']");
    public Table tbTradPermission = Table.xpath("//app-trading-permission//table",colTotal);
    public Label messageDialog = Label.xpath("//app-alert//div[@class='message-box']");

    public void filterAccount(String companyUnit, String userRole, String username){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpUserRole.selectByVisibleText(userRole);
        txtUsername.sendKeys(username);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public void clickAutoAssignAll(String username, boolean isChecked){
        int rowIndex = getRowContainsUsername(username);
        CheckBox cbAutoAssign = CheckBox.xpath(tbTradPermission.getxPathOfCell(1,colAuto,rowIndex,"input"));
        boolean isChecking = cbAutoAssign.isSelected();
        if (isChecked){
            if(!isChecking) {
                cbAutoAssign.click();
            }
        } else {
            if(isChecking) {
                cbAutoAssign.click();
            }
        }
    }

    public void verifyPermissionEnabled(String username, boolean isEnabled) throws InterruptedException {
        ClientAgentPermissionPopup clientAgentPermissionPopup = new ClientAgentPermissionPopup();
        ClientPermissionPopup clientPermissionPopup = new ClientPermissionPopup();
        SmartMasterPermissionPopup smartMasterPermissionPopup = new SmartMasterPermissionPopup();
        SmartAgentPermissionPopup smartAgentPermissionPopup = new SmartAgentPermissionPopup();
        SmartGroupPermissionPopup smartGroupPermissionPopup = new SmartGroupPermissionPopup();
        int rowIndex = getRowContainsUsername(username);
        if (isEnabled){
            Icon.xpath(tbTradPermission.getxPathOfCell(1,colClientAgent,rowIndex,null)).click();
            System.out.println("Title page is " + clientAgentPermissionPopup.getTitlePage());
            Thread.sleep(2000);
            Assert.assertTrue(clientAgentPermissionPopup.getTitlePage().contains("Client Agent Permission"),"Failed! Client Agent Permission popup is not displayed");
            clientAgentPermissionPopup.close();
            waitSpinnerDisappeared();
            Icon.xpath(tbTradPermission.getxPathOfCell(1,colClient,rowIndex,null)).click();
            Thread.sleep(2000);
            Assert.assertTrue(clientPermissionPopup.getTitlePage().contains("Client Permission"),"Failed! Client Permission popup is not displayed");
            clientPermissionPopup.close();

            Icon.xpath(tbTradPermission.getxPathOfCell(1,colSmartM,rowIndex,null)).click();
            Thread.sleep(2000);
            Assert.assertTrue(smartMasterPermissionPopup.getTitlePage().contains("Smart Master Permission"),"Failed! Smart Master Permission popup is not displayed");
            smartMasterPermissionPopup.close();

            Icon.xpath(tbTradPermission.getxPathOfCell(1,colSmartA,rowIndex,null)).click();
            Thread.sleep(2000);
            Assert.assertTrue(smartAgentPermissionPopup.getTitlePage().contains("Smart Agent Permission"),"Failed! Smart Agent Permission popup is not displayed");
            smartAgentPermissionPopup.close();

            Icon.xpath(tbTradPermission.getxPathOfCell(1,colSmartG,rowIndex,null)).click();
            Thread.sleep(2000);
            Assert.assertTrue(smartGroupPermissionPopup.getTitlePage().contains("Smart Group Permission"),"Failed! Smart Group Permission popup is not displayed");
            smartGroupPermissionPopup.close();

        } else {
            Icon.xpath(tbTradPermission.getxPathOfCell(1,colClientAgent,rowIndex,null)).click();
            Assert.assertTrue(messageDialog.getText().contains("You need to disable Auto-assigned first"), "Failed! Client Agent is enabled!");
            Icon.xpath(tbTradPermission.getxPathOfCell(1,colClient,rowIndex,null)).click();
            Assert.assertTrue(messageDialog.getText().contains("You need to disable Auto-assigned first"), "Failed! Failed! Client is enabled!");
            Icon.xpath(tbTradPermission.getxPathOfCell(1,colSmartM,rowIndex,null)).click();
            Assert.assertTrue(messageDialog.getText().contains("You need to disable Auto-assigned first"), "Failed! Smart(M) is enabled!");
            Icon.xpath(tbTradPermission.getxPathOfCell(1,colSmartA,rowIndex,null)).click();
            Assert.assertTrue(messageDialog.getText().contains("You need to disable Auto-assigned first"), "Failed! Smart(A) is enabled!");
            Icon.xpath(tbTradPermission.getxPathOfCell(1,colSmartG,rowIndex,null)).click();
            Assert.assertTrue(messageDialog.getText().contains("You need to disable Auto-assigned first"), "Failed! Smart(G) is enabled!");
        }
    }

    public boolean isAccountDisplayed(String username){
        int index = getRowContainsUsername(username);
        if(index==0)
            return false;
        return true;
    }

    private int getRowContainsUsername(String username){
        int i = 1;
        Label lblUserName;
        while (true){
            lblUserName = Label.xpath(tbTradPermission.getxPathOfCell(1,colUsername,i,null));
            if(!lblUserName.isDisplayed()){
                System.out.println("The username "+username+" does not display in the list");
                return 0;
            }
            if(lblUserName.getText().contains(username))
                return i;
            i = i +1;
        }
    }

    public void verifyUI() {
        System.out.println("Controls: Company Unit, User Role, Username and Show button");
        Assert.assertEquals(ddpCompanyUnit.getOptions(), CompanySetUpUtils.getListCompany(),"FAILED! Company Unit dropdown is not displayed!");
        Assert.assertTrue(ddpUserRole.getOptions().contains("Administrator"), "FAILED! Company Unit dropdown is not displayed!");
        Assert.assertEquals(lblUsername.getText(), "Username","FAILED! Username textbox is not displayed!");
        Assert.assertEquals(btnShow.getText(),"Show","Failed! Show button is not displayed!");
        System.out.println("Customer table header columns is correctly display");
        Assert.assertEquals(tbTradPermission.getHeaderNameOfRows(), SBPConstants.TradingPermission.TABLE_HEADER,"FAILED! Pending table header is incorrect display");
    }
}
