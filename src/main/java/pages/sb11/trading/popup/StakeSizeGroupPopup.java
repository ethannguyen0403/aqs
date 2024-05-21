package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import org.testng.Assert;
import pages.sb11.WelcomePage;

public class StakeSizeGroupPopup extends WelcomePage {
    DropDownBox ddCompanyUnit = DropDownBox.xpath("//app-stake-group-creation//td[contains(text(),'Company Unit')]//following-sibling::td/select");
    DropDownBox ddClient = DropDownBox.xpath("//app-stake-group-creation//td[contains(text(),'Client')]//following-sibling::td/select");
    TextBox txtGroupName = TextBox.xpath("//app-stake-group-creation//th[contains(text(),'Group Name')]//following-sibling::th/input");
    TextBox txtStageRangeFrom = TextBox.xpath("//app-stake-group-creation//span[text()='From']/parent::div//following-sibling::input");
    TextBox txtStageRangeTo = TextBox.xpath("//app-stake-group-creation//span[text()='To']/parent::div//following-sibling::input");
    public Button btnSubmit = Button.xpath("//app-stake-group-creation//button[text()='Submit']");
    public Button btnClear = Button.xpath("//app-stake-group-creation//span[text()='Clear']");

    public void fillFields(String companyName, String clientCode, String groupName, double stakeRangeFrom, double stakeRangeTo) {
        if (!companyName.isEmpty()){
            ddCompanyUnit.selectByVisibleText(companyName);
            waitSpinnerDisappeared();
            waitSpinnerDisappeared();
        }
        if (!clientCode.isEmpty()){
            ddClient.selectByVisibleText(clientCode);
            waitSpinnerDisappeared();
        }
        if (!groupName.isEmpty()){
            txtGroupName.sendKeys(groupName);
        }
        txtStageRangeFrom.sendKeys(String.valueOf(stakeRangeFrom));
        txtStageRangeTo.sendKeys(String.valueOf(stakeRangeTo));
    }
    public void addNewGroup(String companyName, String clientCode, String groupName, double stakeRangeFrom, double stakeRangeTo, boolean submit){
        fillFields(companyName,clientCode,groupName,stakeRangeFrom,stakeRangeTo);
        if (submit){
            btnSubmit.click();
            waitSpinnerDisappeared();
        }
    }

    public void verifyUIDefault() {
        Assert.assertEquals(ddCompanyUnit.getFirstSelectedOption(), SBPConstants.StakeSizeGroupNewPopup.PLEASE_SELECT,"FAILED! Company Unit Dropdown display incorrect");
        Assert.assertEquals(ddClient.getFirstSelectedOption(), SBPConstants.StakeSizeGroupNewPopup.PLEASE_SELECT,"FAILED! Client Dropdown display incorrect");
        Assert.assertEquals(txtGroupName.getText(), "","FAILED! Group Name textbox display incorrect");
        Assert.assertEquals(txtStageRangeFrom.getText(), "","FAILED! Stake Range From textbox display incorrect");
        Assert.assertEquals(txtStageRangeTo.getText(), "","FAILED! Stake Range To textbox display incorrect");
    }
}
