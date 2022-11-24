package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;

public class ConfirmPopup {
    public Label lblHeader = Label.xpath("//app-confirm//div[contains(@class,'box-dialog-header')]");
    public Label lblConfirm = Label.xpath("//app-confirm//h6");
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
