package pages.sb11.master;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

public class AccountSearchPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//span[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpType = DropDownBox.xpath("//span[contains(text(),'Type')]//following::select[1]");
    public TextBox txtAccountSearch = TextBox.name("searchBy");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");

    public Label lblAccountInfo = Label.xpath("//span[text()='Account Info']");
    public Label lblBookieInfo = Label.xpath("//span[text()='Bookie Info']");
    public Label lblClientInfo = Label.xpath("//span[text()='Client Info']");
    public Label lblSuperInfo = Label.xpath("//span[text()='Super Info']");
    public Label lblCASSuperInfo = Label.xpath("//span[text()='CAS Super Info']");
    public Label lblMasterInfo = Label.xpath("//span[text()='Master Info']");
    public Label lblCASMasterInfo = Label.xpath("//span[text()='CAS Master Info']");
    public Label lblAgentInfo = Label.xpath("//span[text()='Agent Info']");
    public Label lblCASAgentInfo = Label.xpath("//span[text()='CAS Agent Info']");
    public Label lblAccountEmailHeader = Label.xpath("//span[text()='Account - Email Header Info']");
    public Label lblSuperEmailHeader = Label.xpath("//span[text()='CAS Super - Email Header Info']");
    public Label lblAddressBook = Label.xpath("//span[text()='Account - Address Book']");
    public Label lblSmartGroupInfo = Label.xpath("//span[text()='Smart Group Info']");
    public Label lblNoRecordFound = Label.xpath("//span[contains(text(),'No record found.')]");
    public Label lblClientNameValue = Label.xpath("((//div[@class='panel-header'])[3]//following-sibling::table//td)[2]");

    public void filterAccount (String companyUnit, String type, String accountCodeID){
        ddpCompanyUnit.selectByVisibleText(companyUnit);
        ddpType.selectByVisibleText(type);
        txtAccountSearch.sendKeys(accountCodeID);
        btnShow.click();
        waitSpinnerDisappeared();
    }

    public boolean isClientNameDisplay(String clientName) {
        if (lblNoRecordFound.isDisplayed()){
            System.out.println("No record found.");
            return true;
        } else {
            if (!clientName.equals(lblClientNameValue.getText())){
                System.out.println(clientName+" is not displayed");
                return false;
            }
            return true;
        }
    }
}
