package pages.sb11.master.clientsystempopup;

import com.paltech.element.common.Label;

public class MasterListPopup {
    Label lblTitle = Label.xpath("//app-dialog-master-list//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }


}
