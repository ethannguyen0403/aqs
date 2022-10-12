package pages.ess.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import com.paltech.element.common.Popup;
import controls.Table;

public class BetListPopup {
    public Label lblTitle = Label.xpath("//app-bet-list-dialog//span[@class='modal-title']");
    public Button btnClose = Button.xpath("//app-bet-list-dialog//div[contains(@class,'bet-list-header')]//em");
    public Table tblOrder = Table.xpath("//app-bet-list-dialog//table",11);
    public Label lbleventInfo = Label.xpath("//app-bet-list-dialog//div[contains(@class,'align-items-center')][2]");
}
