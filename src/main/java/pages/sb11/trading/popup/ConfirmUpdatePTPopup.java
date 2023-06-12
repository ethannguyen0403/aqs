package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;

public class ConfirmUpdatePTPopup {
    public Label lblConfirm = Label.xpath("//app-confirm//h6");
    public Button btnYes = Button.xpath("//app-confirm//button[contains(text(),'Yes')]");
    public Button btnNo = Button.xpath("//app-confirm//button[contains(text(),'No')]");

    public void confirmUpdate(boolean isYes){
        if (isYes){
            btnYes.click();
        } else {
            btnNo.click();
        }
    }

}
