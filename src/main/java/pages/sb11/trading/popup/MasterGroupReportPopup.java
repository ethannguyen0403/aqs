package pages.sb11.trading.popup;

import com.paltech.element.common.Label;

public class MasterGroupReportPopup {
    Label lblTitle = Label.xpath("//app-group-detail//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public Label lblMasterCode = Label.xpath("//app-group-detail//th[contains(text(),'Master Code')]//following::td[1]");
}
