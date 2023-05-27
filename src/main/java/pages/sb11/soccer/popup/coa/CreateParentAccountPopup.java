package pages.sb11.soccer.popup.coa;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;

public class CreateParentAccountPopup {
    Label lblTitle = Label.xpath("//app-create-parent-account//span[contains(@class,'modal-title')]");
    public String getTitlePage (){return lblTitle.getText().trim();}

    public TextBox txtParentAccName = TextBox.xpath("//app-create-parent-account//span[contains(text(),'Parent Account Name')]//following::input[1]");
    public TextBox txtChartCode1 = TextBox.xpath("//app-create-parent-account//span[contains(text(),'Parent Account Number')]//following::input[1]");
    public TextBox txtChartCode2 = TextBox.xpath("//app-create-parent-account//span[contains(text(),'Parent Account Number')]//following::input[2]");
    public TextBox txtChartCode3 = TextBox.xpath("//app-create-parent-account//span[contains(text(),'Parent Account Number')]//following::input[3]");
    public TextBox txtChartCode4 = TextBox.xpath("//app-create-parent-account//span[contains(text(),'Parent Account Number')]//following::input[4]");
    public DropDownBox ddpDetailType = DropDownBox.xpath("//app-create-parent-account//span[contains(text(),'Detail Type')]//following::select[1]");
    public DropDownBox ddpAccType = DropDownBox.xpath("//app-create-parent-account//span[contains(text(),'Account Type')]//following::select[1]");
    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//app-create-parent-account//span[contains(text(),'Company Unit')]//following::select[1]");
    public Button btnSubmit = Button.xpath("//app-create-parent-account//button[contains(text(),'Submit')]");
    public Button btnClear = Button.xpath("//app-create-parent-account//span[contains(text(),'Clear')]");

    public void addParentAcc (String parentAccount, String code1, String code2, String code3, String code4, String detailType, boolean isSubmit){
        txtParentAccName.sendKeys(parentAccount);
        if (!code1.isEmpty())
            txtChartCode1.sendKeys(code1);
        if (!code2.isEmpty())
            txtChartCode2.sendKeys(code2);
        if (!code3.isEmpty())
            txtChartCode3.sendKeys(code3);
        if (!code4.isEmpty())
            txtChartCode4.sendKeys(code4);
        ddpDetailType.selectByVisibleContainsText(detailType);
        if(isSubmit)
            btnSubmit.click();
    }
}
