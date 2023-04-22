package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;

public class BLSettingsPopup {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public TextBox txtChannel = TextBox.id("account-code");
    public DropDownBox ddpKP = DropDownBox.xpath("//label[contains(text(),'KP')]//following::select[1]");
    public DropDownBox ddpLiveRB = DropDownBox.xpath("//label[contains(text(),'Live RB')]//following::select[1]");
    public Button btnSubmit = Button.xpath("//button[contains(text(),'Submit')]");
    public Button btnClose = Button.xpath("//span[contains(@class,'close-icon')]");

    public void fillBLSettings (String channel, String KP, String liveRB, boolean isSubmit){
        txtChannel.sendKeys(channel);
        ddpKP.selectByVisibleText(KP);
        ddpLiveRB.selectByVisibleText(liveRB);
        if (isSubmit){
            btnSubmit.click();
        }
    }
}
