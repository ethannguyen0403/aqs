package pages.sb11.soccer;

import com.paltech.element.common.Label;
import controls.Table;

public class PendingBetsPage {
    Label lblTitle = Label.xpath("//app-pending-bets//div[contains(@class,'main-box-header')]//span[1]");
    Label lblTableHeader = Label.xpath("//app-pending-bets//table//thead//th");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public String getTableHeader (){
        return lblTableHeader.getText().trim();
    }

    public Table tblPBM = Table.xpath("//app-pending-bets//table",12);
}
