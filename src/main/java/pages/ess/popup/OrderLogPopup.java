package pages.ess.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.Label;
import controls.Table;

public class OrderLogPopup {
    public Label lblTitle = Label.xpath("//app-order-log//div[@class='card-header']/span");
    //public Button btnClose = Button.xpath("//app-order-log//div[contains(@class,'bet-list-header')]//em");
    public Table tblOrder = Table.xpath("//app-order-log//table",11);



}
