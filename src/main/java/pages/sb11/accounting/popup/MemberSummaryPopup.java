package pages.sb11.accounting.popup;

import com.paltech.element.common.Label;
import controls.Table;

public class MemberSummaryPopup {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    Label lblMaster = Label.xpath("//div[contains(@class,'main-box-header')]//span[contains(@class,'text-white')]");
    public String getTitleMaster ()
    {
        return lblMaster.getText().trim();
    }

    public Table tbMemSummary = Table.xpath("//app-accounting-current-detail//table",7);
}
