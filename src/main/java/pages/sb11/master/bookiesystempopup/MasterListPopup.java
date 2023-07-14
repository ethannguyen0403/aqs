package pages.sb11.master.bookiesystempopup;

import com.paltech.element.common.Label;

public class MasterListPopup {
    Label lblTitle = Label.xpath("//app-master-list//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
