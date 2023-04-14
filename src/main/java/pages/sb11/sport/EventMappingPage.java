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
    public DropDownBox ddSport = DropDownBox.xpath("//span[contains(text(),'Sport')]//following::select[1]");
    public DropDownBox ddLeague = DropDownBox.xpath("//label[contains(text(),'League')]//preceding::select[3]");
    public DropDownBox ddEvent = DropDownBox.xpath("//app-event-mapping/div/div[1]/div/div/div[2]/div/div[4]/select");
    public DropDownBox ddProvider = DropDownBox.xpath("//app-event-mapping/div/div[1]/div/div/div[2]/div/div[5]/select");
    public DropDownBox ddProviderLeague = DropDownBox.xpath("//label[contains(text(),'Provider League')]//following::select[1]");
    public DropDownBox ddProviderEvent = DropDownBox.xpath("//label[contains(text(),'Provider Event')]//following::select[1]");
    public TextBox txtProviderDate = TextBox.xpath("//label[contains(text(),'Provider Event Date')]//following::input[1]");
    public DateTimePicker dtpProviderDate = DateTimePicker.xpath(txtDate,"//bs-days-calendar-view");
    public Button btnSubmit = Button.xpath("//button[contains(text(),'Submit')]");
    public Button btnMap = Button.xpath("//button[contains(text(),'Map')]");
    public Table tbEvent = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[1]",4);
    public Table tbProviderEvent = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[2]",4);
    public Table tbMappedList = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[3]",11);
}
