package pages.ess;

import controls.Table;
import com.paltech.element.common.Button;

public class MasterAccountPage {
    public Table tbMasterAccount = Table.xpath("//span[contains(text(),'Master Account')]//following::table[1]",3);
    public Button btnAdd = Button.xpath("//button[contains(@class,'btn-success')]");
}
