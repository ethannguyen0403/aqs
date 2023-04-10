package pages.sb11.sport.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;

public class ConfirmDeleteLeaguePopup {
    public Label lblConfirm = Label.xpath("//app-confirm//h6");
    public Button btnYes = Button.xpath("//app-confirm//button[contains(text(),'Yes')]");
    public Button btnNo = Button.xpath("//app-confirm//button[contains(text(),'No')]");

    public void deleteLeague(boolean isDeleted){
        if (isDeleted){
            btnYes.click();
        } else {
            btnNo.click();
        }
    }

}
