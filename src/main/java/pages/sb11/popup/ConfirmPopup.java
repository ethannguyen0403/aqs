package pages.sb11.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;

public class ConfirmPopup {
    public Label lblHeader = Label.xpath("//app-confirm//div[contains(@class,'box-dialog-header')]");
    public Label lblConfirm = Label.xpath("//app-confirm//h6");
    public Button btnYes = Button.xpath("//app-confirm//button[contains(text(),'Yes')]");
    public Button btnOK = Button.xpath("//app-confirm//button[contains(text(),'Ok')]");
    public Button btnNo = Button.xpath("//app-confirm//button[contains(text(),'No')]");

    public void confirm(boolean yes){
        if(yes)
            btnYes.click();
        else
            btnNo.click();
    }

    public String getContentMessage() throws InterruptedException {
        //wait for content display
        Thread.sleep(2000);
        return lblConfirm.getText();
    }

}
