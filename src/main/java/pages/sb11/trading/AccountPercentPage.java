package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.Table;
import pages.sb11.WelcomePage;
import pages.sb11.trading.popup.BLSettingsPopup;
import pages.sb11.trading.popup.ConfirmUpdatePTPopup;

public class AccountPercentPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
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
    }

    public void editPercent(String accountCode, String PT){
        int rowIndex = getRowContainsAccCode(accountCode);
        tbAccPercent.getControlOfCell(1,colPercent, rowIndex,"span//em").click();
        TextBox txtPT = TextBox.xpath(tbAccPercent.getxPathOfCell(1,colPercent,rowIndex,"input"));
        txtPT.click();
        txtPT.sendKeys(PT);
        tbAccPercent.getControlOfCell(1,colPercent, rowIndex,"span[1]//em").click();
        ConfirmUpdatePTPopup confirmUpdatePTPopup = new ConfirmUpdatePTPopup();
        confirmUpdatePTPopup.confirmUpdate(true);
        waitSpinnerDisappeared();
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


}
