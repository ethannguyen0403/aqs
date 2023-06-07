package pages.sb11.trading;

import com.paltech.element.common.Button;
import com.paltech.element.common.DropDownBox;
import com.paltech.element.common.Label;
import com.paltech.element.common.TextBox;
import controls.DateTimePicker;
import pages.sb11.WelcomePage;

public class PhoneBettingPage extends WelcomePage {
    Label lblTitle = Label.xpath("//div[contains(@class,'main-box-header')]//span[1]");
    public String getTitlePage ()
    {
        return lblTitle.getText().trim();
    }

    public DropDownBox ddpCompanyUnit = DropDownBox.xpath("//span[text()='Company Unit']//following::select[1]");
    public DropDownBox ddpFinancialYear = DropDownBox.xpath("//span[text()='Financial Year']//following::select[2]");
    public DropDownBox ddpSport = DropDownBox.xpath("//span[text()='Sport']//following::select[3]");
    public TextBox txtFromDate = TextBox.xpath("//app-phone-betting-report//span[text() = 'From Date']//following::input[1]");
    public TextBox txtToDate = TextBox.xpath("//app-phone-betting-report//span[text() = 'To Date']//following::input[2]");
    public Label lblFromDate = Label.xpath("//app-phone-betting-report//span[text() = 'From Date']");
    public Label lblToDate = Label.xpath("//app-phone-betting-report//span[text() = 'To Date']");
    public DateTimePicker dtpFromDate = DateTimePicker.xpath(txtFromDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public DateTimePicker dtpToDate = DateTimePicker.xpath(txtToDate,"//bs-datepicker-container//div[contains(@class,'bs-datepicker-container')]//div[contains(@class,'bs-calendar-container ')]");
    public Button btnShow = Button.xpath("//button[text()='Show']");
}
