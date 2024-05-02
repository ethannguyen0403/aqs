package pages.sb11.master.clientsystempopup;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import pages.sb11.WelcomePage;

import java.util.List;

public class AccountListPopup extends WelcomePage {
    Label lblTitle = Label.xpath("//app-account-list//div[contains(@class,'main-box-header')]//h6[1]");
    public DropDownBox ddType = DropDownBox.xpath("//div[@class='modal-content']//select[1]");
    public DropDownBox ddClientBookie = DropDownBox.xpath("//div[@class='modal-content']//select[2]");
    public Button btnClose = Button.xpath("//div[@class='modal-content']//span[contains(@class,'close-icon')]//em");

    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }

    public List<String> getLstBookie() {
        ddType.selectByVisibleText("Bookie");
        waitSpinnerDisappeared();
        return ddClientBookie.getOptions();
    }
    public void closeToPopup(){
        btnClose.click();
        waitSpinnerDisappeared();
        waitSpinnerDisappeared();
    }
}
