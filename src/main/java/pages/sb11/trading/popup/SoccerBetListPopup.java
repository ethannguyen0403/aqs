package pages.sb11.trading.popup;

import com.paltech.element.common.Button;
import com.paltech.element.common.CheckBox;
import com.paltech.element.common.Icon;
import com.paltech.element.common.Label;
import controls.Table;
import objects.Order;
import pages.sb11.trading.controls.OrderRowControl;

import java.util.List;

public class SoccerBetListPopup {
    private Label lblTitle = Label.xpath("//app-entry-bet-log//span[contains(@class,'modal-title')]");
    private Button btnClose = Button.xpath("//app-entry-bet-log//span[contains(@class,'close-icon')]");
    private Label lblInfo = Label.xpath("//app-entry-bet-log//div[contains(@class,'bet-list-content')]/div[2]");
    public Icon icRefresh = Icon.xpath("//app-entry-bet-log//div[contains(@class,'bet-list-content')]/div[2]/em");
    int totalCol = 11;
    int colAccount = 2;
    int colBetType = 3;
    int colSelection = 4;
    int colHDP = 5;
    int colLive =6;
    int colPrice = 7;
    int colStake = 8;
    int colCUR = 9;
    int colEntryDate = 10;
    int colBetrefId = 11;
    private Table tblOrder = Table.xpath("//app-entry-bet-log//div[@id='customTable']//table",totalCol);
    /**
     * Click on close icon on the top right of the popup
     */
    public void close(){
        btnClose.click();
    }

    public boolean verifyOrderInfoDisplay(List<Order> lstOrder, String type){
       return true;

    }


}
