package pages.sb11.soccer;

import com.paltech.element.common.*;
import controls.DateTimePicker;
import controls.Table;
import pages.sb11.WelcomePage;

public class BBGPhoneBettingPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//div[contains(text(),'Company Unit')]//following::select[1]");
    public DropDownBox ddpReportBy = DropDownBox.xpath("//div[contains(text(),'Report By')]//following::select[1]");
    public TextBox txtFromDate = TextBox.name("fromDate");
    public TextBox txtToDate = TextBox.name("toDate");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-days-calendar-view");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-days-calendar-view");

    public Button btnShowBetTypes = Button.xpath("//button[contains(text(),' Show  Bet Types ')]");
    public Button btnShowLeagues = Button.xpath("//button[contains(text(),' Show  Leagues ')]");
    public Button btnShowWinLose = Button.xpath("//button[contains(text(),' Show  Win/Lose ')]");
    public Button btnShow = Button.xpath("//button[contains(text(),'Show')]");
    public Table tblOrder = Table.xpath("//app-phone-betting//table",12);
}
