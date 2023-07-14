package pages.sb11.master.bookiesystempopup;

import com.paltech.element.common.Label;

public class AccountListPopup {
    Label lblTitle = Label.xpath("//app-bookie-agent-list-account//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
