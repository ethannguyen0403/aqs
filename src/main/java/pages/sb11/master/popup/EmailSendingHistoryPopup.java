package pages.sb11.master.popup;

import com.paltech.element.common.Label;

public class EmailSendingHistoryPopup {
    Label lblTitle = Label.xpath("//app-email-sending-history//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return this.lblTitle.getText().trim();
    }
}
