package pages.sb11.trading;
import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import controls.Table;
import pages.sb11.Header;

public class SoccerBetEntryPage extends BetEntryPage {
    private Label lblTitle = Label.xpath("//app-bet-entry-soccer//app-common-header-sport//div[contains(@class,'main-box-header')]/div[1]/span");
    private Label lblGoto = Label.xpath("//app-bet-entry-soccer//app-common-header-sport//div[contains(@class,'main-box-header')]/div[2]/span");
    private DropDownBox ddbSport = DropDownBox.id("navigate-page");
    int totalCol =14;
    public Table tblEvent = Table.xpath("//app-bet-entry-soccer//table",totalCol);
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

}
