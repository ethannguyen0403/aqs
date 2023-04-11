package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

public class EventMappingPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'card-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }
    public TextBox txtDate = TextBox.name("fromDate");
    public DateTimePicker dtpDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public DropDownBox ddSport = DropDownBox.xpath("//app-event-mapping//span[contains(text(),'Sport')]//following::select[1]");
    public DropDownBox ddLeague = DropDownBox.xpath("//label[contains(text(),'League')]//preceding::select[3]");
    public DropDownBox ddEvent = DropDownBox.xpath("//label[contains(text(),'League')]//preceding::select[3]");
}
