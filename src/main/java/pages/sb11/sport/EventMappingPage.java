package pages.sb11.sport;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import common.SBPConstants;
import controls.DateTimePicker;
import controls.Table;
import org.testng.Assert;
import pages.sb11.WelcomePage;

import static common.SBPConstants.PROVIDER_LIST;
import static common.SBPConstants.SPORT_LIST;

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
    public Button btnMap = Button.xpath("//button[text()='Map']");
    public Table tbEvent = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[1]",4);
    public Table tbProviderEvent = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[2]",4);
    public Table tbMappedList = Table.xpath("//div[contains(@class,'main-box-header')]//following::table[3]",11);
    public Label lblDate = Label.xpath("//label[(text()='Date')]");
    public Label lblProviderEventDate = Label.xpath("//label[(text()='Provider Event Date')]");

    public void verifyUI() {
        System.out.println("Date, Sport, League, Event, Provider, Provider League, Provider Event, Provider Event Date, Submit button and Map button");
        Assert.assertEquals(lblDate.getText(), "Date","Failed! Date datetime picker is not displayed!");
        Assert.assertEquals(ddSport.getOptions(),SPORT_LIST,"Failed! Sport dropdown is not displayed!");
        Assert.assertTrue(ddLeague.getOptions().contains("All"),"Failed! League dropdown is not displayed!");
        Assert.assertTrue(ddEvent.getOptions().contains("All"),"Failed! Event dropdown is not displayed!");
        Assert.assertEquals(ddProvider.getOptions(),PROVIDER_LIST,"Failed! Provider dropdown is not displayed!");
        Assert.assertTrue(ddProviderLeague.getOptions().contains("All"),"Failed! Provider League dropdown is not displayed!");
        Assert.assertTrue(ddProviderEvent.getOptions().contains("All"),"Failed! Provider Event dropdown is not displayed!");
        Assert.assertEquals(lblProviderEventDate.getText(),"Provider Event Date","Failed! Provider Event Date datetime picker is not displayed!");
        Assert.assertEquals(btnSubmit.getText(),"Submit","Failed! Submit button is not displayed!");
        Assert.assertEquals(btnMap.getText(),"Map","Failed! Map button is not displayed!");
        System.out.println("Event, Provider Event and Mapped List table header columns are correctly display");
        Assert.assertEquals(tbEvent.getHeaderNameOfRows(), SBPConstants.EventMapping.EVENT_TABLE_HEADER,"FAILED! Event table header is incorrect display");
        Assert.assertEquals(tbProviderEvent.getHeaderNameOfRows(), SBPConstants.EventMapping.PROVIDER_EVENT_TABLE_HEADER,"FAILED! Provider Event table header is incorrect display");
        Assert.assertEquals(tbMappedList.getHeaderNameOfRows(), SBPConstants.EventMapping.MAPPED_LIST_TABLE_HEADER,"FAILED! Mapped List table header is incorrect display");
    }
}
