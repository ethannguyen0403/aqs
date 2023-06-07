package pages.sb11.soccer.popup.coa;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;

public class CreateSubAccountPopup {
    Label lblTitle = Label.xpath("//app-create-ledger//span[contains(@class,'modal-title')]");
    public String getTitlePage (){return lblTitle.getText().trim();}

    public TextBox txtSubAccName = TextBox.xpath("//app-create-ledger//span[contains(text(),'Sub-Account Name')]//following::input[1]");
    public TextBox txtChartCode1 = TextBox.xpath("//app-create-ledger//span[contains(text(),'Sub-Account Number')]//following::input[1]");
    public TextBox txtChartCode2 = TextBox.xpath("//app-create-ledger//span[contains(text(),'Sub-Account Number')]//following::input[2]");
    public TextBox txtChartCode3 = TextBox.xpath("//app-create-ledger//span[contains(text(),'Sub-Account Number')]//following::input[3]");
    public TextBox txtChartCode4 = TextBox.xpath("//app-create-ledger//span[contains(text(),'Sub-Account Number')]//following::input[4]");
    public DropDownBox ddpDetailType = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'Detail Type')]//following::select[1]");
    public DropDownBox ddpAccType = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'Account Type')]//following::select[1]");
    public DropDownBox ddpParentAcc = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'Parent Account')]//following::select[1]");
    public DropDownBox ddpCurrency = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'CUR')]//following::select[1]");
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpClient = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'Client')]//following::select[1]");
    public DropDownBox ddpMaster = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'Master')]//following::select[1]");
    public DropDownBox ddpAgent = DropDownBox.xpath("//app-create-ledger//span[contains(text(),'Agent')]//following::select[1]");
    public TextBox txtSuper = TextBox.xpath("//app-create-ledger//span[contains(text(),'Super')]//following::input[1]");
    public Button btnSubmit = Button.xpath("//app-create-ledger//button[contains(text(),'Submit')]");
    public Button btnClear = Button.xpath("//app-create-ledger//span[contains(text(),'Clear')]");

    public void addSubAcc (String subAccount, String parentAcc, String code1, String code2, String code3, String code4, String currency, String client, String master, String agent, boolean isSubmit){
        txtSubAccName.sendKeys(subAccount);
        ddpParentAcc.selectByVisibleContainsText(parentAcc);
        if (!code1.isEmpty())
            txtChartCode1.sendKeys(code1);
        if (!code2.isEmpty())
            txtChartCode2.sendKeys(code2);
        if (!code3.isEmpty())
            txtChartCode3.sendKeys(code3);
        if (!code4.isEmpty())
            txtChartCode4.sendKeys(code4);
        ddpCurrency.selectByVisibleText(currency);
        if (!client.isEmpty())
            ddpClient.selectByVisibleText(client);
            if (!master.isEmpty())
                ddpMaster.selectByVisibleText(master);
            if (!agent.isEmpty())
                ddpAgent.selectByVisibleText(agent);
        if(isSubmit)
            btnSubmit.click();
    }
}
