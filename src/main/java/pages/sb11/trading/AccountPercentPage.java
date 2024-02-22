package pages.sb11.trading;

import com.paltech.element.common.*;
import common.SBPConstants;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.BLSettingsPopup;
import pages.sb11.trading.popup.ConfirmUpdatePTPopup;

public class AccountPercentPage extends WelcomePage {
    public Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpBookie = DropDownBox.xpath("//label[contains(text(),'Bookie')]//following::select[1]");
    public DropDownBox ddpType = DropDownBox.xpath("//label[contains(text(),'Type')]//following::select[1]");
    public Label lblAccountCode = Label.xpath("//label[contains(text(),'Account Code')]");
    public TextBox txtAccountCode = TextBox.xpath("//label[contains(text(),'Account Code')]//following::input[1]");
    public Button btnSearch = Button.xpath("//button[contains(@class,'search-icon')]");

    int totalCol = 6;
    int colPercent = 4;
    int colAccCode = 3;
    public Table tbAccPercent = Table.xpath("//app-account-percent//table",6);

    public void filterAccount(String bookie, String type, String accountCode){
        ddpBookie.selectByVisibleText(bookie);
        if(!type.isEmpty()){
            ddpType.selectByVisibleText(type);
        }
        txtAccountCode.sendKeys(accountCode);
        btnSearch.click();
        waitSpinnerDisappeared();
    }

    public void editPercent(String accountCode, String PT, boolean save){
        int rowIndex = getRowContainsAccCode(accountCode);
        tbAccPercent.getControlOfCell(1,colPercent, rowIndex,"span//em").click();
        TextBox txtPT = TextBox.xpath(tbAccPercent.getxPathOfCell(1,colPercent,rowIndex,"input"));
        txtPT.click();
        txtPT.sendKeys(PT);
        if (save){
            tbAccPercent.getControlOfCell(1,colPercent, rowIndex,"span[1]//em").click();
            ConfirmUpdatePTPopup confirmUpdatePTPopup = new ConfirmUpdatePTPopup();
            confirmUpdatePTPopup.confirmUpdate(true);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            clickToXIcon(accountCode);
        }
    }

    public String getAccPT(String accountCode){
        int rowIndex = getRowContainsAccCode(accountCode);
        return tbAccPercent.getControlOfCell(1,colPercent, rowIndex,"span").getText();
    }

    private int getRowContainsAccCode(String accountCode){
        int i = 1;
        Label lblAccountCode;
        while (true){
            lblAccountCode = Label.xpath(tbAccPercent.getxPathOfCell(1,colAccCode,i,null));
            if(!lblAccountCode.isDisplayed()){
                System.out.println("The Account Code "+accountCode+" does not display in the list");
                return 0;
            }
            if(lblAccountCode.getText().contains(accountCode)){
                System.out.println("Found the Account Code "+accountCode+" in the table");
                return i;
            }
            i = i +1;
        }
    }
    public void verifyLayoutOfPage() {
        Assert.assertTrue(getTitlePage().contains(SBPConstants.ACCOUNT_PERCENT),"FAILED! Title page display incorrect");
        Assert.assertTrue(ddpBookie.isEnabled(),"FAILED! Bookie dropdown display incorrect");
        Assert.assertTrue(ddpType.isEnabled(),"FAILED! Type dropdown display incorrect");
        Assert.assertTrue(txtAccountCode.isEnabled(),"FAILED! Account Code textbox display incorrect");
        Assert.assertTrue(btnSearch.isEnabled(),"FAILED! Search Button display incorrect");
        Assert.assertEquals(tbAccPercent.getHeaderNameOfRows(),SBPConstants.AccountPercent.TABLE_HEADER,"FAILED! Header table display incorrect");
    }

    public void verifyTooltipDisplay(String accountCode, String accountIDEx, String createdByEx, String createdDateEx, String modifiedEx) {
        int rowIndex = getRowContainsAccCode(accountCode);
        Icon iconI = Icon.xpath(tbAccPercent.getxPathOfCell(1,tbAccPercent.getColumnIndexByName("i"),rowIndex,"i"));
        iconI.moveAndHoverOnControl();
        //Wait for tooltip display
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String tooltipValueXpath = "//popover-container//div[text()='%s']/following-sibling::div";
        String accountIDAc = Label.xpath(String.format(tooltipValueXpath,"Account ID")).getText().trim();
        String createdByAc = Label.xpath(String.format(tooltipValueXpath,"Created By")).getText().trim();
        String createDateAc = Label.xpath(String.format(tooltipValueXpath,"Created Date")).getText().trim();
        String modifiedByAc = Label.xpath(String.format(tooltipValueXpath,"Modified By")).getText().trim();
        Assert.assertTrue(accountIDAc.equals(accountIDEx),"FAILED! Account ID display incorrect");
        Assert.assertTrue(createdByAc.equals(createdByEx),"FAILED! Created By display incorrect");
        Assert.assertTrue(createDateAc.equals(createdDateEx),"FAILED! Created Date display incorrect");
        Assert.assertTrue(modifiedByAc.equals(modifiedEx),"FAILED! Modified By display incorrect");
    }
    public void clickToXIcon(String accountCode){
        int rowIndex = getRowContainsAccCode(accountCode);
        tbAccPercent.getControlOfCell(1,colPercent, rowIndex,"span[2]//em").click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
