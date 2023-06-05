package pages.sb11.trading.popup;

import com.paltech.element.common.Label;

public class SmartGroupReportPopup {
    Label lblTitle = Label.xpath("//app-smart-group-report//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public Label lblGroupCode = Label.xpath("//app-smart-group-report//th[contains(text(),'Group Code')]//following::th[1]");
}
