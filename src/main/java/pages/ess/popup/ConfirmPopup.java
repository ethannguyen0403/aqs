package pages.ess.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;

public class ConfirmPopup {
    public Label lblConfirm = Label.xpath("//app-confirm//span[contains(text(),'Confirm')]");
    public Label msgConfirm = Label.xpath("//app-confirm//h6[contains(@class,'modal-title')]");
    public Button btnYes = Button.xpath("//app-confirm//button[contains(text(),'Yes')]");
    public Button btnNo = Button.xpath("//app-confirm//button[contains(text(),'No')]");

    public void clickConfirmPopup(String name){
        String menu = name.toUpperCase();
        switch (menu){
            case "YES":
                btnYes.click();
                return;
            case "NO":
                btnNo.click();
                return;
        }
    }
}
