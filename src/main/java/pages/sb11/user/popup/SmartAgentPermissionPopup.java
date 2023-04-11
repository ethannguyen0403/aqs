package pages.sb11.user.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;

public class SmartAgentPermissionPopup {
    Label lblTitle = Label.xpath("//app-trading-permission-editor//div[contains(@class,'main-box-header')]//span[2]");
    public String getTitlePage (){return lblTitle.getText().trim();}
    public Button btnClose = Button.xpath("//app-trading-permission-editor//div[contains(@class,'close-btn')]");
    public void close(){btnClose.click();}
}
