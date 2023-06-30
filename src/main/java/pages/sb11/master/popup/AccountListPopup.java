package pages.sb11.master.popup;

import com.paltech.element.common.Label;

public class AccountListPopup {
    Label lblTitle = Label.xpath("//app-account-list//div[contains(@class,'main-box-header')]//h6[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
