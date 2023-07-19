package pages.sb11.master.clientsystempopup;

import com.paltech.element.common.Label;

public class MemberListPopup {
    Label lblTitle = Label.xpath("//app-dialog-member-list//div[contains(@class,'main-box-header')]//h6[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
